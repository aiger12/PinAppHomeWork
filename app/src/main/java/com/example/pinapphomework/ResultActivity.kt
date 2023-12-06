package com.example.pinapphomework

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pinapphomework.Convertor.ConvertorActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        initButton()
    }

    private fun initButton(){
        initButtonShare()
        initButtonShareByEmail()
        initButtonCall()
        initButtonCamera()
        initButtonNews()
        initButtonConvertor()
    }

    private fun initButtonNews(){
        val btnNews:Button=findViewById(R.id.btn_open_news)
        btnNews.setOnClickListener {
            val intentNews=Intent(this, NewsActivity::class.java)
            startActivity(intentNews)
        }
    }
    private fun initButtonShare() {
        val btnShare: Button = findViewById(R.id.btn_share)
        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun initButtonShareByEmail(){
        val btnSend: Button = findViewById(R.id.btn_send_by_email)
        btnSend.setOnClickListener {
            val emailIntent=Intent()
            emailIntent.action=Intent.ACTION_SENDTO
            emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle thi
            emailIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            }
        }
    }

    private fun initButtonCall(){
        val btnCall: Button = findViewById(R.id.btn_call)
        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+77777777777", null))
            startActivity(intent)
        }
    }

    private fun initButtonCamera(){
        val btnCall: Button = findViewById(R.id.btn_open_cam)
        btnCall.setOnClickListener {
            val REQUEST_IMAGE_CAPTURE = 1
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }
    }

    private fun initButtonConvertor(){
        val btnConvertor: Button = findViewById(R.id.btn_open_convertor)
        btnConvertor.setOnClickListener {
            val intentNews=Intent(this, ConvertorActivity::class.java)
            startActivity(intentNews)
        }
    }
}