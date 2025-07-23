package androidx.activity;

import android.view.Window;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EdgeToEdgeApi30 extends EdgeToEdgeApi29 {
    @Override // androidx.activity.EdgeToEdgeApi28
    public void adjustLayoutInDisplayCutoutMode(Window window) {
        window.getAttributes().layoutInDisplayCutoutMode = 3;
    }
}
