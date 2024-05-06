package com.skillstorm.DTO;

import lombok.Data;

@Data
public class Form1099Dto {
    private int taxReturnId;
    private int year;
    private double wages;
    private String payer;

}
