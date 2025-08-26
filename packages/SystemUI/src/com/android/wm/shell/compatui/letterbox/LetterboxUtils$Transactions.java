package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;

/* loaded from: classes3.dex */
public final class LetterboxUtils$Transactions {
    public static final LetterboxUtils$Transactions INSTANCE = new LetterboxUtils$Transactions();

    private LetterboxUtils$Transactions() {
    }

    public static void moveAndCrop(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.setPosition(surfaceControl, rect.left, rect.top).setWindowCrop(surfaceControl, rect.width(), rect.height());
    }
}
