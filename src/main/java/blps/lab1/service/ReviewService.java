package blps.lab1.service;

import blps.lab1.entity.Review;
import blps.lab1.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
        log.debug("{} review by {} on saved in DB", review.getCarModel(), review.getAuthorName());
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> findAllByApproved(boolean approved) {
        return reviewRepository.findAllByApproved(approved);
    }


    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> findAllByAuthorName(String authorName) {
        return reviewRepository.findAllByAuthorName(authorName);
    }
}
