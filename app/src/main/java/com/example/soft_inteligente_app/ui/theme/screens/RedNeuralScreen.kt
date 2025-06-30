package com.example.soft_inteligente_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soft_inteligente_app.R
import com.example.soft_inteligente_app.data.IrisModelHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IrisClassifierScreen(navController: NavHostController) {
    val context = LocalContext.current
    val helper = remember { IrisModelHelper(context) }

    var sepalLength by remember { mutableStateOf("") }
    var sepalWidth by remember { mutableStateOf("") }
    var petalLength by remember { mutableStateOf("") }
    var petalWidth by remember { mutableStateOf("") }

    var resultado by remember { mutableStateOf("") }
    var probabilidades by remember { mutableStateOf<List<Float>>(emptyList()) }
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
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues).background(brush = gradientBrush)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.padding(20.dp))
            Text("Clasificador de Flores (Iris)"
                , style = MaterialTheme.typography.titleLarge
                , color = Color.White, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally))

            Spacer(Modifier.padding(10.dp))
            Text("Escriba la Longitud del sépalo (cm) entre 4.3 a 7.9", color = Color.White
            , modifier = Modifier.padding(start = 10.dp, end = 10.dp))
            OutlinedTextField(
                value = sepalLength,
                onValueChange = { sepalLength = it },
                label = { Text("Longitud del sépalo", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            Spacer(Modifier.padding(10.dp))
            Text("Escriba Ancho del sépalo (cm) entre 2.0 a 4.4", color = Color.White
                , modifier = Modifier.padding(start = 10.dp, end = 10.dp))
            OutlinedTextField(
                value = sepalWidth,
                onValueChange = { sepalWidth = it },
                label = { Text("Ancho del sépalo", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            Spacer(Modifier.padding(10.dp))
            Text("Escriba Longitud del pétalo (cm) entre 2.0 a 4.4", color = Color.White
                , modifier = Modifier.padding(start = 10.dp, end = 10.dp))
            OutlinedTextField(
                value = petalLength,
                onValueChange = { petalLength = it },
                label = { Text("Longitud del pétalo", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            Spacer(Modifier.padding(10.dp))
            Text("Escriba Ancho del pétalo (cm) entre 0.1 a 2.5", color = Color.White
            , modifier = Modifier.padding(start = 10.dp, end = 10.dp))
            OutlinedTextField(
                value = petalWidth,
                onValueChange = { petalWidth = it },
                label = { Text("Ancho del pétalo", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            Spacer(Modifier.padding(10.dp))
            Button(
                onClick = {
                    val input = floatArrayOf(
                        sepalLength.toFloatOrNull() ?: 0f,
                        sepalWidth.toFloatOrNull() ?: 0f,
                        petalLength.toFloatOrNull() ?: 0f,
                        petalWidth.toFloatOrNull() ?: 0f
                    )
                    val (clase, probs) = helper.predecirConProbabilidades(input)
                    resultado = clase
                    probabilidades = probs
                },
                modifier = Modifier
                    .padding(bottom = 20.dp,top = 30.dp).fillMaxWidth()
                ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4393C5)))
            {
                Text("Predecir Tipo de Flor", color = Color.White)
            }

            if (resultado.isNotEmpty()) {
                Text("Resultado: $resultado", style = MaterialTheme.typography.titleMedium
                    , textAlign = TextAlign.Center,color=Color.Black, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(8.dp))

                val clases = listOf("Setosa", "Versicolor", "Virginica")
                probabilidades.forEachIndexed { index, prob ->
                    Text("${clases[index]}: ${String.format("%.2f", prob * 100)}%",color = Color.Gray
                        , modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                }
            }
            Spacer(Modifier.height(16.dp))

            val imageRes = when (resultado.lowercase()) {
                "setosa" -> R.drawable.setosa
                "versicolor" -> R.drawable.versicolor
                "virginica" -> R.drawable.virginica
                else -> null
            }

            imageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Imagen de $resultado",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@Composable
@Preview
fun previewRed (){
    val navController = rememberNavController()
    IrisClassifierScreen(navController)
}