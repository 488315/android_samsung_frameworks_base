package com.android.systemui.qs.tileimpl;

import com.android.systemui.Dependency;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.tileimpl.SecQSIconViewImpl;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSIconViewImpl$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        SecQSIconViewImpl.Companion companion = SecQSIconViewImpl.Companion;
        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
    }
}
