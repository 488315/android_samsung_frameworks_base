package com.android.server.am;

import android.app.IApplicationThread;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.SparseArray;

import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.os.TransferPipe;
import com.android.server.chimera.ppn.PerProcessNandswap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ActivityManagerService$$ExternalSyntheticLambda20
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda20(
            Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        long[] writebackSizePid;
        switch (this.$r8$classId) {
            case 0:
                ActivityManagerService activityManagerService = (ActivityManagerService) this.f$0;
                PrintWriter printWriter = (PrintWriter) this.f$1;
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) this.f$2;
                ProcessRecord processRecord = (ProcessRecord) obj;
                activityManagerService.getClass();
                IApplicationThread iApplicationThread = processRecord.mThread;
                if (iApplicationThread == null) {
                    return;
                }
                if (Binder.isSystemServerBinderTrackerEnabled
                        || Build.IS_DEBUGGABLE
                        || processRecord.isDebuggable()) {
                    if (CachedAppOptimizer.isFreezerSupported()) {
                        activityManagerService.mOomAdjuster.mCachedAppOptimizer.unfreezeAppLSP(
                                23, processRecord);
                    }
                    printWriter.println("Traces for process: " + processRecord.processName);
                    printWriter.flush();
                    try {
                        TransferPipe transferPipe = new TransferPipe();
                        try {
                            if (Binder.isSystemServerBinderTrackerEnabled) {
                                iApplicationThread.stopBinderTrackingAndDumpSystemServer(
                                        transferPipe.getWriteFd(),
                                        processRecord.processName,
                                        processRecord.mPkgList.getPackageList()[0],
                                        processRecord.mPid,
                                        processRecord.uid);
                                transferPipe.go(parcelFileDescriptor.getFileDescriptor(), 10000L);
                            } else {
                                iApplicationThread.stopBinderTrackingAndDump(
                                        transferPipe.getWriteFd());
                                transferPipe.go(parcelFileDescriptor.getFileDescriptor());
                            }
                            transferPipe.kill();
                            return;
                        } catch (Throwable th) {
                            transferPipe.kill();
                            throw th;
                        }
                    } catch (RemoteException e) {
                        printWriter.println(
                                "Got a RemoteException while dumping IPC traces from "
                                        + processRecord
                                        + ".  Exception: "
                                        + e);
                        printWriter.flush();
                        return;
                    } catch (IOException e2) {
                        printWriter.println(
                                "Failure while dumping IPC traces from "
                                        + processRecord
                                        + ".  Exception: "
                                        + e2);
                        printWriter.flush();
                        return;
                    }
                }
                return;
            default:
                SparseArray sparseArray = (SparseArray) this.f$0;
                long[] jArr = (long[]) this.f$1;
                ArrayList[] arrayListArr = (ArrayList[]) this.f$2;
                ProcessCpuTracker.Stats stats = (ProcessCpuTracker.Stats) obj;
                if (stats.vsize <= 0
                        || sparseArray.indexOfKey(stats.pid) >= 0
                        || (writebackSizePid = PerProcessNandswap.getWritebackSizePid(stats.pid))
                                == null) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(stats.name);
                sb.append(" (pid ");
                String m = AmFmBandRange$$ExternalSyntheticOutline0.m(stats.pid, sb, ")");
                long j = writebackSizePid[0];
                long j2 = writebackSizePid[1];
                PerProcessNandswap.MemoryItem memoryItem =
                        new PerProcessNandswap.MemoryItem(0, j, j2, -1L, m);
                jArr[0] = jArr[0] + j2;
                if (arrayListArr[0] == null) {
                    arrayListArr[0] = new ArrayList();
                }
                arrayListArr[0].add(memoryItem);
                return;
        }
    }
}
