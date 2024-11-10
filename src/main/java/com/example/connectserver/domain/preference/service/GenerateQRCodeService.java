package com.example.connectserver.domain.preference.service;

import com.example.connectserver.domain.preference.dto.PreferenceResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GenerateQRCodeService {

    public byte[] execute(Long userId, String username, List<PreferenceResponse> preferences) throws WriterException, IOException {
        StringBuilder qrContent = new StringBuilder("User: " + username + " (ID: " + userId + ")\n");
        for (PreferenceResponse pref : preferences) {
            qrContent.append("Q: ").append(pref.getQuestion()).append("\n")
                .append("A: ").append(pref.getAnswer()).append("\n");
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent.toString(), BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
