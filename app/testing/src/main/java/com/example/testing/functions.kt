package com.example.testing

import kotlin.system.exitProcess

//abstract class UnitConverter<T>{
//    abstract fun convert(baseValue: Double, targetUnit: T): Double
//}

enum class LengthUnit(val factorToMeter: Double, val displayUnit: String) {
    MCM(1e-6,"mcm"),
    MM(1e-3,"mm"),
    CM(1e-2, "cm"),
    IN(0.0254,"in"),
    DM(0.1,"dm"),
    FT(0.3048,"ft"),
    YD(0.9144,"yd"),
    M(1.0,"m"),
    KM(1e3,"km"),
    MILE(1609.34,"mi");

    fun convert(baseValue: Double, targetUnit: LengthUnit): Double {
        return baseValue * factorToMeter / targetUnit.factorToMeter
    }
}

enum class WeightUnit(val factorToKg: Double) {
    MCG(1e-6),
    MG(1e-3),
    G(1.0),
    OZ(0.0283495),
    LBS(0.453592),
    KG(1.0),
    MT(1000.0);

    fun convert(baseValue: Double, targetUnit: WeightUnit): Double {
        return baseValue * factorToKg / targetUnit.factorToKg
    }
}

enum class TemperatureUnit {
    CELSIUS, KELVIN, FAHRENHEIT;

    fun convert(baseValue: Double, targetUnit: TemperatureUnit): Double {
        return when (this) {
            CELSIUS -> when (targetUnit) {
                CELSIUS -> baseValue
                KELVIN -> baseValue + 273.15
                FAHRENHEIT -> baseValue * 9.0 / 5.0 + 32
            }
            KELVIN -> when (targetUnit) {
                CELSIUS -> baseValue - 273.15
                KELVIN -> baseValue
                FAHRENHEIT -> (baseValue - 273.15) * 9.0 / 5.0 + 32
            }
            FAHRENHEIT -> when (targetUnit) {
                CELSIUS -> (baseValue - 32) * 5.0 / 9.0
                KELVIN -> (baseValue - 32) * 5.0 / 9.0 + 273.15
                FAHRENHEIT -> baseValue
            }
        }
    }
}

sealed class UnitCategory {
    data class Length(val unit: LengthUnit) : UnitCategory()
    data class Weight(val unit: WeightUnit) : UnitCategory()
    data class Temperature(val unit: TemperatureUnit) : UnitCategory()
}

class ConvertedUnit(val baseValue: Double, val baseUnit: UnitCategory, val targetUnit: UnitCategory) {
    init {
        // Ensure the base and target units belong to the same category
        if (baseUnit::class != targetUnit::class) {
            throw IllegalArgumentException("The units must belong to the same category.")
        }
    }

    val targetValue: Double = when (baseUnit) {
        is UnitCategory.Length -> {
            (baseUnit.unit as LengthUnit).convert(baseValue, (targetUnit as UnitCategory.Length).unit)
        }
        is UnitCategory.Weight -> {
            (baseUnit.unit as WeightUnit).convert(baseValue, (targetUnit as UnitCategory.Weight).unit)
        }
        is UnitCategory.Temperature -> {
            (baseUnit.unit as TemperatureUnit).convert(baseValue, (targetUnit as UnitCategory.Temperature).unit)
        }
    }
}

fun printValidUnits() {
    println("Valid Length Units: ${LengthUnit.entries.joinToString { it.name }}")
    println("Valid Weight Units: ${WeightUnit.entries.joinToString { it.name }}")
    println("Valid Temperature Units: ${TemperatureUnit.entries.joinToString { it.name }}")
}

fun main(){
    printValidUnits()
    val convertedLength = ConvertedUnit(
        baseValue = 6.7,
        baseUnit = UnitCategory.Length(LengthUnit.M),
        targetUnit = UnitCategory.Length(LengthUnit.YD))
    println("${convertedLength.baseValue} ${convertedLength.baseUnit} is ${convertedLength.targetValue} ${convertedLength.targetUnit}")
}



//val VALID_LENGTH_UNITS: List<String> = listOf(
//    "mcm",
//    "mm",
//    "cm",
//    "in",
//    "dm",
//    "ft",
//    "yd",
//    "m",
//    "km",
//    "mile",
//)

