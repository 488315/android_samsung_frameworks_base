package com.android.systemui.keyguard.ui.view;

import android.view.LayoutInflater;
import android.view.WindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SideFpsProgressBar {
    public final LayoutInflater layoutInflater;
    public final WindowManager.LayoutParams overlayViewParams;
    public final WindowManager windowManager;

    public SideFpsProgressBar(LayoutInflater layoutInflater, WindowManager windowManager) {
        this.layoutInflater = layoutInflater;
        this.windowManager = windowManager;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2024, 16777496, -2);
        layoutParams.setTitle("SideFpsProgressBar");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags = 536870976;
        this.overlayViewParams = layoutParams;
    }
}
