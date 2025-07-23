package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class EnvironmentalReverb extends AudioEffect {
    public static final int PARAM_DECAY_HF_RATIO = 3;
    public static final int PARAM_DECAY_TIME = 2;
    public static final int PARAM_DENSITY = 9;
    public static final int PARAM_DIFFUSION = 8;
    private static final int PARAM_PROPERTIES = 10;
    public static final int PARAM_REFLECTIONS_DELAY = 5;
    public static final int PARAM_REFLECTIONS_LEVEL = 4;
    public static final int PARAM_REVERB_DELAY = 7;
    public static final int PARAM_REVERB_LEVEL = 6;
    public static final int PARAM_ROOM_HF_LEVEL = 1;
    public static final int PARAM_ROOM_LEVEL = 0;
    private static int PROPERTY_SIZE = 26;
    private static final String TAG = "EnvironmentalReverb";
    private BaseParameterListener mBaseParamListener;
    private OnParameterChangeListener mParamListener;
    private final Object mParamListenerLock;

    public interface OnParameterChangeListener {
        void onParameterChange(EnvironmentalReverb environmentalReverb, int i, int i2, int i3);
    }

    public EnvironmentalReverb(int i, int i2) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_ENV_REVERB, EFFECT_TYPE_NULL, i, i2);
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
    }

    public void setRoomLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(0, shortToByteArray(s)));
    }

    public short getRoomLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(0, bArr));
        return byteArrayToShort(bArr);
    }

    public void setRoomHFLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(1, shortToByteArray(s)));
    }

    public short getRoomHFLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(1, bArr));
        return byteArrayToShort(bArr);
    }

    public void setDecayTime(int i) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(2, intToByteArray(i)));
    }

    public int getDecayTime() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[4];
        checkStatus(getParameter(2, bArr));
        return byteArrayToInt(bArr);
    }

    public void setDecayHFRatio(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(3, shortToByteArray(s)));
    }

    public short getDecayHFRatio() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(3, bArr));
        return byteArrayToShort(bArr);
    }

    public void setReflectionsLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(4, shortToByteArray(s)));
    }

    public short getReflectionsLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(4, bArr));
        return byteArrayToShort(bArr);
    }

    public void setReflectionsDelay(int i) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(5, intToByteArray(i)));
    }

    public int getReflectionsDelay() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[4];
        checkStatus(getParameter(5, bArr));
        return byteArrayToInt(bArr);
    }

    public void setReverbLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(6, shortToByteArray(s)));
    }

    public short getReverbLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(6, bArr));
        return byteArrayToShort(bArr);
    }

    public void setReverbDelay(int i) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(7, intToByteArray(i)));
    }

    public int getReverbDelay() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[4];
        checkStatus(getParameter(7, bArr));
        return byteArrayToInt(bArr);
    }

    public void setDiffusion(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(8, shortToByteArray(s)));
    }

    public short getDiffusion() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(8, bArr));
        return byteArrayToShort(bArr);
    }

    public void setDensity(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(9, shortToByteArray(s)));
    }

    public short getDensity() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[2];
        checkStatus(getParameter(9, bArr));
        return byteArrayToShort(bArr);
    }

    private class BaseParameterListener implements AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            OnParameterChangeListener onParameterChangeListener;
            int byteArrayToInt;
            synchronized (EnvironmentalReverb.this.mParamListenerLock) {
                onParameterChangeListener = EnvironmentalReverb.this.mParamListener != null ? EnvironmentalReverb.this.mParamListener : null;
            }
            if (onParameterChangeListener != null) {
                int byteArrayToInt2 = bArr.length == 4 ? AudioEffect.byteArrayToInt(bArr, 0) : -1;
                if (bArr2.length == 2) {
                    byteArrayToInt = AudioEffect.byteArrayToShort(bArr2, 0);
                } else {
                    byteArrayToInt = bArr2.length == 4 ? AudioEffect.byteArrayToInt(bArr2, 0) : -1;
                }
                if (byteArrayToInt2 == -1 || byteArrayToInt == -1) {
                    return;
                }
                onParameterChangeListener.onParameterChange(EnvironmentalReverb.this, i, byteArrayToInt2, byteArrayToInt);
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

    public static class Settings {
        public short decayHFRatio;
        public int decayTime;
        public short density;
        public short diffusion;
        public int reflectionsDelay;
        public short reflectionsLevel;
        public int reverbDelay;
        public short reverbLevel;
        public short roomHFLevel;
        public short roomLevel;

        public Settings() {
        }

        public Settings(String str) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, "=;");
            stringTokenizer.countTokens();
            if (stringTokenizer.countTokens() != 21) {
                throw new IllegalArgumentException("settings: " + str);
            }
            String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(EnvironmentalReverb.TAG)) {
                throw new IllegalArgumentException("invalid settings for EnvironmentalReverb: " + nextToken);
            }
            try {
                String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("roomLevel")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.roomLevel = Short.parseShort(stringTokenizer.nextToken());
                String nextToken3 = stringTokenizer.nextToken();
                if (!nextToken3.equals("roomHFLevel")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken3);
                }
                this.roomHFLevel = Short.parseShort(stringTokenizer.nextToken());
                String nextToken4 = stringTokenizer.nextToken();
                if (!nextToken4.equals("decayTime")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken4);
                }
                this.decayTime = Integer.parseInt(stringTokenizer.nextToken());
                String nextToken5 = stringTokenizer.nextToken();
                if (!nextToken5.equals("decayHFRatio")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken5);
                }
                this.decayHFRatio = Short.parseShort(stringTokenizer.nextToken());
                String nextToken6 = stringTokenizer.nextToken();
                if (!nextToken6.equals("reflectionsLevel")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken6);
                }
                this.reflectionsLevel = Short.parseShort(stringTokenizer.nextToken());
                String nextToken7 = stringTokenizer.nextToken();
                if (!nextToken7.equals("reflectionsDelay")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken7);
                }
                this.reflectionsDelay = Integer.parseInt(stringTokenizer.nextToken());
                String nextToken8 = stringTokenizer.nextToken();
                if (!nextToken8.equals("reverbLevel")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken8);
                }
                this.reverbLevel = Short.parseShort(stringTokenizer.nextToken());
                String nextToken9 = stringTokenizer.nextToken();
                if (!nextToken9.equals("reverbDelay")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken9);
                }
                this.reverbDelay = Integer.parseInt(stringTokenizer.nextToken());
                String nextToken10 = stringTokenizer.nextToken();
                if (!nextToken10.equals("diffusion")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken10);
                }
                this.diffusion = Short.parseShort(stringTokenizer.nextToken());
                String nextToken11 = stringTokenizer.nextToken();
                if (!nextToken11.equals("density")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken11);
                }
                this.density = Short.parseShort(stringTokenizer.nextToken());
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public String toString() {
            return new String("EnvironmentalReverb;roomLevel=" + Short.toString(this.roomLevel) + ";roomHFLevel=" + Short.toString(this.roomHFLevel) + ";decayTime=" + Integer.toString(this.decayTime) + ";decayHFRatio=" + Short.toString(this.decayHFRatio) + ";reflectionsLevel=" + Short.toString(this.reflectionsLevel) + ";reflectionsDelay=" + Integer.toString(this.reflectionsDelay) + ";reverbLevel=" + Short.toString(this.reverbLevel) + ";reverbDelay=" + Integer.toString(this.reverbDelay) + ";diffusion=" + Short.toString(this.diffusion) + ";density=" + Short.toString(this.density));
        }
    }

    public Settings getProperties() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] bArr = new byte[PROPERTY_SIZE];
        checkStatus(getParameter(10, bArr));
        Settings settings = new Settings();
        settings.roomLevel = byteArrayToShort(bArr, 0);
        settings.roomHFLevel = byteArrayToShort(bArr, 2);
        settings.decayTime = byteArrayToInt(bArr, 4);
        settings.decayHFRatio = byteArrayToShort(bArr, 8);
        settings.reflectionsLevel = byteArrayToShort(bArr, 10);
        settings.reflectionsDelay = byteArrayToInt(bArr, 12);
        settings.reverbLevel = byteArrayToShort(bArr, 16);
        settings.reverbDelay = byteArrayToInt(bArr, 18);
        settings.diffusion = byteArrayToShort(bArr, 22);
        settings.density = byteArrayToShort(bArr, 24);
        return settings;
    }

    public void setProperties(Settings settings) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(10, concatArrays(shortToByteArray(settings.roomLevel), shortToByteArray(settings.roomHFLevel), intToByteArray(settings.decayTime), shortToByteArray(settings.decayHFRatio), shortToByteArray(settings.reflectionsLevel), intToByteArray(settings.reflectionsDelay), shortToByteArray(settings.reverbLevel), intToByteArray(settings.reverbDelay), shortToByteArray(settings.diffusion), shortToByteArray(settings.density))));
    }
}
