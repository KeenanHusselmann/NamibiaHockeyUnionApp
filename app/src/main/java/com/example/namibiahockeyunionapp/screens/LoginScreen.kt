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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
import com.example.namibiahockeyunionapp.AppUtil
import com.example.namibiahockeyunionapp.R
import com.example.namibiahockeyunionapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel = viewModel()) {


    var email by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }

    var isLoading by remember{
        mutableStateOf(false)
    }

    var context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
        Image( painter = painterResource(id = R.drawable.bg4), contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    Column(
        modifier = Modifier.fillMaxSize().padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
            text = "Welcome",
            modifier = Modifier.fillMaxWidth().padding(2.dp),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

       Column(
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally,
           modifier = Modifier.fillMaxWidth()) {

        Text(
            text = "Sign in to your Account",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

           OutlinedTextField(
               value = email,
               onValueChange = { newText ->
                   if (!newText.contains(' ') && !newText.contains('\n')) {
                       email = newText
                   }
               },
               label = {
                   Text(text = "Email Address")
               },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
               modifier = Modifier.fillMaxWidth()
           )

        Spacer(modifier = Modifier.height(10.dp))

           OutlinedTextField(
               value = password,
               onValueChange = { newText ->
                   if (!newText.contains(' ') && !newText.contains('\n')) {
                       password = newText
                   }
               },
               label = {
                   Text(text = "Password")
               },
               modifier = Modifier.fillMaxWidth(),
               visualTransformation = PasswordVisualTransformation()
           )

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
                    authViewModel.login(email, password) { success, errorMessage ->
                        if (success) {
                            isLoading = false
                            //navigate to home screen
                            navController.navigate("home"){
                                popUpTo("auth"){
                                    inclusive = true
                                }

                            }
                        } else {
                            isLoading = false
                            AppUtil.showToast(context,errorMessage ?: "Something went wrong")
                        }
                    }

                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(text = if(isLoading) "Logging in..." else "Login",
                    fontSize = 22.sp,
                    color = Color.DarkGray)
            }

        }
           Spacer(modifier = Modifier.height(8.dp))

           TextButton(onClick = {
               navController.navigate("signup")
           }) {
               Text(text = "Don't have an account?")
           }


           TextButton(onClick = {
               navController.navigate("signup")
           }) {
               Text(text = "Forgot Password?")
           }
       }
    }
}
}