package ru.geekbrains.gainanov.market.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderData {
    private String phone;
    private String address;
}
