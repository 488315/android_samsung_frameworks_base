package gov.nist.javax.sip.header;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/* loaded from: classes4.dex */
public abstract class SIPHeaderNamesCache {
    public static final HashMap lowercaseMap = new HashMap();

    static {
        for (Field field : SIPHeaderNames.class.getFields()) {
            if (field.getType().equals(String.class) && Modifier.isStatic(field.getModifiers())) {
                try {
                    String str = (String) field.get(null);
                    String lowerCase = str.toLowerCase();
                    HashMap map = lowercaseMap;
                    map.put(str, lowerCase);
                    map.put(lowerCase, lowerCase);
                } catch (IllegalAccessException unused) {
                }
            }
        }
    }

    public static String toLowerCase(String str) {
        String str2 = (String) lowercaseMap.get(str);
        return str2 == null ? str.toLowerCase() : str2;
    }
}
