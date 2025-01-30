package com.android.systemui.statusbar.events;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface BackgroundAnimatableView {
    int getChipWidth();

    View getContentView();

    View getView();

    void setBoundsForAnimation(int i, int i2, int i3, int i4);
}
