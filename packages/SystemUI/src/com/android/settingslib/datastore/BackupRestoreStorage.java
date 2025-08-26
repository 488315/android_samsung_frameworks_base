package com.android.settingslib.datastore;

import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class BackupRestoreStorage implements BackupHelper {
    public static final Companion Companion = new Companion(null);
    public List entities;
    public final MutableScatterMap entityStates = new MutableScatterMap(0, 1, null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract List createBackupRestoreEntities();

    public abstract String getName();

    @Override // android.app.backup.BackupHelper
    public final void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) throws IOException {
        readEntityStates$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(parcelFileDescriptor, this.entityStates);
        new BackupContext(backupDataOutput);
        Log.i("BackupRestoreStorage", "[" + getName() + "] Backup start");
        Companion.getClass();
        new CRC32();
        Iterator it = createBackupRestoreEntities().iterator();
        if (it.hasNext()) {
            throw FragmentManager$$ExternalSyntheticOutline0.m(it);
        }
        writeAndClearEntityStates(parcelFileDescriptor2);
        Log.i("BackupRestoreStorage", "[" + getName() + "] Backup end");
    }

    public final void readEntityStates$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(ParcelFileDescriptor parcelFileDescriptor, MutableScatterMap mutableScatterMap) throws IOException {
        FileDescriptor fileDescriptor;
        mutableScatterMap.clear();
        if (parcelFileDescriptor == null || (fileDescriptor = parcelFileDescriptor.getFileDescriptor()) == null) {
            return;
        }
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(fileDescriptor));
        try {
            byte b = dataInputStream.readByte();
            if (b != 0) {
                Log.w("BackupRestoreStorage", "[" + getName() + "] Unexpected state version, read:" + ((int) b) + ", expected:0");
                return;
            }
            int i = dataInputStream.readInt();
            while (true) {
                int i2 = i - 1;
                if (i <= 0) {
                    return;
                }
                String utf = dataInputStream.readUTF();
                long j = dataInputStream.readLong();
                utf.getClass();
                mutableScatterMap.set(utf, Long.valueOf(j));
                i = i2;
            }
        } catch (Exception e) {
            if (e instanceof EOFException) {
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("[", getName(), "] Hit EOF when read state file", "BackupRestoreStorage");
            } else {
                Log.e("BackupRestoreStorage", "[" + getName() + "] Fail to read state file", e);
            }
            mutableScatterMap.clear();
        }
    }

    @Override // android.app.backup.BackupHelper
    public final void restoreEntity(BackupDataInputStream backupDataInputStream) {
        String key = backupDataInputStream.getKey();
        List listCreateBackupRestoreEntities = this.entities;
        if (listCreateBackupRestoreEntities == null) {
            listCreateBackupRestoreEntities = createBackupRestoreEntities();
            this.entities = listCreateBackupRestoreEntities;
        }
        Iterator it = listCreateBackupRestoreEntities.iterator();
        if (it.hasNext()) {
            throw FragmentManager$$ExternalSyntheticOutline0.m(it);
        }
        Log.w("BackupRestoreStorage", "[" + getName() + "] Cannot find handler for entity " + key);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void writeAndClearEntityStates(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        int i;
        int iNormalizeCapacity;
        Object[] objArr;
        Object[] objArr2;
        long[] jArr;
        int length;
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        int i2 = 0;
        try {
            dataOutputStream.writeByte(0);
            dataOutputStream.writeInt(this.entityStates._size);
            MutableScatterMap mutableScatterMap = this.entityStates;
            objArr = mutableScatterMap.keys;
            objArr2 = mutableScatterMap.values;
            jArr = mutableScatterMap.metadata;
            length = jArr.length - 2;
        } catch (Exception e) {
            Log.e("BackupRestoreStorage", "[" + getName() + "] Fail to write state file", e);
        }
        if (length >= 0) {
            int i3 = 0;
            while (true) {
                long j = jArr[i3];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i4 = 8 - ((~(i3 - length)) >>> 31);
                    for (int i5 = i2; i5 < i4; i5++) {
                        if ((255 & j) < 128) {
                            int i6 = (i3 << 3) + i5;
                            Object obj = objArr[i6];
                            long jLongValue = ((Number) objArr2[i6]).longValue();
                            dataOutputStream.writeUTF((String) obj);
                            dataOutputStream.writeLong(jLongValue);
                        }
                        j >>= 8;
                    }
                    if (i4 != 8) {
                        break;
                    }
                    if (i3 == length) {
                        break;
                    }
                    i3++;
                    i2 = 0;
                }
                this.entityStates.clear();
                MutableScatterMap mutableScatterMap2 = this.entityStates;
                i = mutableScatterMap2._capacity;
                iNormalizeCapacity = ScatterMapKt.normalizeCapacity(ScatterMapKt.unloadedCapacity(mutableScatterMap2._size));
                if (iNormalizeCapacity >= i) {
                    mutableScatterMap2.resizeStorage$collection(iNormalizeCapacity);
                    return;
                }
                return;
            }
        }
        dataOutputStream.flush();
        this.entityStates.clear();
        MutableScatterMap mutableScatterMap22 = this.entityStates;
        i = mutableScatterMap22._capacity;
        iNormalizeCapacity = ScatterMapKt.normalizeCapacity(ScatterMapKt.unloadedCapacity(mutableScatterMap22._size));
        if (iNormalizeCapacity >= i) {
        }
    }

    @Override // android.app.backup.BackupHelper
    public final void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        this.entities = null;
        writeAndClearEntityStates(parcelFileDescriptor);
    }

    public static /* synthetic */ void getEntities$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore$annotations() {
    }

    public static /* synthetic */ void getEntityStates$annotations() {
    }
}
