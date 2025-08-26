package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class BassBoost extends AudioEffect {
    public static final int PARAM_STRENGTH = 1;
    public static final int PARAM_STRENGTH_SUPPORTED = 0;
    private static final String TAG = "BassBoost";
    private BaseParameterListener mBaseParamListener;
    private OnParameterChangeListener mParamListener;
    private final Object mParamListenerLock;
    private boolean mStrengthSupported;

    public interface OnParameterChangeListener {
        void onParameterChange(BassBoost bassBoost, int i, int i2, short s);
    }

    public BassBoost(int i, int i2) throws RuntimeException {
        super(EFFECT_TYPE_BASS_BOOST, EFFECT_TYPE_NULL, i, i2);
        this.mStrengthSupported = false;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        if (i2 == 0) {
            Log.w(TAG, "WARNING: attaching a BassBoost to global output mix is deprecated!");
        }
        int[] iArr = new int[1];
        checkStatus(getParameter(0, iArr));
        this.mStrengthSupported = iArr[0] != 0;
    }

    public boolean getStrengthSupported() {
        return this.mStrengthSupported;
    }

    public void setStrength(short s) throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        checkStatus(setParameter(1, s));
    }

    public short getRoundedStrength() throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        short[] sArr = new short[1];
        checkStatus(getParameter(1, sArr));
        return sArr[0];
    }

    private class BaseParameterListener implements AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            OnParameterChangeListener onParameterChangeListener;
            synchronized (BassBoost.this.mParamListenerLock) {
                onParameterChangeListener = BassBoost.this.mParamListener != null ? BassBoost.this.mParamListener : null;
            }
            if (onParameterChangeListener != null) {
                int iByteArrayToInt = bArr.length == 4 ? AudioEffect.byteArrayToInt(bArr, 0) : -1;
                short sByteArrayToShort = bArr2.length == 2 ? AudioEffect.byteArrayToShort(bArr2, 0) : (short) -1;
                if (iByteArrayToInt == -1 || sByteArrayToShort == -1) {
                    return;
                }
                onParameterChangeListener.onParameterChange(BassBoost.this, i, iByteArrayToInt, sByteArrayToShort);
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
        public short strength;

        public Settings() {
        }

        public Settings(String str) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, "=;");
            stringTokenizer.countTokens();
            if (stringTokenizer.countTokens() != 3) {
                throw new IllegalArgumentException("settings: " + str);
            }
            String strNextToken = stringTokenizer.nextToken();
            if (!strNextToken.equals(BassBoost.TAG)) {
                throw new IllegalArgumentException("invalid settings for BassBoost: " + strNextToken);
            }
            try {
                String strNextToken2 = stringTokenizer.nextToken();
                if (!strNextToken2.equals("strength")) {
                    throw new IllegalArgumentException("invalid key name: " + strNextToken2);
                }
                this.strength = Short.parseShort(stringTokenizer.nextToken());
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("invalid value for key: " + strNextToken);
            }
        }

        public String toString() {
            return new String("BassBoost;strength=" + Short.toString(this.strength));
        }
    }

    public Settings getProperties() throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        Settings settings = new Settings();
        short[] sArr = new short[1];
        checkStatus(getParameter(1, sArr));
        settings.strength = sArr[0];
        return settings;
    }

    public void setProperties(Settings settings) throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        checkStatus(setParameter(1, settings.strength));
    }
}
