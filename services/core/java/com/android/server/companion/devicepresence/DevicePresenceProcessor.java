package com.android.server.companion.devicepresence;

import android.companion.AssociationInfo;
import android.companion.DevicePresenceEvent;
import android.companion.ObservingDevicePresenceRequest;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.PowerManagerInternal;
import android.os.UserManager;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.util.CollectionUtils;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.companion.association.AssociationStore;
import com.android.server.companion.devicepresence.CompanionAppBinder;
import com.android.server.companion.utils.PermissionsUtils;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DevicePresenceProcessor implements AssociationStore.OnChangeListener {
    public final AssociationStore mAssociationStore;
    public final BleDeviceProcessor mBleDeviceProcessor;
    public final BluetoothDeviceProcessor mBluetoothDeviceProcessor;
    public final CompanionAppBinder mCompanionAppBinder;
    public final Context mContext;
    public final ObservableUuidStore mObservableUuidStore;
    public final PowerManagerInternal mPowerManagerInternal;
    public final UserManager mUserManager;
    public final Set mConnectedBtDevices = new HashSet();
    public final Set mNearbyBleDevices = new HashSet();
    public final Set mReportedSelfManagedDevices = new HashSet();
    public final Set mConnectedUuidDevices = new HashSet();
    public final Set mBtDisconnectedDevices = new HashSet();
    public final SparseBooleanArray mBtDisconnectedDevicesBlePresence = new SparseBooleanArray();
    public final Set mSimulated = new HashSet();
    public final BleDeviceDisappearedScheduler mSchedulerHelper = new BleDeviceDisappearedScheduler(this, 1);
    public final BleDeviceDisappearedScheduler mBleDeviceDisappearedScheduler = new BleDeviceDisappearedScheduler(this, 0);
    public final SparseArray mPendingDevicePresenceEvents = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BleDeviceDisappearedScheduler extends Handler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DevicePresenceProcessor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BleDeviceDisappearedScheduler(DevicePresenceProcessor devicePresenceProcessor, int i) {
            super(Looper.getMainLooper());
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = devicePresenceProcessor;
                    super(Looper.getMainLooper());
                    break;
                default:
                    this.this$0 = devicePresenceProcessor;
                    break;
            }
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (this.$r8$classId) {
                case 0:
                    int i = message.what;
                    synchronized (this.this$0.mBtDisconnectedDevices) {
                        try {
                            boolean z = this.this$0.mBtDisconnectedDevicesBlePresence.get(i);
                            if (((HashSet) this.this$0.mBtDisconnectedDevices).contains(Integer.valueOf(i)) && !z) {
                                Slog.i("CDM_DevicePresenceProcessor", "Device ( " + i + " ) is likely BLE out of range, sending callback with event ( 1 )");
                                DevicePresenceProcessor devicePresenceProcessor = this.this$0;
                                devicePresenceProcessor.onDevicePresenceEvent(devicePresenceProcessor.mNearbyBleDevices, i, 1);
                            }
                            ((HashSet) this.this$0.mBtDisconnectedDevices).remove(Integer.valueOf(i));
                            this.this$0.mBtDisconnectedDevicesBlePresence.delete(i);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                default:
                    int i2 = message.what;
                    DevicePresenceProcessor devicePresenceProcessor2 = this.this$0;
                    if (((HashSet) devicePresenceProcessor2.mSimulated).contains(Integer.valueOf(i2))) {
                        devicePresenceProcessor2.onDevicePresenceEvent(devicePresenceProcessor2.mSimulated, i2, 1);
                        return;
                    }
                    return;
            }
        }

        public void unScheduleDeviceDisappeared(int i) {
            if (hasMessages(i)) {
                BootReceiver$$ExternalSyntheticOutline0.m(i, "unScheduleDeviceDisappeared for Device( ", " )", "CDM_DevicePresenceProcessor");
                synchronized (this.this$0.mBtDisconnectedDevices) {
                    ((HashSet) this.this$0.mBtDisconnectedDevices).remove(Integer.valueOf(i));
                    this.this$0.mBtDisconnectedDevicesBlePresence.delete(i);
                }
                removeMessages(i);
            }
        }
    }

    public DevicePresenceProcessor(Context context, CompanionAppBinder companionAppBinder, UserManager userManager, AssociationStore associationStore, ObservableUuidStore observableUuidStore, PowerManagerInternal powerManagerInternal) {
        this.mContext = context;
        this.mCompanionAppBinder = companionAppBinder;
        this.mAssociationStore = associationStore;
        this.mObservableUuidStore = observableUuidStore;
        this.mUserManager = userManager;
        this.mBluetoothDeviceProcessor = new BluetoothDeviceProcessor(associationStore, observableUuidStore, this);
        this.mBleDeviceProcessor = new BleDeviceProcessor(associationStore, this);
        this.mPowerManagerInternal = powerManagerInternal;
    }

    public static void enforceCallerShellOrRoot() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && callingUid != 0) {
            throw new SecurityException("Caller is neither Shell nor Root");
        }
    }

    public final void bindApplicationIfNeeded(int i, String str, boolean z) {
        List list;
        if (this.mCompanionAppBinder.isCompanionApplicationBound(i, str)) {
            Slog.i("CDM_DevicePresenceProcessor", AccountManagerService$$ExternalSyntheticOutline0.m(i, "UserId=[", "], packageName=[", str, "] is already bound."));
            return;
        }
        CompanionAppBinder companionAppBinder = this.mCompanionAppBinder;
        DevicePresenceProcessor$$ExternalSyntheticLambda0 devicePresenceProcessor$$ExternalSyntheticLambda0 = new DevicePresenceProcessor$$ExternalSyntheticLambda0(this);
        companionAppBinder.getClass();
        StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "Binding user=[", "], package=[", str, "], isSelfManaged=[");
        m.append(z);
        m.append("]...");
        Slog.i("CDM_CompanionAppBinder", m.toString());
        CompanionAppBinder.CompanionServicesRegister companionServicesRegister = companionAppBinder.mCompanionServicesRegister;
        synchronized (companionServicesRegister) {
            list = (List) companionServicesRegister.forUser(i).getOrDefault(str, Collections.emptyList());
        }
        if (list.isEmpty()) {
            Slog.e("CDM_CompanionAppBinder", AccountManagerService$$ExternalSyntheticOutline0.m(i, "Can not bind companion applications u", "/", str, ": eligible CompanionDeviceService not found.\nA CompanionDeviceService should declare an intent-filter for \"android.companion.CompanionDeviceService\" action and require \"android.permission.BIND_COMPANION_DEVICE_SERVICE\" permission."));
            return;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (companionAppBinder.mBoundCompanionApplications) {
            try {
                if (((HashMap) companionAppBinder.mBoundCompanionApplications).containsKey(new Pair(Integer.valueOf(i), str))) {
                    Slog.w("CDM_CompanionAppBinder", "The package is ALREADY bound.");
                    return;
                }
                int i2 = 0;
                while (i2 < list.size()) {
                    boolean z2 = i2 == 0;
                    Context context = companionAppBinder.mContext;
                    ComponentName componentName = (ComponentName) list.get(i2);
                    ServiceThread serviceThread = CompanionServiceConnector.sServiceThread;
                    arrayList.add(new CompanionServiceConnector(i, z ? 268435456 : EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT, componentName, context, z2));
                    i2++;
                }
                ((HashMap) companionAppBinder.mBoundCompanionApplications).put(new Pair(Integer.valueOf(i), str), arrayList);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((CompanionServiceConnector) it.next()).setListener(devicePresenceProcessor$$ExternalSyntheticLambda0);
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    ((CompanionServiceConnector) it2.next()).connect();
                }
            } finally {
            }
        }
    }

    public final boolean canStopBleScan() {
        for (AssociationInfo associationInfo : this.mAssociationStore.getActiveAssociations()) {
            int id = associationInfo.getId();
            synchronized (this.mBtDisconnectedDevices) {
                try {
                    if (associationInfo.isNotifyOnDeviceNearby()) {
                        if (((HashSet) this.mConnectedBtDevices).contains(Integer.valueOf(id))) {
                            if (((HashSet) this.mNearbyBleDevices).contains(Integer.valueOf(id))) {
                                if (!((HashSet) this.mBtDisconnectedDevices).isEmpty()) {
                                }
                            }
                        }
                        Slog.i("CDM_DevicePresenceProcessor", "The BLE scan cannot be stopped, device( " + id + " ) is not yet connected OR the BLE is not current present Or is pending to report BLE lost");
                        return false;
                    }
                } finally {
                }
            }
        }
        return true;
    }

    public final boolean isDevicePresent(int i) {
        if (!((HashSet) this.mReportedSelfManagedDevices).contains(Integer.valueOf(i))) {
            if (!((HashSet) this.mConnectedBtDevices).contains(Integer.valueOf(i))) {
                if (!((HashSet) this.mNearbyBleDevices).contains(Integer.valueOf(i))) {
                    if (!((HashSet) this.mSimulated).contains(Integer.valueOf(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final void legacyNotifyDevicePresenceEvent(AssociationInfo associationInfo, boolean z) {
        Slog.i("CDM_DevicePresenceProcessor", "legacyNotifyDevicePresenceEvent() association=[" + associationInfo.toShortString() + "], isAppeared=[" + z + "]");
        CompanionServiceConnector primaryServiceConnector = this.mCompanionAppBinder.getPrimaryServiceConnector(associationInfo.getUserId(), associationInfo.getPackageName());
        if (primaryServiceConnector == null) {
            Slog.e("CDM_DevicePresenceProcessor", "Package is not bound.");
        } else if (z) {
            primaryServiceConnector.post(new CompanionServiceConnector$$ExternalSyntheticLambda2(0, associationInfo));
        } else {
            primaryServiceConnector.post(new CompanionServiceConnector$$ExternalSyntheticLambda2(1, associationInfo));
        }
    }

    public final void notifyDevicePresenceEvent(int i, String str, DevicePresenceEvent devicePresenceEvent) {
        StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "notifyCompanionDevicePresenceEvent userId=[", "], packageName=[", str, "], event=[");
        m.append(devicePresenceEvent);
        m.append("]...");
        Slog.i("CDM_DevicePresenceProcessor", m.toString());
        CompanionServiceConnector primaryServiceConnector = this.mCompanionAppBinder.getPrimaryServiceConnector(i, str);
        if (primaryServiceConnector == null) {
            Slog.e("CDM_DevicePresenceProcessor", "Package is NOT bound.");
        } else {
            primaryServiceConnector.post(new CompanionServiceConnector$$ExternalSyntheticLambda2(devicePresenceEvent));
        }
    }

    public final void notifySelfManagedDevicePresenceEvent(int i, boolean z) {
        HermesService$3$$ExternalSyntheticOutline0.m(i, "notifySelfManagedDeviceAppeared() id=", "CDM_DevicePresenceProcessor");
        AssociationStore associationStore = this.mAssociationStore;
        AssociationInfo associationWithCallerChecks = associationStore.getAssociationWithCallerChecks(i);
        if (!associationWithCallerChecks.isSelfManaged()) {
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Association id=[", "] is not self-managed."));
        }
        AssociationInfo build = new AssociationInfo.Builder(associationWithCallerChecks).setLastTimeConnected(System.currentTimeMillis()).build();
        associationStore.updateAssociation(build);
        if (z) {
            onDevicePresenceEvent(this.mReportedSelfManagedDevices, i, 4);
        } else {
            onDevicePresenceEvent(this.mReportedSelfManagedDevices, i, 5);
        }
        String deviceProfile = build.getDeviceProfile();
        if ("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION".equals(deviceProfile)) {
            Slog.i("CDM_DevicePresenceProcessor", "Enable hint mode for device device profile: " + deviceProfile);
            this.mPowerManagerInternal.setPowerMode(18, z);
        }
    }

    @Override // com.android.server.companion.association.AssociationStore.OnChangeListener
    public final void onAssociationRemoved(AssociationInfo associationInfo) {
        int id = associationInfo.getId();
        ((HashSet) this.mConnectedBtDevices).remove(Integer.valueOf(id));
        ((HashSet) this.mNearbyBleDevices).remove(Integer.valueOf(id));
        ((HashSet) this.mReportedSelfManagedDevices).remove(Integer.valueOf(id));
        ((HashSet) this.mSimulated).remove(Integer.valueOf(id));
        synchronized (this.mBtDisconnectedDevices) {
            ((HashSet) this.mBtDisconnectedDevices).remove(Integer.valueOf(id));
            this.mBtDisconnectedDevicesBlePresence.delete(id);
        }
    }

    public final void onBleCompanionDeviceFound(int i, int i2) {
        BootReceiver$$ExternalSyntheticOutline0.m(i, "onBleCompanionDeviceFound associationId( ", " )", "CDM_DevicePresenceProcessor");
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i2)) {
            onDeviceLocked(i, i2, 0, null);
            return;
        }
        onDevicePresenceEvent(this.mNearbyBleDevices, i, 0);
        synchronized (this.mBtDisconnectedDevices) {
            try {
                boolean z = this.mBtDisconnectedDevicesBlePresence.get(i);
                if (((HashSet) this.mBtDisconnectedDevices).contains(Integer.valueOf(i)) && z) {
                    this.mBleDeviceDisappearedScheduler.unScheduleDeviceDisappeared(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onBluetoothCompanionDeviceConnected(int i, int i2) {
        BootReceiver$$ExternalSyntheticOutline0.m(i, "onBluetoothCompanionDeviceConnected: associationId( ", " )", "CDM_DevicePresenceProcessor");
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i2)) {
            onDeviceLocked(i, i2, 2, null);
            return;
        }
        synchronized (this.mBtDisconnectedDevices) {
            try {
                if (((HashSet) this.mBtDisconnectedDevices).contains(Integer.valueOf(i))) {
                    Slog.i("CDM_DevicePresenceProcessor", "Device ( " + i + " ) is reconnected within 10s.");
                    this.mBleDeviceDisappearedScheduler.unScheduleDeviceDisappeared(i);
                }
                Slog.i("CDM_DevicePresenceProcessor", "onBluetoothCompanionDeviceConnected: associationId( " + i + " )");
                onDevicePresenceEvent(this.mConnectedBtDevices, i, 2);
                if (canStopBleScan()) {
                    this.mBleDeviceProcessor.stopScanIfNeeded();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onBluetoothCompanionDeviceDisconnected(int i, int i2) {
        BootReceiver$$ExternalSyntheticOutline0.m(i, "onBluetoothCompanionDeviceDisconnected associationId( ", " )", "CDM_DevicePresenceProcessor");
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i2)) {
            onDeviceLocked(i, i2, 3, null);
            return;
        }
        this.mBleDeviceProcessor.startScan();
        onDevicePresenceEvent(this.mConnectedBtDevices, i, 3);
        if (((HashSet) this.mNearbyBleDevices).contains(Integer.valueOf(i))) {
            synchronized (this.mBtDisconnectedDevices) {
                ((HashSet) this.mBtDisconnectedDevices).add(Integer.valueOf(i));
            }
            BleDeviceDisappearedScheduler bleDeviceDisappearedScheduler = this.mBleDeviceDisappearedScheduler;
            if (bleDeviceDisappearedScheduler.hasMessages(i)) {
                bleDeviceDisappearedScheduler.removeMessages(i);
            }
            BootReceiver$$ExternalSyntheticOutline0.m(i, "scheduleBleDeviceDisappeared for Device: ( ", " ).", "CDM_DevicePresenceProcessor");
            bleDeviceDisappearedScheduler.sendEmptyMessageDelayed(i, 10000L);
        }
    }

    public final void onDeviceLocked(final int i, int i2, int i3, final ParcelUuid parcelUuid) {
        if (i3 != 0) {
            if (i3 == 1) {
                synchronized (this.mPendingDevicePresenceEvents) {
                    try {
                        List list = (List) this.mPendingDevicePresenceEvents.get(i2);
                        if (list != null) {
                            final int i4 = 0;
                            list.removeIf(new Predicate() { // from class: com.android.server.companion.devicepresence.DevicePresenceProcessor$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    switch (i4) {
                                        case 0:
                                            ParcelUuid parcelUuid2 = parcelUuid;
                                            int i5 = i;
                                            DevicePresenceEvent devicePresenceEvent = (DevicePresenceEvent) obj;
                                            if (devicePresenceEvent.getEvent() != 0 || !Objects.equals(devicePresenceEvent.getUuid(), parcelUuid2) || devicePresenceEvent.getAssociationId() != i5) {
                                            }
                                            break;
                                        default:
                                            ParcelUuid parcelUuid3 = parcelUuid;
                                            int i6 = i;
                                            DevicePresenceEvent devicePresenceEvent2 = (DevicePresenceEvent) obj;
                                            if (devicePresenceEvent2.getEvent() != 2 || !Objects.equals(devicePresenceEvent2.getUuid(), parcelUuid3) || devicePresenceEvent2.getAssociationId() != i6) {
                                            }
                                            break;
                                    }
                                    return false;
                                }
                            });
                        }
                    } finally {
                    }
                }
                return;
            }
            if (i3 != 2) {
                if (i3 != 3) {
                    FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i3, "Event: ", "is not supported", "CDM_DevicePresenceProcessor");
                    return;
                }
                synchronized (this.mPendingDevicePresenceEvents) {
                    try {
                        List list2 = (List) this.mPendingDevicePresenceEvents.get(i2);
                        if (list2 != null) {
                            final int i5 = 1;
                            list2.removeIf(new Predicate() { // from class: com.android.server.companion.devicepresence.DevicePresenceProcessor$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    switch (i5) {
                                        case 0:
                                            ParcelUuid parcelUuid2 = parcelUuid;
                                            int i52 = i;
                                            DevicePresenceEvent devicePresenceEvent = (DevicePresenceEvent) obj;
                                            if (devicePresenceEvent.getEvent() != 0 || !Objects.equals(devicePresenceEvent.getUuid(), parcelUuid2) || devicePresenceEvent.getAssociationId() != i52) {
                                            }
                                            break;
                                        default:
                                            ParcelUuid parcelUuid3 = parcelUuid;
                                            int i6 = i;
                                            DevicePresenceEvent devicePresenceEvent2 = (DevicePresenceEvent) obj;
                                            if (devicePresenceEvent2.getEvent() != 2 || !Objects.equals(devicePresenceEvent2.getUuid(), parcelUuid3) || devicePresenceEvent2.getAssociationId() != i6) {
                                            }
                                            break;
                                    }
                                    return false;
                                }
                            });
                        }
                    } finally {
                    }
                }
                return;
            }
        }
        Slog.i("CDM_DevicePresenceProcessor", "Current user is not in unlocking or unlocked stage yet. Notify the application when the phone is unlocked");
        synchronized (this.mPendingDevicePresenceEvents) {
            DevicePresenceEvent devicePresenceEvent = new DevicePresenceEvent(i, i3, parcelUuid);
            List list3 = (List) this.mPendingDevicePresenceEvents.get(i2, new ArrayList());
            list3.add(devicePresenceEvent);
            this.mPendingDevicePresenceEvents.put(i2, list3);
        }
    }

    public final void onDevicePresenceEvent(Set set, int i, int i2) {
        Slog.i("CDM_DevicePresenceProcessor", DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "onDevicePresenceEvent() id=[", "], event=[", "]..."));
        AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        if (associationById == null) {
            Slog.e("CDM_DevicePresenceProcessor", "Association doesn't exist.");
            return;
        }
        int userId = associationById.getUserId();
        String packageName = associationById.getPackageName();
        DevicePresenceEvent devicePresenceEvent = new DevicePresenceEvent(i, i2, (ParcelUuid) null);
        if (i2 == 0) {
            synchronized (this.mBtDisconnectedDevices) {
                try {
                    if (((HashSet) this.mBtDisconnectedDevices).contains(Integer.valueOf(i))) {
                        Slog.i("CDM_DevicePresenceProcessor", "Device ( " + i + " ) is present, do not need to send the callback with event ( 0 ).");
                        this.mBtDisconnectedDevicesBlePresence.append(i, true);
                    }
                } finally {
                }
            }
        }
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3) {
                        if (i2 != 4) {
                            if (i2 != 5) {
                                FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i2, "Event: ", " is not supported.", "CDM_DevicePresenceProcessor");
                                return;
                            }
                        }
                    }
                }
            }
            boolean remove = set.remove(Integer.valueOf(i));
            if (!remove) {
                Slog.w("CDM_DevicePresenceProcessor", "The association is already NOT present.");
            }
            if (!this.mCompanionAppBinder.isCompanionApplicationBound(userId, packageName)) {
                Slog.e("CDM_DevicePresenceProcessor", "Package is not bound");
                return;
            }
            if (associationById.isSelfManaged() || remove) {
                notifyDevicePresenceEvent(userId, packageName, devicePresenceEvent);
                legacyNotifyDevicePresenceEvent(associationById, false);
            }
            if (shouldBindPackage(userId, packageName)) {
                return;
            }
            this.mCompanionAppBinder.unbindCompanionApp(userId, packageName);
            return;
        }
        boolean add = set.add(Integer.valueOf(i));
        if (!add) {
            Slog.w("CDM_DevicePresenceProcessor", "The association is already present.");
        }
        if (associationById.shouldBindWhenPresent()) {
            bindApplicationIfNeeded(userId, packageName, associationById.isSelfManaged());
            if (associationById.isSelfManaged() || add) {
                notifyDevicePresenceEvent(userId, packageName, devicePresenceEvent);
                legacyNotifyDevicePresenceEvent(associationById, true);
            }
        }
    }

    public final void onDevicePresenceEventByUuid(ObservableUuid observableUuid, int i) {
        Slog.i("CDM_DevicePresenceProcessor", "onDevicePresenceEventByUuid ObservableUuid=[" + observableUuid + "], event=[" + i + "]...");
        ParcelUuid parcelUuid = observableUuid.mUuid;
        UserManager userManager = this.mUserManager;
        int i2 = observableUuid.mUserId;
        if (!userManager.isUserUnlockingOrUnlocked(i2)) {
            onDeviceLocked(-1, i2, i, parcelUuid);
            return;
        }
        DevicePresenceEvent devicePresenceEvent = new DevicePresenceEvent(-1, i, parcelUuid);
        String str = observableUuid.mPackageName;
        if (i == 2) {
            if (!((HashSet) this.mConnectedUuidDevices).add(parcelUuid)) {
                Slog.w("CDM_DevicePresenceProcessor", "This device is already connected.");
            }
            bindApplicationIfNeeded(i2, str, false);
            notifyDevicePresenceEvent(i2, str, devicePresenceEvent);
            return;
        }
        if (i != 3) {
            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i, "Event: ", " is not supported", "CDM_DevicePresenceProcessor");
            return;
        }
        if (!((HashSet) this.mConnectedUuidDevices).remove(parcelUuid)) {
            Slog.w("CDM_DevicePresenceProcessor", "This device is already disconnected.");
            return;
        }
        CompanionAppBinder companionAppBinder = this.mCompanionAppBinder;
        if (!companionAppBinder.isCompanionApplicationBound(i2, str)) {
            Slog.e("CDM_DevicePresenceProcessor", "Package is not bound.");
            return;
        }
        notifyDevicePresenceEvent(i2, str, devicePresenceEvent);
        if (shouldBindPackage(i2, str)) {
            return;
        }
        companionAppBinder.unbindCompanionApp(i2, str);
    }

    public final void sendDevicePresenceEventOnUnlocked(int i) {
        List<DevicePresenceEvent> list;
        List<ObservableUuid> readObservableUuidsFromCache;
        synchronized (this.mPendingDevicePresenceEvents) {
            list = (List) this.mPendingDevicePresenceEvents.get(i, new ArrayList());
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        ObservableUuidStore observableUuidStore = this.mObservableUuidStore;
        synchronized (observableUuidStore.mLock) {
            readObservableUuidsFromCache = observableUuidStore.readObservableUuidsFromCache(i);
        }
        for (DevicePresenceEvent devicePresenceEvent : list) {
            if (devicePresenceEvent.getUuid() != null) {
                for (ObservableUuid observableUuid : readObservableUuidsFromCache) {
                    if (observableUuid.mUuid.equals(devicePresenceEvent.getUuid())) {
                        onDevicePresenceEventByUuid(observableUuid, 2);
                    }
                }
            } else {
                int event = devicePresenceEvent.getEvent();
                AssociationInfo associationById = this.mAssociationStore.getAssociationById(devicePresenceEvent.getAssociationId());
                if (associationById == null) {
                    return;
                }
                if (event == 0) {
                    onBleCompanionDeviceFound(associationById.getId(), associationById.getUserId());
                } else if (event != 2) {
                    FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(event, "Event: ", "is not supported", "CDM_DevicePresenceProcessor");
                } else {
                    onBluetoothCompanionDeviceConnected(associationById.getId(), associationById.getUserId());
                }
            }
        }
        synchronized (this.mPendingDevicePresenceEvents) {
            try {
                if (this.mPendingDevicePresenceEvents.contains(i)) {
                    this.mPendingDevicePresenceEvents.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean shouldBindPackage(int i, String str) {
        List<AssociationInfo> activeAssociationsByPackage = this.mAssociationStore.getActiveAssociationsByPackage(i, str);
        List observableUuidsForPackage = this.mObservableUuidStore.getObservableUuidsForPackage(i, str);
        for (AssociationInfo associationInfo : activeAssociationsByPackage) {
            if (associationInfo.shouldBindWhenPresent() && isDevicePresent(associationInfo.getId())) {
                return true;
            }
        }
        Iterator it = ((ArrayList) observableUuidsForPackage).iterator();
        while (it.hasNext()) {
            if (((HashSet) this.mConnectedUuidDevices).contains(((ObservableUuid) it.next()).mUuid)) {
                return true;
            }
        }
        return false;
    }

    public final void simulateDeviceEvent(int i, int i2) {
        enforceCallerShellOrRoot();
        AssociationStore associationStore = this.mAssociationStore;
        if (associationStore.getAssociationById(i) == null) {
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Association with id ", " does not exist."));
        }
        AssociationInfo associationById = associationStore.getAssociationById(i);
        BleDeviceDisappearedScheduler bleDeviceDisappearedScheduler = this.mSchedulerHelper;
        if (i2 == 0) {
            onDevicePresenceEvent(this.mSimulated, i, i2);
            if (bleDeviceDisappearedScheduler.hasMessages(i)) {
                bleDeviceDisappearedScheduler.removeMessages(i);
            }
            bleDeviceDisappearedScheduler.sendEmptyMessageDelayed(i, 60000L);
            return;
        }
        if (i2 == 1) {
            bleDeviceDisappearedScheduler.removeMessages(i);
            onDevicePresenceEvent(this.mSimulated, i, i2);
        } else if (i2 == 2) {
            onBluetoothCompanionDeviceConnected(i, associationById.getUserId());
        } else {
            if (i2 != 3) {
                throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i2, "Event: ", "is not supported"));
            }
            onBluetoothCompanionDeviceDisconnected(i, associationById.getUserId());
        }
    }

    public final void startObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i, boolean z) {
        List readObservableUuidsFromCache;
        Slog.i("CDM_DevicePresenceProcessor", "Start observing request=[" + observingDevicePresenceRequest + "] for userId=[" + i + "], package=[" + str + "]...");
        ParcelUuid uuid = observingDevicePresenceRequest.getUuid();
        if (uuid != null) {
            if (z) {
                PermissionsUtils.enforceCallerCanObserveDevicePresenceByUuid(this.mContext, str, i);
            }
            if (this.mObservableUuidStore.isUuidBeingObserved(i, uuid, str)) {
                StringBuilder sb = new StringBuilder("UUID=[");
                sb.append(uuid);
                sb.append("], package=[");
                sb.append(str);
                sb.append("], userId=[");
                CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(sb, i, "] is already being observed.", "CDM_DevicePresenceProcessor");
                return;
            }
            final ObservableUuid observableUuid = new ObservableUuid(i, uuid, str, Long.valueOf(System.currentTimeMillis()));
            ObservableUuidStore observableUuidStore = this.mObservableUuidStore;
            observableUuidStore.getClass();
            Slog.i("CDM_ObservableUuidStore", "Writing uuid=[" + uuid + "] to store...");
            synchronized (observableUuidStore.mLock) {
                readObservableUuidsFromCache = observableUuidStore.readObservableUuidsFromCache(i);
                readObservableUuidsFromCache.removeIf(new Predicate() { // from class: com.android.server.companion.devicepresence.ObservableUuidStore$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        ObservableUuid observableUuid2 = ObservableUuid.this;
                        ObservableUuid observableUuid3 = (ObservableUuid) obj;
                        return observableUuid3.mUuid.equals(observableUuid2.mUuid) && observableUuid3.mPackageName.equals(observableUuid2.mPackageName);
                    }
                });
                readObservableUuidsFromCache.add(observableUuid);
                observableUuidStore.mCachedPerUser.set(i, readObservableUuidsFromCache);
            }
            observableUuidStore.mExecutor.execute(new ObservableUuidStore$$ExternalSyntheticLambda2(observableUuidStore, i, readObservableUuidsFromCache, 1));
        } else {
            int associationId = observingDevicePresenceRequest.getAssociationId();
            AssociationInfo associationWithCallerChecks = this.mAssociationStore.getAssociationWithCallerChecks(associationId);
            if (associationWithCallerChecks.isNotifyOnDeviceNearby()) {
                Slog.i("CDM_DevicePresenceProcessor", "Associated device id=[" + associationWithCallerChecks.getId() + "] is already being observed. No-op.");
                return;
            }
            this.mAssociationStore.updateAssociation(new AssociationInfo.Builder(associationWithCallerChecks).setNotifyOnDeviceNearby(true).build());
            if (isDevicePresent(associationId)) {
                Slog.i("CDM_DevicePresenceProcessor", "Device is already present. Triggering callback.");
                if (((HashSet) this.mNearbyBleDevices).contains(Integer.valueOf(associationId))) {
                    onDevicePresenceEvent(this.mNearbyBleDevices, associationId, 0);
                } else {
                    if (((HashSet) this.mConnectedBtDevices).contains(Integer.valueOf(associationId))) {
                        onDevicePresenceEvent(this.mConnectedBtDevices, associationId, 2);
                    } else {
                        if (((HashSet) this.mSimulated).contains(Integer.valueOf(associationId))) {
                            onDevicePresenceEvent(this.mSimulated, associationId, 0);
                        }
                    }
                }
            }
        }
        Slog.i("CDM_DevicePresenceProcessor", "Registered device presence listener.");
    }

    public final void stopObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i, boolean z) {
        Slog.i("CDM_DevicePresenceProcessor", "Stop observing request=[" + observingDevicePresenceRequest + "] for userId=[" + i + "], package=[" + str + "]...");
        ParcelUuid uuid = observingDevicePresenceRequest.getUuid();
        if (uuid != null) {
            if (z) {
                PermissionsUtils.enforceCallerCanObserveDevicePresenceByUuid(this.mContext, str, i);
            }
            ObservableUuidStore observableUuidStore = this.mObservableUuidStore;
            if (!observableUuidStore.isUuidBeingObserved(i, uuid, str)) {
                StringBuilder sb = new StringBuilder("UUID=[");
                sb.append(uuid);
                sb.append("], package=[");
                sb.append(str);
                sb.append("], userId=[");
                CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(sb, i, "] is already not being observed.", "CDM_DevicePresenceProcessor");
                return;
            }
            observableUuidStore.removeObservableUuid(i, uuid, str);
            ((HashSet) this.mConnectedUuidDevices).remove(uuid);
        } else {
            int associationId = observingDevicePresenceRequest.getAssociationId();
            AssociationStore associationStore = this.mAssociationStore;
            AssociationInfo associationWithCallerChecks = associationStore.getAssociationWithCallerChecks(associationId);
            if (!associationWithCallerChecks.isNotifyOnDeviceNearby()) {
                Slog.i("CDM_DevicePresenceProcessor", "Associated device id=[" + associationWithCallerChecks.getId() + "] is already not being observed. No-op.");
                return;
            }
            associationStore.updateAssociation(new AssociationInfo.Builder(associationWithCallerChecks).setNotifyOnDeviceNearby(false).build());
        }
        Slog.i("CDM_DevicePresenceProcessor", "Unregistered device presence listener.");
        if (shouldBindPackage(i, str)) {
            return;
        }
        this.mCompanionAppBinder.unbindCompanionApp(i, str);
    }
}
