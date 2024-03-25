package com.example.medicalalarm.android.Frontend

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeAlert(showTimePicker: MutableState<Boolean>, timePicker: TimePickerState) {

    AlertDialog(
        onDismissRequest = { showTimePicker.value = false },
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(10.dp)){
       TimePicker(state = timePicker)
    }


}