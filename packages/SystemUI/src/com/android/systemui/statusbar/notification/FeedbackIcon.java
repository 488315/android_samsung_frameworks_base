package com.android.systemui.statusbar.notification;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

public final class FeedbackIcon {
    public final int contentDescRes;
    public final int iconRes;

    public FeedbackIcon(int i, int i2) {
        this.iconRes = i;
        this.contentDescRes = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FeedbackIcon)) {
            return false;
        }
        FeedbackIcon feedbackIcon = (FeedbackIcon) obj;
        return this.iconRes == feedbackIcon.iconRes && this.contentDescRes == feedbackIcon.contentDescRes;
    }

    public final int hashCode() {
        return Integer.hashCode(this.contentDescRes) + (Integer.hashCode(this.iconRes) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FeedbackIcon(iconRes=");
        sb.append(this.iconRes);
        sb.append(", contentDescRes=");
        return Anchor$$ExternalSyntheticOutline0.m(this.contentDescRes, ")", sb);
    }
}
