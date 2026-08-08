package com.ticketchef.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ticketchef.app.navigation.TicketChefNavHost
import com.ticketchef.app.ui.theme.TicketChefTheme
import com.ticketchef.app.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketChefTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TicketChefNavHost(viewModel = viewModel)
                }
            }
        }
    }
}
