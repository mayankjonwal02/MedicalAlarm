package com.example.medicalalarm.android.Frontend

import android.app.LauncherActivity
import android.content.Context
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {

    var screen = remember {
        mutableStateOf(1)
    }

    Crossfade(targetState = screen) {
        it ->
        if(it.value == 1)
        {
            SplashScreen(screen)
        }
        else if (it.value == 2)
        {
            AlarmScreen()
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmScreen() {
    var isDialogOpen = remember { mutableStateOf(false) }
    val dateTime = LocalDateTime.now()
    val timePicker = remember {
        TimePickerState(
            initialHour = dateTime.hour,
            initialMinute = dateTime.minute,
            is24Hour = true
        )
    }
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
        .background(Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.3f))
    ) {
        var context = LocalContext.current
        var alarms = remember { mutableStateOf(

        getItems(context) ?: emptyList<Int>()
        )
        }




        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Schedule", color = Color.Magenta , fontSize = 40.sp , fontWeight = FontWeight.ExtraBold)

            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn {
                if(alarms.value != null){
                    items(alarms.value!!)
                    { item ->
                        ToggleButton(item)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                imageVector = Icons.Filled.AddCircleOutline,
                contentDescription = "",
                tint = Color.Magenta,
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
//                        alarms.value = alarms.value?.plus(alarms.value!!.size)!!
//                        if(alarms.value != null){ setItems(context, alarms.value!!)

                        isDialogOpen.value = true


                    }

            )
        }
    }
    if (isDialogOpen.value){ TimeAlert(showTimePicker = isDialogOpen, timePicker = timePicker) }
}

@Composable
fun ToggleButton(alarmvalue : Int) {
    val checkedState = remember {
        mutableStateOf(false)
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text = alarmvalue.toString(), color = Color.Blue , fontSize = 30.sp , fontWeight = FontWeight.ExtraBold)
        Switch(checked = checkedState.value, onCheckedChange = {
            ischecked ->
            checkedState.value = ischecked
        },
            colors = SwitchDefaults.colors())
    }
}

@Composable
fun SplashScreen(screen: MutableState<Int>) {

    var iconsize = remember {
        androidx.compose.animation.core.Animatable(0.2f)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent), contentAlignment = Alignment.Center)
    {
        Icon(imageVector = Icons.Filled.Medication, contentDescription = "icon" , tint = Color.Magenta , modifier = Modifier.size(iconsize.value.dp))
    }

    LaunchedEffect(Unit)
    {

        iconsize.animateTo(targetValue = 300f , animationSpec = tween(2000, easing = FastOutSlowInEasing))
        delay(500)
        screen.value = 2

    }
}



fun getsharedprefs(context: Context): SharedPreferences {
    return context.getSharedPreferences("alarms" , Context.MODE_PRIVATE)
}


fun getItems(context: Context ): List<Int>? {
    var sp = getsharedprefs(context)
    val gson = Gson()
    var json = sp.getString("alarmitems",null)
    return gson.fromJson<List<Int>>(json , object  : TypeToken<List<Int>>() {}.type)
}


fun setItems(context: Context , list : List<Int>)
{
    var sp = getsharedprefs(context)
    var gson = Gson()
    val json = gson.toJson(list)
    sp.edit().putString("alarmitems",json).apply()
}