package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class ColorSchemeTransition$textPrimaryInverse$2$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$textPrimaryInverse$2$1 INSTANCE = new ColorSchemeTransition$textPrimaryInverse$2$1();

    public ColorSchemeTransition$textPrimaryInverse$2$1() {
        super(1, MediaColorSchemesKt.class, "textPrimaryInverseFromScheme", "textPrimaryInverseFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Integer num = (Integer) ((ColorScheme) obj).mNeutral1.allShades.get(11);
        num.intValue();
        return num;
    }
}
