package com.example.soft_inteligente_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soft_inteligente_app.R

@Composable
fun GraficRNScreen(navController: NavHostController) {

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
            modifier = Modifier.background(gradientBrush).padding(paddingValues).padding(8.dp).fillMaxSize().verticalScroll(scrollState)
            ,            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(15.dp))
            Text("Matriz de confusión",color= Color.White, fontSize= 20.sp, fontWeight = FontWeight.ExtraBold)
            val image = painterResource(id = R.drawable.matrizred)
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Perdida",color= Color.White, fontSize= 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.padding(5.dp))
            val image3 = painterResource(id = R.drawable.perdidared)
            Image(
                painter = image3,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Text("Precisión ",color= Color.White, fontSize= 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.padding(10.dp))
            val image4 = painterResource(id = R.drawable.precisionred)
            Image(
                painter = image4,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
@Preview
fun PreviewGraficR(){
    val navController = rememberNavController()
    GraficRNScreen(navController)
}