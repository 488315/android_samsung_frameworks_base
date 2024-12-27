package android.telephony.ims;

import android.annotation.SystemApi;

@SystemApi
public interface SipDelegateConnection {
    void cleanupSession(String str);

    void notifyMessageReceiveError(String str, int i);

    void notifyMessageReceived(String str);

    void sendMessage(SipMessage sipMessage, long j);
}
