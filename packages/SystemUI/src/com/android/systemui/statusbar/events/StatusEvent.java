package com.android.systemui.statusbar.events;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
