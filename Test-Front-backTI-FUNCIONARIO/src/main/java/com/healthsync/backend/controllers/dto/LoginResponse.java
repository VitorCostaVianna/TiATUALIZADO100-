package com.healthsync.backend.controllers.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
