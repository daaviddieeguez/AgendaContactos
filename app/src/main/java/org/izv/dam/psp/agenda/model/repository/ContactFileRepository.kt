package org.izv.dam.psp.agenda.model.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.izv.dam.psp.agenda.model.data.Contact
import java.io.File

class ContactFileRepository (private val context: Context){
    val fileName = "contacts.csv"

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    //suspend fun fileRead(): MutableList<Contact> {
    suspend fun readContacts(): MutableList<Contact> {
        return withContext(Dispatchers.IO) {
            val listaContactos: MutableList<Contact> = mutableListOf()

            val file = getFile()

            if (file.exists()) {
                val lines = file.readLines()
                lines.forEach {
                    val line = it.split(";")
                    if(line.size == 3) {
                        val contact = Contact(
                            id = line[0].toInt(),
                            name = line[1],
                            phone = line[2]
                        )
                        listaContactos.add(contact)
                    }
                }
                listaContactos
            } else {
                mutableListOf()
            }
        }
    }

    /*Corrutinas: métodos que se ejecutan en segundo plano para no afectar a la IU*/
    suspend fun readLastContacts(): Int {
        return withContext(Dispatchers.IO) {
            val file = getFile()

            if (file.exists()) {
                val lines = file.readLines()
                if (lines.isNotEmpty()) {
                    val lastLine = lines.last()

                    val line = lastLine.split(";")

                    if(line.size >= 1) {
                        line[0].toInt()
                    }else{
                        0
                    }
                }else{
                    0
                }
            }else {
                0
            }
        }
    }

    suspend fun writeContact(contact: Contact): List<Contact>{
        return withContext(Dispatchers.IO){
            val file = getFile()
            val newContact = "${contact.id};${contact.name};${contact.phone}"

            if(file.exists() && file.length()>0) {
                file.appendText("\n${newContact}")
            }else{
                file.writeText(newContact)
            }

            readContacts()
        }
    }

    suspend fun editContact(contact: Contact): List<Contact>{
        return withContext(Dispatchers.IO){
            val listaContactos: MutableList<Contact> = readContacts()

            val index = listaContactos.indexOfFirst { it.id == contact.id }

            if(index != -1){
                listaContactos[index] = contact

                val updateFile = listaContactos.joinToString("\n") { contact ->
                    "${contact.id};${contact.name};${contact.phone}"
                }

                val file = getFile()
                file.writeText(updateFile)
            }

            listaContactos
        }
    }

    suspend fun deleteContact(contact: Contact): List<Contact>{
        return withContext(Dispatchers.IO){
            val listaContactos: MutableList<Contact> = readContacts()

            val index = listaContactos.indexOfFirst { it.id == contact.id }

            if(index != -1){
                listaContactos.removeAt(index)

                val updateFile = listaContactos.joinToString("\n") { contact ->
                    "${contact.id};${contact.name};${contact.phone}"
                }

                val file = getFile()
                file.writeText(updateFile)
            }

            listaContactos
        }
    }
}