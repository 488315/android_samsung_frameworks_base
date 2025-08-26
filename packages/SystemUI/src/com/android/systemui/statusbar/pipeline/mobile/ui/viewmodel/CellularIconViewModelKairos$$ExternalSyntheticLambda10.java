package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
public final /* synthetic */ class CellularIconViewModelKairos$$ExternalSyntheticLambda10 implements Function4 {
    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        if (((Boolean) obj4).booleanValue()) {
            zBooleanValue2 = false;
        } else if (!zBooleanValue) {
            zBooleanValue2 = true;
        }
        return Boolean.valueOf(zBooleanValue2);
    }
}
