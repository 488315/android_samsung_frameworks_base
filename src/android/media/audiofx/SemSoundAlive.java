package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import com.samsung.android.audio.Rune;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import java.util.UUID;

/* loaded from: classes2.dex */
public class SemSoundAlive extends AudioEffect {
    public static final UUID EFFECT_TYPE_SOUNDALIVE = UUID.fromString("c4da1d1f-7cdf-42e2-ba60-efc7eb3508a3");
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

    public void setHMT(int i, int i2) throws IllegalArgumentException {
    }

    public SemSoundAlive(int i, int i2) throws IllegalArgumentException {
        super(EFFECT_TYPE_SOUNDALIVE, EFFECT_TYPE_NULL, i, i2);
        this.mNumBands = (short) 0;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        this.mErrorListener = null;
        this.mBaseErrorListener = null;
        this.mErrorListenerLock = new Object();
        if (i2 == 0) {
            Log.w(TAG, "WARNING: attaching an SemSoundAlive to global output mix is deprecated!");
        }
        getNumberOfBands();
        int numberOfPresets = getNumberOfPresets();
        this.mNumPresets = numberOfPresets;
        if (numberOfPresets != 0) {
            this.mPresetNames = new String[numberOfPresets];
            byte[] bArr = new byte[32];
            for (int i3 = 0; i3 < this.mNumPresets; i3++) {
                checkStatus(getParameter(new int[]{8, i3}, bArr));
                int i4 = 0;
                while (bArr[i4] != 0) {
                    i4++;
                }
                this.mPresetNames[i3] = new String(bArr, 0, i4, StandardCharsets.ISO_8859_1);
            }
        }
    }

    public short getNumberOfBands() throws IllegalArgumentException {
        short s = this.mNumBands;
        if (s != 0) {
            return s;
        }
        short[] sArr = new short[1];
        checkStatus(getParameter(new int[]{0}, sArr));
        short s2 = sArr[0];
        this.mNumBands = s2;
        return s2;
    }

    public short[] getBandLevelRange() {
        short[] sArr = new short[2];
        checkStatus(getParameter(1, sArr));
        return sArr;
    }

    public void setBandLevel(short s, short s2) throws IllegalArgumentException {
        checkStatus(setParameter(new int[]{2, s}, new short[]{s2}));
    }

    public void setAllBandLevels(short[] sArr) throws IllegalArgumentException {
        if (sArr.length != getNumberOfBands()) {
            Log.w(TAG, "WARNING: invalid number of bands");
            return;
        }
        Settings settings = new Settings();
        settings.curPreset = (short) -1;
        settings.numBands = (short) sArr.length;
        settings.bandLevels = sArr;
        setProperties(settings);
    }

    public short getBandLevel(short s) throws IllegalArgumentException {
        short[] sArr = new short[1];
        checkStatus(getParameter(new int[]{2, s}, sArr));
        return sArr[0];
    }

    public int getCenterFreq(short s) throws IllegalArgumentException {
        int[] iArr = new int[1];
        checkStatus(getParameter(new int[]{3, s}, iArr));
        return iArr[0];
    }

    public int[] getBandFreqRange(short s) throws IllegalArgumentException {
        int[] iArr = new int[2];
        checkStatus(getParameter(new int[]{4, s}, iArr));
        return iArr;
    }

    public short getBand(int i) throws IllegalArgumentException {
        short[] sArr = new short[1];
        checkStatus(getParameter(new int[]{5, i}, sArr));
        return sArr[0];
    }

    public short getCurrentPreset() throws IllegalArgumentException {
        short[] sArr = new short[1];
        checkStatus(getParameter(6, sArr));
        return sArr[0];
    }

    public void usePreset(short s) throws IllegalArgumentException {
        checkStatus(setParameter(6, s));
    }

    public short getNumberOfPresets() throws IllegalArgumentException {
        short[] sArr = new short[1];
        checkStatus(getParameter(7, sArr));
        return sArr[0];
    }

    public String getPresetName(short s) {
        if (s >= 0 && s < this.mNumPresets) {
            return this.mPresetNames[s];
        }
        return "";
    }

    public void setStrength(short s, short s2) throws IllegalArgumentException {
        checkStatus(setParameter(new int[]{10, s}, new short[]{s2}));
    }

    public short getRoundedStrength(short s) throws IllegalArgumentException {
        short[] sArr = new short[1];
        checkStatus(getParameter(new int[]{10, s}, sArr));
        return sArr[0];
    }

    public void setEqCoordinator(int i, int i2) throws IllegalArgumentException {
        checkStatus(setParameter(new int[]{11}, new int[]{i, i2}));
    }

    public void set3dEffectPosition(boolean z, double d) throws IllegalArgumentException {
        if (-1.0d > d || d > 1.0d) {
            return;
        }
        int i = z ? 1 : -1;
        setEnabled(true);
        checkStatus(setParameter(new int[]{13, i}, new int[]{(int) (d * 100.0d)}));
    }

    public int getSpeakerCount() {
        return Rune.SEC_AUDIO_NUM_OF_SPEAKER;
    }

