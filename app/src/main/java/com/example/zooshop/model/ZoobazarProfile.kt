package com.example.zooshop.model

import java.io.Serializable

class ZoobazarProfile(image: String, firstname: String, lastname: String, email: String) : Serializable {
    var image = image
    var firstname = firstname
    var lastname = lastname
    var email = email
}