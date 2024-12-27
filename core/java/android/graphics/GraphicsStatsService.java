package android.graphics;

import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.job.JobInfo;
import android.app.time.LocationTimeZoneManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.rendererpolicy.GraphicsRendererPolicy;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.os.Trace;
import android.os.UserHandle;
import android.system.ErrnoException;
import android.text.format.Time;
import android.util.Log;
import android.view.IGraphicsStats;
import android.view.IGraphicsStatsCallback;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastPrintWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class GraphicsStatsService extends IGraphicsStats.Stub {
    private static final int AID_STATSD = 1066;
    private static final int DELETE_OLD = 2;
    public static final String GRAPHICS_STATS_SERVICE = "graphicsstats";
    private static final int SAVE_BUFFER = 1;
    private static final String TAG = "GraphicsStatsService";
    private final AlarmManager mAlarmManager;
    private final AppOpsManager mAppOps;
    private final Context mContext;
    private final GraphicsRendererPolicy mGraphicsRendererPolicy;
    private File mGraphicsStatsDir;
    private Handler mWriteOutHandler;
    private final int mAshmemSize = nGetAshmemSize();
    private final byte[] mZeroData = new byte[this.mAshmemSize];
    private final Object mLock = new Object();
    private ArrayList<ActiveBuffer> mActive = new ArrayList<>();
    private final Object mFileAccessLock = new Object();
    private boolean mRotateIsScheduled = false;

    public enum GraphicsStatsRenderEngine {
        GL,
        VK
    }

    private static native void nAddToDump(long j, String str);

    private static native void nAddToDump(long j, String str, String str2, long j2, long j3, long j4, byte[] bArr);

    private static native long nCreateDump(int i, boolean z);

    private static native void nFinishDump(long j);

    private static native void nFinishDumpInMemory(long j, long j2, boolean z);

    private static native int nGetAshmemSize();

    private static native void nSaveBuffer(String str, String str2, long j, long j2, long j3, byte[] bArr);

    private static native void nativeDestructor();

    private native void nativeInit();

    public GraphicsStatsService(Context context) {
        this.mContext = context;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        File systemDataDir = new File(Environment.getDataDirectory(), "system");
        this.mGraphicsStatsDir = new File(systemDataDir, GRAPHICS_STATS_SERVICE);
        this.mGraphicsStatsDir.mkdirs();
        if (!this.mGraphicsStatsDir.exists()) {
            throw new IllegalStateException("Graphics stats directory does not exist: " + this.mGraphicsStatsDir.getAbsolutePath());
        }
        HandlerThread bgthread = new HandlerThread("GraphicsStats-disk", 10);
        bgthread.start();
        this.mGraphicsRendererPolicy = new GraphicsRendererPolicy(context);
        this.mWriteOutHandler = new Handler(bgthread.getLooper(), new Handler.Callback() { // from class: android.graphics.GraphicsStatsService.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        GraphicsStatsService.this.saveBuffer((HistoricalBuffer) msg.obj);
                        break;
                    case 2:
                        GraphicsStatsService.this.deleteOldBuffers();
                        break;
                }
                return true;
            }
        });
        nativeInit();
    }

    private void scheduleRotateLocked() {
        if (this.mRotateIsScheduled) {
            return;
        }
        this.mRotateIsScheduled = true;
        Calendar calendar = normalizeDate(System.currentTimeMillis());
        calendar.add(5, 1);
        this.mAlarmManager.setExact(1, calendar.getTimeInMillis(), TAG, new AlarmManager.OnAlarmListener() { // from class: android.graphics.GraphicsStatsService$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                GraphicsStatsService.this.onAlarm();
            }
        }, this.mWriteOutHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAlarm() {
        int i;
        ActiveBuffer[] activeCopy;
        synchronized (this.mLock) {
            this.mRotateIsScheduled = false;
            scheduleRotateLocked();
            activeCopy = (ActiveBuffer[]) this.mActive.toArray(new ActiveBuffer[0]);
        }
        for (ActiveBuffer active : activeCopy) {
            try {
                active.mCallback.onRotateGraphicsStatsBuffer();
            } catch (RemoteException e) {
                Log.w(TAG, String.format("Failed to notify '%s' (pid=%d) to rotate buffers", active.mInfo.mPackageName, Integer.valueOf(active.mPid)), e);
            }
        }
        this.mWriteOutHandler.sendEmptyMessageDelayed(2, JobInfo.MIN_BACKOFF_MILLIS);
    }

    @Override // android.view.IGraphicsStats
    public ParcelFileDescriptor requestBufferForProcess(String packageName, IGraphicsStatsCallback token) throws RemoteException {
        int uid = Binder.getCallingUid();
        int pid = Binder.getCallingPid();
        long callingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAppOps.checkPackage(uid, packageName);
                PackageInfo info = this.mContext.getPackageManager().getPackageInfoAsUser(packageName, 0, UserHandle.getUserId(uid));
                try {
                    synchronized (this.mLock) {
                        try {
                            ParcelFileDescriptor pfd = requestBufferForProcessLocked(token, uid, pid, packageName, info.getLongVersionCode());
                            return pfd;
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (PackageManager.NameNotFoundException e) {
                throw new RemoteException("Unable to find package: '" + packageName + "'");
            }
        } finally {
            Binder.restoreCallingIdentity(callingIdentity);
        }
    }

    @Override // android.view.IGraphicsStats
    public int requestRenderEngineFor(String packageName) throws RemoteException {
        Log.d(TAG, "requestRenderEngineFor(" + packageName + NavigationBarInflaterView.KEY_CODE_END);
        if (packageName == null) {
            Log.w(TAG, "packageName is null.");
            return GraphicsStatsRenderEngine.VK.ordinal();
        }
        int uid = Binder.getCallingUid();
        long callingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAppOps.checkPackage(uid, packageName);
                return this.mGraphicsRendererPolicy.getRendererType(packageName);
            } catch (Exception e) {
                throw new RemoteException("Unable to find package: '" + packageName + "'");
            }
        } finally {
            Binder.restoreCallingIdentity(callingIdentity);
        }
    }

    private void pullGraphicsStats(boolean lastFullDay, long pulledData) throws RemoteException {
        int uid = Binder.getCallingUid();
        if (uid != 1066) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new FastPrintWriter(sw);
            if (!DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, pw)) {
                pw.flush();
                throw new RemoteException(sw.toString());
            }
        }
        long callingIdentity = Binder.clearCallingIdentity();
        try {
            pullGraphicsStatsImpl(lastFullDay, pulledData);
        } finally {
            Binder.restoreCallingIdentity(callingIdentity);
        }
    }

    private void pullGraphicsStatsImpl(boolean lastFullDay, long pulledData) {
        long targetDay;
        if (lastFullDay) {
            targetDay = normalizeDate(System.currentTimeMillis() - 86400000).getTimeInMillis();
        } else {
            long targetDay2 = System.currentTimeMillis();
            targetDay = normalizeDate(targetDay2).getTimeInMillis();
        }
        synchronized (this.mLock) {
            try {
                ArrayList<HistoricalBuffer> buffers = new ArrayList<>(this.mActive.size());
                for (int i = 0; i < this.mActive.size(); i++) {
                    try {
                        ActiveBuffer buffer = this.mActive.get(i);
                        if (buffer.mInfo.mStartTime == targetDay) {
                            try {
                                buffers.add(new HistoricalBuffer(buffer));
                            } catch (IOException e) {
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        while (true) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        }
                    }
                }
                long dump = nCreateDump(-1, true);
                try {
                    try {
                        synchronized (this.mFileAccessLock) {
                            try {
                                HashSet<File> skipList = dumpActiveLocked(dump, buffers);
                                buffers.clear();
                                String subPath = String.format("%d", Long.valueOf(targetDay));
                                File dateDir = new File(this.mGraphicsStatsDir, subPath);
                                if (dateDir.exists()) {
                                    File[] listFiles = dateDir.listFiles();
                                    int length = listFiles.length;
                                    int i2 = 0;
                                    while (i2 < length) {
                                        File pkg = listFiles[i2];
                                        File[] listFiles2 = pkg.listFiles();
                                        long targetDay3 = targetDay;
                                        try {
                                            int length2 = listFiles2.length;
                                            int i3 = 0;
                                            while (i3 < length2) {
                                                File version = listFiles2[i3];
                                                File[] fileArr = listFiles2;
                                                int i4 = length2;
                                                ArrayList<HistoricalBuffer> buffers2 = buffers;
                                                File data = new File(version, "total");
                                                if (!skipList.contains(data)) {
                                                    nAddToDump(dump, data.getAbsolutePath());
                                                }
                                                i3++;
                                                listFiles2 = fileArr;
                                                length2 = i4;
                                                buffers = buffers2;
                                            }
                                            i2++;
                                            targetDay = targetDay3;
                                        } catch (Throwable th3) {
                                            th = th3;
                                            try {
                                                throw th;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                nFinishDumpInMemory(dump, pulledData, lastFullDay);
                                                throw th;
                                            }
                                        }
                                    }
                                }
                                nFinishDumpInMemory(dump, pulledData, lastFullDay);
                            } catch (Throwable th5) {
                                th = th5;
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
            } catch (Throwable th8) {
                th = th8;
            }
        }
    }

    private ParcelFileDescriptor requestBufferForProcessLocked(IGraphicsStatsCallback token, int uid, int pid, String packageName, long versionCode) throws RemoteException {
        ActiveBuffer buffer = fetchActiveBuffersLocked(token, uid, pid, packageName, versionCode);
        scheduleRotateLocked();
        return buffer.getPfd();
    }

    private Calendar normalizeDate(long timestamp) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        calendar.setTimeInMillis(timestamp);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }

    private File pathForApp(BufferInfo info) {
        String subPath = String.format("%d/%s/%d/total", Long.valueOf(normalizeDate(info.mStartTime).getTimeInMillis()), info.mPackageName, Long.valueOf(info.mVersionCode));
        return new File(this.mGraphicsStatsDir, subPath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveBuffer(HistoricalBuffer buffer) {
        if (Trace.isTagEnabled(524288L)) {
            Trace.traceBegin(524288L, "saving graphicsstats for " + buffer.mInfo.mPackageName);
        }
        synchronized (this.mFileAccessLock) {
            File path = pathForApp(buffer.mInfo);
            File parent = path.getParentFile();
            parent.mkdirs();
            if (!parent.exists()) {
                Log.w(TAG, "Unable to create path: '" + parent.getAbsolutePath() + "'");
            } else {
                nSaveBuffer(path.getAbsolutePath(), buffer.mInfo.mPackageName, buffer.mInfo.mVersionCode, buffer.mInfo.mStartTime, buffer.mInfo.mEndTime, buffer.mData);
                Trace.traceEnd(524288L);
            }
        }
    }

    private void deleteRecursiveLocked(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursiveLocked(child);
            }
        }
        if (!file.delete()) {
            Log.w(TAG, "Failed to delete '" + file.getAbsolutePath() + "'!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteOldBuffers() {
        Trace.traceBegin(524288L, "deleting old graphicsstats buffers");
        synchronized (this.mFileAccessLock) {
            File[] files = this.mGraphicsStatsDir.listFiles();
            if (files != null && files.length > 3) {
                long[] sortedDates = new long[files.length];
                for (int i = 0; i < files.length; i++) {
                    try {
                        sortedDates[i] = Long.parseLong(files[i].getName());
                    } catch (NumberFormatException e) {
                    }
                }
                int i2 = sortedDates.length;
                if (i2 <= 3) {
                    return;
                }
                Arrays.sort(sortedDates);
                for (int i3 = 0; i3 < sortedDates.length - 3; i3++) {
                    deleteRecursiveLocked(new File(this.mGraphicsStatsDir, Long.toString(sortedDates[i3])));
                }
                Trace.traceEnd(524288L);
            }
        }
    }

    private void addToSaveQueue(ActiveBuffer buffer) {
        try {
            HistoricalBuffer data = new HistoricalBuffer(buffer);
            Message.obtain(this.mWriteOutHandler, 1, data).sendToTarget();
        } catch (IOException e) {
            Log.w(TAG, "Failed to copy graphicsstats from " + buffer.mInfo.mPackageName, e);
        }
        buffer.closeAllBuffers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDied(ActiveBuffer buffer) {
        synchronized (this.mLock) {
            this.mActive.remove(buffer);
        }
        addToSaveQueue(buffer);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0040, code lost:
    
        r0 = new android.graphics.GraphicsStatsService.ActiveBuffer(r15, r16, r17, r18, r19, r20);
        r15.mActive.add(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0056, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005f, code lost:
    
        throw new android.os.RemoteException("Failed to allocate space");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.GraphicsStatsService.ActiveBuffer fetchActiveBuffersLocked(android.view.IGraphicsStatsCallback r16, int r17, int r18, java.lang.String r19, long r20) throws android.os.RemoteException {
        /*
            r15 = this;
            r9 = r15
            java.util.ArrayList<android.graphics.GraphicsStatsService$ActiveBuffer> r0 = r9.mActive
            int r10 = r0.size()
            long r0 = java.lang.System.currentTimeMillis()
            java.util.Calendar r0 = r15.normalizeDate(r0)
            long r11 = r0.getTimeInMillis()
            r0 = 0
        L14:
            if (r0 >= r10) goto L3c
            java.util.ArrayList<android.graphics.GraphicsStatsService$ActiveBuffer> r1 = r9.mActive
            java.lang.Object r1 = r1.get(r0)
            android.graphics.GraphicsStatsService$ActiveBuffer r1 = (android.graphics.GraphicsStatsService.ActiveBuffer) r1
            int r2 = r1.mPid
            r13 = r18
            if (r2 != r13) goto L37
            int r2 = r1.mUid
            r14 = r17
            if (r2 != r14) goto L39
            android.graphics.GraphicsStatsService$BufferInfo r2 = r1.mInfo
            long r2 = r2.mStartTime
            int r2 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r2 >= 0) goto L36
            r1.binderDied()
            goto L40
        L36:
            return r1
        L37:
            r14 = r17
        L39:
            int r0 = r0 + 1
            goto L14
        L3c:
            r14 = r17
            r13 = r18
        L40:
            android.graphics.GraphicsStatsService$ActiveBuffer r0 = new android.graphics.GraphicsStatsService$ActiveBuffer     // Catch: java.io.IOException -> L57
            r1 = r0
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            r7 = r20
            r1.<init>(r3, r4, r5, r6, r7)     // Catch: java.io.IOException -> L57
            java.util.ArrayList<android.graphics.GraphicsStatsService$ActiveBuffer> r1 = r9.mActive     // Catch: java.io.IOException -> L57
            r1.add(r0)     // Catch: java.io.IOException -> L57
            return r0
        L57:
            r0 = move-exception
            android.os.RemoteException r1 = new android.os.RemoteException
            java.lang.String r2 = "Failed to allocate space"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.GraphicsStatsService.fetchActiveBuffersLocked(android.view.IGraphicsStatsCallback, int, int, java.lang.String, long):android.graphics.GraphicsStatsService$ActiveBuffer");
    }

    private HashSet<File> dumpActiveLocked(long dump, ArrayList<HistoricalBuffer> buffers) {
        HashSet<File> skipFiles = new HashSet<>(buffers.size());
        for (int i = 0; i < buffers.size(); i++) {
            HistoricalBuffer buffer = buffers.get(i);
            File path = pathForApp(buffer.mInfo);
            skipFiles.add(path);
            nAddToDump(dump, path.getAbsolutePath(), buffer.mInfo.mPackageName, buffer.mInfo.mVersionCode, buffer.mInfo.mStartTime, buffer.mInfo.mEndTime, buffer.mData);
        }
        return skipFiles;
    }

    private void dumpHistoricalLocked(long dump, HashSet<File> skipFiles) {
        File[] fileArr;
        File[] listFiles = this.mGraphicsStatsDir.listFiles();
        int length = listFiles.length;
        int i = 0;
        while (i < length) {
            File date = listFiles[i];
            File[] listFiles2 = date.listFiles();
            int length2 = listFiles2.length;
            int i2 = 0;
            while (i2 < length2) {
                File pkg = listFiles2[i2];
                File[] listFiles3 = pkg.listFiles();
                int length3 = listFiles3.length;
                int i3 = 0;
                while (i3 < length3) {
                    File version = listFiles3[i3];
                    File data = new File(version, "total");
                    if (skipFiles.contains(data)) {
                        fileArr = listFiles;
                    } else {
                        fileArr = listFiles;
                        nAddToDump(dump, data.getAbsolutePath());
                    }
                    i3++;
                    listFiles = fileArr;
                }
                i2++;
                listFiles = listFiles;
            }
            i++;
            listFiles = listFiles;
        }
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
        ArrayList<HistoricalBuffer> buffers;
        if (DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, fout)) {
            boolean dumpProto = false;
            int length = args.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String str = args[i];
                if (!LocationTimeZoneManager.DUMP_STATE_OPTION_PROTO.equals(str)) {
                    i++;
                } else {
                    dumpProto = true;
                    break;
                }
            }
            synchronized (this.mLock) {
                buffers = new ArrayList<>(this.mActive.size());
                for (int i2 = 0; i2 < this.mActive.size(); i2++) {
                    try {
                        buffers.add(new HistoricalBuffer(this.mActive.get(i2)));
                    } catch (IOException e) {
                    }
                }
            }
            long dump = nCreateDump(fd.getInt$(), dumpProto);
            try {
                synchronized (this.mFileAccessLock) {
                    HashSet<File> skipList = dumpActiveLocked(dump, buffers);
                    buffers.clear();
                    dumpHistoricalLocked(dump, skipList);
                }
            } finally {
                nFinishDump(dump);
            }
        }
    }

    protected void finalize() throws Throwable {
        nativeDestructor();
    }

    private final class BufferInfo {
        long mEndTime;
        final String mPackageName;
        long mStartTime;
        final long mVersionCode;

        BufferInfo(String packageName, long versionCode, long startTime) {
            this.mPackageName = packageName;
            this.mVersionCode = versionCode;
            this.mStartTime = startTime;
        }
    }

    private final class ActiveBuffer implements IBinder.DeathRecipient {
        final IGraphicsStatsCallback mCallback;
        final BufferInfo mInfo;
        ByteBuffer mMapping;
        final int mPid;
        SharedMemory mProcessBuffer;
        final IBinder mToken;
        final int mUid;

        ActiveBuffer(IGraphicsStatsCallback token, int uid, int pid, String packageName, long versionCode) throws RemoteException, IOException {
            this.mInfo = GraphicsStatsService.this.new BufferInfo(packageName, versionCode, System.currentTimeMillis());
            this.mUid = uid;
            this.mPid = pid;
            this.mCallback = token;
            this.mToken = this.mCallback.asBinder();
            this.mToken.linkToDeath(this, 0);
            try {
                this.mProcessBuffer = SharedMemory.create("GFXStats-" + pid, GraphicsStatsService.this.mAshmemSize);
                this.mMapping = this.mProcessBuffer.mapReadWrite();
            } catch (ErrnoException ex) {
                ex.rethrowAsIOException();
            }
            this.mMapping.position(0);
            this.mMapping.put(GraphicsStatsService.this.mZeroData, 0, GraphicsStatsService.this.mAshmemSize);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mToken.unlinkToDeath(this, 0);
            GraphicsStatsService.this.processDied(this);
        }

        void closeAllBuffers() {
            if (this.mMapping != null) {
                SharedMemory.unmap(this.mMapping);
                this.mMapping = null;
            }
            if (this.mProcessBuffer != null) {
                this.mProcessBuffer.close();
                this.mProcessBuffer = null;
            }
        }

        ParcelFileDescriptor getPfd() {
            try {
                return this.mProcessBuffer.getFdDup();
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to get PFD from memory file", ex);
            }
        }

        void readBytes(byte[] buffer, int count) throws IOException {
            if (this.mMapping == null) {
                throw new IOException("SharedMemory has been deactivated");
            }
            this.mMapping.position(0);
            this.mMapping.get(buffer, 0, count);
        }
    }

    private final class HistoricalBuffer {
        final byte[] mData;
        final BufferInfo mInfo;

        HistoricalBuffer(ActiveBuffer active) throws IOException {
            this.mData = new byte[GraphicsStatsService.this.mAshmemSize];
            this.mInfo = active.mInfo;
            this.mInfo.mEndTime = System.currentTimeMillis();
            active.readBytes(this.mData, GraphicsStatsService.this.mAshmemSize);
        }
    }
}
