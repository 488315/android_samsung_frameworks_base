package com.android.server.asks;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Map;

public final class PolicyConvert {
    public String TAG;
    public Map pkgInfos;

    public static void MakeString(ArrayList arrayList, String str, String str2, Map map) {
        for (int i = 0; i < arrayList.size(); i++) {
            ArrayList arrayList2 =
                    map.containsKey(arrayList.get(i))
                            ? (ArrayList) map.get(arrayList.get(i))
                            : new ArrayList();
            if ("DELETE".equals(str)) {
                arrayList2.add("        <delete version=\"00000000\" datelimit=\"" + str2 + "\"/>");
            } else {
                arrayList2.add(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "        <restrict version=\"00000000\" type=\"",
                                str,
                                "\" datelimit=\"",
                                str2,
                                "\" from=\"Token\"/>"));
            }
            map.put((String) arrayList.get(i), arrayList2);
        }
    }

    public static String parseToken(String str, String str2) {
        String[] split;
        String[] split2 = str.split(str2);
        if (split2 == null || split2.length != 2 || (split = split2[1].split("\"/>")) == null) {
            return null;
        }
        return split[0];
    }

    /* JADX WARN: Code restructure failed: missing block: B:179:0x039b, code lost:

       if (r0.length() <= 0) goto L473;
    */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x039d, code lost:

       if (r13 == null) goto L474;
    */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x03a3, code lost:

       if (r13.size() <= 0) goto L475;
    */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x03a5, code lost:

       r5.put(r0, r13);
    */
    /* JADX WARN: Code restructure failed: missing block: B:317:0x032c, code lost:

       r6 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x0332, code lost:

       r11 = 0;
       r9 = r9;
    */
    /* JADX WARN: Code restructure failed: missing block: B:362:0x03e4, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x03e5, code lost:

       r0.printStackTrace();
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean convert(java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 1543
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.PolicyConvert.convert(java.lang.String):boolean");
    }
}
