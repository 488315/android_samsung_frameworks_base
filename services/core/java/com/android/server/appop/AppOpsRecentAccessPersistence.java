package com.android.server.appop;

import android.app.AppOpsManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.appop.AppOpsService.Op;
import com.android.server.appop.AppOpsService.UidState;

import com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppOpsRecentAccessPersistence {
    public final AppOpsService mAppOpsService;
    public final AtomicFile mRecentAccessesFile;

    public AppOpsRecentAccessPersistence(AtomicFile atomicFile, AppOpsService appOpsService) {
        this.mRecentAccessesFile = atomicFile;
        this.mAppOpsService = appOpsService;
    }

    public static void writeDeviceAttributedOps(
            TypedXmlSerializer typedXmlSerializer, AppOpsService.Op op) {
        ArrayMap arrayMap;
        AppOpsManager.AttributedOpEntry attributedOpEntry;
        AppOpsService.Op op2 = op;
        Iterator it = op2.mDeviceAttributedOps.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            ArrayMap arrayMap2 = (ArrayMap) op2.mDeviceAttributedOps.get(str);
            int i = 0;
            while (i < arrayMap2.size()) {
                String str2 = (String) arrayMap2.keyAt(i);
                AppOpsManager.AttributedOpEntry createAttributedOpEntryLocked =
                        ((AttributedOp) arrayMap2.valueAt(i)).createAttributedOpEntryLocked();
                ArraySet collectKeys = createAttributedOpEntryLocked.collectKeys();
                int i2 = 0;
                while (i2 < collectKeys.size()) {
                    long longValue = ((Long) collectKeys.valueAt(i2)).longValue();
                    int extractUidStateFromKey = AppOpsManager.extractUidStateFromKey(longValue);
                    int extractFlagsFromKey = AppOpsManager.extractFlagsFromKey(longValue);
                    int i3 = i;
                    long lastAccessTime =
                            createAttributedOpEntryLocked.getLastAccessTime(
                                    extractUidStateFromKey,
                                    extractUidStateFromKey,
                                    extractFlagsFromKey);
                    Iterator it2 = it;
                    long lastRejectTime =
                            createAttributedOpEntryLocked.getLastRejectTime(
                                    extractUidStateFromKey,
                                    extractUidStateFromKey,
                                    extractFlagsFromKey);
                    ArraySet arraySet = collectKeys;
                    int i4 = i2;
                    long lastDuration =
                            createAttributedOpEntryLocked.getLastDuration(
                                    extractUidStateFromKey,
                                    extractUidStateFromKey,
                                    extractFlagsFromKey);
                    AppOpsManager.OpEventProxyInfo lastProxyInfo =
                            createAttributedOpEntryLocked.getLastProxyInfo(
                                    extractUidStateFromKey,
                                    extractUidStateFromKey,
                                    extractFlagsFromKey);
                    if (lastAccessTime > 0
                            || lastRejectTime > 0
                            || lastDuration > 0
                            || lastProxyInfo != null) {
                        arrayMap = arrayMap2;
                        typedXmlSerializer.startTag(
                                (String) null, KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
                        attributedOpEntry = createAttributedOpEntryLocked;
                        if (str2 != null) {
                            typedXmlSerializer.attribute((String) null, "id", str2);
                        }
                        typedXmlSerializer.attributeLong((String) null, "n", longValue);
                        if (!Objects.equals(str, "default:0")) {
                            typedXmlSerializer.attribute((String) null, "dv", str);
                        }
                        if (lastAccessTime > 0) {
                            typedXmlSerializer.attributeLong(
                                    (String) null,
                                    KnoxAnalyticsDataConverter.TIMESTAMP,
                                    lastAccessTime);
                        }
                        if (lastRejectTime > 0) {
                            typedXmlSerializer.attributeLong((String) null, "r", lastRejectTime);
                        }
                        if (lastDuration > 0) {
                            typedXmlSerializer.attributeLong((String) null, "d", lastDuration);
                        }
                        if (lastProxyInfo != null) {
                            typedXmlSerializer.attributeInt(
                                    (String) null, "pu", lastProxyInfo.getUid());
                            if (lastProxyInfo.getPackageName() != null) {
                                typedXmlSerializer.attribute(
                                        (String) null, "pp", lastProxyInfo.getPackageName());
                            }
                            if (lastProxyInfo.getAttributionTag() != null) {
                                typedXmlSerializer.attribute(
                                        (String) null, "pc", lastProxyInfo.getAttributionTag());
                            }
                            if (lastProxyInfo.getDeviceId() != null
                                    && !Objects.equals(lastProxyInfo.getDeviceId(), "default:0")) {
                                typedXmlSerializer.attribute(
                                        (String) null, "pdv", lastProxyInfo.getDeviceId());
                            }
                        }
                        typedXmlSerializer.endTag(
                                (String) null, KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
                    } else {
                        arrayMap = arrayMap2;
                        attributedOpEntry = createAttributedOpEntryLocked;
                    }
                    i2 = i4 + 1;
                    i = i3;
                    it = it2;
                    collectKeys = arraySet;
                    arrayMap2 = arrayMap;
                    createAttributedOpEntryLocked = attributedOpEntry;
                }
                i++;
            }
            op2 = op;
        }
    }

    public final void readPackage(TypedXmlPullParser typedXmlPullParser, SparseArray sparseArray) {
        String str;
        String str2;
        String str3;
        String str4;
        int i;
        AppOpsService appOpsService;
        AppOpsService.UidState uidState;
        int i2;
        String str5;
        AppOpsService.Op op;
        String str6 = null;
        String str7 = "n";
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            int i3 = 1;
            if (next == 1) {
                return;
            }
            int i4 = 3;
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3) {
                int i5 = 4;
                if (next != 4) {
                    if (typedXmlPullParser.getName().equals("uid")) {
                        int attributeInt = typedXmlPullParser.getAttributeInt(str6, str7);
                        AppOpsService appOpsService2 = this.mAppOpsService;
                        Objects.requireNonNull(appOpsService2);
                        AppOpsService.UidState uidState2 =
                                appOpsService2.new UidState(attributeInt);
                        sparseArray.put(attributeInt, uidState2);
                        int depth2 = typedXmlPullParser.getDepth();
                        while (true) {
                            int next2 = typedXmlPullParser.next();
                            if (next2 == i3
                                    || (next2 == i4 && typedXmlPullParser.getDepth() <= depth2)) {
                                break;
                            }
                            if (next2 != i4 && next2 != i5) {
                                if (typedXmlPullParser.getName().equals("op")) {
                                    AppOpsService.Op op2 = r3;
                                    i = depth2;
                                    appOpsService = appOpsService2;
                                    uidState = uidState2;
                                    AppOpsService.Op op3 =
                                            appOpsService2
                                            .new Op(
                                                    uidState2,
                                                    attributeValue,
                                                    typedXmlPullParser.getAttributeInt(str6, str7),
                                                    uidState2.uid);
                                    int depth3 = typedXmlPullParser.getDepth();
                                    while (true) {
                                        int next3 = typedXmlPullParser.next();
                                        if (next3 == 1
                                                || (next3 == i4
                                                        && typedXmlPullParser.getDepth()
                                                                <= depth3)) {
                                            break;
                                        }
                                        if (next3 != i4 && next3 != i5) {
                                            if (typedXmlPullParser
                                                    .getName()
                                                    .equals(
                                                            KnoxAnalyticsSystemService
                                                                    .STATUS_PARAMETER_NAME)) {
                                                String readStringAttribute =
                                                        XmlUtils.readStringAttribute(
                                                                typedXmlPullParser, "id");
                                                long attributeLong =
                                                        typedXmlPullParser.getAttributeLong(
                                                                (String) null, str7);
                                                int extractUidStateFromKey =
                                                        AppOpsManager.extractUidStateFromKey(
                                                                attributeLong);
                                                int extractFlagsFromKey =
                                                        AppOpsManager.extractFlagsFromKey(
                                                                attributeLong);
                                                String attributeValue2 =
                                                        typedXmlPullParser.getAttributeValue(
                                                                (String) null, "dv");
                                                long attributeLong2 =
                                                        typedXmlPullParser.getAttributeLong(
                                                                (String) null,
                                                                KnoxAnalyticsDataConverter
                                                                        .TIMESTAMP,
                                                                0L);
                                                i2 = depth3;
                                                str5 = str7;
                                                long attributeLong3 =
                                                        typedXmlPullParser.getAttributeLong(
                                                                (String) null, "r", 0L);
                                                long attributeLong4 =
                                                        typedXmlPullParser.getAttributeLong(
                                                                (String) null, "d", -1L);
                                                String readStringAttribute2 =
                                                        XmlUtils.readStringAttribute(
                                                                typedXmlPullParser, "pp");
                                                int attributeInt2 =
                                                        typedXmlPullParser.getAttributeInt(
                                                                (String) null, "pu", -1);
                                                String readStringAttribute3 =
                                                        XmlUtils.readStringAttribute(
                                                                typedXmlPullParser, "pc");
                                                String attributeValue3 =
                                                        typedXmlPullParser.getAttributeValue(
                                                                (String) null, "pdv");
                                                if (attributeValue2 == null
                                                        || attributeValue2.equals("")) {
                                                    attributeValue2 = "default:0";
                                                }
                                                op = op2;
                                                AttributedOp orCreateAttribution =
                                                        op.getOrCreateAttribution(
                                                                op,
                                                                readStringAttribute,
                                                                attributeValue2);
                                                if (attributeLong2 > 0) {
                                                    orCreateAttribution.accessed(
                                                            attributeLong2,
                                                            attributeLong4,
                                                            attributeInt2,
                                                            readStringAttribute2,
                                                            readStringAttribute3,
                                                            attributeValue3,
                                                            extractUidStateFromKey,
                                                            extractFlagsFromKey);
                                                }
                                                if (attributeLong3 > 0) {
                                                    orCreateAttribution.rejected(
                                                            extractUidStateFromKey,
                                                            extractFlagsFromKey,
                                                            attributeLong3);
                                                }
                                            } else {
                                                i2 = depth3;
                                                str5 = str7;
                                                op = op2;
                                                Slog.w(
                                                        "AppOpsRecentAccessPersistence",
                                                        "Unknown element under <op>: "
                                                                + typedXmlPullParser.getName());
                                                XmlUtils.skipCurrentTag(typedXmlPullParser);
                                            }
                                            op2 = op;
                                            str7 = str5;
                                            depth3 = i2;
                                            i4 = 3;
                                            i5 = 4;
                                        }
                                    }
                                    str4 = str7;
                                    AppOpsService.Op op4 = op2;
                                    str3 = null;
                                    AppOpsService.Ops ops =
                                            (AppOpsService.Ops) uidState.pkgOps.get(attributeValue);
                                    if (ops == null) {
                                        ops = new AppOpsService.Ops(attributeValue, uidState);
                                        uidState.pkgOps.put(attributeValue, ops);
                                    }
                                    ops.put(op4.op, op4);
                                } else {
                                    str3 = str6;
                                    str4 = str7;
                                    i = depth2;
                                    appOpsService = appOpsService2;
                                    uidState = uidState2;
                                    Slog.w(
                                            "AppOpsRecentAccessPersistence",
                                            "Unknown element under <pkg>: "
                                                    + typedXmlPullParser.getName());
                                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                                }
                                str6 = str3;
                                uidState2 = uidState;
                                depth2 = i;
                                appOpsService2 = appOpsService;
                                str7 = str4;
                                i3 = 1;
                                i4 = 3;
                                i5 = 4;
                            }
                            i3 = 1;
                        }
                        str = str6;
                        str2 = str7;
                    } else {
                        str = str6;
                        str2 = str7;
                        Slog.w(
                                "AppOpsRecentAccessPersistence",
                                "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                    str6 = str;
                    str7 = str2;
                }
            }
        }
    }

    public final void readRecentAccesses(SparseArray sparseArray) {
        TypedXmlPullParser resolvePullParser;
        int next;
        synchronized (this.mRecentAccessesFile) {
            try {
                FileInputStream openRead = this.mRecentAccessesFile.openRead();
                try {
                    sparseArray.clear();
                    this.mAppOpsService.mAppOpsCheckingService.clearAllModes();
                    try {
                        resolvePullParser = Xml.resolvePullParser(openRead);
                        do {
                            next = resolvePullParser.next();
                            if (next == 2) {
                                break;
                            }
                        } while (next != 1);
                    } catch (IOException
                            | IllegalStateException
                            | IndexOutOfBoundsException
                            | NullPointerException
                            | NumberFormatException
                            | XmlPullParserException e) {
                        Slog.w("AppOpsRecentAccessPersistence", "Failed parsing " + e);
                        sparseArray.clear();
                        this.mAppOpsService.mAppOpsCheckingService.clearAllModes();
                    }
                    if (next != 2) {
                        throw new IllegalStateException("no start tag found");
                    }
                    int depth = resolvePullParser.getDepth();
                    while (true) {
                        int next2 = resolvePullParser.next();
                        if (next2 != 1) {
                            if (next2 == 3) {
                                if (resolvePullParser.getDepth() > depth) {}
                            }
                            if (next2 != 3 && next2 != 4) {
                                String name = resolvePullParser.getName();
                                if (name.equals("pkg")) {
                                    readPackage(resolvePullParser, sparseArray);
                                } else if (name.equals("uid")) {
                                    XmlUtils.skipCurrentTag(resolvePullParser);
                                } else {
                                    Slog.w(
                                            "AppOpsRecentAccessPersistence",
                                            "Unknown element under <app-ops>: "
                                                    + resolvePullParser.getName());
                                    XmlUtils.skipCurrentTag(resolvePullParser);
                                }
                            }
                        }
                        try {
                            openRead.close();
                        } catch (IOException unused) {
                        }
                    }
                } catch (Throwable th) {
                    sparseArray.clear();
                    this.mAppOpsService.mAppOpsCheckingService.clearAllModes();
                    try {
                        openRead.close();
                    } catch (IOException unused2) {
                    }
                    throw th;
                }
            } catch (FileNotFoundException unused3) {
                Slog.i(
                        "AppOpsRecentAccessPersistence",
                        "No existing app ops "
                                + this.mRecentAccessesFile.getBaseFile()
                                + "; starting empty");
            }
        }
    }

    public final void writeRecentAccesses(SparseArray sparseArray) {
        synchronized (this.mRecentAccessesFile) {
            try {
                try {
                    FileOutputStream startWrite = this.mRecentAccessesFile.startWrite();
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "app-ops");
                        resolveSerializer.attributeInt((String) null, "v", 1);
                        for (int i = 0; i < sparseArray.size(); i++) {
                            AppOpsService.UidState uidState =
                                    (AppOpsService.UidState) sparseArray.valueAt(i);
                            int i2 = uidState.uid;
                            for (int i3 = 0; i3 < uidState.pkgOps.size(); i3++) {
                                String str = (String) uidState.pkgOps.keyAt(i3);
                                AppOpsService.Ops ops =
                                        (AppOpsService.Ops) uidState.pkgOps.valueAt(i3);
                                resolveSerializer.startTag((String) null, "pkg");
                                resolveSerializer.attribute((String) null, "n", str);
                                resolveSerializer.startTag((String) null, "uid");
                                resolveSerializer.attributeInt((String) null, "n", i2);
                                for (int i4 = 0; i4 < ops.size(); i4++) {
                                    AppOpsService.Op op = (AppOpsService.Op) ops.valueAt(i4);
                                    resolveSerializer.startTag((String) null, "op");
                                    resolveSerializer.attributeInt((String) null, "n", op.op);
                                    writeDeviceAttributedOps(resolveSerializer, op);
                                    resolveSerializer.endTag((String) null, "op");
                                }
                                resolveSerializer.endTag((String) null, "uid");
                                resolveSerializer.endTag((String) null, "pkg");
                            }
                        }
                        resolveSerializer.endTag((String) null, "app-ops");
                        resolveSerializer.endDocument();
                        this.mRecentAccessesFile.finishWrite(startWrite);
                    } catch (IOException e) {
                        Slog.w(
                                "AppOpsRecentAccessPersistence",
                                "Failed to write state, restoring backup.",
                                e);
                        this.mRecentAccessesFile.failWrite(startWrite);
                    }
                } catch (IOException e2) {
                    Slog.w("AppOpsRecentAccessPersistence", "Failed to write state: " + e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
