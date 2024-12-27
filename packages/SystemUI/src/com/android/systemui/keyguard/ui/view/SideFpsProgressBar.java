package com.android.systemui.keyguard.ui.view;

import android.view.LayoutInflater;
import android.view.WindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SideFpsProgressBar {
    public final LayoutInflater layoutInflater;
    public final WindowManager.LayoutParams overlayViewParams;
    public final WindowManager windowManager;

    public SideFpsProgressBar(LayoutInflater layoutInflater, WindowManager windowManager) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2024, 16777496, -2);
        layoutParams.setTitle("SideFpsProgressBar");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags = 536870976;
    }
}
