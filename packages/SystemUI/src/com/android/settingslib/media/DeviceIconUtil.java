package com.android.settingslib.media;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.SystemProperties;
import android.util.SparseIntArray;
import com.android.systemui.R;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public class DeviceIconUtil {
    public static final SparseIntArray AUDIO_DEVICE_TO_MEDIA_ROUTE_TYPE;
    public final Context mContext;
    public final boolean mIsTablet;
    public final boolean mIsTv;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        AUDIO_DEVICE_TO_MEDIA_ROUTE_TYPE = sparseIntArray;
        sparseIntArray.put(11, 11);
        sparseIntArray.put(22, 22);
        sparseIntArray.put(12, 12);
        sparseIntArray.put(13, 13);
        sparseIntArray.put(9, 9);
        sparseIntArray.put(10, 10);
        sparseIntArray.put(29, 29);
        sparseIntArray.put(3, 3);
        sparseIntArray.put(4, 4);
        sparseIntArray.put(2, 2);
    }

    public DeviceIconUtil(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mIsTv = context.getPackageManager().hasSystemFeature("android.software.leanback");
        this.mIsTablet = Arrays.asList(SystemProperties.get("ro.build.characteristics").split(",")).contains("tablet");
    }

    public final int getIconResIdFromAudioDeviceType(int i) {
        int i2 = AUDIO_DEVICE_TO_MEDIA_ROUTE_TYPE.get(i, -1);
        if (!this.mIsTv) {
            int i3 = this.mIsTablet ? R.drawable.ic_media_tablet : R.drawable.ic_smartphone;
            if (i2 == 3 || i2 == 4 || i2 == 22) {
                return R.drawable.ic_headphone;
            }
            if (i2 == 29) {
                return R.drawable.ic_external_display;
            }
            switch (i2) {
                case 9:
                case 10:
                    return R.drawable.ic_external_display;
                case 11:
                case 12:
                    return R.drawable.ic_headphone;
                case 13:
                    return R.drawable.ic_dock_device;
                default:
                    return i3;
            }
        }
        if (i2 == 2) {
            Context context = this.mContext;
            if (context == null) {
                return R.drawable.ic_tv;
            }
            for (AudioDeviceInfo audioDeviceInfo : ((AudioManager) context.getSystemService(AudioManager.class)).getDevices(2)) {
                if (audioDeviceInfo.getType() == 9) {
                    return R.drawable.ic_tv_box_internal_speaker;
                }
            }
            return R.drawable.ic_tv;
        }
        if (i2 == 3 || i2 == 4) {
            return R.drawable.ic_wired_device;
        }
        if (i2 == 22) {
            return R.drawable.ic_headphone;
        }
        if (i2 == 29) {
            return R.drawable.ic_hdmi;
        }
        switch (i2) {
            case 9:
                return R.drawable.ic_tv;
            case 10:
                return R.drawable.ic_hdmi;
            case 11:
                return R.drawable.ic_headphone;
            case 12:
                return R.drawable.ic_usb;
            case 13:
                return R.drawable.ic_dock_device;
            default:
                return R.drawable.ic_media_speaker_device;
        }
    }

    public DeviceIconUtil(boolean z) {
        this.mContext = null;
        this.mIsTv = z;
        this.mIsTablet = false;
    }
}
