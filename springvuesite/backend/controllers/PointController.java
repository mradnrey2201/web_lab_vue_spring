package springvuesite.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springvuesite.backend.models.Point;

@RestController
@RequestMapping("/api/point")
public class PointController {

    private final PointRepository pointRepository;

    public PointController(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @PostMapping("/add_point")
    ResponseEntity<?> addPoint(@RequestBody Point point) {
        if (point.getX() < -5 || point.getX() > 5 || point.getY() < -5 || point.getY() > 3 || point.getR() < 0 || point.getR() > 5) {
            return new ResponseEntity<>("Not good data", HttpStatus.CONFLICT);
        }
        Point newPoint = new Point();
        newPoint.setX(point.getX());
        newPoint.setY(point.getY());
        newPoint.setR(point.getR());
        newPoint.setUsername(point.getUsername());
        newPoint.checkHit();
        return new ResponseEntity<>(pointRepository.save(newPoint), HttpStatus.OK);
    }

    @RequestMapping("/get_points")
    ResponseEntity<?> getPoints(@RequestParam(value = "username") String username) {
        return new ResponseEntity<>(pointRepository.findAllByUsername(username), HttpStatus.OK);
    }

    @RequestMapping("/clear_points")
    ResponseEntity<?> clearPoints(@RequestParam(value = "username") String username) {
        pointRepository.removeAllByUsername(username);
        return ResponseEntity.ok("Cleared");
    }
}
