package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.scheme.DynamicScheme;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MaterialDynamicColors f$0;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda4(MaterialDynamicColors materialDynamicColors, int i) {
        this.$r8$classId = i;
        this.f$0 = materialDynamicColors;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 1:
                return this.f$0.primaryFixedDim();
            case 2:
                this.f$0.getClass();
                return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda5(21), new MaterialDynamicColors$$ExternalSyntheticLambda5(22), null, null);
            case 3:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 4:
                return new ToneDeltaConstraint(15.0d, this.f$0.primaryContainer(), ((DynamicScheme) obj).isDark ? TonePolarity.DARKER : TonePolarity.LIGHTER);
            case 5:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 6:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 7:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 8:
                return new ToneDeltaConstraint(15.0d, this.f$0.secondaryContainer(), ((DynamicScheme) obj).isDark ? TonePolarity.DARKER : TonePolarity.LIGHTER);
            default:
                return this.f$0.errorContainer();
        }
    }
}
