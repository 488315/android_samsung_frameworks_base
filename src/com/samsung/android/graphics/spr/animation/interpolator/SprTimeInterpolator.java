package com.samsung.android.graphics.spr.animation.interpolator;

import android.animation.TimeInterpolator;
import java.util.TimeZone;

/* loaded from: classes6.dex */
public class SprTimeInterpolator implements TimeInterpolator {
    static final int DAY_MILLISECONDS = 86400000;
    public static final int DAY_TYPE = 1;
    static final int WEEK_MILLISECONDS = 604800000;
    public static final int WEEK_TYPE = 2;
    private int mDuration;
    private int mPeriodType;
    private int mQuotient;

    public SprTimeInterpolator() {
        this.mDuration = 0;
        this.mPeriodType = 0;
        this.mQuotient = 1;
    }

    public SprTimeInterpolator(int i, int i2, int i3) {
        this.mDuration = i;
        this.mPeriodType = i2;
        this.mQuotient = i3;
    }

    public void setDuration(int i) {
        this.mDuration = i;
    }

    public void setPeriodType(int i) {
        this.mPeriodType = i;
    }

    public void setQuotient(int i) {
        this.mQuotient = i;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        long j;
        long jCurrentTimeMillis = System.currentTimeMillis() + TimeZone.getDefault().getOffset(r0);
        if (this.mPeriodType == 1) {
            j = jCurrentTimeMillis % 86400000;
        } else {
            j = (jCurrentTimeMillis - 259200000) % 604800000;
        }
        int i = this.mDuration;
        long j2 = j % i;
        int i2 = this.mQuotient;
        if (i2 > 1) {
            j2 = (j2 / i2) * i2;
        }
        return j2 / i;
    }
}
