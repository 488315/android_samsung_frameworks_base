package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.os.Bundle;

import com.android.internal.util.function.QuadConsumer;

public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda9
        implements QuadConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        ((ActivityManagerInternal) obj)
                .killAllBackgroundProcessesExcept(
                        ((Integer) obj2).intValue(), ((Integer) obj3).intValue(), (Bundle) obj4);
    }
}
