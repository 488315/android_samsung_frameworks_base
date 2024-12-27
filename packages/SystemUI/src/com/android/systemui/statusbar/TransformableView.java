package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.TransformState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface TransformableView {
    TransformState getCurrentState(int i);

    void setVisible(boolean z);

    void transformFrom(float f, TransformableView transformableView);

    void transformFrom(TransformableView transformableView);

    void transformTo(float f, TransformableView transformableView);

    void transformTo(TransformableView transformableView, Runnable runnable);
}
