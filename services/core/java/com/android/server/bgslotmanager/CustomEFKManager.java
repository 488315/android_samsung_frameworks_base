package com.android.server.bgslotmanager;

import android.app.ActivityManager;
import android.os.CountDownTimer;
import android.os.Process;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.am.DynamicHiddenApp;

/* loaded from: classes.dex */
public class CustomEFKManager {
    public boolean isBlockDecEFK;
    public boolean isStillDecEFK;
    public int last_Watermark_EFK;
    public int vDecreaseEFKTime;
    public int vWatermarkScaleGetPerTickTime;
    public int vWatermarkScaleGetTime;
    public int v_watermark_scale;
    public static final long mTotalMemMb = MemInfoGetter.getTotalMemoryMB();
    public static final boolean APP_EFK_DECREASE_BOOST_ENABLE = BgAppPropManager.getSlmkPropertyBool("dec_EFK_enable", "false");
    public static final boolean BOOTING_EFK_BOOST_ENABLE = BgAppPropManager.getSlmkPropertyBool("bEFKb_enable", "false");
    public static final String[] DECEFK_EXCEPT_PROC_ARRAY = {DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE=")};
    public int addBonusEFK = BgAppPropManager.getSlmkPropertyInt("add_bonusEFK", "-1");
    public int v_BonusEFK = BgAppPropManager.getSlmkPropertyInt("v_bonusEFK", "0");
    public int origin_EFK = -1;
    public int v_bonusEFKWhileBoot = BgAppPropManager.getSlmkPropertyInt("v_BootEFK", "204800");
    public int vDecreaseEFK = BgAppPropManager.getSlmkPropertyInt("v_decrease_EFK", "0");

    public CustomEFKManager() {
        int slmkPropertyInt = BgAppPropManager.getSlmkPropertyInt("tm_decrease_EFK", "1000");
        this.vDecreaseEFKTime = slmkPropertyInt;
        this.v_watermark_scale = 0;
        this.vWatermarkScaleGetPerTickTime = slmkPropertyInt;
        this.vWatermarkScaleGetTime = slmkPropertyInt * 5;
        this.last_Watermark_EFK = -1;
        this.isBlockDecEFK = false;
        this.isStillDecEFK = false;
    }

    public final void setEFKBoost() {
        int i = this.origin_EFK;
        if (i != -1) {
            BgAppPropManager.setSystemPropertyString("sys.sysctl.extra_free_kbytes", Integer.toString(i + this.v_bonusEFKWhileBoot));
        }
    }

    public final void restoreEFKAfterBoot() {
        int i = this.origin_EFK;
        if (i != -1) {
            BgAppPropManager.setSystemPropertyString("sys.sysctl.extra_free_kbytes", Integer.toString(i));
        }
    }

    public final void setEFKDecreaseBoost() {
        int i = this.origin_EFK;
        if (i != -1) {
            int i2 = this.vDecreaseEFK;
            if (i2 > 0) {
                BgAppPropManager.setSystemPropertyString("sys.sysctl.extra_free_kbytes", Integer.toString(i2));
            } else {
                BgAppPropManager.setSystemPropertyString("sys.sysctl.extra_free_kbytes", Integer.toString(i / 3));
            }
        }
    }

    public final void restoreWatermarkScaleFactor() {
        int i = this.v_watermark_scale;
        if (i > 10) {
            BgAppPropManager.setSystemPropertyString("sys.sysctl.watermark_scale_factor", Integer.toString(i));
        }
    }

    public final void setWatermarkScaleFactorDecreaseBoost() {
        int i = this.v_watermark_scale;
        int i2 = i / 3 < 10 ? 10 : i / 3;
        if (i > 10) {
            int i3 = this.vDecreaseEFK;
            if (i3 > 0) {
                BgAppPropManager.setSystemPropertyString("sys.sysctl.watermark_scale_factor", Integer.toString(i3));
            } else {
                BgAppPropManager.setSystemPropertyString("sys.sysctl.watermark_scale_factor", Integer.toString(i2));
            }
        }
    }

    public void runBootEFKBoost() {
        if (BOOTING_EFK_BOOST_ENABLE) {
            setEFKBoost();
            new CountDownTimer(180000L, 15000L) { // from class: com.android.server.bgslotmanager.CustomEFKManager.1
                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    if (SystemClock.uptimeMillis() >= 180000) {
                        Slog.i("DynamicHiddenApp_CustomEFKManager", "setEFKBoostRestoreTimer onTick after 3minutes");
                        CustomEFKManager.this.restoreEFKAfterBoot();
                        cancel();
                        return;
                    }
                    Slog.i("DynamicHiddenApp_CustomEFKManager", "setEFKBoostRestoreTimer onTick");
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    Slog.i("DynamicHiddenApp_CustomEFKManager", "setEFKBoostRestoreTimer onfinish");
                    CustomEFKManager.this.restoreEFKAfterBoot();
                }
            }.start();
        }
    }

