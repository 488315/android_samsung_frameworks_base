package com.android.server.display.exynos;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class ExynosDisplayFactory {
  public String APS_SYSFS_PATH;
  public String CGC17_CON_SYSFS_PATH;
  public String CGC17_DEC_SYSFS_PATH;
  public String CGC17_ENC_SYSFS_PATH;
  public String CGC17_IDX_SYSFS_PATH;
  public String CGC_DITHER_SYSFS_PATH;
  public final boolean DEBUG;
  public String DEGAMMA_EXT_SYSFS_PATH;
  public String DEGAMMA_SYSFS_PATH;
  public String DE_SYSFS_PATH;
  public String DQE_COEF_XML_FILE_PATH;
  public String DQE_SYSFS_PATH;
  public String EXTENSION_OFF;
  public String EXTENSION_ON;
  public String GAMMA_EXT_SYSFS_PATH;
  public String GAMMA_MATRIX_SYSFS_PATH;
  public String GAMMA_SYSFS_PATH;
  public String HDR_SYSFS_PATH;
  public String HSC48_IDX_SYSFS_PATH;
  public String HSC48_LCG_SYSFS_PATH;
  public String HSC_SYSFS_PATH;
  public String MODE_IDX_SYSFS_PATH;
  public String SCL_SYSFS_PATH;
  public int[] mColorModeModeIdx;
  public int mColorModeModeIdxDefault;
  public String[] mColorModeSettingTable;
  public Context mContext;
  public int mCountDownTimerCount;
  public int[][] mCountDownTimerTable;
  public CountDownTimer mCountdownTimer;
  public ExynosDisplayATC mExynosDisplayATC;
  public String mFactoryXMLPath;
  public Handler mHandler;
  public int mIntervalMs;
  public Handler mLocalHandler;
  public int mTimeoutMs;

  public ExynosDisplayFactory(Context context) {
    String str = Build.TYPE;
    this.DEBUG = "eng".equals(str) || "userdebug".equals(str);
    this.mCountdownTimer = null;
    this.mTimeoutMs = 800;
    this.mIntervalMs = 40;
    this.mCountDownTimerCount = 0;
    this.DQE_SYSFS_PATH = "/sys/class/dqe/dqe";
    this.HDR_SYSFS_PATH = "/sys/class/dqe/hdr";
    this.MODE_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/mode_idx";
    this.APS_SYSFS_PATH = "/sys/class/dqe/dqe/aps";
    this.GAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_ext";
    this.GAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/gamma";
    this.DEGAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/degamma_ext";
    this.DEGAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/degamma";
    this.HSC_SYSFS_PATH = "/sys/class/dqe/dqe/hsc";
    this.CGC17_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_idx";
    this.CGC17_ENC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_enc";
    this.CGC17_DEC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_dec";
    this.CGC17_CON_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_con";
    this.GAMMA_MATRIX_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_matrix";
    this.CGC_DITHER_SYSFS_PATH = "/sys/class/dqe/dqe/cgc_dither";
    this.HSC48_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_idx";
    this.HSC48_LCG_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_lcg";
    this.SCL_SYSFS_PATH = "/sys/class/dqe/dqe/scl";
    this.DE_SYSFS_PATH = "/sys/class/dqe/dqe/de";
    this.DQE_COEF_XML_FILE_PATH = "/vendor/etc/dqe/DQE_coef_data.xml";
    this.EXTENSION_OFF = "0";
    this.EXTENSION_ON = "1";
    this.mFactoryXMLPath = null;
    this.mExynosDisplayATC = null;
    this.mColorModeModeIdxDefault = 0;
    this.mColorModeModeIdx = new int[] {1, 2};
    this.mColorModeSettingTable = new String[] {"hdr10", "hdr10p"};
    this.mCountDownTimerTable = new int[][] {new int[] {0}, new int[] {0}};
    this.mHandler =
        new Handler() { // from class: com.android.server.display.exynos.ExynosDisplayFactory.2
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
              ExynosDisplayUtils.sendEmptyUpdate();
            } else if (i == 2) {
              ExynosDisplayFactory.this.sysfsWriteCGC17_IDX(message.arg1);
            } else {
              if (i != 3) {
                return;
              }
              ExynosDisplayFactory.this.sysfsWriteCGC17_ENC(message.obj.toString());
            }
          }
        };
    this.mContext = context;
    this.mLocalHandler = new Handler(context.getMainLooper());
    setSysfsPath();
    this.mCountDownTimerTable = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 2, 20);
    for (int i = 0; i < 2; i++) {
      for (int i2 = 0; i2 < 20; i2++) {
        this.mCountDownTimerTable[i][i2] = 0;
      }
    }
    initCountDownTimer();
    this.mFactoryXMLPath = null;
    this.mLocalHandler.postDelayed(
        new Runnable() { // from class: com.android.server.display.exynos.ExynosDisplayFactory.1
          @Override // java.lang.Runnable
          public void run() {
            ExynosDisplayFactory exynosDisplayFactory = ExynosDisplayFactory.this;
            exynosDisplayFactory.startCountDownTimer(exynosDisplayFactory.mFactoryXMLPath);
          }
        },
        0L);
  }

  public void setExynosDisplayATC(ExynosDisplayATC exynosDisplayATC) {
    this.mExynosDisplayATC = exynosDisplayATC;
  }

  public final String sysfsPathReplace(String str) {
    return ExynosDisplayUtils.existPath(this.HDR_SYSFS_PATH)
        ? str.replaceFirst(this.DQE_SYSFS_PATH, this.HDR_SYSFS_PATH)
        : str;
  }

  public final boolean sysfsWriteMODE_IDX(int i) {
    return ExynosDisplayUtils.sysfsWriteSting(this.MODE_IDX_SYSFS_PATH, Integer.toString(i));
  }

  public final void sysfsWriteCGC17_IDX(int i) {
    ExynosDisplayUtils.sysfsWriteSting(
        this.CGC17_IDX_SYSFS_PATH, Integer.toString(i / 17) + " " + Integer.toString(i % 17));
  }

  public final void sysfsWriteCGC17_ENC(String str) {
    ExynosDisplayUtils.sysfsWriteSting(this.CGC17_ENC_SYSFS_PATH, str);
  }

  public final void sysfsWriteCGC17_DEC(String str) {
    ExynosDisplayUtils.sysfsWriteSting(this.CGC17_DEC_SYSFS_PATH, str);
  }

  public final void sysfsWriteCGC17_CON(String str) {
    String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.CGC17_CON_SYSFS_PATH);
    if (stringFromFile == null || stringFromFile.equals("0")) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.CGC17_CON_SYSFS_PATH, str);
  }

  public final void sysfsWriteCGC_DITHER(String str) {
    ExynosDisplayUtils.sysfsWriteSting(this.CGC_DITHER_SYSFS_PATH, str);
  }

  public final void sysfsWriteDEGAMMA(String str, String str2) {
    String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.DEGAMMA_SYSFS_PATH);
    if (stringFromFile == null || stringFromFile.equals("0") || str2 == null) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.DEGAMMA_EXT_SYSFS_PATH, str2);
    ExynosDisplayUtils.sysfsWriteSting(this.DEGAMMA_SYSFS_PATH, str);
  }

  public final void sysfsWriteGAMMA(String str, String str2) {
    String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.GAMMA_SYSFS_PATH);
    if (stringFromFile == null || stringFromFile.equals("0") || str2 == null) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_EXT_SYSFS_PATH, str2);
    ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_SYSFS_PATH, str);
  }

  public final void sysfsWriteGAMMA_MATRIX(String str) {
    String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.GAMMA_MATRIX_SYSFS_PATH);
    if (stringFromFile == null || stringFromFile.equals("0")) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_MATRIX_SYSFS_PATH, str);
  }

  public final void sysfsWriteHSC48_IDX(int i) {
    ExynosDisplayUtils.sysfsWriteSting(this.HSC48_IDX_SYSFS_PATH, Integer.toString(i));
  }

  public final void sysfsWriteHSC48_LCG(String str) {
    ExynosDisplayUtils.sysfsWriteSting(this.HSC48_LCG_SYSFS_PATH, str);
  }

  public final void sysfsWriteHSC(String str) {
    String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.HSC_SYSFS_PATH);
    if (stringFromFile == null || stringFromFile.equals("0")) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.HSC_SYSFS_PATH, str);
  }

  public final void sysfsWriteSCL(String str) {
    String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.SCL_SYSFS_PATH);
    if (stringFromFile == null || stringFromFile.equals("0")) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.SCL_SYSFS_PATH, str);
  }

  public final void sysfsWriteAPS(String str) {
    String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.APS_SYSFS_PATH);
    if (stringFromFile == null || stringFromFile.equals("0")) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.APS_SYSFS_PATH, str);
  }

  public final void sysfsWriteDE(String str) {
    String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.DE_SYSFS_PATH);
    if (stringFromFile == null || stringFromFile.equals("0")) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.DE_SYSFS_PATH, str);
  }

  public final void setSysfsPath() {
    this.MODE_IDX_SYSFS_PATH = sysfsPathReplace(this.MODE_IDX_SYSFS_PATH);
    this.APS_SYSFS_PATH = sysfsPathReplace(this.APS_SYSFS_PATH);
    this.GAMMA_EXT_SYSFS_PATH = sysfsPathReplace(this.GAMMA_EXT_SYSFS_PATH);
    this.GAMMA_SYSFS_PATH = sysfsPathReplace(this.GAMMA_SYSFS_PATH);
    this.DEGAMMA_EXT_SYSFS_PATH = sysfsPathReplace(this.DEGAMMA_EXT_SYSFS_PATH);
    this.DEGAMMA_SYSFS_PATH = sysfsPathReplace(this.DEGAMMA_SYSFS_PATH);
    this.HSC_SYSFS_PATH = sysfsPathReplace(this.HSC_SYSFS_PATH);
    this.CGC17_IDX_SYSFS_PATH = sysfsPathReplace(this.CGC17_IDX_SYSFS_PATH);
    this.CGC17_ENC_SYSFS_PATH = sysfsPathReplace(this.CGC17_ENC_SYSFS_PATH);
    this.CGC17_DEC_SYSFS_PATH = sysfsPathReplace(this.CGC17_DEC_SYSFS_PATH);
    this.CGC17_CON_SYSFS_PATH = sysfsPathReplace(this.CGC17_CON_SYSFS_PATH);
    this.GAMMA_MATRIX_SYSFS_PATH = sysfsPathReplace(this.GAMMA_MATRIX_SYSFS_PATH);
    this.CGC_DITHER_SYSFS_PATH = sysfsPathReplace(this.CGC_DITHER_SYSFS_PATH);
    this.HSC48_IDX_SYSFS_PATH = sysfsPathReplace(this.HSC48_IDX_SYSFS_PATH);
    this.HSC48_LCG_SYSFS_PATH = sysfsPathReplace(this.HSC48_LCG_SYSFS_PATH);
    this.SCL_SYSFS_PATH = sysfsPathReplace(this.SCL_SYSFS_PATH);
    this.DE_SYSFS_PATH = sysfsPathReplace(this.DE_SYSFS_PATH);
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setSysfsPath: " + sysfsPathReplace(this.DQE_SYSFS_PATH));
    }
  }

  public final boolean setCalibrationMODE_IDX(int i) {
    return sysfsWriteMODE_IDX(i);
  }

  public final void setCalibrationCGC17_ENC(String str, String str2, int i) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationCGC17_ENC + " + i);
    }
    for (int i2 = 0; i2 < 17; i2++) {
      try {
        String[] parserFactoryXMLText =
            ExynosDisplayUtils.parserFactoryXMLText(str, str2, "cgc17_enc", i, i2);
        if (parserFactoryXMLText != null && parserFactoryXMLText.length >= 1) {
          String str3 = parserFactoryXMLText[0];
          sysfsWriteCGC17_IDX((i * 17) + i2);
          sysfsWriteCGC17_ENC(str3);
        }
        if (this.DEBUG) {
          Log.d("ExynosDisplayFactory", "xml cgc17_enc not found");
          return;
        }
        return;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationCGC17_ENC - " + i);
    }
  }

  public final void setCalibrationCGC17_DEC() {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationCGC17_DEC");
    }
    sysfsWriteCGC17_DEC("7");
  }

  public final void setCalibrationCGC17_CON(String str, String str2) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationCGC17_CON");
    }
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "cgc17_con", 0, 0);
      if (parserFactoryXMLText == null) {
        if (this.DEBUG) {
          Log.d("ExynosDisplayFactory", "xml cgc17_con not found");
        }
      } else {
        if (parserFactoryXMLText.length < 1) {
          if (this.DEBUG) {
            Log.d(
                "ExynosDisplayFactory",
                "xml cgc17_con array size wrong: " + parserFactoryXMLText.length);
            return;
          }
          return;
        }
        sysfsWriteCGC17_CON(parserFactoryXMLText[0]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setCalibrationCGC_DITHER(String str, String str2) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationCGC_DITHER");
    }
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "cgc_dither", 0, 0);
      if (parserFactoryXMLText == null) {
        Log.d("ExynosDisplayFactory", "xml cgc_dither not found");
        return;
      }
      if (parserFactoryXMLText.length < 1) {
        Log.d(
            "ExynosDisplayFactory",
            "xml cgc_dither array size wrong: " + parserFactoryXMLText.length);
        return;
      }
      sysfsWriteCGC_DITHER(parserFactoryXMLText[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setCalibrationDEGAMMA(String str, String str2) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationDEGAMMA");
    }
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "degamma", 0, 0);
      if (parserFactoryXMLText != null && parserFactoryXMLText.length >= 1) {
        sysfsWriteDEGAMMA(parserFactoryXMLText[0], this.EXTENSION_OFF);
      } else {
        String[] parserFactoryXMLText2 =
            ExynosDisplayUtils.parserFactoryXMLText(str, str2, "degamma", 10, 0);
        if (parserFactoryXMLText2 != null && parserFactoryXMLText2.length >= 1) {
          sysfsWriteDEGAMMA(parserFactoryXMLText2[0], this.EXTENSION_OFF);
          String[] parserFactoryXMLText3 =
              ExynosDisplayUtils.parserFactoryXMLText(str, str2, "degamma", 8, 0);
          if (parserFactoryXMLText3 != null && parserFactoryXMLText3.length >= 1) {
            sysfsWriteDEGAMMA(parserFactoryXMLText3[0], this.EXTENSION_ON);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setCalibrationGAMMA(String str, String str2) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationGAMMA");
    }
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "gamma", 0, 0);
      if (parserFactoryXMLText != null && parserFactoryXMLText.length >= 1) {
        sysfsWriteGAMMA(parserFactoryXMLText[0], this.EXTENSION_OFF);
      } else {
        String[] parserFactoryXMLText2 =
            ExynosDisplayUtils.parserFactoryXMLText(str, str2, "gamma", 10, 0);
        if (parserFactoryXMLText2 != null && parserFactoryXMLText2.length >= 1) {
          sysfsWriteGAMMA(parserFactoryXMLText2[0], this.EXTENSION_OFF);
          String[] parserFactoryXMLText3 =
              ExynosDisplayUtils.parserFactoryXMLText(str, str2, "gamma", 8, 0);
          if (parserFactoryXMLText3 != null && parserFactoryXMLText3.length >= 1) {
            sysfsWriteGAMMA(parserFactoryXMLText3[0], this.EXTENSION_ON);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setCalibrationGAMMA_MATRIX(String str, String str2) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationGAMMA_MATRIX");
    }
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "gamma_matrix", 0, 0);
      if (parserFactoryXMLText == null) {
        Log.d("ExynosDisplayFactory", "xml gamma_matrix not found");
        return;
      }
      if (parserFactoryXMLText.length < 1) {
        Log.d(
            "ExynosDisplayFactory",
            "xml gamma_matrix array size wrong: " + parserFactoryXMLText.length);
        return;
      }
      sysfsWriteGAMMA_MATRIX(parserFactoryXMLText[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setCalibrationHSC48_LCG(String str, String str2, int i) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationHSC48_LCG: " + i);
    }
    for (int i2 = 0; i2 < 3; i2++) {
      try {
        String[] parserFactoryXMLText =
            ExynosDisplayUtils.parserFactoryXMLText(str, str2, "hsc48_lcg", i2, 0);
        if (parserFactoryXMLText != null && parserFactoryXMLText.length >= 1) {
          String str3 = parserFactoryXMLText[0];
          sysfsWriteHSC48_IDX(i2);
          sysfsWriteHSC48_LCG(str3);
        }
        Log.d("ExynosDisplayFactory", "xml hsc48_lcg not found");
        return;
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
    }
  }

  public final void setCalibrationHSC(String str, String str2) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationHSC");
    }
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "hsc", 0, 0);
      if (parserFactoryXMLText == null) {
        Log.d("ExynosDisplayFactory", "xml hsc not found");
        return;
      }
      if (parserFactoryXMLText.length < 1) {
        Log.d("ExynosDisplayFactory", "xml hsc array size wrong: " + parserFactoryXMLText.length);
        return;
      }
      sysfsWriteHSC(parserFactoryXMLText[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setCalibrationSCL(String str, String str2) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationSCL");
    }
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "scl", 0, 0);
      if (parserFactoryXMLText == null) {
        Log.d("ExynosDisplayFactory", "xml scl not found");
        return;
      }
      if (parserFactoryXMLText.length < 1) {
        Log.d("ExynosDisplayFactory", "xml scl array size wrong: " + parserFactoryXMLText.length);
        return;
      }
      sysfsWriteSCL(parserFactoryXMLText[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setCalibrationAPS(String str, String str2) {
    if (this.DEBUG) {
      Log.d("ExynosDisplayFactory", "setCalibrationAPS");
    }
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "aps", 0, 0);
      if (parserFactoryXMLText == null) {
        Log.d("ExynosDisplayFactory", "xml aps not found");
        return;
      }
      if (parserFactoryXMLText.length < 1) {
        Log.d("ExynosDisplayFactory", "xml aps array size wrong: " + parserFactoryXMLText.length);
        return;
      }
      sysfsWriteAPS(parserFactoryXMLText[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setCalibrationDE(String str, String str2) {
    Log.d("ExynosDisplayFactory", "setCalibrationDE");
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, "de", 0, 0);
      if (parserFactoryXMLText == null) {
        Log.d("ExynosDisplayFactory", "xml de not found");
        return;
      }
      if (parserFactoryXMLText.length < 1) {
        Log.d("ExynosDisplayFactory", "xml array size wrong: " + parserFactoryXMLText.length);
        return;
      }
      sysfsWriteDE(parserFactoryXMLText[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void initCountDownTimer() {
    this.mCountdownTimer =
        new CountDownTimer(
            this.mTimeoutMs,
            this
                .mIntervalMs) { // from class:
                                // com.android.server.display.exynos.ExynosDisplayFactory.3
          @Override // android.os.CountDownTimer
          public void onTick(long j) {
            int i = 0;
            if (ExynosDisplayFactory.this.mCountDownTimerCount <= 0) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory.setCalibrationMODE_IDX(
                      exynosDisplayFactory.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory2 = ExynosDisplayFactory.this;
                    exynosDisplayFactory2.setCalibrationAPS(
                        exynosDisplayFactory2.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 1) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory3 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory3.setCalibrationMODE_IDX(
                      exynosDisplayFactory3.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory4 = ExynosDisplayFactory.this;
                    exynosDisplayFactory4.setCalibrationDEGAMMA(
                        exynosDisplayFactory4.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 2) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory5 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory5.setCalibrationMODE_IDX(
                      exynosDisplayFactory5.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory6 = ExynosDisplayFactory.this;
                    exynosDisplayFactory6.setCalibrationGAMMA(
                        exynosDisplayFactory6.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 3) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory7 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory7.setCalibrationMODE_IDX(
                      exynosDisplayFactory7.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory8 = ExynosDisplayFactory.this;
                    exynosDisplayFactory8.setCalibrationGAMMA_MATRIX(
                        exynosDisplayFactory8.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 4) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory9 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory9.setCalibrationMODE_IDX(
                      exynosDisplayFactory9.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory10 = ExynosDisplayFactory.this;
                    exynosDisplayFactory10.setCalibrationHSC48_LCG(
                        exynosDisplayFactory10.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i],
                        ExynosDisplayFactory.this.mCountDownTimerCount - 4);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 5) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory11 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory11.setCalibrationMODE_IDX(
                      exynosDisplayFactory11.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory12 = ExynosDisplayFactory.this;
                    exynosDisplayFactory12.setCalibrationHSC(
                        exynosDisplayFactory12.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 6) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory13 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory13.setCalibrationMODE_IDX(
                      exynosDisplayFactory13.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory14 = ExynosDisplayFactory.this;
                    exynosDisplayFactory14.setCalibrationSCL(
                        exynosDisplayFactory14.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 9) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory15 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory15.setCalibrationMODE_IDX(
                      exynosDisplayFactory15.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory16 = ExynosDisplayFactory.this;
                    exynosDisplayFactory16.setCalibrationCGC17_ENC(
                        exynosDisplayFactory16.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i],
                        ExynosDisplayFactory.this.mCountDownTimerCount - 7);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 10) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory17 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory17.setCalibrationMODE_IDX(
                      exynosDisplayFactory17.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory.this.setCalibrationCGC17_DEC();
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 11) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory18 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory18.setCalibrationMODE_IDX(
                      exynosDisplayFactory18.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory19 = ExynosDisplayFactory.this;
                    exynosDisplayFactory19.setCalibrationCGC17_CON(
                        exynosDisplayFactory19.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 12) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory20 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory20.setCalibrationMODE_IDX(
                      exynosDisplayFactory20.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory21 = ExynosDisplayFactory.this;
                    exynosDisplayFactory21.setCalibrationCGC_DITHER(
                        exynosDisplayFactory21.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 13) {
              while (i < 2) {
                if (ExynosDisplayFactory.this
                        .mCountDownTimerTable[i][ExynosDisplayFactory.this.mCountDownTimerCount]
                    != 0) {
                  ExynosDisplayFactory exynosDisplayFactory22 = ExynosDisplayFactory.this;
                  if (exynosDisplayFactory22.setCalibrationMODE_IDX(
                      exynosDisplayFactory22.mColorModeModeIdx[i])) {
                    ExynosDisplayFactory exynosDisplayFactory23 = ExynosDisplayFactory.this;
                    exynosDisplayFactory23.setCalibrationDE(
                        exynosDisplayFactory23.mFactoryXMLPath,
                        ExynosDisplayFactory.this.mColorModeSettingTable[i]);
                  }
                }
                i++;
              }
            } else if (ExynosDisplayFactory.this.mCountDownTimerCount <= 14) {
              ExynosDisplayFactory exynosDisplayFactory24 = ExynosDisplayFactory.this;
              exynosDisplayFactory24.setCalibrationMODE_IDX(
                  exynosDisplayFactory24.mColorModeModeIdxDefault);
            }
            ExynosDisplayFactory.this.mCountDownTimerCount++;
          }

          @Override // android.os.CountDownTimer
          public void onFinish() {
            if (ExynosDisplayFactory.this.DEBUG) {
              Log.d(
                  "ExynosDisplayFactory",
                  "CountDownTimer finished = " + ExynosDisplayFactory.this.mCountDownTimerCount);
            }
            ExynosDisplayFactory.this.mCountDownTimerCount = 0;
          }
        };
  }

  public final int getItemEnable(String str, String str2, String str3) {
    try {
      String[] parserFactoryXMLText =
          ExynosDisplayUtils.parserFactoryXMLText(str, str2, str3, 0, 0);
      if (parserFactoryXMLText != null && parserFactoryXMLText.length >= 1) {
        return Integer.parseInt(parserFactoryXMLText[0].split("\\s*,\\s*")[0]);
      }
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public void startCountDownTimer(String str) {
    String[] parserXMLNodeText;
    if (str == null) {
      str = ExynosDisplayUtils.getPathWithPanel(this.DQE_COEF_XML_FILE_PATH);
    }
    this.mFactoryXMLPath = str;
    if (ExynosDisplayUtils.existFile(str)) {
      if (this.DEBUG) {
        Log.d("ExynosDisplayFactory", "startCountDownTimer: xml_path=" + str);
      }
      for (int i = 0; i < 2; i++) {
        for (int i2 = 0; i2 < 20; i2++) {
          this.mCountDownTimerTable[i][i2] = 0;
        }
      }
      try {
        parserXMLNodeText = ExynosDisplayUtils.parserXMLNodeText(str, "version");
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (parserXMLNodeText != null && parserXMLNodeText.length >= 1) {
        if (this.DEBUG) {
          Log.d("ExynosDisplayFactory", "xml version: " + parserXMLNodeText[0]);
        }
        for (int i3 = 0; i3 < 2; i3++) {
          String str2 = this.mColorModeSettingTable[i3];
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "mode = " + str2);
          }
          int itemEnable = getItemEnable(str, str2, "aps");
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "aps: enable = " + itemEnable);
          }
          if (itemEnable > 0) {
            this.mCountDownTimerTable[i3][0] = 1;
          }
          int itemEnable2 = getItemEnable(str, str2, "degamma");
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "degamma: enable = " + itemEnable2);
          }
          if (itemEnable2 > 0) {
            this.mCountDownTimerTable[i3][1] = 1;
          }
          int itemEnable3 = getItemEnable(str, str2, "gamma");
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "gamma: enable = " + itemEnable3);
          }
          if (itemEnable3 > 0) {
            this.mCountDownTimerTable[i3][2] = 1;
          }
          int itemEnable4 = getItemEnable(str, str2, "gamma_matrix");
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "gamma_matrix: enable = " + itemEnable4);
          }
          if (itemEnable4 > 0) {
            this.mCountDownTimerTable[i3][3] = 1;
          }
          int itemEnable5 = getItemEnable(str, str2, "hsc");
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "hsc: enable = " + itemEnable5);
          }
          for (int i4 = 4; i4 <= 5; i4++) {
            if (itemEnable5 > 0) {
              this.mCountDownTimerTable[i3][i4] = 1;
            }
          }
          int itemEnable6 = getItemEnable(str, str2, "scl");
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "scl: enable = " + itemEnable6);
          }
          if (itemEnable6 > 0) {
            this.mCountDownTimerTable[i3][6] = 1;
          }
          int itemEnable7 = getItemEnable(str, str2, "cgc17_con");
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "cgc17_con: enable = " + itemEnable7);
          }
          for (int i5 = 7; i5 <= 11; i5++) {
            if (itemEnable7 > 0) {
              this.mCountDownTimerTable[i3][i5] = 1;
            }
          }
          int itemEnable8 = getItemEnable(str, str2, "cgc_dither");
          if (this.DEBUG) {
            Log.d("ExynosDisplayFactory", "cgc_dither: enable = " + itemEnable8);
          }
          if (itemEnable8 > 0) {
            this.mCountDownTimerTable[i3][12] = 1;
          }
          if (getItemEnable(str, str2, "de") > 0) {
            this.mCountDownTimerTable[i3][13] = 1;
          }
          Log.d(
              "ExynosDisplayFactory",
              str2 + " enable " + Arrays.toString(this.mCountDownTimerTable[i3]));
        }
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
          this.mCountDownTimerCount = 0;
          countDownTimer.cancel();
          this.mCountdownTimer.start();
          return;
        }
        return;
      }
      Log.d("ExynosDisplayFactory", "xml version not found");
    }
  }

  public int getCountDownTimerCount() {
    return this.mCountDownTimerCount;
  }
}
