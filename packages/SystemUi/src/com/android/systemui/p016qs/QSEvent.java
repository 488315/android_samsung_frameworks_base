package com.android.systemui.p016qs;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum QSEvent implements UiEventLogger.UiEventEnum {
    QS_ACTION_CLICK(387),
    QS_ACTION_SECONDARY_CLICK(388),
    QS_ACTION_LONG_PRESS(389),
    QS_PANEL_EXPANDED(390),
    QS_PANEL_COLLAPSED(391),
    QS_TILE_VISIBLE(392),
    QQS_PANEL_EXPANDED(393),
    QQS_PANEL_COLLAPSED(394),
    QQS_TILE_VISIBLE(395);

    private final int _id;

    QSEvent(int i) {
        this._id = i;
    }

    public final int getId() {
        return this._id;
    }
}
