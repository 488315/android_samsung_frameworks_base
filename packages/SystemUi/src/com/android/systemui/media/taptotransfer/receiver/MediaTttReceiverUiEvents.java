package com.android.systemui.media.taptotransfer.receiver;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum MediaTttReceiverUiEvents implements UiEventLogger.UiEventEnum {
    MEDIA_TTT_RECEIVER_CLOSE_TO_SENDER(982),
    MEDIA_TTT_RECEIVER_FAR_FROM_SENDER(983),
    MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_SUCCEEDED(1263),
    MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_FAILED(1264);

    private final int metricId;

    MediaTttReceiverUiEvents(int i) {
        this.metricId = i;
    }

    public final int getId() {
        return this.metricId;
    }
}
