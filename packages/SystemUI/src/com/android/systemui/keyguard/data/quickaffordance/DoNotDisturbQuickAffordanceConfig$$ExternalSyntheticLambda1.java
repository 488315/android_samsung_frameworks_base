package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.settingslib.notification.modes.EnableDndDialogFactory;
import com.android.settingslib.notification.modes.EnableDndDialogMetricsLogger;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class DoNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ DoNotDisturbQuickAffordanceConfig f$1;

    public /* synthetic */ DoNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda1(EnableDndDialogFactory enableDndDialogFactory, DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig) {
        this.f$0 = enableDndDialogFactory;
        this.f$1 = doNotDisturbQuickAffordanceConfig;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$0;
        DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                int i = DoNotDisturbQuickAffordanceConfig.$r8$clinit;
                EnableDndDialogFactory enableDndDialogFactory = (EnableDndDialogFactory) obj;
                if (enableDndDialogFactory != null) {
                    return enableDndDialogFactory;
                }
                Context context = doNotDisturbQuickAffordanceConfig.context;
                return new EnableDndDialogFactory(context, R.style.Theme_SystemUI_Dialog, true, new EnableDndDialogMetricsLogger(context));
            default:
                ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig.controller).removeCallback((DoNotDisturbQuickAffordanceConfig$lockScreenState$2$callback$1) obj);
                return Unit.INSTANCE;
        }
    }

    public /* synthetic */ DoNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda1(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, DoNotDisturbQuickAffordanceConfig$lockScreenState$2$callback$1 doNotDisturbQuickAffordanceConfig$lockScreenState$2$callback$1) {
        this.f$1 = doNotDisturbQuickAffordanceConfig;
        this.f$0 = doNotDisturbQuickAffordanceConfig$lockScreenState$2$callback$1;
    }
}
