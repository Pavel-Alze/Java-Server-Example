package test.connectdb.dto;

import java.util.ArrayList;
import java.util.List;

public class ResRouteDTO {
    private String accessToken;
    private String refreshToken;
    private int id;
    private String name;
    private int resort_id;
    private List<Object> route = new ArrayList<>();

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

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
        return "ResRouteDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", resort_id=" + resort_id +
                ", route=" + route +
                '}';
    }
}
