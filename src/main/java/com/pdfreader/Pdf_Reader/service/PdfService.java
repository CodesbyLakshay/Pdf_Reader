package com.pdfreader.Pdf_Reader.service;

import com.pdfreader.Pdf_Reader.model.PdfReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfService {
    @Value("${tesseract.datapath}")
    private String tessDataPath;

    public PdfReader parsePdf(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("pdf_", ".pdf");
        try {
            file.transferTo(tempFile);
            PDDocument document = PDDocument.load(tempFile);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            String name = extractField(text, "Customer Name: ", "([A-Za-z\\s]+)");
            String email = extractField(text, "Email: ", "([\\w.-]+@[\\w.-]+\\.[\\w]+)");
            Double openingBal = extractDouble(text, "Opening Balance: ", "([\\d,.]+)");
            Double closingBal = extractDouble(text, "Closing Balance: ", "([\\d,.]+)");

            PdfReader pdfReader = new PdfReader();
            pdfReader.setName(name);
            pdfReader.setEmail(email);
            pdfReader.setOpeningBal(openingBal);
            pdfReader.setClosingBal(closingBal);
            return pdfReader;
        } finally {
            tempFile.delete();
        }
    }

    private String extractField(String text, String fieldName, String regex) {
        String[] lines = text.split("\n");
        Pattern pattern = Pattern.compile(fieldName + regex, Pattern.CASE_INSENSITIVE);
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line.trim());
            if (matcher.find()) {
                return matcher.group(1).trim();
            }
        }
        return "Not found";
    }

    private Double extractDouble(String text, String fieldName, String regex) {
        String[] lines = text.split("\n");
        Pattern pattern = Pattern.compile(fieldName + regex, Pattern.CASE_INSENSITIVE);
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line.trim());
            if (matcher.find()) {
                String value = matcher.group(1).replace(",", "");
                return Double.parseDouble(value);
            }
        }
        return 0.0;
    }
}