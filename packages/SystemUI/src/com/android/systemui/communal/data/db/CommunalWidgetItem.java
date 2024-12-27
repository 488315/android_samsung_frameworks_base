package com.android.systemui.communal.data.db;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class CommunalWidgetItem {
    public final String componentName;
    public final long itemId;
    public final long uid;
    public final int widgetId;

    public CommunalWidgetItem(long j, int i, String str, long j2) {
        this.uid = j;
        this.widgetId = i;
        this.componentName = str;
        this.itemId = j2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommunalWidgetItem)) {
            return false;
        }
        CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) obj;
        return this.uid == communalWidgetItem.uid && this.widgetId == communalWidgetItem.widgetId && Intrinsics.areEqual(this.componentName, communalWidgetItem.componentName) && this.itemId == communalWidgetItem.itemId;
    }

    public final int hashCode() {
        return Long.hashCode(this.itemId) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.widgetId, Long.hashCode(this.uid) * 31, 31), 31, this.componentName);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CommunalWidgetItem(uid=");
        sb.append(this.uid);
        sb.append(", widgetId=");
        sb.append(this.widgetId);
        sb.append(", componentName=");
        sb.append(this.componentName);
        sb.append(", itemId=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.itemId, ")", sb);
    }
}
