package ee.ttu.tarkvaratehnika.simpleapp.data.entity.board;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.User;
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
@Table(name = "Board_Configuration")
public class BoardConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @OneToOne
    private Board board;

    @OneToOne
    private User administrator;

    @OneToMany
    @JoinTable(
            name = "Board_Moderators",
            joinColumns = @JoinColumn(name = "moderator_id"),
            inverseJoinColumns = @JoinColumn(name = "board_configuration_id")
    )
    private Set<User> moderators;

    @OneToMany
    @JoinTable(
            name = "Board_Allowed_Users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "board_configuration_id")
    )
    private Set<User> allowedUsers;

    @OneToMany
    @JoinTable(
            name = "Board_Blocked_Users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "board_configuration_id")
    )
    private Set<User> blockedUsers;

}
