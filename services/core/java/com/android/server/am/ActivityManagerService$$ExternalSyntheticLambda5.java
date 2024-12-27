package com.android.server.am;

import android.util.ArraySet;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.FrameworkStatsLog;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityManagerService$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda5(String str, int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = str;
    }

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda5(Set set, ArrayList arrayList) {
        this.$r8$classId = 2;
        this.f$0 = set;
        this.f$1 = arrayList;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ArraySet arraySet;
        switch (this.$r8$classId) {
            case 0:
                int[] iArr = (int[]) this.f$0;
                String str = (String) this.f$1;
                ProcessRecord processRecord = (ProcessRecord) obj;
                if (iArr[0] > processRecord.mState.mSetProcState) {
                    if (processRecord.mPkgList.containsKey(str) || ((arraySet = processRecord.mPkgDeps) != null && arraySet.contains(str))) {
                        iArr[0] = processRecord.mState.mSetProcState;
                        break;
                    }
                }
                break;
            case 1:
                ProcessRecord processRecord2 = (ProcessRecord) this.f$0;
                String str2 = (String) this.f$1;
                ProcessStats.ProcessStateHolder processStateHolder = (ProcessStats.ProcessStateHolder) obj;
                ProcessState processState = processStateHolder.state;
                FrameworkStatsLog.write(16, processRecord2.info.uid, str2, processState != null ? processState.getPackage() : processRecord2.info.packageName, processStateHolder.appVersion);
                break;
            default:
                Set set = (Set) this.f$0;
                ArrayList arrayList = (ArrayList) this.f$1;
                ProcessCpuTracker.Stats stats = (ProcessCpuTracker.Stats) obj;
                if (stats.vsize > 0 && !set.contains(Integer.valueOf(stats.pid))) {
                    arrayList.add(stats);
                    break;
                }
                break;
        }
    }
}
