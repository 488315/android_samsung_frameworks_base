package com.samsung.android.wifi;

import android.p009os.Debug;
import android.p009os.Environment;
import android.p009os.SystemProperties;
import com.google.android.mms.pdu.CharacterSets;
import com.samsung.android.feature.SemCscFeature;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/* loaded from: classes6.dex */
public class SemOpBrandingLoader {
  private static final String GBK = "gbk";
  private static final String KSC5601 = "ksc5601";
  private static SemOpBrandingLoader sInstance = null;
  private final String FILE_NAME;
  private final String localeString;
  private String mCharacterSet;
  private final boolean mIsProductDev;
  private long mLastModified = 0;
  private String mNotificationStyle;
  private SemVendor mVendor;

  private SemOpBrandingLoader() {
    String locale = Locale.getDefault().toString();
    this.localeString = locale;
    this.FILE_NAME =
        Environment.getLegacyExternalStorageDirectory().getPath() + "/.opbranding.info";
    this.mIsProductDev = Debug.semIsProductDev();
    this.mVendor = getVendorFromCsc();
    this.mCharacterSet =
        (locale == null || !locale.startsWith("zh"))
            ? CharacterSets.MIMENAME_EUC_KR
            : CharacterSets.MIMENAME_GBK;
    this.mNotificationStyle = SystemProperties.get("ro.csc.sales_code");
  }

  private SemVendor getVendorFromCsc() {
    return getVendor(SemCscFeature.getInstance().getString("CscFeature_Wifi_ConfigOpBranding"));
  }

  private SemVendor getVendor(String value) {
    if (value != null) {
      try {
        return SemVendor.valueOf(value);
      } catch (IllegalArgumentException e) {
      }
    }
    return SemVendor.Unknown;
  }

  public boolean writeVendorToFile(String vendorName) {
    if (this.mIsProductDev) {
      SemVendor vendor2 = getVendor(vendorName);
      this.mVendor = vendor2;
      if (vendor2 != SemVendor.Unknown) {
        File file = new File(this.FILE_NAME);
        try {
          BufferedWriter bw = new BufferedWriter(new FileWriter(file));
          try {
            bw.write(vendorName);
            bw.close();
            return true;
          } finally {
          }
        } catch (IOException e) {
          e.printStackTrace();
          return false;
        }
      }
      return false;
    }
    return false;
  }

  private SemVendor getVendorFromFile() {
    File file = new File(this.FILE_NAME);
    if (file.exists()) {
      long lastModified = file.lastModified();
      if (this.mLastModified == lastModified) {
        return SemVendor.Unknown;
      }
      this.mLastModified = lastModified;
      String valueFromFile = null;
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
          valueFromFile = br.readLine();
          br.close();
        } finally {
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (valueFromFile != null) {
        return getVendor(valueFromFile);
      }
    }
    return getVendorFromCsc();
  }

  public static synchronized SemOpBrandingLoader getInstance() {
    SemOpBrandingLoader semOpBrandingLoader;
    synchronized (SemOpBrandingLoader.class) {
      if (sInstance == null) {
        sInstance = new SemOpBrandingLoader();
      }
      semOpBrandingLoader = sInstance;
    }
    return semOpBrandingLoader;
  }

  void updateFromFile() {
    SemVendor vendor2 = getVendorFromFile();
    if (vendor2 != SemVendor.Unknown) {
      this.mVendor = vendor2;
      String characterSet = null;
      String notificationStyle = null;
      switch (C53621.f3093x40b3619c[vendor2.ordinal()]) {
        case 1:
        case 2:
        case 3:
        case 4:
          characterSet = KSC5601;
          break;
        case 5:
          characterSet = GBK;
          notificationStyle = vendor2.name();
          break;
        case 6:
          notificationStyle = vendor2.name();
          break;
      }
      this.mNotificationStyle = notificationStyle;
      this.mCharacterSet = characterSet;
    }
  }

  /* renamed from: com.samsung.android.wifi.SemOpBrandingLoader$1 */
  static /* synthetic */ class C53621 {

    /* renamed from: $SwitchMap$com$samsung$android$wifi$SemOpBrandingLoader$SemVendor */
    static final /* synthetic */ int[] f3093x40b3619c;

    static {
      int[] iArr = new int[SemVendor.values().length];
      f3093x40b3619c = iArr;
      try {
        iArr[SemVendor.LGU.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f3093x40b3619c[SemVendor.KOO.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        f3093x40b3619c[SemVendor.SKT.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f3093x40b3619c[SemVendor.KTT.ordinal()] = 4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        f3093x40b3619c[SemVendor.CMCC.ordinal()] = 5;
      } catch (NoSuchFieldError e5) {
      }
      try {
        f3093x40b3619c[SemVendor.VZW.ordinal()] = 6;
      } catch (NoSuchFieldError e6) {
      }
    }
  }

  public SemVendor getOpBranding() {
    if (this.mIsProductDev) {
      updateFromFile();
    }
    return this.mVendor;
  }

  public String getSupportCharacterSet() {
    if (this.mIsProductDev) {
      updateFromFile();
    }
    return this.mCharacterSet;
  }

  public String getNotificationStyle() {
    if (this.mIsProductDev) {
      updateFromFile();
    }
    return this.mNotificationStyle;
  }

  public enum SemVendor {
    SKT(1),
    KTT(1),
    LGU(1),
    KOO(1),
    VZW(2),
    ATT(2),
    TMB(2),
    MTR(2),
    TMK(2),
    ATO(5),
    CHA(5),
    AIO(5),
    SingTel(5),
    AIS(5),
    CMCC(3),
    DCM(4),
    Unknown(5);

    public static final int COUNTRY_CHINA = 3;
    public static final int COUNTRY_JAPAN = 4;
    public static final int COUNTRY_KOREA = 1;
    public static final int COUNTRY_OTHERS = 5;
    public static final int COUNTRY_USA = 2;
    private final int country;

    SemVendor(int country) {
      this.country = country;
    }

    public int getCountry() {
      return this.country;
    }
  }
}
