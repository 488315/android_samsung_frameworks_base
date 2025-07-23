package com.android.systemui.media.controls.ui;

import com.android.systemui.media.controls.ui.animation.MediaColorSchemesKt;
import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class SecColorSchemeTransition$accentSecondary$1 extends FunctionReferenceImpl implements Function1 {
    public static final SecColorSchemeTransition$accentSecondary$1 INSTANCE = new SecColorSchemeTransition$accentSecondary$1();

    public SecColorSchemeTransition$accentSecondary$1() {
        super(1, MediaColorSchemesKt.class, "accentSecondaryFromScheme", "accentSecondaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).mAccent1.getS200());
    }
}
