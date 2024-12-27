package com.android.systemui.statusbar.notification.logging;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationMemoryDumper$dumpNotificationObjects$Totals {
    public int bigPicture;
    public int extender;
    public int extras;
    public int largeIcon;
    public int smallIcon;
    public int styleIcon;

    public NotificationMemoryDumper$dumpNotificationObjects$Totals(int i, int i2, int i3, int i4, int i5, int i6) {
        this.smallIcon = i;
        this.largeIcon = i2;
        this.styleIcon = i3;
        this.bigPicture = i4;
        this.extender = i5;
        this.extras = i6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationMemoryDumper$dumpNotificationObjects$Totals)) {
            return false;
        }
        NotificationMemoryDumper$dumpNotificationObjects$Totals notificationMemoryDumper$dumpNotificationObjects$Totals = (NotificationMemoryDumper$dumpNotificationObjects$Totals) obj;
        return this.smallIcon == notificationMemoryDumper$dumpNotificationObjects$Totals.smallIcon && this.largeIcon == notificationMemoryDumper$dumpNotificationObjects$Totals.largeIcon && this.styleIcon == notificationMemoryDumper$dumpNotificationObjects$Totals.styleIcon && this.bigPicture == notificationMemoryDumper$dumpNotificationObjects$Totals.bigPicture && this.extender == notificationMemoryDumper$dumpNotificationObjects$Totals.extender && this.extras == notificationMemoryDumper$dumpNotificationObjects$Totals.extras;
    }

    public final int hashCode() {
        return Integer.hashCode(this.extras) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.extender, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.bigPicture, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.styleIcon, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.largeIcon, Integer.hashCode(this.smallIcon) * 31, 31), 31), 31), 31);
    }

    public final String toString() {
        int i = this.smallIcon;
        int i2 = this.largeIcon;
        int i3 = this.styleIcon;
        int i4 = this.bigPicture;
        int i5 = this.extender;
        int i6 = this.extras;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "Totals(smallIcon=", ", largeIcon=", ", styleIcon=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i3, ", bigPicture=", i4, ", extender=");
        m.append(i5);
        m.append(", extras=");
        m.append(i6);
        m.append(")");
        return m.toString();
    }

    public /* synthetic */ NotificationMemoryDumper$dumpNotificationObjects$Totals(int i, int i2, int i3, int i4, int i5, int i6, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this((i7 & 1) != 0 ? 0 : i, (i7 & 2) != 0 ? 0 : i2, (i7 & 4) != 0 ? 0 : i3, (i7 & 8) != 0 ? 0 : i4, (i7 & 16) != 0 ? 0 : i5, (i7 & 32) != 0 ? 0 : i6);
    }
}
