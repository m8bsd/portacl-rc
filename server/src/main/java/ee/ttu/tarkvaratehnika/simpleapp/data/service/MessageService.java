package ee.ttu.tarkvaratehnika.simpleapp.data.service;

import ee.ttu.tarkvaratehnika.simpleapp.data.entity.Message;
import ee.ttu.tarkvaratehnika.simpleapp.data.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Primary
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> findAll(int page, int pageSize) {
        return messageRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "time")).getContent();
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public long messageCount() {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false).count();
    }
}
