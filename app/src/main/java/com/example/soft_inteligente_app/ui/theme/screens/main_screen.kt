package com.example.soft_inteligente_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.soft_inteligente_app.R

@Composable
fun menu(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.weight(1f).fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color(0xFFB3E5FC))
        ){
            Text("MENÚ",fontSize = 30.sp, fontWeight = FontWeight
            .ExtraBold, color = Color.White, modifier = Modifier.padding(10.dp).align(Alignment.Center))
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp).clickable { navController.navigate("alg_gene") },
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.genetico1)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Text("Algoritmo genético", fontSize = 18.sp, fontWeight = FontWeight
                .ExtraBold, color = Color.White, textAlign = TextAlign.Center
                , modifier = Modifier.weight(3f).fillMaxWidth().padding(20.dp) )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.naive)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Column(modifier = Modifier.weight(3f).fillMaxSize()) {
                Text(
                    "Naive Bayes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight
                        .ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(top = 10.dp)
                )
                Row(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                        ,horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick ={navController.navigate("grafico_naive")},modifier = Modifier
                        ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ,colors = ButtonDefaults.buttonColors(containerColor = Color.White)) { Text("Gráficas", color = Color.Black)}
                    Button(onClick ={navController.navigate("naive_bayes")},modifier = Modifier
                        ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ,colors = ButtonDefaults.buttonColors(containerColor = Color.White)) { Text("Usar", color = Color.Black)}
                }
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp).clickable { navController.navigate("red_neuronal") },
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.flores)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Column(modifier = Modifier.weight(3f).fillMaxSize()) {
                Text(
                    "Redes Neuronales",
                    fontSize = 18.sp,
                    fontWeight = FontWeight
                        .ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(top = 10.dp)
                )
                Row(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                    ,horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick ={navController.navigate("grafico_red")},modifier = Modifier
                        ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ,colors = ButtonDefaults.buttonColors(containerColor = Color.White)) { Text("Gráficas", color = Color.Black)}
                    Button(onClick ={navController.navigate("red_neuronal")},modifier = Modifier
                        ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ,colors = ButtonDefaults.buttonColors(containerColor = Color.White)) { Text("Usar", color = Color.Black)}
                }
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.pegato)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Column(modifier = Modifier.weight(3f).fillMaxSize()) {
                Text(
                    "MobileNetV2",
                    fontSize = 18.sp,
                    fontWeight = FontWeight
                        .ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(top = 10.dp)
                )
                Row(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                    ,horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick ={navController.navigate("grafico_ci")},modifier = Modifier
                        ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ,colors = ButtonDefaults.buttonColors(containerColor = Color.White))
                    { Text("Gráficas", color = Color.Black)}
                    Button(onClick ={navController.navigate("clas_im")},modifier = Modifier
                        ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ,colors = ButtonDefaults.buttonColors(containerColor = Color.White)) { Text("Usar", color = Color.Black)}
                }
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.noticia)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Column(modifier = Modifier.weight(3f).fillMaxSize()) {
                Text(
                    "P. L. N. ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight
                        .ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(top = 10.dp)
                )
                Row(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                    ,horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick ={navController.navigate("grafico_pln")},modifier = Modifier
                        ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ,colors = ButtonDefaults.buttonColors(containerColor = Color.White))
                    { Text("Gráficas", color = Color.Black)}
                    Button(onClick ={navController.navigate("natural_leng")},modifier = Modifier
                        ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ,colors = ButtonDefaults.buttonColors(containerColor = Color.White)) { Text("Usar", color = Color.Black)}
                }
            }
        }
    }
}

@Composable
@Preview
fun previewMenu (){
    val navController = rememberNavController()
    menu(navController)
}