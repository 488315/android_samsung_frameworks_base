package com.android.systemui.statusbar.policy;

import com.android.systemui.Dependency;
import com.android.systemui.blur.SecQpBlurController;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class QuickPanelBlur$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
    }
}
