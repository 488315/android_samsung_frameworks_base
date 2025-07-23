package com.android.systemui.volume.util;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BluetoothAdapterWrapper {
    public BluetoothA2dp a2dp;
    public final AudioManagerWrapper audioManager;
    public final BluetoothAdapterWrapper$bluetoothProfileServiceListener$1 bluetoothProfileServiceListener;
    public final Context context;
    public BluetoothHeadset hfp;
    public BluetoothLeAudio leAudio;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.bluetooth.BluetoothProfile$ServiceListener, com.android.systemui.volume.util.BluetoothAdapterWrapper$bluetoothProfileServiceListener$1] */
    public BluetoothAdapterWrapper(Context context) {
        this.context = context;
        ?? r0 = new BluetoothProfile.ServiceListener() { // from class: com.android.systemui.volume.util.BluetoothAdapterWrapper$bluetoothProfileServiceListener$1
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                if (i == 1) {
                    BluetoothAdapterWrapper.this.hfp = (BluetoothHeadset) bluetoothProfile;
                    return;
                }
                if (i == 2) {
                    BluetoothAdapterWrapper.this.a2dp = (BluetoothA2dp) bluetoothProfile;
                } else if (i == 21) {
                    BluetoothAdapterWrapper.this.getClass();
                } else {
                    if (i != 22) {
                        return;
                    }
                    BluetoothAdapterWrapper.this.leAudio = (BluetoothLeAudio) bluetoothProfile;
                }
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceDisconnected(int i) {
                if (i == 1) {
                    BluetoothAdapterWrapper.this.hfp = null;
                    return;
                }
                if (i == 2) {
                    BluetoothAdapterWrapper.this.a2dp = null;
                } else if (i == 21) {
                    BluetoothAdapterWrapper.this.getClass();
                } else {
                    if (i != 22) {
                        return;
                    }
                    BluetoothAdapterWrapper.this.leAudio = null;
                }
            }
        };
        this.bluetoothProfileServiceListener = r0;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != 0) {
            defaultAdapter.getProfileProxy(context, r0, 2);
            defaultAdapter.getProfileProxy(context, r0, 1);
            defaultAdapter.getProfileProxy(context, r0, 22);
            defaultAdapter.getProfileProxy(context, r0, 21);
        }
        this.audioManager = new AudioManagerWrapper(context);
    }

    public final CachedBluetoothDevice cachedBluetoothDevice(BluetoothDevice bluetoothDevice) {
        Context context = this.context;
        BluetoothUtils.AnonymousClass2 anonymousClass2 = BluetoothUtils.mOnInitCallback;
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = LocalBluetoothManager.getInstance(context, anonymousClass2).mCachedDeviceManager;
        LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothManager.getInstance(this.context, anonymousClass2).mLocalAdapter;
        return cachedBluetoothDeviceManager.findDevice(localBluetoothAdapter.mAdapter.getRemoteDevice(bluetoothDevice.getAddress()));
    }

    public final String getActiveBTDeviceName() {
        String str;
        int semGetCurrentDeviceType = this.audioManager.am.semGetCurrentDeviceType();
        if (semGetCurrentDeviceType == 23) {
            BluetoothCommonUtil bluetoothCommonUtil = BluetoothCommonUtil.INSTANCE;
            List hearingAidDevices = getHearingAidDevices();
            bluetoothCommonUtil.getClass();
            str = (String) CollectionsKt___CollectionsKt.firstOrNull(BluetoothCommonUtil.mapNames(hearingAidDevices));
        } else if (semGetCurrentDeviceType == 26 || semGetCurrentDeviceType == 27) {
            BluetoothCommonUtil bluetoothCommonUtil2 = BluetoothCommonUtil.INSTANCE;
            List connectedLeDevices = getConnectedLeDevices();
            bluetoothCommonUtil2.getClass();
            str = (String) CollectionsKt___CollectionsKt.firstOrNull(BluetoothCommonUtil.mapNames(connectedLeDevices));
        } else {
            BluetoothA2dp bluetoothA2dp = this.a2dp;
            if (bluetoothA2dp != null) {
                BluetoothA2dpUtil.INSTANCE.getClass();
                BluetoothDevice activeDevice = bluetoothA2dp.getActiveDevice();
                if (activeDevice == null || (str = activeDevice.semGetAliasName()) == null) {
                    str = "";
                }
            } else {
                str = null;
            }
        }
        return str == null ? "" : str;
    }

    public final String getBtCallDeviceName() {
        String semGetAliasName;
        Object obj;
        String semGetAliasName2;
        BluetoothHeadset bluetoothHeadset = this.hfp;
        if (bluetoothHeadset == null) {
            BluetoothLeAudio bluetoothLeAudio = this.leAudio;
            if (bluetoothLeAudio == null) {
                return "";
            }
            BluetoothLeAudioUtil.INSTANCE.getClass();
            BluetoothCommonUtil.INSTANCE.getClass();
            List<BluetoothDevice> connectedDevices = bluetoothLeAudio.getConnectedDevices();
            if (connectedDevices == null) {
                connectedDevices = EmptyList.INSTANCE;
            }
            BluetoothDevice bluetoothDevice = (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull((List) connectedDevices);
            return (bluetoothDevice == null || (semGetAliasName = bluetoothDevice.semGetAliasName()) == null) ? "" : semGetAliasName;
        }
        BluetoothHeadsetUtil.INSTANCE.getClass();
        BluetoothCommonUtil.INSTANCE.getClass();
        List<BluetoothDevice> connectedDevices2 = bluetoothHeadset.getConnectedDevices();
        if (connectedDevices2 == null) {
            connectedDevices2 = EmptyList.INSTANCE;
        }
        Iterator<T> it = connectedDevices2.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (bluetoothHeadset.isAudioConnected((BluetoothDevice) obj)) {
                break;
            }
        }
        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) obj;
        return (bluetoothDevice2 == null || (semGetAliasName2 = bluetoothDevice2.semGetAliasName()) == null) ? "" : semGetAliasName2;
    }

    public final List getConnectedDevices(boolean z) {
        if (z) {
            return getConnectedLeDevices();
        }
        int semGetCurrentDeviceType = this.audioManager.am.semGetCurrentDeviceType();
        if (semGetCurrentDeviceType == 23) {
            return getHearingAidDevices();
        }
        if (semGetCurrentDeviceType == 26 || semGetCurrentDeviceType == 27) {
            return getConnectedLeDevices();
        }
        BluetoothA2dp bluetoothA2dp = this.a2dp;
        if (bluetoothA2dp != null) {
            BluetoothA2dpUtil.INSTANCE.getClass();
            List orderConnectedDevices = BluetoothA2dpUtil.getOrderConnectedDevices(bluetoothA2dp);
            if (orderConnectedDevices != null) {
                return orderConnectedDevices;
            }
        }
        return EmptyList.INSTANCE;
    }

    public final List getConnectedLeDevices() {
        List activeDevices;
        try {
            int i = Result.$r8$clinit;
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && (activeDevices = defaultAdapter.getActiveDevices(22)) != null) {
                if (activeDevices.isEmpty()) {
                    activeDevices = null;
                }
                if (activeDevices != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : activeDevices) {
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                        bluetoothDevice.getClass();
                        CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDevice(bluetoothDevice);
                        if (cachedBluetoothDevice != null && cachedBluetoothDevice.isActiveDevice(22)) {
                            arrayList.add(obj);
                        }
                    }
                    return arrayList;
                }
            }
            return EmptyList.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(new Result.Failure(th));
            if (m3422exceptionOrNullimpl != null) {
                m3422exceptionOrNullimpl.printStackTrace();
            }
            return EmptyList.INSTANCE;
        }
    }

    public final List getConnectedLeHearingAidDevice() {
        Object failure;
        try {
            int i = Result.$r8$clinit;
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            failure = defaultAdapter != null ? defaultAdapter.getActiveDevices(22) : null;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            m3422exceptionOrNullimpl.printStackTrace();
        }
        List list = (List) (failure instanceof Result.Failure ? null : failure);
        if (list == null) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof BluetoothDevice) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj2 = arrayList.get(i3);
            i3++;
            CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDevice((BluetoothDevice) obj2);
            if (cachedBluetoothDevice != null && (cachedBluetoothDevice.isHearingDevice() || cachedBluetoothDevice.mIsHearingAidDeviceByUUID)) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }

    public final List getHearingAidDevices() {
        List activeDevices;
        try {
            int i = Result.$r8$clinit;
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && (activeDevices = defaultAdapter.getActiveDevices(21)) != null) {
                if (activeDevices.isEmpty()) {
                    activeDevices = null;
                }
                if (activeDevices != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : activeDevices) {
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                        bluetoothDevice.getClass();
                        CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDevice(bluetoothDevice);
                        if (cachedBluetoothDevice != null && cachedBluetoothDevice.isActiveDevice(21)) {
                            arrayList.add(obj);
                        }
                    }
                    return arrayList;
                }
            }
            return EmptyList.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(new Result.Failure(th));
            if (m3422exceptionOrNullimpl != null) {
                m3422exceptionOrNullimpl.printStackTrace();
            }
            return EmptyList.INSTANCE;
        }
    }
}
