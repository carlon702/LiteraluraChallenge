package com.carlosmarchal.literalura.model;

public enum Language {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    PORTUGUESE("pt"),
    ITALIAN("it"),
    GERMAN("de"),
    RUSSIAN("ru"),
    CHINESE("zh");


    private String languageCode;

    Language(String languageCode){
        this.languageCode = languageCode;
    }

    public static Language getNameByCode(String code) {
        for (Language language: Language.values()){
            if (language.languageCode.equalsIgnoreCase(code)){
                return language;
            }
        }
        throw new IllegalArgumentException("El codigo +"+ code+", es invalido.");
    }

    public static Language getName(String inputLanguage){
        for(Language language: Language.values()){
            if(language.name().equalsIgnoreCase(inputLanguage)){
                return language;
            }
        }
        throw new IllegalArgumentException("El lenguage "+ inputLanguage + " no es valido.");
    }



}
