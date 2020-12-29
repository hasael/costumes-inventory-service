package app;

import app.contract.Costume;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JmsAssignmentProducer implements AssignmentNotifier{

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void notifyCostumeAssigner(Costume costume) {
        try {
            log.info("sending costume for assignment: " + costume.getId());
            jmsTemplate.convertAndSend("assignment", toJson(costume));
        } catch (Exception ex) {
            log.error("error: " + ex.getLocalizedMessage());
        }
    }

    private String toJson(Costume costume){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(costume);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
