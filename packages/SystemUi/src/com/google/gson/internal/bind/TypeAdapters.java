package com.google.gson.internal.bind;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TypeAdapters {
    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY;
    public static final TypeAdapter BIG_DECIMAL;
    public static final TypeAdapter BIG_INTEGER;
    public static final TypeAdapter BOOLEAN_AS_STRING;
    public static final TypeAdapterFactory BOOLEAN_FACTORY;
    public static final TypeAdapterFactory BYTE_FACTORY;
    public static final TypeAdapterFactory CALENDAR_FACTORY;
    public static final TypeAdapterFactory CHARACTER_FACTORY;
    public static final TypeAdapterFactory CURRENCY_FACTORY;
    public static final TypeAdapter DOUBLE;
    public static final TypeAdapterFactory ENUM_FACTORY;
    public static final TypeAdapter FLOAT;
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
    public static final TypeAdapterFactory INTEGER_FACTORY;
    public static final TypeAdapter JSON_ELEMENT;
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
    public static final TypeAdapter LAZILY_PARSED_NUMBER;
    public static final TypeAdapterFactory LOCALE_FACTORY;
    public static final TypeAdapter LONG;
    public static final TypeAdapterFactory SHORT_FACTORY;
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
    public static final TypeAdapterFactory STRING_FACTORY;
    public static final TypeAdapterFactory URI_FACTORY;
    public static final TypeAdapterFactory URL_FACTORY;
    public static final TypeAdapterFactory UUID_FACTORY;
    public static final TypeAdapterFactory CLASS_FACTORY = new C450431(Class.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.1
        @Override // com.google.gson.TypeAdapter
        /* renamed from: read */
        public final Object mo2768read(JsonReader jsonReader) {
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + ((Class) obj).getName() + ". Forgot to register a type adapter?");
        }
    }.nullSafe());
    public static final TypeAdapterFactory BIT_SET_FACTORY = new C450431(BitSet.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.2
        @Override // com.google.gson.TypeAdapter
        /* renamed from: read */
        public final Object mo2768read(JsonReader jsonReader) {
            BitSet bitSet = new BitSet();
            jsonReader.beginArray();
            JsonToken peek = jsonReader.peek();
            int i = 0;
            while (peek != JsonToken.END_ARRAY) {
                int i2 = AbstractC450835.$SwitchMap$com$google$gson$stream$JsonToken[peek.ordinal()];
                boolean z = true;
                if (i2 == 1 || i2 == 2) {
                    int nextInt = jsonReader.nextInt();
                    if (nextInt == 0) {
                        z = false;
                    } else if (nextInt != 1) {
                        StringBuilder m1m = AbstractC0000x2c234b15.m1m("Invalid bitset value ", nextInt, ", expected 0 or 1; at path ");
                        m1m.append(jsonReader.getPreviousPath());
                        throw new JsonSyntaxException(m1m.toString());
                    }
                } else {
                    if (i2 != 3) {
                        throw new JsonSyntaxException("Invalid bitset value type: " + peek + "; at path " + jsonReader.getPath());
                    }
                    z = jsonReader.nextBoolean();
                }
                if (z) {
                    bitSet.set(i);
                }
                i++;
                peek = jsonReader.peek();
            }
            jsonReader.endArray();
            return bitSet;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            BitSet bitSet = (BitSet) obj;
            jsonWriter.beginArray();
            int length = bitSet.length();
            for (int i = 0; i < length; i++) {
                jsonWriter.value(bitSet.get(i) ? 1L : 0L);
            }
            jsonWriter.endArray();
        }
    }.nullSafe());

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.gson.internal.bind.TypeAdapters$30 */
    class C450330 implements TypeAdapterFactory {
        public final /* synthetic */ TypeToken val$type;
        public final /* synthetic */ TypeAdapter val$typeAdapter;

        public C450330(TypeToken typeToken, TypeAdapter typeAdapter) {
            this.val$type = typeToken;
            this.val$typeAdapter = typeAdapter;
        }

        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.equals(this.val$type)) {
                return this.val$typeAdapter;
            }
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.gson.internal.bind.TypeAdapters$31 */
    class C450431 implements TypeAdapterFactory {
        public final /* synthetic */ Class val$type;
        public final /* synthetic */ TypeAdapter val$typeAdapter;

        public C450431(Class cls, TypeAdapter typeAdapter) {
            this.val$type = cls;
            this.val$typeAdapter = typeAdapter;
        }

        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.rawType == this.val$type) {
                return this.val$typeAdapter;
            }
            return null;
        }

        public final String toString() {
            return "Factory[type=" + this.val$type.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.gson.internal.bind.TypeAdapters$32 */
    class C450532 implements TypeAdapterFactory {
        public final /* synthetic */ Class val$boxed;
        public final /* synthetic */ TypeAdapter val$typeAdapter;
        public final /* synthetic */ Class val$unboxed;

        public C450532(Class cls, Class cls2, TypeAdapter typeAdapter) {
            this.val$unboxed = cls;
            this.val$boxed = cls2;
            this.val$typeAdapter = typeAdapter;
        }

        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class cls = typeToken.rawType;
            if (cls == this.val$unboxed || cls == this.val$boxed) {
                return this.val$typeAdapter;
            }
            return null;
        }

        public final String toString() {
            return "Factory[type=" + this.val$boxed.getName() + "+" + this.val$unboxed.getName() + ",adapter=" + this.val$typeAdapter + "]";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.gson.internal.bind.TypeAdapters$35 */
    public abstract /* synthetic */ class AbstractC450835 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$com$google$gson$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NULL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BEGIN_ARRAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_DOCUMENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NAME.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_OBJECT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_ARRAY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter {
        public final Map nameToConstant = new HashMap();
        public final Map constantToName = new HashMap();

        public EnumTypeAdapter(final Class<T> cls) {
            try {
                for (Field field : (Field[]) AccessController.doPrivileged(new PrivilegedAction(this) { // from class: com.google.gson.internal.bind.TypeAdapters.EnumTypeAdapter.1
                    @Override // java.security.PrivilegedAction
                    public final Object run() {
                        Field[] declaredFields = cls.getDeclaredFields();
                        ArrayList arrayList = new ArrayList(declaredFields.length);
                        for (Field field2 : declaredFields) {
                            if (field2.isEnumConstant()) {
                                arrayList.add(field2);
                            }
                        }
                        Field[] fieldArr = (Field[]) arrayList.toArray(new Field[0]);
                        AccessibleObject.setAccessible(fieldArr, true);
                        return fieldArr;
                    }
                })) {
                    Enum r4 = (Enum) field.get(null);
                    String name = r4.name();
                    SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
                    if (serializedName != null) {
                        name = serializedName.value();
                        for (String str : serializedName.alternate()) {
                            this.nameToConstant.put(str, r4);
                        }
                    }
                    this.nameToConstant.put(name, r4);
                    this.constantToName.put(r4, name);
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.google.gson.TypeAdapter
        /* renamed from: read */
        public final Object mo2768read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return (Enum) ((HashMap) this.nameToConstant).get(jsonReader.nextString());
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            Enum r2 = (Enum) obj;
            jsonWriter.value(r2 == null ? null : (String) ((HashMap) this.constantToName).get(r2));
        }
    }

    static {
        TypeAdapter typeAdapter = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.3
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                JsonToken peek = jsonReader.peek();
                if (peek != JsonToken.NULL) {
                    return peek == JsonToken.STRING ? Boolean.valueOf(Boolean.parseBoolean(jsonReader.nextString())) : Boolean.valueOf(jsonReader.nextBoolean());
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((Boolean) obj);
            }
        };
        BOOLEAN_AS_STRING = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.4
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return Boolean.valueOf(jsonReader.nextString());
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                Boolean bool = (Boolean) obj;
                jsonWriter.value(bool == null ? "null" : bool.toString());
            }
        };
        BOOLEAN_FACTORY = new C450532(Boolean.TYPE, Boolean.class, typeAdapter);
        BYTE_FACTORY = new C450532(Byte.TYPE, Byte.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.5
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    int nextInt = jsonReader.nextInt();
                    if (nextInt <= 255 && nextInt >= -128) {
                        return Byte.valueOf((byte) nextInt);
                    }
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("Lossy conversion from ", nextInt, " to byte; at path ");
                    m1m.append(jsonReader.getPreviousPath());
                    throw new JsonSyntaxException(m1m.toString());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((Number) obj);
            }
        });
        SHORT_FACTORY = new C450532(Short.TYPE, Short.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.6
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    int nextInt = jsonReader.nextInt();
                    if (nextInt <= 65535 && nextInt >= -32768) {
                        return Short.valueOf((short) nextInt);
                    }
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("Lossy conversion from ", nextInt, " to short; at path ");
                    m1m.append(jsonReader.getPreviousPath());
                    throw new JsonSyntaxException(m1m.toString());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((Number) obj);
            }
        });
        INTEGER_FACTORY = new C450532(Integer.TYPE, Integer.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.7
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    return Integer.valueOf(jsonReader.nextInt());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((Number) obj);
            }
        });
        ATOMIC_INTEGER_FACTORY = new C450431(AtomicInteger.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.8
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                try {
                    return new AtomicInteger(jsonReader.nextInt());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value(((AtomicInteger) obj).get());
            }
        }.nullSafe());
        ATOMIC_BOOLEAN_FACTORY = new C450431(AtomicBoolean.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.9
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                return new AtomicBoolean(jsonReader.nextBoolean());
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value(((AtomicBoolean) obj).get());
            }
        }.nullSafe());
        ATOMIC_INTEGER_ARRAY_FACTORY = new C450431(AtomicIntegerArray.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.10
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                ArrayList arrayList = new ArrayList();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    try {
                        arrayList.add(Integer.valueOf(jsonReader.nextInt()));
                    } catch (NumberFormatException e) {
                        throw new JsonSyntaxException(e);
                    }
                }
                jsonReader.endArray();
                int size = arrayList.size();
                AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(size);
                for (int i = 0; i < size; i++) {
                    atomicIntegerArray.set(i, ((Integer) arrayList.get(i)).intValue());
                }
                return atomicIntegerArray;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.beginArray();
                int length = ((AtomicIntegerArray) obj).length();
                for (int i = 0; i < length; i++) {
                    jsonWriter.value(r5.get(i));
                }
                jsonWriter.endArray();
            }
        }.nullSafe());
        LONG = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.11
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    return Long.valueOf(jsonReader.nextLong());
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((Number) obj);
            }
        };
        FLOAT = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.12
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return Float.valueOf((float) jsonReader.nextDouble());
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((Number) obj);
            }
        };
        DOUBLE = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.13
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return Double.valueOf(jsonReader.nextDouble());
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((Number) obj);
            }
        };
        CHARACTER_FACTORY = new C450532(Character.TYPE, Character.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.14
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                String nextString = jsonReader.nextString();
                if (nextString.length() == 1) {
                    return Character.valueOf(nextString.charAt(0));
                }
                StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Expecting character, got: ", nextString, "; at ");
                m4m.append(jsonReader.getPreviousPath());
                throw new JsonSyntaxException(m4m.toString());
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                Character ch = (Character) obj;
                jsonWriter.value(ch == null ? null : String.valueOf(ch));
            }
        });
        TypeAdapter typeAdapter2 = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.15
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                JsonToken peek = jsonReader.peek();
                if (peek != JsonToken.NULL) {
                    return peek == JsonToken.BOOLEAN ? Boolean.toString(jsonReader.nextBoolean()) : jsonReader.nextString();
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((String) obj);
            }
        };
        BIG_DECIMAL = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.16
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                String nextString = jsonReader.nextString();
                try {
                    return new BigDecimal(nextString);
                } catch (NumberFormatException e) {
                    StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Failed parsing '", nextString, "' as BigDecimal; at path ");
                    m4m.append(jsonReader.getPreviousPath());
                    throw new JsonSyntaxException(m4m.toString(), e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((BigDecimal) obj);
            }
        };
        BIG_INTEGER = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.17
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                String nextString = jsonReader.nextString();
                try {
                    return new BigInteger(nextString);
                } catch (NumberFormatException e) {
                    StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Failed parsing '", nextString, "' as BigInteger; at path ");
                    m4m.append(jsonReader.getPreviousPath());
                    throw new JsonSyntaxException(m4m.toString(), e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((BigInteger) obj);
            }
        };
        LAZILY_PARSED_NUMBER = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.18
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return new LazilyParsedNumber(jsonReader.nextString());
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value((LazilyParsedNumber) obj);
            }
        };
        STRING_FACTORY = new C450431(String.class, typeAdapter2);
        STRING_BUILDER_FACTORY = new C450431(StringBuilder.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.19
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return new StringBuilder(jsonReader.nextString());
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                StringBuilder sb = (StringBuilder) obj;
                jsonWriter.value(sb == null ? null : sb.toString());
            }
        });
        STRING_BUFFER_FACTORY = new C450431(StringBuffer.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.20
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return new StringBuffer(jsonReader.nextString());
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                StringBuffer stringBuffer = (StringBuffer) obj;
                jsonWriter.value(stringBuffer == null ? null : stringBuffer.toString());
            }
        });
        URL_FACTORY = new C450431(URL.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.21
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                String nextString = jsonReader.nextString();
                if ("null".equals(nextString)) {
                    return null;
                }
                return new URL(nextString);
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                URL url = (URL) obj;
                jsonWriter.value(url == null ? null : url.toExternalForm());
            }
        });
        URI_FACTORY = new C450431(URI.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.22
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    String nextString = jsonReader.nextString();
                    if ("null".equals(nextString)) {
                        return null;
                    }
                    return new URI(nextString);
                } catch (URISyntaxException e) {
                    throw new JsonIOException(e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                URI uri = (URI) obj;
                jsonWriter.value(uri == null ? null : uri.toASCIIString());
            }
        });
        final TypeAdapter typeAdapter3 = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.23
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return InetAddress.getByName(jsonReader.nextString());
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                InetAddress inetAddress = (InetAddress) obj;
                jsonWriter.value(inetAddress == null ? null : inetAddress.getHostAddress());
            }
        };
        final Class<InetAddress> cls = InetAddress.class;
        INET_ADDRESS_FACTORY = new TypeAdapterFactory() { // from class: com.google.gson.internal.bind.TypeAdapters.34
            @Override // com.google.gson.TypeAdapterFactory
            public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                final Class<?> cls2 = typeToken.rawType;
                if (cls.isAssignableFrom(cls2)) {
                    return new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.34.1
                        @Override // com.google.gson.TypeAdapter
                        /* renamed from: read */
                        public final Object mo2768read(JsonReader jsonReader) {
                            Object mo2768read = typeAdapter3.mo2768read(jsonReader);
                            if (mo2768read != null) {
                                Class cls3 = cls2;
                                if (!cls3.isInstance(mo2768read)) {
                                    throw new JsonSyntaxException("Expected a " + cls3.getName() + " but was " + mo2768read.getClass().getName() + "; at path " + jsonReader.getPreviousPath());
                                }
                            }
                            return mo2768read;
                        }

                        @Override // com.google.gson.TypeAdapter
                        public final void write(JsonWriter jsonWriter, Object obj) {
                            typeAdapter3.write(jsonWriter, obj);
                        }
                    };
                }
                return null;
            }

            public final String toString() {
                return "Factory[typeHierarchy=" + cls.getName() + ",adapter=" + typeAdapter3 + "]";
            }
        };
        UUID_FACTORY = new C450431(UUID.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.24
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                String nextString = jsonReader.nextString();
                try {
                    return UUID.fromString(nextString);
                } catch (IllegalArgumentException e) {
                    StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Failed parsing '", nextString, "' as UUID; at path ");
                    m4m.append(jsonReader.getPreviousPath());
                    throw new JsonSyntaxException(m4m.toString(), e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                UUID uuid = (UUID) obj;
                jsonWriter.value(uuid == null ? null : uuid.toString());
            }
        });
        CURRENCY_FACTORY = new C450431(Currency.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.25
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                String nextString = jsonReader.nextString();
                try {
                    return Currency.getInstance(nextString);
                } catch (IllegalArgumentException e) {
                    StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Failed parsing '", nextString, "' as Currency; at path ");
                    m4m.append(jsonReader.getPreviousPath());
                    throw new JsonSyntaxException(m4m.toString(), e);
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                jsonWriter.value(((Currency) obj).getCurrencyCode());
            }
        }.nullSafe());
        final TypeAdapter typeAdapter4 = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.26
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                jsonReader.beginObject();
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                while (jsonReader.peek() != JsonToken.END_OBJECT) {
                    String nextName = jsonReader.nextName();
                    int nextInt = jsonReader.nextInt();
                    if ("year".equals(nextName)) {
                        i = nextInt;
                    } else if ("month".equals(nextName)) {
                        i2 = nextInt;
                    } else if ("dayOfMonth".equals(nextName)) {
                        i3 = nextInt;
                    } else if ("hourOfDay".equals(nextName)) {
                        i4 = nextInt;
                    } else if ("minute".equals(nextName)) {
                        i5 = nextInt;
                    } else if ("second".equals(nextName)) {
                        i6 = nextInt;
                    }
                }
                jsonReader.endObject();
                return new GregorianCalendar(i, i2, i3, i4, i5, i6);
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                if (((Calendar) obj) == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.beginObject();
                jsonWriter.name("year");
                jsonWriter.value(r4.get(1));
                jsonWriter.name("month");
                jsonWriter.value(r4.get(2));
                jsonWriter.name("dayOfMonth");
                jsonWriter.value(r4.get(5));
                jsonWriter.name("hourOfDay");
                jsonWriter.value(r4.get(11));
                jsonWriter.name("minute");
                jsonWriter.value(r4.get(12));
                jsonWriter.name("second");
                jsonWriter.value(r4.get(13));
                jsonWriter.endObject();
            }
        };
        final Class<Calendar> cls2 = Calendar.class;
        final Class<GregorianCalendar> cls3 = GregorianCalendar.class;
        CALENDAR_FACTORY = new TypeAdapterFactory() { // from class: com.google.gson.internal.bind.TypeAdapters.33
            @Override // com.google.gson.TypeAdapterFactory
            public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                Class cls4 = typeToken.rawType;
                if (cls4 == cls2 || cls4 == cls3) {
                    return typeAdapter4;
                }
                return null;
            }

            public final String toString() {
                return "Factory[type=" + cls2.getName() + "+" + cls3.getName() + ",adapter=" + typeAdapter4 + "]";
            }
        };
        LOCALE_FACTORY = new C450431(Locale.class, new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.27
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(jsonReader.nextString(), "_");
                String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                String nextToken3 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                return (nextToken2 == null && nextToken3 == null) ? new Locale(nextToken) : nextToken3 == null ? new Locale(nextToken, nextToken2) : new Locale(nextToken, nextToken2, nextToken3);
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                Locale locale = (Locale) obj;
                jsonWriter.value(locale == null ? null : locale.toString());
            }
        });
        final TypeAdapter typeAdapter5 = new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.28
            public static void write(JsonElement jsonElement, JsonWriter jsonWriter) {
                if (jsonElement == null || (jsonElement instanceof JsonNull)) {
                    jsonWriter.nullValue();
                    return;
                }
                boolean z = jsonElement instanceof JsonPrimitive;
                if (z) {
                    if (!z) {
                        throw new IllegalStateException("Not a JSON Primitive: " + jsonElement);
                    }
                    JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonElement;
                    Object obj = jsonPrimitive.value;
                    if (obj instanceof Number) {
                        jsonWriter.value(jsonPrimitive.getAsNumber());
                        return;
                    } else if (obj instanceof Boolean) {
                        jsonWriter.value(jsonPrimitive.getAsBoolean());
                        return;
                    } else {
                        jsonWriter.value(jsonPrimitive.getAsString());
                        return;
                    }
                }
                boolean z2 = jsonElement instanceof JsonArray;
                if (z2) {
                    jsonWriter.beginArray();
                    if (!z2) {
                        throw new IllegalStateException("Not a JSON Array: " + jsonElement);
                    }
                    Iterator it = ((JsonArray) jsonElement).iterator();
                    while (it.hasNext()) {
                        write((JsonElement) it.next(), jsonWriter);
                    }
                    jsonWriter.endArray();
                    return;
                }
                if (!(jsonElement instanceof JsonObject)) {
                    throw new IllegalArgumentException("Couldn't write " + jsonElement.getClass());
                }
                jsonWriter.beginObject();
                LinkedTreeMap.EntrySet.C44621 c44621 = new LinkedTreeMap.EntrySet.C44621((LinkedTreeMap.EntrySet) jsonElement.getAsJsonObject().members.entrySet());
                while (c44621.hasNext()) {
                    LinkedTreeMap.Node nextNode = c44621.nextNode();
                    jsonWriter.name((String) nextNode.key);
                    write((JsonElement) nextNode.value, jsonWriter);
                }
                jsonWriter.endObject();
            }

            @Override // com.google.gson.TypeAdapter
            /* renamed from: read, reason: collision with other method in class */
            public final /* bridge */ /* synthetic */ Object mo2768read(JsonReader jsonReader) {
                return read(jsonReader);
            }

            public static JsonElement read(JsonReader jsonReader) {
                if (jsonReader instanceof JsonTreeReader) {
                    JsonTreeReader jsonTreeReader = (JsonTreeReader) jsonReader;
                    JsonToken peek = jsonTreeReader.peek();
                    if (peek != JsonToken.NAME && peek != JsonToken.END_ARRAY && peek != JsonToken.END_OBJECT && peek != JsonToken.END_DOCUMENT) {
                        JsonElement jsonElement = (JsonElement) jsonTreeReader.peekStack();
                        jsonTreeReader.skipValue();
                        return jsonElement;
                    }
                    throw new IllegalStateException("Unexpected " + peek + " when reading a JsonElement.");
                }
                switch (AbstractC450835.$SwitchMap$com$google$gson$stream$JsonToken[jsonReader.peek().ordinal()]) {
                    case 1:
                        return new JsonPrimitive(new LazilyParsedNumber(jsonReader.nextString()));
                    case 2:
                        return new JsonPrimitive(jsonReader.nextString());
                    case 3:
                        return new JsonPrimitive(Boolean.valueOf(jsonReader.nextBoolean()));
                    case 4:
                        jsonReader.nextNull();
                        return JsonNull.INSTANCE;
                    case 5:
                        JsonArray jsonArray = new JsonArray();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            jsonArray.add(read(jsonReader));
                        }
                        jsonReader.endArray();
                        return jsonArray;
                    case 6:
                        JsonObject jsonObject = new JsonObject();
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            jsonObject.add(jsonReader.nextName(), read(jsonReader));
                        }
                        jsonReader.endObject();
                        return jsonObject;
                    default:
                        throw new IllegalArgumentException();
                }
            }

            @Override // com.google.gson.TypeAdapter
            public final /* bridge */ /* synthetic */ void write(JsonWriter jsonWriter, Object obj) {
                write((JsonElement) obj, jsonWriter);
            }
        };
        JSON_ELEMENT = typeAdapter5;
        final Class<JsonElement> cls4 = JsonElement.class;
        JSON_ELEMENT_FACTORY = new TypeAdapterFactory() { // from class: com.google.gson.internal.bind.TypeAdapters.34
            @Override // com.google.gson.TypeAdapterFactory
            public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                final Class cls22 = typeToken.rawType;
                if (cls4.isAssignableFrom(cls22)) {
                    return new TypeAdapter() { // from class: com.google.gson.internal.bind.TypeAdapters.34.1
                        @Override // com.google.gson.TypeAdapter
                        /* renamed from: read */
                        public final Object mo2768read(JsonReader jsonReader) {
                            Object mo2768read = typeAdapter5.mo2768read(jsonReader);
                            if (mo2768read != null) {
                                Class cls32 = cls22;
                                if (!cls32.isInstance(mo2768read)) {
                                    throw new JsonSyntaxException("Expected a " + cls32.getName() + " but was " + mo2768read.getClass().getName() + "; at path " + jsonReader.getPreviousPath());
                                }
                            }
                            return mo2768read;
                        }

                        @Override // com.google.gson.TypeAdapter
                        public final void write(JsonWriter jsonWriter, Object obj) {
                            typeAdapter5.write(jsonWriter, obj);
                        }
                    };
                }
                return null;
            }

            public final String toString() {
                return "Factory[typeHierarchy=" + cls4.getName() + ",adapter=" + typeAdapter5 + "]";
            }
        };
        ENUM_FACTORY = new TypeAdapterFactory() { // from class: com.google.gson.internal.bind.TypeAdapters.29
            @Override // com.google.gson.TypeAdapterFactory
            public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                Class cls5 = typeToken.rawType;
                if (!Enum.class.isAssignableFrom(cls5) || cls5 == Enum.class) {
                    return null;
                }
                if (!cls5.isEnum()) {
                    cls5 = cls5.getSuperclass();
                }
                return new EnumTypeAdapter(cls5);
            }
        };
    }

    private TypeAdapters() {
        throw new UnsupportedOperationException();
    }

    public static TypeAdapterFactory newFactory(Class cls, TypeAdapter typeAdapter) {
        return new C450431(cls, typeAdapter);
    }

    public static TypeAdapterFactory newFactory(Class cls, Class cls2, TypeAdapter typeAdapter) {
        return new C450532(cls, cls2, typeAdapter);
    }
}
