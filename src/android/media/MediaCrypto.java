package android.media;

import java.util.UUID;

/* loaded from: classes2.dex */
public final class MediaCrypto {
    private long mNativeContext;

    private static final native boolean isCryptoSchemeSupportedNative(byte[] bArr);

    private final native void native_finalize();

    private static final native void native_init();

    private final native void native_setup(byte[] bArr, byte[] bArr2) throws MediaCryptoException;

    public final native void release();

    public final native boolean requiresSecureDecoderComponent(String str);

    public final native void setMediaDrmSession(byte[] bArr) throws MediaCryptoException;

    public static final boolean isCryptoSchemeSupported(UUID uuid) {
        return isCryptoSchemeSupportedNative(getByteArrayFromUUID(uuid));
    }

    private static final byte[] getByteArrayFromUUID(UUID uuid) {
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        byte[] bArr = new byte[16];
        for (int i = 0; i < 8; i++) {
            int i2 = (7 - i) * 8;
            bArr[i] = (byte) (mostSignificantBits >>> i2);
            bArr[i + 8] = (byte) (leastSignificantBits >>> i2);
        }
        return bArr;
    }

    public MediaCrypto(UUID uuid, byte[] bArr) throws MediaCryptoException {
        native_setup(getByteArrayFromUUID(uuid), bArr);
    }

    protected void finalize() {
        native_finalize();
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }
}
