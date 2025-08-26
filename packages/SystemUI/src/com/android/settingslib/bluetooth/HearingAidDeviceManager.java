package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.audiopolicy.AudioProductStrategy;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.FeatureFlagUtils;
import android.util.Log;
import androidx.collection.ArraySet;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.HearingAidInfo;
import com.android.systemui.accessibility.floatingmenu.MenuInfoRepository$$ExternalSyntheticLambda1;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class HearingAidDeviceManager {
    public static final boolean DEBUG = BluetoothUtils.DEBUG;
    public static final Map mConnectionStatusListeners = new ConcurrentHashMap();
    public final LocalBluetoothManager mBtManager;
    public final List mCachedDevices;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final List mFilteredCachedDevices;
    public final HearingAidAudioRoutingHelper mRoutingHelper;
    public int mDevicesConnectionStatus = -1;
    public boolean mInitialDevicesConnectionStatusUpdate = false;

    public HearingAidDeviceManager(Context context, LocalBluetoothManager localBluetoothManager, List<CachedBluetoothDevice> list, List<CachedBluetoothDevice> list2) {
        this.mFilteredCachedDevices = list2;
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mBtManager = localBluetoothManager;
        this.mCachedDevices = list;
        this.mRoutingHelper = new HearingAidAudioRoutingHelper(context);
    }

    public static boolean isValidHiSyncId(long j) {
        return j != 0;
    }

    public final void clearLocalDataIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        ListeningExecutorService listeningExecutorService;
        Context context = this.mContext;
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        final HearingDeviceLocalDataManager hearingDeviceLocalDataManager = new HearingDeviceLocalDataManager(context);
        hearingDeviceLocalDataManager.getLocalDataFromSettings();
        if (bluetoothDevice != null) {
            synchronized (HearingDeviceLocalDataManager.sLock) {
                try {
                    final String anonymizedAddress = bluetoothDevice.getAnonymizedAddress();
                    ((HashMap) hearingDeviceLocalDataManager.mAddrToDataMap).remove(anonymizedAddress);
                    if (hearingDeviceLocalDataManager.mListener != null && (listeningExecutorService = hearingDeviceLocalDataManager.mListenerExecutor) != null) {
                        listeningExecutorService.execute(new Runnable(anonymizedAddress) { // from class: com.android.settingslib.bluetooth.HearingDeviceLocalDataManager$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.mListener.getClass();
                            }
                        });
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        hearingDeviceLocalDataManager.putAmbientVolumeSettings();
    }

    public final CachedBluetoothDevice findMainDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice2;
        List<CachedBluetoothDevice> list = this.mCachedDevices;
        if (list == null) {
            return null;
        }
        loop0: for (CachedBluetoothDevice cachedBluetoothDevice3 : list) {
            if (cachedBluetoothDevice3.mGroupId != -1) {
                Iterator it = ((HashSet) cachedBluetoothDevice3.mMemberDevices).iterator();
                while (it.hasNext()) {
                    CachedBluetoothDevice cachedBluetoothDevice4 = (CachedBluetoothDevice) it.next();
                    if (cachedBluetoothDevice4 != null && cachedBluetoothDevice4.equals(cachedBluetoothDevice)) {
                        break loop0;
                    }
                }
                if (isValidHiSyncId(cachedBluetoothDevice3.getHiSyncId()) || (cachedBluetoothDevice2 = cachedBluetoothDevice3.mSubDevice) == null || !cachedBluetoothDevice2.equals(cachedBluetoothDevice)) {
                }
            } else if (isValidHiSyncId(cachedBluetoothDevice3.getHiSyncId())) {
            }
            return cachedBluetoothDevice3;
        }
        return null;
    }

    public final void initHearingAidDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        int deviceSide;
        int deviceMode;
        HearingAidProfile hearingAidProfile;
        BluetoothHearingAid bluetoothHearingAid;
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
        long hiSyncId = 0;
        if (localBluetoothProfileManager != null && (hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile) != null && (bluetoothHearingAid = hearingAidProfile.mService) != null && bluetoothDevice != null) {
            hiSyncId = bluetoothHearingAid.getHiSyncId(bluetoothDevice);
        }
        if (isValidHiSyncId(hiSyncId)) {
            HearingAidInfo.Builder builder = new HearingAidInfo.Builder();
            BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
            LocalBluetoothProfileManager localBluetoothProfileManager2 = localBluetoothManager.mProfileManager;
            if (localBluetoothProfileManager2 != null) {
                HearingAidProfile hearingAidProfile2 = localBluetoothProfileManager2.mHearingAidProfile;
                if (hearingAidProfile2 == null) {
                    Log.w("HearingAidDeviceManager", "HearingAidProfile is not supported and not ready to fetch device side");
                } else {
                    BluetoothHearingAid bluetoothHearingAid2 = hearingAidProfile2.mService;
                    if (bluetoothHearingAid2 == null) {
                        Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                    } else {
                        deviceSide = bluetoothHearingAid2.getDeviceSide(bluetoothDevice2);
                    }
                }
                deviceSide = -1;
            } else {
                deviceSide = -1;
            }
            builder.mSide = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(deviceSide, -1);
            BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
            if (localBluetoothProfileManager2 != null) {
                HearingAidProfile hearingAidProfile3 = localBluetoothProfileManager2.mHearingAidProfile;
                if (hearingAidProfile3 == null) {
                    Log.w("HearingAidDeviceManager", "HearingAidProfile is not supported and not ready to fetch device mode");
                } else {
                    BluetoothHearingAid bluetoothHearingAid3 = hearingAidProfile3.mService;
                    if (bluetoothHearingAid3 == null) {
                        Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                    } else {
                        deviceMode = bluetoothHearingAid3.getDeviceMode(bluetoothDevice3);
                    }
                }
                deviceMode = -1;
            } else {
                deviceMode = -1;
            }
            builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(deviceMode, -1);
            builder.mHiSyncId = hiSyncId;
            cachedBluetoothDevice.setHearingAidInfo(builder.build());
        }
    }

    public final synchronized void notifyDevicesConnectionStatusChanged() {
        int i = this.mDevicesConnectionStatus;
        updateDevicesConnectionStatus();
        final int i2 = this.mDevicesConnectionStatus;
        if (i2 != i) {
            ((ConcurrentHashMap) mConnectionStatusListeners).forEach(new BiConsumer() { // from class: com.android.settingslib.bluetooth.HearingAidDeviceManager$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    final int i3 = i2;
                    final MenuInfoRepository$$ExternalSyntheticLambda1 menuInfoRepository$$ExternalSyntheticLambda1 = (MenuInfoRepository$$ExternalSyntheticLambda1) obj;
                    boolean z = HearingAidDeviceManager.DEBUG;
                    ((Executor) obj2).execute(new Runnable() { // from class: com.android.settingslib.bluetooth.HearingAidDeviceManager$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            MenuInfoRepository$$ExternalSyntheticLambda1 menuInfoRepository$$ExternalSyntheticLambda12 = menuInfoRepository$$ExternalSyntheticLambda1;
                            int i4 = i3;
                            boolean z2 = HearingAidDeviceManager.DEBUG;
                            menuInfoRepository$$ExternalSyntheticLambda12.f$0.mSettingsContentsCallback.onDevicesConnectionStatusChanged(i4);
                        }
                    });
                }
            });
        }
    }

    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean zIsEnabled = FeatureFlagUtils.isEnabled(this.mContext, "settings_audio_routing");
        HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper = this.mRoutingHelper;
        if (zIsEnabled) {
            AudioDeviceAttributes audioDeviceAttributes = null;
            if (cachedBluetoothDevice.isConnectedHearingAidDevice() && (cachedBluetoothDevice.isActiveDevice(21) || cachedBluetoothDevice.isActiveDevice(22))) {
                hearingAidAudioRoutingHelper.getClass();
                if (cachedBluetoothDevice.isHearingAidDevice()) {
                    AudioDeviceInfo[] devices = hearingAidAudioRoutingHelper.mAudioManager.getDevices(2);
                    int length = devices.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        AudioDeviceInfo audioDeviceInfo = devices[i];
                        if ((audioDeviceInfo.getType() == 23 || audioDeviceInfo.getType() == 26) && HearingAidAudioRoutingHelper.matchAddress(cachedBluetoothDevice, audioDeviceInfo)) {
                            audioDeviceAttributes = new AudioDeviceAttributes(audioDeviceInfo);
                            break;
                        }
                        i++;
                    }
                }
                if (audioDeviceAttributes == null) {
                    Log.w("HearingAidDeviceManager", "Can not find expected AudioDeviceAttributes for hearing device: " + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
                } else {
                    int i2 = Settings.Secure.getInt(this.mContentResolver, "hearing_aid_call_routing", 0);
                    int i3 = Settings.Secure.getInt(this.mContentResolver, "hearing_aid_media_routing", 0);
                    int i4 = Settings.Secure.getInt(this.mContentResolver, "hearing_aid_ringtone_routing", 0);
                    int i5 = Settings.Secure.getInt(this.mContentResolver, "hearing_aid_notification_routing", 0);
                    setPreferredDeviceRoutingStrategies(i2, audioDeviceAttributes, HearingAidAudioRoutingConstants.CALL_ROUTING_ATTRIBUTES);
                    setPreferredDeviceRoutingStrategies(i3, audioDeviceAttributes, HearingAidAudioRoutingConstants.MEDIA_ROUTING_ATTRIBUTES);
                    setPreferredDeviceRoutingStrategies(i4, audioDeviceAttributes, HearingAidAudioRoutingConstants.RINGTONE_ROUTING_ATTRIBUTES);
                    setPreferredDeviceRoutingStrategies(i5, audioDeviceAttributes, HearingAidAudioRoutingConstants.NOTIFICATION_ROUTING_ATTRIBUTES);
                }
            } else {
                setPreferredDeviceRoutingStrategies(0, null, HearingAidAudioRoutingConstants.CALL_ROUTING_ATTRIBUTES);
                setPreferredDeviceRoutingStrategies(0, null, HearingAidAudioRoutingConstants.MEDIA_ROUTING_ATTRIBUTES);
                setPreferredDeviceRoutingStrategies(0, null, HearingAidAudioRoutingConstants.RINGTONE_ROUTING_ATTRIBUTES);
                setPreferredDeviceRoutingStrategies(0, null, HearingAidAudioRoutingConstants.NOTIFICATION_ROUTING_ATTRIBUTES);
            }
        }
        if (cachedBluetoothDevice.isConnectedHearingAidDevice() && (cachedBluetoothDevice.isActiveDevice(21) || cachedBluetoothDevice.isActiveDevice(22))) {
            if (hearingAidAudioRoutingHelper.setPreferredInputDeviceForCalls(cachedBluetoothDevice, cachedBluetoothDevice.mDevice.isMicrophonePreferredForCalls() ? 0 : 2)) {
                return;
            }
            Log.d("HearingAidDeviceManager", "Fail to configure setPreferredInputDeviceForCalls");
        } else {
            if (hearingAidAudioRoutingHelper.mAudioManager.clearPreferredDevicesForCapturePreset(7)) {
                return;
            }
            Log.d("HearingAidDeviceManager", "Fail to configure clearMicrophoneForCalls");
        }
    }

    public void onHiSyncIdChanged(long j) {
        CachedBluetoothDevice cachedBluetoothDevice;
        int size = this.mCachedDevices.size() - 1;
        int i = -1;
        while (size >= 0) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) this.mCachedDevices.get(size);
            if (cachedBluetoothDevice2.getHiSyncId() == j) {
                if (cachedBluetoothDevice2.getProfiles().stream().anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0(0))) {
                    Log.w("HearingAidDeviceManager", "Skip ASHA grouping since this device supports CSIP");
                } else {
                    if (i != -1) {
                        if (cachedBluetoothDevice2.isConnected()) {
                            cachedBluetoothDevice = (CachedBluetoothDevice) this.mCachedDevices.get(i);
                            size = i;
                        } else {
                            cachedBluetoothDevice2 = (CachedBluetoothDevice) this.mCachedDevices.get(i);
                            cachedBluetoothDevice = cachedBluetoothDevice2;
                        }
                        cachedBluetoothDevice2.setSubDevice(cachedBluetoothDevice);
                        List list = this.mFilteredCachedDevices;
                        if (list != null) {
                            list.remove(this.mCachedDevices.get(size));
                        }
                        this.mCachedDevices.remove(size);
                        String str = "onHiSyncIdChanged: removed from UI device =" + cachedBluetoothDevice + ", with hiSyncId=" + j;
                        if (DEBUG) {
                            Log.d("HearingAidDeviceManager", str);
                        }
                        this.mBtManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice);
                        return;
                    }
                    i = size;
                }
            }
            size--;
        }
    }

    public final boolean onProfileConnectionStateChangedIfProcessed(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (i != 0) {
            if (i != 2) {
                return false;
            }
            onHiSyncIdChanged(cachedBluetoothDevice.getHiSyncId());
            CachedBluetoothDevice cachedBluetoothDeviceFindMainDevice = findMainDevice(cachedBluetoothDevice);
            if (cachedBluetoothDeviceFindMainDevice == null) {
                return false;
            }
            if (!cachedBluetoothDeviceFindMainDevice.isConnected()) {
                switchDeviceContent(cachedBluetoothDeviceFindMainDevice, cachedBluetoothDevice);
                return true;
            }
        } else if (!cachedBluetoothDevice.mUnpairing && findMainDevice(cachedBluetoothDevice) == null) {
            CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
            CachedBluetoothDevice cachedBluetoothDevice3 = (cachedBluetoothDevice2 == null || !cachedBluetoothDevice2.isConnected()) ? (CachedBluetoothDevice) cachedBluetoothDevice.mMemberDevices.stream().filter(new HearingAidDeviceManager$$ExternalSyntheticLambda0(5)).findAny().orElse(null) : cachedBluetoothDevice.mSubDevice;
            if (cachedBluetoothDevice3 == null) {
                return false;
            }
            switchDeviceContent(cachedBluetoothDevice, cachedBluetoothDevice3);
            return true;
        }
        return true;
    }

    public final void setPreferredDeviceRoutingStrategies(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
        boolean zRemovePreferredDeviceForStrategies;
        HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper = this.mRoutingHelper;
        hearingAidAudioRoutingHelper.getClass();
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i2 : iArr) {
            arrayList.add(new AudioAttributes.Builder().setUsage(i2).build());
        }
        List<AudioProductStrategy> audioProductStrategies = hearingAidAudioRoutingHelper.getAudioProductStrategies();
        ArrayList arrayList2 = new ArrayList();
        for (AudioProductStrategy audioProductStrategy : audioProductStrategies) {
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                if (audioProductStrategy.supportsAudioAttributes((AudioAttributes) obj)) {
                    arrayList2.add(audioProductStrategy);
                }
            }
        }
        List list = (List) arrayList2.stream().distinct().collect(Collectors.toList());
        if (i != 0) {
            boolean preferredDeviceForStrategy = true;
            if (i == 1) {
                boolean zRemovePreferredDeviceForStrategies2 = hearingAidAudioRoutingHelper.removePreferredDeviceForStrategies(list);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    preferredDeviceForStrategy &= hearingAidAudioRoutingHelper.mAudioManager.setPreferredDeviceForStrategy((AudioProductStrategy) it.next(), audioDeviceAttributes);
                }
                zRemovePreferredDeviceForStrategies = zRemovePreferredDeviceForStrategies2 & preferredDeviceForStrategy;
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unexpected routingValue: "));
                }
                boolean zRemovePreferredDeviceForStrategies3 = hearingAidAudioRoutingHelper.removePreferredDeviceForStrategies(list);
                AudioDeviceAttributes audioDeviceAttributes2 = HearingAidAudioRoutingConstants.BUILTIN_SPEAKER;
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    preferredDeviceForStrategy &= hearingAidAudioRoutingHelper.mAudioManager.setPreferredDeviceForStrategy((AudioProductStrategy) it2.next(), audioDeviceAttributes2);
                }
                zRemovePreferredDeviceForStrategies = zRemovePreferredDeviceForStrategies3 & preferredDeviceForStrategy;
            }
        } else {
            zRemovePreferredDeviceForStrategies = hearingAidAudioRoutingHelper.removePreferredDeviceForStrategies(list);
        }
        if (zRemovePreferredDeviceForStrategies) {
            return;
        }
        Log.w("HearingAidDeviceManager", "routingStrategies: " + list.toString() + "routingValue: " + i + " fail to configure AudioProductStrategy");
    }

    public final boolean setSubDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice2;
        long hiSyncId = cachedBluetoothDevice.getHiSyncId();
        if (isValidHiSyncId(hiSyncId)) {
            if (cachedBluetoothDevice.getProfiles().stream().anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0(2))) {
                Log.w("HearingAidDeviceManager", "Skip ASHA grouping since this device supports CSIP");
                return false;
            }
            int size = this.mCachedDevices.size() - 1;
            while (true) {
                if (size < 0) {
                    cachedBluetoothDevice2 = null;
                    break;
                }
                cachedBluetoothDevice2 = (CachedBluetoothDevice) this.mCachedDevices.get(size);
                if (cachedBluetoothDevice2.getHiSyncId() == hiSyncId) {
                    break;
                }
                size--;
            }
            if (cachedBluetoothDevice2 != null) {
                cachedBluetoothDevice2.setSubDevice(cachedBluetoothDevice);
                cachedBluetoothDevice.setName(cachedBluetoothDevice2.getName());
                return true;
            }
        }
        return false;
    }

    public final void switchDeviceContent(CachedBluetoothDevice cachedBluetoothDevice, CachedBluetoothDevice cachedBluetoothDevice2) {
        CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice.mSubDevice;
        if (cachedBluetoothDevice3 == null || !cachedBluetoothDevice3.equals(cachedBluetoothDevice2)) {
            return;
        }
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        localBluetoothManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice);
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        short s = cachedBluetoothDevice.mRssi;
        boolean z = cachedBluetoothDevice.mJustDiscovered;
        HearingAidInfo hearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
        String str = cachedBluetoothDevice.mName;
        CachedBluetoothDevice cachedBluetoothDevice4 = cachedBluetoothDevice.mSubDevice;
        cachedBluetoothDevice.mDevice = cachedBluetoothDevice4.mDevice;
        cachedBluetoothDevice.mRssi = cachedBluetoothDevice4.mRssi;
        cachedBluetoothDevice.mJustDiscovered = cachedBluetoothDevice4.mJustDiscovered;
        cachedBluetoothDevice.mName = cachedBluetoothDevice4.mName;
        cachedBluetoothDevice.mHearingAidInfo = cachedBluetoothDevice4.mHearingAidInfo;
        cachedBluetoothDevice4.mDevice = bluetoothDevice;
        cachedBluetoothDevice4.mRssi = s;
        cachedBluetoothDevice4.mJustDiscovered = z;
        cachedBluetoothDevice4.mName = str;
        cachedBluetoothDevice4.mHearingAidInfo = hearingAidInfo;
        cachedBluetoothDevice.fetchActiveDevices();
        cachedBluetoothDevice.mCachedMaxConnectionState = cachedBluetoothDevice.mSubDevice.mCachedMaxConnectionState;
        localBluetoothManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDevice);
    }

    public final void syncDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDeviceFindMainDevice;
        int activePresetIndex;
        HapClientProfile hapClientProfile = this.mBtManager.mProfileManager.mHapClientProfile;
        if (hapClientProfile != null) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
            int activePresetIndex2 = 0;
            if ((bluetoothHapClient == null ? false : bluetoothHapClient.supportsSynchronizedPresets(bluetoothDevice)) || (cachedBluetoothDeviceFindMainDevice = findMainDevice(cachedBluetoothDevice)) == null) {
                return;
            }
            BluetoothDevice bluetoothDevice2 = cachedBluetoothDeviceFindMainDevice.mDevice;
            BluetoothHapClient bluetoothHapClient2 = hapClientProfile.mService;
            if (bluetoothHapClient2 == null) {
                Log.w("HapClientProfile", "Proxy not attached to service. Cannot get active preset index.");
                activePresetIndex = 0;
            } else {
                activePresetIndex = bluetoothHapClient2.getActivePresetIndex(bluetoothDevice2);
            }
            BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
            BluetoothHapClient bluetoothHapClient3 = hapClientProfile.mService;
            if (bluetoothHapClient3 == null) {
                Log.w("HapClientProfile", "Proxy not attached to service. Cannot get active preset index.");
            } else {
                activePresetIndex2 = bluetoothHapClient3.getActivePresetIndex(bluetoothDevice3);
            }
            if (activePresetIndex == 0 || activePresetIndex == activePresetIndex2) {
                return;
            }
            if (DEBUG) {
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(activePresetIndex2, activePresetIndex, "syncing preset from ", "->", ", device=");
                sbM.append(cachedBluetoothDevice);
                Log.d("HearingAidDeviceManager", sbM.toString());
            }
            hapClientProfile.selectPreset(cachedBluetoothDevice.mDevice, activePresetIndex);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDevicesConnectionStatus() {
        boolean zIsConnectedProfile;
        int profileConnectionState;
        int profileConnectionState2;
        this.mInitialDevicesConnectionStatusUpdate = true;
        Set<CachedBluetoothDevice> set = (Set) this.mCachedDevices.stream().filter(new HearingAidDeviceManager$$ExternalSyntheticLambda0(1)).flatMap(new Function() { // from class: com.android.settingslib.bluetooth.HearingAidDeviceManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                HearingAidDeviceManager hearingAidDeviceManager = this.f$0;
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                boolean z = HearingAidDeviceManager.DEBUG;
                hearingAidDeviceManager.getClass();
                ArraySet arraySet = new ArraySet();
                arraySet.add(cachedBluetoothDevice);
                Set set2 = cachedBluetoothDevice.mMemberDevices;
                if (set2.isEmpty()) {
                    CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                    if (cachedBluetoothDevice2 != null) {
                        arraySet.add(cachedBluetoothDevice2);
                    }
                } else {
                    arraySet.addAll(set2);
                }
                return arraySet.stream();
            }
        }).collect(Collectors.toSet());
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mBtManager.mProfileManager;
        HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
        HapClientProfile hapClientProfile = localBluetoothProfileManager.mHapClientProfile;
        for (CachedBluetoothDevice cachedBluetoothDevice : set) {
            if ((hearingAidProfile != null && ((profileConnectionState2 = cachedBluetoothDevice.getProfileConnectionState(hearingAidProfile)) == 3 || profileConnectionState2 == 1)) || (hapClientProfile != null && ((profileConnectionState = cachedBluetoothDevice.getProfileConnectionState(hapClientProfile)) == 3 || profileConnectionState == 1))) {
                this.mDevicesConnectionStatus = 2;
                break;
            }
        }
        Iterator it = set.iterator();
        while (true) {
            boolean zIsConnectedProfile2 = false;
            if (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice2.isActiveDevice(21)) {
                    Iterator it2 = cachedBluetoothDevice2.getProfiles().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            zIsConnectedProfile = false;
                            break;
                        }
                        LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it2.next();
                        if (localBluetoothProfile.getProfileId() == 21) {
                            zIsConnectedProfile = cachedBluetoothDevice2.isConnectedProfile(localBluetoothProfile);
                            break;
                        }
                    }
                    if (zIsConnectedProfile) {
                        break;
                    }
                    if (cachedBluetoothDevice2.isActiveDevice(22)) {
                        Iterator it3 = cachedBluetoothDevice2.getProfiles().iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) it3.next();
                            if (localBluetoothProfile2.getProfileId() == 22) {
                                zIsConnectedProfile2 = cachedBluetoothDevice2.isConnectedProfile(localBluetoothProfile2);
                                break;
                            }
                        }
                        if (zIsConnectedProfile2) {
                            break;
                        }
                    }
                }
            } else if (set.stream().anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0(5))) {
                this.mDevicesConnectionStatus = 1;
            } else if (set.stream().anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0(4))) {
                this.mDevicesConnectionStatus = 0;
            } else {
                this.mDevicesConnectionStatus = -1;
            }
        }
        this.mDevicesConnectionStatus = 3;
        if (DEBUG) {
            RecyclerView$$ExternalSyntheticOutline0.m(this.mDevicesConnectionStatus, "HearingAidDeviceManager", new StringBuilder("updateDevicesConnectionStatus: "));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateHearingAidsDevices() {
        int deviceSide;
        Iterator it;
        int deviceMode;
        HearingAidInfo hearingAidInfoBuild;
        HashSet hashSet = new HashSet();
        Iterator it2 = this.mCachedDevices.iterator();
        while (it2.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it2.next();
            if (!isValidHiSyncId(cachedBluetoothDevice.getHiSyncId())) {
                LocalBluetoothProfileManager localBluetoothProfileManager = this.mBtManager.mProfileManager;
                HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
                boolean z = DEBUG;
                if (hearingAidProfile == null) {
                    Log.w("HearingAidDeviceManager", "HearingAidProfile is not supported on this device");
                } else {
                    BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                    BluetoothHearingAid bluetoothHearingAid = hearingAidProfile.mService;
                    long hiSyncId = (bluetoothHearingAid == null || bluetoothDevice == null) ? 0L : bluetoothHearingAid.getHiSyncId(bluetoothDevice);
                    if (isValidHiSyncId(hiSyncId)) {
                        HearingAidInfo.Builder builder = new HearingAidInfo.Builder();
                        BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
                        BluetoothHearingAid bluetoothHearingAid2 = hearingAidProfile.mService;
                        if (bluetoothHearingAid2 == null) {
                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            deviceSide = -1;
                        } else {
                            deviceSide = bluetoothHearingAid2.getDeviceSide(bluetoothDevice2);
                        }
                        it = it2;
                        builder.mSide = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(deviceSide, -1);
                        BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
                        BluetoothHearingAid bluetoothHearingAid3 = hearingAidProfile.mService;
                        if (bluetoothHearingAid3 == null) {
                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            deviceMode = -1;
                        } else {
                            deviceMode = bluetoothHearingAid3.getDeviceMode(bluetoothDevice3);
                        }
                        builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(deviceMode, -1);
                        builder.mHiSyncId = hiSyncId;
                        hearingAidInfoBuild = builder.build();
                        if (z) {
                            Log.d("HearingAidDeviceManager", "generateHearingAidInfo, " + cachedBluetoothDevice + ", info=" + hearingAidInfoBuild);
                        }
                    }
                    if (hearingAidInfoBuild != null) {
                        cachedBluetoothDevice.setHearingAidInfo(hearingAidInfoBuild);
                        long j = hearingAidInfoBuild.mHiSyncId;
                        if (isValidHiSyncId(j)) {
                            hashSet.add(Long.valueOf(j));
                        }
                    }
                    it2 = it;
                }
                it = it2;
                HapClientProfile hapClientProfile = localBluetoothProfileManager.mHapClientProfile;
                LeAudioProfile leAudioProfile = localBluetoothProfileManager.mLeAudioProfile;
                if (hapClientProfile == null || leAudioProfile == null) {
                    Log.w("HearingAidDeviceManager", "HapClientProfile or LeAudioProfile is not supported on this device");
                } else if (cachedBluetoothDevice.getProfiles().stream().anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0(3))) {
                    BluetoothDevice bluetoothDevice4 = cachedBluetoothDevice.mDevice;
                    BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
                    int audioLocation = (bluetoothLeAudio == null || bluetoothDevice4 == null) ? 0 : bluetoothLeAudio.getAudioLocation(bluetoothDevice4);
                    BluetoothDevice bluetoothDevice5 = cachedBluetoothDevice.mDevice;
                    BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                    int hearingAidType = bluetoothHapClient == null ? -1 : bluetoothHapClient.getHearingAidType(bluetoothDevice5);
                    if (hearingAidType != -1) {
                        HearingAidInfo.Builder builder2 = new HearingAidInfo.Builder();
                        builder2.setLeAudioLocation(audioLocation);
                        builder2.mMode = HearingAidInfo.HAP_DEVICE_TYPE_TO_INTERNAL_MODE_MAPPING.get(hearingAidType, -1);
                        hearingAidInfoBuild = builder2.build();
                        if (z) {
                            Log.d("HearingAidDeviceManager", "generateHearingAidInfo, " + cachedBluetoothDevice + ", info=" + hearingAidInfoBuild);
                        }
                    }
                    if (hearingAidInfoBuild != null) {
                    }
                    it2 = it;
                }
                hearingAidInfoBuild = null;
                if (hearingAidInfoBuild != null) {
                }
                it2 = it;
            }
        }
        Iterator it3 = hashSet.iterator();
        while (it3.hasNext()) {
            onHiSyncIdChanged(((Long) it3.next()).longValue());
        }
    }

    public HearingAidDeviceManager(Context context, LocalBluetoothManager localBluetoothManager, List<CachedBluetoothDevice> list, HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper) {
        this.mContentResolver = context.getContentResolver();
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
        this.mCachedDevices = list;
        this.mRoutingHelper = hearingAidAudioRoutingHelper;
        this.mFilteredCachedDevices = localBluetoothManager.mCachedDeviceManager.mFilteredCachedDevices;
    }
}
