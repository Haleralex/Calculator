package com.example.calculator.expressions

import java.util.*
import kotlin.math.sqrt

class ExpressionCreator {
    private val expressionConverter = ExpressionConverter()

    fun Calculate(expression: String): Double {
        val rpnArray = expressionConverter.Convert(expression)
        val stack = Stack<Double>()

        for (element in rpnArray) {


            when (element) {
                "+" -> {
                    val res = this.getElementValue(stack)
                    stack.push(res!!.first + res.second)
                }
                "-" -> {
                    val res = this.getElementValue(stack)
                    stack.push(res!!.second - res.first)
                }
                "*" -> {
                    val res = this.getElementValue(stack)
                    stack.push(res!!.first * res.second)
                }
                "/" -> {
                    val res = this.getElementValue(stack)
                    stack.push(res!!.second / res.first)
                }
                "√" ->{
                    val res = this.getElementValueSolo(stack)
                    stack.push(sqrt(res!!))
                }
                else -> {
                    stack.push(this.getDoubleValue(element))
                }
            }
        }

        if (stack.size > 1) {
            throw Exception("Invalid expression: '$expression'")
        }
        return stack.pop()
    }

    private fun getDoubleValue(element: String): Double {
        val value = doubleOrString(element)

        return if (value is Number) {
            value as Double
        } else {
            0.0//this.valueProvider?.getValue(value as String)!!
        }
    }

    private fun getElementValue(stack: Stack<Double>): Pair<Double, Double>? {
        if (stack.isEmpty())
            return null
        val first: Double?
        val second: Double?

        var value = doubleOrString(stack.pop())
        first = if (value is Number) {
            value as Double
        } else {
            0.0//this.valueProvider?.getValue(value as String)
        }

        if (stack.isEmpty())
            return Pair(first!!, 0.0)

        value = doubleOrString(stack.pop())
        second = if (value is Number) {
            value as Double
        } else {
            0.0//this.valueProvider?.getValue(value as String)
        }

        return Pair(first!!, second!!)
    }
    private fun getElementValueSolo(stack: Stack<Double>): Double? {
        if (stack.isEmpty())
            return null
        val first: Double?

        var value = doubleOrString(stack.pop())
        first = if (value is Number) {
            value as Double
        } else {
            0.0//this.valueProvider?.getValue(value as String)
        }

        return first
    }

    private fun doubleOrString(element: Any) = try {
        element.toString().toDouble()
    } catch (e: NumberFormatException) {
        element
    }
}