package com.android.systemui.media.taptotransfer.receiver;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class MediaTttReceiverUiEvents implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ MediaTttReceiverUiEvents[] $VALUES;
    public static final MediaTttReceiverUiEvents MEDIA_TTT_RECEIVER_CLOSE_TO_SENDER;
    public static final MediaTttReceiverUiEvents MEDIA_TTT_RECEIVER_FAR_FROM_SENDER;
    public static final MediaTttReceiverUiEvents MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_FAILED;
    public static final MediaTttReceiverUiEvents MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_SUCCEEDED;
    private final int metricId;

    static {
        MediaTttReceiverUiEvents mediaTttReceiverUiEvents = new MediaTttReceiverUiEvents("MEDIA_TTT_RECEIVER_CLOSE_TO_SENDER", 0, 982);
        MEDIA_TTT_RECEIVER_CLOSE_TO_SENDER = mediaTttReceiverUiEvents;
        MediaTttReceiverUiEvents mediaTttReceiverUiEvents2 = new MediaTttReceiverUiEvents("MEDIA_TTT_RECEIVER_FAR_FROM_SENDER", 1, 983);
        MEDIA_TTT_RECEIVER_FAR_FROM_SENDER = mediaTttReceiverUiEvents2;
        MediaTttReceiverUiEvents mediaTttReceiverUiEvents3 = new MediaTttReceiverUiEvents("MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_SUCCEEDED", 2, 1263);
        MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_SUCCEEDED = mediaTttReceiverUiEvents3;
        MediaTttReceiverUiEvents mediaTttReceiverUiEvents4 = new MediaTttReceiverUiEvents("MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_FAILED", 3, 1264);
        MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_FAILED = mediaTttReceiverUiEvents4;
        MediaTttReceiverUiEvents[] mediaTttReceiverUiEventsArr = {mediaTttReceiverUiEvents, mediaTttReceiverUiEvents2, mediaTttReceiverUiEvents3, mediaTttReceiverUiEvents4};
        $VALUES = mediaTttReceiverUiEventsArr;
        EnumEntriesKt.enumEntries(mediaTttReceiverUiEventsArr);
    }

    private MediaTttReceiverUiEvents(String str, int i, int i2) {
        this.metricId = i2;
    }

    public static MediaTttReceiverUiEvents valueOf(String str) {
        return (MediaTttReceiverUiEvents) Enum.valueOf(MediaTttReceiverUiEvents.class, str);
    }

    public static MediaTttReceiverUiEvents[] values() {
        return (MediaTttReceiverUiEvents[]) $VALUES.clone();
    }

    public final int getId() {
        return this.metricId;
    }
}
