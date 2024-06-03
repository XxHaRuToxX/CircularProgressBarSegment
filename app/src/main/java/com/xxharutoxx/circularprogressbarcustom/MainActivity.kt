package com.xxharutoxx.circularprogressbarcustom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xxharutoxx.circularprogressbarcustom.ui.theme.CircularProgressBarCustomTheme
import com.xxharutoxx.circularprogressbarcustom.utils.ExperimentalHorologistComposablesApi
import com.xxharutoxx.circularprogressbarcustom.utils.ProgressIndicatorSegment
import com.xxharutoxx.circularprogressbarcustom.utils.SquareSegmentedProgressIndicator
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CircularProgressBarCustomTheme {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    PreviewProgressAnimation()
                }
            }
        }
    }
}

val previewProgressSections = listOf(
    ProgressIndicatorSegment(
        weight = 3f,
        indicatorColor = Color.Cyan
    ),
    ProgressIndicatorSegment(
        weight = 3f,
        indicatorColor = Color.Magenta
    ),
    ProgressIndicatorSegment(
        weight = 3f,
        indicatorColor = Color.Yellow
    ),
    ProgressIndicatorSegment(
        weight = 3f,
        indicatorColor = Color.Green
    ),
    ProgressIndicatorSegment(
        weight = 3f,
        indicatorColor = Color.Cyan
    ),
    ProgressIndicatorSegment(
        weight = 3f,
        indicatorColor = Color.Magenta
    ),
    ProgressIndicatorSegment(
        weight = 3f,
        indicatorColor = Color.Yellow
    ),
    ProgressIndicatorSegment(
        weight = 3f,
        indicatorColor = Color.Green
    )
)

val segmentNumber = (1..17).map { _ ->
    ProgressIndicatorSegment(weight = 100f, indicatorColor = Color.Blue)
}

@OptIn(ExperimentalHorologistComposablesApi::class)
@Composable
fun PreviewHighCornerRadius() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        SquareSegmentedProgressIndicator(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp),
            progress = 0.5f,
            trackSegments = previewProgressSections,
            cornerRadiusDp = 50.dp
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewCircular(){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        PreviewProgressAnimation()
    }
}

enum class PreviewAnimationState(val target: Float) {
    Start(0f), End(0.65f)
}
@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalHorologistComposablesApi::class)
@Composable
fun PreviewProgressAnimation() {
    var progressState by remember { mutableStateOf(PreviewAnimationState.Start) }
    val transition = updateTransition(
        targetState = progressState,
        label = "Square Progress Indicator"
    )

    val progress by transition.animateFloat(
        label = "Progress",
        targetValueByState = { it.target },
        transitionSpec = {
            tween(durationMillis = 500, easing = LinearEasing)
        },
    )

    val cornerRadiusDp = 120.dp
    Box(modifier = Modifier.size(300.dp)) {
        SquareSegmentedProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .height(300.dp)
                .width(300.dp),
//                .clickable {
//                    progressState = if (progressState == PreviewAnimationState.Start) {
//                        PreviewAnimationState.End
//                    } else {
//                        PreviewAnimationState.Start
//                    }
//                },
            progress = progress,
//            trackSegments = previewProgressSections,
            trackSegments = segmentNumber,
            cornerRadiusDp = cornerRadiusDp,
            paddingDp = 1000.dp,
            strokeWidth = 30.dp
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "${(progress * 100).toInt()}%",
            color = Color.Black
        )
        Text(
            modifier = Modifier.align(Alignment.Center).padding(top = 20.dp),
            text = "${(progress * 100).toInt()}%",
            color = Color.Black
        )
        val cornerRadiusPx: Float = with(LocalDensity.current) { cornerRadiusDp.toPx() }
        /*Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                Color.LightGray,
                Offset(size.width / 2, 0f),
                Offset(size.width / 2, size.height),
                strokeWidth = 0.2f
            )
            drawLine(
                Color.LightGray,
                Offset(0f, size.height / 2),
                Offset(size.width, size.height / 2),
                strokeWidth = 0.2f
            )

            drawLine(
                Color.LightGray,
                Offset(cornerRadiusPx, 0f),
                Offset(cornerRadiusPx, size.height),
                strokeWidth = 0.2f
            )
            drawLine(
                Color.LightGray,
                Offset(size.width - cornerRadiusPx, 0f),
                Offset(size.width - cornerRadiusPx, size.height),
                strokeWidth = 0.2f
            )

            drawLine(
                Color.LightGray,
                Offset(0f, cornerRadiusPx),
                Offset(size.width, cornerRadiusPx),
                strokeWidth = 0.2f
            )
            drawLine(
                Color.LightGray,
                Offset(0f, size.height - cornerRadiusPx),
                Offset(size.width, size.height - cornerRadiusPx),
                strokeWidth = 0.2f
            )
        }*/
    }

    LaunchedEffect(Unit) {
        while (true) {
            progressState = PreviewAnimationState.End
            delay(100000)
            progressState = PreviewAnimationState.Start
        }
    }
}


