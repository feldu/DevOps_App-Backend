package blps.lab1.repository;

import blps.lab1.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByAuthorName(String name);
}
