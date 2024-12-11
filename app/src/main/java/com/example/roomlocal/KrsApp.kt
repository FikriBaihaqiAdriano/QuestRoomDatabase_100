package com.example.roomlocal

import android.app.Application
import com.example.roomlocal.dependeciesinjection.ContainerApp

class KrsApp : Application() {
    // Fungsinya untuk menyimpan instance ContainerApp
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        // Membuat instance ContainerApp
        containerApp = ContainerApp(this)
        // Instance adalah object yang dibuat dari class
    }
}