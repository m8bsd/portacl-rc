package ee.ttu.tarkvaratehnika.simpleapp.data.entity.board;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.thread.Thread;
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
@Table(name = "Board")
public class Board implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "creation_time_stamp")
    private Date creationTimeStamp;

    @OneToOne(cascade = CascadeType.PERSIST)
    private BoardConfiguration boardConfiguration;

    @OneToMany
    @JoinTable(
            name = "Board_Threads",
            joinColumns = @JoinColumn(name = "thread_id"),
            inverseJoinColumns = @JoinColumn(name = "board_id")
    )
    private Set<Thread> threads;

    public Thread addThread(Thread thread) {
        this.threads.add(thread);
        return thread;
    }
}
