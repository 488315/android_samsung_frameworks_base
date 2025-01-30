package com.samsung.android.server.vibrator;

import android.content.Context;
import android.content.res.Resources;
import android.net.util.NetworkConstants;
import android.text.TextUtils;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.SystemService;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;
import com.samsung.android.vibrator.VibRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes2.dex */
public class VibratorHelper {
    public static final String PKG_ACT_ALWAYS = VibRune.DND_EXCEPTION_PACKAGES;
    public static VibratorHelper sInstance;
    public static boolean sIsFrequencySupported;
    public static boolean sIsHapticEngineSupported;
    public static int sMotorType;
    public final HashSet mActAlwaysPackages;
    public final HashMap mIndexDurationHash;
    public final HashMap mVibePatternHash;

    public static String getHapticFeedbackSettingName() {
        return "VIB_FEEDBACK_MAGNITUDE";
    }

    public static String getNotificationVibrationSettingName() {
        return "SEM_VIBRATION_NOTIFICATION_INTENSITY";
    }

    public static String getRingVibrationSettingName() {
        return "VIB_RECVCALL_MAGNITUDE";
    }

    public static String getVibrationTypeToString(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "SEM_SUPPORTED_VIBRATION_NONE" : "SEM_SUPPORTED_VIBRATION_TYPE_D" : "SEM_SUPPORTED_VIBRATION_TYPE_C" : "SEM_SUPPORTED_VIBRATION_TYPE_B" : "SEM_SUPPORTED_VIBRATION_TYPE_A";
    }

    public VibratorHelper() {
        HashSet hashSet = new HashSet();
        this.mActAlwaysPackages = hashSet;
        this.mVibePatternHash = new HashMap();
        this.mIndexDurationHash = new HashMap();
        if (VibRune.SUPPORT_ALWAYS_VIBRATE) {
            hashSet.addAll(Arrays.asList(PKG_ACT_ALWAYS.split(",")));
        }
    }

    public static VibratorHelper getInstance() {
        if (sInstance == null) {
            sInstance = new VibratorHelper();
        }
        return sInstance;
    }

    public static void setIsFrequencySupported(boolean z) {
        sIsFrequencySupported = z;
        Slog.d("VibratorManagerService", "frequency supported is : " + sIsFrequencySupported);
    }

    public static void setIsHapticEngineSupported(boolean z) {
        sIsHapticEngineSupported = z;
        Slog.d("VibratorManagerService", "haptic engine supported is : " + sIsHapticEngineSupported);
    }

    public static void setMotorType(int i) {
        sMotorType = i;
        Slog.d("VibratorManagerService", "vibrator motor type is : " + getMotorTypeToString(sMotorType));
    }

    public static boolean getIsHapticEngineSupported() {
        return sIsHapticEngineSupported;
    }

    public static int getMotorType() {
        return sMotorType;
    }

    public static boolean isDcMotorClickIndex(int i) {
        return getMotorType() == 1 && i == 50124 && VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR;
    }

    public final int[] getIntArray(Resources resources, int i) {
        int i2;
        int[] intArray = resources.getIntArray(i);
        int i3 = 0;
        while (true) {
            i2 = -1;
            if (i3 >= intArray.length) {
                break;
            }
            if (intArray[i3] == -1) {
                i2 = i3 + 1;
                break;
            }
            i3++;
        }
        if (i2 < 0) {
            return null;
        }
        int length = intArray.length - i2;
        int[] iArr = new int[length];
        System.arraycopy(intArray, i2, iArr, 0, length);
        return iArr;
    }

