package com.example.roomlocal.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomlocal.data.dao.MahasiswaDao
import com.example.roomlocal.data.entitiy.Mahasiswa

//Mendifinisikan database dengan tabel Mahasiswa
@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase() {
    //Mendifinisikan fungsi untuk mengakses data Mahasiswa
    abstract fun MahasiswaDao(): MahasiswaDao

    companion object {
        @Volatile // Memastikan bahwa nilai variabel Instance selalu sama di semua thread
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase{
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    KrsDatabase::class.java, // Class Database
                    "KrsDatabase" // Nama Database
                )
                    .build().also { Instance = it }
            })
        }
    }
}