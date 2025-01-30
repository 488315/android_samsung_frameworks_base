package com.android.server.broadcastradio.hal1;

import android.util.Slog;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class Convert {
  public static final String TAG = "BcRadio1Srv.Convert";

  public static String[][] stringMapToNative(Map map) {
    if (map == null) {
      Slog.v(TAG, "map is null, returning zero-elements array");
      return (String[][]) Array.newInstance((Class<?>) String.class, 0, 0);
    }
    Set<Map.Entry> entrySet = map.entrySet();
    String[][] strArr = (String[][]) Array.newInstance((Class<?>) String.class, entrySet.size(), 2);
    int i = 0;
    for (Map.Entry entry : entrySet) {
      strArr[i][0] = (String) entry.getKey();
      strArr[i][1] = (String) entry.getValue();
      i++;
    }
    Slog.v(TAG, "converted " + i + " element(s)");
    return strArr;
  }
}
