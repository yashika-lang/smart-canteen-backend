package com.example.smartcanteenapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.smartcanteenapp.ui.theme.SmartCanteenAppTheme
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import kotlinx.coroutines.delay
import java.net.HttpURLConnection
import java.net.URL
import androidx.navigation.NavHostController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.lazy.grid.*
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.snapshots.SnapshotStateList



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartCanteenAppTheme {
                AppNavigation()
            }
        }
    }
}

//////////////////// SPLASH ////////////////////

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFB71C1C))
    ) {
        Text("Bite 🍔", color = Color.White, fontSize = 40.sp,
            modifier = Modifier.align(Alignment.Center))
    }
}

//////////////////// LOGIN ////////////////////

@Composable
fun LoginScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Welcome Back 👋")

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            Thread {
                try {
                    val url = URL("http://192.168.1.34:8080/api/auth/login")
                    val conn = url.openConnection() as HttpURLConnection

                    conn.requestMethod = "POST"
                    conn.doOutput = true
                    conn.setRequestProperty("Content-Type", "application/json")

                    val json = """
                        {"email":"$email","password":"$password"}
                    """

                    conn.outputStream.write(json.toByteArray())

                    val res = conn.inputStream.bufferedReader().readText()

                    (navController.context as ComponentActivity).runOnUiThread {
                        if (res.contains("ADMIN")) {
                            navController.navigate("admin")
                        } else {
                            navController.navigate("home")
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text("Register", color = Color.Blue,
            modifier = Modifier.clickable {
                navController.navigate("register")

            })
    }
}

//////////////////// REGISTER ////////////////////

@Composable
fun RegisterScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Create Account")

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = password, onValueChange = { password = it })

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            Thread {
                try {
                    val url = URL("http://192.168.1.34:8080/api/auth/register")
                    val conn = url.openConnection() as HttpURLConnection

                    conn.requestMethod = "POST"
                    conn.doOutput = true
                    conn.setRequestProperty("Content-Type", "application/json")

                    val json = """
                        {"email":"$email","password":"$password"}
                    """

                    conn.outputStream.write(json.toByteArray())

                    (navController.context as ComponentActivity).runOnUiThread {
                        navController.navigate("login")
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }) {
            Text("Register")
        }
    }
}

//////////////////// HOME ////////////////////
@Composable
fun HomeScreen(navController: NavHostController,
               cartItems: SnapshotStateList<CartItem>) {

    var menu by remember { mutableStateOf(listOf<MenuItem>()) }

    // 🔥 API CALL
    LaunchedEffect(Unit) {
        Thread {
            try {
                val url = URL("http://192.168.1.34:8080/api/menu")
                val res = url.openStream().bufferedReader().readText()

                val items = res.replace("[", "").replace("]", "").split("},")

                val parsedList = items.map {
                    val name = Regex("\"name\":\"(.*?)\"").find(it)?.groupValues?.get(1) ?: ""
                    val price =
                        Regex("\"price\":(\\d+)").find(it)?.groupValues?.get(1)?.toInt() ?: 0
                    val imageUrl =
                        Regex("\"imageUrl\":\"(.*?)\"").find(it)?.groupValues?.get(1) ?: ""

                    MenuItem(name, price, imageUrl)
                }

                menu = parsedList

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    val categories = listOf("All", "Snacks", "Breakfast", "Ice Cream", "Beverages")
    var selected by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FB))
    ) {

        // 🔝 HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.Menu, contentDescription = null)

            Text(
                text = "Bite 🍔",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Icon(Icons.Default.LocationOn, contentDescription = null)
        }

        // 🎁 BANNER
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB71C1C))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "🔥 50% OFF",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🧠 CATEGORY FILTER
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = category == selected

                Text(
                    text = category,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) Color(0xFFB71C1C) else Color.LightGray)
                        .clickable { selected = category }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🍔 MENU GRID
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(menu.size) { index ->
                val item = menu[index]

                Card(
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFDFD))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.name,
                            fontSize = 16.sp
                        )

                        Text(
                            text = "₹${item.price}",
                            fontSize = 14.sp,
                            color = Color(0xFFFF6B00)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                val existing = cartItems.find { it.item.name == item.name }

                                if (existing != null) {
                                    existing.quantity++
                                } else {
                                    cartItems.add(CartItem(item, 1))
                                }

                                println("Added to cart: ${item.name}")},
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB71C1C)
                            )
                        ) {
                            Text("Add", color = Color.White)
                        }
                    }
                }
            }
        }

        // 🔻 BOTTOM NAV
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB71C1C))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Icon(
                Icons.Default.Home, null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    println("Home clicked")
                })

            Icon(
                Icons.Default.ShoppingCart, null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    navController.navigate("cart")
                })

            Icon(
                Icons.Default.Favorite, null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    println("Fav clicked")
                })

            Icon(
                Icons.Default.Person, null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    println("Profile clicked")
                })
        }
    }
}

