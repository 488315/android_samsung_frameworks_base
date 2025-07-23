package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class LoudnessEnhancer extends AudioEffect {
    public static final int PARAM_TARGET_GAIN_MB = 0;
    private static final String TAG = "LoudnessEnhancer";
    private BaseParameterListener mBaseParamListener;
    private OnParameterChangeListener mParamListener;
    private final Object mParamListenerLock;

    public interface OnParameterChangeListener {
        void onParameterChange(LoudnessEnhancer loudnessEnhancer, int i, int i2);
    }

    public LoudnessEnhancer(int i) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_LOUDNESS_ENHANCER, EFFECT_TYPE_NULL, 0, i);
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        if (i == 0) {
            Log.w(TAG, "WARNING: attaching a LoudnessEnhancer to global output mix is deprecated!");
        }
    }

    public LoudnessEnhancer(int i, int i2) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_LOUDNESS_ENHANCER, EFFECT_TYPE_NULL, i, i2);
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        if (i2 == 0) {
            Log.w(TAG, "WARNING: attaching a LoudnessEnhancer to global output mix is deprecated!");
        }
    }

    public void setTargetGain(int i) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(0, i));
    }

    public float getTargetGain() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(getParameter(0, new int[1]));
        return r0[0];
    }

    private class BaseParameterListener implements AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            OnParameterChangeListener onParameterChangeListener;
            if (i != 0) {
                return;
            }
            synchronized (LoudnessEnhancer.this.mParamListenerLock) {
                onParameterChangeListener = LoudnessEnhancer.this.mParamListener != null ? LoudnessEnhancer.this.mParamListener : null;
            }
            if (onParameterChangeListener != null) {
                int byteArrayToInt = bArr.length == 4 ? AudioEffect.byteArrayToInt(bArr, 0) : -1;
                int byteArrayToInt2 = bArr2.length == 4 ? AudioEffect.byteArrayToInt(bArr2, 0) : Integer.MIN_VALUE;
                if (byteArrayToInt == -1 || byteArrayToInt2 == Integer.MIN_VALUE) {
                    return;
                }
                onParameterChangeListener.onParameterChange(LoudnessEnhancer.this, byteArrayToInt, byteArrayToInt2);
            }
        }
    }

    public void setParameterListener(OnParameterChangeListener onParameterChangeListener) {
        synchronized (this.mParamListenerLock) {
            if (this.mParamListener == null) {
                BaseParameterListener baseParameterListener = new BaseParameterListener();
                this.mBaseParamListener = baseParameterListener;
                super.setParameterListener(baseParameterListener);
            }
            this.mParamListener = onParameterChangeListener;
        }
    }

    public static class Settings {
        public int targetGainmB;

        public Settings() {
        }

        public Settings(String str) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, "=;");
            if (stringTokenizer.countTokens() != 3) {
                throw new IllegalArgumentException("settings: " + str);
            }
            String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals(LoudnessEnhancer.TAG)) {
                throw new IllegalArgumentException("invalid settings for LoudnessEnhancer: " + nextToken);
            }
            try {
                String nextToken2 = stringTokenizer.nextToken();
                if (!nextToken2.equals("targetGainmB")) {
                    throw new IllegalArgumentException("invalid key name: " + nextToken2);
                }
                this.targetGainmB = Integer.parseInt(stringTokenizer.nextToken());
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("invalid value for key: " + nextToken);
            }
        }

        public String toString() {
            return new String("LoudnessEnhancer;targetGainmB=" + Integer.toString(this.targetGainmB));
        }
    }

    public Settings getProperties() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        Settings settings = new Settings();
        int[] iArr = new int[1];
        checkStatus(getParameter(0, iArr));
        settings.targetGainmB = iArr[0];
        return settings;
    }

    public void setProperties(Settings settings) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        checkStatus(setParameter(0, settings.targetGainmB));
    }
}
