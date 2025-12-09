package ee.ttu.tarkvaratehnika.simpleapp.data.service;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.Board;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.thread.Thread;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.Person;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.User;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.UserPreferences;
import ee.ttu.tarkvaratehnika.simpleapp.data.repository.board.BoardRepository;
import ee.ttu.tarkvaratehnika.simpleapp.data.repository.board.thread.ThreadRepository;
import ee.ttu.tarkvaratehnika.simpleapp.data.repository.user.PersonRepository;
import ee.ttu.tarkvaratehnika.simpleapp.data.repository.user.UserPreferencesRepository;
import ee.ttu.tarkvaratehnika.simpleapp.data.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private BoardRepository boardRepository;

    public User createUser(String userName, String password, String firstName,
                           String lastName, String email) {
        // Create user
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setPosts(new HashSet<>());

        // Create person
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setUser(user);

        // Create user preferences
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setFollowedBoards(new HashSet<>());
        userPreferences.setFollowedThreads(new HashSet<>());
        userPreferences.setUser(user);

        // Make connections for user
        user.setPerson(person);
        user.setUserPreferences(userPreferences);

        // Save and return user
        return userRepository.save(user);
    }

    public UserPreferences editUserPreferences(Long userId, List<Long> followedThreadIds, List<Long> followedBoardIds) {
        // Check if user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // Set required objects
            UserPreferences userPreferences = userOptional.get().getUserPreferences();
            List<Thread> followedThreads = threadRepository.findAllById(followedThreadIds);
            List<Board> followedBoards = boardRepository.findAllById(followedBoardIds);

            // Make changes
            userPreferences.setFollowedThreads(new HashSet<>(followedThreads));
            userPreferences.setFollowedBoards(new HashSet<>(followedBoards));

            // Save and return user preferences
            return userPreferencesRepository.save(userPreferences);
        }
        return null;
    }

    public User editUser(Long userId, String userName, String password) {
        // Check if user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // Set required objects
            User user = userOptional.get();

            // Make changes
            user.setUserName(userName);
            user.setPassword(password);

            // Save and return user
            return userRepository.save(user);
        }
        return null;
    }

    public Person editPerson(Long userId, String firstName, String lastName, String email) {
        // Check if user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // Set required Objects
            Person person = userOptional.get().getPerson();

            // Make changes
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setEmail(email);

            // Save and return person
            return personRepository.save(person);
        }
        return null;
    }
}
