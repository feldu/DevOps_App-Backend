package blps.lab1.service;

import blps.lab1.entity.Review;
import blps.lab1.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public boolean saveReview(Review review) {
        reviewRepository.save(review);
        log.debug("{} review by {} on saved in DB", review.getCarModel(), review.getAuthorName());
        return true;
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

}
