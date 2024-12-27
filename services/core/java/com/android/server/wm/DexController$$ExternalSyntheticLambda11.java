package com.android.server.wm;

import android.app.ActivityManagerInternal;

import com.android.internal.util.function.QuadConsumer;

public final /* synthetic */ class DexController$$ExternalSyntheticLambda11
        implements QuadConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        ((ActivityManagerInternal) obj)
                .killProcessForDex(
                        ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (String) obj4);
    }
}
