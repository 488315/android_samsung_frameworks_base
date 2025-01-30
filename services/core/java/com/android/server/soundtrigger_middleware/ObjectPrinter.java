package com.android.server.soundtrigger_middleware;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class ObjectPrinter {
  public static String print(Object obj, int i) {
    StringBuilder sb = new StringBuilder();
    print(sb, obj, i);
    return sb.toString();
  }

  public static void print(StringBuilder sb, Object obj) {
    print(sb, obj, 16);
  }

  public static void print(StringBuilder sb, Object obj, int i) {
    try {
      if (obj == null) {
        sb.append("null");
        return;
      }
      if (obj instanceof Boolean) {
        sb.append(obj);
        return;
      }
      if (obj instanceof Number) {
        sb.append(obj);
        return;
      }
      if (obj instanceof Character) {
        sb.append('\'');
        sb.append(obj);
        sb.append('\'');
        return;
      }
      if (obj instanceof String) {
        sb.append('\"');
        sb.append(obj.toString());
        sb.append('\"');
        return;
      }
      Class<?> cls = obj.getClass();
      boolean z = true;
      if (Collection.class.isAssignableFrom(cls)) {
        Collection collection = (Collection) obj;
        sb.append("[ ");
        int size = collection.size();
        Iterator it = collection.iterator();
        int i2 = 0;
        while (true) {
          if (!it.hasNext()) {
            z = false;
            break;
          }
          Object next = it.next();
          if (i2 > 0) {
            sb.append(", ");
          }
          if (i2 >= i) {
            break;
          }
          print(sb, next, i);
          i2++;
        }
        if (z) {
          sb.append("... (+");
          sb.append(size - i);
          sb.append(" entries)");
        }
        sb.append(" ]");
        return;
      }
      if (Map.class.isAssignableFrom(cls)) {
        Map map = (Map) obj;
        sb.append("< ");
        int size2 = map.size();
        Iterator it2 = map.entrySet().iterator();
        int i3 = 0;
        while (true) {
          if (!it2.hasNext()) {
            z = false;
            break;
          }
          Map.Entry entry = (Map.Entry) it2.next();
          if (i3 > 0) {
            sb.append(", ");
          }
          if (i3 >= i) {
            break;
          }
          print(sb, entry.getKey(), i);
          sb.append(": ");
          print(sb, entry.getValue(), i);
          i3++;
        }
        if (z) {
          sb.append("... (+");
          sb.append(size2 - i);
          sb.append(" entries)");
        }
        sb.append(" >");
        return;
      }
      if (cls.isArray()) {
        sb.append("[ ");
        int length = Array.getLength(obj);
        int i4 = 0;
        while (true) {
          if (i4 >= length) {
            z = false;
            break;
          }
          if (i4 > 0) {
            sb.append(", ");
          }
          if (i4 >= i) {
            break;
          }
          print(sb, Array.get(obj, i4), i);
          i4++;
        }
        if (z) {
          sb.append("... (+");
          sb.append(length - i);
          sb.append(" entries)");
        }
        sb.append(" ]");
        return;
      }
      sb.append(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
