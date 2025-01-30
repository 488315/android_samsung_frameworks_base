package android.os;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.os.vibrator.VibrationConfig;
import android.os.vibrator.VibratorFrequencyProfile;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public abstract class Vibrator {
  public static final int SEM_SUPPORTED_VIBRATION_NONE = 0;
  public static final int SEM_SUPPORTED_VIBRATION_TYPE_A = 1;
  public static final int SEM_SUPPORTED_VIBRATION_TYPE_B = 2;
  public static final int SEM_SUPPORTED_VIBRATION_TYPE_C = 3;
  public static final int SEM_SUPPORTED_VIBRATION_TYPE_D = 4;
  private static final String TAG = "Vibrator";
  public static final int VIBRATION_EFFECT_SUPPORT_NO = 2;
  public static final int VIBRATION_EFFECT_SUPPORT_UNKNOWN = 0;
  public static final int VIBRATION_EFFECT_SUPPORT_YES = 1;
  public static final int VIBRATION_INTENSITY_HIGH = 3;
  public static final int VIBRATION_INTENSITY_LOW = 1;
  public static final int VIBRATION_INTENSITY_MEDIUM = 2;
  public static final int VIBRATION_INTENSITY_OFF = 0;
  private final String mPackageName;
  private final Resources mResources;
  private volatile VibrationConfig mVibrationConfig;

  @SystemApi
  public interface OnVibratorStateChangedListener {
    void onVibratorStateChanged(boolean z);
  }

  public enum SemMagnitudeTypes {
    TYPE_TOUCH,
    TYPE_NOTIFICATION,
    TYPE_CALL,
    TYPE_MAX,
    TYPE_MIN,
    TYPE_EXTRA,
    TYPE_FORCE
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface VibrationEffectSupport {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface VibrationIntensity {}

  public abstract void cancel();

  public abstract void cancel(int i);

  public abstract boolean hasAmplitudeControl();

  public abstract boolean hasVibrator();

  public abstract void vibrate(
      int i,
      String str,
      VibrationEffect vibrationEffect,
      String str2,
      VibrationAttributes vibrationAttributes);

  public Vibrator() {
    this.mPackageName = ActivityThread.currentPackageName();
    this.mResources = null;
  }

  protected Vibrator(Context context) {
    this.mPackageName = context.getOpPackageName();
    this.mResources = context.getResources();
  }

  protected VibratorInfo getInfo() {
    return VibratorInfo.EMPTY_VIBRATOR_INFO;
  }

  private VibrationConfig getConfig() {
    if (this.mVibrationConfig == null) {
      Resources resources = this.mResources;
      if (resources == null) {
        Context ctx = ActivityThread.currentActivityThread().getSystemContext();
        resources = ctx != null ? ctx.getResources() : null;
      }
      this.mVibrationConfig = new VibrationConfig(resources);
    }
    return this.mVibrationConfig;
  }

  public int getDefaultVibrationIntensity(int usage) {
    return getConfig().getDefaultVibrationIntensity(usage);
  }

  public int getId() {
    return getInfo().getId();
  }

  public boolean hasFrequencyControl() {
    return getInfo().hasCapability(1536L);
  }

  public boolean areVibrationFeaturesSupported(VibrationEffect effect) {
    return effect.areVibrationFeaturesSupported(this);
  }

  public boolean hasExternalControl() {
    return getInfo().hasCapability(8L);
  }

  public float getResonantFrequency() {
    return getInfo().getResonantFrequencyHz();
  }

  public float getQFactor() {
    return getInfo().getQFactor();
  }

  public VibratorFrequencyProfile getFrequencyProfile() {
    VibratorInfo.FrequencyProfile frequencyProfile = getInfo().getFrequencyProfile();
    if (frequencyProfile.isEmpty()) {
      return null;
    }
    return new VibratorFrequencyProfile(frequencyProfile);
  }

  public float getHapticChannelMaximumAmplitude() {
    return getConfig().getHapticChannelMaximumAmplitude();
  }

  public boolean setAlwaysOnEffect(
      int alwaysOnId, VibrationEffect effect, VibrationAttributes attributes) {
    return setAlwaysOnEffect(Process.myUid(), this.mPackageName, alwaysOnId, effect, attributes);
  }

  public boolean setAlwaysOnEffect(
      int uid,
      String opPkg,
      int alwaysOnId,
      VibrationEffect effect,
      VibrationAttributes attributes) {
    Log.m102w(TAG, "Always-on effects aren't supported");
    return false;
  }

  @Deprecated
  public void vibrate(long milliseconds) {
    vibrate(milliseconds, (AudioAttributes) null);
  }

  @Deprecated
  public void vibrate(long milliseconds, AudioAttributes attributes) {
    try {
      VibrationEffect effect = VibrationEffect.createOneShot(milliseconds, -1);
      vibrate(effect, attributes);
    } catch (IllegalArgumentException iae) {
      Log.m97e(TAG, "Failed to create VibrationEffect", iae);
    }
  }

  @Deprecated
  public void vibrate(long[] pattern, int repeat) {
    vibrate(pattern, repeat, null);
  }

  @Deprecated
  public void vibrate(long[] pattern, int repeat, AudioAttributes attributes) {
    if (repeat < -1 || repeat >= pattern.length) {
      Log.m96e(
          TAG,
          "vibrate called with repeat index out of bounds (pattern.length="
              + pattern.length
              + ", index="
              + repeat
              + NavigationBarInflaterView.KEY_CODE_END);
      throw new ArrayIndexOutOfBoundsException();
    }
    try {
      vibrate(VibrationEffect.createWaveform(pattern, repeat), attributes);
    } catch (IllegalArgumentException iae) {
      Log.m97e(TAG, "Failed to create VibrationEffect", iae);
    }
  }

  public void vibrate(VibrationEffect vibe) {
    vibrate(vibe, new VibrationAttributes.Builder().build());
  }

  public void vibrate(VibrationEffect vibe, AudioAttributes attributes) {
    VibrationAttributes build;
    if (attributes == null) {
      build = new VibrationAttributes.Builder().build();
    } else {
      build = new VibrationAttributes.Builder(attributes).build();
    }
    vibrate(vibe, build);
  }

  public void vibrate(VibrationEffect vibe, VibrationAttributes attributes) {
    vibrate(Process.myUid(), this.mPackageName, vibe, null, attributes);
  }

  public int[] areEffectsSupported(int... effectIds) {
    VibratorInfo info = getInfo();
    int[] supported = new int[effectIds.length];
    for (int i = 0; i < effectIds.length; i++) {
      supported[i] = info.isEffectSupported(effectIds[i]);
    }
    return supported;
  }

  public final int areAllEffectsSupported(int... effectIds) {
    VibratorInfo info = getInfo();
    int allSupported = 1;
    for (int effectId : effectIds) {
      switch (info.isEffectSupported(effectId)) {
        case 1:
          break;
        case 2:
          return 2;
        default:
          allSupported = 0;
          break;
      }
    }
    return allSupported;
  }

  public boolean[] arePrimitivesSupported(int... primitiveIds) {
    VibratorInfo info = getInfo();
    boolean[] supported = new boolean[primitiveIds.length];
    for (int i = 0; i < primitiveIds.length; i++) {
      supported[i] = info.isPrimitiveSupported(primitiveIds[i]);
    }
    return supported;
  }

  public final boolean areAllPrimitivesSupported(int... primitiveIds) {
    VibratorInfo info = getInfo();
    for (int primitiveId : primitiveIds) {
      if (!info.isPrimitiveSupported(primitiveId)) {
        return false;
      }
    }
    return true;
  }

  public int[] getPrimitiveDurations(int... primitiveIds) {
    VibratorInfo info = getInfo();
    int[] durations = new int[primitiveIds.length];
    for (int i = 0; i < primitiveIds.length; i++) {
      durations[i] = info.getPrimitiveDuration(primitiveIds[i]);
    }
    return durations;
  }

  @SystemApi
  public boolean isVibrating() {
    return false;
  }

  @SystemApi
  public void addVibratorStateListener(OnVibratorStateChangedListener listener) {}

  @SystemApi
  public void addVibratorStateListener(
      Executor executor, OnVibratorStateChangedListener listener) {}

  @SystemApi
  public void removeVibratorStateListener(OnVibratorStateChangedListener listener) {}

  public int semGetSupportedVibrationType() {
    return 0;
  }

  public int getMaxMagnitude() {
    return 9999;
  }

  public int semGetNumberOfSupportedPatterns() {
    return 0;
  }

  protected String getPackageName() {
    return this.mPackageName;
  }

  public boolean semIsHapticSupported() {
    return false;
  }

  public void semVibrate(
      int type, int repeat, AudioAttributes attributes, SemMagnitudeTypes magnitudeType) {
    try {
      VibrationEffect effect =
          VibrationEffect.semCreateHaptic(type, repeat, convertMagnitudeType(magnitudeType));
      vibrate(
          Process.myUid(),
          this.mPackageName,
          effect,
          "semVibrate",
          new VibrationAttributes.Builder(attributes).build());
    } catch (IllegalArgumentException iae) {
      Log.m97e(TAG, "Failed to create VibrationEffect", iae);
    }
  }

  private VibrationEffect.SemMagnitudeType convertMagnitudeType(SemMagnitudeTypes magnitudeType) {
    return VibrationEffect.SemMagnitudeType.values()[magnitudeType.ordinal()];
  }

  public boolean semIsVibrating() {
    return false;
  }

  public String executeVibrationDebugCommand(int param) {
    return "";
  }
}
