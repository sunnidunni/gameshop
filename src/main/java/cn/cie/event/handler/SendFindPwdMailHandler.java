package cn.cie.event.handler;

import cn.cie.event.EventModel;
import cn.cie.event.EventType;
import cn.cie.utils.MailUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Event for sending password recovery email
 */
@Service
public class SendFindPwdMailHandler implements EventHandler {

    public void doHandler(EventModel model) {
        MailUtil.sendFetchPwdMail(model.getExts("mail"), model.getExts("code"));
    }

    public List<EventType> getSupportEvent() {
        return Arrays.asList(EventType.SEND_FIND_PWD_EMAIL);
    }
}

