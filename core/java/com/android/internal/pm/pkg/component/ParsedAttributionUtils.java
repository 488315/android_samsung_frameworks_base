package com.android.internal.pm.pkg.component;

import android.util.ArraySet;
import java.util.List;

/* loaded from: classes5.dex */
public class ParsedAttributionUtils {
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b0, code lost:
    
        if (r0 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b2, code lost:
    
        r0 = java.util.Collections.emptyList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c6, code lost:
    
        return r13.success(new com.android.internal.pm.pkg.component.ParsedAttributionImpl(r4, r5, r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b7, code lost:
    
        ((java.util.ArrayList) r0).trimToSize();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedAttribution> parseAttribution(android.content.res.Resources r11, android.content.res.XmlResourceParser r12, android.content.pm.parsing.result.ParseInput r13) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            r0 = 0
            int[] r1 = com.android.internal.R.styleable.AndroidManifestAttribution
            android.content.res.TypedArray r1 = r11.obtainAttributes(r12, r1)
            if (r1 != 0) goto L10
            java.lang.String r2 = "<attribution> could not be parsed"
            android.content.pm.parsing.result.ParseResult r2 = r13.error(r2)
            return r2
        L10:
            r2 = 1
            r3 = 0
            java.lang.String r4 = r1.getNonConfigurationString(r2, r3)     // Catch: java.lang.Throwable -> Lc7
            if (r4 != 0) goto L22
            java.lang.String r2 = "<attribution> does not specify android:tag"
            android.content.pm.parsing.result.ParseResult r2 = r13.error(r2)     // Catch: java.lang.Throwable -> Lc7
            r1.recycle()
            return r2
        L22:
            int r5 = r4.length()     // Catch: java.lang.Throwable -> Lc7
            r6 = 50
            if (r5 <= r6) goto L34
            java.lang.String r2 = "android:tag is too long. Max length is 50"
            android.content.pm.parsing.result.ParseResult r2 = r13.error(r2)     // Catch: java.lang.Throwable -> Lc7
            r1.recycle()
            return r2
        L34:
            int r5 = r1.getResourceId(r3, r3)     // Catch: java.lang.Throwable -> Lc7
            if (r5 != 0) goto L44
            java.lang.String r2 = "<attribution> does not specify android:label"
            android.content.pm.parsing.result.ParseResult r2 = r13.error(r2)     // Catch: java.lang.Throwable -> Lc7
            r1.recycle()
            return r2
        L44:
            r1.recycle()
            int r6 = r12.getDepth()
        L4c:
            int r7 = r12.next()
            r8 = r7
            if (r7 == r2) goto Lb0
            r7 = 3
            if (r8 != r7) goto L5c
            int r9 = r12.getDepth()
            if (r9 <= r6) goto Lb0
        L5c:
            if (r8 == r7) goto L4c
            r7 = 4
            if (r8 != r7) goto L62
            goto L4c
        L62:
            java.lang.String r7 = r12.getName()
            java.lang.String r9 = "inherit-from"
            boolean r9 = r7.equals(r9)
            if (r9 == 0) goto L98
            int[] r9 = com.android.internal.R.styleable.AndroidManifestAttributionInheritFrom
            android.content.res.TypedArray r1 = r11.obtainAttributes(r12, r9)
            if (r1 != 0) goto L7e
            java.lang.String r2 = "<inherit-from> could not be parsed"
            android.content.pm.parsing.result.ParseResult r2 = r13.error(r2)
            return r2
        L7e:
            java.lang.String r9 = r1.getNonConfigurationString(r3, r3)     // Catch: java.lang.Throwable -> L93
            if (r0 != 0) goto L8a
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L93
            r10.<init>()     // Catch: java.lang.Throwable -> L93
            r0 = r10
        L8a:
            r0.add(r9)     // Catch: java.lang.Throwable -> L93
            r1.recycle()
            goto L4c
        L93:
            r2 = move-exception
            r1.recycle()
            throw r2
        L98:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Bad element under <attribution>: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r2 = r2.toString()
            android.content.pm.parsing.result.ParseResult r2 = r13.error(r2)
            return r2
        Lb0:
            if (r0 != 0) goto Lb7
            java.util.List r0 = java.util.Collections.emptyList()
            goto Lbd
        Lb7:
            r2 = r0
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            r2.trimToSize()
        Lbd:
            com.android.internal.pm.pkg.component.ParsedAttributionImpl r2 = new com.android.internal.pm.pkg.component.ParsedAttributionImpl
            r2.<init>(r4, r5, r0)
            android.content.pm.parsing.result.ParseResult r2 = r13.success(r2)
            return r2
        Lc7:
            r2 = move-exception
            r1.recycle()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedAttributionUtils.parseAttribution(android.content.res.Resources, android.content.res.XmlResourceParser, android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    public static boolean isCombinationValid(List<ParsedAttribution> attributions) {
        if (attributions == null) {
            return true;
        }
        ArraySet<String> attributionTags = new ArraySet<>(attributions.size());
        ArraySet<String> inheritFromAttributionTags = new ArraySet<>();
        int numAttributions = attributions.size();
        if (numAttributions > 400) {
            return false;
        }
        for (int attributionNum = 0; attributionNum < numAttributions; attributionNum++) {
            boolean wasAdded = attributionTags.add(attributions.get(attributionNum).getTag());
            if (!wasAdded) {
                return false;
            }
        }
        for (int attributionNum2 = 0; attributionNum2 < numAttributions; attributionNum2++) {
            ParsedAttribution feature = attributions.get(attributionNum2);
            List<String> inheritFromList = feature.getInheritFrom();
            int numInheritFrom = inheritFromList.size();
            for (int inheritFromNum = 0; inheritFromNum < numInheritFrom; inheritFromNum++) {
                String inheritFrom = inheritFromList.get(inheritFromNum);
                if (attributionTags.contains(inheritFrom)) {
                    return false;
                }
                boolean wasAdded2 = inheritFromAttributionTags.add(inheritFrom);
                if (!wasAdded2) {
                    return false;
                }
            }
        }
        return true;
    }
}
