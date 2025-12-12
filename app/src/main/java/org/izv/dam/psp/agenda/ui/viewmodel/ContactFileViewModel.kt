package org.izv.dam.psp.agenda.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.izv.dam.psp.agenda.model.data.Contact
import org.izv.dam.psp.agenda.model.repository.ContactFileRepository

class ContactFileViewModel (private val repository: ContactFileRepository): ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts = _contacts.asStateFlow()

    fun readContacts() {
        viewModelScope.launch {
            _contacts.value = repository.readContacts()
        }
    }

    fun writeContacts(name: String, phone: String) {
        viewModelScope.launch {
            val lastId = repository.readLastContacts()
            val newId = lastId + 1

            val contact = Contact(id = newId, name = name, phone = phone)

            _contacts.value = repository.writeContact(contact)
        }
    }

    fun editContacts(updateContact: Contact) {
        viewModelScope.launch {
            _contacts.value = repository.editContact(updateContact)
        }
    }

    fun deleteContacts(contact: Contact) {
        viewModelScope.launch {
            _contacts.value = repository.deleteContact(contact)
        }
    }
}