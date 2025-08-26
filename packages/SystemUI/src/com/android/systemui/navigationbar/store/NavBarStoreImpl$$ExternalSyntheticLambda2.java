package com.android.systemui.navigationbar.store;

import com.android.systemui.navigationbar.bandaid.Band;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class NavBarStoreImpl$$ExternalSyntheticLambda2 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return Integer.valueOf(((Band) obj).priority >= ((Band) obj2).priority ? 1 : -1);
    }
}
