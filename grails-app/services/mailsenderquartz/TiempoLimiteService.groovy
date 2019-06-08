package mailsenderquartz

import grails.gorm.transactions.Transactional

@Transactional
class TiempoLimiteService {

    def serviceMethod() {

    }

    def List<Tiempolimite> findAll(){
        return Tiempolimite.list()
    }

}
