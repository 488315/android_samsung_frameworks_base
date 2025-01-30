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
import android.util.SparseIntArray;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.HearingAidInfo;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;
import com.samsung.android.settingslib.bluetooth.SppProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LocalBluetoothProfileManager {
    public static final boolean DEBUG = BluetoothUtils.DEBUG;
    public A2dpProfile mA2dpProfile;
    public final Context mContext;
    public CsipSetCoordinatorProfile mCsipSetCoordinatorProfile;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final BluetoothEventManager mEventManager;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PanStateChangedHandler extends StateChangedHandler {
        public PanStateChangedHandler(LocalBluetoothProfileManager localBluetoothProfileManager, LocalBluetoothProfile localBluetoothProfile) {
            super(localBluetoothProfile);
        }

        @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.StateChangedHandler, com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            PanProfile panProfile = (PanProfile) this.mProfile;
            int intExtra = intent.getIntExtra("android.bluetooth.pan.extra.LOCAL_ROLE", 0);
            HashMap hashMap = panProfile.mDeviceRoleMap;
            if (hashMap != null) {
                if (panProfile.getConnectionStatus(bluetoothDevice) == 0) {
                    hashMap.remove(bluetoothDevice);
                } else {
                    hashMap.put(bluetoothDevice, Integer.valueOf(intExtra));
                }
            }
            super.onReceive(context, intent, bluetoothDevice);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ServiceListener {
        void onServiceConnected();

        void onServiceDisconnected();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class StateChangedHandler implements BluetoothEventManager.Handler {
        public final LocalBluetoothProfile mProfile;

        public StateChangedHandler(LocalBluetoothProfile localBluetoothProfile) {
            this.mProfile = localBluetoothProfile;
        }

        /* JADX WARN: Removed duplicated region for block: B:82:0x01af  */
        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            boolean onProfileConnectionStateChangedIfProcessed;
            boolean z;
            boolean z2;
            String string;
            boolean z3;
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
                boolean z4 = LocalBluetoothProfileManager.DEBUG;
                if (z4) {
                    Log.w("LocalBluetoothProfileManager", "StateChangedHandler found new device: " + bluetoothDevice);
                }
                if (bluetoothDevice.getBondState() == 10 && KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(context.getPackageName()) && intExtra == 0) {
                    Log.w("LocalBluetoothProfileManager", "StateChangedHandler: not create cached for devices that have already been unbonded");
                    return;
                }
                CachedBluetoothDevice addDevice = LocalBluetoothProfileManager.this.mDeviceManager.addDevice(bluetoothDevice);
                if (addDevice == null) {
                    if (z4) {
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
            if (LocalBluetoothProfileManager.this.mHearingAidProfile != null && (this.mProfile instanceof HearingAidProfile) && intExtra3 == 2) {
                if (findDevice.getHiSyncId() == 0) {
                    HearingAidProfile hearingAidProfile = LocalBluetoothProfileManager.this.mHearingAidProfile;
                    BluetoothDevice bluetoothDevice2 = findDevice.mDevice;
                    BluetoothHearingAid bluetoothHearingAid = hearingAidProfile.mService;
                    long hiSyncId = (bluetoothHearingAid == null || bluetoothDevice2 == null) ? 0L : bluetoothHearingAid.getHiSyncId(bluetoothDevice2);
                    if (hiSyncId != 0) {
                        BluetoothDevice bluetoothDevice3 = findDevice.mDevice;
                        HearingAidInfo.Builder builder = new HearingAidInfo.Builder();
                        BluetoothHearingAid bluetoothHearingAid2 = LocalBluetoothProfileManager.this.mHearingAidProfile.mService;
                        if (bluetoothHearingAid2 == null) {
                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            deviceSide = -1;
                        } else {
                            deviceSide = bluetoothHearingAid2.getDeviceSide(bluetoothDevice3);
                        }
                        builder.mSide = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(deviceSide, -1);
                        BluetoothHearingAid bluetoothHearingAid3 = LocalBluetoothProfileManager.this.mHearingAidProfile.mService;
                        if (bluetoothHearingAid3 == null) {
                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            deviceMode = -1;
                        } else {
                            deviceMode = bluetoothHearingAid3.getDeviceMode(bluetoothDevice3);
                        }
                        builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(deviceMode, -1);
                        builder.mHiSyncId = hiSyncId;
                        findDevice.mHearingAidInfo = builder.build();
                    }
                }
                HearingAidStatsLogUtils.logHearingAidInfo(findDevice);
            }
            LocalBluetoothProfileManager localBluetoothProfileManager = LocalBluetoothProfileManager.this;
            if (((localBluetoothProfileManager.mHapClientProfile != null && (this.mProfile instanceof HapClientProfile)) || (localBluetoothProfileManager.mLeAudioProfile != null && (this.mProfile instanceof LeAudioProfile))) && intExtra3 == 2) {
                HapClientProfile hapClientProfile = findDevice.mProfileManager.mHapClientProfile;
                if (hapClientProfile != null && hapClientProfile.getConnectionStatus(findDevice.mDevice) == 2) {
                    LeAudioProfile leAudioProfile = findDevice.mProfileManager.mLeAudioProfile;
                    if (leAudioProfile != null && leAudioProfile.getConnectionStatus(findDevice.mDevice) == 2) {
                        z3 = true;
                        if (z3) {
                            BluetoothDevice bluetoothDevice4 = findDevice.mDevice;
                            HearingAidInfo.Builder builder2 = new HearingAidInfo.Builder();
                            BluetoothLeAudio bluetoothLeAudio = LocalBluetoothProfileManager.this.mLeAudioProfile.mService;
                            int audioLocation = (bluetoothLeAudio == null || bluetoothDevice4 == null) ? 0 : bluetoothLeAudio.getAudioLocation(bluetoothDevice4);
                            SparseIntArray sparseIntArray = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING;
                            boolean z5 = (88413265 & audioLocation) != 0;
                            boolean z6 = (audioLocation & 176826530) != 0;
                            builder2.mSide = (z5 && z6) ? 2 : z5 ? 0 : z6 ? 1 : -1;
                            BluetoothHapClient bluetoothHapClient = LocalBluetoothProfileManager.this.mHapClientProfile.mService;
                            builder2.mMode = HearingAidInfo.HAP_DEVICE_TYPE_TO_INTERNAL_MODE_MAPPING.get(bluetoothHapClient == null ? -1 : bluetoothHapClient.getHearingAidType(bluetoothDevice4), -1);
                            findDevice.mHearingAidInfo = builder2.build();
                            HearingAidStatsLogUtils.logHearingAidInfo(findDevice);
                        }
                    }
                }
                z3 = false;
                if (z3) {
                }
            }
            CsipSetCoordinatorProfile csipSetCoordinatorProfile = LocalBluetoothProfileManager.this.mCsipSetCoordinatorProfile;
            if (csipSetCoordinatorProfile != null && (this.mProfile instanceof CsipSetCoordinatorProfile) && intExtra3 == 2 && findDevice.mGroupId == -1) {
                BluetoothDevice bluetoothDevice5 = findDevice.mDevice;
                BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = csipSetCoordinatorProfile.mService;
                Map groupUuidMapByDevice = (bluetoothCsipSetCoordinator == null || bluetoothDevice5 == null) ? null : bluetoothCsipSetCoordinator.getGroupUuidMapByDevice(bluetoothDevice5);
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
            findDevice.onProfileStateChanged(this.mProfile, intExtra3);
            if (findDevice.getHiSyncId() == 0 && findDevice.mGroupId == -1) {
                z2 = true;
            } else {
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager = LocalBluetoothProfileManager.this.mDeviceManager;
                int profileId = this.mProfile.getProfileId();
                synchronized (cachedBluetoothDeviceManager) {
                    if (profileId == 21) {
                        onProfileConnectionStateChangedIfProcessed = cachedBluetoothDeviceManager.mHearingAidDeviceManager.onProfileConnectionStateChangedIfProcessed(intExtra3, findDevice);
                    } else if (profileId == 1 || profileId == 2 || profileId == 22 || profileId == 25) {
                        onProfileConnectionStateChangedIfProcessed = cachedBluetoothDeviceManager.mCsipDeviceManager.onProfileConnectionStateChangedIfProcessed(intExtra3, findDevice);
                    } else {
                        z = true;
                        onProfileConnectionStateChangedIfProcessed = false;
                    }
                    z = true;
                }
                z2 = z ^ onProfileConnectionStateChangedIfProcessed;
            }
            if (z2) {
                findDevice.refresh();
                BluetoothEventManager bluetoothEventManager = LocalBluetoothProfileManager.this.mEventManager;
                int profileId2 = this.mProfile.getProfileId();
                Iterator it2 = ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).iterator();
                while (it2.hasNext()) {
                    ((BluetoothCallback) it2.next()).onProfileConnectionStateChanged(findDevice, intExtra3, profileId2);
                }
            }
            BluetoothEventManager bluetoothEventManager2 = LocalBluetoothProfileManager.this.mEventManager;
            LocalBluetoothProfile localBluetoothProfile = this.mProfile;
            synchronized (bluetoothEventManager2.mSemCallbacks) {
                Iterator it3 = ((ArrayList) bluetoothEventManager2.mSemCallbacks).iterator();
                while (it3.hasNext()) {
                    ((SBluetoothControllerImpl) ((SemBluetoothCallback) it3.next())).onProfileStateChanged(localBluetoothProfile, intExtra, intExtra2);
                }
            }
            LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
            if (localBluetoothManager == null) {
                Log.e("LocalBluetoothProfileManager", "StateChangedHandler :: localBtManager is null");
                return;
            }
            if (intExtra == 0 && intExtra2 == 1) {
                Log.d("LocalBluetoothProfileManager", "Failed to connect " + this.mProfile + " device");
                if (findDevice.isBusy() || findDevice.isConnected()) {
                    return;
                }
                if (this.mProfile.toString().equals("PAN")) {
                    Log.d("LocalBluetoothProfileManager", "PAN connection was rejected by NAP or Connection Timeout...");
                    int intExtra5 = intent.getIntExtra("android.bluetooth.pan.extra.LOCAL_ROLE", 0);
                    if (LocalBluetoothManager.mSystemUiInstance) {
                        if (localBluetoothManager.semIsForegroundActivity() || localBluetoothManager.isTetheredSettings()) {
                            return;
                        }
                        if (intExtra5 == 1) {
                            string = context.getString(R.string.bluetooth_connecting_error_message, findDevice.getName());
                        } else {
                            String name = findDevice.mBondState == 10 ? findDevice.mDeviceName : findDevice.getName();
                            if (BluetoothUtils.isRTL(context)) {
                                name = PathParser$$ExternalSyntheticOutline0.m29m("\u200e", name, "\u200e");
                            }
                            string = context.getString(R.string.bluetooth_pan_connecting_error_summury, name);
                        }
                        BluetoothUtils.showToast(context, string);
                        return;
                    }
                    if (localBluetoothManager.semIsForegroundActivity() || localBluetoothManager.isTetheredSettings()) {
                        if (intExtra5 == 1) {
                            findDevice.mErrorMsg = context.getString(R.string.bluetooth_pan_nap_connecting_error_summury);
                            return;
                        }
                        String name2 = findDevice.mBondState == 10 ? findDevice.mDeviceName : findDevice.getName();
                        if (BluetoothUtils.isRTL(context)) {
                            name2 = PathParser$$ExternalSyntheticOutline0.m29m("\u200e", name2, "\u200e");
                        }
                        String string2 = context.getString(R.string.bluetooth_pan_connecting_error_summury, name2);
                        if (localBluetoothManager.semIsForegroundActivity()) {
                            findDevice.mErrorMsg = string2;
                            return;
                        } else {
                            if (localBluetoothManager.isTetheredSettings()) {
                                BluetoothUtils.showToast(context, string2);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if (!this.mProfile.toString().equals(PeripheralConstants.ConnectionProfile.HID)) {
                    if (localBluetoothManager.semIsForegroundActivity() || !LocalBluetoothManager.mSystemUiInstance) {
                        return;
                    }
                    if (findDevice.mGroupId != -1) {
                        CachedBluetoothDevice cachedBluetoothDevice = findDevice.mLeadDevice;
                        if (cachedBluetoothDevice == null) {
                            cachedBluetoothDevice = findDevice;
                        }
                        if (BluetoothUtils.getDeviceForGroupConnectionState(cachedBluetoothDevice).getMaxConnectionState() > 0) {
                            return;
                        }
                    }
                    BluetoothUtils.showToast(context, context.getString(R.string.bluetooth_connecting_error_message, PathParser$$ExternalSyntheticOutline0.m29m("\u200e", findDevice.getName(), "\u200e")));
                    return;
                }
                if (booleanExtra) {
                    if (!LocalBluetoothManager.mSystemUiInstance) {
                        if (localBluetoothManager.semIsForegroundActivity()) {
                            findDevice.mErrorMsg = context.getString(R.string.bluetooth_hid_normally_connecting_error_summury);
                            return;
                        }
                        return;
                    } else {
                        if (localBluetoothManager.semIsForegroundActivity()) {
                            return;
                        }
                        String name3 = findDevice.getName();
                        if (BluetoothUtils.isRTL(context)) {
                            name3 = PathParser$$ExternalSyntheticOutline0.m29m("\u200e", name3, "\u200e");
                        }
                        BluetoothUtils.showToast(context, context.getString(R.string.bluetooth_connecting_error_message, name3));
                        return;
                    }
                }
                Log.d("LocalBluetoothProfileManager", "Failed to connect " + this.mProfile + " device");
                String name4 = findDevice.getName();
                if (BluetoothUtils.isRTL(context)) {
                    name4 = PathParser$$ExternalSyntheticOutline0.m29m("\u200e", name4, "\u200e");
                }
                String string3 = context.getString(R.string.bluetooth_hid_connecting_error_summury, name4);
                if (LocalBluetoothManager.mSystemUiInstance) {
                    if (localBluetoothManager.semIsForegroundActivity()) {
                        return;
                    }
                    BluetoothUtils.showToast(context, string3);
                } else if (localBluetoothManager.semIsForegroundActivity()) {
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
        Iterator it = new ArrayList(this.mServiceListeners).iterator();
        while (it.hasNext()) {
            ((ServiceListener) it.next()).onServiceConnected();
        }
    }

    public final void callServiceDisconnectedListeners() {
        Iterator it = new ArrayList(this.mServiceListeners).iterator();
        while (it.hasNext()) {
            ((ServiceListener) it.next()).onServiceDisconnected();
        }
    }

    public HidDeviceProfile getHidDeviceProfile() {
        return null;
    }

    public HidProfile getHidProfile() {
        return this.mHidProfile;
    }

    public final void updateLocalProfiles() {
        boolean z;
        Log.d("LocalBluetoothProfileManager", "updateLocalProfiles :: ");
        List supportedProfiles = BluetoothAdapter.getDefaultAdapter().getSupportedProfiles();
        boolean isEmpty = CollectionUtils.isEmpty(supportedProfiles);
        boolean z2 = DEBUG;
        if (isEmpty) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "supportedList is null");
                return;
            }
            return;
        }
        A2dpProfile a2dpProfile = this.mA2dpProfile;
        boolean z3 = true;
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
        Context context = this.mContext;
        if (a2dpProfile == null && supportedProfiles.contains(2)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local A2DP profile");
            }
            A2dpProfile a2dpProfile2 = new A2dpProfile(context, cachedBluetoothDeviceManager, this);
            this.mA2dpProfile = a2dpProfile2;
            addProfile(a2dpProfile2, "A2DP", "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        } else {
            z = false;
        }
        if (this.mHeadsetProfile == null && supportedProfiles.contains(1)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local HEADSET profile");
            }
            HeadsetProfile headsetProfile = new HeadsetProfile(context, cachedBluetoothDeviceManager, this);
            this.mHeadsetProfile = headsetProfile;
            addProfile(headsetProfile, "HEADSET", "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mMapClientProfile == null && supportedProfiles.contains(18)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local MAP CLIENT profile");
            }
            MapClientProfile mapClientProfile = new MapClientProfile(context, cachedBluetoothDeviceManager, this);
            this.mMapClientProfile = mapClientProfile;
            addProfile(mapClientProfile, "MAP Client", "android.bluetooth.mapmce.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mMapProfile == null && supportedProfiles.contains(9)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local MAP profile");
            }
            MapProfile mapProfile = new MapProfile(context, cachedBluetoothDeviceManager, this);
            this.mMapProfile = mapProfile;
            addProfile(mapProfile, "MAP", "android.bluetooth.map.profile.action.CONNECTION_STATE_CHANGED");
        }
        OppProfile oppProfile = this.mOppProfile;
        Map map = this.mProfileNameMap;
        if (oppProfile == null && supportedProfiles.contains(20)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local OPP profile");
            }
            OppProfile oppProfile2 = new OppProfile();
            this.mOppProfile = oppProfile2;
            ((HashMap) map).put("OPP", oppProfile2);
        }
        if (this.mHearingAidProfile == null && supportedProfiles.contains(21)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local Hearing Aid profile");
            }
            HearingAidProfile hearingAidProfile = new HearingAidProfile(context, cachedBluetoothDeviceManager, this);
            this.mHearingAidProfile = hearingAidProfile;
            addProfile(hearingAidProfile, "HearingAid", "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mHapClientProfile == null && supportedProfiles.contains(28)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local HAP_CLIENT profile");
            }
            HapClientProfile hapClientProfile = new HapClientProfile(context, cachedBluetoothDeviceManager, this);
            this.mHapClientProfile = hapClientProfile;
            addProfile(hapClientProfile, "HapClient", "android.bluetooth.action.HAP_CONNECTION_STATE_CHANGED");
        }
        if (this.mHidProfile == null && supportedProfiles.contains(4)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local HID_HOST profile");
            }
            HidProfile hidProfile = new HidProfile(context, cachedBluetoothDeviceManager, this);
            this.mHidProfile = hidProfile;
            addProfile(hidProfile, PeripheralConstants.ConnectionProfile.HID, "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mPbapProfile == null && supportedProfiles.contains(6)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local PBAP profile");
            }
            PbapServerProfile pbapServerProfile = new PbapServerProfile(context);
            this.mPbapProfile = pbapServerProfile;
            addProfile(pbapServerProfile, PbapServerProfile.NAME, "android.bluetooth.pbap.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mSapProfile == null && supportedProfiles.contains(10)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local SAP profile");
            }
            SapProfile sapProfile = new SapProfile(context, cachedBluetoothDeviceManager, this);
            this.mSapProfile = sapProfile;
            addProfile(sapProfile, "SAP", "android.bluetooth.sap.profile.action.CONNECTION_STATE_CHANGED");
            z = true;
        }
        if (this.mSppProfile == null) {
            Log.d("LocalBluetoothProfileManager", "Adding local Spp profile");
            SppProfile sppProfile = new SppProfile(context, this.mLocalAdapter, cachedBluetoothDeviceManager, this);
            this.mSppProfile = sppProfile;
            addProfile(sppProfile, PeripheralConstants.ConnectionProfile.SPP, "com.samsung.bluetooth.action.GEAR_CONNECTION_STATE_CHANGED");
        } else {
            Log.w("LocalBluetoothProfileManager", "updateLocalProfiles :: Spp profile was created already ");
            z3 = z;
        }
        if (this.mVolumeControlProfile == null && supportedProfiles.contains(23)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local Volume Control profile");
            }
            VolumeControlProfile volumeControlProfile = new VolumeControlProfile(context, cachedBluetoothDeviceManager, this);
            this.mVolumeControlProfile = volumeControlProfile;
            addProfile(volumeControlProfile, "VCP", "android.bluetooth.volume-control.profile.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mLeAudioProfile == null && supportedProfiles.contains(22)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local LE_AUDIO profile");
            }
            LeAudioProfile leAudioProfile = new LeAudioProfile(context, cachedBluetoothDeviceManager, this);
            this.mLeAudioProfile = leAudioProfile;
            addProfile(leAudioProfile, "LE_AUDIO", "android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
        }
        if (this.mLeAudioBroadcast == null && supportedProfiles.contains(26)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local LE_AUDIO_BROADCAST profile");
            }
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast = new LocalBluetoothLeBroadcast(context, this);
            this.mLeAudioBroadcast = localBluetoothLeBroadcast;
            ((HashMap) map).put("LE_AUDIO_BROADCAST", localBluetoothLeBroadcast);
        }
        if (this.mLeAudioBroadcastAssistant == null && supportedProfiles.contains(29)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local LE_AUDIO_BROADCAST_ASSISTANT profile");
            }
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = new LocalBluetoothLeBroadcastAssistant(context, cachedBluetoothDeviceManager, this);
            this.mLeAudioBroadcastAssistant = localBluetoothLeBroadcastAssistant;
            addProfile(localBluetoothLeBroadcastAssistant, "LE_AUDIO_BROADCAST", "android.bluetooth.action.CONNECTION_STATE_CHANGED");
        }
        if (this.mCsipSetCoordinatorProfile == null && supportedProfiles.contains(25)) {
            if (z2) {
                Log.d("LocalBluetoothProfileManager", "Adding local CSIP set coordinator profile");
            }
            CsipSetCoordinatorProfile csipSetCoordinatorProfile = new CsipSetCoordinatorProfile(context, cachedBluetoothDeviceManager, this);
            this.mCsipSetCoordinatorProfile = csipSetCoordinatorProfile;
            addProfile(csipSetCoordinatorProfile, "CSIP Set Coordinator", "android.bluetooth.action.CSIS_CONNECTION_STATE_CHANGED");
        }
        if (z3) {
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
    }
}
