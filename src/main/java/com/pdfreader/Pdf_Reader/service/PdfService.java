package com.pdfreader.Pdf_Reader.service;


import com.pdfreader.Pdf_Reader.model.PdfReader;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;
import java.io.IOException;

@Service
public class PdfService {
    @Value("${tesseract.datapath}")
    private String tessDataPath;

    public PdfReader parsePdf(MultipartFile file) throws IOException, TesseractException {
        File tempFile = new File("temp.pdf");
        file.transferTo(tempFile);

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessDataPath);
        String text = tesseract.doOCR(tempFile);

        String name = extractField(text, "name", "[A-Za-z\\s]+");
        String email = extractField(text, "email", "[\\w.-]+@[\\w.-]+\\.[\\w]+");
        Double openingBal = extractDouble(text, "opening balance", "Opening Balance:\\s*([\\d,.]+)");
        Double closingBal = extractDouble(text, "closing balance", "Closing Balance:\\s*([\\d,.]+)");

        tempFile.delete();
        PdfReader pdfReader = new PdfReader();
        pdfReader.setName(name);
        pdfReader.setEmail(email);
        pdfReader.setOpeningBal(openingBal);
        pdfReader.setClosingBal(closingBal);
        return pdfReader;
    }

    private String extractField(String text, String fieldName, String regex) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.toLowerCase().contains(fieldName) || line.matches(".*" + regex + ".*")) {
                return line.replaceAll(".*" + regex, "$1").trim();
            }
        }
        return "Not found";
    }

    private Double extractDouble(String text, String fieldName, String regex) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.toLowerCase().contains(fieldName) && line.matches(regex)) {
                return Double.parseDouble(line.replaceAll(regex, "$1").replace(",", ""));
            }
        }
        return 0.0;
    }
}