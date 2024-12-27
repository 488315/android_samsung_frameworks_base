package android.media.audiofx;

import android.util.Log;

import com.samsung.android.audio.Rune;

import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import java.util.UUID;

/* loaded from: classes2.dex */
public class SemSoundAlive extends AudioEffect {
    public static final UUID EFFECT_TYPE_SOUNDALIVE =
            UUID.fromString("c4da1d1f-7cdf-42e2-ba60-efc7eb3508a3");
    public static final int PARAM_3DPA = 13;
    public static final int PARAM_BAND_FREQ_RANGE = 4;
    public static final int PARAM_BAND_LEVEL = 2;
    public static final int PARAM_CENTER_FREQ = 3;
    public static final int PARAM_CURRENT_PRESET = 6;
    public static final int PARAM_EQUALIZER_COORDINATOR = 11;
    public static final int PARAM_GET_BAND = 5;
    public static final int PARAM_GET_NUM_OF_PRESETS = 7;
    public static final int PARAM_GET_PRESET_NAME = 8;
    public static final int PARAM_HMT = 12;
    public static final int PARAM_LEVEL_RANGE = 1;
    public static final int PARAM_NUM_BANDS = 0;
    private static final int PARAM_PROPERTIES = 9;
    public static final int PARAM_STRENGTH = 10;
    public static final int PARAM_STRING_SIZE_MAX = 32;
    public static final int PRESET_CLASSIC = 4;
    public static final int PRESET_JAZZ = 3;
    public static final int PRESET_NORMAL = 0;
    public static final int PRESET_POP = 1;
    public static final int PRESET_ROCK = 2;
    public static final int PRESET_USER = 5;
    private static final String TAG = "SemSoundAlive";
    private BaseErrorListener mBaseErrorListener;
    private BaseParameterListener mBaseParamListener;
    private OnErrorListener mErrorListener;
    private final Object mErrorListenerLock;
    private short mNumBands;
    private int mNumPresets;
    private OnParameterChangeListener mParamListener;
    private final Object mParamListenerLock;
    private String[] mPresetNames;

    public interface OnErrorListener {
        void onError();
    }

    public interface OnParameterChangeListener {
        void onParameterChange(SemSoundAlive semSoundAlive, int i, int i2, int i3, int i4);
    }

    public SemSoundAlive(int priority, int audioSession) throws IllegalArgumentException {
        super(EFFECT_TYPE_SOUNDALIVE, EFFECT_TYPE_NULL, priority, audioSession);
        this.mNumBands = (short) 0;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        this.mErrorListener = null;
        this.mBaseErrorListener = null;
        this.mErrorListenerLock = new Object();
        if (audioSession == 0) {
            Log.w(TAG, "WARNING: attaching an SemSoundAlive to global output mix is deprecated!");
        }
        getNumberOfBands();
        this.mNumPresets = getNumberOfPresets();
        if (this.mNumPresets != 0) {
            this.mPresetNames = new String[this.mNumPresets];
            byte[] value = new byte[32];
            int[] param = new int[2];
            param[0] = 8;
            for (int i = 0; i < this.mNumPresets; i++) {
                param[1] = i;
                checkStatus(getParameter(param, value));
                int length = 0;
                while (value[length] != 0) {
                    length++;
                }
                this.mPresetNames[i] = new String(value, 0, length, StandardCharsets.ISO_8859_1);
            }
        }
    }

    public short getNumberOfBands() throws IllegalArgumentException {
        if (this.mNumBands != 0) {
            return this.mNumBands;
        }
        int[] param = {0};
        short[] result = new short[1];
        checkStatus(getParameter(param, result));
        this.mNumBands = result[0];
        return this.mNumBands;
    }

    public short[] getBandLevelRange() {
        short[] result = new short[2];
        checkStatus(getParameter(1, result));
        return result;
    }

    public void setBandLevel(short band, short level) throws IllegalArgumentException {
        int[] param = {2, band};
        short[] value = {level};
        checkStatus(setParameter(param, value));
    }

