package com.example.namibiahockeyunionapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.namibiahockeyunionapp.R

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // User Details
                Text(text = "Keenan Husselmann", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "keenan.husselmann39@gmail.com", color = Color.Gray)
                Text(text = "Player", color = MaterialTheme.colorScheme.primary)

                Spacer(modifier = Modifier.height(16.dp))

                // Edit Profile Button
                Button(
                    onClick = { /* TODO: Implement edit profile action */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Profile")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Edit Profile")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Additional Information Section
        Text(text = "Account Information", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        ProfileInfoRow(icon = Icons.Filled.Person, label = "Name", value = "John Doe")
        ProfileInfoRow(icon = Icons.Filled.Email, label = "Email", value = "john.doe@example.com")
        ProfileInfoRow(icon = Icons.Filled.Star, label = "Member Type", value = "Player")
        ProfileInfoRow(icon = Icons.Filled.LocationOn, label = "Location", value = "Windhoek, Namibia")
        ProfileInfoRow(icon = Icons.Filled.Phone, label = "Phone", value = "+264 81 XXX XXXX")
        ProfileInfoRow(icon = Icons.Filled.DateRange, label = "Date of Birth", value = "1990-05-15")

        Spacer(modifier = Modifier.height(24.dp))

//        // Actions Section
//        Text(text = "Actions", style = MaterialTheme.typography.headlineSmall)
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(
//            onClick = { /* TODO: Implement view statistics action */ },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Filled.BarChart, contentDescription = "View Statistics")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "View Statistics")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(
//            onClick = { /* TODO: Implement view teams action */ },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Filled.People, contentDescription = "View Teams")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "View Teams")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedButton(
//            onClick = { /* TODO: Implement settings action */ },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Filled.Settings, contentDescription = "Settings")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "Settings")
//            }
//        }
//
//        Spacer(modifier = Modifier.weight(1f)) // Push bottom buttons to the bottom
//
//        // Logout Button
//        Button(
//            onClick = { /* TODO: Implement logout action */ },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "Logout")
//            }
//        }
   }
}

@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "$label:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(text = value, modifier = Modifier.weight(2f))
    }
}