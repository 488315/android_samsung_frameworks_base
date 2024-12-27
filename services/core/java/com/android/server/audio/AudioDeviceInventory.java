package com.android.server.audio;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.hardware.display.DisplayManager;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioRoutesInfo;
import android.media.AudioSystem;
import android.media.MediaMetrics;
import android.media.Utils;
import android.media.audio.Flags;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.SafeCloseable;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.DesktopModeSettings;
import com.android.server.utils.EventLogger;

import com.google.android.collect.Sets;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.server.audio.AudioExecutor;
import com.samsung.android.server.audio.DesktopModeHelper;
import com.samsung.android.server.audio.FactoryUtils;
import com.samsung.android.server.audio.MultiSoundManager;
import com.samsung.android.server.audio.ScreenSharingHelper;
import com.samsung.android.server.audio.utils.AudioUtils;
import com.samsung.android.server.audio.utils.BtUtils;
import com.samsung.android.server.audio.utils.KnoxAudioUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AudioDeviceInventory {
    public static final Set BECOMING_NOISY_INTENT_DEVICES_SET;
    public static final int[] CAPTURE_PRESETS;
    public static final Set DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET;
    public final ArrayList mApmConnectedDevices;
    public final ArrayMap mAppliedPresetRoles;
    public final ArrayMap mAppliedPresetRolesInt;
    public final ArrayMap mAppliedStrategyRoles;
    public final ArrayMap mAppliedStrategyRolesInt;
    public final AudioSystemAdapter mAudioSystem;
    public final boolean mBluetoothDualModeEnabled;
    public final LinkedHashMap mConnectedDevices;
    public final AudioRoutesInfo mCurAudioRoutes;
    public final RemoteCallbackList mDevRoleCapturePresetDispatchers;
    public final AudioDeviceBroker mDeviceBroker;
    public final LinkedHashMap mDeviceInventory;
    public final Object mDeviceInventoryLock;
    public final Object mDevicesLock;
    public String mForcePath;
    public final RemoteCallbackList mNonDefDevDispatchers;
    public final ArrayMap mNonDefaultDevices;
    public final RemoteCallbackList mPrefDevDispatchers;
    public final ArrayMap mPreferredDevices;
    public final ArrayMap mPreferredDevicesForCapturePreset;
    public final RemoteCallbackList mRoutesObservers;
    public final List mStrategies;
    public boolean mSystemReady;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceInfo {
        public final String mDeviceAddress;
        public int mDeviceCodecFormat;
        public String mDeviceIdentityAddress;
        public final String mDeviceName;
        public final int mDeviceType;
        public final ArraySet mDisabledModes;
        public final int mGroupId;
        public String mPeerDeviceAddress;
        public String mPeerIdentityDeviceAddress;

        public DeviceInfo(int i, int i2, String str, String str2, String str3) {
            this(i, str, str2, str3, i2, -1, null, null);
        }

        public DeviceInfo(int i, String str, String str2) {
            this(i, 0, str, str2, null);
        }

        public DeviceInfo(
                int i,
                String str,
                String str2,
                String str3,
                int i2,
                int i3,
                String str4,
                String str5) {
            this.mDisabledModes = new ArraySet(0);
            this.mDeviceType = i;
            this.mDeviceName = TextUtils.emptyIfNull(str);
            String emptyIfNull = TextUtils.emptyIfNull(str2);
            this.mDeviceAddress = emptyIfNull;
            String emptyIfNull2 = TextUtils.emptyIfNull(str3);
            this.mDeviceIdentityAddress = emptyIfNull2;
            if (emptyIfNull2.isEmpty()) {
                this.mDeviceIdentityAddress = emptyIfNull;
            }
            this.mDeviceCodecFormat = i2;
            this.mGroupId = i3;
            this.mPeerDeviceAddress = TextUtils.emptyIfNull(str4);
            this.mPeerIdentityDeviceAddress = TextUtils.emptyIfNull(str5);
        }

        public static String makeDeviceListKey(int i, String str) {
            return "0x" + Integer.toHexString(i) + ":" + str;
        }

        public final boolean isModeEnabled(String str) {
            return !this.mDisabledModes.contains(str);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[DeviceInfo: type:0x");
            int i = this.mDeviceType;
            sb.append(Integer.toHexString(i));
            sb.append(" (");
            sb.append(AudioSystem.getDeviceName(i));
            sb.append(") name:");
            sb.append(this.mDeviceName);
            sb.append(" addr:");
            sb.append(Utils.anonymizeBluetoothAddress(i, this.mDeviceAddress));
            sb.append(" identity addr:");
            sb.append(Utils.anonymizeBluetoothAddress(i, this.mDeviceIdentityAddress));
            sb.append(" codec: ");
            BatteryService$$ExternalSyntheticOutline0.m(this.mDeviceCodecFormat, sb, " group:");
            sb.append(this.mGroupId);
            sb.append(" peer addr:");
            sb.append(Utils.anonymizeBluetoothAddress(i, this.mPeerDeviceAddress));
            sb.append(" peer identity addr:");
            sb.append(Utils.anonymizeBluetoothAddress(i, this.mPeerIdentityDeviceAddress));
            sb.append(" disabled modes: ");
            sb.append(this.mDisabledModes);
            sb.append("]");
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SetForceDeviceState {
        public final String mActiveBTDeviceName;
        public final String mAddress;
        public final int mDevice;

        public SetForceDeviceState(int i, String str, String str2) {
            this.mDevice = i;
            this.mAddress = str;
            this.mActiveBTDeviceName = str2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WiredDeviceConnectionState {
        public final AudioDeviceAttributes mAttributes;
        public final String mCaller;
        public boolean mForTest = false;
        public final int mState;

        public WiredDeviceConnectionState(
                AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
            this.mAttributes = audioDeviceAttributes;
            this.mState = i;
            this.mCaller = str;
        }
    }

    static {
        HashSet hashSet = new HashSet();
        DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET = hashSet;
        hashSet.add(4);
        hashSet.add(8);
        hashSet.add(131072);
        Set set = AudioSystem.DEVICE_OUT_ALL_USB_SET;
        hashSet.addAll(set);
        CAPTURE_PRESETS = new int[] {1, 5, 6, 7, 9, 10, 1999};
        HashSet hashSet2 = new HashSet();
        BECOMING_NOISY_INTENT_DEVICES_SET = hashSet2;
        hashSet2.add(4);
        hashSet2.add(8);
        hashSet2.add(1024);
        hashSet2.add(2048);
        hashSet2.add(131072);
        hashSet2.add(134217728);
        hashSet2.add(536870912);
        hashSet2.add(536870914);
        hashSet2.addAll(AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
        hashSet2.addAll(set);
        hashSet2.addAll(AudioSystem.DEVICE_OUT_ALL_BLE_SET);
    }

    public AudioDeviceInventory(AudioDeviceBroker audioDeviceBroker) {
        AudioSystemAdapter defaultAdapter = AudioSystemAdapter.getDefaultAdapter();
        this.mDevicesLock = new Object();
        this.mDeviceInventoryLock = new Object();
        this.mDeviceInventory = new LinkedHashMap();
        this.mConnectedDevices =
                new LinkedHashMap() { // from class: com.android.server.audio.AudioDeviceInventory.1
                    public static void record(String str, boolean z, DeviceInfo deviceInfo) {
                        new MediaMetrics.Item(
                                        "audio.device."
                                                + AudioSystem.getDeviceName(deviceInfo.mDeviceType))
                                .set(MediaMetrics.Property.ADDRESS, deviceInfo.mDeviceAddress)
                                .set(MediaMetrics.Property.EVENT, str)
                                .set(MediaMetrics.Property.NAME, deviceInfo.mDeviceName)
                                .set(MediaMetrics.Property.STATE, z ? "connected" : "disconnected")
                                .record();
                    }

                    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
                    public final Object put(Object obj, Object obj2) {
                        DeviceInfo deviceInfo = (DeviceInfo) obj2;
                        DeviceInfo deviceInfo2 = (DeviceInfo) super.put((String) obj, deviceInfo);
                        record("put", true, deviceInfo);
                        return deviceInfo2;
                    }

                    @Override // java.util.HashMap, java.util.Map
                    public final Object putIfAbsent(Object obj, Object obj2) {
                        DeviceInfo deviceInfo = (DeviceInfo) obj2;
                        DeviceInfo deviceInfo2 =
                                (DeviceInfo) super.putIfAbsent((String) obj, deviceInfo);
                        if (deviceInfo2 == null) {
                            record("putIfAbsent", true, deviceInfo);
                        }
                        return deviceInfo2;
                    }

                    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
                    public final Object remove(Object obj) {
                        DeviceInfo deviceInfo = (DeviceInfo) super.remove(obj);
                        if (deviceInfo != null) {
                            record("remove", false, deviceInfo);
                        }
                        return deviceInfo;
                    }

                    @Override // java.util.HashMap, java.util.Map
                    public final boolean remove(Object obj, Object obj2) {
                        boolean remove = super.remove(obj, obj2);
                        if (remove) {
                            record("remove", false, (DeviceInfo) obj2);
                        }
                        return remove;
                    }
                };
        this.mApmConnectedDevices = new ArrayList();
        this.mPreferredDevices = new ArrayMap();
        this.mNonDefaultDevices = new ArrayMap();
        this.mPreferredDevicesForCapturePreset = new ArrayMap();
        this.mCurAudioRoutes = new AudioRoutesInfo();
        this.mRoutesObservers = new RemoteCallbackList();
        this.mPrefDevDispatchers = new RemoteCallbackList();
        this.mNonDefDevDispatchers = new RemoteCallbackList();
        this.mDevRoleCapturePresetDispatchers = new RemoteCallbackList();
        this.mAppliedStrategyRoles = new ArrayMap();
        this.mAppliedStrategyRolesInt = new ArrayMap();
        this.mAppliedPresetRoles = new ArrayMap();
        this.mAppliedPresetRolesInt = new ArrayMap();
        this.mSystemReady = false;
        this.mForcePath = "";
        this.mDeviceBroker = audioDeviceBroker;
        this.mAudioSystem = defaultAdapter;
        this.mStrategies = AudioProductStrategy.getAudioProductStrategies();
        this.mBluetoothDualModeEnabled =
                SystemProperties.getBoolean("persist.bluetooth.enable_dual_mode_audio", false);
    }

    public static int addDevicesRole(
            ArrayMap arrayMap,
            AudioDeviceInventory$$ExternalSyntheticLambda2
                    audioDeviceInventory$$ExternalSyntheticLambda2,
            int i,
            List list) {
        synchronized (arrayMap) {
            try {
                Pair pair = new Pair(Integer.valueOf(i), 2);
                List arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                if (arrayMap.containsKey(pair)) {
                    arrayList = (List) arrayMap.get(pair);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        AudioDeviceAttributes audioDeviceAttributes =
                                (AudioDeviceAttributes) it.next();
                        if (!arrayList.contains(audioDeviceAttributes)) {
                            arrayList2.add(audioDeviceAttributes);
                        }
                    }
                } else {
                    arrayList2.addAll(list);
                }
                if (arrayList2.isEmpty()) {
                    return 0;
                }
                int deviceRoleAction =
                        audioDeviceInventory$$ExternalSyntheticLambda2.deviceRoleAction(
                                i, 2, arrayList2);
                if (deviceRoleAction == 0) {
                    arrayList.addAll(arrayList2);
                    arrayMap.put(pair, arrayList);
                }
                return deviceRoleAction;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static boolean isBtStateConnected(AudioDeviceBroker.BtDeviceInfo btDeviceInfo) {
        int i = btDeviceInfo.mState;
        return i == 2 || i == 99;
    }

    public static void purgeRoles(
            ArrayMap arrayMap,
            AudioDeviceInventory$$ExternalSyntheticLambda2
                    audioDeviceInventory$$ExternalSyntheticLambda2) {
        synchronized (arrayMap) {
            try {
                AudioDeviceInfo[] devicesStatic = AudioManager.getDevicesStatic(3);
                Iterator it = arrayMap.entrySet().iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) ((Map.Entry) it.next()).getKey();
                    Iterator it2 = ((List) arrayMap.get(pair)).iterator();
                    while (it2.hasNext()) {
                        AudioDeviceAttributes audioDeviceAttributes =
                                (AudioDeviceAttributes) it2.next();
                        if (((AudioDeviceInfo)
                                        Stream.of((Object[]) devicesStatic)
                                                .filter(
                                                        new AudioDeviceInventory$$ExternalSyntheticLambda34(
                                                                0, audioDeviceAttributes))
                                                .filter(
                                                        new AudioDeviceInventory$$ExternalSyntheticLambda34(
                                                                1, audioDeviceAttributes))
                                                .findFirst()
                                                .orElse(null))
                                == null) {
                            Slog.i(
                                    "AS.AudioDeviceInventory",
                                    "purgeRoles() removing device: "
                                            + audioDeviceAttributes.toString()
                                            + ", for strategy: "
                                            + pair.first
                                            + " and role: "
                                            + pair.second);
                            audioDeviceInventory$$ExternalSyntheticLambda2.deviceRoleAction(
                                    ((Integer) pair.first).intValue(),
                                    ((Integer) pair.second).intValue(),
                                    Arrays.asList(audioDeviceAttributes));
                            it2.remove();
                        }
                    }
                    if (((List) arrayMap.get(pair)).isEmpty()) {
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static int removeDevicesRole(
            ArrayMap arrayMap,
            AudioDeviceInventory$$ExternalSyntheticLambda2
                    audioDeviceInventory$$ExternalSyntheticLambda2,
            int i,
            List list) {
        synchronized (arrayMap) {
            try {
                Pair pair = new Pair(Integer.valueOf(i), 2);
                if (!arrayMap.containsKey(pair)) {
                    return -2;
                }
                List list2 = (List) arrayMap.get(pair);
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) it.next();
                    if (list2.contains(audioDeviceAttributes)) {
                        arrayList.add(audioDeviceAttributes);
                    }
                }
                if (arrayList.isEmpty()) {
                    return 0;
                }
                int deviceRoleAction =
                        audioDeviceInventory$$ExternalSyntheticLambda2.deviceRoleAction(
                                i, 2, arrayList);
                if (deviceRoleAction == 0) {
                    list2.removeAll(arrayList);
                    if (list2.isEmpty()) {
                        arrayMap.remove(pair);
                    } else {
                        arrayMap.put(pair, list2);
                    }
                }
                return deviceRoleAction;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static int setDevicesRole(
            ArrayMap arrayMap,
            AudioDeviceInventory$$ExternalSyntheticLambda2
                    audioDeviceInventory$$ExternalSyntheticLambda2,
            AudioDeviceInventory$$ExternalSyntheticLambda2
                    audioDeviceInventory$$ExternalSyntheticLambda22,
            int i,
            List list) {
        int deviceRoleAction;
        synchronized (arrayMap) {
            try {
                Pair pair = new Pair(Integer.valueOf(i), 1);
                if (arrayMap.containsKey(pair)) {
                    List list2 = (List) arrayMap.get(pair);
                    if (list.size() == list2.size()) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            AudioDeviceAttributes audioDeviceAttributes =
                                    (AudioDeviceAttributes) it.next();
                            Iterator it2 = list2.iterator();
                            while (it2.hasNext()) {
                                if (audioDeviceAttributes.equalTypeAddress(
                                        (AudioDeviceAttributes) it2.next())) {
                                    break;
                                }
                            }
                        }
                        return 0;
                    }
                } else if (list.isEmpty()) {
                    return 0;
                }
                if (list.isEmpty()) {
                    deviceRoleAction =
                            audioDeviceInventory$$ExternalSyntheticLambda22.deviceRoleAction(
                                    i, 1, null);
                    if (deviceRoleAction == 0) {
                        arrayMap.remove(pair);
                    }
                } else {
                    deviceRoleAction =
                            audioDeviceInventory$$ExternalSyntheticLambda2.deviceRoleAction(
                                    i, 1, list);
                    if (deviceRoleAction == 0) {
                        arrayMap.put(pair, new ArrayList(list));
                    }
                }
                return deviceRoleAction;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addAudioDeviceInInventoryIfNeeded(
            String str, int i, boolean z, int i2, String str2) {
        Pair pair;
        if (AudioSystem.isBluetoothOutDevice(i)) {
            synchronized (this.mDeviceInventoryLock) {
                try {
                    AdiDeviceState findBtDeviceStateForAddress =
                            findBtDeviceStateForAddress(str, i);
                    if (findBtDeviceStateForAddress == null && str2 != null) {
                        findBtDeviceStateForAddress = findBtDeviceStateForAddress(str2, i);
                    }
                    if (findBtDeviceStateForAddress != null) {
                        if (findBtDeviceStateForAddress.getAudioDeviceCategory() != i2
                                && (z || i2 != 0)) {
                            findBtDeviceStateForAddress.setAudioDeviceCategory(i2);
                            this.mDeviceBroker.postUpdatedAdiDeviceState(
                                    findBtDeviceStateForAddress, false);
                            this.mDeviceBroker.postPersistAudioDeviceSettings();
                        }
                        this.mDeviceBroker.sendLMsgNoDelay(58, 2, findBtDeviceStateForAddress);
                        return;
                    }
                    AdiDeviceState adiDeviceState =
                            new AdiDeviceState(
                                    AudioDeviceInfo.convertInternalDeviceToDeviceType(i), i, str);
                    adiDeviceState.setAudioDeviceCategory(i2);
                    LinkedHashMap linkedHashMap = this.mDeviceInventory;
                    synchronized (adiDeviceState) {
                        pair = adiDeviceState.mDeviceId;
                    }
                    linkedHashMap.put(pair, adiDeviceState);
                    checkDeviceInventorySize_l();
                    this.mDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState, true);
                    this.mDeviceBroker.postPersistAudioDeviceSettings();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final int addDevicesRoleForStrategy(int i, boolean z, List list) {
        return addDevicesRole(
                z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles,
                new AudioDeviceInventory$$ExternalSyntheticLambda2(this, 1),
                i,
                list);
    }

    public final void addOrUpdateAudioDeviceCategoryInInventory(
            AdiDeviceState adiDeviceState, boolean z) {
        Pair pair;
        AdiDeviceState adiDeviceState2;
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        synchronized (this.mDeviceInventoryLock) {
            try {
                if (Flags.automaticBtDeviceType() && adiDeviceState.updateAudioDeviceCategory()) {
                    atomicBoolean.set(true);
                }
                LinkedHashMap linkedHashMap = this.mDeviceInventory;
                synchronized (adiDeviceState) {
                    pair = adiDeviceState.mDeviceId;
                }
                adiDeviceState2 =
                        (AdiDeviceState)
                                linkedHashMap.merge(
                                        pair,
                                        adiDeviceState,
                                        new BiFunction() { // from class:
                                                           // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda7
                                            @Override // java.util.function.BiFunction
                                            public final Object apply(Object obj, Object obj2) {
                                                AtomicBoolean atomicBoolean2 = atomicBoolean;
                                                AdiDeviceState adiDeviceState3 =
                                                        (AdiDeviceState) obj;
                                                AdiDeviceState adiDeviceState4 =
                                                        (AdiDeviceState) obj2;
                                                if (adiDeviceState3.getAudioDeviceCategory()
                                                        != adiDeviceState4
                                                                .getAudioDeviceCategory()) {
                                                    adiDeviceState3.setAudioDeviceCategory(
                                                            adiDeviceState4
                                                                    .getAudioDeviceCategory());
                                                    atomicBoolean2.set(true);
                                                }
                                                return adiDeviceState3;
                                            }
                                        });
                checkDeviceInventorySize_l();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (atomicBoolean.get()) {
            this.mDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState2, false);
        }
        if (z) {
            this.mDeviceBroker.sendLMsgNoDelay(58, 2, adiDeviceState2);
        }
    }

    public final void addOrUpdateDeviceSAStateInInventory(
            AdiDeviceState adiDeviceState, boolean z) {
        Pair pair;
        synchronized (this.mDeviceInventoryLock) {
            LinkedHashMap linkedHashMap = this.mDeviceInventory;
            synchronized (adiDeviceState) {
                pair = adiDeviceState.mDeviceId;
            }
            linkedHashMap.merge(
                    pair, adiDeviceState, new AudioDeviceInventory$$ExternalSyntheticLambda8());
            checkDeviceInventorySize_l();
        }
        if (z) {
            this.mDeviceBroker.sendLMsgNoDelay(58, 2, adiDeviceState);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00fe, code lost:

       if (r3.isModeEnabled("audio_mode_duplex") != false) goto L67;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x011b, code lost:

       if (r3.isModeEnabled(r7) != false) goto L67;
    */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyConnectedDevicesRoles_l() {
        /*
            Method dump skipped, instructions count: 546
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.AudioDeviceInventory.applyConnectedDevicesRoles_l():void");
    }

    public final String changeActiveBleDevice(String str) {
        BluetoothLeAudio bluetoothLeAudio;
        BluetoothDevice bluetoothDevice;
        BtHelper btHelper = this.mDeviceBroker.mBtHelper;
        synchronized (btHelper) {
            bluetoothLeAudio = btHelper.mLeAudio;
        }
        if (bluetoothLeAudio == null) {
            return null;
        }
        List<BluetoothDevice> connectedDevices = bluetoothLeAudio.getConnectedDevices();
        if (connectedDevices == null) {
            Log.w("AS.AudioDeviceInventory", "Nothing connected BLE devices");
            return null;
        }
        Boolean bool = Boolean.FALSE;
        if (str != null && isBleHeadsetConnected(str)) {
            bool = Boolean.TRUE;
        }
        if (!bool.booleanValue()) {
            for (BluetoothDevice bluetoothDevice2 : connectedDevices) {
                if (bluetoothDevice2.getAddress().equals(str)) {
                    bluetoothLeAudio.setActiveDevice(bluetoothDevice2);
                    Log.i(
                            "AS.AudioDeviceInventory",
                            "changeActiveBleDevice setActiveDevice for BLE connection address = "
                                    + AudioManagerHelper.getAddressForLog(str));
                    return null;
                }
            }
        }
        if (connectedDevices.size() > 0) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    bluetoothDevice = null;
                    break;
                }
                bluetoothDevice = it.next();
                if (bluetoothDevice.getAddress().equals(str)) {
                    break;
                }
            }
            if (bluetoothDevice != null) {
                if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                    BtUtils.setBtVolumeMonitor(bluetoothDevice);
                }
                String semGetAliasName = bluetoothDevice.semGetAliasName();
                bluetoothLeAudio.setActiveDevice(bluetoothDevice);
                Log.i(
                        "AS.AudioDeviceInventory",
                        "changeActiveBleDevice activeDeviceName = " + semGetAliasName);
                return semGetAliasName;
            }
        }
        return null;
    }

    public final String changeActiveBluetoothDevice(String str) {
        BluetoothDevice activeDevice;
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        BluetoothA2dp a2dp = audioDeviceBroker.getA2dp();
        if (a2dp == null) {
            return null;
        }
        List<BluetoothDevice> connectedDevices = a2dp.getConnectedDevices();
        if (connectedDevices == null) {
            Log.w("AS.AudioDeviceInventory", "Nothing connected BT devices");
            return null;
        }
        if (connectedDevices.size() == 1 && (activeDevice = a2dp.getActiveDevice()) != null) {
            a2dp.setActiveDevice(activeDevice);
            Log.i(
                    "AS.AudioDeviceInventory",
                    "changeActiveBluetoothDevice a2dp setActiveDevice = " + activeDevice.getName());
            if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                BtUtils.setBtVolumeMonitor(activeDevice);
            }
            return activeDevice.semGetAliasName();
        }
        for (BluetoothDevice bluetoothDevice : connectedDevices) {
            if (bluetoothDevice.getAddress().equals(str)) {
                if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                    BtUtils.setBtVolumeMonitor(bluetoothDevice);
                }
                if (a2dp.setActiveDevice(bluetoothDevice)) {
                    if (audioDeviceBroker.mDualA2dpManager.mDualModeEnabled) {
                        Log.i(
                                "AS.AudioDeviceInventory",
                                "Dual Audio is disabled by a2dp active changed.");
                        audioDeviceBroker.setDualA2dpMode(bluetoothDevice, false);
                    }
                    audioDeviceBroker.setActiveBluetoothDevice(bluetoothDevice);
                    return bluetoothDevice.semGetAliasName();
                }
            }
        }
        return null;
    }

    public final boolean checkDeviceConnected(final int i) {
        boolean anyMatch;
        if (i == 32768) {
            this.mAudioSystem.getClass();
            if (AudioSystem.getDeviceConnectionState(32768, "0") > 0) {
                return true;
            }
        }
        synchronized (this.mDevicesLock) {
            anyMatch =
                    this.mConnectedDevices.values().stream()
                            .anyMatch(
                                    new Predicate() { // from class:
                                                      // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda13
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return ((AudioDeviceInventory.DeviceInfo) obj)
                                                            .mDeviceType
                                                    == i;
                                        }
                                    });
        }
        return anyMatch;
    }

    public final void checkDeviceInventorySize_l() {
        if (this.mDeviceInventory.size() > 840) {
            Iterator it = this.mDeviceInventory.entrySet().iterator();
            if (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
    }

    public final int checkProfileIsConnected(int i) {
        if (i == 1) {
            if (getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_SCO_SET) == null
                    && getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_IN_ALL_SCO_SET) == null) {
                return 0;
            }
            return i;
        }
        if (i == 2) {
            if (getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_A2DP_SET) != null) {
                return i;
            }
            return 0;
        }
        if (i != 22 && i != 26) {
            return 0;
        }
        if (getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_BLE_SET) == null
                && getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_IN_ALL_BLE_SET) == null) {
            return 0;
        }
        return i;
    }

    public final int checkSendBecomingNoisyIntentInt(int i, int i2, int i3) {
        MediaMetrics.Item item =
                new MediaMetrics.Item("audio.device.checkSendBecomingNoisyIntentInt")
                        .set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(i))
                        .set(MediaMetrics.Property.STATE, i2 == 1 ? "connected" : "disconnected");
        int i4 = 0;
        if (i2 != 0) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i2, "not sending NOISY: state=", "AS.AudioDeviceInventory");
            item.set(MediaMetrics.Property.DELAY_MS, 0).record();
            return 0;
        }
        Set set = BECOMING_NOISY_INTENT_DEVICES_SET;
        if (!((HashSet) set).contains(Integer.valueOf(i))) {
            Log.i(
                    "AS.AudioDeviceInventory",
                    "not sending NOISY: device=0x" + Integer.toHexString(i) + " not in set " + set);
            item.set(MediaMetrics.Property.DELAY_MS, 0).record();
            return 0;
        }
        HashSet hashSet = new HashSet();
        for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
            if (!AudioSystem.isInputDevice(deviceInfo.mDeviceType)) {
                Set set2 = BECOMING_NOISY_INTENT_DEVICES_SET;
                int i5 = deviceInfo.mDeviceType;
                if (((HashSet) set2).contains(Integer.valueOf(i5))) {
                    hashSet.add(Integer.valueOf(i5));
                    Log.i("AS.AudioDeviceInventory", "NOISY: adding 0x" + Integer.toHexString(i5));
                }
            }
        }
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        if (i3 == 0) {
            i3 = audioDeviceBroker.mAudioService.getDeviceForStream(3);
            Log.i(
                    "AS.AudioDeviceInventory",
                    "NOISY: musicDevice changing from NONE to 0x" + Integer.toHexString(i3));
        }
        boolean isInCommunication = audioDeviceBroker.isInCommunication();
        boolean isSingleAudioDeviceType = AudioSystem.isSingleAudioDeviceType(hashSet, i);
        AudioService audioService = audioDeviceBroker.mAudioService;
        boolean hasMediaDynamicPolicy = audioService.hasMediaDynamicPolicy();
        this.mAudioSystem.getClass();
        boolean z = AudioSystem.isStreamActive(7, 0) && audioService.getDeviceForStream(1) == i;
        int i6 = 500;
        if ((i == i3 || isInCommunication || z) && !hasMediaDynamicPolicy && i3 != 32768) {
            if (!AudioSystem.isStreamActive(3, 0) && !audioService.hasAudioFocusUsers()) {
                EventLogger eventLogger = AudioService.sDeviceLogger;
                EventLogger.StringEvent stringEvent =
                        new EventLogger.StringEvent("dropping ACTION_AUDIO_BECOMING_NOISY");
                stringEvent.printLog(0, "AS.AudioDeviceInventory");
                eventLogger.enqueue(stringEvent);
                item.set(MediaMetrics.Property.DELAY_MS, 0).record();
                return 0;
            }
            if (ScreenSharingHelper.sSplitSoundEnabled
                    && AudioUtils.isWiredDeviceType(i)
                    && audioDeviceBroker.isInCommunication()) {
                EventLogger eventLogger2 = AudioService.sDeviceLogger;
                EventLogger.StringEvent stringEvent2 =
                        new EventLogger.StringEvent(
                                "dropping ACTION_AUDIO_BECOMING_NOISY by split sound");
                stringEvent2.printLog(0, "AS.AudioDeviceInventory");
                eventLogger2.enqueue(stringEvent2);
                item.set(MediaMetrics.Property.DELAY_MS, 0).record();
                return 0;
            }
            audioDeviceBroker.sendIMsgNoDelay(12, 0, i);
        } else if (audioService.isMultiSoundOn()
                && AudioSystem.isStreamActive(3, 0)
                && audioService.getPinDeviceInternal() == i) {
            EventLogger eventLogger3 = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent3 =
                    new EventLogger.StringEvent("send ACTION_AUDIO_BECOMING_NOISY to pinned apps");
            stringEvent3.printLog(0, "AS.AudioDeviceInventory");
            eventLogger3.enqueue(stringEvent3);
            audioDeviceBroker.mSystemServer.getClass();
            MultiSoundManager multiSoundManager = audioService.mMultiSoundManager;
            if (multiSoundManager.mEnabled) {
                Iterator it = new ArrayList(multiSoundManager.mPinAppInfoList.values()).iterator();
                while (it.hasNext()) {
                    MultiSoundManager.MultiSoundItem multiSoundItem =
                            (MultiSoundManager.MultiSoundItem) it.next();
                    if (multiSoundManager.getAppDevice(multiSoundItem.mUid) == i) {
                        AudioService.AnonymousClass11 anonymousClass11 =
                                multiSoundManager.mInterface;
                        anonymousClass11.getClass();
                        int i7 = AudioService.BECOMING_NOISY_DELAY_MS;
                        AudioService audioService2 = AudioService.this;
                        int i8 = multiSoundItem.mUid;
                        audioService2.sendBecomingNoisyIntent(i8, audioService2.getPackageName(i8));
                    }
                }
            }
        } else {
            StringBuilder sb = new StringBuilder("not sending NOISY: device:0x");
            sb.append(Integer.toHexString(i));
            sb.append(" musicDevice:0x");
            sb.append(Integer.toHexString(i3));
            sb.append(" inComm:");
            BatteryService$$ExternalSyntheticOutline0.m(
                    sb,
                    isInCommunication,
                    " mediaPolicy:",
                    hasMediaDynamicPolicy,
                    " singleDevice:");
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "AS.AudioDeviceInventory", sb, isSingleAudioDeviceType);
            i6 = 0;
        }
        if (i6 > 0) {
            if (FactoryUtils.isFactoryMode() && (i == 1024 || i == 67108864)) {
                EventLogger eventLogger4 = AudioService.sDeviceLogger;
                EventLogger.StringEvent stringEvent4 =
                        new EventLogger.StringEvent("HDMI/USB Headset delay in factory mode");
                stringEvent4.printLog(0, "AS.AudioDeviceInventory");
                eventLogger4.enqueue(stringEvent4);
            } else if (AudioUtils.isWiredDeviceType(i) && audioDeviceBroker.isInCommunication()) {
                EventLogger eventLogger5 = AudioService.sDeviceLogger;
                EventLogger.StringEvent stringEvent5 =
                        new EventLogger.StringEvent("in call wired headset/headphone");
                stringEvent5.printLog(0, "AS.AudioDeviceInventory");
                eventLogger5.enqueue(stringEvent5);
            }
            item.set(MediaMetrics.Property.DELAY_MS, Integer.valueOf(i4)).record();
            return i4;
        }
        i4 = i6;
        item.set(MediaMetrics.Property.DELAY_MS, Integer.valueOf(i4)).record();
        return i4;
    }

    public final int clearDevicesRoleForStrategy(int i, boolean z) {
        ArrayMap arrayMap = z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles;
        synchronized (arrayMap) {
            try {
                Pair pair = new Pair(Integer.valueOf(i), 1);
                if (!arrayMap.containsKey(pair)) {
                    return -2;
                }
                this.mAudioSystem.invalidateRoutingCache();
                int clearDevicesRoleForStrategy = AudioSystem.clearDevicesRoleForStrategy(i, 1);
                if (clearDevicesRoleForStrategy == 0) {
                    arrayMap.remove(pair);
                }
                return clearDevicesRoleForStrategy;
            } finally {
            }
        }
    }

    public final void disconnectLeAudio(final int i) {
        if (i != 536870912 && i != 536870914) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    i,
                    "disconnectLeAudio: Can't disconnect not LE Audio device ",
                    "AS.AudioDeviceInventory");
            return;
        }
        synchronized (this.mDevicesLock) {
            try {
                ArraySet arraySet = new ArraySet();
                this.mConnectedDevices
                        .values()
                        .forEach(new AudioDeviceInventory$$ExternalSyntheticLambda28(i, arraySet));
                new MediaMetrics.Item("audio.device.disconnectLeAudio")
                        .set(MediaMetrics.Property.EVENT, "disconnectLeAudio")
                        .record();
                if (arraySet.size() > 0) {
                    final int checkSendBecomingNoisyIntentInt =
                            checkSendBecomingNoisyIntentInt(i, 0, 0);
                    arraySet.stream()
                            .forEach(
                                    new Consumer() { // from class:
                                                     // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda29
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            AudioDeviceInventory audioDeviceInventory =
                                                    AudioDeviceInventory.this;
                                            int i2 = i;
                                            int i3 = checkSendBecomingNoisyIntentInt;
                                            Pair pair = (Pair) obj;
                                            audioDeviceInventory.getClass();
                                            String str = (String) pair.first;
                                            int intValue = ((Integer) pair.second).intValue();
                                            audioDeviceInventory.mDeviceBroker.setLeAudioSuspended(
                                                    "makeLeAudioDeviceUnavailableLater",
                                                    true,
                                                    true);
                                            audioDeviceInventory.mConnectedDevices.remove(
                                                    AudioDeviceInventory.DeviceInfo
                                                            .makeDeviceListKey(i2, str));
                                            audioDeviceInventory.mDeviceBroker.sendIILMsg(
                                                    49, 2, i2, intValue, str, i3);
                                        }
                                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dispatchDevicesRoleForCapturePreset(int i, List list) {
        int beginBroadcast = this.mDevRoleCapturePresetDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                if (!((Boolean) this.mDevRoleCapturePresetDispatchers.getBroadcastCookie(i2))
                        .booleanValue()) {
                    this.mDeviceBroker.mAudioService.getClass();
                    list = AudioService.anonymizeAudioDeviceAttributesListUnchecked(list);
                }
                this.mDevRoleCapturePresetDispatchers
                        .getBroadcastItem(i2)
                        .dispatchDevicesRoleChanged(i, 1, list);
            } catch (RemoteException e) {
                Log.e("AS.AudioDeviceInventory", "dispatchDevicesRoleForCapturePreset ", e);
            }
        }
        this.mDevRoleCapturePresetDispatchers.finishBroadcast();
    }

    public final void dispatchNonDefaultDevice(int i, List list) {
        int beginBroadcast = this.mNonDefDevDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                if (!((Boolean) this.mNonDefDevDispatchers.getBroadcastCookie(i2)).booleanValue()) {
                    this.mDeviceBroker.mAudioService.getClass();
                    list = AudioService.anonymizeAudioDeviceAttributesListUnchecked(list);
                }
                this.mNonDefDevDispatchers
                        .getBroadcastItem(i2)
                        .dispatchNonDefDevicesChanged(i, list);
            } catch (RemoteException e) {
                Log.e("AS.AudioDeviceInventory", "dispatchNonDefaultDevice ", e);
            }
        }
        this.mNonDefDevDispatchers.finishBroadcast();
    }

    public final void dispatchPreferredDevice(int i, List list) {
        int beginBroadcast = this.mPrefDevDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                if (!((Boolean) this.mPrefDevDispatchers.getBroadcastCookie(i2)).booleanValue()) {
                    this.mDeviceBroker.mAudioService.getClass();
                    list = AudioService.anonymizeAudioDeviceAttributesListUnchecked(list);
                }
                this.mPrefDevDispatchers.getBroadcastItem(i2).dispatchPrefDevicesChanged(i, list);
            } catch (RemoteException e) {
                Log.e("AS.AudioDeviceInventory", "dispatchPreferredDevice ", e);
            }
        }
        this.mPrefDevDispatchers.finishBroadcast();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r7v3, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.util.Set] */
    public AdiDeviceState findBtDeviceStateForAddress(String str, int i) {
        HashSet hashSet;
        if (AudioSystem.isBluetoothA2dpOutDevice(i)) {
            hashSet = AudioSystem.DEVICE_OUT_ALL_A2DP_SET;
        } else if (AudioSystem.isBluetoothLeOutDevice(i)) {
            hashSet = AudioSystem.DEVICE_OUT_ALL_BLE_SET;
        } else if (AudioSystem.isBluetoothScoOutDevice(i)) {
            hashSet = AudioSystem.DEVICE_OUT_ALL_SCO_SET;
        } else {
            if (i != 134217728) {
                return null;
            }
            HashSet hashSet2 = new HashSet();
            hashSet2.add(134217728);
            hashSet = hashSet2;
        }
        synchronized (this.mDeviceInventoryLock) {
            try {
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    AdiDeviceState adiDeviceState =
                            (AdiDeviceState)
                                    this.mDeviceInventory.get(new Pair((Integer) it.next(), str));
                    if (adiDeviceState != null) {
                        return adiDeviceState;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Set getAvailableDeviceSetForQuickSoundPath() {
        HashSet hashSet = new HashSet();
        synchronized (this.mDevicesLock) {
            try {
                if (checkDeviceConnected(-2147352576)) {
                    hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
                }
                if (this.mDeviceBroker.isDeviceOnForCommunication(7)) {
                    hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
                }
                if (checkDeviceConnected(32768)) {
                    if (!ScreenSharingHelper.sIsWifiDisplayConnected) {
                        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
                    } else if (((DisplayManager)
                                            this.mDeviceBroker.mContext.getSystemService("display"))
                                    .semGetActiveDlnaState()
                            == 1) {
                        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
                    }
                }
                if (Rune.SEC_AUDIO_FM_RADIO && AudioManagerHelper.isFMPlayerActive()) {
                    hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        HashSet hashSet2 = new HashSet(AudioSystem.DEVICE_OUT_ALL_SET);
        hashSet2.removeAll(hashSet);
        return hashSet2;
    }

    public final List getConnectedDevicesOfTypes(Set set) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mDevicesLock) {
            try {
                for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                    if (set.contains(Integer.valueOf(deviceInfo.mDeviceType))) {
                        arrayList.add(deviceInfo);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final List getDeviceIdentityAddresses(AudioDeviceAttributes audioDeviceAttributes) {
        ArrayList arrayList = new ArrayList();
        String makeDeviceListKey =
                DeviceInfo.makeDeviceListKey(
                        audioDeviceAttributes.getInternalType(),
                        audioDeviceAttributes.getAddress());
        synchronized (this.mDevicesLock) {
            try {
                DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(makeDeviceListKey);
                if (deviceInfo != null) {
                    if (!deviceInfo.mDeviceIdentityAddress.isEmpty()) {
                        arrayList.add(deviceInfo.mDeviceIdentityAddress);
                    }
                    if (!deviceInfo.mPeerIdentityDeviceAddress.isEmpty()
                            && !deviceInfo.mPeerIdentityDeviceAddress.equals(
                                    deviceInfo.mDeviceIdentityAddress)) {
                        arrayList.add(deviceInfo.mPeerIdentityDeviceAddress);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final DeviceInfo getFirstConnectedDeviceOfTypes(Set set) {
        ArrayList arrayList = (ArrayList) getConnectedDevicesOfTypes(set);
        if (arrayList.isEmpty()) {
            return null;
        }
        return (DeviceInfo) arrayList.get(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x01a7 A[Catch: all -> 0x010c, TryCatch #0 {all -> 0x010c, blocks: (B:7:0x0083, B:10:0x00a9, B:16:0x0112, B:17:0x016c, B:20:0x016f, B:22:0x0187, B:25:0x01a7, B:29:0x01b3, B:31:0x01b8, B:32:0x01ca, B:35:0x01e2, B:38:0x01f4, B:41:0x01bc, B:43:0x0207, B:44:0x0243, B:46:0x0214, B:47:0x00d1, B:49:0x00db, B:51:0x00e1, B:56:0x0193), top: B:6:0x0083 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0214 A[Catch: all -> 0x010c, TryCatch #0 {all -> 0x010c, blocks: (B:7:0x0083, B:10:0x00a9, B:16:0x0112, B:17:0x016c, B:20:0x016f, B:22:0x0187, B:25:0x01a7, B:29:0x01b3, B:31:0x01b8, B:32:0x01ca, B:35:0x01e2, B:38:0x01f4, B:41:0x01bc, B:43:0x0207, B:44:0x0243, B:46:0x0214, B:47:0x00d1, B:49:0x00db, B:51:0x00e1, B:56:0x0193), top: B:6:0x0083 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleDeviceConnection(
            android.media.AudioDeviceAttributes r20,
            boolean r21,
            boolean r22,
            android.bluetooth.BluetoothDevice r23) {
        /*
            Method dump skipped, instructions count: 583
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.AudioDeviceInventory.handleDeviceConnection(android.media.AudioDeviceAttributes,"
                    + " boolean, boolean, android.bluetooth.BluetoothDevice):boolean");
    }

    public boolean isA2dpDeviceConnected(BluetoothDevice bluetoothDevice) {
        Iterator it =
                ((ArrayList) getConnectedDevicesOfTypes(Sets.newHashSet(new Integer[] {128})))
                        .iterator();
        while (it.hasNext()) {
            if (((DeviceInfo) it.next()).mDeviceAddress.equals(bluetoothDevice.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBleHeadsetConnected(String str) {
        synchronized (this.mDevicesLock) {
            try {
                for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                    if (str.equals(deviceInfo.mDeviceAddress)
                            && deviceInfo.mDeviceType == 536870912) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void makeA2dpDeviceAvailable(AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i) {
        String address = btDeviceInfo.mDevice.getAddress();
        String name = btDeviceInfo.mDevice.getName();
        if (name == null) {
            name = "";
        }
        String str = name;
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        audioDeviceBroker.setBluetoothA2dpOnInt("onSetBtActiveDevice", true, true);
        int deviceConnectionState =
                this.mAudioSystem.setDeviceConnectionState(
                        new AudioDeviceAttributes(128, address, str), 1, i);
        if (deviceConnectionState != 0) {
            EventLogger eventLogger = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent =
                    new EventLogger.StringEvent(
                            "APM failed to make available A2DP device addr="
                                    + Utils.anonymizeBluetoothAddress(address)
                                    + " error="
                                    + deviceConnectionState);
            stringEvent.printSlog(1, "AS.AudioDeviceInventory");
            eventLogger.enqueue(stringEvent);
            return;
        }
        EventLogger eventLogger2 = AudioService.sDeviceLogger;
        EventLogger.StringEvent stringEvent2 =
                new EventLogger.StringEvent(
                        "A2DP sink device addr="
                                + Utils.anonymizeBluetoothAddress(address)
                                + " now available");
        stringEvent2.printSlog(0, "AS.AudioDeviceInventory");
        eventLogger2.enqueue(stringEvent2);
        if (checkDeviceConnected(128)) {
            Log.w(
                    "AS.AudioDeviceInventory",
                    "makeA2dpDeviceAvailable clearA2dpSuspended(true) skipped");
        } else {
            audioDeviceBroker.clearA2dpSuspended(true);
        }
        DeviceInfo deviceInfo =
                new DeviceInfo(128, i, str, address, btDeviceInfo.mDevice.getIdentityAddress());
        String makeDeviceListKey =
                DeviceInfo.makeDeviceListKey(deviceInfo.mDeviceType, deviceInfo.mDeviceAddress);
        this.mConnectedDevices.put(makeDeviceListKey, deviceInfo);
        this.mApmConnectedDevices.add(makeDeviceListKey);
        audioDeviceBroker.mAudioService.postAccessoryPlugMediaUnmute(128);
        setCurrentAudioRouteNameIfPossible(str, true);
        updateBluetoothPreferredModes_l(btDeviceInfo.mDevice);
        addAudioDeviceInInventoryIfNeeded(
                address, 128, false, BtHelper.getBtDeviceCategory(address), "");
    }

    public final void makeA2dpDeviceUnavailableNow(int i, String str) {
        MediaMetrics.Item item =
                new MediaMetrics.Item(
                                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                        "audio.device.a2dp.", str))
                        .set(MediaMetrics.Property.ENCODING, AudioSystem.audioFormatToString(i))
                        .set(MediaMetrics.Property.EVENT, "makeA2dpDeviceUnavailableNow");
        if (str == null) {
            item.set(MediaMetrics.Property.EARLY_RETURN, "address null").record();
            return;
        }
        String makeDeviceListKey = DeviceInfo.makeDeviceListKey(128, str);
        this.mConnectedDevices.remove(makeDeviceListKey);
        Iterator it = this.mApmConnectedDevices.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (makeDeviceListKey.equals((String) it.next())) {
                z = true;
            }
        }
        if (!z) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                    "makeA2dpDeviceUnavailableNow return ",
                    makeDeviceListKey,
                    " not connected !!!",
                    "AS.AudioDeviceInventory");
            EventLogger eventLogger = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent =
                    new EventLogger.StringEvent(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "A2DP device ", str, " made unavailable, was not used"));
            stringEvent.printLog(0, "AS.AudioDeviceInventory");
            eventLogger.enqueue(stringEvent);
            item.set(
                            MediaMetrics.Property.EARLY_RETURN,
                            "A2DP device made unavailable, was not used")
                    .record();
            return;
        }
        AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(128, str);
        int deviceConnectionState =
                this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, i);
        if (deviceConnectionState != 0) {
            EventLogger eventLogger2 = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent2 =
                    new EventLogger.StringEvent(
                            "APM failed to make unavailable A2DP device addr="
                                    + Utils.anonymizeBluetoothAddress(str)
                                    + " error="
                                    + deviceConnectionState);
            stringEvent2.printSlog(1, "AS.AudioDeviceInventory");
            eventLogger2.enqueue(stringEvent2);
        } else {
            EventLogger eventLogger3 = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent3 =
                    new EventLogger.StringEvent(
                            "A2DP device addr="
                                    + Utils.anonymizeBluetoothAddress(str)
                                    + " made unavailable");
            stringEvent3.printSlog(0, "AS.AudioDeviceInventory");
            eventLogger3.enqueue(stringEvent3);
        }
        this.mApmConnectedDevices.remove(makeDeviceListKey);
        setCurrentAudioRouteNameIfPossible(null, true);
        item.record();
        updateBluetoothPreferredModes_l(null);
        purgeDevicesRoles_l();
        if (!checkDeviceConnected(128)) {
            setCurrentAudioRouteNameIfPossible(null, false);
        }
        this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
    }

    public final void makeA2dpSrcAvailable(String str) {
        int deviceConnectionState =
                this.mAudioSystem.setDeviceConnectionState(
                        new AudioDeviceAttributes(-2147352576, str), 1, 0);
        if (deviceConnectionState != 0) {
            EventLogger eventLogger = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent =
                    new EventLogger.StringEvent(
                            "APM failed to make available A2DP source device addr="
                                    + Utils.anonymizeBluetoothAddress(str)
                                    + " error="
                                    + deviceConnectionState);
            stringEvent.printSlog(1, "AS.AudioDeviceInventory");
            eventLogger.enqueue(stringEvent);
        } else {
            EventLogger eventLogger2 = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent2 =
                    new EventLogger.StringEvent(
                            "A2DP source device addr="
                                    + Utils.anonymizeBluetoothAddress(str)
                                    + " now available");
            stringEvent2.printSlog(0, "AS.AudioDeviceInventory");
            eventLogger2.enqueue(stringEvent2);
        }
        this.mConnectedDevices.put(
                DeviceInfo.makeDeviceListKey(-2147352576, str),
                new DeviceInfo(-2147352576, "", str));
    }

    public final void makeHearingAidDeviceAvailable(int i, String str, String str2) {
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        audioDeviceBroker.sendIILMsg(
                14,
                0,
                audioDeviceBroker.mAudioService.getVssVolumeForDevice(i, 134217728),
                i,
                null,
                0);
        audioDeviceBroker.setBluetoothA2dpOnInt("onSetBtActiveDevice", true, false);
        this.mAudioSystem.setDeviceConnectionState(
                new AudioDeviceAttributes(134217728, str, str2), 1, 0);
        this.mConnectedDevices.put(
                DeviceInfo.makeDeviceListKey(134217728, str), new DeviceInfo(134217728, str2, str));
        audioDeviceBroker.mAudioService.postAccessoryPlugMediaUnmute(134217728);
        audioDeviceBroker.postApplyVolumeOnDevice(i, 134217728, "makeHearingAidDeviceAvailable");
        setCurrentAudioRouteNameIfPossible(str2, false);
        addAudioDeviceInInventoryIfNeeded(
                str, 134217728, false, BtHelper.getBtDeviceCategory(str), "");
        new MediaMetrics.Item("audio.device.makeHearingAidDeviceAvailable")
                .set(MediaMetrics.Property.ADDRESS, str != null ? str : "")
                .set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(134217728))
                .set(MediaMetrics.Property.NAME, str2)
                .set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i))
                .record();
    }

    public final void makeHearingAidDeviceUnavailable(String str) {
        AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(134217728, str);
        this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
        this.mConnectedDevices.remove(DeviceInfo.makeDeviceListKey(134217728, str));
        setCurrentAudioRouteNameIfPossible(null, false);
        MediaMetrics.Item item =
                new MediaMetrics.Item("audio.device.makeHearingAidDeviceUnavailable");
        MediaMetrics.Key key = MediaMetrics.Property.ADDRESS;
        if (str == null) {
            str = "";
        }
        item.set(key, str)
                .set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(134217728))
                .record();
        this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void makeLeAudioDeviceAvailable(
            com.android.server.audio.AudioDeviceBroker.BtDeviceInfo r23, int r24) {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.AudioDeviceInventory.makeLeAudioDeviceAvailable(com.android.server.audio.AudioDeviceBroker$BtDeviceInfo,"
                    + " int):void");
    }

    public final void makeLeAudioDeviceUnavailableNow(int i, int i2, String str) {
        AudioDeviceAttributes audioDeviceAttributes;
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        if (i != 0) {
            audioDeviceAttributes = new AudioDeviceAttributes(i, str);
            int deviceConnectionState =
                    AudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, i2);
            if (deviceConnectionState != 0) {
                EventLogger eventLogger = AudioService.sDeviceLogger;
                EventLogger.StringEvent stringEvent =
                        new EventLogger.StringEvent(
                                SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                                        .m(
                                                deviceConnectionState,
                                                "APM failed to make unavailable LE Audio device"
                                                    + " addr=",
                                                str,
                                                " error="));
                stringEvent.printSlog(1, "AS.AudioDeviceInventory");
                eventLogger.enqueue(stringEvent);
            } else {
                EventLogger eventLogger2 = AudioService.sDeviceLogger;
                EventLogger.StringEvent stringEvent2 =
                        new EventLogger.StringEvent(
                                "LE Audio device addr="
                                        + Utils.anonymizeBluetoothAddress(str)
                                        + " made unavailable");
                stringEvent2.printSlog(0, "AS.AudioDeviceInventory");
                eventLogger2.enqueue(stringEvent2);
                if (i == 536870914) {
                    BtUtils.setAuracast(false);
                    AudioService audioService = audioDeviceBroker.mAudioService;
                    audioService.mIsLeBroadCasting = false;
                    AudioService.sendMsg(audioService.mAudioHandler, 2775, 0, 0, 0, null, 0);
                }
            }
            this.mConnectedDevices.remove(DeviceInfo.makeDeviceListKey(i, str));
        } else {
            audioDeviceAttributes = null;
        }
        if (i == 536870912 || i == 536870913) {
            Boolean bool = Boolean.TRUE;
            Iterator it = this.mConnectedDevices.values().iterator();
            while (it.hasNext()) {
                int i3 = ((DeviceInfo) it.next()).mDeviceType;
                if (i3 == 536870912 || i3 == 536870913) {
                    bool = Boolean.FALSE;
                    break;
                }
            }
            if (bool.booleanValue()) {
                Iterator it2 = audioDeviceBroker.mCommunicationRouteClients.iterator();
                while (it2.hasNext()) {
                    AudioDeviceBroker.CommunicationRouteClient communicationRouteClient =
                            (AudioDeviceBroker.CommunicationRouteClient) it2.next();
                    int type = communicationRouteClient.mDevice.getType();
                    if (type == 26 || type == 27) {
                        communicationRouteClient.unregisterDeathRecipient();
                        it2.remove();
                        EventLogger eventLogger3 = AudioService.sDeviceLogger;
                        EventLogger.StringEvent stringEvent3 =
                                new EventLogger.StringEvent(
                                        "ble route removed on CommunicationRouteClients");
                        stringEvent3.printLog(0, "AS.AudioDeviceBroker");
                        eventLogger3.enqueue(stringEvent3);
                    }
                }
            }
        }
        setCurrentAudioRouteNameIfPossible(null, false);
        updateBluetoothPreferredModes_l(null);
        purgeDevicesRoles_l();
        if (audioDeviceAttributes != null) {
            audioDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0158 A[Catch: all -> 0x0097, TryCatch #0 {all -> 0x0097, blocks: (B:13:0x0066, B:15:0x0075, B:17:0x0088, B:18:0x00ae, B:20:0x009a, B:21:0x00b0, B:29:0x0158, B:30:0x015b, B:35:0x00e2, B:38:0x00f5, B:40:0x00fe, B:41:0x012e), top: B:12:0x0066 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onBluetoothDeviceConfigChange(
            com.android.server.audio.AudioDeviceBroker.BtDeviceInfo r17, int r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.AudioDeviceInventory.onBluetoothDeviceConfigChange(com.android.server.audio.AudioDeviceBroker$BtDeviceInfo,"
                    + " int, boolean):int");
    }

    public final void onBtProfileDisconnected(int i) {
        boolean z = false;
        if (i == 1) {
            synchronized (this.mDevicesLock) {
                try {
                    Iterator it = this.mConnectedDevices.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        } else if (AudioSystem.isBluetoothScoDevice(
                                ((DeviceInfo) it.next()).mDeviceType)) {
                            z = true;
                            break;
                        }
                    }
                } finally {
                }
            }
            if (z) {
                this.mDeviceBroker.mBtHelper.onSetBtScoActiveDevice(null);
                return;
            }
            return;
        }
        if (i == 2) {
            AudioService audioService = this.mDeviceBroker.mAudioService;
            if (audioService.mIsBluetoothCastState) {
                audioService.mIsBluetoothCastState = false;
                String makeDeviceListKey = DeviceInfo.makeDeviceListKey(32768, "0");
                new ArraySet(0);
                TextUtils.emptyIfNull("remote_submix");
                TextUtils.emptyIfNull("0");
                TextUtils.emptyIfNull(null).getClass();
                TextUtils.emptyIfNull(null);
                TextUtils.emptyIfNull(null);
                this.mConnectedDevices.remove(makeDeviceListKey);
            }
            synchronized (this.mDevicesLock) {
                try {
                    final ArraySet arraySet = new ArraySet();
                    final int i2 = 0;
                    this.mConnectedDevices
                            .values()
                            .forEach(
                                    new Consumer() { // from class:
                                                     // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda30
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            int i3 = i2;
                                            ArraySet arraySet2 = arraySet;
                                            AudioDeviceInventory.DeviceInfo deviceInfo =
                                                    (AudioDeviceInventory.DeviceInfo) obj;
                                            switch (i3) {
                                                case 0:
                                                    if (deviceInfo.mDeviceType == 128) {
                                                        arraySet2.add(deviceInfo.mDeviceAddress);
                                                        break;
                                                    }
                                                    break;
                                                case 1:
                                                    if (deviceInfo.mDeviceType == -2147352576) {
                                                        arraySet2.add(deviceInfo.mDeviceAddress);
                                                        break;
                                                    }
                                                    break;
                                                default:
                                                    if (deviceInfo.mDeviceType == 134217728) {
                                                        arraySet2.add(deviceInfo.mDeviceAddress);
                                                        break;
                                                    }
                                                    break;
                                            }
                                        }
                                    });
                    new MediaMetrics.Item("audio.device.disconnectA2dp")
                            .set(MediaMetrics.Property.EVENT, "disconnectA2dp")
                            .record();
                    if (arraySet.size() > 0) {
                        arraySet.stream()
                                .forEach(
                                        new AudioDeviceInventory$$ExternalSyntheticLambda28(
                                                this, checkSendBecomingNoisyIntentInt(128, 0, 0)));
                    }
                } finally {
                }
            }
            return;
        }
        if (i == 11) {
            synchronized (this.mDevicesLock) {
                final ArraySet arraySet2 = new ArraySet();
                final int i3 = 1;
                this.mConnectedDevices
                        .values()
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda30
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i32 = i3;
                                        ArraySet arraySet22 = arraySet2;
                                        AudioDeviceInventory.DeviceInfo deviceInfo =
                                                (AudioDeviceInventory.DeviceInfo) obj;
                                        switch (i32) {
                                            case 0:
                                                if (deviceInfo.mDeviceType == 128) {
                                                    arraySet22.add(deviceInfo.mDeviceAddress);
                                                    break;
                                                }
                                                break;
                                            case 1:
                                                if (deviceInfo.mDeviceType == -2147352576) {
                                                    arraySet22.add(deviceInfo.mDeviceAddress);
                                                    break;
                                                }
                                                break;
                                            default:
                                                if (deviceInfo.mDeviceType == 134217728) {
                                                    arraySet22.add(deviceInfo.mDeviceAddress);
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                new MediaMetrics.Item("audio.device.disconnectA2dpSink")
                        .set(MediaMetrics.Property.EVENT, "disconnectA2dpSink")
                        .record();
                arraySet2.stream()
                        .forEach(new AudioDeviceInventory$$ExternalSyntheticLambda33(0, this));
            }
            return;
        }
        if (i == 26) {
            disconnectLeAudio(536870914);
            return;
        }
        if (i != 21) {
            if (i == 22) {
                disconnectLeAudio(536870912);
                return;
            }
            Log.e(
                    "AS.AudioDeviceInventory",
                    "onBtProfileDisconnected: Not a valid profile to disconnect "
                            + BluetoothProfile.getProfileName(i));
            return;
        }
        synchronized (this.mDevicesLock) {
            try {
                final ArraySet arraySet3 = new ArraySet();
                final int i4 = 2;
                this.mConnectedDevices
                        .values()
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda30
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i32 = i4;
                                        ArraySet arraySet22 = arraySet3;
                                        AudioDeviceInventory.DeviceInfo deviceInfo =
                                                (AudioDeviceInventory.DeviceInfo) obj;
                                        switch (i32) {
                                            case 0:
                                                if (deviceInfo.mDeviceType == 128) {
                                                    arraySet22.add(deviceInfo.mDeviceAddress);
                                                    break;
                                                }
                                                break;
                                            case 1:
                                                if (deviceInfo.mDeviceType == -2147352576) {
                                                    arraySet22.add(deviceInfo.mDeviceAddress);
                                                    break;
                                                }
                                                break;
                                            default:
                                                if (deviceInfo.mDeviceType == 134217728) {
                                                    arraySet22.add(deviceInfo.mDeviceAddress);
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                new MediaMetrics.Item("audio.device.disconnectHearingAid")
                        .set(MediaMetrics.Property.EVENT, "disconnectHearingAid")
                        .record();
                if (arraySet3.size() > 0) {
                    checkSendBecomingNoisyIntentInt(134217728, 0, 0);
                    arraySet3.stream()
                            .forEach(new AudioDeviceInventory$$ExternalSyntheticLambda33(1, this));
                }
            } finally {
            }
        }
    }

    public final void onMakeA2dpDeviceUnavailableNow(int i, String str) {
        synchronized (this.mDevicesLock) {
            makeA2dpDeviceUnavailableNow(i, str);
        }
    }

    public final void onMakeLeAudioDeviceUnavailableNow(int i, int i2, String str) {
        synchronized (this.mDevicesLock) {
            makeLeAudioDeviceUnavailableNow(i, i2, str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0045, code lost:

       if ("OTHERS".equals(r6.mForcePath) != false) goto L15;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReportNewRoutes() {
        /*
            r6 = this;
            java.lang.String r0 = "dispatchAudioRoutesChanged mainType = "
            android.os.RemoteCallbackList r1 = r6.mRoutesObservers
            int r1 = r1.beginBroadcast()
            if (r1 <= 0) goto L8e
            android.media.MediaMetrics$Item r2 = new android.media.MediaMetrics$Item
            java.lang.String r3 = "audio.device.onReportNewRoutes"
            r2.<init>(r3)
            android.media.MediaMetrics$Key r3 = android.media.MediaMetrics.Property.OBSERVERS
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)
            android.media.MediaMetrics$Item r2 = r2.set(r3, r4)
            r2.record()
            android.media.AudioRoutesInfo r2 = r6.mCurAudioRoutes
            monitor-enter(r2)
            android.media.AudioRoutesInfo r3 = new android.media.AudioRoutesInfo     // Catch: java.lang.Throwable -> L3b
            android.media.AudioRoutesInfo r4 = r6.mCurAudioRoutes     // Catch: java.lang.Throwable -> L3b
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r4 = "BT"
            java.lang.String r5 = r6.mForcePath     // Catch: java.lang.Throwable -> L3b
            boolean r4 = r4.equals(r5)     // Catch: java.lang.Throwable -> L3b
            if (r4 == 0) goto L3d
            android.media.AudioRoutesInfo r4 = r6.mCurAudioRoutes     // Catch: java.lang.Throwable -> L3b
            java.lang.CharSequence r4 = r4.bluetoothName     // Catch: java.lang.Throwable -> L3b
            if (r4 != 0) goto L47
            goto L3d
        L3b:
            r6 = move-exception
            goto L8c
        L3d:
            java.lang.String r4 = "OTHERS"
            java.lang.String r5 = r6.mForcePath     // Catch: java.lang.Throwable -> L3b
            boolean r4 = r4.equals(r5)     // Catch: java.lang.Throwable -> L3b
            if (r4 == 0) goto L50
        L47:
            java.lang.String r4 = r6.mForcePath     // Catch: java.lang.Throwable -> L3b
            r3.setForcePath(r4)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r4 = ""
            r6.mForcePath = r4     // Catch: java.lang.Throwable -> L3b
        L50:
            java.lang.String r4 = "AS.AudioDeviceInventory"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3b
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L3b
            android.media.AudioRoutesInfo r0 = r6.mCurAudioRoutes     // Catch: java.lang.Throwable -> L3b
            int r0 = r0.mainType     // Catch: java.lang.Throwable -> L3b
            r5.append(r0)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r0 = " setForcePath = "
            r5.append(r0)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r0 = r3.getSetForcePath()     // Catch: java.lang.Throwable -> L3b
            r5.append(r0)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L3b
            android.util.Log.i(r4, r0)     // Catch: java.lang.Throwable -> L3b
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L3b
        L72:
            if (r1 <= 0) goto L8e
            int r1 = r1 + (-1)
            android.os.RemoteCallbackList r0 = r6.mRoutesObservers
            android.os.IInterface r0 = r0.getBroadcastItem(r1)
            android.media.IAudioRoutesObserver r0 = (android.media.IAudioRoutesObserver) r0
            r0.dispatchAudioRoutesChanged(r3)     // Catch: android.os.RemoteException -> L82
            goto L72
        L82:
            r0 = move-exception
            java.lang.String r2 = "AS.AudioDeviceInventory"
            java.lang.String r4 = "onReportNewRoutes"
            android.util.Log.e(r2, r4, r0)
            goto L72
        L8c:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L3b
            throw r6
        L8e:
            android.os.RemoteCallbackList r0 = r6.mRoutesObservers
            r0.finishBroadcast()
            com.android.server.audio.AudioDeviceBroker r6 = r6.mDeviceBroker
            com.android.server.audio.AudioService r6 = r6.mAudioService
            r6.postObserveDevicesForAllStreams()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.AudioDeviceInventory.onReportNewRoutes():void");
    }

    public final void onRestoreDevices() {
        synchronized (this.mDevicesLock) {
            try {
                for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                    int i = deviceInfo.mDeviceType;
                    if (!((HashSet) AudioUtils.SKIP_RESTORE_DEVICE_SET)
                            .contains(Integer.valueOf(i))) {
                        this.mAudioSystem.setDeviceConnectionState(
                                new AudioDeviceAttributes(
                                        deviceInfo.mDeviceType,
                                        deviceInfo.mDeviceAddress,
                                        deviceInfo.mDeviceName),
                                1,
                                deviceInfo.mDeviceCodecFormat);
                    }
                }
                this.mAppliedStrategyRolesInt.clear();
                this.mAppliedPresetRolesInt.clear();
                applyConnectedDevicesRoles_l();
            } catch (Throwable th) {
                throw th;
            }
        }
        reapplyExternalDevicesRoles();
    }

    public void onSetBtActiveDevice(AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i, int i2) {
        StringBuilder sb = new StringBuilder("onSetBtActiveDevice btDevice=");
        sb.append(btDeviceInfo.mDevice);
        sb.append(" profile=");
        sb.append(BluetoothProfile.getProfileName(btDeviceInfo.mProfile));
        sb.append(" state=");
        int i3 = btDeviceInfo.mState;
        VpnManagerService$$ExternalSyntheticOutline0.m(
                sb,
                i3 == 99 ? "STATE_CONNECTED_IMPLICIT" : BluetoothProfile.getConnectionStateName(i3),
                "AS.AudioDeviceInventory");
        String address = btDeviceInfo.mDevice.getAddress();
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            address = "";
        }
        AudioService.sDeviceLogger.enqueue(
                new EventLogger.StringEvent(
                        "BT connected:"
                                + btDeviceInfo
                                + " codec="
                                + AudioSystem.audioFormatToString(i)));
        new MediaMetrics.Item("audio.device.onSetBtActiveDevice")
                .set(MediaMetrics.Property.STATUS, Integer.valueOf(btDeviceInfo.mProfile))
                .set(
                        MediaMetrics.Property.DEVICE,
                        AudioSystem.getDeviceName(btDeviceInfo.mAudioSystemDevice))
                .set(MediaMetrics.Property.ADDRESS, address)
                .set(MediaMetrics.Property.ENCODING, AudioSystem.audioFormatToString(i))
                .set(MediaMetrics.Property.EVENT, "onSetBtActiveDevice")
                .set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i2))
                .set(
                        MediaMetrics.Property.STATE,
                        isBtStateConnected(btDeviceInfo) ? "connected" : "disconnected")
                .record();
        synchronized (this.mDevicesLock) {
            try {
                DeviceInfo deviceInfo =
                        (DeviceInfo)
                                this.mConnectedDevices.get(
                                        DeviceInfo.makeDeviceListKey(
                                                btDeviceInfo.mAudioSystemDevice, address));
                boolean z = deviceInfo != null;
                boolean z2 = z && !isBtStateConnected(btDeviceInfo);
                boolean z3 = !z && isBtStateConnected(btDeviceInfo);
                int i4 = btDeviceInfo.mProfile;
                if (i4 == 1) {
                    AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
                    if (audioDeviceBroker.mScoManagedByAudio) {
                        if (z2) {
                            audioDeviceBroker.mBtHelper.onSetBtScoActiveDevice(null);
                        } else if (z3) {
                            audioDeviceBroker.mBtHelper.onSetBtScoActiveDevice(
                                    btDeviceInfo.mDevice);
                        }
                    }
                } else if (i4 == 2) {
                    this.mDeviceBroker.connectA2dpDevice(
                            btDeviceInfo.mDevice, btDeviceInfo.mState, btDeviceInfo.mVolume);
                    if (z2) {
                        makeA2dpDeviceUnavailableNow(deviceInfo.mDeviceCodecFormat, address);
                        if (!checkDeviceConnected(128)) {
                            setCurrentAudioRouteNameIfPossible(null, true);
                        }
                        if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                            final int i5 = 0;
                            AudioExecutor.execute(
                                    new Runnable(
                                            this) { // from class:
                                                    // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda10
                                        public final /* synthetic */ AudioDeviceInventory f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i6 = i5;
                                            AudioDeviceInventory audioDeviceInventory = this.f$0;
                                            switch (i6) {
                                                case 0:
                                                    audioDeviceInventory.updateBtVolumeMonitor();
                                                    break;
                                                default:
                                                    audioDeviceInventory.updateBtVolumeMonitor();
                                                    break;
                                            }
                                        }
                                    });
                        }
                    } else if (z3) {
                        int i6 = btDeviceInfo.mVolume;
                        if (i6 != -1) {
                            this.mDeviceBroker.mAudioService.postSetVolumeIndexOnDevice(
                                    3, i6, btDeviceInfo.mAudioSystemDevice, "onSetBtActiveDevice");
                        } else {
                            int a2dpDeviceVolume =
                                    this.mDeviceBroker.getA2dpDeviceVolume(btDeviceInfo.mDevice);
                            if (a2dpDeviceVolume != -1) {
                                this.mDeviceBroker.mAudioService.postSetVolumeIndexOnDevice(
                                        3, a2dpDeviceVolume, 128, "onSetBtActiveDevice");
                            }
                        }
                        if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                            BtUtils.setBtVolumeMonitor(btDeviceInfo.mDevice);
                        }
                        makeA2dpDeviceAvailable(btDeviceInfo, i);
                    }
                } else if (i4 != 11) {
                    if (i4 != 26) {
                        if (i4 != 21) {
                            if (i4 != 22) {
                                throw new IllegalArgumentException(
                                        "Invalid profile "
                                                + BluetoothProfile.getProfileName(
                                                        btDeviceInfo.mProfile));
                            }
                        } else if (z2) {
                            makeHearingAidDeviceUnavailable(address);
                        } else if (z3) {
                            String name = btDeviceInfo.mDevice.getName();
                            if (name == null) {
                                name = "";
                            }
                            makeHearingAidDeviceAvailable(i2, address, name);
                        }
                    }
                    if (z2) {
                        makeLeAudioDeviceUnavailableNow(
                                btDeviceInfo.mAudioSystemDevice,
                                deviceInfo.mDeviceCodecFormat,
                                address);
                        if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                            boolean z4 = btDeviceInfo.mIsLeOutput;
                            if (!this.mDeviceBroker.isDualModeActive() && z4) {
                                final int i7 = 1;
                                AudioExecutor.execute(
                                        new Runnable(
                                                this) { // from class:
                                                        // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda10
                                            public final /* synthetic */ AudioDeviceInventory f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i62 = i7;
                                                AudioDeviceInventory audioDeviceInventory =
                                                        this.f$0;
                                                switch (i62) {
                                                    case 0:
                                                        audioDeviceInventory
                                                                .updateBtVolumeMonitor();
                                                        break;
                                                    default:
                                                        audioDeviceInventory
                                                                .updateBtVolumeMonitor();
                                                        break;
                                                }
                                            }
                                        });
                            }
                        }
                    } else if (z3) {
                        makeLeAudioDeviceAvailable(btDeviceInfo, i2);
                        if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                            boolean z5 = btDeviceInfo.mIsLeOutput;
                            if (!this.mDeviceBroker.isDualModeActive() && z5) {
                                BtUtils.setBtVolumeMonitor(btDeviceInfo.mDevice);
                            }
                        }
                    }
                } else if (z2) {
                    AudioDeviceAttributes audioDeviceAttributes =
                            new AudioDeviceAttributes(-2147352576, address);
                    this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
                    this.mConnectedDevices.remove(
                            DeviceInfo.makeDeviceListKey(-2147352576, address));
                    this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
                } else if (z3) {
                    makeA2dpSrcAvailable(address);
                }
            } finally {
            }
        }
    }

    public final void onSetDeviceConnectionStateForceByUser(
            SetForceDeviceState setForceDeviceState) {
        int i = setForceDeviceState.mDevice;
        String str = setForceDeviceState.mAddress;
        String str2 = setForceDeviceState.mActiveBTDeviceName;
        synchronized (this.mDevicesLock) {
            try {
                DeviceInfo deviceInfo =
                        (DeviceInfo)
                                this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(i, str));
                if (deviceInfo == null) {
                    Log.e(
                            "AS.AudioDeviceInventory",
                            "There is no device spec in connected devices");
                    return;
                }
                Log.d(
                        "AS.AudioDeviceInventory",
                        "Device is changed by force ret : "
                                + this.mAudioSystem.setDeviceConnectionState(
                                        new AudioDeviceAttributes(deviceInfo.mDeviceType, str, ""),
                                        2,
                                        0));
                synchronized (this.mCurAudioRoutes) {
                    Log.i("AS.AudioDeviceInventory", "send NEW_ROUTES MSG, BT Name is " + str2);
                    this.mCurAudioRoutes.bluetoothName = str2;
                    this.mForcePath = str2 != null ? "BT" : "OTHERS";
                    AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
                    audioDeviceBroker.getClass();
                    audioDeviceBroker.sendIILMsg(13, 1, 0, 0, null, 0);
                }
            } finally {
            }
        }
    }

    public final void onSetWiredDeviceConnectionState(
            final WiredDeviceConnectionState wiredDeviceConnectionState) {
        AudioDeviceInfo audioDeviceInfo;
        int internalType = wiredDeviceConnectionState.mAttributes.getInternalType();
        AudioService.sDeviceLogger.enqueue(
                new EventLogger.Event(
                        wiredDeviceConnectionState) { // from class:
                                                      // com.android.server.audio.AudioServiceEvents$WiredDevConnectEvent
                    public final AudioDeviceInventory.WiredDeviceConnectionState mState;

                    {
                        this.mState = wiredDeviceConnectionState;
                    }

                    @Override // com.android.server.utils.EventLogger.Event
                    public final String eventToString() {
                        StringBuilder sb =
                                new StringBuilder("setWiredDeviceConnectionState( type:");
                        AudioDeviceInventory.WiredDeviceConnectionState
                                wiredDeviceConnectionState2 = this.mState;
                        sb.append(
                                Integer.toHexString(
                                        wiredDeviceConnectionState2.mAttributes.getInternalType()));
                        sb.append(" (");
                        sb.append(
                                AudioSystem.isInputDevice(
                                                wiredDeviceConnectionState2.mAttributes
                                                        .getInternalType())
                                        ? "source"
                                        : "sink");
                        sb.append(")  state:");
                        sb.append(
                                AudioSystem.deviceStateToString(
                                        wiredDeviceConnectionState2.mState));
                        sb.append(" addr:");
                        sb.append(wiredDeviceConnectionState2.mAttributes.getAddress());
                        sb.append(" name:");
                        sb.append(wiredDeviceConnectionState2.mAttributes.getName());
                        sb.append(") from ");
                        sb.append(wiredDeviceConnectionState2.mCaller);
                        return sb.toString();
                    }
                });
        MediaMetrics.Item item =
                new MediaMetrics.Item("audio.device.onSetWiredDeviceConnectionState")
                        .set(
                                MediaMetrics.Property.ADDRESS,
                                wiredDeviceConnectionState.mAttributes.getAddress())
                        .set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(internalType))
                        .set(
                                MediaMetrics.Property.STATE,
                                wiredDeviceConnectionState.mState == 0
                                        ? "disconnected"
                                        : "connected");
        int i = 2;
        if (wiredDeviceConnectionState.mState == 0
                && AudioSystem.DEVICE_OUT_ALL_USB_SET.contains(
                        Integer.valueOf(
                                wiredDeviceConnectionState.mAttributes.getInternalType()))) {
            AudioDeviceInfo[] devicesStatic = AudioManager.getDevicesStatic(2);
            int length = devicesStatic.length;
            for (int i2 = 0; i2 < length; i2++) {
                audioDeviceInfo = devicesStatic[i2];
                if (audioDeviceInfo.getInternalType()
                        == wiredDeviceConnectionState.mAttributes.getInternalType()) {
                    break;
                }
            }
        }
        audioDeviceInfo = null;
        synchronized (this.mDevicesLock) {
            try {
                if (wiredDeviceConnectionState.mState == 0) {
                    if (((HashSet) DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET)
                            .contains(Integer.valueOf(internalType))) {
                        this.mDeviceBroker.setBluetoothA2dpOnInt(
                                "onSetWiredDeviceConnectionState state DISCONNECTED", true, false);
                    }
                }
                if (!handleDeviceConnection(
                        wiredDeviceConnectionState.mAttributes,
                        wiredDeviceConnectionState.mState == 1,
                        wiredDeviceConnectionState.mForTest,
                        null)) {
                    item.set(
                                    MediaMetrics.Property.EARLY_RETURN,
                                    "change of connection state failed")
                            .record();
                    return;
                }
                if (wiredDeviceConnectionState.mState != 0) {
                    if ((internalType & 12) != 0) {
                        FactoryUtils.increaseEarJackCounter();
                    }
                    if (((HashSet) DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET)
                            .contains(Integer.valueOf(internalType))) {
                        this.mDeviceBroker.setBluetoothA2dpOnInt(
                                "onSetWiredDeviceConnectionState state not DISCONNECTED",
                                false,
                                false);
                    }
                    this.mDeviceBroker.mAudioService.checkMusicActive(
                            internalType, wiredDeviceConnectionState.mCaller);
                }
                if (internalType == 1024) {
                    AudioService.sendMsg(
                            this.mDeviceBroker.mAudioService.mAudioHandler,
                            28,
                            0,
                            wiredDeviceConnectionState.mState,
                            0,
                            wiredDeviceConnectionState.mCaller,
                            0);
                }
                AudioUtils.wakeUpDeviceByWiredHeadset(this.mDeviceBroker.mContext, internalType);
                if (wiredDeviceConnectionState.mState == 0
                        && AudioSystem.DEVICE_OUT_ALL_USB_SET.contains(
                                Integer.valueOf(
                                        wiredDeviceConnectionState.mAttributes
                                                .getInternalType()))) {
                    if (audioDeviceInfo != null) {
                        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
                        audioDeviceBroker.getClass();
                        audioDeviceBroker.mAudioService.dispatchPreferredMixerAttributesChanged(
                                new AudioAttributes.Builder().setUsage(1).build(),
                                audioDeviceInfo.getId(),
                                null);
                    } else {
                        Log.e(
                                "AS.AudioDeviceInventory",
                                "Didn't find AudioDeviceInfo to notify preferred mixer attributes"
                                    + " change for type="
                                        + wiredDeviceConnectionState.mAttributes.getType());
                    }
                }
                sendDeviceConnectionIntent(
                        internalType,
                        wiredDeviceConnectionState.mState,
                        wiredDeviceConnectionState.mAttributes.getAddress(),
                        wiredDeviceConnectionState.mAttributes.getName());
                int i3 = wiredDeviceConnectionState.mState;
                if (internalType == 4) {
                    i = 1;
                } else if (internalType != 8) {
                    if (internalType != 1024) {
                        if (internalType != 4096) {
                            if (internalType != 16384) {
                                if (internalType != 131072) {
                                    if (internalType != 67108864) {
                                        if (internalType != 262144 && internalType != 262145) {
                                            i = 0;
                                        }
                                    }
                                }
                            }
                            i = 16;
                        } else {
                            i = 4;
                        }
                    }
                    i = 8;
                }
                synchronized (this.mCurAudioRoutes) {
                    try {
                        if (i != 0) {
                            AudioRoutesInfo audioRoutesInfo = this.mCurAudioRoutes;
                            int i4 = audioRoutesInfo.mainType;
                            int i5 = i3 != 0 ? i4 | i : (~i) & i4;
                            if (i5 != i4) {
                                audioRoutesInfo.mainType = i5;
                                AudioDeviceBroker audioDeviceBroker2 = this.mDeviceBroker;
                                audioDeviceBroker2.getClass();
                                audioDeviceBroker2.sendIILMsg(13, 1, 0, 0, null, 0);
                            }
                        }
                    } finally {
                    }
                }
                item.record();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSynchronizeAdiDeviceInInventory_l(AdiDeviceState adiDeviceState) {
        AudioDeviceBroker audioDeviceBroker;
        boolean z;
        boolean z2;
        Iterator it = this.mConnectedDevices.values().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            audioDeviceBroker = this.mDeviceBroker;
            z = true;
            if (!hasNext) {
                z2 = false;
                break;
            }
            DeviceInfo deviceInfo = (DeviceInfo) it.next();
            if (deviceInfo.mDeviceType == adiDeviceState.getInternalDeviceType()) {
                String deviceAddress = adiDeviceState.getDeviceAddress();
                String str = deviceInfo.mDeviceAddress;
                boolean equals = str.equals(deviceAddress);
                int i = deviceInfo.mDeviceType;
                if (equals) {
                    for (AdiDeviceState adiDeviceState2 : this.mDeviceInventory.values()) {
                        if (i == adiDeviceState2.getInternalDeviceType()
                                && deviceInfo.mPeerDeviceAddress.equals(
                                        adiDeviceState2.getDeviceAddress())) {
                            if (audioDeviceBroker.isSADevice(adiDeviceState)
                                    == audioDeviceBroker.isSADevice(adiDeviceState2)) {
                                adiDeviceState2.setHasHeadTracker(adiDeviceState.hasHeadTracker());
                                adiDeviceState2.setHeadTrackerEnabled(
                                        adiDeviceState.isHeadTrackerEnabled());
                                adiDeviceState2.setSAEnabled(adiDeviceState.isSAEnabled());
                            }
                            adiDeviceState2.setAudioDeviceCategory(
                                    adiDeviceState.getAudioDeviceCategory());
                            audioDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState2, false);
                            EventLogger eventLogger = AudioService.sDeviceLogger;
                            EventLogger.StringEvent stringEvent =
                                    new EventLogger.StringEvent(
                                            "synchronizeBleDeviceInInventory synced device pair"
                                                + " ads1="
                                                    + adiDeviceState
                                                    + " ads2="
                                                    + adiDeviceState2);
                            stringEvent.printLog(0, "AS.AudioDeviceInventory");
                            eventLogger.enqueue(stringEvent);
                        }
                    }
                }
                if (deviceInfo.mPeerDeviceAddress.equals(adiDeviceState.getDeviceAddress())) {
                    for (AdiDeviceState adiDeviceState3 : this.mDeviceInventory.values()) {
                        if (i == adiDeviceState3.getInternalDeviceType()
                                && str.equals(adiDeviceState3.getDeviceAddress())) {
                            if (audioDeviceBroker.isSADevice(adiDeviceState)
                                    == audioDeviceBroker.isSADevice(adiDeviceState3)) {
                                adiDeviceState3.setHasHeadTracker(adiDeviceState.hasHeadTracker());
                                adiDeviceState3.setHeadTrackerEnabled(
                                        adiDeviceState.isHeadTrackerEnabled());
                                adiDeviceState3.setSAEnabled(adiDeviceState.isSAEnabled());
                            }
                            adiDeviceState3.setAudioDeviceCategory(
                                    adiDeviceState.getAudioDeviceCategory());
                            audioDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState3, false);
                            EventLogger eventLogger2 = AudioService.sDeviceLogger;
                            EventLogger.StringEvent stringEvent2 =
                                    new EventLogger.StringEvent(
                                            "synchronizeBleDeviceInInventory synced device pair"
                                                + " ads1="
                                                    + adiDeviceState
                                                    + " peer ads2="
                                                    + adiDeviceState3);
                            stringEvent2.printLog(0, "AS.AudioDeviceInventory");
                            eventLogger2.enqueue(stringEvent2);
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        z2 = true;
        if (Flags.automaticBtDeviceType()) {
            Iterator it2 = this.mDeviceInventory.values().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                AdiDeviceState adiDeviceState4 = (AdiDeviceState) it2.next();
                if (adiDeviceState.getInternalDeviceType()
                                != adiDeviceState4.getInternalDeviceType()
                        && adiDeviceState
                                .getDeviceAddress()
                                .equals(adiDeviceState4.getDeviceAddress())) {
                    if (audioDeviceBroker.isSADevice(adiDeviceState)
                            == audioDeviceBroker.isSADevice(adiDeviceState4)) {
                        adiDeviceState4.setHasHeadTracker(adiDeviceState.hasHeadTracker());
                        adiDeviceState4.setHeadTrackerEnabled(
                                adiDeviceState.isHeadTrackerEnabled());
                        adiDeviceState4.setSAEnabled(adiDeviceState.isSAEnabled());
                    }
                    adiDeviceState4.setAudioDeviceCategory(adiDeviceState.getAudioDeviceCategory());
                    audioDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState4, false);
                    EventLogger eventLogger3 = AudioService.sDeviceLogger;
                    EventLogger.StringEvent stringEvent3 =
                            new EventLogger.StringEvent(
                                    "synchronizeDeviceProfilesInInventory synced device pair ads1="
                                            + adiDeviceState
                                            + " ads2="
                                            + adiDeviceState4);
                    stringEvent3.printLog(0, "AS.AudioDeviceInventory");
                    eventLogger3.enqueue(stringEvent3);
                }
            }
            z2 |= z;
        }
        if (z2) {
            audioDeviceBroker.postPersistAudioDeviceSettings();
        }
    }

    public final void onSynchronizeAdiDevicesInInventory(AdiDeviceState adiDeviceState) {
        synchronized (this.mDevicesLock) {
            synchronized (this.mDeviceInventoryLock) {
                try {
                    if (adiDeviceState != null) {
                        onSynchronizeAdiDeviceInInventory_l(adiDeviceState);
                    } else {
                        Iterator it = this.mDeviceInventory.values().iterator();
                        while (it.hasNext()) {
                            onSynchronizeAdiDeviceInInventory_l((AdiDeviceState) it.next());
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void onToggleHdmi() {
        MediaMetrics.Item item =
                new MediaMetrics.Item("audio.device.onToggleHdmi")
                        .set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(1024));
        synchronized (this.mDevicesLock) {
            try {
                if (((DeviceInfo)
                                this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(1024, "")))
                        == null) {
                    Log.e("AS.AudioDeviceInventory", "invalid null DeviceInfo in onToggleHdmi");
                    item.set(MediaMetrics.Property.EARLY_RETURN, "invalid null DeviceInfo")
                            .record();
                } else {
                    setWiredDeviceConnectionState(
                            new AudioDeviceAttributes(1024, ""), 0, "android");
                    setWiredDeviceConnectionState(
                            new AudioDeviceAttributes(1024, ""), 1, "android");
                    item.record();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onUpdateLeAudioGroupAddresses(int i) {
        synchronized (this.mDevicesLock) {
            try {
                List arrayList = new ArrayList();
                for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                    if (deviceInfo.mGroupId == i) {
                        if (arrayList.isEmpty()) {
                            arrayList = this.mDeviceBroker.getLeAudioGroupAddresses(i);
                        }
                        if (deviceInfo.mPeerDeviceAddress.equals("")) {
                            Iterator it = arrayList.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                Pair pair = (Pair) it.next();
                                if (!deviceInfo.mDeviceAddress.equals(pair.first)) {
                                    deviceInfo.mPeerDeviceAddress =
                                            TextUtils.emptyIfNull((String) pair.first);
                                    deviceInfo.mPeerIdentityDeviceAddress =
                                            TextUtils.emptyIfNull((String) pair.second);
                                    break;
                                }
                            }
                        } else if (!arrayList.contains(
                                new Pair(
                                        deviceInfo.mPeerDeviceAddress,
                                        deviceInfo.mPeerIdentityDeviceAddress))) {
                            deviceInfo.mPeerDeviceAddress = "";
                            deviceInfo.mPeerIdentityDeviceAddress = "";
                        }
                        if (deviceInfo.mDeviceIdentityAddress.equals("")) {
                            Iterator it2 = arrayList.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    Pair pair2 = (Pair) it2.next();
                                    if (deviceInfo.mDeviceAddress.equals(pair2.first)) {
                                        deviceInfo.mDeviceIdentityAddress =
                                                TextUtils.emptyIfNull((String) pair2.second);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void purgeDevicesRoles_l() {
        purgeRoles(
                this.mAppliedStrategyRolesInt,
                new AudioDeviceInventory$$ExternalSyntheticLambda2(this, 3));
        purgeRoles(
                this.mAppliedPresetRolesInt,
                new AudioDeviceInventory$$ExternalSyntheticLambda2(this, 4));
        reapplyExternalDevicesRoles();
    }

    public final void reapplyExternalDevicesRoles() {
        synchronized (this.mDevicesLock) {
            this.mAppliedStrategyRoles.clear();
            this.mAppliedPresetRoles.clear();
        }
        synchronized (this.mPreferredDevices) {
            final int i = 0;
            this.mPreferredDevices.forEach(
                    new BiConsumer(
                            this) { // from class:
                                    // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda38
                        public final /* synthetic */ AudioDeviceInventory f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            int i2 = i;
                            AudioDeviceInventory audioDeviceInventory = this.f$0;
                            Integer num = (Integer) obj;
                            List list = (List) obj2;
                            audioDeviceInventory.getClass();
                            switch (i2) {
                                case 0:
                                    int intValue = num.intValue();
                                    SafeCloseable create = ClearCallingIdentityContext.create();
                                    try {
                                        audioDeviceInventory.setDevicesRoleForStrategy(
                                                intValue, false, list);
                                        if (create != null) {
                                            create.close();
                                            return;
                                        }
                                        return;
                                    } catch (Throwable th) {
                                        if (create != null) {
                                            try {
                                                create.close();
                                            } catch (Throwable th2) {
                                                th.addSuppressed(th2);
                                            }
                                        }
                                        throw th;
                                    }
                                case 1:
                                    audioDeviceInventory.addDevicesRoleForStrategy(
                                            num.intValue(), false, list);
                                    return;
                                default:
                                    AudioDeviceInventory.setDevicesRole(
                                            audioDeviceInventory.mAppliedPresetRoles,
                                            new AudioDeviceInventory$$ExternalSyntheticLambda2(
                                                    audioDeviceInventory, 8),
                                            new AudioDeviceInventory$$ExternalSyntheticLambda2(
                                                    audioDeviceInventory, 9),
                                            num.intValue(),
                                            list);
                                    return;
                            }
                        }
                    });
        }
        synchronized (this.mNonDefaultDevices) {
            final int i2 = 1;
            this.mNonDefaultDevices.forEach(
                    new BiConsumer(
                            this) { // from class:
                                    // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda38
                        public final /* synthetic */ AudioDeviceInventory f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            int i22 = i2;
                            AudioDeviceInventory audioDeviceInventory = this.f$0;
                            Integer num = (Integer) obj;
                            List list = (List) obj2;
                            audioDeviceInventory.getClass();
                            switch (i22) {
                                case 0:
                                    int intValue = num.intValue();
                                    SafeCloseable create = ClearCallingIdentityContext.create();
                                    try {
                                        audioDeviceInventory.setDevicesRoleForStrategy(
                                                intValue, false, list);
                                        if (create != null) {
                                            create.close();
                                            return;
                                        }
                                        return;
                                    } catch (Throwable th) {
                                        if (create != null) {
                                            try {
                                                create.close();
                                            } catch (Throwable th2) {
                                                th.addSuppressed(th2);
                                            }
                                        }
                                        throw th;
                                    }
                                case 1:
                                    audioDeviceInventory.addDevicesRoleForStrategy(
                                            num.intValue(), false, list);
                                    return;
                                default:
                                    AudioDeviceInventory.setDevicesRole(
                                            audioDeviceInventory.mAppliedPresetRoles,
                                            new AudioDeviceInventory$$ExternalSyntheticLambda2(
                                                    audioDeviceInventory, 8),
                                            new AudioDeviceInventory$$ExternalSyntheticLambda2(
                                                    audioDeviceInventory, 9),
                                            num.intValue(),
                                            list);
                                    return;
                            }
                        }
                    });
        }
        synchronized (this.mPreferredDevicesForCapturePreset) {
            final int i3 = 2;
            this.mPreferredDevicesForCapturePreset.forEach(
                    new BiConsumer(
                            this) { // from class:
                                    // com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda38
                        public final /* synthetic */ AudioDeviceInventory f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            int i22 = i3;
                            AudioDeviceInventory audioDeviceInventory = this.f$0;
                            Integer num = (Integer) obj;
                            List list = (List) obj2;
                            audioDeviceInventory.getClass();
                            switch (i22) {
                                case 0:
                                    int intValue = num.intValue();
                                    SafeCloseable create = ClearCallingIdentityContext.create();
                                    try {
                                        audioDeviceInventory.setDevicesRoleForStrategy(
                                                intValue, false, list);
                                        if (create != null) {
                                            create.close();
                                            return;
                                        }
                                        return;
                                    } catch (Throwable th) {
                                        if (create != null) {
                                            try {
                                                create.close();
                                            } catch (Throwable th2) {
                                                th.addSuppressed(th2);
                                            }
                                        }
                                        throw th;
                                    }
                                case 1:
                                    audioDeviceInventory.addDevicesRoleForStrategy(
                                            num.intValue(), false, list);
                                    return;
                                default:
                                    AudioDeviceInventory.setDevicesRole(
                                            audioDeviceInventory.mAppliedPresetRoles,
                                            new AudioDeviceInventory$$ExternalSyntheticLambda2(
                                                    audioDeviceInventory, 8),
                                            new AudioDeviceInventory$$ExternalSyntheticLambda2(
                                                    audioDeviceInventory, 9),
                                            num.intValue(),
                                            list);
                                    return;
                            }
                        }
                    });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0066, code lost:

       if (r17 != 262145) goto L66;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendDeviceConnectionIntent(
            int r17, int r18, java.lang.String r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.AudioDeviceInventory.sendDeviceConnectionIntent(int,"
                    + " int, java.lang.String, java.lang.String):void");
    }

    public final void setBluetoothActiveDevice(AudioDeviceBroker.BtDeviceInfo btDeviceInfo) {
        int i;
        int i2;
        synchronized (this.mDevicesLock) {
            try {
                if (btDeviceInfo.mSupprNoisy
                        || !((((i2 = btDeviceInfo.mProfile) == 22 || i2 == 26)
                                        && btDeviceInfo.mIsLeOutput)
                                || i2 == 21
                                || i2 == 2)) {
                    i = 0;
                } else {
                    i =
                            checkSendBecomingNoisyIntentInt(
                                    btDeviceInfo.mAudioSystemDevice,
                                    isBtStateConnected(btDeviceInfo) ? 1 : 0,
                                    btDeviceInfo.mMusicDevice);
                }
                int i3 = i;
                Log.i(
                        "AS.AudioDeviceInventory",
                        "setBluetoothActiveDevice "
                                + btDeviceInfo.toString()
                                + " delay(ms): "
                                + i3);
                this.mDeviceBroker.sendIILMsg(7, 2, 0, 0, btDeviceInfo, i3);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setCurrentAudioRouteNameIfPossible(String str, boolean z) {
        synchronized (this.mCurAudioRoutes) {
            try {
                if (TextUtils.equals(this.mCurAudioRoutes.bluetoothName, str)) {
                    return;
                }
                if (str != null
                        || !this.mConnectedDevices.values().stream()
                                .anyMatch(
                                        new AudioDeviceInventory$$ExternalSyntheticLambda34(
                                                2, this))) {
                    this.mCurAudioRoutes.bluetoothName = str;
                    AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
                    audioDeviceBroker.getClass();
                    audioDeviceBroker.sendIILMsg(z ? 36 : 13, 1, 0, 0, null, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int setDeviceToForceByUser(int i, String str, boolean z) {
        String str2;
        String str3;
        int i2;
        EventLogger eventLogger = AudioService.sDeviceLogger;
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "setDeviceToForceByUser : device = ", " address = ");
        m.append(AudioManagerHelper.getAddressForLog(str));
        m.append(" force = ");
        m.append(z);
        m.append(" uid = ");
        m.append(Binder.getCallingUid());
        m.append(" pid = ");
        m.append(Binder.getCallingPid());
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(m.toString());
        stringEvent.printLog(0, "AS.AudioDeviceInventory");
        eventLogger.enqueue(stringEvent);
        if (i == 32768) {
            Log.d(
                    "AS.AudioDeviceInventory",
                    "setDeviceToForceByUser: remote submix should use address 0");
            str2 = "0";
        } else {
            str2 = str;
        }
        if (Binder.getCallingUid() == 1002
                && !z
                && this.mDeviceBroker.mAudioService.isMultiSoundOn()
                && this.mDeviceBroker.mAudioService.getPinDeviceInternal() == 128
                && this.mDeviceBroker.mAudioService.getDeviceForStream(3) != 128) {
            Log.d("AS.AudioDeviceInventory", "Device does not change while MultiSound On");
            String changeActiveBluetoothDevice = changeActiveBluetoothDevice(str2);
            synchronized (this.mCurAudioRoutes) {
                this.mCurAudioRoutes.bluetoothName = changeActiveBluetoothDevice;
            }
            return 0;
        }
        synchronized (this.mDevicesLock) {
            try {
                if (((DeviceInfo) this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(i, str2)))
                                == null
                        && !AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(i))) {
                    Log.e(
                            "AS.AudioDeviceInventory",
                            "There is no device spec in connected devices");
                    return -1;
                }
                if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
                    str3 = changeActiveBluetoothDevice(str2);
                    this.mDeviceBroker.setBluetoothA2dpOnInt(
                            "setDeviceToForceByUser(true) from u/pid:"
                                    + Binder.getCallingUid()
                                    + "/"
                                    + Binder.getCallingPid(),
                            true,
                            false);
                } else if (AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(i))) {
                    str3 = changeActiveBleDevice(str2);
                    this.mDeviceBroker.updateLeAudioVolume(i);
                    if (str3 == null) {
                        return -1;
                    }
                    this.mDeviceBroker.setBluetoothA2dpOnInt(
                            "setDeviceToForceByUser(true) BLE address = "
                                    + AudioManagerHelper.getAddressForLog(str2)
                                    + " from u/pid:"
                                    + Binder.getCallingUid()
                                    + "/"
                                    + Binder.getCallingPid(),
                            true,
                            false);
                } else {
                    BluetoothA2dp a2dp = this.mDeviceBroker.getA2dp();
                    if (a2dp != null) {
                        int audioPath = a2dp.setAudioPath(i);
                        Log.d("AS.AudioDeviceInventory", "setAudioPath delay : " + audioPath);
                        i2 = audioPath;
                        str3 = null;
                        this.mDeviceBroker.sendIILMsg(
                                2760, 2, 0, 0, new SetForceDeviceState(i, str2, str3), i2);
                        return 0;
                    }
                    str3 = null;
                }
                i2 = 0;
                this.mDeviceBroker.sendIILMsg(
                        2760, 2, 0, 0, new SetForceDeviceState(i, str2, str3), i2);
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int setDevicesRoleForStrategy(int i, boolean z, List list) {
        return setDevicesRole(
                z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles,
                new AudioDeviceInventory$$ExternalSyntheticLambda2(this, 0),
                new AudioDeviceInventory$$ExternalSyntheticLambda2(this, 6),
                i,
                list);
    }

    public final void setWiredDeviceConnectionState(
            AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
        synchronized (this.mDevicesLock) {
            try {
                int internalType = audioDeviceAttributes.getInternalType();
                if (i != 0
                        && AudioUtils.isWiredDeviceType(internalType)
                        && KnoxAudioUtils.isRestrictedHeadphone(this.mDeviceBroker.mContext)) {
                    if (!checkDeviceConnected(internalType)) {
                        return;
                    } else {
                        i = 0;
                    }
                }
                int checkSendBecomingNoisyIntentInt =
                        checkSendBecomingNoisyIntentInt(
                                audioDeviceAttributes.getInternalType(), i, 0);
                if ("h2w-before-boot-completed".equals(audioDeviceAttributes.getName())) {
                    int internalType2 = audioDeviceAttributes.getInternalType();
                    if ((!this.mSystemReady && internalType2 == 4) || internalType2 == 8) {
                        this.mDeviceBroker.sendIILMsg(
                                2,
                                2,
                                0,
                                0,
                                new WiredDeviceConnectionState(
                                        new AudioDeviceAttributes(
                                                internalType2,
                                                audioDeviceAttributes.getAddress(),
                                                "h2w"),
                                        i,
                                        str),
                                checkSendBecomingNoisyIntentInt);
                    }
                } else {
                    this.mDeviceBroker.sendIILMsg(
                            2,
                            2,
                            0,
                            0,
                            new WiredDeviceConnectionState(audioDeviceAttributes, i, str),
                            checkSendBecomingNoisyIntentInt);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateBluetoothPreferredModes_l(BluetoothDevice bluetoothDevice) {
        if (this.mBluetoothDualModeEnabled) {
            HashSet hashSet = new HashSet(0);
            for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                if (AudioSystem.isBluetoothDevice(deviceInfo.mDeviceType)) {
                    String str = deviceInfo.mDeviceAddress;
                    if (!hashSet.contains(str)) {
                        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                        Bundle preferredAudioProfiles =
                                defaultAdapter.getPreferredAudioProfiles(
                                        defaultAdapter.getRemoteDevice(str));
                        Log.i(
                                "AS.AudioDeviceInventory",
                                "updateBluetoothPreferredModes_l processing device address: "
                                        + str
                                        + ", preferredProfiles: "
                                        + preferredAudioProfiles);
                        for (DeviceInfo deviceInfo2 : this.mConnectedDevices.values()) {
                            if (AudioSystem.isBluetoothDevice(deviceInfo2.mDeviceType)
                                    && str.equals(deviceInfo2.mDeviceAddress)) {
                                int i = deviceInfo2.mDeviceType;
                                int i2 =
                                        AudioSystem.isBluetoothA2dpOutDevice(i)
                                                ? 2
                                                : AudioSystem.isBluetoothScoDevice(i)
                                                        ? 1
                                                        : AudioSystem.isBluetoothLeDevice(i)
                                                                ? 22
                                                                : 0;
                                if (i2 != 0) {
                                    int checkProfileIsConnected =
                                            checkProfileIsConnected(
                                                    preferredAudioProfiles.getInt(
                                                            "audio_mode_duplex"));
                                    if (checkProfileIsConnected == i2
                                            || checkProfileIsConnected == 0) {
                                        deviceInfo2.mDisabledModes.remove("audio_mode_duplex");
                                    } else {
                                        deviceInfo2.mDisabledModes.add("audio_mode_duplex");
                                    }
                                    int checkProfileIsConnected2 =
                                            checkProfileIsConnected(
                                                    preferredAudioProfiles.getInt(
                                                            "audio_mode_output_only"));
                                    if (checkProfileIsConnected2 == i2
                                            || checkProfileIsConnected2 == 0) {
                                        deviceInfo2.mDisabledModes.remove("audio_mode_output_only");
                                    } else {
                                        deviceInfo2.mDisabledModes.add("audio_mode_output_only");
                                    }
                                }
                            }
                        }
                        hashSet.add(str);
                    }
                }
            }
            applyConnectedDevicesRoles_l();
            if (bluetoothDevice != null) {
                this.mDeviceBroker.sendLMsgNoDelay(52, 2, bluetoothDevice);
            }
        }
    }

    public final void updateBtVolumeMonitor() {
        BluetoothLeAudio bluetoothLeAudio;
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        BluetoothA2dp a2dp = audioDeviceBroker.getA2dp();
        BtHelper btHelper = audioDeviceBroker.mBtHelper;
        synchronized (btHelper) {
            bluetoothLeAudio = btHelper.mLeAudio;
        }
        Boolean bool = Boolean.FALSE;
        if (a2dp != null) {
            List<BluetoothDevice> connectedDevices = a2dp.getConnectedDevices();
            if (connectedDevices.size() != 0) {
                Iterator<BluetoothDevice> it = connectedDevices.iterator();
                while (it.hasNext()) {
                    BtUtils.setBtVolumeMonitor(it.next());
                    bool = Boolean.TRUE;
                }
            }
        }
        if (bluetoothLeAudio != null) {
            List<BluetoothDevice> connectedDevices2 = bluetoothLeAudio.getConnectedDevices();
            if (connectedDevices2.size() != 0) {
                for (BluetoothDevice bluetoothDevice : connectedDevices2) {
                    if (isBleHeadsetConnected(bluetoothDevice.getAddress())) {
                        BtUtils.setBtVolumeMonitor(bluetoothDevice);
                        bool = Boolean.TRUE;
                    }
                }
            }
        }
        if (bool.booleanValue()) {
            return;
        }
        BtUtils.setBtVolumeMonitor(false);
    }

    public final void updateDexState() {
        DesktopModeHelper desktopModeHelper = this.mDeviceBroker.mAudioService.mDesktopModeHelper;
        boolean settingsAsUser =
                DesktopModeSettings.getSettingsAsUser(
                        desktopModeHelper.mResolver,
                        "audio_output_to_display",
                        false,
                        DesktopModeSettings.sCurrentUserId);
        if (desktopModeHelper.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1
                && !settingsAsUser
                && !desktopModeHelper.mDexState) {
            desktopModeHelper.mDexState = true;
            desktopModeHelper.setDexPolicyParameter("dex");
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "AS.DesktopModeHelper",
                    new StringBuilder("The dex mode is available. "),
                    desktopModeHelper.mDexState);
        } else if (desktopModeHelper.mDexState) {
            boolean equals =
                    "dual"
                            .equals(
                                    DesktopModeSettings.getSettingsAsUser(
                                            desktopModeHelper.mResolver,
                                            "external_display_mode",
                                            "dual",
                                            DesktopModeSettings.sCurrentUserId));
            SemDesktopModeManager semDesktopModeManager = desktopModeHelper.mDesktopModeManager;
            if (semDesktopModeManager == null
                    || semDesktopModeManager.getDesktopModeState() == null) {
                return;
            }
            int displayType =
                    desktopModeHelper.mDesktopModeManager.getDesktopModeState().getDisplayType();
            if (equals || displayType != 101) {
                return;
            }
            Log.i("AS.DesktopModeHelper", "isDexMirroringMode mirroring mode.");
            Log.i("AS.DesktopModeHelper", "The dex mode changed to mirrored mode from tablet mode");
            desktopModeHelper.setDexPolicyParameter(settingsAsUser ? "none" : "dex");
        }
    }
}
