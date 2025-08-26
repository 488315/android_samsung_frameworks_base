package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class ConfigurationControllerExtKt$onConfigChanged$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ConfigurationController f$0;
    public final /* synthetic */ ConfigurationController.ConfigurationListener f$1;

    public /* synthetic */ ConfigurationControllerExtKt$onConfigChanged$1$$ExternalSyntheticLambda0(ConfigurationController configurationController, ConfigurationController.ConfigurationListener configurationListener, int i) {
        this.$r8$classId = i;
        this.f$0 = configurationController;
        this.f$1 = configurationListener;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((ConfigurationControllerImpl) this.f$0).removeCallback((ConfigurationControllerExtKt$onConfigChanged$1$listener$1) this.f$1);
                break;
            default:
                ((ConfigurationControllerImpl) this.f$0).removeCallback((ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1$listener$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
