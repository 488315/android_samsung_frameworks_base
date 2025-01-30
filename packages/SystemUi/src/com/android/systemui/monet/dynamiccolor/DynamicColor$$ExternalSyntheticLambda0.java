package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.scheme.DynamicScheme;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DynamicColor$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DynamicScheme f$0;

    public /* synthetic */ DynamicColor$$ExternalSyntheticLambda0(DynamicScheme dynamicScheme, int i) {
        this.$r8$classId = i;
        this.f$0 = dynamicScheme;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Double.valueOf(((DynamicColor) obj).getTone(this.f$0));
            case 1:
                return (Double) ((DynamicColor) obj).toneMinContrast.apply(this.f$0);
            default:
                return (Double) ((DynamicColor) obj).toneMaxContrast.apply(this.f$0);
        }
    }
}
