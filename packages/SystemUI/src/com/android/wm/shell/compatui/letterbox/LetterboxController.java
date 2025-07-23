package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface LetterboxController {
    void createLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

    void destroyLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction);

    void dump();

    void updateLetterboxSurfaceBounds(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, Rect rect, Rect rect2);

    void updateLetterboxSurfaceVisibility(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, boolean z);
}
