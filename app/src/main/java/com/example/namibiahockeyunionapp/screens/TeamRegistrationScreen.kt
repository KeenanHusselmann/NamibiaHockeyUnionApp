package com.example.namibiahockeyunionapp.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.namibiahockeyunionapp.AppUtil
import com.example.namibiahockeyunionapp.R
import com.example.namibiahockeyunionapp.viewmodel.AuthViewModel


@Composable
fun TeamRegistrationScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel = viewModel()) {

    var teamName by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }


    var isLoading by remember {
        mutableStateOf(false)
    }


    var context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg4), contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logotr),
                contentDescription = "Login",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop

            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Register New Team",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = teamName,
                onValueChange = {
                    teamName = it
                },
                label = {
                    Text(text = "Team Name")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = category,
                onValueChange = {
                    category = it
                },
                label = {
                    Text(text = "Category")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        brush = Brush.horizontalGradient(

                            colors = listOf(Color.Green, Color.Cyan)
                        ),
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {

                Button(
                    onClick = {
                        isLoading = true
                        authViewModel.teamSignup(teamName, category) { success, errorMessage ->
                            if (success) {
                                isLoading = false
                                //navigate to home screen
                                navController.navigate("home") {
                                    popUpTo("auth") {//clearing backstack
                                        inclusive = true
                                    }
                                }
                            } else {
                                isLoading = false
                                AppUtil.showToast(context, errorMessage ?: "Something went wrong")
                            }
                        }

                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(60.dp),
                    colors = ButtonDefaults
                        .buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = if (isLoading) "Registering Team..." else "Register Team",
                        fontSize = 22.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
