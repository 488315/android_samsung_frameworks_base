package com.android.systemui.plank.monitor;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class EventData {
    public final int endX;
    public final int endY;
    public final EventType eventType;
    public final int interval;
    public final int startX;
    public final int startY;
    public final int steps;

    public EventData(EventType eventType, int i, int i2, int i3, int i4, int i5, int i6) {
        this.eventType = eventType;
        this.startX = i;
        this.startY = i2;
        this.endX = i3;
        this.endY = i4;
        this.steps = i5;
        this.interval = i6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EventData)) {
            return false;
        }
        EventData eventData = (EventData) obj;
        return this.eventType == eventData.eventType && this.startX == eventData.startX && this.startY == eventData.startY && this.endX == eventData.endX && this.endY == eventData.endY && this.steps == eventData.steps && this.interval == eventData.interval;
    }

    public final int hashCode() {
        return Integer.hashCode(this.interval) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.steps, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.endY, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.endX, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.startY, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.startX, this.eventType.hashCode() * 31, 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("EventData(eventType=");
        sb.append(this.eventType);
        sb.append(", startX=");
        sb.append(this.startX);
        sb.append(", startY=");
        sb.append(this.startY);
        sb.append(", endX=");
        sb.append(this.endX);
        sb.append(", endY=");
        sb.append(this.endY);
        sb.append(", steps=");
        sb.append(this.steps);
        sb.append(", interval=");
        return Anchor$$ExternalSyntheticOutline0.m(this.interval, ")", sb);
    }
}
