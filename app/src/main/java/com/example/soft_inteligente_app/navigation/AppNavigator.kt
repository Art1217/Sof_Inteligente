package com.example.soft_inteligente_app.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.soft_inteligente_app.ui.theme.screens.ClasificadorScreen
import com.example.soft_inteligente_app.ui.theme.screens.GraficCIScreen
import com.example.soft_inteligente_app.ui.theme.screens.GraficNaiveScreen
import com.example.soft_inteligente_app.ui.theme.screens.GraficRNScreen
import com.example.soft_inteligente_app.ui.theme.screens.InputScreen
import com.example.soft_inteligente_app.ui.theme.screens.IrisClassifierScreen
import com.example.soft_inteligente_app.ui.theme.screens.ResultScreen
import com.example.soft_inteligente_app.ui.theme.screens.TextClassifierScreen
import com.example.soft_inteligente_app.ui.theme.screens.WaterQualityScreen
import com.example.soft_inteligente_app.ui.theme.screens.menu

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") { menu(navController) }
        composable("alg_gene") { InputScreen(navController) }
        composable("res_gene") { ResultScreen(navController) }
        composable("naive_bayes") { WaterQualityScreen(navController) }
        composable("red_neuronal") {IrisClassifierScreen(navController)  }
        composable("grafico_naive") {GraficNaiveScreen(navController)}
        composable("grafico_red") {GraficRNScreen(navController)}
        composable("clas_im") {ClasificadorScreen(navController)  }
        composable("grafico_ci") {GraficCIScreen(navController)  }
        composable("natural_leng"){TextClassifierScreen(navController)}
    }
}