package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import kotlin.jvm.functions.Function6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CellularIconViewModelKairos$$ExternalSyntheticLambda2 implements Function6 {
    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        return Boolean.valueOf(((Boolean) obj4).booleanValue() || (!((Boolean) obj6).booleanValue() && booleanValue2 && booleanValue && ((Boolean) obj5).booleanValue()));
    }
}
