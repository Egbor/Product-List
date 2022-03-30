package com.example.zooshop.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.zooshop.R
import com.example.zooshop.dialog.ZoobazarDialogCallback
import com.example.zooshop.dialog.ZoobazarProfileCreateDialog
import com.example.zooshop.model.ZoobazarProfile
import com.example.zooshop.storage.ZoobazarStorage
import com.example.zooshop.storage.ZoobazarStorageProfile
import android.Manifest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment(private val storage: ZoobazarStorageProfile?) : Fragment(), ZoobazarDialogCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var zoobazarProfile: ZoobazarProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        zoobazarProfile = storage?.getProfile()
        return if (zoobazarProfile == null) {
            inflater.inflate(R.layout.fragment_new_profile, container, false)
        } else {
            inflater.inflate(R.layout.fragment_profile, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (zoobazarProfile == null) {
            val addProfileButton = view.findViewById<ImageView>(R.id.add_profile_button)
            val profileCreateDialog = ZoobazarProfileCreateDialog(storage!!, this)

            addProfileButton.setOnClickListener {
                activity?.let { activity -> profileCreateDialog.start(activity) }
            }
        } else {
            val profileImage = view.findViewById<ImageView>(R.id.profile_image)
            val profileLoadImage = view.findViewById<ImageView>(R.id.profile_load_image)

            val signOutButton = view.findViewById<Button>(R.id.sign_out_button)
            val nameTextView = view.findViewById<TextView>(R.id.name_textview)
            val surnameTextView = view.findViewById<TextView>(R.id.surname_textview)
            val emailTextView = view.findViewById<TextView>(R.id.email_textview)

            if (zoobazarProfile!!.image.isNotEmpty()) {
                profileImage.setImageBitmap(BitmapFactory.decodeFile(zoobazarProfile!!.image))
            }
            nameTextView.text = zoobazarProfile!!.firstname
            surnameTextView.text = zoobazarProfile!!.lastname
            emailTextView.text = zoobazarProfile!!.email

            val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    //val data: Intent? = result.data
                    val selectedImage = result.data?.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor = activity?.let { activity ->
                        activity.contentResolver.query(selectedImage!!,
                            filePathColumn, null, null, null)
                    }
                    cursor?.moveToFirst()

                    val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                    val picturePath = cursor?.getString(columnIndex!!)
                    cursor?.close()

                    storage?.cacheProfile(ZoobazarProfile(
                        picturePath!!,
                        zoobazarProfile!!.firstname,
                        zoobazarProfile!!.lastname,
                        zoobazarProfile!!.email
                    ))

                    profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                }
            }

            signOutButton.setOnClickListener {
                storage?.removeProfile()
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.fragment_container,
                    ProfileFragment(storage)
                )?.commit()
            }

            profileLoadImage.setOnClickListener {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

                val intent = Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                resultLauncher.launch(intent)
            }
        }
    }

    override fun onZoobazarDialogConfirm() {
        activity?.supportFragmentManager?.beginTransaction()?.replace(
            R.id.fragment_container,
            ProfileFragment(storage)
        )?.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment(storage = null).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}