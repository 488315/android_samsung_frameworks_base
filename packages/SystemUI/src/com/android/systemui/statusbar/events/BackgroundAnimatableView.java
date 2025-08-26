package com.android.systemui.statusbar.events;

import android.view.View;

/* loaded from: classes3.dex */
public interface BackgroundAnimatableView {
    default View getContentView() {
        return null;
    }

    void setBoundsForAnimation(int i, int i2, int i3, int i4);
}
