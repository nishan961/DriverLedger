package com.gigpilot.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gigpilot.app.ui.home.HomeScreen
import com.gigpilot.app.ui.home.HomeViewModel
import com.gigpilot.app.ui.report.ReportCardScreen
import com.gigpilot.app.ui.settings.SettingsScreen
import com.gigpilot.app.ui.theme.GigPilotTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeViewModel: HomeViewModel = viewModel()
            val settings by homeViewModel.settingsStore.settings.collectAsState()

            GigPilotTheme(themeMode = settings.themeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val reportShiftId by homeViewModel.reportShiftId.collectAsState()
                    LaunchedEffect(reportShiftId) {
                        reportShiftId?.let { id ->
                            navController.navigate("report/$id")
                            homeViewModel.reportConsumed()
                        }
                    }

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                viewModel = homeViewModel,
                                onOpenSettings = { navController.navigate("settings") }
                            )
                        }
                        composable(
                            route = "report/{shiftId}",
                            arguments = listOf(
                                navArgument("shiftId") { type = NavType.LongType }
                            )
                        ) { entry ->
                            ReportCardScreen(
                                shiftId = entry.arguments?.getLong("shiftId") ?: 0L,
                                viewModel = homeViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                settingsStore = homeViewModel.settingsStore,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
