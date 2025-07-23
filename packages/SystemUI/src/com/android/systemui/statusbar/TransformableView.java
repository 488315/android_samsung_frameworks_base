package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.TransformState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface TransformableView {
    TransformState getCurrentState(int i);

    void setVisible(boolean z);

    void transformFrom(float f, TransformableView transformableView);

    void transformFrom(TransformableView transformableView);

    void transformTo(float f, TransformableView transformableView);

    void transformTo(TransformableView transformableView, Runnable runnable);
}
