package com.pdfreader.Pdf_Reader.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PdfReader {

    private String name;
    private String email;
    private Double openingBal;
    private Double closingBal;
}
