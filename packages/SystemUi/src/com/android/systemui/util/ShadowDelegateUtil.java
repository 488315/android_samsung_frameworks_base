package com.android.systemui.util;

import android.graphics.drawable.Drawable;
import com.android.systemui.shared.shadow.DoubleShadowIconDrawable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadowDelegateUtil {
    public static final ShadowDelegateUtil INSTANCE = new ShadowDelegateUtil();

    private ShadowDelegateUtil() {
    }

    public static DoubleShadowIconDrawable createShadowDrawable(Drawable drawable, float f, float f2, int i) {
        return new DoubleShadowIconDrawable(new DoubleShadowTextHelper.ShadowInfo(f, 0.0f, 0.0f, f2), new DoubleShadowTextHelper.ShadowInfo(f, 0.0f, 0.0f, 0.0f), drawable, i, 0);
    }
}
