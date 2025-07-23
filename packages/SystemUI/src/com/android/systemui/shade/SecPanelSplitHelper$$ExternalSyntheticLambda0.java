package com.android.systemui.shade;

import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SecPanelSplitHelper$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SecPanelSplitHelper$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                SecPanelSplitHelper.Companion companion = SecPanelSplitHelper.Companion;
                return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
            case 1:
                SecPanelSplitHelper.Companion companion2 = SecPanelSplitHelper.Companion;
                return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
            default:
                return (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
        }
    }
}
