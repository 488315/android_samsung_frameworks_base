package com.android.wm.shell.onehanded;

import android.graphics.Rect;

/* loaded from: classes3.dex */
public interface OneHandedTransitionCallback {
    void onStartFinished(Rect rect);

    void onStopFinished(Rect rect);

    default void onStartTransition() {
    }
}
