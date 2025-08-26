package com.android.systemui.statusbar;

import com.android.systemui.plugins.DarkIconDispatcher;

/* loaded from: classes3.dex */
public interface StatusIconDisplayable extends DarkIconDispatcher.DarkReceiver {
    String getSlot();

    int getVisibleState();

    default boolean isIconBlocked() {
        return false;
    }

    boolean isIconVisible();

    void setDecorColor(int i);

    void setStaticDrawableColor(int i);

    default void setStaticDrawableColor(int i, int i2) {
        setStaticDrawableColor(i);
    }

    default void setVisibleState(int i) {
        setVisibleState(2, false);
    }

    void setVisibleState(int i, boolean z);
}
