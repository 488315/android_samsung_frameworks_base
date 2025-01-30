package androidx.core.app;

import android.content.res.Configuration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MultiWindowModeChangedInfo {
    public final boolean mIsInMultiWindowMode;

    public MultiWindowModeChangedInfo(boolean z) {
        this.mIsInMultiWindowMode = z;
    }

    public MultiWindowModeChangedInfo(boolean z, Configuration configuration) {
        this.mIsInMultiWindowMode = z;
    }
}
