package android.app.backup;

import android.os.ParcelFileDescriptor;
import android.util.ArrayMap;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/* loaded from: classes.dex */
public abstract class BlobBackupHelper extends BackupHelperWithLogger {
    private static final boolean DEBUG = false;
    private static final String TAG = "BlobBackupHelper";
    private final int mCurrentBlobVersion;
    private final String[] mKeys;

    protected abstract void applyRestoredPayload(String str, byte[] bArr);

    protected abstract byte[] getBackupPayload(String str);

    public BlobBackupHelper(int i, String... strArr) {
        this.mCurrentBlobVersion = i;
        this.mKeys = strArr;
    }

    private ArrayMap<String, Long> readOldState(ParcelFileDescriptor parcelFileDescriptor) {
        int readInt;
        ArrayMap<String, Long> arrayMap = new ArrayMap<>();
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(parcelFileDescriptor.getFileDescriptor()));
        try {
            readInt = dataInputStream.readInt();
        } catch (EOFException unused) {
            arrayMap.clear();
        } catch (Exception e) {
            Log.e(TAG, "Error examining prior backup state " + e.getMessage());
            arrayMap.clear();
        }
        if (readInt > this.mCurrentBlobVersion) {
            Log.w(TAG, "Prior state from unrecognized version " + readInt);
            return arrayMap;
        }
        int readInt2 = dataInputStream.readInt();
        for (int i = 0; i < readInt2; i++) {
            arrayMap.put(dataInputStream.readUTF(), Long.valueOf(dataInputStream.readLong()));
        }
        return arrayMap;
    }

    private void writeBackupState(ArrayMap<String, Long> arrayMap, ParcelFileDescriptor parcelFileDescriptor) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
            dataOutputStream.writeInt(this.mCurrentBlobVersion);
            int size = arrayMap != null ? arrayMap.size() : 0;
            dataOutputStream.writeInt(size);
            for (int i = 0; i < size; i++) {
                String keyAt = arrayMap.keyAt(i);
                long longValue = arrayMap.valueAt(i).longValue();
                dataOutputStream.writeUTF(keyAt);
                dataOutputStream.writeLong(longValue);
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to write updated state", e);
        }
    }

    private byte[] deflate(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new DataOutputStream(byteArrayOutputStream).writeInt(this.mCurrentBlobVersion);
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
            deflaterOutputStream.write(bArr);
            deflaterOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            Log.w(TAG, "Unable to process payload: " + e.getMessage());
            return null;
        }
    }

    private byte[] inflate(byte[] bArr) {
        if (bArr != null) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                int readInt = new DataInputStream(byteArrayInputStream).readInt();
                if (readInt > this.mCurrentBlobVersion) {
                    Log.w(TAG, "Saved payload from unrecognized version " + readInt);
                    return null;
                }
                InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr2 = new byte[4096];
                while (true) {
                    int read = inflaterInputStream.read(bArr2);
                    if (read <= 0) {
                        inflaterInputStream.close();
                        byteArrayOutputStream.flush();
                        return byteArrayOutputStream.toByteArray();
                    }
                    byteArrayOutputStream.write(bArr2, 0, read);
                }
            } catch (IOException e) {
                Log.w(TAG, "Unable to process restored payload: " + e.getMessage());
            }
        }
        return null;
    }

    private long checksum(byte[] bArr) {
        if (bArr == null) {
            return -1L;
        }
        try {
            CRC32 crc32 = new CRC32();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            byte[] bArr2 = new byte[4096];
            while (true) {
                int read = byteArrayInputStream.read(bArr2);
                if (read >= 0) {
                    crc32.update(bArr2, 0, read);
                } else {
                    return crc32.getValue();
                }
            }
        } catch (Exception unused) {
            return -1L;
        }
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        ArrayMap<String, Long> readOldState = readOldState(parcelFileDescriptor);
        ArrayMap<String, Long> arrayMap = new ArrayMap<>();
        try {
            for (String str : this.mKeys) {
                byte[] deflate = deflate(getBackupPayload(str));
                long checksum = checksum(deflate);
                arrayMap.put(str, Long.valueOf(checksum));
                Long l = readOldState.get(str);
                if (l == null || checksum != l.longValue()) {
                    if (deflate != null) {
                        backupDataOutput.writeEntityHeader(str, deflate.length);
                        backupDataOutput.writeEntityData(deflate, deflate.length);
                    } else {
                        backupDataOutput.writeEntityHeader(str, -1);
                    }
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Unable to record notification state: " + e.getMessage());
            arrayMap.clear();
        } finally {
            writeBackupState(arrayMap, parcelFileDescriptor2);
        }
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void restoreEntity(BackupDataInputStream backupDataInputStream) {
        String key = backupDataInputStream.getKey();
        int i = 0;
        while (true) {
            try {
                String[] strArr = this.mKeys;
                if (i >= strArr.length || key.equals(strArr[i])) {
                    break;
                } else {
                    i++;
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception restoring entity " + key + " : " + e.getMessage());
                return;
            }
        }
        if (i >= this.mKeys.length) {
            Log.e(TAG, "Unrecognized key " + key + ", ignoring");
            return;
        }
        byte[] bArr = new byte[backupDataInputStream.size()];
        backupDataInputStream.read(bArr);
        applyRestoredPayload(key, inflate(bArr));
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        writeBackupState(null, parcelFileDescriptor);
    }
}
