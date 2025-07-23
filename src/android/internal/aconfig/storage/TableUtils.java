package android.internal.aconfig.storage;

import android.adservices.common.AdservicesCelEnums;
import android.bluetooth.hci.BluetoothHciProtoEnums;
import com.android.internal.logging.nano.MetricsProto;

/* loaded from: classes2.dex */
public class TableUtils {
    private static final int[] HASH_PRIMES = {7, 17, 29, 53, 97, 193, 389, 769, MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_REAL_ACTIVITY, AdservicesCelEnums.GET_AD_SELECTION_DATA_RUNNER_NOTIFY_FAILURE_CALLBACK_ERROR, BluetoothHciProtoEnums.CMD_ENABLE_AMP_RCVR_REPORTS, 12289, 24593, 49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

    public static int getTableSize(int i) {
        for (int i2 : HASH_PRIMES) {
            if (i2 >= i * 2) {
                return i2;
            }
        }
        throw new AconfigStorageException("Number of items in a hash table exceeds limit");
    }

    public static int getBucketIndex(byte[] bArr, int i) {
        return (int) Long.remainderUnsigned(SipHasher13.hash(bArr), i);
    }

    public static class StorageFilesBundle {
        public final FlagTable flagTable;
        public final FlagValueList flagValueList;
        public final PackageTable packageTable;

        public StorageFilesBundle(PackageTable packageTable, FlagTable flagTable, FlagValueList flagValueList) {
            this.packageTable = packageTable;
            this.flagTable = flagTable;
            this.flagValueList = flagValueList;
        }
    }
}
