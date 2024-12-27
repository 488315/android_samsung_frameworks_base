package com.android.server.backup.restore;

import android.app.backup.IFullBackupRestoreObserver;
import android.os.ParcelFileDescriptor;
import android.util.Slog;

import com.android.server.backup.BackupManagerYuva;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.fullbackup.FullBackupObbConnection;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.android.server.backup.utils.PasswordUtils;

import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PerformAdbRestoreTask implements Runnable {
    public static BackupManagerYuva mBackupManagerYuva;
    public final UserBackupManagerService mBackupManagerService;
    public final String mCurrentPassword;
    public final String mDecryptPassword;
    public final ParcelFileDescriptor mInputFile;
    public final AtomicBoolean mLatchObject;
    public final FullBackupObbConnection mObbConnection;
    public IFullBackupRestoreObserver mObserver;
    public final OperationStorage mOperationStorage;
    public final boolean mOperationTypeMIGRATION;
    public final boolean mPrivilegeApp;

    public PerformAdbRestoreTask(
            UserBackupManagerService userBackupManagerService,
            LifecycleOperationStorage lifecycleOperationStorage,
            ParcelFileDescriptor parcelFileDescriptor,
            String str,
            String str2,
            IFullBackupRestoreObserver iFullBackupRestoreObserver,
            AtomicBoolean atomicBoolean,
            boolean z,
            boolean z2) {
        this.mPrivilegeApp = false;
        this.mOperationTypeMIGRATION = false;
        this.mBackupManagerService = userBackupManagerService;
        this.mOperationStorage = lifecycleOperationStorage;
        this.mInputFile = parcelFileDescriptor;
        this.mCurrentPassword = str;
        this.mDecryptPassword = str2;
        this.mObserver = iFullBackupRestoreObserver;
        this.mLatchObject = atomicBoolean;
        this.mObbConnection = new FullBackupObbConnection(userBackupManagerService);
        this.mPrivilegeApp = z;
        this.mOperationTypeMIGRATION = z2;
        if (userBackupManagerService.isYuvaSupported()) {
            Slog.d("BackupManagerService", "Backup Manager Yuva is Supported");
            mBackupManagerYuva = BackupManagerYuva.getInstanceBackupYuva();
        }
    }

    public static InputStream attemptEncryptionKeyDecryption(
            String str,
            String str2,
            byte[] bArr,
            byte[] bArr2,
            int i,
            String str3,
            String str4,
            InputStream inputStream,
            boolean z) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey buildCharArrayKey =
                    PasswordUtils.buildCharArrayKey(str2, str.toCharArray(), bArr, i);
            cipher.init(
                    2,
                    new SecretKeySpec(buildCharArrayKey.getEncoded(), "AES"),
                    new IvParameterSpec(PasswordUtils.hexToByteArray(str3)));
            byte[] doFinal = cipher.doFinal(PasswordUtils.hexToByteArray(str4));
            byte b = doFinal[0];
            int i2 = 1 + b;
            byte[] copyOfRange = Arrays.copyOfRange(doFinal, 1, i2);
            int i3 = b + 2;
            int i4 = doFinal[i2] + i3;
            byte[] copyOfRange2 = Arrays.copyOfRange(doFinal, i3, i4);
            int i5 = i4 + 1;
            byte[] copyOfRange3 = Arrays.copyOfRange(doFinal, i5, doFinal[i4] + i5);
            char[] cArr = new char[copyOfRange2.length];
            for (int i6 = 0; i6 < copyOfRange2.length; i6++) {
                cArr[i6] = (char) copyOfRange2[i6];
            }
            if (Arrays.equals(
                    PasswordUtils.buildCharArrayKey(str2, cArr, bArr2, i).getEncoded(),
                    copyOfRange3)) {
                cipher.init(
                        2,
                        new SecretKeySpec(copyOfRange2, "AES"),
                        new IvParameterSpec(copyOfRange));
                return new CipherInputStream(inputStream, cipher);
            }
            if (!z) {
                return null;
            }
            Slog.w("BackupManagerService", "Incorrect password");
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            if (!z) {
                return null;
            }
            Slog.e("BackupManagerService", "Needed parameter spec unavailable!", e);
            return null;
        } catch (InvalidKeyException unused) {
            if (!z) {
                return null;
            }
            Slog.w("BackupManagerService", "Illegal password; aborting");
            return null;
        } catch (NoSuchAlgorithmException unused2) {
            if (!z) {
                return null;
            }
            Slog.e("BackupManagerService", "Needed decryption algorithm unavailable!");
            return null;
        } catch (BadPaddingException unused3) {
            if (!z) {
                return null;
            }
            Slog.w("BackupManagerService", "Incorrect password");
            return null;
        } catch (IllegalBlockSizeException unused4) {
            if (!z) {
                return null;
            }
            Slog.w("BackupManagerService", "Invalid block size in encryption key");
            return null;
        } catch (NoSuchPaddingException unused5) {
            if (!z) {
                return null;
            }
            Slog.e("BackupManagerService", "Needed padding mechanism unavailable!");
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.InputStream parseBackupFileHeaderAndReturnTarStream(
            java.io.InputStream r23, java.lang.String r24) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.restore.PerformAdbRestoreTask.parseBackupFileHeaderAndReturnTarStream(java.io.InputStream,"
                    + " java.lang.String):java.io.InputStream");
    }

    public static String readHeaderLine(InputStream inputStream) {
        StringBuilder sb = new StringBuilder(80);
        while (true) {
            int read = inputStream.read();
            if (read < 0 || read == 10) {
                break;
            }
            sb.append((char) read);
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x0250 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 734
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.restore.PerformAdbRestoreTask.run():void");
    }
}
