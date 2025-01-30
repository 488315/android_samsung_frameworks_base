package androidx.transition;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class PathMotion {
    public PathMotion() {
    }

    public abstract Path getPath(float f, float f2, float f3, float f4);

    public PathMotion(Context context, AttributeSet attributeSet) {
    }
}
