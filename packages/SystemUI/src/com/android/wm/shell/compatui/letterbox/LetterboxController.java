package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;

/* loaded from: classes3.dex */
public interface LetterboxController {
    void createLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

    void destroyLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction);

    void dump();

    void updateLetterboxSurfaceBounds(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, Rect rect, Rect rect2);

    void updateLetterboxSurfaceVisibility(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, boolean z);
}
