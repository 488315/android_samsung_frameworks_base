package com.android.server.chimera;

import android.net.util.NetworkConstants;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ChimeraStrategy {
    public final int mAlwaysRunningProcessQuota;
    public final int mDeviceIdleAppThreshold;
    public final int mDeviceIdleNativeThreshold;
    public long mMemFreeTarget;
    public final int mProtectedCountOnDynamic;
    public int mProtectedCountOnHomeTrigger;
    public int mProtectedCountOnLmkdTrigger;
    public final int mQuickReclaimDefaultThreshold;
    public final SettingRepository mSettingRepository;
    public final SystemRepository mSystemRepository;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class DefaultParameters {
        public static final int[][] sParameters = {
            new int[] {2, 485, 5, 3, 885, 100, 400, 500, 600},
            new int[] {3, 614, 7, 6, 1098, 150, 400, 500, 1000},
            new int[] {4, 630, 11, 8, 1406, 300, 400, 500, NetworkConstants.ETHER_MTU},
            new int[] {6, 1100, 19, 9, 2034, 350, 600, 500, 2048},
            new int[] {8, 2150, 24, 10, 2550, 400, 600, 1024, 2480},
            new int[] {
                12,
                2457,
                28,
                14,
                2857,
                500,
                1024,
                FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM,
                4096
            },
            new int[] {
                16,
                2457,
                28,
                14,
                2857,
                600,
                1024,
                FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM,
                6144
            },
            new int[] {-1, -1, -1, -1, -1, -1}
        };
        public static final float[][] sDynamicParameters = {
            new float[] {2.0f, 3.0f, 5.0f},
            new float[] {3.0f, 3.0f, 7.0f},
            new float[] {4.0f, 6.0f, 10.0f},
            new float[] {6.0f, 7.0f, 13.0f},
            new float[] {8.0f, 8.0f, 16.0f},
            new float[] {12.0f, 14.0f, 16.0f},
            new float[] {16.0f, 14.0f, 17.0f},
            new float[] {-1.0f, -1.0f, -1.0f}
        };
    }

    public ChimeraStrategy(SystemRepository systemRepository, SettingRepository settingRepository) {
        int[][] iArr;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        float f;
        int i6;
        int i7;
        int i8;
        this.mSystemRepository = systemRepository;
        this.mSettingRepository = settingRepository;
        int ramSizeGb = ChimeraCommonUtil.getRamSizeGb();
        int i9 = 0;
        while (true) {
            iArr = DefaultParameters.sParameters;
            i = iArr[i9][0];
            i2 = -1;
            if (i <= 0) {
                i9 = 0;
                i = -1;
                break;
            } else if (i >= ramSizeGb) {
                break;
            } else {
                i9++;
            }
        }
        SystemRepository systemRepository2 = this.mSystemRepository;
        if (i != -1) {
            String format = String.format("ro.slmk.chimera_strategy_%dgb", Integer.valueOf(i));
            String str = SystemProperties.get(format, "");
            systemRepository2.getClass();
            SystemRepository.logDebug(
                    "ChimeraStrategy", "updateDefaultParameters > " + format + " : " + str);
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split(",");
                if (split.length > 3) {
                    for (int i10 = 1; i10 <= 3; i10++) {
                        try {
                            iArr[i9][i10] = Integer.parseInt(split[i10 - 1].trim());
                        } catch (Exception e) {
                            RCPManagerService$$ExternalSyntheticOutline0.m(
                                    e,
                                    new StringBuilder("Error while updating default : "),
                                    "ChimeraStrategy");
                        }
                    }
                } else {
                    Log.e("ChimeraStrategy", "Chimera parameter mismatched");
                }
            }
        }
        int i11 = 0;
        while (true) {
            int[] iArr2 = iArr[i11];
            int i12 = iArr2[0];
            if (i12 <= 0) {
                i3 = -1;
                break;
            } else {
                if (i12 >= ramSizeGb) {
                    i3 = iArr2[1];
                    break;
                }
                i11++;
            }
        }
        this.mMemFreeTarget = i3 * 1024;
        int i13 = 0;
        while (true) {
            int[] iArr3 = iArr[i13];
            int i14 = iArr3[0];
            if (i14 <= 0) {
                i4 = -1;
                break;
            } else {
                if (i14 >= ramSizeGb) {
                    i4 = iArr3[2];
                    break;
                }
                i13++;
            }
        }
        this.mProtectedCountOnLmkdTrigger =
                SystemProperties.getInt("persist.config.chimera.protected_count_on_lmkd", i4);
        int i15 = 0;
        while (true) {
            int[] iArr4 = iArr[i15];
            int i16 = iArr4[0];
            if (i16 <= 0) {
                i5 = -1;
                break;
            } else {
                if (i16 >= ramSizeGb) {
                    i5 = iArr4[3];
                    break;
                }
                i15++;
            }
        }
        int i17 = SystemProperties.getInt("persist.config.chimera.protected_count_on_home", i5);
        this.mProtectedCountOnHomeTrigger = i17;
        if (this.mMemFreeTarget < 0 || i17 < 0 || this.mProtectedCountOnLmkdTrigger < 0) {
            this.mMemFreeTarget = 32768;
            this.mProtectedCountOnHomeTrigger = 32;
            this.mProtectedCountOnLmkdTrigger = 32;
        }
        int i18 = 0;
        while (true) {
            float[] fArr = DefaultParameters.sDynamicParameters[i18];
            float f2 = fArr[0];
            if (f2 <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                f = -1.0f;
                break;
            } else {
                if (f2 >= ramSizeGb) {
                    f = fArr[1];
                    break;
                }
                i18++;
            }
        }
        this.mProtectedCountOnDynamic = (int) f;
        int i19 = 0;
        while (true) {
            int[] iArr5 = iArr[i19];
            int i20 = iArr5[0];
            if (i20 <= 0) {
                i6 = -1;
                break;
            } else {
                if (i20 >= ramSizeGb) {
                    i6 = iArr5[5];
                    break;
                }
                i19++;
            }
        }
        this.mQuickReclaimDefaultThreshold = i6;
        int i21 = 0;
        while (true) {
            int[] iArr6 = iArr[i21];
            int i22 = iArr6[0];
            if (i22 <= 0) {
                i7 = -1;
                break;
            } else {
                if (i22 >= ramSizeGb) {
                    i7 = iArr6[6];
                    break;
                }
                i21++;
            }
        }
        this.mDeviceIdleAppThreshold = i7;
        int i23 = 0;
        while (true) {
            int[] iArr7 = iArr[i23];
            int i24 = iArr7[0];
            if (i24 <= 0) {
                i8 = -1;
                break;
            } else {
                if (i24 >= ramSizeGb) {
                    i8 = iArr7[7];
                    break;
                }
                i23++;
            }
        }
        this.mDeviceIdleNativeThreshold = i8;
        int i25 = 0;
        while (true) {
            int[] iArr8 = iArr[i25];
            int i26 = iArr8[0];
            if (i26 <= 0) {
                break;
            }
            if (i26 >= ramSizeGb) {
                i2 = iArr8[8];
                break;
            }
            i25++;
        }
        this.mAlwaysRunningProcessQuota = i2;
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        ramSizeGb, "ChimeraStrategy() - ramSizeGb: ", " mMemFreeTarget: ");
        m.append(this.mMemFreeTarget);
        m.append(" mProtectedCountOnLmkdTrigger: ");
        m.append(this.mProtectedCountOnLmkdTrigger);
        m.append(" mProtectedCountOnHomeTrigger: ");
        m.append(this.mProtectedCountOnHomeTrigger);
        m.append(" mProtectedCountOnDynamic: ");
        m.append(this.mProtectedCountOnDynamic);
        m.append(" mQuickReclaimDefaultThreshold: ");
        m.append(this.mQuickReclaimDefaultThreshold);
        m.append(" mDeviceIdleAppThreshold: ");
        m.append(this.mDeviceIdleAppThreshold);
        m.append(" mDeviceIdleNativeThreshold: ");
        m.append(this.mDeviceIdleNativeThreshold);
        m.append(" mAlwaysRunningProcessQuota: ");
        m.append(this.mAlwaysRunningProcessQuota);
        String sb = m.toString();
        systemRepository2.getClass();
        SystemRepository.logDebug("ChimeraStrategy", sb);
    }

    public final void dumpInfo(PrintWriter printWriter) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("MemFreeTarget: "),
                                this.mMemFreeTarget,
                                printWriter,
                                "ProtectedCountOnLmkdTrigger: "),
                        this.mProtectedCountOnLmkdTrigger,
                        printWriter,
                        "ProtectedCountOnHomeTrigger: "),
                this.mProtectedCountOnHomeTrigger,
                printWriter);
    }
}
