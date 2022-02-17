package blps.lab1.controller;

import blps.lab1.entity.Review;
import blps.lab1.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody Review review) {
        try {
            reviewService.saveReview(review);
            return new ResponseEntity<>("Отзыв сохранен", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Непредвиденная ошибка", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/changeApproval/{id}")
    public ResponseEntity<String> changeApproval(@PathVariable(name = "id") Long id,
                                                 @RequestBody Map<String, Boolean> payload) {
        try {
            Boolean approved = payload.get("approved");
            if (approved == null)
                return new ResponseEntity<>("Не указано значение approved", HttpStatus.BAD_REQUEST);
            Review review = reviewService.findReviewById(id);
            if (review == null)
                return new ResponseEntity<>("Отзыв с указанным id не существует", HttpStatus.BAD_REQUEST);
            if (review.isApproved() == approved)
                return new ResponseEntity<>("Отзыв с указанным id уже имеет значение " + approved, HttpStatus.BAD_REQUEST);
            review.setApproved(approved);
            reviewService.saveReview(review);
            return new ResponseEntity<>("Подтверждение отзыва изменено", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Непредвиденная ошибка", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> addReview(@PathVariable(name = "id") Long id) {
        try {
            reviewService.deleteReviewById(id);
            return new ResponseEntity<>("Отзыв удален", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Непредвиденная ошибка", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/approved/{approved}")
    public ResponseEntity<List<Review>> getReviewByApproval(@PathVariable Boolean approved) {
        try {
            List<Review> reviews = reviewService.findAllByApproved(approved);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/authorName/{authorName}")
    public ResponseEntity<List<Review>> getReviewByAuthorName(@PathVariable String authorName) {
        try {
            List<Review> reviews = reviewService.findAllByAuthorName(authorName);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
