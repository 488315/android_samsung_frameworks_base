package com.android.systemui.knox;

import android.content.Context;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.Dumpable;
import com.samsung.android.knox.dar.ddar.DualDarAuthUtils;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DualDarMonitor implements Dumpable {
    public final Context mContext;
    public final DualDarAuthUtils mDualDarAuthUtils;
    public final LockPatternUtils mLockPatternUtils;
    public long mLockoutAttemptDeadline = 0;
    public long mLockoutAttemptTimeout = 0;

    public DualDarMonitor(KnoxStateMonitorImpl knoxStateMonitorImpl) {
        Context context = knoxStateMonitorImpl.mContext;
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mDualDarAuthUtils = new DualDarAuthUtils(context);
    }

    public final int getInnerAuthUserId(int i) {
        int innerAuthUserId = this.mDualDarAuthUtils.getInnerAuthUserId(i);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("getInnerAuthUserId - userId : ", i, ", ret : ", innerAuthUserId, "DualDarMonitor");
        return innerAuthUserId;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
