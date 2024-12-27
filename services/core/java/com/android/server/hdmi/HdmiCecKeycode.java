package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import com.android.internal.util.FrameworkStatsLog;
import libcore.util.EmptyArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class HdmiCecKeycode {
    public static final KeycodeEntry[] KEYCODE_ENTRIES = {new KeycodeEntry(23, 0), new KeycodeEntry(19, 1), new KeycodeEntry(20, 2), new KeycodeEntry(21, 3), new KeycodeEntry(22, 4), new KeycodeEntry(-1, 5), new KeycodeEntry(-1, 6), new KeycodeEntry(-1, 7), new KeycodeEntry(-1, 8), new KeycodeEntry(3, 9), new KeycodeEntry(82, 9), new KeycodeEntry(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT, 10), new KeycodeEntry(256, 11, 0), new KeycodeEntry(-1, 12), new KeycodeEntry(4, 13), new KeycodeEntry(111, 13), new KeycodeEntry(226, 16), new KeycodeEntry(FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP, 17), new KeycodeEntry(234, 29), new KeycodeEntry(FrameworkStatsLog.CAMERA_ACTION_EVENT, 30), new KeycodeEntry(228, 31), new KeycodeEntry(7, 32), new KeycodeEntry(8, 33), new KeycodeEntry(9, 34), new KeycodeEntry(10, 35), new KeycodeEntry(11, 36), new KeycodeEntry(12, 37), new KeycodeEntry(13, 38), new KeycodeEntry(14, 39), new KeycodeEntry(15, 40), new KeycodeEntry(16, 41), new KeycodeEntry(56, 42), new KeycodeEntry(160, 43), new KeycodeEntry(28, 44), new KeycodeEntry(-1, 47), new KeycodeEntry(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP, 48), new KeycodeEntry(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_INSTALL_BANNER_INTENT, 49), new KeycodeEntry(229, 50), new KeycodeEntry(222, 51), new KeycodeEntry(178, 52), new KeycodeEntry(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_WORK_APP, 53), new KeycodeEntry(-1, 54), new KeycodeEntry(92, 55), new KeycodeEntry(93, 56), new KeycodeEntry(26, 64, 0), new KeycodeEntry(24, 65), new KeycodeEntry(25, 66), new KeycodeEntry(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_ADMIN_RESTRICTED, 67, 0), new KeycodeEntry(126, 68), new KeycodeEntry(86, 69), new KeycodeEntry(127, 70), new KeycodeEntry(85, 70), new KeycodeEntry(130, 71), new KeycodeEntry(89, 72), new KeycodeEntry(90, 73), new KeycodeEntry(129, 74), new KeycodeEntry(87, 75), new KeycodeEntry(88, 76), new KeycodeEntry(-1, 77), new KeycodeEntry(-1, 78), new KeycodeEntry(-1, 79), new KeycodeEntry(-1, 80), new KeycodeEntry(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP, 81), new KeycodeEntry(-1, 82), new KeycodeEntry(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_PERMISSION_REVOKED, 83), new KeycodeEntry(258, 84), new KeycodeEntry(-1, 85), new KeycodeEntry(-1, 86), new KeycodeEntry(235, 86, new byte[]{(byte) 16}, true), new KeycodeEntry(236, 86, new byte[]{(byte) 96}, true), new KeycodeEntry(FrameworkStatsLog.REBOOT_ESCROW_RECOVERY_REPORTED, 86, new byte[]{(byte) 128}, true), new KeycodeEntry(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, 86, new byte[]{(byte) 144}, true), new KeycodeEntry(FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_HYBRID_NNHDR_MERGE_QZ, 86, new byte[]{(byte) 1}, true), new KeycodeEntry(-1, 87), new KeycodeEntry(-1, 96, 0), new KeycodeEntry(-1, 97, 0), new KeycodeEntry(-1, 98, 0), new KeycodeEntry(-1, 99, 0), new KeycodeEntry(-1, 100, 0), new KeycodeEntry(-1, 101, 0), new KeycodeEntry(-1, 102, 0), new KeycodeEntry(-1, 103, 0), new KeycodeEntry(-1, 104, 0), new KeycodeEntry(-1, 105, 0), new KeycodeEntry(-1, 106, 0), new KeycodeEntry(-1, 107, 0), new KeycodeEntry(-1, 108, 0), new KeycodeEntry(-1, 109, 0), new KeycodeEntry(186, 113), new KeycodeEntry(183, 114), new KeycodeEntry(184, 115), new KeycodeEntry(185, 116), new KeycodeEntry(135, 117), new KeycodeEntry(230, 118)};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeycodeEntry {
        public final int mAndroidKeycode;
        public final byte[] mCecKeycodeAndParams;
        public final boolean mIsRepeatable;

        public KeycodeEntry(int i, int i2) {
            this(i, i2, EmptyArray.BYTE, true);
        }

        public KeycodeEntry(int i, int i2, int i3) {
            this(i, i2, EmptyArray.BYTE, false);
        }

        public KeycodeEntry(int i, int i2, byte[] bArr, boolean z) {
            this.mAndroidKeycode = i;
            this.mIsRepeatable = z;
            byte[] bArr2 = new byte[bArr.length + 1];
            this.mCecKeycodeAndParams = bArr2;
            System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
            bArr2[0] = (byte) (i2 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        }
    }

    public static byte[] androidKeyToCecKey(int i) {
        int i2 = 0;
        while (true) {
            KeycodeEntry[] keycodeEntryArr = KEYCODE_ENTRIES;
            if (i2 >= keycodeEntryArr.length) {
                return null;
            }
            KeycodeEntry keycodeEntry = keycodeEntryArr[i2];
            byte[] bArr = keycodeEntry.mAndroidKeycode == i ? keycodeEntry.mCecKeycodeAndParams : null;
            if (bArr != null) {
                return bArr;
            }
            i2++;
        }
    }

    public static boolean isRepeatableKey(int i) {
        int i2 = 0;
        while (true) {
            KeycodeEntry[] keycodeEntryArr = KEYCODE_ENTRIES;
            if (i2 >= keycodeEntryArr.length) {
                return false;
            }
            KeycodeEntry keycodeEntry = keycodeEntryArr[i2];
            Boolean valueOf = keycodeEntry.mAndroidKeycode == i ? Boolean.valueOf(keycodeEntry.mIsRepeatable) : null;
            if (valueOf != null) {
                return valueOf.booleanValue();
            }
            i2++;
        }
    }

    public static boolean isVolumeKeycode(int i) {
        byte b = androidKeyToCecKey(i)[0];
        if (androidKeyToCecKey(i) != null) {
            return b == 65 || b == 66 || b == 67 || b == 101 || b == 102;
        }
        return false;
    }
}
