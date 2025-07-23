package com.android.systemui.shared.clocks;

import com.android.systemui.customization.R$string;
import com.android.systemui.plugins.clocks.ClockConfig;
import com.android.systemui.shared.clocks.DefaultClockController;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DefaultClockController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DefaultClockController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                DefaultClockController defaultClockController = (DefaultClockController) this.f$0;
                return new ClockConfig("DEFAULT", defaultClockController.resources.getString(R$string.clock_default_name), defaultClockController.resources.getString(R$string.clock_default_description), false, false, 24, null);
            default:
                return Boolean.valueOf(((DefaultClockController.DefaultClockAnimations) this.f$0).dozeState.isActive);
        }
    }
}
