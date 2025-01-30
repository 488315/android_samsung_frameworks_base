package com.samsung.android.server.packagefeature.core;

import com.samsung.android.server.packagefeature.PackageFeatureData;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
class PackageFeatureRawData extends PackageFeatureData implements Serializable {
    public static boolean sShowPackageName = false;
    private static final long serialVersionUID = 202206080250L;
    private final ConcurrentHashMap mSerial = new ConcurrentHashMap();

    public String put(String str, String str2, String str3) {
        if (str == null) {
            str = "null";
        }
        this.mSerial.put(str2, str);
        return super.put(str2, str3);
    }

    public void dump(PrintWriter printWriter, String str) {
        Iterator it = entrySet().iterator();
        int i = sShowPackageName ? 5 : 10;
        int i2 = 0;
        while (it.hasNext()) {
            int i3 = i2 + 1;
            if (i2 % i == 0) {
                printWriter.println();
                printWriter.print(str);
            }
            Map.Entry entry = (Map.Entry) it.next();
            String str2 = (String) entry.getKey();
            if (!sShowPackageName) {
                str2 = (String) this.mSerial.get(str2);
            }
            String str3 = (String) entry.getValue();
            printWriter.print(str2);
            if (!PackageFeatureData.EMPTY_STRING.equals(str3)) {
                printWriter.print("(" + str3 + ")");
            }
            if (it.hasNext()) {
                printWriter.print(", ");
            }
            i2 = i3;
        }
        printWriter.println();
    }
}
