package com.secudoc.document_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.secudoc.document_service.entity.Document;
import com.secudoc.document_service.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @PostMapping("/upload")
    public Document upload(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam("file") MultipartFile file) throws Exception {

        return service.upload(userId, file);
    }

    @GetMapping("/{id}")
    public String getDownloadUrl(
            @PathVariable Long id) throws Exception {

        return service.getDownloadUrl(id);
    }
}