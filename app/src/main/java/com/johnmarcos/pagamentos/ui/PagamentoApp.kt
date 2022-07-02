package com.johnmarcos.pagamentos.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.johnmarcos.pagamentos.ui.home.Home
import com.johnmarcos.pagamentos.ui.login.Login
import com.johnmarcos.pagamentos.ui.pagamentos.Pagamento

@Composable
fun PagamentoApp(
    appState: PagamentoAppState = lembrarPagamentoAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(navController = appState.navController)
        }
        composable(route = "home") {
            Home(
                navController = appState.navController
            )
        }
        composable(route = "pagamento") {
            Pagamento(onBackPress = appState::navigateBack)
        }
    }
}