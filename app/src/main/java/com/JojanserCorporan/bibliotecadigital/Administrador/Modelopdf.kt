package com.JojanserCorporan.bibliotecadigital.Administrador

class Modelopdf {

    var uid: String  = ""
    var id: String = ""
    var titulo: String = ""
    var descripcion: String = ""
    var categoria : String = ""
    var url : String = ""
    var tiempo : Long = 0
    var contadorVistas : Long = 0
    var contadorDescargas : Long = 0

    constructor()

    constructor(
        id: String,
        uid: String,
        titulo: String,
        descripcion: String,
        url: String,
        categoria: String,
        tiempo: Long,
        contadorVistas: Long,
        contadorDescargas: Long
    ) {
        this.id = id
        this.uid = uid
        this.titulo = titulo
        this.descripcion = descripcion
        this.url = url
        this.categoria = categoria
        this.tiempo = tiempo
        this.contadorVistas = contadorVistas
        this.contadorDescargas = contadorDescargas
    }


}