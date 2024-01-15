package test.connectdb.dto;

import jakarta.persistence.Column;

public class AnaliticDTO {

    private int time;
    private String difficulty;
    private int route_id;
    private int user_id;


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "RideAnaliticRepository{" +
                " time=" + time +
                ", difficulty='" + difficulty + '\'' +
                ", route_id=" + route_id +
                ", user_id=" + user_id +
                '}';
    }
}
