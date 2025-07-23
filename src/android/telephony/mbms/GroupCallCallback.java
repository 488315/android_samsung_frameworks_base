package android.telephony.mbms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public interface GroupCallCallback {
    public static final int SIGNAL_STRENGTH_UNAVAILABLE = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupCallError {
    }

    default void onBroadcastSignalStrengthUpdated(int i) {
    }

    default void onError(int i, String str) {
    }

    default void onGroupCallStateChanged(int i, int i2) {
    }
}
