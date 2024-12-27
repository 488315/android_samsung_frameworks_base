package com.android.server;

import android.util.ArraySet;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
