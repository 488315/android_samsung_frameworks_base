package com.samsung.systemui.splugins.navigationbar;

import android.graphics.Point;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
