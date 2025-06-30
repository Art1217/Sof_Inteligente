package com.example.soft_inteligente_app.data.TextClassifier

import android.content.Context

class VocabLoader(context: Context) {
    val vocab: Map<String, Int> = context.assets.open("vocab.txt").bufferedReader().useLines { lines ->
        lines.mapIndexed { index, word -> word.trim() to index }.toMap()
    }
}