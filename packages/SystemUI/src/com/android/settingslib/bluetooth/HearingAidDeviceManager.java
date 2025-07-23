package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                                HearingDeviceLocalDataManager.this.mListener.getClass();
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
            }
            if (isValidHiSyncId(cachedBluetoothDevice3.getHiSyncId()) && (cachedBluetoothDevice2 = cachedBluetoothDevice3.mSubDevice) != null && cachedBluetoothDevice2.equals(cachedBluetoothDevice)) {
                return cachedBluetoothDevice3;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initHearingAidDeviceIfNeeded(com.android.settingslib.bluetooth.CachedBluetoothDevice r10) {
        /*
            r9 = this;
            android.bluetooth.BluetoothDevice r0 = r10.mDevice
            com.android.settingslib.bluetooth.LocalBluetoothManager r9 = r9.mBtManager
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r1 = r9.mProfileManager
            r2 = 0
            if (r1 != 0) goto Lb
            goto L1b
        Lb:
            com.android.settingslib.bluetooth.HearingAidProfile r1 = r1.mHearingAidProfile
            if (r1 != 0) goto L10
            goto L1b
        L10:
            android.bluetooth.BluetoothHearingAid r1 = r1.mService
            if (r1 == 0) goto L1b
            if (r0 != 0) goto L17
            goto L1b
        L17:
            long r2 = r1.getHiSyncId(r0)
        L1b:
            boolean r0 = isValidHiSyncId(r2)
            if (r0 == 0) goto L80
            com.android.settingslib.bluetooth.HearingAidInfo$Builder r0 = new com.android.settingslib.bluetooth.HearingAidInfo$Builder
            r0.<init>()
            android.bluetooth.BluetoothDevice r1 = r10.mDevice
            java.lang.String r4 = "Proxy not attached to HearingAidService"
            java.lang.String r5 = "HearingAidProfile"
            r6 = -1
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r9 = r9.mProfileManager
            java.lang.String r7 = "HearingAidDeviceManager"
            if (r9 != 0) goto L35
        L33:
            r1 = r6
            goto L4b
        L35:
            com.android.settingslib.bluetooth.HearingAidProfile r8 = r9.mHearingAidProfile
            if (r8 != 0) goto L3f
            java.lang.String r1 = "HearingAidProfile is not supported and not ready to fetch device side"
            android.util.Log.w(r7, r1)
            goto L33
        L3f:
            android.bluetooth.BluetoothHearingAid r8 = r8.mService
            if (r8 != 0) goto L47
            android.util.Log.w(r5, r4)
            goto L33
        L47:
            int r1 = r8.getDeviceSide(r1)
        L4b:
            android.util.SparseIntArray r8 = com.android.settingslib.bluetooth.HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING
            int r1 = r8.get(r1, r6)
            r0.mSide = r1
            android.bluetooth.BluetoothDevice r1 = r10.mDevice
            if (r9 != 0) goto L59
        L57:
            r9 = r6
            goto L6f
        L59:
            com.android.settingslib.bluetooth.HearingAidProfile r9 = r9.mHearingAidProfile
            if (r9 != 0) goto L63
            java.lang.String r9 = "HearingAidProfile is not supported and not ready to fetch device mode"
            android.util.Log.w(r7, r9)
            goto L57
        L63:
            android.bluetooth.BluetoothHearingAid r9 = r9.mService
            if (r9 != 0) goto L6b
            android.util.Log.w(r5, r4)
            goto L57
        L6b:
            int r9 = r9.getDeviceMode(r1)
        L6f:
            android.util.SparseIntArray r1 = com.android.settingslib.bluetooth.HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING
            int r9 = r1.get(r9, r6)
            r0.mMode = r9
            r0.mHiSyncId = r2
            com.android.settingslib.bluetooth.HearingAidInfo r9 = r0.build()
            r10.setHearingAidInfo(r9)
        L80:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.HearingAidDeviceManager.initHearingAidDeviceIfNeeded(com.android.settingslib.bluetooth.CachedBluetoothDevice):void");
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
                            MenuInfoRepository$$ExternalSyntheticLambda1 menuInfoRepository$$ExternalSyntheticLambda12 = MenuInfoRepository$$ExternalSyntheticLambda1.this;
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
        boolean isEnabled = FeatureFlagUtils.isEnabled(this.mContext, "settings_audio_routing");
        HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper = this.mRoutingHelper;
        if (isEnabled) {
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
            CachedBluetoothDevice findMainDevice = findMainDevice(cachedBluetoothDevice);
            if (findMainDevice == null) {
                return false;
            }
            if (!findMainDevice.isConnected()) {
                switchDeviceContent(findMainDevice, cachedBluetoothDevice);
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
        boolean removePreferredDeviceForStrategies;
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
            boolean z = true;
            if (i == 1) {
                boolean removePreferredDeviceForStrategies2 = hearingAidAudioRoutingHelper.removePreferredDeviceForStrategies(list);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    z &= hearingAidAudioRoutingHelper.mAudioManager.setPreferredDeviceForStrategy((AudioProductStrategy) it.next(), audioDeviceAttributes);
                }
                removePreferredDeviceForStrategies = removePreferredDeviceForStrategies2 & z;
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unexpected routingValue: "));
                }
                boolean removePreferredDeviceForStrategies3 = hearingAidAudioRoutingHelper.removePreferredDeviceForStrategies(list);
                AudioDeviceAttributes audioDeviceAttributes2 = HearingAidAudioRoutingConstants.BUILTIN_SPEAKER;
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    z &= hearingAidAudioRoutingHelper.mAudioManager.setPreferredDeviceForStrategy((AudioProductStrategy) it2.next(), audioDeviceAttributes2);
                }
                removePreferredDeviceForStrategies = removePreferredDeviceForStrategies3 & z;
            }
        } else {
            removePreferredDeviceForStrategies = hearingAidAudioRoutingHelper.removePreferredDeviceForStrategies(list);
        }
        if (removePreferredDeviceForStrategies) {
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
        CachedBluetoothDevice findMainDevice;
        int activePresetIndex;
        HapClientProfile hapClientProfile = this.mBtManager.mProfileManager.mHapClientProfile;
        if (hapClientProfile != null) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
            int i = 0;
            if ((bluetoothHapClient == null ? false : bluetoothHapClient.supportsSynchronizedPresets(bluetoothDevice)) || (findMainDevice = findMainDevice(cachedBluetoothDevice)) == null) {
                return;
            }
            BluetoothDevice bluetoothDevice2 = findMainDevice.mDevice;
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
                i = bluetoothHapClient3.getActivePresetIndex(bluetoothDevice3);
            }
            if (activePresetIndex == 0 || activePresetIndex == i) {
                return;
            }
            if (DEBUG) {
                StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, activePresetIndex, "syncing preset from ", "->", ", device=");
                m.append(cachedBluetoothDevice);
                Log.d("HearingAidDeviceManager", m.toString());
            }
            hapClientProfile.selectPreset(cachedBluetoothDevice.mDevice, activePresetIndex);
        }
    }

    public final void updateDevicesConnectionStatus() {
        boolean z;
        int profileConnectionState;
        int profileConnectionState2;
        this.mInitialDevicesConnectionStatusUpdate = true;
        Set<CachedBluetoothDevice> set = (Set) this.mCachedDevices.stream().filter(new HearingAidDeviceManager$$ExternalSyntheticLambda0(1)).flatMap(new Function() { // from class: com.android.settingslib.bluetooth.HearingAidDeviceManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                HearingAidDeviceManager hearingAidDeviceManager = HearingAidDeviceManager.this;
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                boolean z2 = HearingAidDeviceManager.DEBUG;
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
            boolean z2 = false;
            if (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice2.isActiveDevice(21)) {
                    Iterator it2 = cachedBluetoothDevice2.getProfiles().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = false;
                            break;
                        }
                        LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it2.next();
                        if (localBluetoothProfile.getProfileId() == 21) {
                            z = cachedBluetoothDevice2.isConnectedProfile(localBluetoothProfile);
                            break;
                        }
                    }
                    if (z) {
                        break;
                    }
                }
                if (cachedBluetoothDevice2.isActiveDevice(22)) {
                    Iterator it3 = cachedBluetoothDevice2.getProfiles().iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) it3.next();
                        if (localBluetoothProfile2.getProfileId() == 22) {
                            z2 = cachedBluetoothDevice2.isConnectedProfile(localBluetoothProfile2);
                            break;
                        }
                    }
                    if (z2) {
                        break;
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

    /* JADX WARN: Removed duplicated region for block: B:29:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateHearingAidsDevices() {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.HearingAidDeviceManager.updateHearingAidsDevices():void");
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
