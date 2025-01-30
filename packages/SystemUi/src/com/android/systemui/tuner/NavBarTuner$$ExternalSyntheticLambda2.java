package com.android.systemui.tuner;

import com.android.systemui.Dependency;
import com.android.systemui.tuner.TunerService;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int[][] iArr = NavBarTuner.ICONS;
        ((TunerService) Dependency.get(TunerService.class)).removeTunable((TunerService.Tunable) obj);
    }
}
