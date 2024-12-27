package com.android.systemui.statusbar.events;

import kotlin.jvm.functions.Function1;

public interface StatusEvent {
    String getContentDescription();

    boolean getForceVisible();

    int getPriority();

    boolean getShowAnimation();

    Function1 getViewCreator();

    void setForceVisible();

    default boolean shouldUpdateFromEvent(StatusEvent statusEvent) {
        return false;
    }

    default void updateFromEvent(StatusEvent statusEvent) {
    }
}
