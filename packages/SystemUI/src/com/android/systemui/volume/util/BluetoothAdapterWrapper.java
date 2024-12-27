package com.android.systemui.volume.util;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHearingAid;
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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class BluetoothAdapterWrapper {
    public BluetoothA2dp a2dp;
    public final AudioManagerWrapper audioManager;
    public final BluetoothAdapterWrapper$bluetoothProfileServiceListener$1 bluetoothProfileServiceListener;
    public final Context context;
    public BluetoothHearingAid hearingAid;
    public BluetoothHeadset hfp;
    public BluetoothLeAudio leAudio;

    public BluetoothAdapterWrapper(Context context) {
        this.context = context;
        BluetoothProfile.ServiceListener serviceListener = new BluetoothProfile.ServiceListener() { // from class: com.android.systemui.volume.util.BluetoothAdapterWrapper$bluetoothProfileServiceListener$1
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                if (i == 1) {
                    BluetoothAdapterWrapper.this.hfp = (BluetoothHeadset) bluetoothProfile;
                    return;
                }
                if (i == 2) {
                    BluetoothAdapterWrapper.this.a2dp = (BluetoothA2dp) bluetoothProfile;
                } else if (i == 21) {
                    BluetoothAdapterWrapper.this.hearingAid = (BluetoothHearingAid) bluetoothProfile;
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
                    BluetoothAdapterWrapper.this.hearingAid = null;
                } else {
                    if (i != 22) {
                        return;
                    }
                    BluetoothAdapterWrapper.this.leAudio = null;
                }
            }
        };
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.getProfileProxy(context, serviceListener, 2);
            defaultAdapter.getProfileProxy(context, serviceListener, 1);
            defaultAdapter.getProfileProxy(context, serviceListener, 22);
            defaultAdapter.getProfileProxy(context, serviceListener, 21);
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
                str = activeDevice != null ? activeDevice.semGetAliasName() : null;
                if (str == null) {
                    str = "";
                }
            }
        }
        return str == null ? "" : str;
    }

    public final String getBtCallDeviceName() {
        String semGetAliasName;
        Object obj;
        BluetoothHeadset bluetoothHeadset = this.hfp;
        if (bluetoothHeadset != null) {
            BluetoothHeadsetUtil.INSTANCE.getClass();
            BluetoothCommonUtil.INSTANCE.getClass();
            List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
            if (connectedDevices == null) {
                connectedDevices = EmptyList.INSTANCE;
            }
            Iterator<T> it = connectedDevices.iterator();
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
            BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
            semGetAliasName = bluetoothDevice != null ? bluetoothDevice.semGetAliasName() : null;
            if (semGetAliasName == null) {
                return "";
            }
        } else {
            BluetoothLeAudio bluetoothLeAudio = this.leAudio;
            if (bluetoothLeAudio == null) {
                return "";
            }
            BluetoothLeAudioUtil.INSTANCE.getClass();
            BluetoothCommonUtil.INSTANCE.getClass();
            List<BluetoothDevice> connectedDevices2 = bluetoothLeAudio.getConnectedDevices();
            if (connectedDevices2 == null) {
                connectedDevices2 = EmptyList.INSTANCE;
            }
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull((List) connectedDevices2);
            semGetAliasName = bluetoothDevice2 != null ? bluetoothDevice2.semGetAliasName() : null;
            if (semGetAliasName == null) {
                return "";
            }
        }
        return semGetAliasName;
    }

    public final List getConnectedDevices() {
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
        try {
            int i = Result.$r8$clinit;
            BluetoothLeAudio bluetoothLeAudio = this.leAudio;
            if (bluetoothLeAudio != null) {
                BluetoothCommonUtil.INSTANCE.getClass();
                List<BluetoothDevice> connectedDevices = bluetoothLeAudio.getConnectedDevices();
                if (connectedDevices == null) {
                    connectedDevices = EmptyList.INSTANCE;
                }
                if (connectedDevices != null) {
                    if (!(!connectedDevices.isEmpty())) {
                        connectedDevices = null;
                    }
                    if (connectedDevices != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : connectedDevices) {
                            BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                            Intrinsics.checkNotNull(bluetoothDevice);
                            if (cachedBluetoothDevice(bluetoothDevice).isActiveDevice(22)) {
                                arrayList.add(obj);
                            }
                        }
                        return arrayList;
                    }
                }
            }
            return EmptyList.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(new Result.Failure(th));
            if (m2527exceptionOrNullimpl != null) {
                m2527exceptionOrNullimpl.printStackTrace();
            }
            return EmptyList.INSTANCE;
        }
    }

    public final List getConnectedLeHearingAidDevice() {
        try {
            int i = Result.$r8$clinit;
            BluetoothLeAudio bluetoothLeAudio = this.leAudio;
            if (bluetoothLeAudio != null) {
                BluetoothCommonUtil.INSTANCE.getClass();
                List<BluetoothDevice> connectedDevices = bluetoothLeAudio.getConnectedDevices();
                if (connectedDevices == null) {
                    connectedDevices = EmptyList.INSTANCE;
                }
                if (connectedDevices != null) {
                    if (!(!connectedDevices.isEmpty())) {
                        connectedDevices = null;
                    }
                    if (connectedDevices != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : connectedDevices) {
                            BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                            Intrinsics.checkNotNull(bluetoothDevice);
                            if (!cachedBluetoothDevice(bluetoothDevice).isHearingAidDevice() && !cachedBluetoothDevice(bluetoothDevice).mIsHearingAidDeviceByUUID) {
                            }
                            arrayList.add(obj);
                        }
                        return arrayList;
                    }
                }
            }
            return EmptyList.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(new Result.Failure(th));
            if (m2527exceptionOrNullimpl != null) {
                m2527exceptionOrNullimpl.printStackTrace();
            }
            return EmptyList.INSTANCE;
        }
    }

    public final List getHearingAidDevices() {
        try {
            int i = Result.$r8$clinit;
            BluetoothHearingAid bluetoothHearingAid = this.hearingAid;
            if (bluetoothHearingAid != null) {
                BluetoothCommonUtil.INSTANCE.getClass();
                List<BluetoothDevice> connectedDevices = bluetoothHearingAid.getConnectedDevices();
                if (connectedDevices == null) {
                    connectedDevices = EmptyList.INSTANCE;
                }
                if (connectedDevices != null) {
                    if (!(!connectedDevices.isEmpty())) {
                        connectedDevices = null;
                    }
                    if (connectedDevices != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : connectedDevices) {
                            BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                            Intrinsics.checkNotNull(bluetoothDevice);
                            if (cachedBluetoothDevice(bluetoothDevice).isActiveDevice(21)) {
                                arrayList.add(obj);
                            }
                        }
                        return arrayList;
                    }
                }
            }
            return EmptyList.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(new Result.Failure(th));
            if (m2527exceptionOrNullimpl != null) {
                m2527exceptionOrNullimpl.printStackTrace();
            }
            return EmptyList.INSTANCE;
        }
    }
}
