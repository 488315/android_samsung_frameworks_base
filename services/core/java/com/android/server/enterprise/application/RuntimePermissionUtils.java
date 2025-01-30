package com.android.server.enterprise.application;

import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class RuntimePermissionUtils {
    public Integer getStrictestPermStateInDb(String str, List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Iterator it = list.iterator();
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        while (it.hasNext()) {
            String[] strArr = (String[]) it.next();
            int parseInt = Integer.parseInt(strArr[0]);
            int parseInt2 = Integer.parseInt(strArr[1]);
            String str2 = strArr[2];
            if (str2.contains(str) && (i == -1 || i < parseInt2)) {
                i3 = parseInt;
                i = parseInt2;
            }
            if (str2.equals("*") && (i2 == -1 || i2 < parseInt2)) {
                i4 = parseInt;
                i2 = parseInt2;
            }
        }
        if (i == -1) {
            if (i2 != -1) {
                return Integer.valueOf(i2);
            }
            return null;
        }
        if (i2 == -1) {
            return Integer.valueOf(i);
        }
        if (i3 == i4) {
            return Integer.valueOf(i);
        }
        return Integer.valueOf(Math.max(i, i2));
    }

    public String[] createEntry(int i, int i2, String str) {
        return new String[]{String.valueOf(i), String.valueOf(i2), str};
    }
}
