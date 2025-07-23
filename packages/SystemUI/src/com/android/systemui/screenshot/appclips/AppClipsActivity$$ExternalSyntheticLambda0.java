package com.android.systemui.screenshot.appclips;

import android.content.pm.PackageManager;
import android.view.View;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppClipsActivity$$ExternalSyntheticLambda0 implements OnApplyWindowInsetsListener {
    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
        PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
        Insets insets = windowInsetsCompat.mImpl.getInsets(135);
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return WindowInsetsCompat.CONSUMED;
    }
}
