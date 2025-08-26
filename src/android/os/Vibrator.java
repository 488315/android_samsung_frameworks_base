package android.os;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.content.Context;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import android.os.vibrator.VendorVibrationSession;
import android.os.vibrator.VibrationConfig;
import android.os.vibrator.VibratorEnvelopeEffectInfo;
import android.os.vibrator.VibratorFrequencyProfile;
import android.os.vibrator.VibratorFrequencyProfileLegacy;
import android.util.Log;
import com.samsung.android.vibrator.VibrationDebugInfo;
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
    private VibratorEnvelopeEffectInfo mVibratorEnvelopeEffectInfo;
    private VibratorFrequencyProfile mVibratorFrequencyProfile;

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
    public @interface VibrationEffectSupport {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VibrationIntensity {
    }

    @SystemApi
    public void addVibratorStateListener(OnVibratorStateChangedListener onVibratorStateChangedListener) {
    }

    @SystemApi
    public void addVibratorStateListener(Executor executor, OnVibratorStateChangedListener onVibratorStateChangedListener) {
    }

    @SystemApi
    public boolean areVendorSessionsSupported() {
        return false;
    }

    public abstract void cancel();

    public abstract void cancel(int i);

    public int getMaxMagnitude() {
        return 9999;
    }

    public abstract boolean hasAmplitudeControl();

    public abstract boolean hasVibrator();

    @SystemApi
    public boolean isVibrating() {
        return false;
    }

    @SystemApi
    public void removeVibratorStateListener(OnVibratorStateChangedListener onVibratorStateChangedListener) {
    }

    public int semGetNumberOfSupportedPatterns() {
        return 0;
    }

    public int semGetSupportedVibrationType() {
        return 0;
    }

    public boolean semIsHapticSupported() {
        return false;
    }

    public boolean semIsVibrating() {
        return false;
    }

    public abstract void vibrate(int i, String str, VibrationEffect vibrationEffect, String str2, VibrationAttributes vibrationAttributes);

    public Vibrator() {
        this.mPackageName = ActivityThread.currentPackageName();
        this.mResources = null;
    }

    protected Vibrator(Context context) {
        this.mPackageName = context.getOpPackageName();
        this.mResources = context.getResources();
    }

    public VibratorInfo getInfo() {
        return VibratorInfo.EMPTY_VIBRATOR_INFO;
    }

    private VibrationConfig getConfig() {
        if (this.mVibrationConfig == null) {
            Resources resources = this.mResources;
            if (resources == null) {
                ContextImpl systemContext = ActivityThread.currentActivityThread().getSystemContext();
                resources = systemContext != null ? systemContext.getResources() : null;
            }
            this.mVibrationConfig = new VibrationConfig(resources);
        }
        return this.mVibrationConfig;
    }

    public int getDefaultVibrationIntensity(int i) {
        return getConfig().getDefaultVibrationIntensity(i);
    }

    public int getId() {
        return getInfo().getId();
    }

    public boolean hasFrequencyControl() {
        return getInfo().hasFrequencyControl();
    }

    public boolean areVibrationFeaturesSupported(VibrationEffect vibrationEffect) {
        return getInfo().areVibrationFeaturesSupported(vibrationEffect);
    }

    @SystemApi
    public boolean areVendorEffectsSupported() {
        return getInfo().hasCapability(2048L);
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

    public VibratorFrequencyProfileLegacy getFrequencyProfileLegacy() {
        VibratorInfo.FrequencyProfileLegacy frequencyProfileLegacy = getInfo().getFrequencyProfileLegacy();
        if (frequencyProfileLegacy.isEmpty()) {
            return null;
        }
        return new VibratorFrequencyProfileLegacy(frequencyProfileLegacy);
    }

    public VibratorFrequencyProfile getFrequencyProfile() {
        VibratorInfo.FrequencyProfile frequencyProfile = getInfo().getFrequencyProfile();
        if (frequencyProfile.isEmpty()) {
            return null;
        }
        if (this.mVibratorFrequencyProfile == null) {
            this.mVibratorFrequencyProfile = new VibratorFrequencyProfile(frequencyProfile);
        }
        return this.mVibratorFrequencyProfile;
    }

    public float getHapticChannelMaximumAmplitude() {
        return getConfig().getHapticChannelMaximumAmplitude();
    }

    public boolean areEnvelopeEffectsSupported() {
        return getInfo().areEnvelopeEffectsSupported();
    }

    public VibratorEnvelopeEffectInfo getEnvelopeEffectInfo() {
        if (this.mVibratorEnvelopeEffectInfo == null) {
            this.mVibratorEnvelopeEffectInfo = new VibratorEnvelopeEffectInfo(getInfo().getMaxEnvelopeEffectSize(), getInfo().getMinEnvelopeEffectControlPointDurationMillis(), getInfo().getMaxEnvelopeEffectControlPointDurationMillis());
        }
        return this.mVibratorEnvelopeEffectInfo;
    }

    public boolean setAlwaysOnEffect(int i, VibrationEffect vibrationEffect, VibrationAttributes vibrationAttributes) {
        return setAlwaysOnEffect(Process.myUid(), this.mPackageName, i, vibrationEffect, vibrationAttributes);
    }

    public boolean setAlwaysOnEffect(int i, String str, int i2, VibrationEffect vibrationEffect, VibrationAttributes vibrationAttributes) {
        Log.w(TAG, "Always-on effects aren't supported");
        return false;
    }

    @Deprecated
    public void vibrate(long j) {
        vibrate(j, (AudioAttributes) null);
    }

    @Deprecated
    public void vibrate(long j, AudioAttributes audioAttributes) {
        try {
            vibrate(VibrationEffect.createOneShot(j, -1), audioAttributes);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to create VibrationEffect", e);
        }
    }

    @Deprecated
    public void vibrate(long[] jArr, int i) {
        vibrate(jArr, i, (AudioAttributes) null);
    }

    @Deprecated
    public void vibrate(long[] jArr, int i, AudioAttributes audioAttributes) {
        if (i < -1 || i >= jArr.length) {
            Log.e(TAG, "vibrate called with repeat index out of bounds (pattern.length=" + jArr.length + ", index=" + i + NavigationBarInflaterView.KEY_CODE_END);
            throw new ArrayIndexOutOfBoundsException();
        }
        try {
            vibrate(VibrationEffect.createWaveform(jArr, i), audioAttributes);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to create VibrationEffect", e);
        }
    }

    public void vibrate(VibrationEffect vibrationEffect) {
        vibrate(vibrationEffect, new VibrationAttributes.Builder().build());
    }

    public void vibrate(VibrationEffect vibrationEffect, AudioAttributes audioAttributes) {
        VibrationAttributes vibrationAttributesBuild;
        if (audioAttributes == null) {
            vibrationAttributesBuild = new VibrationAttributes.Builder().build();
        } else {
            vibrationAttributesBuild = new VibrationAttributes.Builder(audioAttributes).build();
        }
        vibrate(vibrationEffect, vibrationAttributesBuild);
    }

    public void vibrate(VibrationEffect vibrationEffect, VibrationAttributes vibrationAttributes) {
        vibrate(vibrationEffect, vibrationAttributes, (String) null);
    }

    public void vibrate(VibrationEffect vibrationEffect, VibrationAttributes vibrationAttributes, String str) {
        vibrate(Process.myUid(), this.mPackageName, vibrationEffect, str, vibrationAttributes);
    }

    public void performHapticFeedback(int i, String str, int i2, int i3) {
        Log.w(TAG, "performHapticFeedback is not supported");
    }

    public void performHapticFeedbackForInputDevice(int i, int i2, int i3, String str, int i4, int i5) {
        Log.w(TAG, "performHapticFeedbackForInputDevice is not supported");
    }

    public int[] areEffectsSupported(int... iArr) {
        VibratorInfo info = getInfo();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = info.isEffectSupported(iArr[i]);
        }
        return iArr2;
    }

    public final int areAllEffectsSupported(int... iArr) {
        VibratorInfo info = getInfo();
        int i = 1;
        for (int i2 : iArr) {
            int iIsEffectSupported = info.isEffectSupported(i2);
            if (iIsEffectSupported != 1) {
                if (iIsEffectSupported == 2) {
                    return 2;
                }
                i = 0;
            }
        }
        return i;
    }

    public boolean[] arePrimitivesSupported(int... iArr) {
        VibratorInfo info = getInfo();
        boolean[] zArr = new boolean[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            zArr[i] = info.isPrimitiveSupported(iArr[i]);
        }
        return zArr;
    }

    public final boolean areAllPrimitivesSupported(int... iArr) {
        VibratorInfo info = getInfo();
        for (int i : iArr) {
            if (!info.isPrimitiveSupported(i)) {
                return false;
            }
        }
        return true;
    }

    public int[] getPrimitiveDurations(int... iArr) {
        VibratorInfo info = getInfo();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = info.getPrimitiveDuration(iArr[i]);
        }
        return iArr2;
    }

    @SystemApi
    public void startVendorSession(VibrationAttributes vibrationAttributes, String str, CancellationSignal cancellationSignal, Executor executor, final VendorVibrationSession.Callback callback) {
        Log.w(TAG, "startVendorSession is not supported");
        executor.execute(new Runnable() { // from class: android.os.Vibrator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                callback.onFinished(3);
            }
        });
    }

    protected String getPackageName() {
        return this.mPackageName;
    }

    public void semVibrate(int i, int i2, AudioAttributes audioAttributes, SemMagnitudeTypes semMagnitudeTypes) {
        try {
            vibrate(Process.myUid(), this.mPackageName, VibrationEffect.semCreateHaptic(i, i2, convertMagnitudeType(semMagnitudeTypes)), "semVibrate", new VibrationAttributes.Builder(audioAttributes).build());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to create VibrationEffect", e);
        }
    }

    private VibrationEffect.SemMagnitudeType convertMagnitudeType(SemMagnitudeTypes semMagnitudeTypes) {
        return VibrationEffect.SemMagnitudeType.values()[semMagnitudeTypes.ordinal()];
    }

    public String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) {
        return "";
    }
}
