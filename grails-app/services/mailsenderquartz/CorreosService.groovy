package mailsenderquartz

import grails.gorm.transactions.Transactional

@Transactional
class CorreosService {

    def serviceMethod() {

    }

    def List<Correos> findAll(){
        return Correos.list()
    }

    def List<Correos> findByTiempo(){
        return Correos.findAllByAlertasIlike("%tiempo_limite%")
    }
}
