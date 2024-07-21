package com.kbm.Iron.Gym.dto;

import com.kbm.Iron.Gym.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PaymentStatusDTO {
    private List<Client> paidClients;
    private List<Client> unpaidClient;

}
