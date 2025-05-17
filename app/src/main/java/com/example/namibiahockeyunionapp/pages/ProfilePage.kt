package com.example.namibiahockeyunionapp.pages

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.namibiahockeyunionapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.setValue


@Composable
fun ProfilePage(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController(), onLogout: () -> Unit) {
    var name by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnSuccessListener() {
                name = it.get("email").toString()
            }
    }

    var email by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnSuccessListener() {
                email = it.get("name").toString()
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg4),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        // Make the Column scrollable
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState), // Add verticalScroll modifier
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Header Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), // Reduced width by adding horizontal padding
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var imageUri by remember { mutableStateOf<Uri?>(null) }
                    val context = LocalContext.current
                    val bitmap = remember {
                        mutableStateOf<Bitmap?>(null)
                    }

                    val launcher = rememberLauncherForActivityResult(contract =
                    ActivityResultContracts.GetContent()) { uri: Uri? ->
                        imageUri = uri
                    }
                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        imageUri?.let {
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap.value = android.provider.MediaStore.Images
                                    .Media.getBitmap(context.contentResolver, it)

                            } else {
                                val source = ImageDecoder
                                    .createSource(context.contentResolver, it)
                                bitmap.value = ImageDecoder.decodeBitmap(source)
                            }

                            bitmap.value?.let { btm ->
                                Image(
                                    bitmap = btm.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.size(400.dp)
                                )
                            }
                        } ?: Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    // User Details
                    Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(text = "Player", color = MaterialTheme.colorScheme.primary)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons on the Card
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround // Distribute buttons horizontally
                    ) {
                        Button(
                            onClick = { /* TODO: Implement edit profile action */ },
                        ) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit Profile")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Edit")
                        }
                        Button(
                            onClick = { /* TODO: Implement view statistics action */ },
                        ) {
                            Icon(Icons.Filled.BarChart, contentDescription = "View Statistics")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Stats")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Additional Information Section
            Text(text = "Account Information", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            ProfileInfoRow(icon = Icons.Filled.Person, label = "Name", value = name)
            ProfileInfoRow(icon = Icons.Filled.Email, label = "Email", value = email)
            ProfileInfoRow(icon = Icons.Filled.LocationOn, label = "Location", value = "Windhoek, Namibia")
            ProfileInfoRow(icon = Icons.Filled.Phone, label = "Phone", value = "+264 81 884 5566")
            ProfileInfoRow(icon = Icons.Filled.DateRange, label = "Date of Birth", value = "1990-05-15")

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Distribute buttons with space between
            ) {
                // View Teams Button
                Button(
                    onClick = { navController.navigate("teams") },
                    modifier = Modifier.weight(1f) // Take up available space
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.People, contentDescription = "View Teams")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "View Teams")
                    }
                }

                Spacer(modifier = Modifier.width(8.dp)) // Add space between buttons

                // Logout Button
                Button(
                    onClick = {
                        Firebase.auth.signOut()
                        navController.navigate("auth") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier.weight(1f), // Take up available space
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Logout")
                    }
                }
            }
        }
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