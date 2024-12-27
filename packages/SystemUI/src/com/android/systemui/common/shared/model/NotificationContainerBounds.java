package com.android.systemui.common.shared.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class NotificationContainerBounds {
    public final float bottom;
    public final boolean isAnimated;
    public final float top;

    public NotificationContainerBounds() {
        this(0.0f, 0.0f, false, 7, null);
    }

    public static NotificationContainerBounds copy$default(NotificationContainerBounds notificationContainerBounds, float f, boolean z, int i) {
        float f2 = notificationContainerBounds.bottom;
        if ((i & 4) != 0) {
            z = notificationContainerBounds.isAnimated;
        }
        notificationContainerBounds.getClass();
        return new NotificationContainerBounds(f, f2, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationContainerBounds)) {
            return false;
        }
        NotificationContainerBounds notificationContainerBounds = (NotificationContainerBounds) obj;
        return Float.compare(this.top, notificationContainerBounds.top) == 0 && Float.compare(this.bottom, notificationContainerBounds.bottom) == 0 && this.isAnimated == notificationContainerBounds.isAnimated;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isAnimated) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.bottom, Float.hashCode(this.top) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NotificationContainerBounds(top=");
        sb.append(this.top);
        sb.append(", bottom=");
        sb.append(this.bottom);
        sb.append(", isAnimated=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isAnimated, ")");
    }

    public NotificationContainerBounds(float f, float f2, boolean z) {
        this.top = f;
        this.bottom = f2;
        this.isAnimated = z;
    }

    public /* synthetic */ NotificationContainerBounds(float f, float f2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? false : z);
    }
}
