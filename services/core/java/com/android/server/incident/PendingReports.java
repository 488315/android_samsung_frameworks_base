package com.android.server.incident;

import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.IIncidentAuthListener;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.Log;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PendingReports {
    public final AppOpsManager mAppOpsManager;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final PermissionManager mPermissionManager;
    public final RequestQueue mRequestQueue = new RequestQueue(new Handler());
    public final Object mLock = new Object();
    public final ArrayList mPending = new ArrayList();
    public int mNextPendingId = 1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingReportRec {
        public final long addedRealtime;
        public final long addedWalltime;
        public final String callingPackage;
        public final int flags;
        public final int id;
        public final IIncidentAuthListener listener;
        public final String receiverClass;
        public final String reportId;

        public PendingReportRec(
                PendingReports pendingReports,
                String str,
                String str2,
                String str3,
                int i,
                IIncidentAuthListener iIncidentAuthListener) {
            int i2 = pendingReports.mNextPendingId;
            pendingReports.mNextPendingId = i2 + 1;
            this.id = i2;
            this.callingPackage = str;
            this.flags = i;
            this.listener = iIncidentAuthListener;
            this.addedRealtime = SystemClock.elapsedRealtime();
            this.addedWalltime = System.currentTimeMillis();
            this.receiverClass = str2;
            this.reportId = str3;
        }

        public final Uri getUri() {
            Uri.Builder appendQueryParameter =
                    new Uri.Builder()
                            .scheme("content")
                            .authority("android.os.IncidentManager")
                            .path("/pending")
                            .appendQueryParameter("id", Integer.toString(this.id))
                            .appendQueryParameter("pkg", this.callingPackage)
                            .appendQueryParameter("flags", Integer.toString(this.flags))
                            .appendQueryParameter(
                                    KnoxAnalyticsDataConverter.TIMESTAMP,
                                    Long.toString(this.addedWalltime));
            String str = this.receiverClass;
            if (str != null && str.length() > 0) {
                appendQueryParameter.appendQueryParameter("receiver", str);
            }
            String str2 = this.reportId;
            if (str2 != null && str2.length() > 0) {
                appendQueryParameter.appendQueryParameter("r", str2);
            }
            return appendQueryParameter.build();
        }
    }

    public PendingReports(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mPermissionManager =
                (PermissionManager) context.getSystemService(PermissionManager.class);
    }

    public static void denyReportBeforeAddingRec(
            IIncidentAuthListener iIncidentAuthListener, String str) {
        try {
            iIncidentAuthListener.onReportDenied();
        } catch (RemoteException e) {
            Log.w("IncidentCompanionService", "Failed calling back for denial for " + str, e);
        }
    }

    public final void approveReport(String str) {
        ComponentName approverComponent;
        synchronized (this.mLock) {
            try {
                PendingReportRec findAndRemovePendingReportRecLocked =
                        findAndRemovePendingReportRecLocked(str);
                if (findAndRemovePendingReportRecLocked == null) {
                    Log.e(
                            "IncidentCompanionService",
                            "confirmApproved: Couldn't find record for uri: " + str);
                    return;
                }
                int currentUserIfAdmin = IncidentCompanionService.getCurrentUserIfAdmin();
                if (currentUserIfAdmin != -10000
                        && (approverComponent = getApproverComponent(currentUserIfAdmin)) != null) {
                    sendBroadcast(currentUserIfAdmin, approverComponent);
                }
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        "Approved report: ", str, "IncidentCompanionService");
                try {
                    findAndRemovePendingReportRecLocked.listener.onReportApproved();
                } catch (RemoteException e) {
                    Log.w(
                            "IncidentCompanionService",
                            "Failed calling back for approval for: " + str,
                            e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cancelReportImpl(
            IIncidentAuthListener iIncidentAuthListener, ComponentName componentName, int i) {
        synchronized (this.mLock) {
            removePendingReportRecLocked(iIncidentAuthListener);
        }
        sendBroadcast(i, componentName);
    }

    public final void denyReport(String str) {
        ComponentName approverComponent;
        synchronized (this.mLock) {
            try {
                PendingReportRec findAndRemovePendingReportRecLocked =
                        findAndRemovePendingReportRecLocked(str);
                if (findAndRemovePendingReportRecLocked == null) {
                    Log.e(
                            "IncidentCompanionService",
                            "confirmDenied: Couldn't find record for uri: " + str);
                    return;
                }
                int currentUserIfAdmin = IncidentCompanionService.getCurrentUserIfAdmin();
                if (currentUserIfAdmin != -10000
                        && (approverComponent = getApproverComponent(currentUserIfAdmin)) != null) {
                    sendBroadcast(currentUserIfAdmin, approverComponent);
                }
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        "Denied report: ", str, "IncidentCompanionService");
                try {
                    findAndRemovePendingReportRecLocked.listener.onReportDenied();
                } catch (RemoteException e) {
                    Log.w(
                            "IncidentCompanionService",
                            "Failed calling back for denial for: " + str,
                            e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final PendingReportRec findAndRemovePendingReportRecLocked(String str) {
        try {
            int parseInt = Integer.parseInt(Uri.parse(str).getQueryParameter("id"));
            Iterator it = this.mPending.iterator();
            while (it.hasNext()) {
                PendingReportRec pendingReportRec = (PendingReportRec) it.next();
                if (pendingReportRec.id == parseInt) {
                    it.remove();
                    return pendingReportRec;
                }
            }
            return null;
        } catch (NumberFormatException unused) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    "Can't parse id from: ", str, "IncidentCompanionService");
            return null;
        }
    }

    public final ComponentName getApproverComponent(int i) {
        List queryBroadcastReceiversAsUser =
                this.mPackageManager.queryBroadcastReceiversAsUser(
                        new Intent("android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED"),
                        1835008,
                        i);
        if (queryBroadcastReceiversAsUser.size() == 1) {
            return ((ResolveInfo) queryBroadcastReceiversAsUser.get(0))
                    .getComponentInfo()
                    .getComponentName();
        }
        Log.w(
                "IncidentCompanionService",
                "Didn't find exactly one BroadcastReceiver to handle"
                    + " android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED. The report will be"
                    + " denied. size="
                        + queryBroadcastReceiversAsUser.size()
                        + ": matches="
                        + queryBroadcastReceiversAsUser);
        return null;
    }

    public final void removePendingReportRecLocked(IIncidentAuthListener iIncidentAuthListener) {
        Iterator it = this.mPending.iterator();
        while (it.hasNext()) {
            PendingReportRec pendingReportRec = (PendingReportRec) it.next();
            if (pendingReportRec.listener.asBinder() == iIncidentAuthListener.asBinder()) {
                Log.i(
                        "IncidentCompanionService",
                        "  ...Removed PendingReportRec index="
                                + it
                                + ": "
                                + pendingReportRec.getUri());
                it.remove();
            }
        }
    }

    public final void sendBroadcast(int i, ComponentName componentName) {
        Intent intent = new Intent("android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED");
        intent.setComponent(componentName);
        intent.addFlags(268435456);
        intent.addFlags(16777216);
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setBackgroundActivityStartsAllowed(true);
        this.mContext.sendBroadcastAsUser(
                intent,
                UserHandle.of(i),
                "android.permission.APPROVE_INCIDENT_REPORTS",
                makeBasic.toBundle());
    }
}
