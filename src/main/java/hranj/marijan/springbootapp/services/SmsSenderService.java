package hranj.marijan.springbootapp.services;

public interface SmsSenderService {

    boolean sendSms(String sender, String receiverNumber, String message);

    boolean sendSms(String sender, String receiverNumber, char[] message);

}
