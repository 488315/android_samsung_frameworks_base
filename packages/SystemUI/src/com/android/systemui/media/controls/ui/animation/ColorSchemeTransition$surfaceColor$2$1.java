package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class ColorSchemeTransition$surfaceColor$2$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$surfaceColor$2$1 INSTANCE = new ColorSchemeTransition$surfaceColor$2$1();

    public ColorSchemeTransition$surfaceColor$2$1() {
        super(1, MediaColorSchemesKt.class, "surfaceFromScheme", "surfaceFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).mAccent2.getS800());
    }
}
