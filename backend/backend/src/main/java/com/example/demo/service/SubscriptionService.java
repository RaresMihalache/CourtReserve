package com.example.demo.service;

import com.example.demo.dtos.SubscriptionDTO;
import com.example.demo.dtos.builders.CourtBuilder;
import com.example.demo.dtos.builders.SubscriptionBuilder;
import com.example.demo.model.Reservation;
import com.example.demo.model.Subscription;
import com.example.demo.repository.SubscriptionRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final EmailSenderService emailSenderService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, EmailSenderService emailSenderService) {
        this.subscriptionRepository = subscriptionRepository;
        this.emailSenderService = emailSenderService;
    }

    public SubscriptionDTO saveSubscription(Subscription subscription) {
        return SubscriptionBuilder.toSubscriptionDTO(subscriptionRepository.save(subscription));
    }

    public SubscriptionDTO getSubscription(Long id) {
        return SubscriptionBuilder.toSubscriptionDTO(subscriptionRepository.getSubscriptionById(id));
    }

    public List<SubscriptionDTO> findAll() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream().map(SubscriptionBuilder::toSubscriptionDTO)
                .collect(Collectors.toList());
    }

    public SubscriptionDTO updateSubscription(Subscription subscription) {

        Subscription subscriptionDB = subscriptionRepository.getSubscriptionById(subscription.getId());

        if (subscription.getReservations().size() > 0) {
            subscriptionDB.setReservations(subscription.getReservations());
        }

        return SubscriptionBuilder.toSubscriptionDTO(subscriptionRepository.save(subscriptionDB));
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public Float computeSubscriptionPrice(Long id) {

        Subscription subscription = subscriptionRepository.getSubscriptionById(id);
        float seasonPercent;
        float pricePerHour = 50;
        float totalPrice = 0;
        Set<String> spring = Set.of("JANUARY", "FEBRUARY", "MARCH", "APRIL");
        Set<String> winter = Set.of("SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");

        for (Reservation reservation : subscription.getReservations()) {

            if (spring.contains(reservation.getStartTime().getMonth().toString()) ||
                    winter.contains(reservation.getStartTime().getMonth().toString()))
                seasonPercent = 5;
            else
                seasonPercent = 10;

            if (reservation.getStartTime().getHour() > 20)
                pricePerHour = reservation.getCourt().getTariff().getNightTariff();

            totalPrice += pricePerHour * (reservation.getEndTime().getHour() - reservation.getStartTime().getHour()) + pricePerHour * (seasonPercent / 100);

        }

        generateReceipt(subscription, totalPrice);
        return totalPrice;

    }

    public void generateReceipt(Subscription subscription, Float totalPrice) {

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.open();

        try {
            Image img = Image.getInstance("https://photoresources.wtatennis.com/photo-resources/2019/08/15/dbb59626-9254-4426-915e-57397b6d6635/tennis-origins-e1444901660593.jpg?width=1200&height=630");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin()) / img.getWidth()) * 100;

            img.scalePercent(scaler);
            document.add(img);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Font bold = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD,BaseColor.BLACK);
        Paragraph title = new Paragraph("INVOICE", bold);
        title.setSpacingAfter(20);
        title.setPaddingTop(100);

        Paragraph paragraph = new Paragraph("Thank you for your latest subscription! Please check your" +
                " subscription details and contact us if any of the provided informations are wrong. Don't forget " +
                "about our discounts and make sure that you're partners will be there. You can cancel any reservation " +
                "with 24 hours before, otherwise additional money will be required.");
        paragraph.setSpacingAfter(20);
        paragraph.setPaddingTop(10);

        Paragraph paragraph1 = new Paragraph("Reservation details: ");
        paragraph1.setPaddingTop(10);
        paragraph1.setSpacingAfter(20);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        addRows(table, subscription.getReservations());
        table.setSpacingAfter(20);

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph price = new Paragraph("Total price -> " + totalPrice, font);
        price.setAlignment(Element.ALIGN_RIGHT);
        price.setPaddingTop(10);
        price.setSpacingAfter(20);

        try {
            document.add(title);
            document.add(paragraph);
            document.add(paragraph1);
            document.add(table);
            document.add(price);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();

        System.out.println(subscription.getReservations().get(0).getUsers().get(0).getEmail());
        emailSenderService.subscriptionEmail(subscription.getReservations().get(0).getUsers().get(0).getEmail(), outputStream, "Subscription receipt");
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Data", "Hour")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<Reservation> reservations) {
        for(Reservation reservation: reservations){
            table.addCell(reservation.getStartTime().getYear() + "-" + reservation.getStartTime().getMonth() + "-" + reservation.getStartTime().getDayOfMonth());
            table.addCell(String.valueOf(reservation.getStartTime().getHour()));
        }
    }

    public List<Subscription> getSubscriptionByUserId(String userId){
        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        List<Subscription> filteredList = subscriptionRepository.findAll();
        for(Subscription subscription : subscriptionList){
            if(subscription.getReservations().get(0).getUsers().get(0).getId() != Long.parseLong(userId)){
                filteredList.remove(subscription);
            }
        }

        return filteredList;
    }

}
