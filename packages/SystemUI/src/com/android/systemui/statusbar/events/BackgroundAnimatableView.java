package com.android.systemui.statusbar.events;

import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface BackgroundAnimatableView {
    default View getContentView() {
        return null;
    }

    void setBoundsForAnimation(int i, int i2, int i3, int i4);
}
