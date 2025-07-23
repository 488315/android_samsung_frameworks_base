package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxUtilsKt$append$1 implements LetterboxController {
    public final /* synthetic */ LetterboxController $other;
    public final /* synthetic */ LetterboxController $this_append;

    public LetterboxUtilsKt$append$1(LetterboxController letterboxController, LetterboxController letterboxController2) {
        this.$this_append = letterboxController;
        this.$other = letterboxController2;
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void createLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        this.$this_append.createLetterboxSurface(letterboxKey, transaction, surfaceControl);
        this.$other.createLetterboxSurface(letterboxKey, transaction, surfaceControl);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void destroyLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction) {
        this.$this_append.destroyLetterboxSurface(letterboxKey, transaction);
        this.$other.destroyLetterboxSurface(letterboxKey, transaction);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void dump() {
        this.$this_append.dump();
        this.$other.dump();
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void updateLetterboxSurfaceBounds(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, Rect rect, Rect rect2) {
        this.$this_append.updateLetterboxSurfaceBounds(letterboxKey, transaction, rect, rect2);
        this.$other.updateLetterboxSurfaceBounds(letterboxKey, transaction, rect, rect2);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void updateLetterboxSurfaceVisibility(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, boolean z) {
        this.$this_append.updateLetterboxSurfaceVisibility(letterboxKey, transaction, z);
        this.$other.updateLetterboxSurfaceVisibility(letterboxKey, transaction, z);
    }
}
