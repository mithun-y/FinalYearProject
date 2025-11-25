package com.personal.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class KeyProvider {

    private final byte[] masterKey;

    public KeyProvider(@Value("${security.aes.master-key}") String base64Key) {
        this.masterKey = Base64.getDecoder().decode(base64Key);
    }

    public byte[] getMasterKey() {
        return masterKey;
    }
}