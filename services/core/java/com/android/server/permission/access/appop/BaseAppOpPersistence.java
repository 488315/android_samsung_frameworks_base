package com.android.server.permission.access.appop;

import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.immutable.IndexedMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class BaseAppOpPersistence {
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0099, code lost:

       r0 = r9.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x009d, code lost:

       if (r0 == 1) goto L61;
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x009f, code lost:

       if (r0 == 2) goto L62;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a1, code lost:

       if (r0 == 3) goto L63;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void parseAppOps(
            com.android.modules.utils.BinaryXmlPullParser r9,
            com.android.server.permission.access.immutable.MutableIndexedMap r10) {
        /*
            int r0 = r9.getEventType()
            java.lang.String r1 = "Unexpected event type "
            r2 = 2
            if (r0 == 0) goto L16
            if (r0 != r2) goto Lc
            goto L16
        Lc:
            org.xmlpull.v1.XmlPullParserException r9 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r10 = android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0.m(r0, r1)
            r9.<init>(r10)
            throw r9
        L16:
            int r0 = r9.next()
            r3 = 1
            r4 = 3
            if (r0 == r3) goto L23
            if (r0 == r2) goto L23
            if (r0 == r4) goto L23
            goto L16
        L23:
            int r0 = r9.getEventType()
            if (r0 == r3) goto Lc7
            if (r0 == r2) goto L39
            if (r0 != r4) goto L2f
            goto Lc7
        L2f:
            org.xmlpull.v1.XmlPullParserException r9 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r10 = android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0.m(r0, r1)
            r9.<init>(r10)
            throw r9
        L39:
            int r0 = r9.getDepth()
            java.lang.String r5 = r9.getName()
            java.lang.String r6 = "app-op"
            boolean r5 = com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
            if (r5 == 0) goto L6d
            r5 = 0
            java.lang.String r6 = "name"
            int r6 = r9.getAttributeIndexOrThrow(r5, r6)
            java.lang.String r6 = r9.getAttributeValue(r6)
            java.lang.String r6 = r6.intern()
            java.lang.String r7 = "intern(...)"
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r6)
            java.lang.String r7 = "mode"
            int r5 = r9.getAttributeInt(r5, r7)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r10.put(r6, r5)
            goto L7a
        L6d:
            java.lang.String r5 = r9.getName()
            java.lang.String r6 = "Ignoring unknown tag "
            java.lang.String r7 = " when parsing app-op state"
            java.lang.String r8 = "BaseAppOpPersistence"
            com.android.server.PinnerService$$ExternalSyntheticOutline0.m(r6, r5, r7, r8)
        L7a:
            int r5 = r9.getDepth()
            if (r5 != r0) goto Lb9
        L80:
            int r5 = r9.getEventType()
            if (r5 == r2) goto Lae
            if (r5 != r4) goto La4
            int r5 = r9.getDepth()
            if (r5 <= r0) goto L99
        L8e:
            int r5 = r9.next()
            if (r5 == r3) goto L80
            if (r5 == r2) goto L80
            if (r5 == r4) goto L80
            goto L8e
        L99:
            int r0 = r9.next()
            if (r0 == r3) goto L23
            if (r0 == r2) goto L23
            if (r0 == r4) goto L23
            goto L99
        La4:
            org.xmlpull.v1.XmlPullParserException r9 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r10 = android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0.m(r5, r1)
            r9.<init>(r10)
            throw r9
        Lae:
            int r5 = r9.next()
            if (r5 == r3) goto L80
            if (r5 == r2) goto L80
            if (r5 == r4) goto L80
            goto Lae
        Lb9:
            org.xmlpull.v1.XmlPullParserException r9 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r10 = "Unexpected post-block depth "
            java.lang.String r1 = ", expected "
            java.lang.String r10 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r5, r0, r10, r1)
            r9.<init>(r10)
            throw r9
        Lc7:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.appop.BaseAppOpPersistence.parseAppOps(com.android.modules.utils.BinaryXmlPullParser,"
                    + " com.android.server.permission.access.immutable.MutableIndexedMap):void");
    }

    public static void serializeAppOps(
            BinaryXmlSerializer binaryXmlSerializer, IndexedMap indexedMap) {
        int size = indexedMap.map.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = indexedMap.map.keyAt(i);
            int intValue = ((Number) indexedMap.map.valueAt(i)).intValue();
            binaryXmlSerializer.startTag((String) null, "app-op");
            binaryXmlSerializer.attributeInterned((String) null, "name", (String) keyAt);
            binaryXmlSerializer.attributeInt((String) null, "mode", intValue);
            binaryXmlSerializer.endTag((String) null, "app-op");
        }
    }

    public abstract void parseUserState(
            BinaryXmlPullParser binaryXmlPullParser, MutableAccessState mutableAccessState, int i);

    public abstract void serializeUserState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i);
}
