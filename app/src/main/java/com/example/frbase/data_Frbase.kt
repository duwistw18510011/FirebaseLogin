package com.example.frbase

class data_Frbase {
    var nama: String? = null
    var alamat: String? = null
    var noHp: String? = null
    var key: String? = null

    constructor()

    constructor(nama: String?, alamat: String?, noHp: String?){
        this.nama = nama
        this.alamat = alamat
        this.noHp = noHp
    }
}