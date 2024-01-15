package test.connectdb.models;

import jakarta.persistence.*;

@Entity(name = "RideCount")
@Table(name = "ride_count")
public class RideCount {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "ride_count_id_seq", sequenceName = "ride_count_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ride_count_id_seq")
    private int id;
    @Column(name = "count")
    private int count;
    @Column(name = "route_id")
    private int route_id;
    @Column(name = "user_id")
    private int user_id;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
        return "RideCount{" +
                ", count=" + count +
                ", route_id=" + route_id +
                ", user_id=" + user_id +
                '}';
    }
}
