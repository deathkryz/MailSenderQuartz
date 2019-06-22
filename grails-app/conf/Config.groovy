grails {
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "sgaccesos@gmail.com"
        password = "Xqd.2234"
        props = ["mail.smtp.auth":"true",
                 "mail.smtp.socketFactory.port":"465",
                 "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                 "mail.smtp.socketFactory.fallback":"false"]
    }
}