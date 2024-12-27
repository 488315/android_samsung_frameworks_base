package com.android.server.am;

import android.app.ActivityManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.MirrorActiveUids;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActiveUids {
    public final SparseArray mActiveUids = new SparseArray();
    public final boolean mPostChangesToAtm;
    public final ActivityManagerService mService;

    public ActiveUids(ActivityManagerService activityManagerService, boolean z) {
        this.mService = activityManagerService;
        this.mPostChangesToAtm = z;
    }

    public final boolean dump(int i, final PrintWriter printWriter, String str, String str2) {
        boolean z = false;
        for (int i2 = 0; i2 < this.mActiveUids.size(); i2++) {
            UidRecord uidRecord = (UidRecord) this.mActiveUids.valueAt(i2);
            if (str == null || UserHandle.getAppId(uidRecord.mUid) == i) {
                if (!z) {
                    printWriter.println();
                    printWriter.print("  ");
                    printWriter.println(str2);
                    z = true;
                }
                printWriter.print("    UID ");
                UserHandle.formatUid(printWriter, uidRecord.mUid);
                printWriter.print(": ");
                printWriter.println(uidRecord);
                printWriter.print("      curProcState=");
                printWriter.print(uidRecord.mCurProcState);
                printWriter.print(" curCapability=");
                ActivityManager.printCapabilitiesFull(printWriter, uidRecord.mCurCapability);
                printWriter.println();
                uidRecord.forEachProcess(new Consumer() { // from class: com.android.server.am.ActiveUids$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PrintWriter printWriter2 = printWriter;
                        printWriter2.print("      proc=");
                        printWriter2.println((ProcessRecord) obj);
                    }
                });
            }
        }
        return z;
    }

    public final void dumpProto(ProtoOutputStream protoOutputStream, String str, int i, long j) {
        int i2;
        for (0; i2 < this.mActiveUids.size(); i2 + 1) {
            UidRecord uidRecord = (UidRecord) this.mActiveUids.valueAt(i2);
            i2 = (str == null || UserHandle.getAppId(uidRecord.mUid) == i) ? 0 : i2 + 1;
            uidRecord.getClass();
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, uidRecord.mUid);
            protoOutputStream.write(1159641169922L, ProcessList.makeProcStateProtoEnum(uidRecord.mCurProcState));
            protoOutputStream.write(1133871366147L, uidRecord.mEphemeral);
            protoOutputStream.write(1133871366148L, uidRecord.mForegroundServices);
            protoOutputStream.write(1133871366149L, uidRecord.mCurAllowList);
            ProtoUtils.toDuration(protoOutputStream, 1146756268038L, uidRecord.mLastBackgroundTime, SystemClock.elapsedRealtime());
            protoOutputStream.write(1133871366151L, uidRecord.mIdle);
            int i3 = uidRecord.mLastReportedChange;
            if (i3 != 0) {
                ProtoUtils.writeBitWiseFlagsToProtoEnum(protoOutputStream, 2259152797704L, i3, UidRecord.ORIG_ENUMS, UidRecord.PROTO_ENUMS);
            }
            protoOutputStream.write(1120986464265L, 0);
            long start2 = protoOutputStream.start(1146756268042L);
            protoOutputStream.write(1112396529665L, uidRecord.curProcStateSeq);
            protoOutputStream.write(1112396529666L, uidRecord.lastNetworkUpdatedProcStateSeq);
            protoOutputStream.end(start2);
            protoOutputStream.end(start);
        }
    }

    public final UidRecord get(int i) {
        return (UidRecord) this.mActiveUids.get(i);
    }

    public final void put(int i, UidRecord uidRecord) {
        this.mActiveUids.put(i, uidRecord);
        if (this.mPostChangesToAtm) {
            ActivityTaskManagerInternal activityTaskManagerInternal = this.mService.mAtmInternal;
            int i2 = uidRecord.mCurProcState;
            MirrorActiveUids mirrorActiveUids = ActivityTaskManagerService.this.mActiveUids;
            synchronized (mirrorActiveUids) {
                mirrorActiveUids.mUidStates.put(i, i2);
            }
        }
    }

    public final void remove(int i) {
        this.mActiveUids.remove(i);
        if (this.mPostChangesToAtm) {
            MirrorActiveUids mirrorActiveUids = ActivityTaskManagerService.this.mActiveUids;
            synchronized (mirrorActiveUids) {
                mirrorActiveUids.mUidStates.delete(i);
            }
        }
    }

    public final UidRecord valueAt(int i) {
        return (UidRecord) this.mActiveUids.valueAt(i);
    }
}
