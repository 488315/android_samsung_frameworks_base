package com.samsung.android.graphics.spr.animation.interpolator;

import android.animation.TimeInterpolator;
import android.app.admin.DevicePolicyManager;
import java.util.TimeZone;

/* loaded from: classes5.dex */
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

    public SprTimeInterpolator(int duration, int type, int quotient) {
        this.mDuration = 0;
        this.mPeriodType = 0;
        this.mQuotient = 1;
        this.mDuration = duration;
        this.mPeriodType = type;
        this.mQuotient = quotient;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setPeriodType(int type) {
        this.mPeriodType = type;
    }

    public void setQuotient(int quotient) {
        this.mQuotient = quotient;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float input) {
        long now;
        long currentTime = System.currentTimeMillis();
        long now2 = TimeZone.getDefault().getOffset(currentTime) + currentTime;
        if (this.mPeriodType == 1) {
            now = now2 % 86400000;
        } else {
            now = (now2 - DevicePolicyManager.DEFAULT_STRONG_AUTH_TIMEOUT_MS) % 604800000;
        }
        int i = this.mDuration;
        long tick = now % i;
        int i2 = this.mQuotient;
        if (i2 > 1) {
            tick = (tick / i2) * i2;
        }
        return tick / i;
    }
}
