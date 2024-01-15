package test.connectdb.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity(name = "Route")
@Table(name = "routes")
public class Route {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "routes_id_seq", sequenceName = "routes_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routes_id_seq")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "resort_id")
    private int resort_id;

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

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resort_id=" + resort_id +
                '}';
    }
}
