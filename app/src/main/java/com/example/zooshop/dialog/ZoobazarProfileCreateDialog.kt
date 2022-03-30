package com.example.zooshop.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.zooshop.R
import com.example.zooshop.model.ZoobazarProfile
import com.example.zooshop.storage.ZoobazarStorageProfile

class ZoobazarProfileCreateDialog(private val storage: ZoobazarStorageProfile,
                                  private val callback: ZoobazarDialogCallback) {
    private var dialog: AlertDialog? = null

    fun start(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.dialog_profile_creation, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog?.show()

        startListeners()
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    private fun startListeners() {
        val firstnameEditText = dialog?.findViewById<EditText>(R.id.edit_text_firstname)
        val lastnameEditText = dialog?.findViewById<EditText>(R.id.edit_text_lastname)
        val emailEditText = dialog?.findViewById<EditText>(R.id.edit_text_email)
        val createButton = dialog?.findViewById<Button>(R.id.button_create)

        firstnameEditText?.addTextChangedListener {
           createButton?.isEnabled = firstnameEditText.text.isNotEmpty() &&
                   lastnameEditText?.text?.isNotEmpty() == true &&
                   emailEditText?.text?.isNotEmpty() == true
        }

        lastnameEditText?.addTextChangedListener {
            createButton?.isEnabled = lastnameEditText.text.isNotEmpty() &&
                    firstnameEditText?.text?.isNotEmpty() == true &&
                    emailEditText?.text?.isNotEmpty() == true
        }

        emailEditText?.addTextChangedListener {
            createButton?.isEnabled = emailEditText.text.isNotEmpty() &&
                    firstnameEditText?.text?.isNotEmpty() == true &&
                    lastnameEditText?.text?.isNotEmpty() == true
        }

        createButton?.setOnClickListener {
            storage.cacheProfile(ZoobazarProfile(
                "",
                firstnameEditText?.text.toString(),
                lastnameEditText?.text.toString(),
                emailEditText?.text.toString()
            ))
            dismiss()

            callback.onZoobazarDialogConfirm()
        }
    }
}