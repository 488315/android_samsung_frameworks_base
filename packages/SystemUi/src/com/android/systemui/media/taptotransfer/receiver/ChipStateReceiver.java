package com.android.systemui.media.taptotransfer.receiver;

import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum ChipStateReceiver {
    CLOSE_TO_SENDER(0, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_CLOSE_TO_SENDER),
    /* JADX INFO: Fake field, exist only in values array */
    FAR_FROM_SENDER(1, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_FAR_FROM_SENDER),
    /* JADX INFO: Fake field, exist only in values array */
    TRANSFER_TO_RECEIVER_SUCCEEDED(2, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_SUCCEEDED),
    /* JADX INFO: Fake field, exist only in values array */
    TRANSFER_TO_RECEIVER_FAILED(3, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_FAILED);

    public static final Companion Companion = new Companion(null);
    private final int stateInt;
    private final UiEventLogger.UiEventEnum uiEvent;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    ChipStateReceiver(int i, UiEventLogger.UiEventEnum uiEventEnum) {
        this.stateInt = i;
        this.uiEvent = uiEventEnum;
    }

    public final int getStateInt() {
        return this.stateInt;
    }

    public final UiEventLogger.UiEventEnum getUiEvent() {
        return this.uiEvent;
    }
}
