package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public final class StandardExtension {
    private final int mDvbsStandardExtension;
    private final int mDvbtStandardExtension;

    private StandardExtension(int i, int i2) {
        this.mDvbsStandardExtension = i;
        this.mDvbtStandardExtension = i2;
    }

    public int getDvbsStandardExtension() {
        int i = this.mDvbsStandardExtension;
        if (i != 0) {
            return i;
        }
        throw new IllegalStateException("No DVB-S standard transition");
    }

    public int getDvbtStandardExtension() {
        int i = this.mDvbtStandardExtension;
        if (i != 0) {
            return i;
        }
        throw new IllegalStateException("No DVB-T standard transition");
    }
}
