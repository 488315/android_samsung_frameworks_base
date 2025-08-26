package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplay;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiPortInfo;
import android.media.AudioDeviceInfo;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.os.SystemProperties;
import android.util.Log;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda0;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda1;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda3;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.R;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class PhoneMediaDevice extends MediaDevice {
    public static CachedBluetoothCastDevice sCachedBluetoothCastDevice;
    public static String sDisplayDeviceName;

    /* JADX WARN: Removed duplicated region for block: B:22:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public PhoneMediaDevice(Context context, MediaRoute2Info mediaRoute2Info, RouteListingPreference.Item item) {
        CachedBluetoothCastDevice cachedBluetoothCastDevice;
        SemWifiDisplay activeDisplay;
        LocalBluetoothManager localBluetoothManager;
        AudioCastProfile audioCastProfile;
        super(context, mediaRoute2Info, item);
        new DeviceIconUtil(this.mContext);
        initDeviceRecord();
        LocalBluetoothManager localBluetoothManager2 = LocalBluetoothManager.getInstance(context, BluetoothUtils.mOnInitCallback);
        if (!SemBluetoothCastAdapter.isBluetoothCastSupported() || localBluetoothManager2 == null) {
            cachedBluetoothCastDevice = null;
        } else {
            ArrayList arrayList = (ArrayList) localBluetoothManager2.mCachedCastDeviceManager.getCachedCastDevicesCopy();
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    cachedBluetoothCastDevice = (CachedBluetoothCastDevice) obj;
                    String address = cachedBluetoothCastDevice.mCastDevice.getAddress();
                    if (SemBluetoothCastAdapter.isBluetoothCastSupported() && (localBluetoothManager = LocalBluetoothManager.getInstance(context, BluetoothUtils.mOnInitCallback)) != null && (audioCastProfile = localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile) != null) {
                        List list = (List) audioCastProfile.getConnectedDevices().stream().filter(new BluetoothUtils$$ExternalSyntheticLambda0(0)).filter(new BluetoothUtils$$ExternalSyntheticLambda1(audioCastProfile, 0)).filter(new BluetoothUtils$$ExternalSyntheticLambda0(1)).map(new BluetoothUtils$$ExternalSyntheticLambda3()).collect(Collectors.toList());
                        if (!list.isEmpty() && list.contains(address)) {
                            break;
                        }
                    }
                }
                cachedBluetoothCastDevice = null;
            }
        }
        sCachedBluetoothCastDevice = cachedBluetoothCastDevice;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        if (displayManager == null || displayManager.semGetWifiDisplayStatus() == null || (activeDisplay = displayManager.semGetWifiDisplayStatus().getActiveDisplay()) == null) {
            return;
        }
        sDisplayDeviceName = activeDisplay.getFriendlyDisplayName();
    }

    public static String getMediaTransferThisDeviceName(Context context) {
        return context.getPackageManager().hasSystemFeature("android.software.leanback") ? context.getString(R.string.sec_tv_speaker) : Arrays.asList(SystemProperties.get("ro.build.characteristics").split(",")).contains("tablet") ? context.getString(R.string.sec_tablet_speaker) : context.getString(R.string.sec_phone_speaker);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getAddress() {
        int device = getDevice();
        if (device == 32768) {
            return "0";
        }
        for (AudioDeviceInfo audioDeviceInfo : this.mAudioManager.getDevices(2)) {
            if (audioDeviceInfo.getDeviceId() == device) {
                return audioDeviceInfo.getAddress();
            }
        }
        return "";
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
        if (type == 9) {
            return 1024;
        }
        if (type == 22) {
            return 67108864;
        }
        if (type == 25) {
            return NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        }
        switch (type) {
            case 11:
            case 12:
                return 67108864;
            case 13:
                return 1024;
            default:
                return 2;
        }
    }

    public int getDrawableResId() {
        int type = this.mRouteInfo.getType();
        if (type == 3 || type == 4 || type == 9 || type == 22) {
            return R.drawable.list_ic_headset;
        }
        if (type == 25) {
            String str = sDisplayDeviceName;
            return (str == null || !str.contains("DeX")) ? R.drawable.list_ic_tv : R.drawable.list_ic_laptop;
        }
        switch (type) {
            case 11:
            case 12:
            case 13:
                return R.drawable.list_ic_headset;
            default:
                return R.drawable.list_ic_mobile;
        }
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIcon() {
        Drawable iconWithoutBackground = getIconWithoutBackground();
        if (sCachedBluetoothCastDevice != null && this.mRouteInfo.getType() == 25) {
            return sCachedBluetoothCastDevice.getBtCastDrawable();
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
        return this.mRouteInfo.getId();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e1  */
    @Override // com.android.settingslib.media.MediaDevice
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getName() {
        String mediaTransferThisDeviceName;
        HdmiPortInfo hdmiPortInfo;
        String displayName;
        Context context = this.mContext;
        MediaRoute2Info mediaRoute2Info = this.mRouteInfo;
        boolean zHasSystemFeature = context.getPackageManager().hasSystemFeature("android.software.leanback");
        int type = mediaRoute2Info.getType();
        if (type == 2) {
            mediaTransferThisDeviceName = getMediaTransferThisDeviceName(context);
        } else if (type == 3 || type == 4) {
            mediaTransferThisDeviceName = context.getString(R.string.media_transfer_wired_headphone_name);
        } else if (type == 5) {
            mediaTransferThisDeviceName = context.getString(R.string.media_transfer_analog_line_name);
        } else if (type == 6) {
            mediaTransferThisDeviceName = context.getString(R.string.media_transfer_digital_line_name);
        } else if (type == 19) {
            mediaTransferThisDeviceName = context.getString(R.string.media_transfer_aux_line_name);
        } else if (type == 22) {
            mediaTransferThisDeviceName = context.getString(R.string.media_transfer_wired_headphone_name);
        } else if (type == 25) {
            mediaTransferThisDeviceName = mediaRoute2Info.getName();
            CachedBluetoothCastDevice cachedBluetoothCastDevice = sCachedBluetoothCastDevice;
            if (cachedBluetoothCastDevice != null) {
                mediaTransferThisDeviceName = cachedBluetoothCastDevice.getName();
            }
            String str = sDisplayDeviceName;
            if (str != null) {
                mediaTransferThisDeviceName = str.contains("DeX") ? "PC" : sDisplayDeviceName;
            }
        } else if (type != 29) {
            switch (type) {
                case 9:
                    mediaTransferThisDeviceName = mediaRoute2Info.getName();
                    break;
                case 10:
                    if (!zHasSystemFeature) {
                        mediaTransferThisDeviceName = context.getString(R.string.media_transfer_external_device_name);
                        break;
                    } else {
                        String str2 = null;
                        if (context.checkCallingOrSelfPermission("android.permission.HDMI_CEC") == 0) {
                            HdmiControlManager hdmiControlManager = (HdmiControlManager) context.getSystemService(HdmiControlManager.class);
                            Iterator it = hdmiControlManager.getPortInfo().iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    hdmiPortInfo = (HdmiPortInfo) it.next();
                                    if (hdmiPortInfo.getType() == 1) {
                                    }
                                } else {
                                    hdmiPortInfo = null;
                                }
                            }
                            if (hdmiPortInfo != null) {
                                Iterator it2 = hdmiControlManager.getConnectedDevices().iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) it2.next();
                                        if (hdmiDeviceInfo.getPortId() == hdmiPortInfo.getId() && (displayName = hdmiDeviceInfo.getDisplayName()) != null && !displayName.isEmpty()) {
                                            str2 = displayName;
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.w("PhoneMediaDevice", "Could not get HDMI device name, android.permission.HDMI_CEC denied");
                        }
                        if (str2 == null) {
                            mediaTransferThisDeviceName = context.getString(R.string.tv_media_transfer_arc_fallback_title);
                            break;
                        } else {
                            mediaTransferThisDeviceName = str2;
                            break;
                        }
                    }
                    break;
                case 11:
                case 12:
                    break;
                case 13:
                    mediaTransferThisDeviceName = mediaRoute2Info.getName();
                    break;
                default:
                    mediaTransferThisDeviceName = getMediaTransferThisDeviceName(context);
                    break;
            }
        }
        return mediaTransferThisDeviceName.toString();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getSelectionBehavior() {
        return 1;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isConnected() {
        return true;
    }
}
