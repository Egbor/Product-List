package com.example.zooshop.storage

import android.content.Context
import android.provider.ContactsContract
import com.example.zooshop.model.ZoobazarProfile
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ZoobazarStorageProfile(val context: Context) {
    fun cacheProfile(profile: ZoobazarProfile) {
        val fileStream = context.openFileOutput(profileUrl, Context.MODE_PRIVATE)
        val objectStream = ObjectOutputStream(fileStream)

        objectStream.writeObject(profile)
        objectStream.close()
        fileStream.close()
    }

    fun removeProfile() {
        if (context.getFileStreamPath(profileUrl).exists()) {
            context.getFileStreamPath(profileUrl).delete()
        }
    }

    fun getProfile(): ZoobazarProfile? {
        if (!context.getFileStreamPath(profileUrl).exists()) {
            return null
        }

        val fileStream = context.openFileInput(profileUrl)
        val objectStream = ObjectInputStream(fileStream)
        val profile = objectStream.readObject() as ZoobazarProfile

        objectStream.close()
        fileStream.close()

        return profile
    }

    companion object {
        private const val profileUrl = "profile.dat"
    }
}