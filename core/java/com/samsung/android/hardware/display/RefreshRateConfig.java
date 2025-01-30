package com.samsung.android.hardware.display;

import android.hardware.display.DisplayManagerGlobal;
import android.p009os.SystemProperties;
import android.text.TextUtils;
import android.view.DisplayAddress;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes5.dex */
public final class RefreshRateConfig {
  private static final String PROPERTY_AMBIENT_BRIGHTNESS = "persist.dm.passive.ambient_brightness";
  private static final String PROPERTY_DISPLAY_BRIGHTNESS = "persist.dm.passive.display_brightness";
  private static final String PROPERTY_SUB_AMBIENT_BRIGHTNESS =
      "persist.dm.passive.sub_ambient_brightness";
  private static final String PROPERTY_SUB_DISPLAY_BRIGHTNESS =
      "persist.dm.passive.sub_display_brightness";
  private static final int TYPE_SEAMLESS = 2;
  private static final int TYPE_SEAMLESS_PLUS = 3;
  private static final int TYPE_SWITCHABLE = 1;
  private static RefreshRateConfig sInstance;
  private static boolean sIsSubScreen;
  private static DisplayAddress sPrimaryPhysicalDisplayAddress = null;
  private static RefreshRateConfig sSubInstance;
  private BrightnessThreshold mBrightnessThreshold;
  private final int mDisplayType;
  SupportedRefreshRate mHighSpeedRefreshRates;
  SupportedRefreshRate mNormalSpeedRefreshRates;
  private final boolean mUnsupportedNS;

  public static RefreshRateConfig getInstance() {
    return getInstance(sIsSubScreen);
  }

