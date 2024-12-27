package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
        return this.isVisible.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.iconId, Integer.hashCode(this.messageId) * 31, 31);
    }

    public final String toString() {
        return "FooterMessageViewModel(messageId=" + this.messageId + ", iconId=" + this.iconId + ", isVisible=" + this.isVisible + ")";
    }
}
