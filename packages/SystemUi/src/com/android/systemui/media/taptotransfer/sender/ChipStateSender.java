package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.common.shared.model.Text;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum ChipStateSender {
    /* JADX INFO: Fake field, exist only in values array */
    ALMOST_CLOSE_TO_START_CAST { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.ALMOST_CLOSE_TO_START_CAST
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.TRANSFER_TO_RECEIVER_TRIGGERED;
        }
    },
    /* JADX INFO: Fake field, exist only in values array */
    ALMOST_CLOSE_TO_END_CAST { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.ALMOST_CLOSE_TO_END_CAST
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.TRANSFER_TO_THIS_DEVICE_TRIGGERED;
        }
    },
    TRANSFER_TO_RECEIVER_TRIGGERED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_TRIGGERED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.TRANSFER_TO_RECEIVER_SUCCEEDED || chipStateSender == ChipStateSender.TRANSFER_TO_RECEIVER_FAILED;
        }
    },
    TRANSFER_TO_THIS_DEVICE_TRIGGERED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_TRIGGERED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.TRANSFER_TO_THIS_DEVICE_SUCCEEDED || chipStateSender == ChipStateSender.TRANSFER_TO_THIS_DEVICE_FAILED;
        }
    },
    TRANSFER_TO_RECEIVER_SUCCEEDED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_SUCCEEDED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender);
        }
    },
    TRANSFER_TO_THIS_DEVICE_SUCCEEDED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_SUCCEEDED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender);
        }
    },
    TRANSFER_TO_RECEIVER_FAILED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_FAILED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender);
        }
    },
    TRANSFER_TO_THIS_DEVICE_FAILED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_FAILED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender);
        }
    },
    FAR_FROM_RECEIVER { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.FAR_FROM_RECEIVER
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final Text getChipTextString(Context context, String str) {
            throw new IllegalArgumentException("FAR_FROM_RECEIVER should never be displayed, so its string should never be fetched");
        }

        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public final boolean isValidNextState(ChipStateSender chipStateSender) {
            return Companion.access$stateIsStartOfSequence(ChipStateSender.Companion, chipStateSender);
        }
    };

    public static final Companion Companion = new Companion(null);
    private final SenderEndItem endItem;
    private final int stateInt;
    private final Integer stringResId;
    private final TimeoutLength timeoutLength;
    private final TransferStatus transferStatus;
    private final UiEventLogger.UiEventEnum uiEvent;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final boolean access$stateIsStartOfSequence(Companion companion, ChipStateSender chipStateSender) {
            companion.getClass();
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender.getTransferStatus() == TransferStatus.NOT_STARTED || chipStateSender.getTransferStatus() == TransferStatus.IN_PROGRESS;
        }
    }

    /* synthetic */ ChipStateSender(int i, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, TimeoutLength timeoutLength, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, uiEventEnum, num, transferStatus, senderEndItem, timeoutLength);
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

    ChipStateSender(int i, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, TimeoutLength timeoutLength) {
        this.stateInt = i;
        this.uiEvent = uiEventEnum;
        this.stringResId = num;
        this.transferStatus = transferStatus;
        this.endItem = senderEndItem;
        this.timeoutLength = timeoutLength;
    }

    /* synthetic */ ChipStateSender(int i, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, TimeoutLength timeoutLength, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, uiEventEnum, num, transferStatus, senderEndItem, (i2 & 32) != 0 ? TimeoutLength.DEFAULT : timeoutLength);
    }
}
