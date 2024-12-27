package com.android.server;

import android.util.ArraySet;

import java.util.function.Consumer;

public final /* synthetic */ class PinnerService$$ExternalSyntheticLambda4 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ArraySet arraySet;
        PinnerService pinnerService = (PinnerService) obj;
        synchronized (pinnerService) {
            arraySet = pinnerService.mPinKeys;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            pinnerService.unpinApp(((Integer) arraySet.valueAt(size)).intValue());
        }
    }
}
