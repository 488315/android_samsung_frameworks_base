package gov.nist.javax.sip.header;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
