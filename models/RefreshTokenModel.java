package test.connectdb.models;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenModel {


    private Date data;

    public RefreshTokenModel(long data) {
        this.data = new Date(data);
    }

    public long getData() {
        return data.getTime();
    }

    public void setData(long data) {
        this.data = new Date(data);
    }

}
