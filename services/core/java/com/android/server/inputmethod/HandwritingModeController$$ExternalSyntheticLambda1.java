package com.android.server.inputmethod;

import android.view.MotionEvent;

import java.util.function.Consumer;

public final /* synthetic */ class HandwritingModeController$$ExternalSyntheticLambda1
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((MotionEvent) obj).recycle();
    }
}
