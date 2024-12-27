package android.graphics.rendererpolicy;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Slog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class GraphicsRendererPolicyCipher {
    private static final String AES_CBC_PKCS_7_PADDING = "AES/CBC/PKCS7Padding";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final int BUFFER_SIZE = 8192;
    private static final int KEY_SIZE = 256;
    private static final String SCSPCIPHER_FORMAT = "%s_scspcipher_%s";
    private static final String TAG = GraphicsRendererPolicyCipher.class.getSimpleName();
    private final String appId;
    private final Context context;

    public static GraphicsRendererPolicyCipher create(Context context, String appId) {
        return new GraphicsRendererPolicyCipher(context, appId);
    }

    public GraphicsRendererPolicyCipher(Context context, String appId) {
        this.context = context;
        this.appId = appId;
    }

    public boolean encrypt(InputStream inputStream, File outFile) {
        try {
            FileOutputStream outputStream = new FileOutputStream(outFile);
            try {
                boolean encrypt = encrypt(inputStream, outputStream);
                outputStream.close();
                return encrypt;
            } finally {
            }
        } catch (Throwable e) {
            Slog.e(TAG, "encrypt", e);
            return false;
        }
    }

    public boolean decrypt(File encryptedFile, OutputStream outputStream) {
        try {
            FileInputStream inputStream = new FileInputStream(encryptedFile);
            try {
                boolean decrypt = decrypt(inputStream, outputStream);
                inputStream.close();
                return decrypt;
            } finally {
            }
        } catch (Exception e) {
            Slog.e(TAG, "decrypt", e);
            return false;
        }
    }

    public boolean encrypt(InputStream plainInputStream, OutputStream encryptedOutputStream) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_7_PADDING);
            cipher.init(1, KeyStoreHolder.getKey(this.context, this.appId));
            byte[] iv = cipher.getIV();
            encryptedOutputStream.write(iv.length);
            encryptedOutputStream.write(iv);
            byte[] buffer = new byte[8192];
            while (true) {
                int len = plainInputStream.read(buffer);
                if (len != -1) {
                    byte[] updateBytes = cipher.update(buffer, 0, len);
                    if (updateBytes != null) {
                        encryptedOutputStream.write(updateBytes);
                    }
                } else {
                    encryptedOutputStream.write(cipher.doFinal());
                    return true;
                }
            }
        } catch (Throwable e) {
            Slog.e(TAG, "encrypt ////", e);
            return false;
        }
    }

    public boolean decrypt(InputStream encryptedInputStream, OutputStream decryptedOutputStream) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_7_PADDING);
            byte[] buffer = new byte[8192];
            int ivLength = encryptedInputStream.read();
            byte[] iv = new byte[ivLength];
            encryptedInputStream.read(iv, 0, ivLength);
            cipher.init(
                    2, KeyStoreHolder.getKey(this.context, this.appId), new IvParameterSpec(iv));
            while (true) {
                int len = encryptedInputStream.read(buffer);
                if (len != -1) {
                    byte[] updateResult = cipher.update(buffer, 0, len);
                    if (updateResult != null) {
                        decryptedOutputStream.write(updateResult);
                    } else {
                        Slog.e(TAG, "updateResult result is null");
                    }
                } else {
                    decryptedOutputStream.write(cipher.doFinal());
                    return true;
                }
            }
        } catch (Throwable e) {
            Slog.e(TAG, "decrypt", e);
            return false;
        }
    }

    public void clear() {
        KeyStoreHolder.clear(this.context, this.appId);
    }

    private static class KeyStoreHolder {
        private KeyStoreHolder() {}

        private static SecretKey generateKey(String alias) throws Exception {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            KeyGenParameterSpec keyGenParameterSpec =
                    new KeyGenParameterSpec.Builder(alias, 3)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                            .setDigests("SHA-256")
                            .setUserAuthenticationRequired(false)
                            .setKeySize(256)
                            .build();
            keyGenerator.init(keyGenParameterSpec);
            return keyGenerator.generateKey();
        }

        public static SecretKey getKey(Context context, String appId) throws Exception {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            String alias =
                    String.format(
                            GraphicsRendererPolicyCipher.SCSPCIPHER_FORMAT,
                            context.getPackageName(),
                            appId);
            SecretKey key = (SecretKey) keyStore.getKey(alias, null);
            if (key == null) {
                return generateKey(alias);
            }
            return key;
        }

        public static void clear(Context context, String appId) {
            try {
                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);
                String alias =
                        String.format(
                                GraphicsRendererPolicyCipher.SCSPCIPHER_FORMAT,
                                context.getPackageName(),
                                appId);
                keyStore.deleteEntry(alias);
            } catch (Exception e) {
                Slog.e(GraphicsRendererPolicyCipher.TAG, "clear", e);
            }
        }
    }
}
