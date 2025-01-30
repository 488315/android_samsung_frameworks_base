package com.android.systemui.statusbar;

import android.content.res.ColorStateList;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        long j = this.mDurationTime;
        sb.append((j > (-1L) ? 1 : (j == (-1L) ? 0 : -1)) == 0 ? "PERSISTENT" : Long.valueOf(j));
        sb.append("|an=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.mIsAnimation, "]");
    }
}
