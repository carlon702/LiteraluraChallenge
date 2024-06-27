package com.carlosmarchal.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectConversor implements IObjectConversor{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> classType) {
        try{
            return objectMapper.readValue(json, classType);
        } catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }}
