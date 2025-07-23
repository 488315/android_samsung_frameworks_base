package android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* loaded from: classes4.dex */
public class HashedStringCache {
    private static final long DAYS_TO_MILLIS = 86400000;
    private static final boolean DEBUG = false;
    private static final int HASH_CACHE_SIZE = 100;
    private static final int HASH_LENGTH = 8;
    static final String HASH_SALT = "_hash_salt";
    static final String HASH_SALT_DATE = "_hash_salt_date";
    static final String HASH_SALT_GEN = "_hash_salt_gen";
    private static final int MAX_SALT_DAYS = 100;
    private static final String TAG = "HashedStringCache";
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static HashedStringCache sHashedStringCache;
    private final MessageDigest mDigester;
    private byte[] mSalt;
    private int mSaltGen;
    private SharedPreferences mSharedPreferences;
    private final Object mPreferenceLock = new Object();
    private final LruCache<String, String> mHashes = new LruCache<>(100);
    private final SecureRandom mSecureRandom = new SecureRandom();

    private HashedStringCache() {
        try {
            this.mDigester = MessageDigest.getInstance(KeyProperties.DIGEST_MD5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashedStringCache getInstance() {
        if (sHashedStringCache == null) {
            sHashedStringCache = new HashedStringCache();
        }
        return sHashedStringCache;
    }

    public HashResult hashString(Context context, String str, String str2, int i) {
        if (i == -1 || context == null || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return null;
        }
        populateSaltValues(context, str, i);
        String str3 = this.mHashes.get(str2);
        if (str3 != null) {
            return new HashResult(this, str3, this.mSaltGen);
        }
        this.mDigester.reset();
        this.mDigester.update(this.mSalt);
        this.mDigester.update(str2.getBytes(UTF_8));
        byte[] digest = this.mDigester.digest();
        String encodeToString = Base64.encodeToString(digest, 0, Math.min(8, digest.length), 3);
        this.mHashes.put(str2, encodeToString);
        return new HashResult(this, encodeToString, this.mSaltGen);
    }

    private boolean checkNeedsNewSalt(String str, int i, long j) {
        if (j != 0 && i >= -1) {
            if (i > 100) {
                i = 100;
            }
            long currentTimeMillis = System.currentTimeMillis() - j;
            if (currentTimeMillis < i * 86400000 && currentTimeMillis >= 0) {
                return false;
            }
        }
        return true;
    }

    private void populateSaltValues(Context context, String str, int i) {
        synchronized (this.mPreferenceLock) {
            SharedPreferences hashSharedPreferences = getHashSharedPreferences(context);
            this.mSharedPreferences = hashSharedPreferences;
            boolean checkNeedsNewSalt = checkNeedsNewSalt(str, i, hashSharedPreferences.getLong(str + HASH_SALT_DATE, 0L));
            if (checkNeedsNewSalt) {
                this.mHashes.evictAll();
            }
            if (this.mSalt == null || checkNeedsNewSalt) {
                String string = this.mSharedPreferences.getString(str + HASH_SALT, null);
                int i2 = this.mSharedPreferences.getInt(str + HASH_SALT_GEN, 0);
                this.mSaltGen = i2;
                if (string == null || checkNeedsNewSalt) {
                    this.mSaltGen = i2 + 1;
                    byte[] bArr = new byte[16];
                    this.mSecureRandom.nextBytes(bArr);
                    string = Base64.encodeToString(bArr, 3);
                    this.mSharedPreferences.edit().putString(str + HASH_SALT, string).putInt(str + HASH_SALT_GEN, this.mSaltGen).putLong(str + HASH_SALT_DATE, System.currentTimeMillis()).apply();
                }
                this.mSalt = string.getBytes(UTF_8);
            }
        }
    }

    private SharedPreferences getHashSharedPreferences(Context context) {
        return context.getSharedPreferences(new File(new File(Environment.getDataUserCePackageDirectory(StorageManager.UUID_PRIVATE_INTERNAL, context.getUserId(), context.getPackageName()), "shared_prefs"), "hashed_cache.xml"), 0);
    }

    public class HashResult {
        public String hashedString;
        public int saltGeneration;

        public HashResult(HashedStringCache hashedStringCache, String str, int i) {
            this.hashedString = str;
            this.saltGeneration = i;
        }
    }
}
