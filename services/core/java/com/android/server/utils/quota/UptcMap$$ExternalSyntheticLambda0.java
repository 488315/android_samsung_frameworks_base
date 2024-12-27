package com.android.server.utils.quota;

import android.util.ArrayMap;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class UptcMap$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ Consumer f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Consumer consumer = this.f$0;
        ArrayMap arrayMap = (ArrayMap) obj;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            consumer.accept(arrayMap.valueAt(size));
        }
    }
}
