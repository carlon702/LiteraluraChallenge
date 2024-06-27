package com.carlosmarchal.literalura.dto;

import com.carlosmarchal.literalura.model.Language;

public record BookDTO(
        Long id,
        String title,
        String author,
        Language language,
        Integer downloads
) {
}
