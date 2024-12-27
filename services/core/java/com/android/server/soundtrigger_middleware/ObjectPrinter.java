package com.android.server.soundtrigger_middleware;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ObjectPrinter {
    public static String print(Object obj) {
        StringBuilder sb = new StringBuilder();
        print$1(sb, obj);
        return sb.toString();
    }

    public static void print$1(StringBuilder sb, Object obj) {
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
            int i = 0;
            if (Collection.class.isAssignableFrom(cls)) {
                Collection collection = (Collection) obj;
                sb.append("[ ");
                int size = collection.size();
                Iterator it = collection.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    if (i > 0) {
                        sb.append(", ");
                    }
                    if (i >= 16) {
                        sb.append("... (+");
                        sb.append(size - 16);
                        sb.append(" entries)");
                        break;
                    }
                    print$1(sb, next);
                    i++;
                }
                sb.append(" ]");
                return;
            }
            if (!Map.class.isAssignableFrom(cls)) {
                if (!cls.isArray()) {
                    sb.append(obj);
                    return;
                }
                sb.append("[ ");
                int length = Array.getLength(obj);
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    if (i > 0) {
                        sb.append(", ");
                    }
                    if (i >= 16) {
                        sb.append("... (+");
                        sb.append(length - 16);
                        sb.append(" entries)");
                        break;
                    }
                    print$1(sb, Array.get(obj, i));
                    i++;
                }
                sb.append(" ]");
                return;
            }
            Map map = (Map) obj;
            sb.append("< ");
            int size2 = map.size();
            Iterator it2 = map.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it2.next();
                if (i > 0) {
                    sb.append(", ");
                }
                if (i >= 16) {
                    sb.append("... (+");
                    sb.append(size2 - 16);
                    sb.append(" entries)");
                    break;
                } else {
                    print$1(sb, entry.getKey());
                    sb.append(": ");
                    print$1(sb, entry.getValue());
                    i++;
                }
            }
            sb.append(" >");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
