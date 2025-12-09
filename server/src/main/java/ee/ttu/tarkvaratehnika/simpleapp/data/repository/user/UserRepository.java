package ee.ttu.tarkvaratehnika.simpleapp.data.repository.user;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
