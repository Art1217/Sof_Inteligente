package com.example.soft_inteligente_app.ui.theme.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.soft_inteligente_app.data.NaiveBayesMock


@Composable
fun NaiveBayesScreen(navController: NavHostController) {
    var ph by remember { mutableStateOf(7.0) }
    var temperatura by remember { mutableStateOf(22.0) }
    var turbidez by remember { mutableStateOf(2) }
    var resultado by remember { mutableStateOf("") }
    val datos = remember { NaiveBayesMock.generarDatos() }
    val metricas = remember { NaiveBayesMock.calcularMetricas(datos) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavPanel(navController)
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF4393C5),
                Color.White
            )
        )

        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(scrollState)) {




            Text("Métricas del modelo:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(15.dp))
            metricas.forEach { (clave, valor) ->
                Text("$clave: ${"%.2f".format(valor)}", modifier = Modifier.padding(top=10.dp,start = 20.dp, end = 20.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Canvas(modifier = Modifier.fillMaxWidth().height(300.dp).padding(18.dp)) {
                datos.forEach {
                    val color = if (it.calidad == "buena") Color.Green else Color.Red
                    val x = ((it.ph - 5.5) / 3.0 * size.width).toFloat()
                    val y = size.height - ((it.temperatura - 15.0) / 15.0 * size.height).toFloat()
                    drawCircle(
                        color = color,
                        radius = 4f,
                        center = androidx.compose.ui.geometry.Offset(x, y)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Predicción de Calidad del Agua", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(15.dp))

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = ph.toString(),
                onValueChange = { ph = it.toDoubleOrNull() ?: 7.0 },
                label = { Text("pH") },modifier = Modifier.padding(15.dp))
            OutlinedTextField(
                value = temperatura.toString(),
                onValueChange = { temperatura = it.toDoubleOrNull() ?: 22.0 },
                label = { Text("Temperatura (°C)") },modifier = Modifier.padding(15.dp))
            OutlinedTextField(
                value = turbidez.toString(),
                onValueChange = { turbidez = it.toIntOrNull() ?: 2 },
                label = { Text("Turbidez") },modifier = Modifier.padding(15.dp))

            Button(onClick = {
                resultado = NaiveBayesMock.predecir(ph, temperatura, turbidez)
            }, modifier = Modifier.padding(15.dp)) {
                Text("Predecir")
            }

            Text(
                "Resultado: ${resultado.uppercase()}",
                color = if (resultado == "buena") Color.Green else Color.Red,modifier = Modifier.padding(15.dp)
            )
        }
    }
}

