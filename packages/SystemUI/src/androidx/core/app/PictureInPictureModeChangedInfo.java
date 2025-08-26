package androidx.core.app;

import android.content.res.Configuration;

/* loaded from: classes.dex */
public final class PictureInPictureModeChangedInfo {
    public final boolean isInPictureInPictureMode;

    public PictureInPictureModeChangedInfo(boolean z) {
        this.isInPictureInPictureMode = z;
    }

    public PictureInPictureModeChangedInfo(boolean z, Configuration configuration) {
        this(z);
    }
}
