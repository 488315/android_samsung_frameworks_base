package com.samsung.android.knox.dar;

import android.util.Log;
import com.android.internal.widget.LockscreenCredential;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class StreamCipher {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_KEY_LEN = 64;
    public static final long DEFAULT_KS_HANDLE = 0;
    private static final char[] HDR_CHARS;
    private static final int HDR_LEN;
    private static final int MAX_RETRY_CNT = 100;
    private static final String TAG = "StreamCipher.SDP";
    private static StreamCipher sInstance;
    private static final SecureRandom sSecureRandom = new SecureRandom();
    private static final byte[] EMPTY_BYTES = new byte[0];
    private long mPublicHandle = 0;
    private final Map<Long, KeyStream> mKeyMap = new HashMap();

    static {
        char[] cArr = {221, 222};
        HDR_CHARS = cArr;
        HDR_LEN = cArr.length;
    }

    private StreamCipher() {
        initKeyMap();
    }

    public static synchronized StreamCipher getInstance() {
        if (sInstance == null) {
            sInstance = new StreamCipher();
        }
        return sInstance;
    }

    private void initKeyMap() {
        synchronized (this.mKeyMap) {
            this.mKeyMap.clear();
            byte[] bArr = new byte[64];
            Arrays.fill(bArr, 0, 64, (byte) 0);
            registerKeyStream(0L, new KeyStream(bArr));
        }
    }

    public long getPublicHandle() {
        return issueKeyStream();
    }

    public long issueKeyStream() {
        synchronized (this.mKeyMap) {
            long j = this.mPublicHandle;
            if (j == 0 || !this.mKeyMap.containsKey(Long.valueOf(j))) {
                this.mPublicHandle = issueKeyStream(64);
            }
        }
        return this.mPublicHandle;
    }

    public long issueKeyStream(int i) {
        if (i > 0) {
            for (int i2 = 0; i2 < 100; i2++) {
                long jNextLong = sSecureRandom.nextLong();
                if (jNextLong != 0 && registerKeyStream(jNextLong, new KeyStream(generateKey(i)))) {
                    return jNextLong;
                }
            }
        }
        return 0L;
    }

    public void clearKeyStream() {
        synchronized (this.mKeyMap) {
            for (Map.Entry<Long, KeyStream> entry : this.mKeyMap.entrySet()) {
                Long key = entry.getKey();
                KeyStream value = entry.getValue();
                if (key.longValue() != 0 && value != null) {
                    value.destroy();
                }
            }
            initKeyMap();
        }
    }

    public byte[] streamCipher(byte[] bArr, long j) {
        byte[] bArrStreamCipher;
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return EMPTY_BYTES;
        }
        synchronized (this.mKeyMap) {
            KeyStream keyStreamLocked = getKeyStreamLocked(j);
            if (keyStreamLocked == null) {
                keyStreamLocked = new KeyStream(generateKey(bArr.length));
                registerKeyStream(j, keyStreamLocked);
            }
            bArrStreamCipher = streamCipher(bArr, keyStreamLocked.getKey());
        }
        return bArrStreamCipher;
    }

    private byte[] streamCipher(byte[] bArr, byte[] bArr2) throws IllegalArgumentException {
        if (bArr == null || bArr.length == 0 || bArr2 == null || bArr2.length == 0) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        byte[] bArr3 = new byte[bArr.length];
        int i = 0;
        if (bArr.length > bArr2.length) {
            int length = 0;
            while (i < bArr.length) {
                bArr3[i] = (byte) (bArr2[length] ^ bArr[i]);
                i++;
                length = i % bArr2.length;
            }
        } else {
            while (i < bArr.length) {
                bArr3[i] = (byte) (bArr[i] ^ bArr2[i]);
                i++;
            }
        }
        return bArr3;
    }

    private byte[] generateKey(int i) {
        if (i > 0) {
            byte[] bArr = new byte[i];
            sSecureRandom.nextBytes(bArr);
            return bArr;
        }
        byte[] bArr2 = new byte[64];
        Arrays.fill(bArr2, 0, 64, (byte) 0);
        return bArr2;
    }

    public byte[] getKey(long j) {
        byte[] key;
        synchronized (this.mKeyMap) {
            KeyStream keyStreamLocked = getKeyStreamLocked(j);
            if (keyStreamLocked != null) {
                Log.d(TAG, "Key found with handle " + j);
                key = keyStreamLocked.getKey();
            } else {
                key = null;
            }
        }
        return key;
    }

    private KeyStream getKeyStreamLocked(long j) {
        return this.mKeyMap.get(Long.valueOf(j));
    }

    private boolean registerKeyStream(long j, KeyStream keyStream) {
        return registerKeyStream(Long.valueOf(j), keyStream);
    }

    private boolean registerKeyStream(Long l, KeyStream keyStream) {
        synchronized (this.mKeyMap) {
            if (this.mKeyMap.containsKey(l)) {
                return false;
            }
            this.mKeyMap.put(l, keyStream);
            return true;
        }
    }

    private static class KeyStream {
        private byte[] mKey;

        KeyStream(byte[] bArr) {
            this.mKey = bArr;
        }

        byte[] getKey() {
            return this.mKey;
        }

        void destroy() {
            StreamCipher.clear(this.mKey);
        }
    }

    public static void clear(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        Arrays.fill(bArr, 0, bArr.length, (byte) 0);
    }

    public byte[] getCipher(byte[] bArr, long j) {
        if (bArr == null) {
            return null;
        }
        return streamCipher(bArr, j);
    }

    public byte[] restoreCipher(byte[] bArr, long j) {
        if (bArr == null) {
            return null;
        }
        return streamCipher(bArr, j);
    }

    private static void fillHeader(byte[] bArr, int i) {
        for (int i2 = 0; i2 < HDR_LEN; i2++) {
            bArr[i + i2] = (byte) (bArr[i2] ^ ((byte) HDR_CHARS[i2]));
        }
    }

    private static boolean checkHeader(byte[] bArr, int i) {
        for (int i2 = 0; i2 < HDR_LEN; i2++) {
            if ((bArr[i2] ^ bArr[i + i2]) != ((byte) HDR_CHARS[i2])) {
                return false;
            }
        }
        return true;
    }

    public static byte[] encryptStream(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            Log.d(TAG, "encryptStream - Invalid parameters");
            return null;
        }
        int length = bArr.length;
        int i = HDR_LEN;
        int i2 = (length + i) * 2;
        int i3 = i + length;
        byte[] bArr2 = new byte[i2];
        sSecureRandom.nextBytes(bArr2);
        fillHeader(bArr2, i3);
        for (int i4 = 0; i4 < length; i4++) {
            int i5 = HDR_LEN;
            bArr2[i3 + i4 + i5] = (byte) (bArr2[i5 + i4] ^ bArr[i4]);
        }
        return bArr2;
    }

    public static byte[] decryptStream(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length;
            int i = HDR_LEN;
            if (length >= i * 2) {
                int length2 = bArr.length / 2;
                int i2 = length2 - i;
                if (!checkHeader(bArr, length2)) {
                    Log.e(TAG, "Failed to decrypt stream due to invalid header");
                    return null;
                }
                byte[] bArr2 = new byte[i2];
                for (int i3 = 0; i3 < i2; i3++) {
                    int i4 = HDR_LEN;
                    bArr2[i3] = (byte) (bArr[(length2 + i3) + i4] ^ bArr[i3 + i4]);
                }
                return bArr2;
            }
        }
        Log.d(TAG, "decryptStream - Invalid parameters");
        return null;
    }

    public static LockscreenCredential encryptStream(LockscreenCredential lockscreenCredential) {
        if (lockscreenCredential.isNone() || lockscreenCredential.size() == 0) {
            Log.d(TAG, "encryptStream is none or size zero. return duplicate.");
            return lockscreenCredential.duplicate();
        }
        int size = lockscreenCredential.size();
        int i = HDR_LEN;
        int i2 = (size + i) * 2;
        int i3 = i + size;
        byte[] bArr = new byte[i2];
        sSecureRandom.nextBytes(bArr);
        fillHeader(bArr, i3);
        byte[] credential = lockscreenCredential.getCredential();
        for (int i4 = 0; i4 < size; i4++) {
            int i5 = HDR_LEN;
            bArr[i3 + i4 + i5] = (byte) (bArr[i5 + i4] ^ credential[i4]);
        }
        Log.d(TAG, "encryptStream type:" + lockscreenCredential.getType());
        return getStreamCredential(lockscreenCredential, bArr);
    }

    private static LockscreenCredential getStreamCredential(LockscreenCredential lockscreenCredential, byte[] bArr) {
        return LockscreenCredential.streamCredential(lockscreenCredential.getType(), bArr);
    }

    public static LockscreenCredential decryptStream(LockscreenCredential lockscreenCredential) {
        if (!lockscreenCredential.isNone()) {
            int size = lockscreenCredential.size();
            int i = HDR_LEN;
            if (size >= i * 2) {
                int size2 = lockscreenCredential.size() / 2;
                int i2 = size2 - i;
                byte[] credential = lockscreenCredential.getCredential();
                if (!checkHeader(credential, size2)) {
                    Log.e(TAG, "Failed to decrypt stream due to invalid header. return duplicate.");
                    return lockscreenCredential.duplicate();
                }
                byte[] bArr = new byte[i2];
                for (int i3 = 0; i3 < i2; i3++) {
                    int i4 = HDR_LEN;
                    bArr[i3] = (byte) (credential[(size2 + i3) + i4] ^ credential[i3 + i4]);
                }
                Log.d(TAG, "decryptStream type:" + lockscreenCredential.getType());
                try {
                    return getStreamCredential(lockscreenCredential, bArr);
                } finally {
                    clear(bArr);
                }
            }
        }
        Log.d(TAG, "decryptStream is none or size zero. return duplicate.");
        return lockscreenCredential.duplicate();
    }
}
