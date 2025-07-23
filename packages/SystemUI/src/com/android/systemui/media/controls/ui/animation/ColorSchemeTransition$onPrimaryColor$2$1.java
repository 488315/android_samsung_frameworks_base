package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class ColorSchemeTransition$onPrimaryColor$2$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$onPrimaryColor$2$1 INSTANCE = new ColorSchemeTransition$onPrimaryColor$2$1();

    public ColorSchemeTransition$onPrimaryColor$2$1() {
        super(1, MediaColorSchemesKt.class, "onPrimaryFromScheme", "onPrimaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        DynamicScheme dynamicScheme = ((ColorScheme) obj).mMaterialScheme;
        dynamicScheme.getClass();
        return Integer.valueOf(new MaterialDynamicColors().onPrimaryFixed().getArgb(dynamicScheme));
    }
}
