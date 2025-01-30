package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.palettes.TonalPalette;
import com.android.systemui.monet.scheme.DynamicScheme;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DynamicColor$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DynamicColor$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Double.valueOf(((TonalPalette) ((Function) this.f$0).apply((DynamicScheme) obj)).hue);
            case 1:
                return Double.valueOf(((TonalPalette) ((Function) this.f$0).apply((DynamicScheme) obj)).chroma);
            default:
                return (DynamicColor) this.f$0;
        }
    }
}
