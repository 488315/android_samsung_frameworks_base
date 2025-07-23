package com.android.systemui.screenshot.appclips;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CollectionFuture;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppClipsViewModel$$ExternalSyntheticLambda5 implements AsyncFunction {
    @Override // com.google.common.util.concurrent.AsyncFunction
    public final ListenableFuture apply(Object obj) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf((Iterable) obj), true);
    }
}
