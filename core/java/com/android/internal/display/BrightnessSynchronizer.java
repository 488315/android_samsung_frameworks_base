package com.android.internal.display;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.SystemClock;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.C4337R;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class BrightnessSynchronizer {
  private static final boolean COVER_DISPLAY_ENABLED = false;
  private static final boolean DEBUG = false;
  public static final float EPSILON = 0.001f;
  private static final int MSG_RUN_UPDATE = 1;
  private static final Uri SUB_SCREEN_BRIGHTNESS_URI;
  private static final String TAG = "BrightnessSynchronizer";
  private static final int UPDATE_TYPE_FLOAT = 2;
  private static final int UPDATE_TYPE_INT = 1;
  private static final long WAIT_FOR_RESPONSE_MILLIS = 200;
  private static float sScreenExtendedBrightnessRangeMaximumFloat;
  private static int sScreenExtendedBrightnessRangeMaximumInt;
  private final BrightnessSyncObserver mBrightnessSyncObserver;
  private final Clock mClock;
  private final Context mContext;
  private BrightnessUpdate mCurrentUpdate;
  private DisplayManager mDisplayManager;
  private final SparseArray<DisplaySynchronizer> mDisplaySynchronizers;
  private final Handler mHandler;
  private float mLatestFloatBrightness;
  private int mLatestIntBrightness;
  private BrightnessUpdate mPendingUpdate;
  private float mPreferredSettingValue;
  private static final Uri BRIGHTNESS_URI =
      Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
  private static int sBrightnessUpdateCount = 1;

  public interface Clock {
    long uptimeMillis();
  }

  static {
    int max =
        Math.max(
            255,
            Resources.getSystem()
                .getInteger(C4337R.integer.config_screenBrightnessExtendedMaximum));
    sScreenExtendedBrightnessRangeMaximumInt = max;
    sScreenExtendedBrightnessRangeMaximumFloat = max / 255.0f;
    SUB_SCREEN_BRIGHTNESS_URI = Settings.System.getUriFor(Settings.System.SUB_SCREEN_BRIGHTNESS);
  }

  public BrightnessSynchronizer(Context context) {
    this(
        context,
        Looper.getMainLooper(),
        new Clock() { // from class:
                      // com.android.internal.display.BrightnessSynchronizer$$ExternalSyntheticLambda2
          @Override // com.android.internal.display.BrightnessSynchronizer.Clock
          public final long uptimeMillis() {
            return SystemClock.uptimeMillis();
          }
        });
  }

  public BrightnessSynchronizer(Context context, Looper looper, Clock clock) {
    this.mPreferredSettingValue = Float.NaN;
    this.mDisplaySynchronizers = new SparseArray<>();
    this.mContext = context;
    this.mClock = clock;
    this.mBrightnessSyncObserver = new BrightnessSyncObserver();
    this.mHandler = new BrightnessSynchronizerHandler(looper);
  }

  public void startSynchronizing() {
    if (this.mDisplayManager == null) {
      this.mDisplayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
    }
    if (this.mBrightnessSyncObserver.isObserving()) {
      Slog.wtf(TAG, "Brightness sync observer requesting synchronization a second time.");
      return;
    }
    this.mLatestFloatBrightness = getScreenBrightnessFloat();
    this.mLatestIntBrightness = getScreenBrightnessInt();
    Slog.m117i(
        TAG,
        "Initial brightness readings: "
            + this.mLatestIntBrightness
            + "(int), "
            + this.mLatestFloatBrightness
            + "(float)");
    if (!Float.isNaN(this.mLatestFloatBrightness)) {
      this.mPendingUpdate = new BrightnessUpdate(2, this.mLatestFloatBrightness);
    } else {
      int i = this.mLatestIntBrightness;
      if (i != -1) {
        this.mPendingUpdate = new BrightnessUpdate(1, i);
      } else {
        float defaultBrightness =
            this.mContext
                .getResources()
                .getFloat(C4337R.dimen.config_screenBrightnessSettingDefaultFloat);
        this.mPendingUpdate = new BrightnessUpdate(2, defaultBrightness);
        Slog.m117i(TAG, "Setting initial brightness to default value of: " + defaultBrightness);
      }
    }
    this.mDisplaySynchronizers.append(
        0,
        new DisplaySynchronizer(
            0,
            this.mDisplayManager,
            new Supplier() { // from class:
                             // com.android.internal.display.BrightnessSynchronizer$$ExternalSyntheticLambda0
              @Override // java.util.function.Supplier
              public final Object get() {
                int screenBrightnessInt;
                screenBrightnessInt = BrightnessSynchronizer.this.getScreenBrightnessInt();
                return Integer.valueOf(screenBrightnessInt);
              }
            },
            new Consumer() { // from class:
                             // com.android.internal.display.BrightnessSynchronizer$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                BrightnessSynchronizer.this.setScreenBrightnessInt(((Integer) obj).intValue());
              }
            }));
    this.mBrightnessSyncObserver.startObserving(this.mHandler);
    this.mHandler.sendEmptyMessageAtTime(1, this.mClock.uptimeMillis());
  }

  public void dump(PrintWriter pw) {
    pw.println(TAG);
    pw.println("  mLatestIntBrightness=" + this.mLatestIntBrightness);
    pw.println("  mLatestFloatBrightness=" + this.mLatestFloatBrightness);
    pw.println("  mCurrentUpdate=" + this.mCurrentUpdate);
    pw.println("  mPendingUpdate=" + this.mPendingUpdate);
  }

  public static float brightnessIntToFloat(int brightnessInt) {
    if (brightnessInt == 0) {
      return 0.0f;
    }
    if (brightnessInt == -1) {
      return Float.NaN;
    }
    float maxFloat = sScreenExtendedBrightnessRangeMaximumFloat;
    float maxInt = sScreenExtendedBrightnessRangeMaximumInt;
    return MathUtils.constrainedMap(0.0f, maxFloat, 0.0f, maxInt, brightnessInt);
  }

  public static int brightnessFloatToInt(float brightnessFloat) {
    return Math.round(brightnessFloatToIntRange(brightnessFloat));
  }

  public static float brightnessFloatToIntRange(float brightnessFloat) {
    if (floatEquals(brightnessFloat, 0.0f)) {
      return 0.0f;
    }
    if (Float.isNaN(brightnessFloat)) {
      return -1.0f;
    }
    float maxFloat = sScreenExtendedBrightnessRangeMaximumFloat;
    float maxInt = sScreenExtendedBrightnessRangeMaximumInt;
    return MathUtils.constrainedMap(0.0f, maxInt, 0.0f, maxFloat, brightnessFloat);
  }

  private void handleBrightnessChangeFloat(float brightness) {
    this.mLatestFloatBrightness = brightness;
    handleBrightnessChange(2, brightness);
  }

  private void handleBrightnessChangeInt(int brightness) {
    this.mLatestIntBrightness = brightness;
    handleBrightnessChange(1, brightness);
  }

  private void handleBrightnessChange(int type, float brightness) {
    BrightnessUpdate brightnessUpdate = this.mCurrentUpdate;
    boolean swallowUpdate =
        brightnessUpdate != null && brightnessUpdate.swallowUpdate(type, brightness);
    BrightnessUpdate prevUpdate = null;
    if (!swallowUpdate) {
      prevUpdate = this.mPendingUpdate;
      this.mPendingUpdate = new BrightnessUpdate(type, brightness);
    }
    runUpdate();
    if (!swallowUpdate && this.mPendingUpdate != null) {
      Slog.m117i(TAG, "New PendingUpdate: " + this.mPendingUpdate + ", prev=" + prevUpdate);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void runUpdate() {
    BrightnessUpdate brightnessUpdate;
    do {
      BrightnessUpdate brightnessUpdate2 = this.mCurrentUpdate;
      if (brightnessUpdate2 != null) {
        brightnessUpdate2.update();
        if (!this.mCurrentUpdate.isRunning()) {
          if (this.mCurrentUpdate.isCompleted()) {
            if (this.mCurrentUpdate.madeUpdates()) {
              Slog.m117i(TAG, "Completed Update: " + this.mCurrentUpdate);
            }
            this.mCurrentUpdate = null;
          }
        } else {
          return;
        }
      }
      if (this.mCurrentUpdate == null && (brightnessUpdate = this.mPendingUpdate) != null) {
        this.mCurrentUpdate = brightnessUpdate;
        this.mPendingUpdate = null;
      }
    } while (this.mCurrentUpdate != null);
  }

  private float getScreenBrightnessFloat() {
    return this.mDisplayManager.getBrightness(0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int getScreenBrightnessInt() {
    return Settings.System.getIntForUser(
        this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1, -2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void setScreenBrightnessInt(int brightnessInt) {
    Settings.System.putIntForUser(
        this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessInt, -2);
  }

  private int getSubScreenBrightnessInt() {
    return Settings.System.getIntForUser(
        this.mContext.getContentResolver(), Settings.System.SUB_SCREEN_BRIGHTNESS, -1, -2);
  }

  private void setSubScreenBrightnessInt(int brightnessInt) {
    Settings.System.putIntForUser(
        this.mContext.getContentResolver(),
        Settings.System.SUB_SCREEN_BRIGHTNESS,
        brightnessInt,
        -2);
  }

  private void updateScreenBrightness(int type) {
    int currentBrightnessInt = getScreenBrightnessInt();
    float currentBrightnessFloat = getScreenBrightnessFloat();
    int currentBrightnessIntFromFloat = brightnessFloatToInt(currentBrightnessFloat);
    Slog.m113d(
        TAG,
        "updateScreenBrightness: type="
            + type
            + " mPreferredSettingValue="
            + this.mPreferredSettingValue
            + NavigationBarInflaterView.KEY_CODE_START
            + brightnessFloatToInt(this.mPreferredSettingValue)
            + ") currentBrightnessInt="
            + currentBrightnessInt
            + " currentBrightnessIntFromFloat="
            + currentBrightnessIntFromFloat
            + NavigationBarInflaterView.KEY_CODE_START
            + currentBrightnessFloat
            + NavigationBarInflaterView.KEY_CODE_END);
    if (currentBrightnessInt == currentBrightnessIntFromFloat) {
      if (this.mPreferredSettingValue != currentBrightnessFloat) {
        Slog.m113d(
            TAG,
            "synced: mPreferredSettingValue: "
                + this.mPreferredSettingValue
                + " currentBrightnessFloat: "
                + currentBrightnessFloat);
        this.mPreferredSettingValue = currentBrightnessFloat;
        return;
      }
      return;
    }
    boolean lastFloatChanged = false;
    if (Float.isNaN(this.mPreferredSettingValue)) {
      if (type == 1) {
        lastFloatChanged = false;
      } else if (type == 2) {
        lastFloatChanged = true;
      }
    } else {
      int preferredSettingValueInt = brightnessFloatToInt(this.mPreferredSettingValue);
      if (currentBrightnessInt == preferredSettingValueInt) {
        lastFloatChanged = true;
      } else if (currentBrightnessIntFromFloat != preferredSettingValueInt) {
        Slog.m115e(TAG, "onChange: both changed");
        if (type == 1) {
          lastFloatChanged = true;
        } else if (type == 2) {
          lastFloatChanged = false;
        }
      } else {
        lastFloatChanged = false;
      }
    }
    if (lastFloatChanged) {
      int newBrightnessInt = brightnessFloatToInt(currentBrightnessFloat);
      this.mPreferredSettingValue = currentBrightnessFloat;
      Slog.m113d(
          TAG,
          "onChange: last float changed: " + currentBrightnessFloat + " -> " + newBrightnessInt);
      Settings.System.putIntForUser(
          this.mContext.getContentResolver(),
          Settings.System.SCREEN_BRIGHTNESS,
          newBrightnessInt,
          -2);
      return;
    }
    float newBrightnessFloat = brightnessIntToFloat(currentBrightnessInt);
    this.mPreferredSettingValue = newBrightnessFloat;
    Slog.m113d(
        TAG, "onChange: last int changed: " + currentBrightnessInt + " -> " + newBrightnessFloat);
    this.mDisplayManager.setBrightness(0, newBrightnessFloat);
  }

  public static boolean floatEquals(float a, float b) {
    if (a == b) {
      return true;
    }
    return (Float.isNaN(a) && Float.isNaN(b)) || Math.abs(a - b) < 0.001f;
  }

  public class BrightnessUpdate {
    private static final int STATE_COMPLETED = 3;
    private static final int STATE_NOT_STARTED = 1;
    private static final int STATE_RUNNING = 2;
    static final int TYPE_FLOAT = 2;
    static final int TYPE_INT = 1;
    private final float mBrightness;
    private int mConfirmedTypes;
    private int mId;
    private final int mSourceType;
    private int mState;
    private long mTimeUpdated;
    private int mUpdatedTypes;

    BrightnessUpdate(int sourceType, float brightness) {
      int i = BrightnessSynchronizer.sBrightnessUpdateCount;
      BrightnessSynchronizer.sBrightnessUpdateCount = i + 1;
      this.mId = i;
      this.mSourceType = sourceType;
      this.mBrightness = brightness;
      this.mTimeUpdated = 0L;
      this.mUpdatedTypes = 0;
      this.mConfirmedTypes = 0;
      this.mState = 1;
    }

    public String toString() {
      return "{["
          + this.mId
          + "] "
          + toStringLabel(this.mSourceType, this.mBrightness)
          + ", mUpdatedTypes="
          + this.mUpdatedTypes
          + ", mConfirmedTypes="
          + this.mConfirmedTypes
          + ", mTimeUpdated="
          + this.mTimeUpdated
          + "}";
    }

    void update() {
      if (this.mState == 1) {
        this.mState = 2;
        int brightnessInt = getBrightnessAsInt();
        if (BrightnessSynchronizer.this.mLatestIntBrightness != brightnessInt) {
          Settings.System.putIntForUser(
              BrightnessSynchronizer.this.mContext.getContentResolver(),
              Settings.System.SCREEN_BRIGHTNESS,
              brightnessInt,
              -2);
          BrightnessSynchronizer.this.mLatestIntBrightness = brightnessInt;
          this.mUpdatedTypes |= 1;
        }
        float brightnessFloat = getBrightnessAsFloat();
        if (!BrightnessSynchronizer.floatEquals(
            BrightnessSynchronizer.this.mLatestFloatBrightness, brightnessFloat)) {
          BrightnessSynchronizer.this.mDisplayManager.setBrightness(0, brightnessFloat);
          BrightnessSynchronizer.this.mLatestFloatBrightness = brightnessFloat;
          this.mUpdatedTypes |= 2;
        }
        if (this.mUpdatedTypes != 0) {
          Slog.m117i(
              BrightnessSynchronizer.TAG,
              NavigationBarInflaterView.SIZE_MOD_START
                  + this.mId
                  + "] New Update "
                  + toStringLabel(this.mSourceType, this.mBrightness)
                  + " set brightness values: "
                  + toStringLabel(this.mUpdatedTypes & 2, brightnessFloat)
                  + " "
                  + toStringLabel(this.mUpdatedTypes & 1, brightnessInt));
          BrightnessSynchronizer.this.mHandler.sendEmptyMessageAtTime(
              1, BrightnessSynchronizer.this.mClock.uptimeMillis() + 200);
        }
        this.mTimeUpdated = BrightnessSynchronizer.this.mClock.uptimeMillis();
      }
      if (this.mState == 2) {
        if (this.mConfirmedTypes == this.mUpdatedTypes
            || this.mTimeUpdated + 200 < BrightnessSynchronizer.this.mClock.uptimeMillis()) {
          this.mState = 3;
        }
      }
    }

    boolean swallowUpdate(int type, float brightness) {
      if ((this.mUpdatedTypes & type) != type || (this.mConfirmedTypes & type) != 0) {
        return false;
      }
      boolean floatUpdateConfirmed =
          type == 2 && BrightnessSynchronizer.floatEquals(getBrightnessAsFloat(), brightness);
      boolean intUpdateConfirmed = type == 1 && getBrightnessAsInt() == ((int) brightness);
      if (!floatUpdateConfirmed && !intUpdateConfirmed) {
        return false;
      }
      this.mConfirmedTypes |= type;
      Slog.m117i(
          BrightnessSynchronizer.TAG,
          "Swallowing update of " + toStringLabel(type, brightness) + " by update: " + this);
      return true;
    }

    boolean isRunning() {
      return this.mState == 2;
    }

    boolean isCompleted() {
      return this.mState == 3;
    }

    boolean madeUpdates() {
      return this.mUpdatedTypes != 0;
    }

    private int getBrightnessAsInt() {
      if (this.mSourceType == 1) {
        return (int) this.mBrightness;
      }
      return BrightnessSynchronizer.brightnessFloatToInt(this.mBrightness);
    }

    private float getBrightnessAsFloat() {
      if (this.mSourceType == 2) {
        return this.mBrightness;
      }
      return BrightnessSynchronizer.brightnessIntToFloat((int) this.mBrightness);
    }

    private String toStringLabel(int type, float brightness) {
      return type == 1 ? ((int) brightness) + "(i)" : type == 2 ? brightness + "(f)" : "";
    }
  }

  class BrightnessSynchronizerHandler extends Handler {
    BrightnessSynchronizerHandler(Looper looper) {
      super(looper);
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 1:
          BrightnessSynchronizer.this.runUpdate();
          break;
        default:
          super.handleMessage(msg);
          break;
      }
    }
  }

  private class BrightnessSyncObserver {
    private boolean mIsObserving;
    private final DisplayManager.DisplayListener mListener;

    private BrightnessSyncObserver() {
      this.mListener =
          new DisplayManager
              .DisplayListener() { // from class:
                                   // com.android.internal.display.BrightnessSynchronizer.BrightnessSyncObserver.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int displayId) {}

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int displayId) {}

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int displayId) {
              Slog.m113d(BrightnessSynchronizer.TAG, "onDisplayChanged() : displayId=" + displayId);
              if (displayId == 0) {
                ((DisplaySynchronizer)
                        BrightnessSynchronizer.this.mDisplaySynchronizers.get(displayId))
                    .updateScreenBrightness(2);
              }
            }
          };
    }

    private ContentObserver createBrightnessContentObserver(Handler handler) {
      return new ContentObserver(
          handler) { // from class:
                     // com.android.internal.display.BrightnessSynchronizer.BrightnessSyncObserver.2
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
          if (selfChange) {
            return;
          }
          Slog.m113d(BrightnessSynchronizer.TAG, "onChange : " + uri);
          if (BrightnessSynchronizer.BRIGHTNESS_URI.equals(uri)) {
            ((DisplaySynchronizer) BrightnessSynchronizer.this.mDisplaySynchronizers.get(0))
                .updateScreenBrightness(1);
          }
        }
      };
    }

    boolean isObserving() {
      return this.mIsObserving;
    }

    void startObserving(Handler handler) {
      ContentResolver cr = BrightnessSynchronizer.this.mContext.getContentResolver();
      ContentObserver contentObserver = createBrightnessContentObserver(handler);
      cr.registerContentObserver(BrightnessSynchronizer.BRIGHTNESS_URI, false, contentObserver, -1);
      BrightnessSynchronizer.this.mDisplayManager.registerDisplayListener(
          this.mListener, handler, 8L);
      this.mIsObserving = true;
    }
  }

  public static class DisplaySynchronizer {
    private final int mDisplayId;
    private final DisplayManager mDisplayManager;
    public float mPreferredSettingValue = Float.NaN;
    private final Supplier<Integer> mScreenBrightnessIntGetter;
    private final Consumer<Integer> mScreenBrightnessIntSetter;

    public DisplaySynchronizer(
        int displayId,
        DisplayManager displayManager,
        Supplier<Integer> supplier,
        Consumer<Integer> consumer) {
      this.mDisplayId = displayId;
      this.mDisplayManager = displayManager;
      this.mScreenBrightnessIntGetter = supplier;
      this.mScreenBrightnessIntSetter = consumer;
    }

    void setScreenBrightnessFloat(float brightnessFloat) {
      this.mDisplayManager.setBrightness(this.mDisplayId, brightnessFloat);
    }

    float getScreenBrightnessFloat() {
      return this.mDisplayManager.getBrightness(this.mDisplayId);
    }

    public void updateScreenBrightness(int type) {
      int currentBrightnessInt =
          constrainBrightnessInt(this.mScreenBrightnessIntGetter.get().intValue());
      float currentBrightnessFloat = getScreenBrightnessFloat();
      int currentBrightnessIntFromFloat =
          BrightnessSynchronizer.brightnessFloatToInt(currentBrightnessFloat);
      Slog.m113d(
          BrightnessSynchronizer.TAG,
          "updateScreenBrightness: displayId="
              + this.mDisplayId
              + " type="
              + type
              + " mPreferredSettingValue="
              + this.mPreferredSettingValue
              + NavigationBarInflaterView.KEY_CODE_START
              + BrightnessSynchronizer.brightnessFloatToInt(this.mPreferredSettingValue)
              + ") currentBrightnessInt="
              + currentBrightnessInt
              + " currentBrightnessIntFromFloat="
              + currentBrightnessIntFromFloat
              + NavigationBarInflaterView.KEY_CODE_START
              + currentBrightnessFloat
              + NavigationBarInflaterView.KEY_CODE_END);
      if (currentBrightnessInt != currentBrightnessIntFromFloat) {
        boolean lastFloatChanged =
            checkFloatTypeChanged(type, currentBrightnessInt, currentBrightnessIntFromFloat);
        syncBrightnessValue(currentBrightnessInt, currentBrightnessFloat, lastFloatChanged);
      } else if (this.mPreferredSettingValue != currentBrightnessFloat) {
        Slog.m113d(
            BrightnessSynchronizer.TAG,
            "synced: mPreferredSettingValue: "
                + this.mPreferredSettingValue
                + " currentBrightnessFloat: "
                + currentBrightnessFloat);
        this.mPreferredSettingValue = currentBrightnessFloat;
      }
    }

    private void syncBrightnessValue(
        int currentBrightnessInt, float currentBrightnessFloat, boolean lastFloatChanged) {
      if (lastFloatChanged) {
        int newBrightnessInt = BrightnessSynchronizer.brightnessFloatToInt(currentBrightnessFloat);
        this.mPreferredSettingValue = currentBrightnessFloat;
        Slog.m113d(
            BrightnessSynchronizer.TAG,
            "onChange: last float changed: " + currentBrightnessFloat + " -> " + newBrightnessInt);
        this.mScreenBrightnessIntSetter.accept(Integer.valueOf(newBrightnessInt));
        return;
      }
      float newBrightnessFloat = BrightnessSynchronizer.brightnessIntToFloat(currentBrightnessInt);
      this.mPreferredSettingValue = newBrightnessFloat;
      Slog.m113d(
          BrightnessSynchronizer.TAG,
          "onChange: last int changed: " + currentBrightnessInt + " -> " + newBrightnessFloat);
      setScreenBrightnessFloat(newBrightnessFloat);
    }

    private boolean checkFloatTypeChanged(
        int type, int currentBrightnessInt, int currentBrightnessIntFromFloat) {
      if (currentBrightnessInt == -1) {
        return true;
      }
      if (Float.isNaN(this.mPreferredSettingValue)) {
        if (type == 1 || type != 2) {
          return false;
        }
        return true;
      }
      int preferredSettingValueInt =
          BrightnessSynchronizer.brightnessFloatToInt(this.mPreferredSettingValue);
      if (currentBrightnessInt == preferredSettingValueInt) {
        return true;
      }
      if (currentBrightnessIntFromFloat == preferredSettingValueInt) {
        return false;
      }
      Slog.m115e(BrightnessSynchronizer.TAG, "onChange: both changed");
      if (type == 1) {
        return true;
      }
      if (type != 2) {
        return false;
      }
      return false;
    }

    private static int constrainBrightnessInt(int brightnessInt) {
      return MathUtils.constrain(brightnessInt, -1, 255);
    }
  }
}
