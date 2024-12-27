package com.android.systemui.media.taptotransfer.sender;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaTttSenderUiEvents implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ MediaTttSenderUiEvents[] $VALUES;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_END_CAST;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_START_CAST;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_FAR_FROM_RECEIVER;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_FAILED;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_SUCCEEDED;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_TRIGGERED;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_FAILED;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_SUCCEEDED;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_TRIGGERED;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED;
    public static final MediaTttSenderUiEvents MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED;
    private final int metricId;

    static {
        MediaTttSenderUiEvents mediaTttSenderUiEvents = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED", 0, 971);
        MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED = mediaTttSenderUiEvents;
        MediaTttSenderUiEvents mediaTttSenderUiEvents2 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED", 1, 972);
        MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED = mediaTttSenderUiEvents2;
        MediaTttSenderUiEvents mediaTttSenderUiEvents3 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_START_CAST", 2, 973);
        MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_START_CAST = mediaTttSenderUiEvents3;
        MediaTttSenderUiEvents mediaTttSenderUiEvents4 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_END_CAST", 3, 974);
        MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_END_CAST = mediaTttSenderUiEvents4;
        MediaTttSenderUiEvents mediaTttSenderUiEvents5 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_TRIGGERED", 4, 975);
        MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_TRIGGERED = mediaTttSenderUiEvents5;
        MediaTttSenderUiEvents mediaTttSenderUiEvents6 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_TRIGGERED", 5, 976);
        MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_TRIGGERED = mediaTttSenderUiEvents6;
        MediaTttSenderUiEvents mediaTttSenderUiEvents7 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_SUCCEEDED", 6, 977);
        MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_SUCCEEDED = mediaTttSenderUiEvents7;
        MediaTttSenderUiEvents mediaTttSenderUiEvents8 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_SUCCEEDED", 7, 978);
        MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_SUCCEEDED = mediaTttSenderUiEvents8;
        MediaTttSenderUiEvents mediaTttSenderUiEvents9 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_FAILED", 8, 979);
        MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_FAILED = mediaTttSenderUiEvents9;
        MediaTttSenderUiEvents mediaTttSenderUiEvents10 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_FAILED", 9, 980);
        MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_FAILED = mediaTttSenderUiEvents10;
        MediaTttSenderUiEvents mediaTttSenderUiEvents11 = new MediaTttSenderUiEvents("MEDIA_TTT_SENDER_FAR_FROM_RECEIVER", 10, 981);
        MEDIA_TTT_SENDER_FAR_FROM_RECEIVER = mediaTttSenderUiEvents11;
        MediaTttSenderUiEvents[] mediaTttSenderUiEventsArr = {mediaTttSenderUiEvents, mediaTttSenderUiEvents2, mediaTttSenderUiEvents3, mediaTttSenderUiEvents4, mediaTttSenderUiEvents5, mediaTttSenderUiEvents6, mediaTttSenderUiEvents7, mediaTttSenderUiEvents8, mediaTttSenderUiEvents9, mediaTttSenderUiEvents10, mediaTttSenderUiEvents11};
        $VALUES = mediaTttSenderUiEventsArr;
        EnumEntriesKt.enumEntries(mediaTttSenderUiEventsArr);
    }

    private MediaTttSenderUiEvents(String str, int i, int i2) {
        this.metricId = i2;
    }

    public static MediaTttSenderUiEvents valueOf(String str) {
        return (MediaTttSenderUiEvents) Enum.valueOf(MediaTttSenderUiEvents.class, str);
    }

    public static MediaTttSenderUiEvents[] values() {
        return (MediaTttSenderUiEvents[]) $VALUES.clone();
    }

    public final int getId() {
        return this.metricId;
    }
}
