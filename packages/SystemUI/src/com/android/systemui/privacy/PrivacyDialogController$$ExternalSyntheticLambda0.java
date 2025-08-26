package com.android.systemui.privacy;

import com.android.systemui.Dependency;
import com.android.systemui.shade.SecPanelSplitHelper;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class PrivacyDialogController$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = PrivacyDialogController.$r8$clinit;
        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
    }
}
