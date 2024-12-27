package com.android.server.logcat;

import android.app.ActivityManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.ILogd;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.logcat.ILogcatManagerService;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.app.ILogAccessDialogCallback;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class LogcatManagerService extends SystemService {
    static final int PENDING_CONFIRMATION_TIMEOUT_MILLIS;
    static final int STATUS_EXPIRATION_TIMEOUT_MILLIS = 60000;
    public final Map mActiveLogAccessCount;
    public ActivityManagerInternal mActivityManagerInternal;
    public final BinderService mBinderService;
    public final LogcatManagerService$Injector$$ExternalSyntheticLambda0 mClock;
    public final Context mContext;
    public final LogAccessDialogCallback mDialogCallback;
    public final LogAccessRequestHandler mHandler;
    public final Injector mInjector;
    public final Map mLogAccessStatus;
    public ILogd mLogdService;
    public final KnoxSecurityLogHandler mSecurityLogHandlerCallback;

    public final class BinderService extends ILogcatManagerService.Stub {
        public BinderService() {}

        public final void finishThread(int i, int i2, int i3, int i4) {
            Message obtainMessage =
                    LogcatManagerService.this.mHandler.obtainMessage(
                            3, new LogAccessRequest(i, i2, i3, i4));
            LogcatManagerService logcatManagerService = LogcatManagerService.this;
            logcatManagerService.mHandler.sendMessageAtTime(
                    obtainMessage, ((Long) logcatManagerService.mClock.get()).longValue());
        }

        public final void onKnoxSecurityLogEvent(int i, List list) {
            LogcatManagerService.this.mSecurityLogHandlerCallback.sendMessage(
                    LogcatManagerService.this.mHandler.obtainMessage(
                            100, new SecurityLogEvent(i, list)));
        }

        public final void startThread(int i, int i2, int i3, int i4) {
            Message obtainMessage =
                    LogcatManagerService.this.mHandler.obtainMessage(
                            0, new LogAccessRequest(i, i2, i3, i4));
            LogcatManagerService logcatManagerService = LogcatManagerService.this;
            logcatManagerService.mHandler.sendMessageAtTime(
                    obtainMessage, ((Long) logcatManagerService.mClock.get()).longValue());
        }
    }

    public final class Injector {}

    public final class KnoxSecurityLogHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 100) {
                return;
            }
            SecurityLogEvent securityLogEvent = (SecurityLogEvent) message.obj;
            AuditLog.logSecurityLogEvent(securityLogEvent.tag, securityLogEvent.payloads);
            if (SystemProperties.get("ro.build.type", "user").equals("user")
                    && SystemProperties.get("ro.product_ship", "true").equals("true")) {
                return;
            }
            Slog.d(
                    "LogcatManagerService",
                    "Knox security log event received - event: " + securityLogEvent);
        }
    }

    public final class LogAccessClient {
        public final String mPackageName;
        public final int mUid;

        public LogAccessClient(int i, String str) {
            this.mUid = i;
            this.mPackageName = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LogAccessClient)) {
                return false;
            }
            LogAccessClient logAccessClient = (LogAccessClient) obj;
            return this.mUid == logAccessClient.mUid
                    && Objects.equals(this.mPackageName, logAccessClient.mPackageName);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mUid), this.mPackageName);
        }

        public final String toString() {
            return "LogAccessClient{mUid="
                    + this.mUid
                    + ", mPackageName="
                    + this.mPackageName
                    + '}';
        }
    }

    public final class LogAccessDialogCallback extends ILogAccessDialogCallback.Stub {
        public LogAccessDialogCallback() {}

        public final void approveAccessForClient(int i, String str) {
            Message obtainMessage =
                    LogcatManagerService.this.mHandler.obtainMessage(
                            1, new LogAccessClient(i, str));
            LogcatManagerService logcatManagerService = LogcatManagerService.this;
            logcatManagerService.mHandler.sendMessageAtTime(
                    obtainMessage, ((Long) logcatManagerService.mClock.get()).longValue());
        }

        public final void declineAccessForClient(int i, String str) {
            Message obtainMessage =
                    LogcatManagerService.this.mHandler.obtainMessage(
                            2, new LogAccessClient(i, str));
            LogcatManagerService logcatManagerService = LogcatManagerService.this;
            logcatManagerService.mHandler.sendMessageAtTime(
                    obtainMessage, ((Long) logcatManagerService.mClock.get()).longValue());
        }
    }

    public final class LogAccessRequest {
        public final int mFd;
        public final int mGid;
        public final int mPid;
        public final int mUid;

        public LogAccessRequest(int i, int i2, int i3, int i4) {
            this.mUid = i;
            this.mGid = i2;
            this.mPid = i3;
            this.mFd = i4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LogAccessRequest)) {
                return false;
            }
            LogAccessRequest logAccessRequest = (LogAccessRequest) obj;
            return this.mUid == logAccessRequest.mUid
                    && this.mGid == logAccessRequest.mGid
                    && this.mPid == logAccessRequest.mPid
                    && this.mFd == logAccessRequest.mFd;
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.mUid),
                    Integer.valueOf(this.mGid),
                    Integer.valueOf(this.mPid),
                    Integer.valueOf(this.mFd));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("LogAccessRequest{mUid=");
            sb.append(this.mUid);
            sb.append(", mGid=");
            sb.append(this.mGid);
            sb.append(", mPid=");
            sb.append(this.mPid);
            sb.append(", mFd=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.mFd, '}');
        }
    }

    public final class LogAccessRequestHandler extends Handler {
        public final LogcatManagerService mService;

        public LogAccessRequestHandler(Looper looper, LogcatManagerService logcatManagerService) {
            super(looper);
            this.mService = logcatManagerService;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            PackageManager packageManager;
            int i = message.what;
            LogcatManagerService logcatManagerService = this.mService;
            if (i != 0) {
                if (i == 1) {
                    logcatManagerService.onAccessApprovedForClient((LogAccessClient) message.obj);
                    return;
                }
                if (i == 2) {
                    logcatManagerService.onAccessDeclinedForClient((LogAccessClient) message.obj);
                    return;
                }
                if (i == 3) {
                    LogAccessClient clientForRequest =
                            logcatManagerService.getClientForRequest(
                                    (LogAccessRequest) message.obj);
                    int intValue =
                            ((Integer)
                                                    logcatManagerService.mActiveLogAccessCount
                                                            .getOrDefault(clientForRequest, 1))
                                            .intValue()
                                    - 1;
                    if (intValue == 0) {
                        ((ArrayMap) logcatManagerService.mActiveLogAccessCount)
                                .remove(clientForRequest);
                        return;
                    } else {
                        ((ArrayMap) logcatManagerService.mActiveLogAccessCount)
                                .put(clientForRequest, Integer.valueOf(intValue));
                        return;
                    }
                }
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    ((ArrayMap) logcatManagerService.mLogAccessStatus)
                            .remove((LogAccessClient) message.obj);
                    return;
                } else {
                    LogAccessClient logAccessClient = (LogAccessClient) message.obj;
                    LogAccessStatus logAccessStatus =
                            (LogAccessStatus)
                                    ((ArrayMap) logcatManagerService.mLogAccessStatus)
                                            .get(logAccessClient);
                    if (logAccessStatus == null || logAccessStatus.mStatus != 1) {
                        return;
                    }
                    logcatManagerService.onAccessDeclinedForClient(logAccessClient);
                    return;
                }
            }
            LogAccessRequest logAccessRequest = (LogAccessRequest) message.obj;
            LogAccessClient clientForRequest2 =
                    logcatManagerService.getClientForRequest(logAccessRequest);
            if (clientForRequest2 == null) {
                logcatManagerService.declineRequest(logAccessRequest);
                return;
            }
            LogAccessStatus logAccessStatus2 =
                    (LogAccessStatus)
                            ((ArrayMap) logcatManagerService.mLogAccessStatus)
                                    .get(clientForRequest2);
            if (logAccessStatus2 == null) {
                logAccessStatus2 = new LogAccessStatus();
                ((ArrayMap) logcatManagerService.mLogAccessStatus)
                        .put(clientForRequest2, logAccessStatus2);
            }
            int i2 = logAccessStatus2.mStatus;
            if (i2 != 0) {
                if (i2 == 1) {
                    ((ArrayList) logAccessStatus2.mPendingRequests).add(logAccessRequest);
                    return;
                } else if (i2 == 2) {
                    logcatManagerService.approveRequest(clientForRequest2, logAccessRequest);
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    logcatManagerService.declineRequest(logAccessRequest);
                    return;
                }
            }
            ((ArrayList) logAccessStatus2.mPendingRequests).add(logAccessRequest);
            ActivityManagerInternal activityManagerInternal =
                    logcatManagerService.mActivityManagerInternal;
            int i3 = clientForRequest2.mUid;
            boolean z = false;
            boolean z2 = activityManagerInternal.getInstrumentationSourceUid(i3) != -1;
            String str = clientForRequest2.mPackageName;
            if ("com.sec.android.easyMover".equals(str)
                    && (packageManager = logcatManagerService.mContext.getPackageManager())
                            != null) {
                try {
                    if (packageManager.checkSignatures("android", "com.sec.android.easyMover")
                            == 0) {
                        z = true;
                    }
                } catch (Exception unused) {
                }
            }
            if (z2 || z) {
                logcatManagerService.onAccessApprovedForClient(clientForRequest2);
                return;
            }
            if (logcatManagerService.mActivityManagerInternal.getUidProcessState(i3) != 2) {
                logcatManagerService.onAccessDeclinedForClient(clientForRequest2);
                return;
            }
            ((LogAccessStatus)
                                    ((ArrayMap) logcatManagerService.mLogAccessStatus)
                                            .get(clientForRequest2))
                            .mStatus =
                    1;
            LogAccessRequestHandler logAccessRequestHandler = logcatManagerService.mHandler;
            logAccessRequestHandler.sendMessageAtTime(
                    logAccessRequestHandler.obtainMessage(4, clientForRequest2),
                    ((Long) logcatManagerService.mClock.get()).longValue()
                            + LogcatManagerService.PENDING_CONFIRMATION_TIMEOUT_MILLIS);
            Intent intent = new Intent();
            intent.setFlags(268468224);
            intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
            intent.putExtra("android.intent.extra.UID", i3);
            intent.putExtra("EXTRA_CALLBACK", logcatManagerService.mDialogCallback.asBinder());
            intent.setFlags(268435456);
            intent.setComponent(
                    new ComponentName(
                            Constants.SYSTEMUI_PACKAGE_NAME,
                            "com.android.systemui.logcat.LogAccessDialogActivity"));
            logcatManagerService.mContext.startActivityAsUser(intent, UserHandle.SYSTEM);
        }
    }

    public final class LogAccessStatus {
        public int mStatus = 0;
        public final List mPendingRequests = new ArrayList();
    }

    public final class SecurityLogEvent extends Record {
        public final List payloads;
        public final int tag;

        public SecurityLogEvent(int i, List list) {
            this.tag = i;
            this.payloads = list;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return (boolean)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "equals",
                                    MethodType.methodType(
                                            Boolean.TYPE, SecurityLogEvent.class, Object.class),
                                    SecurityLogEvent.class,
                                    "tag;payloads",
                                    "FIELD:Lcom/android/server/logcat/LogcatManagerService$SecurityLogEvent;->tag:I",
                                    "FIELD:Lcom/android/server/logcat/LogcatManagerService$SecurityLogEvent;->payloads:Ljava/util/List;")
                            .dynamicInvoker()
                            .invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "hashCode",
                                    MethodType.methodType(Integer.TYPE, SecurityLogEvent.class),
                                    SecurityLogEvent.class,
                                    "tag;payloads",
                                    "FIELD:Lcom/android/server/logcat/LogcatManagerService$SecurityLogEvent;->tag:I",
                                    "FIELD:Lcom/android/server/logcat/LogcatManagerService$SecurityLogEvent;->payloads:Ljava/util/List;")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final String toString() {
            return "SecurityLogEvent{tag=" + this.tag + ", payloads=" + this.payloads + "}";
        }
    }

    static {
        PENDING_CONFIRMATION_TIMEOUT_MILLIS = Build.IS_DEBUGGABLE ? 70000 : 400000;
    }

    public LogcatManagerService(Context context) {
        super(context);
        Injector injector = new Injector();
        this.mLogAccessStatus = new ArrayMap();
        this.mActiveLogAccessCount = new ArrayMap();
        this.mContext = context;
        this.mInjector = injector;
        this.mClock = new LogcatManagerService$Injector$$ExternalSyntheticLambda0();
        this.mBinderService = new BinderService();
        this.mDialogCallback = new LogAccessDialogCallback();
        this.mHandler = new LogAccessRequestHandler(Looper.getMainLooper(), this);
        this.mSecurityLogHandlerCallback = new KnoxSecurityLogHandler(Looper.getMainLooper());
    }

    public final void approveRequest(
            LogAccessClient logAccessClient, LogAccessRequest logAccessRequest) {
        try {
            try {
                getLogdService()
                        .approve(
                                logAccessRequest.mUid,
                                logAccessRequest.mGid,
                                logAccessRequest.mPid,
                                logAccessRequest.mFd);
            } catch (DeadObjectException unused) {
                Slog.w(
                        "LogcatManagerService",
                        "Logd connection no longer valid while approving, trying once more.");
                this.mLogdService = null;
                getLogdService()
                        .approve(
                                logAccessRequest.mUid,
                                logAccessRequest.mGid,
                                logAccessRequest.mPid,
                                logAccessRequest.mFd);
            }
            Integer num = (Integer) this.mActiveLogAccessCount.getOrDefault(logAccessClient, 0);
            ((ArrayMap) this.mActiveLogAccessCount)
                    .put(logAccessClient, Integer.valueOf(num.intValue() + 1));
        } catch (RemoteException e) {
            Slog.e("LogcatManagerService", "Fails to call remote functions", e);
        }
    }

    public final void declineRequest(LogAccessRequest logAccessRequest) {
        try {
            try {
                getLogdService()
                        .decline(
                                logAccessRequest.mUid,
                                logAccessRequest.mGid,
                                logAccessRequest.mPid,
                                logAccessRequest.mFd);
            } catch (DeadObjectException unused) {
                Slog.w(
                        "LogcatManagerService",
                        "Logd connection no longer valid while declining, trying once more.");
                this.mLogdService = null;
                getLogdService()
                        .decline(
                                logAccessRequest.mUid,
                                logAccessRequest.mGid,
                                logAccessRequest.mPid,
                                logAccessRequest.mFd);
            }
        } catch (RemoteException e) {
            Slog.e("LogcatManagerService", "Fails to call remote functions", e);
        }
    }

    public ILogcatManagerService getBinderService() {
        return this.mBinderService;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x004a, code lost:

       if (com.android.internal.util.ArrayUtils.contains(r0, r3) != false) goto L27;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.logcat.LogcatManagerService.LogAccessClient getClientForRequest(
            com.android.server.logcat.LogcatManagerService.LogAccessRequest r8) {
        /*
            r7 = this;
            android.content.Context r0 = r7.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            r1 = 0
            java.lang.String r2 = "LogcatManagerService"
            if (r0 != 0) goto L12
            java.lang.String r7 = "PackageManager is null, declining the logd access"
            android.util.Slog.e(r2, r7)
        L10:
            r3 = r1
            goto L5f
        L12:
            int r3 = r8.mUid
            java.lang.String[] r0 = r0.getPackagesForUid(r3)
            boolean r3 = com.android.internal.util.ArrayUtils.isEmpty(r0)
            java.lang.String r4 = "Unknown calling package name, declining the logd access"
            if (r3 == 0) goto L24
            android.util.Slog.e(r2, r4)
            goto L10
        L24:
            android.app.ActivityManagerInternal r3 = r7.mActivityManagerInternal
            if (r3 == 0) goto L4d
            int r5 = r8.mPid
            java.lang.String r3 = r3.getPackageNameByPid(r5)
        L2e:
            if (r3 == 0) goto L36
            boolean r6 = com.android.internal.util.ArrayUtils.contains(r0, r3)
            if (r6 != 0) goto L44
        L36:
            r6 = -1
            if (r5 == r6) goto L44
            int r5 = android.os.Process.getParentPid(r5)
            android.app.ActivityManagerInternal r3 = r7.mActivityManagerInternal
            java.lang.String r3 = r3.getPackageNameByPid(r5)
            goto L2e
        L44:
            if (r3 == 0) goto L4d
            boolean r7 = com.android.internal.util.ArrayUtils.contains(r0, r3)
            if (r7 == 0) goto L4d
            goto L5f
        L4d:
            java.util.Arrays.sort(r0)
            r7 = 0
            r3 = r0[r7]
            if (r3 == 0) goto L5b
            boolean r7 = r3.isEmpty()
            if (r7 == 0) goto L5f
        L5b:
            android.util.Slog.e(r2, r4)
            goto L10
        L5f:
            if (r3 != 0) goto L62
            return r1
        L62:
            com.android.server.logcat.LogcatManagerService$LogAccessClient r7 = new com.android.server.logcat.LogcatManagerService$LogAccessClient
            int r8 = r8.mUid
            r7.<init>(r8, r3)
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.logcat.LogcatManagerService.getClientForRequest(com.android.server.logcat.LogcatManagerService$LogAccessRequest):com.android.server.logcat.LogcatManagerService$LogAccessClient");
    }

    public LogAccessDialogCallback getDialogCallback() {
        return this.mDialogCallback;
    }

    public final ILogd getLogdService() {
        if (this.mLogdService == null) {
            this.mInjector.getClass();
            this.mLogdService = ILogd.Stub.asInterface(ServiceManager.getService("logd"));
        }
        return this.mLogdService;
    }

    public final void onAccessApprovedForClient(LogAccessClient logAccessClient) {
        scheduleStatusExpiry(logAccessClient);
        LogAccessStatus logAccessStatus =
                (LogAccessStatus) ((ArrayMap) this.mLogAccessStatus).get(logAccessClient);
        if (logAccessStatus != null) {
            Iterator it = ((ArrayList) logAccessStatus.mPendingRequests).iterator();
            while (it.hasNext()) {
                approveRequest(logAccessClient, (LogAccessRequest) it.next());
            }
            logAccessStatus.mStatus = 2;
            ((ArrayList) logAccessStatus.mPendingRequests).clear();
        }
    }

    public final void onAccessDeclinedForClient(LogAccessClient logAccessClient) {
        scheduleStatusExpiry(logAccessClient);
        LogAccessStatus logAccessStatus =
                (LogAccessStatus) ((ArrayMap) this.mLogAccessStatus).get(logAccessClient);
        if (logAccessStatus != null) {
            Iterator it = ((ArrayList) logAccessStatus.mPendingRequests).iterator();
            while (it.hasNext()) {
                declineRequest((LogAccessRequest) it.next());
            }
            logAccessStatus.mStatus = 3;
            ((ArrayList) logAccessStatus.mPendingRequests).clear();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        try {
            this.mActivityManagerInternal =
                    (ActivityManagerInternal)
                            LocalServices.getService(ActivityManagerInternal.class);
            publishBinderService("logcat", this.mBinderService);
        } catch (Throwable th) {
            Slog.e("LogcatManagerService", "Could not start the LogcatManagerService.", th);
        }
    }

    public final void scheduleStatusExpiry(LogAccessClient logAccessClient) {
        LogAccessRequestHandler logAccessRequestHandler = this.mHandler;
        logAccessRequestHandler.removeMessages(4, logAccessClient);
        logAccessRequestHandler.removeMessages(5, logAccessClient);
        logAccessRequestHandler.sendMessageAtTime(
                logAccessRequestHandler.obtainMessage(5, logAccessClient),
                ((Long) this.mClock.get()).longValue() + 60000);
    }
}
