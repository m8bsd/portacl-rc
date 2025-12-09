package ee.ttu.tarkvaratehnika.simpleapp.data.entity.user;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.thread.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "creation_time_stamp")
    private Date creationTimeStamp;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Person person;

    @OneToOne(cascade = CascadeType.PERSIST)
    private UserPreferences userPreferences;

    @OneToMany
    @JoinTable(
            name = "User_Posts",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Post> posts;

    public Post addPost(Post post) {
        System.out.println(post);
        posts.add(post);
        return post;
    }
}
