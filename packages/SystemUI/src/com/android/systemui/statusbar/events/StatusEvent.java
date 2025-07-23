package com.android.systemui.statusbar.events;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
