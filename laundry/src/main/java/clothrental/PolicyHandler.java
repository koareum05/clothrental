package clothrental;

import clothrental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    LaundryRepository laundryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverWashed_wash(@Payload Washed washed){

        if(washed.isMe()) {
            // To-Do : SMS발송, CJ Logistics 연계, ...
            Laundry laundry = new Laundry();
            laundry.setOrderId(washed.getId());
            laundry.setStatus("Laundry Started");

            laundryRepository.save(laundry);

            System.out.println("##### listener  : " + washed.toJson());
        }
    }

}
