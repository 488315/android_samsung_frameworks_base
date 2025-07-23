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
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BackupRestoreStorage implements BackupHelper {
    public static final Companion Companion = new Companion(null);
    public List entities;
    public final MutableScatterMap entityStates = new MutableScatterMap(0, 1, null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
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

    public final void readEntityStates$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore(ParcelFileDescriptor parcelFileDescriptor, MutableScatterMap mutableScatterMap) {
        FileDescriptor fileDescriptor;
        mutableScatterMap.clear();
        if (parcelFileDescriptor == null || (fileDescriptor = parcelFileDescriptor.getFileDescriptor()) == null) {
            return;
        }
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(fileDescriptor));
        try {
            byte readByte = dataInputStream.readByte();
            if (readByte != 0) {
                Log.w("BackupRestoreStorage", "[" + getName() + "] Unexpected state version, read:" + ((int) readByte) + ", expected:0");
                return;
            }
            int readInt = dataInputStream.readInt();
            while (true) {
                int i = readInt - 1;
                if (readInt <= 0) {
                    return;
                }
                String readUTF = dataInputStream.readUTF();
                long readLong = dataInputStream.readLong();
                readUTF.getClass();
                mutableScatterMap.set(readUTF, Long.valueOf(readLong));
                readInt = i;
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
        List list = this.entities;
        if (list == null) {
            list = createBackupRestoreEntities();
            this.entities = list;
        }
        Iterator it = list.iterator();
        if (it.hasNext()) {
            throw FragmentManager$$ExternalSyntheticOutline0.m(it);
        }
        Log.w("BackupRestoreStorage", "[" + getName() + "] Cannot find handler for entity " + key);
    }

    public final void writeAndClearEntityStates(ParcelFileDescriptor parcelFileDescriptor) {
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        int i = 0;
        try {
            dataOutputStream.writeByte(0);
            dataOutputStream.writeInt(this.entityStates._size);
            MutableScatterMap mutableScatterMap = this.entityStates;
            Object[] objArr = mutableScatterMap.keys;
            Object[] objArr2 = mutableScatterMap.values;
            long[] jArr = mutableScatterMap.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i2 = 0;
                while (true) {
                    long j = jArr[i2];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i3 = 8 - ((~(i2 - length)) >>> 31);
                        for (int i4 = i; i4 < i3; i4++) {
                            if ((255 & j) < 128) {
                                int i5 = (i2 << 3) + i4;
                                Object obj = objArr[i5];
                                long longValue = ((Number) objArr2[i5]).longValue();
                                dataOutputStream.writeUTF((String) obj);
                                dataOutputStream.writeLong(longValue);
                            }
                            j >>= 8;
                        }
                        if (i3 != 8) {
                            break;
                        }
                    }
                    if (i2 == length) {
                        break;
                    }
                    i2++;
                    i = 0;
                }
            }
            dataOutputStream.flush();
        } catch (Exception e) {
            Log.e("BackupRestoreStorage", "[" + getName() + "] Fail to write state file", e);
        }
        this.entityStates.clear();
        MutableScatterMap mutableScatterMap2 = this.entityStates;
        int i6 = mutableScatterMap2._capacity;
        int normalizeCapacity = ScatterMapKt.normalizeCapacity(ScatterMapKt.unloadedCapacity(mutableScatterMap2._size));
        if (normalizeCapacity < i6) {
            mutableScatterMap2.resizeStorage$collection(normalizeCapacity);
        }
    }

    @Override // android.app.backup.BackupHelper
    public final void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        this.entities = null;
        writeAndClearEntityStates(parcelFileDescriptor);
    }

    public static /* synthetic */ void getEntities$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore$annotations() {
    }

    public static /* synthetic */ void getEntityStates$annotations() {
    }
}
