package com.android.server.asks;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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

    /* JADX WARN: Can't wrap try/catch for region: R(15:0|1|(5:2|3|(5:5|7|8|9|(3:10|11|(7:13|(5:15|16|17|18|(4:433|434|435|436)(4:20|21|22|23))(1:446)|24|25|(1:27)(2:49|(2:51|52))|(8:33|34|35|(3:37|38|39)(1:48)|40|(2:42|(1:44)(1:45))|46|47)(3:29|30|31)|32)(2:447|448)))(1:476)|(2:412|413)|(2:55|56))|(3:59|(7:62|(1:64)(1:71)|65|(2:68|66)|69|70|60)|72)(0)|73|74|(2:75|76)|(18:(27:78|80|81|82|(3:83|84|(1:369)(3:86|(3:108|109|(2:111|112)(1:113))(3:88|89|(3:105|106|107)(3:91|92|(5:94|95|(2:97|(2:99|100))|101|102)(1:104)))|103))|(2:364|365)|(2:117|118)|(2:121|(2:122|(3:124|(2:126|127)(2:129|130)|128)(1:131)))(0)|132|133|134|135|(5:137|139|140|141|(3:142|(3:143|144|(5:146|(3:201|202|(1:1)(3:206|207|208))(2:148|(2:199|200)(2:150|(2:197|198)(2:152|(4:189|190|(1:193)|(1:196))(3:154|(5:156|157|(1:162)|163|164)(3:166|167|(1:177)(2:169|(3:171|172|173)(1:174)))|165))))|175|176|165)(2:328|329))|185))(1:355)|(2:322|323)|(2:321|216)|(4:219|(3:221|(2:224|222)|225)|226|(3:228|(2:231|229)|232))|233|(4:236|(2:239|237)|240|234)|241|242|243|244|(7:246|247|248|(5:251|(2:254|252)|255|256|249)|257|258|259)(1:306)|(2:280|281)|(10:262|(1:264)|265|(1:267)|268|(1:270)|271|(1:273)|274|(1:276))|277|278)(1:403)|134|135|(0)(0)|(0)|(0)|(0)|233|(1:234)|241|242|243|244|(0)(0)|(0)|(0)|277|278)|114|(0)|(0)|(0)(0)|132|133|(1:(0))) */
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
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:117:0x026d A[Catch: IOException -> 0x0271, TRY_ENTER, TRY_LEAVE, TryCatch #8 {IOException -> 0x0271, blocks: (B:117:0x026d, B:376:0x0289), top: B:75:0x01e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02eb A[Catch: all -> 0x03c6, IOException -> 0x03ca, TRY_LEAVE, TryCatch #45 {IOException -> 0x03ca, all -> 0x03c6, blocks: (B:135:0x02e0, B:137:0x02eb), top: B:134:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x03fc A[Catch: IOException -> 0x03e4, TRY_ENTER, TRY_LEAVE, TryCatch #40 {IOException -> 0x03e4, blocks: (B:215:0x03fc, B:321:0x03e0), top: B:133:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0401  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0465  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x04ac A[Catch: all -> 0x053d, IOException -> 0x0541, TRY_LEAVE, TryCatch #44 {IOException -> 0x0541, all -> 0x053d, blocks: (B:244:0x04a6, B:246:0x04ac), top: B:243:0x04a6 }] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0568  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0548 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:299:0x05be A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0544  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x03f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:321:0x03e0 A[Catch: IOException -> 0x03e4, TRY_ENTER, TRY_LEAVE, TryCatch #40 {IOException -> 0x03e4, blocks: (B:215:0x03fc, B:321:0x03e0), top: B:133:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:322:0x03d5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:342:0x05cf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:349:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:350:0x05c4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:355:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0262 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:376:0x0289 A[Catch: IOException -> 0x0271, TRY_ENTER, TRY_LEAVE, TryCatch #8 {IOException -> 0x0271, blocks: (B:117:0x026d, B:376:0x0289), top: B:75:0x01e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:377:0x027e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:403:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x015b A[Catch: IOException -> 0x0143, TRY_ENTER, TRY_LEAVE, TryCatch #14 {IOException -> 0x0143, blocks: (B:55:0x013f, B:423:0x015b), top: B:2:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:424:0x0150 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:454:0x05fd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:461:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:462:0x05f2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01eb A[Catch: all -> 0x0256, IOException -> 0x025a, TRY_LEAVE, TryCatch #42 {IOException -> 0x025a, all -> 0x0256, blocks: (B:76:0x01e0, B:78:0x01eb), top: B:75:0x01e0 }] */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v17 */
    /* JADX WARN: Type inference failed for: r10v18, types: [int] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v30 */
    /* JADX WARN: Type inference failed for: r10v34 */
    /* JADX WARN: Type inference failed for: r10v35 */
    /* JADX WARN: Type inference failed for: r10v36, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r10v37, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r10v38 */
    /* JADX WARN: Type inference failed for: r10v39 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v40, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r10v41 */
    /* JADX WARN: Type inference failed for: r10v42 */
    /* JADX WARN: Type inference failed for: r10v45 */
    /* JADX WARN: Type inference failed for: r10v46 */
    /* JADX WARN: Type inference failed for: r10v47, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v48 */
    /* JADX WARN: Type inference failed for: r10v49 */
    /* JADX WARN: Type inference failed for: r10v51 */
    /* JADX WARN: Type inference failed for: r10v53 */
    /* JADX WARN: Type inference failed for: r10v54 */
    /* JADX WARN: Type inference failed for: r10v56 */
    /* JADX WARN: Type inference failed for: r10v57, types: [int] */
    /* JADX WARN: Type inference failed for: r10v60 */
    /* JADX WARN: Type inference failed for: r10v61 */
    /* JADX WARN: Type inference failed for: r10v62 */
    /* JADX WARN: Type inference failed for: r10v63 */
    /* JADX WARN: Type inference failed for: r10v64 */
    /* JADX WARN: Type inference failed for: r10v65 */
    /* JADX WARN: Type inference failed for: r10v66 */
    /* JADX WARN: Type inference failed for: r10v67 */
    /* JADX WARN: Type inference failed for: r10v68 */
    /* JADX WARN: Type inference failed for: r10v69 */
    /* JADX WARN: Type inference failed for: r10v70 */
    /* JADX WARN: Type inference failed for: r10v71 */
    /* JADX WARN: Type inference failed for: r10v72 */
    /* JADX WARN: Type inference failed for: r10v73 */
    /* JADX WARN: Type inference failed for: r10v75 */
    /* JADX WARN: Type inference failed for: r10v76 */
    /* JADX WARN: Type inference failed for: r10v77 */
    /* JADX WARN: Type inference failed for: r10v78 */
    /* JADX WARN: Type inference failed for: r10v79 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v80 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v21 */
    /* JADX WARN: Type inference failed for: r11v22 */
    /* JADX WARN: Type inference failed for: r11v23 */
    /* JADX WARN: Type inference failed for: r11v24 */
    /* JADX WARN: Type inference failed for: r11v29 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v32 */
    /* JADX WARN: Type inference failed for: r11v35 */
    /* JADX WARN: Type inference failed for: r11v36 */
    /* JADX WARN: Type inference failed for: r11v37 */
    /* JADX WARN: Type inference failed for: r11v38 */
    /* JADX WARN: Type inference failed for: r11v39 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v40 */
    /* JADX WARN: Type inference failed for: r11v41 */
    /* JADX WARN: Type inference failed for: r11v42 */
    /* JADX WARN: Type inference failed for: r11v43, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v44 */
    /* JADX WARN: Type inference failed for: r11v45 */
    /* JADX WARN: Type inference failed for: r11v46 */
    /* JADX WARN: Type inference failed for: r11v47 */
    /* JADX WARN: Type inference failed for: r11v48 */
    /* JADX WARN: Type inference failed for: r11v49 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v55 */
    /* JADX WARN: Type inference failed for: r11v56 */
    /* JADX WARN: Type inference failed for: r11v57, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v58 */
    /* JADX WARN: Type inference failed for: r11v59 */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r11v60 */
    /* JADX WARN: Type inference failed for: r11v61 */
    /* JADX WARN: Type inference failed for: r11v62 */
    /* JADX WARN: Type inference failed for: r11v63 */
    /* JADX WARN: Type inference failed for: r11v64 */
    /* JADX WARN: Type inference failed for: r11v65 */
    /* JADX WARN: Type inference failed for: r11v66 */
    /* JADX WARN: Type inference failed for: r11v67 */
    /* JADX WARN: Type inference failed for: r11v68 */
    /* JADX WARN: Type inference failed for: r11v69 */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v70 */
    /* JADX WARN: Type inference failed for: r11v72 */
    /* JADX WARN: Type inference failed for: r11v73 */
    /* JADX WARN: Type inference failed for: r11v74 */
    /* JADX WARN: Type inference failed for: r11v75 */
    /* JADX WARN: Type inference failed for: r11v76 */
    /* JADX WARN: Type inference failed for: r11v77 */
    /* JADX WARN: Type inference failed for: r11v78 */
    /* JADX WARN: Type inference failed for: r11v79 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r11v80 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8, types: [boolean] */
    /* JADX WARN: Type inference failed for: r13v9 */
    /* JADX WARN: Type inference failed for: r14v16, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r14v21, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v10, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v22, types: [int] */
    /* JADX WARN: Type inference failed for: r9v27, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v4, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r9v43, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v46 */
    /* JADX WARN: Type inference failed for: r9v48 */
    /* JADX WARN: Type inference failed for: r9v49, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r9v50 */
    /* JADX WARN: Type inference failed for: r9v51, types: [java.io.FileReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r9v55, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r9v73 */
    /* JADX WARN: Type inference failed for: r9v74 */
    /* JADX WARN: Type inference failed for: r9v78 */
    /* JADX WARN: Type inference failed for: r9v85 */
    /* JADX WARN: Type inference failed for: r9v86 */
    /* JADX WARN: Type inference failed for: r9v88 */
    /* JADX WARN: Type inference failed for: r9v89 */
    /* JADX WARN: Type inference failed for: r9v9 */
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
