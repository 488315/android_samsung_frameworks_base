package androidx.core.app;

import android.content.res.Configuration;

/* loaded from: classes.dex */
public final class MultiWindowModeChangedInfo {
    public final boolean isInMultiWindowMode;

    public MultiWindowModeChangedInfo(boolean z) {
        this.isInMultiWindowMode = z;
    }

    public MultiWindowModeChangedInfo(boolean z, Configuration configuration) {
        this(z);
    }
}
