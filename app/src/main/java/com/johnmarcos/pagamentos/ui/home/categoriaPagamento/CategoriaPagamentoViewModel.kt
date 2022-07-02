package com.johnmarcos.pagamentos.ui.home.categoriaPagamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmarcos.pagamentos.Graph
import com.johnmarcos.pagamentos.data.repository.PagamentoRepository
import com.johnmarcos.pagamentos.data.room.PagamentoParaCategoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CategoriaPagamentoViewModel(
    private val idCategoria: Long,
    private val pagamentoRepository: PagamentoRepository = Graph.pagamentoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CategoriaPagamentoViewState())

    val state: StateFlow<CategoriaPagamentoViewState>
        get() = _state

    init {
        viewModelScope.launch {
            pagamentoRepository.pagamentosNaCategoria(idCategoria).collect { list ->
                _state.value = CategoriaPagamentoViewState(
                    pagamentos = list
                )
            }
        }
    }
}

data class CategoriaPagamentoViewState(
    val pagamentos: List<PagamentoParaCategoria> = emptyList()
)