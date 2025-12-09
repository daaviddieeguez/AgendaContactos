package org.izv.dam.psp.agenda.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.izv.dam.psp.agenda.ui.viewmodel.ContactFileViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ContactFileViewModel,
    innerPadding: PaddingValues
) {
    //val contactos = listOf("one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three", "one", "two", "three")
    val contacts = viewModel.contacts.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.readContacts()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (contacts.value.isEmpty()) {
            Text("No contacts found.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(items = contacts.value) { contact ->
                    Item(navController, contact)
                }
            }
        }
        Button(onClick = { navController.navigate("add-contact") }) {
            Text("Add contact")
        }
    }
}