package com.android.server;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class CommonNativeResetReasonCode extends ResetReasonCode {
    @Override // com.android.server.ResetReasonCode
    public List addCauseStackFromContexts(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        String str = "";
        String str2 = str;
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (str3.startsWith("signal")) {
                StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ",");
                m.append(str3.split(",")[0]);
                str = m.toString();
            } else if (str3.startsWith("Abort message") || str3.startsWith("#")) {
                str2 = AnyMotionDetector$$ExternalSyntheticOutline0.m(str2, str3, "\n");
            } else if (str3.startsWith("pid: ") && str3.indexOf("  >>>") != -1) {
                String trim =
                        str3.substring(str3.indexOf(">>>"), str3.length())
                                .replace(">>>", "")
                                .replace("<<<", "")
                                .trim();
                String[] split =
                        (str3.substring(0, str3.indexOf("  >>>")).replace(":", ",")
                                        + "("
                                        + trim
                                        + ")")
                                .split(", ");
                if (split.length >= 6) {
                    StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, ",");
                    m2.append(split[5]);
                    str = m2.toString();
                }
            }
        }
        arrayList.add(str);
        arrayList.add(str2);
        return arrayList;
    }

    @Override // com.android.server.ResetReasonCode
    public String addSuffix() {
        return "sys_native";
    }
}
