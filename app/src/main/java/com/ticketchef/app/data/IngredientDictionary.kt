package com.ticketchef.app.data

import java.text.Normalizer

/**
 * Maps canonical ingredient ids (used by the recipe dataset) to the words
 * that tend to appear on Spanish supermarket receipts for that product.
 * Ticket abbreviations vary a lot by chain, so each entry lists several
 * common variants (singular/plural, abbreviated, brand-agnostic).
 */
object IngredientDictionary {

    val canonicalToKeywords: Map<String, List<String>> = mapOf(
        "patata" to listOf("patata", "patatas", "papa"),
        "cebolla" to listOf("cebolla", "cebollas"),
        "cebolleta" to listOf("cebolleta", "cebolletas", "cebollino"),
        "ajo" to listOf("ajo", "ajos"),
        "tomate" to listOf("tomate", "tomates", "tomate rama", "tomate pera"),
        "tomate frito" to listOf("tomate frito", "tomate triturado"),
        "pimiento verde" to listOf("pimiento verde", "pimientos verdes"),
        "pimiento rojo" to listOf("pimiento rojo", "pimientos rojos"),
        "pimiento" to listOf("pimiento", "pimientos"),
        "aceite de oliva" to listOf("aceite oliva", "aceite de oliva", "aove"),
        "huevo" to listOf("huevo", "huevos", "docena huevos"),
        "arroz" to listOf("arroz", "arroz redondo", "arroz bomba"),
        "pollo" to listOf("pollo", "pechuga pollo", "muslo pollo", "contramuslo", "filete pollo"),
        "carne picada" to listOf("carne picada", "picada mixta", "carne pic"),
        "ternera" to listOf("ternera", "filete ternera", "solomillo ternera"),
        "cerdo" to listOf("cerdo", "lomo cerdo", "solomillo cerdo", "costilla cerdo", "panceta"),
        "chorizo" to listOf("chorizo", "chorizo sarta"),
        "jamon" to listOf("jamon", "jamon york", "jamon serrano", "jamon iberico"),
        "bacon" to listOf("bacon", "beicon"),
        "morcilla" to listOf("morcilla"),
        "atun" to listOf("atun", "atun lata", "bonito lata"),
        "bacalao" to listOf("bacalao"),
        "merluza" to listOf("merluza"),
        "salmon" to listOf("salmon"),
        "gambas" to listOf("gamba", "gambas", "langostino", "langostinos"),
        "calamar" to listOf("calamar", "calamares", "chipiron", "chipirones"),
        "pulpo" to listOf("pulpo"),
        "mejillon" to listOf("mejillon", "mejillones"),
        "sepia" to listOf("sepia"),
        "leche" to listOf("leche", "leche entera", "leche semi"),
        "nata" to listOf("nata", "nata cocinar", "nata montar"),
        "queso" to listOf("queso", "queso lonchas", "queso rallado", "queso curado"),
        "queso parmesano" to listOf("parmesano", "grana padano"),
        "mantequilla" to listOf("mantequilla"),
        "yogur" to listOf("yogur", "yogures"),
        "pan" to listOf("pan", "barra pan", "pan molde", "pan rustico"),
        "pan rallado" to listOf("pan rallado"),
        "harina" to listOf("harina", "harina trigo"),
        "azucar" to listOf("azucar", "azucar blanco"),
        "sal" to listOf("sal"),
        "pimienta" to listOf("pimienta"),
        "perejil" to listOf("perejil"),
        "laurel" to listOf("laurel", "hoja laurel"),
        "vino blanco" to listOf("vino blanco"),
        "vino tinto" to listOf("vino tinto"),
        "limon" to listOf("limon", "limones"),
        "naranja" to listOf("naranja", "naranjas"),
        "manzana" to listOf("manzana", "manzanas"),
        "platano" to listOf("platano", "platanos", "banana"),
        "zanahoria" to listOf("zanahoria", "zanahorias"),
        "calabacin" to listOf("calabacin", "calabacines"),
        "berenjena" to listOf("berenjena", "berenjenas"),
        "espinaca" to listOf("espinaca", "espinacas"),
        "lechuga" to listOf("lechuga"),
        "garbanzos" to listOf("garbanzo", "garbanzos", "garbanzo cocido"),
        "lentejas" to listOf("lenteja", "lentejas"),
        "judias blancas" to listOf("judia blanca", "judias blancas", "alubia", "alubias"),
        "judias verdes" to listOf("judia verde", "judias verdes"),
        "guisantes" to listOf("guisante", "guisantes"),
        "champinon" to listOf("champinon", "champinones", "champi"),
        "seta" to listOf("seta", "setas"),
        "aceituna" to listOf("aceituna", "aceitunas", "oliva"),
        "vinagre" to listOf("vinagre"),
        "pasta" to listOf("pasta", "macarrones", "espagueti", "espaguetis"),
        "fideos" to listOf("fideo", "fideos"),
        "azafran" to listOf("azafran", "colorante"),
        "pimenton" to listOf("pimenton"),
        "comino" to listOf("comino"),
        "canela" to listOf("canela"),
        "chocolate" to listOf("chocolate", "chocolate postres"),
        "miel" to listOf("miel"),
        "almendra" to listOf("almendra", "almendras"),
        "nuez" to listOf("nuez", "nueces"),
        "levadura" to listOf("levadura"),
        "caldo" to listOf("caldo", "caldo pollo", "caldo verduras", "caldo pescado", "avecrem"),
        "puerro" to listOf("puerro", "puerros"),
        "apio" to listOf("apio"),
        "pepino" to listOf("pepino"),
        "aguacate" to listOf("aguacate", "aguacates"),
        "mozzarella" to listOf("mozzarella"),
        "cilantro" to listOf("cilantro"),
        "romero" to listOf("romero"),
        "tomillo" to listOf("tomillo"),
        "oregano" to listOf("oregano"),
        "conejo" to listOf("conejo"),
        "cordero" to listOf("cordero", "chuleta cordero", "paletilla cordero"),
        "sardina" to listOf("sardina", "sardinas"),
        "requeson" to listOf("requeson"),
        "membrillo" to listOf("membrillo"),
        "coco" to listOf("coco", "leche coco"),
        "curry" to listOf("curry"),
        "vino de jerez" to listOf("jerez", "vino jerez", "fino"),
        "almeja" to listOf("almeja", "almejas"),
        "pan de molde" to listOf("pan molde", "pan sandwich")
    )

    private val flatIndex: List<Pair<String, Regex>> by lazy {
        canonicalToKeywords.entries.flatMap { (canonical, keywords) ->
            keywords.map { keyword ->
                canonical to Regex("\\b" + Regex.escape(normalize(keyword)) + "\\w*")
            }
        }.sortedByDescending { it.second.pattern.length }
    }

    fun normalize(text: String): String {
        val noAccents = Normalizer.normalize(text, Normalizer.Form.NFD)
            .replace(Regex("\\p{Mn}+"), "")
        return noAccents.lowercase().replace(Regex("[^a-z0-9 ]"), " ").replace(Regex("\\s+"), " ").trim()
    }

    /** Returns the canonical ingredient id matched inside [line], or null. */
    fun matchLine(line: String): String? {
        val normalized = normalize(line)
        if (normalized.isBlank()) return null
        for ((canonical, regex) in flatIndex) {
            if (regex.containsMatchIn(normalized)) return canonical
        }
        return null
    }

    fun allCanonicalIngredients(): List<String> = canonicalToKeywords.keys.sorted()
}
