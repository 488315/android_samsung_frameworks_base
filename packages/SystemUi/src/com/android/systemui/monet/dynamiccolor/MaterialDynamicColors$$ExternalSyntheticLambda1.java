package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.scheme.DynamicScheme;
import com.android.systemui.monet.scheme.Variant;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MaterialDynamicColors f$0;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda1(MaterialDynamicColors materialDynamicColors, int i) {
        this.$r8$classId = i;
        this.f$0 = materialDynamicColors;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 1:
                return this.f$0.primaryFixedDim();
            case 2:
                MaterialDynamicColors materialDynamicColors = this.f$0;
                DynamicScheme dynamicScheme = (DynamicScheme) obj;
                materialDynamicColors.getClass();
                if (MaterialDynamicColors.isFidelity(dynamicScheme)) {
                    return Double.valueOf(DynamicColor.contrastingTone(((Double) materialDynamicColors.primaryContainer().tone.apply(dynamicScheme)).doubleValue(), 4.5d));
                }
                z = dynamicScheme.variant == Variant.MONOCHROME;
                boolean z2 = dynamicScheme.isDark;
                if (z) {
                    return Double.valueOf(z2 ? 0.0d : 100.0d);
                }
                return Double.valueOf(z2 ? 90.0d : 10.0d);
            case 3:
                return this.f$0.primaryContainer();
            case 4:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 5:
                this.f$0.getClass();
                return DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(1), null, null);
            case 6:
                return this.f$0.tertiary();
            case 7:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 8:
                return this.f$0.secondaryFixedDim();
            case 9:
                return this.f$0.secondaryFixedDim();
            case 10:
                return this.f$0.error();
            case 11:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 12:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 13:
                return new ToneDeltaConstraint(15.0d, this.f$0.errorContainer(), ((DynamicScheme) obj).isDark ? TonePolarity.DARKER : TonePolarity.LIGHTER);
            case 14:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 15:
                return new ToneDeltaConstraint(15.0d, this.f$0.tertiaryContainer(), ((DynamicScheme) obj).isDark ? TonePolarity.DARKER : TonePolarity.LIGHTER);
            case 16:
                return this.f$0.tertiaryFixedDim();
            case 17:
                MaterialDynamicColors materialDynamicColors2 = this.f$0;
                DynamicScheme dynamicScheme2 = (DynamicScheme) obj;
                materialDynamicColors2.getClass();
                z = dynamicScheme2.variant == Variant.MONOCHROME;
                boolean z3 = dynamicScheme2.isDark;
                if (z) {
                    return Double.valueOf(z3 ? 0.0d : 100.0d);
                }
                if (MaterialDynamicColors.isFidelity(dynamicScheme2)) {
                    return Double.valueOf(DynamicColor.contrastingTone(((Double) materialDynamicColors2.tertiaryContainer().tone.apply(dynamicScheme2)).doubleValue(), 4.5d));
                }
                return Double.valueOf(z3 ? 90.0d : 10.0d);
            case 18:
                return this.f$0.tertiaryContainer();
            case 19:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 20:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 21:
                return this.f$0.primary();
            case 22:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 23:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 24:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            case 25:
                return this.f$0.secondary();
            case 26:
                MaterialDynamicColors materialDynamicColors3 = this.f$0;
                DynamicScheme dynamicScheme3 = (DynamicScheme) obj;
                materialDynamicColors3.getClass();
                if (MaterialDynamicColors.isFidelity(dynamicScheme3)) {
                    return Double.valueOf(DynamicColor.contrastingTone(((Double) materialDynamicColors3.secondaryContainer().tone.apply(dynamicScheme3)).doubleValue(), 4.5d));
                }
                return Double.valueOf(dynamicScheme3.isDark ? 90.0d : 10.0d);
            case 27:
                return this.f$0.secondaryContainer();
            case 28:
                this.f$0.getClass();
                return MaterialDynamicColors.highestSurface((DynamicScheme) obj);
            default:
                return this.f$0.tertiaryFixedDim();
        }
    }
}
