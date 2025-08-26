package com.android.systemui.media;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda6 implements Function {
    public final /* synthetic */ ConcurrentHashMap f$0;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return (SecMediaPlayerData) this.f$0.get((MediaType) obj);
    }
}
