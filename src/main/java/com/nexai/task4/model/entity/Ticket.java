package com.nexai.task4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    private int id;
    private int numberOfSeats;
    private int orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (numberOfSeats != ticket.numberOfSeats) return false;
        return orderId == ticket.orderId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + numberOfSeats;
        result = 31 * result + orderId;
        return result;
    }
}
