package com.android.server.display.exynos;

import android.os.Build;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public class ExynosDisplayTune {
  public Timer mTuneTimer;
  public final boolean DEBUG = "eng".equals(Build.TYPE);
  public String GAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_ext";
  public String GAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/gamma";
  public String DEGAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/degamma_ext";
  public String DEGAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/degamma";
  public String HSC_SYSFS_PATH = "/sys/class/dqe/dqe/hsc";
  public String CGC17_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_idx";
  public String CGC17_ENC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_enc";
  public String CGC17_DEC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_dec";
  public String CGC17_CON_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_con";
  public String GAMMA_MATRIX_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_matrix";
  public String CGC_DITHER_SYSFS_PATH = "/sys/class/dqe/dqe/cgc_dither";
  public String HSC48_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_idx";
  public String HSC48_LCG_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_lcg";
  public String SCL_SYSFS_PATH = "/sys/class/dqe/dqe/scl";
  public String DE_SYSFS_PATH = "/sys/class/dqe/dqe/de";
  public String EXTENSION_OFF = "0";
  public String EXTENSION_ON = "1";
  public long mDelayMs = 1000;
  public long mPeriodMs = 1000;
  public String CALIB_DATA_XML_PATH = "/data/dqe/calib_data.xml";

  public final void startTuneTimer() {
    if (this.mTuneTimer == null) {
      Timer timer = new Timer();
      this.mTuneTimer = timer;
      timer.scheduleAtFixedRate(
          new TimerTask() { // from class: com.android.server.display.exynos.ExynosDisplayTune.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
              ExynosDisplayTune exynosDisplayTune = ExynosDisplayTune.this;
              exynosDisplayTune.setCalibrationDQE(exynosDisplayTune.getCalibXMLPath(), "tune");
            }
          },
          this.mDelayMs,
          this.mPeriodMs);
    }
  }

  public final void stopTuneTimer() {
    Timer timer = this.mTuneTimer;
    if (timer != null) {
      timer.cancel();
      this.mTuneTimer = null;
    }
  }

  public void enableTuneTimer(boolean z) {
    if (z) {
      startTuneTimer();
    } else {
      stopTuneTimer();
    }
    Log.d("ExynosDisplayTune", "enableTuneTimer: enable=" + z);
  }

  public final String getCalibXMLPath() {
    return ExynosDisplayUtils.getPathWithPanel(this.CALIB_DATA_XML_PATH);
  }

  public final String getXMLVersion(String str) {
    try {
      String[] parserXMLNodeText = ExynosDisplayUtils.parserXMLNodeText(str, "version");
      if (parserXMLNodeText != null && parserXMLNodeText.length >= 1) {
        String str2 = parserXMLNodeText[0];
        Log.d("ExynosDisplayTune", "xml version: " + str2);
        return str2;
      }
      Log.d("ExynosDisplayTune", "xml version not found");
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public final int getItemEnable(String str, String str2, String str3) {
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, str3, 0, 0);
      if ((parserFactoryXMLText != null && parserFactoryXMLText.length >= 1)
          || ((parserFactoryXMLText =
                      ExynosDisplayUtils.parserFactoryXMLText(str, str2, str3, 10, 0))
                  != null
              && parserFactoryXMLText.length >= 1)) {
        return Integer.parseInt(parserFactoryXMLText[0].split("\\s*,\\s*")[0]);
      }
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public final void sysfsWriteGamma(String str, String str2) {
    if (str == null || str2 == null) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_EXT_SYSFS_PATH, str2);
    ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_SYSFS_PATH, str);
  }

  public final void sysfsWriteDegamma(String str, String str2) {
    if (str == null || str2 == null) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.DEGAMMA_EXT_SYSFS_PATH, str2);
    ExynosDisplayUtils.sysfsWriteSting(this.DEGAMMA_SYSFS_PATH, str);
  }

  enum CalibOrder {
    none(0),
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

    /* renamed from: id */
    private final int f1682id;

    public abstract class CalibCounter {
      public static int nid;
    }

    CalibOrder() {
      this(CalibCounter.nid);
    }

    CalibOrder(CalibOrder calibOrder, int i) {
      this(calibOrder.m26id() + (i - 1));
    }

    CalibOrder(int i) {
      this.f1682id = i;
      CalibCounter.nid = i + 1;
    }

    /* renamed from: id */
    public int m26id() {
      return this.f1682id;
    }
  }

  public void setCalibrationDQE(String str, String str2) {
    String str3;
    int[] iArr;
    String str4;
    boolean z;
    boolean z2;
    boolean z3;
    String str5 = "scl";
    String str6 = "cgc_dither";
    if (ExynosDisplayUtils.existFile(str) && getXMLVersion(str) != null) {
      StringBuilder sb = new StringBuilder();
      sb.append("setCalibrationDQE+ (");
      CalibOrder calibOrder = CalibOrder.max;
      sb.append(calibOrder.m26id());
      sb.append(")");
      Log.d("ExynosDisplayTune", sb.toString());
      int[] iArr2 = new int[calibOrder.m26id()];
      for (int i = 0; i < CalibOrder.max.m26id(); i++) {
        iArr2[i] = 0;
      }
      try {
        int itemEnable = getItemEnable(str, str2, "cgc_dither");
        Log.d("ExynosDisplayTune", "cgc_dither: enable = " + itemEnable);
        if (itemEnable > 0) {
          iArr2[CalibOrder.cgc_dither.m26id()] = 1;
        }
        int itemEnable2 = getItemEnable(str, str2, "degamma");
        Log.d("ExynosDisplayTune", "degamma: enable = " + itemEnable2);
        if (itemEnable2 > 0) {
          iArr2[CalibOrder.degamma.m26id()] = 1;
        }
        int itemEnable3 = getItemEnable(str, str2, "gamma");
        Log.d("ExynosDisplayTune", "gamma: enable = " + itemEnable3);
        if (itemEnable3 > 0) {
          iArr2[CalibOrder.gamma.m26id()] = 1;
        }
        int itemEnable4 = getItemEnable(str, str2, "gamma_matrix");
        Log.d("ExynosDisplayTune", "gamma_matrix: enable = " + itemEnable4);
        if (itemEnable4 > 0) {
          iArr2[CalibOrder.gamma_matrix.m26id()] = 1;
        }
        int itemEnable5 = getItemEnable(str, str2, "hsc");
        Log.d("ExynosDisplayTune", "hsc: enable = " + itemEnable5);
        if (itemEnable5 > 0) {
          for (int m26id = CalibOrder.hsc48_lcg_s.m26id();
              m26id <= CalibOrder.hsc48_lcg_e.m26id();
              m26id++) {
            iArr2[m26id] = 1;
          }
          iArr2[CalibOrder.hsc.m26id()] = 1;
        }
        int itemEnable6 = getItemEnable(str, str2, "scl");
        Log.d("ExynosDisplayTune", "scl: enable = " + itemEnable6);
        if (itemEnable6 > 0) {
          iArr2[CalibOrder.scl.m26id()] = 1;
        }
        int itemEnable7 = getItemEnable(str, str2, "cgc17_con");
        Log.d("ExynosDisplayTune", "cgc17_con: enable = " + itemEnable7);
        if (itemEnable7 > 0) {
          for (int m26id2 = CalibOrder.cgc17_enc_s.m26id();
              m26id2 <= CalibOrder.cgc17_enc_e.m26id();
              m26id2++) {
            iArr2[m26id2] = 1;
          }
          iArr2[CalibOrder.cgc17_dec.m26id()] = 1;
          iArr2[CalibOrder.cgc17_con.m26id()] = 1;
        }
        int itemEnable8 = getItemEnable(str, str2, "de");
        Log.d("ExynosDisplayTune", "de: enable = " + itemEnable8);
        if (itemEnable8 > 0) {
          iArr2[CalibOrder.de.m26id()] = 1;
        }
        int i2 = 0;
        while (i2 < CalibOrder.max.m26id()) {
          int i3 = iArr2[i2];
          if (i2 <= CalibOrder.none.m26id()) {
            str4 = str5;
            str3 = str6;
          } else {
            if (i2 <= CalibOrder.cgc_dither.m26id()) {
              String[] parserFactoryXMLText =
                  ExynosDisplayUtils.parserFactoryXMLText(str, str2, str6, 0, 0);
              if (parserFactoryXMLText != null) {
                str3 = str6;
                if (parserFactoryXMLText.length >= 1) {
                  ExynosDisplayUtils.sysfsWriteSting(
                      this.CGC_DITHER_SYSFS_PATH, parserFactoryXMLText[0]);
                  str4 = str5;
                } else {
                  str4 = str5;
                  iArr = iArr2;
                }
              } else {
                str3 = str6;
                str4 = str5;
                iArr = iArr2;
              }
            } else {
              str3 = str6;
              if (i2 <= CalibOrder.degamma.m26id()) {
                String[] parserFactoryXMLText2 =
                    ExynosDisplayUtils.parserFactoryXMLText(str, str2, "degamma", 0, 0);
                if (parserFactoryXMLText2 != null) {
                  iArr = iArr2;
                  if (parserFactoryXMLText2.length >= 1) {
                    sysfsWriteDegamma(parserFactoryXMLText2[0], this.EXTENSION_OFF);
                  }
                } else {
                  iArr = iArr2;
                }
                String[] parserFactoryXMLText3 =
                    ExynosDisplayUtils.parserFactoryXMLText(str, str2, "degamma", 10, 0);
                if (parserFactoryXMLText3 != null && parserFactoryXMLText3.length >= 1) {
                  sysfsWriteDegamma(parserFactoryXMLText3[0], this.EXTENSION_OFF);
                  String[] parserFactoryXMLText4 =
                      ExynosDisplayUtils.parserFactoryXMLText(str, str2, "degamma", 8, 0);
                  if (parserFactoryXMLText4 != null && parserFactoryXMLText4.length >= 1) {
                    sysfsWriteDegamma(parserFactoryXMLText4[0], this.EXTENSION_ON);
                  }
                }
              } else {
                iArr = iArr2;
                if (i2 <= CalibOrder.gamma.m26id()) {
                  String[] parserFactoryXMLText5 =
                      ExynosDisplayUtils.parserFactoryXMLText(str, str2, "gamma", 0, 0);
                  if (parserFactoryXMLText5 != null && parserFactoryXMLText5.length >= 1) {
                    sysfsWriteGamma(parserFactoryXMLText5[0], this.EXTENSION_OFF);
                  } else {
                    String[] parserFactoryXMLText6 =
                        ExynosDisplayUtils.parserFactoryXMLText(str, str2, "gamma", 10, 0);
                    if (parserFactoryXMLText6 != null && parserFactoryXMLText6.length >= 1) {
                      sysfsWriteGamma(parserFactoryXMLText6[0], this.EXTENSION_OFF);
                      String[] parserFactoryXMLText7 =
                          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "gamma", 8, 0);
                      if (parserFactoryXMLText7 != null && parserFactoryXMLText7.length >= 1) {
                        sysfsWriteGamma(parserFactoryXMLText7[0], this.EXTENSION_ON);
                      }
                    }
                  }
                } else if (i2 <= CalibOrder.gamma_matrix.m26id()) {
                  z2 = false;
                  String[] parserFactoryXMLText8 =
                      ExynosDisplayUtils.parserFactoryXMLText(str, str2, "gamma_matrix", 0, 0);
                  if (parserFactoryXMLText8 != null) {
                    z3 = true;
                    if (parserFactoryXMLText8.length >= 1) {
                      ExynosDisplayUtils.sysfsWriteSting(
                          this.GAMMA_MATRIX_SYSFS_PATH, parserFactoryXMLText8[0]);
                    }
                    str4 = str5;
                  }
                  str4 = str5;
                } else if (i2 <= CalibOrder.hsc48_lcg_e.m26id()) {
                  z = true;
                  if (i3 == 1) {
                    int m26id3 = i2 - CalibOrder.hsc48_lcg_s.m26id();
                    String[] parserFactoryXMLText9 =
                        ExynosDisplayUtils.parserFactoryXMLText(str, str2, "hsc48_lcg", m26id3, 0);
                    if (parserFactoryXMLText9 != null && parserFactoryXMLText9.length >= 1) {
                      String num = Integer.toString(m26id3);
                      String str7 = parserFactoryXMLText9[0];
                      ExynosDisplayUtils.sysfsWriteSting(this.HSC48_IDX_SYSFS_PATH, num);
                      ExynosDisplayUtils.sysfsWriteSting(this.HSC48_LCG_SYSFS_PATH, str7);
                    }
                  }
                  str4 = str5;
                } else if (i2 <= CalibOrder.hsc.m26id()) {
                  z2 = false;
                  String[] parserFactoryXMLText10 =
                      ExynosDisplayUtils.parserFactoryXMLText(str, str2, "hsc", 0, 0);
                  if (parserFactoryXMLText10 != null) {
                    z3 = true;
                    if (parserFactoryXMLText10.length >= 1) {
                      ExynosDisplayUtils.sysfsWriteSting(
                          this.HSC_SYSFS_PATH, parserFactoryXMLText10[0]);
                    }
                    str4 = str5;
                  }
                  str4 = str5;
                } else if (i2 <= CalibOrder.scl.m26id()) {
                  z2 = false;
                  String[] parserFactoryXMLText11 =
                      ExynosDisplayUtils.parserFactoryXMLText(str, str2, str5, 0, 0);
                  if (parserFactoryXMLText11 != null) {
                    z3 = true;
                    if (parserFactoryXMLText11.length >= 1) {
                      ExynosDisplayUtils.sysfsWriteSting(
                          this.SCL_SYSFS_PATH, parserFactoryXMLText11[0]);
                    }
                    str4 = str5;
                  }
                  str4 = str5;
                } else if (i2 > CalibOrder.cgc17_enc_e.m26id()) {
                  str4 = str5;
                  if (i2 > CalibOrder.cgc17_dec.m26id()) {
                    if (i2 <= CalibOrder.cgc17_con.m26id()) {
                      String[] parserFactoryXMLText12 =
                          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "cgc17_con", 0, 0);
                      if (parserFactoryXMLText12 != null) {
                        if (parserFactoryXMLText12.length >= 1) {
                          ExynosDisplayUtils.sysfsWriteSting(
                              this.CGC17_CON_SYSFS_PATH, parserFactoryXMLText12[0]);
                        }
                      }
                    } else {
                      if (i2 > CalibOrder.de.m26id()) {
                        break;
                      }
                      String[] parserFactoryXMLText13 =
                          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "de", 0, 0);
                      if (parserFactoryXMLText13 != null) {
                        if (parserFactoryXMLText13.length >= 1) {
                          ExynosDisplayUtils.sysfsWriteSting(
                              this.DE_SYSFS_PATH, parserFactoryXMLText13[0]);
                        }
                      }
                    }
                  } else if (i3 == 1) {
                    ExynosDisplayUtils.sysfsWriteSting(this.CGC17_DEC_SYSFS_PATH, "7");
                  }
                } else {
                  z = true;
                  if (i3 == 1) {
                    CalibOrder calibOrder2 = CalibOrder.cgc17_enc_s;
                    int m26id4 = (i2 - calibOrder2.m26id()) / 17;
                    int m26id5 = (i2 - calibOrder2.m26id()) % 17;
                    String[] parserFactoryXMLText14 =
                        ExynosDisplayUtils.parserFactoryXMLText(
                            str, str2, "cgc17_enc", m26id4, m26id5);
                    if (parserFactoryXMLText14 != null) {
                      str4 = str5;
                      if (parserFactoryXMLText14.length >= 1) {
                        String str8 = Integer.toString(m26id4) + " " + Integer.toString(m26id5);
                        String str9 = parserFactoryXMLText14[0];
                        ExynosDisplayUtils.sysfsWriteSting(this.CGC17_IDX_SYSFS_PATH, str8);
                        ExynosDisplayUtils.sysfsWriteSting(this.CGC17_ENC_SYSFS_PATH, str9);
                      }
                    }
                  }
                  str4 = str5;
                }
              }
              str4 = str5;
            }
            i2++;
            str6 = str3;
            str5 = str4;
            iArr2 = iArr;
          }
          iArr = iArr2;
          i2++;
          str6 = str3;
          str5 = str4;
          iArr2 = iArr;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      ExynosDisplayUtils.sendEmptyUpdate();
      ExynosDisplayUtils.sendEmptyUpdate();
      Log.d("ExynosDisplayTune", "setCalibrationDQE-");
    }
  }

  public void enableTuneDQE(boolean z) {
    Log.d("ExynosDisplayTune", "enableTuneDQE: enable=" + z);
    if (z) {
      setCalibrationDQE(getCalibXMLPath(), "tune");
    }
  }
}
