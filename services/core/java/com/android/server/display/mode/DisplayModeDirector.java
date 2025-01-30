package com.android.server.display.mode;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.net.Uri;
import android.os.Handler;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.provider.Settings;
import android.sysprop.SurfaceFlingerProperties;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.os.BackgroundThread;
import com.android.server.LocalServices;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.DisplayPowerController2;
import com.android.server.display.utils.AmbientFilter;
import com.android.server.display.utils.AmbientFilterFactory;
import com.android.server.display.utils.SensorUtils;
import com.android.server.sensors.SensorManagerInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;

/* loaded from: classes2.dex */
public class DisplayModeDirector {
  public boolean mAlwaysRespectAppRequest;
  public final AppRequestObserver mAppRequestObserver;
  public BrightnessObserver mBrightnessObserver;
  public final Context mContext;
  public DisplayDeviceConfig mDefaultDisplayDeviceConfig;
  public SparseArray mDefaultModeByDisplay;
  public DesiredDisplayModeSpecsListener mDesiredDisplayModeSpecsListener;
  public final DeviceConfigInterface mDeviceConfig;
  public final DeviceConfigDisplaySettings mDeviceConfigDisplaySettings;
  public final DisplayObserver mDisplayObserver;
  public final DisplayModeDirectorHandler mHandler;
  public final HbmObserver mHbmObserver;
  public final Injector mInjector;
  public final Object mLock;
  public boolean mLoggingEnabled;
  public int mModeSwitchingType;
  public RefreshRateModeManager mRefreshRateModeManager;
  public final SensorObserver mSensorObserver;
  public final SettingsObserver mSettingsObserver;
  public final SkinThermalStatusObserver mSkinThermalStatusObserver;
  public SparseArray mSupportedModesByDisplay;
  public final boolean mSupportsFrameRateOverride;
  public final UdfpsObserver mUdfpsObserver;
  public final VotesStorage mVotesStorage;

  public interface DesiredDisplayModeSpecsListener {
    void onDesiredDisplayModeSpecsChanged();
  }

  public interface Injector {
    public static final Uri PEAK_REFRESH_RATE_URI = Settings.System.getUriFor("peak_refresh_rate");

    BrightnessInfo getBrightnessInfo(int i);

    DeviceConfigInterface getDeviceConfig();

    Display getDisplay(int i);

    boolean getDisplayInfo(int i, DisplayInfo displayInfo);

    Display[] getDisplays();

    boolean isDozeState(Display display);

    void registerDisplayListener(DisplayManager.DisplayListener displayListener, Handler handler);

    void registerDisplayListener(
        DisplayManager.DisplayListener displayListener, Handler handler, long j);

    void registerPeakRefreshRateObserver(
        ContentResolver contentResolver, ContentObserver contentObserver);

    boolean registerThermalServiceListener(IThermalEventListener iThermalEventListener);

    boolean supportsFrameRateOverride();
  }

  public final boolean equalsWithinFloatTolerance(float f, float f2) {
    return f >= f2 - 0.01f && f <= f2 + 0.01f;
  }

  public DisplayModeDirector(Context context, Handler handler) {
    this(context, handler, new RealInjector(context));
  }

  /* JADX WARN: Multi-variable type inference failed */
  public DisplayModeDirector(Context context, Handler handler, Injector injector) {
    Object obj = new Object();
    this.mLock = obj;
    this.mModeSwitchingType = (!CoreRune.FW_VRR_POLICY || CoreRune.FW_VRR_SEAMLESS) ? 1 : 0;
    this.mContext = context;
    this.mHandler = new DisplayModeDirectorHandler(handler.getLooper());
    this.mInjector = injector;
    this.mSupportedModesByDisplay = new SparseArray();
    this.mDefaultModeByDisplay = new SparseArray();
    this.mAppRequestObserver = new AppRequestObserver();
    this.mDeviceConfig = injector.getDeviceConfig();
    DeviceConfigDisplaySettings deviceConfigDisplaySettings = new DeviceConfigDisplaySettings();
    this.mDeviceConfigDisplaySettings = deviceConfigDisplaySettings;
    this.mSettingsObserver = new SettingsObserver(context, handler);
    this.mBrightnessObserver = new BrightnessObserver(context, handler, injector);
    this.mDefaultDisplayDeviceConfig = null;
    this.mUdfpsObserver = new UdfpsObserver();
    VotesStorage votesStorage =
        new VotesStorage(
            new VotesStorage
                .Listener() { // from class:
                              // com.android.server.display.mode.DisplayModeDirector$$ExternalSyntheticLambda0
              @Override // com.android.server.display.mode.VotesStorage.Listener
              public final void onChanged() {
                DisplayModeDirector.this.notifyDesiredDisplayModeSpecsChangedLocked();
              }
            });
    this.mVotesStorage = votesStorage;
    this.mDisplayObserver = new DisplayObserver(context, handler, votesStorage);
    this.mSensorObserver = new SensorObserver(context, votesStorage, injector);
    this.mSkinThermalStatusObserver = new SkinThermalStatusObserver(injector, votesStorage);
    this.mHbmObserver =
        new HbmObserver(
            injector, votesStorage, BackgroundThread.getHandler(), deviceConfigDisplaySettings);
    this.mAlwaysRespectAppRequest = false;
    this.mSupportsFrameRateOverride = injector.supportsFrameRateOverride();
    this.mRefreshRateModeManager =
        new RefreshRateModeManager(context, this, obj, handler, votesStorage);
  }

  public RefreshRateModeManager getRefreshRateModeManager() {
    return this.mRefreshRateModeManager;
  }

