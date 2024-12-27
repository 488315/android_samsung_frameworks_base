package com.android.server.am;

import android.app.PendingIntent;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.MutableLong;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppTimeTracker {
    public final ArrayMap mPackageTimes = new ArrayMap();
    public final PendingIntent mReceiver;
    public String mStartedPackage;
    public MutableLong mStartedPackageTime;
    public long mStartedTime;
    public long mTotalTime;

    public AppTimeTracker(PendingIntent pendingIntent) {
        this.mReceiver = pendingIntent;
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268063L);
        protoOutputStream.write(1138166333441L, this.mReceiver.toString());
        protoOutputStream.write(1112396529666L, this.mTotalTime);
        for (int i = 0; i < this.mPackageTimes.size(); i++) {
            long start2 = protoOutputStream.start(2246267895811L);
            protoOutputStream.write(1138166333441L, (String) this.mPackageTimes.keyAt(i));
            protoOutputStream.write(
                    1112396529666L, ((MutableLong) this.mPackageTimes.valueAt(i)).value);
            protoOutputStream.end(start2);
        }
        long j = this.mStartedTime;
        if (j != 0) {
            ProtoUtils.toDuration(
                    protoOutputStream, 1146756268036L, j, SystemClock.elapsedRealtime());
            protoOutputStream.write(1138166333445L, this.mStartedPackage);
        }
        protoOutputStream.end(start);
    }

    public final void dumpWithHeader(PrintWriter printWriter, String str, boolean z) {
        printWriter.print(str);
        printWriter.print("AppTimeTracker #");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(":");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mReceiver=");
        printWriter.println(this.mReceiver);
        printWriter.print(str2);
        printWriter.print("mTotalTime=");
        TimeUtils.formatDuration(this.mTotalTime, printWriter);
        printWriter.println();
        for (int i = 0; i < this.mPackageTimes.size(); i++) {
            printWriter.print(str2);
            printWriter.print("mPackageTime:");
            printWriter.print((String) this.mPackageTimes.keyAt(i));
            printWriter.print("=");
            TimeUtils.formatDuration(
                    ((MutableLong) this.mPackageTimes.valueAt(i)).value, printWriter);
            printWriter.println();
        }
        if (!z || this.mStartedTime == 0) {
            return;
        }
        printWriter.print(str2);
        printWriter.print("mStartedTime=");
        TimeUtils.formatDuration(SystemClock.elapsedRealtime(), this.mStartedTime, printWriter);
        printWriter.println();
        printWriter.print(str2);
        printWriter.print("mStartedPackage=");
        printWriter.println(this.mStartedPackage);
    }

    public final void stop() {
        if (this.mStartedTime != 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mStartedTime;
            this.mTotalTime += elapsedRealtime;
            MutableLong mutableLong = this.mStartedPackageTime;
            if (mutableLong != null) {
                mutableLong.value += elapsedRealtime;
            }
            this.mStartedPackage = null;
            this.mStartedPackageTime = null;
        }
    }
}
