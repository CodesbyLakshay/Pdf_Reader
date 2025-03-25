package com.pdfreader.Pdf_Reader.controller;

import com.pdfreader.Pdf_Reader.model.PdfReader;
import com.pdfreader.Pdf_Reader.service.PdfService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    public PdfReader parsePdf(@RequestParam("file")MultipartFile file)  {

        return pdfService.parsePdf();
    }
}
