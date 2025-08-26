package android.graphics.rendererpolicy;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Slog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes.dex */
public class GraphicsRendererPolicyCipher {
    private static final String AES_CBC_PKCS_7_PADDING = "AES/CBC/PKCS7Padding";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final int BUFFER_SIZE = 8192;
    private static final int KEY_SIZE = 256;
    private static final String SCSPCIPHER_FORMAT = "%s_scspcipher_%s";
    private static final String TAG = "GraphicsRendererPolicyCipher";
    private final String appId;
    private final Context context;

    public static GraphicsRendererPolicyCipher create(Context context, String str) {
        return new GraphicsRendererPolicyCipher(context, str);
    }

    public GraphicsRendererPolicyCipher(Context context, String str) {
        this.context = context;
        this.appId = str;
    }

    public boolean encrypt(InputStream inputStream, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                boolean zEncrypt = encrypt(inputStream, fileOutputStream);
                fileOutputStream.close();
                return zEncrypt;
            } finally {
            }
        } catch (Throwable th) {
            Slog.e(TAG, "encrypt", th);
            return false;
        }
    }

    public boolean decrypt(File file, OutputStream outputStream) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                boolean zDecrypt = decrypt(fileInputStream, outputStream);
                fileInputStream.close();
                return zDecrypt;
            } finally {
            }
        } catch (Exception e) {
            Slog.e(TAG, "decrypt", e);
            return false;
        }
    }

    public boolean encrypt(InputStream inputStream, OutputStream outputStream) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_7_PADDING);
            cipher.init(1, KeyStoreHolder.getKey(this.context, this.appId));
            byte[] iv = cipher.getIV();
            outputStream.write(iv.length);
            outputStream.write(iv);
            byte[] bArr = new byte[8192];
            while (true) {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    byte[] bArrUpdate = cipher.update(bArr, 0, i);
                    if (bArrUpdate != null) {
                        outputStream.write(bArrUpdate);
                    }
                } else {
                    outputStream.write(cipher.doFinal());
                    return true;
                }
            }
        } catch (Throwable th) {
            Slog.e(TAG, "encrypt ////", th);
            return false;
        }
    }

    public boolean decrypt(InputStream inputStream, OutputStream outputStream) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_7_PADDING);
            byte[] bArr = new byte[8192];
            int i = inputStream.read();
            byte[] bArr2 = new byte[i];
            inputStream.read(bArr2, 0, i);
            cipher.init(2, KeyStoreHolder.getKey(this.context, this.appId), new IvParameterSpec(bArr2));
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 != -1) {
                    byte[] bArrUpdate = cipher.update(bArr, 0, i2);
                    if (bArrUpdate != null) {
                        outputStream.write(bArrUpdate);
                    } else {
                        Slog.e(TAG, "updateResult result is null");
                    }
                } else {
                    outputStream.write(cipher.doFinal());
                    return true;
                }
            }
        } catch (Throwable th) {
            Slog.e(TAG, "decrypt", th);
            return false;
        }
    }

    public void clear() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        KeyStoreHolder.clear(this.context, this.appId);
    }

    private static class KeyStoreHolder {
        private KeyStoreHolder() {
        }

        private static SecretKey generateKey(String str) throws Exception {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            keyGenerator.init(new KeyGenParameterSpec.Builder(str, 3).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).setDigests("SHA-256").setUserAuthenticationRequired(false).setKeySize(256).build());
            return keyGenerator.generateKey();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static SecretKey getKey(Context context, String str) throws Exception {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            String str2 = String.format(GraphicsRendererPolicyCipher.SCSPCIPHER_FORMAT, context.getPackageName(), str);
            SecretKey secretKey = (SecretKey) keyStore.getKey(str2, null);
            return secretKey == null ? generateKey(str2) : secretKey;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void clear(Context context, String str) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
            try {
                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);
                keyStore.deleteEntry(String.format(GraphicsRendererPolicyCipher.SCSPCIPHER_FORMAT, context.getPackageName(), str));
            } catch (Exception e) {
                Slog.e(GraphicsRendererPolicyCipher.TAG, "clear", e);
            }
        }
    }
}
