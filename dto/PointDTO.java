package test.connectdb.dto;

public class PointDTO {

    private double pointlan;
    private double pointlon;

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
    public String toString() {
        return "PointDTO{" +
                ", pointlan=" + pointlan +
                ", pointlon=" + pointlon +
                '}';
    }
}
