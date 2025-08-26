package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import kotlin.jvm.functions.Function6;

/* loaded from: classes3.dex */
public final /* synthetic */ class CellularIconViewModelKairos$$ExternalSyntheticLambda2 implements Function6 {
    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        return Boolean.valueOf(((Boolean) obj4).booleanValue() || (!((Boolean) obj6).booleanValue() && zBooleanValue2 && zBooleanValue && ((Boolean) obj5).booleanValue()));
    }
}
