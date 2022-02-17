package blps.lab1.repository;

import blps.lab1.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByAuthorName(String name);

    List<Review> findAllByApproved(boolean approved);

    List<Review> findAllByAuthorName(String authorName);
}
