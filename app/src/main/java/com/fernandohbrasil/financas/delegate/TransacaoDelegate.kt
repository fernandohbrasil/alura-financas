package com.fernandohbrasil.financas.delegate

import com.fernandohbrasil.financas.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}