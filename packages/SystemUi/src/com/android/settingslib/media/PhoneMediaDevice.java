package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplay;
import android.media.AudioDeviceInfo;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda0;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda1;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda2;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.R;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDeviceManager;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PhoneMediaDevice extends MediaDevice {
    public final CachedBluetoothCastDevice mCachedBluetoothCastDevice;
    public final String mDisplayDeviceName;

    public PhoneMediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str) {
        this(context, mediaRouter2Manager, mediaRoute2Info, str, null);
        SemWifiDisplay activeDisplay;
        this.mCachedBluetoothCastDevice = getCachedBluetoothCastDevice();
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        if (displayManager == null || displayManager.semGetWifiDisplayStatus() == null || (activeDisplay = displayManager.semGetWifiDisplayStatus().getActiveDisplay()) == null) {
            return;
        }
        this.mDisplayDeviceName = activeDisplay.getFriendlyDisplayName();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getAddress() {
        int device = getDevice();
        if (device == 32768) {
            return DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        }
        for (AudioDeviceInfo audioDeviceInfo : this.mAudioManager.getDevices(2)) {
            if (audioDeviceInfo.getDeviceId() == device) {
                return audioDeviceInfo.getAddress();
            }
        }
        return "";
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a0, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CachedBluetoothCastDevice getCachedBluetoothCastDevice() {
        ArrayList arrayList;
        LocalBluetoothManager localBluetoothManager;
        AudioCastProfile audioCastProfile;
        LocalBluetoothManager localBluetoothManager2 = LocalBluetoothManager.getInstance(this.mContext, BluetoothUtils.mOnInitCallback);
        if (!SemBluetoothCastAdapter.isBluetoothCastSupported() || localBluetoothManager2 == null) {
            return null;
        }
        CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager = localBluetoothManager2.mCachedCastDeviceManager;
        synchronized (cachedBluetoothCastDeviceManager) {
            arrayList = new ArrayList(cachedBluetoothCastDeviceManager.mCachedCastDevices);
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CachedBluetoothCastDevice cachedBluetoothCastDevice = (CachedBluetoothCastDevice) it.next();
            Context context = this.mContext;
            String address = cachedBluetoothCastDevice.mCastDevice.getAddress();
            boolean z = false;
            if (SemBluetoothCastAdapter.isBluetoothCastSupported() && (localBluetoothManager = LocalBluetoothManager.getInstance(context, BluetoothUtils.mOnInitCallback)) != null && (audioCastProfile = localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile) != null) {
                SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                List list = (List) (semBluetoothAudioCast == null ? new ArrayList() : semBluetoothAudioCast.getConnectedDevices()).stream().filter(new BluetoothUtils$$ExternalSyntheticLambda0(0)).filter(new BluetoothUtils$$ExternalSyntheticLambda1(audioCastProfile, 0)).filter(new BluetoothUtils$$ExternalSyntheticLambda0(1)).map(new BluetoothUtils$$ExternalSyntheticLambda2(0)).collect(Collectors.toList());
                if (!list.isEmpty() && list.contains(address)) {
                    z = true;
                }
            }
        }
        return null;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getDevice() {
        int type = this.mRouteInfo.getType();
        if (type == 3) {
            return 4;
        }
        if (type == 4) {
            return 8;
        }
        if (type != 9) {
            if (type != 22) {
                if (type == 25) {
                    return 32768;
                }
                switch (type) {
                    case 11:
                    case 12:
                        break;
                    case 13:
                        break;
                    default:
                        return 2;
                }
            }
            return QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY;
        }
        return 1024;
    }

    public int getDrawableResId() {
        int type = this.mRouteInfo.getType();
        if (type != 3 && type != 4 && type != 9 && type != 22) {
            if (type == 25) {
                String str = this.mDisplayDeviceName;
                return (str == null || !str.contains("DeX")) ? R.drawable.list_ic_tv : R.drawable.list_ic_laptop;
            }
            switch (type) {
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    return R.drawable.list_ic_mobile;
            }
        }
        return R.drawable.list_ic_headset;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIcon() {
        Drawable iconWithoutBackground = getIconWithoutBackground();
        if (this.mRouteInfo.getType() == 25) {
            CachedBluetoothCastDevice cachedBluetoothCastDevice = this.mCachedBluetoothCastDevice;
            if (cachedBluetoothCastDevice != null) {
                return cachedBluetoothCastDevice.getBtCastDrawable();
            }
        }
        iconWithoutBackground.setTint(this.mContext.getResources().getColor(R.color.bt_device_icon_tint_color));
        return iconWithoutBackground;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIconWithoutBackground() {
        return this.mContext.getDrawable(getDrawableResId());
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getId() {
        int type = this.mRouteInfo.getType();
        if (type != 2) {
            if (type == 3 || type == 4) {
                return "wired_headset_media_device_id";
            }
            if (type != 9 && type != 22) {
                if (type != 25) {
                    switch (type) {
                        case 11:
                        case 12:
                        case 13:
                            break;
                        default:
                            return "phone_media_device_id";
                    }
                }
            }
            return "usb_headset_media_device_id";
        }
        return "remote_submix_media_device_id";
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getName() {
        String string;
        int type = this.mRouteInfo.getType();
        if (type != 2) {
            if (type != 3 && type != 4) {
                if (type == 9) {
                    string = this.mRouteInfo.getName();
                } else if (type != 22) {
                    if (type != 25) {
                        switch (type) {
                            case 11:
                            case 12:
                                break;
                            case 13:
                                string = this.mRouteInfo.getName();
                                break;
                            default:
                                string = this.mContext.getString(R.string.sec_media_output_device);
                                break;
                        }
                    } else {
                        CharSequence name = this.mRouteInfo.getName();
                        CachedBluetoothCastDevice cachedBluetoothCastDevice = this.mCachedBluetoothCastDevice;
                        if (cachedBluetoothCastDevice != null) {
                            name = cachedBluetoothCastDevice.getName();
                        }
                        String str = this.mDisplayDeviceName;
                        string = str != null ? str.contains("DeX") ? "PC" : this.mDisplayDeviceName : name;
                    }
                }
            }
            string = this.mContext.getString(R.string.media_transfer_wired_usb_device_name);
        } else {
            string = this.mContext.getString(R.string.sec_media_output_device);
        }
        return string.toString();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getSelectionBehavior() {
        return 1;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isConnected() {
        return true;
    }

    public PhoneMediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str, RouteListingPreference.Item item) {
        super(context, mediaRouter2Manager, mediaRoute2Info, str, item);
        SemWifiDisplay activeDisplay;
        this.mCachedBluetoothCastDevice = null;
        this.mDisplayDeviceName = null;
        new DeviceIconUtil();
        this.mCachedBluetoothCastDevice = getCachedBluetoothCastDevice();
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        if (displayManager != null && displayManager.semGetWifiDisplayStatus() != null && (activeDisplay = displayManager.semGetWifiDisplayStatus().getActiveDisplay()) != null) {
            this.mDisplayDeviceName = activeDisplay.getFriendlyDisplayName();
        }
        initDeviceRecord();
    }
}
