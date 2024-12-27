package com.android.systemui.media.mediaoutput.ext;

import android.media.MediaRoute2Info;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.icons.Icons$Device;
import com.android.systemui.media.mediaoutput.icons.device.DockKt;
import com.android.systemui.media.mediaoutput.icons.device.GroupSpeakerKt;
import com.android.systemui.media.mediaoutput.icons.device.HdmiKt;
import com.android.systemui.media.mediaoutput.icons.device.HearingAidsKt;
import com.android.systemui.media.mediaoutput.icons.device.LevelBoxKt;
import com.android.systemui.media.mediaoutput.icons.device.LevelUKt;
import com.android.systemui.media.mediaoutput.icons.device.MobileDeviceKt;
import com.android.systemui.media.mediaoutput.icons.device.SoundAccessoryKt;
import com.android.systemui.media.mediaoutput.icons.device.TabletKt;
import com.android.systemui.media.mediaoutput.icons.device.TvKt;
import com.android.systemui.media.mediaoutput.icons.device.TwsKt;
import com.android.systemui.media.mediaoutput.icons.device.UsbKt;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class MediaRoute2InfoExtKt {
    public static final CharSequence getDisplayName(MediaRoute2Info mediaRoute2Info) {
        Integer valueOf;
        int type = mediaRoute2Info.getType();
        if (type == 1 || type == 2) {
            valueOf = Integer.valueOf((DeviceType.isTablet() ? mediaRoute2Info : null) != null ? R.string.tablet_speaker : R.string.phone_speaker);
        } else {
            if (type != 3) {
                if (type == 4) {
                    valueOf = Integer.valueOf(R.string.headphones);
                } else if (type != 22) {
                    switch (type) {
                        case 9:
                        case 10:
                            valueOf = Integer.valueOf(R.string.hdmi_mhl_device);
                            break;
                        case 11:
                            break;
                        default:
                            valueOf = null;
                            break;
                    }
                }
            }
            valueOf = Integer.valueOf(R.string.headset);
        }
        return valueOf != null ? new ResourceString(valueOf.intValue(), null, 2, null) : mediaRoute2Info.getName().toString();
    }

    public static final ImageVector getSimpleIcon(MediaRoute2Info mediaRoute2Info) {
        int type = mediaRoute2Info.getType();
        if (type == 2) {
            if (DeviceType.isTablet()) {
                Icons$Device icons$Device = Icons$Device.INSTANCE;
                return (ImageVector) TabletKt.Tablet$delegate.getValue();
            }
            Icons$Device icons$Device2 = Icons$Device.INSTANCE;
            return (ImageVector) MobileDeviceKt.MobileDevice$delegate.getValue();
        }
        if (type != 3 && type != 4 && type != 22) {
            if (type == 23) {
                Icons$Device icons$Device3 = Icons$Device.INSTANCE;
                return (ImageVector) HearingAidsKt.HearingAids$delegate.getValue();
            }
            if (type != 26) {
                if (type != 29) {
                    if (type == 2000) {
                        Icons$Device icons$Device4 = Icons$Device.INSTANCE;
                        return (ImageVector) GroupSpeakerKt.GroupSpeaker$delegate.getValue();
                    }
                    if (type == 1001) {
                        Icons$Device icons$Device5 = Icons$Device.INSTANCE;
                        return TvKt.getTv();
                    }
                    if (type == 1002) {
                        Icons$Device icons$Device6 = Icons$Device.INSTANCE;
                        return (ImageVector) LevelBoxKt.LevelBox$delegate.getValue();
                    }
                    switch (type) {
                        case 8:
                            break;
                        case 9:
                        case 10:
                            break;
                        case 11:
                            break;
                        case 12:
                            Icons$Device icons$Device7 = Icons$Device.INSTANCE;
                            return (ImageVector) UsbKt.Usb$delegate.getValue();
                        case 13:
                            Icons$Device icons$Device8 = Icons$Device.INSTANCE;
                            return (ImageVector) DockKt.Dock$delegate.getValue();
                        default:
                            Icons$Device icons$Device9 = Icons$Device.INSTANCE;
                            return (ImageVector) SoundAccessoryKt.SoundAccessory$delegate.getValue();
                    }
                }
                Icons$Device icons$Device10 = Icons$Device.INSTANCE;
                return (ImageVector) HdmiKt.Hdmi$delegate.getValue();
            }
            Icons$Device icons$Device11 = Icons$Device.INSTANCE;
            return (ImageVector) TwsKt.Tws$delegate.getValue();
        }
        Icons$Device icons$Device12 = Icons$Device.INSTANCE;
        return (ImageVector) LevelUKt.LevelU$delegate.getValue();
    }
}
