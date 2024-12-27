package com.android.systemui.statusbar.events;

import android.view.View;

public interface BackgroundAnimatableView {
    default View getContentView() {
        return null;
    }

    void setBoundsForAnimation(int i, int i2, int i3, int i4);
}
