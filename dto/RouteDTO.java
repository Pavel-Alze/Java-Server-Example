package test.connectdb.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RouteDTO {

    private int id;
    private String name;
    private int resort_id;
    private List<Object> route = new ArrayList<Object>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResort_id() {
        return resort_id;
    }

    public void setResort_id(int resort_id) {
        this.resort_id = resort_id;
    }

    public List<Object> getRoute() {
        return route;
    }

    public void setRoute(List<Object> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "RouteDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resort_id=" + resort_id +
                ", route=" + route +
                '}';
    }
}