@Composable
fun CartScreen(cartItems: SnapshotStateList<CartItem>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🛒 TITLE
        Text(
            text = "🛒 Cart",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🧾 ITEMS LOOP
        cartItems.forEach { cartItem ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(modifier = Modifier.padding(12.dp)) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            // 🖼 IMAGE
                            AsyncImage(
                                model = cartItem.item.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = cartItem.item.name,
                                    fontSize = 16.sp
                                )

                                Text(
                                    text = "₹${cartItem.item.price}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        // ❌ REMOVE
                        Text(
                            text = "✕",
                            fontSize = 18.sp,
                            modifier = Modifier.clickable {
                                cartItems.remove(cartItem)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // ➕➖ QUANTITY CONTROL
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "−",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .clickable {
                                    if (cartItem.quantity > 1) {
                                        cartItem.qty--
                                    } else {
                                        cartItems.remove(cartItem)
                                    }
                                }
                                .padding(8.dp)
                        )

                        Text(
                            text = cartItem.qty.toString(),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )

                        Text(
                            text = "+",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .clickable {
                                    cartItem.qty++
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        // 🔥 TOTAL + BUTTON (OUTSIDE LOOP)
        val total = cartItems.sumOf { it.item.price * it.qty }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB71C1C)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Checkout ₹$total", color = Color.White)
        }
    }
}
//////////////////// ADMIN ////////////////////

@Composable
fun AdminScreen(navController: NavHostController) {

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Admin Panel")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") })
        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") })

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            println("BUTTON CLICKED")

            Thread {
                try {
                    val url = URL("http://192.168.1.34:8080/api/menu")
                    val conn = url.openConnection() as HttpURLConnection

                    conn.requestMethod = "POST"
                    conn.doOutput = true
                    conn.setRequestProperty("Content-Type", "application/json")

                    val json = """
                {
                  "name":"$name",
                  "price":$price,
                  "imageUrl":"$imageUrl"
                }
            """

                    conn.outputStream.write(json.toByteArray())

                    // 🔥 IMPORTANT PART
                    val responseCode = conn.responseCode
                    println("Response Code: $responseCode")

                    if (responseCode == 200 || responseCode == 201) {

                        // 👉 UI thread pe navigation
                        (navController.context as ComponentActivity).runOnUiThread {
                            navController.navigate("home")
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()

        }) {
            Text("Add Item")
        }
    }
}

//////////////////// NAVIGATION ////////////////////

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // 🔥 GLOBAL CART (IMPORTANT)
    val cartItems = remember { mutableStateListOf<CartItem>() }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("home") {
            HomeScreen(navController, cartItems) // 👈 PASS KAR DIYA
        }

        composable("cart") {
            CartScreen(cartItems) // 👈 SAME LIST
        }

        composable("admin") {
            AdminScreen(navController)
        }
    }
}
