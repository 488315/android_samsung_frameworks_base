package com.android.wm.shell.common;

import android.graphics.Rect;
import android.view.InsetsSource;
import android.view.InsetsState;
import com.android.wm.shell.common.DisplayInsetsController;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

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
        InsetsSource insetsSourcePeekSource = insetsState.peekSource(InsetsSource.ID_IME);
        Rect frame = (insetsSourcePeekSource == null || !insetsSourcePeekSource.isVisible()) ? null : insetsSourcePeekSource.getFrame();
        return new Pair(Boolean.valueOf(insetsSourcePeekSource != null ? insetsSourcePeekSource.isVisible() : false), Integer.valueOf(frame != null ? this.mTmpBounds.bottom - frame.top : 0));
    }

    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    public final void insetsChanged(InsetsState insetsState) {
        DisplayLayout displayLayout;
        if (Intrinsics.areEqual(this.mInsetsState, insetsState) || (displayLayout = this.displayController.getDisplayLayout(this.displayId)) == null) {
            return;
        }
        displayLayout.getStableBounds(this.mTmpBounds, false);
        Pair imeVisibilityAndHeight = getImeVisibilityAndHeight(this.mInsetsState);
        boolean zBooleanValue = ((Boolean) imeVisibilityAndHeight.component1()).booleanValue();
        int iIntValue = ((Number) imeVisibilityAndHeight.component2()).intValue();
        Pair imeVisibilityAndHeight2 = getImeVisibilityAndHeight(insetsState);
        boolean zBooleanValue2 = ((Boolean) imeVisibilityAndHeight2.component1()).booleanValue();
        int iIntValue2 = ((Number) imeVisibilityAndHeight2.component2()).intValue();
        this.mInsetsState.set(insetsState, true);
        if (zBooleanValue == zBooleanValue2 && iIntValue == iIntValue2) {
            return;
        }
        onImeVisibilityChanged(zBooleanValue2, iIntValue2);
    }

    public abstract void onImeVisibilityChanged(boolean z, int i);
}
