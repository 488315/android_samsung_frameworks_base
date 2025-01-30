package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.R;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CachedBluetoothCastDevice implements Comparable {
    public final String TAG = "CachedBluetoothCastDevice";
    public final Collection mCallbacks;
    public SemBluetoothCastDevice mCastDevice;
    public final HashMap mCastProfileConnectionState;
    public final LinkedHashSet mCastProfiles;
    public final Context mContext;
    public String mErrorMsg;
    public String mName;
    public int mSequence;
    public final Handler toastHandler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ToastRunnable implements Runnable {
        public final int mLength;
        public final String mText;

        public ToastRunnable(String str, int i) {
            this.mText = str;
            this.mLength = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Toast.makeText(CachedBluetoothCastDevice.this.mContext, this.mText, this.mLength).show();
        }
    }

    public CachedBluetoothCastDevice(Context context, LocalBluetoothCastProfileManager localBluetoothCastProfileManager, SemBluetoothCastDevice semBluetoothCastDevice) {
        AudioCastProfile audioCastProfile;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.mCastProfiles = linkedHashSet;
        this.mCallbacks = new ArrayList();
        this.mContext = context;
        this.mCastDevice = semBluetoothCastDevice;
        this.mCastProfileConnectionState = new HashMap();
        this.toastHandler = new Handler();
        this.mName = this.mCastDevice.getDeviceName();
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(this.mCastDevice.getBluetoothCastType()));
        synchronized (localBluetoothCastProfileManager) {
            linkedHashSet.clear();
            if (arrayList.contains(1) && ((audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) != null || !linkedHashSet.contains(audioCastProfile))) {
                Log.d(localBluetoothCastProfileManager.TAG, "Audio Cast Profile added");
                linkedHashSet.add(localBluetoothCastProfileManager.mAudioCastProfile);
            }
        }
        Log.d("CachedBluetoothCastDevice", "updateCastProfiles : " + String.valueOf(linkedHashSet.size()));
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        CachedBluetoothCastDevice cachedBluetoothCastDevice = (CachedBluetoothCastDevice) obj;
        int maxConnectionState = cachedBluetoothCastDevice.getMaxConnectionState();
        int maxConnectionState2 = getMaxConnectionState();
        int i = (maxConnectionState == 2 ? 1 : 0) - (maxConnectionState2 == 2 ? 1 : 0);
        if (i != 0) {
            return i;
        }
        int i2 = ((maxConnectionState == 1 || maxConnectionState == 3) ? 1 : 0) - ((maxConnectionState2 == 1 || maxConnectionState2 == 3) ? 1 : 0);
        if (i2 != 0) {
            return i2;
        }
        long connectionTimeStamp = getConnectionTimeStamp() - cachedBluetoothCastDevice.getConnectionTimeStamp();
        if (connectionTimeStamp > 0) {
            return 1;
        }
        if (connectionTimeStamp < 0) {
            return -1;
        }
        int i3 = this.mSequence - cachedBluetoothCastDevice.mSequence;
        return i3 != 0 ? i3 : this.mName.compareTo(cachedBluetoothCastDevice.mName);
    }

    public final void disconnect() {
        Iterator it = this.mCastProfiles.iterator();
        while (it.hasNext()) {
            LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) it.next();
            SemBluetoothCastDevice semBluetoothCastDevice = this.mCastDevice;
            AudioCastProfile audioCastProfile = (AudioCastProfile) localBluetoothCastProfile;
            Log.d(audioCastProfile.TAG, "disconnect");
            SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
            if (semBluetoothAudioCast != null) {
                semBluetoothAudioCast.disconnect(semBluetoothCastDevice);
            }
        }
    }

    public final void dispatchAttributesChanged() {
        try {
            synchronized (this.mCallbacks) {
                Iterator it = ((ArrayList) this.mCallbacks).iterator();
                if (it.hasNext()) {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it.next());
                    throw null;
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00fa, code lost:
    
        if (r4 == 0) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable getBtCastDrawable() {
        int i;
        Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.list_ic_general_device);
        if (this.mCastDevice.getBluetoothCastType() == 1) {
            Resources resources = this.mContext.getResources();
            String deviceName = this.mCastDevice.getDeviceName();
            BluetoothClass bluetoothClass = this.mCastDevice.getBluetoothClass();
            ManufacturerData manufacturerData = new ManufacturerData(this.mCastDevice.getManufacturerData());
            Log.d(this.TAG, "getBtClassDrawable :: " + this.mCastDevice.getAddressForLog() + ", BluetoothClass = " + bluetoothClass);
            int deviceIcon = manufacturerData.getDeviceIcon();
            if (deviceIcon == 0) {
                if (bluetoothClass != null) {
                    int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
                    if (majorDeviceClass == 256) {
                        if (bluetoothClass.getDeviceClass() != 284) {
                            deviceIcon = R.drawable.list_ic_laptop;
                        }
                        deviceIcon = R.drawable.list_ic_tablet;
                    } else if (majorDeviceClass == 512) {
                        byte[] manufacturerData2 = this.mCastDevice.getManufacturerData();
                        ManufacturerData manufacturerData3 = new ManufacturerData(manufacturerData2);
                        if (!(manufacturerData2 != null && manufacturerData3.mManufacturerType == 2 && manufacturerData3.mData.mDeviceCategory == 2)) {
                            deviceIcon = R.drawable.list_ic_mobile;
                        }
                        deviceIcon = R.drawable.list_ic_tablet;
                    } else if (majorDeviceClass == 1024) {
                        if (deviceName != null) {
                            if (deviceName.toUpperCase().startsWith("SAMSUNG LEVEL")) {
                                i = deviceName.toUpperCase().contains("BOX") ? R.drawable.list_ic_dlna_audio : R.drawable.list_ic_headset;
                            } else if (deviceName.toUpperCase().startsWith("GEAR CIRCLE") && isGearIconX()) {
                                i = R.drawable.list_ic_gear_circle;
                            } else {
                                deviceIcon = 0;
                            }
                            deviceIcon = i;
                        }
                        if (isGearIconX()) {
                            deviceIcon = R.drawable.list_ic_true_wireless_earbuds;
                        } else if (bluetoothClass.getDeviceClass() == 1084) {
                            deviceIcon = R.drawable.list_ic_tv;
                        } else {
                            if (bluetoothClass.getDeviceClass() == 1076) {
                                deviceIcon = R.drawable.list_ic_camcoder;
                            }
                            if (!bluetoothClass.doesClassMatch(1)) {
                                deviceIcon = R.drawable.list_ic_mono_headset;
                            }
                        }
                    } else if (majorDeviceClass != 1536) {
                        if (majorDeviceClass == 1792 && bluetoothClass.getDeviceClass() == 1796) {
                            deviceIcon = (deviceName == null || !(deviceName.toUpperCase().startsWith("GEAR FIT") || deviceName.toUpperCase().startsWith("GALAXY FIT"))) ? R.drawable.list_ic_wearable : R.drawable.list_ic_band;
                        }
                        if (!bluetoothClass.doesClassMatch(1) && bluetoothClass.doesClassMatch(0)) {
                            deviceIcon = R.drawable.list_ic_mono_headset;
                        }
                    } else {
                        deviceIcon = (bluetoothClass.getDeviceClass() == 1664 || bluetoothClass.getDeviceClass() == 1600) ? R.drawable.list_ic_printer : R.drawable.list_ic_camera;
                    }
                } else {
                    Log.w(this.TAG, "mBtClass is null");
                }
                deviceIcon = R.drawable.list_ic_sound_accessory_default;
            }
            drawable = resources.getDrawable(deviceIcon);
        }
        return BluetoothUtils.getOverlayIconTintableDrawable(drawable, this.mContext, R.drawable.sharing_ic_overlay, R.drawable.sharing_ic_tintable);
    }

    public final int getCastProfileConnectionState(LocalBluetoothCastProfile localBluetoothCastProfile) {
        if (this.mCastProfileConnectionState.get(localBluetoothCastProfile) == null) {
            this.mCastProfileConnectionState.put(localBluetoothCastProfile, 0);
        }
        return ((Integer) this.mCastProfileConnectionState.get(localBluetoothCastProfile)).intValue();
    }

    public final long getConnectionTimeStamp() {
        LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(this.mContext, null);
        if (localBluetoothManager == null) {
            return 0L;
        }
        SemBluetoothCastDevice semBluetoothCastDevice = this.mCastDevice;
        LocalBluetoothCastAdapter localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter;
        SemBluetoothCastAdapter semBluetoothCastAdapter = localBluetoothCastAdapter.mCastAdapter;
        String str = localBluetoothCastAdapter.TAG;
        if (semBluetoothCastAdapter == null) {
            Log.d(str, "Cannot getLastConnectedTime");
            return 0L;
        }
        Log.d(str, "cancelDiscovery");
        return localBluetoothCastAdapter.mCastAdapter.getLastConnectedTime(semBluetoothCastDevice);
    }

    public final int getMaxConnectionState() {
        int castProfileConnectionState;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mCastProfiles) {
            arrayList.addAll(this.mCastProfiles);
        }
        List unmodifiableList = Collections.unmodifiableList(arrayList);
        Log.d(this.TAG, "getMaxConnectionState size : " + String.valueOf(unmodifiableList.size()));
        int i = 0;
        for (int i2 = 0; i2 < unmodifiableList.size(); i2++) {
            LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) unmodifiableList.get(i2);
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("getMaxConnectionState profile != null : ");
            sb.append(String.valueOf(localBluetoothCastProfile != null));
            Log.d(str, sb.toString());
            if (localBluetoothCastProfile != null && (castProfileConnectionState = getCastProfileConnectionState(localBluetoothCastProfile)) > i) {
                i = castProfileConnectionState;
            }
        }
        Log.d(this.TAG, "getMaxConnectionState : " + String.valueOf(i));
        return i;
    }

    public final String getName() {
        String str = this.mName;
        return str != null ? str : this.mCastDevice.getDeviceName() != null ? this.mCastDevice.getDeviceName() : this.mCastDevice.getAddress();
    }

    public final boolean isConnected() {
        Iterator it = this.mCastProfiles.iterator();
        while (it.hasNext()) {
            if (getCastProfileConnectionState((LocalBluetoothCastProfile) it.next()) == 2) {
                return true;
            }
        }
        return false;
    }

    public final boolean isGearIconX() {
        byte[] manufacturerData = this.mCastDevice.getManufacturerData();
        BluetoothClass bluetoothClass = this.mCastDevice.getBluetoothClass();
        ManufacturerData manufacturerData2 = new ManufacturerData(manufacturerData);
        if (manufacturerData == null || bluetoothClass == null || manufacturerData.length < 9) {
            return false;
        }
        byte[] bArr = manufacturerData2.mData.mDeviceId;
        byte b = bArr[0];
        return (b == 0 || b == 1) && bArr[1] == 1 && bluetoothClass.getDeviceClass() == 1028;
    }

    public final void onCastProfileStateChanged(LocalBluetoothCastProfile localBluetoothCastProfile, int i) {
        Log.d(this.TAG, "onCastProfileStateChanged : " + String.valueOf(i));
        this.mCastProfileConnectionState.put(localBluetoothCastProfile, Integer.valueOf(i));
        this.mCastDevice.setConnectionState(i);
        if (i == 2 && !this.mCastProfiles.contains(localBluetoothCastProfile)) {
            this.mCastProfiles.add(localBluetoothCastProfile);
        }
        dispatchAttributesChanged();
    }
}
