package com.ticketchef.app.ocr

import com.ticketchef.app.data.IngredientDictionary

/**
 * Turns raw OCR text from a supermarket receipt into a set of canonical
 * ingredient ids. Receipt lines are noisy ("2 PATATA 1KG 1,35"), so we
 * strip prices/quantities/codes before matching against the dictionary.
 */
object IngredientParser {

    private val noiseLine = Regex(
        "^(total|subtotal|iva|cambio|efectivo|tarjeta|gracias|factura|ticket|caja|" +
            "cif|fecha|hora|operacion|nif|www\\.|tel[eé]fono|direcci[oó]n)",
        RegexOption.IGNORE_CASE
    )

    fun parse(rawText: String): List<String> {
        val found = LinkedHashSet<String>()
        rawText.lineSequence()
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .filter { !noiseLine.containsMatchIn(it) }
            .forEach { line ->
                IngredientDictionary.matchLine(line)?.let { found.add(it) }
            }
        return found.toList()
    }
}
