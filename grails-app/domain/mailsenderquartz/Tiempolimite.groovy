package mailsenderquartz


class Tiempolimite {

    String id
    String empresa
    String nombre
    String appaterno
    String apmaterno
    String limiteTiempo
    String tiempoEnPlanta

    static mapping = {
        table 'tiempolimite'
        version false
        empresa column: "empresa"
        nombre column: "nombre"
        appaterno column: "appaterno"
        apmaterno column: "apmaterno"
        limiteTiempo column: "limite_tiempo"
        tiempoEnPlanta column: "tiempo_en_planta"
    }

    static constraints = {
    }
}
