package com.android.server.companion;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ecm.EnhancedConfirmationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.companion.AssociationInfo;
import android.companion.DeviceNotAssociatedException;
import android.companion.ICompanionDeviceManager;
import android.companion.IOnAssociationsChangedListener;
import android.companion.IOnMessageReceivedListener;
import android.companion.IOnTransportsChangedListener;
import android.companion.ISystemDataTransferCallback;
import android.companion.ObservingDevicePresenceRequest;
import android.companion.datatransfer.PermissionSyncRequest;
import android.companion.datatransfer.SystemDataTransferRequest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.net.MacAddress;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PowerExemptionManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.app.IAppOpsService;
import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.notification.NotificationAccessConfirmationActivityContract;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.companion.CompanionDeviceManagerService;
import com.android.server.companion.association.AssociationDiskStore;
import com.android.server.companion.association.AssociationRequestsProcessor;
import com.android.server.companion.association.AssociationRequestsProcessor$$ExternalSyntheticLambda2;
import com.android.server.companion.association.AssociationStore;
import com.android.server.companion.association.AssociationStore$$ExternalSyntheticLambda3;
import com.android.server.companion.association.AssociationStore$$ExternalSyntheticLambda7;
import com.android.server.companion.association.DisassociationProcessor;
import com.android.server.companion.association.InactiveAssociationsRemovalService;
import com.android.server.companion.datatransfer.SystemDataTransferProcessor;
import com.android.server.companion.datatransfer.SystemDataTransferRequestStore;
import com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData;
import com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController;
import com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback;
import com.android.server.companion.devicepresence.BleDeviceProcessor;
import com.android.server.companion.devicepresence.BleDeviceProcessor.AnonymousClass1;
import com.android.server.companion.devicepresence.BluetoothDeviceProcessor;
import com.android.server.companion.devicepresence.CompanionAppBinder;
import com.android.server.companion.devicepresence.CompanionServiceConnector;
import com.android.server.companion.devicepresence.DevicePresenceProcessor;
import com.android.server.companion.devicepresence.ObservableUuid;
import com.android.server.companion.devicepresence.ObservableUuidStore;
import com.android.server.companion.transport.CompanionTransportManager;
import com.android.server.companion.transport.Transport;
import com.android.server.companion.utils.PackageUtils;
import com.android.server.companion.utils.PackageUtils$$ExternalSyntheticLambda1;
import com.android.server.companion.utils.PermissionsUtils;
import com.android.server.companion.utils.RolesUtils;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CompanionDeviceManagerService extends SystemService {
    public final ActivityManagerInternal mAmInternal;
    public final IAppOpsService mAppOpsManager;
    public final AssociationRequestsProcessor mAssociationRequestsProcessor;
    public final AssociationStore mAssociationStore;
    public final AnonymousClass1 mAssociationStoreChangeListener;
    public final ActivityTaskManagerInternal mAtmInternal;
    public final BackupRestoreProcessor mBackupRestoreProcessor;
    public final CompanionAppBinder mCompanionAppBinder;
    public final CrossDeviceSyncController mCrossDeviceSyncController;
    public final DevicePresenceProcessor mDevicePresenceProcessor;
    public final DisassociationProcessor mDisassociationProcessor;
    public final ObservableUuidStore mObservableUuidStore;
    public final PackageManagerInternal mPackageManagerInternal;
    public final AnonymousClass2 mPackageMonitor;
    public final PowerExemptionManager mPowerExemptionManager;
    public final SystemDataTransferProcessor mSystemDataTransferProcessor;
    public final SystemDataTransferRequestStore mSystemDataTransferRequestStore;
    public final CompanionTransportManager mTransportManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CompanionDeviceManagerImpl extends ICompanionDeviceManager.Stub {
        public CompanionDeviceManagerImpl() {
        }

        public final void addOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) {
            addOnAssociationsChangedListener_enforcePermission();
            PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(CompanionDeviceManagerService.this.getContext(), i);
            AssociationStore associationStore = CompanionDeviceManagerService.this.mAssociationStore;
            synchronized (associationStore.mRemoteListeners) {
                associationStore.mRemoteListeners.register(iOnAssociationsChangedListener, Integer.valueOf(i));
            }
        }

        public final void addOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) {
            addOnMessageReceivedListener_enforcePermission();
            CompanionDeviceManagerService.this.mTransportManager.addListener(i, iOnMessageReceivedListener);
        }

        public final void addOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) {
            addOnTransportsChangedListener_enforcePermission();
            CompanionDeviceManagerService.this.mTransportManager.addListener(iOnTransportsChangedListener);
        }

        public final void applyRestoredPayload(byte[] bArr, int i) {
            CompanionDeviceManagerService.this.mBackupRestoreProcessor.applyRestoredPayload(bArr, i);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(8:35|36|37|(2:40|(4:42|43|44|(1:46)))|51|43|44|(0)) */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0179, code lost:
        
            com.android.server.BootReceiver$$ExternalSyntheticOutline0.m58m("NameNotFoundException ", r21, " or com.samsung.accessory.wmanager", "CDM_AssociationRequestsProcessor");
         */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0183  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void associate(android.companion.AssociationRequest r19, android.companion.IAssociationRequestCallback r20, final java.lang.String r21, final int r22) {
            /*
                Method dump skipped, instructions count: 469
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.companion.CompanionDeviceManagerService.CompanionDeviceManagerImpl.associate(android.companion.AssociationRequest, android.companion.IAssociationRequestCallback, java.lang.String, int):void");
        }

        public final void attachSystemDataTransport(String str, int i, int i2, ParcelFileDescriptor parcelFileDescriptor) {
            attachSystemDataTransport_enforcePermission();
            CompanionTransportManager companionTransportManager = CompanionDeviceManagerService.this.mTransportManager;
            companionTransportManager.getClass();
            Slog.i("CDM_CompanionTransportManager", "Attaching transport for association id=[" + i2 + "]...");
            companionTransportManager.mAssociationStore.getAssociationWithCallerChecks(i2);
            synchronized (companionTransportManager.mTransports) {
                try {
                    if (companionTransportManager.mTransports.contains(i2)) {
                        companionTransportManager.detachSystemDataTransport(i2);
                    }
                    companionTransportManager.initializeTransport(i2, parcelFileDescriptor);
                    companionTransportManager.notifyOnTransportsChanged();
                } catch (Throwable th) {
                    throw th;
                }
            }
            Slog.i("CDM_CompanionTransportManager", "Transport attached.");
        }

        public final PendingIntent buildAssociationCancellationIntent(String str, int i) {
            Slog.i("CDM_CompanionDeviceManagerService", "buildAssociationCancellationIntent() package=u" + i + "/" + str);
            PermissionsUtils.enforceCallerCanManageAssociationsForPackage(i, CompanionDeviceManagerService.this.getContext(), str, "build association cancellation intent");
            AssociationRequestsProcessor associationRequestsProcessor = CompanionDeviceManagerService.this.mAssociationRequestsProcessor;
            associationRequestsProcessor.getClass();
            Objects.requireNonNull(str, "Package name MUST NOT be null");
            PackageUtils.enforceUsesCompanionDeviceFeature(associationRequestsProcessor.mContext, str, i);
            int packageUid = associationRequestsProcessor.mPackageManagerInternal.getPackageUid(str, 0L, i);
            Bundle bundle = new Bundle();
            bundle.putBoolean("cancel_confirmation", true);
            Intent intent = new Intent();
            intent.setComponent(associationRequestsProcessor.mCompanionAssociationActivity);
            intent.putExtras(bundle);
            return (PendingIntent) Binder.withCleanCallingIdentity(new AssociationRequestsProcessor$$ExternalSyntheticLambda2(associationRequestsProcessor, packageUid, intent));
        }

        public final PendingIntent buildPermissionTransferUserConsentIntent(String str, int i, final int i2) {
            final SystemDataTransferProcessor systemDataTransferProcessor = CompanionDeviceManagerService.this.mSystemDataTransferProcessor;
            if (PackageUtils.isPackageAllowlisted(systemDataTransferProcessor.mContext, systemDataTransferProcessor.mPackageManager, str, R.array.config_screenBrightnessNits, R.array.config_screenBrightnessBacklight)) {
                Slog.i("CDM_SystemDataTransferProcessor", "User consent Intent should be skipped. Returning null.");
                if (systemDataTransferProcessor.getPermissionSyncRequest(i2) == null) {
                    SystemDataTransferRequest permissionSyncRequest = new PermissionSyncRequest(i2);
                    permissionSyncRequest.setUserConsented(true);
                    systemDataTransferProcessor.mSystemDataTransferRequestStore.writeRequest(i, permissionSyncRequest);
                }
                return null;
            }
            Slog.i("CDM_SystemDataTransferProcessor", DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "Creating permission sync intent for userId [", "] associationId [", "]"));
            AssociationInfo associationWithCallerChecks = systemDataTransferProcessor.mAssociationStore.getAssociationWithCallerChecks(i2);
            Bundle bundle = new Bundle();
            PermissionSyncRequest permissionSyncRequest2 = new PermissionSyncRequest(i2);
            permissionSyncRequest2.setUserId(i);
            bundle.putParcelable("permission_sync_request", permissionSyncRequest2);
            bundle.putCharSequence("companion_device_name", associationWithCallerChecks.getDisplayName());
            Parcel obtain = Parcel.obtain();
            systemDataTransferProcessor.mOnSystemDataTransferRequestConfirmationReceiver.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            ResultReceiver resultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(obtain);
            obtain.recycle();
            bundle.putParcelable("system_data_transfer_result_receiver", resultReceiver);
            final Intent intent = new Intent();
            intent.setComponent(systemDataTransferProcessor.mCompanionDeviceDataTransferActivity);
            intent.putExtras(bundle);
            return (PendingIntent) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor$$ExternalSyntheticLambda1
                public final Object getOrThrow() {
                    SystemDataTransferProcessor systemDataTransferProcessor2 = SystemDataTransferProcessor.this;
                    return PendingIntent.getActivityAsUser(systemDataTransferProcessor2.mContext, i2, intent, 1409286144, ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(1).toBundle(), UserHandle.CURRENT);
                }
            });
        }

        public final boolean canPairWithoutPrompt(String str, String str2, int i) {
            AssociationInfo firstAssociationByAddress = CompanionDeviceManagerService.this.mAssociationStore.getFirstAssociationByAddress(i, str, str2);
            return firstAssociationByAddress != null && System.currentTimeMillis() - firstAssociationByAddress.getTimeApprovedMs() < 600000;
        }

        public final void checkCanCallNotificationApi(int i, String str) {
            Map map = PermissionsUtils.DEVICE_PROFILE_TO_PERMISSION;
            int callingUid = Binder.getCallingUid();
            if (callingUid != 1000) {
                int callingUserId = UserHandle.getCallingUserId();
                if (UserHandle.getCallingUserId() != i) {
                    throw new SecurityException(DualAppManagerService$$ExternalSyntheticOutline0.m(callingUserId, i, "Calling UserId (", ") does not match the expected UserId (", ")"));
                }
                if (!PermissionsUtils.checkPackage(callingUid, str)) {
                    throw new SecurityException(str + " doesn't belong to calling uid (" + callingUid + ")");
                }
            }
            if (ICompanionDeviceManager.Stub.getCallingUid() == 1000) {
                return;
            }
            PackageUtils.enforceUsesCompanionDeviceFeature(CompanionDeviceManagerService.this.getContext(), str, i);
            Preconditions.checkState(!ArrayUtils.isEmpty(CompanionDeviceManagerService.this.mAssociationStore.getActiveAssociationsByPackage(i, str)), "App must have an association before calling this API");
        }

        public final void clearAssociationTag(int i) {
            setAssociationTag(i, null);
        }

        public final void createAssociation(String str, String str2, int i, byte[] bArr) {
            createAssociation_enforcePermission();
            if (!CompanionDeviceManagerService.this.getContext().getPackageManager().hasSigningCertificate(str, bArr, 1)) {
                Slog.e("CDM_CompanionDeviceManagerService", "Given certificate doesn't match the package certificate.");
            } else {
                CompanionDeviceManagerService.this.mAssociationRequestsProcessor.createAssociation(i, str, MacAddress.fromString(str2), null, null, null, false, null, null);
            }
        }

        public final void detachSystemDataTransport(String str, int i, int i2) {
            detachSystemDataTransport_enforcePermission();
            CompanionDeviceManagerService.this.mTransportManager.detachSystemDataTransport(i2);
        }

        public final void disablePermissionsSync(int i) {
            SystemDataTransferProcessor systemDataTransferProcessor = CompanionDeviceManagerService.this.mSystemDataTransferProcessor;
            int userId = systemDataTransferProcessor.mAssociationStore.getAssociationWithCallerChecks(i).getUserId();
            SystemDataTransferRequest permissionSyncRequest = new PermissionSyncRequest(i);
            permissionSyncRequest.setUserConsented(false);
            systemDataTransferProcessor.mSystemDataTransferRequestStore.writeRequest(userId, permissionSyncRequest);
        }

        public final void disableSystemDataSync(int i, int i2) {
            AssociationStore associationStore = CompanionDeviceManagerService.this.mAssociationRequestsProcessor.mAssociationStore;
            AssociationInfo associationWithCallerChecks = associationStore.getAssociationWithCallerChecks(i);
            associationStore.updateAssociation(new AssociationInfo.Builder(associationWithCallerChecks).setSystemDataSyncFlags(associationWithCallerChecks.getSystemDataSyncFlags() & (~i2)).build());
        }

        public final void disassociate(int i) {
            CompanionDeviceManagerService.this.mDisassociationProcessor.disassociate(i);
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            int i;
            if (DumpUtils.checkDumpAndUsageStatsPermission(CompanionDeviceManagerService.this.getContext(), "CDM_CompanionDeviceManagerService", printWriter)) {
                AssociationStore associationStore = CompanionDeviceManagerService.this.mAssociationStore;
                associationStore.getClass();
                printWriter.append("Companion Device Associations: ");
                if (associationStore.getActiveAssociations().isEmpty()) {
                    printWriter.append("<empty>\n");
                } else {
                    printWriter.append("\n");
                    Iterator it = associationStore.getActiveAssociations().iterator();
                    while (it.hasNext()) {
                        printWriter.append("  ").append((CharSequence) ((AssociationInfo) it.next()).toString()).append('\n');
                    }
                }
                DevicePresenceProcessor devicePresenceProcessor = CompanionDeviceManagerService.this.mDevicePresenceProcessor;
                devicePresenceProcessor.getClass();
                printWriter.append("Companion Device Present: ");
                if (((HashSet) devicePresenceProcessor.mConnectedBtDevices).isEmpty() && ((HashSet) devicePresenceProcessor.mNearbyBleDevices).isEmpty() && ((HashSet) devicePresenceProcessor.mReportedSelfManagedDevices).isEmpty()) {
                    printWriter.append("<empty>\n");
                } else {
                    printWriter.append("\n");
                    printWriter.append("  Connected Bluetooth Devices: ");
                    boolean isEmpty = ((HashSet) devicePresenceProcessor.mConnectedBtDevices).isEmpty();
                    AssociationStore associationStore2 = devicePresenceProcessor.mAssociationStore;
                    if (isEmpty) {
                        printWriter.append("<empty>\n");
                    } else {
                        printWriter.append("\n");
                        Iterator it2 = ((HashSet) devicePresenceProcessor.mConnectedBtDevices).iterator();
                        while (it2.hasNext()) {
                            printWriter.append("    ").append((CharSequence) associationStore2.getAssociationById(((Integer) it2.next()).intValue()).toShortString()).append('\n');
                        }
                    }
                    printWriter.append("  Nearby BLE Devices: ");
                    if (((HashSet) devicePresenceProcessor.mNearbyBleDevices).isEmpty()) {
                        printWriter.append("<empty>\n");
                    } else {
                        printWriter.append("\n");
                        Iterator it3 = ((HashSet) devicePresenceProcessor.mNearbyBleDevices).iterator();
                        while (it3.hasNext()) {
                            printWriter.append("    ").append((CharSequence) associationStore2.getAssociationById(((Integer) it3.next()).intValue()).toShortString()).append('\n');
                        }
                    }
                    printWriter.append("  Self-Reported Devices: ");
                    if (((HashSet) devicePresenceProcessor.mReportedSelfManagedDevices).isEmpty()) {
                        printWriter.append("<empty>\n");
                    } else {
                        printWriter.append("\n");
                        Iterator it4 = ((HashSet) devicePresenceProcessor.mReportedSelfManagedDevices).iterator();
                        while (it4.hasNext()) {
                            printWriter.append("    ").append((CharSequence) associationStore2.getAssociationById(((Integer) it4.next()).intValue()).toShortString()).append('\n');
                        }
                    }
                }
                CompanionAppBinder companionAppBinder = CompanionDeviceManagerService.this.mCompanionAppBinder;
                companionAppBinder.getClass();
                printWriter.append("Companion Device Application Controller: \n");
                synchronized (companionAppBinder.mBoundCompanionApplications) {
                    try {
                        printWriter.append("  Bound Companion Applications: ");
                        if (((HashMap) companionAppBinder.mBoundCompanionApplications).isEmpty()) {
                            printWriter.append("<empty>\n");
                        } else {
                            printWriter.append("\n");
                            for (Map.Entry entry : ((HashMap) companionAppBinder.mBoundCompanionApplications).entrySet()) {
                                printWriter.append("<u").append((CharSequence) String.valueOf(((Pair) entry.getKey()).first)).append(", ").append((CharSequence) ((Pair) entry.getKey()).second).append(">");
                                Iterator it5 = ((List) entry.getValue()).iterator();
                                while (it5.hasNext()) {
                                    printWriter.append(", isPrimary=").append((CharSequence) String.valueOf(((CompanionServiceConnector) it5.next()).isPrimary()));
                                }
                            }
                        }
                    } finally {
                    }
                }
                printWriter.append("  Companion Applications Scheduled For Rebinding: ");
                synchronized (companionAppBinder.mScheduledForRebindingCompanionApplications) {
                    try {
                        if (((HashSet) companionAppBinder.mScheduledForRebindingCompanionApplications).isEmpty()) {
                            printWriter.append("<empty>\n");
                        } else {
                            printWriter.append("\n");
                            Iterator it6 = ((HashSet) companionAppBinder.mScheduledForRebindingCompanionApplications).iterator();
                            while (it6.hasNext()) {
                                Pair pair = (Pair) it6.next();
                                printWriter.append("<u").append((CharSequence) String.valueOf(pair.first)).append(", ").append((CharSequence) pair.second).append(">");
                            }
                        }
                    } finally {
                    }
                }
                CompanionTransportManager companionTransportManager = CompanionDeviceManagerService.this.mTransportManager;
                synchronized (companionTransportManager.mTransports) {
                    try {
                        printWriter.append("System Data Transports: ");
                        if (companionTransportManager.mTransports.size() == 0) {
                            printWriter.append("<empty>\n");
                        } else {
                            printWriter.append("\n");
                            for (int i2 = 0; i2 < companionTransportManager.mTransports.size(); i2++) {
                                printWriter.append("  ").append((CharSequence) ((Transport) companionTransportManager.mTransports.get(companionTransportManager.mTransports.keyAt(i2))).toString()).append('\n');
                            }
                        }
                    } finally {
                    }
                }
                SystemDataTransferRequestStore systemDataTransferRequestStore = CompanionDeviceManagerService.this.mSystemDataTransferRequestStore;
                synchronized (systemDataTransferRequestStore.mLock) {
                    try {
                        printWriter.append("System Data Transfer Requests (Cached): ");
                        if (systemDataTransferRequestStore.mCachedPerUser.size() == 0) {
                            printWriter.append("<empty>\n");
                        } else {
                            printWriter.append("\n");
                            for (i = 0; i < systemDataTransferRequestStore.mCachedPerUser.size(); i++) {
                                int keyAt = systemDataTransferRequestStore.mCachedPerUser.keyAt(i);
                                Iterator it7 = ((ArrayList) systemDataTransferRequestStore.mCachedPerUser.get(keyAt)).iterator();
                                while (it7.hasNext()) {
                                    printWriter.append("  u").append((CharSequence) String.valueOf(keyAt)).append(" -> ").append((CharSequence) ((SystemDataTransferRequest) it7.next()).toString()).append('\n');
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        public final void enablePermissionsSync(int i) {
            SystemDataTransferProcessor systemDataTransferProcessor = CompanionDeviceManagerService.this.mSystemDataTransferProcessor;
            int userId = systemDataTransferProcessor.mAssociationStore.getAssociationWithCallerChecks(i).getUserId();
            SystemDataTransferRequest permissionSyncRequest = new PermissionSyncRequest(i);
            permissionSyncRequest.setUserConsented(true);
            systemDataTransferProcessor.mSystemDataTransferRequestStore.writeRequest(userId, permissionSyncRequest);
        }

        public final void enableSecureTransport(boolean z) {
            enableSecureTransport_enforcePermission();
            CompanionDeviceManagerService.this.mTransportManager.mSecureTransportEnabled = z;
        }

        public final void enableSystemDataSync(int i, int i2) {
            AssociationStore associationStore = CompanionDeviceManagerService.this.mAssociationRequestsProcessor.mAssociationStore;
            AssociationInfo associationWithCallerChecks = associationStore.getAssociationWithCallerChecks(i);
            associationStore.updateAssociation(new AssociationInfo.Builder(associationWithCallerChecks).setSystemDataSyncFlags(associationWithCallerChecks.getSystemDataSyncFlags() | i2).build());
        }

        public final List getAllAssociationsForUser(int i) {
            getAllAssociationsForUser_enforcePermission();
            PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(CompanionDeviceManagerService.this.getContext(), i);
            return i == -1 ? CompanionDeviceManagerService.this.mAssociationStore.getActiveAssociations() : CompanionDeviceManagerService.this.mAssociationStore.getActiveAssociationsByUser(i);
        }

        public final List getAssociations(String str, int i) {
            PermissionsUtils.enforceCallerCanManageAssociationsForPackage(i, CompanionDeviceManagerService.this.getContext(), str, "get associations");
            return CompanionDeviceManagerService.this.mAssociationStore.getActiveAssociationsByPackage(i, str);
        }

        public final byte[] getBackupPayload(int i) {
            return CompanionDeviceManagerService.this.mBackupRestoreProcessor.getBackupPayload(i);
        }

        public final PermissionSyncRequest getPermissionSyncRequest(int i) {
            return CompanionDeviceManagerService.this.mSystemDataTransferProcessor.getPermissionSyncRequest(i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            CompanionDeviceManagerService companionDeviceManagerService = CompanionDeviceManagerService.this;
            return new CompanionDeviceShellCommand(companionDeviceManagerService, companionDeviceManagerService.mAssociationStore, companionDeviceManagerService.mDevicePresenceProcessor, companionDeviceManagerService.mTransportManager, companionDeviceManagerService.mSystemDataTransferProcessor, companionDeviceManagerService.mAssociationRequestsProcessor, companionDeviceManagerService.mBackupRestoreProcessor, companionDeviceManagerService.mDisassociationProcessor).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        public final boolean hasNotificationAccess(ComponentName componentName) {
            checkCanCallNotificationApi(UserHandle.getCallingUserId(), componentName.getPackageName());
            return ((NotificationManager) CompanionDeviceManagerService.this.getContext().getSystemService(NotificationManager.class)).isNotificationListenerAccessGranted(componentName);
        }

        public final boolean isCompanionApplicationBound(String str, int i) {
            return CompanionDeviceManagerService.this.mCompanionAppBinder.isCompanionApplicationBound(i, str);
        }

        public final boolean isDeviceAssociatedForWifiConnection(String str, final String str2, int i) {
            isDeviceAssociatedForWifiConnection_enforcePermission();
            if (CompanionDeviceManagerService.this.getContext().getPackageManager().checkPermission("android.permission.COMPANION_APPROVE_WIFI_CONNECTIONS", str) == 0) {
                return true;
            }
            return CollectionUtils.any(CompanionDeviceManagerService.this.mAssociationStore.getActiveAssociationsByPackage(i, str), new Predicate() { // from class: com.android.server.companion.CompanionDeviceManagerService$CompanionDeviceManagerImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((AssociationInfo) obj).isLinkedTo(str2);
                }
            });
        }

        public final boolean isPermissionTransferUserConsented(String str, int i, int i2) {
            SystemDataTransferProcessor systemDataTransferProcessor = CompanionDeviceManagerService.this.mSystemDataTransferProcessor;
            systemDataTransferProcessor.mAssociationStore.getAssociationWithCallerChecks(i2);
            PermissionSyncRequest permissionSyncRequest = systemDataTransferProcessor.getPermissionSyncRequest(i2);
            if (permissionSyncRequest == null) {
                return false;
            }
            return permissionSyncRequest.isUserConsented();
        }

        public final void legacyDisassociate(String str, String str2, int i) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(str2);
            DisassociationProcessor disassociationProcessor = CompanionDeviceManagerService.this.mDisassociationProcessor;
            AssociationStore associationStore = disassociationProcessor.mAssociationStore;
            AssociationInfo firstAssociationByAddress = associationStore.getFirstAssociationByAddress(i, str2, str);
            if (firstAssociationByAddress == null) {
                throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("Association for mac address=[", str, "] doesn't exist"));
            }
            associationStore.getAssociationWithCallerChecks(firstAssociationByAddress.getId());
            disassociationProcessor.disassociate(firstAssociationByAddress.getId());
        }

        public final void legacyStartObservingDevicePresence(String str, String str2, int i) {
            legacyStartObservingDevicePresence_enforcePermission();
            DevicePresenceProcessor devicePresenceProcessor = CompanionDeviceManagerService.this.mDevicePresenceProcessor;
            devicePresenceProcessor.getClass();
            Slog.i("CDM_DevicePresenceProcessor", "Start observing device=[" + str + "] for userId=[" + i + "], package=[" + str2 + "]...");
            PermissionsUtils.enforceCallerCanManageAssociationsForPackage(i, devicePresenceProcessor.mContext, str2, null);
            AssociationInfo firstAssociationByAddress = devicePresenceProcessor.mAssociationStore.getFirstAssociationByAddress(i, str2, str);
            if (firstAssociationByAddress != null) {
                devicePresenceProcessor.startObservingDevicePresence(new ObservingDevicePresenceRequest.Builder().setAssociationId(firstAssociationByAddress.getId()).build(), str2, i, true);
            } else {
                StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("App ", str2, " is not associated with device ", str, " for user ");
                m.append(i);
                throw new RemoteException(new DeviceNotAssociatedException(m.toString()));
            }
        }

        public final void legacyStopObservingDevicePresence(String str, String str2, int i) {
            legacyStopObservingDevicePresence_enforcePermission();
            DevicePresenceProcessor devicePresenceProcessor = CompanionDeviceManagerService.this.mDevicePresenceProcessor;
            devicePresenceProcessor.getClass();
            Slog.i("CDM_DevicePresenceProcessor", "Stop observing device=[" + str + "] for userId=[" + i + "], package=[" + str2 + "]...");
            PermissionsUtils.enforceCallerCanManageAssociationsForPackage(i, devicePresenceProcessor.mContext, str2, null);
            AssociationInfo firstAssociationByAddress = devicePresenceProcessor.mAssociationStore.getFirstAssociationByAddress(i, str2, str);
            if (firstAssociationByAddress != null) {
                devicePresenceProcessor.stopObservingDevicePresence(new ObservingDevicePresenceRequest.Builder().setAssociationId(firstAssociationByAddress.getId()).build(), str2, i, true);
            } else {
                StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("App ", str2, " is not associated with device ", str, " for user ");
                m.append(i);
                throw new RemoteException(new DeviceNotAssociatedException(m.toString()));
            }
        }

        public final void notifySelfManagedDeviceAppeared(int i) {
            notifySelfManagedDeviceAppeared_enforcePermission();
            CompanionDeviceManagerService.this.mDevicePresenceProcessor.notifySelfManagedDevicePresenceEvent(i, true);
        }

        public final void notifySelfManagedDeviceDisappeared(int i) {
            notifySelfManagedDeviceDisappeared_enforcePermission();
            CompanionDeviceManagerService.this.mDevicePresenceProcessor.notifySelfManagedDevicePresenceEvent(i, false);
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (Throwable th) {
                Slog.e("CDM_CompanionDeviceManagerService", "Error during IPC", th);
                throw ExceptionUtils.propagate(th, RemoteException.class);
            }
        }

        public final boolean removeBond(int i, String str, int i2) {
            removeBond_enforcePermission();
            DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "removeBond() associationId=", ", package=u", "/"), str, "CDM_CompanionDeviceManagerService");
            PermissionsUtils.enforceCallerCanManageAssociationsForPackage(i2, CompanionDeviceManagerService.this.getContext(), str, "remove bonds");
            MacAddress deviceMacAddress = CompanionDeviceManagerService.this.mAssociationStore.getAssociationWithCallerChecks(i).getDeviceMacAddress();
            if (deviceMacAddress != null) {
                return ((BluetoothManager) CompanionDeviceManagerService.this.getContext().getSystemService(BluetoothManager.class)).getAdapter().getRemoteDevice(deviceMacAddress.toString().toUpperCase()).removeBond();
            }
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Association id=[", "] doesn't have a device address."));
        }

        public final void removeOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) {
            removeOnAssociationsChangedListener_enforcePermission();
            PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(CompanionDeviceManagerService.this.getContext(), i);
            AssociationStore associationStore = CompanionDeviceManagerService.this.mAssociationStore;
            synchronized (associationStore.mRemoteListeners) {
                associationStore.mRemoteListeners.unregister(iOnAssociationsChangedListener);
            }
        }

        public final void removeOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) {
            removeOnMessageReceivedListener_enforcePermission();
            CompanionDeviceManagerService.this.mTransportManager.mMessageListeners.remove(i);
        }

        public final void removeOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) {
            removeOnTransportsChangedListener_enforcePermission();
            CompanionTransportManager companionTransportManager = CompanionDeviceManagerService.this.mTransportManager;
            synchronized (companionTransportManager.mTransportsListeners) {
                companionTransportManager.mTransportsListeners.unregister(iOnTransportsChangedListener);
            }
        }

        public final PendingIntent requestNotificationAccess(final ComponentName componentName, final int i) {
            final int callingUid = ICompanionDeviceManager.Stub.getCallingUid();
            final String packageName = componentName.getPackageName();
            checkCanCallNotificationApi(i, packageName);
            if (componentName.flattenToString().length() <= 500) {
                return (PendingIntent) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.companion.CompanionDeviceManagerService$CompanionDeviceManagerImpl$$ExternalSyntheticLambda0
                    public final Object getOrThrow() {
                        Intent launcherIntent;
                        CompanionDeviceManagerService.CompanionDeviceManagerImpl companionDeviceManagerImpl = CompanionDeviceManagerService.CompanionDeviceManagerImpl.this;
                        String str = packageName;
                        int i2 = callingUid;
                        int i3 = i;
                        ComponentName componentName2 = componentName;
                        Context context = CompanionDeviceManagerService.this.getContext();
                        Intent intent = PackageUtils.COMPANION_SERVICE_INTENT;
                        boolean z = true;
                        if (Flags.enhancedConfirmationModeApisEnabled()) {
                            try {
                                z = true ^ ((EnhancedConfirmationManager) context.getSystemService(EnhancedConfirmationManager.class)).isRestricted(str, "android:access_notifications");
                            } catch (PackageManager.NameNotFoundException unused) {
                            }
                        } else {
                            int noteOpNoThrow = ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteOpNoThrow(119, i2, str, (String) null, (String) null);
                            if (noteOpNoThrow != 0 && noteOpNoThrow != 3) {
                                z = false;
                            }
                        }
                        if (z) {
                            launcherIntent = NotificationAccessConfirmationActivityContract.launcherIntent(CompanionDeviceManagerService.this.getContext(), i3, componentName2);
                        } else {
                            Slog.e("CDM_CompanionDeviceManagerService", "Side loaded app must enable restricted setting before request the notification access");
                            if (!Flags.enhancedConfirmationModeApisEnabled()) {
                                return null;
                            }
                            launcherIntent = ((EnhancedConfirmationManager) CompanionDeviceManagerService.this.getContext().getSystemService(EnhancedConfirmationManager.class)).createRestrictedSettingDialogIntent(str, "android:access_notifications");
                        }
                        return PendingIntent.getActivityAsUser(CompanionDeviceManagerService.this.getContext(), 0, launcherIntent, 1409286144, null, new UserHandle(i3));
                    }
                });
            }
            throw new IllegalArgumentException("Component name is too long.");
        }

        public final void sendMessage(int i, byte[] bArr, int[] iArr) {
            sendMessage_enforcePermission();
            CompanionDeviceManagerService.this.mTransportManager.sendMessage(i, bArr, iArr);
        }

        public final void setAssociationTag(int i, String str) {
            AssociationRequestsProcessor associationRequestsProcessor = CompanionDeviceManagerService.this.mAssociationRequestsProcessor;
            associationRequestsProcessor.getClass();
            StringBuilder sb = new StringBuilder("Setting association tag=[");
            sb.append(str);
            sb.append("] to id=[");
            sb.append(i);
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, "]...", "CDM_AssociationRequestsProcessor");
            AssociationStore associationStore = associationRequestsProcessor.mAssociationStore;
            associationStore.updateAssociation(new AssociationInfo.Builder(associationStore.getAssociationWithCallerChecks(i)).setTag(str).build());
        }

        public final void startObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) {
            startObservingDevicePresence_enforcePermission();
            CompanionDeviceManagerService.this.mDevicePresenceProcessor.startObservingDevicePresence(observingDevicePresenceRequest, str, i, true);
        }

        public final void startSystemDataTransfer(String str, final int i, final int i2, final ISystemDataTransferCallback iSystemDataTransferCallback) {
            final SystemDataTransferProcessor systemDataTransferProcessor = CompanionDeviceManagerService.this.mSystemDataTransferProcessor;
            systemDataTransferProcessor.getClass();
            Slog.i("CDM_SystemDataTransferProcessor", "Start system data transfer for package [" + str + "] userId [" + i + "] associationId [" + i2 + "]");
            systemDataTransferProcessor.mAssociationStore.getAssociationWithCallerChecks(i2);
            PermissionSyncRequest permissionSyncRequest = systemDataTransferProcessor.getPermissionSyncRequest(i2);
            if (permissionSyncRequest != null && permissionSyncRequest.isUserConsented()) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor$$ExternalSyntheticLambda2
                    public final void runOrThrow() {
                        final SystemDataTransferProcessor systemDataTransferProcessor2 = SystemDataTransferProcessor.this;
                        int i3 = i;
                        final int i4 = i2;
                        final ISystemDataTransferCallback iSystemDataTransferCallback2 = iSystemDataTransferCallback;
                        systemDataTransferProcessor2.mPermissionControllerManager.getRuntimePermissionBackup(UserHandle.of(i3), systemDataTransferProcessor2.mExecutor, new Consumer() { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Future failedFuture;
                                SystemDataTransferProcessor systemDataTransferProcessor3 = SystemDataTransferProcessor.this;
                                int i5 = i4;
                                ISystemDataTransferCallback iSystemDataTransferCallback3 = iSystemDataTransferCallback2;
                                byte[] bArr = (byte[]) obj;
                                CompanionTransportManager companionTransportManager = systemDataTransferProcessor3.mTransportManager;
                                synchronized (companionTransportManager.mTransports) {
                                    try {
                                        Transport transport = (Transport) companionTransportManager.mTransports.get(i5);
                                        failedFuture = transport == null ? CompletableFuture.failedFuture(new IOException("Missing transport")) : transport.sendMessage(1669491075, bArr);
                                    } finally {
                                    }
                                }
                                try {
                                    try {
                                        failedFuture.get(15L, TimeUnit.SECONDS);
                                        if (iSystemDataTransferCallback3 != null) {
                                            iSystemDataTransferCallback3.onResult();
                                        }
                                    } catch (Exception e) {
                                        if (iSystemDataTransferCallback3 != null) {
                                            iSystemDataTransferCallback3.onError(e.getMessage());
                                        }
                                    }
                                } catch (RemoteException unused) {
                                }
                            }
                        });
                    }
                });
                return;
            }
            String m = DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "User ", " hasn't consented permission sync for associationId [", ".");
            Slog.e("CDM_SystemDataTransferProcessor", m);
            try {
                iSystemDataTransferCallback.onError(m);
            } catch (RemoteException unused) {
            }
        }

        public final void stopObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) {
            stopObservingDevicePresence_enforcePermission();
            CompanionDeviceManagerService.this.mDevicePresenceProcessor.stopObservingDevicePresence(observingDevicePresenceRequest, str, i, true);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {
        }

        public final void registerCallMetadataSyncCallback(CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback, int i) {
            if (CompanionDeviceConfig.isEnabled()) {
                CrossDeviceSyncController crossDeviceSyncController = CompanionDeviceManagerService.this.mCrossDeviceSyncController;
                crossDeviceSyncController.getClass();
                if (i != 2) {
                    if (i == 1) {
                        crossDeviceSyncController.mConnectionServiceCallbackRef = new WeakReference(crossDeviceSyncControllerCallback);
                        return;
                    } else {
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "Cannot register callback of unknown type: ", "CrossDeviceSyncController");
                        return;
                    }
                }
                crossDeviceSyncController.mInCallServiceCallbackRef = new WeakReference(crossDeviceSyncControllerCallback);
                Iterator it = ((ArrayList) crossDeviceSyncController.mConnectedAssociations).iterator();
                while (it.hasNext()) {
                    AssociationInfo associationInfo = (AssociationInfo) it.next();
                    if (CrossDeviceSyncController.isAssociationBlocked(associationInfo)) {
                        ((HashSet) crossDeviceSyncController.mBlocklist).add(Integer.valueOf(associationInfo.getId()));
                    } else {
                        ((HashSet) crossDeviceSyncController.mBlocklist).remove(Integer.valueOf(associationInfo.getId()));
                        crossDeviceSyncControllerCallback.updateNumberOfActiveSyncAssociations(associationInfo.getUserId(), true);
                        crossDeviceSyncControllerCallback.requestCrossDeviceSync(associationInfo);
                    }
                }
            }
        }

        public final void sendCrossDeviceSyncMessageToAllDevices(int i, byte[] bArr) {
            if (CompanionDeviceConfig.isEnabled()) {
                CrossDeviceSyncController crossDeviceSyncController = CompanionDeviceManagerService.this.mCrossDeviceSyncController;
                crossDeviceSyncController.getClass();
                HashSet hashSet = new HashSet();
                Iterator it = ((ArrayList) crossDeviceSyncController.mConnectedAssociations).iterator();
                while (it.hasNext()) {
                    AssociationInfo associationInfo = (AssociationInfo) it.next();
                    if (associationInfo.getUserId() == i && !CrossDeviceSyncController.isAssociationBlocked(associationInfo)) {
                        hashSet.add(Integer.valueOf(associationInfo.getId()));
                    }
                }
                if (hashSet.isEmpty()) {
                    Slog.w("CrossDeviceSyncController", "No eligible devices to sync to");
                } else {
                    crossDeviceSyncController.mCompanionTransportManager.sendMessage(1667729539, bArr, hashSet.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray());
                }
            }
        }
    }

    /* renamed from: -$$Nest$monPackageRemoveOrDataClearedInternal, reason: not valid java name */
    public static void m344$$Nest$monPackageRemoveOrDataClearedInternal(CompanionDeviceManagerService companionDeviceManagerService, int i, String str) {
        List associationsByPackage = companionDeviceManagerService.mAssociationStore.getAssociationsByPackage(i, str);
        if (!associationsByPackage.isEmpty()) {
            Slog.i("CDM_CompanionDeviceManagerService", AccountManagerService$$ExternalSyntheticOutline0.m(i, "Package removed or data cleared for user=[", "], package=[", str, "]. Cleaning up CDM data..."));
        }
        Iterator it = associationsByPackage.iterator();
        while (it.hasNext()) {
            companionDeviceManagerService.mDisassociationProcessor.disassociate(((AssociationInfo) it.next()).getId());
        }
        ObservableUuidStore observableUuidStore = companionDeviceManagerService.mObservableUuidStore;
        Iterator it2 = ((ArrayList) observableUuidStore.getObservableUuidsForPackage(i, str)).iterator();
        while (it2.hasNext()) {
            observableUuidStore.removeObservableUuid(i, ((ObservableUuid) it2.next()).mUuid, str);
        }
        CompanionAppBinder.CompanionServicesRegister companionServicesRegister = companionDeviceManagerService.mCompanionAppBinder.mCompanionServicesRegister;
        synchronized (companionServicesRegister) {
            companionServicesRegister.remove(i);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.companion.CompanionDeviceManagerService$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.companion.CompanionDeviceManagerService$2] */
    public CompanionDeviceManagerService(Context context) {
        super(context);
        this.mAssociationStoreChangeListener = new AssociationStore.OnChangeListener() { // from class: com.android.server.companion.CompanionDeviceManagerService.1
            @Override // com.android.server.companion.association.AssociationStore.OnChangeListener
            public final void onAssociationChanged(int i, AssociationInfo associationInfo) {
                Slog.d("CDM_CompanionDeviceManagerService", "onAssociationChanged changeType=[" + i + "], association=[" + associationInfo);
                int userId = associationInfo.getUserId();
                CompanionDeviceManagerService companionDeviceManagerService = CompanionDeviceManagerService.this;
                companionDeviceManagerService.updateAtm(userId, companionDeviceManagerService.mAssociationStore.getActiveAssociationsByUser(userId));
                int userId2 = associationInfo.getUserId();
                String packageName = associationInfo.getPackageName();
                Context context2 = companionDeviceManagerService.getContext();
                Intent intent = PackageUtils.COMPANION_SERVICE_INTENT;
                Binder.withCleanCallingIdentity(new CompanionDeviceManagerService$$ExternalSyntheticLambda1(companionDeviceManagerService, (PackageInfo) Binder.withCleanCallingIdentity(new PackageUtils$$ExternalSyntheticLambda1(context2.getPackageManager(), packageName, PackageManager.PackageInfoFlags.of(20480L), userId2))));
            }
        };
        this.mPackageMonitor = new PackageMonitor() { // from class: com.android.server.companion.CompanionDeviceManagerService.2
            public final void onPackageAdded(String str, int i) {
                List<AssociationInfo> filter;
                CompanionDeviceManagerService companionDeviceManagerService = CompanionDeviceManagerService.this;
                int changingUserId = getChangingUserId();
                final BackupRestoreProcessor backupRestoreProcessor = companionDeviceManagerService.mBackupRestoreProcessor;
                AssociationStore associationStore = backupRestoreProcessor.mAssociationStore;
                synchronized (associationStore.mLock) {
                    filter = CollectionUtils.filter(associationStore.getAssociations(), new AssociationStore$$ExternalSyntheticLambda7(changingUserId, 0, str));
                }
                if (!filter.isEmpty()) {
                    BootReceiver$$ExternalSyntheticOutline0.m58m("Found pending associations for package=[", str, "]. Restoring...", "CDM_BackupRestoreProcessor");
                }
                for (final AssociationInfo associationInfo : filter) {
                    final AssociationInfo build = new AssociationInfo.Builder(associationInfo).setPending(false).build();
                    RolesUtils.addRoleHolderForAssociation(backupRestoreProcessor.mContext, build, new Consumer() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            BackupRestoreProcessor backupRestoreProcessor2 = BackupRestoreProcessor.this;
                            AssociationInfo associationInfo2 = build;
                            AssociationInfo associationInfo3 = associationInfo;
                            backupRestoreProcessor2.getClass();
                            if (!((Boolean) obj).booleanValue()) {
                                Slog.e("CDM_BackupRestoreProcessor", "Failed to restore association=[" + associationInfo3 + "].");
                                return;
                            }
                            backupRestoreProcessor2.mAssociationStore.updateAssociation(associationInfo2);
                            Slog.i("CDM_BackupRestoreProcessor", "Association=[" + associationInfo3 + "] is restored.");
                        }
                    });
                }
            }

            public final void onPackageDataCleared(String str, int i) {
                CompanionDeviceManagerService.m344$$Nest$monPackageRemoveOrDataClearedInternal(CompanionDeviceManagerService.this, getChangingUserId(), str);
            }

            public final void onPackageModified(String str) {
                CompanionDeviceManagerService companionDeviceManagerService = CompanionDeviceManagerService.this;
                int changingUserId = getChangingUserId();
                for (AssociationInfo associationInfo : companionDeviceManagerService.mAssociationStore.getAssociationsByPackage(changingUserId, str)) {
                    int userId = associationInfo.getUserId();
                    String packageName = associationInfo.getPackageName();
                    Context context2 = companionDeviceManagerService.getContext();
                    Intent intent = PackageUtils.COMPANION_SERVICE_INTENT;
                    Binder.withCleanCallingIdentity(new CompanionDeviceManagerService$$ExternalSyntheticLambda1(companionDeviceManagerService, (PackageInfo) Binder.withCleanCallingIdentity(new PackageUtils$$ExternalSyntheticLambda1(context2.getPackageManager(), packageName, PackageManager.PackageInfoFlags.of(20480L), userId))));
                }
                CompanionAppBinder.CompanionServicesRegister companionServicesRegister = companionDeviceManagerService.mCompanionAppBinder.mCompanionServicesRegister;
                synchronized (companionServicesRegister) {
                    companionServicesRegister.remove(changingUserId);
                }
            }

            public final void onPackageRemoved(String str, int i) {
                CompanionDeviceManagerService.m344$$Nest$monPackageRemoveOrDataClearedInternal(CompanionDeviceManagerService.this, getChangingUserId(), str);
            }
        };
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
        this.mPowerExemptionManager = (PowerExemptionManager) context.getSystemService(PowerExemptionManager.class);
        this.mAppOpsManager = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
        this.mAtmInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mAmInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mPackageManagerInternal = packageManagerInternal;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        PowerManagerInternal powerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        AssociationDiskStore associationDiskStore = new AssociationDiskStore();
        AssociationStore associationStore = new AssociationStore(context, userManager, associationDiskStore);
        this.mAssociationStore = associationStore;
        SystemDataTransferRequestStore systemDataTransferRequestStore = new SystemDataTransferRequestStore();
        this.mSystemDataTransferRequestStore = systemDataTransferRequestStore;
        ObservableUuidStore observableUuidStore = new ObservableUuidStore();
        this.mObservableUuidStore = observableUuidStore;
        AssociationRequestsProcessor associationRequestsProcessor = new AssociationRequestsProcessor(context, packageManagerInternal, associationStore);
        this.mAssociationRequestsProcessor = associationRequestsProcessor;
        this.mBackupRestoreProcessor = new BackupRestoreProcessor(context, packageManagerInternal, associationStore, associationDiskStore, systemDataTransferRequestStore, associationRequestsProcessor);
        CompanionAppBinder companionAppBinder = new CompanionAppBinder(context);
        this.mCompanionAppBinder = companionAppBinder;
        DevicePresenceProcessor devicePresenceProcessor = new DevicePresenceProcessor(context, companionAppBinder, userManager, associationStore, observableUuidStore, powerManagerInternal);
        this.mDevicePresenceProcessor = devicePresenceProcessor;
        CompanionTransportManager companionTransportManager = new CompanionTransportManager(context, associationStore);
        this.mTransportManager = companionTransportManager;
        this.mDisassociationProcessor = new DisassociationProcessor(context, activityManager, associationStore, packageManagerInternal, devicePresenceProcessor, companionAppBinder, systemDataTransferRequestStore, companionTransportManager);
        this.mSystemDataTransferProcessor = new SystemDataTransferProcessor(this, packageManagerInternal, associationStore, systemDataTransferRequestStore, companionTransportManager);
        this.mCrossDeviceSyncController = new CrossDeviceSyncController(getContext(), companionTransportManager);
    }

    public final void exemptFromAutoRevoke(int i, String str) {
        try {
            this.mAppOpsManager.setMode(97, i, str, 1);
        } catch (RemoteException e) {
            Slog.w("CDM_CompanionDeviceManagerService", "Error while granting auto revoke exemption for " + str, e);
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        PhoneAccountHandle defaultOutgoingPhoneAccount;
        PhoneAccount phoneAccount;
        Context context = getContext();
        if (i != 500) {
            if (i == 1000) {
                Context context2 = getContext();
                int i2 = InactiveAssociationsRemovalService.$r8$clinit;
                Slog.i("CDM_InactiveAssociationsRemovalService", "Scheduling the Association Removal job");
                ((JobScheduler) context2.getSystemService(JobScheduler.class)).forNamespace("companion").schedule(new JobInfo.Builder(1, new ComponentName(context2, (Class<?>) InactiveAssociationsRemovalService.class)).setRequiresCharging(true).setRequiresDeviceIdle(true).setPeriodic(InactiveAssociationsRemovalService.ONE_DAY_INTERVAL).build());
                CrossDeviceSyncController crossDeviceSyncController = this.mCrossDeviceSyncController;
                crossDeviceSyncController.getClass();
                if (CompanionDeviceConfig.isEnabled()) {
                    crossDeviceSyncController.mPhoneAccountManager.mTelecomManager.clearPhoneAccounts();
                    TelecomManager telecomManager = (TelecomManager) crossDeviceSyncController.mContext.getSystemService(TelecomManager.class);
                    if (telecomManager == null || telecomManager.getCallCapablePhoneAccounts().size() == 0 || (defaultOutgoingPhoneAccount = telecomManager.getDefaultOutgoingPhoneAccount("tel")) == null || (phoneAccount = telecomManager.getPhoneAccount(defaultOutgoingPhoneAccount)) == null || phoneAccount.getLabel() == null) {
                        return;
                    }
                    ((ArrayList) crossDeviceSyncController.mCallFacilitators).add(new CallMetadataSyncData.CallFacilitator(phoneAccount.getLabel().toString(), "system", "system"));
                    return;
                }
                return;
            }
            return;
        }
        register(context, FgThread.get().getLooper(), UserHandle.ALL, true);
        DevicePresenceProcessor devicePresenceProcessor = this.mDevicePresenceProcessor;
        devicePresenceProcessor.getClass();
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth");
        if (bluetoothManager == null) {
            Slog.w("CDM_DevicePresenceProcessor", "BluetoothManager is not available.");
            return;
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (adapter == null) {
            Slog.w("CDM_DevicePresenceProcessor", "BluetoothAdapter is NOT available.");
            return;
        }
        BluetoothDeviceProcessor bluetoothDeviceProcessor = devicePresenceProcessor.mBluetoothDeviceProcessor;
        bluetoothDeviceProcessor.getClass();
        adapter.registerBluetoothConnectionCallback(new HandlerExecutor(Handler.getMain()), bluetoothDeviceProcessor);
        bluetoothDeviceProcessor.mAssociationStore.registerLocalListener(bluetoothDeviceProcessor);
        BleDeviceProcessor bleDeviceProcessor = devicePresenceProcessor.mBleDeviceProcessor;
        if (bleDeviceProcessor.mBtAdapter != null) {
            throw new IllegalStateException("BleDeviceProcessor is already initialized");
        }
        bleDeviceProcessor.mBtAdapter = adapter;
        bleDeviceProcessor.checkBleState();
        BleDeviceProcessor.AnonymousClass1 anonymousClass1 = bleDeviceProcessor.new AnonymousClass1();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.BLE_STATE_CHANGED");
        context.registerReceiver(anonymousClass1, intentFilter);
        bleDeviceProcessor.mAssociationStore.registerLocalListener(bleDeviceProcessor);
        devicePresenceProcessor.mAssociationStore.registerLocalListener(devicePresenceProcessor);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        AssociationStore associationStore = this.mAssociationStore;
        associationStore.getClass();
        Binder.withCleanCallingIdentity(new AssociationStore$$ExternalSyntheticLambda3(associationStore));
        associationStore.registerLocalListener(this.mAssociationStoreChangeListener);
        int userId = getContext().getUserId();
        ObservableUuidStore observableUuidStore = this.mObservableUuidStore;
        synchronized (observableUuidStore.mLock) {
            observableUuidStore.readObservableUuidsFromCache(userId);
        }
        publishBinderService("companiondevice", new CompanionDeviceManagerImpl());
        LocalServices.addService(LocalService.class, new LocalService());
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocked(SystemService.TargetUser targetUser) {
        Slog.i("CDM_CompanionDeviceManagerService", "onUserUnlocked() user=" + targetUser);
        this.mDevicePresenceProcessor.sendDevicePresenceEventOnUnlocked(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        Slog.d("CDM_CompanionDeviceManagerService", "onUserUnlocking...");
        int userIdentifier = targetUser.getUserIdentifier();
        List activeAssociationsByUser = this.mAssociationStore.getActiveAssociationsByUser(userIdentifier);
        if (activeAssociationsByUser.isEmpty()) {
            return;
        }
        updateAtm(userIdentifier, activeAssociationsByUser);
        BackgroundThread.getHandler().sendMessageDelayed(PooledLambda.obtainMessage(new CompanionDeviceManagerService$$ExternalSyntheticLambda0(), this), TimeUnit.MINUTES.toMillis(10L));
    }

    public final void updateAtm(int i, List list) {
        ArraySet arraySet = new ArraySet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int packageUid = this.mPackageManagerInternal.getPackageUid(((AssociationInfo) it.next()).getPackageName(), 0L, i);
            if (packageUid >= 0) {
                arraySet.add(Integer.valueOf(packageUid));
            }
        }
        ActivityTaskManagerInternal activityTaskManagerInternal = this.mAtmInternal;
        if (activityTaskManagerInternal != null) {
            ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ((ArrayMap) ActivityTaskManagerService.this.mCompanionAppUidsMap).put(Integer.valueOf(i), arraySet);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
        ActivityManagerInternal activityManagerInternal = this.mAmInternal;
        if (activityManagerInternal != null) {
            activityManagerInternal.setCompanionAppUids(i, new ArraySet((Collection) arraySet));
        }
    }
}
