package com.android.server.display.exynos;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Build;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import java.io.File;
import java.util.function.IntPredicate;

/* loaded from: classes2.dex */
public class ExynosDisplayColor {
  public final boolean DEBUG = "eng".equals(Build.TYPE);
  public String HW_VER_SYSFS_PATH = "/sys/class/dqe/dqe/dqe_ver";
  public String GAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_ext";
  public String GAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/gamma";
  public String DEGAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/degamma_ext";
  public String DEGAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/degamma";
  public String HSC_SYSFS_PATH = "/sys/class/dqe/dqe/hsc";
  public String CGC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc";
  public String CGC17_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_idx";
  public String CGC17_ENC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_enc";
  public String CGC17_DEC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_dec";
  public String CGC17_CON_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_con";
  public String GAMMA_MATRIX_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_matrix";
  public String CGC_DITHER_SYSFS_PATH = "/sys/class/dqe/dqe/cgc_dither";
  public String HSC48_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_idx";
  public String HSC48_LCG_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_lcg";
  public String DE_SYSFS_PATH = "/sys/class/dqe/dqe/de";
  public String COLORTEMP_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_colortemp.xml";
  public String COLORTEMP_EXT_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_colortemp_ext.xml";
  public String EYETEMP_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_eyetemp.xml";
  public String EYETEMP_EXT_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_eyetemp_ext.xml";
  public String BYPASS_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_bypass.xml";
  public String RGBGAIN_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_rgbgain.xml";
  public String RGBGAIN_EXT_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_rgbgain_ext.xml";
  public String SKINCOLOR_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_skincolor.xml";
  public String WHITEPOINT_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_whitepoint.xml";
  public String SHARPNESS_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_sharpness.xml";
  public String COLORMODE_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_colormode0.xml";
  public String EXTENSION_OFF = "0";
  public String EXTENSION_ON = "1";
  public String[] colortemp_array = null;
  public String[] colortemp_ext_array = null;
  public String[] eyetemp_array = null;
  public String[] eyetemp_ext_array = null;
  public String[] gamma_bypass_array = null;
  public String[] gamma_ext_bypass_array = null;
  public String[] cgc_dither_array = null;
  public String[] rgain_array = null;
  public String[] ggain_array = null;
  public String[] bgain_array = null;
  public String[] rgain_ext_array = null;
  public String[] ggain_ext_array = null;
  public String[] bgain_ext_array = null;
  public String[] skincolor_array = null;
  public String[] sharpness_array = null;
  public String[] whitepoint_array = null;
  public String[] hsc_bypass_array = null;
  public float[] rgb_gain = {1.0f, 1.0f, 1.0f};
  public String hw_ver = null;
  public String HW_VER_8_0 = "08000000";
  public ExynosDisplayTune mExynosDisplayTune = null;

  public String getColorEnhancementMode() {
    return "Off,NATIVE,DISPLAY_P3,SRGB";
  }

  public ExynosDisplayColor() {
    checkHWVersion();
  }

  public void setExynosDisplayTune(ExynosDisplayTune exynosDisplayTune) {
    this.mExynosDisplayTune = exynosDisplayTune;
  }

  public final boolean existFile(String str) {
    File file = new File(str);
    return file.exists() && file.isFile();
  }

  public final void checkHWVersion() {
    this.hw_ver = null;
    if (existFile(this.HW_VER_SYSFS_PATH)) {
      this.hw_ver = ExynosDisplayUtils.getStringFromFile(this.HW_VER_SYSFS_PATH);
      if (this.DEBUG) {
        Log.d("ExynosDisplayColor", "hw_ver: " + this.hw_ver);
      }
    }
  }

