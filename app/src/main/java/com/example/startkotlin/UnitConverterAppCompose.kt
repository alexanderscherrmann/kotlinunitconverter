package com.example.startkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.startkotlin.*
import kotlin.reflect.typeOf

//import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterApp()
        }
    }
}

@Composable
fun UnitConverterApp() {
    var valueInput by remember { mutableStateOf(TextFieldValue()) }
    var selectedCategory by remember { mutableStateOf("Length") }
    var baseUnit by remember { mutableStateOf("m") }
    var targetUnit by remember { mutableStateOf("yd") }
    var result by remember { mutableStateOf(0.0) }

    val units = when (selectedCategory) {
        "Length" -> LengthUnit.entries.map { it.displayUnit }
        "Weight" -> WeightUnit.entries.map { it.displayUnit }
        "Temperature" -> TemperatureUnit.entries.map { it.displayUnit }
        else -> emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter", style = MaterialTheme.typography.h5)

        OutlinedTextField(
            value = valueInput,
            onValueChange = { valueInput = it },
            label = { Text("Value") },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownSelector(
            label = "Category",
            options = listOf("Length", "Weight", "Temperature"),
            selectedOption = selectedCategory,
            onOptionSelected = {
                selectedCategory = it
                baseUnit = units.first()
                targetUnit = units.first()
            }
        )
        DropdownSelector(
            label = "Base Unit",
            options = units,
            selectedOption = baseUnit,
            onOptionSelected = { baseUnit = it }
        )
        DropdownSelector(
            label = "Target Unit",
            options = units,
            selectedOption = targetUnit,
            onOptionSelected = { targetUnit = it }
        )
        Spacer(modifier = Modifier.width(70.dp))
        Text(text = "Result: ${String.format("%.2f",result)}", style = MaterialTheme.typography.body1)

        Button(onClick = {
            val value = valueInput.text.toDoubleOrNull()
            if (value != null) {
                try {
                    result = when (selectedCategory) {
                        "Length" -> {
                            val base = UnitCategory.Length(LengthUnit.entries[(LengthUnit.entries.map{it.displayUnit == baseUnit}).indexOf(true)])
                            val target = UnitCategory.Length(LengthUnit.entries[(LengthUnit.entries.map{it.displayUnit == targetUnit}).indexOf(true)])
                            ConvertedUnit(value, base, target).targetValue
                        }
                        "Weight" -> {
                            val base = UnitCategory.Weight(WeightUnit.entries[(WeightUnit.entries.map{it.displayUnit == baseUnit}).indexOf(true)])
                            val target = UnitCategory.Weight(WeightUnit.entries[(WeightUnit.entries.map{it.displayUnit == targetUnit}).indexOf(true)])
                            ConvertedUnit(value, base, target).targetValue
                        }
                        "Temperature" -> {
                            val base = UnitCategory.Temperature(TemperatureUnit.entries[(TemperatureUnit.entries.map{it.displayUnit == baseUnit}).indexOf(true)])
                            val target = UnitCategory.Temperature(TemperatureUnit.entries[(TemperatureUnit.entries.map{it.displayUnit == targetUnit}).indexOf(true)])
                            ConvertedUnit(value, base, target).targetValue
                        }
                        else -> 0.0
                    }
                }
                catch (e: IllegalArgumentException) {
                    result = 0.0
                }
            }
            else {
                result = 0.0
            }
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert")
        }
    }
}

@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label, style = MaterialTheme.typography.caption)
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedOption)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }) {
                        Text(option)
                    }
                }
            }
        }
    }
}
