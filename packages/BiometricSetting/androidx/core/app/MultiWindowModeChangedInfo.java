package androidx.core.app;

/* loaded from: classes.dex */
public final class MultiWindowModeChangedInfo {
    private final boolean mIsInMultiWindowMode;

    public MultiWindowModeChangedInfo(boolean z) {
        this.mIsInMultiWindowMode = z;
    }

    public final boolean isInMultiWindowMode() {
        return this.mIsInMultiWindowMode;
    }

    public MultiWindowModeChangedInfo(boolean z, int i) {
        this.mIsInMultiWindowMode = z;
    }
}
