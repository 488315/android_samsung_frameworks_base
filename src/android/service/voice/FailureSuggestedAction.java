package android.service.voice;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class FailureSuggestedAction {
    public static final int DISABLE_DETECTION = 2;
    public static final int NONE = 1;
    public static final int RECREATE_DETECTOR = 3;
    public static final int RESTART_RECOGNITION = 4;
    public static final int UNKNOWN = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureSuggestedActionDef {
    }

    private FailureSuggestedAction() {
    }
}
