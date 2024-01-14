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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(
                        viewModel = viewModel,
                        onClick = { id -> navigateToDetails(id) })
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

//@Composable
//fun StarshipsDisplay(viewModel: MainViewModel){
//    val starships by viewModel.immutableStarshipsData.observeAsState(emptyList())

@Composable
fun MyListView(starships: List<Starship>, onClick: (String) -> Unit){
//    if (starships.isNotEmpty()){
        // don't forget -> import androidx.compose.foundation.lazy.items
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

    Column (
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable{ onClick.invoke("1")}
//            .clickable { onClick.invoke()}
    ){
        Text(
//            text = "Luke Skywalker",
            text = name,
            fontStyle = FontStyle.Italic,
            color = Color.Black,
            modifier = Modifier
//                .padding(10.dp)
//                .border(BorderStroke(1.dp, Color.Black))
                .background(Color.Gray),
        )
        Text(
            text = model,
            fontStyle = FontStyle.Italic,
            color = Color.White,
            modifier = Modifier
//                .padding(10.dp)
//                .border(BorderStroke(1.dp, Color.Black))
                .background(Color.Black),
        )

        Text(
            text = "19.99 $",
            color = Color.Green,
//            modifier = Modifier
//                .padding(horizontal = 35.dp)
        )

        Button(
            onClick = { /* Handle button click here */ },
//            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Buy here",
                color = Color.Black,
                )
        }

//        AsyncImage(model = randomImageResource)

        AsyncImage(
            model = randomImageResource,
//            model = "https://fastly.picsum.photos/id/111/4400/2656.jpg?hmac=leq8lj40D6cqFq5M_NLXkMYtV-30TtOOnzklhjPaAAQ",
            contentDescription = "#Description",
//            contentScale =
            modifier = Modifier
                .height(100.dp)
                .width(120.dp)
        )

//        Text(
//            text = "Free delivery!",
//            fontStyle = FontStyle.Italic,
//            color = Color.White,
//            modifier = Modifier
//                .padding(horizontal = 18.dp)
//                .border(BorderStroke(1.dp, Color.Black))
//                .background(Color.Gray)
//        )

    }
}

//fun Text(text: String, fontSize: String, fontStyle: FontStyle, color: Color, modifier: Modifier) {
//
//}

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
