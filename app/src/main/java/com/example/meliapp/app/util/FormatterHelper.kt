package com.example.meliapp.app.util

import com.example.meliapp.R
import java.text.NumberFormat

object FormatterHelper {
    fun resolvePriceText(price: Double): String {
        val format = NumberFormat.getCurrencyInstance()
        return format.format(price)
    }

    fun resolveShipmentText(freeshipping: Boolean): String {
        return if (freeshipping) "Envio Gratis" else ""
    }

    fun resolveConditionText(condition: String): String {
        when (condition) {
            "new" -> return "Nuevo"
            "used" -> return "Usado"
            else -> return "No especificado"
        }
    }
}