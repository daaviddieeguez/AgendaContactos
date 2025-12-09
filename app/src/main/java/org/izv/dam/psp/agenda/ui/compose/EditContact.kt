package org.izv.dam.psp.agenda.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.izv.dam.psp.agenda.model.data.Contact
import org.izv.dam.psp.agenda.ui.viewmodel.ContactFileViewModel

@Composable
fun EditContact(
    navController: NavController,
    viewModel: ContactFileViewModel,
    innerPadding: PaddingValues,
    contactId: Int
) {
    val contactsState = viewModel.contacts.collectAsState()

    var contact by remember { mutableStateOf<Contact?>(null) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(key1 = contactId, key2 = contactsState.value) {
        val foundContact = contactsState.value.firstOrNull { it.id == contactId }

        if (foundContact != null) {
            contact = foundContact
            name = foundContact.name
            phone = foundContact.phone
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Text(
            text = "Edit contact",
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Button(onClick = {
            if(contact != null) {
                val updatedContact = contact!!.copy(
                    name = name,
                    phone = phone
                )

                viewModel.editContacts(updatedContact)
                navController.popBackStack()
            }
        }) {
            Text("Edit")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            val copyContact = contact

            if(copyContact != null){
                val deleteContact = contact!!.copy(
                    name = name,
                    phone = phone
                )

                viewModel.deleteContacts(deleteContact)
                navController.popBackStack()
            }
        }) {
            Text("Delete")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}