package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.telephony.ims.SipMessage;

@SystemApi
public interface DelegateConnectionMessageCallback {
    void onMessageReceived(SipMessage sipMessage);

    void onMessageSendFailure(String str, int i);

    void onMessageSent(String str);
}
