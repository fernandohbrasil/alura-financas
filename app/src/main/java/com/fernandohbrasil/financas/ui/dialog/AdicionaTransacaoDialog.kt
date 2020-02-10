package com.fernandohbrasil.financas.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.fernandohbrasil.financas.R
import com.fernandohbrasil.financas.model.Tipo

class AdicionaTransacaoDialog(
    viewGroup: ViewGroup,
    context: Context
) : FormularioTransacaoDialog(viewGroup, context) {
    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }
}