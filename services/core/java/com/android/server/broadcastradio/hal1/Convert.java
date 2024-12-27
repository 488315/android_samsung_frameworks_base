package com.android.server.broadcastradio.hal1;

import com.android.server.utils.Slogf;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Set;

public final class Convert {
    public static final String TAG = "BcRadio1Srv.Convert";

    public static String[][] stringMapToNative(Map map) {
        if (map == null) {
            Slogf.v(TAG, "map is null, returning zero-elements array");
            return (String[][]) Array.newInstance((Class<?>) String.class, 0, 0);
        }
        Set<Map.Entry> entrySet = map.entrySet();
        String[][] strArr =
                (String[][]) Array.newInstance((Class<?>) String.class, entrySet.size(), 2);
        int i = 0;
        for (Map.Entry entry : entrySet) {
            strArr[i][0] = (String) entry.getKey();
            strArr[i][1] = (String) entry.getValue();
            i++;
        }
        Slogf.v(TAG, "converted " + i + " element(s)");
        return strArr;
    }
}
