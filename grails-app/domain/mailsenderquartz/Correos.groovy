package mailsenderquartz

import grails.gorm.annotation.Entity

@Entity
class Correos {

    int id
    String mail
    String alertas

    static mapping = {
        version false

        id column: "id_correos"
    }

    static constraints = {
    }
}
