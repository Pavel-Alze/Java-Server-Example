package test.connectdb.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "Resort")
@Table(name = "resorts")
public class Resort {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "resorts_id_seq", sequenceName = "resorts_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resorts_id_seq")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "pointlan")
    private double pointlan;

    @Column(name = "pointlon")
    private double pointlon;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resort resort)) return false;
        return getId() == resort.getId() && Double.compare(resort.pointlan, pointlan) == 0 && Double.compare(resort.pointlon, pointlon) == 0 && Objects.equals(title, resort.title) && Objects.equals(description, resort.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), title, description, pointlan, pointlon);
    }

    @Override
    public String toString() {
        return "Resort{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pointlan=" + pointlan +
                ", pointlon=" + pointlon +
                '}';
    }
}
