package ee.ttu.tarkvaratehnika.simpleapp.controller;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.Message;
import ee.ttu.tarkvaratehnika.simpleapp.data.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/messages")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(
            path = "/save",
            method = RequestMethod.POST
    )
    public Message saveMessage(@RequestBody Message message) {
        System.out.println(message);
        return messageService.save(message);
    }

    @RequestMapping(
            path = "/all/{page}/{pageSize}",
            method = RequestMethod.GET
    )
    public List<Message> findMessages(@PathVariable int page,
                                      @PathVariable int pageSize) {
        return messageService.findAll(page, pageSize);
    }

    @RequestMapping(
            path = "/count",
            method = RequestMethod.GET
    )
    public long messageCount() {
        return messageService.messageCount();
    }
}
