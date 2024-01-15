package test.connectdb.models;

import jakarta.persistence.*;

@Entity(name="Point")
@Table(name="points")
public class Point {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "points_id_seq", sequenceName = "points_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "points_id_seq")
    private int id;

    @Column(name="pointlan")
    private double pointlan;

    @Column(name="pointlon")
    private double pointlon;

    @Column(name="route_id")
    private int route_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", pointlan=" + pointlan +
                ", pointlon=" + pointlon +
                ", route_id=" + route_id +
                '}';
    }
}
