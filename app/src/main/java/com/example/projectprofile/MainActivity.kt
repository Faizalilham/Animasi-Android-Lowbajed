package com.example.projectprofile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.projectprofile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    companion object{
        val REQUEST_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postData()
        opengallery()
        declareanimation()
    }
    private fun declareanimation(){
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

    private fun postData(){
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
    private fun opengallery(){
        binding.FloatButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            binding.ImageProfile.setImageURI(data?.data) // handle chosen image


        }
    }
}