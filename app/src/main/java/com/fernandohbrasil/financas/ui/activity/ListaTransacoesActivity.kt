package com.fernandohbrasil.financas.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.fernandohbrasil.financas.R
import com.fernandohbrasil.financas.dao.TransacaoDAO
import com.fernandohbrasil.financas.model.Tipo
import com.fernandohbrasil.financas.model.Transacao
import com.fernandohbrasil.financas.ui.ResumoView
import com.fernandohbrasil.financas.ui.adapter.ListaTransacoesAdapter
import com.fernandohbrasil.financas.ui.dialog.AdicionaTransacaoDialog
import com.fernandohbrasil.financas.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDAO()
    private val transacoes = dao.transacoes

    private val viewActivity by lazy {
        window.decorView
    }

    private val viewGroupActivity by lazy {
        viewActivity as ViewGroup
    }

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
        AdicionaTransacaoDialog(viewGroupActivity, this)
            .chama(tipo) { transacaoCriada ->
                adiciona(transacaoCriada)
                lista_transacoes_adiciona_menu.close(true)
            }
    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {

        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewActivity, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogAlteracao(transacao, posicao)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId
        if (idDoMenu == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            remove(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogAlteracao(
        transacao: Transacao,
        posicao: Int
    ) {
        AlteraTransacaoDialog(viewGroupActivity, this)
            .chama(transacao) { transacaoAlterada ->
                altera(transacaoAlterada, posicao)
            }

    }

    private fun altera(transacao: Transacao, posicao: Int) {
        dao.altera(transacao, posicao)
        atualizaTransacoes()
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