package com.example.soft_inteligente_app.data


import kotlin.random.Random

object GeneticAlgorithm {

    fun calcularDistanciaTotal(ruta: List<Int>, distancias: Array<DoubleArray>): Double {
        var suma = 0.0
        for (i in 0 until ruta.size - 1) {
            suma += distancias[ruta[i]][ruta[i + 1]]
        }
        suma += distancias[ruta.last()][ruta.first()]
        return suma
    }

    fun resolver(distancias: Array<DoubleArray>, generaciones: Int = 1000, tamPoblacion: Int = 100): Pair<List<Int>, Double> {
        val numPuntos = distancias.size
        var poblacion = List(tamPoblacion) {
            (0 until numPuntos).shuffled()
        }

        fun fitness(ruta: List<Int>) = calcularDistanciaTotal(ruta, distancias)

        repeat(generaciones) {
            poblacion = poblacion.sortedBy { fitness(it) }
            val nuevaPoblacion = poblacion.take(20).toMutableList()

            while (nuevaPoblacion.size < tamPoblacion) {
                val (p1, p2) = poblacion.shuffled().take(2)
                val corte = Random.nextInt(1, numPuntos - 1)
                val hijo = p1.take(corte).toMutableList()
                hijo.addAll(p2.filter { it !in hijo })
                nuevaPoblacion.add(hijo)
            }

            poblacion = nuevaPoblacion
        }

        val mejor = poblacion.minByOrNull { fitness(it) } ?: emptyList()
        return Pair(mejor, fitness(mejor))
    }
}
