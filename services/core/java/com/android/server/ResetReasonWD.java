package com.android.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
class ResetReasonWD extends CommonPlatformResetReasonCode {
    @Override // com.android.server.CommonPlatformResetReasonCode,
              // com.android.server.ResetReasonCode
    public List addCauseStackFromContexts(List list) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder("");
        StringBuilder sb2 = new StringBuilder("");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str.startsWith("!@")) {
                str = str.substring(2);
            }
            if (str.startsWith("*** FATAL")) {
                Matcher matcher = getCurrentPattern().matcher(str);
                if (matcher.matches()) {
                    sb.append(matcher.group(1));
                } else {
                    sb.append(str);
                }
            } else if (str.endsWith("annotated stack trace:") && str.indexOf("annotated") >= 2) {
                sb.append(str.substring(0, str.indexOf("annotated") - 1).trim());
                sb.append(",");
                sb2.append(str.substring(0, str.indexOf("annotated") - 1).trim());
                sb2.append(":\n");
            } else if (str.startsWith("    at")) {
                sb2.append(str.trim());
                sb2.append("\n");
            }
        }
        arrayList.add(sb.toString());
        arrayList.add(sb2.toString());
        return arrayList;
    }

    @Override // com.android.server.ResetReasonCode
    public String addSuffix() {
        return "sys_watchdog";
    }

    @Override // com.android.server.CommonPlatformResetReasonCode,
              // com.android.server.ResetReasonCode
    public Pattern getPatternByReason() {
        Pattern compile = Pattern.compile("PLATFORM WATCHDOG RESET");
        this.pattern = compile;
        return compile;
    }
}
