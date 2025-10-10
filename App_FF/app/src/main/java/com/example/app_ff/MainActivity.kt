package com.example.app_ff

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import android.content.res.Configuration
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_ff.ui.theme.App_FFTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import com.example.app_ff.viewmodel.SampleData
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.app_ff.ui.screens.HomeScreen2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_FFTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FormularioScreen()
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)


@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.gato),
            contentDescription = null,
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }

        Column(modifier = Modifier.clickable {isExpanded=!isExpanded}) {
            Text(text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall)

            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium){
            Text(text = msg.body,
                modifier= Modifier.padding(all=4.dp),
                maxLines=if (isExpanded) Int.MAX_VALUE else 1,
                style = MaterialTheme.typography.bodyMedium)
}
        }
    }
}



@Preview(name = "Light Mode")

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)


@Composable
fun PreviewMessageCard() {
    App_FFTheme {
        Surface {
            MessageCard(
                msg = Message("Lexi", "Take a look at Jetpack Compose, it's great!")
            )
        }
    }
}
@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    App_FFTheme{
        Conversation(SampleData.conversationSample)
    }
}

@Composable
fun SimpleTextField() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Enter text") }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_FFTheme {
        HomeScreen2()
    }
}

@Composable
fun FormularioScreen() {
    // Definición de los estados del formulario usando destructuring 'by'
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") } // Mensaje de validación/éxito

    // La lógica de validación y envío
    val onEnviarClick: () -> Unit = {
        if (nombre.isBlank() || email.isBlank()) {
            mensaje = "🚫 Por favor, completa todos los campos."
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mensaje = "❌ Formato de correo electrónico inválido."
        } else {
            // Lógica de envío exitoso (aquí iría la llamada a la API o ViewModel)
            mensaje = "✅ Datos enviados con éxito:\nNombre: $nombre\nEmail: $email"
        }
    }
    FormularioUI(
        nombre = nombre,
        onNombreChange = { nombre = it }, // Función para actualizar el estado del nombre
        email = email,
        onEmailChange = { email = it },   // Función para actualizar el estado del email
        onEnviarClick = onEnviarClick,
        mensaje = mensaje
    )
}
@Composable
fun FormularioUI(
    nombre: String,
    onNombreChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    onEnviarClick: () -> Unit,
    mensaje: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro de Usuario",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo para el Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre Completo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // Campo para el Email
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true
        )

        // Botón de Enviar
        Button(
            onClick = onEnviarClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mensaje de resultado o validación
        if (mensaje.isNotEmpty()) {
            val color = when {
                mensaje.startsWith("✅") -> Color(0xFF006400) // Verde oscuro para éxito
                mensaje.startsWith("🚫") || mensaje.startsWith("❌") -> Color.Red
                else -> Color.Black
            }
            Text(
                text = mensaje,
                color = color,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FormularioPreview() {
    App_FFTheme {
        FormularioUI(
            nombre = "Juan Pérez",
            onNombreChange = {},
            email = "juan@ejemplo.com",
            onEmailChange = {},
            onEnviarClick = {},
            mensaje = "Esto es un mensaje de prueba para el Preview."
        )
    }
}