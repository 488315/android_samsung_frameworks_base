package com.android.systemui.privacy;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum PrivacyChipEvent implements UiEventLogger.UiEventEnum {
    ONGOING_INDICATORS_CHIP_VIEW(601),
    ONGOING_INDICATORS_CHIP_CLICK(602);

    private final int _id;

    PrivacyChipEvent(int i) {
        this._id = i;
    }

    public final int getId() {
        return this._id;
    }
}
