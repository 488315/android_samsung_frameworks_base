package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CellularIconViewModelKairos$$ExternalSyntheticLambda10 implements Function4 {
    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        if (((Boolean) obj4).booleanValue()) {
            booleanValue2 = false;
        } else if (!booleanValue) {
            booleanValue2 = true;
        }
        return Boolean.valueOf(booleanValue2);
    }
}
