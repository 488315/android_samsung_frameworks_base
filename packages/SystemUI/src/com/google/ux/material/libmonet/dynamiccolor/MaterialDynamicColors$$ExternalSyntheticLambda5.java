package com.google.ux.material.libmonet.dynamiccolor;

import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MaterialDynamicColors f$0;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda5(MaterialDynamicColors materialDynamicColors, int i) {
        this.$r8$classId = i;
        this.f$0 = materialDynamicColors;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.primary();
            case 1:
                return this.f$0.primaryFixedDim();
            case 2:
                return this.f$0.primaryFixed();
            case 3:
                return this.f$0.secondaryFixedDim();
            case 4:
                return this.f$0.secondaryFixed();
            case 5:
                return this.f$0.tertiaryFixedDim();
            case 6:
                return this.f$0.tertiaryFixed();
            case 7:
                return this.f$0.errorContainer();
            case 8:
                return this.f$0.secondary();
            case 9:
                return this.f$0.tertiaryFixedDim();
            case 10:
                return this.f$0.tertiaryFixed();
            case 11:
                MaterialDynamicColors materialDynamicColors = this.f$0;
                return new ToneDeltaPair(materialDynamicColors.tertiaryFixed(), materialDynamicColors.tertiaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
            case 12:
                return this.f$0.tertiary();
            default:
                return MaterialDynamicColors.inverseSurface();
        }
    }
}
