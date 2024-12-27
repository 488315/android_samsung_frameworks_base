package com.android.server.am;

import android.os.PowerExemptionManager;
import android.os.UserHandle;
import android.util.Pair;
import android.util.TimeUtils;
import com.android.server.am.ActivityManagerService;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityManagerService$$ExternalSyntheticLambda27 implements BiConsumer {
    public final /* synthetic */ PrintWriter f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda27(PrintWriter printWriter, long j, long j2) {
        this.f$0 = printWriter;
        this.f$1 = j;
        this.f$2 = j2;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        PrintWriter printWriter = this.f$0;
        long j = this.f$1;
        long j2 = this.f$2;
        Pair pair = (Pair) obj2;
        printWriter.print("    " + UserHandle.formatUid(((Integer) obj).intValue()) + ": ");
        ActivityManagerService.FgsTempAllowListItem fgsTempAllowListItem = (ActivityManagerService.FgsTempAllowListItem) pair.second;
        printWriter.print(" duration=" + fgsTempAllowListItem.mDuration + " callingUid=" + UserHandle.formatUid(fgsTempAllowListItem.mCallingUid) + " reasonCode=" + PowerExemptionManager.reasonCodeToString(fgsTempAllowListItem.mReasonCode) + " reason=" + fgsTempAllowListItem.mReason);
        printWriter.print(" expiration=");
        TimeUtils.dumpTimeWithDelta(printWriter, ((Long) pair.first).longValue() + (j - j2), j);
        printWriter.println();
    }
}
