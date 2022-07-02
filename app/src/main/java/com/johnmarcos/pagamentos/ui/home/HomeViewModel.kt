package com.johnmarcos.pagamentos.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmarcos.pagamentos.Graph
import com.johnmarcos.pagamentos.data.entity.Categoria
import com.johnmarcos.pagamentos.data.repository.CategoriaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoriaRepository: CategoriaRepository = Graph.categoriaRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    private val _categoriaSelecionada = MutableStateFlow<Categoria?>(null)

    val state: StateFlow<HomeViewState>
        get() = _state

    fun onCategoriaSelecionada(categoria: Categoria) {
        _categoriaSelecionada.value = categoria
    }

    init {
        viewModelScope.launch {

            combine(
                categoriaRepository.categorias().onEach { list ->
                    if (list.isNotEmpty() && _categoriaSelecionada.value == null) {
                        _categoriaSelecionada.value = list[0]
                    }
                },
                _categoriaSelecionada
            ) { categorias, categoriaSelecionada ->
                HomeViewState(
                    categorias = categorias,
                    categoriaSelecionada = categoriaSelecionada
                )
            }.collect { _state.value = it }
        }

        carregarCategoriasDoBD()
    }

    private fun carregarCategoriasDoBD() {
        val list = mutableListOf(
            Categoria(nome = "Alimentação"),
            Categoria(nome = "Saúde"),
            Categoria(nome = "Poupança"),
            Categoria(nome = "Bebidas"),
            Categoria(nome = "Roupas"),
            Categoria(nome = "Investimento"),
            Categoria(nome = "Viagens"),
            Categoria(nome = "Combustível"),
            Categoria(nome = "Reparos"),
            Categoria(nome = "Café")
        )
        viewModelScope.launch {
            list.forEach { categoria -> categoriaRepository.addCategoria(categoria) }
        }
    }
}

data class HomeViewState(
    val categorias: List<Categoria> = emptyList(),
    val categoriaSelecionada: Categoria? = null
)