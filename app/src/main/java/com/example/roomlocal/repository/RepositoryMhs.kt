package com.example.roomlocal.repository

import com.example.roomlocal.data.entitiy.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
    fun getAllMahasiswa(): Flow<List<Mahasiswa>>
    fun getMhs(nim: String): Flow<Mahasiswa>

}