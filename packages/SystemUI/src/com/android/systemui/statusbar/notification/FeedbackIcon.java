package com.android.systemui.statusbar.notification;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        return ReorderTile$$ExternalSyntheticOutline0.m(this.contentDescRes, ")", sb);
    }
}
