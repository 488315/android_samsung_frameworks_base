package com.android.server.accounts;

import android.os.Bundle;
import android.os.Parcel;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

public final class CryptoHelper {
    public static CryptoHelper sInstance;
    public final SecretKey mEncryptionKey = KeyGenerator.getInstance("AES").generateKey();
    public final SecretKey mMacKey = KeyGenerator.getInstance("HMACSHA256").generateKey();

    public static synchronized CryptoHelper getInstance() {
        CryptoHelper cryptoHelper;
        synchronized (CryptoHelper.class) {
            try {
                if (sInstance == null) {
                    sInstance = new CryptoHelper();
                }
                cryptoHelper = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return cryptoHelper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0041, code lost:

       if (r12 == r5) goto L30;
    */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle decryptBundle(android.os.Bundle r12) {
        /*
            r11 = this;
            java.lang.String r0 = "Cannot decrypt null bundle."
            java.util.Objects.requireNonNull(r12, r0)
            java.lang.String r0 = "iv"
            byte[] r0 = r12.getByteArray(r0)
            java.lang.String r1 = "cipher"
            byte[] r1 = r12.getByteArray(r1)
            java.lang.String r2 = "mac"
            byte[] r12 = r12.getByteArray(r2)
            java.lang.String r2 = "Account"
            r3 = 2
            r4 = 0
            if (r1 == 0) goto L5d
            int r5 = r1.length
            if (r5 == 0) goto L5d
            if (r12 == 0) goto L5d
            int r5 = r12.length
            if (r5 != 0) goto L29
            goto L5d
        L29:
            java.lang.String r5 = "HMACSHA256"
            javax.crypto.Mac r5 = javax.crypto.Mac.getInstance(r5)
            javax.crypto.SecretKey r6 = r11.mMacKey
            r5.init(r6)
            r5.update(r1)
            r5.update(r0)
            byte[] r5 = r5.doFinal()
            r6 = 1
            if (r5 != 0) goto L44
            if (r12 != r5) goto L68
            goto L69
        L44:
            int r7 = r12.length
            int r8 = r5.length
            if (r7 == r8) goto L49
            goto L68
        L49:
            r8 = r4
            r7 = r6
        L4b:
            int r9 = r5.length
            if (r8 >= r9) goto L5b
            r9 = r12[r8]
            r10 = r5[r8]
            if (r9 != r10) goto L56
            r9 = r6
            goto L57
        L56:
            r9 = r4
        L57:
            r7 = r7 & r9
            int r8 = r8 + 1
            goto L4b
        L5b:
            r6 = r7
            goto L69
        L5d:
            boolean r12 = android.util.Log.isLoggable(r2, r3)
            if (r12 == 0) goto L68
            java.lang.String r12 = "Cipher or MAC is empty!"
            android.util.Log.v(r2, r12)
        L68:
            r6 = r4
        L69:
            if (r6 != 0) goto L72
            java.lang.String r11 = "Escrow mac mismatched!"
            android.util.Log.w(r2, r11)
            r11 = 0
            return r11
        L72:
            javax.crypto.spec.IvParameterSpec r12 = new javax.crypto.spec.IvParameterSpec
            r12.<init>(r0)
            java.lang.String r0 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r0 = javax.crypto.Cipher.getInstance(r0)
            javax.crypto.SecretKey r11 = r11.mEncryptionKey
            r0.init(r3, r11, r12)
            byte[] r11 = r0.doFinal(r1)
            android.os.Parcel r12 = android.os.Parcel.obtain()
            int r0 = r11.length
            r12.unmarshall(r11, r4, r0)
            r12.setDataPosition(r4)
            android.os.Bundle r11 = new android.os.Bundle
            r11.<init>()
            r11.readFromParcel(r12)
            r12.recycle()
            return r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accounts.CryptoHelper.decryptBundle(android.os.Bundle):android.os.Bundle");
    }

    public final Bundle encryptBundle(Bundle bundle) {
        Parcel obtain = Parcel.obtain();
        bundle.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, this.mEncryptionKey);
        byte[] doFinal = cipher.doFinal(marshall);
        byte[] iv = cipher.getIV();
        Mac mac = Mac.getInstance("HMACSHA256");
        mac.init(this.mMacKey);
        mac.update(doFinal);
        mac.update(iv);
        byte[] doFinal2 = mac.doFinal();
        Bundle bundle2 = new Bundle();
        bundle2.putByteArray("cipher", doFinal);
        bundle2.putByteArray("mac", doFinal2);
        bundle2.putByteArray("iv", iv);
        return bundle2;
    }
}
