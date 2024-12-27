package com.android.server.contentcapture;

import android.app.ActivityManagerInternal;
import android.app.assist.ActivityId;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.content.pm.ActivityPresentationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.contentcapture.ActivityEvent;
import android.service.contentcapture.ContentCaptureService;
import android.service.contentcapture.ContentCaptureServiceInfo;
import android.service.contentcapture.FlushMetrics;
import android.service.contentcapture.IContentCaptureServiceCallback;
import android.service.contentcapture.SnapshotData;
import android.service.voice.VoiceInteractionManagerInternal;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.LocalLog;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.IContentCaptureOptionsCallback;

import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.ServiceNameBaseResolver;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public final class ContentCapturePerUserService extends AbstractPerUserSystemService
        implements AbstractRemoteService.VultureCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayMap mConditionsByPkg;
    public ContentCaptureServiceInfo mInfo;
    public RemoteContentCaptureService mRemoteService;
    public final ContentCaptureServiceRemoteCallback mRemoteServiceCallback;
    public final SparseArray mSessions;
    public boolean mZombie;

    public final class ContentCaptureServiceRemoteCallback
            extends IContentCaptureServiceCallback.Stub {
        public ContentCaptureServiceRemoteCallback() {}

        public final void disableSelf() {
            if (((ContentCaptureManagerService) ContentCapturePerUserService.this.mMaster)
                    .verbose) {
                int i = ContentCapturePerUserService.$r8$clinit;
                Slog.v("ContentCapturePerUserService", "disableSelf()");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Settings.Secure.putStringForUser(
                        ContentCapturePerUserService.this.mMaster.getContext().getContentResolver(),
                        "content_capture_enabled",
                        "0",
                        ContentCapturePerUserService.this.mUserId);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                ContentCaptureMetricsLogger.writeServiceEvent(
                        4, ContentCapturePerUserService.this.getServiceComponentName());
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void setContentCaptureConditions(String str, List list) {
            String str2;
            if (((ContentCaptureManagerService) ContentCapturePerUserService.this.mMaster)
                    .verbose) {
                int i = ContentCapturePerUserService.$r8$clinit;
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "setContentCaptureConditions(", str, "): ");
                if (list == null) {
                    str2 = "null";
                } else {
                    str2 = list.size() + " conditions";
                }
                m.append(str2);
                Slog.v("ContentCapturePerUserService", m.toString());
            }
            synchronized (ContentCapturePerUserService.this.mLock) {
                try {
                    if (list == null) {
                        ContentCapturePerUserService.this.mConditionsByPkg.remove(str);
                    } else {
                        ContentCapturePerUserService.this.mConditionsByPkg.put(
                                str, new ArraySet(list));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setContentCaptureWhitelist(List list, List list2) {
            if (((ContentCaptureManagerService) ContentCapturePerUserService.this.mMaster)
                    .verbose) {
                int i = ContentCapturePerUserService.$r8$clinit;
                StringBuilder sb = new StringBuilder("setContentCaptureWhitelist(");
                sb.append(list == null ? "null_packages" : list.size() + " packages");
                sb.append(", ");
                sb.append(list2 == null ? "null_activities" : list2.size() + " activities");
                sb.append(") for user ");
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        sb,
                        ContentCapturePerUserService.this.mUserId,
                        "ContentCapturePerUserService");
            }
            ContentCapturePerUserService contentCapturePerUserService =
                    ContentCapturePerUserService.this;
            ArraySet whitelistedPackages =
                    ((ContentCaptureManagerService) contentCapturePerUserService.mMaster)
                            .mGlobalContentCaptureOptions.getWhitelistedPackages(
                                    contentCapturePerUserService.mUserId);
            EventLog.writeEvent(
                    53202,
                    Integer.valueOf(ContentCapturePerUserService.this.mUserId),
                    Integer.valueOf(CollectionUtils.size(whitelistedPackages)));
            ContentCapturePerUserService contentCapturePerUserService2 =
                    ContentCapturePerUserService.this;
            ((ContentCaptureManagerService) contentCapturePerUserService2.mMaster)
                    .mGlobalContentCaptureOptions.setWhitelist(
                            contentCapturePerUserService2.mUserId, list, list2);
            EventLog.writeEvent(
                    53201,
                    Integer.valueOf(ContentCapturePerUserService.this.mUserId),
                    Integer.valueOf(CollectionUtils.size(list)),
                    Integer.valueOf(CollectionUtils.size(list2)));
            FrameworkStatsLog.write(
                    207,
                    3,
                    ComponentName.flattenToShortString(
                            ContentCapturePerUserService.this.getServiceComponentName()),
                    (String) null,
                    list != null ? list.size() : 0,
                    list2 != null ? list2.size() : 0);
            ContentCapturePerUserService contentCapturePerUserService3 =
                    ContentCapturePerUserService.this;
            ArraySet whitelistedPackages2 =
                    ((ContentCaptureManagerService) contentCapturePerUserService3.mMaster)
                            .mGlobalContentCaptureOptions.getWhitelistedPackages(
                                    contentCapturePerUserService3.mUserId);
            EventLog.writeEvent(
                    53202,
                    Integer.valueOf(ContentCapturePerUserService.this.mUserId),
                    Integer.valueOf(CollectionUtils.size(whitelistedPackages2)));
            if (whitelistedPackages != null && whitelistedPackages2 != null) {
                whitelistedPackages2.removeAll(whitelistedPackages);
            }
            int size = CollectionUtils.size(whitelistedPackages2);
            EventLog.writeEvent(
                    53203,
                    Integer.valueOf(ContentCapturePerUserService.this.mUserId),
                    Integer.valueOf(size));
            for (int i2 = 0; i2 < size; i2++) {
                final String str = (String) whitelistedPackages2.valueAt(i2);
                ContentCapturePerUserService contentCapturePerUserService4 =
                        ContentCapturePerUserService.this;
                final ContentCaptureOptions options =
                        ((ContentCaptureManagerService) contentCapturePerUserService4.mMaster)
                                .mGlobalContentCaptureOptions.getOptions(
                                        contentCapturePerUserService4.mUserId, str);
                ((ContentCaptureManagerService) ContentCapturePerUserService.this.mMaster)
                        .mCallbacks.broadcast(
                                new BiConsumer() { // from class:
                                    // com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda4
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        String str2 = str;
                                        ContentCaptureOptions contentCaptureOptions = options;
                                        IContentCaptureOptionsCallback
                                                iContentCaptureOptionsCallback =
                                                        (IContentCaptureOptionsCallback) obj;
                                        if (obj2.equals(str2)) {
                                            try {
                                                iContentCaptureOptionsCallback
                                                        .setContentCaptureOptions(
                                                                contentCaptureOptions);
                                            } catch (RemoteException e) {
                                                AccountManagerService$$ExternalSyntheticOutline0.m(
                                                        "Unable to send setContentCaptureOptions():"
                                                            + " ",
                                                        e,
                                                        "ContentCaptureManagerService");
                                            }
                                        }
                                    }
                                });
            }
            int size2 = ContentCapturePerUserService.this.mSessions.size();
            if (size2 <= 0) {
                return;
            }
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(size2);
            for (int i3 = 0; i3 < size2; i3++) {
                ContentCaptureServerSession contentCaptureServerSession =
                        (ContentCaptureServerSession)
                                ContentCapturePerUserService.this.mSessions.valueAt(i3);
                ContentCapturePerUserService contentCapturePerUserService5 =
                        ContentCapturePerUserService.this;
                if (!((ContentCaptureManagerService) contentCapturePerUserService5.mMaster)
                        .mGlobalContentCaptureOptions.isWhitelisted(
                                contentCapturePerUserService5.mUserId,
                                contentCaptureServerSession.appComponentName)) {
                    int keyAt = ContentCapturePerUserService.this.mSessions.keyAt(i3);
                    if (((ContentCaptureManagerService) ContentCapturePerUserService.this.mMaster)
                            .debug) {
                        int i4 = ContentCapturePerUserService.$r8$clinit;
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        keyAt, "marking session ", " (");
                        m.append(contentCaptureServerSession.appComponentName);
                        m.append(") for un-whitelisting");
                        Slog.d("ContentCapturePerUserService", m.toString());
                    }
                    sparseBooleanArray.append(keyAt, true);
                }
            }
            int size3 = sparseBooleanArray.size();
            if (size3 <= 0) {
                return;
            }
            synchronized (ContentCapturePerUserService.this.mLock) {
                for (int i5 = 0; i5 < size3; i5++) {
                    try {
                        int keyAt2 = sparseBooleanArray.keyAt(i5);
                        if (((ContentCaptureManagerService)
                                        ContentCapturePerUserService.this.mMaster)
                                .debug) {
                            int i6 = ContentCapturePerUserService.$r8$clinit;
                            Slog.d("ContentCapturePerUserService", "un-whitelisting " + keyAt2);
                        }
                        ((ContentCaptureServerSession)
                                        ContentCapturePerUserService.this.mSessions.get(keyAt2))
                                .setContentCaptureEnabledLocked(false);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void writeSessionFlush(
                int i,
                ComponentName componentName,
                FlushMetrics flushMetrics,
                ContentCaptureOptions contentCaptureOptions,
                int i2) {
            FrameworkStatsLog.write(
                    209,
                    i,
                    ComponentName.flattenToShortString(
                            ContentCapturePerUserService.this.getServiceComponentName()),
                    (String) null,
                    flushMetrics.sessionStarted,
                    flushMetrics.sessionFinished,
                    flushMetrics.viewAppearedCount,
                    flushMetrics.viewDisappearedCount,
                    flushMetrics.viewTextChangedCount,
                    contentCaptureOptions.maxBufferSize,
                    contentCaptureOptions.idleFlushingFrequencyMs,
                    contentCaptureOptions.textChangeFlushingFrequencyMs,
                    i2);
        }
    }

    public ContentCapturePerUserService(
            ContentCaptureManagerService contentCaptureManagerService,
            Object obj,
            boolean z,
            int i) {
        super(contentCaptureManagerService, obj, i);
        this.mSessions = new SparseArray();
        this.mRemoteServiceCallback = new ContentCaptureServiceRemoteCallback();
        this.mConditionsByPkg = new ArrayMap();
        updateRemoteServiceLocked(z);
    }

    public final void assertCallerLocked(String str) {
        PackageManager packageManager = this.mMaster.getContext().getPackageManager();
        int callingUid = Binder.getCallingUid();
        try {
            int packageUidAsUser =
                    packageManager.getPackageUidAsUser(str, UserHandle.getCallingUserId());
            if (callingUid == packageUidAsUser
                    || ((ActivityManagerInternal)
                                    LocalServices.getService(ActivityManagerInternal.class))
                            .hasRunningActivity(callingUid, str)) {
                return;
            }
            VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity
                    hotwordDetectionServiceIdentity =
                            ((VoiceInteractionManagerInternal)
                                            LocalServices.getService(
                                                    VoiceInteractionManagerInternal.class))
                                    .getHotwordDetectionServiceIdentity();
            if (hotwordDetectionServiceIdentity != null
                    && callingUid == hotwordDetectionServiceIdentity.getIsolatedUid()
                    && packageUidAsUser == hotwordDetectionServiceIdentity.getOwnerUid()) {
                return;
            }
            String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            callingUid,
                            "App (package=",
                            packagesForUid != null
                                    ? packagesForUid[0]
                                    : VibrationParam$1$$ExternalSyntheticOutline0.m(
                                            callingUid, "uid-"),
                            ", UID=",
                            ") passed package (");
            m.append(str);
            m.append(") owned by UID ");
            m.append(packageUidAsUser);
            Slog.w("ContentCapturePerUserService", m.toString());
            throw new SecurityException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Invalid package: ", str));
        } catch (PackageManager.NameNotFoundException unused) {
            throw new SecurityException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Could not verify UID for ", str));
        }
    }

    public final void destroySessionsLocked() {
        int size = this.mSessions.size();
        for (int i = 0; i < size; i++) {
            ((ContentCaptureServerSession) this.mSessions.valueAt(i)).destroyLocked();
        }
        this.mSessions.clear();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final void dumpLocked(PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.print("    ");
        printWriter.print("Service Info: ");
        if (this.mInfo == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mInfo.dump("      ", printWriter);
        }
        printWriter.print("    ");
        printWriter.print("Zombie: ");
        printWriter.println(this.mZombie);
        if (this.mRemoteService != null) {
            printWriter.print("    ");
            printWriter.println("remote service:");
            this.mRemoteService.dump("      ", printWriter);
        }
        if (this.mSessions.size() == 0) {
            printWriter.print("    ");
            printWriter.println("no sessions");
            return;
        }
        int size = this.mSessions.size();
        printWriter.print("    ");
        printWriter.print("number sessions: ");
        printWriter.println(size);
        for (int i = 0; i < size; i++) {
            printWriter.print("    ");
            printWriter.print("#");
            printWriter.println(i);
            ContentCaptureServerSession contentCaptureServerSession =
                    (ContentCaptureServerSession) this.mSessions.valueAt(i);
            contentCaptureServerSession.getClass();
            printWriter.print("      ");
            printWriter.print("id: ");
            printWriter.print(contentCaptureServerSession.mId);
            printWriter.println();
            printWriter.print("      ");
            printWriter.print("uid: ");
            printWriter.print(contentCaptureServerSession.mUid);
            printWriter.println();
            printWriter.print("      ");
            printWriter.print("context: ");
            contentCaptureServerSession.mContentCaptureContext.dump(printWriter);
            printWriter.println();
            printWriter.print("      ");
            printWriter.print("activity token: ");
            printWriter.println(contentCaptureServerSession.mActivityToken);
            printWriter.print("      ");
            printWriter.print("app component: ");
            printWriter.println(contentCaptureServerSession.appComponentName);
            printWriter.print("      ");
            printWriter.print("has autofill callback: ");
            printWriter.println();
        }
    }

    public final void finishSessionLocked(int i) {
        if (isEnabledLocked()) {
            ContentCaptureServerSession contentCaptureServerSession =
                    (ContentCaptureServerSession) this.mSessions.get(i);
            AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
            if (contentCaptureServerSession == null) {
                if (((ContentCaptureManagerService) abstractMasterSystemService).debug) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            i,
                            "finishSession(): no session with id",
                            "ContentCapturePerUserService");
                    return;
                }
                return;
            }
            if (((ContentCaptureManagerService) abstractMasterSystemService).verbose) {
                ProxyManager$$ExternalSyntheticOutline0.m(
                        i, "finishSession(): id=", "ContentCapturePerUserService");
            }
            int i2 = contentCaptureServerSession.mId;
            ContentCapturePerUserService contentCapturePerUserService =
                    contentCaptureServerSession.mService;
            try {
                contentCaptureServerSession.destroyLocked();
            } finally {
                contentCapturePerUserService.mSessions.remove(i2);
            }
        }
    }

    public final void listSessionsLocked(ArrayList arrayList) {
        int size = this.mSessions.size();
        for (int i = 0; i < size; i++) {
            ContentCaptureServerSession contentCaptureServerSession =
                    (ContentCaptureServerSession) this.mSessions.valueAt(i);
            arrayList.add(
                    contentCaptureServerSession.mId
                            + ":"
                            + contentCaptureServerSession.mActivityToken);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        Context context = abstractMasterSystemService.getContext();
        ServiceNameBaseResolver serviceNameBaseResolver =
                abstractMasterSystemService.mServiceNameResolver;
        int i = this.mUserId;
        ContentCaptureServiceInfo contentCaptureServiceInfo =
                new ContentCaptureServiceInfo(
                        context, componentName, serviceNameBaseResolver.isTemporary(i), i);
        this.mInfo = contentCaptureServiceInfo;
        return contentCaptureServiceInfo.getServiceInfo();
    }

    public final void onActivityEventLocked(
            ActivityId activityId, ComponentName componentName, int i) {
        RemoteContentCaptureService remoteContentCaptureService = this.mRemoteService;
        String str = this.mTag;
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        if (remoteContentCaptureService == null) {
            if (((ContentCaptureManagerService) abstractMasterSystemService).debug) {
                Slog.d(str, "onActivityEvent(): no remote service");
            }
        } else if (remoteContentCaptureService.getServiceInterface() == null) {
            if (((ContentCaptureManagerService) abstractMasterSystemService).debug) {
                Slog.d(str, "onActivityEvent(): remote service is dead or unbound");
            }
        } else {
            ActivityEvent activityEvent = new ActivityEvent(activityId, componentName, i);
            if (((ContentCaptureManagerService) abstractMasterSystemService).verbose) {
                Slog.v(str, "onActivityEvent(): " + activityEvent);
            }
            this.mRemoteService.onActivityLifecycleEvent(activityEvent);
        }
    }

    public final void onConnected() {
        synchronized (this.mLock) {
            try {
                if (this.mZombie) {
                    if (this.mRemoteService == null) {
                        Slog.w(
                                "ContentCapturePerUserService",
                                "Cannot ressurect sessions because remote service is null");
                    } else {
                        this.mZombie = false;
                        resurrectSessionsLocked$1();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onServiceDied(Object obj) {
        Slog.w(
                "ContentCapturePerUserService",
                "remote service died: " + ((RemoteContentCaptureService) obj));
        synchronized (this.mLock) {
            this.mZombie = true;
            ContentCaptureMetricsLogger.writeServiceEvent(16, getServiceComponentName());
            EventLog.writeEvent(53200, Integer.valueOf(this.mUserId), 0, 0);
        }
    }

    public final void resurrectSessionsLocked$1() {
        int size = this.mSessions.size();
        if (((ContentCaptureManagerService) this.mMaster).debug) {
            Slog.d(
                    "ContentCapturePerUserService",
                    "Ressurrecting remote service ("
                            + this.mRemoteService
                            + ") on "
                            + size
                            + " sessions");
        }
        for (int i = 0; i < size; i++) {
            ContentCaptureServerSession contentCaptureServerSession =
                    (ContentCaptureServerSession) this.mSessions.valueAt(i);
            ContentCapturePerUserService contentCapturePerUserService =
                    contentCaptureServerSession.mService;
            RemoteContentCaptureService remoteContentCaptureService =
                    contentCapturePerUserService.mRemoteService;
            if (remoteContentCaptureService == null) {
                Slog.w("ContentCaptureServerSession", "destroyLocked(: no remote service");
            } else {
                if (contentCapturePerUserService.mMaster.verbose) {
                    Slog.v(
                            "ContentCaptureServerSession",
                            "resurrecting "
                                    + contentCaptureServerSession.mActivityToken
                                    + " on "
                                    + remoteContentCaptureService);
                }
                remoteContentCaptureService.onSessionStarted(
                        new ContentCaptureContext(
                                contentCaptureServerSession.mContentCaptureContext, 4),
                        contentCaptureServerSession.mId,
                        contentCaptureServerSession.mUid,
                        contentCaptureServerSession.mSessionStateReceiver,
                        4098);
            }
        }
    }

    public final boolean sendActivityAssistDataLocked(IBinder iBinder, Bundle bundle) {
        int i;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mSessions.size()) {
                i = 0;
                break;
            }
            if (((ContentCaptureServerSession) this.mSessions.valueAt(i2))
                    .mActivityToken.equals(iBinder)) {
                i = this.mSessions.keyAt(i2);
                break;
            }
            i2++;
        }
        SnapshotData snapshotData =
                new SnapshotData(
                        bundle.getBundle("data"),
                        (AssistStructure) bundle.getParcelable("structure", AssistStructure.class),
                        (AssistContent) bundle.getParcelable("content", AssistContent.class));
        if (i == 0) {
            RemoteContentCaptureService remoteContentCaptureService = this.mRemoteService;
            if (remoteContentCaptureService == null) {
                return false;
            }
            remoteContentCaptureService.onActivitySnapshotRequest(0, snapshotData);
            StringBuilder sb = new StringBuilder("Notified activity assist data for activity: ");
            sb.append(iBinder);
            BootReceiver$$ExternalSyntheticOutline0.m(
                    sb, " without a session Id", "ContentCapturePerUserService");
            return true;
        }
        ContentCaptureServerSession contentCaptureServerSession =
                (ContentCaptureServerSession) this.mSessions.get(i);
        ContentCapturePerUserService contentCapturePerUserService =
                contentCaptureServerSession.mService;
        LocalLog localLog =
                ((ContentCaptureManagerService) contentCapturePerUserService.mMaster)
                        .mRequestsHistory;
        int i3 = contentCaptureServerSession.mId;
        if (localLog != null) {
            localLog.log("snapshot: id=" + i3);
        }
        RemoteContentCaptureService remoteContentCaptureService2 =
                contentCapturePerUserService.mRemoteService;
        if (remoteContentCaptureService2 == null) {
            Slog.w(
                    "ContentCaptureServerSession",
                    "sendActivitySnapshotLocked(): no remote service");
        } else {
            remoteContentCaptureService2.onActivitySnapshotRequest(i3, snapshotData);
        }
        return true;
    }

    public final void startSessionLocked(
            IBinder iBinder,
            IBinder iBinder2,
            ActivityPresentationInfo activityPresentationInfo,
            int i,
            int i2,
            int i3,
            IResultReceiver iResultReceiver) {
        if (activityPresentationInfo == null) {
            Slog.w("ContentCapturePerUserService", "basic activity info is null");
            ContentCaptureService.setClientState(iResultReceiver, 260, (IBinder) null);
            return;
        }
        int i4 = activityPresentationInfo.taskId;
        int i5 = activityPresentationInfo.displayId;
        ComponentName componentName = activityPresentationInfo.componentName;
        ContentCaptureManagerService contentCaptureManagerService =
                (ContentCaptureManagerService) this.mMaster;
        ContentCaptureManagerService.GlobalContentCaptureOptions globalContentCaptureOptions =
                contentCaptureManagerService.mGlobalContentCaptureOptions;
        int i6 = this.mUserId;
        boolean z =
                globalContentCaptureOptions.isWhitelisted(i6, componentName)
                        || contentCaptureManagerService.mGlobalContentCaptureOptions.isWhitelisted(
                                i6, componentName.getPackageName());
        ComponentName serviceComponentName = getServiceComponentName();
        boolean isEnabledLocked = isEnabledLocked();
        if (contentCaptureManagerService.mRequestsHistory != null) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "id=", " uid=", " a=");
            m.append(ComponentName.flattenToShortString(componentName));
            m.append(" t=");
            m.append(i4);
            m.append(" d=");
            m.append(i5);
            m.append(" s=");
            m.append(ComponentName.flattenToShortString(serviceComponentName));
            m.append(" u=");
            m.append(i6);
            m.append(" f=");
            m.append(i3);
            m.append(isEnabledLocked ? "" : " (disabled)");
            m.append(" w=");
            m.append(z);
            contentCaptureManagerService.mRequestsHistory.log(m.toString());
        }
        if (!isEnabledLocked) {
            ContentCaptureService.setClientState(iResultReceiver, 20, (IBinder) null);
            ContentCaptureMetricsLogger.writeSessionEvent(i, 3, 20, serviceComponentName);
            return;
        }
        if (serviceComponentName == null) {
            if (contentCaptureManagerService.debug) {
                Slog.d(
                        "ContentCapturePerUserService",
                        "startSession(" + iBinder + "): hold your horses");
                return;
            }
            return;
        }
        if (!z) {
            if (contentCaptureManagerService.debug) {
                Slog.d(
                        "ContentCapturePerUserService",
                        "startSession("
                                + componentName
                                + "): package or component not whitelisted");
            }
            ContentCaptureService.setClientState(
                    iResultReceiver, FrameworkStatsLog.ANR_LATENCY_REPORTED, (IBinder) null);
            ContentCaptureMetricsLogger.writeSessionEvent(
                    i, 3, FrameworkStatsLog.ANR_LATENCY_REPORTED, serviceComponentName);
            return;
        }
        ContentCaptureServerSession contentCaptureServerSession =
                (ContentCaptureServerSession) this.mSessions.get(i);
        if (contentCaptureServerSession != null) {
            Slog.w(
                    "ContentCapturePerUserService",
                    "startSession(id="
                            + contentCaptureServerSession
                            + ", token="
                            + iBinder
                            + ": ignoring because it already exists for "
                            + contentCaptureServerSession.mActivityToken);
            ContentCaptureService.setClientState(iResultReceiver, 12, (IBinder) null);
            ContentCaptureMetricsLogger.writeSessionEvent(i, 3, 12, serviceComponentName);
            return;
        }
        if (this.mRemoteService == null) {
            updateRemoteServiceLocked(false);
        }
        RemoteContentCaptureService remoteContentCaptureService = this.mRemoteService;
        if (remoteContentCaptureService == null) {
            Slog.w(
                    "ContentCapturePerUserService",
                    "startSession(id="
                            + contentCaptureServerSession
                            + ", token="
                            + iBinder
                            + ": ignoring because service is not set");
            ContentCaptureService.setClientState(iResultReceiver, 20, (IBinder) null);
            ContentCaptureMetricsLogger.writeSessionEvent(i, 3, 20, serviceComponentName);
            return;
        }
        remoteContentCaptureService.ensureBoundLocked();
        ContentCaptureServerSession contentCaptureServerSession2 =
                new ContentCaptureServerSession(
                        this.mLock,
                        iBinder,
                        new ActivityId(i4, iBinder2),
                        this,
                        componentName,
                        iResultReceiver,
                        i5,
                        i,
                        i2,
                        i3);
        if (contentCaptureManagerService.verbose) {
            Slog.v(
                    "ContentCapturePerUserService",
                    "startSession(): new session for "
                            + ComponentName.flattenToShortString(componentName)
                            + " and id "
                            + i);
        }
        this.mSessions.put(i, contentCaptureServerSession2);
        RemoteContentCaptureService remoteContentCaptureService2 =
                contentCaptureServerSession2.mService.mRemoteService;
        if (remoteContentCaptureService2 == null) {
            Slog.w(
                    "ContentCaptureServerSession",
                    "notifySessionStartedLocked(): no remote service");
        } else {
            remoteContentCaptureService2.onSessionStarted(
                    contentCaptureServerSession2.mContentCaptureContext,
                    contentCaptureServerSession2.mId,
                    contentCaptureServerSession2.mUid,
                    iResultReceiver,
                    2);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        if (updateLocked) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                ((ContentCaptureServerSession) this.mSessions.valueAt(i))
                        .setContentCaptureEnabledLocked(!z);
            }
        }
        if (((ContentCaptureManagerService) this.mMaster).debug) {
            Slog.d("ContentCapturePerUserService", "destroyLocked()");
        }
        RemoteContentCaptureService remoteContentCaptureService = this.mRemoteService;
        if (remoteContentCaptureService != null) {
            remoteContentCaptureService.destroy();
        }
        destroySessionsLocked();
        updateRemoteServiceLocked(z);
        return updateLocked;
    }

    public final void updateRemoteServiceLocked(boolean z) {
        ContentCaptureManagerService contentCaptureManagerService =
                (ContentCaptureManagerService) this.mMaster;
        if (contentCaptureManagerService.verbose) {
            Slog.v("ContentCapturePerUserService", "updateRemoteService(disabled=" + z + ")");
        }
        if (this.mRemoteService != null) {
            if (contentCaptureManagerService.debug) {
                Slog.d(
                        "ContentCapturePerUserService",
                        "updateRemoteService(): destroying old remote service");
            }
            this.mRemoteService.destroy();
            this.mRemoteService = null;
            if (contentCaptureManagerService.verbose) {
                Slog.v("ContentCapturePerUserService", "resetting content capture whitelist");
            }
            contentCaptureManagerService.mGlobalContentCaptureOptions.resetWhitelist(this.mUserId);
        }
        ComponentName updateServiceInfoLocked = updateServiceInfoLocked();
        if (updateServiceInfoLocked == null) {
            if (contentCaptureManagerService.debug) {
                Slog.d(
                        "ContentCapturePerUserService",
                        "updateRemoteService(): no service component name");
            }
        } else {
            if (z) {
                return;
            }
            if (contentCaptureManagerService.debug) {
                Slog.d(
                        "ContentCapturePerUserService",
                        "updateRemoteService(): creating new remote service for "
                                + updateServiceInfoLocked);
            }
            this.mRemoteService =
                    new RemoteContentCaptureService(
                            contentCaptureManagerService.getContext(),
                            updateServiceInfoLocked,
                            this.mRemoteServiceCallback,
                            this.mUserId,
                            this,
                            contentCaptureManagerService.isBindInstantServiceAllowed(),
                            contentCaptureManagerService.verbose,
                            contentCaptureManagerService.mDevCfgIdleUnbindTimeoutMs);
        }
    }
}
