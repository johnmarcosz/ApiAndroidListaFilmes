package com.johnmarcos.pagamentos.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class PagamentoAppState(
    val navController: NavHostController
) {
    fun navigateBack() {
        navController.popBackStack()
    }
}

@Composable
fun lembrarPagamentoAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    PagamentoAppState(navController)
}