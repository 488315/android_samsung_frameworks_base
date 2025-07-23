package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxUtils$Transactions {
    public static final LetterboxUtils$Transactions INSTANCE = new LetterboxUtils$Transactions();

    private LetterboxUtils$Transactions() {
    }

    public static void moveAndCrop(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.setPosition(surfaceControl, rect.left, rect.top).setWindowCrop(surfaceControl, rect.width(), rect.height());
    }
}
