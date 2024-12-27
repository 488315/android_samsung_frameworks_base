package android.security;

import android.os.Environment;
import android.os.FileUtils;
import android.os.StrictMode;

import libcore.io.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SystemKeyStore {
    private static final String KEY_FILE_EXTENSION = ".sks";
    private static final String SYSTEM_KEYSTORE_DIRECTORY = "misc/systemkeys";
    private static SystemKeyStore mInstance = new SystemKeyStore();

    private SystemKeyStore() {}

    public static SystemKeyStore getInstance() {
        return mInstance;
    }

    public static String toHexString(byte[] keyData) {
        if (keyData == null) {
            return null;
        }
        int length = keyData.length;
        int expectedStringLen = keyData.length * 2;
        StringBuilder sb = new StringBuilder(expectedStringLen);
        for (byte b : keyData) {
            String hexStr = Integer.toString(b & 255, 16);
            if (hexStr.length() == 1) {
                hexStr = "0" + hexStr;
            }
            sb.append(hexStr);
        }
        return sb.toString();
    }

    public String generateNewKeyHexString(int numBits, String algName, String keyName)
            throws NoSuchAlgorithmException {
        return toHexString(generateNewKey(numBits, algName, keyName));
    }

    public byte[] generateNewKey(int numBits, String algName, String keyName)
            throws NoSuchAlgorithmException {
        StrictMode.noteDiskWrite();
        File keyFile = getKeyFile(keyName);
        if (keyFile.exists()) {
            throw new IllegalArgumentException();
        }
        KeyGenerator skg = KeyGenerator.getInstance(algName);
        SecureRandom srng = SecureRandom.getInstance("SHA1PRNG");
        skg.init(numBits, srng);
        SecretKey sk = skg.generateKey();
        byte[] retKey = sk.getEncoded();
        try {
            if (!keyFile.createNewFile()) {
                throw new IllegalArgumentException();
            }
            FileOutputStream fos = new FileOutputStream(keyFile);
            fos.write(retKey);
            fos.flush();
            FileUtils.sync(fos);
            fos.close();
            FileUtils.setPermissions(keyFile.getName(), 384, -1, -1);
            return retKey;
        } catch (IOException e) {
            return null;
        }
    }

    private File getKeyFile(String keyName) {
        StrictMode.noteDiskWrite();
        File sysKeystoreDir = new File(Environment.getDataDirectory(), SYSTEM_KEYSTORE_DIRECTORY);
        File keyFile = new File(sysKeystoreDir, keyName + KEY_FILE_EXTENSION);
        return keyFile;
    }

    public String retrieveKeyHexString(String keyName) throws IOException {
        return toHexString(retrieveKey(keyName));
    }

    public byte[] retrieveKey(String keyName) throws IOException {
        StrictMode.noteDiskRead();
        File keyFile = getKeyFile(keyName);
        if (!keyFile.exists()) {
            return null;
        }
        return IoUtils.readFileAsByteArray(keyFile.toString());
    }

    public void deleteKey(String keyName) {
        StrictMode.noteDiskWrite();
        File keyFile = getKeyFile(keyName);
        if (!keyFile.exists()) {
            throw new IllegalArgumentException();
        }
        keyFile.delete();
    }
}
