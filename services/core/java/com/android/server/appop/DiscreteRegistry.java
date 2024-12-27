package com.android.server.appop;

import android.app.AppOpsManager;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DiscreteRegistry {
    public static int sDiscreteFlags;
    public static long sDiscreteHistoryCutoff;
    public static long sDiscreteHistoryQuantization;
    public static int[] sDiscreteOps;
    public DiscreteOps mCachedOps;
    public boolean mDebugMode;
    public final File mDiscreteAccessDir;
    public DiscreteOps mDiscreteOps;
    public final Object mInMemoryLock;
    public final Object mOnDiskLock;
    public static final long DEFAULT_DISCRETE_HISTORY_CUTOFF = Duration.ofDays(7).toMillis();
    public static final long MAXIMUM_DISCRETE_HISTORY_CUTOFF = Duration.ofDays(30).toMillis();
    public static final long DEFAULT_DISCRETE_HISTORY_QUANTIZATION =
            Duration.ofMinutes(1).toMillis();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AttributionChain {
        public ArrayList mChain;
        public Set mExemptPkgs;
        public OpEvent mLastVisibleEvent;
        public OpEvent mStartEvent;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class OpEvent {
            public String mAttributionTag;
            public int mOpCode;
            public DiscreteOpEvent mOpEvent;
            public String mPkgName;
            public int mUid;
        }

        public final OpEvent getStart() {
            OpEvent opEvent;
            if (this.mChain.isEmpty()
                    || (opEvent = (OpEvent) this.mChain.get(0)) == null
                    || (opEvent.mOpEvent.mAttributionFlags & 4) == 0) {
                return null;
            }
            return (OpEvent) this.mChain.get(0);
        }

        public final boolean isComplete() {
            if (!this.mChain.isEmpty() && getStart() != null) {
                ArrayList arrayList = this.mChain;
                OpEvent opEvent = (OpEvent) arrayList.get(arrayList.size() - 1);
                if (opEvent != null && (opEvent.mOpEvent.mAttributionFlags & 1) != 0) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isStart(
                String str, int i, String str2, int i2, DiscreteOpEvent discreteOpEvent) {
            OpEvent opEvent = this.mStartEvent;
            if (opEvent == null
                    || !Objects.equals(str, opEvent.mPkgName)
                    || opEvent.mUid != i
                    || !Objects.equals(str2, opEvent.mAttributionTag)
                    || opEvent.mOpCode != i2) {
                return false;
            }
            DiscreteOpEvent discreteOpEvent2 = opEvent.mOpEvent;
            return discreteOpEvent2.mAttributionChainId == discreteOpEvent.mAttributionChainId
                    && discreteOpEvent2.mAttributionFlags == discreteOpEvent.mAttributionFlags
                    && discreteOpEvent2.mNoteTime == discreteOpEvent.mNoteTime;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DiscreteOp {
        public ArrayMap mAttributedOps = new ArrayMap();
        public final /* synthetic */ DiscreteRegistry this$0;

        public DiscreteOp(DiscreteRegistry discreteRegistry) {}

        public final List getOrCreateDiscreteOpEventsList(String str) {
            List list = (List) this.mAttributedOps.get(str);
            if (list != null) {
                return list;
            }
            ArrayList arrayList = new ArrayList();
            this.mAttributedOps.put(str, arrayList);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DiscreteOpEvent {
        public final int mAttributionChainId;
        public final int mAttributionFlags;
        public final long mNoteDuration;
        public final long mNoteTime;
        public final int mOpFlag;
        public final int mUidState;

        public DiscreteOpEvent(int i, int i2, long j, long j2, int i3, int i4) {
            this.mNoteTime = j;
            this.mNoteDuration = j2;
            this.mUidState = i;
            this.mOpFlag = i2;
            this.mAttributionFlags = i3;
            this.mAttributionChainId = i4;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DiscreteOps {
        public int mChainIdOffset;
        public int mLargestChainId;
        public ArrayMap mUids = new ArrayMap();

        /* renamed from: -$$Nest$mclearHistory, reason: not valid java name */
        public static void m240$$Nest$mclearHistory(DiscreteOps discreteOps, int i, String str) {
            if (discreteOps.mUids.containsKey(Integer.valueOf(i))) {
                ((DiscreteUidOps) discreteOps.mUids.get(Integer.valueOf(i))).mPackages.remove(str);
                if (((DiscreteUidOps) discreteOps.mUids.get(Integer.valueOf(i)))
                        .mPackages.isEmpty()) {
                    discreteOps.mUids.remove(Integer.valueOf(i));
                }
            }
        }

        /* renamed from: -$$Nest$mfilter, reason: not valid java name */
        public static void m241$$Nest$mfilter(
                DiscreteOps discreteOps,
                long j,
                long j2,
                int i,
                int i2,
                String str,
                String[] strArr,
                String str2,
                int i3,
                ArrayMap arrayMap) {
            int i4;
            int i5;
            int i6;
            String str3;
            String str4;
            int i7;
            String str5;
            DiscreteOp discreteOp;
            int i8;
            String str6;
            int i9;
            DiscreteOps discreteOps2 = discreteOps;
            String str7 = str;
            String str8 = str2;
            if ((i & 1) != 0) {
                ArrayMap arrayMap2 = new ArrayMap();
                arrayMap2.put(Integer.valueOf(i2), discreteOps2.getOrCreateDiscreteUidOps(i2));
                discreteOps2.mUids = arrayMap2;
            }
            int size = discreteOps2.mUids.size() - 1;
            while (size >= 0) {
                DiscreteUidOps discreteUidOps = (DiscreteUidOps) discreteOps2.mUids.valueAt(size);
                int intValue = ((Integer) discreteOps2.mUids.keyAt(size)).intValue();
                if ((i & 2) != 0) {
                    discreteUidOps.getClass();
                    ArrayMap arrayMap3 = new ArrayMap();
                    arrayMap3.put(str7, discreteUidOps.getOrCreateDiscretePackageOps(str7));
                    discreteUidOps.mPackages = arrayMap3;
                }
                int size2 = discreteUidOps.mPackages.size() - 1;
                while (size2 >= 0) {
                    DiscretePackageOps discretePackageOps =
                            (DiscretePackageOps) discreteUidOps.mPackages.valueAt(size2);
                    String str9 = (String) discreteUidOps.mPackages.keyAt(size2);
                    int size3 = discretePackageOps.mPackageOps.size() - 1;
                    while (size3 >= 0) {
                        int intValue2 =
                                ((Integer) discretePackageOps.mPackageOps.keyAt(size3)).intValue();
                        if ((i & 8) != 0
                                && !ArrayUtils.contains(
                                        strArr, AppOpsManager.opToPublicName(intValue2))) {
                            discretePackageOps.mPackageOps.removeAt(size3);
                            i4 = size;
                            i5 = intValue;
                            i6 = size3;
                            str3 = str9;
                            size3 = i6 - 1;
                            size = i4;
                            str8 = str2;
                            str9 = str3;
                            intValue = i5;
                        }
                        DiscreteOp discreteOp2 =
                                (DiscreteOp) discretePackageOps.mPackageOps.valueAt(size3);
                        int intValue3 =
                                ((Integer) discretePackageOps.mPackageOps.keyAt(size3)).intValue();
                        if ((i & 4) != 0) {
                            discreteOp2.getClass();
                            ArrayMap arrayMap4 = new ArrayMap();
                            arrayMap4.put(str8, discreteOp2.getOrCreateDiscreteOpEventsList(str8));
                            discreteOp2.mAttributedOps = arrayMap4;
                        }
                        int size4 = discreteOp2.mAttributedOps.size() - 1;
                        while (size4 >= 0) {
                            String str10 = (String) discreteOp2.mAttributedOps.keyAt(size4);
                            List list = (List) discreteOp2.mAttributedOps.valueAt(size4);
                            String str11 = (String) discreteOp2.mAttributedOps.keyAt(size4);
                            int[] iArr = DiscreteRegistry.sDiscreteOps;
                            int size5 = list.size();
                            ArrayList arrayList = new ArrayList(size5);
                            int i10 = size;
                            int i11 = 0;
                            while (i11 < size5) {
                                int i12 = size5;
                                DiscreteOpEvent discreteOpEvent = (DiscreteOpEvent) list.get(i11);
                                List list2 = list;
                                AttributionChain attributionChain =
                                        (AttributionChain)
                                                arrayMap.get(
                                                        Integer.valueOf(
                                                                discreteOpEvent
                                                                        .mAttributionChainId));
                                if (attributionChain != null) {
                                    str5 = str10;
                                    String str12 = str9;
                                    str6 = str9;
                                    i9 = size4;
                                    int i13 = intValue;
                                    i7 = intValue;
                                    discreteOp = discreteOp2;
                                    String str13 = str11;
                                    str4 = str11;
                                    i8 = size3;
                                    if (!attributionChain.isStart(
                                                    str12, i13, str13, intValue3, discreteOpEvent)
                                            && attributionChain.isComplete()
                                            && discreteOpEvent.mAttributionChainId != -1) {
                                        i11++;
                                        str10 = str5;
                                        size3 = i8;
                                        discreteOp2 = discreteOp;
                                        size4 = i9;
                                        list = list2;
                                        size5 = i12;
                                        str9 = str6;
                                        intValue = i7;
                                        str11 = str4;
                                    }
                                } else {
                                    str4 = str11;
                                    i7 = intValue;
                                    str5 = str10;
                                    discreteOp = discreteOp2;
                                    i8 = size3;
                                    str6 = str9;
                                    i9 = size4;
                                }
                                if ((discreteOpEvent.mOpFlag & i3) != 0) {
                                    long j3 = discreteOpEvent.mNoteDuration;
                                    long j4 = discreteOpEvent.mNoteTime;
                                    if (j3 + j4 > j && j4 < j2) {
                                        arrayList.add(discreteOpEvent);
                                    }
                                }
                                i11++;
                                str10 = str5;
                                size3 = i8;
                                discreteOp2 = discreteOp;
                                size4 = i9;
                                list = list2;
                                size5 = i12;
                                str9 = str6;
                                intValue = i7;
                                str11 = str4;
                            }
                            int i14 = intValue;
                            DiscreteOp discreteOp3 = discreteOp2;
                            int i15 = size3;
                            String str14 = str9;
                            int i16 = size4;
                            discreteOp3.mAttributedOps.put(str10, arrayList);
                            if (arrayList.size() == 0) {
                                discreteOp3.mAttributedOps.removeAt(i16);
                            }
                            size4 = i16 - 1;
                            size = i10;
                            size3 = i15;
                            discreteOp2 = discreteOp3;
                            str9 = str14;
                            intValue = i14;
                        }
                        i4 = size;
                        i5 = intValue;
                        i6 = size3;
                        str3 = str9;
                        if (((DiscreteOp) discretePackageOps.mPackageOps.valueAt(i6))
                                .mAttributedOps.isEmpty()) {
                            discretePackageOps.mPackageOps.removeAt(i6);
                        }
                        size3 = i6 - 1;
                        size = i4;
                        str8 = str2;
                        str9 = str3;
                        intValue = i5;
                    }
                    int i17 = size;
                    int i18 = intValue;
                    if (((DiscretePackageOps) discreteUidOps.mPackages.valueAt(size2))
                            .mPackageOps.isEmpty()) {
                        discreteUidOps.mPackages.removeAt(size2);
                    }
                    size2--;
                    discreteOps2 = discreteOps;
                    size = i17;
                    str8 = str2;
                    intValue = i18;
                }
                if (((DiscreteUidOps) discreteOps2.mUids.valueAt(size)).mPackages.isEmpty()) {
                    discreteOps2.mUids.removeAt(size);
                }
                size--;
                str7 = str;
                str8 = str2;
            }
        }

        /* renamed from: -$$Nest$moffsetHistory, reason: not valid java name */
        public static void m242$$Nest$moffsetHistory(DiscreteOps discreteOps, long j) {
            DiscreteOps discreteOps2 = discreteOps;
            int size = discreteOps2.mUids.size();
            int i = 0;
            while (i < size) {
                DiscreteUidOps discreteUidOps = (DiscreteUidOps) discreteOps2.mUids.valueAt(i);
                int size2 = discreteUidOps.mPackages.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    DiscretePackageOps discretePackageOps =
                            (DiscretePackageOps) discreteUidOps.mPackages.valueAt(i2);
                    int size3 = discretePackageOps.mPackageOps.size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        DiscreteOp discreteOp =
                                (DiscreteOp) discretePackageOps.mPackageOps.valueAt(i3);
                        int size4 = discreteOp.mAttributedOps.size();
                        for (int i4 = 0; i4 < size4; i4++) {
                            List list = (List) discreteOp.mAttributedOps.valueAt(i4);
                            int size5 = list.size();
                            int i5 = 0;
                            while (i5 < size5) {
                                DiscreteOpEvent discreteOpEvent = (DiscreteOpEvent) list.get(i5);
                                DiscreteUidOps discreteUidOps2 = discreteUidOps;
                                int i6 = size2;
                                long j2 = discreteOpEvent.mNoteTime - j;
                                int i7 = discreteOpEvent.mAttributionFlags;
                                int i8 = discreteOpEvent.mAttributionChainId;
                                DiscretePackageOps discretePackageOps2 = discretePackageOps;
                                list.set(
                                        i5,
                                        new DiscreteOpEvent(
                                                discreteOpEvent.mUidState,
                                                discreteOpEvent.mOpFlag,
                                                j2,
                                                discreteOpEvent.mNoteDuration,
                                                i7,
                                                i8));
                                i5++;
                                discreteUidOps = discreteUidOps2;
                                size2 = i6;
                                discretePackageOps = discretePackageOps2;
                                size3 = size3;
                                size = size;
                            }
                        }
                    }
                }
                i++;
                discreteOps2 = discreteOps;
            }
        }

        /* renamed from: -$$Nest$mreadFromFile, reason: not valid java name */
        public static void m243$$Nest$mreadFromFile(DiscreteOps discreteOps, File file, long j) {
            TypedXmlPullParser resolvePullParser;
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    XmlUtils.beginDocument(resolvePullParser, "h");
                } finally {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                }
                if (resolvePullParser.getAttributeInt((String) null, "v") != 1) {
                    throw new IllegalStateException(
                            "Dropping unsupported discrete history " + file);
                }
                int depth = resolvePullParser.getDepth();
                while (XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                    if ("u".equals(resolvePullParser.getName())) {
                        discreteOps
                                .getOrCreateDiscreteUidOps(
                                        resolvePullParser.getAttributeInt((String) null, "ui", -1))
                                .deserialize(resolvePullParser, j);
                    }
                }
                fileInputStream.close();
            } catch (FileNotFoundException | IOException unused2) {
            }
        }

        /* renamed from: -$$Nest$mwriteToStream, reason: not valid java name */
        public static void m244$$Nest$mwriteToStream(
                DiscreteOps discreteOps, FileOutputStream fileOutputStream) {
            DiscreteUidOps discreteUidOps;
            int i;
            String str;
            DiscreteOps discreteOps2 = discreteOps;
            discreteOps.getClass();
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
            String str2 = null;
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            String str3 = "h";
            resolveSerializer.startTag((String) null, "h");
            resolveSerializer.attributeInt((String) null, "v", 1);
            resolveSerializer.attributeInt((String) null, "lc", discreteOps2.mLargestChainId);
            int size = discreteOps2.mUids.size();
            int i2 = 0;
            while (i2 < size) {
                resolveSerializer.startTag(str2, "u");
                resolveSerializer.attributeInt(
                        str2, "ui", ((Integer) discreteOps2.mUids.keyAt(i2)).intValue());
                DiscreteUidOps discreteUidOps2 = (DiscreteUidOps) discreteOps2.mUids.valueAt(i2);
                int size2 = discreteUidOps2.mPackages.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    resolveSerializer.startTag(str2, KnoxAnalyticsDataConverter.PAYLOAD);
                    resolveSerializer.attribute(
                            str2, "pn", (String) discreteUidOps2.mPackages.keyAt(i3));
                    DiscretePackageOps discretePackageOps =
                            (DiscretePackageOps) discreteUidOps2.mPackages.valueAt(i3);
                    int size3 = discretePackageOps.mPackageOps.size();
                    int i4 = 0;
                    while (i4 < size3) {
                        resolveSerializer.startTag(str2, "o");
                        resolveSerializer.attributeInt(
                                str2,
                                "op",
                                ((Integer) discretePackageOps.mPackageOps.keyAt(i4)).intValue());
                        DiscreteOp discreteOp =
                                (DiscreteOp) discretePackageOps.mPackageOps.valueAt(i4);
                        int size4 = discreteOp.mAttributedOps.size();
                        int i5 = 0;
                        while (i5 < size4) {
                            int i6 = size;
                            int i7 = size4;
                            resolveSerializer.startTag((String) null, "a");
                            if (((String) discreteOp.mAttributedOps.keyAt(i5)) != null) {
                                discreteUidOps = discreteUidOps2;
                                i = size2;
                                resolveSerializer.attribute(
                                        (String) null,
                                        "at",
                                        (String) discreteOp.mAttributedOps.keyAt(i5));
                            } else {
                                discreteUidOps = discreteUidOps2;
                                i = size2;
                            }
                            List list = (List) discreteOp.mAttributedOps.valueAt(i5);
                            int size5 = list.size();
                            int i8 = 0;
                            while (i8 < size5) {
                                DiscreteOp discreteOp2 = discreteOp;
                                int i9 = size5;
                                resolveSerializer.startTag(
                                        (String) null, KnoxAnalyticsDataConverter.EVENT);
                                DiscreteOpEvent discreteOpEvent = (DiscreteOpEvent) list.get(i8);
                                DiscretePackageOps discretePackageOps2 = discretePackageOps;
                                int i10 = size3;
                                List list2 = list;
                                String str4 = str3;
                                resolveSerializer.attributeLong(
                                        (String) null, "nt", discreteOpEvent.mNoteTime);
                                int i11 = i5;
                                long j = discreteOpEvent.mNoteDuration;
                                if (j != -1) {
                                    str = null;
                                    resolveSerializer.attributeLong((String) null, "nd", j);
                                } else {
                                    str = null;
                                }
                                int i12 = discreteOpEvent.mAttributionFlags;
                                if (i12 != 0) {
                                    resolveSerializer.attributeInt(str, "af", i12);
                                }
                                int i13 = discreteOpEvent.mAttributionChainId;
                                if (i13 != -1) {
                                    resolveSerializer.attributeInt(str, "ci", i13);
                                }
                                resolveSerializer.attributeInt(
                                        str, "us", discreteOpEvent.mUidState);
                                resolveSerializer.attributeInt(
                                        str,
                                        KnoxAnalyticsDataConverter.FEATURE,
                                        discreteOpEvent.mOpFlag);
                                resolveSerializer.endTag(str, KnoxAnalyticsDataConverter.EVENT);
                                i8++;
                                i5 = i11;
                                discretePackageOps = discretePackageOps2;
                                discreteOp = discreteOp2;
                                size5 = i9;
                                size3 = i10;
                                list = list2;
                                str3 = str4;
                            }
                            resolveSerializer.endTag((String) null, "a");
                            i5++;
                            size = i6;
                            size4 = i7;
                            discreteUidOps2 = discreteUidOps;
                            size2 = i;
                            size3 = size3;
                        }
                        resolveSerializer.endTag((String) null, "o");
                        i4++;
                        str2 = null;
                        size3 = size3;
                    }
                    resolveSerializer.endTag(str2, KnoxAnalyticsDataConverter.PAYLOAD);
                }
                resolveSerializer.endTag(str2, "u");
                i2++;
                discreteOps2 = discreteOps;
            }
            resolveSerializer.endTag(str2, str3);
            resolveSerializer.endDocument();
        }

        public DiscreteOps(int i) {
            this.mChainIdOffset = i;
            this.mLargestChainId = i;
        }

        public final void addDiscreteAccess(
                int i,
                int i2,
                int i3,
                int i4,
                int i5,
                int i6,
                long j,
                long j2,
                String str,
                String str2) {
            int i7;
            if (i6 != -1) {
                int i8 = this.mChainIdOffset + i6;
                if (i8 > this.mLargestChainId) {
                    this.mLargestChainId = i8;
                } else if (i8 < 0) {
                    i8 = 0;
                    this.mLargestChainId = 0;
                    this.mChainIdOffset = i6 * (-1);
                }
                i7 = i8;
            } else {
                i7 = i6;
            }
            List orCreateDiscreteOpEventsList =
                    getOrCreateDiscreteUidOps(i2)
                            .getOrCreateDiscretePackageOps(str)
                            .getOrCreateDiscreteOp(i)
                            .getOrCreateDiscreteOpEventsList(str2);
            for (int size = orCreateDiscreteOpEventsList.size(); size > 0; size--) {
                DiscreteOpEvent discreteOpEvent =
                        (DiscreteOpEvent) orCreateDiscreteOpEventsList.get(size - 1);
                long j3 = discreteOpEvent.mNoteTime;
                long j4 = DiscreteRegistry.sDiscreteHistoryQuantization;
                if ((j3 / j4) * j4 < (j / j4) * j4) {
                    break;
                }
                if (discreteOpEvent.mOpFlag == i3 && discreteOpEvent.mUidState == i4) {
                    if (discreteOpEvent.mAttributionFlags == i5
                            && discreteOpEvent.mAttributionChainId == i7) {
                        if (DiscreteRegistry.m239$$Nest$smdiscretizeDuration(j2)
                                == DiscreteRegistry.m239$$Nest$smdiscretizeDuration(
                                        discreteOpEvent.mNoteDuration)) {
                            return;
                        }
                        orCreateDiscreteOpEventsList.add(
                                size, new DiscreteOpEvent(i4, i3, j, j2, i5, i7));
                    }
                }
            }
            orCreateDiscreteOpEventsList.add(size, new DiscreteOpEvent(i4, i3, j, j2, i5, i7));
        }

        public final DiscreteUidOps getOrCreateDiscreteUidOps(int i) {
            DiscreteUidOps discreteUidOps = (DiscreteUidOps) this.mUids.get(Integer.valueOf(i));
            if (discreteUidOps != null) {
                return discreteUidOps;
            }
            DiscreteUidOps discreteUidOps2 = DiscreteRegistry.this.new DiscreteUidOps();
            this.mUids.put(Integer.valueOf(i), discreteUidOps2);
            return discreteUidOps2;
        }

        public final void merge(DiscreteOps discreteOps) {
            DiscreteOps discreteOps2 = this;
            DiscreteOps discreteOps3 = discreteOps;
            discreteOps2.mLargestChainId =
                    Math.max(discreteOps2.mLargestChainId, discreteOps3.mLargestChainId);
            int size = discreteOps3.mUids.size();
            int i = 0;
            while (i < size) {
                int intValue = ((Integer) discreteOps3.mUids.keyAt(i)).intValue();
                DiscreteUidOps discreteUidOps = (DiscreteUidOps) discreteOps3.mUids.valueAt(i);
                DiscreteUidOps orCreateDiscreteUidOps =
                        discreteOps2.getOrCreateDiscreteUidOps(intValue);
                int size2 = discreteUidOps.mPackages.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str = (String) discreteUidOps.mPackages.keyAt(i2);
                    DiscretePackageOps discretePackageOps =
                            (DiscretePackageOps) discreteUidOps.mPackages.valueAt(i2);
                    DiscretePackageOps orCreateDiscretePackageOps =
                            orCreateDiscreteUidOps.getOrCreateDiscretePackageOps(str);
                    int size3 = discretePackageOps.mPackageOps.size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        int intValue2 =
                                ((Integer) discretePackageOps.mPackageOps.keyAt(i3)).intValue();
                        DiscreteOp discreteOp =
                                (DiscreteOp) discretePackageOps.mPackageOps.valueAt(i3);
                        DiscreteOp orCreateDiscreteOp =
                                orCreateDiscretePackageOps.getOrCreateDiscreteOp(intValue2);
                        int size4 = discreteOp.mAttributedOps.size();
                        int i4 = 0;
                        while (i4 < size4) {
                            String str2 = (String) discreteOp.mAttributedOps.keyAt(i4);
                            List list = (List) discreteOp.mAttributedOps.valueAt(i4);
                            int i5 = size;
                            List orCreateDiscreteOpEventsList =
                                    orCreateDiscreteOp.getOrCreateDiscreteOpEventsList(str2);
                            DiscreteUidOps discreteUidOps2 = orCreateDiscreteUidOps;
                            ArrayMap arrayMap = orCreateDiscreteOp.mAttributedOps;
                            int[] iArr = DiscreteRegistry.sDiscreteOps;
                            DiscreteUidOps discreteUidOps3 = discreteUidOps;
                            int size5 = orCreateDiscreteOpEventsList.size();
                            int i6 = size2;
                            int size6 = list.size();
                            DiscretePackageOps discretePackageOps2 = orCreateDiscretePackageOps;
                            DiscretePackageOps discretePackageOps3 = discretePackageOps;
                            ArrayList arrayList = new ArrayList(size5 + size6);
                            int i7 = size3;
                            int i8 = 0;
                            int i9 = 0;
                            while (true) {
                                if (i8 < size5 || i9 < size6) {
                                    if (i8 == size5) {
                                        arrayList.add((DiscreteOpEvent) list.get(i9));
                                        i9++;
                                    } else if (i9 == size6) {
                                        arrayList.add(
                                                (DiscreteOpEvent)
                                                        orCreateDiscreteOpEventsList.get(i8));
                                        i8++;
                                    } else {
                                        String str3 = str2;
                                        ArrayMap arrayMap2 = arrayMap;
                                        int i10 = size5;
                                        DiscreteOp discreteOp2 = orCreateDiscreteOp;
                                        DiscreteOp discreteOp3 = discreteOp;
                                        if (((DiscreteOpEvent) orCreateDiscreteOpEventsList.get(i8))
                                                        .mNoteTime
                                                < ((DiscreteOpEvent) list.get(i9)).mNoteTime) {
                                            arrayList.add(
                                                    (DiscreteOpEvent)
                                                            orCreateDiscreteOpEventsList.get(i8));
                                            i8++;
                                        } else {
                                            arrayList.add((DiscreteOpEvent) list.get(i9));
                                            i9++;
                                        }
                                        arrayMap = arrayMap2;
                                        str2 = str3;
                                        size5 = i10;
                                        discreteOp = discreteOp3;
                                        orCreateDiscreteOp = discreteOp2;
                                    }
                                }
                            }
                            arrayMap.put(str2, arrayList);
                            i4++;
                            size = i5;
                            orCreateDiscreteUidOps = discreteUidOps2;
                            discreteUidOps = discreteUidOps3;
                            size2 = i6;
                            orCreateDiscretePackageOps = discretePackageOps2;
                            discretePackageOps = discretePackageOps3;
                            size3 = i7;
                        }
                    }
                }
                i++;
                discreteOps2 = this;
                discreteOps3 = discreteOps;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DiscretePackageOps {
        public final ArrayMap mPackageOps = new ArrayMap();

        public DiscretePackageOps() {}

        public final DiscreteOp getOrCreateDiscreteOp(int i) {
            DiscreteOp discreteOp = (DiscreteOp) this.mPackageOps.get(Integer.valueOf(i));
            if (discreteOp != null) {
                return discreteOp;
            }
            DiscreteOp discreteOp2 = new DiscreteOp(DiscreteRegistry.this);
            this.mPackageOps.put(Integer.valueOf(i), discreteOp2);
            return discreteOp2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DiscreteUidOps {
        public ArrayMap mPackages = new ArrayMap();

        public DiscreteUidOps() {}

        public final void deserialize(TypedXmlPullParser typedXmlPullParser, long j) {
            int depth = typedXmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (KnoxAnalyticsDataConverter.PAYLOAD.equals(typedXmlPullParser.getName())) {
                    DiscretePackageOps orCreateDiscretePackageOps =
                            getOrCreateDiscretePackageOps(
                                    typedXmlPullParser.getAttributeValue((String) null, "pn"));
                    int depth2 = typedXmlPullParser.getDepth();
                    while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                        if ("o".equals(typedXmlPullParser.getName())) {
                            DiscreteOp orCreateDiscreteOp =
                                    orCreateDiscretePackageOps.getOrCreateDiscreteOp(
                                            typedXmlPullParser.getAttributeInt(
                                                    (String) null, "op"));
                            int depth3 = typedXmlPullParser.getDepth();
                            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                                if ("a".equals(typedXmlPullParser.getName())) {
                                    List orCreateDiscreteOpEventsList =
                                            orCreateDiscreteOp.getOrCreateDiscreteOpEventsList(
                                                    typedXmlPullParser.getAttributeValue(
                                                            (String) null, "at"));
                                    int depth4 = typedXmlPullParser.getDepth();
                                    while (XmlUtils.nextElementWithin(typedXmlPullParser, depth4)) {
                                        if (KnoxAnalyticsDataConverter.EVENT.equals(
                                                typedXmlPullParser.getName())) {
                                            long attributeLong =
                                                    typedXmlPullParser.getAttributeLong(
                                                            (String) null, "nt");
                                            long attributeLong2 =
                                                    typedXmlPullParser.getAttributeLong(
                                                            (String) null, "nd", -1L);
                                            int attributeInt =
                                                    typedXmlPullParser.getAttributeInt(
                                                            (String) null, "us");
                                            int attributeInt2 =
                                                    typedXmlPullParser.getAttributeInt(
                                                            (String) null,
                                                            KnoxAnalyticsDataConverter.FEATURE);
                                            int attributeInt3 =
                                                    typedXmlPullParser.getAttributeInt(
                                                            (String) null, "af", 0);
                                            int attributeInt4 =
                                                    typedXmlPullParser.getAttributeInt(
                                                            (String) null, "ci", -1);
                                            if (attributeLong + attributeLong2 >= j) {
                                                orCreateDiscreteOpEventsList.add(
                                                        new DiscreteOpEvent(
                                                                attributeInt,
                                                                attributeInt2,
                                                                attributeLong,
                                                                attributeLong2,
                                                                attributeInt3,
                                                                attributeInt4));
                                            }
                                        }
                                    }
                                    Collections.sort(
                                            orCreateDiscreteOpEventsList,
                                            new DiscreteRegistry$DiscreteOp$$ExternalSyntheticLambda0());
                                }
                            }
                        }
                    }
                }
            }
        }

        public final DiscretePackageOps getOrCreateDiscretePackageOps(String str) {
            DiscretePackageOps discretePackageOps = (DiscretePackageOps) this.mPackages.get(str);
            if (discretePackageOps != null) {
                return discretePackageOps;
            }
            DiscretePackageOps discretePackageOps2 = DiscreteRegistry.this.new DiscretePackageOps();
            this.mPackages.put(str, discretePackageOps2);
            return discretePackageOps2;
        }
    }

    /* renamed from: -$$Nest$smdiscretizeDuration, reason: not valid java name */
    public static long m239$$Nest$smdiscretizeDuration(long j) {
        if (j == -1) {
            return -1L;
        }
        long j2 = sDiscreteHistoryQuantization;
        return j2 * (((j + j2) - 1) / j2);
    }

    public DiscreteRegistry(Object obj) {
        Object obj2 = new Object();
        this.mOnDiskLock = obj2;
        this.mCachedOps = null;
        this.mDebugMode = false;
        this.mInMemoryLock = obj;
        synchronized (obj2) {
            File file =
                    new File(new File(Environment.getDataSystemDirectory(), "appops"), "discrete");
            this.mDiscreteAccessDir = file;
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    Slog.e("DiscreteRegistry", "Failed to create DiscreteRegistry directory");
                }
                FileUtils.setPermissions(file.getPath(), 505, -1, -1);
            }
            int readLargestChainIdFromDiskLocked = readLargestChainIdFromDiskLocked();
            synchronized (obj) {
                this.mDiscreteOps = new DiscreteOps(readLargestChainIdFromDiskLocked);
            }
        }
    }

    public static int[] parseOpsList(String str) {
        String[] split = str.isEmpty() ? new String[0] : str.split(",");
        int length = split.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            try {
                iArr[i] = Integer.parseInt(split[i]);
            } catch (NumberFormatException e) {
                Slog.e("DiscreteRegistry", "Failed to parse Discrete ops list: " + e.getMessage());
                return parseOpsList("1,0,26,27,100,101,120,136,141");
            }
        }
        return iArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0222  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addFilteredDiscreteOpsToHistoricalOps(
            android.app.AppOpsManager.HistoricalOps r33,
            long r34,
            long r36,
            int r38,
            int r39,
            java.lang.String r40,
            java.lang.String[] r41,
            java.lang.String r42,
            int r43,
            java.util.Set r44) {
        /*
            Method dump skipped, instructions count: 1096
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.appop.DiscreteRegistry.addFilteredDiscreteOpsToHistoricalOps(android.app.AppOpsManager$HistoricalOps,"
                    + " long, long, int, int, java.lang.String, java.lang.String[],"
                    + " java.lang.String, int, java.util.Set):void");
    }

    public final void clearHistory() {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                this.mDiscreteOps = new DiscreteOps(0);
            }
            this.mCachedOps = null;
            FileUtils.deleteContentsAndDir(this.mDiscreteAccessDir);
            if (!this.mDiscreteAccessDir.exists()) {
                if (!this.mDiscreteAccessDir.mkdirs()) {
                    Slog.e("DiscreteRegistry", "Failed to create DiscreteRegistry directory");
                }
                FileUtils.setPermissions(this.mDiscreteAccessDir.getPath(), 505, -1, -1);
            }
        }
    }

    public final void deleteOldDiscreteHistoryFilesLocked() {
        File[] listFiles = this.mDiscreteAccessDir.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return;
        }
        for (File file : listFiles) {
            String name = file.getName();
            if (name.endsWith("tl")) {
                try {
                    if (Instant.now()
                                    .minus(sDiscreteHistoryCutoff, (TemporalUnit) ChronoUnit.MILLIS)
                                    .toEpochMilli()
                            > Long.valueOf(name.substring(0, name.length() - 2)).longValue()) {
                        file.delete();
                        Slog.e("DiscreteRegistry", "Deleting file " + name);
                    }
                } catch (Throwable th) {
                    Slog.e("DiscreteRegistry", "Error while cleaning timeline files: ", th);
                }
            }
        }
    }

    public final DiscreteOps getAllDiscreteOps() {
        DiscreteOps discreteOps = new DiscreteOps(0);
        synchronized (this.mOnDiskLock) {
            try {
                synchronized (this.mInMemoryLock) {
                    discreteOps.merge(this.mDiscreteOps);
                }
                if (this.mCachedOps == null) {
                    DiscreteOps discreteOps2 = new DiscreteOps(0);
                    this.mCachedOps = discreteOps2;
                    readDiscreteOpsFromDisk(discreteOps2);
                }
                discreteOps.merge(this.mCachedOps);
            } catch (Throwable th) {
                throw th;
            }
        }
        return discreteOps;
    }

    public final void persistDiscreteOpsLocked(DiscreteOps discreteOps) {
        FileOutputStream fileOutputStream;
        long epochMilli = Instant.now().toEpochMilli();
        AtomicFile atomicFile =
                new AtomicFile(new File(this.mDiscreteAccessDir, epochMilli + "tl"));
        try {
            fileOutputStream = atomicFile.startWrite();
        } catch (Throwable th) {
            th = th;
            fileOutputStream = null;
        }
        try {
            DiscreteOps.m244$$Nest$mwriteToStream(discreteOps, fileOutputStream);
            atomicFile.finishWrite(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            Slog.e(
                    "DiscreteRegistry",
                    "Error writing timeline state: "
                            + th.getMessage()
                            + " "
                            + Arrays.toString(th.getStackTrace()));
            if (fileOutputStream != null) {
                atomicFile.failWrite(fileOutputStream);
            }
        }
    }

    public final void readDiscreteOpsFromDisk(DiscreteOps discreteOps) {
        synchronized (this.mOnDiskLock) {
            try {
                long epochMilli =
                        Instant.now()
                                .minus(sDiscreteHistoryCutoff, (TemporalUnit) ChronoUnit.MILLIS)
                                .toEpochMilli();
                File[] listFiles = this.mDiscreteAccessDir.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    for (File file : listFiles) {
                        String name = file.getName();
                        if (name.endsWith("tl")
                                && Long.valueOf(name.substring(0, name.length() - 2)).longValue()
                                        >= epochMilli) {
                            DiscreteOps.m243$$Nest$mreadFromFile(discreteOps, file, epochMilli);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int readLargestChainIdFromDiskLocked() {
        File[] listFiles = this.mDiscreteAccessDir.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            long j = 0;
            File file = null;
            for (File file2 : listFiles) {
                String name = file2.getName();
                if (name.endsWith("tl")) {
                    long longValue = Long.valueOf(name.substring(0, name.length() - 2)).longValue();
                    if (j < longValue) {
                        file = file2;
                        j = longValue;
                    }
                }
            }
            if (file == null) {
                return 0;
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    XmlUtils.beginDocument(resolvePullParser, "h");
                    int attributeInt = resolvePullParser.getAttributeInt((String) null, "lc", 0);
                    try {
                        fileInputStream.close();
                    } catch (IOException unused) {
                    }
                    return attributeInt;
                } catch (Throwable unused2) {
                    fileInputStream.close();
                }
            } catch (FileNotFoundException | IOException unused3) {
            }
        }
        return 0;
    }

    public final void recordDiscreteAccess(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            long j,
            long j2,
            String str,
            String str2) {
        if (ArrayUtils.contains(sDiscreteOps, i2) && (i3 & sDiscreteFlags) != 0) {
            synchronized (this.mInMemoryLock) {
                this.mDiscreteOps.addDiscreteAccess(i2, i, i3, i4, i5, i6, j, j2, str, str2);
            }
        }
    }

    public final void setDiscreteHistoryParameters(DeviceConfig.Properties properties) {
        boolean contains = properties.getKeyset().contains("discrete_history_cutoff_millis");
        long j = DEFAULT_DISCRETE_HISTORY_CUTOFF;
        if (contains) {
            sDiscreteHistoryCutoff = properties.getLong("discrete_history_cutoff_millis", j);
            if (!Build.IS_DEBUGGABLE && !this.mDebugMode) {
                sDiscreteHistoryCutoff =
                        Long.min(MAXIMUM_DISCRETE_HISTORY_CUTOFF, sDiscreteHistoryCutoff);
            }
        } else {
            sDiscreteHistoryCutoff = j;
        }
        boolean contains2 = properties.getKeyset().contains("discrete_history_quantization_millis");
        long j2 = DEFAULT_DISCRETE_HISTORY_QUANTIZATION;
        if (contains2) {
            sDiscreteHistoryQuantization =
                    properties.getLong("discrete_history_quantization_millis", j2);
            if (!Build.IS_DEBUGGABLE && !this.mDebugMode) {
                sDiscreteHistoryQuantization = Math.max(j2, sDiscreteHistoryQuantization);
            }
        } else {
            sDiscreteHistoryQuantization = j2;
        }
        int i = 11;
        if (properties.getKeyset().contains("discrete_history_op_flags")) {
            i = properties.getInt("discrete_history_op_flags", 11);
            sDiscreteFlags = i;
        }
        sDiscreteFlags = i;
        sDiscreteOps =
                properties.getKeyset().contains("discrete_history_ops_cslist")
                        ? parseOpsList(
                                properties.getString(
                                        "discrete_history_ops_cslist",
                                        "1,0,26,27,100,101,120,136,141"))
                        : parseOpsList("1,0,26,27,100,101,120,136,141");
    }

    public final void writeAndClearAccessHistory() {
        DiscreteOps discreteOps;
        synchronized (this.mOnDiskLock) {
            try {
                if (this.mDiscreteAccessDir == null) {
                    Slog.d("DiscreteRegistry", "State not saved - persistence not initialized.");
                    return;
                }
                synchronized (this.mInMemoryLock) {
                    discreteOps = this.mDiscreteOps;
                    this.mDiscreteOps = new DiscreteOps(discreteOps.mChainIdOffset);
                    this.mCachedOps = null;
                }
                deleteOldDiscreteHistoryFilesLocked();
                if (!discreteOps.mUids.isEmpty()) {
                    persistDiscreteOpsLocked(discreteOps);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
