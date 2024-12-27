package com.android.server.autofill;

import android.service.autofill.Dataset;

import com.android.internal.util.function.HexConsumer;

public final /* synthetic */ class Session$$ExternalSyntheticLambda6 implements HexConsumer {
    public final void accept(
            Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        ((Session) obj)
                .autoFill(
                        ((Integer) obj2).intValue(),
                        ((Integer) obj3).intValue(),
                        (Dataset) obj4,
                        ((Boolean) obj5).booleanValue(),
                        ((Integer) obj6).intValue());
    }
}
