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
    private ArrayList<ActiveBuffer> mActive;
    private final AlarmManager mAlarmManager;
    private final AppOpsManager mAppOps;
    private final int mAshmemSize;
    private final Context mContext;
    private final Object mFileAccessLock;
    private final GraphicsRendererPolicy mGraphicsRendererPolicy;
    private File mGraphicsStatsDir;
    private final Object mLock;
    private boolean mRotateIsScheduled;
    private Handler mWriteOutHandler;
    private final byte[] mZeroData;

    public enum GraphicsStatsRenderEngine {
        GL,
        VK
    }

    private static native void nAddToDump(long j, String str);

    private static native void nAddToDump(long j, String str, int i, String str2, long j2, long j3, long j4, byte[] bArr);

    private static native long nCreateDump(int i, boolean z);

    private static native void nFinishDump(long j);

    private static native void nFinishDumpInMemory(long j, long j2, boolean z);

    private static native int nGetAshmemSize();

    private static native void nSaveBuffer(String str, int i, String str2, long j, long j2, long j3, byte[] bArr);

    private static native void nativeDestructor();

    private native void nativeInit();

    public GraphicsStatsService(Context context) {
        int nGetAshmemSize = nGetAshmemSize();
        this.mAshmemSize = nGetAshmemSize;
        this.mZeroData = new byte[nGetAshmemSize];
        this.mLock = new Object();
        this.mActive = new ArrayList<>();
        this.mFileAccessLock = new Object();
        this.mRotateIsScheduled = false;
        this.mContext = context;
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        File file = new File(new File(Environment.getDataDirectory(), "system"), GRAPHICS_STATS_SERVICE);
        this.mGraphicsStatsDir = file;
        file.mkdirs();
        if (!this.mGraphicsStatsDir.exists()) {
            throw new IllegalStateException("Graphics stats directory does not exist: " + this.mGraphicsStatsDir.getAbsolutePath());
        }
        HandlerThread handlerThread = new HandlerThread("GraphicsStats-disk", 10);
        handlerThread.start();
        this.mGraphicsRendererPolicy = new GraphicsRendererPolicy(context);
        this.mWriteOutHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() { // from class: android.graphics.GraphicsStatsService.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    GraphicsStatsService.this.saveBuffer((HistoricalBuffer) message.obj);
                } else if (i == 2) {
                    GraphicsStatsService.this.deleteOldBuffers();
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
        Calendar normalizeDate = normalizeDate(System.currentTimeMillis());
        normalizeDate.add(5, 1);
        this.mAlarmManager.setExact(1, normalizeDate.getTimeInMillis(), TAG, new AlarmManager.OnAlarmListener() { // from class: android.graphics.GraphicsStatsService$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                GraphicsStatsService.this.onAlarm();
            }
        }, this.mWriteOutHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAlarm() {
        int i;
        ActiveBuffer[] activeBufferArr;
        synchronized (this.mLock) {
            this.mRotateIsScheduled = false;
            scheduleRotateLocked();
            activeBufferArr = (ActiveBuffer[]) this.mActive.toArray(new ActiveBuffer[0]);
        }
        for (ActiveBuffer activeBuffer : activeBufferArr) {
            try {
                activeBuffer.mCallback.onRotateGraphicsStatsBuffer();
            } catch (RemoteException e) {
                Log.w(TAG, String.format("Failed to notify '%s' (pid=%d) to rotate buffers", activeBuffer.mInfo.mPackageName, Integer.valueOf(activeBuffer.mPid)), e);
            }
        }
        this.mWriteOutHandler.sendEmptyMessageDelayed(2, JobInfo.MIN_BACKOFF_MILLIS);
    }

    @Override // android.view.IGraphicsStats
    public ParcelFileDescriptor requestBufferForProcess(String str, IGraphicsStatsCallback iGraphicsStatsCallback) throws RemoteException {
        String str2;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAppOps.checkPackage(callingUid, str);
                str2 = null;
                PackageInfo packageInfoAsUser = this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, UserHandle.getUserId(callingUid));
                try {
                    synchronized (this.mLock) {
                        try {
                            return requestBufferForProcessLocked(iGraphicsStatsCallback, callingUid, callingPid, str, packageInfoAsUser.getLongVersionCode());
                        } catch (Throwable th) {
                            th = th;
                            str2 = str;
                            Throwable th2 = th;
                            try {
                                throw th2;
                            } catch (PackageManager.NameNotFoundException unused) {
                                throw new RemoteException("Unable to find package: '" + str2 + "'");
                            }
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (PackageManager.NameNotFoundException unused2) {
                str2 = str;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.view.IGraphicsStats
    public int requestRenderEngineFor(String str) throws RemoteException {
        Log.d(TAG, "requestRenderEngineFor(" + str + NavigationBarInflaterView.KEY_CODE_END);
        if (str == null) {
            Log.w(TAG, "packageName is null.");
            return GraphicsStatsRenderEngine.VK.ordinal();
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAppOps.checkPackage(callingUid, str);
                return this.mGraphicsRendererPolicy.getRendererType(str);
            } catch (Exception unused) {
                throw new RemoteException("Unable to find package: '" + str + "'");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void pullGraphicsStats(boolean z, long j) throws RemoteException {
        if (Binder.getCallingUid() != 1066) {
            StringWriter stringWriter = new StringWriter();
            FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter);
            if (!DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, fastPrintWriter)) {
                fastPrintWriter.flush();
                throw new RemoteException(stringWriter.toString());
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            pullGraphicsStatsImpl(z, j);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void pullGraphicsStatsImpl(boolean z, long j) {
        long timeInMillis;
        ArrayList<HistoricalBuffer> arrayList;
        if (z) {
            timeInMillis = normalizeDate(System.currentTimeMillis() - 86400000).getTimeInMillis();
        } else {
            timeInMillis = normalizeDate(System.currentTimeMillis()).getTimeInMillis();
        }
        synchronized (this.mLock) {
            arrayList = new ArrayList<>(this.mActive.size());
            for (int i = 0; i < this.mActive.size(); i++) {
                ActiveBuffer activeBuffer = this.mActive.get(i);
                if (activeBuffer.mInfo.mStartTime == timeInMillis) {
                    try {
                        arrayList.add(new HistoricalBuffer(activeBuffer));
                    } catch (IOException unused) {
                    }
                }
            }
        }
        long nCreateDump = nCreateDump(-1, true);
        try {
            synchronized (this.mFileAccessLock) {
                HashSet<File> dumpActiveLocked = dumpActiveLocked(nCreateDump, arrayList);
                arrayList.clear();
                File file = new File(this.mGraphicsStatsDir, String.format("%d", Long.valueOf(timeInMillis)));
                if (file.exists()) {
                    File[] listFiles = file.listFiles();
                    for (File file2 : listFiles) {
                        for (File file3 : file2.listFiles()) {
                            File file4 = new File(file3, "total");
                            if (!dumpActiveLocked.contains(file4)) {
                                nAddToDump(nCreateDump, file4.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        } finally {
            nFinishDumpInMemory(nCreateDump, j, z);
        }
    }

    private ParcelFileDescriptor requestBufferForProcessLocked(IGraphicsStatsCallback iGraphicsStatsCallback, int i, int i2, String str, long j) throws RemoteException {
        ActiveBuffer fetchActiveBuffersLocked = fetchActiveBuffersLocked(iGraphicsStatsCallback, i, i2, str, j);
        scheduleRotateLocked();
        return fetchActiveBuffersLocked.getPfd();
    }

    private Calendar normalizeDate(long j) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        calendar.setTimeInMillis(j);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }

    private File pathForApp(BufferInfo bufferInfo) {
        return new File(this.mGraphicsStatsDir, String.format("%d/%s/%d/total", Long.valueOf(normalizeDate(bufferInfo.mStartTime).getTimeInMillis()), bufferInfo.mPackageName, Long.valueOf(bufferInfo.mVersionCode)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveBuffer(HistoricalBuffer historicalBuffer) {
        if (Trace.isTagEnabled(524288L)) {
            Trace.traceBegin(524288L, "saving graphicsstats for " + historicalBuffer.mInfo.mPackageName);
        }
        synchronized (this.mFileAccessLock) {
            File pathForApp = pathForApp(historicalBuffer.mInfo);
            File parentFile = pathForApp.getParentFile();
            parentFile.mkdirs();
            if (!parentFile.exists()) {
                Log.w(TAG, "Unable to create path: '" + parentFile.getAbsolutePath() + "'");
                return;
            }
            nSaveBuffer(pathForApp.getAbsolutePath(), historicalBuffer.mInfo.mUid, historicalBuffer.mInfo.mPackageName, historicalBuffer.mInfo.mVersionCode, historicalBuffer.mInfo.mStartTime, historicalBuffer.mInfo.mEndTime, historicalBuffer.mData);
            Trace.traceEnd(524288L);
        }
    }

    private void deleteRecursiveLocked(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                deleteRecursiveLocked(file2);
            }
        }
        if (file.delete()) {
            return;
        }
        Log.w(TAG, "Failed to delete '" + file.getAbsolutePath() + "'!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteOldBuffers() {
        Trace.traceBegin(524288L, "deleting old graphicsstats buffers");
        synchronized (this.mFileAccessLock) {
            File[] listFiles = this.mGraphicsStatsDir.listFiles();
            if (listFiles != null && listFiles.length > 3) {
                int length = listFiles.length;
                long[] jArr = new long[length];
                for (int i = 0; i < listFiles.length; i++) {
                    try {
                        jArr[i] = Long.parseLong(listFiles[i].getName());
                    } catch (NumberFormatException unused) {
                    }
                }
                if (length <= 3) {
                    return;
                }
                Arrays.sort(jArr);
                for (int i2 = 0; i2 < length - 3; i2++) {
                    deleteRecursiveLocked(new File(this.mGraphicsStatsDir, Long.toString(jArr[i2])));
                }
                Trace.traceEnd(524288L);
            }
        }
    }

    private void addToSaveQueue(ActiveBuffer activeBuffer) {
        try {
            Message.obtain(this.mWriteOutHandler, 1, new HistoricalBuffer(activeBuffer)).sendToTarget();
        } catch (IOException e) {
            Log.w(TAG, "Failed to copy graphicsstats from " + activeBuffer.mInfo.mPackageName, e);
        }
        activeBuffer.closeAllBuffers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDied(ActiveBuffer activeBuffer) {
        synchronized (this.mLock) {
            this.mActive.remove(activeBuffer);
        }
        addToSaveQueue(activeBuffer);
    }

    private ActiveBuffer fetchActiveBuffersLocked(IGraphicsStatsCallback iGraphicsStatsCallback, int i, int i2, String str, long j) throws RemoteException {
        int size = this.mActive.size();
        long timeInMillis = normalizeDate(System.currentTimeMillis()).getTimeInMillis();
        for (int i3 = 0; i3 < size; i3++) {
            ActiveBuffer activeBuffer = this.mActive.get(i3);
            if (activeBuffer.mPid == i2 && activeBuffer.mUid == i) {
                if (activeBuffer.mInfo.mStartTime >= timeInMillis) {
                    return activeBuffer;
                }
                activeBuffer.binderDied();
            }
        }
        try {
            ActiveBuffer activeBuffer2 = new ActiveBuffer(iGraphicsStatsCallback, i, i2, str, j);
            this.mActive.add(activeBuffer2);
            return activeBuffer2;
        } catch (IOException unused) {
            throw new RemoteException("Failed to allocate space");
        }
    }

    private HashSet<File> dumpActiveLocked(long j, ArrayList<HistoricalBuffer> arrayList) {
        HashSet<File> hashSet = new HashSet<>(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            HistoricalBuffer historicalBuffer = arrayList.get(i);
            File pathForApp = pathForApp(historicalBuffer.mInfo);
            hashSet.add(pathForApp);
            nAddToDump(j, pathForApp.getAbsolutePath(), historicalBuffer.mInfo.mUid, historicalBuffer.mInfo.mPackageName, historicalBuffer.mInfo.mVersionCode, historicalBuffer.mInfo.mStartTime, historicalBuffer.mInfo.mEndTime, historicalBuffer.mData);
        }
        return hashSet;
    }

    private void dumpHistoricalLocked(long j, HashSet<File> hashSet) {
        for (File file : this.mGraphicsStatsDir.listFiles()) {
            for (File file2 : file.listFiles()) {
                for (File file3 : file2.listFiles()) {
                    File file4 = new File(file3, "total");
                    if (!hashSet.contains(file4)) {
                        nAddToDump(j, file4.getAbsolutePath());
                    }
                }
            }
        }
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        boolean z;
        ArrayList<HistoricalBuffer> arrayList;
        if (DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = false;
                    break;
                } else {
                    if (LocationTimeZoneManager.DUMP_STATE_OPTION_PROTO.equals(strArr[i])) {
                        z = true;
                        break;
                    }
                    i++;
                }
            }
            synchronized (this.mLock) {
                arrayList = new ArrayList<>(this.mActive.size());
                for (int i2 = 0; i2 < this.mActive.size(); i2++) {
                    try {
                        arrayList.add(new HistoricalBuffer(this.mActive.get(i2)));
                    } catch (IOException unused) {
                    }
                }
            }
            long nCreateDump = nCreateDump(fileDescriptor.getInt$(), z);
            try {
                synchronized (this.mFileAccessLock) {
                    HashSet<File> dumpActiveLocked = dumpActiveLocked(nCreateDump, arrayList);
                    arrayList.clear();
                    dumpHistoricalLocked(nCreateDump, dumpActiveLocked);
                }
            } finally {
                nFinishDump(nCreateDump);
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
        final int mUid;
        final long mVersionCode;

        BufferInfo(GraphicsStatsService graphicsStatsService, int i, String str, long j, long j2) {
            this.mUid = i;
            this.mPackageName = str;
            this.mVersionCode = j;
            this.mStartTime = j2;
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

        ActiveBuffer(IGraphicsStatsCallback iGraphicsStatsCallback, int i, int i2, String str, long j) throws RemoteException, IOException {
            this.mInfo = new BufferInfo(GraphicsStatsService.this, i, str, j, System.currentTimeMillis());
            this.mUid = i;
            this.mPid = i2;
            this.mCallback = iGraphicsStatsCallback;
            IBinder asBinder = iGraphicsStatsCallback.asBinder();
            this.mToken = asBinder;
            asBinder.linkToDeath(this, 0);
            try {
                SharedMemory create = SharedMemory.create("GFXStats-" + i2, GraphicsStatsService.this.mAshmemSize);
                this.mProcessBuffer = create;
                this.mMapping = create.mapReadWrite();
            } catch (ErrnoException e) {
                e.rethrowAsIOException();
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
            ByteBuffer byteBuffer = this.mMapping;
            if (byteBuffer != null) {
                SharedMemory.unmap(byteBuffer);
                this.mMapping = null;
            }
            SharedMemory sharedMemory = this.mProcessBuffer;
            if (sharedMemory != null) {
                sharedMemory.close();
                this.mProcessBuffer = null;
            }
        }

        ParcelFileDescriptor getPfd() {
            try {
                return this.mProcessBuffer.getFdDup();
            } catch (IOException e) {
                throw new IllegalStateException("Failed to get PFD from memory file", e);
            }
        }

        void readBytes(byte[] bArr, int i) throws IOException {
            ByteBuffer byteBuffer = this.mMapping;
            if (byteBuffer == null) {
                throw new IOException("SharedMemory has been deactivated");
            }
            byteBuffer.position(0);
            this.mMapping.get(bArr, 0, i);
        }
    }

    private final class HistoricalBuffer {
        final byte[] mData;
        final BufferInfo mInfo;

        HistoricalBuffer(ActiveBuffer activeBuffer) throws IOException {
            byte[] bArr = new byte[GraphicsStatsService.this.mAshmemSize];
            this.mData = bArr;
            BufferInfo bufferInfo = activeBuffer.mInfo;
            this.mInfo = bufferInfo;
            bufferInfo.mEndTime = System.currentTimeMillis();
            activeBuffer.readBytes(bArr, GraphicsStatsService.this.mAshmemSize);
        }
    }
}
