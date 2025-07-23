package com.android.settingslib.media;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.provider.DeviceConfig;
import android.util.Log;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BluetoothMediaDevice extends MediaDevice {
    public final AudioManager mAudioManager;
    public final CachedBluetoothDevice mCachedDevice;

    public BluetoothMediaDevice(Context context, CachedBluetoothDevice cachedBluetoothDevice, MediaRoute2Info mediaRoute2Info, RouteListingPreference.Item item) {
        super(context, mediaRoute2Info, item);
        this.mCachedDevice = cachedBluetoothDevice;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        initDeviceRecord();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getAddress() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice != null ? cachedBluetoothDevice.mDevice.getAddress() : "";
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
        Drawable iconDrawable = cachedBluetoothDevice.getIconDrawable(true);
        if (BluetoothUtils.isBtCastConnectedAsHost(context, cachedBluetoothDevice.mDevice.getAddress())) {
            return BluetoothUtils.getOverlayIconTintableDrawable(iconDrawable, context, R.drawable.sharing_ic_overlay, R.drawable.sharing_ic_tintable);
        }
        iconDrawable.setTint(color);
        return iconDrawable;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIconWithoutBackground() {
        BluetoothDevice bluetoothDevice = this.mCachedDevice.mDevice;
        boolean z = BluetoothUtils.DEBUG;
        if (!DeviceConfig.getBoolean("settings_ui", "bt_advanced_header_enabled", true)) {
            Log.d("BluetoothUtils", "isAdvancedDetailsHeader: advancedEnabled is false");
        } else if (BluetoothUtils.getBooleanMetaData(bluetoothDevice)) {
            Log.d("BluetoothUtils", "isAdvancedDetailsHeader: untetheredHeadset is true");
            return this.mContext.getDrawable(R.drawable.ic_earbuds_advanced);
        }
        return (Drawable) BluetoothUtils.getBtClassDrawableWithDescription(this.mContext, this.mCachedDevice).first;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getId() {
        return (!this.mCachedDevice.isHearingAidDevice() || this.mCachedDevice.getHiSyncId() == 0) ? this.mCachedDevice.mDevice.getAddress() : Long.toString(this.mCachedDevice.getHiSyncId());
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
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return cachedBluetoothDevice != null && BluetoothUtils.getBooleanMetaData(cachedBluetoothDevice.mDevice);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isMutingExpectedDevice() {
        return this.mAudioManager.getMutingExpectedDevice() != null && this.mCachedDevice.mDevice.getAddress().equals(this.mAudioManager.getMutingExpectedDevice().getAddress());
    }
}
