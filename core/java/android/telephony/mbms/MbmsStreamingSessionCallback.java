package android.telephony.mbms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes4.dex */
public class MbmsStreamingSessionCallback {

    @Retention(RetentionPolicy.SOURCE)
    private @interface StreamingError {
    }

    public void onError(int errorCode, String message) {
    }

    public void onStreamingServicesUpdated(List<StreamingServiceInfo> services) {
    }

    public void onMiddlewareReady() {
    }
}
