package com.android.internal.util;

import android.app.slice.SliceItem;
import android.hardware.scontext.SContextConstants;
import android.widget.SemRemoteViewsValueAnimation;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class TypedProperties extends HashMap<String, Object> {
    static final String NULL_STRING = new String("<TypedProperties:NULL_STRING>");
    public static final int STRING_NOT_SET = -1;
    public static final int STRING_NULL = 0;
    public static final int STRING_SET = 1;
    public static final int STRING_TYPE_MISMATCH = -2;
    static final int TYPE_BOOLEAN = 90;
    static final int TYPE_BYTE = 329;
    static final int TYPE_DOUBLE = 2118;
    static final int TYPE_ERROR = -1;
    static final int TYPE_FLOAT = 1094;
    static final int TYPE_INT = 1097;
    static final int TYPE_LONG = 2121;
    static final int TYPE_SHORT = 585;
    static final int TYPE_STRING = 29516;
    static final int TYPE_UNSET = 120;

    static StreamTokenizer initTokenizer(Reader reader) {
        StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
        streamTokenizer.resetSyntax();
        streamTokenizer.wordChars(48, 57);
        streamTokenizer.wordChars(65, 90);
        streamTokenizer.wordChars(97, 122);
        streamTokenizer.wordChars(95, 95);
        streamTokenizer.wordChars(36, 36);
        streamTokenizer.wordChars(46, 46);
        streamTokenizer.wordChars(45, 45);
        streamTokenizer.wordChars(43, 43);
        streamTokenizer.ordinaryChar(61);
        streamTokenizer.whitespaceChars(32, 32);
        streamTokenizer.whitespaceChars(9, 9);
        streamTokenizer.whitespaceChars(10, 10);
        streamTokenizer.whitespaceChars(13, 13);
        streamTokenizer.quoteChar(34);
        streamTokenizer.slashStarComments(true);
        streamTokenizer.slashSlashComments(true);
        return streamTokenizer;
    }

    public static class ParseException extends IllegalArgumentException {
        ParseException(StreamTokenizer streamTokenizer, String str) {
            super("expected " + str + ", saw " + streamTokenizer.toString());
        }
    }

    static int interpretType(String str) {
        if ("unset".equals(str)) {
            return 120;
        }
        if ("boolean".equals(str)) {
            return 90;
        }
        if ("byte".equals(str)) {
            return 329;
        }
        if ("short".equals(str)) {
            return 585;
        }
        if ("int".equals(str)) {
            return 1097;
        }
        if (SliceItem.FORMAT_LONG.equals(str)) {
            return 2121;
        }
        if (SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT.equals(str)) {
            return 1094;
        }
        if ("double".equals(str)) {
            return 2118;
        }
        if ("String".equals(str)) {
            return TYPE_STRING;
        }
        return -1;
    }

    static void parse(Reader reader, Map<String, Object> map) throws ParseException, IOException, NumberFormatException {
        StreamTokenizer streamTokenizerInitTokenizer = initTokenizer(reader);
        Pattern patternCompile = Pattern.compile("([a-zA-Z_$][0-9a-zA-Z_$]*\\.)*[a-zA-Z_$][0-9a-zA-Z_$]*");
        do {
            int iNextToken = streamTokenizerInitTokenizer.nextToken();
            if (iNextToken == -1) {
                return;
            }
            if (iNextToken != -3) {
                throw new ParseException(streamTokenizerInitTokenizer, "type name");
            }
            int iInterpretType = interpretType(streamTokenizerInitTokenizer.sval);
            if (iInterpretType == -1) {
                throw new ParseException(streamTokenizerInitTokenizer, "valid type name");
            }
            streamTokenizerInitTokenizer.sval = null;
            if (iInterpretType == 120 && streamTokenizerInitTokenizer.nextToken() != 40) {
                throw new ParseException(streamTokenizerInitTokenizer, "'('");
            }
            if (streamTokenizerInitTokenizer.nextToken() != -3) {
                throw new ParseException(streamTokenizerInitTokenizer, "property name");
            }
            String str = streamTokenizerInitTokenizer.sval;
            if (!patternCompile.matcher(str).matches()) {
                throw new ParseException(streamTokenizerInitTokenizer, "valid property name");
            }
            streamTokenizerInitTokenizer.sval = null;
            if (iInterpretType == 120) {
                if (streamTokenizerInitTokenizer.nextToken() != 41) {
                    throw new ParseException(streamTokenizerInitTokenizer, "')'");
                }
                map.remove(str);
            } else {
                if (streamTokenizerInitTokenizer.nextToken() != 61) {
                    throw new ParseException(streamTokenizerInitTokenizer, "'='");
                }
                Object value = parseValue(streamTokenizerInitTokenizer, iInterpretType);
                Object objRemove = map.remove(str);
                if (objRemove != null && value.getClass() != objRemove.getClass()) {
                    throw new ParseException(streamTokenizerInitTokenizer, "(property previously declared as a different type)");
                }
                map.put(str, value);
            }
        } while (streamTokenizerInitTokenizer.nextToken() == 59);
        throw new ParseException(streamTokenizerInitTokenizer, "';'");
    }

    static Object parseValue(StreamTokenizer streamTokenizer, int i) throws IOException, NumberFormatException {
        int iNextToken = streamTokenizer.nextToken();
        if (i == 90) {
            if (iNextToken != -3) {
                throw new ParseException(streamTokenizer, "boolean constant");
            }
            if ("true".equals(streamTokenizer.sval)) {
                return Boolean.TRUE;
            }
            if ("false".equals(streamTokenizer.sval)) {
                return Boolean.FALSE;
            }
            throw new ParseException(streamTokenizer, "boolean constant");
        }
        int i2 = i & 255;
        if (i2 != 73) {
            if (i2 != 70) {
                if (i != TYPE_STRING) {
                    throw new IllegalStateException("Internal error; unknown type " + i);
                }
                if (iNextToken == 34) {
                    return streamTokenizer.sval;
                }
                if (iNextToken == -3 && PerfettoProtoLogImpl.NULL_STRING.equals(streamTokenizer.sval)) {
                    return NULL_STRING;
                }
                throw new ParseException(streamTokenizer, "double-quoted string or 'null'");
            }
            if (iNextToken != -3) {
                throw new ParseException(streamTokenizer, "float constant");
            }
            try {
                double d = Double.parseDouble(streamTokenizer.sval);
                if (((i >> 8) & 255) == 4) {
                    double dAbs = Math.abs(d);
                    if (dAbs != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && !Double.isInfinite(d) && !Double.isNaN(d) && (dAbs < 1.401298464324817E-45d || dAbs > 3.4028234663852886E38d)) {
                        throw new ParseException(streamTokenizer, "32-bit float constant");
                    }
                    return Float.valueOf((float) d);
                }
                return Double.valueOf(d);
            } catch (NumberFormatException unused) {
                throw new ParseException(streamTokenizer, "float constant");
            }
        }
        if (iNextToken != -3) {
            throw new ParseException(streamTokenizer, "integer constant");
        }
        try {
            Long lDecode = Long.decode(streamTokenizer.sval);
            long jLongValue = lDecode.longValue();
            int i3 = (i >> 8) & 255;
            if (i3 == 1) {
                if (jLongValue < -128 || jLongValue > 127) {
                    throw new ParseException(streamTokenizer, "8-bit integer constant");
                }
                return Byte.valueOf((byte) jLongValue);
            }
            if (i3 == 2) {
                if (jLongValue < -32768 || jLongValue > 32767) {
                    throw new ParseException(streamTokenizer, "16-bit integer constant");
                }
                return Short.valueOf((short) jLongValue);
            }
            if (i3 == 4) {
                if (jLongValue < -2147483648L || jLongValue > 2147483647L) {
                    throw new ParseException(streamTokenizer, "32-bit integer constant");
                }
                return Integer.valueOf((int) jLongValue);
            }
            if (i3 != 8) {
                throw new IllegalStateException("Internal error; unexpected integer type width " + i3);
            }
            if (jLongValue < Long.MIN_VALUE || jLongValue > Long.MAX_VALUE) {
                throw new ParseException(streamTokenizer, "64-bit integer constant");
            }
            return lDecode;
        } catch (NumberFormatException unused2) {
            throw new ParseException(streamTokenizer, "integer constant");
        }
    }

    public void load(Reader reader) throws ParseException, IOException, NumberFormatException {
        parse(reader, this);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        Object obj2 = super.get(obj);
        if (obj2 == NULL_STRING) {
            return null;
        }
        return obj2;
    }

    public static class TypeException extends IllegalArgumentException {
        TypeException(String str, Object obj, String str2) {
            super(str + " has type " + obj.getClass().getName() + ", not " + str2);
        }
    }

    public boolean getBoolean(String str, boolean z) {
        Object obj = super.get(str);
        if (obj == null) {
            return z;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        throw new TypeException(str, obj, "boolean");
    }

    public byte getByte(String str, byte b) {
        Object obj = super.get(str);
        if (obj == null) {
            return b;
        }
        if (obj instanceof Byte) {
            return ((Byte) obj).byteValue();
        }
        throw new TypeException(str, obj, "byte");
    }

    public short getShort(String str, short s) {
        Object obj = super.get(str);
        if (obj == null) {
            return s;
        }
        if (obj instanceof Short) {
            return ((Short) obj).shortValue();
        }
        throw new TypeException(str, obj, "short");
    }

    public int getInt(String str, int i) {
        Object obj = super.get(str);
        if (obj == null) {
            return i;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        throw new TypeException(str, obj, "int");
    }

    public long getLong(String str, long j) {
        Object obj = super.get(str);
        if (obj == null) {
            return j;
        }
        if (obj instanceof Long) {
            return ((Long) obj).longValue();
        }
        throw new TypeException(str, obj, SliceItem.FORMAT_LONG);
    }

    public float getFloat(String str, float f) {
        Object obj = super.get(str);
        if (obj == null) {
            return f;
        }
        if (obj instanceof Float) {
            return ((Float) obj).floatValue();
        }
        throw new TypeException(str, obj, SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT);
    }

    public double getDouble(String str, double d) {
        Object obj = super.get(str);
        if (obj == null) {
            return d;
        }
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue();
        }
        throw new TypeException(str, obj, "double");
    }

    public String getString(String str, String str2) {
        Object obj = super.get(str);
        if (obj == null) {
            return str2;
        }
        if (obj == NULL_STRING) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        throw new TypeException(str, obj, "string");
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public byte getByte(String str) {
        return getByte(str, (byte) 0);
    }

    public short getShort(String str) {
        return getShort(str, (short) 0);
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public long getLong(String str) {
        return getLong(str, 0L);
    }

    public float getFloat(String str) {
        return getFloat(str, 0.0f);
    }

    public double getDouble(String str) {
        return getDouble(str, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public String getString(String str) {
        return getString(str, "");
    }

    public int getStringInfo(String str) {
        Object obj = super.get(str);
        if (obj == null) {
            return -1;
        }
        if (obj == NULL_STRING) {
            return 0;
        }
        return obj instanceof String ? 1 : -2;
    }
}
