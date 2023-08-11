package com.example.androiddemo.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp

/**
 * 单个可选框
 */
@Composable
fun SingleCheckBox() {
    val checkStatus = remember { mutableStateOf(true) }
    Checkbox(
        checked = checkStatus.value,
        onCheckedChange = { checkStatus.value = it },
        colors = CheckboxDefaults.colors(checkedColor = Color.Blue)
    )
}

@Composable
fun TriStateCheckBox() {

    val (state, onStateChange) = remember { mutableStateOf(true) }
    val (state2, onStateChange2) = remember { mutableStateOf(true) }

    val parentState = remember(state, state2) {
        if (state && state2) ToggleableState.On else if (!state && !state2) ToggleableState.Off else ToggleableState.Indeterminate
    }

    val onParentClick = {
        val s = parentState != ToggleableState.On
        onStateChange(s)
        onStateChange2(s)
    }
    Column {
        TriStateCheckbox(
            state = parentState,
            onClick = onParentClick,
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
        )
        Column(Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)) {
            Checkbox(state, onStateChange)
            Checkbox(state2, onStateChange2)
        }

    }
}
