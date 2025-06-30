package com.example.soft_inteligente_app.ui.theme.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.example.soft_inteligente_app.data.WaterQualityPredictor


@Composable
fun WaterQualityScreen(navController: NavHostController) {
    val context = LocalContext.current
    val predictor = remember { WaterQualityPredictor(context) }
    var ph by remember { mutableStateOf("") }
    var hardness by remember { mutableStateOf("") }
    var solids by remember { mutableStateOf("") }
    var chloramines by remember { mutableStateOf("") }
    var sulfate by remember { mutableStateOf("") }
    var conductivity by remember { mutableStateOf("") }
    var organic by remember { mutableStateOf("") }
    var trihalo by remember { mutableStateOf("") }
    var turbidity by remember { mutableStateOf("") }

    var resultado by remember { mutableStateOf("") }
    fun setEjemploPotable() {
        ph = "6"
        hardness = "60.0"
        solids = "500.0"
        chloramines = "10"
        sulfate = "100"
        conductivity = "200.0"
        organic = "27.0"
        trihalo = "70.0"
        turbidity = "6"
    }
    fun setEjemploNoPotable() {
        ph = "3.5"
        hardness = "600.0"
        solids = "60000.0"
        chloramines = "0.1"
        sulfate = "950.0"
        conductivity = "1200.0"
        organic = "50.0"
        trihalo = "200.0"
        turbidity = "8.0"
    }
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
            .fillMaxSize().background(gradientBrush)
            .padding(paddingValues)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.padding(20.dp))
        Text("Ingrese los parámetros del agua", style = MaterialTheme.typography.titleLarge
        , color = Color.White, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { setEjemploPotable() },
                modifier = Modifier.padding(bottom = 20.dp,top = 30.dp)
                ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4393C5))
            ) {
                Text("Ejemplo Potable", color = Color.White)
            }
            Button(onClick = { setEjemploNoPotable() },
                modifier = Modifier.padding(bottom = 20.dp,top = 30.dp)
                ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ,colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                Text("Ejemplo No Potable", color = Color.Black)
            }
        }
        InputField("pH", "Rango recomendado: 6.5 – 8.5", ph) { ph = it }
        InputField("Hardness", "Rango: 50 – 300 mg/L", hardness) { hardness = it }
        InputField("Solids", "Rango: 500 – 40000 ppm", solids) { solids = it }
        InputField("Chloramines", "Rango: 0 – 15 ppm", chloramines) { chloramines = it }
        InputField("Sulfate", "Rango: 100 – 500 mg/L", sulfate,) { sulfate = it }
        InputField("Conductivity", "Rango: 100 – 800 µS/cm", conductivity) { conductivity = it }
        InputField("Organic Carbon", "Rango: 2 – 30 mg/L", organic) { organic = it }
        InputField("Trihalomethanes", "Rango: 0 – 120 µg/L", trihalo) { trihalo = it }
        InputField("Turbidity", "Rango: 0 – 7 NTU", turbidity) { turbidity = it }

        Button(
            onClick = {
                val campos = listOf(
                    ph, hardness, solids, chloramines, sulfate,
                    conductivity, organic, trihalo, turbidity
                )

                if (campos.any { it.toFloatOrNull() == null }) {
                    resultado = "Ingrese todos los valores correctamente"
                    return@Button
                }

                val prob = predictor.predictProbabilidad(
                    ph = ph.toFloat() ,
                    hardness = hardness.toFloat(),
                    solids = solids.toFloat(),
                    chloramines = chloramines.toFloat(),
                    sulfate = sulfate.toFloat(),
                    conductivity = conductivity.toFloat(),
                    organic = organic.toFloat(),
                    trihalomethanes = trihalo.toFloat(),
                    turbidity = turbidity.toFloat()
                )

                resultado = if (prob > 0.5f)
                    "AGUA POTABLE (${(prob * 100).toInt()}%)"
                else
                    "AGUA NO POTABLE (${(prob * 100).toInt()}%)"
            },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth(),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4393C5))
        ) {
            Text("Predecir",color= Color.White)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Resultado: $resultado",
            color = if (resultado.contains("NO")) Color.Red else Color(0xFF2E7D32),
            style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center
            , modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    rango: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(rango, color = Color.Black, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 10.dp, end = 10.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = Color.Black) },
            textStyle = TextStyle(color = Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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


