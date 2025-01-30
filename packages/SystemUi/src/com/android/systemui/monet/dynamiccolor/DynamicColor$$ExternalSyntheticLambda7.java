package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.scheme.DynamicScheme;
import java.util.function.BiFunction;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DynamicColor$$ExternalSyntheticLambda7 implements BiFunction {
    public final /* synthetic */ Function f$0;
    public final /* synthetic */ DynamicScheme f$1;

    public /* synthetic */ DynamicColor$$ExternalSyntheticLambda7(Function function, DynamicScheme dynamicScheme) {
        this.f$0 = function;
        this.f$1 = dynamicScheme;
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        Function function = this.f$0;
        DynamicScheme dynamicScheme = this.f$1;
        Double d = (Double) obj2;
        return function != null && function.apply(dynamicScheme) != null && ((DynamicColor) function.apply(dynamicScheme)).background != null && ((DynamicColor) function.apply(dynamicScheme)).background.apply(dynamicScheme) != null ? Double.valueOf(DynamicColor.contrastingTone(d.doubleValue(), 7.0d)) : Double.valueOf(DynamicColor.contrastingTone(d.doubleValue(), Math.max(7.0d, ((Double) obj).doubleValue())));
    }
}
