package com.example.projectprofile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.projectprofile.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private lateinit var CURRENTPHOTOPATH : File
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val REQUEST_CODE = 100
    val REQUEST_IMAGE_CAPTURE = 1
    val FILE_NAME = "photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postData()
        declareanimation()

    }

    override fun onResume() {
        super.onResume()
        opengallery()
    }

    // ANIMATION FOR APP VIEW
    private fun declareanimation() {
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.left_to_rigth1)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.left_to_right2)
        val btt3 = AnimationUtils.loadAnimation(this, R.anim.left_to_right3)
        binding.ImageProfile.startAnimation(stb)
        binding.FloatButton.startAnimation(stb)
        binding.tvname.startAnimation(ttb)
        binding.ETName.startAnimation(btt)
        binding.tvinfo.startAnimation(ttb)
        binding.ETInfo.startAnimation(btt2)
        binding.ButtonSave.startAnimation(btt3)

    }


    // INTENT IMAGE WITHOUT DATABASE TO NEW ACTIVITY
    private fun postData() {
        binding.ButtonSave.setOnClickListener {
            val name = binding.ETName.text.toString()
            val info = binding.ETInfo.text.toString()
            binding.ImageProfile.buildDrawingCache()
            val bitmap: Bitmap = binding.ImageProfile.getDrawingCache()

            startActivity(Intent(this, DetailActivity::class.java).apply {
                putExtra("name", name)
                putExtra("info", info)
                putExtra("BitmapImage", bitmap)

            })
        }

    }

    // OPEN ALERT TO CHOSE CAMERA OR GALLERY
    private fun opengallery() {
        binding.FloatButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Choose")
            alert.setMessage("Gallery or Camera ? ")
            alert.setPositiveButton("Gallery") { dialogInterface: DialogInterface, i: Int ->
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE)
            }
            alert.setNegativeButton("Camera") { dialogInterface: DialogInterface, i: Int ->
               openCamera()
            }
            alert.setNeutralButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
            alert.show()
        }
    }


    // OPEN CAMERA & GIVE THE NAME OF FOTO
    private fun openCamera(){
        if(ContextCompat.checkSelfPermission(
                        applicationContext,Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED)
                    ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE
                    )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        CURRENTPHOTOPATH = getPhotoFile(FILE_NAME)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, CURRENTPHOTOPATH)
        val file_provider = FileProvider.getUriForFile(this,"com.example.projectprofile.fileprovider", CURRENTPHOTOPATH)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,file_provider)
        if(intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }else{
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getPhotoFile(file_name : String): File {
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(file_name,".jpg",storageDir)

    }

    // ACTIVITY RESULT FOR OPEN CAMERA OR GALLERY
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            binding.ImageProfile.setImageURI(data?.data) // handle chosen image
        }else if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
//            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imageBitmap = BitmapFactory.decodeFile(CURRENTPHOTOPATH.absolutePath)
            binding.ImageProfile.setImageBitmap(imageBitmap)
        }
    }


}