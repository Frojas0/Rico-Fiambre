package com.ricofiambre.ecomerce.controladores;

import com.ricofiambre.ecomerce.dtos.CarritoCompraDTO;
import com.ricofiambre.ecomerce.modelos.Client;
import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.servicios.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
@RestController
public class TicketControlador {
    @Autowired
    private EmailService emailService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ProductoPesoServicio productoPesoServicio;
    @Autowired
    private ProductoUniServicio productoUniServicio;
    @Autowired
    private OrdenServicio ordenServicio;
    @Autowired
    private OrdenProductoUniServicio ordenProductoUniServicio;
    @Autowired
    private OrdenProductoPesoServicio ordenProductoPesoServicio;

//    @GetMapping("/api/enviar-mail")
//    public ResponseEntity<Object> ExportingToPDF(HttpServletResponse response){
//
//        emailService.sendConfirmationEmail("gutin97agustin.martinez@gmail.com");
//
//        return new ResponseEntity<>("PDF enviado!", HttpStatus.CREATED);
//    }
//    @GetMapping("/api/enviar-mail")
//    public ResponseEntity<Object> enviarPDF(Authentication authentication){
////    ResponseEntity<Object> exportingToPDF(HttpServletResponse response)
//        Cliente cliente = clienteServicio.findByEmail(authentication.getName());
//        try {
//            // Generar el archivo PDF
//            PDDocument document = new PDDocument();
//            PDPage page = new PDPage(PDRectangle.A4);
//            document.addPage(page);
//
//            // Añadir contenido al PDF (opcional)
//            // ...
//
//            // Guardar el PDF en un ByteArrayOutputStream
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            document.save(outputStream);
//            document.close();
//
//            // Enviar el correo electrónico con el PDF adjunto
//            sendConfirmationEmailWithAttachment(outputStream.toByteArray());
//
//            return new ResponseEntity<>("PDF enviado!", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    private void sendConfirmationEmailWithAttachment(byte[] pdfBytes) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo("gutin97agustin.martinez@gmail.com");
//        helper.setSubject("Ticket compra - RICO FIAMBRE");
//        helper.setText("Adjunto encontrarás el TICKET de compra.");
//
//        // Adjuntar el PDF al correo electrónico
//        helper.addAttachment("ticket.pdf", new ByteArrayResource(pdfBytes));
//
//        mailSender.send(message);
//    }

}
