package com.android.systemui.knox;

import android.content.Context;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.Dumpable;
import com.samsung.android.knox.dar.ddar.DualDarAuthUtils;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, innerAuthUserId, "getInnerAuthUserId - userId : ", ", ret : ", "DualDarMonitor");
        return innerAuthUserId;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