//val VALID_WEIGHT_UNITS = listOf(
//    "mcg",
//    "mg",
//    "g",
//    "oz",
//    "lbs",
//    "kg",
//    "mt",
//)


//val VALID_TEMPERATURE_UNITS = listOf(
//    "°C",
//    "K",
//    "°F",
//)


//fun convertTemperatures(baseValue: Double, baseUnit: String, targetUnit: String): Double{
//    val targetValue:Double =  when (baseUnit){
//        //in VALID_TEMPERATURE_UNITS.subList(0,4) ->
//        VALID_TEMPERATURE_UNITS[0] ->
//            when(targetUnit){
//                //in VALID_TEMPERATURE_UNITS.subList(4,6) -> 273.15 + baseValue
//                VALID_TEMPERATURE_UNITS[1] -> 273.15 + baseValue
//                //in VALID_TEMPERATURE_UNITS.subList(6, VALID_TEMPERATURE_UNITS.size) -> 32.0 + 9.0/5.0 * baseValue
//                VALID_TEMPERATURE_UNITS[2] -> 32.0 + 9.0/5.0 * baseValue
//                else -> baseValue
//            }
//        //in VALID_TEMPERATURE_UNITS.subList(4,6) ->
//        VALID_TEMPERATURE_UNITS[1] ->
//            when(targetUnit){
//                //in VALID_TEMPERATURE_UNITS.subList(0,4) ->
//                VALID_TEMPERATURE_UNITS[0] ->
//                    -273.15 + baseValue
//                //in VALID_TEMPERATURE_UNITS.subList(6,VALID_TEMPERATURE_UNITS.size) ->
//                VALID_TEMPERATURE_UNITS[2] ->
//                    32 + 9.0/5.0 * (baseValue - 273.15)
//                else -> baseValue
//            }
//        else ->
//            when(targetUnit){
//                //in VALID_TEMPERATURE_UNITS.subList(0,4)
//                VALID_TEMPERATURE_UNITS[0]
//                    -> (baseValue - 32) * 5.0/9.0
//                //in VALID_TEMPERATURE_UNITS.subList(4,6)
//                VALID_TEMPERATURE_UNITS[1]
//                    -> (baseValue - 32) * 5.0/9.0 + 273.15
//                else -> baseValue
//            }
//    }
//    return targetValue
//}


//class ConversionUnit(val baseValue: Double, val baseUnit: String, val targetUnit: String){
//    init {
//        if ((baseUnit in VALID_LENGTH_UNITS && targetUnit !in VALID_LENGTH_UNITS)
//            || (baseUnit in VALID_WEIGHT_UNITS && targetUnit !in VALID_WEIGHT_UNITS)
//            || (baseUnit in VALID_TEMPERATURE_UNITS && targetUnit !in VALID_TEMPERATURE_UNITS)
//        ){
//            println("The units have to be from the same category.")
//            printValidUnits()
//            exitProcess(6)
//        }
//    }
//    val targetValue: Double = when (baseUnit){
//        in VALID_LENGTH_UNITS -> baseValue*CONVERSION_FACTORS_FROM_METER_TO_LENGTH[VALID_LENGTH_UNITS.indexOf(baseUnit)] /
//                                                CONVERSION_FACTORS_FROM_METER_TO_LENGTH[VALID_LENGTH_UNITS.indexOf(targetUnit)]
//
//        in VALID_WEIGHT_UNITS -> baseValue*CONVERSION_FACTORS_FROM_KG_TO_WEIGHT[VALID_WEIGHT_UNITS.indexOf(baseUnit)] /
//                                                CONVERSION_FACTORS_FROM_KG_TO_WEIGHT[VALID_WEIGHT_UNITS.indexOf(targetUnit)]
//
//        in VALID_TEMPERATURE_UNITS -> convertTemperatures(baseValue=baseValue,baseUnit=baseUnit,targetUnit=targetUnit)
//        else -> 0.0
//    }
//}
//
//fun printValidUnits(){
//    val unitList = listOf(VALID_LENGTH_UNITS,VALID_WEIGHT_UNITS,VALID_TEMPERATURE_UNITS)
//    println("Valid units are:")
//    for (idx in unitList.indices){
//        println(listOf("Length","Weight","Temperature")[idx])
//        println(unitList[idx])
//        println("")
//    }
//}

