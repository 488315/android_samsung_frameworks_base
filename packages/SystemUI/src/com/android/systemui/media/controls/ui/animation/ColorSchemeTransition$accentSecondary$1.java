package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class ColorSchemeTransition$accentSecondary$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$accentSecondary$1 INSTANCE = new ColorSchemeTransition$accentSecondary$1();

    public ColorSchemeTransition$accentSecondary$1() {
        super(1, MediaColorSchemesKt.class, "accentSecondaryFromScheme", "accentSecondaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).mAccent1.getS200());
    }
}