  public void setColorTempValue(int i) {
    try {
      setGammaValue(this.colortemp_array, this.colortemp_ext_array, i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setColorTempOn(int i) {
    try {
      if (i != 0) {
        this.colortemp_array =
            ExynosDisplayUtils.parserXML(this.COLORTEMP_XML_FILE_PATH, "colortemp", "gamma");
        this.colortemp_ext_array =
            ExynosDisplayUtils.parserXML(this.COLORTEMP_EXT_XML_FILE_PATH, "colortemp", "gamma");
      } else {
        this.colortemp_ext_array = null;
        this.colortemp_array = null;
      }
      setGammaOn(i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void sysfsWriteGamma(String str, String str2) {
    if (str == null || str2 == null) {
      return;
    }
    ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_EXT_SYSFS_PATH, str2);
    ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_SYSFS_PATH, str);
  }

  /* JADX WARN: Removed duplicated region for block: B:15:0x001c  */
  /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setGammaValue(String[] strArr, String[] strArr2, int i) {
    String str;
    if (strArr != null) {
      String str2 = null;
      try {
      } catch (Exception e) {
        e = e;
        str = null;
      }
      if (strArr.length != 0 && i < strArr.length) {
        str = strArr[i];
        if (strArr2 != null) {
          try {
            str2 = strArr2[i];
          } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            if (str == null) {}
          }
        }
        if (str == null) {
          Log.d("ExynosDisplayColor", "setGammaValue()");
          sysfsWriteGamma(str, this.EXTENSION_OFF);
          sysfsWriteGamma(str2, this.EXTENSION_ON);
        }
      }
    }
  }

  public final void setGammaValue(String str, String str2) {
    if (str == null) {
      return;
    }
    if (str2 == null) {
      str2 = null;
    }
    Log.d("ExynosDisplayColor", "setGammaValue()");
    sysfsWriteGamma(str, this.EXTENSION_OFF);
    sysfsWriteGamma(str2, this.EXTENSION_ON);
  }

  /* JADX WARN: Code restructure failed: missing block: B:34:0x006e, code lost:

     android.util.Log.d("ExynosDisplayColor", "setGammaOn()");
  */
  /* JADX WARN: Code restructure failed: missing block: B:35:0x0076, code lost:

     if (r0 == null) goto L42;
  */
  /* JADX WARN: Code restructure failed: missing block: B:36:0x0078, code lost:

     com.android.server.display.exynos.ExynosDisplayUtils.sysfsWriteSting(r6.CGC_DITHER_SYSFS_PATH, r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:37:0x007d, code lost:

     sysfsWriteGamma(r7, r6.EXTENSION_OFF);
     sysfsWriteGamma(r1, r6.EXTENSION_ON);
  */
  /* JADX WARN: Code restructure failed: missing block: B:38:0x0087, code lost:

     return;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setGammaOn(int i) {
    String str;
    String str2;
    String str3;
    String[] strArr;
    String str4 = null;
    try {
      if (this.gamma_bypass_array == null) {
        String[] parserFactoryXMLText =
            ExynosDisplayUtils.parserFactoryXMLText(
                this.BYPASS_XML_FILE_PATH, "bypass", "gamma", 0, 0);
        this.gamma_bypass_array = parserFactoryXMLText;
        this.gamma_ext_bypass_array = null;
        if (parserFactoryXMLText == null) {
          this.gamma_bypass_array =
              ExynosDisplayUtils.parserFactoryXMLText(
                  this.BYPASS_XML_FILE_PATH, "bypass", "gamma", 10, 0);
          this.gamma_ext_bypass_array =
              ExynosDisplayUtils.parserFactoryXMLText(
                  this.BYPASS_XML_FILE_PATH, "bypass", "gamma", 8, 0);
        }
      }
      strArr = this.gamma_bypass_array;
    } catch (Exception e) {
      e = e;
      str = null;
      str2 = null;
    }
    if (strArr != null && strArr.length != 0) {
      if (this.cgc_dither_array == null) {
        this.cgc_dither_array =
            ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "cgc_dither");
      }
      String str5 = this.gamma_bypass_array[0];
      try {
        String[] strArr2 = this.gamma_ext_bypass_array;
        str3 = strArr2 != null ? strArr2[0] : null;
      } catch (Exception e2) {
        str2 = null;
        str = str5;
        e = e2;
      }
      try {
        String[] strArr3 = this.cgc_dither_array;
        if (strArr3 != null && strArr3.length != 0) {
          str4 = strArr3[0];
        }
      } catch (Exception e3) {
        String str6 = str3;
        str = str5;
        e = e3;
        str2 = str6;
        e.printStackTrace();
        str5 = str;
        str3 = str2;
      }
    }
  }

  public void setEyeTempValue(int i) {
    try {
      setGammaValue(this.eyetemp_array, this.eyetemp_ext_array, i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setEyeTempOn(int i) {
    try {
      if (i != 0) {
        this.eyetemp_array =
            ExynosDisplayUtils.parserXML(this.EYETEMP_XML_FILE_PATH, "eyetemp", "gamma");
        this.eyetemp_ext_array =
            ExynosDisplayUtils.parserXML(this.EYETEMP_EXT_XML_FILE_PATH, "eyetemp", "gamma");
      } else {
        this.eyetemp_ext_array = null;
        this.eyetemp_array = null;
      }
      setGammaOn(i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setRgbGainValue(int i, int i2, int i3) {
    String[] strArr;
    String[] strArr2;
    String str;
    String[] strArr3;
    String[] strArr4;
    try {
      String[] strArr5 = this.rgain_array;
      if (strArr5 == null
          || strArr5.length == 0
          || (strArr = this.ggain_array) == null
          || strArr.length == 0
          || (strArr2 = this.bgain_array) == null
          || strArr2.length == 0
          || i >= strArr5.length
          || i2 >= strArr.length
          || i3 >= strArr2.length) {
        return;
      }
      String str2 = this.rgain_array[i] + "," + this.ggain_array[i2] + "," + this.bgain_array[i3];
      String[] strArr6 = this.rgain_ext_array;
      if (strArr6 == null
          || strArr6.length == 0
          || (strArr3 = this.ggain_ext_array) == null
          || strArr3.length == 0
          || (strArr4 = this.bgain_ext_array) == null
          || strArr4.length == 0) {
        str = null;
      } else {
        str =
            this.rgain_ext_array[i]
                + ","
                + this.ggain_ext_array[i2]
                + ","
                + this.bgain_ext_array[i3];
      }
      setGammaValue(str2, str);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setRgbGainOn(int i) {
    try {
      if (i != 0) {
        this.rgain_array =
            ExynosDisplayUtils.parserXML(this.RGBGAIN_XML_FILE_PATH, "rgbgain", "red");
        this.ggain_array =
            ExynosDisplayUtils.parserXML(this.RGBGAIN_XML_FILE_PATH, "rgbgain", "green");
        this.bgain_array =
            ExynosDisplayUtils.parserXML(this.RGBGAIN_XML_FILE_PATH, "rgbgain", "blue");
        this.rgain_ext_array =
            ExynosDisplayUtils.parserXML(this.RGBGAIN_EXT_XML_FILE_PATH, "rgbgain", "red");
        this.ggain_ext_array =
            ExynosDisplayUtils.parserXML(this.RGBGAIN_EXT_XML_FILE_PATH, "rgbgain", "green");
        this.bgain_ext_array =
            ExynosDisplayUtils.parserXML(this.RGBGAIN_EXT_XML_FILE_PATH, "rgbgain", "blue");
      } else {
        this.bgain_array = null;
        this.ggain_array = null;
        this.rgain_array = null;
        this.bgain_ext_array = null;
        this.ggain_ext_array = null;
        this.rgain_ext_array = null;
      }
      setGammaOn(i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setSkinColorOn(int i) {
    String str;
    String[] strArr;
    try {
      if (i != 0) {
        this.skincolor_array =
            ExynosDisplayUtils.parserXML(this.SKINCOLOR_XML_FILE_PATH, "skincolor", "hsc");
      } else {
        this.skincolor_array =
            ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "hsc");
      }
      strArr = this.skincolor_array;
    } catch (Exception e) {
      e.printStackTrace();
      str = null;
    }
    if (strArr != null && strArr.length != 0) {
      str = strArr[0];
      if (str != null) {
        Log.d("ExynosDisplayColor", "setSkinColorOn()");
        ExynosDisplayUtils.sysfsWriteSting(this.HSC_SYSFS_PATH, str);
      }
    }
  }

  public void setHsvGainValue(int i, int i2, int i3) {
    String[] strArr;
    StringBuilder sb = new StringBuilder();
    String str = null;
    try {
      strArr = this.hsc_bypass_array;
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (strArr != null && strArr.length != 0) {
      String[] split = strArr[0].split(",");
      String str2 = this.hw_ver;
      if (str2 == null) {
        split[9] = Integer.toString(1);
        split[10] = Integer.toString(1);
        split[11] = Integer.toString(1);
        split[12] = Integer.toString(i2 - 127);
        split[13] = Integer.toString(i - 127);
        split[14] = Integer.toString(i3 - 127);
        split[146] =
            Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        split[147] =
            Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        split[148] =
            Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        split[149] =
            Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        split[150] =
            Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        split[151] =
            Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        split[152] =
            Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        split[153] =
            Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
      } else if (str2.compareTo(this.HW_VER_8_0) >= 0) {
        split[4] = Integer.toString(1);
        split[5] = Integer.toString(i - 127);
        split[6] = Integer.toString(1);
        split[7] = Integer.toString(i2 - 127);
        split[8] = Integer.toString(1);
        split[9] = Integer.toString(i3 - 127);
        for (int i4 = 49; i4 <= 66; i4++) {
          split[i4] =
              Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        }
        split[49] = Integer.toString(0);
        split[58] = Integer.toString(0);
      } else {
        split[8] = Integer.toString(1);
        split[9] = Integer.toString(i - 127);
        split[10] = Integer.toString(1);
        split[11] = Integer.toString(i2 - 127);
        split[12] = Integer.toString(1);
        split[13] = Integer.toString(i3 - 127);
        for (int i5 = 57; i5 <= 74; i5++) {
          split[i5] =
              Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        }
        split[57] = Integer.toString(0);
        split[66] = Integer.toString(0);
      }
      for (int i6 = 0; i6 < split.length; i6++) {
        sb.append(i6 < split.length - 1 ? split[i6] + "," : split[i6]);
      }
      if (sb.length() > 0) {
        str = sb.toString();
      }
      if (str != null) {
        Log.d("ExynosDisplayColor", "setHsvGainValue()");
        ExynosDisplayUtils.sysfsWriteSting(this.HSC_SYSFS_PATH, str);
      }
    }
  }

  public void setHsvGainOn(int i) {
    String str;
    String[] strArr;
    try {
      if (this.hsc_bypass_array == null) {
        this.hsc_bypass_array =
            ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "hsc");
      }
      strArr = this.hsc_bypass_array;
    } catch (Exception e) {
      e.printStackTrace();
      str = null;
    }
    if (strArr != null && strArr.length != 0) {
      str = strArr[0];
      if (str != null) {
        Log.d("ExynosDisplayColor", "setHsvGainOn()");
        ExynosDisplayUtils.sysfsWriteSting(this.HSC_SYSFS_PATH, str);
      }
    }
  }

  public void setEdgeSharpnessValue(int i) {
    String str;
    String[] strArr;
    try {
      strArr = this.sharpness_array;
    } catch (Exception e) {
      e.printStackTrace();
      str = null;
    }
    if (strArr == null || strArr.length == 0 || i >= strArr.length) {
      return;
    }
    str = strArr[i];
    if (str != null) {
      Log.d("ExynosDisplayColor", "setEdgeSharpnessValue()");
      ExynosDisplayUtils.sysfsWriteSting(this.DE_SYSFS_PATH, str);
    }
  }

  public void setEdgeSharpnessOn(int i) {
    String str;
    String[] strArr;
    try {
      if (i != 0) {
        this.sharpness_array =
            ExynosDisplayUtils.parserXML(this.SHARPNESS_XML_FILE_PATH, "sharpness", "de");
      } else {
        this.sharpness_array =
            ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "de");
      }
      strArr = this.sharpness_array;
    } catch (Exception e) {
      e.printStackTrace();
      str = null;
    }
    if (strArr != null && strArr.length != 0) {
      str = strArr[0];
      if (str != null) {
        Log.d("ExynosDisplayColor", "setEdgeSharpnessOn()");
        ExynosDisplayUtils.sysfsWriteSting(this.DE_SYSFS_PATH, str);
      }
    }
  }

  public final String getColorModePath(String str) {
    String str2 = null;
    try {
      if (str.equals("bypass")) {
        return this.BYPASS_XML_FILE_PATH;
      }
      String pathWithPanel = ExynosDisplayUtils.getPathWithPanel(this.COLORMODE_XML_FILE_PATH);
      try {
        String[] parserFactoryXMLAttribute =
            ExynosDisplayUtils.parserFactoryXMLAttribute(pathWithPanel, str, null, "subxml");
        if (parserFactoryXMLAttribute != null && parserFactoryXMLAttribute.length >= 1) {
          String str3 = this.COLORMODE_XML_FILE_PATH;
          return str3.substring(0, str3.lastIndexOf(".xml"))
              + "_"
              + parserFactoryXMLAttribute[0].split("\\s*,\\s*")[0]
              + ".xml";
        }
        return pathWithPanel;
      } catch (Exception e) {
        e = e;
        str2 = pathWithPanel;
        e.printStackTrace();
        return str2;
      }
    } catch (Exception e2) {
      e = e2;
    }
  }

  public final String getColorModeName(String str) {
    String[] parserFactoryXMLAttribute;
    try {
      if (!str.equals("bypass")
          && (parserFactoryXMLAttribute =
                  ExynosDisplayUtils.parserFactoryXMLAttribute(
                      ExynosDisplayUtils.getPathWithPanel(this.COLORMODE_XML_FILE_PATH),
                      str,
                      null,
                      "subxml"))
              != null) {
        if (parserFactoryXMLAttribute.length >= 1) {
          return "tune";
        }
      }
      return str;
    } catch (Exception e) {
      e.printStackTrace();
      return str;
    }
  }

  public void setColorEnhancement(int i) {
    try {
      setXMLColorModesImpl(i != 1 ? i != 2 ? i != 3 ? "bypass" : "SRGB" : "DISPLAY_P3" : "NATIVE");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:15:0x002a A[Catch: Exception -> 0x009e, TRY_LEAVE, TryCatch #0 {Exception -> 0x009e, blocks: (B:5:0x000d, B:7:0x0011, B:8:0x001b, B:10:0x0020, B:13:0x0024, B:15:0x002a, B:22:0x003b, B:24:0x0043, B:26:0x0047, B:29:0x0072, B:34:0x0079, B:36:0x007c, B:38:0x008b, B:41:0x008f, B:43:0x0097), top: B:4:0x000d }] */
  /* JADX WARN: Removed duplicated region for block: B:19:0x0036  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setWhitePointColorOnCGC17(int i) {
    String str;
    String str2;
    String str3;
    int i2;
    String[] parserFactoryXMLText;
    if (i != 0) {
      str = this.WHITEPOINT_XML_FILE_PATH;
      str2 = "whitepoint";
    } else {
      str = this.BYPASS_XML_FILE_PATH;
      str2 = "bypass";
    }
    try {
      if (this.cgc_dither_array == null) {
        this.cgc_dither_array =
            ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "cgc_dither");
      }
      String[] strArr = this.cgc_dither_array;
      if (strArr != null && strArr.length != 0) {
        str3 = strArr[0];
        if (str3 != null) {
          ExynosDisplayUtils.sysfsWriteSting(this.CGC_DITHER_SYSFS_PATH, str3);
        }
        for (i2 = 0; i2 < 3; i2++) {
          for (int i3 = 0; i3 < 17; i3++) {
            String[] parserFactoryXMLText2 =
                ExynosDisplayUtils.parserFactoryXMLText(str, str2, "cgc17_enc", i2, i3);
            if (parserFactoryXMLText2 != null && parserFactoryXMLText2.length >= 1) {
              String str4 = parserFactoryXMLText2[0];
              ExynosDisplayUtils.sysfsWriteSting(
                  this.CGC17_IDX_SYSFS_PATH, Integer.toString(i2) + " " + Integer.toString(i3));
              ExynosDisplayUtils.sysfsWriteSting(this.CGC17_ENC_SYSFS_PATH, str4);
            }
            Log.d("ExynosDisplayColor", "xml cgc17_enc not found");
            return;
          }
        }
        ExynosDisplayUtils.sysfsWriteSting(this.CGC17_DEC_SYSFS_PATH, "7");
        parserFactoryXMLText =
            ExynosDisplayUtils.parserFactoryXMLText(str, str2, "cgc17_con", 0, 0);
        if (parserFactoryXMLText != null && parserFactoryXMLText.length >= 1) {
          ExynosDisplayUtils.sysfsWriteSting(this.CGC17_CON_SYSFS_PATH, parserFactoryXMLText[0]);
          return;
        }
        Log.d("ExynosDisplayColor", "xml cgc17_con not found");
      }
      str3 = null;
      if (str3 != null) {}
      while (i2 < 3) {}
      ExynosDisplayUtils.sysfsWriteSting(this.CGC17_DEC_SYSFS_PATH, "7");
      parserFactoryXMLText = ExynosDisplayUtils.parserFactoryXMLText(str, str2, "cgc17_con", 0, 0);
      if (parserFactoryXMLText != null) {
        ExynosDisplayUtils.sysfsWriteSting(this.CGC17_CON_SYSFS_PATH, parserFactoryXMLText[0]);
        return;
      }
      Log.d("ExynosDisplayColor", "xml cgc17_con not found");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:26:0x004e, code lost:

     android.util.Log.d("ExynosDisplayColor", "setWhitePointColorOn()");
  */
  /* JADX WARN: Code restructure failed: missing block: B:27:0x0056, code lost:

     if (r2 == null) goto L32;
  */
  /* JADX WARN: Code restructure failed: missing block: B:28:0x0058, code lost:

     com.android.server.display.exynos.ExynosDisplayUtils.sysfsWriteSting(r6.CGC_DITHER_SYSFS_PATH, r2);
  */
  /* JADX WARN: Code restructure failed: missing block: B:29:0x005d, code lost:

     com.android.server.display.exynos.ExynosDisplayUtils.sysfsWriteSting(r6.CGC_SYSFS_PATH, r7);
  */
  /* JADX WARN: Code restructure failed: missing block: B:30:0x0062, code lost:

     return;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void setWhitePointColorOnCGC(int i) {
    String str;
    String[] strArr;
    String str2 = null;
    try {
      if (i != 0) {
        this.whitepoint_array =
            ExynosDisplayUtils.parserXML(this.WHITEPOINT_XML_FILE_PATH, "whitepoint", "cgc");
      } else {
        this.whitepoint_array =
            ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "cgc");
      }
      strArr = this.whitepoint_array;
    } catch (Exception e) {
      e = e;
      str = null;
    }
    if (strArr != null && strArr.length != 0) {
      String str3 = strArr[0];
      try {
        if (this.cgc_dither_array == null) {
          this.cgc_dither_array =
              ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "cgc_dither");
        }
        String[] strArr2 = this.cgc_dither_array;
        if (strArr2 != null && strArr2.length != 0) {
          str2 = strArr2[0];
        }
      } catch (Exception e2) {
        str = str3;
        e = e2;
        e.printStackTrace();
        str3 = str;
      }
    }
  }

  public void setWhitePointColorOn(int i) {
    long currentTimeMillis = System.currentTimeMillis();
    if (this.hw_ver == null) {
      setWhitePointColorOnCGC(i);
    } else {
      setWhitePointColorOnCGC17(i);
    }
    Log.d("ExynosDisplayColor", "elaspedTime: " + (System.currentTimeMillis() - currentTimeMillis));
  }

  /* JADX WARN: Removed duplicated region for block: B:44:0x0148  */
  /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void setRgbGain(float f, float f2, float f3) {
    String str;
    StringBuilder sb;
    int i;
    int i2;
    float[] fArr = this.rgb_gain;
    fArr[0] = f;
    fArr[1] = f2;
    fArr[2] = f3;
    try {
      sb = new StringBuilder();
      String[] parserXML =
          ExynosDisplayUtils.parserXML(this.RGBGAIN_XML_FILE_PATH, "rgbgain", "red");
      this.rgain_array = parserXML;
      int i3 = 65;
      if (parserXML == null || parserXML[0].length() <= 0) {
        i = 65;
      } else {
        i =
            (int)
                this.rgain_array[0]
                    .codePoints()
                    .filter(
                        new IntPredicate() { // from class:
                                             // com.android.server.display.exynos.ExynosDisplayColor$$ExternalSyntheticLambda0
                          @Override // java.util.function.IntPredicate
                          public final boolean test(int i4) {
                            boolean lambda$setRgbGain$0;
                            lambda$setRgbGain$0 = ExynosDisplayColor.lambda$setRgbGain$0(i4);
                            return lambda$setRgbGain$0;
                          }
                        })
                    .count();
        Log.d("ExynosDisplayColor", "setRgbGain(): size=" + i);
      }
      if (i == 66 || i == 65) {
        i3 = i;
      }
      int i4 = i3 * 3;
      int[] iArr = new int[i4];
      String[] strArr = new String[i4];
      if (i3 == 66) {
        int i5 = 0;
        while (i5 < 33) {
          int i6 = i5 * 8;
          iArr[i5] = i6;
          double d = i5;
          iArr[i5 + 33] = (int) Math.round(((f * 255.0f) / 32.0d) * d);
          iArr[i5 + 66] = i6;
          iArr[i5 + 99] = (int) Math.round(((f2 * 255.0f) / 32.0d) * d);
          iArr[i5 + 132] = i6;
          iArr[i5 + FrameworkStatsLog.f643xde3a78eb] =
              (int) Math.round(((f3 * 255.0f) / 32.0d) * d);
          i5++;
          i4 = i4;
        }
        i2 = i4;
        for (int i7 = 0; i7 < i2; i7 += 33) {
          int i8 = 32 + i7;
          iArr[i8] = iArr[i8] - iArr[i8 - 1];
        }
      } else {
        i2 = i4;
        for (int i9 = 0; i9 < i3; i9++) {
          double d2 = i9;
          iArr[i9] = (int) Math.round(((f * 255.0f) / 64.0d) * d2);
          iArr[i9 + 65] = (int) Math.round(((f2 * 255.0f) / 64.0d) * d2);
          iArr[i9 + 130] = (int) Math.round(((f3 * 255.0f) / 64.0d) * d2);
        }
      }
      for (int i10 = 0; i10 < i2; i10++) {
        strArr[i10] = Integer.toString(iArr[i10] * 16);
      }
      if (this.hw_ver != null) {
        sb.append("1,");
      }
      for (int i11 = 0; i11 < i2; i11++) {
        sb.append(i11 < i2 - 1 ? strArr[i11] + "," : strArr[i11]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (sb.length() > 0) {
      str = sb.toString();
      if (str == null) {
        Log.d(
            "ExynosDisplayColor",
            "setRgbGain(): r=" + (f * 255.0f) + ", g=" + (f2 * 255.0f) + ", b=" + (f3 * 255.0f));
        sysfsWriteGamma(str, this.EXTENSION_OFF);
        return;
      }
      return;
    }
    str = null;
    if (str == null) {}
  }

  public static /* synthetic */ boolean lambda$setRgbGain$0(int i) {
    return i == ",".codePointAt(0);
  }

  public float[] getRgbGain() {
    return this.rgb_gain;
  }

  public final void setXMLColorModesImpl(String str) {
    try {
      String colorModePath = getColorModePath(str);
      String colorModeName = getColorModeName(str);
      Log.d(
          "ExynosDisplayColor",
          "setXMLColorModesImpl: xml_path=" + colorModePath + ", mode_name=" + colorModeName);
      ExynosDisplayTune exynosDisplayTune = this.mExynosDisplayTune;
      if (exynosDisplayTune != null) {
        exynosDisplayTune.setCalibrationDQE(colorModePath, colorModeName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setProductXMLColorModes(String str) {
    if (str == null) {
      return;
    }
    setXMLColorModesImpl(str);
  }

  public void setDisplayColorFeature(int i, int i2, String str) {
    Log.d("ExynosDisplayColor", "setDisplayColorFeature(): " + i + "  " + i2 + "  " + str);
    if (i == 0 && i2 == 0 && str != null) {
      setProductXMLColorModes(str);
    }
  }
}
