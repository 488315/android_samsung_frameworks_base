package com.android.wm.shell.common;

import android.graphics.Rect;
import android.view.InsetsSource;
import android.view.InsetsState;
import com.android.wm.shell.common.DisplayInsetsController;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ImeListener implements DisplayInsetsController.OnInsetsChangedListener {
    public final DisplayController displayController;
    public final int displayId;
    public final InsetsState mInsetsState = new InsetsState();
    public final Rect mTmpBounds = new Rect();

    public ImeListener(DisplayController displayController, int i) {
        this.displayController = displayController;
        this.displayId = i;
    }

    public final Pair getImeVisibilityAndHeight(InsetsState insetsState) {
        InsetsSource peekSource = insetsState.peekSource(InsetsSource.ID_IME);
        Rect frame = (peekSource == null || !peekSource.isVisible()) ? null : peekSource.getFrame();
        return new Pair(Boolean.valueOf(peekSource != null ? peekSource.isVisible() : false), Integer.valueOf(frame != null ? this.mTmpBounds.bottom - frame.top : 0));
    }

    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    public final void insetsChanged(InsetsState insetsState) {
        DisplayLayout displayLayout;
        if (Intrinsics.areEqual(this.mInsetsState, insetsState) || (displayLayout = this.displayController.getDisplayLayout(this.displayId)) == null) {
            return;
        }
        displayLayout.getStableBounds(this.mTmpBounds, false);
        Pair imeVisibilityAndHeight = getImeVisibilityAndHeight(this.mInsetsState);
        boolean booleanValue = ((Boolean) imeVisibilityAndHeight.component1()).booleanValue();
        int intValue = ((Number) imeVisibilityAndHeight.component2()).intValue();
        Pair imeVisibilityAndHeight2 = getImeVisibilityAndHeight(insetsState);
        boolean booleanValue2 = ((Boolean) imeVisibilityAndHeight2.component1()).booleanValue();
        int intValue2 = ((Number) imeVisibilityAndHeight2.component2()).intValue();
        this.mInsetsState.set(insetsState, true);
        if (booleanValue == booleanValue2 && intValue == intValue2) {
            return;
        }
        onImeVisibilityChanged(booleanValue2, intValue2);
    }

    public abstract void onImeVisibilityChanged(boolean z, int i);
}
