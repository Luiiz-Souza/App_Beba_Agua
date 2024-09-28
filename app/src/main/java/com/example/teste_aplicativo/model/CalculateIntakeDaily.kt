package com.example.teste_aplicativo.model

class CalculateIntakeDaily {

    private val mlYoung = 40.0
    private val mlAdult = 35.0
    private val mlOld = 30.0
    private val mlBiggestThan66 = 25.0
    private var resultMl = 0.0
    private var resultTotalMl = 0.0

    fun calculateTotalMl(weight: Double, age: Int) {

        if (age <= 17) {
            resultMl = weight * mlYoung
            resultTotalMl = resultMl
        } else if (age <= 55) {
            resultMl = weight * mlAdult
            resultTotalMl = resultMl
        } else if (age <= 65) {
            resultMl = weight * mlOld
            resultTotalMl = resultMl
        } else {
            resultMl = weight * mlBiggestThan66
            resultTotalMl = resultMl
        }
    }

    fun resultTotalMl(): Double{
        return resultTotalMl
    }
}