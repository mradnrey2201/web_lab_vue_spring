package springvuesite.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "points")
public class Point {
    private @Id @GeneratedValue Integer id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean hit;
    private String username;

    public Point(Double x, Double y, Double r, String username) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.username = username;
    }

    public Point() {}

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getHit() {
        return hit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void checkHit() {
        this.hit = (x <= 0 && y >= 0 && -x <= (r / 2) && y <= r) ||
                (x <= 0 && y <= 0 && (y + (2 * x)) >= -r) ||
                (x >= 0 && y <= 0 && ((x * x + y * y) <= ((r / 2) * (r / 2))));
    }
}
