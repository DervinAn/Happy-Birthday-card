package com.example.happyb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happyb.ui.theme.HappybTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappybTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    var currentScreen by remember { mutableStateOf("main_screen") }
    var name by remember { mutableStateOf(TextFieldValue()) }

    when (currentScreen) {
        "main_screen" -> MainScreen(
            onNameChange = { name = it },
            onSubmit = { currentScreen = "appear_hc_screen" }
        )
        "appear_hc_screen" -> AppearHC(
            name = name.text,
            onBack = {
                currentScreen = "main_screen"
                name = TextFieldValue()
            }
        )
    }
}

@Composable
fun MainScreen(
    onNameChange: (TextFieldValue) -> Unit,
    onSubmit: () -> Unit
) {
    val gradient = Brush.linearGradient(
        0.0f to Color.Magenta,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    var text by remember { mutableStateOf(TextFieldValue()) }
    var borderVisible by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition()
    var color2: Color by remember { mutableStateOf(Color.Magenta) }
    val color by infiniteTransition.animateColor(
        initialValue = Color.Magenta,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                onNameChange(text)
                borderVisible = text.text.isNotEmpty()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .then(
                    if (borderVisible) Modifier.border(
                        BorderStroke(width = 2.dp, color = color),
                        shape = RoundedCornerShape(4.dp)
                    ) else Modifier
                ),
            label = { Text(text = "Enter your name") },
            maxLines = 1,
        )
        Button(
            onClick = {
                color2 = Color.Blue
                onSubmit()
            },
            modifier = Modifier.padding(20.dp),
            border = BorderStroke(width = 1.5.dp, color = color2)
        ) {
            Text(text = "Submit")
        }
    }
}

@Composable
fun AppearHC(name: String, onBack: () -> Unit) {
    val backgroundPainter: Painter = painterResource(id = R.drawable.balones1)
    val scImage: Painter = painterResource(id = R.drawable.ball)
    val scImage2: Painter = painterResource(id = R.drawable.confetti)
    var offsetState by remember { mutableStateOf(-200.dp) }
    val animatedOffset by animateDpAsState(
        targetValue = offsetState,
        animationSpec = tween(
            durationMillis = 1000,
            easing = EaseOutBounce
        )
    )
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor1 by infiniteTransition.animateColor(
        initialValue = Color.Magenta,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val animatedColor2 by infiniteTransition.animateColor(
        initialValue = Color.Cyan,
        targetValue = Color.Yellow,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Create the gradient brush
    val gradient2 = Brush.linearGradient(
        colors = listOf(animatedColor1, animatedColor2)
    )


    val customFont = remember {
        FontFamily(
            Font(R.font.bir, FontWeight.Normal),
            Font(R.font.harley, FontWeight.Bold)
        )
    }

    LaunchedEffect(Unit) {
        offsetState = 0.dp
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = backgroundPainter,
            contentDescription = "background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = scImage,
            contentDescription = "foreground image",
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = animatedOffset),
            contentScale = ContentScale.Crop
        )
        ExplodingImage(scImage2, onAnimationFinish = onBack)

        Box {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = TextStyle(
                                fontSize = 68.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = customFont,
                                color = animatedColor1, // Use gradient for text color
                                shadow = Shadow(
                                    color = Color.Gray,
                                    offset = Offset(4.0f, 4.0f),
                                    blurRadius = 8f
                                )
                            ).toSpanStyle()
                        ) {
                            append("Happy Birthday")
                        }
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 470.dp)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = TextStyle(
                                fontSize = 64.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = customFont,
                                brush = gradient2, // Gold color for the border
                                shadow = Shadow(
                                    color = Color.Gray,
                                    offset = Offset(4.0f, 4.0f),
                                    blurRadius = 8f
                                )
                            ).toSpanStyle()
                        ) {
                            append(name)
                        }
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 0.dp), // Adjust padding as needed
                )

                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = onBack) {
                    Text(text = "Return")
                }
            }
        }
    }
}
@Composable
fun ExplodingImage(painter: Painter, onAnimationFinish: () -> Unit) {
    val explosionScale = remember { Animatable(1f) }
    val scaleAnimationSpec = tween<Float>(durationMillis = 1000, easing = LinearEasing)

    LaunchedEffect(Unit) {
        launch {
            explosionScale.animateTo(
                targetValue = 2f, // Scale the image to double its size during explosion
                animationSpec = scaleAnimationSpec
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            onAnimationFinish()
        }
    }

    Image(
        painter = painter,
        contentDescription = "foreground image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(

                scaleX = explosionScale.value,
                scaleY = explosionScale.value
            )
            .alpha(if (explosionScale.isRunning) 1f else 0f), // Set alpha based on animation progress
        contentScale = ContentScale.FillBounds
    )
}



@Preview(showBackground = true)
@Composable
fun MyComposablePreview() {
    MyApp()
}

