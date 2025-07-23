package androidx.compose.ui.platform;

import android.graphics.Outline;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.Path;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OutlineVerificationHelper {
    public static final OutlineVerificationHelper INSTANCE = new OutlineVerificationHelper();

    private OutlineVerificationHelper() {
    }

    public final void setPath(Outline outline, Path path) {
        if (!(path instanceof AndroidPath)) {
            throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
        }
        outline.setPath(((AndroidPath) path).internalPath);
    }
}
