package com.example.juego

import android.graphics.Color

class Paleta {

    val colorsAbeja = listOf(
        Color.parseColor("#ffff42"),
        Color.parseColor("#553d0c"),
        Color.parseColor("#def5ff")
    )
    val colorsArcoiris = listOf(
        Color.parseColor("#e3102c"),
        Color.parseColor("#fe9e16"),
        Color.parseColor("#ffe803"),
        Color.parseColor("#83b441"),
        Color.parseColor("#10aabc"),
        Color.parseColor("#0679b4"),
        Color.parseColor("#6b317e"),
    )
    val colorsCaracol = listOf(
        Color.parseColor("#b7b835"),
        Color.parseColor("#f3d67d"),
        Color.parseColor("#c6964a"),
        Color.parseColor("#f0776f")
    )
    val colorsElefante = listOf(
        Color.parseColor("#53accc"),
        Color.parseColor("#c4a68d"),
        Color.parseColor("#e28270"),
        Color.parseColor("#e58596"),
        Color.parseColor("#6F291B"),
        Color.parseColor("#ecddc3")
    )
    val colorsGusano1 = listOf(
        Color.parseColor("#ffef01"),
        Color.parseColor("#ff9f10"),
        Color.parseColor("#f63516"),
        Color.parseColor("#289446"),
        Color.parseColor("#0db8d1"),
        Color.parseColor("#80358e"),
        Color.parseColor("#a84327")
    )
    val colorsGusano2 = listOf(
        Color.parseColor("#ffb3bb"),
        Color.parseColor("#ff7c77")
    )
    val colorsLuna = listOf(
        Color.parseColor("#fff102"),
        Color.parseColor("#feb503"),
        Color.parseColor("#ed350c")
    )
    val colorsMedusa = listOf(
        Color.parseColor("#0faec2"),
        Color.parseColor("#0a77a2"),
        Color.parseColor("#e45388"),
        Color.parseColor("#ba1d4f"),
        Color.parseColor("#d42727")
    )
    val colorsMono1 = listOf(
        Color.parseColor("#ffba90"),
        Color.parseColor("#8c5e3b")
    )
    val colorsMono2 = listOf(
        Color.parseColor("#8e4f1c"),
        Color.parseColor("#ffd153"),
        Color.parseColor("#e55590")
    )
    val colorsMono3 = listOf(
        Color.parseColor("#804003"),
        Color.parseColor("#358500"),
        Color.parseColor("#aa6d2a"),
        Color.parseColor("#ffdfa1"),
        Color.parseColor("#fba368"),
        Color.parseColor("#e87c60")
    )
    val colorsNube = listOf(
        Color.parseColor("#0eb6d0"),
        Color.parseColor("#c63a16")
    )
    val colorsPez = listOf(
        Color.parseColor("#ffa006"),
        Color.parseColor("#ff7104")
    )
    val colorsPulpo = listOf(
        Color.parseColor("#f05790"),
        Color.parseColor("#ba174c"),
        Color.parseColor("#fce103"),
        Color.parseColor("#cf3312")
    )
    val colorsSeta = listOf(
        Color.parseColor("#ff482c"),
        Color.parseColor("#fff5b3"),
        Color.parseColor("#94b913"),
        Color.parseColor("#7e9bb4"),
        Color.parseColor("#ff97b1")
    )
    val colorsVolcan = listOf(
        Color.parseColor("#b0afaf"),
        Color.parseColor("#ffea02"),
        Color.parseColor("#eb990b"),
        Color.parseColor("#8e4e22")
    )


    fun getColorsForCurrentImage(index: Int): List<Int> {
        return when (index) {
            0 -> colorsAbeja
            1 -> colorsArcoiris
            2 -> colorsCaracol
            3 -> colorsElefante
            4 -> colorsGusano1
            5 -> colorsGusano2
            6 -> colorsLuna
            7 -> colorsMedusa
            8 -> colorsMono1
            9 -> colorsMono2
            10 -> colorsMono3
            11 -> colorsNube
            12 -> colorsPez
            13 -> colorsPulpo
            14 -> colorsSeta
            15 -> colorsVolcan
            else -> listOf() // Retorna una lista vacía si el índice es inválido
        }
    }

}
