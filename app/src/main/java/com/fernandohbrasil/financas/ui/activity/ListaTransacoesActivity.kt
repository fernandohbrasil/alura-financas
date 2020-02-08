package com.fernandohbrasil.financas.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.fernandohbrasil.financas.R
import com.fernandohbrasil.financas.delegate.TransacaoDelegate
import com.fernandohbrasil.financas.model.Tipo
import com.fernandohbrasil.financas.model.Transacao
import com.fernandohbrasil.financas.ui.ResumoView
import com.fernandohbrasil.financas.ui.adapter.ListaTransacoesAdapter
import com.fernandohbrasil.financas.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        ConfiguraFab()
    }

    private fun ConfiguraFab() {
        lista_transacoes_adiciona_receita
            .setOnClickListener {
                chamaDialogDeAdicao(Tipo.RECEITA)
            }

        lista_transacoes_adiciona_despesa
            .setOnClickListener {
                chamaDialogDeAdicao(Tipo.DESPESA)
            }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    atualizaTransacoes(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }

            })
    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        val transacoes = listOf(
            Transacao(
                data = Calendar.getInstance(),
                valor = BigDecimal(100.0),
                tipo = Tipo.DESPESA,
                categoria = "Almoco de final de semana"
            ),
            Transacao(valor = BigDecimal(500.0), categoria = "Economia", tipo = Tipo.RECEITA),
            Transacao(valor = BigDecimal(800.0), tipo = Tipo.DESPESA),
            Transacao(valor = BigDecimal(200.0), categoria = "Premio", tipo = Tipo.RECEITA)
        )
        return transacoes
    }
}