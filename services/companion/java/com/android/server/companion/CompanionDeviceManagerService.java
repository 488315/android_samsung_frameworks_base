package com.android.server.companion;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.companion.AssociationInfo;
import android.companion.AssociationRequest;
import android.companion.DeviceNotAssociatedException;
import android.companion.IAssociationRequestCallback;
import android.companion.ICompanionDeviceManager;
import android.companion.IOnAssociationsChangedListener;
import android.companion.IOnMessageReceivedListener;
import android.companion.IOnTransportsChangedListener;
import android.companion.ISystemDataTransferCallback;
import android.companion.utils.FeatureUtils;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.net.MacAddress;
import android.net.NetworkPolicyManager;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PowerWhitelistManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.app.IAppOpsService;
import com.android.internal.content.PackageMonitor;
import com.android.internal.infra.PerUser;
import com.android.internal.notification.NotificationAccessConfirmationActivityContract;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.companion.AssociationStore;
import com.android.server.companion.CompanionDeviceManagerService;
import com.android.server.companion.datatransfer.SystemDataTransferProcessor;
import com.android.server.companion.datatransfer.SystemDataTransferRequestStore;
import com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController;
import com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback;
import com.android.server.companion.presence.CompanionDevicePresenceMonitor;
import com.android.server.companion.transport.CompanionTransportManager;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CompanionDeviceManagerService extends SystemService {
    public static final long ASSOCIATION_REMOVAL_TIME_WINDOW_DEFAULT = TimeUnit.DAYS.toMillis(90);
    public final ActivityManager mActivityManager;
    public final ActivityManagerInternal mAmInternal;
    public final IAppOpsService mAppOpsManager;
    public AssociationRequestsProcessor mAssociationRequestsProcessor;
    public final AssociationStoreImpl mAssociationStore;
    public final AssociationStore.OnChangeListener mAssociationStoreChangeListener;
    public final ActivityTaskManagerInternal mAtmInternal;
    public CompanionApplicationController mCompanionAppController;
    public CrossDeviceSyncController mCrossDeviceSyncController;
    public final CompanionDevicePresenceMonitor.Callback mDevicePresenceCallback;
    public CompanionDevicePresenceMonitor mDevicePresenceMonitor;
    public final CompanionDeviceManagerImpl mImpl;
    public final RemoteCallbackList mListeners;
    public final OnPackageVisibilityChangeListener mOnPackageVisibilityChangeListener;
    public final PackageManagerInternal mPackageManagerInternal;
    public final PackageMonitor mPackageMonitor;
    public PersistentDataStore mPersistentStore;
    public final PowerWhitelistManager mPowerWhitelistManager;
    public final SparseArray mPreviouslyUsedIds;
    public final PerUserAssociationSet mRevokedAssociationsPendingRoleHolderRemoval;
    public SystemDataTransferProcessor mSystemDataTransferProcessor;
    public final SystemDataTransferRequestStore mSystemDataTransferRequestStore;
    public CompanionTransportManager mTransportManager;
    public final Map mUidsPendingRoleHolderRemoval;
    public final UserManager mUserManager;
    public final PersistUserStateHandler mUserPersistenceHandler;

    public static int getFirstAssociationIdForUser(int i) {
        return (i * 100000) + 1;
    }

    public static int getLastAssociationIdForUser(int i) {
        return (i + 1) * 100000;
    }

    public CompanionDeviceManagerService(Context context) {
        super(context);
        this.mPreviouslyUsedIds = new SparseArray();
        this.mRevokedAssociationsPendingRoleHolderRemoval = new PerUserAssociationSet();
        this.mUidsPendingRoleHolderRemoval = new HashMap();
        this.mListeners = new RemoteCallbackList();
        this.mAssociationStoreChangeListener = new AssociationStore.OnChangeListener() { // from class: com.android.server.companion.CompanionDeviceManagerService.1
            @Override // com.android.server.companion.AssociationStore.OnChangeListener
            public void onAssociationChanged(int i, AssociationInfo associationInfo) {
                CompanionDeviceManagerService.this.onAssociationChangedInternal(i, associationInfo);
            }
        };
        this.mDevicePresenceCallback = new CompanionDevicePresenceMonitor.Callback() { // from class: com.android.server.companion.CompanionDeviceManagerService.2
            @Override // com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback
            public void onDeviceAppeared(int i) {
                CompanionDeviceManagerService.this.onDeviceAppearedInternal(i);
            }

            @Override // com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback
            public void onDeviceDisappeared(int i) {
                CompanionDeviceManagerService.this.onDeviceDisappearedInternal(i);
            }
        };
        this.mPackageMonitor = new PackageMonitor() { // from class: com.android.server.companion.CompanionDeviceManagerService.3
            public void onPackageRemoved(String str, int i) {
                CompanionDeviceManagerService.this.onPackageRemoveOrDataClearedInternal(getChangingUserId(), str);
            }

            public void onPackageDataCleared(String str, int i) {
                CompanionDeviceManagerService.this.onPackageRemoveOrDataClearedInternal(getChangingUserId(), str);
            }

            public void onPackageModified(String str) {
                CompanionDeviceManagerService.this.onPackageModifiedInternal(getChangingUserId(), str);
            }
        };
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
        this.mActivityManager = activityManager;
        this.mImpl = new CompanionDeviceManagerImpl();
        this.mPowerWhitelistManager = (PowerWhitelistManager) context.getSystemService(PowerWhitelistManager.class);
        this.mAppOpsManager = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
        this.mAtmInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mAmInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserPersistenceHandler = new PersistUserStateHandler();
        this.mAssociationStore = new AssociationStoreImpl();
        this.mSystemDataTransferRequestStore = new SystemDataTransferRequestStore();
        this.mOnPackageVisibilityChangeListener = new OnPackageVisibilityChangeListener(activityManager);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Context context = getContext();
        this.mPersistentStore = new PersistentDataStore();
        loadAssociationsFromDisk();
        this.mAssociationStore.registerListener(this.mAssociationStoreChangeListener);
        this.mDevicePresenceMonitor = new CompanionDevicePresenceMonitor(this.mUserManager, this.mAssociationStore, this.mDevicePresenceCallback);
        this.mAssociationRequestsProcessor = new AssociationRequestsProcessor(this, this.mAssociationStore);
        this.mCompanionAppController = new CompanionApplicationController(context, this.mAssociationStore, this.mDevicePresenceMonitor);
        this.mTransportManager = new CompanionTransportManager(context, this.mAssociationStore);
        this.mSystemDataTransferProcessor = new SystemDataTransferProcessor(this, this.mAssociationStore, this.mSystemDataTransferRequestStore, this.mTransportManager);
        this.mCrossDeviceSyncController = new CrossDeviceSyncController(getContext(), this.mTransportManager);
        publishBinderService("companiondevice", this.mImpl);
        LocalServices.addService(CompanionDeviceManagerServiceInternal.class, new LocalService());
    }

    public void loadAssociationsFromDisk() {
        ArraySet<AssociationInfo> arraySet = new ArraySet();
        synchronized (this.mPreviouslyUsedIds) {
            this.mPersistentStore.readStateForUsers(this.mUserManager.getAliveUsers(), arraySet, this.mPreviouslyUsedIds);
        }
        ArraySet arraySet2 = new ArraySet(arraySet.size());
        ArraySet arraySet3 = new ArraySet();
        for (AssociationInfo associationInfo : arraySet) {
            if (!associationInfo.isRevoked()) {
                arraySet2.add(associationInfo);
            } else if (maybeRemoveRoleHolderForAssociation(associationInfo)) {
                arraySet3.add(Integer.valueOf(associationInfo.getUserId()));
            } else {
                addToPendingRoleHolderRemoval(associationInfo);
            }
        }
        this.mAssociationStore.setAssociations(arraySet2);
        Iterator it = arraySet3.iterator();
        while (it.hasNext()) {
            persistStateForUser(((Integer) it.next()).intValue());
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        Context context = getContext();
        if (i == 500) {
            this.mPackageMonitor.register(context, FgThread.get().getLooper(), UserHandle.ALL, true);
            this.mDevicePresenceMonitor.init(context);
        } else if (i == 1000) {
            InactiveAssociationsRemovalService.schedule(getContext());
            this.mCrossDeviceSyncController.onBootCompleted();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        List associationsForUser = this.mAssociationStore.getAssociationsForUser(userIdentifier);
        if (associationsForUser.isEmpty()) {
            return;
        }
        updateAtm(userIdentifier, associationsForUser);
        BackgroundThread.getHandler().sendMessageDelayed(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CompanionDeviceManagerService) obj).maybeGrantAutoRevokeExemptions();
            }
        }, this), TimeUnit.MINUTES.toMillis(10L));
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(SystemService.TargetUser targetUser) {
        Set set = (Set) this.mDevicePresenceMonitor.getPendingConnectedDevices().get(targetUser.getUserIdentifier());
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                for (AssociationInfo associationInfo : this.mAssociationStore.getAssociationsByAddress(((BluetoothDevice) it.next()).getAddress())) {
                    Slog.i("CDM_CompanionDeviceManagerService", "onUserUnlocked, device id( " + associationInfo.getId() + " ) is connected");
                    this.mDevicePresenceMonitor.onBluetoothCompanionDeviceConnected(associationInfo.getId());
                }
            }
        }
    }

    public AssociationInfo getAssociationWithCallerChecks(int i, String str, String str2) {
        AssociationInfo sanitizeWithCallerChecks = PermissionsUtils.sanitizeWithCallerChecks(getContext(), this.mAssociationStore.getAssociationsForPackageWithAddress(i, str, str2));
        if (sanitizeWithCallerChecks != null) {
            return sanitizeWithCallerChecks;
        }
        throw new IllegalArgumentException("Association does not exist or the caller does not have permissions to manage it (ie. it belongs to a different package or a different user).");
    }

    public AssociationInfo getAssociationWithCallerChecks(int i) {
        AssociationInfo sanitizeWithCallerChecks = PermissionsUtils.sanitizeWithCallerChecks(getContext(), this.mAssociationStore.getAssociationById(i));
        if (sanitizeWithCallerChecks != null) {
            return sanitizeWithCallerChecks;
        }
        throw new IllegalArgumentException("Association does not exist or the caller does not have permissions to manage it (ie. it belongs to a different package or a different user).");
    }

    public final void onDeviceAppearedInternal(int i) {
        AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        if (associationById.shouldBindWhenPresent()) {
            int userId = associationById.getUserId();
            String packageName = associationById.getPackageName();
            boolean isSelfManaged = associationById.isSelfManaged();
            if (!this.mCompanionAppController.isCompanionApplicationBound(userId, packageName)) {
                this.mCompanionAppController.bindCompanionApplication(userId, packageName, isSelfManaged);
            }
            this.mCompanionAppController.notifyCompanionApplicationDeviceAppeared(associationById);
        }
    }

    public final void onDeviceDisappearedInternal(int i) {
        AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        int userId = associationById.getUserId();
        String packageName = associationById.getPackageName();
        if (this.mCompanionAppController.isCompanionApplicationBound(userId, packageName)) {
            if (associationById.shouldBindWhenPresent()) {
                this.mCompanionAppController.notifyCompanionApplicationDeviceDisappeared(associationById);
            }
            if (shouldBindPackage(userId, packageName)) {
                return;
            }
            this.mCompanionAppController.unbindCompanionApplication(userId, packageName);
        }
    }

    public final boolean shouldBindPackage(int i, String str) {
        for (AssociationInfo associationInfo : this.mAssociationStore.getAssociationsForPackage(i, str)) {
            if (associationInfo.shouldBindWhenPresent() && this.mDevicePresenceMonitor.isDevicePresent(associationInfo.getId())) {
                return true;
            }
        }
        return false;
    }

    public final void onAssociationChangedInternal(int i, AssociationInfo associationInfo) {
        int id = associationInfo.getId();
        int userId = associationInfo.getUserId();
        String packageName = associationInfo.getPackageName();
        if (i == 1) {
            markIdAsPreviouslyUsedForPackage(id, userId, packageName);
        }
        List associationsForUser = this.mAssociationStore.getAssociationsForUser(userId);
        this.mUserPersistenceHandler.postPersistUserState(userId);
        if (i != 3) {
            notifyListeners(userId, associationsForUser);
        }
        updateAtm(userId, associationsForUser);
    }

    public final void persistStateForUser(int i) {
        ArrayList arrayList = new ArrayList(this.mAssociationStore.getAssociationsForUser(i));
        arrayList.addAll(getPendingRoleHolderRemovalAssociationsForUser(i));
        this.mPersistentStore.persistStateForUser(i, arrayList, getPreviouslyUsedIdsForUser(i));
    }

    public final void notifyListeners(final int i, final List list) {
        this.mListeners.broadcast(new BiConsumer() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CompanionDeviceManagerService.lambda$notifyListeners$0(i, list, (IOnAssociationsChangedListener) obj, obj2);
            }
        });
    }

    public static /* synthetic */ void lambda$notifyListeners$0(int i, List list, IOnAssociationsChangedListener iOnAssociationsChangedListener, Object obj) {
        if (((Integer) obj).intValue() == i) {
            try {
                iOnAssociationsChangedListener.onAssociationsChanged(list);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void markIdAsPreviouslyUsedForPackage(int i, int i2, String str) {
        synchronized (this.mPreviouslyUsedIds) {
            Map map = (Map) this.mPreviouslyUsedIds.get(i2);
            if (map == null) {
                map = new HashMap();
                this.mPreviouslyUsedIds.put(i2, map);
            }
            ((Set) map.computeIfAbsent(str, new Function() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Set lambda$markIdAsPreviouslyUsedForPackage$1;
                    lambda$markIdAsPreviouslyUsedForPackage$1 = CompanionDeviceManagerService.lambda$markIdAsPreviouslyUsedForPackage$1((String) obj);
                    return lambda$markIdAsPreviouslyUsedForPackage$1;
                }
            })).add(Integer.valueOf(i));
        }
    }

    public static /* synthetic */ Set lambda$markIdAsPreviouslyUsedForPackage$1(String str) {
        return new HashSet();
    }

    public final void onPackageRemoveOrDataClearedInternal(int i, String str) {
        Log.i("CDM_CompanionDeviceManagerService", "onPackageRemove_Or_DataCleared() u" + i + "/" + str);
        List associationsForPackage = this.mAssociationStore.getAssociationsForPackage(i, str);
        Iterator it = associationsForPackage.iterator();
        while (it.hasNext()) {
            this.mAssociationStore.removeAssociation(((AssociationInfo) it.next()).getId());
        }
        Iterator it2 = associationsForPackage.iterator();
        while (it2.hasNext()) {
            maybeRemoveRoleHolderForAssociation((AssociationInfo) it2.next());
        }
        this.mCompanionAppController.onPackagesChanged(i);
    }

    public final void onPackageModifiedInternal(int i, String str) {
        Log.i("CDM_CompanionDeviceManagerService", "onPackageModified() u" + i + "/" + str);
        for (AssociationInfo associationInfo : this.mAssociationStore.getAssociationsForPackage(i, str)) {
            if (updateSpecialAccessPermissionForAssociatedPackage(associationInfo) == null) {
                Log.i("CDM_CompanionDeviceManagerService", "onPackageModifiedInternal pkg not available, but has association,so clearing it" + str);
                onPackageRemoveOrDataClearedInternal(i, str);
            } else if (associationInfo.getDeviceMacAddressAsString() != null) {
                try {
                    int applicationEnabledSetting = getContext().getPackageManager().getApplicationEnabledSetting(str);
                    if (applicationEnabledSetting == 2 || applicationEnabledSetting == 3 || applicationEnabledSetting == 4) {
                        Slog.d("CDM_CompanionDeviceManagerService", "onPackageModified unregisterDevicePresence(device address = " + associationInfo.getDeviceMacAddressAsString() + " packageState = " + applicationEnabledSetting + ")");
                        try {
                            this.mImpl.unregisterDevicePresenceListenerService(associationInfo.getDeviceMacAddressAsString(), str, i);
                        } catch (RemoteException e) {
                            Slog.w("CDM_CompanionDeviceManagerService", "unregisterDevicePresenceListenerService RemoteException e=" + e.toString());
                        }
                    }
                } catch (IllegalArgumentException e2) {
                    Slog.e("CDM_CompanionDeviceManagerService", "Failed! to get getApplicationEnabledSetting for package " + str, e2);
                }
            }
        }
        this.mCompanionAppController.onPackagesChanged(i);
    }

    public void removeInactiveSelfManagedAssociations() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = SystemProperties.getLong("debug.cdm.cdmservice.removal_time_window", -1L);
        if (j <= 0) {
            j = ASSOCIATION_REMOVAL_TIME_WINDOW_DEFAULT;
        }
        for (AssociationInfo associationInfo : this.mAssociationStore.getAssociations()) {
            if (associationInfo.isSelfManaged()) {
                if (currentTimeMillis - associationInfo.getLastTimeConnectedMs().longValue() >= j) {
                    int id = associationInfo.getId();
                    Slog.i("CDM_CompanionDeviceManagerService", "Removing inactive self-managed association id=" + id);
                    disassociateInternal(id);
                }
            }
        }
    }

    public class CompanionDeviceManagerImpl extends ICompanionDeviceManager.Stub {
        public CompanionDeviceManagerImpl() {
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (Throwable th) {
                Slog.e("CDM_CompanionDeviceManagerService", "Error during IPC", th);
                throw ExceptionUtils.propagate(th, RemoteException.class);
            }
        }

        public void associate(AssociationRequest associationRequest, IAssociationRequestCallback iAssociationRequestCallback, String str, int i) {
            Slog.i("CDM_CompanionDeviceManagerService", "associate() request=" + associationRequest + ", package=u" + i + "/" + str);
            PermissionsUtils.enforceCallerCanManageAssociationsForPackage(CompanionDeviceManagerService.this.getContext(), i, str, "create associations");
            CompanionDeviceManagerService.this.mAssociationRequestsProcessor.processNewAssociationRequest(associationRequest, str, i, iAssociationRequestCallback);
        }

        public PendingIntent buildAssociationCancellationIntent(String str, int i) {
            Slog.i("CDM_CompanionDeviceManagerService", "buildAssociationCancellationIntent() package=u" + i + "/" + str);
            PermissionsUtils.enforceCallerCanManageAssociationsForPackage(CompanionDeviceManagerService.this.getContext(), i, str, "build association cancellation intent");
            return CompanionDeviceManagerService.this.mAssociationRequestsProcessor.buildAssociationCancellationIntent(str, i);
        }

        public List getAssociations(String str, int i) {
            PermissionsUtils.enforceCallerCanManageAssociationsForPackage(CompanionDeviceManagerService.this.getContext(), i, str, "get associations");
            if (!PermissionsUtils.checkCallerCanManageCompanionDevice(CompanionDeviceManagerService.this.getContext())) {
                PackageUtils.enforceUsesCompanionDeviceFeature(CompanionDeviceManagerService.this.getContext(), i, str);
            }
            return CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackage(i, str);
        }

        public List getAllAssociationsForUser(int i) {
            PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(CompanionDeviceManagerService.this.getContext(), i);
            PermissionsUtils.enforceCallerCanManageCompanionDevice(CompanionDeviceManagerService.this.getContext(), "getAllAssociationsForUser");
            return CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForUser(i);
        }

        public void addOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) {
            PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(CompanionDeviceManagerService.this.getContext(), i);
            PermissionsUtils.enforceCallerCanManageCompanionDevice(CompanionDeviceManagerService.this.getContext(), "addOnAssociationsChangedListener");
            CompanionDeviceManagerService.this.mListeners.register(iOnAssociationsChangedListener, Integer.valueOf(i));
        }

        public void removeOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) {
            PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(CompanionDeviceManagerService.this.getContext(), i);
            PermissionsUtils.enforceCallerCanManageCompanionDevice(CompanionDeviceManagerService.this.getContext(), "removeOnAssociationsChangedListener");
            CompanionDeviceManagerService.this.mListeners.unregister(iOnAssociationsChangedListener);
        }

        public void addOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) {
            CompanionDeviceManagerService.this.mTransportManager.addListener(iOnTransportsChangedListener);
        }

        public void removeOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) {
            CompanionDeviceManagerService.this.mTransportManager.removeListener(iOnTransportsChangedListener);
        }

        public void sendMessage(int i, byte[] bArr, int[] iArr) {
            CompanionDeviceManagerService.this.mTransportManager.sendMessage(i, bArr, iArr);
        }

        public void addOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) {
            CompanionDeviceManagerService.this.mTransportManager.addListener(i, iOnMessageReceivedListener);
        }

        public void removeOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) {
            CompanionDeviceManagerService.this.mTransportManager.removeListener(i, iOnMessageReceivedListener);
        }

        public void legacyDisassociate(String str, String str2, int i) {
            Log.i("CDM_CompanionDeviceManagerService", "legacyDisassociate() pkg=u" + i + "/" + str2 + ", macAddress=" + getMaskedMacAddress(str));
            Objects.requireNonNull(str);
            Objects.requireNonNull(str2);
            CompanionDeviceManagerService.this.disassociateInternal(CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i, str2, str).getId());
        }

        public void disassociate(int i) {
            Log.i("CDM_CompanionDeviceManagerService", "disassociate() associationId=" + i);
            CompanionDeviceManagerService.this.disassociateInternal(CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i).getId());
        }

        public PendingIntent requestNotificationAccess(ComponentName componentName, int i) {
            checkCanCallNotificationApi(componentName.getPackageName(), i);
            if (componentName.flattenToString().length() > 500) {
                throw new IllegalArgumentException("Component name is too long.");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PendingIntent.getActivityAsUser(CompanionDeviceManagerService.this.getContext(), 0, NotificationAccessConfirmationActivityContract.launcherIntent(CompanionDeviceManagerService.this.getContext(), i, componentName), 1409286144, null, new UserHandle(i));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean hasNotificationAccess(ComponentName componentName) {
            checkCanCallNotificationApi(componentName.getPackageName(), UserHandle.getCallingUserId());
            return ((NotificationManager) CompanionDeviceManagerService.this.getContext().getSystemService(NotificationManager.class)).isNotificationListenerAccessGranted(componentName);
        }

        public boolean isDeviceAssociatedForWifiConnection(String str, final String str2, int i) {
            CompanionDeviceManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_COMPANION_DEVICES", "isDeviceAssociated");
            if (CompanionDeviceManagerService.this.getContext().getPackageManager().checkPermission("android.permission.COMPANION_APPROVE_WIFI_CONNECTIONS", str) == 0) {
                return true;
            }
            return CollectionUtils.any(CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackage(i, str), new Predicate() { // from class: com.android.server.companion.CompanionDeviceManagerService$CompanionDeviceManagerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$isDeviceAssociatedForWifiConnection$0;
                    lambda$isDeviceAssociatedForWifiConnection$0 = CompanionDeviceManagerService.CompanionDeviceManagerImpl.lambda$isDeviceAssociatedForWifiConnection$0(str2, (AssociationInfo) obj);
                    return lambda$isDeviceAssociatedForWifiConnection$0;
                }
            });
        }

        public static /* synthetic */ boolean lambda$isDeviceAssociatedForWifiConnection$0(String str, AssociationInfo associationInfo) {
            return associationInfo.isLinkedTo(str);
        }

        public void registerDevicePresenceListenerService(String str, String str2, int i) {
            registerDevicePresenceListenerActive(str2, str, true);
        }

        public void unregisterDevicePresenceListenerService(String str, String str2, int i) {
            registerDevicePresenceListenerActive(str2, str, false);
        }

        public PendingIntent buildPermissionTransferUserConsentIntent(String str, int i, int i2) {
            if (!FeatureUtils.isPermSyncEnabled()) {
                throw new UnsupportedOperationException("Calling buildPermissionTransferUserConsentIntent, but this API is disabled by the system.");
            }
            return CompanionDeviceManagerService.this.mSystemDataTransferProcessor.buildPermissionTransferUserConsentIntent(str, i, i2);
        }

        public void startSystemDataTransfer(String str, int i, int i2, ISystemDataTransferCallback iSystemDataTransferCallback) {
            if (!FeatureUtils.isPermSyncEnabled()) {
                throw new UnsupportedOperationException("Calling startSystemDataTransfer, but this API is disabled by the system.");
            }
            CompanionDeviceManagerService.this.mSystemDataTransferProcessor.startSystemDataTransfer(str, i, i2, iSystemDataTransferCallback);
        }

        public void attachSystemDataTransport(String str, int i, int i2, ParcelFileDescriptor parcelFileDescriptor) {
            CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i2);
            CompanionDeviceManagerService.this.mTransportManager.attachSystemDataTransport(str, i, i2, parcelFileDescriptor);
        }

        public void detachSystemDataTransport(String str, int i, int i2) {
            CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i2);
            CompanionDeviceManagerService.this.mTransportManager.detachSystemDataTransport(str, i, i2);
        }

        public void enableSystemDataSync(int i, int i2) {
            CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            CompanionDeviceManagerService.this.mAssociationRequestsProcessor.enableSystemDataSync(i, i2);
        }

        public void disableSystemDataSync(int i, int i2) {
            CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            CompanionDeviceManagerService.this.mAssociationRequestsProcessor.disableSystemDataSync(i, i2);
        }

        public void enableSecureTransport(boolean z) {
            CompanionDeviceManagerService.this.mTransportManager.enableSecureTransport(z);
        }

        public void notifyDeviceAppeared(int i) {
            Log.i("CDM_CompanionDeviceManagerService", "notifyDevice_Appeared() id=" + i);
            AssociationInfo associationWithCallerChecks = CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            if (!associationWithCallerChecks.isSelfManaged()) {
                throw new IllegalArgumentException("Association with ID " + i + " is not self-managed. notifyDeviceAppeared(int) can only be called for self-managed associations.");
            }
            CompanionDeviceManagerService.this.mAssociationStore.updateAssociation(AssociationInfo.builder(associationWithCallerChecks).setLastTimeConnected(System.currentTimeMillis()).build());
            CompanionDeviceManagerService.this.mDevicePresenceMonitor.onSelfManagedDeviceConnected(i);
        }

        public void notifyDeviceDisappeared(int i) {
            Log.i("CDM_CompanionDeviceManagerService", "notifyDevice_Disappeared() id=" + i);
            if (!CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i).isSelfManaged()) {
                throw new IllegalArgumentException("Association with ID " + i + " is not self-managed. notifyDeviceAppeared(int) can only be called for self-managed associations.");
            }
            CompanionDeviceManagerService.this.mDevicePresenceMonitor.onSelfManagedDeviceDisconnected(i);
        }

        public boolean isCompanionApplicationBound(String str, int i) {
            return CompanionDeviceManagerService.this.mCompanionAppController.isCompanionApplicationBound(i, str);
        }

        public final void registerDevicePresenceListenerActive(String str, String str2, boolean z) {
            Log.i("CDM_CompanionDeviceManagerService", "registerDevicePresenceListenerActive() active=" + z + " deviceAddress=" + getMaskedMacAddress(str2));
            CompanionDeviceManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE", "[un]registerDevicePresenceListenerService");
            int callingUserId = UserHandle.getCallingUserId();
            PermissionsUtils.enforceCallerIsSystemOr(callingUserId, str);
            AssociationInfo associationsForPackageWithAddress = CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackageWithAddress(callingUserId, str, str2);
            if (associationsForPackageWithAddress == null) {
                throw new RemoteException(new DeviceNotAssociatedException("App " + str + " is not associated with device " + getMaskedMacAddress(str2) + " for user " + callingUserId));
            }
            if (z == associationsForPackageWithAddress.isNotifyOnDeviceNearby()) {
                return;
            }
            AssociationInfo build = AssociationInfo.builder(associationsForPackageWithAddress).setNotifyOnDeviceNearby(z).build();
            CompanionDeviceManagerService.this.mAssociationStore.updateAssociation(build);
            if (z && CompanionDeviceManagerService.this.mDevicePresenceMonitor.isDevicePresent(build.getId())) {
                CompanionDeviceManagerService.this.onDeviceAppearedInternal(build.getId());
            }
            if (z || CompanionDeviceManagerService.this.shouldBindPackage(callingUserId, str)) {
                return;
            }
            CompanionDeviceManagerService.this.mCompanionAppController.unbindCompanionApplication(callingUserId, str);
        }

        public void createAssociation(String str, String str2, int i, byte[] bArr) {
            if (!CompanionDeviceManagerService.this.getContext().getPackageManager().hasSigningCertificate(str, bArr, 1)) {
                Slog.e("CDM_CompanionDeviceManagerService", "Given certificate doesn't match the package certificate.");
                return;
            }
            CompanionDeviceManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.ASSOCIATE_COMPANION_DEVICES", "createAssociation");
            CompanionDeviceManagerService.this.createNewAssociation(i, str, MacAddress.fromString(str2), null, null, false);
        }

        public final void checkCanCallNotificationApi(String str, int i) {
            PermissionsUtils.enforceCallerIsSystemOr(i, str);
            if (ICompanionDeviceManager.Stub.getCallingUid() == 1000) {
                return;
            }
            PackageUtils.enforceUsesCompanionDeviceFeature(CompanionDeviceManagerService.this.getContext(), i, str);
            Preconditions.checkState(!ArrayUtils.isEmpty(CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackage(i, str)), "App must have an association before calling this API");
        }

        public boolean canPairWithoutPrompt(String str, String str2, int i) {
            AssociationInfo associationsForPackageWithAddress = CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackageWithAddress(i, str, str2);
            return associationsForPackageWithAddress != null && System.currentTimeMillis() - associationsForPackageWithAddress.getTimeApprovedMs() < 600000;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            CompanionDeviceManagerService companionDeviceManagerService = CompanionDeviceManagerService.this;
            return new CompanionDeviceShellCommand(companionDeviceManagerService, companionDeviceManagerService.mAssociationStore, CompanionDeviceManagerService.this.mDevicePresenceMonitor, CompanionDeviceManagerService.this.mTransportManager, CompanionDeviceManagerService.this.mSystemDataTransferRequestStore, CompanionDeviceManagerService.this.mAssociationRequestsProcessor).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpAndUsageStatsPermission(CompanionDeviceManagerService.this.getContext(), "CDM_CompanionDeviceManagerService", printWriter)) {
                CompanionDeviceManagerService.this.mAssociationStore.dump(printWriter);
                CompanionDeviceManagerService.this.mDevicePresenceMonitor.dump(printWriter);
                CompanionDeviceManagerService.this.mCompanionAppController.dump(printWriter);
            }
        }

        public final String getMaskedMacAddress(String str) {
            if (str == null) {
                str = null;
            }
            if (str == null) {
                return null;
            }
            if (str.length() <= 11) {
                return str;
            }
            return "XX:XX:XX:XX" + str.substring(11);
        }
    }

    public void createNewAssociation(int i, String str, MacAddress macAddress, CharSequence charSequence, String str2, boolean z) {
        this.mAssociationRequestsProcessor.createAssociation(i, str, macAddress, charSequence, str2, null, z, null, null);
    }

    public final Map getPreviouslyUsedIdsForUser(int i) {
        Map previouslyUsedIdsForUserLocked;
        synchronized (this.mPreviouslyUsedIds) {
            previouslyUsedIdsForUserLocked = getPreviouslyUsedIdsForUserLocked(i);
        }
        return previouslyUsedIdsForUserLocked;
    }

    public final Map getPreviouslyUsedIdsForUserLocked(int i) {
        Map map = (Map) this.mPreviouslyUsedIds.get(i);
        if (map == null) {
            return Collections.emptyMap();
        }
        return deepUnmodifiableCopy(map);
    }

    public final Set getPreviouslyUsedIdsForPackageLocked(int i, String str) {
        Set set = (Set) getPreviouslyUsedIdsForUserLocked(i).get(str);
        return set == null ? Collections.emptySet() : set;
    }

    public int getNewAssociationIdForPackage(int i, String str) {
        int firstAssociationIdForUser;
        synchronized (this.mPreviouslyUsedIds) {
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            Iterator it = this.mAssociationStore.getAssociations().iterator();
            while (it.hasNext()) {
                sparseBooleanArray.put(((AssociationInfo) it.next()).getId(), true);
            }
            Set previouslyUsedIdsForPackageLocked = getPreviouslyUsedIdsForPackageLocked(i, str);
            firstAssociationIdForUser = getFirstAssociationIdForUser(i);
            int lastAssociationIdForUser = getLastAssociationIdForUser(i);
            do {
                if (!sparseBooleanArray.get(firstAssociationIdForUser) && !previouslyUsedIdsForPackageLocked.contains(Integer.valueOf(firstAssociationIdForUser))) {
                }
                firstAssociationIdForUser++;
            } while (firstAssociationIdForUser <= lastAssociationIdForUser);
            throw new RuntimeException("Cannot create a new Association ID for " + str + " for user " + i);
        }
        return firstAssociationIdForUser;
    }

    public void disassociateInternal(int i) {
        AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        int userId = associationById.getUserId();
        String packageName = associationById.getPackageName();
        String deviceProfile = associationById.getDeviceProfile();
        if (!maybeRemoveRoleHolderForAssociation(associationById)) {
            addToPendingRoleHolderRemoval(associationById);
        }
        boolean isDevicePresent = this.mDevicePresenceMonitor.isDevicePresent(i);
        this.mAssociationStore.removeAssociation(i);
        MetricUtils.logRemoveAssociation(deviceProfile);
        this.mSystemDataTransferRequestStore.removeRequestsByAssociationId(userId, i);
        if (isDevicePresent && associationById.isNotifyOnDeviceNearby() && !CollectionUtils.any(this.mAssociationStore.getAssociationsForPackage(userId, packageName), new Predicate() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$disassociateInternal$2;
                lambda$disassociateInternal$2 = CompanionDeviceManagerService.this.lambda$disassociateInternal$2((AssociationInfo) obj);
                return lambda$disassociateInternal$2;
            }
        })) {
            this.mCompanionAppController.unbindCompanionApplication(userId, packageName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$disassociateInternal$2(AssociationInfo associationInfo) {
        return associationInfo.isNotifyOnDeviceNearby() && this.mDevicePresenceMonitor.isDevicePresent(associationInfo.getId());
    }

    public final boolean maybeRemoveRoleHolderForAssociation(AssociationInfo associationInfo) {
        final String deviceProfile = associationInfo.getDeviceProfile();
        if (deviceProfile == null || deviceProfile.equals("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION")) {
            return true;
        }
        final int id = associationInfo.getId();
        int userId = associationInfo.getUserId();
        String packageName = associationInfo.getPackageName();
        if (CollectionUtils.any(this.mAssociationStore.getAssociationsForPackage(userId, packageName), new Predicate() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$maybeRemoveRoleHolderForAssociation$3;
                lambda$maybeRemoveRoleHolderForAssociation$3 = CompanionDeviceManagerService.lambda$maybeRemoveRoleHolderForAssociation$3(deviceProfile, id, (AssociationInfo) obj);
                return lambda$maybeRemoveRoleHolderForAssociation$3;
            }
        })) {
            return true;
        }
        if (getPackageProcessImportance(userId, packageName) <= 200) {
            Slog.i("CDM_CompanionDeviceManagerService", "Cannot remove role holder for the removed association id=" + id + " now - process is visible.");
            return false;
        }
        RolesUtils.removeRoleHolderForAssociation(getContext(), associationInfo);
        return true;
    }

    public static /* synthetic */ boolean lambda$maybeRemoveRoleHolderForAssociation$3(String str, int i, AssociationInfo associationInfo) {
        return str.equals(associationInfo.getDeviceProfile()) && i != associationInfo.getId();
    }

    public final int getPackageProcessImportance(final int i, final String str) {
        return ((Integer) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda6
            public final Object getOrThrow() {
                Integer lambda$getPackageProcessImportance$4;
                lambda$getPackageProcessImportance$4 = CompanionDeviceManagerService.this.lambda$getPackageProcessImportance$4(str, i);
                return lambda$getPackageProcessImportance$4;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getPackageProcessImportance$4(String str, int i) {
        return Integer.valueOf(this.mActivityManager.getUidImportance(this.mPackageManagerInternal.getPackageUid(str, 0L, i)));
    }

    public final void addToPendingRoleHolderRemoval(AssociationInfo associationInfo) {
        AssociationInfo build = AssociationInfo.builder(associationInfo).setRevoked(true).build();
        String packageName = build.getPackageName();
        int packageUid = this.mPackageManagerInternal.getPackageUid(packageName, 0L, build.getUserId());
        synchronized (this.mRevokedAssociationsPendingRoleHolderRemoval) {
            ((Set) this.mRevokedAssociationsPendingRoleHolderRemoval.forUser(build.getUserId())).add(build);
            if (!this.mUidsPendingRoleHolderRemoval.containsKey(Integer.valueOf(packageUid))) {
                this.mUidsPendingRoleHolderRemoval.put(Integer.valueOf(packageUid), packageName);
                if (this.mUidsPendingRoleHolderRemoval.size() == 1) {
                    this.mOnPackageVisibilityChangeListener.startListening();
                }
            }
        }
    }

    public final void removeFromPendingRoleHolderRemoval(AssociationInfo associationInfo) {
        final String packageName = associationInfo.getPackageName();
        int userId = associationInfo.getUserId();
        int packageUid = this.mPackageManagerInternal.getPackageUid(packageName, 0L, userId);
        synchronized (this.mRevokedAssociationsPendingRoleHolderRemoval) {
            ((Set) this.mRevokedAssociationsPendingRoleHolderRemoval.forUser(userId)).remove(associationInfo);
            if (!CollectionUtils.any(getPendingRoleHolderRemovalAssociationsForUser(userId), new Predicate() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$removeFromPendingRoleHolderRemoval$5;
                    lambda$removeFromPendingRoleHolderRemoval$5 = CompanionDeviceManagerService.lambda$removeFromPendingRoleHolderRemoval$5(packageName, (AssociationInfo) obj);
                    return lambda$removeFromPendingRoleHolderRemoval$5;
                }
            })) {
                this.mUidsPendingRoleHolderRemoval.remove(Integer.valueOf(packageUid));
            }
            if (this.mUidsPendingRoleHolderRemoval.isEmpty()) {
                this.mOnPackageVisibilityChangeListener.stopListening();
            }
        }
    }

    public static /* synthetic */ boolean lambda$removeFromPendingRoleHolderRemoval$5(String str, AssociationInfo associationInfo) {
        return str.equals(associationInfo.getPackageName());
    }

    public final Set getPendingRoleHolderRemovalAssociationsForUser(int i) {
        ArraySet arraySet;
        synchronized (this.mRevokedAssociationsPendingRoleHolderRemoval) {
            arraySet = new ArraySet((Collection) this.mRevokedAssociationsPendingRoleHolderRemoval.forUser(i));
        }
        return arraySet;
    }

    public final String getPackageNameByUid(int i) {
        String str;
        synchronized (this.mRevokedAssociationsPendingRoleHolderRemoval) {
            str = (String) this.mUidsPendingRoleHolderRemoval.get(Integer.valueOf(i));
        }
        return str;
    }

    public PackageInfo updateSpecialAccessPermissionForAssociatedPackage(AssociationInfo associationInfo) {
        final PackageInfo packageInfo = PackageUtils.getPackageInfo(getContext(), associationInfo.getUserId(), associationInfo.getPackageName());
        if (packageInfo == null) {
            Slog.i("CDM_CompanionDeviceManagerService", "packageInfo is null");
            return null;
        }
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                CompanionDeviceManagerService.this.lambda$updateSpecialAccessPermissionForAssociatedPackage$6(packageInfo);
            }
        });
        return packageInfo;
    }

    /* renamed from: updateSpecialAccessPermissionAsSystem, reason: merged with bridge method [inline-methods] */
    public final void lambda$updateSpecialAccessPermissionForAssociatedPackage$6(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return;
        }
        if (containsEither(packageInfo.requestedPermissions, "android.permission.RUN_IN_BACKGROUND", "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND")) {
            this.mPowerWhitelistManager.addToWhitelist(packageInfo.packageName);
        } else {
            boolean z = false;
            try {
                z = IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).isPowerSaveWhitelistApp(packageInfo.packageName);
                Slog.i("CDM_CompanionDeviceManagerService", "Update SpAccess:" + packageInfo.packageName + " Unrestricted:" + z);
            } catch (RemoteException unused) {
                Slog.w("CDM_CompanionDeviceManagerService", "RemoteException when calling isPowerSaveWhitelistApp");
            }
            if (!z) {
                try {
                    Slog.i("CDM_CompanionDeviceManagerService", "Update SpAccess: remove from whitelist");
                    this.mPowerWhitelistManager.removeFromWhitelist(packageInfo.packageName);
                } catch (UnsupportedOperationException unused2) {
                    Slog.w("CDM_CompanionDeviceManagerService", packageInfo.packageName + " can't be removed from power save whitelist. It might due to the package is whitelisted by the system.");
                }
            }
        }
        NetworkPolicyManager from = NetworkPolicyManager.from(getContext());
        try {
            if (containsEither(packageInfo.requestedPermissions, "android.permission.USE_DATA_IN_BACKGROUND", "android.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND")) {
                from.addUidPolicy(packageInfo.applicationInfo.uid, 4);
            } else {
                from.removeUidPolicy(packageInfo.applicationInfo.uid, 4);
            }
        } catch (IllegalArgumentException e) {
            Slog.e("CDM_CompanionDeviceManagerService", e.getMessage());
        }
        exemptFromAutoRevoke(packageInfo.packageName, packageInfo.applicationInfo.uid);
    }

    public final void exemptFromAutoRevoke(String str, int i) {
        try {
            this.mAppOpsManager.setMode(97, i, str, 1);
        } catch (RemoteException e) {
            Slog.w("CDM_CompanionDeviceManagerService", "Error while granting auto revoke exemption for " + str, e);
        }
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
            activityTaskManagerInternal.setCompanionAppUids(i, arraySet);
        }
        ActivityManagerInternal activityManagerInternal = this.mAmInternal;
        if (activityManagerInternal != null) {
            activityManagerInternal.setCompanionAppUids(i, new ArraySet((Collection) arraySet));
        }
    }

    public final void maybeGrantAutoRevokeExemptions() {
        Slog.d("CDM_CompanionDeviceManagerService", "maybeGrantAutoRevokeExemptions()");
        PackageManager packageManager = getContext().getPackageManager();
        for (int i : ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserIds()) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(new File(Environment.getUserSystemDirectory(i), "companion_device_preferences.xml"), 0);
            if (!sharedPreferences.getBoolean("auto_revoke_grants_done", false)) {
                try {
                    for (AssociationInfo associationInfo : this.mAssociationStore.getAssociationsForUser(i)) {
                        try {
                            exemptFromAutoRevoke(associationInfo.getPackageName(), packageManager.getPackageUidAsUser(associationInfo.getPackageName(), i));
                        } catch (PackageManager.NameNotFoundException e) {
                            Slog.w("CDM_CompanionDeviceManagerService", "Unknown companion package: " + associationInfo.getPackageName(), e);
                        }
                    }
                } finally {
                    sharedPreferences.edit().putBoolean("auto_revoke_grants_done", true).apply();
                }
            }
        }
    }

    public static Map deepUnmodifiableCopy(Map map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            hashMap.put((String) entry.getKey(), Collections.unmodifiableSet(new HashSet((Collection) entry.getValue())));
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static boolean containsEither(Object[] objArr, Object obj, Object obj2) {
        return ArrayUtils.contains(objArr, obj) || ArrayUtils.contains(objArr, obj2);
    }

    public class LocalService implements CompanionDeviceManagerServiceInternal {
        public LocalService() {
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void removeInactiveSelfManagedAssociations() {
            CompanionDeviceManagerService.this.removeInactiveSelfManagedAssociations();
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void registerCallMetadataSyncCallback(CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback, int i) {
            if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
                CompanionDeviceManagerService.this.mCrossDeviceSyncController.registerCallMetadataSyncCallback(crossDeviceSyncControllerCallback, i);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void crossDeviceSync(int i, Collection collection) {
            if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
                CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncToAllDevicesForUserId(i, collection);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void crossDeviceSync(AssociationInfo associationInfo, Collection collection) {
            if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
                CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncToSingleDevice(associationInfo, collection);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void sendCrossDeviceSyncMessage(int i, byte[] bArr) {
            if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
                CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncMessageToDevice(i, bArr);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void sendCrossDeviceSyncMessageToAllDevices(int i, byte[] bArr) {
            if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
                CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncMessageToAllDevicesForUserId(i, bArr);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void addSelfOwnedCallId(String str) {
            if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
                CompanionDeviceManagerService.this.mCrossDeviceSyncController.addSelfOwnedCallId(str);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void removeSelfOwnedCallId(String str) {
            if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
                CompanionDeviceManagerService.this.mCrossDeviceSyncController.removeSelfOwnedCallId(str);
            }
        }
    }

    public void persistState() {
        this.mUserPersistenceHandler.clearMessages();
        Iterator it = this.mUserManager.getAliveUsers().iterator();
        while (it.hasNext()) {
            persistStateForUser(((UserInfo) it.next()).id);
        }
    }

    public class PersistUserStateHandler extends Handler {
        public PersistUserStateHandler() {
            super(BackgroundThread.get().getLooper());
        }

        public synchronized void postPersistUserState(int i) {
            if (!hasMessages(i)) {
                sendMessage(obtainMessage(i));
            }
        }

        public synchronized void clearMessages() {
            removeCallbacksAndMessages(null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            CompanionDeviceManagerService.this.persistStateForUser(message.what);
        }
    }

    public class OnPackageVisibilityChangeListener implements ActivityManager.OnUidImportanceListener {
        public final ActivityManager mAm;

        public OnPackageVisibilityChangeListener(ActivityManager activityManager) {
            this.mAm = activityManager;
        }

        public void startListening() {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.CompanionDeviceManagerService$OnPackageVisibilityChangeListener$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    CompanionDeviceManagerService.OnPackageVisibilityChangeListener.this.lambda$startListening$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startListening$0() {
            this.mAm.addOnUidImportanceListener(this, 200);
        }

        public void stopListening() {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.CompanionDeviceManagerService$OnPackageVisibilityChangeListener$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    CompanionDeviceManagerService.OnPackageVisibilityChangeListener.this.lambda$stopListening$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopListening$1() {
            this.mAm.removeOnUidImportanceListener(this);
        }

        public void onUidImportance(int i, int i2) {
            String packageNameByUid;
            if (i2 > 200 && (packageNameByUid = CompanionDeviceManagerService.this.getPackageNameByUid(i)) != null) {
                int userId = UserHandle.getUserId(i);
                boolean z = false;
                for (AssociationInfo associationInfo : CompanionDeviceManagerService.this.getPendingRoleHolderRemovalAssociationsForUser(userId)) {
                    if (packageNameByUid.equals(associationInfo.getPackageName()) && CompanionDeviceManagerService.this.maybeRemoveRoleHolderForAssociation(associationInfo)) {
                        CompanionDeviceManagerService.this.removeFromPendingRoleHolderRemoval(associationInfo);
                        z = true;
                    }
                }
                if (z) {
                    CompanionDeviceManagerService.this.mUserPersistenceHandler.postPersistUserState(userId);
                }
            }
        }
    }

    public class PerUserAssociationSet extends PerUser {
        public PerUserAssociationSet() {
        }

        public Set create(int i) {
            return new ArraySet();
        }
    }
}
