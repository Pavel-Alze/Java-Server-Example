package test.connectdb.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AccessTokenModel {

    private int user;
    private Date date;

    public AccessTokenModel(int user, long date) {
        this.user = user;
        this.date = new Date(date);
    }

    public long getDate() {
        return date.getTime();
    }

    public void setDate(long date) {
        this.date = new Date(date);
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}