  public void start(SensorManager sensorManager) {
    if (CoreRune.FW_VRR_FOR_SUB_DISPLAY) {
      this.mRefreshRateModeManager.setPrimaryDisplayToken();
    }
    if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
      this.mRefreshRateModeManager.updateDefaultDisplayToken();
    }
    this.mSettingsObserver.observe();
    this.mDisplayObserver.observe();
    if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
      this.mRefreshRateModeManager.observe();
    }
    if (!CoreRune.FW_VRR_POLICY || CoreRune.FW_VRR_SEAMLESS) {
      this.mBrightnessObserver.observe(sensorManager);
    }
    this.mSensorObserver.observe();
    this.mHbmObserver.observe();
    if (!CoreRune.FW_VRR_POLICY) {
      this.mSkinThermalStatusObserver.observe();
    }
    synchronized (this.mLock) {
      notifyDesiredDisplayModeSpecsChangedLocked();
    }
  }

  public void onBootCompleted() {
    this.mUdfpsObserver.observe();
  }

  public void setLoggingEnabled(boolean z) {
    if (this.mLoggingEnabled == z) {
      return;
    }
    this.mLoggingEnabled = z;
    this.mBrightnessObserver.setLoggingEnabled(z);
    this.mSkinThermalStatusObserver.setLoggingEnabled(z);
    this.mVotesStorage.setLoggingEnabled(z);
  }

  public final class VoteSummary {
    public float appRequestBaseModeRefreshRate;
    public boolean disableRefreshRateSwitching;
    public int height;
    public float maxPhysicalRefreshRate;
    public float maxRenderFrameRate;
    public float minPhysicalRefreshRate;
    public float minRenderFrameRate;
    public int width;

    public VoteSummary() {
      reset();
    }

    public void reset() {
      this.minPhysicalRefreshRate = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.maxPhysicalRefreshRate = Float.POSITIVE_INFINITY;
      this.minRenderFrameRate = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.maxRenderFrameRate = Float.POSITIVE_INFINITY;
      this.width = -1;
      this.height = -1;
      this.disableRefreshRateSwitching = false;
      this.appRequestBaseModeRefreshRate = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }

    public String toString() {
      return "minPhysicalRefreshRate="
          + this.minPhysicalRefreshRate
          + ", maxPhysicalRefreshRate="
          + this.maxPhysicalRefreshRate
          + ", minRenderFrameRate="
          + this.minRenderFrameRate
          + ", maxRenderFrameRate="
          + this.maxRenderFrameRate
          + ", width="
          + this.width
          + ", height="
          + this.height
          + ", disableRefreshRateSwitching="
          + this.disableRefreshRateSwitching
          + ", appRequestBaseModeRefreshRate="
          + this.appRequestBaseModeRefreshRate;
    }
  }

  public final void summarizeVotes(
      SparseArray sparseArray, int i, int i2, VoteSummary voteSummary) {
    int i3;
    int i4;
    voteSummary.reset();
    while (i2 >= i) {
      Vote vote = (Vote) sparseArray.get(i2);
      if (vote != null) {
        SurfaceControl.RefreshRateRanges refreshRateRanges = vote.refreshRateRanges;
        voteSummary.minPhysicalRefreshRate =
            Math.max(
                voteSummary.minPhysicalRefreshRate,
                Math.max(refreshRateRanges.physical.min, refreshRateRanges.render.min));
        voteSummary.maxPhysicalRefreshRate =
            Math.min(voteSummary.maxPhysicalRefreshRate, vote.refreshRateRanges.physical.max);
        SurfaceControl.RefreshRateRanges refreshRateRanges2 = vote.refreshRateRanges;
        float min = Math.min(refreshRateRanges2.render.max, refreshRateRanges2.physical.max);
        voteSummary.minRenderFrameRate =
            Math.max(voteSummary.minRenderFrameRate, vote.refreshRateRanges.render.min);
        voteSummary.maxRenderFrameRate = Math.min(voteSummary.maxRenderFrameRate, min);
        if (voteSummary.height == -1
            && voteSummary.width == -1
            && (i3 = vote.height) > 0
            && (i4 = vote.width) > 0) {
          voteSummary.width = i4;
          voteSummary.height = i3;
        }
        if (!voteSummary.disableRefreshRateSwitching && vote.disableRefreshRateSwitching) {
          voteSummary.disableRefreshRateSwitching = true;
        }
        if (voteSummary.appRequestBaseModeRefreshRate
            == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          float f = vote.appRequestBaseModeRefreshRate;
          if (f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            voteSummary.appRequestBaseModeRefreshRate = f;
          }
        }
        if (this.mLoggingEnabled) {
          Slog.w(
              "DisplayModeDirector",
              "Vote summary for priority " + Vote.priorityToString(i2) + ": " + voteSummary);
        }
      }
      i2--;
    }
  }

  public final Display.Mode selectBaseMode(
      VoteSummary voteSummary, ArrayList arrayList, Display.Mode mode) {
    float f = voteSummary.appRequestBaseModeRefreshRate;
    if (f <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      f = mode.getRefreshRate();
    }
    if (CoreRune.FW_VRR_POLICY
        && !CoreRune.FW_VRR_SEAMLESS
        && this.mModeSwitchingType == 0
        && !arrayList.isEmpty()
        && voteSummary.appRequestBaseModeRefreshRate
            == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      f =
          ((Display.Mode)
                  arrayList.stream()
                      .max(
                          new Comparator() { // from class:
                                             // com.android.server.display.mode.DisplayModeDirector$$ExternalSyntheticLambda1
                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                              int lambda$selectBaseMode$0;
                              lambda$selectBaseMode$0 =
                                  DisplayModeDirector.lambda$selectBaseMode$0(
                                      (Display.Mode) obj, (Display.Mode) obj2);
                              return lambda$selectBaseMode$0;
                            }
                          })
                      .get())
              .getRefreshRate();
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      Display.Mode mode2 = (Display.Mode) it.next();
      if (equalsWithinFloatTolerance(f, mode2.getRefreshRate())) {
        return mode2;
      }
    }
    if (arrayList.isEmpty()) {
      return null;
    }
    return (Display.Mode) arrayList.get(0);
  }

  public static /* synthetic */ int lambda$selectBaseMode$0(Display.Mode mode, Display.Mode mode2) {
    return Float.compare(mode.getRefreshRate(), mode2.getRefreshRate());
  }

  public final void disableModeSwitching(VoteSummary voteSummary, float f) {
    voteSummary.maxPhysicalRefreshRate = f;
    voteSummary.minPhysicalRefreshRate = f;
    voteSummary.maxRenderFrameRate = Math.min(voteSummary.maxRenderFrameRate, f);
    if (this.mLoggingEnabled) {
      Slog.i("DisplayModeDirector", "Disabled mode switching on summary: " + voteSummary);
    }
  }

  public final void disableRenderRateSwitching(VoteSummary voteSummary, float f) {
    voteSummary.minRenderFrameRate = voteSummary.maxRenderFrameRate;
    if (!isRenderRateAchievable(f, voteSummary)) {
      voteSummary.maxRenderFrameRate = f;
      voteSummary.minRenderFrameRate = f;
    }
    if (this.mLoggingEnabled) {
      Slog.i("DisplayModeDirector", "Disabled render rate switching on summary: " + voteSummary);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:52:0x026b  */
  /* JADX WARN: Removed duplicated region for block: B:58:0x0259 A[Catch: all -> 0x02c7, TryCatch #0 {, blocks: (B:4:0x0003, B:8:0x001f, B:13:0x003c, B:15:0x0044, B:17:0x0054, B:26:0x005e, B:28:0x0062, B:19:0x00d5, B:21:0x00d9, B:23:0x0143, B:62:0x0048, B:29:0x0147, B:31:0x014b, B:32:0x0186, B:34:0x01ba, B:35:0x01f5, B:37:0x01fb, B:38:0x023c, B:41:0x023e, B:48:0x024c, B:50:0x0266, B:53:0x026c, B:54:0x02a3, B:56:0x0250, B:58:0x0259, B:60:0x0263, B:65:0x02a5, B:66:0x02c5), top: B:3:0x0003 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public DesiredDisplayModeSpecs getDesiredDisplayModeSpecs(int i) {
    int i2;
    int i3;
    boolean z;
    synchronized (this.mLock) {
      SparseArray votes = this.mVotesStorage.getVotes(i);
      Display.Mode[] modeArr = (Display.Mode[]) this.mSupportedModesByDisplay.get(i);
      Display.Mode mode = (Display.Mode) this.mDefaultModeByDisplay.get(i);
      if (modeArr != null && mode != null) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(mode);
        VoteSummary voteSummary = new VoteSummary();
        if (this.mAlwaysRespectAppRequest) {
          i2 = 8;
          i3 = 6;
        } else {
          i2 = 18;
          i3 = 0;
        }
        while (true) {
          if (i3 > i2) {
            break;
          }
          summarizeVotes(votes, i3, i2, voteSummary);
          if (voteSummary.height == -1 || voteSummary.width == -1) {
            voteSummary.width = mode.getPhysicalWidth();
            voteSummary.height = mode.getPhysicalHeight();
          }
          arrayList = filterModes(modeArr, voteSummary);
          if (!arrayList.isEmpty()) {
            if (this.mLoggingEnabled) {
              Slog.w(
                  "DisplayModeDirector",
                  "Found available modes="
                      + arrayList
                      + " with lowest priority considered "
                      + Vote.priorityToString(i3)
                      + " and constraints: width="
                      + voteSummary.width
                      + ", height="
                      + voteSummary.height
                      + ", minPhysicalRefreshRate="
                      + voteSummary.minPhysicalRefreshRate
                      + ", maxPhysicalRefreshRate="
                      + voteSummary.maxPhysicalRefreshRate
                      + ", minRenderFrameRate="
                      + voteSummary.minRenderFrameRate
                      + ", maxRenderFrameRate="
                      + voteSummary.maxRenderFrameRate
                      + ", disableRefreshRateSwitching="
                      + voteSummary.disableRefreshRateSwitching
                      + ", appRequestBaseModeRefreshRate="
                      + voteSummary.appRequestBaseModeRefreshRate);
            }
          } else {
            if (this.mLoggingEnabled) {
              Slog.w(
                  "DisplayModeDirector",
                  "Couldn't find available modes with lowest priority set to "
                      + Vote.priorityToString(i3)
                      + " and with the following constraints: width="
                      + voteSummary.width
                      + ", height="
                      + voteSummary.height
                      + ", minPhysicalRefreshRate="
                      + voteSummary.minPhysicalRefreshRate
                      + ", maxPhysicalRefreshRate="
                      + voteSummary.maxPhysicalRefreshRate
                      + ", minRenderFrameRate="
                      + voteSummary.minRenderFrameRate
                      + ", maxRenderFrameRate="
                      + voteSummary.maxRenderFrameRate
                      + ", disableRefreshRateSwitching="
                      + voteSummary.disableRefreshRateSwitching
                      + ", appRequestBaseModeRefreshRate="
                      + voteSummary.appRequestBaseModeRefreshRate);
            }
            i3++;
          }
        }
        if (this.mLoggingEnabled) {
          Slog.i(
              "DisplayModeDirector",
              "Primary physical range: ["
                  + voteSummary.minPhysicalRefreshRate
                  + " "
                  + voteSummary.maxPhysicalRefreshRate
                  + "] render frame rate range: ["
                  + voteSummary.minRenderFrameRate
                  + " "
                  + voteSummary.maxRenderFrameRate
                  + "]");
        }
        VoteSummary voteSummary2 = new VoteSummary();
        summarizeVotes(votes, 6, 18, voteSummary2);
        voteSummary2.minPhysicalRefreshRate =
            Math.min(voteSummary2.minPhysicalRefreshRate, voteSummary.minPhysicalRefreshRate);
        voteSummary2.maxPhysicalRefreshRate =
            Math.max(voteSummary2.maxPhysicalRefreshRate, voteSummary.maxPhysicalRefreshRate);
        voteSummary2.minRenderFrameRate =
            Math.min(voteSummary2.minRenderFrameRate, voteSummary.minRenderFrameRate);
        voteSummary2.maxRenderFrameRate =
            Math.max(voteSummary2.maxRenderFrameRate, voteSummary.maxRenderFrameRate);
        if (this.mLoggingEnabled) {
          Slog.i(
              "DisplayModeDirector",
              "App request range: ["
                  + voteSummary2.minPhysicalRefreshRate
                  + " "
                  + voteSummary2.maxPhysicalRefreshRate
                  + "] Frame rate range: ["
                  + voteSummary2.minRenderFrameRate
                  + " "
                  + voteSummary2.maxRenderFrameRate
                  + "]");
        }
        Display.Mode selectBaseMode = selectBaseMode(voteSummary, arrayList, mode);
        if (selectBaseMode == null) {
          Slog.w(
              "DisplayModeDirector",
              "Can't find a set of allowed modes which satisfies the votes. Falling back to the"
                  + " default mode. Display = "
                  + i
                  + ", votes = "
                  + votes
                  + ", supported modes = "
                  + Arrays.toString(modeArr));
          float refreshRate = mode.getRefreshRate();
          SurfaceControl.RefreshRateRange refreshRateRange =
              new SurfaceControl.RefreshRateRange(refreshRate, refreshRate);
          SurfaceControl.RefreshRateRanges refreshRateRanges =
              new SurfaceControl.RefreshRateRanges(refreshRateRange, refreshRateRange);
          return new DesiredDisplayModeSpecs(
              mode.getModeId(), false, refreshRateRanges, refreshRateRanges);
        }
        int i4 = this.mModeSwitchingType;
        if (i4 != 0 && i4 != 3) {
          z = false;
          if (!z || voteSummary.disableRefreshRateSwitching) {
            float refreshRate2 = selectBaseMode.getRefreshRate();
            disableModeSwitching(voteSummary, refreshRate2);
            if (z) {
              disableModeSwitching(voteSummary2, refreshRate2);
              disableRenderRateSwitching(voteSummary, refreshRate2);
              if (this.mModeSwitchingType == 0) {
                disableRenderRateSwitching(voteSummary2, refreshRate2);
              }
            }
          }
          return new DesiredDisplayModeSpecs(
              selectBaseMode.getModeId(),
              this.mModeSwitchingType == 2,
              new SurfaceControl.RefreshRateRanges(
                  new SurfaceControl.RefreshRateRange(
                      voteSummary.minPhysicalRefreshRate, voteSummary.maxPhysicalRefreshRate),
                  new SurfaceControl.RefreshRateRange(
                      voteSummary.minRenderFrameRate, voteSummary.maxRenderFrameRate)),
              new SurfaceControl.RefreshRateRanges(
                  new SurfaceControl.RefreshRateRange(
                      voteSummary2.minPhysicalRefreshRate, voteSummary2.maxPhysicalRefreshRate),
                  new SurfaceControl.RefreshRateRange(
                      voteSummary2.minRenderFrameRate, voteSummary2.maxRenderFrameRate)));
        }
        z = true;
        if (!z) {}
        float refreshRate22 = selectBaseMode.getRefreshRate();
        disableModeSwitching(voteSummary, refreshRate22);
        if (z) {}
        return new DesiredDisplayModeSpecs(
            selectBaseMode.getModeId(),
            this.mModeSwitchingType == 2,
            new SurfaceControl.RefreshRateRanges(
                new SurfaceControl.RefreshRateRange(
                    voteSummary.minPhysicalRefreshRate, voteSummary.maxPhysicalRefreshRate),
                new SurfaceControl.RefreshRateRange(
                    voteSummary.minRenderFrameRate, voteSummary.maxRenderFrameRate)),
            new SurfaceControl.RefreshRateRanges(
                new SurfaceControl.RefreshRateRange(
                    voteSummary2.minPhysicalRefreshRate, voteSummary2.maxPhysicalRefreshRate),
                new SurfaceControl.RefreshRateRange(
                    voteSummary2.minRenderFrameRate, voteSummary2.maxRenderFrameRate)));
      }
      Slog.e(
          "DisplayModeDirector",
          "Asked about unknown display, returning empty display mode specs!(id=" + i + ")");
      return new DesiredDisplayModeSpecs();
    }
  }

  public final boolean isRenderRateAchievable(float f, VoteSummary voteSummary) {
    return f / ((float) ((int) Math.ceil((double) ((f / voteSummary.maxRenderFrameRate) - 0.01f))))
        >= voteSummary.minRenderFrameRate - 0.01f;
  }

  public final ArrayList filterModes(Display.Mode[] modeArr, VoteSummary voteSummary) {
    if (voteSummary.minRenderFrameRate > voteSummary.maxRenderFrameRate + 0.01f) {
      if (this.mLoggingEnabled) {
        Slog.w(
            "DisplayModeDirector",
            "Vote summary resulted in empty set (invalid frame rate range): minRenderFrameRate="
                + voteSummary.minRenderFrameRate
                + ", maxRenderFrameRate="
                + voteSummary.maxRenderFrameRate);
      }
      return new ArrayList();
    }
    ArrayList arrayList = new ArrayList();
    boolean z =
        voteSummary.appRequestBaseModeRefreshRate > DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    for (Display.Mode mode : modeArr) {
      if (mode.getPhysicalWidth() != voteSummary.width
          || mode.getPhysicalHeight() != voteSummary.height) {
        if (this.mLoggingEnabled) {
          Slog.w(
              "DisplayModeDirector",
              "Discarding mode "
                  + mode.getModeId()
                  + ", wrong size: desiredWidth="
                  + voteSummary.width
                  + ": desiredHeight="
                  + voteSummary.height
                  + ": actualWidth="
                  + mode.getPhysicalWidth()
                  + ": actualHeight="
                  + mode.getPhysicalHeight());
        }
      } else {
        float refreshRate = mode.getRefreshRate();
        if (refreshRate < voteSummary.minPhysicalRefreshRate - 0.01f
            || refreshRate > voteSummary.maxPhysicalRefreshRate + 0.01f) {
          if (this.mLoggingEnabled) {
            Slog.w(
                "DisplayModeDirector",
                "Discarding mode "
                    + mode.getModeId()
                    + ", outside refresh rate bounds: minPhysicalRefreshRate="
                    + voteSummary.minPhysicalRefreshRate
                    + ", maxPhysicalRefreshRate="
                    + voteSummary.maxPhysicalRefreshRate
                    + ", modeRefreshRate="
                    + refreshRate);
          }
        } else if (!this.mSupportsFrameRateOverride
            && (refreshRate < voteSummary.minRenderFrameRate - 0.01f
                || refreshRate > voteSummary.maxRenderFrameRate + 0.01f)) {
          if (this.mLoggingEnabled) {
            Slog.w(
                "DisplayModeDirector",
                "Discarding mode "
                    + mode.getModeId()
                    + ", outside render rate bounds: minPhysicalRefreshRate="
                    + voteSummary.minPhysicalRefreshRate
                    + ", maxPhysicalRefreshRate="
                    + voteSummary.maxPhysicalRefreshRate
                    + ", modeRefreshRate="
                    + refreshRate);
          }
        } else if (!isRenderRateAchievable(refreshRate, voteSummary)) {
          if (this.mLoggingEnabled) {
            Slog.w(
                "DisplayModeDirector",
                "Discarding mode "
                    + mode.getModeId()
                    + ", outside frame rate bounds: minRenderFrameRate="
                    + voteSummary.minRenderFrameRate
                    + ", maxRenderFrameRate="
                    + voteSummary.maxRenderFrameRate
                    + ", modePhysicalRefreshRate="
                    + refreshRate);
          }
        } else {
          arrayList.add(mode);
          if (equalsWithinFloatTolerance(
              mode.getRefreshRate(), voteSummary.appRequestBaseModeRefreshRate)) {
            z = false;
          }
        }
      }
    }
    return z ? new ArrayList() : arrayList;
  }

  public AppRequestObserver getAppRequestObserver() {
    return this.mAppRequestObserver;
  }

  public void setDesiredDisplayModeSpecsListener(
      DesiredDisplayModeSpecsListener desiredDisplayModeSpecsListener) {
    synchronized (this.mLock) {
      this.mDesiredDisplayModeSpecsListener = desiredDisplayModeSpecsListener;
    }
  }

  public void defaultDisplayDeviceUpdated(DisplayDeviceConfig displayDeviceConfig) {
    synchronized (this.mLock) {
      this.mDefaultDisplayDeviceConfig = displayDeviceConfig;
      this.mSettingsObserver.setRefreshRates(displayDeviceConfig, true);
      this.mBrightnessObserver.updateBlockingZoneThresholds(displayDeviceConfig, true);
      this.mBrightnessObserver.reloadLightSensor(displayDeviceConfig);
      this.mHbmObserver.setupHdrRefreshRates(displayDeviceConfig);
    }
  }

  public void setShouldAlwaysRespectAppRequestedMode(boolean z) {
    synchronized (this.mLock) {
      if (this.mAlwaysRespectAppRequest != z) {
        this.mAlwaysRespectAppRequest = z;
        notifyDesiredDisplayModeSpecsChangedLocked();
        if (CoreRune.FW_VRR_SEAMLESS) {
          this.mBrightnessObserver.onBrightnessChangedLocked();
        }
      }
    }
  }

  public boolean shouldAlwaysRespectAppRequestedMode() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mAlwaysRespectAppRequest;
    }
    return z;
  }

  public void setModeSwitchingType(int i) {
    synchronized (this.mLock) {
      if (i != this.mModeSwitchingType) {
        this.mModeSwitchingType = i;
        notifyDesiredDisplayModeSpecsChangedLocked();
      }
    }
  }

  public int getModeSwitchingType() {
    int i;
    synchronized (this.mLock) {
      i = this.mModeSwitchingType;
    }
    return i;
  }

  public Vote getVote(int i, int i2) {
    return (Vote) this.mVotesStorage.getVotes(i).get(i2);
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println("DisplayModeDirector");
    synchronized (this.mLock) {
      printWriter.println("  mSupportedModesByDisplay:");
      for (int i = 0; i < this.mSupportedModesByDisplay.size(); i++) {
        printWriter.println(
            "    "
                + this.mSupportedModesByDisplay.keyAt(i)
                + " -> "
                + Arrays.toString((Display.Mode[]) this.mSupportedModesByDisplay.valueAt(i)));
      }
      printWriter.println("  mDefaultModeByDisplay:");
      for (int i2 = 0; i2 < this.mDefaultModeByDisplay.size(); i2++) {
        printWriter.println(
            "    "
                + this.mDefaultModeByDisplay.keyAt(i2)
                + " -> "
                + ((Display.Mode) this.mDefaultModeByDisplay.valueAt(i2)));
      }
      printWriter.println(
          "  mModeSwitchingType: " + switchingTypeToString(this.mModeSwitchingType));
      printWriter.println("  mAlwaysRespectAppRequest: " + this.mAlwaysRespectAppRequest);
      this.mSettingsObserver.dumpLocked(printWriter);
      this.mAppRequestObserver.dumpLocked(printWriter);
      this.mBrightnessObserver.dumpLocked(printWriter);
      this.mUdfpsObserver.dumpLocked(printWriter);
      this.mHbmObserver.dumpLocked(printWriter);
      if (!CoreRune.FW_VRR_POLICY) {
        this.mSkinThermalStatusObserver.dumpLocked(printWriter);
      }
      if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
        this.mRefreshRateModeManager.dumpLocked(printWriter);
      }
    }
    this.mVotesStorage.dump(printWriter);
    this.mSensorObserver.dump(printWriter);
  }

  public final float getMaxRefreshRateLocked(int i) {
    Display.Mode[] modeArr = (Display.Mode[]) this.mSupportedModesByDisplay.get(i);
    float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    for (Display.Mode mode : modeArr) {
      if (mode.getRefreshRate() > f) {
        f = mode.getRefreshRate();
      }
    }
    return f;
  }

  public final void notifyDesiredDisplayModeSpecsChangedLocked() {
    if (this.mDesiredDisplayModeSpecsListener == null || this.mHandler.hasMessages(1)) {
      return;
    }
    this.mHandler.obtainMessage(1, this.mDesiredDisplayModeSpecsListener).sendToTarget();
  }

  public static String switchingTypeToString(int i) {
    if (i == 0) {
      return "SWITCHING_TYPE_NONE";
    }
    if (i == 1) {
      return "SWITCHING_TYPE_WITHIN_GROUPS";
    }
    if (i == 2) {
      return "SWITCHING_TYPE_ACROSS_AND_WITHIN_GROUPS";
    }
    if (i == 3) {
      return "SWITCHING_TYPE_RENDER_FRAME_RATE_ONLY";
    }
    return "Unknown SwitchingType " + i;
  }

  public void injectSupportedModesByDisplay(SparseArray sparseArray) {
    this.mSupportedModesByDisplay = sparseArray;
  }

  public void injectDefaultModeByDisplay(SparseArray sparseArray) {
    this.mDefaultModeByDisplay = sparseArray;
  }

  public void injectVotesByDisplay(SparseArray sparseArray) {
    this.mVotesStorage.injectVotesByDisplay(sparseArray);
  }

  public void injectBrightnessObserver(BrightnessObserver brightnessObserver) {
    this.mBrightnessObserver = brightnessObserver;
  }

  public BrightnessObserver getBrightnessObserver() {
    return this.mBrightnessObserver;
  }

  public SettingsObserver getSettingsObserver() {
    return this.mSettingsObserver;
  }

  public UdfpsObserver getUdpfsObserver() {
    return this.mUdfpsObserver;
  }

  public HbmObserver getHbmObserver() {
    return this.mHbmObserver;
  }

  public DesiredDisplayModeSpecs getDesiredDisplayModeSpecsWithInjectedFpsSettings(
      float f, float f2, float f3) {
    DesiredDisplayModeSpecs desiredDisplayModeSpecs;
    synchronized (this.mLock) {
      this.mSettingsObserver.updateRefreshRateSettingLocked(f, f2, f3);
      desiredDisplayModeSpecs = getDesiredDisplayModeSpecs(0);
    }
    return desiredDisplayModeSpecs;
  }

  public final class DisplayModeDirectorHandler extends Handler {
    public DisplayModeDirectorHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 1:
          ((DesiredDisplayModeSpecsListener) message.obj).onDesiredDisplayModeSpecsChanged();
          break;
        case 2:
          Pair pair = (Pair) message.obj;
          DisplayModeDirector.this.mBrightnessObserver.onDeviceConfigLowBrightnessThresholdsChanged(
              (int[]) pair.first, (int[]) pair.second);
          break;
        case 3:
          DisplayModeDirector.this.mSettingsObserver.onDeviceConfigDefaultPeakRefreshRateChanged(
              (Float) message.obj);
          break;
        case 4:
          DisplayModeDirector.this.mBrightnessObserver.onDeviceConfigRefreshRateInLowZoneChanged(
              message.arg1);
          break;
        case 5:
          DisplayModeDirector.this.mBrightnessObserver.onDeviceConfigRefreshRateInHighZoneChanged(
              message.arg1);
          break;
        case 6:
          Pair pair2 = (Pair) message.obj;
          DisplayModeDirector.this.mBrightnessObserver
              .onDeviceConfigHighBrightnessThresholdsChanged(
                  (int[]) pair2.first, (int[]) pair2.second);
          break;
        case 7:
          DisplayModeDirector.this.mHbmObserver.onDeviceConfigRefreshRateInHbmSunlightChanged(
              message.arg1);
          break;
        case 8:
          DisplayModeDirector.this.mHbmObserver.onDeviceConfigRefreshRateInHbmHdrChanged(
              message.arg1);
          break;
      }
    }
  }

  public final class DesiredDisplayModeSpecs {
    public boolean allowGroupSwitching;
    public final SurfaceControl.RefreshRateRanges appRequest;
    public int baseModeId;
    public final SurfaceControl.RefreshRateRanges primary;

    public DesiredDisplayModeSpecs() {
      this.primary = new SurfaceControl.RefreshRateRanges();
      this.appRequest = new SurfaceControl.RefreshRateRanges();
    }

    public DesiredDisplayModeSpecs(
        int i,
        boolean z,
        SurfaceControl.RefreshRateRanges refreshRateRanges,
        SurfaceControl.RefreshRateRanges refreshRateRanges2) {
      this.baseModeId = i;
      this.allowGroupSwitching = z;
      this.primary = refreshRateRanges;
      this.appRequest = refreshRateRanges2;
    }

    public String toString() {
      return String.format(
          "baseModeId=%d allowGroupSwitching=%b primary=%s appRequest=%s",
          Integer.valueOf(this.baseModeId),
          Boolean.valueOf(this.allowGroupSwitching),
          this.primary.toString(),
          this.appRequest.toString());
    }

    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof DesiredDisplayModeSpecs)) {
        return false;
      }
      DesiredDisplayModeSpecs desiredDisplayModeSpecs = (DesiredDisplayModeSpecs) obj;
      return this.baseModeId == desiredDisplayModeSpecs.baseModeId
          && this.allowGroupSwitching == desiredDisplayModeSpecs.allowGroupSwitching
          && this.primary.equals(desiredDisplayModeSpecs.primary)
          && this.appRequest.equals(desiredDisplayModeSpecs.appRequest);
    }

    public int hashCode() {
      return Objects.hash(
          Integer.valueOf(this.baseModeId),
          Boolean.valueOf(this.allowGroupSwitching),
          this.primary,
          this.appRequest);
    }

    public void copyFrom(DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
      this.baseModeId = desiredDisplayModeSpecs.baseModeId;
      this.allowGroupSwitching = desiredDisplayModeSpecs.allowGroupSwitching;
      SurfaceControl.RefreshRateRanges refreshRateRanges = this.primary;
      SurfaceControl.RefreshRateRange refreshRateRange = refreshRateRanges.physical;
      SurfaceControl.RefreshRateRanges refreshRateRanges2 = desiredDisplayModeSpecs.primary;
      SurfaceControl.RefreshRateRange refreshRateRange2 = refreshRateRanges2.physical;
      refreshRateRange.min = refreshRateRange2.min;
      refreshRateRange.max = refreshRateRange2.max;
      SurfaceControl.RefreshRateRange refreshRateRange3 = refreshRateRanges.render;
      SurfaceControl.RefreshRateRange refreshRateRange4 = refreshRateRanges2.render;
      refreshRateRange3.min = refreshRateRange4.min;
      refreshRateRange3.max = refreshRateRange4.max;
      SurfaceControl.RefreshRateRanges refreshRateRanges3 = this.appRequest;
      SurfaceControl.RefreshRateRange refreshRateRange5 = refreshRateRanges3.physical;
      SurfaceControl.RefreshRateRanges refreshRateRanges4 = desiredDisplayModeSpecs.appRequest;
      SurfaceControl.RefreshRateRange refreshRateRange6 = refreshRateRanges4.physical;
      refreshRateRange5.min = refreshRateRange6.min;
      refreshRateRange5.max = refreshRateRange6.max;
      SurfaceControl.RefreshRateRange refreshRateRange7 = refreshRateRanges3.render;
      SurfaceControl.RefreshRateRange refreshRateRange8 = refreshRateRanges4.render;
      refreshRateRange7.min = refreshRateRange8.min;
      refreshRateRange7.max = refreshRateRange8.max;
    }
  }

  final class SettingsObserver extends ContentObserver {
    public final Context mContext;
    public float mDefaultPeakRefreshRate;
    public float mDefaultRefreshRate;
    public final Uri mLowPowerModeSetting;
    public final Uri mMatchContentFrameRateSetting;
    public final Uri mMinRefreshRateSetting;
    public final Uri mPeakRefreshRateSetting;

    public SettingsObserver(Context context, Handler handler) {
      super(handler);
      this.mPeakRefreshRateSetting = Settings.System.getUriFor("peak_refresh_rate");
      this.mMinRefreshRateSetting = Settings.System.getUriFor("min_refresh_rate");
      this.mLowPowerModeSetting = Settings.Global.getUriFor("low_power");
      this.mMatchContentFrameRateSetting = Settings.Secure.getUriFor("match_content_frame_rate");
      this.mContext = context;
      setRefreshRates(null, false);
    }

    public void setRefreshRates(DisplayDeviceConfig displayDeviceConfig, boolean z) {
      int defaultRefreshRate;
      setDefaultPeakRefreshRate(displayDeviceConfig, z);
      if (displayDeviceConfig == null) {
        defaultRefreshRate =
            this.mContext
                .getResources()
                .getInteger(R.integer.config_displayWhiteBalanceDecreaseDebounce);
      } else {
        defaultRefreshRate = displayDeviceConfig.getDefaultRefreshRate();
      }
      this.mDefaultRefreshRate = defaultRefreshRate;
    }

    public void observe() {
      ContentResolver contentResolver = this.mContext.getContentResolver();
      DisplayModeDirector.this.mInjector.registerPeakRefreshRateObserver(contentResolver, this);
      contentResolver.registerContentObserver(this.mMinRefreshRateSetting, false, this, 0);
      contentResolver.registerContentObserver(this.mLowPowerModeSetting, false, this, 0);
      contentResolver.registerContentObserver(this.mMatchContentFrameRateSetting, false, this);
      Float defaultPeakRefreshRate =
          DisplayModeDirector.this.mDeviceConfigDisplaySettings.getDefaultPeakRefreshRate();
      if (defaultPeakRefreshRate != null) {
        this.mDefaultPeakRefreshRate = defaultPeakRefreshRate.floatValue();
      }
      synchronized (DisplayModeDirector.this.mLock) {
        updateRefreshRateSettingLocked();
        updateLowPowerModeSettingLocked();
        updateModeSwitchingTypeSettingLocked();
      }
    }

    public void onDeviceConfigDefaultPeakRefreshRateChanged(Float f) {
      synchronized (DisplayModeDirector.this.mLock) {
        if (f == null) {
          setDefaultPeakRefreshRate(DisplayModeDirector.this.mDefaultDisplayDeviceConfig, false);
          updateRefreshRateSettingLocked();
        } else if (this.mDefaultPeakRefreshRate != f.floatValue()) {
          this.mDefaultPeakRefreshRate = f.floatValue();
          updateRefreshRateSettingLocked();
        }
      }
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri, int i) {
      synchronized (DisplayModeDirector.this.mLock) {
        if (!this.mPeakRefreshRateSetting.equals(uri) && !this.mMinRefreshRateSetting.equals(uri)) {
          if (this.mLowPowerModeSetting.equals(uri)) {
            updateLowPowerModeSettingLocked();
          } else if (this.mMatchContentFrameRateSetting.equals(uri)) {
            updateModeSwitchingTypeSettingLocked();
          }
        }
        updateRefreshRateSettingLocked();
      }
    }

    public float getDefaultRefreshRate() {
      return this.mDefaultRefreshRate;
    }

    public float getDefaultPeakRefreshRate() {
      return this.mDefaultPeakRefreshRate;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setDefaultPeakRefreshRate(
        DisplayDeviceConfig displayDeviceConfig, boolean z) {
      Float defaultPeakRefreshRate;
      int defaultPeakRefreshRate2;
      if (z) {
        try {
          defaultPeakRefreshRate =
              DisplayModeDirector.this.mDeviceConfigDisplaySettings.getDefaultPeakRefreshRate();
        } catch (Exception unused) {
        }
        if (defaultPeakRefreshRate == null) {
          if (displayDeviceConfig == null) {
            defaultPeakRefreshRate2 =
                this.mContext
                    .getResources()
                    .getInteger(R.integer.config_displayWhiteBalanceColorTemperatureMin);
          } else {
            defaultPeakRefreshRate2 = displayDeviceConfig.getDefaultPeakRefreshRate();
          }
          defaultPeakRefreshRate = Float.valueOf(defaultPeakRefreshRate2);
        }
        this.mDefaultPeakRefreshRate = defaultPeakRefreshRate.floatValue();
      }
      defaultPeakRefreshRate = null;
      if (defaultPeakRefreshRate == null) {}
      this.mDefaultPeakRefreshRate = defaultPeakRefreshRate.floatValue();
    }

    public final void updateLowPowerModeSettingLocked() {
      boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "low_power", 0) != 0;
      Vote forRenderFrameRates =
          z ? Vote.forRenderFrameRates(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 60.0f) : null;
      if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN
          || !DisplayModeDirector.this
              .mRefreshRateModeManager
              .getController()
              .checkLowRefershRateToken()) {
        DisplayModeDirector.this.mVotesStorage.updateGlobalVote(14, forRenderFrameRates);
      }
      DisplayModeDirector.this.mBrightnessObserver.onLowPowerModeEnabledLocked(z);
    }

    public final void updateRefreshRateSettingLocked() {
      ContentResolver contentResolver = this.mContext.getContentResolver();
      updateRefreshRateSettingLocked(
          Settings.System.getFloatForUser(
              contentResolver,
              "min_refresh_rate",
              DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
              contentResolver.getUserId()),
          Settings.System.getFloatForUser(
              contentResolver,
              "peak_refresh_rate",
              this.mDefaultPeakRefreshRate,
              contentResolver.getUserId()),
          this.mDefaultRefreshRate);
    }

    public final void updateRefreshRateSettingLocked(float f, float f2, float f3) {
      DisplayModeDirector.this.mVotesStorage.updateGlobalVote(
          9,
          f2 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON
              ? null
              : Vote.forRenderFrameRates(
                  DisplayPowerController2.RATE_FROM_DOZE_TO_ON, Math.max(f, f2)));
      DisplayModeDirector.this.mVotesStorage.updateGlobalVote(
          5, Vote.forRenderFrameRates(f, Float.POSITIVE_INFINITY));
      DisplayModeDirector.this.mVotesStorage.updateGlobalVote(
          0,
          f3 != DisplayPowerController2.RATE_FROM_DOZE_TO_ON
              ? Vote.forRenderFrameRates(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f3)
              : null);
      if (f2 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON
          && f3 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        Slog.e(
            "DisplayModeDirector",
            "Default and peak refresh rates are both 0. One of them should be set to a valid"
                + " value.");
        f2 = f;
      } else if (f2 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        f2 = f3;
      } else if (f3 != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        f2 = Math.min(f3, f2);
      }
      DisplayModeDirector.this.mBrightnessObserver.onRefreshRateSettingChangedLocked(f, f2);
    }

    public final void updateModeSwitchingTypeSettingLocked() {
      ContentResolver contentResolver = this.mContext.getContentResolver();
      int intForUser =
          Settings.Secure.getIntForUser(
              contentResolver,
              "match_content_frame_rate",
              DisplayModeDirector.this.mModeSwitchingType,
              contentResolver.getUserId());
      if (intForUser != DisplayModeDirector.this.mModeSwitchingType) {
        DisplayModeDirector.this.mModeSwitchingType = intForUser;
        DisplayModeDirector.this.notifyDesiredDisplayModeSpecsChangedLocked();
      }
    }

    public void dumpLocked(PrintWriter printWriter) {
      printWriter.println("  SettingsObserver");
      printWriter.println("    mDefaultRefreshRate: " + this.mDefaultRefreshRate);
      printWriter.println("    mDefaultPeakRefreshRate: " + this.mDefaultPeakRefreshRate);
    }
  }

  public final class AppRequestObserver {
    public final SparseArray mAppRequestedModeByDisplay = new SparseArray();
    public final SparseArray mAppPreferredRefreshRateRangeByDisplay = new SparseArray();

    public AppRequestObserver() {}

    public void setAppRequest(int i, int i2, float f, float f2) {
      synchronized (DisplayModeDirector.this.mLock) {
        setAppRequestedModeLocked(i, i2);
        setAppPreferredRefreshRateRangeLocked(i, f, f2);
      }
    }

    public final void setAppRequestedModeLocked(int i, int i2) {
      Vote vote;
      Vote vote2;
      Display.Mode findModeByIdLocked = findModeByIdLocked(i, i2);
      if (Objects.equals(findModeByIdLocked, this.mAppRequestedModeByDisplay.get(i))) {
        return;
      }
      if (findModeByIdLocked != null) {
        this.mAppRequestedModeByDisplay.put(i, findModeByIdLocked);
        vote = Vote.forBaseModeRefreshRate(findModeByIdLocked.getRefreshRate());
        vote2 =
            Vote.forSize(
                findModeByIdLocked.getPhysicalWidth(), findModeByIdLocked.getPhysicalHeight());
      } else {
        this.mAppRequestedModeByDisplay.remove(i);
        vote = null;
        vote2 = null;
      }
      DisplayModeDirector.this.mVotesStorage.updateVote(i, 7, vote);
      DisplayModeDirector.this.mVotesStorage.updateVote(i, 8, vote2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0023, code lost:

       if (r1.max == com.android.server.display.DisplayPowerController2.RATE_FROM_DOZE_TO_ON) goto L7;
    */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0032 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setAppPreferredRefreshRateRangeLocked(int i, float f, float f2) {
      SurfaceControl.RefreshRateRange refreshRateRange;
      Vote vote = null;
      if (f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON
          || f2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        if (f2 <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          f2 = Float.POSITIVE_INFINITY;
        }
        refreshRateRange = new SurfaceControl.RefreshRateRange(f, f2);
        if (refreshRateRange.min == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {}
        if (Objects.equals(refreshRateRange, this.mAppPreferredRefreshRateRangeByDisplay.get(i))) {
          if (refreshRateRange != null) {
            this.mAppPreferredRefreshRateRangeByDisplay.put(i, refreshRateRange);
            vote = Vote.forRenderFrameRates(refreshRateRange.min, refreshRateRange.max);
          } else {
            this.mAppPreferredRefreshRateRangeByDisplay.remove(i);
          }
          DisplayModeDirector.this.mVotesStorage.updateVote(i, 6, vote);
          return;
        }
        return;
      }
      refreshRateRange = null;
      if (Objects.equals(refreshRateRange, this.mAppPreferredRefreshRateRangeByDisplay.get(i))) {}
    }

    public final Display.Mode findModeByIdLocked(int i, int i2) {
      Display.Mode[] modeArr =
          (Display.Mode[]) DisplayModeDirector.this.mSupportedModesByDisplay.get(i);
      if (modeArr == null) {
        return null;
      }
      for (Display.Mode mode : modeArr) {
        if (mode.getModeId() == i2) {
          return mode;
        }
      }
      return null;
    }

    public final void dumpLocked(PrintWriter printWriter) {
      printWriter.println("  AppRequestObserver");
      printWriter.println("    mAppRequestedModeByDisplay:");
      for (int i = 0; i < this.mAppRequestedModeByDisplay.size(); i++) {
        printWriter.println(
            "    "
                + this.mAppRequestedModeByDisplay.keyAt(i)
                + " -> "
                + ((Display.Mode) this.mAppRequestedModeByDisplay.valueAt(i)));
      }
      printWriter.println("    mAppPreferredRefreshRateRangeByDisplay:");
      for (int i2 = 0; i2 < this.mAppPreferredRefreshRateRangeByDisplay.size(); i2++) {
        printWriter.println(
            "    "
                + this.mAppPreferredRefreshRateRangeByDisplay.keyAt(i2)
                + " -> "
                + ((SurfaceControl.RefreshRateRange)
                    this.mAppPreferredRefreshRateRangeByDisplay.valueAt(i2)));
      }
    }
  }

  public final class DisplayObserver implements DisplayManager.DisplayListener {
    public final Context mContext;
    public final Handler mHandler;
    public final VotesStorage mVotesStorage;

    public DisplayObserver(Context context, Handler handler, VotesStorage votesStorage) {
      this.mContext = context;
      this.mHandler = handler;
      this.mVotesStorage = votesStorage;
    }

    public void observe() {
      DisplayModeDirector.this.mInjector.registerDisplayListener(this, this.mHandler);
      SparseArray sparseArray = new SparseArray();
      SparseArray sparseArray2 = new SparseArray();
      DisplayInfo displayInfo = new DisplayInfo();
      for (Display display : DisplayModeDirector.this.mInjector.getDisplays()) {
        int displayId = display.getDisplayId();
        display.getDisplayInfo(displayInfo);
        sparseArray.put(displayId, displayInfo.supportedModes);
        sparseArray2.put(displayId, displayInfo.getDefaultMode());
      }
      synchronized (DisplayModeDirector.this.mLock) {
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
          DisplayModeDirector.this.mSupportedModesByDisplay.put(
              sparseArray.keyAt(i), (Display.Mode[]) sparseArray.valueAt(i));
          DisplayModeDirector.this.mDefaultModeByDisplay.put(
              sparseArray2.keyAt(i), (Display.Mode) sparseArray2.valueAt(i));
        }
      }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
      DisplayInfo displayInfo = getDisplayInfo(i);
      updateDisplayModes(i, displayInfo);
      updateLayoutLimitedFrameRate(i, displayInfo);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
      synchronized (DisplayModeDirector.this.mLock) {
        DisplayModeDirector.this.mSupportedModesByDisplay.remove(i);
        DisplayModeDirector.this.mDefaultModeByDisplay.remove(i);
      }
      updateLayoutLimitedFrameRate(i, null);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
      DisplayInfo displayInfo = getDisplayInfo(i);
      updateDisplayModes(i, displayInfo);
      updateLayoutLimitedFrameRate(i, displayInfo);
      if (CoreRune.FW_VRR_REFRESH_RATE_MODE && i == 0) {
        DisplayModeDirector.this.mRefreshRateModeManager.updateDefaultDisplayToken();
      }
    }

    public final DisplayInfo getDisplayInfo(int i) {
      DisplayInfo displayInfo = new DisplayInfo();
      if (DisplayModeDirector.this.mInjector.getDisplayInfo(i, displayInfo)) {
        return displayInfo;
      }
      return null;
    }

    public final void updateLayoutLimitedFrameRate(int i, DisplayInfo displayInfo) {
      SurfaceControl.RefreshRateRange refreshRateRange;
      this.mVotesStorage.updateVote(
          i,
          13,
          (displayInfo == null || (refreshRateRange = displayInfo.layoutLimitedRefreshRate) == null)
              ? null
              : Vote.forPhysicalRefreshRates(refreshRateRange.min, refreshRateRange.max));
    }

    public final void updateDisplayModes(int i, DisplayInfo displayInfo) {
      boolean z;
      if (displayInfo == null) {
        return;
      }
      synchronized (DisplayModeDirector.this.mLock) {
        boolean z2 = true;
        if (Arrays.equals(
            (Object[]) DisplayModeDirector.this.mSupportedModesByDisplay.get(i),
            displayInfo.supportedModes)) {
          z = false;
        } else {
          DisplayModeDirector.this.mSupportedModesByDisplay.put(i, displayInfo.supportedModes);
          z = true;
        }
        if (Objects.equals(
            DisplayModeDirector.this.mDefaultModeByDisplay.get(i), displayInfo.getDefaultMode())) {
          z2 = z;
        } else {
          DisplayModeDirector.this.mDefaultModeByDisplay.put(i, displayInfo.getDefaultMode());
        }
        if (z2) {
          DisplayModeDirector.this.notifyDesiredDisplayModeSpecsChangedLocked();
        }
      }
    }
  }

  public class BrightnessObserver implements DisplayManager.DisplayListener {
    public AmbientFilter mAmbientFilter;
    public final Context mContext;
    public final Handler mHandler;
    public int[] mHighAmbientBrightnessThresholds;
    public int[] mHighDisplayBrightnessThresholds;
    public final Injector mInjector;
    public Sensor mLightSensor;
    public String mLightSensorName;
    public String mLightSensorType;
    public boolean mLoggingEnabled;
    public int[] mLowAmbientBrightnessThresholds;
    public int[] mLowDisplayBrightnessThresholds;
    public int mRefreshRateInHighZone;
    public int mRefreshRateInLowZone;
    public Sensor mRegisteredLightSensor;
    public SensorManager mSensorManager;
    public boolean mShouldObserveAmbientHighChange;
    public boolean mShouldObserveAmbientLowChange;
    public boolean mShouldObserveDisplayHighChange;
    public boolean mShouldObserveDisplayLowChange;
    public final LightSensorEventListener mLightSensorListener = new LightSensorEventListener();
    public float mAmbientLux = -1.0f;
    public int mBrightness = -1;
    public int mDefaultDisplayState = 0;
    public boolean mRefreshRateChangeable = false;
    public boolean mLowPowerModeEnabled = false;

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {}

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {}

    public BrightnessObserver(Context context, Handler handler, Injector injector) {
      this.mContext = context;
      this.mHandler = handler;
      this.mInjector = injector;
      updateBlockingZoneThresholds(null, false);
      this.mRefreshRateInHighZone =
          context
              .getResources()
              .getInteger(R.integer.config_letterboxDefaultPositionForBookModeReachability);
    }

    public void updateBlockingZoneThresholds(DisplayDeviceConfig displayDeviceConfig, boolean z) {
      loadLowBrightnessThresholds(displayDeviceConfig, z);
      loadHighBrightnessThresholds(displayDeviceConfig, z);
    }

    public int[] getLowDisplayBrightnessThreshold() {
      return this.mLowDisplayBrightnessThresholds;
    }

    public int[] getLowAmbientBrightnessThreshold() {
      return this.mLowAmbientBrightnessThresholds;
    }

    public int[] getHighDisplayBrightnessThreshold() {
      return this.mHighDisplayBrightnessThresholds;
    }

    public int[] getHighAmbientBrightnessThreshold() {
      return this.mHighAmbientBrightnessThresholds;
    }

    public int getRefreshRateInHighZone() {
      return this.mRefreshRateInHighZone;
    }

    public int getRefreshRateInLowZone() {
      return this.mRefreshRateInLowZone;
    }

    public final void loadLowBrightnessThresholds(
        final DisplayDeviceConfig displayDeviceConfig, boolean z) {
      loadRefreshRateInHighZone(displayDeviceConfig, z);
      loadRefreshRateInLowZone(displayDeviceConfig, z);
      this.mLowDisplayBrightnessThresholds =
          loadBrightnessThresholds(
              new Callable() { // from class:
                               // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda12
                @Override // java.util.concurrent.Callable
                public final Object call() {
                  int[] lambda$loadLowBrightnessThresholds$0;
                  lambda$loadLowBrightnessThresholds$0 =
                      DisplayModeDirector.BrightnessObserver.this
                          .lambda$loadLowBrightnessThresholds$0();
                  return lambda$loadLowBrightnessThresholds$0;
                }
              },
              new Callable() { // from class:
                               // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda13
                @Override // java.util.concurrent.Callable
                public final Object call() {
                  int[] lowDisplayBrightnessThresholds;
                  lowDisplayBrightnessThresholds =
                      DisplayDeviceConfig.this.getLowDisplayBrightnessThresholds();
                  return lowDisplayBrightnessThresholds;
                }
              },
              R.array.config_requestVibrationParamsForUsages,
              displayDeviceConfig,
              z);
      int[] loadBrightnessThresholds =
          loadBrightnessThresholds(
              new Callable() { // from class:
                               // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda14
                @Override // java.util.concurrent.Callable
                public final Object call() {
                  int[] lambda$loadLowBrightnessThresholds$2;
                  lambda$loadLowBrightnessThresholds$2 =
                      DisplayModeDirector.BrightnessObserver.this
                          .lambda$loadLowBrightnessThresholds$2();
                  return lambda$loadLowBrightnessThresholds$2;
                }
              },
              new Callable() { // from class:
                               // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda15
                @Override // java.util.concurrent.Callable
                public final Object call() {
                  int[] lowAmbientBrightnessThresholds;
                  lowAmbientBrightnessThresholds =
                      DisplayDeviceConfig.this.getLowAmbientBrightnessThresholds();
                  return lowAmbientBrightnessThresholds;
                }
              },
              R.array.config_minimumBrightnessCurveLux,
              displayDeviceConfig,
              z);
      this.mLowAmbientBrightnessThresholds = loadBrightnessThresholds;
      if (this.mLowDisplayBrightnessThresholds.length == loadBrightnessThresholds.length) {
        return;
      }
      throw new RuntimeException(
          "display low brightness threshold array and ambient brightness threshold array have"
              + " different length: displayBrightnessThresholds="
              + Arrays.toString(this.mLowDisplayBrightnessThresholds)
              + ", ambientBrightnessThresholds="
              + Arrays.toString(this.mLowAmbientBrightnessThresholds));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$loadLowBrightnessThresholds$0() {
      return DisplayModeDirector.this.mDeviceConfigDisplaySettings
          .getLowDisplayBrightnessThresholds();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$loadLowBrightnessThresholds$2() {
      return DisplayModeDirector.this.mDeviceConfigDisplaySettings
          .getLowAmbientBrightnessThresholds();
    }

    public final void loadRefreshRateInLowZone(DisplayDeviceConfig displayDeviceConfig, boolean z) {
      int defaultLowBlockingZoneRefreshRate;
      if (displayDeviceConfig == null) {
        defaultLowBlockingZoneRefreshRate =
            this.mContext
                .getResources()
                .getInteger(R.integer.config_displayWhiteBalanceTransitionTime);
      } else {
        defaultLowBlockingZoneRefreshRate =
            displayDeviceConfig.getDefaultLowBlockingZoneRefreshRate();
      }
      if (z) {
        try {
          defaultLowBlockingZoneRefreshRate =
              DisplayModeDirector.this.mDeviceConfig.getInt(
                  "display_manager", "refresh_rate_in_zone", defaultLowBlockingZoneRefreshRate);
        } catch (Exception unused) {
        }
      }
      this.mRefreshRateInLowZone = defaultLowBlockingZoneRefreshRate;
    }

    public final void loadRefreshRateInHighZone(
        DisplayDeviceConfig displayDeviceConfig, boolean z) {
      int defaultHighBlockingZoneRefreshRate;
      if (displayDeviceConfig == null) {
        defaultHighBlockingZoneRefreshRate =
            this.mContext
                .getResources()
                .getInteger(R.integer.config_letterboxDefaultPositionForBookModeReachability);
      } else {
        defaultHighBlockingZoneRefreshRate =
            displayDeviceConfig.getDefaultHighBlockingZoneRefreshRate();
      }
      if (z) {
        try {
          defaultHighBlockingZoneRefreshRate =
              DisplayModeDirector.this.mDeviceConfig.getInt(
                  "display_manager",
                  "refresh_rate_in_high_zone",
                  defaultHighBlockingZoneRefreshRate);
        } catch (Exception unused) {
        }
      }
      this.mRefreshRateInHighZone = defaultHighBlockingZoneRefreshRate;
    }

    public final void loadHighBrightnessThresholds(
        final DisplayDeviceConfig displayDeviceConfig, boolean z) {
      this.mHighDisplayBrightnessThresholds =
          loadBrightnessThresholds(
              new Callable() { // from class:
                               // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda4
                @Override // java.util.concurrent.Callable
                public final Object call() {
                  int[] lambda$loadHighBrightnessThresholds$4;
                  lambda$loadHighBrightnessThresholds$4 =
                      DisplayModeDirector.BrightnessObserver.this
                          .lambda$loadHighBrightnessThresholds$4();
                  return lambda$loadHighBrightnessThresholds$4;
                }
              },
              new Callable() { // from class:
                               // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda5
                @Override // java.util.concurrent.Callable
                public final Object call() {
                  int[] highDisplayBrightnessThresholds;
                  highDisplayBrightnessThresholds =
                      DisplayDeviceConfig.this.getHighDisplayBrightnessThresholds();
                  return highDisplayBrightnessThresholds;
                }
              },
              R.array.resolver_target_actions_pin,
              displayDeviceConfig,
              z);
      int[] loadBrightnessThresholds =
          loadBrightnessThresholds(
              new Callable() { // from class:
                               // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda6
                @Override // java.util.concurrent.Callable
                public final Object call() {
                  int[] lambda$loadHighBrightnessThresholds$6;
                  lambda$loadHighBrightnessThresholds$6 =
                      DisplayModeDirector.BrightnessObserver.this
                          .lambda$loadHighBrightnessThresholds$6();
                  return lambda$loadHighBrightnessThresholds$6;
                }
              },
              new Callable() { // from class:
                               // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda7
                @Override // java.util.concurrent.Callable
                public final Object call() {
                  int[] highAmbientBrightnessThresholds;
                  highAmbientBrightnessThresholds =
                      DisplayDeviceConfig.this.getHighAmbientBrightnessThresholds();
                  return highAmbientBrightnessThresholds;
                }
              },
              R.array.required_apps_managed_user,
              displayDeviceConfig,
              z);
      this.mHighAmbientBrightnessThresholds = loadBrightnessThresholds;
      if (this.mHighDisplayBrightnessThresholds.length == loadBrightnessThresholds.length) {
        return;
      }
      throw new RuntimeException(
          "display high brightness threshold array and ambient brightness threshold array have"
              + " different length: displayBrightnessThresholds="
              + Arrays.toString(this.mHighDisplayBrightnessThresholds)
              + ", ambientBrightnessThresholds="
              + Arrays.toString(this.mHighAmbientBrightnessThresholds));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$loadHighBrightnessThresholds$4() {
      return DisplayModeDirector.this.mDeviceConfigDisplaySettings
          .getHighDisplayBrightnessThresholds();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$loadHighBrightnessThresholds$6() {
      return DisplayModeDirector.this.mDeviceConfigDisplaySettings
          .getHighAmbientBrightnessThresholds();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:4:0x000c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int[] loadBrightnessThresholds(
        Callable callable,
        Callable callable2,
        int i,
        DisplayDeviceConfig displayDeviceConfig,
        boolean z) {
      int[] iArr;
      int[] iArr2;
      if (z) {
        try {
          iArr = (int[]) callable.call();
        } catch (Exception unused) {
        }
        if (iArr == null) {
          return iArr;
        }
        try {
          if (displayDeviceConfig == null) {
            iArr2 = this.mContext.getResources().getIntArray(i);
          } else {
            iArr2 = (int[]) callable2.call();
          }
          iArr = iArr2;
          return iArr;
        } catch (Exception e) {
          Slog.e("DisplayModeDirector", "Unexpectedly failed to load display brightness threshold");
          e.printStackTrace();
          return iArr;
        }
      }
      iArr = null;
      if (iArr == null) {}
    }

    public int[] getLowDisplayBrightnessThresholds() {
      return this.mLowDisplayBrightnessThresholds;
    }

    public int[] getLowAmbientBrightnessThresholds() {
      return this.mLowAmbientBrightnessThresholds;
    }

    public final void observe(SensorManager sensorManager) {
      this.mSensorManager = sensorManager;
      this.mBrightness = getBrightness(0);
      int[] lowDisplayBrightnessThresholds =
          DisplayModeDirector.this.mDeviceConfigDisplaySettings.getLowDisplayBrightnessThresholds();
      int[] lowAmbientBrightnessThresholds =
          DisplayModeDirector.this.mDeviceConfigDisplaySettings.getLowAmbientBrightnessThresholds();
      if (lowDisplayBrightnessThresholds != null
          && lowAmbientBrightnessThresholds != null
          && lowDisplayBrightnessThresholds.length == lowAmbientBrightnessThresholds.length) {
        this.mLowDisplayBrightnessThresholds = lowDisplayBrightnessThresholds;
        this.mLowAmbientBrightnessThresholds = lowAmbientBrightnessThresholds;
      }
      int[] highDisplayBrightnessThresholds =
          DisplayModeDirector.this.mDeviceConfigDisplaySettings
              .getHighDisplayBrightnessThresholds();
      int[] highAmbientBrightnessThresholds =
          DisplayModeDirector.this.mDeviceConfigDisplaySettings
              .getHighAmbientBrightnessThresholds();
      if (highDisplayBrightnessThresholds != null
          && highAmbientBrightnessThresholds != null
          && highDisplayBrightnessThresholds.length == highAmbientBrightnessThresholds.length) {
        this.mHighDisplayBrightnessThresholds = highDisplayBrightnessThresholds;
        this.mHighAmbientBrightnessThresholds = highAmbientBrightnessThresholds;
      }
      int refreshRateInLowZone =
          DisplayModeDirector.this.mDeviceConfigDisplaySettings.getRefreshRateInLowZone();
      if (refreshRateInLowZone != -1) {
        this.mRefreshRateInLowZone = refreshRateInLowZone;
      }
      int refreshRateInHighZone =
          DisplayModeDirector.this.mDeviceConfigDisplaySettings.getRefreshRateInHighZone();
      if (refreshRateInHighZone != -1) {
        this.mRefreshRateInHighZone = refreshRateInHighZone;
      }
      restartObserver();
      DisplayModeDirector.this.mDeviceConfigDisplaySettings.startListening();
      this.mInjector.registerDisplayListener(this, this.mHandler, 12L);
    }

    public final void setLoggingEnabled(boolean z) {
      if (this.mLoggingEnabled == z) {
        return;
      }
      this.mLoggingEnabled = z;
      this.mLightSensorListener.setLoggingEnabled(z);
    }

    public void onRefreshRateSettingChangedLocked(float f, float f2) {
      boolean z = f2 - f > 1.0f && f2 > 60.0f;
      if (this.mRefreshRateChangeable != z) {
        this.mRefreshRateChangeable = z;
        updateSensorStatus();
        if (z) {
          return;
        }
        DisplayModeDirector.this.mVotesStorage.updateGlobalVote(3, null);
        DisplayModeDirector.this.mVotesStorage.updateGlobalVote(15, null);
      }
    }

    public final void onLowPowerModeEnabledLocked(boolean z) {
      if (this.mLowPowerModeEnabled != z) {
        this.mLowPowerModeEnabled = z;
        updateSensorStatus();
      }
    }

    public final void onDeviceConfigLowBrightnessThresholdsChanged(int[] iArr, int[] iArr2) {
      final DisplayDeviceConfig displayDeviceConfig;
      if (iArr != null && iArr2 != null && iArr.length == iArr2.length) {
        this.mLowDisplayBrightnessThresholds = iArr;
        this.mLowAmbientBrightnessThresholds = iArr2;
      } else {
        synchronized (DisplayModeDirector.this.mLock) {
          displayDeviceConfig = DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
        }
        this.mLowDisplayBrightnessThresholds =
            loadBrightnessThresholds(
                new Callable() { // from class:
                                 // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda8
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    int[] lambda$onDeviceConfigLowBrightnessThresholdsChanged$8;
                    lambda$onDeviceConfigLowBrightnessThresholdsChanged$8 =
                        DisplayModeDirector.BrightnessObserver.this
                            .lambda$onDeviceConfigLowBrightnessThresholdsChanged$8();
                    return lambda$onDeviceConfigLowBrightnessThresholdsChanged$8;
                  }
                },
                new Callable() { // from class:
                                 // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda9
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    int[] lowDisplayBrightnessThresholds;
                    lowDisplayBrightnessThresholds =
                        DisplayDeviceConfig.this.getLowDisplayBrightnessThresholds();
                    return lowDisplayBrightnessThresholds;
                  }
                },
                R.array.config_requestVibrationParamsForUsages,
                displayDeviceConfig,
                false);
        this.mLowAmbientBrightnessThresholds =
            loadBrightnessThresholds(
                new Callable() { // from class:
                                 // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda10
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    int[] lambda$onDeviceConfigLowBrightnessThresholdsChanged$10;
                    lambda$onDeviceConfigLowBrightnessThresholdsChanged$10 =
                        DisplayModeDirector.BrightnessObserver.this
                            .lambda$onDeviceConfigLowBrightnessThresholdsChanged$10();
                    return lambda$onDeviceConfigLowBrightnessThresholdsChanged$10;
                  }
                },
                new Callable() { // from class:
                                 // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda11
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    int[] lowAmbientBrightnessThresholds;
                    lowAmbientBrightnessThresholds =
                        DisplayDeviceConfig.this.getLowAmbientBrightnessThresholds();
                    return lowAmbientBrightnessThresholds;
                  }
                },
                R.array.config_minimumBrightnessCurveLux,
                displayDeviceConfig,
                false);
      }
      restartObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$onDeviceConfigLowBrightnessThresholdsChanged$8() {
      return DisplayModeDirector.this.mDeviceConfigDisplaySettings
          .getLowDisplayBrightnessThresholds();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$onDeviceConfigLowBrightnessThresholdsChanged$10() {
      return DisplayModeDirector.this.mDeviceConfigDisplaySettings
          .getLowAmbientBrightnessThresholds();
    }

    public void onDeviceConfigRefreshRateInLowZoneChanged(int i) {
      if (i == -1) {
        synchronized (DisplayModeDirector.this.mLock) {
          loadRefreshRateInLowZone(DisplayModeDirector.this.mDefaultDisplayDeviceConfig, false);
        }
        restartObserver();
        return;
      }
      if (i != this.mRefreshRateInLowZone) {
        this.mRefreshRateInLowZone = i;
        restartObserver();
      }
    }

    public final void onDeviceConfigHighBrightnessThresholdsChanged(int[] iArr, int[] iArr2) {
      final DisplayDeviceConfig displayDeviceConfig;
      if (iArr != null && iArr2 != null && iArr.length == iArr2.length) {
        this.mHighDisplayBrightnessThresholds = iArr;
        this.mHighAmbientBrightnessThresholds = iArr2;
      } else {
        synchronized (DisplayModeDirector.this.mLock) {
          displayDeviceConfig = DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
        }
        this.mHighDisplayBrightnessThresholds =
            loadBrightnessThresholds(
                new Callable() { // from class:
                                 // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    int[] lambda$onDeviceConfigHighBrightnessThresholdsChanged$12;
                    lambda$onDeviceConfigHighBrightnessThresholdsChanged$12 =
                        DisplayModeDirector.BrightnessObserver.this
                            .lambda$onDeviceConfigHighBrightnessThresholdsChanged$12();
                    return lambda$onDeviceConfigHighBrightnessThresholdsChanged$12;
                  }
                },
                new Callable() { // from class:
                                 // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    int[] highDisplayBrightnessThresholds;
                    highDisplayBrightnessThresholds =
                        DisplayDeviceConfig.this.getHighDisplayBrightnessThresholds();
                    return highDisplayBrightnessThresholds;
                  }
                },
                R.array.resolver_target_actions_pin,
                displayDeviceConfig,
                false);
        this.mHighAmbientBrightnessThresholds =
            loadBrightnessThresholds(
                new Callable() { // from class:
                                 // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    int[] lambda$onDeviceConfigHighBrightnessThresholdsChanged$14;
                    lambda$onDeviceConfigHighBrightnessThresholdsChanged$14 =
                        DisplayModeDirector.BrightnessObserver.this
                            .lambda$onDeviceConfigHighBrightnessThresholdsChanged$14();
                    return lambda$onDeviceConfigHighBrightnessThresholdsChanged$14;
                  }
                },
                new Callable() { // from class:
                                 // com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda3
                  @Override // java.util.concurrent.Callable
                  public final Object call() {
                    int[] highAmbientBrightnessThresholds;
                    highAmbientBrightnessThresholds =
                        DisplayDeviceConfig.this.getHighAmbientBrightnessThresholds();
                    return highAmbientBrightnessThresholds;
                  }
                },
                R.array.required_apps_managed_user,
                displayDeviceConfig,
                false);
      }
      restartObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$onDeviceConfigHighBrightnessThresholdsChanged$12() {
      return DisplayModeDirector.this.mDeviceConfigDisplaySettings
          .getHighDisplayBrightnessThresholds();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int[] lambda$onDeviceConfigHighBrightnessThresholdsChanged$14() {
      return DisplayModeDirector.this.mDeviceConfigDisplaySettings
          .getHighAmbientBrightnessThresholds();
    }

    public void onDeviceConfigRefreshRateInHighZoneChanged(int i) {
      if (i == -1) {
        synchronized (DisplayModeDirector.this.mLock) {
          loadRefreshRateInHighZone(DisplayModeDirector.this.mDefaultDisplayDeviceConfig, false);
        }
        restartObserver();
        return;
      }
      if (i != this.mRefreshRateInHighZone) {
        this.mRefreshRateInHighZone = i;
        restartObserver();
      }
    }

    public void dumpLocked(PrintWriter printWriter) {
      printWriter.println("  BrightnessObserver");
      printWriter.println("    mAmbientLux: " + this.mAmbientLux);
      printWriter.println("    mBrightness: " + this.mBrightness);
      printWriter.println("    mDefaultDisplayState: " + this.mDefaultDisplayState);
      printWriter.println("    mLowPowerModeEnabled: " + this.mLowPowerModeEnabled);
      printWriter.println("    mRefreshRateChangeable: " + this.mRefreshRateChangeable);
      printWriter.println(
          "    mShouldObserveDisplayLowChange: " + this.mShouldObserveDisplayLowChange);
      printWriter.println(
          "    mShouldObserveAmbientLowChange: " + this.mShouldObserveAmbientLowChange);
      printWriter.println("    mRefreshRateInLowZone: " + this.mRefreshRateInLowZone);
      for (int i : this.mLowDisplayBrightnessThresholds) {
        printWriter.println("    mDisplayLowBrightnessThreshold: " + i);
      }
      for (int i2 : this.mLowAmbientBrightnessThresholds) {
        printWriter.println("    mAmbientLowBrightnessThreshold: " + i2);
      }
      printWriter.println(
          "    mShouldObserveDisplayHighChange: " + this.mShouldObserveDisplayHighChange);
      printWriter.println(
          "    mShouldObserveAmbientHighChange: " + this.mShouldObserveAmbientHighChange);
      printWriter.println("    mRefreshRateInHighZone: " + this.mRefreshRateInHighZone);
      int[] iArr = this.mHighDisplayBrightnessThresholds;
      int length = iArr.length;
      for (int i3 = 0; i3 < length; i3++) {
        printWriter.println("    mDisplayHighBrightnessThresholds: " + iArr[i3]);
      }
      for (int i4 : this.mHighAmbientBrightnessThresholds) {
        printWriter.println("    mAmbientHighBrightnessThresholds: " + i4);
      }
      printWriter.println("    mRegisteredLightSensor: " + this.mRegisteredLightSensor);
      printWriter.println("    mLightSensor: " + this.mLightSensor);
      printWriter.println("    mLightSensorName: " + this.mLightSensorName);
      printWriter.println("    mLightSensorType: " + this.mLightSensorType);
      this.mLightSensorListener.dumpLocked(printWriter);
      if (this.mAmbientFilter != null) {
        this.mAmbientFilter.dump(new IndentingPrintWriter(printWriter, "    "));
      }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
      if (i == 0) {
        updateDefaultDisplayState();
        if (CoreRune.FW_VRR_SEAMLESS) {
          return;
        }
        int brightness = getBrightness(i);
        synchronized (DisplayModeDirector.this.mLock) {
          if (brightness != this.mBrightness) {
            this.mBrightness = brightness;
            onBrightnessChangedLocked();
          }
        }
      }
    }

    public final void restartObserver() {
      if (this.mRefreshRateInLowZone > 0) {
        this.mShouldObserveDisplayLowChange =
            hasValidThreshold(this.mLowDisplayBrightnessThresholds);
        this.mShouldObserveAmbientLowChange =
            hasValidThreshold(this.mLowAmbientBrightnessThresholds);
      } else {
        this.mShouldObserveDisplayLowChange = false;
        this.mShouldObserveAmbientLowChange = false;
      }
      if (this.mRefreshRateInHighZone > 0) {
        this.mShouldObserveDisplayHighChange =
            hasValidThreshold(this.mHighDisplayBrightnessThresholds);
        this.mShouldObserveAmbientHighChange =
            hasValidThreshold(this.mHighAmbientBrightnessThresholds);
      } else {
        this.mShouldObserveDisplayHighChange = false;
        this.mShouldObserveAmbientHighChange = false;
      }
      if (CoreRune.FW_VRR_SEAMLESS) {
        this.mShouldObserveAmbientLowChange = true;
      }
      if (this.mShouldObserveAmbientLowChange || this.mShouldObserveAmbientHighChange) {
        Sensor lightSensor = getLightSensor();
        if (CoreRune.FW_VRR_SEAMLESS
            && this.mSensorManager != null
            && (this.mLightSensorType.isEmpty() || lightSensor == null)) {
          List<Sensor> sensorList = this.mSensorManager.getSensorList(65614);
          if (!sensorList.isEmpty()) {
            lightSensor = sensorList.get(0);
          } else {
            Slog.w("DisplayModeDirector", "Failed get SEM_TYPE_LIGHT_SEAMLESS");
          }
        }
        if (lightSensor != null && lightSensor != this.mLightSensor) {
          this.mAmbientFilter =
              AmbientFilterFactory.createBrightnessFilter(
                  "DisplayModeDirector", this.mContext.getResources());
          this.mLightSensor = lightSensor;
        }
      } else {
        this.mAmbientFilter = null;
        this.mLightSensor = null;
      }
      updateSensorStatus();
      synchronized (DisplayModeDirector.this.mLock) {
        onBrightnessChangedLocked();
      }
    }

    public final void reloadLightSensor(DisplayDeviceConfig displayDeviceConfig) {
      reloadLightSensorData(displayDeviceConfig);
      restartObserver();
    }

    public final void reloadLightSensorData(DisplayDeviceConfig displayDeviceConfig) {
      if (displayDeviceConfig != null && displayDeviceConfig.getAmbientLightSensor() != null) {
        this.mLightSensorType = displayDeviceConfig.getAmbientLightSensor().type;
        this.mLightSensorName = displayDeviceConfig.getAmbientLightSensor().name;
      } else if (this.mLightSensorName == null && this.mLightSensorType == null) {
        this.mLightSensorType =
            this.mContext.getResources().getString(R.string.demo_restarting_message);
        this.mLightSensorName = "";
      }
    }

    public final Sensor getLightSensor() {
      return SensorUtils.findSensor(
          this.mSensorManager, this.mLightSensorType, this.mLightSensorName, 5);
    }

    public final boolean hasValidThreshold(int[] iArr) {
      for (int i : iArr) {
        if (i >= 0) {
          return true;
        }
      }
      return false;
    }

    public final boolean isInsideLowZone(int i, float f) {
      int i2 = 0;
      while (true) {
        int[] iArr = this.mLowDisplayBrightnessThresholds;
        if (i2 >= iArr.length) {
          return false;
        }
        int i3 = iArr[i2];
        int i4 = this.mLowAmbientBrightnessThresholds[i2];
        if (i3 < 0 || i4 < 0) {
          if (i3 >= 0) {
            if (i <= i3) {
              return true;
            }
          } else if (i4 >= 0 && f <= i4) {
            return true;
          }
        } else if (i <= i3 && f <= i4) {
          return true;
        }
        i2++;
      }
    }

    public final boolean isInsideHighZone(int i, float f) {
      int i2 = 0;
      while (true) {
        int[] iArr = this.mHighDisplayBrightnessThresholds;
        if (i2 >= iArr.length) {
          return false;
        }
        int i3 = iArr[i2];
        int i4 = this.mHighAmbientBrightnessThresholds[i2];
        if (i3 < 0 || i4 < 0) {
          if (i3 >= 0) {
            if (i >= i3) {
              return true;
            }
          } else if (i4 >= 0 && f >= i4) {
            return true;
          }
        } else if (i >= i3 && f >= i4) {
          return true;
        }
        i2++;
      }
    }

    public final void onBrightnessChangedLocked() {
      Vote vote;
      Vote vote2;
      if (this.mRefreshRateChangeable) {
        if (CoreRune.FW_VRR_SEAMLESS) {
          DisplayModeDirector.this.getRefreshRateModeManager().onBrightnessChangedLocked();
          return;
        }
        if (this.mBrightness < 0) {
          return;
        }
        if (hasValidLowZone() && isInsideLowZone(this.mBrightness, this.mAmbientLux)) {
          int i = this.mRefreshRateInLowZone;
          vote = Vote.forPhysicalRefreshRates(i, i);
          vote2 = Vote.forDisableRefreshRateSwitching();
        } else {
          vote = null;
          vote2 = null;
        }
        if (hasValidHighZone() && isInsideHighZone(this.mBrightness, this.mAmbientLux)) {
          int i2 = this.mRefreshRateInHighZone;
          vote = Vote.forPhysicalRefreshRates(i2, i2);
          vote2 = Vote.forDisableRefreshRateSwitching();
        }
        if (this.mLoggingEnabled) {
          Slog.d(
              "DisplayModeDirector",
              "Display brightness "
                  + this.mBrightness
                  + ", ambient lux "
                  + this.mAmbientLux
                  + ", Vote "
                  + vote);
        }
        DisplayModeDirector.this.mVotesStorage.updateGlobalVote(3, vote);
        DisplayModeDirector.this.mVotesStorage.updateGlobalVote(15, vote2);
      }
    }

    public final boolean hasValidLowZone() {
      return this.mRefreshRateInLowZone > 0
          && (this.mShouldObserveDisplayLowChange || this.mShouldObserveAmbientLowChange);
    }

    public final boolean hasValidHighZone() {
      return this.mRefreshRateInHighZone > 0
          && (this.mShouldObserveDisplayHighChange || this.mShouldObserveAmbientHighChange);
    }

    public final void updateDefaultDisplayState() {
      Display display = this.mInjector.getDisplay(0);
      if (display == null) {
        return;
      }
      setDefaultDisplayState(display.getState());
    }

    public void setDefaultDisplayState(int i) {
      if (this.mLoggingEnabled) {
        Slog.d(
            "DisplayModeDirector",
            "setDefaultDisplayState: mDefaultDisplayState = "
                + this.mDefaultDisplayState
                + ", state = "
                + i);
      }
      if (this.mDefaultDisplayState != i) {
        this.mDefaultDisplayState = i;
        updateSensorStatus();
      }
    }

    public final void updateSensorStatus() {
      if (this.mSensorManager == null || this.mLightSensorListener == null) {
        return;
      }
      if (this.mLoggingEnabled) {
        Slog.d(
            "DisplayModeDirector",
            "updateSensorStatus: mShouldObserveAmbientLowChange = "
                + this.mShouldObserveAmbientLowChange
                + ", mShouldObserveAmbientHighChange = "
                + this.mShouldObserveAmbientHighChange);
        Slog.d(
            "DisplayModeDirector",
            "updateSensorStatus: mLowPowerModeEnabled = "
                + this.mLowPowerModeEnabled
                + ", mRefreshRateChangeable = "
                + this.mRefreshRateChangeable);
      }
      if ((this.mShouldObserveAmbientLowChange || this.mShouldObserveAmbientHighChange)
          && isDeviceActive()
          && (((CoreRune.FW_VRR_REFRESH_RATE_MODE
                      && RefreshRateConfig.getInstance().unsupportedNS())
                  || !this.mLowPowerModeEnabled)
              && this.mRefreshRateChangeable)) {
        registerLightSensor();
      } else {
        unregisterSensorListener();
      }
    }

    public final void registerLightSensor() {
      Sensor sensor = this.mRegisteredLightSensor;
      if (sensor == this.mLightSensor) {
        return;
      }
      if (sensor != null) {
        unregisterSensorListener();
      }
      this.mSensorManager.registerListener(
          this.mLightSensorListener, this.mLightSensor, 250000, this.mHandler);
      this.mRegisteredLightSensor = this.mLightSensor;
      if (this.mLoggingEnabled) {
        Slog.d("DisplayModeDirector", "updateSensorStatus: registerListener");
      }
    }

    public final void unregisterSensorListener() {
      this.mLightSensorListener.removeCallbacks();
      this.mSensorManager.unregisterListener(this.mLightSensorListener);
      this.mRegisteredLightSensor = null;
      if (this.mLoggingEnabled) {
        Slog.d("DisplayModeDirector", "updateSensorStatus: unregisterListener");
      }
      if (CoreRune.FW_VRR_SEAMLESS) {
        this.mAmbientLux = -1.0f;
        DisplayModeDirector.this.getRefreshRateModeManager().onLightSensorChanged(this.mAmbientLux);
      }
    }

    public final boolean isDeviceActive() {
      return this.mDefaultDisplayState == 2;
    }

    public final int getBrightness(int i) {
      BrightnessInfo brightnessInfo = this.mInjector.getBrightnessInfo(i);
      if (brightnessInfo != null) {
        return BrightnessSynchronizer.brightnessFloatToInt(brightnessInfo.adjustedBrightness);
      }
      return -1;
    }

    public final class LightSensorEventListener implements SensorEventListener {
      public final Runnable mInjectSensorEventRunnable;
      public float mLastSensorData;
      public boolean mLoggingEnabled;
      public long mTimestamp;

      @Override // android.hardware.SensorEventListener
      public void onAccuracyChanged(Sensor sensor, int i) {}

      public LightSensorEventListener() {
        this.mInjectSensorEventRunnable =
            new Runnable() { // from class:
                             // com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.1
              @Override // java.lang.Runnable
              public void run() {
                LightSensorEventListener.this.processSensorData(SystemClock.uptimeMillis());
                LightSensorEventListener lightSensorEventListener = LightSensorEventListener.this;
                if (!lightSensorEventListener.isDifferentZone(
                    lightSensorEventListener.mLastSensorData,
                    BrightnessObserver.this.mAmbientLux,
                    BrightnessObserver.this.mLowAmbientBrightnessThresholds)) {
                  LightSensorEventListener lightSensorEventListener2 =
                      LightSensorEventListener.this;
                  if (!lightSensorEventListener2.isDifferentZone(
                      lightSensorEventListener2.mLastSensorData,
                      BrightnessObserver.this.mAmbientLux,
                      BrightnessObserver.this.mHighAmbientBrightnessThresholds)) {
                    return;
                  }
                }
                BrightnessObserver.this.mHandler.postDelayed(
                    LightSensorEventListener.this.mInjectSensorEventRunnable, 250L);
              }
            };
      }

      public void dumpLocked(PrintWriter printWriter) {
        printWriter.println("    mLastSensorData: " + this.mLastSensorData);
        printWriter.println("    mTimestamp: " + formatTimestamp(this.mTimestamp));
      }

      public void setLoggingEnabled(boolean z) {
        if (this.mLoggingEnabled == z) {
          return;
        }
        this.mLoggingEnabled = z;
      }

      @Override // android.hardware.SensorEventListener
      public void onSensorChanged(SensorEvent sensorEvent) {
        this.mLastSensorData = sensorEvent.values[0];
        if (this.mLoggingEnabled) {
          Slog.d("DisplayModeDirector", "On sensor changed: " + this.mLastSensorData);
        }
        if (CoreRune.FW_VRR_SEAMLESS) {
          BrightnessObserver.this.mAmbientLux = this.mLastSensorData;
          if (CoreRune.SAFE_DEBUG) {
            Slog.d(
                "DisplayModeDirector",
                "onSensorChanged mAmbientLux = " + BrightnessObserver.this.mAmbientLux);
          }
          DisplayModeDirector.this
              .getRefreshRateModeManager()
              .onLightSensorChanged(BrightnessObserver.this.mAmbientLux);
          return;
        }
        boolean isDifferentZone =
            isDifferentZone(
                this.mLastSensorData,
                BrightnessObserver.this.mAmbientLux,
                BrightnessObserver.this.mLowAmbientBrightnessThresholds);
        boolean isDifferentZone2 =
            isDifferentZone(
                this.mLastSensorData,
                BrightnessObserver.this.mAmbientLux,
                BrightnessObserver.this.mHighAmbientBrightnessThresholds);
        if (((isDifferentZone && this.mLastSensorData < BrightnessObserver.this.mAmbientLux)
                || (isDifferentZone2 && this.mLastSensorData > BrightnessObserver.this.mAmbientLux))
            && BrightnessObserver.this.mAmbientFilter != null) {
          BrightnessObserver.this.mAmbientFilter.clear();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mTimestamp = System.currentTimeMillis();
        if (BrightnessObserver.this.mAmbientFilter != null) {
          BrightnessObserver.this.mAmbientFilter.addValue(uptimeMillis, this.mLastSensorData);
        }
        BrightnessObserver.this.mHandler.removeCallbacks(this.mInjectSensorEventRunnable);
        processSensorData(uptimeMillis);
        if ((!isDifferentZone || this.mLastSensorData <= BrightnessObserver.this.mAmbientLux)
            && (!isDifferentZone2 || this.mLastSensorData >= BrightnessObserver.this.mAmbientLux)) {
          return;
        }
        BrightnessObserver.this.mHandler.postDelayed(this.mInjectSensorEventRunnable, 250L);
      }

      public void removeCallbacks() {
        BrightnessObserver.this.mHandler.removeCallbacks(this.mInjectSensorEventRunnable);
      }

      public final String formatTimestamp(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(new Date(j));
      }

      public final void processSensorData(long j) {
        if (BrightnessObserver.this.mAmbientFilter != null) {
          BrightnessObserver brightnessObserver = BrightnessObserver.this;
          brightnessObserver.mAmbientLux = brightnessObserver.mAmbientFilter.getEstimate(j);
        } else {
          BrightnessObserver.this.mAmbientLux = this.mLastSensorData;
        }
        synchronized (DisplayModeDirector.this.mLock) {
          BrightnessObserver.this.onBrightnessChangedLocked();
        }
      }

      public final boolean isDifferentZone(float f, float f2, int[] iArr) {
        for (float f3 : iArr) {
          if (f <= f3 && f2 > f3) {
            return true;
          }
          if (f > f3 && f2 <= f3) {
            return true;
          }
        }
        return false;
      }
    }
  }

  public class UdfpsObserver extends IUdfpsRefreshRateRequestCallback.Stub {
    public final SparseBooleanArray mAuthenticationPossible;
    public final SparseBooleanArray mUdfpsRefreshRateEnabled;

    public UdfpsObserver() {
      this.mUdfpsRefreshRateEnabled = new SparseBooleanArray();
      this.mAuthenticationPossible = new SparseBooleanArray();
    }

    public void observe() {
      StatusBarManagerInternal statusBarManagerInternal =
          (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
      if (statusBarManagerInternal == null
          || DisplayModeDirector.this
              .mContext
              .getResources()
              .getBoolean(R.bool.config_faceAuthSupportsSelfIllumination)) {
        return;
      }
      statusBarManagerInternal.setUdfpsRefreshRateCallback(this);
    }

    public void onRequestEnabled(int i) {
      synchronized (DisplayModeDirector.this.mLock) {
        this.mUdfpsRefreshRateEnabled.put(i, true);
        updateVoteLocked(i, true, 18);
      }
    }

    public void onRequestDisabled(int i) {
      synchronized (DisplayModeDirector.this.mLock) {
        this.mUdfpsRefreshRateEnabled.put(i, false);
        updateVoteLocked(i, false, 18);
      }
    }

    public void onAuthenticationPossible(int i, boolean z) {
      synchronized (DisplayModeDirector.this.mLock) {
        this.mAuthenticationPossible.put(i, z);
        updateVoteLocked(i, z, 12);
      }
    }

    public final void updateVoteLocked(int i, boolean z, int i2) {
      Vote vote;
      if (z) {
        float maxRefreshRateLocked = DisplayModeDirector.this.getMaxRefreshRateLocked(i);
        vote = Vote.forPhysicalRefreshRates(maxRefreshRateLocked, maxRefreshRateLocked);
      } else {
        vote = null;
      }
      DisplayModeDirector.this.mVotesStorage.updateVote(i, i2, vote);
    }

    public void dumpLocked(PrintWriter printWriter) {
      printWriter.println("  UdfpsObserver");
      printWriter.println("    mUdfpsRefreshRateEnabled: ");
      for (int i = 0; i < this.mUdfpsRefreshRateEnabled.size(); i++) {
        printWriter.println(
            "      Display "
                + this.mUdfpsRefreshRateEnabled.keyAt(i)
                + ": "
                + (this.mUdfpsRefreshRateEnabled.valueAt(i) ? "enabled" : "disabled"));
      }
      printWriter.println("    mAuthenticationPossible: ");
      for (int i2 = 0; i2 < this.mAuthenticationPossible.size(); i2++) {
        printWriter.println(
            "      Display "
                + this.mAuthenticationPossible.keyAt(i2)
                + ": "
                + (this.mAuthenticationPossible.valueAt(i2) ? "possible" : "impossible"));
      }
    }
  }

  public final class SensorObserver
      implements SensorManagerInternal.ProximityActiveListener, DisplayManager.DisplayListener {
    public final Context mContext;
    public DisplayManager mDisplayManager;
    public DisplayManagerInternal mDisplayManagerInternal;
    public final Injector mInjector;
    public final VotesStorage mVotesStorage;
    public final String mProximitySensorName = null;
    public final String mProximitySensorType = "android.sensor.proximity";
    public final SparseBooleanArray mDozeStateByDisplay = new SparseBooleanArray();
    public final Object mSensorObserverLock = new Object();
    public boolean mIsProxActive = false;

    public SensorObserver(Context context, VotesStorage votesStorage, Injector injector) {
      this.mContext = context;
      this.mVotesStorage = votesStorage;
      this.mInjector = injector;
    }

    @Override // com.android.server.sensors.SensorManagerInternal.ProximityActiveListener
    public void onProximityActive(boolean z) {
      synchronized (this.mSensorObserverLock) {
        if (this.mIsProxActive != z) {
          this.mIsProxActive = z;
          recalculateVotesLocked();
        }
      }
    }

    public void observe() {
      this.mDisplayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
      this.mDisplayManagerInternal =
          (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
      ((SensorManagerInternal) LocalServices.getService(SensorManagerInternal.class))
          .addProximityActiveListener(BackgroundThread.getExecutor(), this);
      synchronized (this.mSensorObserverLock) {
        for (Display display : this.mInjector.getDisplays()) {
          this.mDozeStateByDisplay.put(display.getDisplayId(), this.mInjector.isDozeState(display));
        }
      }
      this.mInjector.registerDisplayListener(this, BackgroundThread.getHandler(), 7L);
    }

    public final void recalculateVotesLocked() {
      SurfaceControl.RefreshRateRange refreshRateForDisplayAndSensor;
      for (Display display : this.mInjector.getDisplays()) {
        int displayId = display.getDisplayId();
        this.mVotesStorage.updateVote(
            displayId,
            17,
            (!this.mIsProxActive
                    || this.mDozeStateByDisplay.get(displayId)
                    || (refreshRateForDisplayAndSensor =
                            this.mDisplayManagerInternal.getRefreshRateForDisplayAndSensor(
                                displayId, this.mProximitySensorName, "android.sensor.proximity"))
                        == null)
                ? null
                : Vote.forPhysicalRefreshRates(
                    refreshRateForDisplayAndSensor.min, refreshRateForDisplayAndSensor.max));
      }
    }

    public void dump(PrintWriter printWriter) {
      printWriter.println("  SensorObserver");
      synchronized (this.mSensorObserverLock) {
        printWriter.println("    mIsProxActive=" + this.mIsProxActive);
        printWriter.println("    mDozeStateByDisplay:");
        for (int i = 0; i < this.mDozeStateByDisplay.size(); i++) {
          printWriter.println(
              "      "
                  + this.mDozeStateByDisplay.keyAt(i)
                  + " -> "
                  + this.mDozeStateByDisplay.valueAt(i));
        }
      }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
      Injector injector = this.mInjector;
      boolean isDozeState = injector.isDozeState(injector.getDisplay(i));
      synchronized (this.mSensorObserverLock) {
        this.mDozeStateByDisplay.put(i, isDozeState);
        recalculateVotesLocked();
      }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
      boolean z = this.mDozeStateByDisplay.get(i);
      synchronized (this.mSensorObserverLock) {
        SparseBooleanArray sparseBooleanArray = this.mDozeStateByDisplay;
        Injector injector = this.mInjector;
        sparseBooleanArray.put(i, injector.isDozeState(injector.getDisplay(i)));
        if (z != this.mDozeStateByDisplay.get(i)) {
          recalculateVotesLocked();
        }
      }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
      synchronized (this.mSensorObserverLock) {
        this.mDozeStateByDisplay.delete(i);
        recalculateVotesLocked();
      }
    }
  }

  public class HbmObserver implements DisplayManager.DisplayListener {
    public final DeviceConfigDisplaySettings mDeviceConfigDisplaySettings;
    public DisplayManagerInternal mDisplayManagerInternal;
    public final Handler mHandler;
    public final Injector mInjector;
    public int mRefreshRateInHbmHdr;
    public int mRefreshRateInHbmSunlight;
    public final VotesStorage mVotesStorage;
    public final SparseIntArray mHbmMode = new SparseIntArray();
    public final SparseBooleanArray mHbmActive = new SparseBooleanArray();

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {}

    public HbmObserver(
        Injector injector,
        VotesStorage votesStorage,
        Handler handler,
        DeviceConfigDisplaySettings deviceConfigDisplaySettings) {
      this.mInjector = injector;
      this.mVotesStorage = votesStorage;
      this.mHandler = handler;
      this.mDeviceConfigDisplaySettings = deviceConfigDisplaySettings;
    }

    public void setupHdrRefreshRates(DisplayDeviceConfig displayDeviceConfig) {
      this.mRefreshRateInHbmHdr =
          this.mDeviceConfigDisplaySettings.getRefreshRateInHbmHdr(displayDeviceConfig);
      this.mRefreshRateInHbmSunlight =
          this.mDeviceConfigDisplaySettings.getRefreshRateInHbmSunlight(displayDeviceConfig);
    }

    public void observe() {
      synchronized (DisplayModeDirector.this.mLock) {
        setupHdrRefreshRates(DisplayModeDirector.this.mDefaultDisplayDeviceConfig);
      }
      this.mDisplayManagerInternal =
          (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
      this.mInjector.registerDisplayListener(this, this.mHandler, 10L);
    }

    public int getRefreshRateInHbmSunlight() {
      return this.mRefreshRateInHbmSunlight;
    }

    public int getRefreshRateInHbmHdr() {
      return this.mRefreshRateInHbmHdr;
    }

    public void onDeviceConfigRefreshRateInHbmSunlightChanged(int i) {
      if (i != this.mRefreshRateInHbmSunlight) {
        this.mRefreshRateInHbmSunlight = i;
        onDeviceConfigRefreshRateInHbmChanged();
      }
    }

    public void onDeviceConfigRefreshRateInHbmHdrChanged(int i) {
      if (i != this.mRefreshRateInHbmHdr) {
        this.mRefreshRateInHbmHdr = i;
        onDeviceConfigRefreshRateInHbmChanged();
      }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
      this.mVotesStorage.updateVote(i, 4, null);
      this.mHbmMode.delete(i);
      this.mHbmActive.delete(i);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
      BrightnessInfo brightnessInfo = this.mInjector.getBrightnessInfo(i);
      if (brightnessInfo == null) {
        return;
      }
      int i2 = brightnessInfo.highBrightnessMode;
      boolean z =
          i2 != 0
              && brightnessInfo.adjustedBrightness > brightnessInfo.highBrightnessTransitionPoint;
      if (i2 == this.mHbmMode.get(i) && z == this.mHbmActive.get(i)) {
        return;
      }
      this.mHbmMode.put(i, i2);
      this.mHbmActive.put(i, z);
      recalculateVotesForDisplay(i);
    }

    public final void onDeviceConfigRefreshRateInHbmChanged() {
      int[] copyKeys = this.mHbmMode.copyKeys();
      if (copyKeys != null) {
        for (int i : copyKeys) {
          recalculateVotesForDisplay(i);
        }
      }
    }

    public final void recalculateVotesForDisplay(int i) {
      int i2;
      int i3 = 0;
      Vote vote = null;
      if (this.mHbmActive.get(i, false)) {
        int i4 = this.mHbmMode.get(i, 0);
        if (i4 == 1) {
          int i5 = this.mRefreshRateInHbmSunlight;
          if (i5 > 0) {
            vote = Vote.forPhysicalRefreshRates(i5, i5);
          } else {
            List refreshRateLimitations = this.mDisplayManagerInternal.getRefreshRateLimitations(i);
            while (true) {
              if (refreshRateLimitations == null || i3 >= refreshRateLimitations.size()) {
                break;
              }
              DisplayManagerInternal.RefreshRateLimitation refreshRateLimitation =
                  (DisplayManagerInternal.RefreshRateLimitation) refreshRateLimitations.get(i3);
              if (refreshRateLimitation.type == 1) {
                SurfaceControl.RefreshRateRange refreshRateRange = refreshRateLimitation.range;
                vote = Vote.forPhysicalRefreshRates(refreshRateRange.min, refreshRateRange.max);
                break;
              }
              i3++;
            }
          }
        } else if (i4 == 2 && (i2 = this.mRefreshRateInHbmHdr) > 0) {
          vote = Vote.forPhysicalRefreshRates(i2, i2);
        } else {
          Slog.w("DisplayModeDirector", "Unexpected HBM mode " + i4 + " for display ID " + i);
        }
      }
      this.mVotesStorage.updateVote(i, 4, vote);
    }

    public void dumpLocked(PrintWriter printWriter) {
      printWriter.println("   HbmObserver");
      printWriter.println("     mHbmMode: " + this.mHbmMode);
      printWriter.println("     mHbmActive: " + this.mHbmActive);
      printWriter.println("     mRefreshRateInHbmSunlight: " + this.mRefreshRateInHbmSunlight);
      printWriter.println("     mRefreshRateInHbmHdr: " + this.mRefreshRateInHbmHdr);
    }
  }

  public class DeviceConfigDisplaySettings implements DeviceConfig.OnPropertiesChangedListener {
    public DeviceConfigDisplaySettings() {}

    public void startListening() {
      DisplayModeDirector.this.mDeviceConfig.addOnPropertiesChangedListener(
          "display_manager", BackgroundThread.getExecutor(), this);
    }

    public int[] getLowDisplayBrightnessThresholds() {
      return getIntArrayProperty("peak_refresh_rate_brightness_thresholds");
    }

    public int[] getLowAmbientBrightnessThresholds() {
      return getIntArrayProperty("peak_refresh_rate_ambient_thresholds");
    }

    public int getRefreshRateInLowZone() {
      return DisplayModeDirector.this.mDeviceConfig.getInt(
          "display_manager", "refresh_rate_in_zone", -1);
    }

    public int[] getHighDisplayBrightnessThresholds() {
      return getIntArrayProperty("fixed_refresh_rate_high_display_brightness_thresholds");
    }

    public int[] getHighAmbientBrightnessThresholds() {
      return getIntArrayProperty("fixed_refresh_rate_high_ambient_brightness_thresholds");
    }

    public int getRefreshRateInHighZone() {
      return DisplayModeDirector.this.mDeviceConfig.getInt(
          "display_manager", "refresh_rate_in_high_zone", -1);
    }

    public int getRefreshRateInHbmHdr(DisplayDeviceConfig displayDeviceConfig) {
      int defaultRefreshRateInHbmHdr;
      if (displayDeviceConfig == null) {
        defaultRefreshRateInHbmHdr =
            DisplayModeDirector.this
                .mContext
                .getResources()
                .getInteger(R.integer.config_displayWhiteBalanceDisplayNominalWhiteCct);
      } else {
        defaultRefreshRateInHbmHdr = displayDeviceConfig.getDefaultRefreshRateInHbmHdr();
      }
      try {
        return DisplayModeDirector.this.mDeviceConfig.getInt(
            "display_manager", "refresh_rate_in_hbm_hdr", defaultRefreshRateInHbmHdr);
      } catch (NullPointerException unused) {
        return defaultRefreshRateInHbmHdr;
      }
    }

    public int getRefreshRateInHbmSunlight(DisplayDeviceConfig displayDeviceConfig) {
      int defaultRefreshRateInHbmSunlight;
      if (displayDeviceConfig == null) {
        defaultRefreshRateInHbmSunlight =
            DisplayModeDirector.this
                .mContext
                .getResources()
                .getInteger(R.integer.config_displayWhiteBalanceIncreaseDebounce);
      } else {
        defaultRefreshRateInHbmSunlight = displayDeviceConfig.getDefaultRefreshRateInHbmSunlight();
      }
      try {
        return DisplayModeDirector.this.mDeviceConfig.getInt(
            "display_manager", "refresh_rate_in_hbm_sunlight", defaultRefreshRateInHbmSunlight);
      } catch (NullPointerException unused) {
        return defaultRefreshRateInHbmSunlight;
      }
    }

    public Float getDefaultPeakRefreshRate() {
      float f =
          DisplayModeDirector.this.mDeviceConfig.getFloat(
              "display_manager", "peak_refresh_rate_default", -1.0f);
      if (f == -1.0f) {
        return null;
      }
      return Float.valueOf(f);
    }

    public void onPropertiesChanged(DeviceConfig.Properties properties) {
      DisplayModeDirector.this
          .mHandler
          .obtainMessage(3, getDefaultPeakRefreshRate())
          .sendToTarget();
      int[] lowDisplayBrightnessThresholds = getLowDisplayBrightnessThresholds();
      int[] lowAmbientBrightnessThresholds = getLowAmbientBrightnessThresholds();
      int refreshRateInLowZone = getRefreshRateInLowZone();
      DisplayModeDirector.this
          .mHandler
          .obtainMessage(
              2, new Pair(lowDisplayBrightnessThresholds, lowAmbientBrightnessThresholds))
          .sendToTarget();
      DisplayModeDirector.this.mHandler.obtainMessage(4, refreshRateInLowZone, 0).sendToTarget();
      int[] highDisplayBrightnessThresholds = getHighDisplayBrightnessThresholds();
      int[] highAmbientBrightnessThresholds = getHighAmbientBrightnessThresholds();
      int refreshRateInHighZone = getRefreshRateInHighZone();
      DisplayModeDirector.this
          .mHandler
          .obtainMessage(
              6, new Pair(highDisplayBrightnessThresholds, highAmbientBrightnessThresholds))
          .sendToTarget();
      DisplayModeDirector.this.mHandler.obtainMessage(5, refreshRateInHighZone, 0).sendToTarget();
      synchronized (DisplayModeDirector.this.mLock) {
        DisplayModeDirector.this
            .mHandler
            .obtainMessage(
                7,
                getRefreshRateInHbmSunlight(DisplayModeDirector.this.mDefaultDisplayDeviceConfig),
                0)
            .sendToTarget();
        DisplayModeDirector.this
            .mHandler
            .obtainMessage(
                8, getRefreshRateInHbmHdr(DisplayModeDirector.this.mDefaultDisplayDeviceConfig), 0)
            .sendToTarget();
      }
    }

    public final int[] getIntArrayProperty(String str) {
      String string =
          DisplayModeDirector.this.mDeviceConfig.getString("display_manager", str, (String) null);
      if (string != null) {
        return parseIntArray(string);
      }
      return null;
    }

    public final int[] parseIntArray(String str) {
      String[] split = str.split(",");
      int length = split.length;
      int[] iArr = new int[length];
      for (int i = 0; i < length; i++) {
        try {
          iArr[i] = Integer.parseInt(split[i]);
        } catch (NumberFormatException e) {
          Slog.e("DisplayModeDirector", "Incorrect format for array: '" + str + "'", e);
          return null;
        }
      }
      return iArr;
    }
  }

  class RealInjector implements Injector {
    public final Context mContext;
    public DisplayManager mDisplayManager;

    public RealInjector(Context context) {
      this.mContext = context;
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public DeviceConfigInterface getDeviceConfig() {
      return DeviceConfigInterface.REAL;
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public void registerPeakRefreshRateObserver(
        ContentResolver contentResolver, ContentObserver contentObserver) {
      contentResolver.registerContentObserver(
          Injector.PEAK_REFRESH_RATE_URI, false, contentObserver, 0);
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public void registerDisplayListener(
        DisplayManager.DisplayListener displayListener, Handler handler) {
      getDisplayManager().registerDisplayListener(displayListener, handler);
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public void registerDisplayListener(
        DisplayManager.DisplayListener displayListener, Handler handler, long j) {
      getDisplayManager().registerDisplayListener(displayListener, handler, j);
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public Display getDisplay(int i) {
      return getDisplayManager().getDisplay(i);
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public Display[] getDisplays() {
      return getDisplayManager()
          .getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public boolean getDisplayInfo(int i, DisplayInfo displayInfo) {
      Display display = getDisplayManager().getDisplay(i);
      if (display == null) {
        return false;
      }
      return display.getDisplayInfo(displayInfo);
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public BrightnessInfo getBrightnessInfo(int i) {
      Display display = getDisplayManager().getDisplay(i);
      if (display != null) {
        return display.getBrightnessInfo();
      }
      return null;
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public boolean isDozeState(Display display) {
      if (display == null) {
        return false;
      }
      return Display.isDozeState(display.getState());
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public boolean registerThermalServiceListener(IThermalEventListener iThermalEventListener) {
      IThermalService thermalService = getThermalService();
      if (thermalService == null) {
        Slog.w("DisplayModeDirector", "Could not observe thermal status. Service not available");
        return false;
      }
      try {
        thermalService.registerThermalEventListenerWithType(iThermalEventListener, 3);
        return true;
      } catch (RemoteException e) {
        Slog.e("DisplayModeDirector", "Failed to register thermal status listener", e);
        return false;
      }
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.Injector
    public boolean supportsFrameRateOverride() {
      return ((Boolean) SurfaceFlingerProperties.enable_frame_rate_override().orElse(Boolean.TRUE))
          .booleanValue();
    }

    public final DisplayManager getDisplayManager() {
      if (this.mDisplayManager == null) {
        this.mDisplayManager =
            (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
      }
      return this.mDisplayManager;
    }

    public final IThermalService getThermalService() {
      return IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
    }
  }

  public void onUserSwitching() {
    synchronized (this.mLock) {
      this.mSettingsObserver.updateRefreshRateSettingLocked();
      this.mSettingsObserver.updateLowPowerModeSettingLocked();
      this.mSettingsObserver.updateModeSwitchingTypeSettingLocked();
      if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
        this.mRefreshRateModeManager.getController().updateRefreshRateModeLocked(false);
      }
    }
  }
}
