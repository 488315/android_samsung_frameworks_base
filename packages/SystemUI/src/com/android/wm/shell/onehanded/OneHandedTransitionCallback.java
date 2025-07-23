package com.android.wm.shell.onehanded;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface OneHandedTransitionCallback {
    void onStartFinished(Rect rect);

    void onStopFinished(Rect rect);

    default void onStartTransition() {
    }
}
