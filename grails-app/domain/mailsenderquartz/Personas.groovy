package mailsenderquartz

import grails.gorm.annotation.Entity

@Entity
class Personas {

    int id
    String nombre
    String apellidos
    String alias

    static constraints = {
    }
}
