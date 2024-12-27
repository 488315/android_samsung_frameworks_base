package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class ColorSchemeTransition$textTertiary$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$textTertiary$1 INSTANCE = new ColorSchemeTransition$textTertiary$1();

    public ColorSchemeTransition$textTertiary$1() {
        super(1, MediaColorSchemesKt.class, "textTertiaryFromScheme", "textTertiaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Integer num = (Integer) ((ColorScheme) obj).mNeutral2.allShades.get(6);
        num.intValue();
        return num;
    }
}
