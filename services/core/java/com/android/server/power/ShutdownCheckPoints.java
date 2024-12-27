package com.android.server.power;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.os.Process;
import android.util.AtomicFile;
import android.util.Log;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ShutdownCheckPoints {
    public final ArrayList mCheckPoints = new ArrayList();
    public final Injector mInjector;
    public static final ShutdownCheckPoints INSTANCE = new ShutdownCheckPoints(new AnonymousClass1());
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
    public static final File[] EMPTY_FILE_ARRAY = new File[0];

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.ShutdownCheckPoints$1, reason: invalid class name */
    public final class AnonymousClass1 implements Injector {
        @Override // com.android.server.power.ShutdownCheckPoints.Injector
        public final IActivityManager activityManager() {
            return ActivityManager.getService();
        }

        @Override // com.android.server.power.ShutdownCheckPoints.Injector
        public final long currentTimeMillis() {
            return System.currentTimeMillis();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderCheckPoint extends SystemServerCheckPoint {
        public final int mCallerProcessId;

        public BinderCheckPoint(int i, String str, long j) {
            super(j, str);
            this.mCallerProcessId = i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0045, code lost:
        
            r2 = r0.processName;
         */
        @Override // com.android.server.power.ShutdownCheckPoints.SystemServerCheckPoint, com.android.server.power.ShutdownCheckPoints.CheckPoint
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dumpDetails(com.android.server.power.ShutdownCheckPoints.Injector r5, java.io.PrintWriter r6) {
            /*
                r4 = this;
                java.lang.String r0 = r4.findMethodName()
                if (r0 != 0) goto L8
                java.lang.String r0 = "Failed to get method name"
            L8:
                r6.println(r0)
                android.app.IActivityManager r5 = r5.activityManager()
                java.lang.String r0 = "No ActivityManager to find name of process with pid="
                int r4 = r4.mCallerProcessId
                java.lang.String r1 = "ShutdownCheckPoints"
                r2 = 0
                if (r5 == 0) goto L1f
                java.util.List r5 = r5.getRunningAppProcesses()     // Catch: android.os.RemoteException -> L1d
                goto L2f
            L1d:
                r5 = move-exception
                goto L48
            L1f:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L1d
                r5.<init>(r0)     // Catch: android.os.RemoteException -> L1d
                r5.append(r4)     // Catch: android.os.RemoteException -> L1d
                java.lang.String r5 = r5.toString()     // Catch: android.os.RemoteException -> L1d
                android.util.Slog.v(r1, r5)     // Catch: android.os.RemoteException -> L1d
                r5 = r2
            L2f:
                if (r5 == 0) goto L4d
                java.util.Iterator r5 = r5.iterator()     // Catch: android.os.RemoteException -> L1d
            L35:
                boolean r0 = r5.hasNext()     // Catch: android.os.RemoteException -> L1d
                if (r0 == 0) goto L4d
                java.lang.Object r0 = r5.next()     // Catch: android.os.RemoteException -> L1d
                android.app.ActivityManager$RunningAppProcessInfo r0 = (android.app.ActivityManager.RunningAppProcessInfo) r0     // Catch: android.os.RemoteException -> L1d
                int r3 = r0.pid     // Catch: android.os.RemoteException -> L1d
                if (r3 != r4) goto L35
                java.lang.String r2 = r0.processName     // Catch: android.os.RemoteException -> L1d
                goto L4d
            L48:
                java.lang.String r0 = "Failed to get running app processes from ActivityManager"
                android.util.Slog.e(r1, r0, r5)
            L4d:
                java.lang.String r5 = "From process "
                r6.print(r5)
                if (r2 != 0) goto L56
                java.lang.String r2 = "?"
            L56:
                r6.print(r2)
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                java.lang.String r0 = " (pid="
                r5.<init>(r0)
                r5.append(r4)
                java.lang.String r4 = ")"
                r5.append(r4)
                java.lang.String r4 = r5.toString()
                r6.println(r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.ShutdownCheckPoints.BinderCheckPoint.dumpDetails(com.android.server.power.ShutdownCheckPoints$Injector, java.io.PrintWriter):void");
        }

        @Override // com.android.server.power.ShutdownCheckPoints.SystemServerCheckPoint, com.android.server.power.ShutdownCheckPoints.CheckPoint
        public final String getOrigin() {
            return "BINDER";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class CheckPoint {
        public final String mReason;
        public final long mTimestamp;

        public CheckPoint(long j, String str) {
            this.mTimestamp = j;
            this.mReason = str;
        }

        public abstract void dumpDetails(Injector injector, PrintWriter printWriter);

        public abstract String getOrigin();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FileDumperThread extends Thread {
        public final File mBaseFile;
        public final int mFileCountLimit;
        public final ShutdownCheckPoints mInstance;

        public FileDumperThread(ShutdownCheckPoints shutdownCheckPoints, File file, int i) {
            this.mInstance = shutdownCheckPoints;
            this.mBaseFile = file;
            this.mFileCountLimit = i;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            FileOutputStream fileOutputStream;
            this.mBaseFile.getParentFile().mkdirs();
            final String str = this.mBaseFile.getName() + PackageManagerShellCommandDataLoader.STDIN_PATH;
            File[] listFiles = this.mBaseFile.getParentFile().listFiles(new FilenameFilter() { // from class: com.android.server.power.ShutdownCheckPoints.FileDumperThread.1
                @Override // java.io.FilenameFilter
                public final boolean accept(File file, String str2) {
                    if (!str2.startsWith(str)) {
                        return false;
                    }
                    try {
                        Long.valueOf(str2.substring(str.length()));
                        return true;
                    } catch (NumberFormatException unused) {
                        return false;
                    }
                }
            });
            if (listFiles == null) {
                listFiles = ShutdownCheckPoints.EMPTY_FILE_ARRAY;
            } else {
                Arrays.sort(listFiles);
            }
            int length = (listFiles.length - this.mFileCountLimit) + 1;
            for (int i = 0; i < length; i++) {
                listFiles[i].delete();
            }
            File file = new File(String.format("%s-%d", this.mBaseFile.getAbsolutePath(), Long.valueOf(System.currentTimeMillis())));
            AtomicFile atomicFile = new AtomicFile(this.mBaseFile);
            try {
                fileOutputStream = atomicFile.startWrite();
                try {
                    PrintWriter printWriter = new PrintWriter(fileOutputStream);
                    this.mInstance.dumpInternal(printWriter);
                    printWriter.flush();
                    atomicFile.finishWrite(fileOutputStream);
                } catch (IOException e) {
                    e = e;
                    Log.e("ShutdownCheckPoints", "Failed to write shutdown checkpoints", e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    this.mBaseFile.renameTo(file);
                }
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = null;
            }
            this.mBaseFile.renameTo(file);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Injector {
        IActivityManager activityManager();

        long currentTimeMillis();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntentCheckPoint extends CheckPoint {
        public final String mIntentName;
        public final String mPackageName;

        public IntentCheckPoint(String str, long j, String str2, String str3) {
            super(j, str3);
            this.mIntentName = str;
            this.mPackageName = str2;
        }

        @Override // com.android.server.power.ShutdownCheckPoints.CheckPoint
        public final void dumpDetails(Injector injector, PrintWriter printWriter) {
            printWriter.print("Intent: ");
            printWriter.println(this.mIntentName);
            printWriter.print("Package: ");
            printWriter.println(this.mPackageName);
        }

        @Override // com.android.server.power.ShutdownCheckPoints.CheckPoint
        public final String getOrigin() {
            return "INTENT";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class SystemServerCheckPoint extends CheckPoint {
        public final StackTraceElement[] mStackTraceElements;

        public SystemServerCheckPoint(long j, String str) {
            super(j, str);
            this.mStackTraceElements = Thread.currentThread().getStackTrace();
        }

        @Override // com.android.server.power.ShutdownCheckPoints.CheckPoint
        public void dumpDetails(Injector injector, PrintWriter printWriter) {
            String findMethodName = findMethodName();
            if (findMethodName == null) {
                findMethodName = "Failed to get method name";
            }
            printWriter.println(findMethodName);
            int findCallSiteIndex = findCallSiteIndex();
            while (true) {
                findCallSiteIndex++;
                if (findCallSiteIndex >= this.mStackTraceElements.length) {
                    return;
                }
                printWriter.print(" at ");
                printWriter.println(this.mStackTraceElements[findCallSiteIndex]);
            }
        }

        public final int findCallSiteIndex() {
            String canonicalName = ShutdownCheckPoints.class.getCanonicalName();
            int i = 0;
            while (true) {
                StackTraceElement[] stackTraceElementArr = this.mStackTraceElements;
                if (i >= stackTraceElementArr.length || stackTraceElementArr[i].getClassName().equals(canonicalName)) {
                    break;
                }
                i++;
            }
            while (true) {
                StackTraceElement[] stackTraceElementArr2 = this.mStackTraceElements;
                if (i >= stackTraceElementArr2.length || !stackTraceElementArr2[i].getClassName().equals(canonicalName)) {
                    break;
                }
                i++;
            }
            return i;
        }

        public final String findMethodName() {
            int findCallSiteIndex = findCallSiteIndex();
            StackTraceElement[] stackTraceElementArr = this.mStackTraceElements;
            if (findCallSiteIndex >= stackTraceElementArr.length) {
                return null;
            }
            StackTraceElement stackTraceElement = stackTraceElementArr[findCallSiteIndex];
            return AnyMotionDetector$$ExternalSyntheticOutline0.m(stackTraceElement.getClassName(), ".", stackTraceElement.getMethodName());
        }

        @Override // com.android.server.power.ShutdownCheckPoints.CheckPoint
        public String getOrigin() {
            return "SYSTEM";
        }
    }

    public ShutdownCheckPoints(Injector injector) {
        this.mInjector = injector;
    }

    public static void recordCheckPoint(String str, String str2) {
        INSTANCE.recordCheckPointInternal(str, str2, null);
    }

    public void dumpInternal(PrintWriter printWriter) {
        ArrayList arrayList;
        synchronized (this.mCheckPoints) {
            arrayList = new ArrayList(this.mCheckPoints);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CheckPoint checkPoint = (CheckPoint) it.next();
            Injector injector = this.mInjector;
            checkPoint.getClass();
            printWriter.print("Shutdown request from ");
            printWriter.print(checkPoint.getOrigin());
            String str = checkPoint.mReason;
            if (str != null) {
                printWriter.print(" for reason ");
                printWriter.print(str);
            }
            printWriter.print(" at ");
            SimpleDateFormat simpleDateFormat = DATE_FORMAT;
            long j = checkPoint.mTimestamp;
            printWriter.print(simpleDateFormat.format(new Date(j)));
            printWriter.println(" (epoch=" + j + ")");
            checkPoint.dumpDetails(injector, printWriter);
            printWriter.println();
        }
    }

    public Thread newDumpThreadInternal(File file) {
        this.mInjector.getClass();
        return new FileDumperThread(this, file, 20);
    }

    public void recordCheckPointInternal(int i, String str) {
        long currentTimeMillis = this.mInjector.currentTimeMillis();
        recordCheckPointInternal(i == Process.myPid() ? new SystemServerCheckPoint(currentTimeMillis, str) : new BinderCheckPoint(i, str, currentTimeMillis));
        android.util.Slog.v("ShutdownCheckPoints", "Binder shutdown checkpoint recorded with pid=" + i);
    }

    public final void recordCheckPointInternal(CheckPoint checkPoint) {
        synchronized (this.mCheckPoints) {
            try {
                this.mCheckPoints.add(checkPoint);
                int size = this.mCheckPoints.size();
                this.mInjector.getClass();
                if (size > 100) {
                    this.mCheckPoints.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void recordCheckPointInternal(String str) {
        recordCheckPointInternal(new SystemServerCheckPoint(this.mInjector.currentTimeMillis(), str));
        android.util.Slog.v("ShutdownCheckPoints", "System server shutdown checkpoint recorded");
    }

    public void recordCheckPointInternal(String str, String str2, String str3) {
        long currentTimeMillis = this.mInjector.currentTimeMillis();
        recordCheckPointInternal("android".equals(str2) ? new SystemServerCheckPoint(currentTimeMillis, str3) : new IntentCheckPoint(str, currentTimeMillis, str2, str3));
        android.util.Slog.v("ShutdownCheckPoints", "Shutdown intent checkpoint recorded intent=" + str + " from package=" + str2);
    }
}
