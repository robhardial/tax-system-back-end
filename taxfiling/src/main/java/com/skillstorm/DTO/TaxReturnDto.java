package com.skillstorm.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TaxReturnDto {

    private int taxReturnId;
    private int personId;
    private int year;
    private String filingStatus;
    private List<Integer> formsW2s;
    private List<Integer> form1099s;

}
