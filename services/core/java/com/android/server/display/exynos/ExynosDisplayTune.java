package com.android.server.display.exynos;

import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import java.util.Timer;
import java.util.TimerTask;

public final class ExynosDisplayTune {
    public String CALIB_DATA_XML_PATH;
    public String CGC17_CON_SYSFS_PATH;
    public String CGC17_DEC_SYSFS_PATH;
    public String CGC17_ENC_SYSFS_PATH;
    public String CGC17_IDX_SYSFS_PATH;
    public String CGC_DITHER_SYSFS_PATH;
    public String DEGAMMA_EXT_SYSFS_PATH;
    public String DEGAMMA_SYSFS_PATH;
    public String DE_SYSFS_PATH;
    public String EXTENSION_OFF;
    public String EXTENSION_ON;
    public String GAMMA_EXT_SYSFS_PATH;
    public String GAMMA_MATRIX_SYSFS_PATH;
    public String GAMMA_SYSFS_PATH;
    public String HSC48_IDX_SYSFS_PATH;
    public String HSC48_LCG_SYSFS_PATH;
    public String HSC_SYSFS_PATH;
    public String SCL_SYSFS_PATH;
    public long mDelayMs;
    public long mPeriodMs;
    public Timer mTuneTimer;

    enum CalibOrder {
        none("none"),
        cgc_dither,
        degamma,
        gamma,
        gamma_matrix,
        hsc48_lcg_s,
        hsc48_lcg_e(hsc48_lcg_s, 3),
        hsc,
        scl,
        cgc17_enc_s,
        cgc17_enc_e(cgc17_enc_s, 51),
        cgc17_dec,
        cgc17_con,
        de,
        max;

        private final int id;

        public abstract class CalibCounter {
            public static int nid;
        }

        CalibOrder(String str) {
            this.id = r2;
            CalibCounter.nid = r2 + 1;
        }

        CalibOrder() {
            this(r2);
        }

        CalibOrder(CalibOrder calibOrder, int i) {
            this(r1);
        }

        public final int id() {
            return this.id;
        }
    }

    public static int getItemEnable(String str, String str2, String str3) {
        try {
            String[] parserFactoryXMLText =
                    ExynosDisplayUtils.parserFactoryXMLText(0, 0, str, str2, str3);
            if (parserFactoryXMLText != null) {
                if (parserFactoryXMLText.length < 1) {}
                return Integer.parseInt(parserFactoryXMLText[0].split("\\s*,\\s*")[0]);
            }
            parserFactoryXMLText = ExynosDisplayUtils.parserFactoryXMLText(10, 0, str, str2, str3);
            if (parserFactoryXMLText != null) {
                if (parserFactoryXMLText.length < 1) {}
                return Integer.parseInt(parserFactoryXMLText[0].split("\\s*,\\s*")[0]);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final void enableTuneTimer(boolean z) {
        if (!z) {
            Timer timer = this.mTuneTimer;
            if (timer != null) {
                timer.cancel();
                this.mTuneTimer = null;
            }
        } else if (this.mTuneTimer == null) {
            Timer timer2 = new Timer();
            this.mTuneTimer = timer2;
            timer2.scheduleAtFixedRate(
                    new TimerTask() { // from class:
                                      // com.android.server.display.exynos.ExynosDisplayTune.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public final void run() {
                            ExynosDisplayTune exynosDisplayTune = ExynosDisplayTune.this;
                            exynosDisplayTune.setCalibrationDQE(
                                    ExynosDisplayUtils.getPathWithPanel(
                                            exynosDisplayTune.CALIB_DATA_XML_PATH),
                                    "tune");
                        }
                    },
                    this.mDelayMs,
                    this.mPeriodMs);
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "enableTuneTimer: enable=", "ExynosDisplayTune", z);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCalibrationDQE(java.lang.String r21, java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 1105
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.exynos.ExynosDisplayTune.setCalibrationDQE(java.lang.String,"
                    + " java.lang.String):void");
    }
}
