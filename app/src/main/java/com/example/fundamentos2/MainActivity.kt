package com.example.fundamentos2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.fundamentos2.ui.theme.Fundamentos2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Fundamentos2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HelloScreen()
                }
            }
        }
    }
}


@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    //usando remember cuando gire la pantalla se inicializa todo desde el principio
    //para evitar que se reinicie al girar usar rememberSaveable
    //Para facilitar los test vamos a usar state hosting que es crear otra funcion que guarde el state
    //en nuestro caso será HelloScreen
    //var name: String by remember { mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!name.isEmpty()) {
            Text("Hola mundo $name")
        }
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nombre") }
        )
    }

}

@Composable
fun HelloScreen(helloViewModel: HelloViewModel = HelloViewModel()) {
    //var name: String by rememberSaveable { mutableStateOf("") }
    val name: String by helloViewModel.name.observeAsState("");
    //HelloContent(name = name, onNameChange = { name = it })
    HelloContent(name = name, onNameChange = {helloViewModel.onNameChange(it)})
}

class HelloViewModel: ViewModel(){
    private val _name: MutableLiveData<String> = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(newName: String){
        _name.value = newName
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Fundamentos2Theme {
        HelloScreen()
    }
}