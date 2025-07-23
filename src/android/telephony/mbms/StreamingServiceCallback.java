package android.telephony.mbms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class StreamingServiceCallback {
    public static final int SIGNAL_STRENGTH_UNAVAILABLE = -1;

    @Retention(RetentionPolicy.SOURCE)
    private @interface StreamingServiceError {
    }

    public void onBroadcastSignalStrengthUpdated(int i) {
    }

    public void onError(int i, String str) {
    }

    public void onMediaDescriptionUpdated() {
    }

    public void onStreamMethodUpdated(int i) {
    }

    public void onStreamStateUpdated(int i, int i2) {
    }
}
