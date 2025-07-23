package com.android.internal.app.procstats;

import android.os.Build;
import android.os.Parcel;
import android.util.EventLog;
import android.util.Slog;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.GrowingArrayUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import libcore.util.EmptyArray;

/* loaded from: classes5.dex */
public class SparseMappingTable {
    private static final int ARRAY_MASK = 255;
    private static final int ARRAY_SHIFT = 8;
    public static final int ARRAY_SIZE = 4096;
    private static final int ID_MASK = 255;
    private static final int ID_SHIFT = 0;
    private static final int INDEX_MASK = 65535;
    private static final int INDEX_SHIFT = 16;
    public static final int INVALID_KEY = -1;
    private static final String TAG = "SparseMappingTable";
    private final ArrayList<long[]> mLongs;
    private int mNextIndex;
    private int mSequence;

    public static int getArrayFromKey(int i) {
        return (i >> 8) & 255;
    }

    public static byte getIdFromKey(int i) {
        return (byte) (i & 255);
    }

    public static int getIndexFromKey(int i) {
        return (i >> 16) & 65535;
    }

    public static class Table {
        private SparseMappingTable mParent;
        private int mSequence;
        private int mSize;
        private int[] mTable;

        private void assertConsistency() {
        }

        public Table(SparseMappingTable sparseMappingTable) {
            this.mSequence = 1;
            this.mParent = sparseMappingTable;
            this.mSequence = sparseMappingTable.mSequence;
        }

