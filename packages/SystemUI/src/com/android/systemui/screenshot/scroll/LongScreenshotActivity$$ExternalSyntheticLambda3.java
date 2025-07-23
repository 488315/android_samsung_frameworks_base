package com.android.systemui.screenshot.scroll;

import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LongScreenshotActivity$$ExternalSyntheticLambda3 implements View.OnApplyWindowInsetsListener {
    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        int i = LongScreenshotActivity.$r8$clinit;
        Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return WindowInsets.CONSUMED;
    }
}
