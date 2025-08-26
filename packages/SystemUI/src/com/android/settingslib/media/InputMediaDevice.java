package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class InputMediaDevice extends MediaDevice {
    public final int mAudioDeviceInfoType;
    public final int mCurrentVolume;
    public final String mId;
    public final boolean mIsVolumeFixed;
    public final int mMaxVolume;
    public final String mProductName;

    private InputMediaDevice(Context context, String str, int i, int i2, int i3, boolean z, String str2) {
        super(context, null, null);
        this.mId = str;
        this.mAudioDeviceInfoType = i;
        this.mMaxVolume = i2;
        this.mCurrentVolume = i3;
        this.mIsVolumeFixed = z;
        this.mProductName = str2;
        initDeviceRecord();
    }

    public static InputMediaDevice create(Context context, int i, String str, String str2) {
        if (isSupportedInputDevice(i)) {
            return new InputMediaDevice(context, str, i, 100, 100, true, str2);
        }
        return null;
    }

    public static boolean isSupportedInputDevice(int i) {
        return i == 3 || i == 7 || i == 15 || i == 22 || i == 26 || i == 11 || i == 12;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getCurrentVolume() {
        return this.mCurrentVolume;
    }

    public int getDrawableResId() {
        return R.drawable.ic_media_microphone;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIcon() {
        return getIconWithoutBackground();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIconWithoutBackground() {
        return this.mContext.getDrawable(getDrawableResId());
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getId() {
        return this.mId;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getMaxVolume() {
        return this.mMaxVolume;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getName() {
        int i = this.mAudioDeviceInfoType;
        if (i == 3) {
            return this.mContext.getString(R.string.media_transfer_wired_device_mic_name);
        }
        if (i != 7) {
            if (i != 22) {
                if (i != 26) {
                    if (i != 11 && i != 12) {
                        return this.mContext.getString(R.string.media_transfer_this_device_name_desktop);
                    }
                }
            }
            String str = this.mProductName;
            return str != null ? str : this.mContext.getString(R.string.media_transfer_usb_device_mic_name);
        }
        String str2 = this.mProductName;
        return str2 != null ? str2 : this.mContext.getString(R.string.media_transfer_bt_device_mic_name);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getSelectionBehavior() {
        return 1;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isConnected() {
        return true;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isVolumeFixed() {
        return this.mIsVolumeFixed;
    }
}
