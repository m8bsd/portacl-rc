package ee.ttu.tarkvaratehnika.simpleapp.data.repository.board.thread;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.thread.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
