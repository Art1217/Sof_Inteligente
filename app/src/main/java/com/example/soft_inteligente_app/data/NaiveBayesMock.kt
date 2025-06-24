package com.example.soft_inteligente_app.data

import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

object NaiveBayesMock {
    data class Registro(val ph: Double, val temperatura: Double, val turbidez: Int, val calidad: String)

    private lateinit var medias: Map<String, DoubleArray>
    private lateinit var desvios: Map<String, DoubleArray>
    private lateinit var priors: Map<String, Double>

    fun generarDatos(n: Int = 300): List<Registro> {
        return List(n) {
            val ph = (5.5 + Random.nextDouble() * 3.0)
            val temp = (15.0 + Random.nextDouble() * 15.0)
            val turb = Random.nextInt(1, 6)
            val calidad = if (ph in 6.5..7.5 && temp in 20.0..25.0 && turb <= 3) "buena" else "mala"
            Registro(ph, temp, turb, calidad)
        }
    }

    fun entrenar(datos: List<Registro>) {
        val porClase = datos.groupBy { it.calidad }
        val atributos = listOf<(Registro) -> Double>(
            { it.ph }, { it.temperatura }, { it.turbidez.toDouble() }
        )

        medias = porClase.mapValues { (_, registros) ->
            atributos.map { attr -> registros.map(attr).average() }.toDoubleArray()
        }

        desvios = porClase.mapValues { (_, registros) ->
            atributos.mapIndexed { i, attr ->
                val media = medias.getValue(registros.first().calidad)[i]
                sqrt(registros.map { (attr(it) - media).pow(2) }.average())
            }.toDoubleArray()
        }

        priors = porClase.mapValues { (_, registros) -> registros.size.toDouble() / datos.size }
    }

    private fun gaussian(x: Double, media: Double, std: Double): Double {
        if (std == 0.0) return 1.0
        val exponent = -((x - media).pow(2)) / (2 * std.pow(2))
        return (1 / (sqrt(2 * Math.PI) * std)) * exp(exponent)
    }

    fun predecir(ph: Double, temperatura: Double, turbidez: Int): String {
        val input = listOf(ph, temperatura, turbidez.toDouble())
        return priors.keys.maxByOrNull { clase ->
            val probPrior = ln(priors[clase] ?: 1e-6)
            val probCondicional = input.mapIndexed { i, x ->
                ln(gaussian(x, medias[clase]!![i], desvios[clase]!![i] + 1e-6))
            }.sum()
            probPrior + probCondicional
        } ?: "desconocido"
    }

    fun calcularMetricas(datos: List<Registro>): Map<String, Double> {
        entrenar(datos)
        val predicciones = datos.map { it to predecir(it.ph, it.temperatura, it.turbidez) }

        val tp = predicciones.count { it.first.calidad == "buena" && it.second == "buena" }
        val tn = predicciones.count { it.first.calidad == "mala" && it.second == "mala" }
        val fp = predicciones.count { it.first.calidad == "mala" && it.second == "buena" }
        val fn = predicciones.count { it.first.calidad == "buena" && it.second == "mala" }

        val precision = tp.toDouble() / (tp + fp).coerceAtLeast(1)
        val recall = tp.toDouble() / (tp + fn).coerceAtLeast(1)
        val f1 = 2 * precision * recall / (precision + recall).coerceAtLeast(1e-6)
        val accuracy = (tp + tn).toDouble() / datos.size

        return mapOf("Precision" to precision, "Recall" to recall, "F1-Score" to f1, "Accuracy" to accuracy)
    }
}