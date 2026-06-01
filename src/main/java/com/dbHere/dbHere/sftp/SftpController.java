package com.dbHere.dbHere.sftp;

import com.dbHere.dbHere.sftp.SftpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/sftp")
public class SftpController {

    private final SftpService sftpService;

    public SftpController(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    // =========================
    // 📤 Upload file
    // =========================
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // convert MultipartFile → temp file
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }

            String remotePath = "/home/ubuntu/sftp-upload/" + file.getOriginalFilename();

            sftpService.uploadFile(tempFile.getAbsolutePath(), remotePath);

            return ResponseEntity.ok("File uploaded successfully: " + remotePath);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    // =========================
    // 📥 Download file
    // =========================
    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(
            @RequestParam String remoteFile,
            @RequestParam String localPath) {

        try {
            sftpService.downloadFile(remoteFile, localPath);
            return ResponseEntity.ok("Downloaded successfully to: " + localPath);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Download failed: " + e.getMessage());
        }
    }
}