package com.android.systemui.statusbar.policy;

import com.android.systemui.Dependency;
import com.android.systemui.blur.SecQpBlurController;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class QuickPanelBlur$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
    }
}
