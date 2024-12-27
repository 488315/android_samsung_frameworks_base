package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.media.taptotransfer.sender.SenderEndItem;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public abstract class ChipStateSender {
    public static final /* synthetic */ ChipStateSender[] $VALUES;
    public static final Companion Companion;
    public static final ChipStateSender FAR_FROM_RECEIVER;
    public static final ChipStateSender TRANSFER_TO_RECEIVER_FAILED;
    public static final ChipStateSender TRANSFER_TO_RECEIVER_SUCCEEDED;
    public static final ChipStateSender TRANSFER_TO_RECEIVER_TRIGGERED;
    public static final ChipStateSender TRANSFER_TO_THIS_DEVICE_FAILED;
    public static final ChipStateSender TRANSFER_TO_THIS_DEVICE_SUCCEEDED;
    public static final ChipStateSender TRANSFER_TO_THIS_DEVICE_TRIGGERED;
    private final SenderEndItem endItem;
    private final int stateInt;
    private final Integer stringResId;
    private final TimeoutLength timeoutLength;
    private final TransferStatus transferStatus;
    private final UiEventLogger.UiEventEnum uiEvent;

    public final class Companion {
        private Companion() {
        }

        public static final boolean access$stateIsStartOfSequence(Companion companion, ChipStateSender chipStateSender) {
            companion.getClass();
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender.getTransferStatus() == TransferStatus.NOT_STARTED || chipStateSender.getTransferStatus() == TransferStatus.IN_PROGRESS;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    final class FAR_FROM_RECEIVER extends ChipStateSender {
        public FAR_FROM_RECEIVER(String str, int i) {
            super(str, i, 8, MediaTttSenderUiEvents.MEDIA_TTT_SENDER_FAR_FROM_RECEIVER, null, TransferStatus.TOO_FAR, null, null, 32, null);
        }

        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final Text getChipTextString(Context context, String str) {
            throw new IllegalArgumentException("FAR_FROM_RECEIVER should never be displayed, so its string should never be fetched");
        }

        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender);
        }
    }

    static {
        ChipStateSender chipStateSender = new ChipStateSender("ALMOST_CLOSE_TO_START_CAST", 0) { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.ALMOST_CLOSE_TO_START_CAST
            {
                MediaTttSenderUiEvents mediaTttSenderUiEvents = MediaTttSenderUiEvents.MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_START_CAST;
                Integer valueOf = Integer.valueOf(R.string.media_move_closer_to_start_cast);
                TransferStatus transferStatus = TransferStatus.NOT_STARTED;
                TimeoutLength timeoutLength = TimeoutLength.LONG;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i = 0;
                SenderEndItem senderEndItem = null;
            }

            @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
            public final boolean isValidNextState(ChipStateSender chipStateSender2) {
                return chipStateSender2 == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender2 == ChipStateSender.TRANSFER_TO_RECEIVER_TRIGGERED;
            }
        };
        ChipStateSender chipStateSender2 = new ChipStateSender("ALMOST_CLOSE_TO_END_CAST", 1) { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.ALMOST_CLOSE_TO_END_CAST
            {
                MediaTttSenderUiEvents mediaTttSenderUiEvents = MediaTttSenderUiEvents.MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_END_CAST;
                Integer valueOf = Integer.valueOf(R.string.media_move_closer_to_end_cast);
                TransferStatus transferStatus = TransferStatus.NOT_STARTED;
                TimeoutLength timeoutLength = TimeoutLength.LONG;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i = 1;
                SenderEndItem senderEndItem = null;
            }

            @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
            public final boolean isValidNextState(ChipStateSender chipStateSender3) {
                return chipStateSender3 == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender3 == ChipStateSender.TRANSFER_TO_THIS_DEVICE_TRIGGERED;
            }
        };
        ChipStateSender chipStateSender3 = new ChipStateSender("TRANSFER_TO_RECEIVER_TRIGGERED", 2) { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_TRIGGERED
            {
                MediaTttSenderUiEvents mediaTttSenderUiEvents = MediaTttSenderUiEvents.MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_TRIGGERED;
                Integer valueOf = Integer.valueOf(R.string.media_transfer_playing_different_device);
                TransferStatus transferStatus = TransferStatus.IN_PROGRESS;
                SenderEndItem.Loading loading = SenderEndItem.Loading.INSTANCE;
                TimeoutLength timeoutLength = TimeoutLength.LONG;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i = 2;
            }

            @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
            public final boolean isValidNextState(ChipStateSender chipStateSender4) {
                return chipStateSender4 == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender4 == ChipStateSender.TRANSFER_TO_RECEIVER_SUCCEEDED || chipStateSender4 == ChipStateSender.TRANSFER_TO_RECEIVER_FAILED;
            }
        };
        TRANSFER_TO_RECEIVER_TRIGGERED = chipStateSender3;
        ChipStateSender chipStateSender4 = new ChipStateSender("TRANSFER_TO_THIS_DEVICE_TRIGGERED", 3) { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_TRIGGERED
            {
                MediaTttSenderUiEvents mediaTttSenderUiEvents = MediaTttSenderUiEvents.MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_TRIGGERED;
                Integer valueOf = Integer.valueOf(R.string.media_transfer_playing_this_device);
                TransferStatus transferStatus = TransferStatus.IN_PROGRESS;
                SenderEndItem.Loading loading = SenderEndItem.Loading.INSTANCE;
                TimeoutLength timeoutLength = TimeoutLength.LONG;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i = 3;
            }

            @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
            public final boolean isValidNextState(ChipStateSender chipStateSender5) {
                return chipStateSender5 == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender5 == ChipStateSender.TRANSFER_TO_THIS_DEVICE_SUCCEEDED || chipStateSender5 == ChipStateSender.TRANSFER_TO_THIS_DEVICE_FAILED;
            }
        };
        TRANSFER_TO_THIS_DEVICE_TRIGGERED = chipStateSender4;
        ChipStateSender chipStateSender5 = new ChipStateSender("TRANSFER_TO_RECEIVER_SUCCEEDED", 4) { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_SUCCEEDED
            {
                MediaTttSenderUiEvents mediaTttSenderUiEvents = MediaTttSenderUiEvents.MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_SUCCEEDED;
                Integer valueOf = Integer.valueOf(R.string.media_transfer_playing_different_device);
                TransferStatus transferStatus = TransferStatus.SUCCEEDED;
                SenderEndItem.UndoButton undoButton = new SenderEndItem.UndoButton(MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED, 3);
                int i = 32;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i2 = 4;
                TimeoutLength timeoutLength = null;
            }

            @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
            public final boolean isValidNextState(ChipStateSender chipStateSender6) {
                return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender6);
            }
        };
        TRANSFER_TO_RECEIVER_SUCCEEDED = chipStateSender5;
        ChipStateSender chipStateSender6 = new ChipStateSender("TRANSFER_TO_THIS_DEVICE_SUCCEEDED", 5) { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_SUCCEEDED
            {
                MediaTttSenderUiEvents mediaTttSenderUiEvents = MediaTttSenderUiEvents.MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_SUCCEEDED;
                Integer valueOf = Integer.valueOf(R.string.media_transfer_playing_this_device);
                TransferStatus transferStatus = TransferStatus.SUCCEEDED;
                SenderEndItem.UndoButton undoButton = new SenderEndItem.UndoButton(MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED, 2);
                int i = 32;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i2 = 5;
                TimeoutLength timeoutLength = null;
            }

            @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
            public final boolean isValidNextState(ChipStateSender chipStateSender7) {
                return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender7);
            }
        };
        TRANSFER_TO_THIS_DEVICE_SUCCEEDED = chipStateSender6;
        ChipStateSender chipStateSender7 = new ChipStateSender("TRANSFER_TO_RECEIVER_FAILED", 6) { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_FAILED
            {
                MediaTttSenderUiEvents mediaTttSenderUiEvents = MediaTttSenderUiEvents.MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_FAILED;
                Integer valueOf = Integer.valueOf(R.string.media_transfer_failed);
                TransferStatus transferStatus = TransferStatus.FAILED;
                SenderEndItem.Error error = SenderEndItem.Error.INSTANCE;
                int i = 32;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i2 = 6;
                TimeoutLength timeoutLength = null;
            }

            @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
            public final boolean isValidNextState(ChipStateSender chipStateSender8) {
                return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender8);
            }
        };
        TRANSFER_TO_RECEIVER_FAILED = chipStateSender7;
        ChipStateSender chipStateSender8 = new ChipStateSender("TRANSFER_TO_THIS_DEVICE_FAILED", 7) { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_FAILED
            {
                MediaTttSenderUiEvents mediaTttSenderUiEvents = MediaTttSenderUiEvents.MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_FAILED;
                Integer valueOf = Integer.valueOf(R.string.media_transfer_failed);
                TransferStatus transferStatus = TransferStatus.FAILED;
                SenderEndItem.Error error = SenderEndItem.Error.INSTANCE;
                int i = 32;
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i2 = 7;
                TimeoutLength timeoutLength = null;
            }

            @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
            public final boolean isValidNextState(ChipStateSender chipStateSender9) {
                return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender9);
            }
        };
        TRANSFER_TO_THIS_DEVICE_FAILED = chipStateSender8;
        FAR_FROM_RECEIVER far_from_receiver = new FAR_FROM_RECEIVER("FAR_FROM_RECEIVER", 8);
        FAR_FROM_RECEIVER = far_from_receiver;
        ChipStateSender[] chipStateSenderArr = {chipStateSender, chipStateSender2, chipStateSender3, chipStateSender4, chipStateSender5, chipStateSender6, chipStateSender7, chipStateSender8, far_from_receiver};
        $VALUES = chipStateSenderArr;
        EnumEntriesKt.enumEntries(chipStateSenderArr);
        Companion = new Companion(null);
    }

    public /* synthetic */ ChipStateSender(String str, int i, int i2, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, TimeoutLength timeoutLength, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, uiEventEnum, num, transferStatus, senderEndItem, timeoutLength);
    }

    public static ChipStateSender valueOf(String str) {
        return (ChipStateSender) Enum.valueOf(ChipStateSender.class, str);
    }

    public static ChipStateSender[] values() {
        return (ChipStateSender[]) $VALUES.clone();
    }

    public Text getChipTextString(Context context, String str) {
        Integer num = this.stringResId;
        Intrinsics.checkNotNull(num);
        return new Text.Loaded(context.getString(num.intValue(), str));
    }

    public final SenderEndItem getEndItem() {
        return this.endItem;
    }

    public final int getStateInt() {
        return this.stateInt;
    }

    public final TimeoutLength getTimeoutLength() {
        return this.timeoutLength;
    }

    public final TransferStatus getTransferStatus() {
        return this.transferStatus;
    }

    public final UiEventLogger.UiEventEnum getUiEvent() {
        return this.uiEvent;
    }

    public abstract boolean isValidNextState(ChipStateSender chipStateSender);

    private ChipStateSender(String str, int i, int i2, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, TimeoutLength timeoutLength) {
        this.stateInt = i2;
        this.uiEvent = uiEventEnum;
        this.stringResId = num;
        this.transferStatus = transferStatus;
        this.endItem = senderEndItem;
        this.timeoutLength = timeoutLength;
    }

    public /* synthetic */ ChipStateSender(String str, int i, int i2, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, TimeoutLength timeoutLength, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, uiEventEnum, num, transferStatus, senderEndItem, (i3 & 32) != 0 ? TimeoutLength.DEFAULT : timeoutLength);
    }
}
