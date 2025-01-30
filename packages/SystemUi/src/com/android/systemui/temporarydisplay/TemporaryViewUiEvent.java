package com.android.systemui.temporarydisplay;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum TemporaryViewUiEvent implements UiEventLogger.UiEventEnum {
    TEMPORARY_VIEW_ADDED(1389),
    TEMPORARY_VIEW_MANUALLY_DISMISSED(1390);

    private final int metricId;

    TemporaryViewUiEvent(int i) {
        this.metricId = i;
    }

    public final int getId() {
        return this.metricId;
    }
}
