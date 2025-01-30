package androidx.core.app;

import android.content.res.Configuration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PictureInPictureModeChangedInfo {
    public final boolean mIsInPictureInPictureMode;

    public PictureInPictureModeChangedInfo(boolean z) {
        this.mIsInPictureInPictureMode = z;
    }

    public PictureInPictureModeChangedInfo(boolean z, Configuration configuration) {
        this.mIsInPictureInPictureMode = z;
    }
}
