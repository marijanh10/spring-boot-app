package hranj.marijan.springbootapp.services.impl;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.MessageStatus;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.messages.TextMessage;
import hranj.marijan.springbootapp.services.SmsSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class SmsSenderServiceImpl implements SmsSenderService {

    @Value("${hranj.marijan.springbootapp.api-key}")
    private String apiKey;

    @Value("${hranj.marijan.springbootapp.api-secret}")
    private String apiSecret;

    private NexmoClient client;

    @PostConstruct
    public void init() {
        client = new NexmoClient.Builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
    }

    @Override
    public boolean sendSms(String sender, String receiverNumber, String messageContent) {
        TextMessage message = new TextMessage(sender, receiverNumber, messageContent);
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
        return response.getMessages().get(0).getStatus() == MessageStatus.OK;
    }

    @Override
    public boolean sendSms(String sender, String receiverNumber, char[] messageContent) {
        return sendSms(sender, receiverNumber, new String(messageContent));
    }

}
