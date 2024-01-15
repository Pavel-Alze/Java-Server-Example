package test.connectdb.dto;

import java.util.ArrayList;
import java.util.List;

public class PostRouteDTO {
    private String name;
    private int resort_id;
    private List<PointDTO> route = new ArrayList<>();

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

    public List<PointDTO> getRoute() {
        return route;
    }

    public void setRoute(List<PointDTO> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "PostRouteDTO{" +
                "name='" + name + '\'' +
                ", resort_id=" + resort_id +
                ", route=" + route +
                '}';
    }
}
