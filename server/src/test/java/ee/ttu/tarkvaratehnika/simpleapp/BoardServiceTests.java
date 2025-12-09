package ee.ttu.tarkvaratehnika.simpleapp;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.board.Board;
import ee.ttu.tarkvaratehnika.simpleapp.data.entity.user.User;
import ee.ttu.tarkvaratehnika.simpleapp.data.service.BoardService;
import ee.ttu.tarkvaratehnika.simpleapp.data.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;
    @Test
    public void testBoardCreation() {
        User user = userService.createUser("", "", "", "", "");
        Board board = boardService.createBoard(user.getId(), "", "",  false);
        System.out.println(board.getCreationTimeStamp());
        System.out.println(board.getBoardConfiguration());
        System.out.println(board.getBoardConfiguration().getBoard());
    }

}
