package test.connectdb.dto;

import test.connectdb.models.Point;

import java.util.ArrayList;
import java.util.List;

public class ResResortDTO {
    private String accessToken;
    private String refreshToken;
    private int id;
    private String title;
    private String description;
    private double pointlan;
    private double pointlon;
    private List<RouteDTO> routes = new ArrayList<>();

    public List<RouteDTO> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDTO> routes) {
        this.routes = routes;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPointlan() {
        return pointlan;
    }

    public void setPointlan(double pointlan) {
        this.pointlan = pointlan;
    }

    public double getPointlon() {
        return pointlon;
    }

    public void setPointlon(double pointlon) {
        this.pointlon = pointlon;
    }

    @Override
    public String toString() {
        return "ResResortDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pointlan=" + pointlan +
                ", pointlon=" + pointlon +
                ", routes=" + routes +
                '}';
    }
}
