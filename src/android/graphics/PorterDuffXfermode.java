package android.graphics;

import android.graphics.PorterDuff;

/* loaded from: classes.dex */
public class PorterDuffXfermode extends Xfermode {
    static final int DEFAULT = PorterDuff.Mode.SRC_OVER.nativeInt;
    int porterDuffMode;

    PorterDuffXfermode() {
        this.porterDuffMode = DEFAULT;
    }

    public PorterDuffXfermode(PorterDuff.Mode mode) {
        this.porterDuffMode = DEFAULT;
        this.porterDuffMode = mode.nativeInt;
    }
}
