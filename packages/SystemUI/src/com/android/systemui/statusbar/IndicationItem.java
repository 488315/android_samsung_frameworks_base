package com.android.systemui.statusbar;

import android.content.res.ColorStateList;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IndicationItem implements Comparable {
    public final long mDurationTime;
    public final IndicationEventType mEventType;
    public final boolean mIsAnimation;
    public final int mItemId;
    public final int mPriority;
    public final CharSequence mText;

    public IndicationItem(int i, IndicationEventType indicationEventType, CharSequence charSequence, ColorStateList colorStateList, long j, boolean z) {
        this.mItemId = i;
        this.mEventType = indicationEventType;
        this.mPriority = indicationEventType.getPriority();
        this.mText = charSequence;
        this.mIsAnimation = z;
        this.mDurationTime = j;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        IndicationItem indicationItem = (IndicationItem) obj;
        int i = this.mPriority;
        int i2 = indicationItem.mPriority;
        return i == i2 ? indicationItem.mItemId - this.mItemId : i2 - i;
    }

    public final boolean isPersistantEvent() {
        return this.mDurationTime == -1;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[id=");
        sb.append(this.mItemId);
        sb.append("|ty=");
        sb.append(this.mEventType);
        sb.append("|pr=");
        sb.append(this.mPriority);
        sb.append("|txt=");
        sb.append((Object) this.mText);
        sb.append("|ti=duration=");
        sb.append(isPersistantEvent() ? "PERSISTENT" : Long.valueOf(this.mDurationTime));
        sb.append("|an=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.mIsAnimation, "]");
    }
}
