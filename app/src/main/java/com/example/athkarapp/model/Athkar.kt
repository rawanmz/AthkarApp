package com.example.athkarapp.model


import com.google.gson.annotations.SerializedName

data class ResponseAthakr(
    @SerializedName("athkar")
    val list: List<Athkar> = emptyList()
)

data class Athkar(
    var id: Int = 0,
    @SerializedName("nameAlthker")
    val nameAlthker: String,
    val titleAlthker: String?,
    val category: AthkarCategory,
    val howTo: String?,
    val position: Int,
    val hadithContent: HadithContent?,
    val QuranContent: QuranContent?,
    val times: Int,
    val strength: String?
)

data class QuranContent(
    val surah: String,
    val surahName: String,
    val startOfAyah: Int,
    val endOfAyah: Int
)

data class HadithContent(val hadith: String?, val listHadith: List<String>?)