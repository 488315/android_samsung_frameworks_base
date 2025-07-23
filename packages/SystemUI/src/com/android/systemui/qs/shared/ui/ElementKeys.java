package com.android.systemui.qs.shared.ui;

import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.ElementKey$Companion$withIdentity$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ElementKeys {
    public static final ElementKey$Companion$withIdentity$1 TileElementMatcher;
    public static final ElementKeys INSTANCE = new ElementKeys();
    public static final ElementKey QuickSettingsContent = new ElementKey("QuickSettingsContent", null, null, false, 14, null);
    public static final ElementKey GridAnchor = new ElementKey("QuickSettingsGridAnchor", null, null, false, 14, null);
    public static final ElementKey FooterActions = new ElementKey("FooterActions", null, null, false, 14, null);

    static {
        ElementKey.Companion companion = ElementKey.Companion;
        ElementKeys$$ExternalSyntheticLambda0 elementKeys$$ExternalSyntheticLambda0 = new ElementKeys$$ExternalSyntheticLambda0();
        companion.getClass();
        TileElementMatcher = new ElementKey$Companion$withIdentity$1(elementKeys$$ExternalSyntheticLambda0);
    }

    private ElementKeys() {
    }
}
