package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class ColorSchemeTransition$textPrimaryInverse$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$textPrimaryInverse$1 INSTANCE = new ColorSchemeTransition$textPrimaryInverse$1();

    public ColorSchemeTransition$textPrimaryInverse$1() {
        super(1, MediaColorSchemesKt.class, "textPrimaryInverseFromScheme", "textPrimaryInverseFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Integer num = (Integer) ((ColorScheme) obj).mNeutral1.allShades.get(11);
        num.intValue();
        return num;
    }
}
