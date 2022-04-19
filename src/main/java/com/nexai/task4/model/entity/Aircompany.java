package com.nexai.task4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Aircompany {
    private int id;
    private String name;
    private List<Airplane> airplaneList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aircompany that = (Aircompany) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return airplaneList != null ? airplaneList.equals(that.airplaneList) : that.airplaneList == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (airplaneList != null ? airplaneList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Aircompany{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", airplaneList=").append(airplaneList);
        sb.append('}');
        return sb.toString();
    }
}
