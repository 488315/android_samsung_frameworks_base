package com.android.systemui.volume.util;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BluetoothAdapterWrapper {
    public BluetoothA2dp a2dp;
    public final BluetoothAdapterWrapper$bluetoothProfileServiceListener$1 bluetoothProfileServiceListener;
    public BluetoothHearingAid hearingAid;
    public BluetoothHeadset hfp;
    public BluetoothLeAudio leAudio;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.bluetooth.BluetoothProfile$ServiceListener, com.android.systemui.volume.util.BluetoothAdapterWrapper$bluetoothProfileServiceListener$1] */
    public BluetoothAdapterWrapper(Context context) {
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
        this.bluetoothProfileServiceListener = r0;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != 0) {
            defaultAdapter.getProfileProxy(context, r0, 2);
            defaultAdapter.getProfileProxy(context, r0, 1);
            defaultAdapter.getProfileProxy(context, r0, 22);
            defaultAdapter.getProfileProxy(context, r0, 21);
        }
    }

    public final String getActiveBTDeviceName() {
        BluetoothA2dp bluetoothA2dp = this.a2dp;
        if (bluetoothA2dp != null) {
            BluetoothA2dpUtil.INSTANCE.getClass();
            BluetoothDevice activeDevice = bluetoothA2dp.getActiveDevice();
            r2 = activeDevice != null ? activeDevice.semGetAliasName() : null;
            if (r2 == null) {
                return "";
            }
        } else {
            BluetoothLeAudio bluetoothLeAudio = this.leAudio;
            if (bluetoothLeAudio != null) {
                BluetoothLeAudioUtil.INSTANCE.getClass();
                BluetoothCommonUtil.INSTANCE.getClass();
                BluetoothDevice bluetoothDevice = (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull(BluetoothCommonUtil.connectedDevices(bluetoothLeAudio));
                r2 = bluetoothDevice != null ? bluetoothDevice.semGetAliasName() : null;
                if (r2 == null) {
                    return "";
                }
            } else {
                BluetoothHearingAid bluetoothHearingAid = this.hearingAid;
                if (bluetoothHearingAid != null) {
                    BluetoothCommonUtil.INSTANCE.getClass();
                    List mapNames = BluetoothCommonUtil.mapNames(BluetoothCommonUtil.connectedDevices(bluetoothHearingAid));
                    if (!(!((ArrayList) mapNames).isEmpty())) {
                        mapNames = null;
                    }
                    if (mapNames != null) {
                        r2 = (String) mapNames.get(0);
                    }
                }
                if (r2 == null) {
                    return "";
                }
            }
        }
        return r2;
    }

    public final String getBtCallDeviceName() {
        String semGetAliasName;
        Object obj;
        BluetoothHeadset bluetoothHeadset = this.hfp;
        if (bluetoothHeadset != null) {
            BluetoothHeadsetUtil.INSTANCE.getClass();
            BluetoothCommonUtil.INSTANCE.getClass();
            Iterator it = BluetoothCommonUtil.connectedDevices(bluetoothHeadset).iterator();
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
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull(BluetoothCommonUtil.connectedDevices(bluetoothLeAudio));
            semGetAliasName = bluetoothDevice2 != null ? bluetoothDevice2.semGetAliasName() : null;
            if (semGetAliasName == null) {
                return "";
            }
        }
        return semGetAliasName;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
    
        if (r0 == null) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List getConnectedDevices() {
        List list;
        BluetoothA2dp bluetoothA2dp = this.a2dp;
        if (bluetoothA2dp != null) {
            BluetoothA2dpUtil.INSTANCE.getClass();
            List orderConnectedDevices = BluetoothA2dpUtil.getOrderConnectedDevices(bluetoothA2dp);
            if (orderConnectedDevices != null) {
                if (!(!orderConnectedDevices.isEmpty())) {
                    orderConnectedDevices = null;
                }
                if (orderConnectedDevices != null) {
                    return orderConnectedDevices;
                }
            }
        }
        BluetoothLeAudio bluetoothLeAudio = this.leAudio;
        if (bluetoothLeAudio != null) {
            BluetoothCommonUtil.INSTANCE.getClass();
            list = BluetoothCommonUtil.connectedDevices(bluetoothLeAudio);
        }
        list = EmptyList.INSTANCE;
        if (!(!list.isEmpty())) {
            list = null;
        }
        if (list != null) {
            return list;
        }
        BluetoothHearingAid bluetoothHearingAid = this.hearingAid;
        if (bluetoothHearingAid != null) {
            BluetoothCommonUtil.INSTANCE.getClass();
            List connectedDevices = BluetoothCommonUtil.connectedDevices(bluetoothHearingAid);
            if (connectedDevices != null) {
                List list2 = connectedDevices.isEmpty() ^ true ? connectedDevices : null;
                if (list2 != null) {
                    return list2;
                }
            }
        }
        return EmptyList.INSTANCE;
    }
}
