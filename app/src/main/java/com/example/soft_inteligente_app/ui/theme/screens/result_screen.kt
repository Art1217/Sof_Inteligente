package com.example.soft_inteligente_app.ui.theme.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.soft_inteligente_app.R
import com.example.soft_inteligente_app.data.GeneticAlgorithm
import com.example.soft_inteligente_app.data.RutaCompartida
import kotlin.random.Random
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(navController: NavHostController) {
    val distancias = RutaCompartida.distancias
    val numPuntos = distancias.size
    val coords = remember { List(numPuntos) { Random.nextInt(50, 350) to Random.nextInt(50, 600) } }
    val (mejorRuta, distanciaTotal) = remember { GeneticAlgorithm.resolver(distancias) }

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
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Volver")
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Ruta Óptima", style = MaterialTheme.typography.titleLarge, fontStyle = FontStyle.Italic
                , fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Spacer(modifier = Modifier.padding(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    coords.forEachIndexed { i, (x, y) ->
                        drawCircle(
                            Color.Blue,
                            radius = 10f,
                            center = androidx.compose.ui.geometry.Offset(x.toFloat(), y.toFloat())
                        )
                        drawContext.canvas.nativeCanvas.drawText(
                            "${i + 1}",
                            x + 12f,
                            y.toFloat(),
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 32f
                            })
                    }
                    for (i in mejorRuta.indices) {
                        val a = mejorRuta[i]
                        val b = mejorRuta[(i + 1) % numPuntos]
                        drawLine(
                            Color.Red,
                            start = androidx.compose.ui.geometry.Offset(
                                coords[a].first.toFloat(),
                                coords[a].second.toFloat()
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                coords[b].first.toFloat(),
                                coords[b].second.toFloat()
                            ),
                            strokeWidth = 4f
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Ruta: ${mejorRuta.joinToString(" -> ") { (it + 1).toString() }}")
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Distancia total: ${"%.2f".format(distanciaTotal)} km", modifier = Modifier.padding(bottom = 40.dp))


        }
    }
}

    @Composable
    fun BottomNavPanel(navController: NavHostController, modifier: Modifier= Modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        BottomNavigation(
            modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White,
        ) {
            BottomNavigationItem(
                selected = currentRoute == "actions",
                onClick = { navController.navigate("menu")},
                icon = {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                            .padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.boton1),
                            contentDescription = "Home",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(70.dp)
                        )
                    }

                }
            )

        }
    }


