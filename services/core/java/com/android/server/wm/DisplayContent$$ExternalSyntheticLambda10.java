package com.android.server.wm;

import android.app.ActivityManagerInternal;

import com.android.internal.util.function.TriConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda10
        implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        ((ActivityManagerInternal) obj)
                .killAllBackgroundProcessesExcept(
                        ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
    }
}
