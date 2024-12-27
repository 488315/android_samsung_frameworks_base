package com.android.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class CommonPlatformResetReasonCode extends ResetReasonCode {
    @Override // com.android.server.ResetReasonCode
    public List addCauseStackFromContexts(List list) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder("");
        StringBuilder sb2 = new StringBuilder("");
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            if (str.startsWith("!@*** FATAL")) {
                Matcher matcher = getCurrentPattern().matcher(str);
                if (matcher.matches()) {
                    sb.append(matcher.group(1));
                } else {
                    sb.append(str);
                }
            } else {
                if (str.contains(
                        "NPE by silent reset. It's normal operation caused by device care")) {
                    sb2.append("devicecare");
                    break;
                }
                if (str.contains("HeapFull")) {
                    sb2.append("HeapFull");
                    break;
                }
                sb2.append(str.trim());
                sb2.append("\n");
            }
        }
        arrayList.add(sb.toString());
        arrayList.add(sb2.toString());
        return arrayList;
    }

    @Override // com.android.server.ResetReasonCode
    public Pattern getPatternByReason() {
        Pattern compile = Pattern.compile(".*FATAL EXCEPTION IN SYSTEM PROCESS: (.*)");
        this.pattern = compile;
        return compile;
    }
}
