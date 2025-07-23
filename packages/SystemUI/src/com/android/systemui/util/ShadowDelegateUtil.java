package com.android.systemui.util;

import android.graphics.drawable.Drawable;
import com.android.systemui.shared.shadow.DoubleShadowIconDrawable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadowDelegateUtil {
    public static final int $stable = 0;
    public static final ShadowDelegateUtil INSTANCE = new ShadowDelegateUtil();

    private ShadowDelegateUtil() {
    }

    public final Drawable createShadowDrawable(Drawable drawable, float f, float f2, int i) {
        return new DoubleShadowIconDrawable(new DoubleShadowTextHelper.ShadowInfo(f, 0.0f, 0.0f, f2), new DoubleShadowTextHelper.ShadowInfo(f, 0.0f, 0.0f, 0.0f), drawable, i, 0);
    }
}
