package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FooterMessageViewModel {
    public final int iconId;
    public final StateFlow isVisible;
    public final int messageId;

    public FooterMessageViewModel(int i, int i2, StateFlow stateFlow) {
        this.messageId = i;
        this.iconId = i2;
        this.isVisible = stateFlow;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FooterMessageViewModel)) {
            return false;
        }
        FooterMessageViewModel footerMessageViewModel = (FooterMessageViewModel) obj;
        return this.messageId == footerMessageViewModel.messageId && this.iconId == footerMessageViewModel.iconId && Intrinsics.areEqual(this.isVisible, footerMessageViewModel.isVisible);
    }

    public final int hashCode() {
        return this.isVisible.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.iconId, Integer.hashCode(this.messageId) * 31, 31);
    }

    public final String toString() {
        return "FooterMessageViewModel(messageId=" + this.messageId + ", iconId=" + this.iconId + ", isVisible=" + this.isVisible + ")";
    }
}