        public void copyFrom(Table table, int i) {
            this.mTable = null;
            this.mSize = 0;
            int keyCount = table.getKeyCount();
            for (int i2 = 0; i2 < keyCount; i2++) {
                int keyAt = table.getKeyAt(i2);
                long[] jArr = (long[]) table.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(keyAt));
                int orAddKey = getOrAddKey(SparseMappingTable.getIdFromKey(keyAt), i);
                System.arraycopy(jArr, SparseMappingTable.getIndexFromKey(keyAt), (long[]) this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(orAddKey)), SparseMappingTable.getIndexFromKey(orAddKey), i);
            }
        }

        public int getOrAddKey(byte b, int i) {
            assertConsistency();
            int binarySearch = binarySearch(b);
            if (binarySearch >= 0) {
                return this.mTable[binarySearch];
            }
            ArrayList arrayList = this.mParent.mLongs;
            int size = arrayList.size();
            int i2 = size - 1;
            if (this.mParent.mNextIndex + i > ((long[]) arrayList.get(i2)).length) {
                arrayList.add(new long[4096]);
                this.mParent.mNextIndex = 0;
            } else {
                size = i2;
            }
            int i3 = b | (size << 8) | (this.mParent.mNextIndex << 16);
            this.mParent.mNextIndex += i;
            int[] iArr = this.mTable;
            if (iArr == null) {
                iArr = EmptyArray.INT;
            }
            this.mTable = GrowingArrayUtils.insert(iArr, this.mSize, ~binarySearch, i3);
            this.mSize++;
            return i3;
        }

        public int getKey(byte b) {
            assertConsistency();
            int binarySearch = binarySearch(b);
            if (binarySearch >= 0) {
                return this.mTable[binarySearch];
            }
            return -1;
        }

        public long getValue(int i) {
            return getValue(i, 0);
        }

        public long getValue(int i, int i2) {
            assertConsistency();
            try {
                return ((long[]) this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(i)))[SparseMappingTable.getIndexFromKey(i) + i2];
            } catch (IndexOutOfBoundsException e) {
                SparseMappingTable.logOrThrow("key=0x" + Integer.toHexString(i) + " index=" + i2 + " -- " + this.dumpInternalState(), e);
                return 0L;
            }
        }

        public long getValueForId(byte b) {
            return getValueForId(b, 0);
        }

        public long getValueForId(byte b, int i) {
            assertConsistency();
            int binarySearch = binarySearch(b);
            if (binarySearch >= 0) {
                int i2 = this.mTable[binarySearch];
                try {
                    return ((long[]) this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(i2)))[SparseMappingTable.getIndexFromKey(i2) + i];
                } catch (IndexOutOfBoundsException e) {
                    SparseMappingTable.logOrThrow("id=0x" + Integer.toHexString(b) + " idx=" + binarySearch + " key=0x" + Integer.toHexString(i2) + " index=" + i + " -- " + this.dumpInternalState(), e);
                }
            }
            return 0L;
        }

        public long[] getArrayForKey(int i) {
            assertConsistency();
            return (long[]) this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(i));
        }

        public void setValue(int i, long j) {
            setValue(i, 0, j);
        }

        public void setValue(int i, int i2, long j) {
            assertConsistency();
            if (j < 0) {
                SparseMappingTable.logOrThrow("can't store negative values key=0x" + Integer.toHexString(i) + " index=" + i2 + " value=" + j + " -- " + dumpInternalState());
                return;
            }
            try {
                ((long[]) this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(i)))[SparseMappingTable.getIndexFromKey(i) + i2] = j;
            } catch (IndexOutOfBoundsException e) {
                SparseMappingTable.logOrThrow("key=0x" + Integer.toHexString(i) + " index=" + i2 + " value=" + j + " -- " + dumpInternalState(), e);
            }
        }

        public void resetTable() {
            this.mTable = null;
            this.mSize = 0;
            this.mSequence = this.mParent.mSequence;
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.mSequence);
            parcel.writeInt(this.mSize);
            for (int i = 0; i < this.mSize; i++) {
                parcel.writeInt(this.mTable[i]);
            }
        }

        public boolean readFromParcel(Parcel parcel) {
            this.mSequence = parcel.readInt();
            int readInt = parcel.readInt();
            this.mSize = readInt;
            if (readInt != 0) {
                this.mTable = new int[readInt];
                for (int i = 0; i < this.mSize; i++) {
                    this.mTable[i] = parcel.readInt();
                }
            } else {
                this.mTable = null;
            }
            if (validateKeys(true)) {
                return true;
            }
            this.mSize = 0;
            this.mTable = null;
            return false;
        }

        public int getKeyCount() {
            return this.mSize;
        }

        public int getKeyAt(int i) {
            return this.mTable[i];
        }

        private int binarySearch(byte b) {
            int i = this.mSize - 1;
            int i2 = 0;
            while (i2 <= i) {
                int i3 = (i2 + i) >>> 1;
                byte b2 = (byte) (this.mTable[i3] & 255);
                if (b2 < b) {
                    i2 = i3 + 1;
                } else {
                    if (b2 <= b) {
                        return i3;
                    }
                    i = i3 - 1;
                }
            }
            return ~i2;
        }

        private boolean validateKeys(boolean z) {
            ArrayList arrayList = this.mParent.mLongs;
            int size = arrayList.size();
            int i = this.mSize;
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = this.mTable[i2];
                int arrayFromKey = SparseMappingTable.getArrayFromKey(i3);
                int indexFromKey = SparseMappingTable.getIndexFromKey(i3);
                if (arrayFromKey >= size || indexFromKey >= ((long[]) arrayList.get(arrayFromKey)).length) {
                    if (z) {
                        Slog.w(SparseMappingTable.TAG, "Invalid stats at index " + i2 + " -- " + dumpInternalState());
                    }
                    return false;
                }
            }
            return true;
        }

        public String dumpInternalState() {
            StringBuilder sb = new StringBuilder("SparseMappingTable.Table{mSequence=");
            sb.append(this.mSequence);
            sb.append(" mParent.mSequence=");
            sb.append(this.mParent.mSequence);
            sb.append(" mParent.mLongs.size()=");
            sb.append(this.mParent.mLongs.size());
            sb.append(" mSize=");
            sb.append(this.mSize);
            sb.append(" mTable=");
            int[] iArr = this.mTable;
            if (iArr == null) {
                sb.append(PerfettoProtoLogImpl.NULL_STRING);
            } else {
                int length = iArr.length;
                sb.append('[');
                for (int i = 0; i < length; i++) {
                    int i2 = this.mTable[i];
                    sb.append("0x");
                    sb.append(Integer.toHexString(i2 & 255));
                    sb.append("/0x");
                    sb.append(Integer.toHexString((i2 >> 8) & 255));
                    sb.append("/0x");
                    sb.append(Integer.toHexString((i2 >> 16) & 65535));
                    if (i != length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(']');
            }
            sb.append(" clazz=");
            sb.append(getClass().getName());
            sb.append('}');
            return sb.toString();
        }
    }

    public SparseMappingTable() {
        ArrayList<long[]> arrayList = new ArrayList<>();
        this.mLongs = arrayList;
        arrayList.add(new long[4096]);
    }

    public void reset() {
        this.mLongs.clear();
        this.mLongs.add(new long[4096]);
        this.mNextIndex = 0;
        this.mSequence++;
    }

    public void writeToParcel(Parcel parcel) {
        parcel.writeInt(this.mSequence);
        parcel.writeInt(this.mNextIndex);
        int size = this.mLongs.size();
        parcel.writeInt(size);
        int i = 0;
        while (true) {
            int i2 = size - 1;
            if (i < i2) {
                long[] jArr = this.mLongs.get(i);
                parcel.writeInt(jArr.length);
                writeCompactedLongArray(parcel, jArr, jArr.length);
                i++;
            } else {
                long[] jArr2 = this.mLongs.get(i2);
                parcel.writeInt(this.mNextIndex);
                writeCompactedLongArray(parcel, jArr2, this.mNextIndex);
                return;
            }
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mSequence = parcel.readInt();
        this.mNextIndex = parcel.readInt();
        this.mLongs.clear();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            int readInt2 = parcel.readInt();
            long[] jArr = new long[readInt2];
            readCompactedLongArray(parcel, jArr, readInt2);
            this.mLongs.add(jArr);
        }
        if (readInt > 0) {
            int i2 = readInt - 1;
            if (this.mLongs.get(i2).length == this.mNextIndex) {
                return;
            }
            EventLog.writeEvent(1397638484, "73252178", -1, "");
            throw new IllegalStateException("Expected array of length " + this.mNextIndex + " but was " + this.mLongs.get(i2).length);
        }
    }

    public String dumpInternalState(boolean z) {
        StringBuilder sb = new StringBuilder("SparseMappingTable{mSequence=");
        sb.append(this.mSequence);
        sb.append(" mNextIndex=");
        sb.append(this.mNextIndex);
        sb.append(" mLongs.size=");
        int size = this.mLongs.size();
        sb.append(size);
        sb.append(ShaderAssembler.NEWLINE);
        if (z) {
            for (int i = 0; i < size; i++) {
                long[] jArr = this.mLongs.get(i);
                for (int i2 = 0; i2 < jArr.length && (i != size - 1 || i2 != this.mNextIndex); i2++) {
                    sb.append(String.format(" %4d %d 0x%016x %-19d\n", Integer.valueOf(i), Integer.valueOf(i2), Long.valueOf(jArr[i2]), Long.valueOf(jArr[i2])));
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private static void writeCompactedLongArray(Parcel parcel, long[] jArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            long j = jArr[i2];
            if (j < 0) {
                Slog.w(TAG, "Time val negative: " + j);
                j = 0L;
            }
            if (j <= 2147483647L) {
                parcel.writeInt((int) j);
            } else {
                parcel.writeInt(~((int) (2147483647L & (j >> 32))));
                parcel.writeInt((int) (j & 4294967295L));
            }
        }
    }

    private static void readCompactedLongArray(Parcel parcel, long[] jArr, int i) {
        int length = jArr.length;
        if (i > length) {
            logOrThrow("bad array lengths: got " + i + " array is " + length);
            return;
        }
        int i2 = 0;
        while (i2 < i) {
            int readInt = parcel.readInt();
            if (readInt >= 0) {
                jArr[i2] = readInt;
            } else {
                jArr[i2] = parcel.readInt() | ((~readInt) << 32);
            }
            i2++;
        }
        while (i2 < length) {
            jArr[i2] = 0;
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logOrThrow(String str) {
        logOrThrow(str, new RuntimeException("Stack trace"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logOrThrow(String str, Throwable th) {
        Slog.e(TAG, str, th);
        if (Build.IS_ENG) {
            throw new RuntimeException(str, th);
        }
    }
}
