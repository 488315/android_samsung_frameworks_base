package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.content.Intent;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class CarrierConfigRepositoryImpl$$ExternalSyntheticLambda2 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return Integer.valueOf(((Intent) obj).getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1));
    }
}
