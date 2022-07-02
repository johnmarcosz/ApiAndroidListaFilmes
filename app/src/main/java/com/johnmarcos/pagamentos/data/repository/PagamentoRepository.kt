package com.johnmarcos.pagamentos.data.repository

import com.johnmarcos.pagamentos.data.entity.Pagamento
import com.johnmarcos.pagamentos.data.room.PagamentoDao
import com.johnmarcos.pagamentos.data.room.PagamentoParaCategoria
import kotlinx.coroutines.flow.Flow

class PagamentoRepository(
    private val pagamentoDao: PagamentoDao
) {

    fun pagamentosNaCategoria(idCategoria: Long) : Flow<List<PagamentoParaCategoria>> {
        return pagamentoDao.pagamentosDaCategoria(idCategoria)
    }

    suspend fun addPagamento(pagamento: Pagamento) = pagamentoDao.insert(pagamento)
}