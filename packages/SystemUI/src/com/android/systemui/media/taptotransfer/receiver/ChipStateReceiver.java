package com.android.systemui.media.taptotransfer.receiver;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ChipStateReceiver {
    public static final /* synthetic */ ChipStateReceiver[] $VALUES;
    public static final ChipStateReceiver CLOSE_TO_SENDER;
    public static final Companion Companion;
    private final int stateInt;
    private final UiEventLogger.UiEventEnum uiEvent;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        ChipStateReceiver chipStateReceiver = new ChipStateReceiver("CLOSE_TO_SENDER", 0, 0, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_CLOSE_TO_SENDER);
        CLOSE_TO_SENDER = chipStateReceiver;
        ChipStateReceiver[] chipStateReceiverArr = {chipStateReceiver, new ChipStateReceiver("FAR_FROM_SENDER", 1, 1, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_FAR_FROM_SENDER), new ChipStateReceiver("TRANSFER_TO_RECEIVER_SUCCEEDED", 2, 2, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_SUCCEEDED), new ChipStateReceiver("TRANSFER_TO_RECEIVER_FAILED", 3, 3, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_FAILED)};
        $VALUES = chipStateReceiverArr;
        EnumEntriesKt.enumEntries(chipStateReceiverArr);
        Companion = new Companion(null);
    }

    private ChipStateReceiver(String str, int i, int i2, UiEventLogger.UiEventEnum uiEventEnum) {
        this.stateInt = i2;
        this.uiEvent = uiEventEnum;
    }

    public static ChipStateReceiver valueOf(String str) {
        return (ChipStateReceiver) Enum.valueOf(ChipStateReceiver.class, str);
    }

    public static ChipStateReceiver[] values() {
        return (ChipStateReceiver[]) $VALUES.clone();
    }

    public final int getStateInt() {
        return this.stateInt;
    }

    public final UiEventLogger.UiEventEnum getUiEvent() {
        return this.uiEvent;
    }
}
