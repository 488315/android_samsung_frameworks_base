package com.samsung.android.knox.dar;

import android.os.SystemProperties;
import android.util.Log;

import com.android.internal.widget.LockscreenCredential;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class StreamCipher {
    private static final int DEFAULT_KEY_LEN = 64;
    public static final long DEFAULT_KS_HANDLE = 0;
    private static final int MAX_RETRY_CNT = 100;
    private static final String TAG = "StreamCipher.SDP";
    private static StreamCipher sInstance;
    private static final SecureRandom sSecureRandom = new SecureRandom();
    private static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final char[] HDR_CHARS = {221, 222};
    private static final int HDR_LEN = HDR_CHARS.length;
    private long mPublicHandle = 0;
    private final Map<Long, KeyStream> mKeyMap = new HashMap();

    private StreamCipher() {
        initKeyMap();
    }

    public static synchronized StreamCipher getInstance() {
        StreamCipher streamCipher;
        synchronized (StreamCipher.class) {
            if (sInstance == null) {
                sInstance = new StreamCipher();
            }
            streamCipher = sInstance;
        }
        return streamCipher;
    }

    private void initKeyMap() {
        synchronized (this.mKeyMap) {
            this.mKeyMap.clear();
            byte[] key = new byte[64];
            Arrays.fill(key, 0, key.length, (byte) 0);
            registerKeyStream(0L, new KeyStream(key));
            if (DEBUG) {
                Log.d(TAG, "init :: Key map has been initialized");
            }
        }
    }

    public long getPublicHandle() {
        return issueKeyStream();
    }

    public long issueKeyStream() {
        synchronized (this.mKeyMap) {
            if (this.mPublicHandle == 0
                    || !this.mKeyMap.containsKey(Long.valueOf(this.mPublicHandle))) {
                this.mPublicHandle = issueKeyStream(64);
            }
        }
        return this.mPublicHandle;
    }

    public long issueKeyStream(int length) {
        long ret = 0;
        if (length <= 0) {
            return 0L;
        }
        int i = 0;
        while (true) {
            if (i >= 100) {
                break;
            }
            long handle = sSecureRandom.nextLong();
            if (handle == 0 || !registerKeyStream(handle, new KeyStream(generateKey(length)))) {
                i++;
            } else {
                ret = handle;
                break;
            }
        }
        if (DEBUG) {
            Log.d(TAG, "issue :: handle = " + ret);
        }
        return ret;
    }

    public void clearKeyStream() {
        synchronized (this.mKeyMap) {
            for (Map.Entry<Long, KeyStream> entry : this.mKeyMap.entrySet()) {
                Long handle = entry.getKey();
                KeyStream keyStream = entry.getValue();
                if (DEBUG) {
                    Log.d(TAG, "clear :: handle = " + handle.longValue());
                }
                if (handle.longValue() != 0) {
                    if (keyStream != null) {
                        keyStream.destroy();
                    }
                }
            }
            initKeyMap();
        }
    }

    public byte[] streamCipher(byte[] stream, long handle) {
        byte[] ret;
        if (stream == null) {
            return null;
        }
        if (stream.length == 0) {
            return EMPTY_BYTES;
        }
        synchronized (this.mKeyMap) {
            KeyStream keyStream = getKeyStreamLocked(handle);
            if (keyStream == null) {
                if (DEBUG) {
                    Log.d(TAG, "cipher :: Key stream not found... critical!");
                }
                keyStream = new KeyStream(generateKey(stream.length));
                registerKeyStream(handle, keyStream);
            }
            byte[] key = keyStream.getKey();
            ret = streamCipher(stream, key);
        }
        return ret;
    }

    private byte[] streamCipher(byte[] stream, byte[] key) throws IllegalArgumentException {
        if (stream == null || stream.length == 0 || key == null || key.length == 0) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        byte[] res = new byte[stream.length];
        if (stream.length > key.length) {
            int i = 0;
            int kI = 0;
            while (i < stream.length) {
                res[i] = (byte) (stream[i] ^ key[kI]);
                i++;
                kI = i % key.length;
            }
        } else {
            for (int i2 = 0; i2 < stream.length; i2++) {
                res[i2] = (byte) (stream[i2] ^ key[i2]);
            }
        }
        return res;
    }

    private byte[] generateKey(int length) {
        if (length > 0) {
            byte[] key = new byte[length];
            sSecureRandom.nextBytes(key);
            return key;
        }
        byte[] key2 = new byte[64];
        Arrays.fill(key2, 0, key2.length, (byte) 0);
        return key2;
    }

    public byte[] getKey(long handle) {
        byte[] ret = null;
        synchronized (this.mKeyMap) {
            KeyStream keyStream = getKeyStreamLocked(handle);
            if (keyStream != null) {
                Log.d(TAG, "Key found with handle " + handle);
                ret = keyStream.getKey();
            }
        }
        return ret;
    }

    private KeyStream getKeyStreamLocked(long handle) {
        return this.mKeyMap.get(Long.valueOf(handle));
    }

    private boolean registerKeyStream(long handle, KeyStream ks) {
        return registerKeyStream(Long.valueOf(handle), ks);
    }

    private boolean registerKeyStream(Long handle, KeyStream ks) {
        synchronized (this.mKeyMap) {
            if (this.mKeyMap.containsKey(handle)) {
                return false;
            }
            this.mKeyMap.put(handle, ks);
            if (DEBUG) {
                Log.d(TAG, "register :: handle = " + handle.longValue());
                return true;
            }
            return true;
        }
    }

    private static class KeyStream {
        private byte[] mKey;

        KeyStream(byte[] key) {
            this.mKey = key;
        }

        byte[] getKey() {
            return this.mKey;
        }

        void destroy() {
            StreamCipher.clear(this.mKey);
        }
    }

    public static void clear(byte[] bytes) {
        if (bytes == null) {
            return;
        }
        Arrays.fill(bytes, 0, bytes.length, (byte) 0);
    }

    public byte[] getCipher(byte[] stream, long handle) {
        if (stream == null) {
            return null;
        }
        return streamCipher(stream, handle);
    }

    public byte[] restoreCipher(byte[] cipher, long handle) {
        if (cipher == null) {
            return null;
        }
        return streamCipher(cipher, handle);
    }

    private static void fillHeader(byte[] stream, int offset) {
        for (int i = 0; i < HDR_LEN; i++) {
            stream[offset + i] = (byte) (stream[i] ^ ((byte) HDR_CHARS[i]));
        }
    }

    private static boolean checkHeader(byte[] stream, int offset) {
        for (int i = 0; i < HDR_LEN; i++) {
            if ((stream[i] ^ stream[offset + i]) != ((byte) HDR_CHARS[i])) {
                return false;
            }
        }
        return true;
    }

    public static byte[] encryptStream(byte[] stream) {
        if (stream == null || stream.length == 0) {
            Log.d(TAG, "encryptStream - Invalid parameters");
            return null;
        }
        int strLen = stream.length;
        int resLen = (HDR_LEN + strLen) * 2;
        int offset = HDR_LEN + strLen;
        byte[] res = new byte[resLen];
        sSecureRandom.nextBytes(res);
        fillHeader(res, offset);
        for (int i = 0; i < strLen; i++) {
            res[offset + i + HDR_LEN] = (byte) (res[HDR_LEN + i] ^ stream[i]);
        }
        return res;
    }

    public static byte[] decryptStream(byte[] stream) {
        if (stream == null || stream.length < HDR_LEN * 2) {
            Log.d(TAG, "decryptStream - Invalid parameters");
            return null;
        }
        int len = stream.length;
        int offset = len / 2;
        int resLen = offset - HDR_LEN;
        byte[] res = null;
        if (!checkHeader(stream, offset)) {
            Log.e(TAG, "Failed to decrypt stream due to invalid header");
        } else {
            res = new byte[resLen];
            for (int i = 0; i < resLen; i++) {
                res[i] = (byte) (stream[HDR_LEN + i] ^ stream[(offset + i) + HDR_LEN]);
            }
        }
        return res;
    }

    public static LockscreenCredential encryptStream(LockscreenCredential credential) {
        if (credential.isNone() || credential.size() == 0) {
            Log.d(TAG, "encryptStream is none or size zero. return duplicate.");
            return credential.duplicate();
        }
        int strLen = credential.size();
        int resLen = (HDR_LEN + strLen) * 2;
        int offset = HDR_LEN + strLen;
        byte[] res = new byte[resLen];
        sSecureRandom.nextBytes(res);
        fillHeader(res, offset);
        byte[] stream = credential.getCredential();
        for (int i = 0; i < strLen; i++) {
            res[offset + i + HDR_LEN] = (byte) (res[HDR_LEN + i] ^ stream[i]);
        }
        Log.d(TAG, "encryptStream type:" + credential.getType());
        return getStreamCredential(credential, res);
    }

    private static LockscreenCredential getStreamCredential(
            LockscreenCredential credential, byte[] res) {
        return LockscreenCredential.streamCredential(credential.getType(), res);
    }

    public static LockscreenCredential decryptStream(LockscreenCredential credential) {
        if (credential.isNone() || credential.size() < HDR_LEN * 2) {
            Log.d(TAG, "decryptStream is none or size zero. return duplicate.");
            return credential.duplicate();
        }
        int len = credential.size();
        int offset = len / 2;
        int resLen = offset - HDR_LEN;
        byte[] stream = credential.getCredential();
        if (!checkHeader(stream, offset)) {
            Log.e(TAG, "Failed to decrypt stream due to invalid header. return duplicate.");
            return credential.duplicate();
        }
        byte[] res = new byte[resLen];
        for (int i = 0; i < resLen; i++) {
            res[i] = (byte) (stream[HDR_LEN + i] ^ stream[(offset + i) + HDR_LEN]);
        }
        Log.d(TAG, "decryptStream type:" + credential.getType());
        try {
            return getStreamCredential(credential, res);
        } finally {
            clear(res);
        }
    }
}
