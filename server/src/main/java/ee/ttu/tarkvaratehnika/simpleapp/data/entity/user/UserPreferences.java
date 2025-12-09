package ee.ttu.tarkvaratehnika.simpleapp.data.entity.user;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.Board;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.thread.Thread;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_Preferences")
public class UserPreferences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    @OneToMany
    @JoinTable(
            name = "User_Followed_Threads",
            joinColumns = @JoinColumn(name = "thread_id"),
            inverseJoinColumns = @JoinColumn(name = "user_preferences_id")
    )
    private Set<Thread> followedThreads;

    @OneToMany
    @JoinTable(
            name = "User_Followed_Boards",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "user_preferences_id")
    )
    private Set<Board> followedBoards;
}
