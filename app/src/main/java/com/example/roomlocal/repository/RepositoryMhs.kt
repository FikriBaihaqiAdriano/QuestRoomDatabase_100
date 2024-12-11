package com.example.roomlocal.repository

import com.example.roomlocal.data.entitiy.Mahasiswa

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
}