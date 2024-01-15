package test.connectdb.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "Login")
@Table(name = "logins")
public class Login {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "logins_id_seq", sequenceName = "logins_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logins_id_seq")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
    @Column(name = "salt")
    private String salt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Login login1)) return false;
        return getId() == login1.getId() && Objects.equals(getLogin(), login1.getLogin()) && Objects.equals(getPassword(), login1.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword());
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
