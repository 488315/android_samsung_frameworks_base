package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.TransformState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface TransformableView {
    TransformState getCurrentState(int i);

    void setVisible(boolean z);

    void transformFrom(float f, TransformableView transformableView);

    void transformFrom(TransformableView transformableView);

    void transformTo(float f, TransformableView transformableView);

    void transformTo(TransformableView transformableView, Runnable runnable);
}
