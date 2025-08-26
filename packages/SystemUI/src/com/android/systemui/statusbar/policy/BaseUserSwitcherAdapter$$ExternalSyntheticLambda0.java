package com.android.systemui.statusbar.policy;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class BaseUserSwitcherAdapter$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        BaseUserSwitcherAdapter.Companion companion = BaseUserSwitcherAdapter.Companion;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        return new ColorMatrixColorFilter(colorMatrix);
    }
}