    public void setAllBandLevels(short[] levels) throws IllegalArgumentException {
        if (levels.length != getNumberOfBands()) {
            Log.w(TAG, "WARNING: invalid number of bands");
            return;
        }
        Settings settings = new Settings();
        settings.curPreset = (short) -1;
        settings.numBands = (short) levels.length;
        settings.bandLevels = levels;
        setProperties(settings);
    }

    public short getBandLevel(short band) throws IllegalArgumentException {
        short[] result = new short[1];
        int[] param = {2, band};
        checkStatus(getParameter(param, result));
        return result[0];
    }

    public int getCenterFreq(short band) throws IllegalArgumentException {
        int[] result = new int[1];
        int[] param = {3, band};
        checkStatus(getParameter(param, result));
        return result[0];
    }

    public int[] getBandFreqRange(short band) throws IllegalArgumentException {
        int[] result = new int[2];
        int[] param = {4, band};
        checkStatus(getParameter(param, result));
        return result;
    }

    public short getBand(int frequency) throws IllegalArgumentException {
        short[] result = new short[1];
        int[] param = {5, frequency};
        checkStatus(getParameter(param, result));
        return result[0];
    }

    public short getCurrentPreset() throws IllegalArgumentException {
        short[] result = new short[1];
        checkStatus(getParameter(6, result));
        return result[0];
    }

    public void usePreset(short preset) throws IllegalArgumentException {
        checkStatus(setParameter(6, preset));
    }

    public short getNumberOfPresets() throws IllegalArgumentException {
        short[] result = new short[1];
        checkStatus(getParameter(7, result));
        return result[0];
    }

    public String getPresetName(short preset) {
        if (preset >= 0 && preset < this.mNumPresets) {
            return this.mPresetNames[preset];
        }
        return "";
    }

    public void setStrength(short type, short strength) throws IllegalArgumentException {
        int[] param = {10, type};
        short[] value = {strength};
        checkStatus(setParameter(param, value));
    }

    public short getRoundedStrength(short type) throws IllegalArgumentException {
        short[] result = new short[1];
        int[] param = {10, type};
        checkStatus(getParameter(param, result));
        return result[0];
    }

    public void setEqCoordinator(int Sqrow, int Sqcol) throws IllegalArgumentException {
        int[] param = {11};
        int[] value = {Sqrow, Sqcol};
        checkStatus(setParameter(param, value));
    }

    public void setHMT(int band, int level) throws IllegalArgumentException {
        int[] param = {12, band};
        int[] value = {level};
    }

    public void set3dEffectPosition(boolean onoff, double position)
            throws IllegalArgumentException {
        if (-1.0d <= position && position <= 1.0d) {
            int[] param = new int[2];
            int[] value = new int[1];
            param[0] = 13;
            param[1] = onoff ? 1 : -1;
            value[0] = (int) (100.0d * position);
            setEnabled(true);
            checkStatus(setParameter(param, value));
        }
    }

    public int getSpeakerCount() {
        return Rune.SEC_AUDIO_NUM_OF_SPEAKER;
    }

