package com.nexai.task4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Route {
    private int id;
    private String cityFrom;
    private String cityTo;
    private int price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (id != route.id) return false;
        if (price != route.price) return false;
        if (cityFrom != null ? !cityFrom.equals(route.cityFrom) : route.cityFrom != null) return false;
        return cityTo != null ? cityTo.equals(route.cityTo) : route.cityTo == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cityFrom != null ? cityFrom.hashCode() : 0);
        result = 31 * result + (cityTo != null ? cityTo.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }
}
