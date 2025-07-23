package com.android.systemui.statusbar;

import com.android.systemui.plugins.DarkIconDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
