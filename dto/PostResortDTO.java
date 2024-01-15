package test.connectdb.dto;

public class PostResortDTO {
    private String title;
    private String description;
    private double pointlan;
    private double pointlon;


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
    public String toString() {
        return "PostResortDTO{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pointlan=" + pointlan +
                ", pointlon=" + pointlon +
                '}';
    }
}
