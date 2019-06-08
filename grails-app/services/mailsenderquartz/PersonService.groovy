package mailsenderquartz

import grails.gorm.transactions.Transactional

@Transactional
class PersonService {

    def serviceMethod() {

    }

    def List<Personas> findAll(){
        return Personas.list()
    }

}
