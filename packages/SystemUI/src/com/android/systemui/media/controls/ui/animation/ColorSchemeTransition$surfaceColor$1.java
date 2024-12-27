package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class ColorSchemeTransition$surfaceColor$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$surfaceColor$1 INSTANCE = new ColorSchemeTransition$surfaceColor$1();

    public ColorSchemeTransition$surfaceColor$1() {
        super(1, MediaColorSchemesKt.class, "surfaceFromScheme", "surfaceFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).mAccent2.getS800());
    }
}
