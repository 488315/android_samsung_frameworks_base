package com.android.systemui.qs;

import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecSTQuickControlRequestReceiver$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = SecSTQuickControlRequestReceiver.$r8$clinit;
        return (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
    }
}