    public final long[] getLongArray(Resources resources, int i) {
        int[] intArray = resources.getIntArray(i);
        int length = intArray.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length && intArray[i3] != -1; i3++) {
            i2++;
        }
        long[] jArr = new long[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            jArr[i4] = intArray[i4];
        }
        return jArr;
    }

    public final long[] initPattern(Resources resources, int i) {
        return getLongArray(resources, i);
    }

    public final int[] initEngineData(Resources resources, int i) {
        int[] intArray = getIntArray(resources, i);
        return intArray == null ? new int[]{-1, -1} : intArray;
    }

    public final int[] initHybridData(Resources resources, int i) {
        return resources.getIntArray(i);
    }

    public final void createPatternInfo(int i, Resources resources, int i2, int i3, int i4) {
        PatternInfo patternInfo = new PatternInfo();
        patternInfo.pattern = initPattern(resources, i2);
        patternInfo.frequency = i3;
        patternInfo.engine = initEngineData(resources, i2);
        patternInfo.duration = i4 + 100;
        patternInfo.scale = 100;
        patternInfo.hybrid = i >= 10000 ? initHybridData(resources, i2) : null;
        this.mVibePatternHash.put(Integer.valueOf(i), patternInfo);
    }

    public final void createIndexDurationInfo(int i, int i2) {
        this.mIndexDurationHash.put(Integer.valueOf(i), Integer.valueOf(i2 + 10));
    }

    public PatternInfo getPatternInfo(int i) {
        return (PatternInfo) this.mVibePatternHash.get(Integer.valueOf(i));
    }

    public int getSupportedPatternSize() {
        return this.mVibePatternHash.size();
    }

    public void loadPatternInfo(Context context) {
        Resources resources = context.getResources();
        createPatternInfo(1, resources, 17236335, 4, 20);
        createPatternInfo(2, resources, 17236336, 4, 30);
        createPatternInfo(3, resources, 17236337, 2, 100);
        createPatternInfo(4, resources, 17236338, 3, 180);
        createPatternInfo(5, resources, 17236339, 1, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_ORGANIZATION_ID);
        createPatternInfo(6, resources, 17236340, 4, 20);
        createPatternInfo(7, resources, 17236341, 2, FrameworkStatsLog.BROADCAST_DELIVERY_EVENT_REPORTED);
        createPatternInfo(8, resources, 17236342, 0, FrameworkStatsLog.CREDENTIAL_MANAGER_AUTH_CLICK_REPORTED);
        createPatternInfo(9, resources, 17236343, 0, NetworkConstants.ETHER_MTU);
        createPatternInfo(10, resources, 17236344, 0, 1000);
        createPatternInfo(11, resources, 17236345, 0, 3500);
        createPatternInfo(12, resources, 17236346, 0, 2000);
        createPatternInfo(13, resources, 17236347, 0, 1600);
        createPatternInfo(14, resources, 17236348, 0, 30);
        createPatternInfo(15, resources, 17236349, 0, 30);
        createPatternInfo(16, resources, 17236350, 0, 30);
        createPatternInfo(17, resources, 17236351, 0, 3000);
        createPatternInfo(18, resources, 17236352, 0, 3100);
        createPatternInfo(19, resources, 17236353, 0, 4700);
        createPatternInfo(20, resources, 17236354, 0, 3100);
        createPatternInfo(21, resources, 17236355, 0, 3260);
        createPatternInfo(22, resources, 17236367, 0, 25);
        createPatternInfo(23, resources, 17236368, 0, 20);
        createPatternInfo(24, resources, 17236369, 0, 20);
        createPatternInfo(25, resources, 17236370, 0, 0);
        createPatternInfo(26, resources, 17236371, 0, 20);
        createPatternInfo(27, resources, 17236372, 0, 140);
        createPatternInfo(28, resources, 17236373, 0, 0);
        createPatternInfo(29, resources, 17236374, 0, 0);
        createPatternInfo(30, resources, 17236375, 0, 0);
        createPatternInfo(31, resources, 17236376, 0, 0);
        createPatternInfo(32, resources, 17236377, 0, 25);
        createPatternInfo(33, resources, 17236378, 0, 20);
        createPatternInfo(34, resources, 17236379, 0, 200);
        createPatternInfo(35, resources, 17236380, 0, 0);
        createPatternInfo(36, resources, 17236381, 0, 0);
        createPatternInfo(37, resources, 17236382, 0, 20);
        createPatternInfo(38, resources, 17236383, 0, 20);
        createPatternInfo(39, resources, 17236384, 0, 30);
        createPatternInfo(40, resources, 17236385, 0, 20);
        createPatternInfo(41, resources, 17236386, 0, 10);
        createPatternInfo(42, resources, 17236387, 0, 20);
        createPatternInfo(43, resources, 17236388, 0, 20);
        createPatternInfo(44, resources, 17236389, 0, 20);
        createPatternInfo(45, resources, 17236390, 0, FrameworkStatsLog.f677xec66d03f);
        createPatternInfo(46, resources, 17236391, 0, 190);
        createPatternInfo(47, resources, 17236392, 0, 140);
        createPatternInfo(48, resources, 17236393, 0, 140);
        createPatternInfo(49, resources, 17236394, 0, 20);
        createPatternInfo(50, resources, 17236395, 0, 10);
        createPatternInfo(51, resources, 17236396, 0, 30);
        createPatternInfo(52, resources, 17236397, 0, 30);
        createPatternInfo(56, resources, 17236398, 0, 2000);
        createPatternInfo(57, resources, 17236399, 0, 2000);
        createPatternInfo(58, resources, 17236400, 0, 2000);
        createPatternInfo(59, resources, 17236401, 0, 2000);
        createPatternInfo(60, resources, 17236402, 0, 0);
        createPatternInfo(84, resources, 17236403, 0, 800);
        createPatternInfo(85, resources, 17236404, 0, 800);
        createPatternInfo(86, resources, 17236405, 0, 800);
        createPatternInfo(87, resources, 17236406, 0, 600);
        createPatternInfo(88, resources, 17236407, 0, 320);
        createPatternInfo(89, resources, 17236408, 0, 650);
        createPatternInfo(90, resources, 17236409, 0, 750);
        createPatternInfo(91, resources, 17236410, 0, 3050);
        createPatternInfo(92, resources, 17236411, 0, 3250);
        createPatternInfo(108, resources, 17236356, 0, 25);
        createPatternInfo(109, resources, 17236357, 0, 25);
        createPatternInfo(110, resources, 17236358, 0, 140);
        createPatternInfo(111, resources, 17236359, 0, 285);
        createPatternInfo(112, resources, 17236360, 0, 525);
        createPatternInfo(113, resources, 17236361, 0, 190);
        createPatternInfo(114, resources, 17236362, 0, SystemService.PHASE_LOCK_SETTINGS_READY);
        createPatternInfo(119, resources, 17236363, 0, 20);
        createPatternInfo(125, resources, 17236364, 0, SystemService.PHASE_ACTIVITY_MANAGER_READY);
        createPatternInfo(126, resources, 17236365, 0, 200);
        createPatternInfo(127, resources, 17236366, 0, 200);
    }

    public void loadIndexDurationInfo() {
        createIndexDurationInfo(64, 1600);
        createIndexDurationInfo(65, 1600);
        createIndexDurationInfo(66, 30);
        createIndexDurationInfo(68, 50);
        createIndexDurationInfo(69, 125);
        createIndexDurationInfo(70, FrameworkStatsLog.f446x58c6c32);
        createIndexDurationInfo(71, 500);
        createIndexDurationInfo(72, 22);
        createIndexDurationInfo(73, 325);
        createIndexDurationInfo(74, 225);
        createIndexDurationInfo(75, 125);
        createIndexDurationInfo(76, 325);
        createIndexDurationInfo(77, 225);
        createIndexDurationInfo(78, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP);
        createIndexDurationInfo(79, 30);
        createIndexDurationInfo(80, 45);
        createIndexDurationInfo(81, 170);
        createIndexDurationInfo(82, 1000);
        createIndexDurationInfo(83, 30);
        createIndexDurationInfo(93, 660);
        createIndexDurationInfo(94, FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_ASTRO);
        createIndexDurationInfo(95, 750);
        createIndexDurationInfo(96, FrameworkStatsLog.AUTH_ENROLL_ACTION_INVOKED);
        createIndexDurationInfo(97, 720);
        createIndexDurationInfo(98, 1015);
        createIndexDurationInfo(99, 500);
        createIndexDurationInfo(101, 3565);
        createIndexDurationInfo(102, 3525);
        createIndexDurationInfo(103, 3610);
        createIndexDurationInfo(104, 3760);
        createIndexDurationInfo(105, 5440);
        createIndexDurationInfo(106, 3910);
        createIndexDurationInfo(107, 3525);
        createIndexDurationInfo(115, 1260);
        createIndexDurationInfo(116, 885);
        createIndexDurationInfo(117, 1850);
        createIndexDurationInfo(118, 2350);
        createIndexDurationInfo(120, 100);
        createIndexDurationInfo(121, 150);
        createIndexDurationInfo(122, 500);
        createIndexDurationInfo(123, 130);
        createIndexDurationInfo(124, 300);
    }

    public static String getMotorTypeToString(int i) {
        String str;
        switch (i) {
            case 0:
                str = "MOTOR_NONE";
                break;
            case 1:
                str = "MOTOR_COIN_DC";
                break;
            case 2:
                str = "MOTOR_LINEAR_0832";
                break;
            case 3:
                str = "MOTOR_COIN_DC_PMIC";
                break;
            case 4:
                str = "MOTOR_COIN_DC_INDEX";
                break;
            case 5:
                str = "MOTOR_LINEAR_INDEX";
                break;
            case 6:
                str = "MOTOR_LINEAR_0832RC";
                break;
            case 7:
                str = "MOTOR_LINEAR_1040";
                break;
            case 8:
                str = "MOTOR_LINEAR_RCVMOT";
                break;
            case 9:
                str = "MOTOR_LINEAR_1030";
                break;
            default:
                str = "";
                break;
        }
        return str + " (" + i + ")";
    }

    public static boolean isColorfulDataFormat(int[] iArr) {
        int length = iArr.length;
        int i = length - 1;
        if (length <= 4 || (i / 4) * 4 != i) {
            for (int i2 = 0; i2 < length; i2++) {
                Slog.e("VibratorManagerService", "isColorfulDataFormat() - wrong format(1) : data[" + i2 + "] = " + iArr[i2]);
            }
            return false;
        }
        if (i == iArr[0]) {
            return true;
        }
        for (int i3 = 0; i3 < length; i3++) {
            Slog.e("VibratorManagerService", "isColorfulDataFormat() - wrong format(2) : data[" + i3 + "] = " + iArr[i3]);
        }
        return false;
    }

    public int getPatternFrequencyByIndex(int i) {
        Integer num;
        int sepIndex = getSepIndex(i);
        PatternInfo patternInfo = getPatternInfo(sepIndex);
        if (patternInfo != null) {
            num = Integer.valueOf(patternInfo.frequency);
        } else {
            Slog.d("VibratorManagerService", "getPatternFrequencyByIndex(), index out of bound, mVibePatternHash:" + getSupportedPatternSize() + ", type:" + i + ", index:" + sepIndex);
            num = null;
        }
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public long[] getPatternByIndex(int i) {
        int sepIndex = getSepIndex(i);
        PatternInfo patternInfo = getPatternInfo(sepIndex);
        if (patternInfo != null) {
            return patternInfo.pattern;
        }
        Slog.d("VibratorManagerService", "getPatternByIndex(), index out of bound, mVibePatternHash:" + getSupportedPatternSize() + ", type:" + i + ", index:" + sepIndex);
        return new long[]{-1, -1};
    }

    public long[] getCustomPattern(int[] iArr) {
        boolean z;
        int i;
        int length = iArr.length;
        if (iArr.length <= 4) {
            return new long[]{-1, -1};
        }
        long[] jArr = new long[((length - 1) / 4) + 1];
        jArr[0] = 0;
        if (iArr[2] != 0) {
            i = 1;
            z = false;
        } else {
            z = true;
            i = 0;
        }
        for (int i2 = 1; i2 < length; i2 += 4) {
            int i3 = i2 + 1;
            if (i3 < length) {
                if (z && iArr[i3] != 0) {
                    i++;
                    z = false;
                } else if (!z && iArr[i3] == 0) {
                    i++;
                    z = true;
                }
                jArr[i] = jArr[i] + iArr[i2];
            }
        }
        int i4 = i + 1;
        long[] jArr2 = new long[i4];
        System.arraycopy(jArr, 0, jArr2, 0, i4);
        return jArr2;
    }

    public int[] getEngineData(int i) {
        int sepIndex = getSepIndex(i);
        PatternInfo patternInfo = getPatternInfo(sepIndex);
        if (patternInfo != null) {
            return patternInfo.engine;
        }
        Slog.d("VibratorManagerService", "getEngineData(), IndexOutOfBoundsException occurred, type:" + i + ", index:" + sepIndex);
        return new int[]{-1, -1};
    }

    public boolean hasEngineData(int i, int[] iArr) {
        if (iArr.length != 2 || iArr[0] >= 0) {
            return true;
        }
        Slog.d("VibratorManagerService", "This " + i + " is not supported for engine.");
        return false;
    }

    public final int getSepIndex(int i) {
        return i - HapticFeedbackConstants.semGetVibrationIndex(0);
    }

    public int getIndexDuration(int i) {
        int sepIndex = getSepIndex(i);
        if (this.mVibePatternHash.containsKey(Integer.valueOf(sepIndex))) {
            return ((PatternInfo) this.mVibePatternHash.get(Integer.valueOf(sepIndex))).duration;
        }
        if (this.mIndexDurationHash.containsKey(Integer.valueOf(sepIndex))) {
            return ((Integer) this.mIndexDurationHash.get(Integer.valueOf(sepIndex))).intValue();
        }
        return 5000;
    }

    public final int getIndexScale(int i) {
        int sepIndex = getSepIndex(i);
        if (getPatternInfo(sepIndex) == null) {
            return 100;
        }
        return getPatternInfo(sepIndex).scale;
    }

    public CommonPatternInfo[] getRamIndexData(int i) {
        Slog.d("VibratorManagerService", "get ram index data");
        return new CommonPatternInfo[]{new CommonPatternInfo(81, getSepIndex(i), getIndexScale(i), getIndexDuration(i), getPatternFrequencyByIndex(i))};
    }

    public CommonPatternInfo[] getResourceIndexData(int i) {
        Slog.d("VibratorManagerService", "get resource index data");
        int[] engineData = getEngineData(i);
        int length = (engineData.length - 1) / 4;
        CommonPatternInfo[] commonPatternInfoArr = new CommonPatternInfo[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 4;
            commonPatternInfoArr[i2] = new CommonPatternInfo(82, 0, engineData[i3 + 2], engineData[i3 + 1], engineData[i3 + 3]);
        }
        return commonPatternInfoArr;
    }

    public CommonPatternInfo[] getHybridIndexData(int i) {
        Slog.d("VibratorManagerService", "get hybrid index data");
        int sepIndex = getSepIndex(i);
        ArrayList arrayList = new ArrayList();
        int[] iArr = getPatternInfo(sepIndex).hybrid;
        if (iArr.length == 0) {
            Slog.d("VibratorManagerService", "index : " + sepIndex + " has not hybrid data");
        }
        for (int i2 : iArr) {
            int i3 = 50024 + i2;
            if (i2 < 0) {
                arrayList.add(new CommonPatternInfo(0, 0, 0, i2 * (-1), 0));
            } else if (SemHapticFeedbackConstants.isRamIndexValid(i3)) {
                arrayList.add(getRamIndexData(i3)[0]);
            } else if (SemHapticFeedbackConstants.isResourceIndexValid(i3)) {
                for (CommonPatternInfo commonPatternInfo : getResourceIndexData(i3)) {
                    arrayList.add(commonPatternInfo);
                }
            }
        }
        CommonPatternInfo[] commonPatternInfoArr = new CommonPatternInfo[arrayList.size()];
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            commonPatternInfoArr[i4] = (CommonPatternInfo) arrayList.get(i4);
        }
        return commonPatternInfoArr;
    }

    public static boolean isDocomoVibration(String str) {
        return TextUtils.equals(str, "com.nttdocomo.android.phonemotion");
    }
}
