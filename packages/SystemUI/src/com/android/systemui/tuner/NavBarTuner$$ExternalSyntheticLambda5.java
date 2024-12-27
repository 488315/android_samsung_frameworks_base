package com.android.systemui.tuner;

import com.android.systemui.Dependency;
import com.android.systemui.tuner.TunerService;
import java.util.function.Consumer;

public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda5 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int[][] iArr = NavBarTuner.ICONS;
        ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).removeTunable((TunerService.Tunable) obj);
    }
}
