package com.johnmarcos.pagamentos.ui.pagamentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmarcos.pagamentos.Graph
import com.johnmarcos.pagamentos.data.entity.Categoria
import com.johnmarcos.pagamentos.data.entity.Pagamento
import com.johnmarcos.pagamentos.data.repository.CategoriaRepository
import com.johnmarcos.pagamentos.data.repository.PagamentoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PagamentoViewModel(
    private val pagamentoRepository: PagamentoRepository = Graph.pagamentoRepository,
    private val categoriaRepository: CategoriaRepository = Graph.categoriaRepository
): ViewModel() {
    private val _state = MutableStateFlow(PagamentoViewState())

    val state: StateFlow<PagamentoViewState>
        get() = _state

    suspend fun salvarPagamento(pagamento: Pagamento): Long {
        return pagamentoRepository.addPagamento(pagamento)
    }

    init {
        viewModelScope.launch {
            categoriaRepository.categorias().collect { categorias ->
                _state.value = PagamentoViewState(categorias)
            }
        }
    }
}

data class PagamentoViewState(
    val categorias: List<Categoria> = emptyList()
)