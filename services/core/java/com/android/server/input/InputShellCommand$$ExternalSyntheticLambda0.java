package com.android.server.input;

import android.hardware.input.InputManagerGlobal;
import android.view.InputEvent;

import java.util.function.BiConsumer;

public final /* synthetic */ class InputShellCommand$$ExternalSyntheticLambda0
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        InputManagerGlobal.getInstance()
                .injectInputEvent((InputEvent) obj, ((Integer) obj2).intValue());
    }
}
