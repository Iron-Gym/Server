package com.kbm.Iron.Gym.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NewMemberStats {

    private int year;
    private int month;
    private Long count;

    public NewMemberStats(int year, int month, Long count) {
        this.year = year;
        this.month = month;
        this.count = count != null ? count : 0L;
    }
}
