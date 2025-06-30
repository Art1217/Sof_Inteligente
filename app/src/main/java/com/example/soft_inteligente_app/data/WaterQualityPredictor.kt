package com.example.soft_inteligente_app.data

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import kotlin.math.roundToInt


import java.io.FileInputStream

class WaterQualityPredictor(context: Context) {
    private val interpreter: Interpreter


    private val mean = floatArrayOf(
        7.08599f, 195.968072f, 21917.441374f, 7.134338f, 333.224672f,
        426.526409f, 14.357709f, 66.400859f, 3.969729f
    )

    private val scale = floatArrayOf(
        1.572945f, 32.626969f, 8640.090806f, 1.584426f, 41.194926f,
        80.692502f, 3.324132f, 16.073112f, 0.780152f
    )

    init {
        val assetFileDescriptor = context.assets.openFd("water_quality_model.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength

        val model: MappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        interpreter = Interpreter(model)
    }

    fun predictProbabilidad(
        ph: Float, turbidity: Float, sulfate: Float, conductivity: Float,
        solids: Float, chloramines: Float, hardness: Float, organic: Float, trihalomethanes: Float
    ): Float {
        val input = ByteBuffer.allocateDirect(4 * 9).order(ByteOrder.nativeOrder())
        val rawValues = floatArrayOf(
            ph, hardness, solids, chloramines, sulfate,
            conductivity, organic, trihalomethanes, turbidity
        )

        for (i in rawValues.indices) {
            val scaled = (rawValues[i] - mean[i]) / scale[i]
            input.putFloat(scaled)
        }

        val output = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder())
        interpreter.run(input, output)
        output.rewind()
        return output.float // valor entre 0.0 y 1.0
    }
}

