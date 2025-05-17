package com.example.neo_backend.domain.image.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.neo_backend.global.common.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile -> File 변환 후 S3 업로드, URL 반환
    public String uploadFiles(MultipartFile multipartFile, String dirName) {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new RuntimeException(
                        ErrorStatus.VALIDATION_ERROR.getMessageOrDefault("MultipartFile 변환 실패")));

        return upload(uploadFile, dirName);
    }

    // 실제 S3 업로드
    public String upload(File uploadFile, String filePath) {
        try {
            String fileName = filePath + "/" + UUID.randomUUID() + "-" + uploadFile.getName();
            String uploadImageUrl = putS3(uploadFile, fileName);

            // 업로드 후 로컬 파일 삭제 (필요시)
            if (uploadFile.delete()) {
                System.out.println("임시 파일 삭제 성공");
            } else {
                System.out.println("임시 파일 삭제 실패");
            }

            return uploadImageUrl;
        } catch (Exception e) {
            throw new RuntimeException(
                    ErrorStatus._INTERNAL_SERVER_ERROR.getMessageOrDefault("파일 업로드 중 오류 발생"), e);
        }
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // MultipartFile -> File 변환
    private Optional<File> convert(MultipartFile file) {
        try {
            // 임시파일을 OS temp폴더에 고유한 이름으로 생성
            String originalFilename = file.getOriginalFilename();
            String suffix = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            File convertFile = File.createTempFile("tempfile-", UUID.randomUUID() + suffix);
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
