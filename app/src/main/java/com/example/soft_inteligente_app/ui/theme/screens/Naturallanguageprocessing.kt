package com.example.soft_inteligente_app.ui.theme.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soft_inteligente_app.data.TextClassifier.TextClassifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextClassifierScreen(navController: NavHostController) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val classifier = remember { TextClassifier(context) }

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
                .fillMaxSize().background(gradientBrush).padding(paddingValues)
            ,verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Clasificador de Noticias",color= Color.White,fontSize = 30.sp, textAlign = TextAlign.Center
                , fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Ingresa una noticia en inglés") },
                textStyle = TextStyle(color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp)
                    .clip(RoundedCornerShape(16.dp))

            )

            Spacer(modifier = Modifier.height(16.dp))
            val coroutineScope = rememberCoroutineScope()
            Button(onClick = {
                result = "Clasificando..."
                coroutineScope.launch {
                    val prediction = classifier.classify(inputText)
                    result = "Categoría: $prediction"
                }
            } ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4393C5))) {
                Text("Clasificar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("$result", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
@Preview
fun PreviewTextC() {
    val navController = rememberNavController()
    TextClassifierScreen(navController)
}