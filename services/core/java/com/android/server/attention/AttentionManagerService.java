package com.android.server.attention;

import android.R;
import android.app.ActivityThread;
import android.attention.AttentionManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.hardware.SensorPrivacyManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.service.attention.IAttentionCallback;
import android.service.attention.IAttentionService;
import android.service.attention.IProximityUpdateCallback;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class AttentionManagerService extends SystemService {
    protected static final int ATTENTION_CACHE_BUFFER_SIZE = 5;
    static final long DEFAULT_STALE_AFTER_MILLIS = 1000;
    static final String KEY_SERVICE_ENABLED = "service_enabled";
    static final String KEY_STALE_AFTER_MILLIS = "stale_after_millis";
    public static String sTestAttentionServicePackage;
    public AttentionCheckCacheBuffer mAttentionCheckCacheBuffer;
    public final AttentionHandler mAttentionHandler;
    public boolean mBinding;
    ComponentName mComponentName;
    public final AttentionServiceConnection mConnection;
    public final Context mContext;
    AttentionCheck mCurrentAttentionCheck;
    ProximityUpdate mCurrentProximityUpdate;
    boolean mIsProximityEnabled;
    boolean mIsServiceEnabled;
    public final Object mLock;
    public final PowerManager mPowerManager;
    public final SensorPrivacyManager mPrivacyManager;
    protected IAttentionService mService;
    public CountDownLatch mServiceBindingLatch;
    long mStaleAfterMillis;

    final class AttentionCheck {
        public final AttentionManagerInternal.AttentionCallbackInternal mCallbackInternal;
        public final AnonymousClass1 mIAttentionCallback;
        public boolean mIsDispatched;
        public boolean mIsFulfilled;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.attention.AttentionManagerService$AttentionCheck$1] */
        public AttentionCheck(
                final AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal,
                final AttentionManagerService attentionManagerService) {
            this.mCallbackInternal = attentionCallbackInternal;
            this.mIAttentionCallback =
                    new IAttentionCallback
                            .Stub() { // from class:
                                      // com.android.server.attention.AttentionManagerService.AttentionCheck.1
                        public final void onFailure(int i) {
                            AttentionCheck attentionCheck = AttentionCheck.this;
                            if (attentionCheck.mIsFulfilled) {
                                return;
                            }
                            attentionCheck.mIsFulfilled = true;
                            attentionCallbackInternal.onFailure(i);
                            FrameworkStatsLog.write(143, i);
                        }

                        public final void onSuccess(int i, long j) {
                            AttentionCheck attentionCheck = AttentionCheck.this;
                            if (attentionCheck.mIsFulfilled) {
                                return;
                            }
                            attentionCheck.mIsFulfilled = true;
                            attentionCallbackInternal.onSuccess(i, j);
                            FrameworkStatsLog.write(143, i);
                            AttentionManagerService attentionManagerService2 =
                                    attentionManagerService;
                            AttentionCheckCache attentionCheckCache =
                                    new AttentionCheckCache(SystemClock.uptimeMillis(), i, j);
                            synchronized (attentionManagerService2.mLock) {
                                try {
                                    if (attentionManagerService2.mAttentionCheckCacheBuffer
                                            == null) {
                                        attentionManagerService2.mAttentionCheckCacheBuffer =
                                                new AttentionCheckCacheBuffer();
                                    }
                                    AttentionCheckCacheBuffer attentionCheckCacheBuffer =
                                            attentionManagerService2.mAttentionCheckCacheBuffer;
                                    int i2 = attentionCheckCacheBuffer.mStartIndex;
                                    int i3 = attentionCheckCacheBuffer.mSize;
                                    attentionCheckCacheBuffer.mQueue[(i2 + i3) % 5] =
                                            attentionCheckCache;
                                    if (i3 == 5) {
                                        attentionCheckCacheBuffer.mStartIndex = i2 + 1;
                                    } else {
                                        attentionCheckCacheBuffer.mSize = i3 + 1;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    };
        }
    }

    public final class AttentionCheckCache {
        public final long mLastComputed;
        public final int mResult;
        public final long mTimestamp;

        public AttentionCheckCache(long j, int i, long j2) {
            this.mLastComputed = j;
            this.mResult = i;
            this.mTimestamp = j2;
        }
    }

    public final class AttentionCheckCacheBuffer {
        public final AttentionCheckCache[] mQueue = new AttentionCheckCache[5];
        public int mStartIndex = 0;
        public int mSize = 0;

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m256$$Nest$mdump(
                AttentionCheckCacheBuffer attentionCheckCacheBuffer,
                IndentingPrintWriter indentingPrintWriter) {
            AttentionCheckCache attentionCheckCache;
            attentionCheckCacheBuffer.getClass();
            indentingPrintWriter.println("attention check cache:");
            int i = 0;
            while (true) {
                int i2 = attentionCheckCacheBuffer.mSize;
                if (i >= i2) {
                    return;
                }
                if (i >= i2) {
                    attentionCheckCache = null;
                } else {
                    attentionCheckCache =
                            attentionCheckCacheBuffer
                                    .mQueue[(attentionCheckCacheBuffer.mStartIndex + i) % 5];
                }
                if (attentionCheckCache != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("timestamp=" + attentionCheckCache.mTimestamp);
                    indentingPrintWriter.println("result=" + attentionCheckCache.mResult);
                    indentingPrintWriter.decreaseIndent();
                }
                i++;
            }
        }
    }

    public class AttentionHandler extends Handler {
        public AttentionHandler() {
            super(Looper.myLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                synchronized (AttentionManagerService.this.mLock) {
                    AttentionManagerService.m254$$Nest$mcancelAndUnbindLocked(
                            AttentionManagerService.this);
                }
            } else {
                if (i != 2) {
                    return;
                }
                synchronized (AttentionManagerService.this.mLock) {
                    AttentionManagerService.this.cancel();
                }
            }
        }
    }

    public final class AttentionManagerServiceShellCommand extends ShellCommand {
        public final TestableAttentionCallbackInternal mTestableAttentionCallback;
        public final TestableProximityUpdateCallbackInternal mTestableProximityUpdateCallback;

        public final class TestableAttentionCallbackInternal
                extends AttentionManagerInternal.AttentionCallbackInternal {
            public int mLastCallbackCode;

            public final void onFailure(int i) {
                this.mLastCallbackCode = i;
            }

            public final void onSuccess(int i, long j) {
                this.mLastCallbackCode = i;
            }
        }

        public final class TestableProximityUpdateCallbackInternal
                implements AttentionManagerInternal.ProximityUpdateCallbackInternal {
            public double mLastCallbackCode;

            public final void onProximityUpdate(double d) {
                this.mLastCallbackCode = d;
            }
        }

        public AttentionManagerServiceShellCommand() {
            TestableAttentionCallbackInternal testableAttentionCallbackInternal =
                    new TestableAttentionCallbackInternal();
            testableAttentionCallbackInternal.mLastCallbackCode = -1;
            this.mTestableAttentionCallback = testableAttentionCallbackInternal;
            TestableProximityUpdateCallbackInternal testableProximityUpdateCallbackInternal =
                    new TestableProximityUpdateCallbackInternal();
            testableProximityUpdateCallbackInternal.mLastCallbackCode = -1.0d;
            this.mTestableProximityUpdateCallback = testableProximityUpdateCallbackInternal;
        }

        public final int onCommand(String str) {
            char c;
            char c2;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            PrintWriter errPrintWriter = getErrPrintWriter();
            try {
                switch (str.hashCode()) {
                    case -1208709968:
                        if (str.equals("getLastTestCallbackCode")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1002424240:
                        if (str.equals("getAttentionServiceComponent")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -415045819:
                        if (str.equals("setTestableAttentionService")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3045982:
                        if (str.equals("call")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1048378748:
                        if (str.equals("getLastTestProximityUpdateCallbackCode")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1193447472:
                        if (str.equals("clearTestableAttentionService")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0) {
                    PrintWriter outPrintWriter = getOutPrintWriter();
                    ComponentName resolveAttentionService =
                            AttentionManagerService.resolveAttentionService(
                                    AttentionManagerService.this.mContext);
                    outPrintWriter.println(
                            resolveAttentionService != null
                                    ? resolveAttentionService.flattenToShortString()
                                    : "");
                    return 0;
                }
                if (c != 1) {
                    if (c == 2) {
                        String nextArgRequired = getNextArgRequired();
                        PrintWriter outPrintWriter2 = getOutPrintWriter();
                        if (TextUtils.isEmpty(nextArgRequired)) {
                            outPrintWriter2.println("false");
                        } else {
                            AttentionManagerService.sTestAttentionServicePackage = nextArgRequired;
                            resetStates();
                            outPrintWriter2.println(
                                    AttentionManagerService.this.mComponentName != null
                                            ? "true"
                                            : "false");
                        }
                        return 0;
                    }
                    if (c == 3) {
                        AttentionManagerService.sTestAttentionServicePackage = "";
                        this.mTestableAttentionCallback.mLastCallbackCode = -1;
                        this.mTestableProximityUpdateCallback.mLastCallbackCode = -1.0d;
                        resetStates();
                        return 0;
                    }
                    if (c == 4) {
                        getOutPrintWriter()
                                .println(this.mTestableAttentionCallback.mLastCallbackCode);
                        return 0;
                    }
                    if (c != 5) {
                        return handleDefaultCommands(str);
                    }
                    getOutPrintWriter()
                            .println(this.mTestableProximityUpdateCallback.mLastCallbackCode);
                    return 0;
                }
                String nextArgRequired2 = getNextArgRequired();
                switch (nextArgRequired2.hashCode()) {
                    case -1571871954:
                        if (nextArgRequired2.equals("onStartProximityUpdates")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 685821932:
                        if (nextArgRequired2.equals("onStopProximityUpdates")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 763077136:
                        if (nextArgRequired2.equals("cancelCheckAttention")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1485997302:
                        if (nextArgRequired2.equals("checkAttention")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        c2 = 65535;
                        break;
                }
                if (c2 == 0) {
                    getOutPrintWriter()
                            .println(
                                    AttentionManagerService.this.checkAttention(
                                                    2000L, this.mTestableAttentionCallback)
                                            ? "true"
                                            : "false");
                    return 0;
                }
                if (c2 == 1) {
                    PrintWriter outPrintWriter3 = getOutPrintWriter();
                    AttentionManagerService.this.cancelAttentionCheck(
                            this.mTestableAttentionCallback);
                    outPrintWriter3.println("true");
                    return 0;
                }
                if (c2 == 2) {
                    getOutPrintWriter()
                            .println(
                                    AttentionManagerService.this.onStartProximityUpdates(
                                                    this.mTestableProximityUpdateCallback)
                                            ? "true"
                                            : "false");
                    return 0;
                }
                if (c2 != 3) {
                    throw new IllegalArgumentException("Invalid argument");
                }
                PrintWriter outPrintWriter4 = getOutPrintWriter();
                AttentionManagerService.this.onStopProximityUpdates(
                        this.mTestableProximityUpdateCallback);
                outPrintWriter4.println("true");
                return 0;
            } catch (IllegalArgumentException e) {
                errPrintWriter.println("Error: " + e.getMessage());
                return -1;
            }
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Attention commands: ");
            outPrintWriter.println(
                    "  setTestableAttentionService <service_package>: Bind to a custom"
                        + " implementation of attention service");
            outPrintWriter.println("  ---<service_package>:");
            outPrintWriter.println(
                    "       := Package containing the Attention Service implementation to bind to");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  ---returns:",
                    "       := true, if was bound successfully",
                    "       := false, if was not bound successfully",
                    "  clearTestableAttentionService: Undo custom bindings. Revert to previous"
                        + " behavior");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  getAttentionServiceComponent: Get the current service component string",
                    "  ---returns:",
                    "       := If valid, the component string (in shorten form) for the currently"
                        + " bound service.",
                    "       := else, empty string");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  call checkAttention: Calls check attention",
                    "  ---returns:",
                    "       := true, if the call was successfully dispatched to the service"
                        + " implementation. (to see the result, call getLastTestCallbackCode)",
                    "       := false, otherwise");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  call cancelCheckAttention: Cancels check attention",
                    "  call onStartProximityUpdates: Calls onStartProximityUpdates",
                    "  ---returns:",
                    "       := true, if the request was successfully dispatched to the service"
                        + " implementation. (to see the result, call"
                        + " getLastTestProximityUpdateCallbackCode)");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "       := false, otherwise",
                    "  call onStopProximityUpdates: Cancels proximity updates",
                    "  getLastTestCallbackCode",
                    "  ---returns:");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "       := An integer, representing the last callback code received from the"
                        + " bounded implementation. If none, it will return -1",
                    "  getLastTestProximityUpdateCallbackCode",
                    "  ---returns:",
                    "       := A double, representing the last proximity value received from the"
                        + " bounded implementation. If none, it will return -1.0");
        }

        public final void resetStates() {
            synchronized (AttentionManagerService.this.mLock) {
                AttentionManagerService attentionManagerService = AttentionManagerService.this;
                attentionManagerService.mCurrentProximityUpdate = null;
                AttentionManagerService.m254$$Nest$mcancelAndUnbindLocked(attentionManagerService);
            }
            AttentionManagerService attentionManagerService2 = AttentionManagerService.this;
            attentionManagerService2.mComponentName =
                    AttentionManagerService.resolveAttentionService(
                            attentionManagerService2.mContext);
        }
    }

    public final class AttentionServiceConnection implements ServiceConnection {
        public AttentionServiceConnection() {}

        public final void cleanupService() {
            init(null);
            AttentionManagerService.this.mServiceBindingLatch = new CountDownLatch(1);
        }

        public final void init(IAttentionService iAttentionService) {
            synchronized (AttentionManagerService.this.mLock) {
                AttentionManagerService attentionManagerService = AttentionManagerService.this;
                attentionManagerService.mService = iAttentionService;
                attentionManagerService.mBinding = false;
                AttentionManagerService.m255$$Nest$mhandlePendingCallbackLocked(
                        attentionManagerService);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            cleanupService();
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            cleanupService();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            init(IAttentionService.Stub.asInterface(iBinder));
            AttentionManagerService.this.mServiceBindingLatch.countDown();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            cleanupService();
        }
    }

    public final class BinderService extends Binder {
        public final AttentionManagerServiceShellCommand mAttentionManagerServiceShellCommand;

        public BinderService() {
            this.mAttentionManagerServiceShellCommand =
                    AttentionManagerService.this.new AttentionManagerServiceShellCommand();
        }

        @Override // android.os.Binder
        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    AttentionManagerService.this.mContext,
                    "AttentionManagerService",
                    printWriter)) {
                AttentionManagerService attentionManagerService = AttentionManagerService.this;
                IndentingPrintWriter indentingPrintWriter =
                        new IndentingPrintWriter(printWriter, "  ");
                attentionManagerService.getClass();
                indentingPrintWriter.println(
                        "Attention Manager Service (dumpsys attention) state:\n");
                StringBuilder m =
                        AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("isServiceEnabled="),
                                        attentionManagerService.mIsServiceEnabled,
                                        indentingPrintWriter,
                                        "mIsProximityEnabled="),
                                attentionManagerService.mIsProximityEnabled,
                                indentingPrintWriter,
                                "mStaleAfterMillis=");
                m.append(attentionManagerService.mStaleAfterMillis);
                indentingPrintWriter.println(m.toString());
                indentingPrintWriter.println(
                        "AttentionServicePackageName="
                                + attentionManagerService
                                        .mContext
                                        .getPackageManager()
                                        .getAttentionServicePackageName());
                indentingPrintWriter.println("Resolved component:");
                if (attentionManagerService.mComponentName != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println(
                            "Component=" + attentionManagerService.mComponentName.getPackageName());
                    indentingPrintWriter.println(
                            "Class=" + attentionManagerService.mComponentName.getClassName());
                    indentingPrintWriter.decreaseIndent();
                }
                synchronized (attentionManagerService.mLock) {
                    try {
                        indentingPrintWriter.println("binding=" + attentionManagerService.mBinding);
                        indentingPrintWriter.println("current attention check:");
                        AttentionCheck attentionCheck =
                                attentionManagerService.mCurrentAttentionCheck;
                        if (attentionCheck != null) {
                            attentionCheck.getClass();
                            indentingPrintWriter.increaseIndent();
                            StringBuilder m2 =
                                    AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0
                                            .m(
                                                    new StringBuilder("is dispatched="),
                                                    attentionCheck.mIsDispatched,
                                                    indentingPrintWriter,
                                                    "is fulfilled:=");
                            m2.append(attentionCheck.mIsFulfilled);
                            indentingPrintWriter.println(m2.toString());
                            indentingPrintWriter.decreaseIndent();
                        }
                        AttentionCheckCacheBuffer attentionCheckCacheBuffer =
                                attentionManagerService.mAttentionCheckCacheBuffer;
                        if (attentionCheckCacheBuffer != null) {
                            AttentionCheckCacheBuffer.m256$$Nest$mdump(
                                    attentionCheckCacheBuffer, indentingPrintWriter);
                        }
                        ProximityUpdate proximityUpdate =
                                attentionManagerService.mCurrentProximityUpdate;
                        if (proximityUpdate != null) {
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println(
                                    "is StartedUpdates=" + proximityUpdate.mStartedUpdates);
                            indentingPrintWriter.decreaseIndent();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            this.mAttentionManagerServiceShellCommand.exec(
                    this,
                    fileDescriptor,
                    fileDescriptor2,
                    fileDescriptor3,
                    strArr,
                    shellCallback,
                    resultReceiver);
        }
    }

    public final class LocalService extends AttentionManagerInternal {
        public LocalService() {}

        public final void cancelAttentionCheck(
                AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal) {
            AttentionManagerService.this.cancelAttentionCheck(attentionCallbackInternal);
        }

        public final boolean checkAttention(
                long j,
                AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal) {
            return AttentionManagerService.this.checkAttention(j, attentionCallbackInternal);
        }

        public final boolean isAttentionServiceSupported() {
            return AttentionManagerService.this.mIsServiceEnabled;
        }

        public final boolean isProximitySupported() {
            return AttentionManagerService.this.mIsProximityEnabled;
        }

        public final boolean onStartProximityUpdates(
                AttentionManagerInternal.ProximityUpdateCallbackInternal
                        proximityUpdateCallbackInternal) {
            return AttentionManagerService.this.onStartProximityUpdates(
                    proximityUpdateCallbackInternal);
        }

        public final void onStopProximityUpdates(
                AttentionManagerInternal.ProximityUpdateCallbackInternal
                        proximityUpdateCallbackInternal) {
            AttentionManagerService.this.onStopProximityUpdates(proximityUpdateCallbackInternal);
        }
    }

    final class ProximityUpdate {
        public final AttentionManagerInternal.ProximityUpdateCallbackInternal mCallbackInternal;
        public final AnonymousClass1 mIProximityUpdateCallback =
                new IProximityUpdateCallback
                        .Stub() { // from class:
                                  // com.android.server.attention.AttentionManagerService.ProximityUpdate.1
                    public final void onProximityUpdate(double d) {
                        ProximityUpdate.this.mCallbackInternal.onProximityUpdate(d);
                        synchronized (AttentionManagerService.this.mLock) {
                            AttentionManagerService.this.freeIfInactiveLocked();
                        }
                    }
                };
        public boolean mStartedUpdates;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.attention.AttentionManagerService$ProximityUpdate$1] */
        public ProximityUpdate(
                AttentionManagerInternal.ProximityUpdateCallbackInternal
                        proximityUpdateCallbackInternal) {
            this.mCallbackInternal = proximityUpdateCallbackInternal;
        }

        public final void cancelUpdates() {
            synchronized (AttentionManagerService.this.mLock) {
                if (this.mStartedUpdates) {
                    IAttentionService iAttentionService = AttentionManagerService.this.mService;
                    if (iAttentionService == null) {
                        this.mStartedUpdates = false;
                        return;
                    }
                    try {
                        iAttentionService.onStopProximityUpdates();
                        this.mStartedUpdates = false;
                    } catch (RemoteException e) {
                        Slog.e(
                                "AttentionManagerService",
                                "Cannot call into the AttentionService",
                                e);
                    }
                }
            }
        }

        public final boolean startUpdates() {
            synchronized (AttentionManagerService.this.mLock) {
                try {
                    if (this.mStartedUpdates) {
                        Slog.w(
                                "AttentionManagerService",
                                "Already registered to a proximity service.");
                        return false;
                    }
                    IAttentionService iAttentionService = AttentionManagerService.this.mService;
                    if (iAttentionService == null) {
                        Slog.w(
                                "AttentionManagerService",
                                "There is no service bound. Proximity update request rejected.");
                        return false;
                    }
                    try {
                        iAttentionService.onStartProximityUpdates(this.mIProximityUpdateCallback);
                        this.mStartedUpdates = true;
                        return true;
                    } catch (RemoteException e) {
                        Slog.e(
                                "AttentionManagerService",
                                "Cannot call into the AttentionService",
                                e);
                        return false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class ScreenStateReceiver extends BroadcastReceiver {
        public ScreenStateReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                synchronized (AttentionManagerService.this.mLock) {
                    AttentionManagerService.m254$$Nest$mcancelAndUnbindLocked(
                            AttentionManagerService.this);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mcancelAndUnbindLocked, reason: not valid java name */
    public static void m254$$Nest$mcancelAndUnbindLocked(
            AttentionManagerService attentionManagerService) {
        synchronized (attentionManagerService.mLock) {
            try {
                if (attentionManagerService.mCurrentAttentionCheck != null) {
                    attentionManagerService.cancel();
                }
                ProximityUpdate proximityUpdate = attentionManagerService.mCurrentProximityUpdate;
                if (proximityUpdate != null) {
                    proximityUpdate.cancelUpdates();
                }
                if (attentionManagerService.mService == null) {
                    return;
                }
                attentionManagerService.mAttentionHandler.post(
                        new AttentionManagerService$$ExternalSyntheticLambda2(
                                attentionManagerService, 1));
                attentionManagerService.mConnection.cleanupService();
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mhandlePendingCallbackLocked, reason: not valid java name */
    public static void m255$$Nest$mhandlePendingCallbackLocked(
            AttentionManagerService attentionManagerService) {
        AttentionCheck attentionCheck = attentionManagerService.mCurrentAttentionCheck;
        if (attentionCheck != null && !attentionCheck.mIsDispatched) {
            IAttentionService iAttentionService = attentionManagerService.mService;
            if (iAttentionService != null) {
                try {
                    iAttentionService.checkAttention(attentionCheck.mIAttentionCallback);
                    attentionManagerService.mCurrentAttentionCheck.mIsDispatched = true;
                } catch (RemoteException unused) {
                    Slog.e("AttentionManagerService", "Cannot call into the AttentionService");
                }
            } else {
                attentionCheck.mCallbackInternal.onFailure(2);
            }
        }
        ProximityUpdate proximityUpdate = attentionManagerService.mCurrentProximityUpdate;
        if (proximityUpdate == null || !proximityUpdate.mStartedUpdates) {
            return;
        }
        IAttentionService iAttentionService2 = attentionManagerService.mService;
        if (iAttentionService2 == null) {
            proximityUpdate.cancelUpdates();
            attentionManagerService.mCurrentProximityUpdate = null;
        } else {
            try {
                iAttentionService2.onStartProximityUpdates(
                        proximityUpdate.mIProximityUpdateCallback);
            } catch (RemoteException e) {
                Slog.e("AttentionManagerService", "Cannot call into the AttentionService", e);
            }
        }
    }

    public AttentionManagerService(Context context) {
        this(context, (PowerManager) context.getSystemService("power"), new Object(), null);
        this.mAttentionHandler = new AttentionHandler();
    }

    public AttentionManagerService(
            Context context,
            PowerManager powerManager,
            Object obj,
            AttentionHandler attentionHandler) {
        super(context);
        this.mConnection = new AttentionServiceConnection();
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mPowerManager = powerManager;
        this.mLock = obj;
        this.mAttentionHandler = attentionHandler;
        this.mPrivacyManager = SensorPrivacyManager.getInstance(context);
        this.mServiceBindingLatch = new CountDownLatch(1);
    }

    public static ComponentName resolveAttentionService(Context context) {
        int i;
        String str;
        ServiceInfo serviceInfo;
        String attentionServicePackageName =
                context.getPackageManager().getAttentionServicePackageName();
        if (TextUtils.isEmpty(sTestAttentionServicePackage)) {
            if (!TextUtils.isEmpty(attentionServicePackageName)) {
                i = 1048576;
                str = attentionServicePackageName;
            }
            return null;
        }
        str = sTestAttentionServicePackage;
        i = 128;
        ResolveInfo resolveService =
                context.getPackageManager()
                        .resolveService(
                                new Intent("android.service.attention.AttentionService")
                                        .setPackage(str),
                                i);
        if (resolveService == null || (serviceInfo = resolveService.serviceInfo) == null) {
            Slog.wtf(
                    "AttentionManagerService",
                    "Service android.service.attention.AttentionService not found in package "
                            + attentionServicePackageName);
            return null;
        }
        if ("android.permission.BIND_ATTENTION_SERVICE".equals(serviceInfo.permission)) {
            return serviceInfo.getComponentName();
        }
        Slog.e(
                "AttentionManagerService",
                "Service "
                        + serviceInfo.getComponentName()
                        + " should require android.permission.BIND_ATTENTION_SERVICE permission."
                        + " Found "
                        + serviceInfo.permission
                        + " permission");
        return null;
    }

    public void cancel() {
        AttentionCheck attentionCheck = this.mCurrentAttentionCheck;
        if (attentionCheck.mIsFulfilled) {
            return;
        }
        IAttentionService iAttentionService = this.mService;
        if (iAttentionService == null) {
            attentionCheck.mIsFulfilled = true;
            attentionCheck.mCallbackInternal.onFailure(3);
            return;
        }
        try {
            iAttentionService.cancelAttentionCheck(attentionCheck.mIAttentionCallback);
        } catch (RemoteException unused) {
            Slog.e("AttentionManagerService", "Unable to cancel attention check");
            AttentionCheck attentionCheck2 = this.mCurrentAttentionCheck;
            attentionCheck2.mIsFulfilled = true;
            attentionCheck2.mCallbackInternal.onFailure(3);
        }
    }

    public void cancelAttentionCheck(
            AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentAttentionCheck.mCallbackInternal.equals(
                        attentionCallbackInternal)) {
                    cancel();
                } else {
                    Slog.w("AttentionManagerService", "Cannot cancel a non-current request");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean checkAttention(
            long j, AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal) {
        Objects.requireNonNull(attentionCallbackInternal);
        if (!this.mIsServiceEnabled) {
            Slog.w(
                    "AttentionManagerService",
                    "Trying to call checkAttention() on an unsupported device.");
            return false;
        }
        if (!isServiceAvailable()) {
            Slog.w("AttentionManagerService", "Service is not available at this moment.");
            return false;
        }
        if (this.mPrivacyManager.isSensorPrivacyEnabled(2)) {
            Slog.w("AttentionManagerService", "Camera is locked by a toggle.");
            return false;
        }
        if (!this.mPowerManager.isInteractive() || this.mPowerManager.isPowerSaveMode()) {
            return false;
        }
        synchronized (this.mLock) {
            freeIfInactiveLocked();
            if (!this.mBinding && this.mService == null) {
                this.mBinding = true;
                this.mAttentionHandler.post(
                        new AttentionManagerService$$ExternalSyntheticLambda2(this, 0));
            }
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            this.mServiceBindingLatch.await(
                    Math.min(DEFAULT_STALE_AFTER_MILLIS, j), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Slog.e(
                    "AttentionManagerService",
                    "Interrupted while waiting to bind Attention Service.",
                    e);
        }
        synchronized (this.mLock) {
            try {
                AttentionCheckCacheBuffer attentionCheckCacheBuffer =
                        this.mAttentionCheckCacheBuffer;
                AttentionCheckCache attentionCheckCache = null;
                if (attentionCheckCacheBuffer != null) {
                    int i = attentionCheckCacheBuffer.mStartIndex;
                    int i2 = attentionCheckCacheBuffer.mSize;
                    int i3 = ((i + i2) - 1) % 5;
                    if (i2 != 0) {
                        attentionCheckCache = attentionCheckCacheBuffer.mQueue[i3];
                    }
                }
                if (attentionCheckCache != null
                        && uptimeMillis
                                < attentionCheckCache.mLastComputed + this.mStaleAfterMillis) {
                    attentionCallbackInternal.onSuccess(
                            attentionCheckCache.mResult, attentionCheckCache.mTimestamp);
                    return true;
                }
                AttentionCheck attentionCheck = this.mCurrentAttentionCheck;
                if (attentionCheck != null
                        && (!attentionCheck.mIsDispatched || !attentionCheck.mIsFulfilled)) {
                    return false;
                }
                this.mCurrentAttentionCheck = new AttentionCheck(attentionCallbackInternal, this);
                if (this.mService != null) {
                    try {
                        this.mAttentionHandler.sendEmptyMessageDelayed(2, j);
                        this.mService.checkAttention(
                                this.mCurrentAttentionCheck.mIAttentionCallback);
                        this.mCurrentAttentionCheck.mIsDispatched = true;
                    } catch (RemoteException unused) {
                        Slog.e("AttentionManagerService", "Cannot call into the AttentionService");
                        return false;
                    }
                }
                return true;
            } finally {
            }
        }
    }

    public void freeIfInactiveLocked() {
        AttentionHandler attentionHandler = this.mAttentionHandler;
        attentionHandler.removeMessages(1);
        attentionHandler.sendEmptyMessageDelayed(1, 60000L);
    }

    public long getStaleAfterMillis() {
        long j =
                DeviceConfig.getLong(
                        "attention_manager_service",
                        KEY_STALE_AFTER_MILLIS,
                        DEFAULT_STALE_AFTER_MILLIS);
        if (j >= 0 && j <= 10000) {
            return j;
        }
        Slog.w("AttentionManagerService", "Bad flag value supplied for: stale_after_millis");
        return DEFAULT_STALE_AFTER_MILLIS;
    }

    public boolean isServiceAvailable() {
        if (this.mComponentName == null) {
            this.mComponentName = resolveAttentionService(this.mContext);
        }
        return this.mComponentName != null;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            this.mContext.registerReceiver(
                    new ScreenStateReceiver(),
                    new IntentFilter("android.intent.action.SCREEN_OFF"));
            readValuesFromDeviceConfig();
            DeviceConfig.addOnPropertiesChangedListener(
                    "attention_manager_service",
                    ActivityThread.currentApplication().getMainExecutor(),
                    new DeviceConfig
                            .OnPropertiesChangedListener() { // from class:
                                                             // com.android.server.attention.AttentionManagerService$$ExternalSyntheticLambda0
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            AttentionManagerService attentionManagerService =
                                    AttentionManagerService.this;
                            attentionManagerService.getClass();
                            for (String str : properties.getKeyset()) {
                                str.getClass();
                                if (str.equals("stale_after_millis")
                                        || str.equals("service_enabled")) {
                                    attentionManagerService.readValuesFromDeviceConfig();
                                    return;
                                }
                                Slog.i(
                                        "AttentionManagerService",
                                        "Ignoring change on ".concat(str));
                            }
                        }
                    });
            this.mIsProximityEnabled =
                    this.mContext.getResources().getBoolean(R.bool.config_enableWallpaperService);
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "AttentionManagerService",
                    new StringBuilder("mIsProximityEnabled is: "),
                    this.mIsProximityEnabled);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("attention", new BinderService());
        publishLocalService(AttentionManagerInternal.class, new LocalService());
    }

    public boolean onStartProximityUpdates(
            AttentionManagerInternal.ProximityUpdateCallbackInternal
                    proximityUpdateCallbackInternal) {
        Objects.requireNonNull(proximityUpdateCallbackInternal);
        if (!this.mIsProximityEnabled) {
            Slog.w(
                    "AttentionManagerService",
                    "Trying to call onProximityUpdate() on an unsupported device.");
            return false;
        }
        if (!isServiceAvailable()) {
            Slog.w("AttentionManagerService", "Service is not available at this moment.");
            return false;
        }
        if (!this.mPowerManager.isInteractive()) {
            Slog.w(
                    "AttentionManagerService",
                    "Proximity Service is unavailable during screen off at this moment.");
            return false;
        }
        synchronized (this.mLock) {
            freeIfInactiveLocked();
            if (!this.mBinding && this.mService == null) {
                this.mBinding = true;
                this.mAttentionHandler.post(
                        new AttentionManagerService$$ExternalSyntheticLambda2(this, 0));
            }
        }
        try {
            this.mServiceBindingLatch.await(DEFAULT_STALE_AFTER_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Slog.e(
                    "AttentionManagerService",
                    "Interrupted while waiting to bind Attention Service.",
                    e);
        }
        synchronized (this.mLock) {
            try {
                ProximityUpdate proximityUpdate = this.mCurrentProximityUpdate;
                if (proximityUpdate == null || !proximityUpdate.mStartedUpdates) {
                    ProximityUpdate proximityUpdate2 =
                            new ProximityUpdate(proximityUpdateCallbackInternal);
                    this.mCurrentProximityUpdate = proximityUpdate2;
                    return proximityUpdate2.startUpdates();
                }
                if (proximityUpdate.mCallbackInternal == proximityUpdateCallbackInternal) {
                    Slog.w(
                            "AttentionManagerService",
                            "Provided callback is already registered. Skipping.");
                    return true;
                }
                Slog.w(
                        "AttentionManagerService",
                        "New proximity update cannot be processed because there is already an"
                            + " ongoing update");
                return false;
            } finally {
            }
        }
    }

    public void onStopProximityUpdates(
            AttentionManagerInternal.ProximityUpdateCallbackInternal
                    proximityUpdateCallbackInternal) {
        synchronized (this.mLock) {
            try {
                ProximityUpdate proximityUpdate = this.mCurrentProximityUpdate;
                if (proximityUpdate != null
                        && proximityUpdate.mCallbackInternal.equals(
                                proximityUpdateCallbackInternal)) {
                    ProximityUpdate proximityUpdate2 = this.mCurrentProximityUpdate;
                    if (proximityUpdate2.mStartedUpdates) {
                        proximityUpdate2.cancelUpdates();
                        this.mCurrentProximityUpdate = null;
                        return;
                    }
                }
                Slog.w("AttentionManagerService", "Cannot stop a non-current callback");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void readValuesFromDeviceConfig() {
        this.mIsServiceEnabled =
                DeviceConfig.getBoolean("attention_manager_service", KEY_SERVICE_ENABLED, true);
        this.mStaleAfterMillis = getStaleAfterMillis();
        Slog.i(
                "AttentionManagerService",
                "readValuesFromDeviceConfig():\nmIsServiceEnabled="
                        + this.mIsServiceEnabled
                        + "\nmStaleAfterMillis="
                        + this.mStaleAfterMillis);
    }
}
