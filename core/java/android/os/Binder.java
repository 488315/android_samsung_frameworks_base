package android.os;

import android.annotation.SystemApi;
import android.media.MediaMetrics;
import android.util.ExceptionUtils;
import android.util.Log;
import android.util.Slog;

import com.android.internal.os.BinderCallHeavyHitterWatcher;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;

import dalvik.annotation.optimization.CriticalNative;

import libcore.io.IoUtils;
import libcore.util.NativeAllocationRegistry;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Supplier;

public class Binder implements IBinder {
    public static final boolean CHECK_PARCEL_SIZE = false;
    private static final boolean FIND_POTENTIAL_LEAKS = false;
    private static final int NATIVE_ALLOCATION_SIZE = 500;
    static final String TAG = "Binder";
    private static final int TRANSACTION_TRACE_NAME_ID_LIMIT = 1024;
    public static final int UNSET_WORKSOURCE = -1;
    private static volatile ThreadLocal<SomeArgs> sIdentity$ravenwood;
    private String mDescriptor;
    private final long mObject;
    private IInterface mOwner;
    private volatile String mSimpleDescriptor;
    private volatile AtomicReferenceArray<String> mTransactionTraceNames;
    public static boolean isSystemServerBinderTrackerEnabled =
            Boolean.parseBoolean(
                    SystemProperties.get("persist.systemserver.sa_bindertracker", "false"));
    public static boolean LOG_RUNTIME_EXCEPTION = false;
    private static volatile String sDumpDisabled = null;
    private static volatile TransactionTracker sTransactionTracker = null;
    private static BinderInternal.Observer sObserver = null;
    private static volatile BinderCallHeavyHitterWatcher sHeavyHitterWatcher = null;
    private static volatile boolean sStackTrackingEnabled = false;
    static volatile boolean sWarnOnBlocking = false;
    static volatile boolean isSystemServer = false;
    static ThreadLocal<Boolean> sWarnOnBlockingOnCurrentThread =
            ThreadLocal.withInitial(
                    new Supplier() { // from class: android.os.Binder$$ExternalSyntheticLambda0
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            Boolean valueOf;
                            valueOf = Boolean.valueOf(Binder.sWarnOnBlocking);
                            return valueOf;
                        }
                    });
    private static boolean sIsHandlingBinderTransaction = false;
    private static IBinderCallback sBinderCallback = null;
    private static volatile BinderInternal.WorkSourceProvider sWorkSourceProvider =
            new BinderInternal
                    .WorkSourceProvider() { // from class:
                                            // android.os.Binder$$ExternalSyntheticLambda1
                @Override // com.android.internal.os.BinderInternal.WorkSourceProvider
                public final int resolveWorkSourceUid(int i) {
                    int callingUid;
                    callingUid = Binder.getCallingUid();
                    return callingUid;
                }
            };

    public static final native void blockUntilThreadAvailable();

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

    @CriticalNative
    public static final native void setThreadStrictModePolicy(int i);

    public final native void forceDowngradeToSystemStability();

    @Override // android.os.IBinder
    public final native IBinder getExtension();

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public final native void markVintfStability();

    public final native void setExtension(IBinder iBinder);

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry =
                new NativeAllocationRegistry(
                        Binder.class.getClassLoader(), Binder.getNativeFinalizer(), 500);

        private NoImagePreloadHolder() {}
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

    public static void setWarnOnBlocking(boolean warnOnBlocking) {
        sWarnOnBlocking = warnOnBlocking;
    }

    public static void setSystemServerProcess() {
        isSystemServer = true;
    }

    public static IBinder allowBlocking(IBinder binder) {
        try {
            if (binder instanceof BinderProxy) {
                ((BinderProxy) binder).mWarnOnBlocking = false;
            } else if (binder != null
                    && binder.getInterfaceDescriptor() != null
                    && binder.queryLocalInterface(binder.getInterfaceDescriptor()) == null) {
                Log.w(TAG, "Unable to allow blocking on interface " + binder);
            }
        } catch (RemoteException e) {
        }
        return binder;
    }

    public static IBinder defaultBlocking(IBinder binder) {
        if (binder instanceof BinderProxy) {
            ((BinderProxy) binder).mWarnOnBlocking = sWarnOnBlocking;
        }
        return binder;
    }

    public static void copyAllowBlocking(IBinder fromBinder, IBinder toBinder) {
        if ((fromBinder instanceof BinderProxy) && (toBinder instanceof BinderProxy)) {
            ((BinderProxy) toBinder).mWarnOnBlocking = ((BinderProxy) fromBinder).mWarnOnBlocking;
        }
    }

    public static void allowBlockingForCurrentThread() {
        sWarnOnBlockingOnCurrentThread.set(false);
    }

    public static void defaultBlockingForCurrentThread() {
        sWarnOnBlockingOnCurrentThread.set(Boolean.valueOf(sWarnOnBlocking));
    }

    private static class IdentitySupplier implements Supplier<SomeArgs> {
        private IdentitySupplier() {}

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.function.Supplier
        public SomeArgs get() {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = Boolean.FALSE;
            args.argi1 = Process.myUid();
            args.argi2 = Process.myPid();
            return args;
        }
    }

    public static final boolean isDirectlyHandlingTransactionNative$ravenwood() {
        return false;
    }

    public static final boolean isDirectlyHandlingTransaction() {
        return sIsHandlingBinderTransaction || isDirectlyHandlingTransactionNative();
    }

    public static void setIsDirectlyHandlingTransactionOverride(boolean isInTransaction) {
        sIsHandlingBinderTransaction = isInTransaction;
    }

    private static boolean hasExplicitIdentity$ravenwood() {
        return ((SomeArgs)
                                ((ThreadLocal)
                                                Preconditions.requireNonNullViaRavenwoodRule(
                                                        sIdentity$ravenwood))
                                        .get())
                        .arg1
                == Boolean.TRUE;
    }

    public static final int getCallingUidOrThrow() {
        if (!isDirectlyHandlingTransaction() && !hasExplicitIdentity()) {
            throw new IllegalStateException(
                    "Thread is not in a binder transaction, and the calling identity has not been"
                        + " explicitly set with clearCallingIdentity");
        }
        return getCallingUid();
    }

    public static final int getCallingUidOrWtf(String message) {
        if (!isDirectlyHandlingTransaction() && !hasExplicitIdentity()) {
            Slog.wtf(
                    TAG,
                    message
                            + ": Thread is not in a binder transaction, and the calling identity"
                            + " has not been explicitly set with clearCallingIdentity");
        }
        return getCallingUid();
    }

    public static final UserHandle getCallingUserHandle() {
        return UserHandle.of(UserHandle.getUserId(getCallingUid()));
    }

    public static final long clearCallingIdentity$ravenwood() {
        long res;
        SomeArgs args =
                (SomeArgs)
                        ((ThreadLocal)
                                        Preconditions.requireNonNullViaRavenwoodRule(
                                                sIdentity$ravenwood))
                                .get();
        long res2 = (args.argi1 << 32) | args.argi2;
        if (args.arg1 == Boolean.TRUE) {
            res = res2 | 1073741824;
        } else {
            res = res2 & (-1073741825);
        }
        args.arg1 = Boolean.TRUE;
        args.argi1 = Process.myUid();
        args.argi2 = Process.myPid();
        return res;
    }

    public static final void restoreCallingIdentity$ravenwood(long token) {
        SomeArgs args =
                (SomeArgs)
                        ((ThreadLocal)
                                        Preconditions.requireNonNullViaRavenwoodRule(
                                                sIdentity$ravenwood))
                                .get();
        args.arg1 = (1073741824 & token) != 0 ? Boolean.TRUE : Boolean.FALSE;
        args.argi1 = (int) (token >> 32);
        args.argi2 = (int) ((-1073741825) & token);
    }

    public static final void withCleanCallingIdentity(FunctionalUtils.ThrowingRunnable action) {
        long callingIdentity = clearCallingIdentity();
        try {
            action.runOrThrow();
            restoreCallingIdentity(callingIdentity);
            if (0 != 0) {
                throw ExceptionUtils.propagate(null);
            }
        } catch (Throwable throwable) {
            restoreCallingIdentity(callingIdentity);
            throw ExceptionUtils.propagate(throwable);
        }
    }

    public static final <T> T withCleanCallingIdentity(FunctionalUtils.ThrowingSupplier<T> action) {
        long callingIdentity = clearCallingIdentity();
        try {
            T orThrow = action.getOrThrow();
            restoreCallingIdentity(callingIdentity);
            if (0 == 0) {
                return orThrow;
            }
            throw ExceptionUtils.propagate(null);
        } catch (Throwable throwable) {
            restoreCallingIdentity(callingIdentity);
            throw ExceptionUtils.propagate(throwable);
        }
    }

    public static final void flushPendingCommands$ravenwood() {}

    public static final void joinThreadPool() {
        BinderInternal.joinThreadPool();
    }

    public static final boolean isProxy(IInterface iface) {
        return iface.asBinder() != iface;
    }

    public static final void setTransactionCallback(IBinderCallback callback) {
        sBinderCallback = callback;
    }

    public static final void transactionCallback(int pid, int code, int flags, int err) {
        if (sBinderCallback != null) {
            sBinderCallback.onTransactionError(pid, code, flags, err);
        }
    }

    public Binder() {
        this(null);
    }

    public Binder(String descriptor) {
        this.mTransactionTraceNames = null;
        this.mSimpleDescriptor = null;
        this.mObject = getNativeBBinderHolder();
        if (this.mObject != 0) {
            NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mObject);
        }
        this.mDescriptor = descriptor;
    }

    public void attachInterface(IInterface owner, String descriptor) {
        this.mOwner = owner;
        this.mDescriptor = descriptor;
    }

    @Override // android.os.IBinder
    public String getInterfaceDescriptor() {
        return this.mDescriptor;
    }

    @Override // android.os.IBinder
    public boolean pingBinder() {
        return true;
    }

    @Override // android.os.IBinder
    public boolean isBinderAlive() {
        return true;
    }

    @Override // android.os.IBinder
    public IInterface queryLocalInterface(String descriptor) {
        if (this.mDescriptor != null && this.mDescriptor.equals(descriptor)) {
            return this.mOwner;
        }
        return null;
    }

    public static void setDumpDisabled(String msg) {
        sDumpDisabled = msg;
    }

    @SystemApi
    public interface ProxyTransactListener {
        void onTransactEnded(Object obj);

        Object onTransactStarted(IBinder iBinder, int i);

        default Object onTransactStarted(IBinder binder, int transactionCode, int flags) {
            return onTransactStarted(binder, transactionCode);
        }
    }

    public static class PropagateWorkSourceTransactListener implements ProxyTransactListener {
        @Override // android.os.Binder.ProxyTransactListener
        public Object onTransactStarted(IBinder binder, int transactionCode) {
            int uid = ThreadLocalWorkSource.getUid();
            if (uid != -1) {
                return Long.valueOf(Binder.setCallingWorkSourceUid(uid));
            }
            return null;
        }

        @Override // android.os.Binder.ProxyTransactListener
        public void onTransactEnded(Object session) {
            if (session != null) {
                long token = ((Long) session).longValue();
                Binder.restoreCallingWorkSource(token);
            }
        }
    }

    @SystemApi
    public static void setProxyTransactListener(ProxyTransactListener listener) {
        BinderProxy.setTransactListener(listener);
    }

    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
            throws RemoteException {
        FileDescriptor fileDescriptor;
        if (code != 1598968902) {
            if (code != 1598311760) {
                if (code != 1598246212) {
                    if (code == 1598640985) {
                        if (reply != null) {
                            reply.writeBoolean(isSystemServer);
                        } else {
                            StrictMode.clearGatheredViolations();
                        }
                        return true;
                    }
                    return false;
                }
                ParcelFileDescriptor in = data.readFileDescriptor();
                ParcelFileDescriptor out = data.readFileDescriptor();
                ParcelFileDescriptor err = data.readFileDescriptor();
                String[] args = data.readStringArray();
                ShellCallback shellCallback = ShellCallback.CREATOR.createFromParcel(data);
                ResultReceiver resultReceiver = ResultReceiver.CREATOR.createFromParcel(data);
                if (out != null) {
                    if (in == null) {
                        fileDescriptor = null;
                    } else {
                        try {
                            fileDescriptor = in.getFileDescriptor();
                        } catch (Throwable th) {
                            IoUtils.closeQuietly(in);
                            IoUtils.closeQuietly(out);
                            IoUtils.closeQuietly(err);
                            if (reply != null) {
                                reply.writeNoException();
                            } else {
                                StrictMode.clearGatheredViolations();
                            }
                            throw th;
                        }
                    }
                    shellCommand(
                            fileDescriptor,
                            out.getFileDescriptor(),
                            err != null ? err.getFileDescriptor() : out.getFileDescriptor(),
                            args,
                            shellCallback,
                            resultReceiver);
                }
                IoUtils.closeQuietly(in);
                IoUtils.closeQuietly(out);
                IoUtils.closeQuietly(err);
                if (reply != null) {
                    reply.writeNoException();
                } else {
                    StrictMode.clearGatheredViolations();
                }
                return true;
            }
            ParcelFileDescriptor fd = data.readFileDescriptor();
            String[] args2 = data.readStringArray();
            if (fd != null) {
                try {
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    dump(fd.getFileDescriptor(), args2);
                    IoUtils.closeQuietly(fd);
                } catch (Throwable th3) {
                    th = th3;
                    IoUtils.closeQuietly(fd);
                    throw th;
                }
            }
            if (reply != null) {
                reply.writeNoException();
            } else {
                StrictMode.clearGatheredViolations();
            }
            return true;
        }
        reply.writeString(getInterfaceDescriptor());
        return true;
    }

    public String getTransactionName(int transactionCode) {
        return null;
    }

    public final String getTransactionTraceName(int transactionCode) {
        boolean isInterfaceUserDefined = getMaxTransactionId() == 0;
        if (this.mTransactionTraceNames == null) {
            int highestId = isInterfaceUserDefined ? 1024 : Math.min(getMaxTransactionId(), 1024);
            this.mSimpleDescriptor = getSimpleDescriptor();
            this.mTransactionTraceNames = new AtomicReferenceArray<>(highestId + 1);
        }
        int index = isInterfaceUserDefined ? transactionCode : transactionCode - 1;
        if (index >= this.mTransactionTraceNames.length() || index < 0) {
            return null;
        }
        String transactionTraceName = this.mTransactionTraceNames.getAcquire(index);
        if (transactionTraceName == null) {
            String transactionName = getTransactionName(transactionCode);
            StringBuffer buf = new StringBuffer();
            buf.append("AIDL::java::");
            if (transactionName != null) {
                buf.append(this.mSimpleDescriptor).append("::").append(transactionName);
            } else {
                buf.append(this.mSimpleDescriptor).append("::#").append(transactionCode);
            }
            buf.append("::server");
            String transactionTraceName2 = buf.toString();
            this.mTransactionTraceNames.setRelease(index, transactionTraceName2);
            return transactionTraceName2;
        }
        return transactionTraceName;
    }

    private String getSimpleDescriptor() {
        String descriptor = this.mDescriptor;
        if (descriptor == null) {
            return TAG;
        }
        int dot = descriptor.lastIndexOf(MediaMetrics.SEPARATOR);
        if (dot > 0) {
            return descriptor.substring(dot + 1);
        }
        return descriptor;
    }

    public int getMaxTransactionId() {
        return 0;
    }

    @Override // android.os.IBinder
    public void dump(FileDescriptor fd, String[] args) {
        FileOutputStream fout = new FileOutputStream(fd);
        PrintWriter pw = new FastPrintWriter(fout);
        try {
            doDump(fd, pw, args);
        } finally {
            pw.flush();
        }
    }

    void doDump(FileDescriptor fd, PrintWriter pw, String[] args) {
        String disabled = sDumpDisabled;
        if (disabled == null) {
            try {
                dump(fd, pw, args);
                return;
            } catch (SecurityException e) {
                pw.println("Security exception: " + e.getMessage());
                throw e;
            } catch (Throwable e2) {
                pw.println();
                pw.println("Exception occurred while dumping:");
                e2.printStackTrace(pw);
                return;
            }
        }
        pw.println(sDumpDisabled);
    }

    @Override // android.os.IBinder
    public void dumpAsync(final FileDescriptor fd, final String[] args) {
        FileOutputStream fout = new FileOutputStream(fd);
        final PrintWriter pw = new FastPrintWriter(fout);
        Thread thr = new Thread("Binder.dumpAsync") { // from class: android.os.Binder.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            Binder.this.dump(fd, pw, args);
                        } finally {
                            pw.flush();
                        }
                    }
                };
        thr.start();
    }

    protected void dump(FileDescriptor fd, PrintWriter fout, String[] args) {}

    @Override // android.os.IBinder
    public void shellCommand(
            FileDescriptor in,
            FileDescriptor out,
            FileDescriptor err,
            String[] args,
            ShellCallback callback,
            ResultReceiver resultReceiver)
            throws RemoteException {
        onShellCommand(in, out, err, args, callback, resultReceiver);
    }

    public void onShellCommand(
            FileDescriptor in,
            FileDescriptor out,
            FileDescriptor err,
            String[] args,
            ShellCallback callback,
            ResultReceiver resultReceiver)
            throws RemoteException {
        ParcelFileDescriptor inPfd;
        ParcelFileDescriptor outPfd;
        int callingUid = getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            resultReceiver.send(-1, null);
            throw new SecurityException("Shell commands are only callable by ADB");
        }
        if (in == null) {
            try {
                in = new FileInputStream("/dev/null").getFD();
            } catch (IOException e) {
                PrintWriter pw = new FastPrintWriter(new FileOutputStream(err != null ? err : out));
                pw.println("Failed to open /dev/null: " + e.getMessage());
                pw.flush();
                resultReceiver.send(-1, null);
                return;
            }
        }
        if (out == null) {
            out = new FileOutputStream("/dev/null").getFD();
        }
        if (err == null) {
            err = out;
        }
        if (args == null) {
            args = new String[0];
        }
        int result = -1;
        try {
            try {
                inPfd = ParcelFileDescriptor.dup(in);
                try {
                    outPfd = ParcelFileDescriptor.dup(out);
                } catch (Throwable th) {
                    if (inPfd != null) {
                        try {
                            inPfd.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                PrintWriter pw2 = new FastPrintWriter(new FileOutputStream(err));
                pw2.println("dup() failed: " + e2.getMessage());
                pw2.flush();
            }
            try {
                ParcelFileDescriptor errPfd = ParcelFileDescriptor.dup(err);
                try {
                    result = handleShellCommand(inPfd, outPfd, errPfd, args);
                    if (errPfd != null) {
                        errPfd.close();
                    }
                    if (outPfd != null) {
                        outPfd.close();
                    }
                    if (inPfd != null) {
                        inPfd.close();
                    }
                } finally {
                }
            } finally {
            }
        } finally {
            resultReceiver.send(-1, null);
        }
    }

    @SystemApi
    public int handleShellCommand(
            ParcelFileDescriptor in,
            ParcelFileDescriptor out,
            ParcelFileDescriptor err,
            String[] args) {
        FileOutputStream ferr = new FileOutputStream(err.getFileDescriptor());
        PrintWriter pw = new FastPrintWriter(ferr);
        pw.println("No shell command implementation.");
        pw.flush();
        return 0;
    }

    @Override // android.os.IBinder
    public final boolean transact(int code, Parcel data, Parcel reply, int flags)
            throws RemoteException {
        if (data != null) {
            data.setDataPosition(0);
        }
        boolean r = onTransact(code, data, reply, flags);
        if (reply != null) {
            reply.setDataPosition(0);
        }
        return r;
    }

    @Override // android.os.IBinder
    public void linkToDeath(IBinder.DeathRecipient recipient, int flags) {}

    @Override // android.os.IBinder
    public boolean unlinkToDeath(IBinder.DeathRecipient recipient, int flags) {
        return true;
    }

    static void checkParcel(IBinder obj, int code, Parcel parcel, String msg) {}

    private static long getNativeBBinderHolder$ravenwood() {
        return 0L;
    }

    public static void setWorkSourceProvider(BinderInternal.WorkSourceProvider workSourceProvider) {
        if (workSourceProvider == null) {
            throw new IllegalArgumentException("workSourceProvider cannot be null");
        }
        sWorkSourceProvider = workSourceProvider;
    }

    private boolean execTransact(int code, long dataObj, long replyObj, int flags) {
        Parcel data = Parcel.obtain(dataObj);
        Parcel reply = Parcel.obtain(replyObj);
        int callingUid = data.isForRpc() ? -1 : getCallingUid();
        long origWorkSource = callingUid == -1 ? -1L : ThreadLocalWorkSource.setUid(callingUid);
        try {
            boolean execTransactInternal =
                    execTransactInternal(code, data, reply, flags, callingUid);
            reply.recycle();
            data.recycle();
            if (callingUid != -1) {
                ThreadLocalWorkSource.restore(origWorkSource);
            }
            return execTransactInternal;
        } finally {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0067, code lost:

       if (r6 != null) goto L36;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0069, code lost:

       r8 = android.os.Binder.sWorkSourceProvider.resolveWorkSourceUid(r18.readCallingWorkSourceUid());
       r6.callEnded(r7, r18.dataSize(), r19.dataSize(), r8);
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007e, code lost:

       checkParcel(r16, r17, r19, "Unreasonably large binder reply buffer");
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00bd, code lost:

       android.os.StrictMode.clearGatheredViolations();
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00c0, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00ba, code lost:

       if (r6 != null) goto L36;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean execTransactInternal(
            int r17, android.os.Parcel r18, android.os.Parcel r19, int r20, int r21) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r19
            r4 = r21
            java.lang.String r5 = "Unreasonably large binder reply buffer"
            com.android.internal.os.BinderInternal$Observer r6 = android.os.Binder.sObserver
            r0 = -1
            if (r6 == 0) goto L14
            com.android.internal.os.BinderInternal$CallSession r7 = r6.callStarted(r1, r2, r0)
            goto L15
        L14:
            r7 = 0
        L15:
            r8 = 16777216(0x1000000, double:8.289046E-317)
            boolean r10 = android.os.Trace.isTagEnabled(r8)
            int r11 = r16.getMaxTransactionId()
            r12 = 1
            if (r11 <= 0) goto L25
            r11 = r12
            goto L26
        L25:
            r11 = 0
        L26:
            if (r10 == 0) goto L2d
            java.lang.String r14 = r16.getTransactionTraceName(r17)
            goto L2e
        L2d:
            r14 = 0
        L2e:
            if (r10 == 0) goto L33
            if (r14 == 0) goto L33
            goto L34
        L33:
            r12 = 0
        L34:
            com.android.internal.os.BinderCallHeavyHitterWatcher r15 = android.os.Binder.sHeavyHitterWatcher     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84
            if (r15 == 0) goto L41
            if (r4 == r0) goto L41
            java.lang.Class r13 = r16.getClass()     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84
            r15.onTransaction(r4, r13, r2)     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84
        L41:
            if (r12 == 0) goto L46
            android.os.Trace.traceBegin(r8, r14)     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84
        L46:
            r13 = r20 & 2
            if (r13 == 0) goto L5e
            if (r4 == r0) goto L5e
            android.app.AppOpsManager.startNotedAppOpsCollection(r21)     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84
            boolean r0 = r16.onTransact(r17, r18, r19, r20)     // Catch: java.lang.Throwable -> L57
            android.app.AppOpsManager.finishNotedAppOpsCollection()     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84 java.lang.Throwable -> L84
            goto L62
        L57:
            r0 = move-exception
            r13 = r0
            android.app.AppOpsManager.finishNotedAppOpsCollection()     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84 java.lang.Throwable -> L84
            throw r13     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84 java.lang.Throwable -> L84
        L5e:
            boolean r0 = r16.onTransact(r17, r18, r19, r20)     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> L84 java.lang.Throwable -> L84
        L62:
            if (r12 == 0) goto L67
            android.os.Trace.traceEnd(r8)
        L67:
            if (r6 == 0) goto L7e
        L69:
            com.android.internal.os.BinderInternal$WorkSourceProvider r8 = android.os.Binder.sWorkSourceProvider
            int r9 = r18.readCallingWorkSourceUid()
            int r8 = r8.resolveWorkSourceUid(r9)
            int r9 = r18.dataSize()
            int r13 = r19.dataSize()
            r6.callEnded(r7, r9, r13, r8)
        L7e:
            checkParcel(r1, r2, r3, r5)
            goto Lbd
        L82:
            r0 = move-exception
            goto Lc1
        L84:
            r0 = move-exception
            if (r6 == 0) goto L8a
            r6.callThrewException(r7, r0)     // Catch: java.lang.Throwable -> L82
        L8a:
            boolean r13 = android.os.Binder.LOG_RUNTIME_EXCEPTION     // Catch: java.lang.Throwable -> L82
            java.lang.String r15 = "Caught a RuntimeException from the binder stub implementation."
            java.lang.String r8 = "Binder"
            if (r13 == 0) goto L95
            android.util.Log.w(r8, r15, r0)     // Catch: java.lang.Throwable -> L82
        L95:
            r9 = r20 & 1
            if (r9 == 0) goto La7
            boolean r9 = r0 instanceof android.os.RemoteException     // Catch: java.lang.Throwable -> L82
            if (r9 == 0) goto La3
            java.lang.String r9 = "Binder call failed."
            android.util.Log.w(r8, r9, r0)     // Catch: java.lang.Throwable -> L82
            goto Lb1
        La3:
            android.util.Log.w(r8, r15, r0)     // Catch: java.lang.Throwable -> L82
            goto Lb1
        La7:
            r8 = 0
            r3.setDataSize(r8)     // Catch: java.lang.Throwable -> L82
            r3.setDataPosition(r8)     // Catch: java.lang.Throwable -> L82
            r3.writeException(r0)     // Catch: java.lang.Throwable -> L82
        Lb1:
            r0 = 1
            if (r12 == 0) goto Lba
            r8 = 16777216(0x1000000, double:8.289046E-317)
            android.os.Trace.traceEnd(r8)
        Lba:
            if (r6 == 0) goto L7e
            goto L69
        Lbd:
            android.os.StrictMode.clearGatheredViolations()
            return r0
        Lc1:
            if (r12 == 0) goto Lc9
            r8 = 16777216(0x1000000, double:8.289046E-317)
            android.os.Trace.traceEnd(r8)
        Lc9:
            if (r6 == 0) goto Le0
            com.android.internal.os.BinderInternal$WorkSourceProvider r8 = android.os.Binder.sWorkSourceProvider
            int r9 = r18.readCallingWorkSourceUid()
            int r8 = r8.resolveWorkSourceUid(r9)
            int r9 = r18.dataSize()
            int r13 = r19.dataSize()
            r6.callEnded(r7, r9, r13, r8)
        Le0:
            checkParcel(r1, r2, r3, r5)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.Binder.execTransactInternal(int,"
                    + " android.os.Parcel, android.os.Parcel, int, int):boolean");
    }

    public static synchronized void setHeavyHitterWatcherConfig(
            boolean enabled,
            int batchSize,
            float threshold,
            BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener listener) {
        synchronized (Binder.class) {
            Slog.i(
                    TAG,
                    "Setting heavy hitter watcher config: "
                            + enabled
                            + ", "
                            + batchSize
                            + ", "
                            + threshold);
            BinderCallHeavyHitterWatcher watcher = sHeavyHitterWatcher;
            if (enabled) {
                if (listener == null) {
                    throw new IllegalArgumentException();
                }
                boolean newWatcher = false;
                if (watcher == null) {
                    watcher = BinderCallHeavyHitterWatcher.getInstance();
                    newWatcher = true;
                }
                watcher.setConfig(true, batchSize, threshold, listener);
                if (newWatcher) {
                    sHeavyHitterWatcher = watcher;
                }
            } else if (watcher != null) {
                watcher.setConfig(false, 0, 0.0f, null);
            }
        }
    }
}
