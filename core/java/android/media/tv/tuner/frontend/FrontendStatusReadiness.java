package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class FrontendStatusReadiness {
    public static final int FRONTEND_STATUS_READINESS_STABLE = 3;
    public static final int FRONTEND_STATUS_READINESS_UNAVAILABLE = 1;
    public static final int FRONTEND_STATUS_READINESS_UNDEFINED = 0;
    public static final int FRONTEND_STATUS_READINESS_UNSTABLE = 2;
    public static final int FRONTEND_STATUS_READINESS_UNSUPPORTED = 4;
    private int mFrontendStatusType;
    private int mStatusReadiness;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Readiness {
    }

    private FrontendStatusReadiness(int type, int readiness) {
        this.mFrontendStatusType = type;
        this.mStatusReadiness = readiness;
    }

    public int getStatusType() {
        return this.mFrontendStatusType;
    }

    public int getStatusReadiness() {
        return this.mStatusReadiness;
    }
}
