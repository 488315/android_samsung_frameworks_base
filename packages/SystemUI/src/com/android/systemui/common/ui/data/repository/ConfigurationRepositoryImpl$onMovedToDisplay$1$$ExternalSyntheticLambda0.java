package com.android.systemui.common.ui.data.repository;

import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class ConfigurationRepositoryImpl$onMovedToDisplay$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ConfigurationRepositoryImpl f$0;
    public final /* synthetic */ ConfigurationController.ConfigurationListener f$1;

    public /* synthetic */ ConfigurationRepositoryImpl$onMovedToDisplay$1$$ExternalSyntheticLambda0(ConfigurationRepositoryImpl configurationRepositoryImpl, ConfigurationController.ConfigurationListener configurationListener, int i) {
        this.$r8$classId = i;
        this.f$0 = configurationRepositoryImpl;
        this.f$1 = configurationListener;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((ConfigurationControllerImpl) this.f$0.configurationController).removeCallback((ConfigurationRepositoryImpl$onMovedToDisplay$1$callback$1) this.f$1);
                break;
            case 1:
                ((ConfigurationControllerImpl) this.f$0.configurationController).removeCallback((ConfigurationRepositoryImpl$configurationValues$1$callback$1) this.f$1);
                break;
            case 2:
                ((ConfigurationControllerImpl) this.f$0.configurationController).removeCallback((ConfigurationRepositoryImpl$onAnyConfigurationChange$1$callback$1) this.f$1);
                break;
            default:
                ((ConfigurationControllerImpl) this.f$0.configurationController).removeCallback((ConfigurationRepositoryImpl$onConfigurationChange$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
