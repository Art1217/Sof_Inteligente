package com.example.soft_inteligente_app.data

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class ImageClassifier(context: Context) {
    private val interpreter: Interpreter

    init {
        val model = loadModelFile(context, "modelo_mascotas.tflite")
        interpreter = Interpreter(model)
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }

    fun classify(image: Bitmap): String {
        val resized = Bitmap.createScaledBitmap(image, 128, 128, true)
        val argbBitmap = resized.copy(Bitmap.Config.ARGB_8888, true)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(argbBitmap)

        val inputBuffer = tensorImage.tensorBuffer

        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 1), DataType.FLOAT32)
        interpreter.run(inputBuffer.buffer, outputBuffer.buffer.rewind())

        val prediction = outputBuffer.floatArray[0]
        return if (prediction > 0.5f) "GATO" else "PERRO"
    }


    fun close() {
        interpreter.close()
    }
}

