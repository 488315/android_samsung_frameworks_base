package gov.nist.javax.sip.header;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SIPHeaderNamesCache {
    public static final HashMap lowercaseMap = new HashMap();

    static {
        for (Field field : SIPHeaderNames.class.getFields()) {
            if (field.getType().equals(String.class) && Modifier.isStatic(field.getModifiers())) {
                try {
                    String str = (String) field.get(null);
                    String lowerCase = str.toLowerCase();
                    HashMap hashMap = lowercaseMap;
                    hashMap.put(str, lowerCase);
                    hashMap.put(lowerCase, lowerCase);
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
