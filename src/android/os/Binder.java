package android.os;

import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.media.MediaMetrics;
import android.os.IBinder;
import android.util.ExceptionUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.BinderCallHeavyHitterWatcher;
import com.android.internal.os.BinderInternal;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FunctionalUtils;
import dalvik.annotation.optimization.CriticalNative;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Supplier;
import libcore.io.IoUtils;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes3.dex */
public class Binder implements IBinder {
    public static final boolean CHECK_PARCEL_SIZE = false;
    private static final boolean FIND_POTENTIAL_LEAKS = false;
    private static final int NATIVE_ALLOCATION_SIZE = 500;
    static final String TAG = "Binder";
    private static final int TRANSACTION_TRACE_NAME_ID_LIMIT = 1024;
    public static final int UNSET_WORKSOURCE = -1;
    private String mDescriptor;
    private IBinder mExtension;
    private final long mObject;
    private IInterface mOwner;
    private volatile String mSimpleDescriptor;
    private volatile AtomicReferenceArray<String> mTransactionTraceNames;
    public static boolean isSystemServerBinderTrackerEnabled = Boolean.parseBoolean(SystemProperties.get("persist.systemserver.sa_bindertracker", "false"));
    public static boolean LOG_RUNTIME_EXCEPTION = false;
    private static volatile String sDumpDisabled = null;
    private static volatile TransactionTracker sTransactionTracker = null;
    private static BinderInternal.Observer sObserver = null;
    private static volatile BinderCallHeavyHitterWatcher sHeavyHitterWatcher = null;
    private static volatile boolean sStackTrackingEnabled = false;
    static volatile boolean sWarnOnBlocking = false;
    static volatile boolean isSystemServer = false;
    static ThreadLocal<Boolean> sWarnOnBlockingOnCurrentThread = ThreadLocal.withInitial(new Supplier() { // from class: android.os.Binder$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            Boolean valueOf;
            valueOf = Boolean.valueOf(Binder.sWarnOnBlocking);
            return valueOf;
        }
    });
    private static boolean sIsHandlingBinderTransaction = false;
    private static IBinderCallback sBinderCallback = null;
    private static volatile BinderInternal.WorkSourceProvider sWorkSourceProvider = new BinderInternal.WorkSourceProvider() { // from class: android.os.Binder$$ExternalSyntheticLambda1
        @Override // com.android.internal.os.BinderInternal.WorkSourceProvider
        public final int resolveWorkSourceUid(int i) {
            int callingUid;
            callingUid = Binder.getCallingUid();
            return callingUid;
        }
    };

    public static final native void blockUntilThreadAvailable();

    static void checkParcel(IBinder iBinder, int i, Parcel parcel, String str) {
    }

    @CriticalNative
    public static final native long clearCallingIdentity();

    @CriticalNative
    public static final native long clearCallingWorkSource();

    public static final native void flushPendingCommands();

    @CriticalNative
    public static final native int getCallingPid();

    @CriticalNative
    public static final native int getCallingUid();

    @CriticalNative
    public static final native int getCallingWorkSourceUid();

    private static native long getNativeBBinderHolder();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeFinalizer();

    @CriticalNative
    public static final native int getThreadStrictModePolicy();

    @CriticalNative
    private static native boolean hasExplicitIdentity();

    @CriticalNative
    public static final native boolean isDirectlyHandlingTransactionNative();

    @CriticalNative
    public static final native void restoreCallingIdentity(long j);

    @CriticalNative
    public static final native void restoreCallingWorkSource(long j);

    @CriticalNative
    public static final native long setCallingWorkSourceUid(int i);

    private final native void setExtensionNative(IBinder iBinder);

    @CriticalNative
    public static final native void setThreadStrictModePolicy(int i);

    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final native void forceDowngradeToSystemStability();

    public int getMaxTransactionId() {
        return 0;
    }

    public String getTransactionName(int i) {
        return null;
    }

    @Override // android.os.IBinder
    public boolean isBinderAlive() {
        return true;
    }

    @Override // android.os.IBinder
    public void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public final native void markVintfStability();

    protected void onUnhandledException(int i, int i2, Exception exc) {
    }

    @Override // android.os.IBinder
    public boolean pingBinder() {
        return true;
    }

    @Override // android.os.IBinder
    public boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        return true;
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(Binder.class.getClassLoader(), Binder.getNativeFinalizer(), 500);

        private NoImagePreloadHolder() {
        }
    }

    public static void enableStackTracking() {
        sStackTrackingEnabled = true;
    }

    public static void disableStackTracking() {
        sStackTrackingEnabled = false;
    }

    public static boolean isStackTrackingEnabled() {
        return sStackTrackingEnabled;
    }

    public static synchronized TransactionTracker getTransactionTracker() {
        TransactionTracker transactionTracker;
        synchronized (Binder.class) {
            if (sTransactionTracker == null) {
                sTransactionTracker = new TransactionTracker();
            }
            transactionTracker = sTransactionTracker;
        }
        return transactionTracker;
    }

    public static void setObserver(BinderInternal.Observer observer) {
        sObserver = observer;
    }

    public static void setWarnOnBlocking(boolean z) {
        sWarnOnBlocking = z;
    }

    public static void setSystemServerProcess() {
        isSystemServer = true;
    }

    public static IBinder allowBlocking(IBinder iBinder) {
        if (iBinder instanceof BinderProxy) {
            ((BinderProxy) iBinder).mWarnOnBlocking = false;
            return iBinder;
        }
        if (iBinder != null && iBinder.getInterfaceDescriptor() != null && iBinder.queryLocalInterface(iBinder.getInterfaceDescriptor()) == null) {
            Log.w(TAG, "Unable to allow blocking on interface " + iBinder);
        }
        return iBinder;
    }

    public static IBinder defaultBlocking(IBinder iBinder) {
        if (iBinder instanceof BinderProxy) {
            ((BinderProxy) iBinder).mWarnOnBlocking = sWarnOnBlocking;
        }
        return iBinder;
    }

    public static void copyAllowBlocking(IBinder iBinder, IBinder iBinder2) {
        if ((iBinder instanceof BinderProxy) && (iBinder2 instanceof BinderProxy)) {
            ((BinderProxy) iBinder2).mWarnOnBlocking = ((BinderProxy) iBinder).mWarnOnBlocking;
        }
    }

    public static void allowBlockingForCurrentThread() {
        sWarnOnBlockingOnCurrentThread.set(false);
    }

    public static void defaultBlockingForCurrentThread() {
        sWarnOnBlockingOnCurrentThread.set(Boolean.valueOf(sWarnOnBlocking));
    }

    public static final boolean isDirectlyHandlingTransaction() {
        return sIsHandlingBinderTransaction || isDirectlyHandlingTransactionNative();
    }

    public static void setIsDirectlyHandlingTransactionOverride(boolean z) {
        sIsHandlingBinderTransaction = z;
    }

    public static final int getCallingUidOrThrow() {
        if (!isDirectlyHandlingTransaction() && !hasExplicitIdentity()) {
            throw new IllegalStateException("Thread is not in a binder transaction, and the calling identity has not been explicitly set with clearCallingIdentity");
        }
        return getCallingUid();
    }

    public static final int getCallingUidOrWtf(String str) {
        if (!isDirectlyHandlingTransaction() && !hasExplicitIdentity()) {
            Slog.wtf(TAG, str + ": Thread is not in a binder transaction, and the calling identity has not been explicitly set with clearCallingIdentity");
        }
        return getCallingUid();
    }

    public static final UserHandle getCallingUserHandle() {
        return UserHandle.of(UserHandle.getUserId(getCallingUid()));
    }

    public static final void withCleanCallingIdentity(FunctionalUtils.ThrowingRunnable throwingRunnable) {
        long clearCallingIdentity = clearCallingIdentity();
        try {
            throwingRunnable.runOrThrow();
            restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            restoreCallingIdentity(clearCallingIdentity);
            throw ExceptionUtils.propagate(th);
        }
    }

    public static final <T> T withCleanCallingIdentity(FunctionalUtils.ThrowingSupplier<T> throwingSupplier) {
        long clearCallingIdentity = clearCallingIdentity();
        try {
            T orThrow = throwingSupplier.getOrThrow();
            restoreCallingIdentity(clearCallingIdentity);
            return orThrow;
        } catch (Throwable th) {
            restoreCallingIdentity(clearCallingIdentity);
            throw ExceptionUtils.propagate(th);
        }
    }

    public static final void joinThreadPool() {
        BinderInternal.joinThreadPool();
    }

    public static final boolean isProxy(IInterface iInterface) {
        return iInterface.asBinder() != iInterface;
    }

    public static final void setTransactionCallback(IBinderCallback iBinderCallback) {
        sBinderCallback = iBinderCallback;
    }

    public static final void transactionCallback(int i, int i2, int i3, int i4) {
        IBinderCallback iBinderCallback = sBinderCallback;
        if (iBinderCallback != null) {
            iBinderCallback.onTransactionError(i, i2, i3, i4);
        }
    }

    public Binder() {
        this(null);
    }

    public Binder(String str) {
        this.mExtension = null;
        this.mTransactionTraceNames = null;
        this.mSimpleDescriptor = null;
        long nativeBBinderHolder = getNativeBBinderHolder();
        this.mObject = nativeBBinderHolder;
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, nativeBBinderHolder);
        this.mDescriptor = str;
    }

    public void attachInterface(IInterface iInterface, String str) {
        this.mOwner = iInterface;
        this.mDescriptor = str;
    }

    @Override // android.os.IBinder
    public String getInterfaceDescriptor() {
        return this.mDescriptor;
    }

    @Override // android.os.IBinder
    public IInterface queryLocalInterface(String str) {
        String str2 = this.mDescriptor;
        if (str2 == null || !str2.equals(str)) {
            return null;
        }
        return this.mOwner;
    }

    public static void setDumpDisabled(String str) {
        sDumpDisabled = str;
    }

    @SystemApi
    public interface ProxyTransactListener {
        void onTransactEnded(Object obj);

        Object onTransactStarted(IBinder iBinder, int i);

        default Object onTransactStarted(IBinder iBinder, int i, int i2) {
            return onTransactStarted(iBinder, i);
        }
    }

    public static class PropagateWorkSourceTransactListener implements ProxyTransactListener {
        @Override // android.os.Binder.ProxyTransactListener
        public Object onTransactStarted(IBinder iBinder, int i) {
            int uid = ThreadLocalWorkSource.getUid();
            if (uid != -1) {
                return Long.valueOf(Binder.setCallingWorkSourceUid(uid));
            }
            return null;
        }

        @Override // android.os.Binder.ProxyTransactListener
        public void onTransactEnded(Object obj) {
            if (obj != null) {
                Binder.restoreCallingWorkSource(((Long) obj).longValue());
            }
        }
    }

    @SystemApi
    public static void setProxyTransactListener(ProxyTransactListener proxyTransactListener) {
        BinderProxy.setTransactListener(proxyTransactListener);
    }

    protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        ParcelFileDescriptor readFileDescriptor;
        FileDescriptor fileDescriptor;
        if (i == 1598968902) {
            parcel2.writeString(getInterfaceDescriptor());
            return true;
        }
        if (i == 1598311760) {
            readFileDescriptor = parcel.readFileDescriptor();
            String[] readStringArray = parcel.readStringArray();
            if (readFileDescriptor != null) {
                try {
                    dump(readFileDescriptor.getFileDescriptor(), readStringArray);
                } finally {
                    IoUtils.closeQuietly(readFileDescriptor);
                }
            }
            if (parcel2 != null) {
                parcel2.writeNoException();
            } else {
                StrictMode.clearGatheredViolations();
            }
            return true;
        }
        if (i != 1598246212) {
            if (i != 1598640985) {
                return false;
            }
            if (parcel2 != null) {
                parcel2.writeBoolean(isSystemServer);
            } else {
                StrictMode.clearGatheredViolations();
            }
            return true;
        }
        ParcelFileDescriptor readFileDescriptor2 = parcel.readFileDescriptor();
        ParcelFileDescriptor readFileDescriptor3 = parcel.readFileDescriptor();
        readFileDescriptor = parcel.readFileDescriptor();
        String[] readStringArray2 = parcel.readStringArray();
        ShellCallback createFromParcel = ShellCallback.CREATOR.createFromParcel(parcel);
        ResultReceiver createFromParcel2 = ResultReceiver.CREATOR.createFromParcel(parcel);
        if (readFileDescriptor3 != null) {
            if (readFileDescriptor2 != null) {
                try {
                    fileDescriptor = readFileDescriptor2.getFileDescriptor();
                } catch (Throwable th) {
                    IoUtils.closeQuietly(readFileDescriptor2);
                    IoUtils.closeQuietly(readFileDescriptor3);
                    if (parcel2 != null) {
                        parcel2.writeNoException();
                        throw th;
                    }
                    StrictMode.clearGatheredViolations();
                    throw th;
                }
            } else {
                fileDescriptor = null;
            }
            shellCommand(fileDescriptor, readFileDescriptor3.getFileDescriptor(), readFileDescriptor != null ? readFileDescriptor.getFileDescriptor() : readFileDescriptor3.getFileDescriptor(), readStringArray2, createFromParcel, createFromParcel2);
        }
        IoUtils.closeQuietly(readFileDescriptor2);
        IoUtils.closeQuietly(readFileDescriptor3);
        if (parcel2 != null) {
            parcel2.writeNoException();
        } else {
            StrictMode.clearGatheredViolations();
        }
        return true;
    }

    public final String getTransactionTraceName(int i) {
        boolean z = getMaxTransactionId() == 0;
        if (this.mTransactionTraceNames == null) {
            int min = z ? 1024 : Math.min(getMaxTransactionId(), 1024);
            this.mSimpleDescriptor = getSimpleDescriptor();
            this.mTransactionTraceNames = new AtomicReferenceArray<>(min + 1);
        }
        int i2 = z ? i : i - 1;
        if (i2 >= this.mTransactionTraceNames.length() || i2 < 0) {
            return null;
        }
        String acquire = this.mTransactionTraceNames.getAcquire(i2);
        if (acquire != null) {
            return acquire;
        }
        String transactionName = getTransactionName(i);
        StringBuffer stringBuffer = new StringBuffer("AIDL::java::");
        if (transactionName != null) {
            stringBuffer.append(this.mSimpleDescriptor).append("::").append(transactionName);
        } else {
            stringBuffer.append(this.mSimpleDescriptor).append("::#").append(i);
        }
        stringBuffer.append("::server");
        String stringBuffer2 = stringBuffer.toString();
        this.mTransactionTraceNames.setRelease(i2, stringBuffer2);
        return stringBuffer2;
    }

    private String getSimpleDescriptor() {
        String str = this.mDescriptor;
        if (str == null) {
            return TAG;
        }
        int lastIndexOf = str.lastIndexOf(MediaMetrics.SEPARATOR);
        return lastIndexOf > 0 ? str.substring(lastIndexOf + 1) : str;
    }

    @Override // android.os.IBinder
    public void dump(FileDescriptor fileDescriptor, String[] strArr) {
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(fileDescriptor));
        try {
            doDump(fileDescriptor, fastPrintWriter, strArr);
        } finally {
            fastPrintWriter.flush();
        }
    }

    void doDump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (sDumpDisabled == null) {
            try {
                dump(fileDescriptor, printWriter, strArr);
                return;
            } catch (SecurityException e) {
                printWriter.println("Security exception: " + e.getMessage());
                throw e;
            } catch (Throwable th) {
                printWriter.println();
                printWriter.println("Exception occurred while dumping:");
                th.printStackTrace(printWriter);
                return;
            }
        }
        printWriter.println(sDumpDisabled);
    }

    @Override // android.os.IBinder
    public void dumpAsync(final FileDescriptor fileDescriptor, final String[] strArr) {
        final FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(fileDescriptor));
        new Thread("Binder.dumpAsync") { // from class: android.os.Binder.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Binder.this.dump(fileDescriptor, fastPrintWriter, strArr);
                } finally {
                    fastPrintWriter.flush();
                }
            }
        }.start();
    }

    @Override // android.os.IBinder
    public void shellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
        onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
        int callingUid = getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            resultReceiver.send(-1, null);
            throw new SecurityException("Shell commands are only callable by ADB");
        }
        if (fileDescriptor == null) {
            try {
                fileDescriptor = new FileInputStream("/dev/null").getFD();
            } catch (IOException e) {
                if (fileDescriptor3 != null) {
                    fileDescriptor2 = fileDescriptor3;
                }
                FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(fileDescriptor2));
                fastPrintWriter.println("Failed to open /dev/null: " + e.getMessage());
                fastPrintWriter.flush();
                return;
            }
        }
        if (fileDescriptor2 == null) {
            fileDescriptor2 = new FileOutputStream("/dev/null").getFD();
        }
        if (fileDescriptor3 == null) {
            fileDescriptor3 = fileDescriptor2;
        }
        if (strArr == null) {
            strArr = new String[0];
        }
        try {
            ParcelFileDescriptor dup = ParcelFileDescriptor.dup(fileDescriptor);
            try {
                ParcelFileDescriptor dup2 = ParcelFileDescriptor.dup(fileDescriptor2);
                try {
                    ParcelFileDescriptor dup3 = ParcelFileDescriptor.dup(fileDescriptor3);
                    try {
                        int handleShellCommand = handleShellCommand(dup, dup2, dup3, strArr);
                        if (dup3 != null) {
                            dup3.close();
                        }
                        if (dup2 != null) {
                            dup2.close();
                        }
                        if (dup != null) {
                            dup.close();
                        }
                        resultReceiver.send(handleShellCommand, null);
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                if (dup != null) {
                    try {
                        dup.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e2) {
            FastPrintWriter fastPrintWriter2 = new FastPrintWriter(new FileOutputStream(fileDescriptor3));
            fastPrintWriter2.println("dup() failed: " + e2.getMessage());
            fastPrintWriter2.flush();
        } finally {
            resultReceiver.send(-1, null);
        }
    }

    @SystemApi
    public int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(parcelFileDescriptor3.getFileDescriptor()));
        fastPrintWriter.println("No shell command implementation.");
        fastPrintWriter.flush();
        return 0;
    }

    @Override // android.os.IBinder
    public final IBinder getExtension() {
        return this.mExtension;
    }

    public final void setExtension(IBinder iBinder) {
        this.mExtension = iBinder;
        setExtensionNative(iBinder);
    }

    @Override // android.os.IBinder
    public final boolean transact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (parcel != null) {
            parcel.setDataPosition(0);
        }
        boolean onTransact = onTransact(i, parcel, parcel2, i2);
        if (parcel2 != null) {
            parcel2.setDataPosition(0);
        }
        return onTransact;
    }

    public static void setWorkSourceProvider(BinderInternal.WorkSourceProvider workSourceProvider) {
        if (workSourceProvider == null) {
            throw new IllegalArgumentException("workSourceProvider cannot be null");
        }
        sWorkSourceProvider = workSourceProvider;
    }

    private boolean execTransact(int i, long j, long j2, int i2) {
        Parcel obtain = Parcel.obtain(j);
        Parcel obtain2 = Parcel.obtain(j2);
        int callingUid = obtain.isForRpc() ? -1 : getCallingUid();
        long uid = callingUid == -1 ? -1L : ThreadLocalWorkSource.setUid(callingUid);
        try {
            boolean execTransactInternal = execTransactInternal(i, obtain, obtain2, i2, callingUid);
            obtain2.recycle();
            obtain.recycle();
            if (callingUid != -1) {
                ThreadLocalWorkSource.restore(uid);
            }
            return execTransactInternal;
        } finally {
        }
    }

    private boolean execTransactInternal(int i, Parcel parcel, Parcel parcel2, int i2, int i3) {
        boolean z;
        boolean onTransact;
        BinderInternal.Observer observer = sObserver;
        BinderInternal.CallSession callStarted = observer != null ? observer.callStarted(this, i, -1) : null;
        boolean isTagEnabled = Trace.isTagEnabled(16777216L);
        getMaxTransactionId();
        String transactionTraceName = isTagEnabled ? getTransactionTraceName(i) : null;
        boolean z2 = isTagEnabled && transactionTraceName != null;
        try {
            try {
                BinderCallHeavyHitterWatcher binderCallHeavyHitterWatcher = sHeavyHitterWatcher;
                if (binderCallHeavyHitterWatcher != null && i3 != -1) {
                    binderCallHeavyHitterWatcher.onTransaction(i3, getClass(), i);
                }
                if (z2) {
                    Trace.traceBegin(16777216L, transactionTraceName);
                }
                if ((i2 & 2) == 0 || i3 == -1) {
                    onTransact = onTransact(i, parcel, parcel2, i2);
                } else {
                    AppOpsManager.startNotedAppOpsCollection(i3);
                    try {
                        onTransact = onTransact(i, parcel, parcel2, i2);
                        AppOpsManager.finishNotedAppOpsCollection();
                    } catch (Throwable th) {
                        AppOpsManager.finishNotedAppOpsCollection();
                        throw th;
                    }
                }
                z = onTransact;
                if (z2) {
                    Trace.traceEnd(16777216L);
                }
                if (observer != null) {
                    observer.callEnded(callStarted, parcel.dataSize(), parcel2.dataSize(), sWorkSourceProvider.resolveWorkSourceUid(parcel.readCallingWorkSourceUid()));
                }
                checkParcel(this, i, parcel2, "Unreasonably large binder reply buffer");
            } catch (Throwable th2) {
                if (z2) {
                    Trace.traceEnd(16777216L);
                }
                if (observer != null) {
                    observer.callEnded(callStarted, parcel.dataSize(), parcel2.dataSize(), sWorkSourceProvider.resolveWorkSourceUid(parcel.readCallingWorkSourceUid()));
                }
                checkParcel(this, i, parcel2, "Unreasonably large binder reply buffer");
                throw th2;
            }
        } catch (RemoteException | RuntimeException e) {
            if (observer != null) {
                observer.callThrewException(callStarted, e);
            }
            if (LOG_RUNTIME_EXCEPTION) {
                Log.w(TAG, "Caught a RuntimeException from the binder stub implementation.", e);
            }
            if ((i2 & 1) != 0) {
                if (e instanceof RemoteException) {
                    Log.w(TAG, "Binder call failed.", e);
                } else {
                    Log.w(TAG, "Caught a RuntimeException from the binder stub implementation.", e);
                }
                onUnhandledException(i, i2, e);
            } else {
                parcel2.setDataSize(0);
                parcel2.setDataPosition(0);
                if (Parcel.getExceptionCode(e) == 0) {
                    onUnhandledException(i, i2, e);
                }
                parcel2.writeException(e);
            }
            if (z2) {
                Trace.traceEnd(16777216L);
            }
            if (observer != null) {
                observer.callEnded(callStarted, parcel.dataSize(), parcel2.dataSize(), sWorkSourceProvider.resolveWorkSourceUid(parcel.readCallingWorkSourceUid()));
            }
            checkParcel(this, i, parcel2, "Unreasonably large binder reply buffer");
            z = true;
        }
        StrictMode.clearGatheredViolations();
        return z;
    }

    public static synchronized void setHeavyHitterWatcherConfig(boolean z, int i, float f, BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener binderCallHeavyHitterListener) {
        synchronized (Binder.class) {
            Slog.i(TAG, "Setting heavy hitter watcher config: " + z + ", " + i + ", " + f);
            BinderCallHeavyHitterWatcher binderCallHeavyHitterWatcher = sHeavyHitterWatcher;
            boolean z2 = false;
            if (z) {
                if (binderCallHeavyHitterListener == null) {
                    throw new IllegalArgumentException();
                }
                if (binderCallHeavyHitterWatcher == null) {
                    binderCallHeavyHitterWatcher = BinderCallHeavyHitterWatcher.getInstance();
                    z2 = true;
                }
                binderCallHeavyHitterWatcher.setConfig(true, i, f, binderCallHeavyHitterListener);
                if (z2) {
                    sHeavyHitterWatcher = binderCallHeavyHitterWatcher;
                }
            } else if (binderCallHeavyHitterWatcher != null) {
                binderCallHeavyHitterWatcher.setConfig(false, 0, 0.0f, null);
            }
        }
    }
}
