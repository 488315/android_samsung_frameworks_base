package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.TransformState;

public interface TransformableView {
    TransformState getCurrentState(int i);

    void setVisible(boolean z);

    void transformFrom(float f, TransformableView transformableView);

    void transformFrom(TransformableView transformableView);

    void transformTo(float f, TransformableView transformableView);

    void transformTo(TransformableView transformableView, Runnable runnable);
}
