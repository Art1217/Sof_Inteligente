package com.example.soft_inteligente_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.soft_inteligente_app.R

@Composable
fun menu(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("MENÚ",fontSize = 30.sp, fontWeight = FontWeight
            .ExtraBold, color = Color.Black)
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp).clickable { navController.navigate("alg_gene") },
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.genetico)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Text("Algoritmo genético", fontSize = 18.sp, fontWeight = FontWeight
                .ExtraBold, color = Color.Black, textAlign = TextAlign.Center
                , modifier = Modifier.weight(3f).fillMaxWidth().padding(20.dp) )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp).clickable { navController.navigate("naive_bayes") },
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.naive)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Text("Naive Bayes", fontSize = 18.sp, fontWeight = FontWeight
                .ExtraBold, color = Color.Black, textAlign = TextAlign.Center
                , modifier = Modifier.weight(3f).fillMaxWidth().padding(20.dp) )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp).clickable { navController.navigate("red_neuronal") },
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.redes)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Text("Redes Neuronales", fontSize = 18.sp, fontWeight = FontWeight
                .ExtraBold, color = Color.Black, textAlign = TextAlign.Center
                , modifier = Modifier.weight(3f).fillMaxWidth().padding(20.dp) )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp).clickable { navController.navigate("clas_im") },
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.pega2)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Text("Clasificación de imágenes", fontSize = 18.sp, fontWeight = FontWeight
                .ExtraBold, color = Color.Black, textAlign = TextAlign.Center
                , modifier = Modifier.weight(3f).fillMaxWidth().padding(20.dp) )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.weight(1f).fillMaxWidth() .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFB3E5FC))
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id= R.drawable.gene)
            Image(
                painter = image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Text("Procesamiento de lenguaje natural", fontSize = 18.sp, fontWeight = FontWeight
                .ExtraBold, color = Color.Black, textAlign = TextAlign.Center
                , modifier = Modifier.weight(3f).fillMaxWidth().padding(20.dp) )
        }

    }
}

@Composable
@Preview
fun previewMenu (){

}