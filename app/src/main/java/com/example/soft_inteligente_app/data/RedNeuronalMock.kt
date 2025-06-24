package com.example.soft_inteligente_app.data

import kotlin.math.exp
import kotlin.random.Random

object RedNeuronalMock {
    data class Registro(val tiempoLibre: Double, val espacio: Double, val alergia: Int, val mascota: String)

    private val clases = listOf("perro", "gato", "pez")
    private lateinit var pesos1: Array<DoubleArray>
    private lateinit var bias1: DoubleArray
    private lateinit var pesos2: Array<DoubleArray>
    private lateinit var bias2: DoubleArray

    fun generarDatos(n: Int = 300): List<Registro> {
        val datos = mutableListOf<Registro>()

        repeat(n / 3) {
            val tiempo = Random.nextDouble(2.0, 6.0)
            val espacio = Random.nextDouble(40.0, 100.0)
            datos.add(Registro(tiempo, espacio, 0, "perro"))
        }

        repeat(n / 3) {
            val tiempo = Random.nextDouble(1.0, 3.0)
            val espacio = Random.nextDouble(20.0, 60.0)
            datos.add(Registro(tiempo, espacio, 0, "gato"))
        }

        repeat(n / 3) {
            val tiempo = Random.nextDouble(0.0, 2.0)
            val espacio = Random.nextDouble(10.0, 30.0)
            val alergia = Random.nextInt(0, 2)
            datos.add(Registro(tiempo, espacio, alergia, "pez"))
        }

        return datos.shuffled()
    }

    fun entrenar(datos: List<Registro>, epocas: Int = 500, tasaAprendizaje: Double = 0.01) {
        val inputSize = 3
        val hiddenSize = 5
        val outputSize = 3

        pesos1 = Array(hiddenSize) { DoubleArray(inputSize) { Random.nextDouble(-1.0, 1.0) } }
        bias1 = DoubleArray(hiddenSize) { Random.nextDouble(-1.0, 1.0) }
        pesos2 = Array(outputSize) { DoubleArray(hiddenSize) { Random.nextDouble(-1.0, 1.0) } }
        bias2 = DoubleArray(outputSize) { Random.nextDouble(-1.0, 1.0) }

        repeat(epocas) {
            datos.shuffled().forEach { r ->
                val x = listOf(r.tiempoLibre, r.espacio, r.alergia.toDouble())
                val y = clases.map { if (it == r.mascota) 1.0 else 0.0 }

                val z1 = DoubleArray(hiddenSize) { i -> x.indices.sumOf { j -> pesos1[i][j] * x[j] } + bias1[i] }
                val a1 = z1.map { relu(it) }

                val z2 = DoubleArray(outputSize) { i -> a1.indices.sumOf { j -> pesos2[i][j] * a1[j] } + bias2[i] }
                val a2 = softmax(z2)

                val error2 = a2.mapIndexed { i, o -> o - y[i] }
                val error1 = DoubleArray(hiddenSize) { i ->
                    val sum = error2.indices.sumOf { j -> error2[j] * pesos2[j][i] }
                    if (z1[i] > 0) sum else 0.0
                }

                for (i in 0 until outputSize) {
                    for (j in 0 until hiddenSize) {
                        pesos2[i][j] -= tasaAprendizaje * error2[i] * a1[j]
                    }
                    bias2[i] -= tasaAprendizaje * error2[i]
                }

                for (i in 0 until hiddenSize) {
                    for (j in 0 until inputSize) {
                        pesos1[i][j] -= tasaAprendizaje * error1[i] * x[j]
                    }
                    bias1[i] -= tasaAprendizaje * error1[i]
                }
            }
        }
    }

    private fun relu(x: Double) = if (x > 0) x else 0.0

    private fun softmax(z: DoubleArray): List<Double> {
        val expZ = z.map { exp(it) }
        val sumExp = expZ.sum()
        return expZ.map { it / sumExp }
    }

    fun predecir(tiempo: Double, espacio: Double, alergia: Int): String {
        val x = listOf(tiempo, espacio, alergia.toDouble())
        val z1 = DoubleArray(pesos1.size) { i -> x.indices.sumOf { j -> pesos1[i][j] * x[j] } + bias1[i] }
        val a1 = z1.map { relu(it) }
        val z2 = DoubleArray(pesos2.size) { i -> a1.indices.sumOf { j -> pesos2[i][j] * a1[j] } + bias2[i] }
        val a2 = softmax(z2)
        return clases[a2.withIndex().maxByOrNull { it.value }?.index ?: 2]
    }

    fun calcularMetricas(datos: List<Registro>): Map<String, Double> {
        entrenar(datos)
        val predicciones = datos.map { it to predecir(it.tiempoLibre, it.espacio, it.alergia) }
        val aciertos = predicciones.count { it.first.mascota == it.second }
        val accuracy = aciertos.toDouble() / datos.size
        return mapOf("Accuracy" to accuracy)
    }
}
