# Pdf_Reader

A RESTful API built with Java and Spring Boot to parse CASA (Current Account and Savings Account) statement PDFs, extracting key fields such as customer name, email, opening balance, and closing balance. This project was developed as part of an academic or personal assignment, with the goal of integrating an LLM API for parsing, though regex-based parsing was ultimately implemented due to time constraints from internal examinations.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [API Endpoint](#api-endpoint)
- [Sample PDF](#sample-pdf)
- [Limitations and Future Improvements](#limitations-and-future-improvements)
- [Contributing](#contributing)

## Overview
This project implements a Spring Boot-based API that processes uploaded CASA statement PDFs and returns structured data in JSON format. It uses Apache PDFBox for text extraction and custom regular expressions (regex) to parse specific fields. The original requirement included integration with an LLM API (e.g., OpenAI or Claude) for advanced parsing, but due to my internal exam schedule, I opted for a regex-based solution that reliably handles the provided sample PDF.

The API is designed to be deployed on a public server and accessed via tools like cURL or Postman, fulfilling the core requirements of the task.

## Features
- **PDF Upload**: Accepts a PDF file via a POST request.
- **Field Extraction**: Parses customer name, email, opening balance, and closing balance from the PDF.
- **JSON Response**: Returns extracted data in a structured format.
- **Local Testing**: Fully functional on a local environment with the provided sample PDF.

## Prerequisites
- **Java**: Version 17 or higher.
- **Maven**: For dependency management and building the project.
- **IDE**: IntelliJ IDEA recommended (though optional).
- **Git**: To clone the repository.
- **cURL/Postman**: For testing the API.

## Setup Instructions
1. **Clone the Repository**:
   git clone https://github.com/CodesbyLakshay/Pdf_Reader.git
   cd Pdf_Reader

2. **Install Dependencies**:
- Ensure Maven is installed:
  ```
  mvn --version
  ```
- Install project dependencies:
  ```
  mvn clean install
  ```

3. **Run the Application**:
- Start the Spring Boot server locally:
  ```
  mvn spring-boot:run
  ```
- The API will be available at `http://localhost:8080`.

## Usage
### Testing Locally
1. **Prepare a PDF**:
- Use the provided sample PDF (`sample/statement.pdf`) or a similar CASA statement PDF with fields like `Customer Name:`, `Email:`, `Opening Balance:`, and `Closing Balance:`.

2. **Test with cURL**:
   curl -X POST -F "file=
@sample
/statement.pdf" http://localhost:8080/api/pdf

3. **Test with Postman**:
- Open Postman.
- Set request type to `POST`.
- Enter URL: `http://localhost:8080/api/pdf`.
- Go to the `Body` tab, select `form-data`.
- Add a key `file`, set type to `File`, and upload `sample/statement.pdf`.
- Click `Send`.

4. **Expected Response**:
   {
     "name": "John Doe",
     "email": "email@gmial.com",
     "openingBal": 1500.75,
     "closingBal": 2000.25
   }

### Deploying to a Public Server
- Build the JAR file:
  mvn clean package
- Deploy the generated `target/Pdf_Reader-0.0.1-SNAPSHOT.jar` to a server (e.g., AWS EC2, Heroku).
- Run the JAR:
  java -jar Pdf_Reader-0.0.1-SNAPSHOT.jar
- Update the API URL in your requests to the public server’s address (e.g., `http://your-server.com:8080/api/pdf`).

## API Endpoint
- **Method**: `POST`
- **URL**: `/api/pdf`
- **Request Body**: 
- Key: `file`
- Value: PDF file (multipart/form-data)
- **Response**: JSON object with parsed fields.
- **Example**:
  curl -X POST -F "file=
@statement
.pdf" http://localhost:8080/api/pdf


## Implementation Notes
- **Development Context**: This project was completed during my internal examination period, which limited my ability to fully implement all planned features.
- **PDF Parsing**: Apache PDFBox extracts text from the PDF, which is then processed using regex to identify fields.
- **Regex Approach**: Due to time constraints, I used regex instead of an LLM API. The patterns were crafted with AI assistance to match the sample PDF format.
- **Challenges Faced**:
  - Initial `FileNotFoundException` with PDFBox, resolved by using temporary files.
  - Git push rejections due to secret scanning, addressed by removing sensitive data from commits.
- **Testing**: The API works reliably with the provided `statement.pdf` on my local system .

## Limitations and Future Improvements
- **LLM Absence**: I couldn’t integrate an LLM API (e.g., OpenAI or Claude) due to my exam schedule. Regex was used instead, limiting flexibility for varied PDF formats.
- **Single PDF Format**: The current regex is tailored to the sample PDF structure. Different bank statement layouts may require pattern adjustments.
- **Deployment**: Not tested on a public server due to time constraints, though local execution is confirmed.
- **Future Plans**:
  - Integrate an LLM API for adaptive parsing.
  - Add support for PDFs from disk locations.
  - Enhance error handling for malformed or password-protected PDFs.

## Contributing
Contributions are welcome! Please:
1. Fork the repository.
2. Create a feature branch:
   git checkout -b feature/improvement
3. Commit changes:
   git commit -m "Add improvement"
4. Push to the branch:
   git push origin feature/improvement
5. Open a pull request.

If you encounter issues with the sample PDF or other files, please open an issue on GitHub.

