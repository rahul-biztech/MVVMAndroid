package com.milansolanki.mvvmandroid.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    topSpace: Int? = 0,
    label: String,
    fieldState: State<String>,
    errorState: State<Int?>,
    imeAction: ImeAction? = ImeAction.Next,
    keyboardType: KeyboardType? = KeyboardType.Text,
    onTextChanged: (value: String) -> Unit,
    isEnabled: Boolean = true
) {

    Column {
        Spacer(modifier = Modifier.height(topSpace!!.dp))
        OutlinedTextField(value = fieldState.value,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction!!, keyboardType = keyboardType!!
            ),
            onValueChange = onTextChanged,
            enabled = isEnabled,
            label = { Text(label) })
        if (errorState.value != null)
            Text(
                stringResource(id = errorState.value!!),
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
    }
}