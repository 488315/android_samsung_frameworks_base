package com.android.server.media;

import android.R;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.android.media.flags.Flags;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LegacyBluetoothRouteController implements BluetoothRouteController {
    public BluetoothA2dp mA2dpProfile;
    public final AudioManager mAudioManager;
    public final BluetoothAdapter mBluetoothAdapter;
    public final Context mContext;
    public BluetoothHearingAid mHearingAidProfile;
    public BluetoothLeAudio mLeAudioProfile;
    public final BluetoothRouteController.BluetoothRoutesUpdatedListener mListener;
    public final Map mBluetoothRoutes = new HashMap();
    public final List mActiveRoutes = new ArrayList();
    public final SparseIntArray mVolumeMap = new SparseIntArray();
    public final BluetoothProfileListener mProfileListener = new BluetoothProfileListener();
    public final DeviceStateChangedReceiver mAdapterStateChangedReceiver =
            new DeviceStateChangedReceiver(this, 1);
    public final DeviceStateChangedReceiver mDeviceStateChangedReceiver =
            new DeviceStateChangedReceiver(this, 0);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BluetoothProfileListener implements BluetoothProfile.ServiceListener {
        public BluetoothProfileListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            List activeDevices;
            if (i == 2) {
                LegacyBluetoothRouteController legacyBluetoothRouteController =
                        LegacyBluetoothRouteController.this;
                legacyBluetoothRouteController.mA2dpProfile = (BluetoothA2dp) bluetoothProfile;
                activeDevices =
                        legacyBluetoothRouteController.mBluetoothAdapter.getActiveDevices(2);
            } else if (i == 21) {
                LegacyBluetoothRouteController legacyBluetoothRouteController2 =
                        LegacyBluetoothRouteController.this;
                legacyBluetoothRouteController2.mHearingAidProfile =
                        (BluetoothHearingAid) bluetoothProfile;
                activeDevices =
                        legacyBluetoothRouteController2.mBluetoothAdapter.getActiveDevices(21);
            } else {
                if (i != 22) {
                    return;
                }
                LegacyBluetoothRouteController legacyBluetoothRouteController3 =
                        LegacyBluetoothRouteController.this;
                legacyBluetoothRouteController3.mLeAudioProfile =
                        (BluetoothLeAudio) bluetoothProfile;
                activeDevices =
                        legacyBluetoothRouteController3.mBluetoothAdapter.getActiveDevices(22);
            }
            for (BluetoothDevice bluetoothDevice : bluetoothProfile.getConnectedDevices()) {
                BluetoothRouteInfo bluetoothRouteInfo =
                        (BluetoothRouteInfo)
                                ((HashMap) LegacyBluetoothRouteController.this.mBluetoothRoutes)
                                        .get(bluetoothDevice.getAddress());
                if (bluetoothRouteInfo == null) {
                    bluetoothRouteInfo =
                            LegacyBluetoothRouteController.this.createBluetoothRoute(
                                    bluetoothDevice);
                    ((HashMap) LegacyBluetoothRouteController.this.mBluetoothRoutes)
                            .put(bluetoothDevice.getAddress(), bluetoothRouteInfo);
                }
                if (activeDevices.contains(bluetoothDevice)) {
                    LegacyBluetoothRouteController.this.addActiveRoute(bluetoothRouteInfo);
                }
            }
            LegacyBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            if (i == 2) {
                LegacyBluetoothRouteController.this.mA2dpProfile = null;
            } else if (i == 21) {
                LegacyBluetoothRouteController.this.mHearingAidProfile = null;
            } else {
                if (i != 22) {
                    return;
                }
                LegacyBluetoothRouteController.this.mLeAudioProfile = null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BluetoothRouteInfo {
        public BluetoothDevice mBtDevice;
        public SparseBooleanArray mConnectedProfiles;
        public MediaRoute2Info mRoute;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceStateChangedReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ LegacyBluetoothRouteController this$0;

        public /* synthetic */ DeviceStateChangedReceiver(
                LegacyBluetoothRouteController legacyBluetoothRouteController, int i) {
            this.$r8$classId = i;
            this.this$0 = legacyBluetoothRouteController;
        }

        public void handleConnectionStateChanged(
                int i, Intent intent, BluetoothDevice bluetoothDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
            BluetoothRouteInfo bluetoothRouteInfo =
                    (BluetoothRouteInfo)
                            ((HashMap) this.this$0.mBluetoothRoutes)
                                    .get(bluetoothDevice.getAddress());
            if (intExtra == 2) {
                if (bluetoothRouteInfo != null) {
                    bluetoothRouteInfo.mConnectedProfiles.put(i, true);
                    return;
                }
                BluetoothRouteInfo createBluetoothRoute =
                        this.this$0.createBluetoothRoute(bluetoothDevice);
                if (createBluetoothRoute.mConnectedProfiles.size() > 0) {
                    ((HashMap) this.this$0.mBluetoothRoutes)
                            .put(bluetoothDevice.getAddress(), createBluetoothRoute);
                    this.this$0.notifyBluetoothRoutesUpdated();
                    return;
                }
                return;
            }
            if ((intExtra == 3 || intExtra == 0) && bluetoothRouteInfo != null) {
                bluetoothRouteInfo.mConnectedProfiles.delete(i);
                if (bluetoothRouteInfo.mConnectedProfiles.size() == 0) {
                    LegacyBluetoothRouteController legacyBluetoothRouteController = this.this$0;
                    BluetoothRouteInfo bluetoothRouteInfo2 =
                            (BluetoothRouteInfo)
                                    ((HashMap) legacyBluetoothRouteController.mBluetoothRoutes)
                                            .remove(bluetoothDevice.getAddress());
                    Log.d(
                            "LBtRouteProvider",
                            "Removing active route: " + bluetoothRouteInfo2.mRoute);
                    if (((ArrayList) legacyBluetoothRouteController.mActiveRoutes)
                            .remove(bluetoothRouteInfo2)) {
                        legacyBluetoothRouteController.setRouteConnectionState(
                                bluetoothRouteInfo2, 0);
                    }
                    this.this$0.notifyBluetoothRoutesUpdated();
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice;
            switch (this.$r8$classId) {
                case 0:
                    bluetoothDevice =
                            (BluetoothDevice)
                                    intent.getParcelableExtra(
                                            "android.bluetooth.device.extra.DEVICE",
                                            BluetoothDevice.class);
                    String action = intent.getAction();
                    action.getClass();
                    switch (action) {
                        case "android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED":
                            handleConnectionStateChanged(22, intent, bluetoothDevice);
                            break;
                        case "android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED":
                            LegacyBluetoothRouteController.m648$$Nest$mclearActiveRoutesWithType(
                                    this.this$0, 26);
                            if (bluetoothDevice != null) {
                                Log.d(
                                        "LBtRouteProvider",
                                        "Setting active le audio devices. device="
                                                + bluetoothDevice);
                                LegacyBluetoothRouteController.m647$$Nest$maddActiveDevices(
                                        this.this$0, bluetoothDevice);
                            }
                            this.this$0.notifyBluetoothRoutesUpdated();
                            break;
                        case "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED":
                            handleConnectionStateChanged(21, intent, bluetoothDevice);
                            break;
                        case "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED":
                            LegacyBluetoothRouteController.m648$$Nest$mclearActiveRoutesWithType(
                                    this.this$0, 8);
                            if (bluetoothDevice != null) {
                                Log.d(
                                        "LBtRouteProvider",
                                        "Setting active a2dp devices. device=" + bluetoothDevice);
                                LegacyBluetoothRouteController.m647$$Nest$maddActiveDevices(
                                        this.this$0, bluetoothDevice);
                            }
                            this.this$0.notifyBluetoothRoutesUpdated();
                            break;
                        case "android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED":
                            LegacyBluetoothRouteController.m648$$Nest$mclearActiveRoutesWithType(
                                    this.this$0, 23);
                            if (bluetoothDevice != null) {
                                Log.d(
                                        "LBtRouteProvider",
                                        "Setting active hearing aid devices. device="
                                                + bluetoothDevice);
                                LegacyBluetoothRouteController.m647$$Nest$maddActiveDevices(
                                        this.this$0, bluetoothDevice);
                            }
                            this.this$0.notifyBluetoothRoutesUpdated();
                            break;
                        case "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED":
                            handleConnectionStateChanged(2, intent, bluetoothDevice);
                            break;
                    }
                default:
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                    if (intExtra != 10 && intExtra != 13) {
                        if (intExtra == 12) {
                            this.this$0.buildBluetoothRoutes();
                            if (!((HashMap) this.this$0.mBluetoothRoutes).isEmpty()) {
                                this.this$0.notifyBluetoothRoutesUpdated();
                                break;
                            }
                        }
                    } else {
                        ((HashMap) this.this$0.mBluetoothRoutes).clear();
                        this.this$0.notifyBluetoothRoutesUpdated();
                        break;
                    }
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$maddActiveDevices, reason: not valid java name */
    public static void m647$$Nest$maddActiveDevices(
            LegacyBluetoothRouteController legacyBluetoothRouteController,
            BluetoothDevice bluetoothDevice) {
        BluetoothRouteInfo bluetoothRouteInfo =
                (BluetoothRouteInfo)
                        ((HashMap) legacyBluetoothRouteController.mBluetoothRoutes)
                                .get(bluetoothDevice.getAddress());
        if (bluetoothRouteInfo == null) {
            bluetoothRouteInfo =
                    legacyBluetoothRouteController.createBluetoothRoute(bluetoothDevice);
            ((HashMap) legacyBluetoothRouteController.mBluetoothRoutes)
                    .put(bluetoothDevice.getAddress(), bluetoothRouteInfo);
        }
        legacyBluetoothRouteController.addActiveRoute(bluetoothRouteInfo);
        for (BluetoothRouteInfo bluetoothRouteInfo2 :
                ((HashMap) legacyBluetoothRouteController.mBluetoothRoutes).values()) {
            if (TextUtils.equals(
                            bluetoothRouteInfo2.mRoute.getId(), bluetoothRouteInfo.mRoute.getId())
                    && !TextUtils.equals(
                            bluetoothRouteInfo2.mBtDevice.getAddress(),
                            bluetoothRouteInfo.mBtDevice.getAddress())) {
                legacyBluetoothRouteController.addActiveRoute(bluetoothRouteInfo2);
            }
        }
    }

    /* renamed from: -$$Nest$mclearActiveRoutesWithType, reason: not valid java name */
    public static void m648$$Nest$mclearActiveRoutesWithType(
            LegacyBluetoothRouteController legacyBluetoothRouteController, int i) {
        legacyBluetoothRouteController.getClass();
        Log.d("LBtRouteProvider", "Clearing active routes with type. type=" + i);
        Iterator it = ((ArrayList) legacyBluetoothRouteController.mActiveRoutes).iterator();
        while (it.hasNext()) {
            BluetoothRouteInfo bluetoothRouteInfo = (BluetoothRouteInfo) it.next();
            if (bluetoothRouteInfo.mRoute.getType() == i) {
                it.remove();
                legacyBluetoothRouteController.setRouteConnectionState(bluetoothRouteInfo, 0);
            }
        }
    }

    public LegacyBluetoothRouteController(
            Context context,
            BluetoothAdapter bluetoothAdapter,
            SystemMediaRoute2Provider$$ExternalSyntheticLambda2
                    systemMediaRoute2Provider$$ExternalSyntheticLambda2) {
        this.mContext = context;
        this.mBluetoothAdapter = bluetoothAdapter;
        this.mListener = systemMediaRoute2Provider$$ExternalSyntheticLambda2;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        buildBluetoothRoutes();
    }

    public final void addActiveRoute(BluetoothRouteInfo bluetoothRouteInfo) {
        Log.d("LBtRouteProvider", "Adding active route: " + bluetoothRouteInfo.mRoute);
        if (((ArrayList) this.mActiveRoutes).contains(bluetoothRouteInfo)) {
            Slog.w("LBtRouteProvider", "addActiveRoute: btRoute is already added.");
        } else {
            setRouteConnectionState(bluetoothRouteInfo, 2);
            ((ArrayList) this.mActiveRoutes).add(bluetoothRouteInfo);
        }
    }

    public final void buildBluetoothRoutes() {
        this.mBluetoothRoutes.clear();
        Set<BluetoothDevice> bondedDevices = this.mBluetoothAdapter.getBondedDevices();
        if (bondedDevices != null) {
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                if (bluetoothDevice.isConnected()) {
                    BluetoothRouteInfo createBluetoothRoute = createBluetoothRoute(bluetoothDevice);
                    if (createBluetoothRoute.mConnectedProfiles.size() > 0) {
                        this.mBluetoothRoutes.put(
                                bluetoothDevice.getAddress(), createBluetoothRoute);
                    }
                }
            }
        }
    }

    public final BluetoothRouteInfo createBluetoothRoute(BluetoothDevice bluetoothDevice) {
        int i;
        BluetoothRouteInfo bluetoothRouteInfo = new BluetoothRouteInfo();
        bluetoothRouteInfo.mBtDevice = bluetoothDevice;
        String address = bluetoothDevice.getAddress();
        String alias =
                Flags.enableUseOfBluetoothDeviceGetAliasForMr2infoGetName()
                        ? bluetoothDevice.getAlias()
                        : bluetoothDevice.getName();
        if (TextUtils.isEmpty(alias)) {
            alias = this.mContext.getResources().getText(R.string.unknownName).toString();
        }
        bluetoothRouteInfo.mConnectedProfiles = new SparseBooleanArray();
        BluetoothA2dp bluetoothA2dp = this.mA2dpProfile;
        if (bluetoothA2dp != null
                && bluetoothA2dp.getConnectedDevices().contains(bluetoothDevice)) {
            bluetoothRouteInfo.mConnectedProfiles.put(2, true);
        }
        BluetoothHearingAid bluetoothHearingAid = this.mHearingAidProfile;
        if (bluetoothHearingAid == null
                || !bluetoothHearingAid.getConnectedDevices().contains(bluetoothDevice)) {
            i = 8;
        } else {
            bluetoothRouteInfo.mConnectedProfiles.put(21, true);
            address = "HEARING_AID_" + this.mHearingAidProfile.getHiSyncId(bluetoothDevice);
            i = 23;
        }
        BluetoothLeAudio bluetoothLeAudio = this.mLeAudioProfile;
        if (bluetoothLeAudio != null
                && bluetoothLeAudio.getConnectedDevices().contains(bluetoothDevice)) {
            bluetoothRouteInfo.mConnectedProfiles.put(22, true);
            address = "LE_AUDIO_" + this.mLeAudioProfile.getGroupId(bluetoothDevice);
            i = 26;
        }
        bluetoothRouteInfo.mRoute =
                new MediaRoute2Info.Builder(address, alias)
                        .addFeature("android.media.route.feature.LIVE_AUDIO")
                        .addFeature("android.media.route.feature.LOCAL_PLAYBACK")
                        .setConnectionState(0)
                        .setDescription(
                                this.mContext
                                        .getResources()
                                        .getText(R.string.config_defaultWellbeingPackage)
                                        .toString())
                        .setType(i)
                        .setVolumeHandling(1)
                        .setVolumeMax(this.mAudioManager.getStreamMaxVolume(3))
                        .setAddress(bluetoothDevice.getAddress())
                        .build();
        return bluetoothRouteInfo;
    }

    @Override // com.android.server.media.BluetoothRouteController
    public final List getAllBluetoothRoutes() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        MediaRoute2Info selectedRoute = getSelectedRoute();
        if (selectedRoute != null) {
            arrayList.add(selectedRoute);
            arrayList2.add(selectedRoute.getId());
        }
        for (BluetoothRouteInfo bluetoothRouteInfo : ((HashMap) this.mBluetoothRoutes).values()) {
            if (!arrayList2.contains(bluetoothRouteInfo.mRoute.getId())) {
                arrayList.add(bluetoothRouteInfo.mRoute);
                arrayList2.add(bluetoothRouteInfo.mRoute.getId());
            }
        }
        return arrayList;
    }

    @Override // com.android.server.media.BluetoothRouteController
    public final MediaRoute2Info getSelectedRoute() {
        if (((ArrayList) this.mActiveRoutes).isEmpty()) {
            return null;
        }
        return ((BluetoothRouteInfo) ((ArrayList) this.mActiveRoutes).get(0)).mRoute;
    }

    @Override // com.android.server.media.BluetoothRouteController
    public final List getTransferableRoutes() {
        List allBluetoothRoutes = getAllBluetoothRoutes();
        Iterator it = ((ArrayList) this.mActiveRoutes).iterator();
        while (it.hasNext()) {
            ((ArrayList) allBluetoothRoutes).remove(((BluetoothRouteInfo) it.next()).mRoute);
        }
        return allBluetoothRoutes;
    }

    public final void notifyBluetoothRoutesUpdated() {
        BluetoothRouteController.BluetoothRoutesUpdatedListener bluetoothRoutesUpdatedListener =
                this.mListener;
        if (bluetoothRoutesUpdatedListener != null) {
            bluetoothRoutesUpdatedListener.onBluetoothRoutesUpdated();
        }
    }

    public final void setRouteConnectionState(BluetoothRouteInfo bluetoothRouteInfo, int i) {
        if (bluetoothRouteInfo.mRoute.getConnectionState() == i) {
            return;
        }
        MediaRoute2Info.Builder connectionState =
                new MediaRoute2Info.Builder(bluetoothRouteInfo.mRoute).setConnectionState(i);
        int i2 = 8;
        connectionState.setType(
                bluetoothRouteInfo.mConnectedProfiles.get(21, false)
                        ? 23
                        : bluetoothRouteInfo.mConnectedProfiles.get(22, false) ? 26 : 8);
        if (i == 2) {
            SparseIntArray sparseIntArray = this.mVolumeMap;
            if (bluetoothRouteInfo.mConnectedProfiles.get(21, false)) {
                i2 = 23;
            } else if (bluetoothRouteInfo.mConnectedProfiles.get(22, false)) {
                i2 = 26;
            }
            connectionState.setVolume(sparseIntArray.get(i2, 0));
        }
        bluetoothRouteInfo.mRoute = connectionState.build();
    }

    @Override // com.android.server.media.BluetoothRouteController
    public final void start(UserHandle userHandle) {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        Context context = this.mContext;
        BluetoothProfileListener bluetoothProfileListener = this.mProfileListener;
        bluetoothAdapter.getProfileProxy(context, bluetoothProfileListener, 2);
        this.mBluetoothAdapter.getProfileProxy(this.mContext, bluetoothProfileListener, 21);
        this.mBluetoothAdapter.getProfileProxy(this.mContext, bluetoothProfileListener, 22);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiverAsUser(
                this.mAdapterStateChangedReceiver, userHandle, intentFilter, null, null);
        IntentFilter m =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED",
                        "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED",
                        "android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED",
                        "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED",
                        "android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
        m.addAction("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED");
        this.mContext.registerReceiverAsUser(
                this.mDeviceStateChangedReceiver, userHandle, m, null, null);
    }

    @Override // com.android.server.media.BluetoothRouteController
    public final void stop() {
        this.mContext.unregisterReceiver(this.mAdapterStateChangedReceiver);
        this.mContext.unregisterReceiver(this.mDeviceStateChangedReceiver);
    }

    @Override // com.android.server.media.BluetoothRouteController
    public final void transferTo(String str) {
        BluetoothRouteInfo bluetoothRouteInfo;
        if (str == null) {
            BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
            if (bluetoothAdapter != null) {
                bluetoothAdapter.removeActiveDevice(0);
                return;
            }
            return;
        }
        Iterator it = ((HashMap) this.mBluetoothRoutes).values().iterator();
        while (true) {
            if (!it.hasNext()) {
                bluetoothRouteInfo = null;
                break;
            } else {
                bluetoothRouteInfo = (BluetoothRouteInfo) it.next();
                if (TextUtils.equals(bluetoothRouteInfo.mRoute.getId(), str)) {
                    break;
                }
            }
        }
        if (bluetoothRouteInfo == null) {
            Slog.w("LBtRouteProvider", "transferTo: Unknown route. ID=".concat(str));
            return;
        }
        BluetoothAdapter bluetoothAdapter2 = this.mBluetoothAdapter;
        if (bluetoothAdapter2 != null) {
            bluetoothAdapter2.setActiveDevice(bluetoothRouteInfo.mBtDevice, 0);
        }
    }

    @Override // com.android.server.media.BluetoothRouteController
    public final boolean updateVolumeForDevices(int i, int i2) {
        int i3;
        boolean z = false;
        if ((134217728 & i) != 0) {
            i3 = 23;
        } else if ((i & 896) != 0) {
            i3 = 8;
        } else {
            if ((i & 536870912) == 0) {
                return false;
            }
            i3 = 26;
        }
        this.mVolumeMap.put(i3, i2);
        Iterator it = ((ArrayList) this.mActiveRoutes).iterator();
        while (it.hasNext()) {
            BluetoothRouteInfo bluetoothRouteInfo = (BluetoothRouteInfo) it.next();
            if (bluetoothRouteInfo.mRoute.getType() == i3) {
                bluetoothRouteInfo.mRoute =
                        new MediaRoute2Info.Builder(bluetoothRouteInfo.mRoute)
                                .setVolume(i2)
                                .build();
                z = true;
            }
        }
        if (z) {
            notifyBluetoothRoutesUpdated();
        }
        return true;
    }
}
