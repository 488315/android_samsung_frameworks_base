package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHearingAid;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.audiopolicy.AudioProductStrategy;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import com.android.settingslib.bluetooth.HearingAidInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HearingAidDeviceManager {
    public static final boolean DEBUG = BluetoothUtils.DEBUG;
    public final LocalBluetoothManager mBtManager;
    public final List mCachedDevices;
    public final ContentResolver mContentResolver;
    public final List mFilteredCachedDevices;
    public final HearingAidAudioRoutingHelper mRoutingHelper;

    public HearingAidDeviceManager(Context context, LocalBluetoothManager localBluetoothManager, List<CachedBluetoothDevice> list, List<CachedBluetoothDevice> list2) {
        this.mFilteredCachedDevices = list2;
        this.mContentResolver = context.getContentResolver();
        this.mBtManager = localBluetoothManager;
        this.mCachedDevices = list;
        this.mRoutingHelper = new HearingAidAudioRoutingHelper(context);
    }

    public final CachedBluetoothDevice findMainDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice2;
        for (CachedBluetoothDevice cachedBluetoothDevice3 : this.mCachedDevices) {
            if ((cachedBluetoothDevice3.getHiSyncId() != 0) && (cachedBluetoothDevice2 = cachedBluetoothDevice3.mSubDevice) != null && cachedBluetoothDevice2.equals(cachedBluetoothDevice)) {
                return cachedBluetoothDevice3;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initHearingAidDeviceIfNeeded(CachedBluetoothDevice cachedBluetoothDevice) {
        int i;
        LocalBluetoothProfileManager localBluetoothProfileManager;
        int i2;
        HearingAidProfile hearingAidProfile;
        BluetoothHearingAid bluetoothHearingAid;
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        LocalBluetoothProfileManager localBluetoothProfileManager2 = localBluetoothManager.mProfileManager;
        long hiSyncId = (localBluetoothProfileManager2 == null || (hearingAidProfile = localBluetoothProfileManager2.mHearingAidProfile) == null || (bluetoothHearingAid = hearingAidProfile.mService) == null || bluetoothDevice == null) ? 0L : bluetoothHearingAid.getHiSyncId(bluetoothDevice);
        if (hiSyncId != 0) {
            HearingAidInfo.Builder builder = new HearingAidInfo.Builder();
            BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
            LocalBluetoothProfileManager localBluetoothProfileManager3 = localBluetoothManager.mProfileManager;
            if (localBluetoothProfileManager3 != null) {
                HearingAidProfile hearingAidProfile2 = localBluetoothProfileManager3.mHearingAidProfile;
                if (hearingAidProfile2 == null) {
                    Log.w("HearingAidDeviceManager", "HearingAidProfile is not supported and not ready to fetch device side");
                } else {
                    BluetoothHearingAid bluetoothHearingAid2 = hearingAidProfile2.mService;
                    if (bluetoothHearingAid2 != null) {
                        i = bluetoothHearingAid2.getDeviceSide(bluetoothDevice2);
                        builder.mSide = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(i, -1);
                        BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
                        localBluetoothProfileManager = localBluetoothManager.mProfileManager;
                        if (localBluetoothProfileManager != null) {
                            HearingAidProfile hearingAidProfile3 = localBluetoothProfileManager.mHearingAidProfile;
                            if (hearingAidProfile3 == null) {
                                Log.w("HearingAidDeviceManager", "HearingAidProfile is not supported and not ready to fetch device mode");
                            } else {
                                BluetoothHearingAid bluetoothHearingAid3 = hearingAidProfile3.mService;
                                if (bluetoothHearingAid3 != null) {
                                    i2 = bluetoothHearingAid3.getDeviceMode(bluetoothDevice3);
                                    builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(i2, -1);
                                    builder.mHiSyncId = hiSyncId;
                                    cachedBluetoothDevice.mHearingAidInfo = builder.build();
                                }
                                Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            }
                        }
                        i2 = -1;
                        builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(i2, -1);
                        builder.mHiSyncId = hiSyncId;
                        cachedBluetoothDevice.mHearingAidInfo = builder.build();
                    }
                    Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                }
            }
            i = -1;
            builder.mSide = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(i, -1);
            BluetoothDevice bluetoothDevice32 = cachedBluetoothDevice.mDevice;
            localBluetoothProfileManager = localBluetoothManager.mProfileManager;
            if (localBluetoothProfileManager != null) {
            }
            i2 = -1;
            builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(i2, -1);
            builder.mHiSyncId = hiSyncId;
            cachedBluetoothDevice.mHearingAidInfo = builder.build();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0091 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0097 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        AudioDeviceAttributes audioDeviceAttributes = null;
        if (!cachedBluetoothDevice.isActiveDevice(21) && !cachedBluetoothDevice.isActiveDevice(22)) {
            setPreferredDeviceRoutingStrategies(0, null, HearingAidAudioRoutingConstants.CALL_ROUTING_ATTRIBUTES);
            setPreferredDeviceRoutingStrategies(0, null, HearingAidAudioRoutingConstants.MEDIA_ROUTING_ATTRIBUTES);
            setPreferredDeviceRoutingStrategies(0, null, HearingAidAudioRoutingConstants.RINGTONE_ROUTING_ATTRIBUTE);
            setPreferredDeviceRoutingStrategies(0, null, HearingAidAudioRoutingConstants.SYSTEM_SOUNDS_ROUTING_ATTRIBUTES);
            return;
        }
        HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper = this.mRoutingHelper;
        hearingAidAudioRoutingHelper.getClass();
        if (cachedBluetoothDevice.mHearingAidInfo != null) {
            AudioDeviceInfo[] devices = hearingAidAudioRoutingHelper.mAudioManager.getDevices(2);
            int length = devices.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                AudioDeviceInfo audioDeviceInfo = devices[i];
                if (audioDeviceInfo.getType() == 23 || audioDeviceInfo.getType() == 26) {
                    final String address = audioDeviceInfo.getAddress();
                    CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                    Set set = cachedBluetoothDevice.mMemberDevices;
                    if (!cachedBluetoothDevice.getAddress().equals(address) && (cachedBluetoothDevice2 == null || !cachedBluetoothDevice2.getAddress().equals(address))) {
                        HashSet hashSet = (HashSet) set;
                        if (hashSet.isEmpty() || !hashSet.stream().anyMatch(new Predicate() { // from class: com.android.settingslib.bluetooth.HearingAidAudioRoutingHelper$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((CachedBluetoothDevice) obj).getAddress().equals(address);
                            }
                        })) {
                            z = false;
                            if (!z) {
                                audioDeviceAttributes = new AudioDeviceAttributes(audioDeviceInfo);
                                break;
                            }
                        }
                    }
                    z = true;
                    if (!z) {
                    }
                }
                i++;
            }
        }
        if (audioDeviceAttributes == null) {
            Log.w("HearingAidDeviceManager", "Can not find expected AudioDeviceAttributes for hearing device: " + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
            return;
        }
        ContentResolver contentResolver = this.mContentResolver;
        int i2 = Settings.Secure.getInt(contentResolver, "hearing_aid_call_routing", 0);
        int i3 = Settings.Secure.getInt(contentResolver, "hearing_aid_media_routing", 0);
        int i4 = Settings.Secure.getInt(contentResolver, "hearing_aid_ringtone_routing", 0);
        int i5 = Settings.Secure.getInt(contentResolver, "hearing_aid_system_sounds_routing", 0);
        setPreferredDeviceRoutingStrategies(i2, audioDeviceAttributes, HearingAidAudioRoutingConstants.CALL_ROUTING_ATTRIBUTES);
        setPreferredDeviceRoutingStrategies(i3, audioDeviceAttributes, HearingAidAudioRoutingConstants.MEDIA_ROUTING_ATTRIBUTES);
        setPreferredDeviceRoutingStrategies(i4, audioDeviceAttributes, HearingAidAudioRoutingConstants.RINGTONE_ROUTING_ATTRIBUTE);
        setPreferredDeviceRoutingStrategies(i5, audioDeviceAttributes, HearingAidAudioRoutingConstants.SYSTEM_SOUNDS_ROUTING_ATTRIBUTES);
    }

    public void onHiSyncIdChanged(long j) {
        CachedBluetoothDevice cachedBluetoothDevice;
        List list = this.mCachedDevices;
        int size = list.size() - 1;
        int i = -1;
        while (size >= 0) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) list.get(size);
            if (cachedBluetoothDevice2.getHiSyncId() == j && !cachedBluetoothDevice2.getProfiles().stream().anyMatch(new HearingAidDeviceManager$$ExternalSyntheticLambda0())) {
                if (i != -1) {
                    if (cachedBluetoothDevice2.isConnected()) {
                        cachedBluetoothDevice = (CachedBluetoothDevice) list.get(i);
                        size = i;
                    } else {
                        cachedBluetoothDevice2 = (CachedBluetoothDevice) list.get(i);
                        cachedBluetoothDevice = cachedBluetoothDevice2;
                    }
                    cachedBluetoothDevice2.mSubDevice = cachedBluetoothDevice;
                    List list2 = this.mFilteredCachedDevices;
                    if (list2 != null) {
                        list2.remove(list.get(size));
                    }
                    list.remove(size);
                    String str = "onHiSyncIdChanged: removed from UI device =" + cachedBluetoothDevice + ", with hiSyncId=" + j;
                    if (DEBUG) {
                        Log.d("HearingAidDeviceManager", str);
                    }
                    this.mBtManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice);
                    return;
                }
                i = size;
            }
            size--;
        }
    }

    public final boolean onProfileConnectionStateChangedIfProcessed(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
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
                localBluetoothManager.mEventManager.dispatchDeviceRemoved(findMainDevice);
                findMainDevice.switchSubDeviceContent();
                localBluetoothManager.mEventManager.dispatchDeviceAdded(findMainDevice);
            }
            return true;
        }
        CachedBluetoothDevice findMainDevice2 = findMainDevice(cachedBluetoothDevice);
        if (cachedBluetoothDevice.mUnpairing || findMainDevice2 != null) {
            return true;
        }
        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
        if (cachedBluetoothDevice2 == null || !cachedBluetoothDevice2.isConnected()) {
            return false;
        }
        localBluetoothManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice);
        cachedBluetoothDevice.switchSubDeviceContent();
        localBluetoothManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDevice);
        return true;
    }

    public final void setPreferredDeviceRoutingStrategies(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
        boolean removePreferredDeviceForStrategies;
        boolean removePreferredDeviceForStrategies2;
        HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper = this.mRoutingHelper;
        hearingAidAudioRoutingHelper.getClass();
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i2 : iArr) {
            arrayList.add(new AudioAttributes.Builder().setUsage(i2).build());
        }
        List<AudioProductStrategy> audioProductStrategies = hearingAidAudioRoutingHelper.getAudioProductStrategies();
        ArrayList arrayList2 = new ArrayList();
        for (AudioProductStrategy audioProductStrategy : audioProductStrategies) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (audioProductStrategy.supportsAudioAttributes((AudioAttributes) it.next())) {
                    arrayList2.add(audioProductStrategy);
                }
            }
        }
        List list = (List) arrayList2.stream().distinct().collect(Collectors.toList());
        if (i != 0) {
            boolean z = true;
            AudioManager audioManager = hearingAidAudioRoutingHelper.mAudioManager;
            if (i == 1) {
                removePreferredDeviceForStrategies2 = hearingAidAudioRoutingHelper.removePreferredDeviceForStrategies(list);
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    z &= audioManager.setPreferredDeviceForStrategy((AudioProductStrategy) it2.next(), audioDeviceAttributes);
                }
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unexpected routingValue: ", i));
                }
                removePreferredDeviceForStrategies2 = hearingAidAudioRoutingHelper.removePreferredDeviceForStrategies(list);
                AudioDeviceAttributes audioDeviceAttributes2 = HearingAidAudioRoutingConstants.DEVICE_SPEAKER_OUT;
                Iterator it3 = list.iterator();
                while (it3.hasNext()) {
                    z &= audioManager.setPreferredDeviceForStrategy((AudioProductStrategy) it3.next(), audioDeviceAttributes2);
                }
            }
            removePreferredDeviceForStrategies = removePreferredDeviceForStrategies2 & z;
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
        if (hiSyncId != 0) {
            List list = this.mCachedDevices;
            int size = list.size();
            while (true) {
                size--;
                if (size < 0) {
                    cachedBluetoothDevice2 = null;
                    break;
                }
                cachedBluetoothDevice2 = (CachedBluetoothDevice) list.get(size);
                if (cachedBluetoothDevice2.getHiSyncId() == hiSyncId) {
                    break;
                }
            }
            if (cachedBluetoothDevice2 != null) {
                cachedBluetoothDevice2.mSubDevice = cachedBluetoothDevice;
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateHearingAidsDevices() {
        int i;
        LocalBluetoothProfileManager localBluetoothProfileManager;
        int i2;
        HearingAidProfile hearingAidProfile;
        BluetoothHearingAid bluetoothHearingAid;
        HashSet hashSet = new HashSet();
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            if (!(cachedBluetoothDevice.getHiSyncId() != 0)) {
                BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                LocalBluetoothManager localBluetoothManager = this.mBtManager;
                LocalBluetoothProfileManager localBluetoothProfileManager2 = localBluetoothManager.mProfileManager;
                long hiSyncId = (localBluetoothProfileManager2 == null || (hearingAidProfile = localBluetoothProfileManager2.mHearingAidProfile) == null || (bluetoothHearingAid = hearingAidProfile.mService) == null || bluetoothDevice == null) ? 0L : bluetoothHearingAid.getHiSyncId(bluetoothDevice);
                if (hiSyncId != 0) {
                    HearingAidInfo.Builder builder = new HearingAidInfo.Builder();
                    BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
                    LocalBluetoothProfileManager localBluetoothProfileManager3 = localBluetoothManager.mProfileManager;
                    if (localBluetoothProfileManager3 != null) {
                        HearingAidProfile hearingAidProfile2 = localBluetoothProfileManager3.mHearingAidProfile;
                        if (hearingAidProfile2 == null) {
                            Log.w("HearingAidDeviceManager", "HearingAidProfile is not supported and not ready to fetch device side");
                        } else {
                            BluetoothHearingAid bluetoothHearingAid2 = hearingAidProfile2.mService;
                            if (bluetoothHearingAid2 == null) {
                                Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                            } else {
                                i = bluetoothHearingAid2.getDeviceSide(bluetoothDevice2);
                                builder.mSide = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(i, -1);
                                BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
                                localBluetoothProfileManager = localBluetoothManager.mProfileManager;
                                if (localBluetoothProfileManager != null) {
                                    HearingAidProfile hearingAidProfile3 = localBluetoothProfileManager.mHearingAidProfile;
                                    if (hearingAidProfile3 == null) {
                                        Log.w("HearingAidDeviceManager", "HearingAidProfile is not supported and not ready to fetch device mode");
                                    } else {
                                        BluetoothHearingAid bluetoothHearingAid3 = hearingAidProfile3.mService;
                                        if (bluetoothHearingAid3 == null) {
                                            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
                                        } else {
                                            i2 = bluetoothHearingAid3.getDeviceMode(bluetoothDevice3);
                                            builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(i2, -1);
                                            builder.mHiSyncId = hiSyncId;
                                            cachedBluetoothDevice.mHearingAidInfo = builder.build();
                                            hashSet.add(Long.valueOf(hiSyncId));
                                        }
                                    }
                                }
                                i2 = -1;
                                builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(i2, -1);
                                builder.mHiSyncId = hiSyncId;
                                cachedBluetoothDevice.mHearingAidInfo = builder.build();
                                hashSet.add(Long.valueOf(hiSyncId));
                            }
                        }
                    }
                    i = -1;
                    builder.mSide = HearingAidInfo.ASHA_DEVICE_SIDE_TO_INTERNAL_SIDE_MAPPING.get(i, -1);
                    BluetoothDevice bluetoothDevice32 = cachedBluetoothDevice.mDevice;
                    localBluetoothProfileManager = localBluetoothManager.mProfileManager;
                    if (localBluetoothProfileManager != null) {
                    }
                    i2 = -1;
                    builder.mMode = HearingAidInfo.ASHA_DEVICE_MODE_TO_INTERNAL_MODE_MAPPING.get(i2, -1);
                    builder.mHiSyncId = hiSyncId;
                    cachedBluetoothDevice.mHearingAidInfo = builder.build();
                    hashSet.add(Long.valueOf(hiSyncId));
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            onHiSyncIdChanged(((Long) it.next()).longValue());
        }
    }

    public HearingAidDeviceManager(Context context, LocalBluetoothManager localBluetoothManager, List<CachedBluetoothDevice> list, HearingAidAudioRoutingHelper hearingAidAudioRoutingHelper) {
        this.mContentResolver = context.getContentResolver();
        this.mBtManager = localBluetoothManager;
        this.mCachedDevices = list;
        this.mRoutingHelper = hearingAidAudioRoutingHelper;
        this.mFilteredCachedDevices = localBluetoothManager.mCachedDeviceManager.mFilteredCachedDevices;
    }
}
