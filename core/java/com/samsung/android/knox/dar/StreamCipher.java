package com.samsung.android.knox.dar;

import android.p009os.SystemProperties;
import android.util.Log;
import com.android.internal.widget.LockscreenCredential;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class StreamCipher {
    private static final int DEFAULT_KEY_LEN = 64;
    public static final long DEFAULT_KS_HANDLE = 0;
    private static final char[] HDR_CHARS;
    private static final int HDR_LEN;
    private static final int MAX_RETRY_CNT = 100;
    private static final String TAG = "StreamCipher.SDP";
    private static StreamCipher sInstance;
    private static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    private static final byte[] EMPTY_BYTES = new byte[0];
    private long mPublicHandle = 0;
    private final SecureRandom mSecureRandom = new SecureRandom();
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
                Log.m94d(TAG, "init :: Key map has been initialized");
            }
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
            long handle = this.mSecureRandom.nextLong();
            if (handle == 0 || !registerKeyStream(handle, new KeyStream(generateKey(length)))) {
                i++;
            } else {
                ret = handle;
                break;
            }
        }
        if (DEBUG) {
            Log.m94d(TAG, "issue :: handle = " + ret);
        }
        return ret;
    }

    public void clearKeyStream() {
        synchronized (this.mKeyMap) {
            for (Long handle : this.mKeyMap.keySet()) {
                if (DEBUG) {
                    Log.m94d(TAG, "clear :: handle = " + handle.longValue());
                }
                if (handle.longValue() != 0) {
                    KeyStream keyStream = this.mKeyMap.get(handle);
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
                    Log.m94d(TAG, "cipher :: Key stream not found... critical!");
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
            this.mSecureRandom.nextBytes(key);
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
                Log.m94d(TAG, "Key found with handle " + handle);
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
                Log.m94d(TAG, "register :: handle = " + handle.longValue());
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
            Log.m94d(TAG, "encryptStream - Invalid parameters");
            return null;
        }
        int strLen = stream.length;
        int i = HDR_LEN;
        int resLen = (strLen + i) * 2;
        int offset = i + strLen;
        byte[] res = new byte[resLen];
        new SecureRandom().nextBytes(res);
        fillHeader(res, offset);
        for (int i2 = 0; i2 < strLen; i2++) {
            int i3 = HDR_LEN;
            res[offset + i2 + i3] = (byte) (res[i3 + i2] ^ stream[i2]);
        }
        return res;
    }

    public static byte[] decryptStream(byte[] stream) {
        if (stream != null) {
            int length = stream.length;
            int i = HDR_LEN;
            if (length >= i * 2) {
                int len = stream.length;
                int offset = len / 2;
                int resLen = offset - i;
                byte[] res = null;
                if (!checkHeader(stream, offset)) {
                    Log.m96e(TAG, "Failed to decrypt stream due to invalid header");
                } else {
                    res = new byte[resLen];
                    for (int i2 = 0; i2 < resLen; i2++) {
                        int i3 = HDR_LEN;
                        res[i2] = (byte) (stream[(offset + i2) + i3] ^ stream[i2 + i3]);
                    }
                }
                return res;
            }
        }
        Log.m94d(TAG, "decryptStream - Invalid parameters");
        return null;
    }

    public static LockscreenCredential encryptStream(LockscreenCredential credential) {
        if (credential.isNone() || credential.size() == 0) {
            Log.m94d(TAG, "encryptStream is none or size zero. return duplicate.");
            return credential.duplicate();
        }
        int strLen = credential.size();
        int i = HDR_LEN;
        int resLen = (strLen + i) * 2;
        int offset = i + strLen;
        byte[] res = new byte[resLen];
        new SecureRandom().nextBytes(res);
        fillHeader(res, offset);
        byte[] stream = credential.getCredential();
        for (int i2 = 0; i2 < strLen; i2++) {
            int i3 = HDR_LEN;
            res[offset + i2 + i3] = (byte) (res[i3 + i2] ^ stream[i2]);
        }
        Log.m94d(TAG, "encryptStream type:" + credential.getType());
        return getStreamCredential(credential, res);
    }

    private static LockscreenCredential getStreamCredential(LockscreenCredential credential, byte[] res) {
        return LockscreenCredential.streamCredential(credential.getType(), res);
    }

    public static LockscreenCredential decryptStream(LockscreenCredential credential) {
        if (!credential.isNone()) {
            int size = credential.size();
            int i = HDR_LEN;
            if (size >= i * 2) {
                int len = credential.size();
                int offset = len / 2;
                int resLen = offset - i;
                byte[] stream = credential.getCredential();
                if (!checkHeader(stream, offset)) {
                    Log.m96e(TAG, "Failed to decrypt stream due to invalid header. return duplicate.");
                    return credential.duplicate();
                }
                byte[] res = new byte[resLen];
                for (int i2 = 0; i2 < resLen; i2++) {
                    int i3 = HDR_LEN;
                    res[i2] = (byte) (stream[(offset + i2) + i3] ^ stream[i2 + i3]);
                }
                Log.m94d(TAG, "decryptStream type:" + credential.getType());
                try {
                    return getStreamCredential(credential, res);
                } finally {
                    clear(res);
                }
            }
        }
        Log.m94d(TAG, "decryptStream is none or size zero. return duplicate.");
        return credential.duplicate();
    }
}
