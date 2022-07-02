package com.johnmarcos.pagamentos.ui.pagamentos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.johnmarcos.pagamentos.data.entity.Categoria
import com.google.accompanist.insets.systemBarsPadding
import java.util.*
import kotlinx.coroutines.launch

@Composable
fun Pagamento(
    onBackPress: () -> Unit,
    viewModel: PagamentoViewModel = viewModel()
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val titulo = rememberSaveable { mutableStateOf("") }
    val categoria = rememberSaveable { mutableStateOf("") }
    val valor = rememberSaveable { mutableStateOf("") }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Pagamento")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = titulo.value,
                    onValueChange = { titulo.value = it },
                    label = { Text(text = "Título do pagamento")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                ListaCategoriaDropdown(
                    viewState = viewState,
                    categoria = categoria
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = valor.value,
                    onValueChange = { valor.value = it },
                    label = { Text(text = "Valor")},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    enabled = true,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.salvarPagamento(
                                com.johnmarcos.pagamentos.data.entity.Pagamento(
                                    titulo_pagamento = titulo.value,
                                    valor_pagamento = valor.value.toDouble(),
                                    data_pagamento = Date().time,
                                    pagamento_categoria_id = getIdCategoria(viewState.categorias, categoria.value)
                                )
                            )
                        }
                        onBackPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Salvar Pagamento")
                }
            }
        }
    }
}

private fun getIdCategoria(categorias: List<Categoria>, nomeCategoria: String): Long {
    return categorias.first { categoria -> categoria.nome == nomeCategoria }.id
}

@Composable
private fun ListaCategoriaDropdown(
    viewState: PagamentoViewState,
    categoria: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column {
        OutlinedTextField(
            value = categoria.value,
            onValueChange = { categoria.value = it},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Categoria") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            viewState.categorias.forEach { dropDownOption ->
                DropdownMenuItem(
                    onClick = {
                        categoria.value = dropDownOption.nome
                        expanded = false
                    }
                ) {
                    Text(text = dropDownOption.nome)
                }

            }
        }
    }
}