package cn.cie.event.handler;

import cn.cie.event.EventModel;
import cn.cie.event.EventType;
import cn.cie.utils.MailUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Event for sending email verification code during user registration
 */
@Service
public class SendValidateMailHandler implements EventHandler {

    public void doHandler(EventModel model) {
        MailUtil.sendValidateMail(model.getExts("mail"), model.getExts("code"));
    }

    public List<EventType> getSupportEvent() {
        return Arrays.asList(EventType.SEND_VALIDATE_EMAIL);
    }
}
