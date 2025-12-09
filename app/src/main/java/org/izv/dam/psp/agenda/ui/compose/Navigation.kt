package org.izv.dam.psp.agenda.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.izv.dam.psp.agenda.model.repository.ContactFileRepository
import org.izv.dam.psp.agenda.ui.viewmodel.ContactFileViewModel

@Composable
    fun MainScreen() {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Navigation(innerPadding)
        }
    }

    @Composable
    fun Navigation(innerPadding: PaddingValues) {
        val navController = rememberNavController()

        val context = LocalContext.current
        val repository = ContactFileRepository(context)
        val viewModel: ContactFileViewModel = viewModel(
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactFileViewModel(repository) as T
                }
            }
        )

        //val viewModel =
        NavHost(
            navController = navController,
            startDestination = "home-screen"
        ) {
            composable("home-screen") {
                //MainMenu(navController)
                HomeScreen(navController, viewModel, innerPadding)
            }
            composable("add-contact") {
                //AddContactScreen(navController, contactos)
                AddContact(navController, viewModel, innerPadding)
            }
            composable("edit-contact/{contactId}") { backStackEntry ->
                val idString = backStackEntry.arguments?.getString("contactId")

                // Si idString es nulo, esto devuelve 0. Eso es lo que te está pasando.
                val contactId = idString?.toIntOrNull() ?: 0

                EditContact(
                    navController = navController,
                    viewModel = viewModel,
                    innerPadding = innerPadding,
                    contactId = contactId
                )
            }
        }
    }
