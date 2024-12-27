package com.android.server.companion.association;

import android.app.ActivityManager;
import android.app.role.RoleManager;
import android.companion.AssociationInfo;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;

import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.companion.datatransfer.SystemDataTransferRequestStore;
import com.android.server.companion.devicepresence.CompanionAppBinder;
import com.android.server.companion.devicepresence.DevicePresenceProcessor;
import com.android.server.companion.transport.CompanionTransportManager;
import com.android.server.companion.utils.MetricUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisassociationProcessor {
    public static final long ASSOCIATION_REMOVAL_TIME_WINDOW_DEFAULT = TimeUnit.DAYS.toMillis(90);
    public final ActivityManager mActivityManager;
    public final AssociationStore mAssociationStore;
    public final CompanionAppBinder mCompanionAppController;
    public final Context mContext;
    public final DevicePresenceProcessor mDevicePresenceMonitor;
    public final OnPackageVisibilityChangeListener mOnPackageVisibilityChangeListener =
            new OnPackageVisibilityChangeListener();
    public final PackageManagerInternal mPackageManagerInternal;
    public final SystemDataTransferRequestStore mSystemDataTransferRequestStore;
    public final CompanionTransportManager mTransportManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OnPackageVisibilityChangeListener
            implements ActivityManager.OnUidImportanceListener {
        public OnPackageVisibilityChangeListener() {}

        public final void onUidImportance(int i, int i2) {
            String nameForUid;
            List filter;
            List filter2;
            if (i2 > 200
                    && (nameForUid =
                                    DisassociationProcessor.this.mPackageManagerInternal
                                            .getNameForUid(i))
                            != null) {
                int userId = UserHandle.getUserId(i);
                AssociationStore associationStore = DisassociationProcessor.this.mAssociationStore;
                synchronized (associationStore.mLock) {
                    filter =
                            CollectionUtils.filter(
                                    associationStore.getAssociations(),
                                    new AssociationStore$$ExternalSyntheticLambda7(
                                            userId, 1, nameForUid));
                }
                Iterator it = filter.iterator();
                while (it.hasNext()) {
                    DisassociationProcessor.this.disassociate(
                            ((AssociationInfo) it.next()).getId());
                }
                AssociationStore associationStore2 = DisassociationProcessor.this.mAssociationStore;
                synchronized (associationStore2.mLock) {
                    filter2 =
                            CollectionUtils.filter(
                                    associationStore2.getAssociations(),
                                    new AssociationStore$$ExternalSyntheticLambda2(1));
                }
                if (filter2.isEmpty()) {
                    DisassociationProcessor disassociationProcessor = DisassociationProcessor.this;
                    disassociationProcessor.getClass();
                    Slog.i(
                            "CDM_DisassociationProcessor",
                            "Stop listening to uid importance changes.");
                    try {
                        Binder.withCleanCallingIdentity(
                                new DisassociationProcessor$$ExternalSyntheticLambda2(
                                        disassociationProcessor, 0));
                    } catch (IllegalArgumentException unused) {
                        Slog.e(
                                "CDM_DisassociationProcessor",
                                "Failed to stop listening to uid importance changes.");
                    }
                }
            }
        }
    }

    public DisassociationProcessor(
            Context context,
            ActivityManager activityManager,
            AssociationStore associationStore,
            PackageManagerInternal packageManagerInternal,
            DevicePresenceProcessor devicePresenceProcessor,
            CompanionAppBinder companionAppBinder,
            SystemDataTransferRequestStore systemDataTransferRequestStore,
            CompanionTransportManager companionTransportManager) {
        this.mContext = context;
        this.mActivityManager = activityManager;
        this.mAssociationStore = associationStore;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mDevicePresenceMonitor = devicePresenceProcessor;
        this.mCompanionAppController = companionAppBinder;
        this.mSystemDataTransferRequestStore = systemDataTransferRequestStore;
        this.mTransportManager = companionTransportManager;
    }

    public final void disassociate(final int i) {
        BootReceiver$$ExternalSyntheticOutline0.m(
                i, "Disassociating id=[", "]...", "CDM_DisassociationProcessor");
        AssociationInfo associationWithCallerChecks =
                this.mAssociationStore.getAssociationWithCallerChecks(i);
        final int userId = associationWithCallerChecks.getUserId();
        final String packageName = associationWithCallerChecks.getPackageName();
        final String deviceProfile = associationWithCallerChecks.getDeviceProfile();
        boolean z =
                deviceProfile != null
                        && CollectionUtils.any(
                                this.mAssociationStore.getActiveAssociationsByPackage(
                                        userId, packageName),
                                new Predicate() { // from class:
                                                  // com.android.server.companion.association.DisassociationProcessor$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        AssociationInfo associationInfo = (AssociationInfo) obj;
                                        return deviceProfile.equals(
                                                        associationInfo.getDeviceProfile())
                                                && i != associationInfo.getId();
                                    }
                                });
        if (((Integer)
                                        Binder.withCleanCallingIdentity(
                                                new FunctionalUtils
                                                        .ThrowingSupplier() { // from class:
                                                                              // com.android.server.companion.association.DisassociationProcessor$$ExternalSyntheticLambda4
                                                    public final Object getOrThrow() {
                                                        DisassociationProcessor
                                                                disassociationProcessor =
                                                                        DisassociationProcessor
                                                                                .this;
                                                        return Integer.valueOf(
                                                                disassociationProcessor
                                                                        .mActivityManager
                                                                        .getUidImportance(
                                                                                disassociationProcessor
                                                                                        .mPackageManagerInternal
                                                                                        .getPackageUid(
                                                                                                packageName,
                                                                                                0L,
                                                                                                userId)));
                                                    }
                                                }))
                                .intValue()
                        <= 200
                && deviceProfile != null
                && !z) {
            Slog.i(
                    "CDM_DisassociationProcessor",
                    "Cannot disassociate id=["
                            + i
                            + "] now - process is visible. Start listening to package"
                            + " importance...");
            this.mAssociationStore.updateAssociation(
                    new AssociationInfo.Builder(associationWithCallerChecks)
                            .setRevoked(true)
                            .build());
            Slog.i("CDM_DisassociationProcessor", "Start listening to uid importance changes...");
            try {
                Binder.withCleanCallingIdentity(
                        new DisassociationProcessor$$ExternalSyntheticLambda2(this, 1));
                return;
            } catch (IllegalArgumentException unused) {
                Slog.e(
                        "CDM_DisassociationProcessor",
                        "Failed to start listening to uid importance changes.");
                return;
            }
        }
        this.mTransportManager.detachSystemDataTransport(i);
        this.mSystemDataTransferRequestStore.removeRequestsByAssociationId(userId, i);
        AssociationStore associationStore = this.mAssociationStore;
        int id = associationWithCallerChecks.getId();
        associationStore.getClass();
        Slog.i("CDM_AssociationStore", "Removing association id=[" + id + "]...");
        synchronized (associationStore.mLock) {
            try {
                AssociationInfo associationInfo =
                        (AssociationInfo)
                                ((HashMap) associationStore.mIdToAssociationMap)
                                        .remove(Integer.valueOf(id));
                if (associationInfo == null) {
                    Slog.w(
                            "CDM_AssociationStore",
                            "Can't remove association id=[" + id + "]. It does not exist.");
                } else {
                    associationStore.mExecutor.execute(
                            new AssociationStore$$ExternalSyntheticLambda9(
                                    associationStore, associationInfo.getUserId()));
                    Slog.i("CDM_AssociationStore", "Done removing association.");
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.CDM_ASSOCIATION_ACTION,
                            2,
                            ((Integer)
                                            MetricUtils.METRIC_DEVICE_PROFILE.get(
                                                    associationInfo.getDeviceProfile()))
                                    .intValue());
                    if (associationInfo.isActive()) {
                        associationStore.broadcastChange(1, associationInfo);
                    }
                }
            } finally {
            }
        }
        if (!z
                && deviceProfile != null
                && !deviceProfile.equals("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION")) {
            final Context context = this.mContext;
            final int userId2 = associationWithCallerChecks.getUserId();
            final String packageName2 = associationWithCallerChecks.getPackageName();
            final String deviceProfile2 = associationWithCallerChecks.getDeviceProfile();
            if (deviceProfile2 != null) {
                final RoleManager roleManager =
                        (RoleManager) context.getSystemService(RoleManager.class);
                final UserHandle of = UserHandle.of(userId2);
                StringBuilder m =
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                userId2,
                                "Removing CDM role=",
                                deviceProfile2,
                                " for userId=",
                                ", packageName=");
                m.append(packageName2);
                Slog.i("CDM_RolesUtils", m.toString());
                Binder.withCleanCallingIdentity(
                        new FunctionalUtils
                                .ThrowingRunnable() { // from class:
                                                      // com.android.server.companion.utils.RolesUtils$$ExternalSyntheticLambda0
                            public final void runOrThrow() {
                                RoleManager roleManager2 = roleManager;
                                final String str = deviceProfile2;
                                final String str2 = packageName2;
                                UserHandle userHandle = of;
                                Context context2 = context;
                                final int i2 = userId2;
                                roleManager2.removeRoleHolderAsUser(
                                        str,
                                        str2,
                                        1,
                                        userHandle,
                                        context2.getMainExecutor(),
                                        new Consumer() { // from class:
                                                         // com.android.server.companion.utils.RolesUtils$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int i3 = i2;
                                                String str3 = str2;
                                                String str4 = str;
                                                if (((Boolean) obj).booleanValue()) {
                                                    return;
                                                }
                                                StringBuilder m2 =
                                                        DirEncryptService$$ExternalSyntheticOutline0
                                                                .m(
                                                                        i3,
                                                                        "Failed to remove userId=",
                                                                        ", packageName=",
                                                                        str3,
                                                                        " from the list of ");
                                                m2.append(str4);
                                                m2.append(" holders.");
                                                Slog.e("CDM_RolesUtils", m2.toString());
                                            }
                                        });
                            }
                        });
            }
        }
        if (this.mDevicePresenceMonitor.isDevicePresent(i)
                && associationWithCallerChecks.isNotifyOnDeviceNearby()
                && !CollectionUtils.any(
                        this.mAssociationStore.getActiveAssociationsByPackage(userId, packageName),
                        new Predicate() { // from class:
                                          // com.android.server.companion.association.DisassociationProcessor$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                DisassociationProcessor disassociationProcessor =
                                        DisassociationProcessor.this;
                                AssociationInfo associationInfo2 = (AssociationInfo) obj;
                                disassociationProcessor.getClass();
                                return associationInfo2.isNotifyOnDeviceNearby()
                                        && disassociationProcessor.mDevicePresenceMonitor
                                                .isDevicePresent(associationInfo2.getId());
                            }
                        })) {
            this.mCompanionAppController.unbindCompanionApp(userId, packageName);
        }
    }

    public final void removeIdleSelfManagedAssociations() {
        Slog.i("CDM_DisassociationProcessor", "Removing idle self-managed associations.");
        long currentTimeMillis = System.currentTimeMillis();
        long j = SystemProperties.getLong("debug.cdm.cdmservice.removal_time_window", -1L);
        if (j <= 0) {
            j = ASSOCIATION_REMOVAL_TIME_WINDOW_DEFAULT;
        }
        for (AssociationInfo associationInfo : this.mAssociationStore.getAssociations()) {
            if (associationInfo.isSelfManaged()
                    && currentTimeMillis - associationInfo.getLastTimeConnectedMs() >= j) {
                int id = associationInfo.getId();
                Slog.i(
                        "CDM_DisassociationProcessor",
                        "Removing inactive self-managed association=["
                                + associationInfo.toShortString()
                                + "].");
                disassociate(id);
            }
        }
    }
}
