package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.shade.LargeScreenHeaderHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class KeyguardClockPositionAlgorithm {
    public boolean mBypassEnabled;
    public float mOverStretchAmount;
    public int mUnlockedStackScrollerPadding;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Result {
        public int stackScrollerPadding;
        public int stackScrollerPaddingExpanded;
    }

    public KeyguardClockPositionAlgorithm(LogBuffer logBuffer) {
        new Logger(logBuffer, "KeyguardClockPositionAlgorithm");
    }

    public int getLockscreenNotifPadding() {
        return 0;
    }

    public boolean isPanelExpanded() {
        return false;
    }

    public void loadDimens(Context context, Resources resources) {
        LargeScreenHeaderHelper.getLargeScreenHeaderHeight(context);
        resources.getDimensionPixelSize(R.dimen.keyguard_split_shade_top_margin);
    }

    public void run(Result result) {
        boolean z = this.mBypassEnabled;
        result.stackScrollerPadding = z ? (int) (this.mUnlockedStackScrollerPadding + this.mOverStretchAmount) : 0;
        result.stackScrollerPaddingExpanded = z ? this.mUnlockedStackScrollerPadding : 0;
    }

    public void setup(float f, float f2, int i, boolean z) {
        this.mOverStretchAmount = f2;
        this.mBypassEnabled = z;
        this.mUnlockedStackScrollerPadding = i;
    }
}
