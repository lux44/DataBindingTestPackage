package com.lux.zena.databindingtestpackage.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.databinding.DataBindingUtil
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.lux.zena.databindingtestpackage.R
import com.lux.zena.databindingtestpackage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var options: ScanOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.main=this
        
        binding.btn.setOnClickListener {
            options=ScanOptions().setOrientationLocked(false).setCaptureActivity(BarcodeScanner::class.java)
            options.setBarcodeImageEnabled(true)
            options.setBeepEnabled(true)
            options.setPrompt("QR")
            barcodeLauncher.launch(options)
        }
    }

    val barcodeLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
        ScanContract()
    ){
        Log.e("MAIN","contents is null")
        if (it.contents==null){
            val intent:Intent = it.originalIntent
            if (intent==null) Log.e("MAIN ACTIVITY","Cancelled Scan")
            else if (intent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)){
                Log.e("MAIN ACTIVITY","Cancelled scan due to missing camera permission")
            }
        }else{
            Log.e("MAIN ACTIVITY","Scanned")
            Toast.makeText(this, "Scanned ${it.contents}", Toast.LENGTH_SHORT).show()
        }
    }
}