package com.android.systemui.broadcast;

import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastDispatcher$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        String str = (String) obj;
        str.getClass();
        return Boolean.valueOf(str.startsWith("android.intent.action.PACKAGE_"));
    }
}
