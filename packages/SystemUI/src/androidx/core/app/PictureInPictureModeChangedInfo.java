package androidx.core.app;

import android.content.res.Configuration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
