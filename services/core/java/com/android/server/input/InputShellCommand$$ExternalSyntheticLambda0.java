package com.android.server.input;

import android.hardware.input.InputManagerGlobal;
import android.view.InputEvent;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class InputShellCommand$$ExternalSyntheticLambda0
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        InputManagerGlobal.getInstance()
                .injectInputEvent((InputEvent) obj, ((Integer) obj2).intValue());
    }
}
