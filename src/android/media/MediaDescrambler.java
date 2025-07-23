package android.media;

import android.hardware.cas.IDescrambler;
import android.hardware.cas.V1_0.IDescramblerBase;
import android.media.MediaCas;
import android.media.MediaCasException;
import android.media.MediaCodec;
import android.os.IHwBinder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class MediaDescrambler implements AutoCloseable {
    public static final byte SCRAMBLE_CONTROL_EVEN_KEY = 2;
    public static final byte SCRAMBLE_CONTROL_ODD_KEY = 3;
    public static final byte SCRAMBLE_CONTROL_RESERVED = 1;
    public static final byte SCRAMBLE_CONTROL_UNSCRAMBLED = 0;
    public static final byte SCRAMBLE_FLAG_PES_HEADER = 1;
    private static final String TAG = "MediaDescrambler";
    private DescramblerWrapper mIDescrambler;
    private boolean mIsAidlHal;
    private long mNativeContext;

    private interface DescramblerWrapper {
        IHwBinder asBinder();

        int descramble(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, MediaCodec.CryptoInfo cryptoInfo) throws RemoteException;

        void release() throws RemoteException;

        boolean requiresSecureDecoderComponent(String str) throws RemoteException;

        void setMediaCasSession(byte[] bArr) throws RemoteException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final native int native_descramble(byte b, byte b2, int i, int[] iArr, int[] iArr2, ByteBuffer byteBuffer, int i2, int i3, ByteBuffer byteBuffer2, int i4, int i5) throws RemoteException;

    private static final native void native_init();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_release();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_setup(IHwBinder iHwBinder);

    private class AidlDescrambler implements DescramblerWrapper {
        IDescrambler mAidlDescrambler;

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public IHwBinder asBinder() {
            return null;
        }

        AidlDescrambler(MediaDescrambler mediaDescrambler, IDescrambler iDescrambler) throws Exception {
            if (iDescrambler != null) {
                this.mAidlDescrambler = iDescrambler;
                return;
            }
            throw new Exception("Descrambler could not be created");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public int descramble(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, MediaCodec.CryptoInfo cryptoInfo) throws RemoteException {
            throw new RemoteException("Not supported");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public boolean requiresSecureDecoderComponent(String str) throws RemoteException {
            throw new RemoteException("Not supported");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public void setMediaCasSession(byte[] bArr) throws RemoteException {
            throw new RemoteException("Not supported");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public void release() throws RemoteException {
            this.mAidlDescrambler.release();
        }
    }

    private class HidlDescrambler implements DescramblerWrapper {
        IDescramblerBase mHidlDescrambler;

        HidlDescrambler(IDescramblerBase iDescramblerBase) throws Exception {
            if (iDescramblerBase != null) {
                this.mHidlDescrambler = iDescramblerBase;
                MediaDescrambler.this.native_setup(iDescramblerBase.asBinder());
                return;
            }
            throw new Exception("Descrambler could not be created");
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public IHwBinder asBinder() {
            return this.mHidlDescrambler.asBinder();
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public int descramble(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, MediaCodec.CryptoInfo cryptoInfo) throws RemoteException {
            try {
                return MediaDescrambler.this.native_descramble(cryptoInfo.key[0], cryptoInfo.key[1], cryptoInfo.numSubSamples, cryptoInfo.numBytesOfClearData, cryptoInfo.numBytesOfEncryptedData, byteBuffer, byteBuffer.position(), byteBuffer.limit(), byteBuffer2, byteBuffer2.position(), byteBuffer2.limit());
            } catch (RemoteException unused) {
                MediaDescrambler.this.cleanupAndRethrowIllegalState();
                return -1;
            } catch (ServiceSpecificException e) {
                MediaCasStateException.throwExceptionIfNeeded(e.errorCode, e.getMessage());
                return -1;
            }
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public boolean requiresSecureDecoderComponent(String str) throws RemoteException {
            return this.mHidlDescrambler.requiresSecureDecoderComponent(str);
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public void setMediaCasSession(byte[] bArr) throws RemoteException {
            ArrayList<Byte> arrayList = new ArrayList<>();
            if (bArr != null) {
                ArrayList<Byte> arrayList2 = new ArrayList<>(bArr.length);
                for (byte b : bArr) {
                    arrayList2.add(Byte.valueOf(b));
                }
                arrayList = arrayList2;
            }
            MediaCasStateException.throwExceptionIfNeeded(this.mHidlDescrambler.setMediaCasSession(arrayList));
        }

        @Override // android.media.MediaDescrambler.DescramblerWrapper
        public void release() throws RemoteException {
            this.mHidlDescrambler.release();
            MediaDescrambler.this.native_release();
        }
    }

    private final void validateInternalStates() {
        if (this.mIDescrambler == null) {
            throw new IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cleanupAndRethrowIllegalState() {
        this.mIDescrambler = null;
        throw new IllegalStateException();
    }

    public MediaDescrambler(int i) throws MediaCasException.UnsupportedCasException {
        try {
            try {
                if (MediaCas.getService() != null) {
                    this.mIDescrambler = new AidlDescrambler(this, MediaCas.getService().createDescrambler(i));
                    this.mIsAidlHal = true;
                } else {
                    if (MediaCas.getServiceHidl() == null) {
                        throw new Exception("No CAS service found!");
                    }
                    this.mIDescrambler = new HidlDescrambler(MediaCas.getServiceHidl().createDescrambler(i));
                    this.mIsAidlHal = false;
                }
                if (this.mIDescrambler != null) {
                    return;
                }
                throw new MediaCasException.UnsupportedCasException("Unsupported CA_system_id " + i);
            } catch (Exception e) {
                Log.e(TAG, "Failed to create descrambler: " + e);
                this.mIDescrambler = null;
                throw new MediaCasException.UnsupportedCasException("Unsupported CA_system_id " + i);
            }
        } catch (Throwable th) {
            if (this.mIDescrambler != null) {
                throw th;
            }
            throw new MediaCasException.UnsupportedCasException("Unsupported CA_system_id " + i);
        }
    }

    public boolean isAidlHal() {
        return this.mIsAidlHal;
    }

    IHwBinder getBinder() {
        validateInternalStates();
        return this.mIDescrambler.asBinder();
    }

    public final boolean requiresSecureDecoderComponent(String str) {
        validateInternalStates();
        try {
            return this.mIDescrambler.requiresSecureDecoderComponent(str);
        } catch (RemoteException unused) {
            this.cleanupAndRethrowIllegalState();
            return true;
        }
    }

    public final void setMediaCasSession(MediaCas.Session session) {
        validateInternalStates();
        try {
            this.mIDescrambler.setMediaCasSession(session.mSessionId);
        } catch (RemoteException unused) {
            cleanupAndRethrowIllegalState();
        }
    }

    public final int descramble(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, MediaCodec.CryptoInfo cryptoInfo) {
        validateInternalStates();
        if (cryptoInfo.numSubSamples <= 0) {
            throw new IllegalArgumentException("Invalid CryptoInfo: invalid numSubSamples=" + cryptoInfo.numSubSamples);
        }
        if (cryptoInfo.numBytesOfClearData == null && cryptoInfo.numBytesOfEncryptedData == null) {
            throw new IllegalArgumentException("Invalid CryptoInfo: clearData and encryptedData size arrays are both null!");
        }
        if (cryptoInfo.numBytesOfClearData != null && cryptoInfo.numBytesOfClearData.length < cryptoInfo.numSubSamples) {
            throw new IllegalArgumentException("Invalid CryptoInfo: numBytesOfClearData is too small!");
        }
        if (cryptoInfo.numBytesOfEncryptedData != null && cryptoInfo.numBytesOfEncryptedData.length < cryptoInfo.numSubSamples) {
            throw new IllegalArgumentException("Invalid CryptoInfo: numBytesOfEncryptedData is too small!");
        }
        if (cryptoInfo.key == null || cryptoInfo.key.length != 16) {
            throw new IllegalArgumentException("Invalid CryptoInfo: key array is invalid!");
        }
        try {
            return this.mIDescrambler.descramble(byteBuffer, byteBuffer2, cryptoInfo);
        } catch (RemoteException unused) {
            this.cleanupAndRethrowIllegalState();
            return -1;
        } catch (ServiceSpecificException e) {
            MediaCasStateException.throwExceptionIfNeeded(e.errorCode, e.getMessage());
            return -1;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        DescramblerWrapper descramblerWrapper = this.mIDescrambler;
        if (descramblerWrapper != null) {
            try {
                descramblerWrapper.release();
            } catch (RemoteException unused) {
            } finally {
                this.mIDescrambler = null;
            }
        }
    }

    protected void finalize() {
        close();
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }
}
