package ee.ttu.tarkvaratehnika.simpleapp.data.repository.board;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.BoardConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardConfigurationRepository extends JpaRepository<BoardConfiguration, Long> {
}
