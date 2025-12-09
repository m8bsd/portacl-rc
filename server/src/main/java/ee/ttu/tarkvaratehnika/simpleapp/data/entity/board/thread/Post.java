package ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.thread;


import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.User;
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
@Table(name = "Post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private Boolean isOriginal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Thread thread;

    @CreationTimestamp
    @Column(name = "creation_time_stamp")
    private Date creationTimeStamp;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToMany
    @JoinTable(
            name = "Post_Replies",
            joinColumns = {
                    @JoinColumn(
                            name = "replier_id",
                            referencedColumnName = "id",
                            nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "original_poster_id",
                            referencedColumnName = "id",
                            nullable = false)
            }
    )
    private Set<Post> replyTo;

    @ManyToMany(mappedBy = "replyTo")
    private Set<Post> replyBy;

    public Post addReplyBy(Post post) {
        replyBy.add(post);
        return post;
    }
}
