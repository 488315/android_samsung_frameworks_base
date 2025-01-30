package com.samsung.systemui.splugins.navigationbar;

import android.graphics.Point;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface LayoutProvider {
    int getButtonDistanceSize(Point point, boolean z);

    int getButtonWidth(Point point, boolean z);

    String getGesturalLayout(boolean z, boolean z2);

    int getGestureWidth(Point point, boolean z);

    String getLayout(boolean z);

    String getLayout(boolean z, int i);

    int getSpaceSidePadding(Point point, boolean z);

    default int getSpaceSidePadding(Point point, boolean z, boolean z2) {
        return 0;
    }

    int getSpaceWidth(Point point, boolean z, boolean z2);

    int getVerticalLayoutID(boolean z);

    default void onSettingChanged(int i, String str) {
    }
}
