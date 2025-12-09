package ee.ttu.tarkvaratehnika.simpleapp.data.repository.board;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
