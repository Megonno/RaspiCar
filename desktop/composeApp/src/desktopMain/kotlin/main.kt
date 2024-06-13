import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.main.AppRoot
import ui.main.MainViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DemoMBOpenScience"
    ) {
        AppRoot(viewModel = MainViewModel())
    }
}
