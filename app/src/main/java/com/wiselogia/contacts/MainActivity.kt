package com.wiselogia.contacts

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private val contactAdapter by lazy {
        ContactAdapter {
            val call = Intent(Intent.ACTION_DIAL)
            call.data = Uri.parse("tel:" + it.number)
            startActivity(call)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.listContacts).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter

        }

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                android.Manifest.permission.READ_CONTACTS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            askPermission()
        } else
            setRecyclerView()
    }

    private fun setRecyclerView() {
        contactAdapter.contacts = fetchAllContacts(this)
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(android.Manifest.permission.READ_CONTACTS),
            15253252
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            15253252 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setRecyclerView()
                }
                else {
                    val toast = Toast.makeText(this, "no permission", Toast.LENGTH_SHORT)
                    toast.show()
                }
                return
            }
        }
    }
}
