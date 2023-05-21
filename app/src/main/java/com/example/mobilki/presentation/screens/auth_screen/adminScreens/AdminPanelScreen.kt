package com.example.mobilki.presentation.screens.auth_screen.adminScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilki.data.UserViewModel.UserViewModel
import com.example.mobilki.data.enity.User
import com.example.mobilki.presentation.dim.Dimens
import com.example.mobilki.ui.theme.typography
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun UserRow(
    user: User,
    onMakeAdminClicked: () -> Unit,
    onRemoveAdminClicked: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        user.name?.let { Text(text = it) }
        Spacer(modifier = Modifier.weight(1f))
        if (!user.isAdmin) {
            Button(onClick = onMakeAdminClicked) {
                Text(text = "Назначить администратором")
            }
        } else {
            Button(onClick = onRemoveAdminClicked) {
                Text(text = "Снять администраторские права")
            }
        }
    }
}

