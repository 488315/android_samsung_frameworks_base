package com.android.systemui.statusbar.events;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface StatusEvent {
    String getContentDescription();

    boolean getForceVisible();

    int getPriority();

    boolean getShowAnimation();

    Function1 getViewCreator();

    void setForceVisible();

    boolean shouldUpdateFromEvent(StatusEvent statusEvent);

    void updateFromEvent(StatusEvent statusEvent);
}
