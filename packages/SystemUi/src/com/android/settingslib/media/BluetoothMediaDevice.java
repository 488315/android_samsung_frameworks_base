package com.android.settingslib.media;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.Log;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BluetoothMediaDevice extends MediaDevice {
    public final AudioManager mAudioManager;
    public final CachedBluetoothDevice mCachedDevice;

    public BluetoothMediaDevice(Context context, CachedBluetoothDevice cachedBluetoothDevice, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str) {
        this(context, cachedBluetoothDevice, mediaRouter2Manager, mediaRoute2Info, str, null);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getAddress() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice != null ? cachedBluetoothDevice.getAddress() : "";
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getCurrentVolume() {
        return this.mAudioManager.semGetFineVolume(this.mCachedDevice.mDevice, 3);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getDevice() {
        return 128;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIcon() {
        Context context = this.mContext;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        boolean z = BluetoothUtils.DEBUG;
        int color = "com.android.systemui".equals(context.getPackageName().toLowerCase()) ? context.getResources().getColor(R.color.qs_detail_item_device_bt_icon_tint_color) : context.getResources().getColor(R.color.bt_device_icon_tint_color);
        if (cachedBluetoothDevice == null) {
            Log.d("BluetoothUtils", "getHostOverlayIconDrawable - cachedBluetoothDevice is null");
            Drawable drawable = context.getResources().getDrawable(R.drawable.list_ic_sound_accessory_default);
            drawable.setTint(color);
            return drawable;
        }
        Drawable iconDrawable = cachedBluetoothDevice.getIconDrawable();
        if (BluetoothUtils.isBtCastConnectedAsHost(context, cachedBluetoothDevice.getAddress())) {
            return BluetoothUtils.getOverlayIconTintableDrawable(iconDrawable, context, R.drawable.sharing_ic_overlay, R.drawable.sharing_ic_tintable);
        }
        iconDrawable.setTint(color);
        return iconDrawable;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
    @Override // com.android.settingslib.media.MediaDevice
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable getIconWithoutBackground() {
        boolean z;
        boolean z2;
        byte[] metadata;
        BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
        boolean z3 = BluetoothUtils.DEBUG;
        boolean z4 = true;
        if (DeviceConfig.getBoolean("settings_ui", "bt_advanced_header_enabled", true)) {
            z = true;
        } else {
            Log.d("BluetoothUtils", "isAdvancedDetailsHeader: advancedEnabled is false");
            z = false;
        }
        if (z) {
            if ((bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(6)) == null) ? false : Boolean.parseBoolean(new String(metadata))) {
                Log.d("BluetoothUtils", "isAdvancedDetailsHeader: untetheredHeadset is true");
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                if (TextUtils.equals(BluetoothUtils.getStringMetaData(bluetoothDevice, 17), "Untethered Headset")) {
                    Log.d("BluetoothUtils", "isAdvancedUntetheredDevice: is untethered device ");
                }
            }
            return !z4 ? this.mContext.getDrawable(R.drawable.ic_earbuds_advanced) : (Drawable) BluetoothUtils.getBtClassDrawableWithDescription(this.mContext, this.mCachedDevice).first;
        }
        z4 = false;
        if (!z4) {
        }
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getId() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return (!(cachedBluetoothDevice.mHearingAidInfo != null) || cachedBluetoothDevice.getHiSyncId() == 0) ? cachedBluetoothDevice.getAddress() : Long.toString(cachedBluetoothDevice.getHiSyncId());
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getName() {
        return this.mCachedDevice.getName();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getSelectionBehavior() {
        return 1;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isCarKitDevice() {
        BluetoothClass bluetoothClass = this.mCachedDevice.mDevice.getBluetoothClass();
        if (bluetoothClass == null) {
            return false;
        }
        int deviceClass = bluetoothClass.getDeviceClass();
        return deviceClass == 1032 || deviceClass == 1056;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isConnected() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice.mBondState == 12 && cachedBluetoothDevice.isConnected();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isFastPairDevice() {
        byte[] metadata;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        boolean z = BluetoothUtils.DEBUG;
        return (bluetoothDevice != null && (metadata = bluetoothDevice.getMetadata(6)) != null) ? Boolean.parseBoolean(new String(metadata)) : false;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isMutingExpectedDevice() {
        return this.mAudioManager.getMutingExpectedDevice() != null && this.mCachedDevice.getAddress().equals(this.mAudioManager.getMutingExpectedDevice().getAddress());
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final void requestSetVolume(int i) {
        this.mAudioManager.semSetFineVolume(this.mCachedDevice.mDevice, 3, i, 0);
    }

    public BluetoothMediaDevice(Context context, CachedBluetoothDevice cachedBluetoothDevice, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str, RouteListingPreference.Item item) {
        super(context, mediaRouter2Manager, mediaRoute2Info, str, item);
        this.mCachedDevice = cachedBluetoothDevice;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        initDeviceRecord();
    }
}
