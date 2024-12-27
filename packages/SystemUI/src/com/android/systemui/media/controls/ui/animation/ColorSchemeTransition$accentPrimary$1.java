package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class ColorSchemeTransition$accentPrimary$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$accentPrimary$1 INSTANCE = new ColorSchemeTransition$accentPrimary$1();

    public ColorSchemeTransition$accentPrimary$1() {
        super(1, MediaColorSchemesKt.class, "accentPrimaryFromScheme", "accentPrimaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).mAccent1.getS100());
    }
}
