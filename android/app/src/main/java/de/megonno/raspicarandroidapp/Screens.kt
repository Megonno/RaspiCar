package de.megonno.raspicarandroidapp

import androidx.compose.runtime.Composable
import de.megonno.raspicarandroidapp.ui.control.ControlScreen

sealed class Screens {
    data object ControlScreen : Screens() {
        @Composable
        override fun Compose() {
            ControlScreen()
        }
    }

    @Composable
    abstract fun Compose()
}
