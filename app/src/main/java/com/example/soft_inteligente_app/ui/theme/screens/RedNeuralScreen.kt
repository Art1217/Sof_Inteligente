package com.example.soft_inteligente_app.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.soft_inteligente_app.R
import com.example.soft_inteligente_app.data.RedNeuronalMock


@Composable
fun RedNeuralScreen(navController: NavHostController) {
    var tiempo by remember { mutableStateOf(0.0) }
    var espacio by remember { mutableStateOf(0.0) }
    var alergia by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf("") }
    var accuracy by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        val datos = RedNeuronalMock.generarDatos()
        accuracy = RedNeuronalMock.calcularMetricas(datos)["Accuracy"] ?: 0.0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(color=Color.White),
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

        Column(modifier = Modifier.padding(paddingValues).verticalScroll(scrollState)) {
            Text(
                "Clasificación de Mascota Ideal (Red Neuronal)",
                style = MaterialTheme.typography.titleLarge, color = Color.Black,
                modifier = Modifier.padding(top = 50.dp, start = 15.dp, end = 15.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = tiempo.toString(),
                onValueChange = { tiempo = it.toDoubleOrNull() ?: 0.0 },
                label = { Text("Tiempo libre diario (horas)") },
                modifier = Modifier.fillMaxWidth().padding(15.dp)
            )

            OutlinedTextField(
                value = espacio.toString(),
                onValueChange = { espacio = it.toDoubleOrNull() ?: 0.0 },
                label = { Text("Espacio disponible (m²)") },
                modifier = Modifier.fillMaxWidth().padding(15.dp)
            )

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(checked = alergia, onCheckedChange = { alergia = it })
                Text("Tengo alergia a mascotas")
            }

            Button(onClick = {
                resultado =
                    RedNeuronalMock.predecir(tiempo, espacio, if (alergia) 1 else 0).uppercase()
            }, modifier = Modifier.padding(top = 16.dp, start = 15.dp, end = 15.dp)) {
                Text("Predecir Mascota Ideal")
            }

            if (resultado.isNotEmpty()) {
                Text(
                    "Mascota recomendada: $resultado",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Text(
                "Precisión del modelo: ${String.format("%.2f", accuracy * 100)}%",
                style = MaterialTheme.typography.bodyMedium,color= Color.Black,
                modifier = Modifier.padding(top = 8.dp, start = 15.dp, end = 15.dp)
            )
        }
    }
}

