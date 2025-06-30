package com.example.soft_inteligente_app.data.TextClassifier

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class TextClassifier(context: Context) {
    private val interpreter: Interpreter
    private val vocab = VocabLoader(context).vocab
    private val labels = listOf("World", "Sports", "Business", "Sci/Tech")

    init {
        try {
        val assetFileDescriptor = context.assets.openFd("text_classifier.tflite")
        val fileInputStream = assetFileDescriptor.createInputStream()
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

        interpreter = Interpreter(modelBuffer)
        } catch (e: Exception) {
            Log.e("TextClassifier", "Error al cargar modelo: ${e.message}")
            throw e
        }
    }

    private fun vectorizeText(text: String, maxLen: Int = 200): IntArray {
        val tokens = text.lowercase().split(" ")
        val indices = tokens.map { vocab[it] ?: 1 }
        val padded = IntArray(maxLen) { 0 }
        for (i in 0 until minOf(indices.size, maxLen)) {
            padded[i] = indices[i]
        }
        return padded
    }

    fun classify(text: String): String {
        val input = vectorizeText(text)
        val inputBuffer = ByteBuffer.allocateDirect(4 * 200).order(ByteOrder.nativeOrder())
        input.forEach { inputBuffer.putInt(it) }
        inputBuffer.rewind()

        val output = Array(1) { FloatArray(4) }
        interpreter.run(inputBuffer, output)

        val predictedIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
        return labels[predictedIndex]
    }
}
