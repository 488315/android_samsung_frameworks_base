package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public interface OnTuneEventListener {
    public static final int SIGNAL_LOCKED = 0;
    public static final int SIGNAL_LOST_LOCK = 2;
    public static final int SIGNAL_NO_SIGNAL = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TuneEvent {
    }

    void onTuneEvent(int i);
}
