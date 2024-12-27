package com.android.server.backup.utils;

import com.android.server.backup.BackupPasswordManager;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DataStreamFileCodec {
    public final BackupPasswordManager.PasswordHashFileCodec mCodec;
    public final File mFile;

    public DataStreamFileCodec(File file, BackupPasswordManager.PasswordHashFileCodec passwordHashFileCodec) {
        this.mFile = file;
        this.mCodec = passwordHashFileCodec;
    }

    public final void serialize(Object obj) {
        FileOutputStream fileOutputStream = new FileOutputStream(this.mFile);
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
                try {
                    switch (this.mCodec.$r8$classId) {
                        case 0:
                            BackupPasswordManager.BackupPasswordHash backupPasswordHash = (BackupPasswordManager.BackupPasswordHash) obj;
                            dataOutputStream.writeInt(backupPasswordHash.salt.length);
                            dataOutputStream.write(backupPasswordHash.salt);
                            dataOutputStream.writeUTF(backupPasswordHash.hash);
                            break;
                        default:
                            dataOutputStream.write(((Integer) obj).intValue());
                            break;
                    }
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
