package com.example.evatiendadeportes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.evatiendadeportes.R

@Composable
fun PantallaQuienesSomos() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Quiénes Somos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.gato), // tu imagen en res/drawable/logo.png
                contentDescription = "Logo EvaTienda Deportes",
                modifier = Modifier
                    .size(180.dp)
                    .padding(8.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "EvaTienda Deportes es una tienda dedicada al mundo del deporte extremo. " +
                        "Nuestra misión es ofrecer productos de calidad para skaters, rollers y ciclistas BMX, " +
                        "promoviendo el espíritu libre y la aventura urbana.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}