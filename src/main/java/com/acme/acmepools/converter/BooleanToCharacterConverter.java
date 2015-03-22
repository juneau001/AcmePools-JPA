/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Juneau
 */
@Converter
public class BooleanToCharacterConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean x) {
        
        if(x){
            return "Y";
        } else {
            return "N";
        }
    }

    @Override
    public Boolean convertToEntityAttribute(String y) {
        String val = (String) y;
        if(val.equals("Y")){
            return true;
        } else {
            return false;
        }
    }
    
}
