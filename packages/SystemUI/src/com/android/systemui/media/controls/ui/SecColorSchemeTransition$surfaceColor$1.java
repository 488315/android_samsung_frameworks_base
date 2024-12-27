package com.android.systemui.media.controls.ui;

import com.android.systemui.media.controls.ui.animation.MediaColorSchemesKt;
import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class SecColorSchemeTransition$surfaceColor$1 extends FunctionReferenceImpl implements Function1 {
    public static final SecColorSchemeTransition$surfaceColor$1 INSTANCE = new SecColorSchemeTransition$surfaceColor$1();

    public SecColorSchemeTransition$surfaceColor$1() {
        super(1, MediaColorSchemesKt.class, "surfaceFromScheme", "surfaceFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).mAccent2.getS800());
    }
}
