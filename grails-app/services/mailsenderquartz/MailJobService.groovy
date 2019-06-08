package mailsenderquartz

import grails.plugins.mail.GrailsMailException
import grails.plugins.mail.MailService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.scheduling.annotation.Scheduled

import java.text.SimpleDateFormat

@Slf4j
@CompileStatic
class MailJobService {

    MailService mailService
    CorreosService correosService
    TiempoLimiteService tiempoLimiteService

    boolean lazyInit = false //<3>


    // //<4>
    //@Scheduled(fixedDelay = 30000L)
    //@Scheduled(fixedDelay = 3600000L, initialDelay = 500000L)
    @Scheduled(fixedDelay = 3600000L)
    void executeEveryTen() {
        try {


            for (Tiempolimite dato : tiempoLimiteService.findAll()) {
                tr_td = tr_td + " <tr>\n" +
                        "      <td>" + dato.empresa + " </td>\n" +
                        "      <td>" + dato.nombre + " " + dato.appaterno + "</td>\n" +
                        "      <td>" + dato.limiteTiempo.substring(11, dato.limiteTiempo.length() - 2) + "</td>\n" +
                        "      <td>" + dato.tiempoEnPlanta + "</td>\n" +
                        "    </tr>"
            }

            String message = html_header + body + table_init + tr_td + table_end + html_end

            List<String> correos=new ArrayList<>()
            for(Correos dato:correosService.findByTiempo()){
                correos.add(dato.mail)
            }

               if(tr_td.equalsIgnoreCase("")){
                   log.warn('No data')
               }else{
                   mailService.sendMail {
                       to correos.toArray()
                       from "sgaccesos@gmail.com"
                       subject "Tiempo limite en planta " + new Date()
                       html message
                   }
                   tr_td=""
               }

        } catch (GrailsMailException e) {
            log.warn('Message failed with exception', e)
        }

    }


    /*String email template*/

    String body = "<body width=\"100%\" style=\"margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #222222;\">\n" +
            "<center style=\"width: 100%; background-color: #222222;\">\n" +
            "   \n" +
            "    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #222222;\">\n" +
            "        <tr>\n" +
            "            <td>\n" +
            "                \n" +
            "    <div style=\"display: none; font-size: 1px; line-height: 1px; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\">\n" +
            "        Notificaciones de tiempo limite de personas en planta\n" +
            "    </div>\n" +
            "   \n" +
            "    <div style=\"display: none; font-size: 1px; line-height: 1px; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\">\n" +
            "        &zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;\n" +
            "    </div>\n" +
            "    <!-- Preview Text Spacing Hack : END -->\n" +
            "\n" +
            "    <!-- Email Body : BEGIN -->\n" +
            "    <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"600\" style=\"margin: auto;\" class=\"email-container\">\n" +
            "        <!-- Email Header : BEGIN -->\n" +
            "        <tr>\n" +
            "            <td style=\"padding: 20px 0; text-align: center\">\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <!-- Email Header : END -->\n" +
            "\n" +
            "        <!-- Hero Image, Flush : BEGIN -->\n" +
            "        <tr>\n" +
            "            <td style=\"background-color: #ffffff;\">\n" +
            "                <img src=\"http://138.68.43.209/kw.png\" width=\"600\" height=\"\" alt=\"alt_text\" border=\"0\" style=\"width: 100%; max-width: 600px; height: auto; background: #dddddd; font-family: sans-serif; font-size: 15px; line-height: 15px; color: #555555; margin: auto; display: block;\" class=\"g-img\">\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <!-- Hero Image, Flush : END -->\n" +
            "\n" +
            "        <!-- 1 Column Text + Button : BEGIN -->\n" +
            "        <tr>\n" +
            "            <td style=\"background-color: #ffffff;\">\n" +
            "                <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n" +
            "                    <tr>\n" +
            "                        <td style=\"padding: 20px; font-family: sans-serif; font-size: 15px; line-height: 20px; color: #555555;\">\n" +
            "                            <h1 style=\"margin: 0 0 10px; font-size: 25px; line-height: 30px; color: #333333; font-weight: normal;\">Personas fuera del tiempo limite en planta.</h1>\n" +
            "                            <ul style=\"padding: 0; margin: 0; list-style-type: disc;\">\n"

