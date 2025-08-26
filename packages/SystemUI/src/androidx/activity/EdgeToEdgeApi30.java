package androidx.activity;

import android.view.Window;

/* loaded from: classes.dex */
public final class EdgeToEdgeApi30 extends EdgeToEdgeApi29 {
    @Override // androidx.activity.EdgeToEdgeApi28
    public void adjustLayoutInDisplayCutoutMode(Window window) {
        window.getAttributes().layoutInDisplayCutoutMode = 3;
    }
}
