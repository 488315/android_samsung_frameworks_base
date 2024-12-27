package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HapticsToPlay {
    public final Integer flag;
    public final int hapticFeedbackConstant;

    public HapticsToPlay(int i, Integer num) {
        this.hapticFeedbackConstant = i;
        this.flag = num;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HapticsToPlay)) {
            return false;
        }
        HapticsToPlay hapticsToPlay = (HapticsToPlay) obj;
        return this.hapticFeedbackConstant == hapticsToPlay.hapticFeedbackConstant && Intrinsics.areEqual(this.flag, hapticsToPlay.flag);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.hapticFeedbackConstant) * 31;
        Integer num = this.flag;
        return hashCode + (num == null ? 0 : num.hashCode());
    }

    public final String toString() {
        return "HapticsToPlay(hapticFeedbackConstant=" + this.hapticFeedbackConstant + ", flag=" + this.flag + ")";
    }
}
