package com.android.server.locksettings.recoverablekeystore;

import android.app.KeyguardManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.RemoteException;
import android.security.GateKeeper;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProtection;
import android.service.gatekeeper.IGateKeeperService;
import android.util.Log;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDbHelper;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PlatformKeyManager {
    public static final byte[] GCM_INSECURE_NONCE_BYTES = new byte[12];
    public final Context mContext;
    public final RecoverableKeyStoreDb mDatabase;
    public final KeyStoreProxy mKeyStore;

    public PlatformKeyManager(Context context, KeyStoreProxy keyStoreProxy, RecoverableKeyStoreDb recoverableKeyStoreDb) {
        this.mKeyStore = keyStoreProxy;
        this.mContext = context;
        this.mDatabase = recoverableKeyStoreDb;
    }

    public static void ensureDecryptionKeyIsValid(int i, PlatformDecryptionKey platformDecryptionKey) {
        try {
            Cipher.getInstance("AES/GCM/NoPadding").init(4, platformDecryptionKey.mKey, new GCMParameterSpec(128, GCM_INSECURE_NONCE_BYTES));
        } catch (KeyPermanentlyInvalidatedException e) {
            Locale locale = Locale.US;
            Log.e("PlatformKeyManager", "The platform key for user " + i + " became invalid.");
            throw new UnrecoverableKeyException(e.getMessage());
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException unused) {
        }
    }

    public static String getDecryptAlias(int i, int i2) {
        return DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "com.android.server.locksettings.recoverablekeystore/platform/", "/", "/decrypt");
    }

    public static String getEncryptAlias(int i, int i2) {
        return DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "com.android.server.locksettings.recoverablekeystore/platform/", "/", "/encrypt");
    }

    public static PlatformKeyManager getInstance(Context context, RecoverableKeyStoreDb recoverableKeyStoreDb) {
        Context applicationContext = context.getApplicationContext();
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        try {
            keyStore.load(null);
            return new PlatformKeyManager(applicationContext, new KeyStoreProxyImpl(keyStore), recoverableKeyStoreDb);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new KeyStoreException("Unable to load keystore.", e);
        }
    }

    public final void generateAndLoadKey(int i, int i2) {
        KeyStoreProxy keyStoreProxy = this.mKeyStore;
        String encryptAlias = getEncryptAlias(i, i2);
        String decryptAlias = getDecryptAlias(i, i2);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey generateKey = keyGenerator.generateKey();
        KeyProtection.Builder encryptionPaddings = new KeyProtection.Builder(2).setBlockModes("GCM").setEncryptionPaddings("NoPadding");
        if (i == 0) {
            encryptionPaddings.setUnlockedDeviceRequired(true);
        }
        try {
            ((KeyStoreProxyImpl) keyStoreProxy).mKeyStore.setEntry(decryptAlias, new KeyStore.SecretKeyEntry(generateKey), encryptionPaddings.build());
            ((KeyStoreProxyImpl) keyStoreProxy).mKeyStore.setEntry(encryptAlias, new KeyStore.SecretKeyEntry(generateKey), new KeyProtection.Builder(1).setBlockModes("GCM").setEncryptionPaddings("NoPadding").build());
            RecoverableKeyStoreDb recoverableKeyStoreDb = this.mDatabase;
            RecoverableKeyStoreDbHelper recoverableKeyStoreDbHelper = recoverableKeyStoreDb.mKeyStoreDbHelper;
            SQLiteDatabase writableDatabase = recoverableKeyStoreDbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(i, contentValues, "user_id", i2, "platform_key_generation_id");
            String[] strArr = {String.valueOf(i)};
            recoverableKeyStoreDb.ensureUserMetadataEntryExists(i);
            SQLiteDatabase writableDatabase2 = recoverableKeyStoreDbHelper.getWritableDatabase();
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("recovery_status", (Integer) 3);
            writableDatabase2.update("keys", contentValues2, "user_id = ?", new String[]{String.valueOf(i)});
            writableDatabase.update("user_metadata", contentValues, "user_id = ?", strArr);
        } catch (KeyStoreException e) {
            if (!((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(i)) {
                throw new InsecureUserException("Screenlock is not set");
            }
            throw e;
        }
    }

    public final PlatformDecryptionKey getDecryptKeyInternal(int i) {
        int platformKeyGenerationId = this.mDatabase.getPlatformKeyGenerationId(i);
        String decryptAlias = getDecryptAlias(i, platformKeyGenerationId);
        if (isKeyLoaded(i, platformKeyGenerationId)) {
            return new PlatformDecryptionKey(platformKeyGenerationId, (SecretKey) ((KeyStoreProxyImpl) this.mKeyStore).mKeyStore.getKey(decryptAlias, null));
        }
        throw new UnrecoverableKeyException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("KeyStore doesn't contain key ", decryptAlias));
    }

    public final PlatformEncryptionKey getEncryptKey(int i) {
        init(i);
        try {
            getDecryptKeyInternal(i);
            return getEncryptKeyInternal(i);
        } catch (UnrecoverableKeyException unused) {
            Locale locale = Locale.US;
            Log.i("PlatformKeyManager", "Regenerating permanently invalid Platform key for user " + i + ".");
            this.regenerate(i);
            return this.getEncryptKeyInternal(i);
        }
    }

    public final PlatformEncryptionKey getEncryptKeyInternal(int i) {
        int platformKeyGenerationId = this.mDatabase.getPlatformKeyGenerationId(i);
        String encryptAlias = getEncryptAlias(i, platformKeyGenerationId);
        if (isKeyLoaded(i, platformKeyGenerationId)) {
            return new PlatformEncryptionKey(platformKeyGenerationId, (SecretKey) ((KeyStoreProxyImpl) this.mKeyStore).mKeyStore.getKey(encryptAlias, null));
        }
        throw new UnrecoverableKeyException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("KeyStore doesn't contain key ", encryptAlias));
    }

    public IGateKeeperService getGateKeeperService() {
        return GateKeeper.getService();
    }

    public final void init(int i) {
        int platformKeyGenerationId = this.mDatabase.getPlatformKeyGenerationId(i);
        if (isKeyLoaded(i, platformKeyGenerationId)) {
            Locale locale = Locale.US;
            Log.i("PlatformKeyManager", "Platform key generation " + platformKeyGenerationId + " exists already.");
            return;
        }
        int i2 = 1;
        if (platformKeyGenerationId == -1) {
            Log.i("PlatformKeyManager", "Generating initial platform key generation ID.");
        } else {
            Locale locale2 = Locale.US;
            Log.w("PlatformKeyManager", "Platform generation ID was " + platformKeyGenerationId + " but no entry was present in AndroidKeyStore. Generating fresh key.");
            i2 = 1 + platformKeyGenerationId;
        }
        generateAndLoadKey(i, Math.max(i2, 1001000));
    }

    public final void invalidatePlatformKey(int i, int i2) {
        KeyStoreProxy keyStoreProxy = this.mKeyStore;
        if (i2 != -1) {
            try {
                ((KeyStoreProxyImpl) keyStoreProxy).mKeyStore.deleteEntry(getEncryptAlias(i, i2));
                ((KeyStoreProxyImpl) keyStoreProxy).mKeyStore.deleteEntry(getDecryptAlias(i, i2));
            } catch (KeyStoreException unused) {
            }
        }
    }

    public final boolean isKeyLoaded(int i, int i2) {
        String encryptAlias = getEncryptAlias(i, i2);
        KeyStoreProxyImpl keyStoreProxyImpl = (KeyStoreProxyImpl) this.mKeyStore;
        if (keyStoreProxyImpl.mKeyStore.containsAlias(encryptAlias)) {
            if (keyStoreProxyImpl.mKeyStore.containsAlias(getDecryptAlias(i, i2))) {
                return true;
            }
        }
        return false;
    }

    public void regenerate(int i) throws NoSuchAlgorithmException, KeyStoreException, IOException, RemoteException, InsecureUserException {
        int platformKeyGenerationId = this.mDatabase.getPlatformKeyGenerationId(i);
        int i2 = 1;
        if (platformKeyGenerationId != -1) {
            invalidatePlatformKey(i, platformKeyGenerationId);
            i2 = 1 + platformKeyGenerationId;
        }
        generateAndLoadKey(i, i2);
    }
}