    public void runDecEFKBoost(String str) {
        if (!APP_EFK_DECREASE_BOOST_ENABLE) {
            return;
        }
        int i = 0;
        while (true) {
            String[] strArr = DECEFK_EXCEPT_PROC_ARRAY;
            if (i < strArr.length) {
                if (strArr[i].equals(str)) {
                    return;
                } else {
                    i++;
                }
            } else {
                if (this.v_watermark_scale > 10) {
                    if (this.isBlockDecEFK) {
                        return;
                    }
                    this.isStillDecEFK = true;
                    setWatermarkScaleFactorDecreaseBoost();
                    int i2 = this.vDecreaseEFKTime;
                    new CountDownTimer(i2, i2) { // from class: com.android.server.bgslotmanager.CustomEFKManager.2
                        @Override // android.os.CountDownTimer
                        public void onTick(long j) {
                        }

                        @Override // android.os.CountDownTimer
                        public void onFinish() {
                            CustomEFKManager.this.restoreWatermarkScaleFactor();
                            CustomEFKManager.this.isStillDecEFK = false;
                        }
                    }.start();
                    return;
                }
                setEFKDecreaseBoost();
                int i3 = this.vDecreaseEFKTime;
                new CountDownTimer(i3, i3) { // from class: com.android.server.bgslotmanager.CustomEFKManager.3
                    @Override // android.os.CountDownTimer
                    public void onTick(long j) {
                    }

                    @Override // android.os.CountDownTimer
                    public void onFinish() {
                        CustomEFKManager.this.restoreEFKAfterBoot();
                    }
                }.start();
                return;
            }
        }
    }

    public int getBonusEFK() {
        int i = (mTotalMemMb < 3072 || ActivityManager.isLowRamDeviceStatic()) ? 0 : (int) (r0 * 1024 * 0.01d);
        int i2 = this.addBonusEFK;
        if (i2 == 1) {
            return (int) (r0 * 1024 * 0.01d);
        }
        if (i2 == 2) {
            return this.v_BonusEFK;
        }
        if (i2 == 0) {
            return 0;
        }
        return i;
    }

    public void setOriginEFKWithSetProperty(int i) {
        this.origin_EFK = i;
        BgAppPropManager.setSystemPropertyString("sys.sysctl.origin_extra_free_kbytes", Integer.toString(i));
    }

    public void setWatermarkBoostFactor(int i) {
        BgAppPropManager.setSystemPropertyString("sys.sysctl.watermark_boost_factor", Integer.toString(i));
    }

    public class WatermarkScaleTimer extends CountDownTimer {
        public int count;
        public int reserve;

        public WatermarkScaleTimer(long j, long j2, int i) {
            super(j, j2);
            this.count = 0;
            this.reserve = i;
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j) {
            int i = CustomEFKManager.this.v_watermark_scale;
            boolean z = true;
            int i2 = this.count + 1;
            this.count = i2;
            if (i2 > 2 && !CustomEFKManager.this.isStillDecEFK) {
                int watermarkScaleInfo = CustomEFKManager.getWatermarkScaleInfo();
                if (i != 0 && CustomEFKManager.this.last_Watermark_EFK != -1 && ((i <= watermarkScaleInfo || CustomEFKManager.this.last_Watermark_EFK <= this.reserve) && (i >= watermarkScaleInfo || CustomEFKManager.this.last_Watermark_EFK >= this.reserve))) {
                    z = false;
                }
                if (z) {
                    CustomEFKManager.this.v_watermark_scale = watermarkScaleInfo;
                    Slog.i("DynamicHiddenApp_CustomEFKManager", "curWatermarkScale : " + CustomEFKManager.this.v_watermark_scale);
                    CustomEFKManager.this.last_Watermark_EFK = this.reserve;
                    CustomEFKManager.this.isBlockDecEFK = false;
                    cancel();
                }
            }
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            CustomEFKManager.this.isBlockDecEFK = false;
        }
    }

    public void getCurWatermarkFactorScale(int i) {
        if (APP_EFK_DECREASE_BOOST_ENABLE) {
            this.isBlockDecEFK = true;
            new WatermarkScaleTimer(this.vWatermarkScaleGetTime, this.vWatermarkScaleGetPerTickTime, i).start();
        }
    }

    public static int getWatermarkScaleInfo() {
        long[] jArr = {0};
        Process.readProcFile("/proc/sys/vm/watermark_scale_factor", new int[]{8224}, null, jArr, null);
        return (int) jArr[0];
    }
}
