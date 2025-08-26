package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class ColorSchemeTransition$accentPrimary$2$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$accentPrimary$2$1 INSTANCE = new ColorSchemeTransition$accentPrimary$2$1();

    public ColorSchemeTransition$accentPrimary$2$1() {
        super(1, MediaColorSchemesKt.class, "accentPrimaryFromScheme", "accentPrimaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).mAccent1.getS100());
    }
}
