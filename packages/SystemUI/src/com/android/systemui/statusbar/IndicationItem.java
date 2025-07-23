package com.android.systemui.statusbar;

import android.content.res.ColorStateList;
import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class IndicationItem implements Comparable {
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
        sb.append(j == -1 ? "PERSISTENT" : Long.valueOf(j));
        sb.append("|an=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.mIsAnimation, "]");
    }
}
