package android.media.audiofx;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class Virtualizer extends AudioEffect {
    private static final boolean DEBUG = false;
    public static final int PARAM_FORCE_VIRTUALIZATION_MODE = 3;
    public static final int PARAM_STRENGTH = 1;
    public static final int PARAM_STRENGTH_SUPPORTED = 0;
    public static final int PARAM_VIRTUALIZATION_MODE = 4;
    public static final int PARAM_VIRTUAL_SPEAKER_ANGLES = 2;
    private static final String TAG = "Virtualizer";
    public static final int VIRTUALIZATION_MODE_AUTO = 1;
    public static final int VIRTUALIZATION_MODE_BINAURAL = 2;
    public static final int VIRTUALIZATION_MODE_OFF = 0;
    public static final int VIRTUALIZATION_MODE_TRANSAURAL = 3;
    private BaseParameterListener mBaseParamListener;
    private OnParameterChangeListener mParamListener;
    private final Object mParamListenerLock;
    private boolean mStrengthSupported;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForceVirtualizationMode {
    }

    public interface OnParameterChangeListener {
        void onParameterChange(Virtualizer virtualizer, int i, int i2, short s);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VirtualizationMode {
    }

    private static int deviceToMode(int i) {
        if (i == 19) {
            return 3;
        }
        if (i == 22) {
            return 2;
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 7:
                return 2;
            case 2:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return 3;
            default:
                return 0;
        }
    }

    public Virtualizer(int i, int i2) throws RuntimeException {
        super(EFFECT_TYPE_VIRTUALIZER, EFFECT_TYPE_NULL, i, i2);
        this.mStrengthSupported = false;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        if (i2 == 0) {
            Log.w(TAG, "WARNING: attaching a Virtualizer to global output mix is deprecated!");
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

    private boolean getAnglesInt(int i, int i2, int[] iArr) throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        int i3;
        if (i == 0) {
            throw new IllegalArgumentException("Virtualizer: illegal CHANNEL_INVALID channel mask");
        }
        if (i == 1) {
            i = 12;
        }
        int iChannelCountFromOutChannelMask = AudioFormat.channelCountFromOutChannelMask(i);
        if (iArr != null && iArr.length < (i3 = iChannelCountFromOutChannelMask * 3)) {
            Log.e(TAG, "Size of array for angles cannot accomodate number of channels in mask (" + iChannelCountFromOutChannelMask + NavigationBarInflaterView.KEY_CODE_END);
            throw new IllegalArgumentException("Virtualizer: array for channel / angle pairs is too small: is " + iArr.length + ", should be " + i3);
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(12);
        byteBufferAllocate.order(ByteOrder.nativeOrder());
        byteBufferAllocate.putInt(2);
        byteBufferAllocate.putInt(AudioFormat.convertChannelOutMaskToNativeMask(i));
        byteBufferAllocate.putInt(AudioDeviceInfo.convertDeviceTypeToInternalDevice(i2));
        byte[] bArr = new byte[iChannelCountFromOutChannelMask * 12];
        int parameter = getParameter(byteBufferAllocate.array(), bArr);
        if (parameter < 0) {
            if (parameter == -4) {
                return false;
            }
            checkStatus(parameter);
            Log.e(TAG, "unexpected status code " + parameter + " after getParameter(PARAM_VIRTUAL_SPEAKER_ANGLES)");
            return false;
        }
        if (iArr != null) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
            byteBufferWrap.order(ByteOrder.nativeOrder());
            for (int i4 = 0; i4 < iChannelCountFromOutChannelMask; i4++) {
                int i5 = i4 * 3;
                int i6 = i4 * 12;
                iArr[i5] = AudioFormat.convertNativeChannelMaskToOutMask(byteBufferWrap.getInt(i6));
                iArr[i5 + 1] = byteBufferWrap.getInt(i6 + 4);
                iArr[i5 + 2] = byteBufferWrap.getInt(i6 + 8);
            }
        }
        return true;
    }

    private static int getDeviceForModeQuery(int i) throws IllegalArgumentException {
        if (i == 2) {
            return 4;
        }
        if (i == 3) {
            return 2;
        }
        throw new IllegalArgumentException("Virtualizer: illegal virtualization mode " + i);
    }

    private static int getDeviceForModeForce(int i) throws IllegalArgumentException {
        if (i == 1) {
            return 0;
        }
        return getDeviceForModeQuery(i);
    }

    public boolean canVirtualize(int i, int i2) throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        return getAnglesInt(i, getDeviceForModeQuery(i2), null);
    }

    public boolean getSpeakerAngles(int i, int i2, int[] iArr) throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        if (iArr == null) {
            throw new IllegalArgumentException("Virtualizer: illegal null channel / angle array");
        }
        return getAnglesInt(i, getDeviceForModeQuery(i2), iArr);
    }

    public boolean forceVirtualizationMode(int i) throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        int parameter = setParameter(3, AudioDeviceInfo.convertDeviceTypeToInternalDevice(getDeviceForModeForce(i)));
        if (parameter >= 0) {
            return true;
        }
        if (parameter == -4) {
            return false;
        }
        checkStatus(parameter);
        Log.e(TAG, "unexpected status code " + parameter + " after setParameter(PARAM_FORCE_VIRTUALIZATION_MODE)");
        return false;
    }

    public int getVirtualizationMode() throws IllegalStateException, UnsupportedOperationException {
        int[] iArr = new int[1];
        int parameter = getParameter(4, iArr);
        if (parameter >= 0) {
            return deviceToMode(AudioDeviceInfo.convertInternalDeviceToDeviceType(iArr[0]));
        }
        if (parameter == -4) {
            return 0;
        }
        checkStatus(parameter);
        Log.e(TAG, "unexpected status code " + parameter + " after getParameter(PARAM_VIRTUALIZATION_MODE)");
        return 0;
    }

    private class BaseParameterListener implements AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        @Override // android.media.audiofx.AudioEffect.OnParameterChangeListener
        public void onParameterChange(AudioEffect audioEffect, int i, byte[] bArr, byte[] bArr2) {
            OnParameterChangeListener onParameterChangeListener;
            synchronized (Virtualizer.this.mParamListenerLock) {
                onParameterChangeListener = Virtualizer.this.mParamListener != null ? Virtualizer.this.mParamListener : null;
            }
            if (onParameterChangeListener != null) {
                int iByteArrayToInt = bArr.length == 4 ? AudioEffect.byteArrayToInt(bArr, 0) : -1;
                short sByteArrayToShort = bArr2.length == 2 ? AudioEffect.byteArrayToShort(bArr2, 0) : (short) -1;
                if (iByteArrayToInt == -1 || sByteArrayToShort == -1) {
                    return;
                }
                onParameterChangeListener.onParameterChange(Virtualizer.this, i, iByteArrayToInt, sByteArrayToShort);
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
            if (!strNextToken.equals(Virtualizer.TAG)) {
                throw new IllegalArgumentException("invalid settings for Virtualizer: " + strNextToken);
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
            return new String("Virtualizer;strength=" + Short.toString(this.strength));
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