  public static RefreshRateConfig getInstance(boolean isSubScreen) {
    if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && isSubScreen) {
      if (sSubInstance == null) {
        sSubInstance =
            createRefreshRateConfig(
                "0",
                "",
                "",
                new BrightnessThreshold(
                    "", "", PROPERTY_SUB_DISPLAY_BRIGHTNESS, PROPERTY_SUB_AMBIENT_BRIGHTNESS));
      }
      return sSubInstance;
    }
    if (sInstance == null) {
      sInstance =
          createRefreshRateConfig(
              "2",
              "30,60,120",
              "",
              new BrightnessThreshold(
                  "102", "250", PROPERTY_DISPLAY_BRIGHTNESS, PROPERTY_AMBIENT_BRIGHTNESS));
    }
    return sInstance;
  }

  public BrightnessThreshold getBrightnessThreshold() {
    return this.mBrightnessThreshold;
  }

  private RefreshRateConfig(
      String typeConfig,
      String highSpeedConfig,
      String normalSpeedConfig,
      BrightnessThreshold brightnessThreshold) {
    this.mDisplayType = Integer.parseInt(typeConfig);
    this.mHighSpeedRefreshRates = createSupportedRefreshRate(highSpeedConfig, false);
    this.mNormalSpeedRefreshRates = createSupportedRefreshRate(normalSpeedConfig, true);
    this.mUnsupportedNS = TextUtils.isEmpty(normalSpeedConfig);
    this.mBrightnessThreshold = brightnessThreshold;
  }

  public boolean isSwitchable() {
    return this.mDisplayType == 1;
  }

  public boolean isSeamless() {
    return this.mDisplayType == 2;
  }

  public boolean isSeamlessPlus() {
    return this.mDisplayType == 3;
  }

  public boolean unsupportedNS() {
    return this.mUnsupportedNS;
  }

  public SupportedRefreshRate getNormalSpeedRefreshRates() {
    return this.mNormalSpeedRefreshRates;
  }

  public SupportedRefreshRate getHighSpeedRefreshRates() {
    return this.mHighSpeedRefreshRates;
  }

  private static boolean isInPrimaryDevice(DisplayAddress address) {
    if (sPrimaryPhysicalDisplayAddress == null) {
      sPrimaryPhysicalDisplayAddress =
          DisplayAddress.fromPhysicalDisplayId(
              DisplayManagerGlobal.getInstance().getPrimaryPhysicalDisplayId());
    }
    return address.equals(sPrimaryPhysicalDisplayAddress);
  }

  public static void updateSubScreen(DisplayAddress address) {
    sIsSubScreen = !isInPrimaryDevice(address);
  }

  public static boolean isSubScreen() {
    return sIsSubScreen;
  }

  public static RefreshRateConfig createRefreshRateConfig(
      String typeConfig,
      String highSpeedConfig,
      String normalSpeedConfig,
      BrightnessThreshold brightnessThreshold) {
    return new RefreshRateConfig(
        typeConfig, highSpeedConfig, normalSpeedConfig, brightnessThreshold);
  }

  public SupportedRefreshRate createSupportedRefreshRate(
      String feature, boolean useDefaultRefreshRate) {
    return new SupportedRefreshRate(feature, useDefaultRefreshRate);
  }

  public class SupportedRefreshRate {
    static final int DEFAULT_REFRESH_RATE = 60;
    private int maxRefreshRate;
    private int minRefreshRate;
    private List<Integer> supportedRefreshRateListForPassive;

    private SupportedRefreshRate(String feature, boolean useDefaultRefreshRate) {
      this.minRefreshRate = Integer.MAX_VALUE;
      this.maxRefreshRate = Integer.MIN_VALUE;
      this.supportedRefreshRateListForPassive = new ArrayList();
      if (!TextUtils.isEmpty(feature)) {
        List<Integer> supportedRefreshRates =
            (List)
                Arrays.stream(feature.split(","))
                    .mapToInt(new RefreshRateConfig$BrightnessThreshold$$ExternalSyntheticLambda0())
                    .boxed()
                    .collect(Collectors.toList());
        this.minRefreshRate = ((Integer) Collections.min(supportedRefreshRates)).intValue();
        this.maxRefreshRate = ((Integer) Collections.max(supportedRefreshRates)).intValue();
        this.supportedRefreshRateListForPassive =
            (List)
                supportedRefreshRates.stream()
                    .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.hardware.display.RefreshRateConfig$SupportedRefreshRate$$ExternalSyntheticLambda2
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            boolean lambda$new$0;
                            lambda$new$0 =
                                RefreshRateConfig.SupportedRefreshRate.this.lambda$new$0(
                                    (Integer) obj);
                            return lambda$new$0;
                          }
                        })
                    .collect(Collectors.toList());
        return;
      }
      if (useDefaultRefreshRate) {
        this.maxRefreshRate = 60;
        this.minRefreshRate = 60;
        this.supportedRefreshRateListForPassive.add(60);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(Integer r) {
      return this.maxRefreshRate % r.intValue() == 0;
    }

    public int min() {
      return this.minRefreshRate;
    }

    public int max() {
      return this.maxRefreshRate;
    }

    public int getSupportedRefreshRateForPassive(final int refreshRate) {
      return this.supportedRefreshRateListForPassive.stream()
          .filter(
              new Predicate() { // from class:
                                // com.samsung.android.hardware.display.RefreshRateConfig$SupportedRefreshRate$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  return RefreshRateConfig.SupportedRefreshRate
                      .lambda$getSupportedRefreshRateForPassive$1(refreshRate, (Integer) obj);
                }
              })
          .min(
              new Comparator() { // from class:
                                 // com.samsung.android.hardware.display.RefreshRateConfig$SupportedRefreshRate$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                  return Integer.compare(((Integer) obj).intValue(), ((Integer) obj2).intValue());
                }
              })
          .orElse(Integer.valueOf(refreshRate))
          .intValue();
    }

    static /* synthetic */ boolean lambda$getSupportedRefreshRateForPassive$1(
        int refreshRate, Integer r) {
      return r.intValue() >= refreshRate;
    }
  }

  public static class BrightnessThreshold {
    static final int INVALID = -1;
    String mAmbientBrightnessProperties;
    String mDisplayBrightnessProperties;
    public int mHighAmbientLuxThreshold;
    public int mHighBrightnessThreshold;
    public int mLowAmbientLuxThreshold;
    public int mLowBrightnessThreshold;

    BrightnessThreshold(
        String configBrt, String configLux, String propertyDisplay, String propertyAmbient) {
      int i;
      int i2;
      int i3;
      this.mLowBrightnessThreshold = -1;
      this.mLowAmbientLuxThreshold = -1;
      this.mHighBrightnessThreshold = -1;
      this.mHighAmbientLuxThreshold = -1;
      this.mDisplayBrightnessProperties = null;
      this.mAmbientBrightnessProperties = null;
      String configBrtProperties = SystemProperties.get(propertyDisplay, "");
      if (!TextUtils.isEmpty(configBrtProperties)) {
        configBrt = configBrtProperties;
        this.mDisplayBrightnessProperties = configBrtProperties;
      }
      if (!TextUtils.isEmpty(configBrt)) {
        List<Integer> brightnessThresholds =
            (List)
                Arrays.stream(configBrt.split(","))
                    .mapToInt(new RefreshRateConfig$BrightnessThreshold$$ExternalSyntheticLambda0())
                    .boxed()
                    .collect(Collectors.toList());
        if (brightnessThresholds.isEmpty()) {
          i2 = -1;
        } else {
          i2 = brightnessThresholds.get(0).intValue();
        }
        this.mLowBrightnessThreshold = i2;
        if (brightnessThresholds.size() <= 1) {
          i3 = -1;
        } else {
          i3 = brightnessThresholds.get(1).intValue();
        }
        this.mHighBrightnessThreshold = i3;
      }
      String configLuxProperties = SystemProperties.get(propertyAmbient, "");
      if (!TextUtils.isEmpty(configLuxProperties)) {
        configLux = configLuxProperties;
        this.mAmbientBrightnessProperties = configLuxProperties;
      }
      if (!TextUtils.isEmpty(configLux)) {
        List<Integer> ambientLuxThresholds =
            (List)
                Arrays.stream(configLux.split(","))
                    .mapToInt(new RefreshRateConfig$BrightnessThreshold$$ExternalSyntheticLambda0())
                    .boxed()
                    .collect(Collectors.toList());
        if (ambientLuxThresholds.isEmpty()) {
          i = -1;
        } else {
          i = ambientLuxThresholds.get(0).intValue();
        }
        this.mLowAmbientLuxThreshold = i;
        this.mHighAmbientLuxThreshold =
            ambientLuxThresholds.size() > 1 ? ambientLuxThresholds.get(1).intValue() : -1;
      }
    }
  }

  public static void dump(PrintWriter pw, String prefix, boolean isSubScreen) {
    if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && isSubScreen) {
      pw.println(prefix + "RefreshRateConfigs");
      pw.println(prefix + "  SUB_HFR_MODE: 0");
      pw.println(prefix + "  SUB_HFR_SUPPORTED_REFRESH_RATE: ");
      pw.println(prefix + "  SUB_HFR_SUPPORTED_REFRESH_RATE_NS: ");
      pw.println(prefix + "  SUB_SEAMLESS_BRT: ");
      RefreshRateConfig refreshRateConfig = sSubInstance;
      if (refreshRateConfig != null
          && refreshRateConfig.getBrightnessThreshold().mDisplayBrightnessProperties != null) {
        pw.println(
            prefix
                + "  "
                + PROPERTY_SUB_DISPLAY_BRIGHTNESS
                + ": "
                + sSubInstance.getBrightnessThreshold().mDisplayBrightnessProperties);
      }
      pw.println(prefix + "  SUB_SEAMLESS_LUX: ");
      RefreshRateConfig refreshRateConfig2 = sSubInstance;
      if (refreshRateConfig2 != null
          && refreshRateConfig2.getBrightnessThreshold().mAmbientBrightnessProperties != null) {
        pw.println(
            prefix
                + "  "
                + PROPERTY_SUB_AMBIENT_BRIGHTNESS
                + ": "
                + sSubInstance.getBrightnessThreshold().mAmbientBrightnessProperties);
        return;
      }
      return;
    }
    pw.println(prefix + "RefreshRateConfigs");
    pw.println(prefix + "  HFR_DEFAULT_REFRESH_RATE: 120");
    pw.println(prefix + "  HFR_MODE: 2");
    pw.println(prefix + "  HFR_SUPPORTED_REFRESH_RATE: 30,60,120");
    pw.println(prefix + "  HFR_SUPPORTED_REFRESH_RATE_NS: ");
    pw.println(prefix + "  SEAMLESS_BRT: 102");
    RefreshRateConfig refreshRateConfig3 = sInstance;
    if (refreshRateConfig3 != null
        && refreshRateConfig3.getBrightnessThreshold().mDisplayBrightnessProperties != null) {
      pw.println(
          prefix
              + "  "
              + PROPERTY_DISPLAY_BRIGHTNESS
              + ": "
              + sInstance.getBrightnessThreshold().mDisplayBrightnessProperties);
    }
    pw.println(prefix + "  SEAMLESS_LUX: 250");
    RefreshRateConfig refreshRateConfig4 = sInstance;
    if (refreshRateConfig4 != null
        && refreshRateConfig4.getBrightnessThreshold().mAmbientBrightnessProperties != null) {
      pw.println(
          prefix
              + "  "
              + PROPERTY_AMBIENT_BRIGHTNESS
              + ": "
              + sInstance.getBrightnessThreshold().mAmbientBrightnessProperties);
    }
  }
}
