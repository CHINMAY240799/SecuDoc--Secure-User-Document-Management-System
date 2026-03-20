package com.secudoc.document_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.secudoc.document_service.entity.Document;
import com.secudoc.document_service.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository repository;
    private final MinioStorageService storageService;

    public Document upload(Long userId, MultipartFile file) throws Exception {

        String key = storageService.uploadFile(userId, file);

        Document doc = new Document();

        doc.setOwnerId(userId);
        doc.setFileName(file.getOriginalFilename());
        doc.setFileType(file.getContentType());
        doc.setFileSize(file.getSize());
        doc.setS3Key(key);

        return repository.save(doc);
    }

    public String getDownloadUrl(Long documentId) throws Exception {

        Document doc = repository.findById(documentId)
                .orElseThrow();

        return storageService.generateDownloadUrl(doc.getS3Key());
    }
}