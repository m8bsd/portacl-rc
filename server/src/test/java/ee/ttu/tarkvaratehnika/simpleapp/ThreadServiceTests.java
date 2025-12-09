package ee.ttu.tarkvaratehnika.simpleapp;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.Board;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.thread.Post;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.thread.Thread;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.User;
import ee.ttu.tarkvaratehnika.simpleapp.data.repository.board.thread.PostRepository;
import ee.ttu.tarkvaratehnika.simpleapp.data.service.BoardService;
import ee.ttu.tarkvaratehnika.simpleapp.data.service.ThreadService;
import ee.ttu.tarkvaratehnika.simpleapp.data.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadServiceTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private BoardService boardService;

    @Test
    public void testThreadCreation() {
        User user = userService.createUser("", "", "", "", "");
        Board board = boardService.createBoard(user.getId(), "", "",  false);
        Thread thread = threadService.createThread(board.getId(), user.getId(), "");

        System.out.println(thread.getBoard().getThreads().size());
        System.out.println(board.getThreads().size());
    }

    @Test
    @Transactional
    public void testPostCreation() {

        User user = userService.createUser("", "", "", "", "");
        Board board = boardService.createBoard(user.getId(), "", "",  false);
        Thread thread = threadService.createThread(board.getId(), user.getId(), "");

        threadService.createPost(thread.getId(), user.getId(), "", true, new ArrayList<>());
        threadService.createPost(thread.getId(), user.getId(), "", false, new ArrayList<>());
        threadService.createPost(thread.getId(), user.getId(), "", false, new ArrayList<>());

        List<Long> replyTo = postRepository.findAll().stream().map(Post::getId).collect(Collectors.toList());

        threadService.createPost(thread.getId(), user.getId(), "", false, replyTo);

        List<Post> posts = postRepository.findAll();
        for (Post p : posts) {
            System.out.println(p.getCreationTimeStamp());
            System.out.println(p.getReplyBy().size());
            System.out.println(p.getReplyTo().size());
        }
    }
}
