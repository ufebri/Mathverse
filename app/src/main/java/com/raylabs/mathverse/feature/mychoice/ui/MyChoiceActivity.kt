package com.raylabs.mathverse.feature.mychoice.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raylabs.mathverse.R
import com.raylabs.mathverse.feature.mychoice.MyChoiceSeeder
import com.raylabs.mathverse.feature.mychoice.data.repo.MyChoiceRepository
import com.raylabs.mathverse.feature.mychoice.di.ServiceLocator

class MyChoiceActivity : ComponentActivity() {
    private val locator by lazy { ServiceLocator(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplySystemBarsFromCompose()
            MaterialTheme { MyChoiceNav(locator) }
        }
        MyChoiceSeeder.seedIfNeeded(this)
    }
}

@Composable
fun ApplySystemBarsFromCompose() {
    val view = LocalView.current
    val activity = view.context as Activity

    // Warna background area di belakang status bar kita ambil dari theme agar konsisten
    val surface = MaterialTheme.colorScheme.surface
    val bgIsLight = surface.luminance() > 0.5f

    SideEffect {
        // Edge-to-edge: Compose yang urus insets
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)

        // Status bar transparan agar menyatu dengan Surface (AppBar akan kasih warna)
        activity.window.statusBarColor = android.graphics.Color.TRANSPARENT

        // Ikon status bar: true = ikon gelap (untuk background terang)
        WindowInsetsControllerCompat(activity.window, activity.window.decorView)
            .isAppearanceLightStatusBars = bgIsLight
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
class DashboardViewModelFactory(private val repo: MyChoiceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(repo) as T
    }
}