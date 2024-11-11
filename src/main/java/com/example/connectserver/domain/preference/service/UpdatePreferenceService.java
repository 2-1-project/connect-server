package com.example.connectserver.domain.preference.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.example.connectserver.domain.preference.dto.PreferenceItem;
import com.example.connectserver.domain.preference.dto.QRCodeResponse;
import com.example.connectserver.domain.preference.dto.UpdatePreferenceRequest;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UpdatePreferenceService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public QRCodeResponse execute(UpdatePreferenceRequest request) throws WriterException, IOException {
        String existingQrCodeUrl = request.getExistingQrCodeUrl();

        if (existingQrCodeUrl != null) {
            String existingFileName = existingQrCodeUrl.substring(existingQrCodeUrl.lastIndexOf("/") + 1);
            amazonS3.deleteObject(bucketName, "qr-codes/" + existingFileName);
        }

        StringBuilder qrContent = new StringBuilder("Name: " + request.getName() + "\nIntro: " + request.getIntro() + "\n");
        for (PreferenceItem item : request.getList()) { // 외부 PreferenceItem 사용
            qrContent.append("Q: ").append(item.getQuestion()).append("\n")
                .append("A: ").append(item.getAnswer()).append("\n");
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent.toString(), com.google.zxing.BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrImageBytes = pngOutputStream.toByteArray();

        String fileName = "qr-codes/" + UUID.randomUUID() + ".png";

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/png");
        metadata.setContentLength(qrImageBytes.length);

        amazonS3.putObject(bucketName, fileName, new ByteArrayInputStream(qrImageBytes), metadata);
        amazonS3.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);

        String newFileUrl = amazonS3.getUrl(bucketName, fileName).toString();

        return new QRCodeResponse(newFileUrl, qrImageBytes);
    }
}