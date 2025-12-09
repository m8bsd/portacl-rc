package ee.ttu.tarkvaratehnika.simpleapp.data.repository;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
