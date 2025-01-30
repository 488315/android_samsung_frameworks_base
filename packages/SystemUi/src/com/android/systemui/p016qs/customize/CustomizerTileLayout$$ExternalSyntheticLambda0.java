package com.android.systemui.p016qs.customize;

import com.android.systemui.p016qs.customize.SecQSCustomizerBase;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomizerTileLayout$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ SecQSCustomizerBase.CustomTileInfo f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SecQSCustomizerBase.CustomTileInfo customTileInfo = this.f$0;
        int i = CustomizerTileLayout.$r8$clinit;
        return ((SecQSCustomizerBase.CustomTileInfo) obj).spec.equals(customTileInfo.spec);
    }
}
