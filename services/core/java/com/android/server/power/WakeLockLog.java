package com.android.server.power;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.text.TextUtils;
import android.util.SparseArray;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WakeLockLog {
    public final Context mContext;
    public final SimpleDateFormat mDumpsysDateFormat;
    public final Injector mInjector;
    public final Object mLock = new Object();
    public final TheLog mLog;
    public final TagDatabase mTagDatabase;
    public static final String[] LEVEL_TO_STRING = {
        "override", "partial", "full", "screen-dim", "screen-bright", "prox", "doze", "draw"
    };
    public static final String[] REDUCED_TAG_PREFIXES = {"*job*/", "*gms_scheduler*/", "IntentOp:"};
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LogEntry {
        public int flags;
        public String packageName;
        public TagData tag;
        public long time;
        public int type;

        public LogEntry(long j, int i, TagData tagData, int i2) {
            this.time = j;
            this.type = i;
            this.tag = tagData;
            this.flags = i2;
        }

        public final String toString() {
            return toStringInternal(WakeLockLog.DATE_FORMAT);
        }

        public final String toStringInternal(SimpleDateFormat simpleDateFormat) {
            StringBuilder sb = new StringBuilder();
            if (this.type == 0) {
                return simpleDateFormat.format(new Date(this.time)) + " - RESET";
            }
            sb.append(simpleDateFormat.format(new Date(this.time)));
            sb.append(" - ");
            TagData tagData = this.tag;
            sb.append(tagData == null ? "---" : Integer.valueOf(tagData.ownerUid));
            if (this.packageName != null) {
                sb.append(" (");
                sb.append(this.packageName);
                sb.append(")");
            }
            sb.append(" - ");
            sb.append(this.type == 1 ? "ACQ" : "REL");
            sb.append(" ");
            TagData tagData2 = this.tag;
            sb.append(tagData2 == null ? "UNKNOWN" : tagData2.tag);
            if (this.type == 1) {
                sb.append(" (");
                sb.append(WakeLockLog.LEVEL_TO_STRING[this.flags & 7]);
                if ((this.flags & 8) == 8) {
                    sb.append(",on-after-release");
                }
                if ((this.flags & 16) == 16) {
                    sb.append(",acq-causes-wake");
                }
                if ((this.flags & 32) == 32) {
                    sb.append(",system-wakelock");
                }
                sb.append(")");
            }
            return sb.toString();
        }

        public final void updatePackageName(
                SparseArray sparseArray, PackageManager packageManager) {
            String[] strArr;
            TagData tagData = this.tag;
            if (tagData == null) {
                return;
            }
            if (sparseArray.contains(tagData.ownerUid)) {
                strArr = (String[]) sparseArray.get(this.tag.ownerUid);
            } else {
                String[] packagesForUid = packageManager.getPackagesForUid(this.tag.ownerUid);
                sparseArray.put(this.tag.ownerUid, packagesForUid);
                strArr = packagesForUid;
            }
            if (strArr == null || strArr.length <= 0) {
                return;
            }
            this.packageName = strArr[0];
            if (strArr.length > 1) {
                this.packageName =
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                new StringBuilder(), this.packageName, ",...");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TagData {
        public int index;
        public long lastUsedTime;
        public int ownerUid;
        public String tag;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TagData)) {
                return false;
            }
            TagData tagData = (TagData) obj;
            return TextUtils.equals(this.tag, tagData.tag) && this.ownerUid == tagData.ownerUid;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[");
            sb.append(this.ownerUid);
            sb.append(" ; ");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.tag, "]");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TagDatabase {
        public final TagData[] mArray;
        public TheLog.AnonymousClass1 mCallback;
        public final int mInvalidIndex;

        public TagDatabase(Injector injector) {
            injector.getClass();
            int min = Math.min(128, 128) - 1;
            this.mArray = new TagData[min];
            this.mInvalidIndex = min;
        }

        public final TagData findOrCreateTag(int i, String str) {
            int i2;
            TheLog.AnonymousClass1 anonymousClass1;
            TagData tagData = new TagData();
            tagData.tag = str;
            tagData.ownerUid = i;
            int i3 = -1;
            int i4 = -1;
            TagData tagData2 = null;
            int i5 = 0;
            while (true) {
                TagData[] tagDataArr = this.mArray;
                if (i5 >= tagDataArr.length) {
                    if (i3 != -1 || (anonymousClass1 = this.mCallback) == null) {
                        i2 = -1;
                    } else {
                        TheLog theLog = (TheLog) anonymousClass1.this$0;
                        int i6 = theLog.mStart;
                        if (i6 != theLog.mEnd) {
                            long j = theLog.mStartTime;
                            LogEntry logEntry = new LogEntry();
                            while (true) {
                                if (i6 == theLog.mEnd) {
                                    break;
                                }
                                LogEntry readEntryAt = theLog.readEntryAt(i6, j, logEntry);
                                if (readEntryAt == null) {
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            i6,
                                            "Entry is unreadable - Unexpected @ ",
                                            "PowerManagerService.WLLog");
                                    break;
                                }
                                TagData tagData3 = readEntryAt.tag;
                                byte[] bArr = theLog.mBuffer;
                                TheLog.AnonymousClass1 anonymousClass12 = theLog.mTranslator;
                                if (tagData3 != null && tagData3.index == i4) {
                                    readEntryAt.tag = null;
                                    byte[] bArr2 = theLog.mReadWriteTempBuffer;
                                    int bytes = anonymousClass12.toBytes(readEntryAt, bArr2, j);
                                    if (bytes > 0) {
                                        for (int i7 = 0; i7 < bytes; i7++) {
                                            bArr[(i6 + i7) % bArr.length] = bArr2[i7];
                                        }
                                    }
                                }
                                long j2 = readEntryAt.time;
                                i6 =
                                        (anonymousClass12.toBytes(readEntryAt, null, 0L) + i6)
                                                % bArr.length;
                                j = j2;
                            }
                        }
                        i2 = -1;
                    }
                    if (i3 == i2) {
                        i3 = i4;
                    }
                    if (i3 >= 0 && i3 < tagDataArr.length) {
                        TagData tagData4 = tagDataArr[i3];
                        if (tagData4 != null) {
                            tagData4.index = this.mInvalidIndex;
                        }
                        tagDataArr[i3] = tagData;
                        tagData.index = i3;
                    }
                    return tagData;
                }
                TagData tagData5 = tagDataArr[i5];
                if (tagData.equals(tagData5)) {
                    return tagData5;
                }
                if (tagData5 != null) {
                    if (tagData2 == null || tagData5.lastUsedTime < tagData2.lastUsedTime) {
                        i4 = i5;
                        tagData2 = tagData5;
                    }
                } else if (i3 == -1) {
                    i3 = i5;
                }
                i5++;
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Tag Database: size(");
            TagData[] tagDataArr = this.mArray;
            sb.append(tagDataArr.length);
            sb.append(")");
            int i = 0;
            int i2 = 0;
            for (TagData tagData : tagDataArr) {
                i2 += 8;
                if (tagData != null) {
                    i++;
                    String str = tagData.tag;
                    int length = (str == null ? 0 : str.length() * 2) + 24 + i2;
                    if (str != null) {
                        str.length();
                    }
                    i2 = length;
                }
            }
            sb.append(", entries: ");
            sb.append(i);
            sb.append(", Bytes used: ");
            sb.append(i2);
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TheLog {
        public final byte[] mBuffer;
        public final List mSavedAcquisitions;
        public final AnonymousClass1 mTranslator;
        public final byte[] mTempBuffer = new byte[9];
        public final byte[] mReadWriteTempBuffer = new byte[9];
        public int mStart = 0;
        public int mEnd = 0;
        public long mStartTime = 0;
        public long mLatestTime = 0;
        public long mChangeCount = 0;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.power.WakeLockLog$TheLog$1, reason: invalid class name */
        public final class AnonymousClass1 {
            public final Object this$0;

            public /* synthetic */ AnonymousClass1(Object obj) {
                this.this$0 = obj;
            }

            public int toBytes(LogEntry logEntry, byte[] bArr, long j) {
                int i;
                int i2;
                int i3 = logEntry.type;
                if (i3 == 0) {
                    long j2 = logEntry.time;
                    if (bArr != null && bArr.length >= 9) {
                        bArr[0] = 0;
                        bArr[1] = (byte) ((j2 >> 56) & 255);
                        bArr[2] = (byte) ((j2 >> 48) & 255);
                        bArr[3] = (byte) ((j2 >> 40) & 255);
                        bArr[4] = (byte) ((j2 >> 32) & 255);
                        bArr[5] = (byte) ((j2 >> 24) & 255);
                        bArr[6] = (byte) ((j2 >> 16) & 255);
                        bArr[7] = (byte) ((j2 >> 8) & 255);
                        bArr[8] = (byte) (j2 & 255);
                    }
                    return 9;
                }
                int i4 = -2;
                TagDatabase tagDatabase = (TagDatabase) this.this$0;
                if (i3 == 1) {
                    if (bArr == null || bArr.length < 3) {
                        return 3;
                    }
                    long j3 = logEntry.time;
                    if (j3 < j) {
                        i4 = -1;
                    } else {
                        long j4 = j3 - j;
                        if (j4 <= 255) {
                            i4 = (int) j4;
                        }
                    }
                    if (i4 < 0) {
                        return i4;
                    }
                    bArr[0] = (byte) ((logEntry.flags & 63) | 64);
                    TagData tagData = logEntry.tag;
                    if (tagData == null) {
                        i = tagDatabase.mInvalidIndex;
                    } else {
                        tagDatabase.getClass();
                        i = tagData.index;
                    }
                    bArr[1] = (byte) i;
                    bArr[2] =
                            (byte)
                                    (i4
                                            & IDnsResolverUnsolicitedEventListener
                                                    .DNS_HEALTH_RESULT_TIMEOUT);
                    return 3;
                }
                if (i3 != 2) {
                    throw new RuntimeException("Unknown type " + logEntry);
                }
                if (bArr != null && bArr.length >= 2) {
                    long j5 = logEntry.time;
                    if (j5 < j) {
                        i4 = -1;
                    } else {
                        long j6 = j5 - j;
                        if (j6 <= 255) {
                            i4 = (int) j6;
                        }
                    }
                    if (i4 < 0) {
                        return i4;
                    }
                    TagData tagData2 = logEntry.tag;
                    if (tagData2 == null) {
                        i2 = tagDatabase.mInvalidIndex;
                    } else {
                        tagDatabase.getClass();
                        i2 = tagData2.index;
                    }
                    bArr[0] = (byte) (i2 | 128);
                    bArr[1] =
                            (byte)
                                    (i4
                                            & IDnsResolverUnsolicitedEventListener
                                                    .DNS_HEALTH_RESULT_TIMEOUT);
                }
                return 2;
            }
        }

        public TheLog(Injector injector, AnonymousClass1 anonymousClass1, TagDatabase tagDatabase) {
            injector.getClass();
            this.mBuffer = new byte[Math.max(10240, 10)];
            this.mTranslator = anonymousClass1;
            tagDatabase.mCallback = new AnonymousClass1(this);
            this.mSavedAcquisitions = new ArrayList();
        }

        public final void addEntry(LogEntry logEntry) {
            int i;
            int i2;
            if (this.mStart == this.mEnd) {
                long j = logEntry.time;
                this.mLatestTime = j;
                this.mStartTime = j;
            }
            long j2 = this.mLatestTime;
            AnonymousClass1 anonymousClass1 = this.mTranslator;
            byte[] bArr = this.mTempBuffer;
            int bytes = anonymousClass1.toBytes(logEntry, bArr, j2);
            if (bytes == -1) {
                return;
            }
            if (bytes == -2) {
                addEntry(new LogEntry(logEntry.time, 0, null, 0));
                bytes = anonymousClass1.toBytes(logEntry, bArr, this.mLatestTime);
            }
            if (bytes > 9 || bytes <= 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        bytes,
                        "Log entry size is out of expected range: ",
                        "PowerManagerService.WLLog");
                return;
            }
            byte[] bArr2 = this.mBuffer;
            int i3 = bytes + 1;
            if (bArr2.length < i3) {
                return;
            }
            while (true) {
                i = this.mEnd;
                int i4 = this.mStart;
                i2 = 0;
                if ((i > i4 ? bArr2.length - (i - i4) : i < i4 ? i4 - i : bArr2.length) >= i3) {
                    break;
                }
                if (i4 != i) {
                    LogEntry readEntryAt = readEntryAt(i4, this.mStartTime, null);
                    int i5 = readEntryAt.type;
                    if (i5 == 1) {
                        ((ArrayList) this.mSavedAcquisitions).add(readEntryAt);
                    } else if (i5 == 2) {
                        while (true) {
                            if (i2 >= ((ArrayList) this.mSavedAcquisitions).size()) {
                                break;
                            }
                            if (Objects.equals(
                                    ((LogEntry) ((ArrayList) this.mSavedAcquisitions).get(i2)).tag,
                                    readEntryAt.tag)) {
                                ((ArrayList) this.mSavedAcquisitions).remove(i2);
                                break;
                            }
                            i2++;
                        }
                    }
                    this.mStart =
                            (this.mStart
                                            + anonymousClass1.toBytes(
                                                    readEntryAt, null, this.mStartTime))
                                    % bArr2.length;
                    this.mStartTime = readEntryAt.time;
                    this.mChangeCount++;
                }
            }
            while (i2 < bytes) {
                bArr2[(i + i2) % bArr2.length] = bArr[i2];
                i2++;
            }
            this.mEnd = (this.mEnd + bytes) % bArr2.length;
            long j3 = logEntry.time;
            this.mLatestTime = j3;
            TagData tagData = logEntry.tag;
            if (tagData != null) {
                tagData.lastUsedTime = j3;
            }
            this.mChangeCount++;
        }

        public final LogEntry readEntryAt(int i, long j, LogEntry logEntry) {
            byte[] bArr;
            int i2 = 0;
            while (true) {
                bArr = this.mReadWriteTempBuffer;
                if (i2 >= 9) {
                    break;
                }
                byte[] bArr2 = this.mBuffer;
                int length = (i + i2) % bArr2.length;
                if (length == this.mEnd) {
                    break;
                }
                bArr[i2] = bArr2[length];
                i2++;
            }
            AnonymousClass1 anonymousClass1 = this.mTranslator;
            anonymousClass1.getClass();
            TagData tagData = null;
            if (bArr == null || bArr.length == 0) {
                return null;
            }
            if (logEntry == null) {
                logEntry = new LogEntry();
            }
            byte b = bArr[0];
            int i3 = b >> 6;
            int i4 = i3 & 3;
            if ((i3 & 2) == 2) {
                i4 = 2;
            }
            if (i4 != 0) {
                TagDatabase tagDatabase = (TagDatabase) anonymousClass1.this$0;
                if (i4 != 1) {
                    if (i4 != 2) {
                        android.util.Slog.w(
                                "PowerManagerService.WLLog",
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        i4, "Type not recognized [", "]"),
                                new Exception());
                        return null;
                    }
                    if (bArr.length < 2) {
                        return null;
                    }
                    int i5 = b & Byte.MAX_VALUE;
                    if (i5 >= 0) {
                        TagData[] tagDataArr = tagDatabase.mArray;
                        if (i5 < tagDataArr.length && i5 != tagDatabase.mInvalidIndex) {
                            tagData = tagDataArr[i5];
                        }
                    } else {
                        tagDatabase.getClass();
                    }
                    logEntry.time = (bArr[1] & 255) + j;
                    logEntry.type = 2;
                    logEntry.tag = tagData;
                    logEntry.flags = 0;
                } else {
                    if (bArr.length < 3) {
                        return null;
                    }
                    int i6 = b & 63;
                    int i7 = bArr[1] & Byte.MAX_VALUE;
                    if (i7 >= 0) {
                        TagData[] tagDataArr2 = tagDatabase.mArray;
                        if (i7 < tagDataArr2.length && i7 != tagDatabase.mInvalidIndex) {
                            tagData = tagDataArr2[i7];
                        }
                    } else {
                        tagDatabase.getClass();
                    }
                    logEntry.time = (bArr[2] & 255) + j;
                    logEntry.type = 1;
                    logEntry.tag = tagData;
                    logEntry.flags = i6;
                }
            } else {
                if (bArr.length < 9) {
                    return null;
                }
                logEntry.time =
                        ((bArr[1] & 255) << 56)
                                | ((bArr[2] & 255) << 48)
                                | ((bArr[3] & 255) << 40)
                                | ((bArr[4] & 255) << 32)
                                | ((bArr[5] & 255) << 24)
                                | ((bArr[6] & 255) << 16)
                                | ((bArr[7] & 255) << 8)
                                | (bArr[8] & 255);
                logEntry.type = 0;
                logEntry.tag = null;
                logEntry.flags = 0;
            }
            return logEntry;
        }
    }

    public WakeLockLog(Injector injector, Context context) {
        this.mInjector = injector;
        TagDatabase tagDatabase = new TagDatabase(injector);
        this.mTagDatabase = tagDatabase;
        this.mLog = new TheLog(injector, new TheLog.AnonymousClass1(tagDatabase), tagDatabase);
        this.mDumpsysDateFormat = DATE_FORMAT;
        this.mContext = context;
    }

    public void dump(PrintWriter printWriter, boolean z) {
        try {
            synchronized (this.mLock) {
                try {
                    printWriter.println("Wake Lock Log");
                    SparseArray sparseArray = new SparseArray();
                    int i = 0;
                    int i2 = 0;
                    for (int i3 = 0; i3 < ((ArrayList) this.mLog.mSavedAcquisitions).size(); i3++) {
                        i2++;
                        LogEntry logEntry =
                                (LogEntry) ((ArrayList) this.mLog.mSavedAcquisitions).get(i3);
                        logEntry.updatePackageName(sparseArray, this.mContext.getPackageManager());
                        printWriter.println(
                                "  " + logEntry.toStringInternal(this.mDumpsysDateFormat));
                    }
                    LogEntry logEntry2 = new LogEntry();
                    TheLog theLog = this.mLog;
                    theLog.getClass();
                    Iterator it =
                            new Iterator(
                                    logEntry2) { // from class:
                                                 // com.android.server.power.WakeLockLog.TheLog.2
                                public final long mChangeValue;
                                public int mCurrent;
                                public long mCurrentTimeReference;
                                public final /* synthetic */ LogEntry val$tempEntry;

                                {
                                    this.val$tempEntry = logEntry2;
                                    this.mCurrent = TheLog.this.mStart;
                                    this.mCurrentTimeReference = TheLog.this.mStartTime;
                                    this.mChangeValue = TheLog.this.mChangeCount;
                                }

                                public final void checkState() {
                                    if (this.mChangeValue == TheLog.this.mChangeCount) {
                                        return;
                                    }
                                    throw new ConcurrentModificationException(
                                            "Buffer modified, old change: "
                                                    + this.mChangeValue
                                                    + ", new change: "
                                                    + TheLog.this.mChangeCount);
                                }

                                @Override // java.util.Iterator
                                public final boolean hasNext() {
                                    checkState();
                                    return this.mCurrent != TheLog.this.mEnd;
                                }

                                @Override // java.util.Iterator
                                public final Object next() {
                                    checkState();
                                    if (!hasNext()) {
                                        throw new NoSuchElementException("No more entries left.");
                                    }
                                    LogEntry readEntryAt =
                                            TheLog.this.readEntryAt(
                                                    this.mCurrent,
                                                    this.mCurrentTimeReference,
                                                    this.val$tempEntry);
                                    TheLog theLog2 = TheLog.this;
                                    this.mCurrent =
                                            (this.mCurrent
                                                            + theLog2.mTranslator.toBytes(
                                                                    readEntryAt,
                                                                    null,
                                                                    theLog2.mStartTime))
                                                    % TheLog.this.mBuffer.length;
                                    this.mCurrentTimeReference = readEntryAt.time;
                                    return readEntryAt;
                                }

                                public final String toString() {
                                    return "@" + this.mCurrent;
                                }
                            };
                    while (it.hasNext()) {
                        LogEntry logEntry3 = (LogEntry) it.next();
                        if (logEntry3.type == 0) {
                            i++;
                        } else {
                            i2++;
                            logEntry3.updatePackageName(
                                    sparseArray, this.mContext.getPackageManager());
                            printWriter.println(
                                    "  " + logEntry3.toStringInternal(this.mDumpsysDateFormat));
                        }
                    }
                    printWriter.println("  -");
                    printWriter.println("  Events: " + i2 + ", Time-Resets: " + i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("  Buffer, Bytes used: ");
                    TheLog theLog2 = this.mLog;
                    byte[] bArr = theLog2.mBuffer;
                    int length = bArr.length;
                    int i4 = theLog2.mEnd;
                    int i5 = theLog2.mStart;
                    sb.append(
                            length
                                    - (i4 > i5
                                            ? bArr.length - (i4 - i5)
                                            : i4 < i5 ? i5 - i4 : bArr.length));
                    printWriter.println(sb.toString());
                    if (z) {
                        printWriter.println("  " + this.mTagDatabase);
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            printWriter.println("Exception dumping wake-lock log: " + e.toString());
        }
    }

    public final void onWakeLockEvent(int i, int i2, String str, int i3, long j) {
        int i4;
        String str2;
        int i5;
        if (str == null) {
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i2,
                            "Insufficient data to log wakelock [tag: ",
                            str,
                            ", ownerUid: ",
                            ", flags: 0x");
            m.append(Integer.toHexString(i3));
            android.util.Slog.w("PowerManagerService.WLLog", m.toString());
            return;
        }
        if (j == -1) {
            this.mInjector.getClass();
            j = System.currentTimeMillis();
        }
        long j2 = j;
        if (i == 1) {
            int i6 = 65535 & i3;
            if (i6 != 1) {
                i5 = 6;
                if (i6 == 6) {
                    i5 = 3;
                } else if (i6 == 10) {
                    i5 = 4;
                } else if (i6 == 26) {
                    i5 = 2;
                } else if (i6 == 32) {
                    i5 = 5;
                } else if (i6 != 64) {
                    if (i6 != 128) {
                        if (i6 != 256) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    i3,
                                    "Unsupported lock level for logging, flags: ",
                                    "PowerManagerService.WLLog");
                        }
                        i5 = 0;
                    } else {
                        i5 = 7;
                    }
                }
            } else {
                i5 = 1;
            }
            if ((268435456 & i3) != 0) {
                i5 |= 16;
            }
            if ((536870912 & i3) != 0) {
                i5 |= 8;
            }
            if ((i3 & Integer.MIN_VALUE) != 0) {
                i5 |= 32;
            }
            i4 = i5;
        } else {
            i4 = 0;
        }
        String[] strArr = REDUCED_TAG_PREFIXES;
        int i7 = 0;
        while (true) {
            if (i7 >= 3) {
                str2 = null;
                break;
            }
            str2 = strArr[i7];
            if (str.startsWith(str2)) {
                break;
            } else {
                i7++;
            }
        }
        if (str2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append((CharSequence) str, 0, str2.length());
            int max = Math.max(str.lastIndexOf("/"), str.lastIndexOf("."));
            int length = sb.length();
            boolean z = true;
            while (length < max) {
                char charAt = str.charAt(length);
                boolean z2 = charAt == '.' || charAt == '/';
                if (z2 || z) {
                    sb.append(charAt);
                }
                length++;
                z = z2;
            }
            sb.append(str.substring(length));
            str = sb.toString();
        }
        synchronized (this.mLock) {
            this.mLog.addEntry(new LogEntry(j2, i, this.mTagDatabase.findOrCreateTag(i2, str), i4));
        }
    }
}
