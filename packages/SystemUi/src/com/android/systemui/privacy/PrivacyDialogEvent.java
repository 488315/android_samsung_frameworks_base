package com.android.systemui.privacy;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum PrivacyDialogEvent implements UiEventLogger.UiEventEnum {
    PRIVACY_DIALOG_ITEM_CLICKED_TO_APP_SETTINGS(904),
    PRIVACY_DIALOG_DISMISSED(905);

    private final int _id;

    PrivacyDialogEvent(int i) {
        this._id = i;
    }

    public final int getId() {
        return this._id;
    }
}
