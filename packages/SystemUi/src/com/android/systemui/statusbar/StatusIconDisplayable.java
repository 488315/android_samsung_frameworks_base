package com.android.systemui.statusbar;

import com.android.systemui.plugins.DarkIconDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface StatusIconDisplayable extends DarkIconDispatcher.DarkReceiver {
    String getSlot();

    int getVisibleState();

    default boolean isIconBlocked() {
        return false;
    }

    boolean isIconVisible();

    void setDecorColor(int i);

    void setStaticDrawableColor(int i);

    default void setVisibleState(int i) {
        setVisibleState(i, false);
    }

    void setVisibleState(int i, boolean z);
}
