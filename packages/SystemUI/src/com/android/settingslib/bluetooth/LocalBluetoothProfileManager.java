package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.HearingAidInfo;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settingslib.bluetooth.GattProfile;
import com.samsung.android.settingslib.bluetooth.SppProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LocalBluetoothProfileManager {
    public static final boolean DEBUG = BluetoothUtils.DEBUG;
    public A2dpProfile mA2dpProfile;
    public final Context mContext;
    public CsipSetCoordinatorProfile mCsipSetCoordinatorProfile;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final BluetoothEventManager mEventManager;
    public GattProfile mGattProfile;
    public HapClientProfile mHapClientProfile;
    public HeadsetProfile mHeadsetProfile;
    public HearingAidProfile mHearingAidProfile;
    public HidProfile mHidProfile;
    public LocalBluetoothLeBroadcast mLeAudioBroadcast;
    public LocalBluetoothLeBroadcastAssistant mLeAudioBroadcastAssistant;
    public LeAudioProfile mLeAudioProfile;
    public final LocalBluetoothAdapter mLocalAdapter;
    public MapClientProfile mMapClientProfile;
    public MapProfile mMapProfile;
    public OppProfile mOppProfile;
    public final PanProfile mPanProfile;
    public PbapServerProfile mPbapProfile;
    public final Map mProfileNameMap;
    public SapProfile mSapProfile;
    public final Collection mServiceListeners;
    public SppProfile mSppProfile;
    public VolumeControlProfile mVolumeControlProfile;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PanStateChangedHandler extends StateChangedHandler {
        public PanStateChangedHandler(LocalBluetoothProfileManager localBluetoothProfileManager, LocalBluetoothProfile localBluetoothProfile) {
            super(localBluetoothProfile);
        }

        @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.StateChangedHandler, com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            ((PanProfile) this.mProfile).setLocalRole(bluetoothDevice, intent.getIntExtra("android.bluetooth.pan.extra.LOCAL_ROLE", 0));
            super.onReceive(context, intent, bluetoothDevice);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ServiceListener {
        void onServiceConnected();

        void onServiceDisconnected();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class StateChangedHandler implements BluetoothEventManager.Handler {
        public final LocalBluetoothProfile mProfile;

        public StateChangedHandler(LocalBluetoothProfile localBluetoothProfile) {
            this.mProfile = localBluetoothProfile;
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            boolean z;
            int i;
            boolean onProfileConnectionStateChangedIfProcessed;
            boolean z2;
            String string;
            long j;
            int deviceSide;
            int deviceMode;
            if (this.mProfile == null) {
                Log.e("LocalBluetoothProfileManager", "StateChangedHandler :: mProfile is null");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
            CachedBluetoothDevice findDevice = LocalBluetoothProfileManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                boolean z3 = LocalBluetoothProfileManager.DEBUG;
                if (z3) {
                    Log.w("LocalBluetoothProfileManager", "StateChangedHandler found new device: " + bluetoothDevice);
                }
                if (bluetoothDevice.getBondState() == 10 && KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(context.getPackageName()) && intExtra == 0) {
                    Log.w("LocalBluetoothProfileManager", "StateChangedHandler: not create cached for devices that have already been unbonded");
                    return;
                }
                CachedBluetoothDevice addDevice = LocalBluetoothProfileManager.this.mDeviceManager.addDevice(bluetoothDevice);
                if (addDevice == null) {
                    if (z3) {
                        Log.w("LocalBluetoothProfileManager", "StateChangedHandler :: Can't add CachedDevice");
                        return;
                    }
                    return;
                }
                findDevice = addDevice;
            }
            boolean booleanExtra = intent.getBooleanExtra("android.bluetooth.profile.extra.isNormallyType", false);
            Log.d("LocalBluetoothProfileManager", "Profiles StateChangedHandler device : " + findDevice.getNameForLog() + ", mProfile : " + this.mProfile + ", new state : " + intExtra + ", old state : " + intExtra2 + ", normally type : " + booleanExtra);
            int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            int intExtra4 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
            if (intExtra3 == 0 && intExtra4 == 1) {
                Log.i("LocalBluetoothProfileManager", "Failed to connect " + this.mProfile + " device");
            }
            LocalBluetoothProfileManager localBluetoothProfileManager = LocalBluetoothProfileManager.this;
            boolean z4 = localBluetoothProfileManager.mHearingAidProfile != null && (this.mProfile instanceof HearingAidProfile);
            boolean z5 = (localBluetoothProfileManager.mHapClientProfile != null && (this.mProfile instanceof HapClientProfile)) || (localBluetoothProfileManager.mLeAudioProfile != null && (this.mProfile instanceof LeAudioProfile));
            boolean z6 = localBluetoothProfileManager.mCsipSetCoordinatorProfile != null && (this.mProfile instanceof CsipSetCoordinatorProfile);
            if (z4 && intExtra3 == 2) {
                if (LocalBluetoothProfileManager.DEBUG) {
                    Log.d("LocalBluetoothProfileManager", "onReceive, hearing aid profile connected, check hisyncid");
                }
                if (findDevice.getHiSyncId() == 0) {
                    HearingAidProfile hearingAidProfile = LocalBluetoothProfileManager.this.mHearingAidProfile;
                    BluetoothDevice bluetoothDevice2 = findDevice.mDevice;
                    BluetoothHearingAid bluetoothHearingAid = hearingAidProfile.mService;
                    if (bluetoothHearingAid == null || bluetoothDevice2 == null) {
                        i = intExtra2;
                        j = 0;
                    } else {
                        long hiSyncId = bluetoothHearingAid.getHiSyncId(bluetoothDevice2);
                        i = intExtra2;
                        j = hiSyncId;
                    }
                    if (j != 0) {
                        BluetoothDevice bluetoothDevice3 = findDevice.mDevice;
                        HearingAidInfo.Builder builder = new HearingAidInfo.Builder();
                        BluetoothHearingAid bluetoothHearingAid2 = LocalBluetoothProfileManager.this.mHearingAidProfile.mService;
                        if (bluetoothHearingAid2 == null) {
                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            deviceSide = -1;
                        } else {
                            deviceSide = bluetoothHearingAid2.getDeviceSide(bluetoothDevice3);
                        }
                        z = booleanExtra;
                        builder.mSide = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(deviceSide, -1);
                        BluetoothHearingAid bluetoothHearingAid3 = LocalBluetoothProfileManager.this.mHearingAidProfile.mService;
                        if (bluetoothHearingAid3 == null) {
                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            deviceMode = -1;
                        } else {
                            deviceMode = bluetoothHearingAid3.getDeviceMode(bluetoothDevice3);
                        }
                        builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(deviceMode, -1);
                        builder.mHiSyncId = j;
                        findDevice.setHearingAidInfo(builder.build());
                    } else {
                        z = booleanExtra;
                    }
                } else {
                    z = booleanExtra;
                    i = intExtra2;
                }
                HearingAidStatsLogUtils.logHearingAidInfo(findDevice);
            } else {
                z = booleanExtra;
                i = intExtra2;
            }
            if (z5 && intExtra3 == 2) {
                if (LocalBluetoothProfileManager.DEBUG) {
                    Log.d("LocalBluetoothProfileManager", "onReceive, hap/lea profile connected, check hearing aid info");
                }
                HapClientProfile hapClientProfile = findDevice.mProfileManager.mHapClientProfile;
                if (hapClientProfile != null && hapClientProfile.getConnectionStatus(findDevice.mDevice) == 2 && findDevice.isConnectedLeAudioDevice()) {
                    BluetoothDevice bluetoothDevice4 = findDevice.mDevice;
                    HearingAidInfo.Builder builder2 = new HearingAidInfo.Builder();
                    BluetoothLeAudio bluetoothLeAudio = LocalBluetoothProfileManager.this.mLeAudioProfile.mService;
                    builder2.setLeAudioLocation((bluetoothLeAudio == null || bluetoothDevice4 == null) ? 0 : bluetoothLeAudio.getAudioLocation(bluetoothDevice4));
                    BluetoothHapClient bluetoothHapClient = LocalBluetoothProfileManager.this.mHapClientProfile.mService;
                    builder2.mMode = HearingAidInfo.HAP_DEVICE_TYPE_TO_INTERNAL_MODE_MAPPING.get(bluetoothHapClient == null ? -1 : bluetoothHapClient.getHearingAidType(bluetoothDevice4), -1);
                    findDevice.setHearingAidInfo(builder2.build());
                    HearingAidStatsLogUtils.logHearingAidInfo(findDevice);
                }
            }
            if (z6 && intExtra3 == 2) {
                boolean z7 = LocalBluetoothProfileManager.DEBUG;
                if (z7) {
                    Log.d("LocalBluetoothProfileManager", "onReceive, csip profile connected, check group id");
                }
                if (findDevice.mGroupId == -1) {
                    CsipSetCoordinatorProfile csipSetCoordinatorProfile = LocalBluetoothProfileManager.this.mCsipSetCoordinatorProfile;
                    BluetoothDevice bluetoothDevice5 = findDevice.mDevice;
                    BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = csipSetCoordinatorProfile.mService;
                    Map groupUuidMapByDevice = (bluetoothCsipSetCoordinator == null || bluetoothDevice5 == null) ? null : bluetoothCsipSetCoordinator.getGroupUuidMapByDevice(bluetoothDevice5);
                    if (z7) {
                        Log.d("LocalBluetoothProfileManager", "csip group uuid map = " + groupUuidMapByDevice);
                    }
                    if (groupUuidMapByDevice != null) {
                        Iterator it = groupUuidMapByDevice.entrySet().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry entry = (Map.Entry) it.next();
                            if (((ParcelUuid) entry.getValue()).equals(BluetoothUuid.CAP)) {
                                findDevice.setGroupId(((Integer) entry.getKey()).intValue());
                                break;
                            }
                        }
                    }
                }
            }
            if (Set.of(21, 28, 22, 25).contains(Integer.valueOf(this.mProfile.getProfileId()))) {
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager = LocalBluetoothProfileManager.this.mDeviceManager;
                synchronized (cachedBluetoothDeviceManager) {
                    if (findDevice.isHearingDevice()) {
                        cachedBluetoothDeviceManager.mHearingAidDeviceManager.notifyDevicesConnectionStatusChanged();
                    }
                }
            }
            findDevice.onProfileStateChanged(this.mProfile, intExtra3);
            if (findDevice.getHiSyncId() == 0 && findDevice.mGroupId == -1) {
                z2 = true;
            } else {
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager2 = LocalBluetoothProfileManager.this.mDeviceManager;
                int profileId = this.mProfile.getProfileId();
                synchronized (cachedBluetoothDeviceManager2) {
                    if ((profileId == 28 || profileId == 21 || profileId == 25) && intExtra3 == 2) {
                        cachedBluetoothDeviceManager2.mHearingAidDeviceManager.syncDeviceIfNeeded(findDevice);
                    }
                }
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager3 = LocalBluetoothProfileManager.this.mDeviceManager;
                int profileId2 = this.mProfile.getProfileId();
                synchronized (cachedBluetoothDeviceManager3) {
                    if (profileId2 == 21) {
                        onProfileConnectionStateChangedIfProcessed = cachedBluetoothDeviceManager3.mHearingAidDeviceManager.onProfileConnectionStateChangedIfProcessed(findDevice, intExtra3);
                    } else if (profileId2 == 1 || profileId2 == 2 || profileId2 == 22 || profileId2 == 25) {
                        onProfileConnectionStateChangedIfProcessed = cachedBluetoothDeviceManager3.mCsipDeviceManager.onProfileConnectionStateChangedIfProcessed(findDevice, intExtra3);
                    } else {
                        onProfileConnectionStateChangedIfProcessed = false;
                    }
                }
                z2 = !onProfileConnectionStateChangedIfProcessed;
            }
            if (z2) {
                if (LocalBluetoothProfileManager.DEBUG) {
                    Log.d("LocalBluetoothProfileManager", "needDispatchProfileConnectionState");
                }
                findDevice.refresh();
                BluetoothEventManager bluetoothEventManager = LocalBluetoothProfileManager.this.mEventManager;
                int profileId3 = this.mProfile.getProfileId();
                Iterator it2 = ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).iterator();
                while (it2.hasNext()) {
                    ((BluetoothCallback) it2.next()).onProfileConnectionStateChanged(findDevice, intExtra3, profileId3);
                }
                if (bluetoothEventManager.mIsWorkProfile) {
                    Log.d("BluetoothEventManager", "Skip profileConnectionStateChanged for audio sharing, work profile");
                } else {
                    LocalBluetoothManager localBluetoothManager = bluetoothEventManager.mBtManager;
                    if (localBluetoothManager != null) {
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = localBluetoothManager.mProfileManager.mLeAudioBroadcast;
                    }
                    if (localBluetoothManager != null) {
                        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = localBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
                    }
                    if (profileId3 == 29 && intExtra3 == 0) {
                        boolean z8 = BluetoothUtils.DEBUG;
                    }
                }
            }
            BluetoothEventManager bluetoothEventManager2 = LocalBluetoothProfileManager.this.mEventManager;
            LocalBluetoothProfile localBluetoothProfile = this.mProfile;
            synchronized (bluetoothEventManager2.mSemCallbacks) {
                try {
                    ArrayList arrayList = (ArrayList) bluetoothEventManager2.mSemCallbacks;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((SBluetoothControllerImpl) obj).onProfileStateChanged(localBluetoothProfile, intExtra, i);
                    }
                } finally {
                }
            }
            LocalBluetoothManager localBluetoothManager2 = LocalBluetoothManager.getInstance(context, null);
            if (localBluetoothManager2 == null) {
                Log.e("LocalBluetoothProfileManager", "StateChangedHandler :: localBtManager is null");
                return;
            }
            if (intExtra == 0 && i == 1) {
                Log.d("LocalBluetoothProfileManager", "Failed to connect " + this.mProfile + " device");
                if (findDevice.isBusy() || findDevice.isConnected()) {
                    return;
                }
                if (this.mProfile.toString().equals("PAN")) {
                    Log.d("LocalBluetoothProfileManager", "PAN connection was rejected by NAP or Connection Timeout...");
                    int intExtra5 = intent.getIntExtra("android.bluetooth.pan.extra.LOCAL_ROLE", 0);
                    if (LocalBluetoothManager.mSystemUiInstance) {
                        if (localBluetoothManager2.semIsForegroundActivity() || localBluetoothManager2.isTetheredSettings()) {
                            return;
                        }
                        if (intExtra5 == 1) {
                            string = context.getString(R.string.bluetooth_connecting_error_message, findDevice.getName());
                        } else {
                            String name = findDevice.mBondState == 10 ? findDevice.mDeviceName : findDevice.getName();
                            if (BluetoothUtils.isRTL(context)) {
                                name = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("\u200e", name, "\u200e");
                            }
                            string = context.getString(R.string.bluetooth_pan_connecting_error_summury, name);
                        }
                        BluetoothUtils.showToast(context, string);
                        return;
                    }
                    if (localBluetoothManager2.semIsForegroundActivity() || localBluetoothManager2.isTetheredSettings()) {
                        if (intExtra5 == 1) {
                            findDevice.mErrorMsg = context.getString(R.string.bluetooth_pan_nap_connecting_error_summury);
                            return;
                        }
                        String name2 = findDevice.mBondState == 10 ? findDevice.mDeviceName : findDevice.getName();
                        if (BluetoothUtils.isRTL(context)) {
                            name2 = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("\u200e", name2, "\u200e");
                        }
                        String string2 = context.getString(R.string.bluetooth_pan_connecting_error_summury, name2);
                        if (localBluetoothManager2.semIsForegroundActivity()) {
                            findDevice.mErrorMsg = string2;
                            return;
                        } else {
                            if (localBluetoothManager2.isTetheredSettings()) {
                                BluetoothUtils.showToast(context, string2);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if (!this.mProfile.toString().equals(PeripheralConstants.ConnectionProfile.HID)) {
                    if (localBluetoothManager2.semIsForegroundActivity() || !LocalBluetoothManager.mSystemUiInstance) {
                        return;
                    }
                    if (findDevice.mGroupId != -1) {
                        CachedBluetoothDevice cachedBluetoothDevice = findDevice.mLeadDevice;
                        if (cachedBluetoothDevice == null) {
                            cachedBluetoothDevice = findDevice;
                        }
                        if (BluetoothUtils.getDeviceForGroupConnectionState(cachedBluetoothDevice).mCachedMaxConnectionState > 0) {
                            return;
                        }
                    }
                    BluetoothUtils.showToast(context, context.getString(R.string.bluetooth_connecting_error_message, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("\u200e", findDevice.getName(), "\u200e")));
                    return;
                }
                if (z) {
                    if (!LocalBluetoothManager.mSystemUiInstance) {
                        if (localBluetoothManager2.semIsForegroundActivity()) {
                            findDevice.mErrorMsg = context.getString(R.string.bluetooth_hid_normally_connecting_error_summury);
                            return;
                        }
                        return;
                    } else {
                        if (localBluetoothManager2.semIsForegroundActivity()) {
                            return;
                        }
                        String name3 = findDevice.getName();
                        if (BluetoothUtils.isRTL(context)) {
                            name3 = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("\u200e", name3, "\u200e");
                        }
                        BluetoothUtils.showToast(context, context.getString(R.string.bluetooth_connecting_error_message, name3));
                        return;
                    }
                }
                Log.d("LocalBluetoothProfileManager", "Failed to connect " + this.mProfile + " device");
                String name4 = findDevice.getName();
                if (BluetoothUtils.isRTL(context)) {
                    name4 = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("\u200e", name4, "\u200e");
                }
                String string3 = context.getString(R.string.bluetooth_hid_connecting_error_summury, name4);
                if (LocalBluetoothManager.mSystemUiInstance) {
                    if (localBluetoothManager2.semIsForegroundActivity()) {
                        return;
                    }
                    BluetoothUtils.showToast(context, string3);
                } else if (localBluetoothManager2.semIsForegroundActivity()) {
                    findDevice.mErrorMsg = string3;
                }
            }
        }
    }

    public LocalBluetoothProfileManager(Context context, LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, BluetoothEventManager bluetoothEventManager) {
        HashMap hashMap = new HashMap();
        this.mProfileNameMap = hashMap;
        this.mServiceListeners = new CopyOnWriteArrayList();
        this.mContext = context;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mEventManager = bluetoothEventManager;
        localBluetoothAdapter.mProfileManager = this;
        bluetoothEventManager.mProfileManager = this;
        this.mLocalAdapter = localBluetoothAdapter;
        if (this.mPanProfile == null) {
            PanProfile panProfile = new PanProfile(context, cachedBluetoothDeviceManager, this);
            this.mPanProfile = panProfile;
            bluetoothEventManager.addProfileHandler("android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED", new PanStateChangedHandler(this, panProfile));
            hashMap.put("PAN", panProfile);
        } else {
            Log.w("LocalBluetoothProfileManager", "Warning: PAN profile was previously added.");
        }
        if (this.mSapProfile == null) {
            SapProfile sapProfile = new SapProfile(context, cachedBluetoothDeviceManager, this);
            this.mSapProfile = sapProfile;
            addProfile(sapProfile, "SAP", "android.bluetooth.sap.profile.action.CONNECTION_STATE_CHANGED");
        } else {
            Log.w("LocalBluetoothProfileManager", "Warning: SAP profile was previously added.");
        }
        if (DEBUG) {
            Log.d("LocalBluetoothProfileManager", "LocalBluetoothProfileManager construction complete");
        }
    }

    public final void addProfile(LocalBluetoothProfile localBluetoothProfile, String str, String str2) {
        this.mEventManager.addProfileHandler(str2, new StateChangedHandler(localBluetoothProfile));
        ((HashMap) this.mProfileNameMap).put(str, localBluetoothProfile);
    }

    public final void callServiceConnectedListeners() {
        ArrayList arrayList = new ArrayList(this.mServiceListeners);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((ServiceListener) obj).onServiceConnected();
        }
    }

    public final void callServiceDisconnectedListeners() {
        ArrayList arrayList = new ArrayList(this.mServiceListeners);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((ServiceListener) obj).onServiceDisconnected();
        }
    }

    public HidDeviceProfile getHidDeviceProfile() {
        return null;
    }

    public final synchronized void updateLocalProfiles() {
        boolean z;
        Log.d("LocalBluetoothProfileManager", "updateLocalProfiles :: ");
        List supportedProfiles = BluetoothAdapter.getDefaultAdapter().getSupportedProfiles();
        if (CollectionUtils.isEmpty(supportedProfiles)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "supportedList is null");
            }
            return;
        }
        boolean z2 = true;
        if (this.mA2dpProfile == null && supportedProfiles.contains(2)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local A2DP profile");
            }
            A2dpProfile a2dpProfile = new A2dpProfile(this.mContext, this.mDeviceManager, this);
            this.mA2dpProfile = a2dpProfile;
            addProfile(a2dpProfile, "A2DP", "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        } else {
            z = false;
        }
        if (this.mHeadsetProfile == null && supportedProfiles.contains(1)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local HEADSET profile");
            }
            HeadsetProfile headsetProfile = new HeadsetProfile(this.mContext, this.mDeviceManager, this);
            this.mHeadsetProfile = headsetProfile;
            addProfile(headsetProfile, "HEADSET", "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mMapClientProfile == null && supportedProfiles.contains(18)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local MAP CLIENT profile");
            }
            MapClientProfile mapClientProfile = new MapClientProfile(this.mContext, this.mDeviceManager, this);
            this.mMapClientProfile = mapClientProfile;
            addProfile(mapClientProfile, "MAP Client", "android.bluetooth.mapmce.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mMapProfile == null && supportedProfiles.contains(9)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local MAP profile");
            }
            MapProfile mapProfile = new MapProfile(this.mContext, this.mDeviceManager, this);
            this.mMapProfile = mapProfile;
            addProfile(mapProfile, "MAP", "android.bluetooth.map.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mOppProfile == null && supportedProfiles.contains(20)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local OPP profile");
            }
            OppProfile oppProfile = new OppProfile();
            this.mOppProfile = oppProfile;
            ((HashMap) this.mProfileNameMap).put("OPP", oppProfile);
        }
        if (this.mHearingAidProfile == null && supportedProfiles.contains(21)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local Hearing Aid profile");
            }
            HearingAidProfile hearingAidProfile = new HearingAidProfile(this.mContext, this.mDeviceManager, this);
            this.mHearingAidProfile = hearingAidProfile;
            addProfile(hearingAidProfile, "HearingAid", "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mHapClientProfile == null && supportedProfiles.contains(28)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local HAP_CLIENT profile");
            }
            HapClientProfile hapClientProfile = new HapClientProfile(this.mContext, this.mDeviceManager, this);
            this.mHapClientProfile = hapClientProfile;
            addProfile(hapClientProfile, "HapClient", "android.bluetooth.action.HAP_CONNECTION_STATE_CHANGED");
        }
        if (this.mHidProfile == null && supportedProfiles.contains(4)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local HID_HOST profile");
            }
            HidProfile hidProfile = new HidProfile(this.mContext, this.mDeviceManager, this);
            this.mHidProfile = hidProfile;
            addProfile(hidProfile, PeripheralConstants.ConnectionProfile.HID, "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mPbapProfile == null && supportedProfiles.contains(6)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local PBAP profile");
            }
            PbapServerProfile pbapServerProfile = new PbapServerProfile(this.mContext);
            this.mPbapProfile = pbapServerProfile;
            addProfile(pbapServerProfile, PbapServerProfile.NAME, "android.bluetooth.pbap.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mSapProfile == null && supportedProfiles.contains(10)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local SAP profile");
            }
            SapProfile sapProfile = new SapProfile(this.mContext, this.mDeviceManager, this);
            this.mSapProfile = sapProfile;
            addProfile(sapProfile, "SAP", "android.bluetooth.sap.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mSppProfile == null) {
            Log.d("LocalBluetoothProfileManager", "Adding local Spp profile");
            SppProfile sppProfile = new SppProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
            this.mSppProfile = sppProfile;
            addProfile(sppProfile, PeripheralConstants.ConnectionProfile.SPP, "com.samsung.bluetooth.action.GEAR_CONNECTION_STATE_CHANGED");
            z = true;
        } else {
            Log.w("LocalBluetoothProfileManager", "updateLocalProfiles :: Spp profile was created already ");
        }
        if (this.mGattProfile == null) {
            Log.d("LocalBluetoothProfileManager", "Adding local Gatt profile");
            GattProfile gattProfile = new GattProfile(this.mContext, this.mLocalAdapter, this.mDeviceManager, this);
            this.mGattProfile = gattProfile;
            addProfile(gattProfile, "GATT", "com.samsung.bluetooth.action.GATT_CONNECTION_STATE_CHANGED");
        } else {
            z2 = z;
        }
        if (this.mVolumeControlProfile == null && supportedProfiles.contains(23)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local Volume Control profile");
            }
            VolumeControlProfile volumeControlProfile = new VolumeControlProfile(this.mContext, this.mDeviceManager, this);
            this.mVolumeControlProfile = volumeControlProfile;
            addProfile(volumeControlProfile, "VCP", "android.bluetooth.volume-control.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mLeAudioProfile == null && supportedProfiles.contains(22)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local LE_AUDIO profile");
            }
            LeAudioProfile leAudioProfile = new LeAudioProfile(this.mContext, this.mDeviceManager, this);
            this.mLeAudioProfile = leAudioProfile;
            addProfile(leAudioProfile, "LE_AUDIO", "android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
        }
        if (this.mLeAudioBroadcast == null && supportedProfiles.contains(26)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local LE_AUDIO_BROADCAST profile");
            }
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast = new LocalBluetoothLeBroadcast(this.mContext, this.mDeviceManager, this);
            this.mLeAudioBroadcast = localBluetoothLeBroadcast;
            ((HashMap) this.mProfileNameMap).put("LE_AUDIO_BROADCAST", localBluetoothLeBroadcast);
        }
        if (this.mLeAudioBroadcastAssistant == null && supportedProfiles.contains(29)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local LE_AUDIO_BROADCAST_ASSISTANT profile");
            }
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = new LocalBluetoothLeBroadcastAssistant(this.mContext, this.mDeviceManager, this);
            this.mLeAudioBroadcastAssistant = localBluetoothLeBroadcastAssistant;
            addProfile(localBluetoothLeBroadcastAssistant, "LE_AUDIO_BROADCAST", "android.bluetooth.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mCsipSetCoordinatorProfile == null && supportedProfiles.contains(25)) {
            if (DEBUG) {
                Log.d("LocalBluetoothProfileManager", "Adding local CSIP set coordinator profile");
            }
            CsipSetCoordinatorProfile csipSetCoordinatorProfile = new CsipSetCoordinatorProfile(this.mContext, this.mDeviceManager, this);
            this.mCsipSetCoordinatorProfile = csipSetCoordinatorProfile;
            addProfile(csipSetCoordinatorProfile, "CSIP Set Coordinator", "android.bluetooth.action.CSIS_CONNECTION_STATE_CHANGED");
        }
        if (z2) {
            this.mEventManager.registerProfileIntentReceiver();
        }
    }

    public final synchronized void updateProfiles(ParcelUuid[] parcelUuidArr, ParcelUuid[] parcelUuidArr2, Collection collection, Collection collection2, CachedBluetoothDevice cachedBluetoothDevice) {
        HidProfile hidProfile;
        SppProfile sppProfile;
        HearingAidProfile hearingAidProfile;
        PanProfile panProfile;
        OppProfile oppProfile;
        A2dpProfile a2dpProfile;
        LeAudioProfile leAudioProfile;
        try {
            collection2.clear();
            collection2.addAll(collection);
            boolean z = DEBUG;
            if (z) {
                Log.d("LocalBluetoothProfileManager", "Current Profiles" + collection.toString());
            }
            collection.clear();
            if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.LE_AUDIO) && (leAudioProfile = this.mLeAudioProfile) != null) {
                collection.add(leAudioProfile);
                collection2.remove(this.mLeAudioProfile);
            }
            if (this.mHeadsetProfile != null && ((ArrayUtils.contains(parcelUuidArr2, BluetoothUuid.HSP_AG) && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HSP)) || (ArrayUtils.contains(parcelUuidArr2, BluetoothUuid.HFP_AG) && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HFP)))) {
                collection.add(this.mHeadsetProfile);
                collection2.remove(this.mHeadsetProfile);
            }
            if (BluetoothUuid.containsAnyUuid(parcelUuidArr, A2dpProfile.SINK_UUIDS) && (a2dpProfile = this.mA2dpProfile) != null) {
                collection.add(a2dpProfile);
                collection2.remove(this.mA2dpProfile);
            }
            BluetoothUuid.containsAnyUuid(parcelUuidArr, A2dpSinkProfile.SRC_UUIDS);
            if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.OBEX_OBJECT_PUSH) && (oppProfile = this.mOppProfile) != null) {
                collection.add(oppProfile);
                collection2.remove(this.mOppProfile);
            }
            if ((ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HID) || ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HOGP)) && (hidProfile = this.mHidProfile) != null) {
                collection.add(hidProfile);
                collection2.remove(this.mHidProfile);
            }
            if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.NAP) && (panProfile = this.mPanProfile) != null) {
                collection.add(panProfile);
                collection2.remove(this.mPanProfile);
            }
            if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HEARING_AID) && (hearingAidProfile = this.mHearingAidProfile) != null) {
                collection.add(hearingAidProfile);
                collection2.remove(this.mHearingAidProfile);
            }
            if (this.mHapClientProfile != null && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HAS)) {
                collection.add(this.mHapClientProfile);
                collection2.remove(this.mHapClientProfile);
            }
            if (this.mVolumeControlProfile != null && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.VOLUME_CONTROL)) {
                collection.add(this.mVolumeControlProfile);
                collection2.remove(this.mVolumeControlProfile);
            }
            if (this.mCsipSetCoordinatorProfile != null && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.COORDINATED_SET)) {
                collection.add(this.mCsipSetCoordinatorProfile);
                collection2.remove(this.mCsipSetCoordinatorProfile);
            }
            if (this.mLeAudioBroadcastAssistant != null && ArrayUtils.contains(parcelUuidArr, BluetoothUuid.BASS)) {
                collection.add(this.mLeAudioBroadcastAssistant);
                collection2.remove(this.mLeAudioBroadcastAssistant);
            }
            if (cachedBluetoothDevice != null && cachedBluetoothDevice.getDeviceType() != 0 && (sppProfile = this.mSppProfile) != null) {
                collection.add(sppProfile);
                collection2.remove(this.mSppProfile);
            }
            if (collection2.contains(this.mSapProfile) && this.mSapProfile != null) {
                Log.d("LocalBluetoothProfileManager", "Adding back SAP profile");
                collection.add(this.mSapProfile);
                collection2.remove(this.mSapProfile);
            }
            if (collection2.contains(this.mMapProfile) && this.mMapProfile != null) {
                Log.d("LocalBluetoothProfileManager", "Adding back MAP profile");
                collection.add(this.mMapProfile);
                collection2.remove(this.mMapProfile);
            }
            if (collection2.contains(this.mPbapProfile) && this.mPbapProfile != null) {
                Log.d("LocalBluetoothProfileManager", "Adding back PBAP profile");
                collection.add(this.mPbapProfile);
                collection2.remove(this.mPbapProfile);
            }
            if (z) {
                Log.d("LocalBluetoothProfileManager", "New Profiles" + collection.toString());
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
