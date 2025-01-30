package com.android.systemui.wallet.ui;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum WalletUiEvent implements UiEventLogger.UiEventEnum {
    QAW_SHOW_ALL(860),
    QAW_UNLOCK_FROM_CARD_CLICK(861),
    QAW_CHANGE_CARD(863),
    QAW_IMPRESSION(864),
    QAW_CLICK_CARD(865),
    QAW_UNLOCK_FROM_UNLOCK_BUTTON(866),
    QAW_UNLOCK_FROM_SHOW_ALL_BUTTON(867);

    private final int mId;

    WalletUiEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