    String html_end = "                            </ul>\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                        <td style=\"padding: 0 20px 20px;\">\n" +
            "                            <!-- Button : BEGIN -->\n" +
            "                            <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"margin: auto;\">\n" +
            "                                <tr>\n" +
            "                                    <td class=\"button-td button-td-primary\" style=\"border-radius: 4px; background: #E9411D;\">\n" +
            "                                        <a class=\"button-a button-a-primary\" href=\"http://sga.xquadrone.com.mx/kw/\" style=\"background: #E9411D; border: 1px solid #E9411D; font-family: sans-serif; font-size: 15px; line-height: 15px; text-decoration: none; padding: 13px 17px; color: #ffffff; display: block; border-radius: 4px;\">Ir a sga</a>\n" +
            "                                    </td>\n" +
            "                                </tr>\n" +
            "                            </table>\n" +
            "                            <!-- Button : END -->\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "\n" +
            "                </table>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <!-- 1 Column Text + Button : END -->\n" +
            "\n" +
            "    <!-- Full Bleed Background Section : BEGIN -->\n" +
            "    <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" style=\"background-color: #192942;\">\n" +
            "        <tr>\n" +
            "            <td>\n" +
            "                <div align=\"center\" style=\"max-width: 600px; margin: auto;\" class=\"email-container\">\n" +
            "                    <!--[if mso]>\n" +
            "                    <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"600\" align=\"center\">\n" +
            "                        <tr>\n" +
            "                            <td>\n" +
            "                    <![endif]-->\n" +
            "                    <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n" +
            "                        <tr>\n" +
            "                            <td style=\"padding: 20px; text-align: left; font-family: sans-serif; font-size: 15px; line-height: 20px; color: #ffffff;\">\n" +
            "                                <p style=\"margin: 0;\"></p>\n" +
            "                            </td>\n" +
            "                        </tr>\n" +
            "                    </table>\n" +
            "                    <!--[if mso]>\n" +
            "                    </td>\n" +
            "                    </tr>\n" +
            "                    </table>\n" +
            "                    <![endif]-->\n" +
            "                </div>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "    </table>\n" +
            "    <!-- Full Bleed Background Section : END -->\n" +
            "\n" +
            "    <!--[if mso | IE]>\n" +
            "    </td>\n" +
            "    </tr>\n" +
            "    </table>\n" +
            "    <![endif]-->\n" +
            "</center>\n" +
            "</body>" +
            " </html>"
    String html_header = "<!DOCTYPE html>\n" +
            "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
            "    <meta name=\"format-detection\" content=\"telephone=no,address=no,email=no,date=no,url=no\">\n" +
            "\n" +
            "    <style>\n" +
            "\n" +
            "        html,\n" +
            "        body {\n" +
            "            margin: 0 !important;\n" +
            "            padding: 0 !important;\n" +
            "            height: 100% !important;\n" +
            "            width: 100% !important;\n" +
            "        }\n" +
            "\n" +
            "        * {\n" +
            "            -ms-text-size-adjust: 100%;\n" +
            "            -webkit-text-size-adjust: 100%;\n" +
            "        }\n" +
            "\n" +
            "        div[style*=\"margin: 16px 0\"] {\n" +
            "            margin: 0 !important;\n" +
            "        }\n" +
            "\n" +
            "        table,\n" +
            "        td {\n" +
            "            mso-table-lspace: 0pt !important;\n" +
            "            mso-table-rspace: 0pt !important;\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        table {\n" +
            "            border-spacing: 0 !important;\n" +
            "            border-collapse: collapse !important;\n" +
            "            table-layout: fixed !important;\n" +
            "            margin: 0 auto !important;\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        a {\n" +
            "            text-decoration: none;\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        img {\n" +
            "            -ms-interpolation-mode:bicubic;\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        a[x-apple-data-detectors],\n" +
            "        .unstyle-auto-detected-links a,\n" +
            "        .aBn {\n" +
            "            border-bottom: 0 !important;\n" +
            "            cursor: default !important;\n" +
            "            color: inherit !important;\n" +
            "            text-decoration: none !important;\n" +
            "            font-size: inherit !important;\n" +
            "            font-family: inherit !important;\n" +
            "            font-weight: inherit !important;\n" +
            "            line-height: inherit !important;\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        .im {\n" +
            "            color: inherit !important;\n" +
            "        }\n" +
            "\n" +
            "       \n" +
            "        .a6S {\n" +
            "            display: none !important;\n" +
            "            opacity: 0.01 !important;\n" +
            "        }\n" +
            "      \n" +
            "        img.g-img + div {\n" +
            "            display: none !important;\n" +
            "        }\n" +
            "\n" +
            "        \n" +
            "        @media only screen and (min-device-width: 320px) and (max-device-width: 374px) {\n" +
            "            u ~ div .email-container {\n" +
            "                min-width: 320px !important;\n" +
            "            }\n" +
            "        }\n" +
            "      \n" +
            "        @media only screen and (min-device-width: 375px) and (max-device-width: 413px) {\n" +
            "            u ~ div .email-container {\n" +
            "                min-width: 375px !important;\n" +
            "            }\n" +
            "        }\n" +
            "      \n" +
            "        @media only screen and (min-device-width: 414px) {\n" +
            "            u ~ div .email-container {\n" +
            "                min-width: 414px !important;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "    </style>\n" +
            "\n" +
            "    <xml>\n" +
            "        <o:OfficeDocumentSettings>\n" +
            "            <o:AllowPNG/>\n" +
            "            <o:PixelsPerInch></o:PixelsPerInch>\n" +
            "        </o:OfficeDocumentSettings>\n" +
            "    </xml>\n" +
            "   \n" +
            "    <style>\n" +
            "\n" +
            "   \n" +
            "        .button-td,\n" +
            "        .button-a {\n" +
            "            transition: all 100ms ease-in;\n" +
            "        }\n" +
            "        .button-td-primary:hover,\n" +
            "        .button-a-primary:hover {\n" +
            "            background: #555555 !important;\n" +
            "            border-color: #555555 !important;\n" +
            "        }\n" +
            "\n" +
            "        \n" +
            "        @media screen and (max-width: 600px) {\n" +
            "\n" +
            "            .email-container {\n" +
            "                width: 100% !important;\n" +
            "                margin: auto !important;\n" +
            "            }\n" +
            "\n" +
            "            \n" +
            "            .stack-column,\n" +
            "            .stack-column-center {\n" +
            "                display: block !important;\n" +
            "                width: 100% !important;\n" +
            "                max-width: 100% !important;\n" +
            "                direction: ltr !important;\n" +
            "            }\n" +
            "          \n" +
            "            .stack-column-center {\n" +
            "                text-align: center !important;\n" +
            "            }\n" +
            "\n" +
            "          \n" +
            "            .center-on-narrow {\n" +
            "                text-align: center !important;\n" +
            "                display: block !important;\n" +
            "                margin-left: auto !important;\n" +
            "                margin-right: auto !important;\n" +
            "                float: none !important;\n" +
            "            }\n" +
            "            table.center-on-narrow {\n" +
            "                display: inline-block !important;\n" +
            "            }\n" +
            "\n" +
            "            /* What it does: Adjust typography on small screens to improve readability */\n" +
            "            .email-container p {\n" +
            "                font-size: 17px !important;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "    </style>\n" +
            "    <!-- Progressive Enhancements : END -->\n" +
            "\n" +
            "</head>"

    String tr_td = ""
    String table_init = "<table role=\"presentation\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >" +
            "<thead>\n" +
            "    <tr>\n" +
            "      <th>Empresa</th>\n" +
            "      <th>Empleado</th>\n" +
            "      <th>Tiempo limite</th>\n" +
            "      <th>Tiempo en planta</th>\n" +
            "    </tr>\n" +
            "  </thead>" +
            " <tbody> "

    String table_end = "  </tbody>" +
            " </table>"


}