    private class BaseParameterListener implements AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            OnParameterChangeListener onParameterChangeListener;
            int i2;
            int i3;
            int i4;
            int byteArrayToInt;
            synchronized (SemSoundAlive.this.mParamListenerLock) {
                onParameterChangeListener = SemSoundAlive.this.mParamListener != null ? SemSoundAlive.this.mParamListener : null;
            }
            if (onParameterChangeListener != null) {
                if (bArr.length >= 4) {
                    int byteArrayToInt2 = AudioEffect.byteArrayToInt(bArr, 0);
                    if (bArr.length >= 8) {
                        i2 = byteArrayToInt2;
                        i3 = AudioEffect.byteArrayToInt(bArr, 4);
                    } else {
                        i2 = byteArrayToInt2;
                        i3 = -1;
                    }
                } else {
                    i2 = -1;
                    i3 = -1;
                }
                if (bArr2.length == 2) {
                    byteArrayToInt = AudioEffect.byteArrayToShort(bArr2, 0);
                } else {
                    if (bArr2.length != 4) {
                        i4 = -1;
                        if (i2 != -1 || i4 == -1) {
                        }
                        onParameterChangeListener.onParameterChange(SemSoundAlive.this, i, i2, i3, i4);
                        return;
                    }
                    byteArrayToInt = AudioEffect.byteArrayToInt(bArr2, 0);
                }
                i4 = byteArrayToInt;
                if (i2 != -1) {
                }
            }
        }
    }

    public void setParameterListener(OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                this.mParamListener = onParameterChangeListener;
                BaseParameterListener baseParameterListener = new BaseParameterListener();
                this.mBaseParamListener = baseParameterListener;
                super.setParameterListener(baseParameterListener);
            }
        }
    }

    private class BaseErrorListener implements AudioEffect.OnErrorListener {
        private BaseErrorListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnErrorListener
        public void onError() {
            OnErrorListener onErrorListener;
            synchronized (SemSoundAlive.this.mErrorListenerLock) {
                onErrorListener = SemSoundAlive.this.mErrorListener != null ? SemSoundAlive.this.mErrorListener : null;
            }
            if (onErrorListener != null) {
                onErrorListener.onError();
            }
        }
    }

    public void setErrorListener(OnErrorListener onErrorListener) {
        synchronized (this.mErrorListenerLock) {
            if (this.mErrorListener == null) {
                this.mErrorListener = onErrorListener;
                BaseErrorListener baseErrorListener = new BaseErrorListener();
                this.mBaseErrorListener = baseErrorListener;
                super.setErrorListener(baseErrorListener);
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

        public Settings(String str) {
            int i = 0;
            this.numBands = (short) 0;
            this.bandLevels = null;
            StringTokenizer stringTokenizer = new StringTokenizer(str, "=;");
            stringTokenizer.countTokens();
            if (stringTokenizer.countTokens() < 5) {
                throw new IllegalArgumentException("settings: " + str);
            }
            String nextToken = stringTokenizer.nextToken();
            if (!SemSoundAlive.TAG.equals(nextToken)) {
                throw new IllegalArgumentException("invalid settings for SemSoundAlive: " + nextToken);
            }
            try {
                String nextToken2 = stringTokenizer.nextToken();
                if (!"curPreset".equals(nextToken2)) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.curPreset = Short.parseShort(stringTokenizer.nextToken());
                String nextToken3 = stringTokenizer.nextToken();
                if (!"numBands".equals(nextToken3)) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken3);
                }
                this.numBands = Short.parseShort(stringTokenizer.nextToken());
                int countTokens = stringTokenizer.countTokens();
                int i2 = this.numBands;
                if (countTokens != i2 * 2) {
                    throw new IllegalArgumentException("settings: " + str);
                }
                this.bandLevels = new short[i2];
                while (i < this.numBands) {
                    String nextToken4 = stringTokenizer.nextToken();
                    StringBuilder sb = new StringBuilder();
                    sb.append("band");
                    int i3 = i + 1;
                    sb.append(i3);
                    sb.append("Level");
                    if (!sb.toString().equals(nextToken4)) {
                        throw new IllegalArgumentException("invalid key name: " + nextToken4);
                    }
                    this.bandLevels[i] = Short.parseShort(stringTokenizer.nextToken());
                    i = i3;
                }
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public String toString() {
            String str = "SemSoundAlive;curPreset=" + ((int) this.curPreset) + ";numBands=" + ((int) this.numBands);
            int i = 0;
            while (i < this.numBands) {
                StringBuilder sb = new StringBuilder(";band");
                int i2 = i + 1;
                sb.append(i2);
                sb.append("Level=");
                sb.append((int) this.bandLevels[i]);
                str = str.concat(sb.toString());
                i = i2;
            }
            return str;
        }
    }

    @Override // android.media.audiofx.AudioEffect
    public int getParameter(int i, byte[] bArr) throws IllegalStateException {
        return super.getParameter(intToByteArray(i), bArr);
    }

    public Settings getProperties() throws IllegalArgumentException {
        byte[] bArr = new byte[(this.mNumBands * 2) + 4];
        checkStatus(getParameter(9, bArr));
        Settings settings = new Settings();
        settings.curPreset = byteArrayToShort(bArr, 0);
        settings.numBands = byteArrayToShort(bArr, 2);
        settings.bandLevels = new short[this.mNumBands];
        for (int i = 0; i < this.mNumBands; i++) {
            settings.bandLevels[i] = byteArrayToShort(bArr, (i * 2) + 4);
        }
        return settings;
    }

    public void setProperties(Settings settings) throws IllegalArgumentException {
        if (settings.numBands != settings.bandLevels.length || settings.numBands != this.mNumBands) {
            throw new IllegalArgumentException("settings invalid band count: " + ((int) settings.numBands));
        }
        byte[] concatArrays = concatArrays(shortToByteArray(settings.curPreset), shortToByteArray(this.mNumBands));
        for (int i = 0; i < this.mNumBands; i++) {
            concatArrays = concatArrays(concatArrays, shortToByteArray(settings.bandLevels[i]));
        }
        checkStatus(setParameter(9, concatArrays));
    }
}
