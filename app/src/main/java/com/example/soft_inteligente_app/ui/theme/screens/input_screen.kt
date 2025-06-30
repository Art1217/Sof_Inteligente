package com.example.soft_inteligente_app.ui.theme.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.soft_inteligente_app.data.RutaCompartida


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavHostController) {
    val numPuntos = 5
    val distancias = remember { Array(numPuntos) { DoubleArray(numPuntos) } }
    val inputs = remember { mutableStateMapOf<Pair<Int, Int>, String>() }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
                .padding(paddingValues)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Ingrese las distancias entre puntos",color =Color.White,
                modifier = Modifier.padding(20.dp), fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold)

            for (i in 0 until numPuntos) {
                for (j in i + 1 until numPuntos) {
                    val key = i to j
                    OutlinedTextField(
                        value = inputs[key] ?: "",
                        onValueChange = { inputs[key] = it },
                        label = { Text("Punto ${i + 1} a Punto ${j + 1}", color = Color.Gray) },
                        textStyle = TextStyle(color = Color.White),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White.copy(alpha = 0.2f),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }

            Button(onClick = {
                try {
                    inputs.forEach { (pair, valor) ->
                        val (i, j) = pair
                        val v = valor.toDouble()
                        distancias[i][j] = v
                        distancias[j][i] = v
                    }
                    RutaCompartida.distancias = distancias
                    navController.navigate("res_gene")
                } catch (e: Exception) {
                }
            }, modifier = Modifier.padding(top = 16.dp)) {
                Text("Calcular Ruta Óptima")
            }
        }
    }
}