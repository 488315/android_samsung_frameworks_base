package com.android.server.incident;

import android.app.ActivityManager;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IIncidentAuthListener;
import android.os.IIncidentCompanion;
import android.os.IIncidentManager;
import android.os.IncidentManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.internal.util.DumpUtils;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IncidentCompanionService extends SystemService {
    public final PendingReports mPendingReports;
    public static final String[] RESTRICTED_IMAGE_DUMP_ARGS = {"--hal", "--restricted_image"};
    public static final String[] DUMP_AND_USAGE_STATS_PERMISSIONS = {
        "android.permission.DUMP", "android.permission.PACKAGE_USAGE_STATS"
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends IIncidentCompanion.Stub {
        public BinderService() {}

        public final void approveReport(String str) {
            IncidentCompanionService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.APPROVE_INCIDENT_REPORTS", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IncidentCompanionService.this.mPendingReports.approveReport(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void authorizeReport(
                final int i,
                final String str,
                final String str2,
                final String str3,
                final int i2,
                final IIncidentAuthListener iIncidentAuthListener) {
            IncidentCompanionService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.REQUEST_INCIDENT_REPORT_APPROVAL", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                final PendingReports pendingReports = IncidentCompanionService.this.mPendingReports;
                pendingReports.getClass();
                pendingReports.mRequestQueue.enqueue(
                        iIncidentAuthListener.asBinder(),
                        true,
                        new Runnable() { // from class:
                            // com.android.server.incident.PendingReports$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                PendingReports.PendingReportRec pendingReportRec;
                                final PendingReports pendingReports2 = PendingReports.this;
                                int i3 = i;
                                String str4 = str;
                                String str5 = str2;
                                String str6 = str3;
                                int i4 = i2;
                                final IIncidentAuthListener iIncidentAuthListener2 =
                                        iIncidentAuthListener;
                                pendingReports2.getClass();
                                if (i3 != 0) {
                                    try {
                                        pendingReports2.mAppOpsManager.checkPackage(i3, str4);
                                    } catch (SecurityException unused) {
                                        Log.w(
                                                "IncidentCompanionService",
                                                "Calling uid "
                                                        + i3
                                                        + " doesn't match package "
                                                        + str4);
                                        PendingReports.denyReportBeforeAddingRec(
                                                iIncidentAuthListener2, str4);
                                        return;
                                    }
                                }
                                final int currentUserIfAdmin =
                                        IncidentCompanionService.getCurrentUserIfAdmin();
                                int userId = UserHandle.getUserId(i3);
                                if (currentUserIfAdmin == -10000
                                        || !UserManager.get(pendingReports2.mContext)
                                                .isSameProfileGroup(userId, currentUserIfAdmin)) {
                                    Log.w(
                                            "IncidentCompanionService",
                                            "Calling user "
                                                    + userId
                                                    + " doesn't belong to the same profile group of"
                                                    + " the current admin user "
                                                    + currentUserIfAdmin);
                                    PendingReports.denyReportBeforeAddingRec(
                                            iIncidentAuthListener2, str4);
                                    return;
                                }
                                final ComponentName approverComponent =
                                        pendingReports2.getApproverComponent(currentUserIfAdmin);
                                if (approverComponent == null) {
                                    PendingReports.denyReportBeforeAddingRec(
                                            iIncidentAuthListener2, str4);
                                    return;
                                }
                                if ((Build.IS_USERDEBUG || Build.IS_ENG) && (i4 & 2) != 0) {
                                    if (pendingReports2.mPermissionManager
                                                    .checkPermissionForDataDelivery(
                                                            "android.permission.CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD",
                                                            new AttributionSource.Builder(i3)
                                                                    .setPackageName(str4)
                                                                    .build(),
                                                            (String) null)
                                            == 0) {
                                        try {
                                            Log.d(
                                                    "IncidentCompanionService",
                                                    "approving consentless report: "
                                                            + new PendingReports.PendingReportRec(
                                                                            pendingReports2,
                                                                            str4,
                                                                            str5,
                                                                            str6,
                                                                            i4,
                                                                            iIncidentAuthListener2)
                                                                    .getUri());
                                            iIncidentAuthListener2.onReportApproved();
                                            return;
                                        } catch (RemoteException e) {
                                            Log.e(
                                                    "IncidentCompanionService",
                                                    "authorizeReportImpl listener.onReportApproved"
                                                        + " RemoteException: ",
                                                    e);
                                        }
                                    }
                                }
                                synchronized (pendingReports2.mLock) {
                                    pendingReportRec =
                                            new PendingReports.PendingReportRec(
                                                    pendingReports2,
                                                    str4,
                                                    str5,
                                                    str6,
                                                    i4,
                                                    iIncidentAuthListener2);
                                    pendingReports2.mPending.add(pendingReportRec);
                                }
                                try {
                                    iIncidentAuthListener2
                                            .asBinder()
                                            .linkToDeath(
                                                    new IBinder.DeathRecipient() { // from class:
                                                        // com.android.server.incident.PendingReports$$ExternalSyntheticLambda2
                                                        @Override // android.os.IBinder.DeathRecipient
                                                        public final void binderDied() {
                                                            PendingReports pendingReports3 =
                                                                    PendingReports.this;
                                                            IIncidentAuthListener
                                                                    iIncidentAuthListener3 =
                                                                            iIncidentAuthListener2;
                                                            ComponentName componentName =
                                                                    approverComponent;
                                                            int i5 = currentUserIfAdmin;
                                                            pendingReports3.getClass();
                                                            Log.i(
                                                                    "IncidentCompanionService",
                                                                    "Got death notification"
                                                                        + " listener="
                                                                            + iIncidentAuthListener3);
                                                            pendingReports3.cancelReportImpl(
                                                                    iIncidentAuthListener3,
                                                                    componentName,
                                                                    i5);
                                                        }
                                                    },
                                                    0);
                                } catch (RemoteException unused2) {
                                    Log.e(
                                            "IncidentCompanionService",
                                            "Remote died while trying to register death listener: "
                                                    + pendingReportRec.getUri());
                                    pendingReports2.cancelReportImpl(
                                            iIncidentAuthListener2,
                                            approverComponent,
                                            currentUserIfAdmin);
                                }
                                pendingReports2.sendBroadcast(
                                        currentUserIfAdmin, approverComponent);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void cancelAuthorization(final IIncidentAuthListener iIncidentAuthListener) {
            IncidentCompanionService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.REQUEST_INCIDENT_REPORT_APPROVAL", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                final PendingReports pendingReports = IncidentCompanionService.this.mPendingReports;
                pendingReports.getClass();
                pendingReports.mRequestQueue.enqueue(
                        iIncidentAuthListener.asBinder(),
                        false,
                        new Runnable() { // from class:
                                         // com.android.server.incident.PendingReports$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                PendingReports pendingReports2 = PendingReports.this;
                                IIncidentAuthListener iIncidentAuthListener2 =
                                        iIncidentAuthListener;
                                pendingReports2.getClass();
                                int currentUserIfAdmin =
                                        IncidentCompanionService.getCurrentUserIfAdmin();
                                ComponentName approverComponent =
                                        pendingReports2.getApproverComponent(currentUserIfAdmin);
                                if (currentUserIfAdmin == -10000 || approverComponent == null) {
                                    return;
                                }
                                pendingReports2.cancelReportImpl(
                                        iIncidentAuthListener2,
                                        approverComponent,
                                        currentUserIfAdmin);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void deleteAllIncidentReports(String str) {
            if (str == null || str.length() == 0) {
                throw new RuntimeException("Invalid pkg");
            }
            enforceAccessReportsPermissions(str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IncidentCompanionService.this.getClass();
                IIncidentManager.Stub.asInterface(ServiceManager.getService("incident"))
                        .deleteAllIncidentReports(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void deleteIncidentReports(String str, String str2, String str3) {
            if (str == null
                    || str2 == null
                    || str3 == null
                    || str.length() == 0
                    || str2.length() == 0
                    || str3.length() == 0) {
                throw new RuntimeException("Invalid pkg, cls or id");
            }
            enforceAccessReportsPermissions(str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IncidentCompanionService.this.getClass();
                IIncidentManager.Stub.asInterface(ServiceManager.getService("incident"))
                        .deleteIncidentReports(str, str2, str3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void denyReport(String str) {
            IncidentCompanionService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.APPROVE_INCIDENT_REPORTS", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IncidentCompanionService.this.mPendingReports.denyReport(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    IncidentCompanionService.this.getContext(),
                    "IncidentCompanionService",
                    printWriter)) {
                int i = 0;
                if (strArr.length != 1 || !"--restricted_image".equals(strArr[0])) {
                    PendingReports pendingReports = IncidentCompanionService.this.mPendingReports;
                    pendingReports.getClass();
                    if (strArr.length == 0) {
                        SimpleDateFormat simpleDateFormat =
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        synchronized (pendingReports.mLock) {
                            try {
                                int size = pendingReports.mPending.size();
                                printWriter.println("mPending: (" + size + ")");
                                while (i < size) {
                                    PendingReports.PendingReportRec pendingReportRec =
                                            (PendingReports.PendingReportRec)
                                                    pendingReports.mPending.get(i);
                                    printWriter.println(
                                            String.format(
                                                    "  %11d %s: %s",
                                                    Long.valueOf(pendingReportRec.addedRealtime),
                                                    simpleDateFormat.format(
                                                            new Date(
                                                                    pendingReportRec
                                                                            .addedWalltime)),
                                                    pendingReportRec.getUri().toString()));
                                    i++;
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                }
                if (Build.IS_ENG || Build.IS_USERDEBUG) {
                    String[] stringArray =
                            IncidentCompanionService.this
                                    .getContext()
                                    .getResources()
                                    .getStringArray(17236290);
                    int length = stringArray.length;
                    while (i < length) {
                        String str = stringArray[i];
                        Log.d("IncidentCompanionService", "Looking up service " + str);
                        IBinder service = ServiceManager.getService(str);
                        if (service != null) {
                            DualAppManagerService$$ExternalSyntheticOutline0.m(
                                    "Calling dump on service: ", str, "IncidentCompanionService");
                            try {
                                service.dump(
                                        fileDescriptor,
                                        IncidentCompanionService.RESTRICTED_IMAGE_DUMP_ARGS);
                            } catch (RemoteException e) {
                                Log.w(
                                        "IncidentCompanionService",
                                        "dump --restricted_image of " + str + " threw",
                                        e);
                            }
                        }
                        i++;
                    }
                }
            }
        }

        public final void enforceAccessReportsPermissions(String str) {
            if (IncidentCompanionService.this
                            .getContext()
                            .checkCallingPermission("android.permission.APPROVE_INCIDENT_REPORTS")
                    != 0) {
                IncidentCompanionService.this
                        .getContext()
                        .enforceCallingOrSelfPermission("android.permission.DUMP", null);
                IncidentCompanionService.this
                        .getContext()
                        .enforceCallingOrSelfPermission(
                                "android.permission.PACKAGE_USAGE_STATS", null);
                if (str != null) {
                    try {
                        int callingUid = Binder.getCallingUid();
                        ApplicationInfo applicationInfoAsUser =
                                IncidentCompanionService.this
                                        .getContext()
                                        .getPackageManager()
                                        .getApplicationInfoAsUser(
                                                str, 0, UserHandle.getCallingUserId());
                        if (applicationInfoAsUser == null) {
                            throw new SecurityException("Unknown package ".concat(str));
                        }
                        if (UserHandle.isSameApp(applicationInfoAsUser.uid, callingUid)) {
                            return;
                        }
                        throw new SecurityException(
                                "Calling uid "
                                        + callingUid
                                        + " gave package "
                                        + str
                                        + " which is owned by uid "
                                        + applicationInfoAsUser.uid);
                    } catch (PackageManager.NameNotFoundException e) {
                        throw new SecurityException("Unknown package " + str + "\n" + e);
                    }
                }
            }
        }

        public final IncidentManager.IncidentReport getIncidentReport(
                String str, String str2, String str3) {
            if (str == null
                    || str2 == null
                    || str3 == null
                    || str.length() == 0
                    || str2.length() == 0
                    || str3.length() == 0) {
                throw new RuntimeException("Invalid pkg, cls or id");
            }
            enforceAccessReportsPermissions(str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IncidentCompanionService.this.getClass();
                return IIncidentManager.Stub.asInterface(ServiceManager.getService("incident"))
                        .getIncidentReport(str, str2, str3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getIncidentReportList(String str, String str2) {
            enforceAccessReportsPermissions(null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IncidentCompanionService.this.getClass();
                return IIncidentManager.Stub.asInterface(ServiceManager.getService("incident"))
                        .getIncidentReportList(str, str2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getPendingReports() {
            ArrayList arrayList;
            IncidentCompanionService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.APPROVE_INCIDENT_REPORTS", null);
            PendingReports pendingReports = IncidentCompanionService.this.mPendingReports;
            synchronized (pendingReports.mLock) {
                try {
                    int size = pendingReports.mPending.size();
                    arrayList = new ArrayList(size);
                    for (int i = 0; i < size; i++) {
                        arrayList.add(
                                ((PendingReports.PendingReportRec) pendingReports.mPending.get(i))
                                        .getUri()
                                        .toString());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        public final void sendReportReadyBroadcast(String str, String str2) {
            IncidentCompanionService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.REQUEST_INCIDENT_REPORT_APPROVAL", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Context context = IncidentCompanionService.this.getContext();
                int currentUserIfAdmin = IncidentCompanionService.getCurrentUserIfAdmin();
                if (currentUserIfAdmin == -10000) {
                    return;
                }
                Intent intent = new Intent("android.intent.action.INCIDENT_REPORT_READY");
                intent.setComponent(new ComponentName(str, str2));
                Log.d(
                        "IncidentCompanionService",
                        "sendReportReadyBroadcast sending currentUser="
                                + currentUserIfAdmin
                                + " userHandle="
                                + UserHandle.of(currentUserIfAdmin)
                                + " intent="
                                + intent);
                context.sendBroadcastAsUserMultiplePermissions(
                        intent,
                        UserHandle.of(currentUserIfAdmin),
                        IncidentCompanionService.DUMP_AND_USAGE_STATS_PERMISSIONS);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public IncidentCompanionService(Context context) {
        super(context);
        this.mPendingReports = new PendingReports(context);
    }

    public static int getCurrentUserIfAdmin() {
        try {
            UserInfo currentUser = ActivityManager.getService().getCurrentUser();
            if (currentUser == null) {
                Log.w(
                        "IncidentCompanionService",
                        "No current user.  Nobody to approve the report. The report will be"
                            + " denied.");
                return -10000;
            }
            if (currentUser.isAdmin()) {
                return currentUser.id;
            }
            Log.w(
                    "IncidentCompanionService",
                    "Only an admin user running in foreground can approve bugreports, but the"
                        + " current foreground user is not an admin user. The report will be"
                        + " denied.");
            return -10000;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i != 1000) {
            return;
        }
        RequestQueue requestQueue = this.mPendingReports.mRequestQueue;
        synchronized (requestQueue.mPending) {
            try {
                if (!requestQueue.mStarted) {
                    if (requestQueue.mPending.size() > 0) {
                        requestQueue.mHandler.post(requestQueue.mWorker);
                    }
                    requestQueue.mStarted = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("incidentcompanion", new BinderService());
    }
}
