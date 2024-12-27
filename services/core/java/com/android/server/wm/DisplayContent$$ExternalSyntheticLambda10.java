package com.android.server.wm;

import android.app.ActivityManagerInternal;

import com.android.internal.util.function.TriConsumer;

public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda10
        implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        ((ActivityManagerInternal) obj)
                .killAllBackgroundProcessesExcept(
                        ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
    }
}
