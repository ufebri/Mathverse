package com.raylabs.mathverse.feature.mychoice.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raylabs.mathverse.feature.mychoice.di.ServiceLocator

class MyChoiceActivity : ComponentActivity() {
    private val locator by lazy { ServiceLocator(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { MyChoiceNav(locator) } }
    }
}

@Composable
fun MyChoiceNav(locator: ServiceLocator) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "dashboard") {
        composable("dashboard") {
            val vm: DashboardViewModel =
                viewModel(factory = DashboardViewModelFactory(locator.repo))
            DashboardScreen(vm, nav)
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            val id = it.arguments?.getLong("id") ?: 0L
            val vm: DetailViewModel =
                viewModel(factory = DetailViewModelFactory(locator.repo, locator.compute, id))
            DetailScreen(vm) { nav.popBackStack() }
        }
    }
}

// Simple VM Factory for Dashboard
class DashboardViewModelFactory(private val repo: com.raylabs.mathverse.feature.mychoice.data.repo.MyChoiceRepository) :
    androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(repo) as T
    }
}