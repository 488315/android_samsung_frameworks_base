package com.google.protobuf.nano;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MessageNanoPrinter {
    private MessageNanoPrinter() {
    }

    public static String deCamelCaseify(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (i == 0) {
                stringBuffer.append(Character.toLowerCase(charAt));
            } else if (Character.isUpperCase(charAt)) {
                stringBuffer.append('_');
                stringBuffer.append(Character.toLowerCase(charAt));
            } else {
                stringBuffer.append(charAt);
            }
        }
        return stringBuffer.toString();
    }

    public static void print(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        if (obj == null) {
            return;
        }
        int i = 0;
        if (obj instanceof MessageNano) {
            int length = stringBuffer.length();
            if (str != null) {
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(deCamelCaseify(str));
                stringBuffer2.append(" <\n");
                stringBuffer.append("  ");
            }
            Class<?> cls = obj.getClass();
            for (Field field : cls.getFields()) {
                int modifiers = field.getModifiers();
                String name = field.getName();
                if (!"cachedSize".equals(name) && (modifiers & 1) == 1 && (modifiers & 8) != 8 && !name.startsWith("_") && !name.endsWith("_")) {
                    Class<?> type = field.getType();
                    Object obj2 = field.get(obj);
                    if (!type.isArray()) {
                        print(name, obj2, stringBuffer, stringBuffer2);
                    } else if (type.getComponentType() == Byte.TYPE) {
                        print(name, obj2, stringBuffer, stringBuffer2);
                    } else {
                        int length2 = obj2 == null ? 0 : Array.getLength(obj2);
                        for (int i2 = 0; i2 < length2; i2++) {
                            print(name, Array.get(obj2, i2), stringBuffer, stringBuffer2);
                        }
                    }
                }
            }
            for (Method method : cls.getMethods()) {
                String name2 = method.getName();
                if (name2.startsWith("set")) {
                    String substring = name2.substring(3);
                    try {
                        if (((Boolean) cls.getMethod("has" + substring, new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                            print(substring, cls.getMethod("get" + substring, new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                        }
                    } catch (NoSuchMethodException unused) {
                    }
                }
            }
            if (str != null) {
                stringBuffer.setLength(length);
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(">\n");
                return;
            }
            return;
        }
        if (obj instanceof Map) {
            String deCamelCaseify = deCamelCaseify(str);
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(deCamelCaseify);
                stringBuffer2.append(" <\n");
                int length3 = stringBuffer.length();
                stringBuffer.append("  ");
                print("key", entry.getKey(), stringBuffer, stringBuffer2);
                print("value", entry.getValue(), stringBuffer, stringBuffer2);
                stringBuffer.setLength(length3);
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(">\n");
            }
            return;
        }
        String deCamelCaseify2 = deCamelCaseify(str);
        stringBuffer2.append(stringBuffer);
        stringBuffer2.append(deCamelCaseify2);
        stringBuffer2.append(": ");
        if (obj instanceof String) {
            String str2 = (String) obj;
            if (!str2.startsWith("http") && str2.length() > 200) {
                str2 = str2.substring(0, 200) + "[...]";
            }
            int length4 = str2.length();
            StringBuilder sb = new StringBuilder(length4);
            while (i < length4) {
                char charAt = str2.charAt(i);
                if (charAt < ' ' || charAt > '~' || charAt == '\"' || charAt == '\'') {
                    sb.append(String.format("\\u%04x", Integer.valueOf(charAt)));
                } else {
                    sb.append(charAt);
                }
                i++;
            }
            String sb2 = sb.toString();
            stringBuffer2.append("\"");
            stringBuffer2.append(sb2);
            stringBuffer2.append("\"");
        } else if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            stringBuffer2.append('\"');
            while (i < bArr.length) {
                int i3 = bArr[i] & 255;
                if (i3 == 92 || i3 == 34) {
                    stringBuffer2.append('\\');
                    stringBuffer2.append((char) i3);
                } else if (i3 < 32 || i3 >= 127) {
                    stringBuffer2.append(String.format("\\%03o", Integer.valueOf(i3)));
                } else {
                    stringBuffer2.append((char) i3);
                }
                i++;
            }
            stringBuffer2.append('\"');
        } else {
            stringBuffer2.append(obj);
        }
        stringBuffer2.append("\n");
    }
}
