package com.android.systemui.statusbar.model;

import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KshData$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KshData f$0;

    public /* synthetic */ KshData$$ExternalSyntheticLambda0(KshData kshData, int i) {
        this.$r8$classId = i;
        this.f$0 = kshData;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                KshData kshData = this.f$0;
                return ((SamsungSystemShortcutsEnum) obj).getKshInfo(kshData.mContext, kshData.mKshDataUtils);
            default:
                KshData kshData2 = this.f$0;
                return ((SamsungAppShortcutsEnum) obj).getKshInfo(kshData2.mContext, kshData2.mKshDataUtils);
        }
    }
}
