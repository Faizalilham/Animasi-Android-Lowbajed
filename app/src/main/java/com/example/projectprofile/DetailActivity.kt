package com.example.projectprofile

import android.graphics.Bitmap
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.projectprofile.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        declareanimation()
    }

    private fun declareanimation(){
        val animname = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top1)
        val animinfo = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top2)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        binding.DetailImageProfile.startAnimation(stb)
        binding.textviewname.startAnimation(animname)
        binding.TvName.startAnimation(animname)
        binding.textviewinfo.startAnimation(animinfo)
        binding.TvInfo.startAnimation(animinfo)
    }
    private fun getData(){
        val getnama = intent.getStringExtra("name")
        val getinfo = intent.getStringExtra("info")
        val bitmap = intent.getParcelableExtra("BitmapImage") as Bitmap?

        binding.TvName.text = getnama
        binding.TvInfo.text = getinfo
        binding.DetailImageProfile.setImageBitmap(bitmap)

    }
}