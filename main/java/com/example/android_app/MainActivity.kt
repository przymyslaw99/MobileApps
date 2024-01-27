package com.example.android_app

import UIState
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android_app.ui.theme.Android_appTheme
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import com.example.android_app.repository.DetailsActivity
import com.example.android_app.repository.Starship
import kotlin.random.Random



class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getData()

        setContent {
            Android_appTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(
                        viewModel = viewModel,
                        onClick = { id -> navigateToDetails(id) }
                    )
                }
            }
        }
    }

    fun navigateToDetails(id: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("CUSTOM_ID", id)
        startActivity(intent)
    }
}


@Composable
fun MyListView(starships: List<Starship>, onClick: (String) -> Unit){
    LazyColumn{
        items(starships) {starship ->
            Log.d("MainActivity", "${starship.name}")
            StarshipView(
                name = starship.name,
                model = starship.model,
                onClick = { id -> onClick.invoke(id) })
        }
    }
}

@Composable
fun MainView(viewModel: MainViewModel, onClick: (String) -> Unit) {
    val uiState by viewModel
        .liveDataStarships.observeAsState(UIState())

    when {
        uiState.isLoading -> {
            MyLoadingView()
        }

        uiState.error != null -> {
            MyErrorView(uiState.error)
        }

        uiState.data != null -> {
            uiState.data?.let {
                MyListView(
                    starships = it,
                    onClick = { id -> onClick.invoke(id) })
            }
        }
    }
}

@Composable
fun MyLoadingView(){
    CircularProgressIndicator()
}

@Composable
fun MyErrorView(error: String?){
    Text(text = error?: "ERROR")
}


@Composable
fun StarshipView(name: String, model: String, onClick: (String) -> Unit) {
    val randomNumber = Random.nextInt(1, 6)
    val randomImageResource = getRandomImage(randomNumber)

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .clickable { onClick.invoke("1") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = name,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = model,
                color = Color.Black,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "19.99 $",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Handle button click here */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.Yellow)
            ) {
                Text(
                    text = "Buy Here",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = randomImageResource,
                contentDescription = "#Description",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
        }
    }
}



@Composable
fun DetailView(name: String, model: String, manufacturer: String, speed: String, hyperdrive: String) {
    val randomNumber = Random.nextInt(1, 6)
    val randomImageResource = getRandomImage(randomNumber)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray)
    ) {
        Text(
            text = "Starship details:",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Name: $name",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "Model: $model",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "Manufacturer: $manufacturer",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "Max atmosphering speed: $speed",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "Hyperdrive rating: $hyperdrive",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = randomImageResource,
            contentDescription = "#Description",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))

    }

}


fun getRandomImage(number: Int) =
    when(number){
        1 -> R.drawable.statek1
        2 -> R.drawable.statek2
        3 -> R.drawable.statek3
        4 -> R.drawable.statek4
        else -> R.drawable.statek5
    }

@Preview(showBackground = true)
@Composable
fun StarshipViewPreview() {
    Android_appTheme {
        StarshipView("test_name", "test_model", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DetailViewPreview() {
    Android_appTheme {
        DetailView("test_name", "test_model",
            "test_manufacturer", "test_speed", "test_hyperdrive")
    }
}