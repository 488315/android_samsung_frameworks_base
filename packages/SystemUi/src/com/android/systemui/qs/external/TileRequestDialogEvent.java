package com.android.systemui.qs.external;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum TileRequestDialogEvent implements UiEventLogger.UiEventEnum {
    TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED(917),
    TILE_REQUEST_DIALOG_SHOWN(918),
    TILE_REQUEST_DIALOG_DISMISSED(919),
    TILE_REQUEST_DIALOG_TILE_ADDED(920),
    TILE_REQUEST_DIALOG_TILE_NOT_ADDED(921);

    private final int _id;

    TileRequestDialogEvent(int i) {
        this._id = i;
    }

    public final int getId() {
        return this._id;
    }
}
