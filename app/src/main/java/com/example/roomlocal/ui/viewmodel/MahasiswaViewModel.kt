package com.example.roomlocal.ui.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomlocal.data.entitiy.Mahasiswa
import com.example.roomlocal.repository.RepositoryMhs
import kotlinx.coroutines.launch
import java.text.Normalizer.Form

class MahasiswaViewModel(private val repositoryMhs: RepositoryMhs) : ViewModel() {

    var uiState by mutableStateOf(MhsUIState())

    //Memperbarui state berdasarkan input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiState = uiState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    //Validasi data input pengguna
    private fun validateFields(): Boolean {
        val event = uiState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nim.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.nim.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.nim.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.nim.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.nim.isNotEmpty()) null else "Angkatan tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }



    // Menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.mahasiswaEvent


        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.insertMhs(currentEvent.toMahasiswaEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data Berhasil disimpan",
                        mahasiswaEvent = MahasiswaEvent(), // Reset input form
                        isEntryValid = FormErrorState() //Reset input state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackbarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    // Reset pesan SnackBar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackbarMessage = null)
    }
}

data class MhsUIState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null,
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null
) {
    fun isValid(): Boolean {
        return nim == null && nama == null && jenisKelamin == null &&
                alamat == null && kelas == null && angkatan == null
    }
}

// Menyimpan input form dalam entity
fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)

//data class Variabel yang menyimpan data input form
data class MahasiswaEvent(
    val nim:String = "",
    val nama:String = "",
    val jenisKelamin:String = "",
    val alamat:String = "",
    val kelas:String = "",
    val angkatan:String = "",
)