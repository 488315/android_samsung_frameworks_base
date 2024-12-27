package com.android.server.appop;

import android.app.AppOpsManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LegacyAppOpStateParser {
    public static void readOp(
            TypedXmlPullParser typedXmlPullParser, int i, String str, SparseArray sparseArray) {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "n");
        int opToDefaultMode = AppOpsManager.opToDefaultMode(attributeInt);
        int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "m", opToDefaultMode);
        if (attributeInt2 != opToDefaultMode) {
            ArrayMap arrayMap = (ArrayMap) sparseArray.get(i);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                sparseArray.put(i, arrayMap);
            }
            SparseIntArray sparseIntArray = (SparseIntArray) arrayMap.get(str);
            if (sparseIntArray == null) {
                sparseIntArray = new SparseIntArray();
                arrayMap.put(str, sparseIntArray);
            }
            sparseIntArray.put(attributeInt, attributeInt2);
        }
    }

    public static void readPackage(TypedXmlPullParser typedXmlPullParser, SparseArray sparseArray) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("uid")) {
                    int userId =
                            UserHandle.getUserId(
                                    typedXmlPullParser.getAttributeInt((String) null, "n"));
                    int depth2 = typedXmlPullParser.getDepth();
                    while (true) {
                        int next2 = typedXmlPullParser.next();
                        if (next2 != 1 && (next2 != 3 || typedXmlPullParser.getDepth() > depth2)) {
                            if (next2 != 3 && next2 != 4) {
                                if (typedXmlPullParser.getName().equals("op")) {
                                    readOp(typedXmlPullParser, userId, attributeValue, sparseArray);
                                } else {
                                    Slog.w(
                                            "LegacyAppOpStateParser",
                                            "Unknown element under <pkg>: "
                                                    + typedXmlPullParser.getName());
                                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                                }
                            }
                        }
                    }
                } else {
                    Slog.w(
                            "LegacyAppOpStateParser",
                            "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0039, code lost:

       if (r6 != 4) goto L66;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x003c, code lost:

       r6 = r2.getName();
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0047, code lost:

       if (r6.equals("pkg") == false) goto L67;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0054, code lost:

       if (r6.equals("uid") == false) goto L70;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0061, code lost:

       if (r6.equals("user") == false) goto L71;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0067, code lost:

       android.util.Slog.w("LegacyAppOpStateParser", "Unknown element under <app-ops>: " + r2.getName());
       com.android.internal.util.XmlUtils.skipCurrentTag(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0063, code lost:

       readUser(r2, r11);
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0056, code lost:

       readUidOps(r2, r10);
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0049, code lost:

       readPackage(r2, r11);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int readState(
            android.util.AtomicFile r9,
            android.util.SparseArray r10,
            android.util.SparseArray r11) {
        /*
            java.lang.String r0 = "LegacyAppOpStateParser"
            java.io.FileInputStream r1 = r9.openRead()     // Catch: java.io.IOException -> L89 org.xmlpull.v1.XmlPullParserException -> L8b java.io.FileNotFoundException -> Lae
            com.android.modules.utils.TypedXmlPullParser r2 = android.util.Xml.resolvePullParser(r1)     // Catch: java.lang.Throwable -> L34
        La:
            int r3 = r2.next()     // Catch: java.lang.Throwable -> L34
            r4 = 1
            r5 = 2
            if (r3 == r5) goto L15
            if (r3 == r4) goto L15
            goto La
        L15:
            if (r3 != r5) goto L8e
            java.lang.String r3 = "v"
            r5 = -1
            r6 = 0
            int r3 = r2.getAttributeInt(r6, r3, r5)     // Catch: java.lang.Throwable -> L34
            int r5 = r2.getDepth()     // Catch: java.lang.Throwable -> L34
        L24:
            int r6 = r2.next()     // Catch: java.lang.Throwable -> L34
            if (r6 == r4) goto L83
            r7 = 3
            if (r6 != r7) goto L36
            int r8 = r2.getDepth()     // Catch: java.lang.Throwable -> L34
            if (r8 <= r5) goto L83
            goto L36
        L34:
            r10 = move-exception
            goto L97
        L36:
            if (r6 == r7) goto L24
            r7 = 4
            if (r6 != r7) goto L3c
            goto L24
        L3c:
            java.lang.String r6 = r2.getName()     // Catch: java.lang.Throwable -> L34
            java.lang.String r7 = "pkg"
            boolean r7 = r6.equals(r7)     // Catch: java.lang.Throwable -> L34
            if (r7 == 0) goto L4d
            readPackage(r2, r11)     // Catch: java.lang.Throwable -> L34
            goto L24
        L4d:
            java.lang.String r7 = "uid"
            boolean r7 = r6.equals(r7)     // Catch: java.lang.Throwable -> L34
            if (r7 == 0) goto L5a
            readUidOps(r2, r10)     // Catch: java.lang.Throwable -> L34
            goto L24
        L5a:
            java.lang.String r7 = "user"
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Throwable -> L34
            if (r6 == 0) goto L67
            readUser(r2, r11)     // Catch: java.lang.Throwable -> L34
            goto L24
        L67:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L34
            r6.<init>()     // Catch: java.lang.Throwable -> L34
            java.lang.String r7 = "Unknown element under <app-ops>: "
            r6.append(r7)     // Catch: java.lang.Throwable -> L34
            java.lang.String r7 = r2.getName()     // Catch: java.lang.Throwable -> L34
            r6.append(r7)     // Catch: java.lang.Throwable -> L34
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L34
            android.util.Slog.w(r0, r6)     // Catch: java.lang.Throwable -> L34
            com.android.internal.util.XmlUtils.skipCurrentTag(r2)     // Catch: java.lang.Throwable -> L34
            goto L24
        L83:
            if (r1 == 0) goto L8d
            r1.close()     // Catch: java.io.IOException -> L89 org.xmlpull.v1.XmlPullParserException -> L8b java.io.FileNotFoundException -> Lae
            goto L8d
        L89:
            r9 = move-exception
            goto La2
        L8b:
            r9 = move-exception
            goto La8
        L8d:
            return r3
        L8e:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L34
            java.lang.String r11 = "no start tag found"
            r10.<init>(r11)     // Catch: java.lang.Throwable -> L34
            throw r10     // Catch: java.lang.Throwable -> L34
        L97:
            if (r1 == 0) goto La1
            r1.close()     // Catch: java.lang.Throwable -> L9d
            goto La1
        L9d:
            r11 = move-exception
            r10.addSuppressed(r11)     // Catch: java.io.IOException -> L89 org.xmlpull.v1.XmlPullParserException -> L8b java.io.FileNotFoundException -> Lae
        La1:
            throw r10     // Catch: java.io.IOException -> L89 org.xmlpull.v1.XmlPullParserException -> L8b java.io.FileNotFoundException -> Lae
        La2:
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            r10.<init>(r9)
            throw r10
        La8:
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            r10.<init>(r9)
            throw r10
        Lae:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "No existing app ops "
            r10.<init>(r11)
            java.io.File r9 = r9.getBaseFile()
            r10.append(r9)
            java.lang.String r9 = "; starting empty"
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Slog.i(r0, r9)
            r9 = -2
            return r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.appop.LegacyAppOpStateParser.readState(android.util.AtomicFile,"
                    + " android.util.SparseArray, android.util.SparseArray):int");
    }

    public static void readUidOps(TypedXmlPullParser typedXmlPullParser, SparseArray sparseArray) {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "n");
        SparseIntArray sparseIntArray = (SparseIntArray) sparseArray.get(attributeInt);
        if (sparseIntArray == null) {
            sparseIntArray = new SparseIntArray();
            sparseArray.put(attributeInt, sparseIntArray);
        }
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("op")) {
                    int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "n");
                    int attributeInt3 = typedXmlPullParser.getAttributeInt((String) null, "m");
                    if (attributeInt3 != AppOpsManager.opToDefaultMode(attributeInt2)) {
                        sparseIntArray.put(attributeInt2, attributeInt3);
                    }
                } else {
                    Slog.w(
                            "LegacyAppOpStateParser",
                            "Unknown element under <uid>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public static void readUser(TypedXmlPullParser typedXmlPullParser, SparseArray sparseArray) {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("pkg")) {
                    String attributeValue =
                            typedXmlPullParser.getAttributeValue((String) null, "n");
                    int depth2 = typedXmlPullParser.getDepth();
                    while (true) {
                        int next2 = typedXmlPullParser.next();
                        if (next2 != 1 && (next2 != 3 || typedXmlPullParser.getDepth() > depth2)) {
                            if (next2 != 3 && next2 != 4) {
                                if (typedXmlPullParser.getName().equals("op")) {
                                    readOp(
                                            typedXmlPullParser,
                                            attributeInt,
                                            attributeValue,
                                            sparseArray);
                                } else {
                                    Slog.w(
                                            "LegacyAppOpStateParser",
                                            "Unknown element under <pkg>: "
                                                    + typedXmlPullParser.getName());
                                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                                }
                            }
                        }
                    }
                } else {
                    Slog.w(
                            "LegacyAppOpStateParser",
                            "Unknown element under <user>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }
}
