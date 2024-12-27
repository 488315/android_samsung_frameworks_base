package com.android.systemui.tuner;

import com.android.systemui.Dependency;
import com.android.systemui.tuner.TunerService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda5 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int[][] iArr = NavBarTuner.ICONS;
        ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).removeTunable((TunerService.Tunable) obj);
    }
}
