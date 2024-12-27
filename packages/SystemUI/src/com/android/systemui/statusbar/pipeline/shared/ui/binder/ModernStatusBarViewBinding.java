package com.android.systemui.statusbar.pipeline.shared.ui.binder;

public interface ModernStatusBarViewBinding {
    boolean getShouldIconBeVisible();

    boolean isCollecting();

    void onDecorTintChanged(int i);

    void onIconTintChanged(int i, int i2);

    void onVisibilityStateChanged(int i);
}
