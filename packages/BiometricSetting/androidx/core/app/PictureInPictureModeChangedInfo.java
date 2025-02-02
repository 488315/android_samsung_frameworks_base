package androidx.core.app;

/* loaded from: classes.dex */
public final class PictureInPictureModeChangedInfo {
    private final boolean mIsInPictureInPictureMode;

    public PictureInPictureModeChangedInfo(boolean z) {
        this.mIsInPictureInPictureMode = z;
    }

    public final boolean isInPictureInPictureMode() {
        return this.mIsInPictureInPictureMode;
    }

    public PictureInPictureModeChangedInfo(boolean z, int i) {
        this.mIsInPictureInPictureMode = z;
    }
}
