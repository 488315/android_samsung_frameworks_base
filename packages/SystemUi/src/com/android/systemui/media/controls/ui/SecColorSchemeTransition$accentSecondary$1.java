package com.android.systemui.media.controls.ui;

import com.android.systemui.monet.ColorScheme;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class SecColorSchemeTransition$accentSecondary$1 extends FunctionReferenceImpl implements Function1 {
    public static final SecColorSchemeTransition$accentSecondary$1 INSTANCE = new SecColorSchemeTransition$accentSecondary$1();

    public SecColorSchemeTransition$accentSecondary$1() {
        super(1, MediaColorSchemesKt.class, "accentSecondaryFromScheme", "accentSecondaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Integer.valueOf(((Number) ((ArrayList) ((ColorScheme) obj).accent1.allShades).get(3)).intValue());
    }
}
