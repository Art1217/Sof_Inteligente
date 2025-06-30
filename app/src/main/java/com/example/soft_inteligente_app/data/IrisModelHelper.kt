package com.example.soft_inteligente_app.data

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class IrisModelHelper(context: Context) {
    private val interpreter: Interpreter

        init {
            interpreter = Interpreter(loadModelFile(context, "iris_model.tflite"))
        }

        // Cargar el modelo desde assets
        private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
            val fileDescriptor = context.assets.openFd(modelName)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        }


        fun predecir(inputData: FloatArray): String {
            val input = arrayOf(inputData)
            val output = Array(1) { FloatArray(3) }

            interpreter.run(input, output)

            val clases = listOf("Setosa", "Versicolor", "Virginica")
            val predictionIndex = output[0].withIndex().maxByOrNull { it.value }?.index ?: -1

            return if (predictionIndex != -1) {
                clases[predictionIndex]
            } else {
                "Desconocido"
            }
        }


        fun predecirConProbabilidades(inputData: FloatArray): Pair<String, List<Float>> {
            val input = arrayOf(inputData)
            val output = Array(1) { FloatArray(3) }

            interpreter.run(input, output)

            val clases = listOf("Setosa", "Versicolor", "Virginica")
            val predictionIndex = output[0].withIndex().maxByOrNull { it.value }?.index ?: -1
            val probabilidades = output[0].toList()

            val clase = if (predictionIndex != -1) clases[predictionIndex] else "Desconocido"
            return clase to probabilidades
        }
    }

