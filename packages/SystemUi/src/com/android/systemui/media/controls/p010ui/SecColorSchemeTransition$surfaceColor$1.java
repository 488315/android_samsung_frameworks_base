package com.android.systemui.media.controls.p010ui;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class SecColorSchemeTransition$surfaceColor$1 extends FunctionReferenceImpl implements Function1 {
    public static final SecColorSchemeTransition$surfaceColor$1 INSTANCE = new SecColorSchemeTransition$surfaceColor$1();

    public SecColorSchemeTransition$surfaceColor$1() {
        super(1, MediaColorSchemesKt.class, "surfaceFromScheme", "surfaceFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).accent2.getS800());
    }
}
