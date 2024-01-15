package test.connectdb.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity(name = "BlackList")
@Table(name = "blacklist_resorts")
public class Blacklist {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "blacklist_resorts_id_seq", sequenceName = "blacklist_resorts_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blacklist_resorts_id_seq")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "resort_id")
    private int resort_id;

    @Column(name = "user_id")
    private int user_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getResort_id() {
        return resort_id;
    }

    public void setResort_id(int resort_id) {
        this.resort_id = resort_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
