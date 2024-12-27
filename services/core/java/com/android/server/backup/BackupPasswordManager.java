package com.android.server.backup;

import android.content.Context;
import android.util.Slog;

import com.android.server.backup.utils.PasswordUtils;

import libcore.util.HexEncoding;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.SecretKey;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BackupPasswordManager {
    public final File mBaseStateDir;
    public final Context mContext;
    public String mPasswordHash;
    public byte[] mPasswordSalt;
    public int mPasswordVersion;
    public final SecureRandom mRng;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BackupPasswordHash {
        public final String hash;
        public final byte[] salt;

        public BackupPasswordHash(String str, byte[] bArr) {
            this.hash = str;
            this.salt = bArr;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PasswordHashFileCodec {
        public final /* synthetic */ int $r8$classId;
    }

    public BackupPasswordManager(Context context, File file, SecureRandom secureRandom) {
        FileInputStream fileInputStream;
        DataInputStream dataInputStream;
        this.mContext = context;
        this.mRng = secureRandom;
        this.mBaseStateDir = file;
        try {
            fileInputStream = new FileInputStream(new File(file, "pwversion"));
            try {
                dataInputStream = new DataInputStream(fileInputStream);
                try {
                    Integer valueOf = Integer.valueOf(dataInputStream.readInt());
                    dataInputStream.close();
                    fileInputStream.close();
                    this.mPasswordVersion = valueOf.intValue();
                } finally {
                    try {
                        dataInputStream.close();
                    } catch (Throwable th) {
                        th.addSuppressed(th);
                    }
                }
            } finally {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
        } catch (IOException unused) {
            Slog.e("BackupPasswordManager", "Unable to read backup pw version");
            this.mPasswordVersion = 1;
        }
        try {
            fileInputStream = new FileInputStream(new File(this.mBaseStateDir, "pwhash"));
            try {
                dataInputStream = new DataInputStream(fileInputStream);
                try {
                    byte[] bArr = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr);
                    BackupPasswordHash backupPasswordHash =
                            new BackupPasswordHash(dataInputStream.readUTF(), bArr);
                    dataInputStream.close();
                    fileInputStream.close();
                    this.mPasswordHash = backupPasswordHash.hash;
                    this.mPasswordSalt = backupPasswordHash.salt;
                } finally {
                }
            } finally {
            }
        } catch (IOException unused2) {
            Slog.e("BackupPasswordManager", "Unable to read saved backup pw hash");
        }
    }

    public final boolean passwordMatchesSaved(String str, String str2) {
        if (this.mPasswordHash == null) {
            return str2 == null || str2.equals("");
        }
        if (str2 == null || str2.length() == 0) {
            return false;
        }
        SecretKey buildCharArrayKey =
                PasswordUtils.buildCharArrayKey(str, str2.toCharArray(), this.mPasswordSalt, 10000);
        return this.mPasswordHash.equalsIgnoreCase(
                buildCharArrayKey != null
                        ? HexEncoding.encodeToString(buildCharArrayKey.getEncoded(), true)
                        : null);
    }
}
