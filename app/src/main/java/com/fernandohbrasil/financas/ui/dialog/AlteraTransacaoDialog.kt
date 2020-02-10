package com.fernandohbrasil.financas.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.fernandohbrasil.financas.R
import com.fernandohbrasil.financas.extension.formataParaBrasileiro
import com.fernandohbrasil.financas.model.Tipo
import com.fernandohbrasil.financas.model.Transacao

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : FormularioTransacaoDialog(viewGroup, context) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    fun chama(transacao: Transacao, delegate: (transacao: Transacao) -> Unit) {
        val tipo = transacao.tipo
        super.chama(tipo, delegate)

        incializaCampos(transacao)

    }

    private fun incializaCampos(
        transacao: Transacao
    ) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(transacao)
    }

    private fun inicializaCampoCategoria(
        transacao: Transacao
    ) {
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(transacao.tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }
}