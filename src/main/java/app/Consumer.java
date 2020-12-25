package app;

import app.db.Costume;
import app.db.CostumesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Slf4j
@Component
public class Consumer implements MessageListener {

    @Autowired
    private CostumesRepository costumesRepository;

    @Override
    @JmsListener(destination = "topic")
    public void onMessage(Message message) {
        try {
            log.info("Received message: " + message);

            var text = ((TextMessage) message).getText();

            costumesRepository.save(fromJson(text));
        } catch (Exception ex) {
            log.error("Error consuming message: " + ex.getLocalizedMessage());
        }
    }

    private Costume fromJson(String data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data, Costume.class);
    }
}
