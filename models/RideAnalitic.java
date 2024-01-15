package test.connectdb.models;

import jakarta.persistence.*;

@Entity(name = "RideAnaliticRepository")
@Table(name = "ride_analitics")
public class RideAnalitic {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "ride_analitics_id_seq", sequenceName = "ride_analitics_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ride_analitics_id_seq")
    private int id;

    @Column(name = "time")
    private int time;
    @Column(name = "difficulty")
    private String difficulty;
    @Column(name = "route_id")
    private int route_id;
    @Column(name = "user_id")
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
                ", time=" + time +
                ", difficulty='" + difficulty + '\'' +
                ", route_id=" + route_id +
                ", user_id=" + user_id +
                '}';
    }
}
