package com.android.systemui.statusbar.model;

import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class KshData$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KshData f$0;

    public /* synthetic */ KshData$$ExternalSyntheticLambda0(KshData kshData, int i) {
        this.$r8$classId = i;
        this.f$0 = kshData;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        KshData kshData = this.f$0;
        switch (i) {
            case 0:
                return ((SamsungAppShortcutsEnum) obj).getKshInfo(kshData.mContext, kshData.mKshDataUtils);
            default:
                return ((SamsungSystemShortcutsEnum) obj).getKshInfo(kshData.mContext, kshData.mKshDataUtils);
        }
    }
}
