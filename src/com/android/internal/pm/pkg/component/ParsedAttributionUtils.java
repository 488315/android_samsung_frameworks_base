package com.android.internal.pm.pkg.component;

import android.util.ArraySet;
import java.util.List;

/* loaded from: classes5.dex */
public class ParsedAttributionUtils {
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a5, code lost:
    
        if (r5 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a7, code lost:
    
        r5 = java.util.Collections.EMPTY_LIST;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00b9, code lost:
    
        return r11.success(new com.android.internal.pm.pkg.component.ParsedAttributionImpl(r3, r4, r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00aa, code lost:
    
        ((java.util.ArrayList) r5).trimToSize();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedAttribution> parseAttribution(android.content.res.Resources r9, android.content.res.XmlResourceParser r10, android.content.pm.parsing.result.ParseInput r11) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            int[] r0 = com.android.internal.R.styleable.AndroidManifestAttribution
            android.content.res.TypedArray r0 = r9.obtainAttributes(r10, r0)
            if (r0 != 0) goto Lf
            java.lang.String r9 = "<attribution> could not be parsed"
            android.content.pm.parsing.result.ParseResult r9 = r11.error(r9)
            return r9
        Lf:
            r1 = 1
            r2 = 0
            java.lang.String r3 = r0.getNonConfigurationString(r1, r2)     // Catch: java.lang.Throwable -> Lba
            if (r3 != 0) goto L21
            java.lang.String r9 = "<attribution> does not specify android:tag"
            android.content.pm.parsing.result.ParseResult r9 = r11.error(r9)     // Catch: java.lang.Throwable -> Lba
            r0.recycle()
            return r9
        L21:
            int r4 = r3.length()     // Catch: java.lang.Throwable -> Lba
            r5 = 50
            if (r4 <= r5) goto L33
            java.lang.String r9 = "android:tag is too long. Max length is 50"
            android.content.pm.parsing.result.ParseResult r9 = r11.error(r9)     // Catch: java.lang.Throwable -> Lba
            r0.recycle()
            return r9
        L33:
            int r4 = r0.getResourceId(r2, r2)     // Catch: java.lang.Throwable -> Lba
            if (r4 != 0) goto L43
            java.lang.String r9 = "<attribution> does not specify android:label"
            android.content.pm.parsing.result.ParseResult r9 = r11.error(r9)     // Catch: java.lang.Throwable -> Lba
            r0.recycle()
            return r9
        L43:
            r0.recycle()
            int r0 = r10.getDepth()
            r5 = 0
        L4b:
            int r6 = r10.next()
            if (r6 == r1) goto La5
            r7 = 3
            if (r6 != r7) goto L5a
            int r8 = r10.getDepth()
            if (r8 <= r0) goto La5
        L5a:
            if (r6 == r7) goto L4b
            r7 = 4
            if (r6 != r7) goto L60
            goto L4b
        L60:
            java.lang.String r6 = r10.getName()
            java.lang.String r7 = "inherit-from"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L92
            int[] r6 = com.android.internal.R.styleable.AndroidManifestAttributionInheritFrom
            android.content.res.TypedArray r6 = r9.obtainAttributes(r10, r6)
            if (r6 != 0) goto L7b
            java.lang.String r9 = "<inherit-from> could not be parsed"
            android.content.pm.parsing.result.ParseResult r9 = r11.error(r9)
            return r9
        L7b:
            java.lang.String r7 = r6.getNonConfigurationString(r2, r2)     // Catch: java.lang.Throwable -> L8d
            if (r5 != 0) goto L86
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L8d
            r5.<init>()     // Catch: java.lang.Throwable -> L8d
        L86:
            r5.add(r7)     // Catch: java.lang.Throwable -> L8d
            r6.recycle()
            goto L4b
        L8d:
            r9 = move-exception
            r6.recycle()
            throw r9
        L92:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Bad element under <attribution>: "
            r9.<init>(r10)
            r9.append(r6)
            java.lang.String r9 = r9.toString()
            android.content.pm.parsing.result.ParseResult r9 = r11.error(r9)
            return r9
        La5:
            if (r5 != 0) goto Laa
            java.util.List r5 = java.util.Collections.EMPTY_LIST
            goto Lb0
        Laa:
            r9 = r5
            java.util.ArrayList r9 = (java.util.ArrayList) r9
            r9.trimToSize()
        Lb0:
            com.android.internal.pm.pkg.component.ParsedAttributionImpl r9 = new com.android.internal.pm.pkg.component.ParsedAttributionImpl
            r9.<init>(r3, r4, r5)
            android.content.pm.parsing.result.ParseResult r9 = r11.success(r9)
            return r9
        Lba:
            r9 = move-exception
            r0.recycle()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedAttributionUtils.parseAttribution(android.content.res.Resources, android.content.res.XmlResourceParser, android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    public static boolean isCombinationValid(List<ParsedAttribution> list) {
        if (list == null) {
            return true;
        }
        ArraySet arraySet = new ArraySet(list.size());
        ArraySet arraySet2 = new ArraySet();
        int size = list.size();
        if (size > 400) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!arraySet.add(list.get(i).getTag())) {
                return false;
            }
        }
        for (int i2 = 0; i2 < size; i2++) {
            List<String> inheritFrom = list.get(i2).getInheritFrom();
            int size2 = inheritFrom.size();
            for (int i3 = 0; i3 < size2; i3++) {
                String str = inheritFrom.get(i3);
                if (arraySet.contains(str) || !arraySet2.add(str)) {
                    return false;
                }
            }
        }
        return true;
    }
}
