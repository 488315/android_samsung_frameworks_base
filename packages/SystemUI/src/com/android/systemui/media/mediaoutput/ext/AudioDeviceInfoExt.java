package com.android.systemui.media.mediaoutput.ext;

import android.media.AudioDeviceInfo;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AudioDeviceInfoExt {
    public static final AudioDeviceInfoExt INSTANCE = new AudioDeviceInfoExt();

    private AudioDeviceInfoExt() {
    }

    public static boolean getNeedEarProtect(AudioDeviceInfo audioDeviceInfo) {
        int type = audioDeviceInfo.getType();
        return type == 3 || type == 4 || type == 8 || type == 22 || type == 26;
    }

    public static boolean isValidDeviceTypeForMedia(AudioDeviceInfo audioDeviceInfo, boolean z) {
        int type = audioDeviceInfo.getType();
        if (type != 2) {
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
                    }
                    return true;
            }
        }
        if (!z) {
            return true;
        }
        return false;
    }

    public static String toLogText(AudioDeviceInfo audioDeviceInfo) {
        int id = audioDeviceInfo.getId();
        int type = audioDeviceInfo.getType();
        CharSequence productName = audioDeviceInfo.getProductName();
        String maskedLogText$default = StringExtKt.maskedLogText$default(audioDeviceInfo.getAddress());
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(id, type, "Id: ", ", Type: ", ", ProductName: ");
        m.append((Object) productName);
        m.append(", Address: ");
        m.append(maskedLogText$default);
        return m.toString();
    }
}
