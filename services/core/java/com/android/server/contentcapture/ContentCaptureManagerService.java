package com.android.server.contentcapture;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.admin.DevicePolicyCache;
import android.app.assist.ActivityId;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityPresentationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.contentcapture.ContentCaptureService;
import android.service.contentcapture.ContentCaptureServiceInfo;
import android.service.contentcapture.IDataShareCallback;
import android.service.contentcapture.IDataShareReadAdapter;
import android.service.voice.VoiceInteractionManagerInternal;
import android.util.ArraySet;
import android.util.LocalLog;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.DataRemovalRequest;
import android.view.contentcapture.DataShareRequest;
import android.view.contentcapture.IContentCaptureManager;
import android.view.contentcapture.IContentCaptureOptionsCallback;
import android.view.contentcapture.IDataShareWriteAdapter;

import com.android.internal.infra.GlobalWhitelistState;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.SyncResultReceiver;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerConstants$$ExternalSyntheticOutline0;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.contentprotection.ContentProtectionAllowlistManager;
import com.android.server.contentprotection.ContentProtectionConsentManager;
import com.android.server.contentprotection.RemoteContentProtectionService;
import com.android.server.contentprotection.RemoteContentProtectionService$$ExternalSyntheticLambda1;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.infra.ServiceNameBaseResolver;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContentCaptureManagerService extends AbstractMasterSystemService {
    public boolean activityStartAssistDataEnabled;
    public ActivityManagerInternal mAm;
    public final RemoteCallbackList mCallbacks;
    public final ContentCaptureManagerServiceStub mContentCaptureManagerServiceStub;
    public ContentProtectionAllowlistManager mContentProtectionAllowlistManager;
    public ContentProtectionConsentManager mContentProtectionConsentManager;
    public ComponentName mContentProtectionServiceComponentName;
    public final Executor mDataShareExecutor;
    public long mDevCfgContentProtectionAllowlistDelayMs;
    public long mDevCfgContentProtectionAutoDisconnectTimeoutMs;
    public int mDevCfgContentProtectionBufferSize;
    public List mDevCfgContentProtectionOptionalGroups;
    public int mDevCfgContentProtectionOptionalGroupsThreshold;
    public List mDevCfgContentProtectionRequiredGroups;
    public boolean mDevCfgDisableFlushForViewTreeAppearing;
    public boolean mDevCfgEnableContentProtectionReceiver;
    public int mDevCfgIdleFlushingFrequencyMs;
    public int mDevCfgIdleUnbindTimeoutMs;
    public int mDevCfgLogHistorySize;
    public int mDevCfgLoggingLevel;
    public int mDevCfgMaxBufferSize;
    public int mDevCfgTextChangeFlushingFrequencyMs;
    public boolean mDisabledByDeviceConfig;
    public SparseBooleanArray mDisabledBySettings;
    public final GlobalContentCaptureOptions mGlobalContentCaptureOptions;
    public final Handler mHandler;
    public final LocalService mLocalService;
    public final Set mPackagesWithShareRequests;
    public final LocalLog mRequestsHistory;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ContentCaptureManagerServiceStub extends IContentCaptureManager.Stub {
        public ContentCaptureManagerServiceStub() {}

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            boolean z = true;
            if (DumpUtils.checkDumpPermission(
                    ContentCaptureManagerService.this.getContext(),
                    "ContentCaptureManagerService",
                    printWriter)) {
                if (strArr != null) {
                    boolean z2 = true;
                    for (String str : strArr) {
                        str.getClass();
                        if (str.equals("--no-history")) {
                            z2 = false;
                        } else {
                            if (str.equals("--help")) {
                                printWriter.println(
                                        "Usage: dumpsys content_capture [--no-history]");
                                return;
                            }
                            Slog.w(
                                    "ContentCaptureManagerService",
                                    "Ignoring invalid dump arg: ".concat(str));
                        }
                    }
                    z = z2;
                }
                synchronized (ContentCaptureManagerService.this.mLock) {
                    ContentCaptureManagerService.this.dumpLocked(printWriter);
                }
                printWriter.print("Requests history: ");
                if (ContentCaptureManagerService.this.mRequestsHistory == null) {
                    printWriter.println("disabled by device config");
                } else {
                    if (!z) {
                        printWriter.println();
                        return;
                    }
                    printWriter.println();
                    ContentCaptureManagerService.this.mRequestsHistory.reverseDump(
                            fileDescriptor, printWriter, strArr);
                    printWriter.println();
                }
            }
        }

        public final void finishSession(int i) {
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (ContentCaptureManagerService.this.mLock) {
                ((ContentCapturePerUserService)
                                ContentCaptureManagerService.this.getServiceForUserLocked(
                                        callingUserId))
                        .finishSessionLocked(i);
            }
        }

        public final void getContentCaptureConditions(
                final String str, IResultReceiver iResultReceiver) {
            ArrayList list;
            if (ContentCaptureManagerService.m383$$Nest$mthrowsSecurityException(
                    ContentCaptureManagerService.this,
                    iResultReceiver,
                    new Runnable() { // from class:
                                     // com.android.server.contentcapture.ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContentCaptureManagerService.ContentCaptureManagerServiceStub
                                    contentCaptureManagerServiceStub =
                                            ContentCaptureManagerService
                                                    .ContentCaptureManagerServiceStub.this;
                            ContentCaptureManagerService.this.assertCalledByPackageOwner(str);
                        }
                    })) {
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (ContentCaptureManagerService.this.mLock) {
                ContentCapturePerUserService contentCapturePerUserService =
                        (ContentCapturePerUserService)
                                ContentCaptureManagerService.this.getServiceForUserLocked(
                                        callingUserId);
                list =
                        contentCapturePerUserService == null
                                ? null
                                : ContentCaptureHelper.toList(
                                        (ArraySet)
                                                contentCapturePerUserService.mConditionsByPkg.get(
                                                        str));
            }
            try {
                iResultReceiver.send(0, SyncResultReceiver.bundleFor(list));
            } catch (RemoteException e) {
                AccountManagerService$$ExternalSyntheticOutline0.m(
                        "Unable to send getServiceComponentName(): ",
                        e,
                        "ContentCaptureManagerService");
            }
        }

        public final void getServiceComponentName(IResultReceiver iResultReceiver) {
            ComponentName serviceComponentName;
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (ContentCaptureManagerService.this.mLock) {
                serviceComponentName =
                        ((ContentCapturePerUserService)
                                        ContentCaptureManagerService.this.getServiceForUserLocked(
                                                callingUserId))
                                .getServiceComponentName();
            }
            try {
                iResultReceiver.send(0, SyncResultReceiver.bundleFor(serviceComponentName));
            } catch (RemoteException e) {
                AccountManagerService$$ExternalSyntheticOutline0.m(
                        "Unable to send service component name: ",
                        e,
                        "ContentCaptureManagerService");
            }
        }

        public final void getServiceSettingsActivity(IResultReceiver iResultReceiver) {
            String settingsActivity;
            if (ContentCaptureManagerService.m383$$Nest$mthrowsSecurityException(
                    ContentCaptureManagerService.this,
                    iResultReceiver,
                    new ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda1(
                            this, 0))) {
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (ContentCaptureManagerService.this.mLock) {
                try {
                    ContentCapturePerUserService contentCapturePerUserService =
                            (ContentCapturePerUserService)
                                    ContentCaptureManagerService.this.getServiceForUserLocked(
                                            callingUserId);
                    if (contentCapturePerUserService == null) {
                        return;
                    }
                    ContentCaptureServiceInfo contentCaptureServiceInfo =
                            contentCapturePerUserService.mInfo;
                    ComponentName componentName = null;
                    if (contentCaptureServiceInfo != null
                            && (settingsActivity = contentCaptureServiceInfo.getSettingsActivity())
                                    != null) {
                        componentName =
                                new ComponentName(
                                        contentCapturePerUserService.mInfo.getServiceInfo()
                                                .packageName,
                                        settingsActivity);
                    }
                    try {
                        iResultReceiver.send(0, SyncResultReceiver.bundleFor(componentName));
                    } catch (RemoteException e) {
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                "Unable to send getServiceSettingsIntent(): ",
                                e,
                                "ContentCaptureManagerService");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0034  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void isContentCaptureFeatureEnabled(
                com.android.internal.os.IResultReceiver r5) {
            /*
                r4 = this;
                com.android.server.contentcapture.ContentCaptureManagerService r0 = com.android.server.contentcapture.ContentCaptureManagerService.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                com.android.server.contentcapture.ContentCaptureManagerService r1 = com.android.server.contentcapture.ContentCaptureManagerService.this     // Catch: java.lang.Throwable -> L15
                com.android.server.contentcapture.ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda1 r2 = new com.android.server.contentcapture.ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda1     // Catch: java.lang.Throwable -> L15
                r3 = 1
                r2.<init>(r4, r3)     // Catch: java.lang.Throwable -> L15
                boolean r1 = com.android.server.contentcapture.ContentCaptureManagerService.m383$$Nest$mthrowsSecurityException(r1, r5, r2)     // Catch: java.lang.Throwable -> L15
                if (r1 == 0) goto L17
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L15
                return
            L15:
                r4 = move-exception
                goto L43
            L17:
                int r1 = android.os.UserHandle.getCallingUserId()     // Catch: java.lang.Throwable -> L15
                com.android.server.contentcapture.ContentCaptureManagerService r4 = com.android.server.contentcapture.ContentCaptureManagerService.this     // Catch: java.lang.Throwable -> L15
                boolean r2 = r4.mDisabledByDeviceConfig     // Catch: java.lang.Throwable -> L15
                r3 = 1
                if (r2 != 0) goto L2f
                android.util.SparseBooleanArray r4 = r4.mDisabledBySettings     // Catch: java.lang.Throwable -> L15
                if (r4 == 0) goto L2d
                boolean r4 = r4.get(r1)     // Catch: java.lang.Throwable -> L15
                if (r4 == 0) goto L2d
                goto L2f
            L2d:
                r4 = r3
                goto L30
            L2f:
                r4 = 0
            L30:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L15
                if (r4 == 0) goto L34
                goto L35
            L34:
                r3 = 2
            L35:
                r4 = 0
                r5.send(r3, r4)     // Catch: android.os.RemoteException -> L3a
                goto L42
            L3a:
                r4 = move-exception
                java.lang.String r5 = "ContentCaptureManagerService"
                java.lang.String r0 = "Unable to send isContentCaptureFeatureEnabled(): "
                com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0.m(r0, r4, r5)
            L42:
                return
            L43:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L15
                throw r4
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.contentcapture.ContentCaptureManagerService.ContentCaptureManagerServiceStub.isContentCaptureFeatureEnabled(com.android.internal.os.IResultReceiver):void");
        }

        public final void onLoginDetected(final ParceledListSlice parceledListSlice) {
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // com.android.server.contentcapture.ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda0
                        public final void runOrThrow() {
                            ContentCaptureManagerService.ContentCaptureManagerServiceStub
                                    contentCaptureManagerServiceStub =
                                            ContentCaptureManagerService
                                                    .ContentCaptureManagerServiceStub.this;
                            ParceledListSlice parceledListSlice2 = parceledListSlice;
                            RemoteContentProtectionService createRemoteContentProtectionService =
                                    ContentCaptureManagerService.this
                                            .createRemoteContentProtectionService();
                            if (createRemoteContentProtectionService == null) {
                                return;
                            }
                            try {
                                createRemoteContentProtectionService.run(
                                        new RemoteContentProtectionService$$ExternalSyntheticLambda1(
                                                1, parceledListSlice2));
                            } catch (Exception e) {
                                Slog.e(
                                        "ContentCaptureManagerService",
                                        "Failed to call remote service",
                                        e);
                            }
                        }
                    });
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new ContentCaptureManagerServiceShellCommand(ContentCaptureManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void registerContentCaptureOptionsCallback(
                String str, IContentCaptureOptionsCallback iContentCaptureOptionsCallback) {
            ContentCaptureManagerService.this.assertCalledByPackageOwner(str);
            ContentCaptureManagerService.this.mCallbacks.register(
                    iContentCaptureOptionsCallback, str);
            ContentCaptureOptions options =
                    ContentCaptureManagerService.this.mGlobalContentCaptureOptions.getOptions(
                            UserHandle.getCallingUserId(), str);
            if (options != null) {
                try {
                    iContentCaptureOptionsCallback.setContentCaptureOptions(options);
                } catch (RemoteException e) {
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            "Unable to send setContentCaptureOptions(): ",
                            e,
                            "ContentCaptureManagerService");
                }
            }
        }

        public final void removeData(DataRemovalRequest dataRemovalRequest) {
            Objects.requireNonNull(dataRemovalRequest);
            ContentCaptureManagerService.this.assertCalledByPackageOwner(
                    dataRemovalRequest.getPackageName());
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (ContentCaptureManagerService.this.mLock) {
                ContentCapturePerUserService contentCapturePerUserService =
                        (ContentCapturePerUserService)
                                ContentCaptureManagerService.this.getServiceForUserLocked(
                                        callingUserId);
                if (contentCapturePerUserService.isEnabledLocked()) {
                    contentCapturePerUserService.assertCallerLocked(
                            dataRemovalRequest.getPackageName());
                    contentCapturePerUserService.mRemoteService.onDataRemovalRequest(
                            dataRemovalRequest);
                }
            }
        }

        public final void resetTemporaryService(int i) {
            ContentCaptureManagerService.this.resetTemporaryService(i);
        }

        public final void setDefaultServiceEnabled(int i, boolean z) {
            ContentCaptureManagerService.this.setDefaultServiceEnabled(i, z);
        }

        public final void setTemporaryService(int i, String str, int i2) {
            ContentCaptureManagerService.this.setTemporaryService(i, str, i2);
        }

        public final void shareData(
                DataShareRequest dataShareRequest, IDataShareWriteAdapter iDataShareWriteAdapter) {
            Objects.requireNonNull(dataShareRequest);
            Objects.requireNonNull(iDataShareWriteAdapter);
            ContentCaptureManagerService.this.assertCalledByPackageOwner(
                    dataShareRequest.getPackageName());
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (ContentCaptureManagerService.this.mLock) {
                ContentCapturePerUserService contentCapturePerUserService =
                        (ContentCapturePerUserService)
                                ContentCaptureManagerService.this.getServiceForUserLocked(
                                        callingUserId);
                if (((HashSet) ContentCaptureManagerService.this.mPackagesWithShareRequests).size()
                        < 10) {
                    if (!((HashSet) ContentCaptureManagerService.this.mPackagesWithShareRequests)
                            .contains(dataShareRequest.getPackageName())) {
                        DataShareCallbackDelegate dataShareCallbackDelegate =
                                new DataShareCallbackDelegate(
                                        dataShareRequest,
                                        iDataShareWriteAdapter,
                                        ContentCaptureManagerService.this);
                        if (contentCapturePerUserService.isEnabledLocked()) {
                            contentCapturePerUserService.assertCallerLocked(
                                    dataShareRequest.getPackageName());
                            contentCapturePerUserService.mRemoteService.onDataShareRequest(
                                    dataShareRequest, dataShareCallbackDelegate);
                        }
                        return;
                    }
                }
                try {
                    FrameworkStatsLog.write(
                            207,
                            14,
                            ContentCaptureManagerService.this.mServiceNameResolver.getServiceName(
                                    callingUserId),
                            (String) null,
                            0,
                            0);
                    iDataShareWriteAdapter.error(2);
                } catch (RemoteException unused) {
                    Slog.e(
                            "ContentCaptureManagerService",
                            "Failed to send error message to client");
                }
            }
        }

        public final void startSession(
                IBinder iBinder,
                IBinder iBinder2,
                ComponentName componentName,
                int i,
                int i2,
                IResultReceiver iResultReceiver) {
            Objects.requireNonNull(iBinder);
            Objects.requireNonNull(iBinder2);
            int callingUserId = UserHandle.getCallingUserId();
            ContentCaptureManagerService contentCaptureManagerService =
                    ContentCaptureManagerService.this;
            synchronized (contentCaptureManagerService.mLock) {
                try {
                    if (contentCaptureManagerService.mAm == null) {
                        contentCaptureManagerService.mAm =
                                (ActivityManagerInternal)
                                        LocalServices.getService(ActivityManagerInternal.class);
                    }
                } finally {
                }
            }
            ActivityPresentationInfo activityPresentationInfo =
                    contentCaptureManagerService.mAm.getActivityPresentationInfo(iBinder);
            synchronized (ContentCaptureManagerService.this.mLock) {
                try {
                    ContentCapturePerUserService contentCapturePerUserService =
                            (ContentCapturePerUserService)
                                    ContentCaptureManagerService.this.getServiceForUserLocked(
                                            callingUserId);
                    ServiceNameBaseResolver serviceNameBaseResolver =
                            ContentCaptureManagerService.this.mServiceNameResolver;
                    String defaultServiceName =
                            serviceNameBaseResolver.getDefaultServiceName(callingUserId);
                    if ((defaultServiceName == null
                                    ? false
                                    : defaultServiceName.equals(
                                            serviceNameBaseResolver.getServiceName(callingUserId)))
                            || ContentCaptureManagerService.this.isCalledByServiceLocked(
                                    "startSession()")) {
                        contentCapturePerUserService.startSessionLocked(
                                iBinder,
                                iBinder2,
                                activityPresentationInfo,
                                i,
                                Binder.getCallingUid(),
                                i2,
                                iResultReceiver);
                    } else {
                        ContentCaptureService.setClientState(iResultReceiver, 4, (IBinder) null);
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DataShareCallbackDelegate extends IDataShareCallback.Stub {
        public final IDataShareWriteAdapter mClientAdapter;
        public final DataShareRequest mDataShareRequest;
        public final AtomicBoolean mLoggedWriteFinish = new AtomicBoolean(false);
        public final ContentCaptureManagerService mParentService;

        public DataShareCallbackDelegate(
                DataShareRequest dataShareRequest,
                IDataShareWriteAdapter iDataShareWriteAdapter,
                ContentCaptureManagerService contentCaptureManagerService) {
            this.mDataShareRequest = dataShareRequest;
            this.mClientAdapter = iDataShareWriteAdapter;
            this.mParentService = contentCaptureManagerService;
        }

        public static void bestEffortCloseFileDescriptors(
                ParcelFileDescriptor... parcelFileDescriptorArr) {
            for (ParcelFileDescriptor parcelFileDescriptor : parcelFileDescriptorArr) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    Slog.e("ContentCaptureManagerService", "Failed to close a file descriptor", e);
                }
            }
        }

        public static Pair createPipe() {
            try {
                ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                if (createPipe.length != 2) {
                    Slog.e(
                            "ContentCaptureManagerService",
                            "Failed to create a content capture data-sharing pipe, unexpected"
                                + " number of file descriptors");
                    return null;
                }
                if (createPipe[0].getFileDescriptor().valid()
                        && createPipe[1].getFileDescriptor().valid()) {
                    return Pair.create(createPipe[0], createPipe[1]);
                }
                Slog.e(
                        "ContentCaptureManagerService",
                        "Failed to create a content capture data-sharing pipe, didn't receive a"
                            + " pair of valid file descriptors.");
                return null;
            } catch (IOException e) {
                Slog.e(
                        "ContentCaptureManagerService",
                        "Failed to create a content capture data-sharing pipe",
                        e);
                return null;
            }
        }

        public static void sendErrorSignal(
                IDataShareWriteAdapter iDataShareWriteAdapter,
                IDataShareReadAdapter iDataShareReadAdapter,
                int i) {
            try {
                iDataShareWriteAdapter.error(i);
            } catch (RemoteException e) {
                Slog.e(
                        "ContentCaptureManagerService",
                        "Failed to call error() the client operation",
                        e);
            }
            try {
                iDataShareReadAdapter.error(i);
            } catch (RemoteException e2) {
                Slog.e(
                        "ContentCaptureManagerService",
                        "Failed to call error() the service operation",
                        e2);
            }
        }

        public final void accept(final IDataShareReadAdapter iDataShareReadAdapter) {
            Slog.i(
                    "ContentCaptureManagerService",
                    "Data share request accepted by Content Capture service");
            logServiceEvent(7);
            Pair createPipe = createPipe();
            if (createPipe == null) {
                logServiceEvent(12);
                sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                return;
            }
            final ParcelFileDescriptor parcelFileDescriptor =
                    (ParcelFileDescriptor) createPipe.second;
            final ParcelFileDescriptor parcelFileDescriptor2 =
                    (ParcelFileDescriptor) createPipe.first;
            Pair createPipe2 = createPipe();
            if (createPipe2 == null) {
                logServiceEvent(13);
                bestEffortCloseFileDescriptors(parcelFileDescriptor, parcelFileDescriptor2);
                sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                return;
            }
            final ParcelFileDescriptor parcelFileDescriptor3 =
                    (ParcelFileDescriptor) createPipe2.second;
            final ParcelFileDescriptor parcelFileDescriptor4 =
                    (ParcelFileDescriptor) createPipe2.first;
            synchronized (this.mParentService.mLock) {
                ((HashSet) this.mParentService.mPackagesWithShareRequests)
                        .add(this.mDataShareRequest.getPackageName());
            }
            try {
                this.mClientAdapter.write(parcelFileDescriptor);
            } catch (RemoteException e) {
                Slog.e(
                        "ContentCaptureManagerService",
                        "Failed to call write() the client operation",
                        e);
                logServiceEvent(12);
            }
            try {
                iDataShareReadAdapter.start(parcelFileDescriptor4);
                bestEffortCloseFileDescriptors(parcelFileDescriptor, parcelFileDescriptor4);
                this.mParentService.mDataShareExecutor.execute(
                        new Runnable() { // from class:
                            // com.android.server.contentcapture.ContentCaptureManagerService$DataShareCallbackDelegate$$ExternalSyntheticLambda0
                            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:121:0x0068 -> B:34:0x00f0). Please report as a decompilation issue!!! */
                            @Override // java.lang.Runnable
                            public final void run() {
                                ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream;
                                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
                                ContentCaptureManagerService.DataShareCallbackDelegate
                                        dataShareCallbackDelegate =
                                                ContentCaptureManagerService
                                                        .DataShareCallbackDelegate.this;
                                ParcelFileDescriptor parcelFileDescriptor5 = parcelFileDescriptor2;
                                ParcelFileDescriptor parcelFileDescriptor6 = parcelFileDescriptor3;
                                IDataShareReadAdapter iDataShareReadAdapter2 =
                                        iDataShareReadAdapter;
                                dataShareCallbackDelegate.getClass();
                                boolean z = false;
                                try {
                                } catch (RemoteException e2) {
                                    Slog.e(
                                            "ContentCaptureManagerService",
                                            "Failed to call finish() the service operation",
                                            e2);
                                }
                                try {
                                    try {
                                        autoCloseInputStream =
                                                new ParcelFileDescriptor.AutoCloseInputStream(
                                                        parcelFileDescriptor5);
                                        try {
                                            autoCloseOutputStream =
                                                    new ParcelFileDescriptor.AutoCloseOutputStream(
                                                            parcelFileDescriptor6);
                                        } catch (Throwable th) {
                                            th = th;
                                            try {
                                                autoCloseInputStream.close();
                                            } catch (Throwable th2) {
                                                th.addSuppressed(th2);
                                            }
                                            throw th;
                                        }
                                    } catch (IOException e3) {
                                        e = e3;
                                    }
                                    try {
                                        byte[] bArr = new byte[1024];
                                        boolean z2 = false;
                                        while (true) {
                                            try {
                                                int read = autoCloseInputStream.read(bArr);
                                                if (read == -1) {
                                                    try {
                                                        break;
                                                    } catch (Throwable th3) {
                                                        th = th3;
                                                        z = z2;
                                                        autoCloseInputStream.close();
                                                        throw th;
                                                    }
                                                }
                                                autoCloseOutputStream.write(bArr, 0, read);
                                                z2 = true;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                z = z2;
                                                try {
                                                    autoCloseOutputStream.close();
                                                } catch (Throwable th5) {
                                                    th.addSuppressed(th5);
                                                }
                                                throw th;
                                            }
                                        }
                                        autoCloseOutputStream.close();
                                        try {
                                            autoCloseInputStream.close();
                                            synchronized (
                                                    dataShareCallbackDelegate
                                                            .mParentService
                                                            .mLock) {
                                                ((HashSet)
                                                                dataShareCallbackDelegate
                                                                        .mParentService
                                                                        .mPackagesWithShareRequests)
                                                        .remove(
                                                                dataShareCallbackDelegate
                                                                        .mDataShareRequest
                                                                        .getPackageName());
                                            }
                                        } catch (IOException e4) {
                                            e = e4;
                                            z = z2;
                                            Slog.e(
                                                    "ContentCaptureManagerService",
                                                    "Failed to pipe client and service streams",
                                                    e);
                                            dataShareCallbackDelegate.logServiceEvent(10);
                                            ContentCaptureManagerService.DataShareCallbackDelegate
                                                    .sendErrorSignal(
                                                            dataShareCallbackDelegate
                                                                    .mClientAdapter,
                                                            iDataShareReadAdapter2,
                                                            1);
                                            synchronized (
                                                    dataShareCallbackDelegate
                                                            .mParentService
                                                            .mLock) {
                                                ((HashSet)
                                                                dataShareCallbackDelegate
                                                                        .mParentService
                                                                        .mPackagesWithShareRequests)
                                                        .remove(
                                                                dataShareCallbackDelegate
                                                                        .mDataShareRequest
                                                                        .getPackageName());
                                            }
                                            if (z) {
                                                if (!dataShareCallbackDelegate.mLoggedWriteFinish
                                                        .get()) {
                                                    dataShareCallbackDelegate.logServiceEvent(9);
                                                    dataShareCallbackDelegate.mLoggedWriteFinish
                                                            .set(true);
                                                }
                                                try {
                                                    dataShareCallbackDelegate.mClientAdapter
                                                            .finish();
                                                } catch (RemoteException e5) {
                                                    Slog.e(
                                                            "ContentCaptureManagerService",
                                                            "Failed to call finish() the client"
                                                                + " operation",
                                                            e5);
                                                }
                                                iDataShareReadAdapter2.finish();
                                                return;
                                            }
                                            dataShareCallbackDelegate.logServiceEvent(11);
                                            ContentCaptureManagerService.DataShareCallbackDelegate
                                                    .sendErrorSignal(
                                                            dataShareCallbackDelegate
                                                                    .mClientAdapter,
                                                            iDataShareReadAdapter2,
                                                            1);
                                        } catch (Throwable th6) {
                                            th = th6;
                                            z = z2;
                                            synchronized (
                                                    dataShareCallbackDelegate
                                                            .mParentService
                                                            .mLock) {
                                                ((HashSet)
                                                                dataShareCallbackDelegate
                                                                        .mParentService
                                                                        .mPackagesWithShareRequests)
                                                        .remove(
                                                                dataShareCallbackDelegate
                                                                        .mDataShareRequest
                                                                        .getPackageName());
                                            }
                                            if (z) {
                                                if (!dataShareCallbackDelegate.mLoggedWriteFinish
                                                        .get()) {
                                                    dataShareCallbackDelegate.logServiceEvent(9);
                                                    dataShareCallbackDelegate.mLoggedWriteFinish
                                                            .set(true);
                                                }
                                                try {
                                                    dataShareCallbackDelegate.mClientAdapter
                                                            .finish();
                                                } catch (RemoteException e6) {
                                                    Slog.e(
                                                            "ContentCaptureManagerService",
                                                            "Failed to call finish() the client"
                                                                + " operation",
                                                            e6);
                                                }
                                                try {
                                                    iDataShareReadAdapter2.finish();
                                                } catch (RemoteException e7) {
                                                    Slog.e(
                                                            "ContentCaptureManagerService",
                                                            "Failed to call finish() the service"
                                                                + " operation",
                                                            e7);
                                                }
                                            } else {
                                                dataShareCallbackDelegate.logServiceEvent(11);
                                                ContentCaptureManagerService
                                                        .DataShareCallbackDelegate.sendErrorSignal(
                                                        dataShareCallbackDelegate.mClientAdapter,
                                                        iDataShareReadAdapter2,
                                                        1);
                                            }
                                            throw th;
                                        }
                                        if (z2) {
                                            if (!dataShareCallbackDelegate.mLoggedWriteFinish
                                                    .get()) {
                                                dataShareCallbackDelegate.logServiceEvent(9);
                                                dataShareCallbackDelegate.mLoggedWriteFinish.set(
                                                        true);
                                            }
                                            try {
                                                dataShareCallbackDelegate.mClientAdapter.finish();
                                            } catch (RemoteException e8) {
                                                Slog.e(
                                                        "ContentCaptureManagerService",
                                                        "Failed to call finish() the client"
                                                            + " operation",
                                                        e8);
                                            }
                                            iDataShareReadAdapter2.finish();
                                            return;
                                        }
                                        dataShareCallbackDelegate.logServiceEvent(11);
                                        ContentCaptureManagerService.DataShareCallbackDelegate
                                                .sendErrorSignal(
                                                        dataShareCallbackDelegate.mClientAdapter,
                                                        iDataShareReadAdapter2,
                                                        1);
                                    } catch (Throwable th7) {
                                        th = th7;
                                    }
                                } catch (Throwable th8) {
                                    th = th8;
                                }
                            }
                        });
                this.mParentService.mHandler.postDelayed(
                        new Runnable() { // from class:
                            // com.android.server.contentcapture.ContentCaptureManagerService$DataShareCallbackDelegate$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ContentCaptureManagerService.DataShareCallbackDelegate
                                        dataShareCallbackDelegate =
                                                ContentCaptureManagerService
                                                        .DataShareCallbackDelegate.this;
                                ParcelFileDescriptor parcelFileDescriptor5 = parcelFileDescriptor;
                                ParcelFileDescriptor parcelFileDescriptor6 = parcelFileDescriptor2;
                                ParcelFileDescriptor parcelFileDescriptor7 = parcelFileDescriptor3;
                                ParcelFileDescriptor parcelFileDescriptor8 = parcelFileDescriptor4;
                                IDataShareReadAdapter iDataShareReadAdapter2 =
                                        iDataShareReadAdapter;
                                synchronized (dataShareCallbackDelegate.mParentService.mLock) {
                                    try {
                                        ((HashSet)
                                                        dataShareCallbackDelegate
                                                                .mParentService
                                                                .mPackagesWithShareRequests)
                                                .remove(
                                                        dataShareCallbackDelegate.mDataShareRequest
                                                                .getPackageName());
                                        boolean z =
                                                (parcelFileDescriptor6.getFileDescriptor().valid()
                                                                || parcelFileDescriptor7
                                                                        .getFileDescriptor()
                                                                        .valid())
                                                        ? false
                                                        : true;
                                        if (z) {
                                            if (!dataShareCallbackDelegate.mLoggedWriteFinish
                                                    .get()) {
                                                dataShareCallbackDelegate.logServiceEvent(9);
                                                dataShareCallbackDelegate.mLoggedWriteFinish.set(
                                                        true);
                                            }
                                            Slog.i(
                                                    "ContentCaptureManagerService",
                                                    "Content capture data sharing session"
                                                        + " terminated successfully for package '"
                                                            + dataShareCallbackDelegate
                                                                    .mDataShareRequest
                                                                    .getPackageName()
                                                            + "'");
                                        } else {
                                            dataShareCallbackDelegate.logServiceEvent(15);
                                            Slog.i(
                                                    "ContentCaptureManagerService",
                                                    "Reached the timeout of Content Capture data"
                                                        + " sharing session for package '"
                                                            + dataShareCallbackDelegate
                                                                    .mDataShareRequest
                                                                    .getPackageName()
                                                            + "', terminating the pipe.");
                                        }
                                        ContentCaptureManagerService.DataShareCallbackDelegate
                                                .bestEffortCloseFileDescriptors(
                                                        parcelFileDescriptor5,
                                                        parcelFileDescriptor6,
                                                        parcelFileDescriptor7,
                                                        parcelFileDescriptor8);
                                        if (!z) {
                                            ContentCaptureManagerService.DataShareCallbackDelegate
                                                    .sendErrorSignal(
                                                            dataShareCallbackDelegate
                                                                    .mClientAdapter,
                                                            iDataShareReadAdapter2,
                                                            3);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        },
                        300000L);
            } catch (RemoteException e2) {
                Slog.e(
                        "ContentCaptureManagerService",
                        "Failed to call start() the service operation",
                        e2);
                logServiceEvent(13);
                sendErrorSignal(this.mClientAdapter, iDataShareReadAdapter, 1);
                bestEffortCloseFileDescriptors(
                        parcelFileDescriptor,
                        parcelFileDescriptor2,
                        parcelFileDescriptor3,
                        parcelFileDescriptor4);
                synchronized (this.mParentService.mLock) {
                    ((HashSet) this.mParentService.mPackagesWithShareRequests)
                            .remove(this.mDataShareRequest.getPackageName());
                }
            }
        }

        public final void logServiceEvent(int i) {
            FrameworkStatsLog.write(
                    207,
                    i,
                    this.mParentService.mServiceNameResolver.getServiceName(
                            UserHandle.getCallingUserId()),
                    (String) null,
                    0,
                    0);
        }

        public final void reject() {
            Slog.i(
                    "ContentCaptureManagerService",
                    "Data share request rejected by Content Capture service");
            logServiceEvent(8);
            try {
                this.mClientAdapter.rejected();
            } catch (RemoteException e) {
                Slog.w(
                        "ContentCaptureManagerService",
                        "Failed to call rejected() the client operation",
                        e);
                try {
                    this.mClientAdapter.error(1);
                } catch (RemoteException e2) {
                    Slog.w(
                            "ContentCaptureManagerService",
                            "Failed to call error() the client operation",
                            e2);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GlobalContentCaptureOptions extends GlobalWhitelistState {
        public final SparseArray mServicePackages = new SparseArray();
        public final SparseBooleanArray mTemporaryServices = new SparseBooleanArray();

        /* renamed from: -$$Nest$msetServiceInfo, reason: not valid java name */
        public static void m384$$Nest$msetServiceInfo(
                GlobalContentCaptureOptions globalContentCaptureOptions,
                int i,
                String str,
                boolean z) {
            synchronized (
                    ((GlobalWhitelistState) globalContentCaptureOptions)
                            .mGlobalWhitelistStateLock) {
                try {
                    if (z) {
                        globalContentCaptureOptions.mTemporaryServices.put(i, true);
                    } else {
                        globalContentCaptureOptions.mTemporaryServices.delete(i);
                    }
                    if (str != null) {
                        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                        if (unflattenFromString == null) {
                            Slog.w(
                                    "ContentCaptureManagerService",
                                    "setServiceInfo(): invalid name: ".concat(str));
                            globalContentCaptureOptions.mServicePackages.remove(i);
                        } else {
                            globalContentCaptureOptions.mServicePackages.put(
                                    i, unflattenFromString.getPackageName());
                        }
                    } else {
                        globalContentCaptureOptions.mServicePackages.remove(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public GlobalContentCaptureOptions() {}

        public final void dump(String str, PrintWriter printWriter) {
            super.dump(str, printWriter);
            synchronized (((GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    if (this.mServicePackages.size() > 0) {
                        printWriter.print(str);
                        printWriter.print("Service packages: ");
                        printWriter.println(this.mServicePackages);
                    }
                    if (this.mTemporaryServices.size() > 0) {
                        printWriter.print(str);
                        printWriter.print("Temp services: ");
                        printWriter.println(this.mTemporaryServices);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:46:0x00f7 A[Catch: all -> 0x0111, TryCatch #0 {all -> 0x0111, blocks: (B:38:0x00ab, B:44:0x00c6, B:46:0x00f7, B:47:0x0113), top: B:37:0x00ab }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.content.ContentCaptureOptions getOptions(
                int r22, java.lang.String r23) {
            /*
                Method dump skipped, instructions count: 281
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.contentcapture.ContentCaptureManagerService.GlobalContentCaptureOptions.getOptions(int,"
                        + " java.lang.String):android.content.ContentCaptureOptions");
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0044, code lost:

           if (r1.mDevicePolicyManagerInternal.isUserOrganizationManaged(r5) == false) goto L26;
        */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0046, code lost:

           r5 = true;
        */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0062, code lost:

           if (r5 != 2) goto L27;
        */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0082 A[ORIG_RETURN, RETURN] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isContentProtectionReceiverEnabled(int r5, java.lang.String r6) {
            /*
                r4 = this;
                com.android.server.contentcapture.ContentCaptureManagerService r0 = com.android.server.contentcapture.ContentCaptureManagerService.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                com.android.server.contentcapture.ContentCaptureManagerService r1 = com.android.server.contentcapture.ContentCaptureManagerService.this     // Catch: java.lang.Throwable -> L83
                boolean r2 = r1.mDevCfgEnableContentProtectionReceiver     // Catch: java.lang.Throwable -> L83
                r3 = 0
                if (r2 == 0) goto L85
                android.content.ComponentName r2 = r1.mContentProtectionServiceComponentName     // Catch: java.lang.Throwable -> L83
                if (r2 == 0) goto L85
                com.android.server.contentprotection.ContentProtectionAllowlistManager r2 = r1.mContentProtectionAllowlistManager     // Catch: java.lang.Throwable -> L83
                if (r2 == 0) goto L85
                com.android.server.contentprotection.ContentProtectionConsentManager r2 = r1.mContentProtectionConsentManager     // Catch: java.lang.Throwable -> L83
                if (r2 == 0) goto L85
                java.util.List r2 = r1.mDevCfgContentProtectionRequiredGroups     // Catch: java.lang.Throwable -> L83
                boolean r2 = r2.isEmpty()     // Catch: java.lang.Throwable -> L83
                if (r2 == 0) goto L28
                java.util.List r1 = r1.mDevCfgContentProtectionOptionalGroups     // Catch: java.lang.Throwable -> L83
                boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> L83
                if (r1 != 0) goto L85
            L28:
                com.android.server.contentcapture.ContentCaptureManagerService r4 = com.android.server.contentcapture.ContentCaptureManagerService.this     // Catch: java.lang.Throwable -> L83
                com.android.server.contentprotection.ContentProtectionConsentManager r1 = r4.mContentProtectionConsentManager     // Catch: java.lang.Throwable -> L83
                com.android.server.contentprotection.ContentProtectionAllowlistManager r4 = r4.mContentProtectionAllowlistManager     // Catch: java.lang.Throwable -> L83
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L83
                boolean r0 = r1.mCachedPackageVerifierConsent
                r2 = 1
                if (r0 == 0) goto L6e
                boolean r0 = com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.Flags.manageDevicePolicyEnabled()
                if (r0 != 0) goto L4a
                boolean r0 = r1.mCachedContentProtectionUserConsent
                if (r0 == 0) goto L48
                android.app.admin.DevicePolicyManagerInternal r0 = r1.mDevicePolicyManagerInternal
                boolean r5 = r0.isUserOrganizationManaged(r5)
                if (r5 != 0) goto L48
            L46:
                r5 = r2
                goto L6a
            L48:
                r5 = r3
                goto L6a
            L4a:
                android.app.admin.DevicePolicyManagerInternal r0 = r1.mDevicePolicyManagerInternal
                boolean r0 = r0.isUserOrganizationManaged(r5)
                if (r0 == 0) goto L68
                boolean r0 = com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.Flags.manageDevicePolicyEnabled()
                if (r0 != 0) goto L59
                goto L48
            L59:
                android.app.admin.DevicePolicyCache r0 = r1.mDevicePolicyCache
                int r5 = r0.getContentProtectionPolicy(r5)
                if (r5 == 0) goto L65
                r0 = 2
                if (r5 == r0) goto L46
                goto L48
            L65:
                boolean r5 = r1.mCachedContentProtectionUserConsent
                goto L6a
            L68:
                boolean r5 = r1.mCachedContentProtectionUserConsent
            L6a:
                if (r5 == 0) goto L6e
                r5 = r2
                goto L6f
            L6e:
                r5 = r3
            L6f:
                if (r5 == 0) goto L82
                java.lang.Object r5 = r4.mLock
                monitor-enter(r5)
                java.util.Set r4 = r4.mAllowedPackages     // Catch: java.lang.Throwable -> L7f
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L7f
                boolean r4 = r4.contains(r6)
                if (r4 == 0) goto L82
                r3 = r2
                goto L82
            L7f:
                r4 = move-exception
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L7f
                throw r4
            L82:
                return r3
            L83:
                r4 = move-exception
                goto L87
            L85:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L83
                return r3
            L87:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L83
                throw r4
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.contentcapture.ContentCaptureManagerService.GlobalContentCaptureOptions.isContentProtectionReceiverEnabled(int,"
                        + " java.lang.String):boolean");
        }

        public final boolean isWhitelisted(int i, ComponentName componentName) {
            return super.isWhitelisted(i, componentName)
                    || isContentProtectionReceiverEnabled(i, componentName.getPackageName());
        }

        public final boolean isWhitelisted(int i, String str) {
            return super.isWhitelisted(i, str) || isContentProtectionReceiverEnabled(i, str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {}

        public final boolean isContentCaptureServiceForUser(int i, int i2) {
            synchronized (ContentCaptureManagerService.this.mLock) {
                try {
                    if (ContentCaptureManagerService.this.isDisabledLocked(i2)) {
                        if (ContentCaptureManagerService.this.verbose) {
                            Slog.v(
                                    "ContentCaptureManagerService",
                                    "isContentCaptureServiceForUser() disabled!");
                        }
                        return false;
                    }
                    ContentCapturePerUserService contentCapturePerUserService =
                            (ContentCapturePerUserService)
                                    ContentCaptureManagerService.this.getServiceForUserLocked(i2);
                    if (contentCapturePerUserService != null) {
                        return i == contentCapturePerUserService.getServiceUidLocked();
                    }
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void notifyActivityEvent(
                int i, ComponentName componentName, int i2, ActivityId activityId) {
            synchronized (ContentCaptureManagerService.this.mLock) {
                try {
                    if (ContentCaptureManagerService.this.isDisabledLocked(i)) {
                        if (ContentCaptureManagerService.this.verbose) {
                            Slog.v(
                                    "ContentCaptureManagerService",
                                    "notifyActivityEvent() disabled!");
                        }
                    } else {
                        ContentCapturePerUserService contentCapturePerUserService =
                                (ContentCapturePerUserService)
                                        ContentCaptureManagerService.this.getServiceForUserLocked(
                                                i);
                        if (contentCapturePerUserService != null) {
                            contentCapturePerUserService.onActivityEventLocked(
                                    activityId, componentName, i2);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mthrowsSecurityException, reason: not valid java name */
    public static boolean m383$$Nest$mthrowsSecurityException(
            ContentCaptureManagerService contentCaptureManagerService,
            IResultReceiver iResultReceiver,
            Runnable runnable) {
        contentCaptureManagerService.getClass();
        try {
            runnable.run();
            return false;
        } catch (SecurityException e) {
            try {
                iResultReceiver.send(-1, SyncResultReceiver.bundleFor(e.getMessage()));
            } catch (RemoteException e2) {
                Slog.w(
                        "ContentCaptureManagerService",
                        "Unable to send security exception (" + e + "): ",
                        e2);
            }
            return true;
        }
    }

    public ContentCaptureManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(
                        context, R.string.date_picker_increment_day_button),
                "no_content_capture",
                1);
        this.mLocalService = new LocalService();
        this.mContentCaptureManagerServiceStub = new ContentCaptureManagerServiceStub();
        this.mDataShareExecutor = Executors.newCachedThreadPool();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mPackagesWithShareRequests = new HashSet();
        this.mCallbacks = new RemoteCallbackList();
        this.mGlobalContentCaptureOptions = new GlobalContentCaptureOptions();
        this.mDevCfgContentProtectionRequiredGroups =
                ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS;
        this.mDevCfgContentProtectionOptionalGroups =
                ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS;
        DeviceConfig.addOnPropertiesChangedListener(
                "content_capture",
                ActivityThread.currentApplication().getMainExecutor(),
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda0
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        ContentCaptureManagerService contentCaptureManagerService =
                                ContentCaptureManagerService.this;
                        contentCaptureManagerService.getClass();
                        for (String str : properties.getKeyset()) {
                            str.getClass();
                            switch (str) {
                                case "disable_flush_for_view_tree_appearing":
                                case "enable_content_protection_receiver":
                                case "content_protection_optional_groups_config":
                                case "content_protection_allowlist_delay_ms":
                                case "content_protection_buffer_size":
                                case "text_change_flush_frequency":
                                case "content_protection_required_groups_config":
                                case "log_history_size":
                                case "content_protection_optional_groups_threshold":
                                case "max_buffer_size":
                                case "content_protection_allowlist_timeout_ms":
                                case "content_protection_auto_disconnect_timeout_ms":
                                case "idle_unbind_timeout":
                                case "idle_flush_frequency":
                                    contentCaptureManagerService
                                            .setFineTuneParamsFromDeviceConfig();
                                    return;
                                case "logging_level":
                                    contentCaptureManagerService.setLoggingLevelFromDeviceConfig();
                                    return;
                                case "service_explicitly_enabled":
                                    contentCaptureManagerService.setDisabledByDeviceConfig(
                                            properties.getString(str, (String) null));
                                    return;
                                case "enable_activity_start_assist_content":
                                    contentCaptureManagerService
                                            .setActivityStartAssistDataEnabled();
                                    break;
                            }
                            Slog.i(
                                    "ContentCaptureManagerService",
                                    "Ignoring change on ".concat(str));
                        }
                    }
                });
        setLoggingLevelFromDeviceConfig();
        setFineTuneParamsFromDeviceConfig();
        setDisabledByDeviceConfig(
                DeviceConfig.getProperty("content_capture", "service_explicitly_enabled"));
        setActivityStartAssistDataEnabled();
        if (this.mDevCfgLogHistorySize > 0) {
            if (this.debug) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("log history size: "),
                        this.mDevCfgLogHistorySize,
                        "ContentCaptureManagerService");
            }
            this.mRequestsHistory = new LocalLog(this.mDevCfgLogHistorySize);
        } else {
            if (this.debug) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("disabled log history because size is "),
                        this.mDevCfgLogHistorySize,
                        "ContentCaptureManagerService");
            }
            this.mRequestsHistory = null;
        }
        List supportedUsers = getSupportedUsers();
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) supportedUsers;
            if (i >= arrayList.size()) {
                return;
            }
            int i2 = ((UserInfo) arrayList.get(i)).id;
            if (!(Settings.Secure.getIntForUser(
                            getContext().getContentResolver(), "content_capture_enabled", 1, i2)
                    == 1)) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        i2, "user ", " disabled by settings", "ContentCaptureManagerService");
                if (this.mDisabledBySettings == null) {
                    this.mDisabledBySettings = new SparseBooleanArray(1);
                }
                this.mDisabledBySettings.put(i2, true);
            }
            GlobalContentCaptureOptions.m384$$Nest$msetServiceInfo(
                    this.mGlobalContentCaptureOptions,
                    i2,
                    this.mServiceNameResolver.getServiceName(i2),
                    this.mServiceNameResolver.isTemporary(i2));
            i++;
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void assertCalledByPackageOwner(String str) {
        try {
            super.assertCalledByPackageOwner(str);
        } catch (SecurityException e) {
            int callingUid = Binder.getCallingUid();
            VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity
                    hotwordDetectionServiceIdentity =
                            ((VoiceInteractionManagerInternal)
                                            LocalServices.getService(
                                                    VoiceInteractionManagerInternal.class))
                                    .getHotwordDetectionServiceIdentity();
            if (callingUid != hotwordDetectionServiceIdentity.getIsolatedUid()) {
                super.assertCalledByPackageOwner(str);
                return;
            }
            String[] packagesForUid =
                    getContext()
                            .getPackageManager()
                            .getPackagesForUid(hotwordDetectionServiceIdentity.getOwnerUid());
            if (packagesForUid != null) {
                for (String str2 : packagesForUid) {
                    if (str.equals(str2)) {
                        return;
                    }
                }
            }
            throw e;
        }
    }

    public ContentProtectionAllowlistManager createContentProtectionAllowlistManager(long j) {
        return new ContentProtectionAllowlistManager(this, BackgroundThread.getHandler(), j);
    }

    public ContentProtectionConsentManager createContentProtectionConsentManager() {
        return new ContentProtectionConsentManager(
                BackgroundThread.getHandler(),
                getContext().getContentResolver(),
                DevicePolicyCache.getInstance());
    }

    public ContentCaptureServiceInfo createContentProtectionServiceInfo(ComponentName componentName)
            throws PackageManager.NameNotFoundException {
        return new ContentCaptureServiceInfo(
                getContext(), componentName, false, UserHandle.getCallingUserId());
    }

    public final RemoteContentProtectionService createRemoteContentProtectionService() {
        ComponentName componentName;
        synchronized (this.mLock) {
            if (this.mDevCfgEnableContentProtectionReceiver
                    && (componentName = this.mContentProtectionServiceComponentName) != null) {
                long j = this.mDevCfgContentProtectionAutoDisconnectTimeoutMs;
                try {
                    createContentProtectionServiceInfo(componentName);
                    return createRemoteContentProtectionService(componentName, j);
                } catch (Exception unused) {
                    return null;
                }
            }
            return null;
        }
    }

    public RemoteContentProtectionService createRemoteContentProtectionService(
            ComponentName componentName, long j) {
        return new RemoteContentProtectionService(
                getContext(),
                componentName,
                UserHandle.getCallingUserId(),
                isBindInstantServiceAllowed(),
                j);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void dumpLocked(PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.print("");
        printWriter.print("Users disabled by Settings: ");
        printWriter.println(this.mDisabledBySettings);
        printWriter.print("");
        printWriter.println("DeviceConfig Settings: ");
        printWriter.print("  ");
        printWriter.print("disabled: ");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, "  ", "loggingLevel: ", this.mDisabledByDeviceConfig);
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDevCfgLoggingLevel, printWriter, "  ", "maxBufferSize: ");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDevCfgMaxBufferSize, printWriter, "  ", "idleFlushingFrequencyMs: ");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDevCfgIdleFlushingFrequencyMs,
                printWriter,
                "  ",
                "textChangeFlushingFrequencyMs: ");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDevCfgTextChangeFlushingFrequencyMs, printWriter, "  ", "logHistorySize: ");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDevCfgLogHistorySize, printWriter, "  ", "idleUnbindTimeoutMs: ");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDevCfgIdleUnbindTimeoutMs,
                printWriter,
                "  ",
                "disableFlushForViewTreeAppearing: ");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                "  ",
                "enableContentProtectionReceiver: ",
                this.mDevCfgDisableFlushForViewTreeAppearing);
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                "  ",
                "contentProtectionBufferSize: ",
                this.mDevCfgEnableContentProtectionReceiver);
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDevCfgContentProtectionBufferSize,
                printWriter,
                "  ",
                "contentProtectionRequiredGroupsSize: ");
        printWriter.println(this.mDevCfgContentProtectionRequiredGroups.size());
        printWriter.print("  ");
        printWriter.print("contentProtectionOptionalGroupsSize: ");
        printWriter.println(this.mDevCfgContentProtectionOptionalGroups.size());
        printWriter.print("  ");
        printWriter.print("contentProtectionOptionalGroupsThreshold: ");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDevCfgContentProtectionOptionalGroupsThreshold,
                printWriter,
                "  ",
                "contentProtectionAllowlistDelayMs: ");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mDevCfgContentProtectionAllowlistDelayMs,
                printWriter,
                "  ",
                "contentProtectionAllowlistTimeoutMs: ");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                0L, printWriter, "  ", "contentProtectionAutoDisconnectTimeoutMs: ");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mDevCfgContentProtectionAutoDisconnectTimeoutMs,
                printWriter,
                "  ",
                "activityStartAssistDataEnabled: ");
        printWriter.println(this.activityStartAssistDataEnabled);
        printWriter.print("");
        printWriter.println("Global Options:");
        this.mGlobalContentCaptureOptions.dump("  ", printWriter);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.MANAGE_CONTENT_CAPTURE",
                        "ContentCaptureManagerService");
    }

    public ContentCaptureManagerServiceStub getContentCaptureManagerServiceStub() {
        return this.mContentCaptureManagerServiceStub;
    }

    public String getContentProtectionServiceFlatComponentName() {
        return getContext().getString(R.string.date_picker_increment_month_button);
    }

    public boolean getDeviceConfigEnableContentProtectionReceiver() {
        return DeviceConfig.getBoolean(
                "content_capture", "enable_content_protection_receiver", false);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 120000;
    }

    public final boolean isCalledByServiceLocked(String str) {
        int callingUserId = UserHandle.getCallingUserId();
        int callingUid = Binder.getCallingUid();
        String serviceName = this.mServiceNameResolver.getServiceName(callingUserId);
        if (serviceName == null) {
            Slog.e(
                    "ContentCaptureManagerService",
                    str
                            + ": called by UID "
                            + callingUid
                            + ", but there's no service set for user "
                            + callingUserId);
            return false;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(serviceName);
        if (unflattenFromString == null) {
            Slog.w("ContentCaptureManagerService", str + ": invalid service name: " + serviceName);
            return false;
        }
        try {
            int packageUidAsUser =
                    getContext()
                            .getPackageManager()
                            .getPackageUidAsUser(
                                    unflattenFromString.getPackageName(),
                                    UserHandle.getCallingUserId());
            if (callingUid == packageUidAsUser) {
                return true;
            }
            Slog.e(
                    "ContentCaptureManagerService",
                    str
                            + ": called by UID "
                            + callingUid
                            + ", but service UID is "
                            + packageUidAsUser);
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.w(
                    "ContentCaptureManagerService",
                    str + ": could not verify UID for " + serviceName);
            return false;
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final boolean isDisabledLocked(int i) {
        SparseBooleanArray sparseBooleanArray;
        return this.mDisabledByDeviceConfig
                || ((sparseBooleanArray = this.mDisabledBySettings) != null
                        && sparseBooleanArray.get(i))
                || super.isDisabledLocked(i);
    }

    @Override // com.android.server.SystemService
    public final boolean isUserSupported(SystemService.TargetUser targetUser) {
        return targetUser.isFull() || targetUser.isProfile();
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new ContentCapturePerUserService(this, this.mLock, z, i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServiceNameChanged(int i, String str, boolean z) {
        GlobalContentCaptureOptions.m384$$Nest$msetServiceInfo(
                this.mGlobalContentCaptureOptions, i, str, z);
        super.onServiceNameChanged(i, str, z);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageUpdatedLocked(int i) {
        ContentCapturePerUserService contentCapturePerUserService =
                (ContentCapturePerUserService) getServiceForUserLocked(i);
        if (contentCapturePerUserService != null) {
            contentCapturePerUserService.updateRemoteServiceLocked(
                    !contentCapturePerUserService.isEnabledLocked());
            contentCapturePerUserService.resurrectSessionsLocked$1();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageUpdatingLocked(int i) {
        ContentCapturePerUserService contentCapturePerUserService =
                (ContentCapturePerUserService) getServiceForUserLocked(i);
        if (contentCapturePerUserService != null) {
            int size = contentCapturePerUserService.mSessions.size();
            if (((ContentCaptureManagerService) contentCapturePerUserService.mMaster).debug) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        size,
                        "Pausing ",
                        " sessions while package is updating",
                        "ContentCapturePerUserService");
            }
            for (int i2 = 0; i2 < size; i2++) {
                ContentCaptureServerSession contentCaptureServerSession =
                        (ContentCaptureServerSession)
                                contentCapturePerUserService.mSessions.valueAt(i2);
                if (contentCaptureServerSession.mService.mMaster.verbose) {
                    Slog.v(
                            "ContentCaptureServerSession",
                            "pausing " + contentCaptureServerSession.mActivityToken);
                }
                ContentCaptureService.setClientState(
                        contentCaptureServerSession.mSessionStateReceiver, 2052, (IBinder) null);
            }
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServiceRemoved(
            AbstractPerUserSystemService abstractPerUserSystemService, int i) {
        ContentCapturePerUserService contentCapturePerUserService =
                (ContentCapturePerUserService) abstractPerUserSystemService;
        if (((ContentCaptureManagerService) contentCapturePerUserService.mMaster).debug) {
            Slog.d("ContentCapturePerUserService", "destroyLocked()");
        }
        RemoteContentCaptureService remoteContentCaptureService =
                contentCapturePerUserService.mRemoteService;
        if (remoteContentCaptureService != null) {
            remoteContentCaptureService.destroy();
        }
        contentCapturePerUserService.destroySessionsLocked();
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onSettingsChanged(int i, String str) {
        if (!str.equals("content_capture_enabled")) {
            PinnerService$$ExternalSyntheticOutline0.m(
                    "Unexpected property (",
                    str,
                    "); updating cache instead",
                    "ContentCaptureManagerService");
            return;
        }
        boolean z = true;
        boolean z2 =
                Settings.Secure.getIntForUser(
                                getContext().getContentResolver(), "content_capture_enabled", 1, i)
                        == 1;
        synchronized (this.mLock) {
            try {
                if (this.mDisabledBySettings == null) {
                    this.mDisabledBySettings = new SparseBooleanArray();
                }
                if (!((!this.mDisabledBySettings.get(i)) ^ z2)) {
                    if (this.debug) {
                        Slog.d(
                                "ContentCaptureManagerService",
                                "setContentCaptureFeatureEnabledForUser(): already " + z2);
                    }
                    return;
                }
                if (z2) {
                    Slog.i(
                            "ContentCaptureManagerService",
                            "setContentCaptureFeatureEnabled(): enabling service for user " + i);
                    this.mDisabledBySettings.delete(i);
                } else {
                    Slog.i(
                            "ContentCaptureManagerService",
                            "setContentCaptureFeatureEnabled(): disabling service for user " + i);
                    this.mDisabledBySettings.put(i, true);
                }
                if (z2 && !this.mDisabledByDeviceConfig) {
                    z = false;
                }
                getServiceForUserLocked(i);
                updateCachedServiceListLocked(i, z);
            } finally {
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("content_capture", this.mContentCaptureManagerServiceStub);
        publishLocalService(LocalService.class, this.mLocalService);
    }

    public List parseContentProtectionGroupsConfig(String str) {
        if (this.verbose) {
            Slog.v("ContentCaptureManagerService", "parseContentProtectionGroupsConfig: " + str);
        }
        return str == null
                ? Collections.emptyList()
                : Arrays.stream(str.split(";"))
                        .map(
                                new Function() { // from class:
                                                 // com.android.server.contentcapture.ContentCaptureManagerService$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        ContentCaptureManagerService.this.getClass();
                                        return Arrays.stream(((String) obj).split(","))
                                                .filter(
                                                        new ContentCaptureManagerService$$ExternalSyntheticLambda2(
                                                                1))
                                                .toList();
                                    }
                                })
                        .filter(new ContentCaptureManagerService$$ExternalSyntheticLambda2(0))
                        .toList();
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void registerForExtraSettingsChanges(
            ContentResolver contentResolver, ContentObserver contentObserver) {
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("content_capture_enabled"), false, contentObserver, -1);
    }

    public final void setActivityStartAssistDataEnabled() {
        synchronized (this.mLock) {
            this.activityStartAssistDataEnabled =
                    DeviceConfig.getBoolean(
                            "content_capture", "enable_activity_start_assist_content", false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setDisabledByDeviceConfig(java.lang.String r10) {
        /*
            r9 = this;
            java.lang.String r0 = "setDisabledByDeviceConfig(): set to "
            java.lang.String r1 = "setDisabledByDeviceConfig(): already "
            boolean r2 = r9.verbose
            if (r2 == 0) goto L1e
            java.lang.String r2 = "ContentCaptureManagerService"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "setDisabledByDeviceConfig(): explicitlyEnabled="
            r3.<init>(r4)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            android.util.Slog.v(r2, r3)
        L1e:
            java.util.List r2 = r9.getSupportedUsers()
            r3 = 1
            r4 = 0
            if (r10 == 0) goto L31
            java.lang.String r5 = "false"
            boolean r10 = r10.equalsIgnoreCase(r5)
            if (r10 == 0) goto L31
            r10 = r3
            goto L32
        L31:
            r10 = r4
        L32:
            java.lang.Object r5 = r9.mLock
            monitor-enter(r5)
            boolean r6 = r9.mDisabledByDeviceConfig     // Catch: java.lang.Throwable -> L4f
            if (r6 != r10) goto L54
            boolean r9 = r9.verbose     // Catch: java.lang.Throwable -> L4f
            if (r9 == 0) goto L52
            java.lang.String r9 = "ContentCaptureManagerService"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L4f
            r0.append(r10)     // Catch: java.lang.Throwable -> L4f
            java.lang.String r10 = r0.toString()     // Catch: java.lang.Throwable -> L4f
            android.util.Slog.v(r9, r10)     // Catch: java.lang.Throwable -> L4f
            goto L52
        L4f:
            r9 = move-exception
            goto Lbe
        L52:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L4f
            return
        L54:
            r9.mDisabledByDeviceConfig = r10     // Catch: java.lang.Throwable -> L4f
            java.lang.String r10 = "ContentCaptureManagerService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L4f
            boolean r0 = r9.mDisabledByDeviceConfig     // Catch: java.lang.Throwable -> L4f
            r1.append(r0)     // Catch: java.lang.Throwable -> L4f
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L4f
            android.util.Slog.i(r10, r0)     // Catch: java.lang.Throwable -> L4f
            r10 = r4
        L6a:
            r0 = r2
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: java.lang.Throwable -> L4f
            int r1 = r0.size()     // Catch: java.lang.Throwable -> L4f
            if (r10 >= r1) goto Lbc
            java.lang.Object r0 = r0.get(r10)     // Catch: java.lang.Throwable -> L4f
            android.content.pm.UserInfo r0 = (android.content.pm.UserInfo) r0     // Catch: java.lang.Throwable -> L4f
            int r0 = r0.id     // Catch: java.lang.Throwable -> L4f
            boolean r1 = r9.mDisabledByDeviceConfig     // Catch: java.lang.Throwable -> L4f
            if (r1 != 0) goto L8c
            android.util.SparseBooleanArray r1 = r9.mDisabledBySettings     // Catch: java.lang.Throwable -> L4f
            if (r1 == 0) goto L8a
            boolean r1 = r1.get(r0)     // Catch: java.lang.Throwable -> L4f
            if (r1 == 0) goto L8a
            goto L8c
        L8a:
            r1 = r4
            goto L8d
        L8c:
            r1 = r3
        L8d:
            java.lang.String r6 = "ContentCaptureManagerService"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f
            r7.<init>()     // Catch: java.lang.Throwable -> L4f
            java.lang.String r8 = "setDisabledByDeviceConfig(): updating service for user "
            r7.append(r8)     // Catch: java.lang.Throwable -> L4f
            r7.append(r0)     // Catch: java.lang.Throwable -> L4f
            java.lang.String r8 = " to "
            r7.append(r8)     // Catch: java.lang.Throwable -> L4f
            if (r1 == 0) goto La7
            java.lang.String r8 = "'disabled'"
            goto La9
        La7:
            java.lang.String r8 = "'enabled'"
        La9:
            r7.append(r8)     // Catch: java.lang.Throwable -> L4f
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L4f
            android.util.Slog.i(r6, r7)     // Catch: java.lang.Throwable -> L4f
            r9.getServiceForUserLocked(r0)     // Catch: java.lang.Throwable -> L4f
            r9.updateCachedServiceListLocked(r0, r1)     // Catch: java.lang.Throwable -> L4f
            int r10 = r10 + 1
            goto L6a
        Lbc:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L4f
            return
        Lbe:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L4f
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.contentcapture.ContentCaptureManagerService.setDisabledByDeviceConfig(java.lang.String):void");
    }

    public void setFineTuneParamsFromDeviceConfig() {
        boolean z;
        boolean deviceConfigEnableContentProtectionReceiver;
        String string;
        String string2;
        int i;
        long j;
        ContentProtectionAllowlistManager contentProtectionAllowlistManager;
        long j2;
        long j3;
        ContentProtectionAllowlistManager contentProtectionAllowlistManager2;
        ContentProtectionConsentManager contentProtectionConsentManager;
        synchronized (this.mLock) {
            try {
                this.mDevCfgMaxBufferSize =
                        DeviceConfig.getInt("content_capture", "max_buffer_size", 500);
                this.mDevCfgIdleFlushingFrequencyMs =
                        DeviceConfig.getInt("content_capture", "idle_flush_frequency", 5000);
                this.mDevCfgTextChangeFlushingFrequencyMs =
                        DeviceConfig.getInt("content_capture", "text_change_flush_frequency", 1000);
                this.mDevCfgLogHistorySize =
                        DeviceConfig.getInt("content_capture", "log_history_size", 20);
                this.mDevCfgIdleUnbindTimeoutMs =
                        DeviceConfig.getInt("content_capture", "idle_unbind_timeout", 0);
                this.mDevCfgDisableFlushForViewTreeAppearing =
                        DeviceConfig.getBoolean(
                                "content_capture", "disable_flush_for_view_tree_appearing", false);
                z = this.mDevCfgEnableContentProtectionReceiver;
                deviceConfigEnableContentProtectionReceiver =
                        getDeviceConfigEnableContentProtectionReceiver();
                this.mDevCfgContentProtectionBufferSize =
                        DeviceConfig.getInt(
                                "content_capture", "content_protection_buffer_size", 150);
                string =
                        DeviceConfig.getString(
                                "content_capture", "content_protection_required_groups_config", "");
                string2 =
                        DeviceConfig.getString(
                                "content_capture", "content_protection_optional_groups_config", "");
                i =
                        DeviceConfig.getInt(
                                "content_capture",
                                "content_protection_optional_groups_threshold",
                                0);
                long j4 =
                        DeviceConfig.getLong(
                                "content_capture", "content_protection_allowlist_delay_ms", 30000L);
                j =
                        DeviceConfig.getLong(
                                "content_capture", "content_protection_allowlist_timeout_ms", 250L);
                this.mDevCfgContentProtectionAutoDisconnectTimeoutMs =
                        DeviceConfig.getLong(
                                "content_capture",
                                "content_protection_auto_disconnect_timeout_ms",
                                3000L);
                contentProtectionAllowlistManager = this.mContentProtectionAllowlistManager;
                if (getContext()
                        .getPackageManager()
                        .hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite")) {
                    this.mDevCfgIdleUnbindTimeoutMs = 120000;
                }
                if (this.verbose) {
                    StringBuilder sb =
                            new StringBuilder("setFineTuneParamsFromDeviceConfig(): bufferSize=");
                    sb.append(this.mDevCfgMaxBufferSize);
                    sb.append(", idleFlush=");
                    sb.append(this.mDevCfgIdleFlushingFrequencyMs);
                    sb.append(", textFlush=");
                    sb.append(this.mDevCfgTextChangeFlushingFrequencyMs);
                    sb.append(", logHistory=");
                    sb.append(this.mDevCfgLogHistorySize);
                    sb.append(", idleUnbindTimeoutMs=");
                    sb.append(this.mDevCfgIdleUnbindTimeoutMs);
                    sb.append(", disableFlushForViewTreeAppearing=");
                    sb.append(this.mDevCfgDisableFlushForViewTreeAppearing);
                    sb.append(", enableContentProtectionReceiver=");
                    sb.append(deviceConfigEnableContentProtectionReceiver);
                    sb.append(", contentProtectionBufferSize=");
                    sb.append(this.mDevCfgContentProtectionBufferSize);
                    sb.append(", contentProtectionRequiredGroupsConfig=");
                    sb.append(string);
                    sb.append(", contentProtectionOptionalGroupsConfig=");
                    sb.append(string2);
                    sb.append(", contentProtectionOptionalGroupsThreshold=");
                    sb.append(i);
                    sb.append(", contentProtectionAllowlistDelayMs=");
                    sb.append(j4);
                    sb.append(", contentProtectionAllowlistTimeoutMs=");
                    sb.append(j);
                    sb.append(", contentProtectionAutoDisconnectTimeoutMs=");
                    j2 = j4;
                    sb.append(this.mDevCfgContentProtectionAutoDisconnectTimeoutMs);
                    Slog.v("ContentCaptureManagerService", sb.toString());
                } else {
                    j2 = j4;
                }
            } finally {
            }
        }
        List parseContentProtectionGroupsConfig = parseContentProtectionGroupsConfig(string);
        List parseContentProtectionGroupsConfig2 = parseContentProtectionGroupsConfig(string2);
        if (contentProtectionAllowlistManager != null
                && !deviceConfigEnableContentProtectionReceiver) {
            contentProtectionAllowlistManager.stop();
        }
        ComponentName componentName = null;
        if (z || !deviceConfigEnableContentProtectionReceiver) {
            j3 = j2;
            contentProtectionAllowlistManager2 = null;
            contentProtectionConsentManager = null;
        } else {
            String contentProtectionServiceFlatComponentName =
                    getContentProtectionServiceFlatComponentName();
            ComponentName unflattenFromString =
                    contentProtectionServiceFlatComponentName == null
                            ? null
                            : ComponentName.unflattenFromString(
                                    contentProtectionServiceFlatComponentName);
            if (unflattenFromString != null) {
                final ContentProtectionAllowlistManager createContentProtectionAllowlistManager =
                        createContentProtectionAllowlistManager(j);
                if (createContentProtectionAllowlistManager.mStarted) {
                    j3 = j2;
                } else {
                    createContentProtectionAllowlistManager.mStarted = true;
                    j3 = j2;
                    createContentProtectionAllowlistManager.mHandler.postDelayed(
                            new Runnable() { // from class:
                                             // com.android.server.contentprotection.ContentProtectionAllowlistManager$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ContentProtectionAllowlistManager
                                            contentProtectionAllowlistManager3 =
                                                    ContentProtectionAllowlistManager.this;
                                    contentProtectionAllowlistManager3.handlePackagesChanged();
                                    contentProtectionAllowlistManager3.mPackageMonitor.register(
                                            contentProtectionAllowlistManager3
                                                    .mContentCaptureManagerService.getContext(),
                                            UserHandle.ALL,
                                            contentProtectionAllowlistManager3.mHandler);
                                }
                            },
                            createContentProtectionAllowlistManager.mHandlerToken,
                            j3);
                }
                contentProtectionConsentManager = createContentProtectionConsentManager();
                ComponentName componentName2 = unflattenFromString;
                contentProtectionAllowlistManager2 = createContentProtectionAllowlistManager;
                componentName = componentName2;
            } else {
                j3 = j2;
                contentProtectionConsentManager = null;
                componentName = unflattenFromString;
                contentProtectionAllowlistManager2 = null;
            }
        }
        synchronized (this.mLock) {
            try {
                this.mDevCfgEnableContentProtectionReceiver =
                        deviceConfigEnableContentProtectionReceiver;
                this.mDevCfgContentProtectionRequiredGroups = parseContentProtectionGroupsConfig;
                this.mDevCfgContentProtectionOptionalGroups = parseContentProtectionGroupsConfig2;
                this.mDevCfgContentProtectionOptionalGroupsThreshold = i;
                this.mDevCfgContentProtectionAllowlistDelayMs = j3;
                if (z ^ deviceConfigEnableContentProtectionReceiver) {
                    this.mContentProtectionServiceComponentName = componentName;
                    this.mContentProtectionAllowlistManager = contentProtectionAllowlistManager2;
                    this.mContentProtectionConsentManager = contentProtectionConsentManager;
                }
            } finally {
            }
        }
    }

    public final void setLoggingLevelFromDeviceConfig() {
        int i =
                DeviceConfig.getInt(
                        "content_capture",
                        "logging_level",
                        ContentCaptureHelper.getDefaultLoggingLevel());
        this.mDevCfgLoggingLevel = i;
        ContentCaptureHelper.setLoggingLevel(i);
        boolean z = ContentCaptureHelper.sVerbose;
        this.verbose = z;
        this.debug = ContentCaptureHelper.sDebug;
        if (z) {
            StringBuilder sb = new StringBuilder("setLoggingLevelFromDeviceConfig(): level=");
            sb.append(this.mDevCfgLoggingLevel);
            sb.append(", debug=");
            sb.append(this.debug);
            sb.append(", verbose=");
            ProxyManager$$ExternalSyntheticOutline0.m(
                    "ContentCaptureManagerService", sb, this.verbose);
        }
    }
}