    private class BaseParameterListener implements AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {}

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(AudioEffect effect, int status, byte[] param, byte[] value) {
            int v;
            OnParameterChangeListener l = null;
            synchronized (SemSoundAlive.this.mParamListenerLock) {
                if (SemSoundAlive.this.mParamListener != null) {
                    l = SemSoundAlive.this.mParamListener;
                }
            }
            if (l != null) {
                int p1 = -1;
                int p2 = -1;
                if (param.length >= 4) {
                    p1 = AudioEffect.byteArrayToInt(param, 0);
                    if (param.length >= 8) {
                        p2 = AudioEffect.byteArrayToInt(param, 4);
                    }
                }
                if (value.length == 2) {
                    int v2 = AudioEffect.byteArrayToShort(value, 0);
                    v = v2;
                } else if (value.length != 4) {
                    v = -1;
                } else {
                    int v3 = AudioEffect.byteArrayToInt(value, 0);
                    v = v3;
                }
                if (p1 != -1 && v != -1) {
                    l.onParameterChange(SemSoundAlive.this, status, p1, p2, v);
                }
            }
        }
    }

    public void setParameterListener(OnParameterChangeListener listener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mParamListener = listener;
                this.mBaseParamListener = new BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
        }
    }

    private class BaseErrorListener implements AudioEffect.OnErrorListener {
        private BaseErrorListener() {}

        @Override // android.media.audiofx.AudioEffect.OnErrorListener
        public void onError() {
            OnErrorListener l = null;
            synchronized (SemSoundAlive.this.mErrorListenerLock) {
                if (SemSoundAlive.this.mErrorListener != null) {
                    l = SemSoundAlive.this.mErrorListener;
                }
            }
            if (l != null) {
                l.onError();
            }
        }
    }

    public void setErrorListener(OnErrorListener listener) {
        synchronized (this.mErrorListenerLock) {
            if (this.mErrorListener == null) {
                this.mErrorListener = listener;
                this.mBaseErrorListener = new BaseErrorListener();
                super.setErrorListener(this.mBaseErrorListener);
            }
        }
    }

    public static class Settings {
        public short[] bandLevels;
        public short curPreset;
        public short numBands;

        public Settings() {
            this.numBands = (short) 0;
            this.bandLevels = null;
        }

        public Settings(String settings) {
            this.numBands = (short) 0;
            this.bandLevels = null;
            StringTokenizer st = new StringTokenizer(settings, "=;");
            st.countTokens();
            if (st.countTokens() < 5) {
                throw new IllegalArgumentException("settings: " + settings);
            }
            String key = st.nextToken();
            if (!SemSoundAlive.TAG.equals(key)) {
                throw new IllegalArgumentException("invalid settings for SemSoundAlive: " + key);
            }
            try {
                String key2 = st.nextToken();
                if (!"curPreset".equals(key2)) {
                    throw new IllegalArgumentException("invalid key name: " + key2);
                }
                this.curPreset = Short.parseShort(st.nextToken());
                String key3 = st.nextToken();
                if (!"numBands".equals(key3)) {
                    throw new IllegalArgumentException("invalid key name: " + key3);
                }
                this.numBands = Short.parseShort(st.nextToken());
                if (st.countTokens() != this.numBands * 2) {
                    throw new IllegalArgumentException("settings: " + settings);
                }
                this.bandLevels = new short[this.numBands];
                for (int i = 0; i < this.numBands; i++) {
                    String key4 = st.nextToken();
                    if (!("band" + (i + 1) + "Level").equals(key4)) {
                        throw new IllegalArgumentException("invalid key name: " + key4);
                    }
                    this.bandLevels[i] = Short.parseShort(st.nextToken());
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid value for key: " + key);
            }
        }

        public String toString() {
            String str =
                    "SemSoundAlive;curPreset="
                            + ((int) this.curPreset)
                            + ";numBands="
                            + ((int) this.numBands);
            for (int i = 0; i < this.numBands; i++) {
                str = str.concat(";band" + (i + 1) + "Level=" + ((int) this.bandLevels[i]));
            }
            return str;
        }
    }

    @Override // android.media.audiofx.AudioEffect
    public int getParameter(int param, byte[] value) throws IllegalStateException {
        byte[] p = intToByteArray(param);
        return super.getParameter(p, value);
    }

    public Settings getProperties() throws IllegalArgumentException {
        byte[] param = new byte[(this.mNumBands * 2) + 4];
        checkStatus(getParameter(9, param));
        Settings settings = new Settings();
        settings.curPreset = byteArrayToShort(param, 0);
        settings.numBands = byteArrayToShort(param, 2);
        settings.bandLevels = new short[this.mNumBands];
        for (int i = 0; i < this.mNumBands; i++) {
            settings.bandLevels[i] = byteArrayToShort(param, (i * 2) + 4);
        }
        return settings;
    }

    public void setProperties(Settings settings) throws IllegalArgumentException {
        if (settings.numBands != settings.bandLevels.length
                || settings.numBands != this.mNumBands) {
            throw new IllegalArgumentException(
                    "settings invalid band count: " + ((int) settings.numBands));
        }
        byte[] param =
                concatArrays(
                        shortToByteArray(settings.curPreset), shortToByteArray(this.mNumBands));
        for (int i = 0; i < this.mNumBands; i++) {
            param = concatArrays(param, shortToByteArray(settings.bandLevels[i]));
        }
        checkStatus(setParameter(9, param));
    }
}
