package com.android.systemui.media.mediaoutput.ext;

import android.media.AudioDeviceInfo;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AudioDeviceInfoExt {
    public static final AudioDeviceInfoExt INSTANCE = new AudioDeviceInfoExt();

    private AudioDeviceInfoExt() {
    }

    public static boolean getNeedEarProtect(AudioDeviceInfo audioDeviceInfo) {
        int type = audioDeviceInfo.getType();
        return type == 3 || type == 4 || type == 8 || type == 26 || type == 22 || type == 23;
    }

    public static boolean isValidDeviceTypeForMedia(AudioDeviceInfo audioDeviceInfo, boolean z) {
        int type = audioDeviceInfo.getType();
        if (type == 2) {
            return !z;
        }
        if (type == 3 || type == 4 || type == 5 || type == 6 || type == 19 || type == 30 || type == 22 || type == 23) {
            return true;
        }
        switch (type) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                return true;
            default:
                switch (type) {
                    case 25:
                    case 26:
                    case 27:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public static String toLogText(AudioDeviceInfo audioDeviceInfo) {
        int id = audioDeviceInfo.getId();
        int type = audioDeviceInfo.getType();
        CharSequence productName = audioDeviceInfo.getProductName();
        String maskedLogText$default = StringExtKt.maskedLogText$default(audioDeviceInfo.getAddress());
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(id, type, "Id: ", ", Type: ", ", ProductName: ");
        m.append((Object) productName);
        m.append(", Address: ");
        m.append(maskedLogText$default);
        return m.toString();
    }
}
