package com.bangkit.faniabdullah_bfaa

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.faniabdullah_bfaa.databinding.ActivityMainBinding
import com.bangkit.faniabdullah_bfaa.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityMainBinding? = null
    companion object {
        private const val SMS_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnPermission?.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_permission -> PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }


}