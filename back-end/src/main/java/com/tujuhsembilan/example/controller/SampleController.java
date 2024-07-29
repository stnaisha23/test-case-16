package com.tujuhsembilan.example.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.example.exception.MultipleException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/sample")
public class SampleController {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class SampleRequestBody {
    @Size(max = 10)
    private String input;

    @NotBlank
    private String mustHaveSomething;
  }

  @Value("${log.file.path}")
  private String logFilePath;

  private static final String LOG_SEPARATOR = "===========================================================================================================================================";

  @PostMapping("/error")
  public ResponseEntity<String> logError(@RequestBody Map<String, Object> errorDetails) {
    try (FileWriter writer = new FileWriter(logFilePath, true)) {
        writer.write(LocalDateTime.now() + " - Error: " 
            + (errorDetails.get("error") != null ? errorDetails.get("error") : "Tidak ada pesan error") 
            + System.lineSeparator());
        writer.write("Error Info: " 
            + (errorDetails.get("errorInfo") != null ? errorDetails.get("errorInfo") : "Tidak ada info error") 
            + System.lineSeparator());
        
        writer.write(LOG_SEPARATOR + System.lineSeparator());
    } catch (IOException e) {
        return new ResponseEntity<>("Failed to write log", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>("Log saved successfully", HttpStatus.OK);
  }

  @PostMapping("/post")
  public ResponseEntity<?> postSomething(@Valid @RequestBody SampleRequestBody body) {
    throw new EntityNotFoundException("Test");
  }

  @GetMapping("/multiple-exception")
  public ResponseEntity<?> multipleException() {
    throw new MultipleException(new EntityNotFoundException("Test1"), new IndexOutOfBoundsException("Test2"));
  }

}
