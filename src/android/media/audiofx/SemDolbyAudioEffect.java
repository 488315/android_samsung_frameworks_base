package android.media.audiofx;

import android.util.Log;
import com.samsung.android.audio.Rune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;

/* loaded from: classes2.dex */
public class SemDolbyAudioEffect extends AudioEffect {
    public static final int DIALOG_ENHANCER_TYPE_IMPAIRED = 0;
    public static final int DIALOG_ENHANCER_TYPE_NORMAL = 1;
    private static final int EFFECT_PARAM_DIALOG_ENHANCER_AMOUNT = 10;
    private static final int EFFECT_PARAM_DIALOG_ENHANCER_TYPE = 11;
    public static final int EFFECT_PARAM_EFF_ENAB = 19;
    public static final int EFFECT_PARAM_PROFILE = 0;
    private static final int EFFECT_PARAM_REVERT_SPECIFIC_PARAMS = 12;
    public static final int EFFECT_PARAM_STEREO_WIDENING_DISTANCE = 1;
    private static final int EFFECT_PARAM_VOLUME_LEVELER_AMOUNT = 9;
    public static final UUID EFFECT_TYPE_DOLBY_AUDIO_PROCESSING = UUID.fromString("46d279d9-9be7-453d-9d7c-ef937f675587");
    public static final UUID EFFECT_TYPE_DOLBY_GAME_AUDIO_PROCESSING = UUID.fromString("4f81d40e-05e2-47eb-9a0a-3686daf37649");
    public static final UUID EFFECT_TYPE_DOLBY_SPATIALIZER_AUDIO_PROCESSING = UUID.fromString("ccd4cf09-a79d-46c2-9aae-06a1698d6c8f");
    public static final int PROFILE_AUTO = 0;
    public static final int PROFILE_GAME = 4;
    public static final int PROFILE_GAME_1 = 6;
    public static final int PROFILE_GAME_2 = 7;
    public static final int PROFILE_LOUD_NORM = 9;
    public static final int PROFILE_MOVIE = 1;
    public static final int PROFILE_MUSIC = 2;
    public static final int PROFILE_OFF = 5;
    public static final int PROFILE_SPATIAL_AUDIO = 8;
    public static final int PROFILE_VOICE = 3;
    public static final int STEREO_WIDENING_DISTANCE_DEFAULT = -1;
    private static final String TAG = "SemDolbyAudioEffect";

    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogEnhancerType {
    }

    public SemDolbyAudioEffect(UUID uuid, int i, int i2) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(uuid, EFFECT_TYPE_NULL, i, i2);
        if (i2 == 0) {
            Log.w(TAG, "WARNING: attaching a SemDolbyAudioEffect to global output mix is deprecated!");
        }
    }

    public void setProfileEnabled(boolean z) {
        checkStatus(setParameter(19, z ? 1 : 0));
    }

    public boolean isProfileEnabled() {
        int[] iArr = new int[1];
        checkStatus(getParameter(19, iArr));
        return iArr[0] == 1;
    }

    public void setProfile(int i) throws IllegalArgumentException {
        if ((i < 0 || i > 5) && i != 8 && i != 9) {
            throw new IllegalArgumentException();
        }
        checkStatus(setParameter(0, i));
    }

    public int getProfile() {
        int[] iArr = new int[1];
        checkStatus(getParameter(0, iArr));
        return iArr[0];
    }

    public void setStereoWideningDistance(int i) throws IllegalArgumentException {
        if (i != -1 && (i < 4 || i > 64)) {
            throw new IllegalArgumentException();
        }
        checkStatus(setParameter(1, i));
    }

    public static boolean isSupported() {
        return Rune.SEC_AUDIO_DOLBY_ENABLED;
    }

    public static boolean isSupported(UUID uuid) {
        if (EFFECT_TYPE_DOLBY_AUDIO_PROCESSING.equals(uuid)) {
            return Rune.SEC_AUDIO_DOLBY_ENABLED;
        }
        if (EFFECT_TYPE_DOLBY_GAME_AUDIO_PROCESSING.equals(uuid)) {
            return Rune.SEC_AUDIO_DOLBY_GAME_FX;
        }
        return false;
    }

    public void setVolumeLevelerAmount(int i) throws IllegalArgumentException {
        checkStatus(setParameter(9, i));
    }

    public int getVolumeLevelerAmount() {
        int[] iArr = new int[1];
        checkStatus(getParameter(9, iArr));
        return iArr[0];
    }

    public void setDialogEnhancerAmount(int i) throws IllegalArgumentException {
        checkStatus(setParameter(10, i));
    }

    public int getDialogEnhancerAmount() {
        int[] iArr = new int[1];
        checkStatus(getParameter(10, iArr));
        return iArr[0];
    }

    public void setDialogEnhancerType(int i) throws IllegalArgumentException {
        if (i < 0 || i > 1) {
            throw new IllegalArgumentException();
        }
        checkStatus(setParameter(11, i));
    }

    public int getDialogEnhancerType() {
        int[] iArr = new int[1];
        checkStatus(getParameter(11, iArr));
        return iArr[0];
    }

    public void revertSpecificParams() {
        checkStatus(setParameter(12, new byte[4]));
    }
}
