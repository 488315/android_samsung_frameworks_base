package android.view;

import android.app.ActivityThread;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.OverlayProperties;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DeviceProductInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.SystemClock;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class Display {
  private static final int CACHED_APP_SIZE_DURATION_MILLIS = 20;
  public static final int CARLIFE_DISPLAY_GROUP = 4;
  public static final int COLOR_MODE_ADOBE_RGB = 8;
  public static final int COLOR_MODE_BT601_525 = 3;
  public static final int COLOR_MODE_BT601_525_UNADJUSTED = 4;
  public static final int COLOR_MODE_BT601_625 = 1;
  public static final int COLOR_MODE_BT601_625_UNADJUSTED = 2;
  public static final int COLOR_MODE_BT709 = 5;
  public static final int COLOR_MODE_DCI_P3 = 6;
  public static final int COLOR_MODE_DEFAULT = 0;
  public static final int COLOR_MODE_DISPLAY_P3 = 9;
  public static final int COLOR_MODE_INVALID = -1;
  public static final int COLOR_MODE_SRGB = 7;
  private static final boolean DEBUG = false;
  public static final int DEFAULT_DISPLAY = 0;
  public static final int DEFAULT_DISPLAY_GROUP = 0;
  public static final int DEX_DISPLAY = 2;
  public static final int DEX_DISPLAY_GROUP = 2;
  public static final int DISPLAY_MODE_ID_FOR_FRAME_RATE_OVERRIDE = 255;
  public static final int EXTRA_BUILT_IN_DISPLAY = 1;
  public static final int FLAG_ALWAYS_UNLOCKED = 512;
  public static final int FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD = 32;
  public static final int FLAG_CARLIFE_DISPLAY = 1048576;
  public static final int FLAG_EXTRA_BUILT_IN_DISPLAY = 262144;
  public static final int FLAG_HIDDEN_SPACE_DISPLAY = 16384;
  public static final int FLAG_NO_LOCK_PRESENTATION = 536870912;
  public static final int FLAG_OWN_DISPLAY_GROUP = 256;
  public static final int FLAG_OWN_FOCUS = 2048;
  public static final int FLAG_PC_DEX_DISPLAY = 134217728;
  public static final int FLAG_PRESENTATION = 8;
  public static final int FLAG_PRIVATE = 4;
  public static final int FLAG_REAR = 8192;
  public static final int FLAG_REMOTE_APP_DISPLAY = 33554432;
  public static final int FLAG_ROUND = 16;
  public static final int FLAG_SCALING_DISABLED = 1073741824;
  public static final int FLAG_SECURE = 2;
  public static final int FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 64;
  public static final int FLAG_SPEG_DISPLAY = 32768;
  public static final int FLAG_STEAL_TOP_FOCUS_DISABLED = 4096;
  public static final int FLAG_SUPPORTS_PROTECTED_BUFFERS = 1;
  public static final int FLAG_TOUCH_FEEDBACK_DISABLED = 1024;
  public static final int FLAG_TRUSTED = 128;
  public static final int FLAG_VIEW_COVER_DISPLAY = 524288;
  public static final int FLAG_WIFI_DISPLAY = 268435456;
  public static final int FLAG_WIRELESS_DEX_DISPLAY = 67108864;
  public static final int INVALID_DISPLAY = -1;
  public static final int INVALID_DISPLAY_GROUP = -1;
  public static final int INVALID_DISPLAY_HEIGHT = -1;
  public static final float INVALID_DISPLAY_REFRESH_RATE = 0.0f;
  public static final int INVALID_DISPLAY_WIDTH = -1;
  public static final int MAX_STATIC_DISPLAY = 4;
  public static final int MAX_STATIC_DISPLAY_GROUP = 4;
  public static final int REMOVE_MODE_DESTROY_CONTENT = 1;
  public static final int REMOVE_MODE_MOVE_CONTENT_TO_PRIMARY = 0;
  public static final int SEM_TYPE_EXTERNAL = 2;
  public static final int STATE_DOZE = 3;
  public static final int STATE_DOZE_SUSPEND = 4;
  public static final int STATE_OFF = 1;
  public static final int STATE_ON = 2;
  public static final int STATE_ON_SUSPEND = 6;
  public static final int STATE_UNKNOWN = 0;
  public static final int STATE_VR = 5;
  private static final String TAG = "Display";
  public static final int TYPE_EXTERNAL = 2;
  public static final int TYPE_INTERNAL = 1;
  public static final int TYPE_OVERLAY = 4;
  public static final int TYPE_UNKNOWN = 0;
  public static final int TYPE_VIRTUAL = 5;
  public static final int TYPE_WIFI = 3;
  public static final int VIEW_COVER_DISPLAY = 4;
  private int mCachedAppHeightCompat;
  private int mCachedAppWidthCompat;
  private DisplayAdjustments mDisplayAdjustments;
  private final int mDisplayId;
  private DisplayInfo mDisplayInfo;
  private final int mFlags;
  private final DisplayManagerGlobal mGlobal;
  private ArrayList<HdrSdrRatioListenerWrapper> mHdrSdrRatioListeners;
  private boolean mIsValid;
  private long mLastCachedAppSizeUpdate;
  private final Object mLock;
  private final String mOwnerPackageName;
  private final int mOwnerUid;
  private final Resources mResources;
  private final DisplayMetrics mTempMetrics;
  private final int mType;

  @Retention(RetentionPolicy.SOURCE)
  public @interface ColorMode {}

  public Display(
      DisplayManagerGlobal global, int displayId, DisplayInfo displayInfo, DisplayAdjustments daj) {
    this(global, displayId, displayInfo, daj, null);
  }

  public Display(
      DisplayManagerGlobal global, int displayId, DisplayInfo displayInfo, Resources res) {
    this(global, displayId, displayInfo, null, res);
  }

  private Display(
      DisplayManagerGlobal global,
      int displayId,
      DisplayInfo displayInfo,
      DisplayAdjustments daj,
      Resources res) {
    DisplayAdjustments displayAdjustments;
    this.mLock = new Object();
    this.mTempMetrics = new DisplayMetrics();
    this.mHdrSdrRatioListeners = new ArrayList<>();
    this.mGlobal = global;
    this.mDisplayId = displayId;
    this.mDisplayInfo = displayInfo;
    this.mResources = res;
    if (res != null) {
      displayAdjustments = new DisplayAdjustments(res.getConfiguration());
    } else {
      displayAdjustments = daj != null ? new DisplayAdjustments(daj) : new DisplayAdjustments();
    }
    this.mDisplayAdjustments = displayAdjustments;
    this.mIsValid = true;
    this.mFlags = displayInfo.flags;
    this.mType = displayInfo.type;
    this.mOwnerUid = displayInfo.ownerUid;
    this.mOwnerPackageName = displayInfo.ownerPackageName;
  }

  public int getDisplayId() {
    return this.mDisplayId;
  }

  public String getUniqueId() {
    return this.mDisplayInfo.uniqueId;
  }

  public boolean isValid() {
    boolean z;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      z = this.mIsValid;
    }
    return z;
  }

  public boolean getDisplayInfo(DisplayInfo outDisplayInfo) {
    boolean z;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      outDisplayInfo.copyFrom(this.mDisplayInfo);
      z = this.mIsValid;
    }
    return z;
  }

  public int getLayerStack() {
    int i;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      i = this.mDisplayInfo.layerStack;
    }
    return i;
  }

  public int getFlags() {
    return this.mFlags;
  }

  public int getType() {
    return this.mType;
  }

  public int semGetType() {
    return getType();
  }

  public DisplayAddress getAddress() {
    DisplayAddress displayAddress;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      displayAddress = this.mDisplayInfo.address;
    }
    return displayAddress;
  }

  public int getOwnerUid() {
    return this.mOwnerUid;
  }

  public String getOwnerPackageName() {
    return this.mOwnerPackageName;
  }

  public DisplayAdjustments getDisplayAdjustments() {
    Resources resources = this.mResources;
    if (resources != null) {
      DisplayAdjustments currentAdjustments = resources.getDisplayAdjustments();
      if (!this.mDisplayAdjustments.equals(currentAdjustments)) {
        this.mDisplayAdjustments = new DisplayAdjustments(currentAdjustments);
      }
    }
    return this.mDisplayAdjustments;
  }

  public String getName() {
    String str;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      str = this.mDisplayInfo.name;
    }
    return str;
  }

  public float getBrightnessDefault() {
    float f;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      f = this.mDisplayInfo.brightnessDefault;
    }
    return f;
  }

  public BrightnessInfo getBrightnessInfo() {
    return this.mGlobal.getBrightnessInfo(this.mDisplayId);
  }

  @Deprecated
  public void getSize(Point outSize) {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
      outSize.f85x = this.mTempMetrics.widthPixels;
      outSize.f86y = this.mTempMetrics.heightPixels;
    }
  }

  @Deprecated
  public void getRectSize(Rect outSize) {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
      outSize.set(0, 0, this.mTempMetrics.widthPixels, this.mTempMetrics.heightPixels);
    }
  }

  public void getCurrentSizeRange(Point outSmallestSize, Point outLargestSize) {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      outSmallestSize.f85x = this.mDisplayInfo.smallestNominalAppWidth;
      outSmallestSize.f86y = this.mDisplayInfo.smallestNominalAppHeight;
      outLargestSize.f85x = this.mDisplayInfo.largestNominalAppWidth;
      outLargestSize.f86y = this.mDisplayInfo.largestNominalAppHeight;
    }
  }

  public int getMaximumSizeDimension() {
    int max;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      max = Math.max(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
    }
    return max;
  }

  @Deprecated
  public int getWidth() {
    int i;
    synchronized (this.mLock) {
      updateCachedAppSizeIfNeededLocked();
      i = this.mCachedAppWidthCompat;
    }
    return i;
  }

  @Deprecated
  public int getHeight() {
    int i;
    synchronized (this.mLock) {
      updateCachedAppSizeIfNeededLocked();
      i = this.mCachedAppHeightCompat;
    }
    return i;
  }

  public int getRotation() {
    int localRotation;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      localRotation = getLocalRotation();
    }
    return localRotation;
  }

  public int getInstallOrientation() {
    int i;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      i = this.mDisplayInfo.installOrientation;
    }
    return i;
  }

  public int getRealRotation() {
    int i;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      i = this.mDisplayInfo.rotation;
    }
    return i;
  }

  @Deprecated
  public int getOrientation() {
    return getRotation();
  }

  public DisplayCutout getCutout() {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      if (this.mResources == null) {
        return this.mDisplayInfo.displayCutout;
      }
      DisplayCutout localCutout = this.mDisplayInfo.displayCutout;
      if (localCutout == null) {
        return null;
      }
      int rotation = getLocalRotation();
      if (rotation == this.mDisplayInfo.rotation) {
        return localCutout;
      }
      return localCutout.getRotated(
          this.mDisplayInfo.logicalWidth,
          this.mDisplayInfo.logicalHeight,
          this.mDisplayInfo.rotation,
          rotation);
    }
  }

  public RoundedCorner getRoundedCorner(int position) {
    RoundedCorner roundedCorner;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      RoundedCorners roundedCorners = this.mDisplayInfo.roundedCorners;
      int rotation = getLocalRotation();
      if (roundedCorners != null && rotation != this.mDisplayInfo.rotation) {
        roundedCorners.rotate(
            rotation, this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
      }
      roundedCorner = roundedCorners == null ? null : roundedCorners.getRoundedCorner(position);
    }
    return roundedCorner;
  }

  public DisplayShape getShape() {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      DisplayShape displayShape = this.mDisplayInfo.displayShape;
      int rotation = getLocalRotation();
      if (displayShape == null || rotation == this.mDisplayInfo.rotation) {
        return displayShape;
      }
      return displayShape.setRotation(rotation);
    }
  }

  @Deprecated
  public int getPixelFormat() {
    return 1;
  }

  public float getRefreshRate() {
    float refreshRate;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      refreshRate = this.mDisplayInfo.getRefreshRate();
    }
    return refreshRate;
  }

  @Deprecated
  public float[] getSupportedRefreshRates() {
    float[] defaultRefreshRates;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      defaultRefreshRates = this.mDisplayInfo.getDefaultRefreshRates();
    }
    return defaultRefreshRates;
  }

  public Mode getMode() {
    Mode mode;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      mode = this.mDisplayInfo.getMode();
    }
    return mode;
  }

  public Mode getDefaultMode() {
    Mode defaultMode;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      defaultMode = this.mDisplayInfo.getDefaultMode();
    }
    return defaultMode;
  }

  public Mode[] getSupportedModes() {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      if (CoreRune.FW_VRR_REFRESH_RATE_MODE && this.mType == 1) {
        final RefreshRateConfig config = RefreshRateConfig.getInstance();
        final Mode activeMode = this.mDisplayInfo.getMode();
        Stream<Mode> stream = Arrays.stream(this.mDisplayInfo.supportedModes);
        if (this.mDisplayInfo.refreshRateMode == 0) {
          if (config.isSwitchable()) {
            return (Mode[])
                stream
                    .filter(
                        new Predicate() { // from class:
                                          // android.view.Display$$ExternalSyntheticLambda0
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            return Display.lambda$getSupportedModes$0(
                                RefreshRateConfig.this, activeMode, (Display.Mode) obj);
                          }
                        })
                    .toArray(
                        new IntFunction() { // from class:
                                            // android.view.Display$$ExternalSyntheticLambda1
                          @Override // java.util.function.IntFunction
                          public final Object apply(int i) {
                            return Display.lambda$getSupportedModes$1(i);
                          }
                        });
          }
          return (Mode[])
              stream
                  .filter(
                      new Predicate() { // from class:
                                        // android.view.Display$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          return Display.lambda$getSupportedModes$2(
                              RefreshRateConfig.this, (Display.Mode) obj);
                        }
                      })
                  .toArray(
                      new IntFunction() { // from class:
                                          // android.view.Display$$ExternalSyntheticLambda3
                        @Override // java.util.function.IntFunction
                        public final Object apply(int i) {
                          return Display.lambda$getSupportedModes$3(i);
                        }
                      });
        }
        if (this.mDisplayInfo.refreshRateMode == 2) {
          return (Mode[])
              stream
                  .filter(
                      new Predicate() { // from class:
                                        // android.view.Display$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          return Display.lambda$getSupportedModes$4(
                              RefreshRateConfig.this, activeMode, (Display.Mode) obj);
                        }
                      })
                  .toArray(
                      new IntFunction() { // from class:
                                          // android.view.Display$$ExternalSyntheticLambda5
                        @Override // java.util.function.IntFunction
                        public final Object apply(int i) {
                          return Display.lambda$getSupportedModes$5(i);
                        }
                      });
        }
        if (this.mDisplayInfo.refreshRateMode == 1) {
          return (Mode[])
              stream
                  .filter(
                      new Predicate() { // from class:
                                        // android.view.Display$$ExternalSyntheticLambda6
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          return Display.lambda$getSupportedModes$6(
                              RefreshRateConfig.this, (Display.Mode) obj);
                        }
                      })
                  .toArray(
                      new IntFunction() { // from class:
                                          // android.view.Display$$ExternalSyntheticLambda7
                        @Override // java.util.function.IntFunction
                        public final Object apply(int i) {
                          return Display.lambda$getSupportedModes$7(i);
                        }
                      });
        }
      }
      Mode[] modes = this.mDisplayInfo.supportedModes;
      return (Mode[]) Arrays.copyOf(modes, modes.length);
    }
  }

  static /* synthetic */ boolean lambda$getSupportedModes$0(
      RefreshRateConfig config, Mode activeMode, Mode m) {
    return ((int) m.getRefreshRate()) == config.getNormalSpeedRefreshRates().max()
        && m.equalsExceptRefreshRate(activeMode);
  }

  static /* synthetic */ Mode[] lambda$getSupportedModes$1(int x$0) {
    return new Mode[x$0];
  }

  static /* synthetic */ boolean lambda$getSupportedModes$2(RefreshRateConfig config, Mode m) {
    return ((int) m.getRefreshRate()) >= config.getNormalSpeedRefreshRates().min()
        && ((int) m.getRefreshRate()) <= config.getNormalSpeedRefreshRates().max();
  }

  static /* synthetic */ Mode[] lambda$getSupportedModes$3(int x$0) {
    return new Mode[x$0];
  }

  static /* synthetic */ boolean lambda$getSupportedModes$4(
      RefreshRateConfig config, Mode activeMode, Mode m) {
    return ((int) m.getRefreshRate()) == config.getHighSpeedRefreshRates().max()
        && m.equalsExceptRefreshRate(activeMode);
  }

  static /* synthetic */ Mode[] lambda$getSupportedModes$5(int x$0) {
    return new Mode[x$0];
  }

  static /* synthetic */ boolean lambda$getSupportedModes$6(RefreshRateConfig config, Mode m) {
    return ((int) m.getRefreshRate()) >= config.getHighSpeedRefreshRates().min()
        && ((int) m.getRefreshRate()) <= config.getHighSpeedRefreshRates().max();
  }

  static /* synthetic */ Mode[] lambda$getSupportedModes$7(int x$0) {
    return new Mode[x$0];
  }

  public boolean isMinimalPostProcessingSupported() {
    boolean z;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      z = this.mDisplayInfo.minimalPostProcessingSupported;
    }
    return z;
  }

  public void requestColorMode(int colorMode) {
    this.mGlobal.requestColorMode(this.mDisplayId, colorMode);
  }

  public int getColorMode() {
    int i;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      i = this.mDisplayInfo.colorMode;
    }
    return i;
  }

  public int getRemoveMode() {
    return this.mDisplayInfo.removeMode;
  }

  public Mode getSystemPreferredDisplayMode() {
    return this.mGlobal.getSystemPreferredDisplayMode(getDisplayId());
  }

  public HdrCapabilities getHdrCapabilities() {
    int[] modeSupportedHdrTypes;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      if (this.mDisplayInfo.hdrCapabilities == null) {
        return null;
      }
      if (this.mDisplayInfo.userDisabledHdrTypes.length == 0) {
        int[] modeSupportedHdrTypes2 = getMode().getSupportedHdrTypes();
        modeSupportedHdrTypes =
            Arrays.copyOf(modeSupportedHdrTypes2, modeSupportedHdrTypes2.length);
      } else {
        ArraySet<Integer> enabledTypesSet = new ArraySet<>();
        for (int supportedType : getMode().getSupportedHdrTypes()) {
          if (!contains(this.mDisplayInfo.userDisabledHdrTypes, supportedType)) {
            enabledTypesSet.add(Integer.valueOf(supportedType));
          }
        }
        int[] supportedHdrTypes = new int[enabledTypesSet.size()];
        int index = 0;
        Iterator<Integer> it = enabledTypesSet.iterator();
        while (it.hasNext()) {
          int enabledType = it.next().intValue();
          supportedHdrTypes[index] = enabledType;
          index++;
        }
        modeSupportedHdrTypes = supportedHdrTypes;
      }
      return new HdrCapabilities(
          modeSupportedHdrTypes,
          this.mDisplayInfo.hdrCapabilities.mMaxLuminance,
          this.mDisplayInfo.hdrCapabilities.mMaxAverageLuminance,
          this.mDisplayInfo.hdrCapabilities.mMinLuminance);
    }
  }

  private boolean contains(int[] disabledHdrTypes, int hdrType) {
    for (int i : disabledHdrTypes) {
      Integer disabledHdrFormat = Integer.valueOf(i);
      if (disabledHdrFormat.intValue() == hdrType) {
        return true;
      }
    }
    return false;
  }

  public int[] getReportedHdrTypes() {
    int[] supportedHdrTypes;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      supportedHdrTypes = this.mDisplayInfo.getMode().getSupportedHdrTypes();
    }
    return supportedHdrTypes;
  }

  public boolean isHdr() {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      HdrCapabilities hdrCapabilities = getHdrCapabilities();
      if (hdrCapabilities == null) {
        return false;
      }
      return hdrCapabilities.getSupportedHdrTypes().length != 0;
    }
  }

  public boolean isHdrSdrRatioAvailable() {
    boolean z;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      z = !Float.isNaN(this.mDisplayInfo.hdrSdrRatio);
    }
    return z;
  }

  public float getHdrSdrRatio() {
    float f;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      f = Float.isNaN(this.mDisplayInfo.hdrSdrRatio) ? 1.0f : this.mDisplayInfo.hdrSdrRatio;
    }
    return f;
  }

  private int findHdrSdrRatioListenerLocked(Consumer<Display> listener) {
    for (int i = 0; i < this.mHdrSdrRatioListeners.size(); i++) {
      HdrSdrRatioListenerWrapper wrapper = this.mHdrSdrRatioListeners.get(i);
      if (wrapper.mListener == listener) {
        return i;
      }
    }
    return -1;
  }

  public void registerHdrSdrRatioChangedListener(Executor executor, Consumer<Display> listener) {
    if (!isHdrSdrRatioAvailable()) {
      throw new IllegalStateException("HDR/SDR ratio changed not available");
    }
    HdrSdrRatioListenerWrapper toRegister = null;
    synchronized (this.mLock) {
      if (findHdrSdrRatioListenerLocked(listener) == -1) {
        toRegister = new HdrSdrRatioListenerWrapper(listener);
        this.mHdrSdrRatioListeners.add(toRegister);
      }
    }
    if (toRegister != null) {
      this.mGlobal.registerDisplayListener(toRegister, executor, 18L);
    }
  }

  public void unregisterHdrSdrRatioChangedListener(Consumer<Display> listener) {
    HdrSdrRatioListenerWrapper toRemove = null;
    synchronized (this.mLock) {
      int index = findHdrSdrRatioListenerLocked(listener);
      if (index != -1) {
        toRemove = this.mHdrSdrRatioListeners.remove(index);
      }
    }
    if (toRemove != null) {
      this.mGlobal.unregisterDisplayListener(toRemove);
    }
  }

  public void setUserPreferredDisplayMode(Mode mode) {
    Mode preferredMode =
        new Mode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate());
    this.mGlobal.setUserPreferredDisplayMode(this.mDisplayId, preferredMode);
  }

  public void clearUserPreferredDisplayMode() {
    this.mGlobal.setUserPreferredDisplayMode(this.mDisplayId, null);
  }

  public Mode getUserPreferredDisplayMode() {
    return this.mGlobal.getUserPreferredDisplayMode(this.mDisplayId);
  }

  public boolean isWideColorGamut() {
    boolean isWideColorGamut;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      isWideColorGamut = this.mDisplayInfo.isWideColorGamut();
    }
    return isWideColorGamut;
  }

  public ColorSpace getPreferredWideGamutColorSpace() {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      if (!this.mDisplayInfo.isWideColorGamut()) {
        return null;
      }
      return this.mGlobal.getPreferredWideGamutColorSpace();
    }
  }

  public OverlayProperties getOverlaySupport() {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      if (this.mDisplayInfo.type == 5) {
        return null;
      }
      return this.mGlobal.getOverlaySupport();
    }
  }

  public int[] getSupportedColorModes() {
    int[] copyOf;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      int[] colorModes = this.mDisplayInfo.supportedColorModes;
      copyOf = Arrays.copyOf(colorModes, colorModes.length);
    }
    return copyOf;
  }

  public ColorSpace[] getSupportedWideColorGamut() {
    synchronized (this.mLock) {
      ColorSpace[] defaultColorSpaces = new ColorSpace[0];
      updateDisplayInfoLocked();
      if (!isWideColorGamut()) {
        return defaultColorSpaces;
      }
      int[] colorModes = getSupportedColorModes();
      List<ColorSpace> colorSpaces = new ArrayList<>();
      for (int colorMode : colorModes) {
        switch (colorMode) {
          case 6:
            colorSpaces.add(ColorSpace.get(ColorSpace.Named.DCI_P3));
            break;
          case 9:
            colorSpaces.add(ColorSpace.get(ColorSpace.Named.DISPLAY_P3));
            break;
        }
      }
      return (ColorSpace[]) colorSpaces.toArray(defaultColorSpaces);
    }
  }

  public long getAppVsyncOffsetNanos() {
    long j;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      j = this.mDisplayInfo.appVsyncOffsetNanos;
    }
    return j;
  }

  public long getPresentationDeadlineNanos() {
    long j;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      j = this.mDisplayInfo.presentationDeadlineNanos;
    }
    return j;
  }

  public DeviceProductInfo getDeviceProductInfo() {
    DeviceProductInfo deviceProductInfo;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      deviceProductInfo = this.mDisplayInfo.deviceProductInfo;
    }
    return deviceProductInfo;
  }

  @Deprecated
  public void getMetrics(DisplayMetrics outMetrics) {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      this.mDisplayInfo.getAppMetrics(outMetrics, getDisplayAdjustments());
    }
  }

  @Deprecated
  public void getRealSize(Point outSize) {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      if (shouldReportMaxBounds()) {
        Rect bounds = this.mResources.getConfiguration().windowConfiguration.getMaxBounds();
        outSize.f85x = bounds.width();
        outSize.f86y = bounds.height();
      } else {
        outSize.f85x = this.mDisplayInfo.logicalWidth;
        outSize.f86y = this.mDisplayInfo.logicalHeight;
        int rotation = getLocalRotation();
        if (rotation != this.mDisplayInfo.rotation) {
          adjustSize(outSize, this.mDisplayInfo.rotation, rotation);
        }
      }
    }
  }

  @Deprecated
  public void getRealMetrics(DisplayMetrics outMetrics) {
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      if (shouldReportMaxBounds()) {
        this.mDisplayInfo.getMaxBoundsMetrics(
            outMetrics,
            CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO,
            this.mResources.getConfiguration());
        return;
      }
      this.mDisplayInfo.getLogicalMetrics(
          outMetrics, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, null);
      int rotation = getLocalRotation();
      if (rotation != this.mDisplayInfo.rotation) {
        adjustMetrics(outMetrics, this.mDisplayInfo.rotation, rotation);
      }
    }
  }

  private boolean shouldReportMaxBounds() {
    Configuration config;
    if (this.mResources == null) {
      return false;
    }
    ActivityThread thread = ActivityThread.currentActivityThread();
    return ((thread != null && thread.isDexCompatMode())
            || (config = this.mResources.getConfiguration()) == null
            || config.windowConfiguration.getMaxBounds().isEmpty())
        ? false
        : true;
  }

  public int getState() {
    int i;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      i = this.mIsValid ? this.mDisplayInfo.state : 0;
    }
    return i;
  }

  public int getCommittedState() {
    int i;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      i = this.mIsValid ? this.mDisplayInfo.committedState : 0;
    }
    return i;
  }

  public boolean hasAccess(int uid) {
    return hasAccess(uid, this.mFlags, this.mOwnerUid, this.mDisplayId);
  }

  public static boolean hasAccess(int uid, int flags, int ownerUid, int displayId) {
    return (flags & 4) == 0
        || uid == ownerUid
        || uid == 1000
        || uid == 0
        || DisplayManagerGlobal.getInstance().isUidPresentOnDisplay(uid, displayId);
  }

  public boolean isPublicPresentation() {
    return (this.mFlags & 12) == 8;
  }

  public boolean isTrusted() {
    return (this.mFlags & 128) == 128;
  }

  public boolean canStealTopFocus() {
    return (this.mFlags & 4096) == 0;
  }

  private void updateDisplayInfoLocked() {
    DisplayInfo newInfo = this.mGlobal.getDisplayInfo(this.mDisplayId);
    if (newInfo == null) {
      if (this.mIsValid) {
        this.mIsValid = false;
      }
    } else {
      this.mDisplayInfo = newInfo;
      if (!this.mIsValid) {
        this.mIsValid = true;
      }
    }
    if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && this.mDisplayId == 0) {
      RefreshRateConfig.updateSubScreen(this.mDisplayInfo.address);
    }
  }

  private void updateCachedAppSizeIfNeededLocked() {
    long now = SystemClock.uptimeMillis();
    if (now > this.mLastCachedAppSizeUpdate + 20) {
      updateDisplayInfoLocked();
      this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
      this.mCachedAppWidthCompat = this.mTempMetrics.widthPixels;
      this.mCachedAppHeightCompat = this.mTempMetrics.heightPixels;
      this.mLastCachedAppSizeUpdate = now;
    }
  }

  private static boolean noFlip(int realRotation, int localRotation) {
    return ((realRotation - localRotation) + 4) % 2 == 0;
  }

  private void adjustSize(Point size, int realRotation, int localRotation) {
    if (noFlip(realRotation, localRotation)) {
      return;
    }
    int w = size.f85x;
    size.f85x = size.f86y;
    size.f86y = w;
  }

  private void adjustMetrics(DisplayMetrics metrics, int realRotation, int localRotation) {
    if (noFlip(realRotation, localRotation)) {
      return;
    }
    int w = metrics.widthPixels;
    metrics.widthPixels = metrics.heightPixels;
    metrics.heightPixels = w;
    int w2 = metrics.noncompatWidthPixels;
    metrics.noncompatWidthPixels = metrics.noncompatHeightPixels;
    metrics.noncompatHeightPixels = w2;
  }

  private int getLocalRotation() {
    Resources resources = this.mResources;
    if (resources == null) {
      return this.mDisplayInfo.rotation;
    }
    int localRotation = resources.getConfiguration().windowConfiguration.getDisplayRotation();
    return localRotation != -1 ? localRotation : this.mDisplayInfo.rotation;
  }

  public String toString() {
    String str;
    synchronized (this.mLock) {
      updateDisplayInfoLocked();
      DisplayAdjustments adjustments = getDisplayAdjustments();
      this.mDisplayInfo.getAppMetrics(this.mTempMetrics, adjustments);
      str =
          "Display id "
              + this.mDisplayId
              + ": "
              + this.mDisplayInfo
              + ", "
              + this.mTempMetrics
              + ", isValid="
              + this.mIsValid;
    }
    return str;
  }

  public static String typeToString(int type) {
    switch (type) {
      case 0:
        return "UNKNOWN";
      case 1:
        return "INTERNAL";
      case 2:
        return "EXTERNAL";
      case 3:
        return "WIFI";
      case 4:
        return "OVERLAY";
      case 5:
        return "VIRTUAL";
      default:
        return Integer.toString(type);
    }
  }

  public static String stateToString(int state) {
    switch (state) {
      case 0:
        return "UNKNOWN";
      case 1:
        return "OFF";
      case 2:
        return "ON";
      case 3:
        return "DOZE";
      case 4:
        return "DOZE_SUSPEND";
      case 5:
        return "VR";
      case 6:
        return "ON_SUSPEND";
      default:
        return Integer.toString(state);
    }
  }

  public static boolean isSuspendedState(int state) {
    return state == 1 || state == 4 || state == 6;
  }

  public static boolean isDozeState(int state) {
    return state == 3 || state == 4;
  }

  public static boolean isActiveState(int state) {
    return state == 2 || state == 5;
  }

  public static boolean isOffState(int state) {
    return state == 1;
  }

  public static boolean isOnState(int state) {
    return state == 2 || state == 5 || state == 6;
  }

  public static boolean isWidthValid(int width) {
    return width > 0;
  }

  public static boolean isHeightValid(int height) {
    return height > 0;
  }

  public static boolean isRefreshRateValid(float refreshRate) {
    return refreshRate > 0.0f;
  }

  public DisplayDecorationSupport getDisplayDecorationSupport() {
    return this.mGlobal.getDisplayDecorationSupport(this.mDisplayId);
  }

  public static final class Mode implements Parcelable {
    public static final int INVALID_MODE_ID = -1;
    private final float[] mAlternativeRefreshRates;
    private final int mHeight;
    private final int mModeId;
    private final float mRefreshRate;
    private final int[] mSupportedHdrTypes;
    private final int mWidth;
    public static final Mode[] EMPTY_ARRAY = new Mode[0];
    public static final Parcelable.Creator<Mode> CREATOR =
        new Parcelable.Creator<Mode>() { // from class: android.view.Display.Mode.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Mode createFromParcel(Parcel in) {
            return new Mode(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Mode[] newArray(int size) {
            return new Mode[size];
          }
        };

    public Mode(int width, int height, float refreshRate) {
      this(-1, width, height, refreshRate, new float[0], new int[0]);
    }

    public Mode(int modeId, int width, int height, float refreshRate) {
      this(modeId, width, height, refreshRate, new float[0], new int[0]);
    }

    public Mode(
        int modeId,
        int width,
        int height,
        float refreshRate,
        float[] alternativeRefreshRates,
        int[] supportedHdrTypes) {
      this.mModeId = modeId;
      this.mWidth = width;
      this.mHeight = height;
      this.mRefreshRate = refreshRate;
      float[] copyOf = Arrays.copyOf(alternativeRefreshRates, alternativeRefreshRates.length);
      this.mAlternativeRefreshRates = copyOf;
      Arrays.sort(copyOf);
      int[] copyOf2 = Arrays.copyOf(supportedHdrTypes, supportedHdrTypes.length);
      this.mSupportedHdrTypes = copyOf2;
      Arrays.sort(copyOf2);
    }

    public int getModeId() {
      return this.mModeId;
    }

    public int getPhysicalWidth() {
      return this.mWidth;
    }

    public int getPhysicalHeight() {
      return this.mHeight;
    }

    public float getRefreshRate() {
      return this.mRefreshRate;
    }

    public float[] getAlternativeRefreshRates() {
      if (CoreRune.FW_VRR_POLICY && RefreshRateConfig.getInstance().isSwitchable()) {
        return new float[0];
      }
      float[] fArr = this.mAlternativeRefreshRates;
      return Arrays.copyOf(fArr, fArr.length);
    }

    public int[] getSupportedHdrTypes() {
      int[] iArr = this.mSupportedHdrTypes;
      return Arrays.copyOf(iArr, iArr.length);
    }

    public boolean matches(int width, int height, float refreshRate) {
      return this.mWidth == width
          && this.mHeight == height
          && Float.floatToIntBits(this.mRefreshRate) == Float.floatToIntBits(refreshRate);
    }

    public boolean matchesIfValid(int width, int height, float refreshRate) {
      if ((!Display.isWidthValid(width)
              && !Display.isHeightValid(height)
              && !Display.isRefreshRateValid(refreshRate))
          || Display.isWidthValid(width) != Display.isHeightValid(height)) {
        return false;
      }
      if (Display.isWidthValid(width) && this.mWidth != width) {
        return false;
      }
      if (!Display.isHeightValid(height) || this.mHeight == height) {
        return !Display.isRefreshRateValid(refreshRate)
            || Float.floatToIntBits(this.mRefreshRate) == Float.floatToIntBits(refreshRate);
      }
      return false;
    }

    public boolean equalsExceptRefreshRate(Mode other) {
      return this.mWidth == other.mWidth && this.mHeight == other.mHeight;
    }

    public boolean isRefreshRateSet() {
      return this.mRefreshRate != 0.0f;
    }

    public boolean isResolutionSet() {
      return (this.mWidth == -1 || this.mHeight == -1) ? false : true;
    }

    public boolean equals(Object other) {
      if (this == other) {
        return true;
      }
      if (!(other instanceof Mode)) {
        return false;
      }
      Mode that = (Mode) other;
      return this.mModeId == that.mModeId
          && matches(that.mWidth, that.mHeight, that.mRefreshRate)
          && Arrays.equals(this.mAlternativeRefreshRates, that.mAlternativeRefreshRates)
          && Arrays.equals(this.mSupportedHdrTypes, that.mSupportedHdrTypes);
    }

    public int hashCode() {
      int hash = (1 * 17) + this.mModeId;
      return (((((((((hash * 17) + this.mWidth) * 17) + this.mHeight) * 17)
                          + Float.floatToIntBits(this.mRefreshRate))
                      * 17)
                  + Arrays.hashCode(this.mAlternativeRefreshRates))
              * 17)
          + Arrays.hashCode(this.mSupportedHdrTypes);
    }

    public String toString() {
      return "{id="
          + this.mModeId
          + ", width="
          + this.mWidth
          + ", height="
          + this.mHeight
          + ", fps="
          + this.mRefreshRate
          + ", alternativeRefreshRates="
          + Arrays.toString(this.mAlternativeRefreshRates)
          + ", supportedHdrTypes="
          + Arrays.toString(this.mSupportedHdrTypes)
          + "}";
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    private Mode(Parcel in) {
      this(
          in.readInt(),
          in.readInt(),
          in.readInt(),
          in.readFloat(),
          in.createFloatArray(),
          in.createIntArray());
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int parcelableFlags) {
      out.writeInt(this.mModeId);
      out.writeInt(this.mWidth);
      out.writeInt(this.mHeight);
      out.writeFloat(this.mRefreshRate);
      out.writeFloatArray(this.mAlternativeRefreshRates);
      out.writeIntArray(this.mSupportedHdrTypes);
    }

    public static final class Builder {
      private int mWidth = -1;
      private int mHeight = -1;
      private float mRefreshRate = 0.0f;

      public Builder setResolution(int width, int height) {
        if (width > 0 && height > 0) {
          this.mWidth = width;
          this.mHeight = height;
        }
        return this;
      }

      public Builder setRefreshRate(float refreshRate) {
        if (refreshRate > 0.0f) {
          this.mRefreshRate = refreshRate;
        }
        return this;
      }

      public Mode build() {
        Mode mode = new Mode(this.mWidth, this.mHeight, this.mRefreshRate);
        return mode;
      }
    }
  }

  public static final class HdrCapabilities implements Parcelable {
    public static final int HDR_TYPE_DOLBY_VISION = 1;
    public static final int HDR_TYPE_HDR10 = 2;
    public static final int HDR_TYPE_HDR10_PLUS = 4;
    public static final int HDR_TYPE_HLG = 3;
    public static final int HDR_TYPE_INVALID = -1;
    public static final float INVALID_LUMINANCE = -1.0f;
    private float mMaxAverageLuminance;
    private float mMaxLuminance;
    private float mMinLuminance;
    private int[] mSupportedHdrTypes;
    public static final int[] HDR_TYPES = {1, 2, 3, 4};
    public static final Parcelable.Creator<HdrCapabilities> CREATOR =
        new Parcelable.Creator<
            HdrCapabilities>() { // from class: android.view.Display.HdrCapabilities.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public HdrCapabilities createFromParcel(Parcel source) {
            return new HdrCapabilities(source);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public HdrCapabilities[] newArray(int size) {
            return new HdrCapabilities[size];
          }
        };

    @Retention(RetentionPolicy.SOURCE)
    public @interface HdrType {}

    public HdrCapabilities() {
      this.mSupportedHdrTypes = new int[0];
      this.mMaxLuminance = -1.0f;
      this.mMaxAverageLuminance = -1.0f;
      this.mMinLuminance = -1.0f;
    }

    public HdrCapabilities(
        int[] supportedHdrTypes,
        float maxLuminance,
        float maxAverageLuminance,
        float minLuminance) {
      this.mSupportedHdrTypes = new int[0];
      this.mMaxLuminance = -1.0f;
      this.mMaxAverageLuminance = -1.0f;
      this.mMinLuminance = -1.0f;
      this.mSupportedHdrTypes = supportedHdrTypes;
      Arrays.sort(supportedHdrTypes);
      this.mMaxLuminance = maxLuminance;
      this.mMaxAverageLuminance = maxAverageLuminance;
      this.mMinLuminance = minLuminance;
    }

    @Deprecated
    public int[] getSupportedHdrTypes() {
      int[] iArr = this.mSupportedHdrTypes;
      return Arrays.copyOf(iArr, iArr.length);
    }

    public float getDesiredMaxLuminance() {
      return this.mMaxLuminance;
    }

    public float getDesiredMaxAverageLuminance() {
      return this.mMaxAverageLuminance;
    }

    public float getDesiredMinLuminance() {
      return this.mMinLuminance;
    }

    public boolean equals(Object other) {
      if (this == other) {
        return true;
      }
      if (!(other instanceof HdrCapabilities)) {
        return false;
      }
      HdrCapabilities that = (HdrCapabilities) other;
      return Arrays.equals(this.mSupportedHdrTypes, that.mSupportedHdrTypes)
          && this.mMaxLuminance == that.mMaxLuminance
          && this.mMaxAverageLuminance == that.mMaxAverageLuminance
          && this.mMinLuminance == that.mMinLuminance;
    }

    public int hashCode() {
      int hash = (23 * 17) + Arrays.hashCode(this.mSupportedHdrTypes);
      return (((((hash * 17) + Float.floatToIntBits(this.mMaxLuminance)) * 17)
                  + Float.floatToIntBits(this.mMaxAverageLuminance))
              * 17)
          + Float.floatToIntBits(this.mMinLuminance);
    }

    private HdrCapabilities(Parcel source) {
      this.mSupportedHdrTypes = new int[0];
      this.mMaxLuminance = -1.0f;
      this.mMaxAverageLuminance = -1.0f;
      this.mMinLuminance = -1.0f;
      readFromParcel(source);
    }

    public void readFromParcel(Parcel source) {
      int types = source.readInt();
      this.mSupportedHdrTypes = new int[types];
      for (int i = 0; i < types; i++) {
        this.mSupportedHdrTypes[i] = source.readInt();
      }
      this.mMaxLuminance = source.readFloat();
      this.mMaxAverageLuminance = source.readFloat();
      this.mMinLuminance = source.readFloat();
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.mSupportedHdrTypes.length);
      int i = 0;
      while (true) {
        int[] iArr = this.mSupportedHdrTypes;
        if (i < iArr.length) {
          dest.writeInt(iArr[i]);
          i++;
        } else {
          dest.writeFloat(this.mMaxLuminance);
          dest.writeFloat(this.mMaxAverageLuminance);
          dest.writeFloat(this.mMinLuminance);
          return;
        }
      }
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    public String toString() {
      return "HdrCapabilities{mSupportedHdrTypes="
          + Arrays.toString(this.mSupportedHdrTypes)
          + ", mMaxLuminance="
          + this.mMaxLuminance
          + ", mMaxAverageLuminance="
          + this.mMaxAverageLuminance
          + ", mMinLuminance="
          + this.mMinLuminance
          + '}';
    }

    public static String hdrTypeToString(int hdrType) {
      switch (hdrType) {
        case 1:
          return "HDR_TYPE_DOLBY_VISION";
        case 2:
          return "HDR_TYPE_HDR10";
        case 3:
          return "HDR_TYPE_HLG";
        case 4:
          return "HDR_TYPE_HDR10_PLUS";
        default:
          return "HDR_TYPE_INVALID";
      }
    }
  }

  private class HdrSdrRatioListenerWrapper implements DisplayManager.DisplayListener {
    float mLastReportedRatio;
    Consumer<Display> mListener;

    private HdrSdrRatioListenerWrapper(Consumer<Display> listener) {
      this.mLastReportedRatio = 1.0f;
      this.mListener = listener;
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int displayId) {}

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int displayId) {}

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int displayId) {
      if (displayId == Display.this.getDisplayId()) {
        float newRatio = Display.this.getHdrSdrRatio();
        if (newRatio != this.mLastReportedRatio) {
          this.mLastReportedRatio = newRatio;
          this.mListener.accept(Display.this);
        }
      }
    }
  }
}
