package com.android.systemui.screenshot.scroll;

import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;

public final /* synthetic */ class LongScreenshotActivity$$ExternalSyntheticLambda3 implements View.OnApplyWindowInsetsListener {
    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        int i = LongScreenshotActivity.$r8$clinit;
        Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return WindowInsets.CONSUMED;
    }
}
