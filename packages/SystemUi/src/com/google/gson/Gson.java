package com.google.gson;

import com.google.gson.TypeAdapter.C44441;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.NumberTypeAdapter;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.internal.sql.SqlTypesSupport;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Gson {
    public final List builderFactories;
    public final List builderHierarchyFactories;
    public final ThreadLocal calls;
    public final boolean complexMapKeySerialization;
    public final ConstructorConstructor constructorConstructor;
    public final String datePattern;
    public final int dateStyle;
    public final Excluder excluder;
    public final List factories;
    public final FieldNamingStrategy fieldNamingStrategy;
    public final boolean generateNonExecutableJson;
    public final boolean htmlSafe;
    public final Map instanceCreators;
    public final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    public final boolean lenient;
    public final LongSerializationPolicy longSerializationPolicy;
    public final ToNumberStrategy numberToNumberStrategy;
    public final ToNumberStrategy objectToNumberStrategy;
    public final boolean prettyPrinting;
    public final boolean serializeNulls;
    public final boolean serializeSpecialFloatingPointValues;
    public final int timeStyle;
    public final Map typeTokenCache;
    public final boolean useJdkUnsafe;
    public static final FieldNamingStrategy DEFAULT_FIELD_NAMING_STRATEGY = FieldNamingPolicy.IDENTITY;
    public static final ToNumberStrategy DEFAULT_OBJECT_TO_NUMBER_STRATEGY = ToNumberPolicy.DOUBLE;
    public static final ToNumberStrategy DEFAULT_NUMBER_TO_NUMBER_STRATEGY = ToNumberPolicy.LAZILY_PARSED_NUMBER;
    public static final TypeToken NULL_KEY_SURROGATE = new TypeToken(Object.class);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class FutureTypeAdapter<T> extends TypeAdapter {
        public TypeAdapter delegate;

        @Override // com.google.gson.TypeAdapter
        /* renamed from: read */
        public final Object mo2768read(JsonReader jsonReader) {
            TypeAdapter typeAdapter = this.delegate;
            if (typeAdapter != null) {
                return typeAdapter.mo2768read(jsonReader);
            }
            throw new IllegalStateException();
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            TypeAdapter typeAdapter = this.delegate;
            if (typeAdapter == null) {
                throw new IllegalStateException();
            }
            typeAdapter.write(jsonWriter, obj);
        }
    }

    public Gson() {
        this(Excluder.DEFAULT, DEFAULT_FIELD_NAMING_STRATEGY, Collections.emptyMap(), false, false, false, true, false, false, false, true, LongSerializationPolicy.DEFAULT, null, 2, 2, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), DEFAULT_OBJECT_TO_NUMBER_STRATEGY, DEFAULT_NUMBER_TO_NUMBER_STRATEGY);
    }

    public static void checkValidFloatingPoint(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    public final Object fromJson(Class cls, String str) {
        Object fromJson = fromJson(str, cls);
        if (cls == Integer.TYPE) {
            cls = Integer.class;
        } else if (cls == Float.TYPE) {
            cls = Float.class;
        } else if (cls == Byte.TYPE) {
            cls = Byte.class;
        } else if (cls == Double.TYPE) {
            cls = Double.class;
        } else if (cls == Long.TYPE) {
            cls = Long.class;
        } else if (cls == Character.TYPE) {
            cls = Character.class;
        } else if (cls == Boolean.TYPE) {
            cls = Boolean.class;
        } else if (cls == Short.TYPE) {
            cls = Short.class;
        } else if (cls == Void.TYPE) {
            cls = Void.class;
        }
        return cls.cast(fromJson);
    }

    public final TypeAdapter getAdapter(TypeToken typeToken) {
        boolean z;
        Map map = this.typeTokenCache;
        TypeAdapter typeAdapter = (TypeAdapter) ((ConcurrentHashMap) map).get(typeToken);
        if (typeAdapter != null) {
            return typeAdapter;
        }
        ThreadLocal threadLocal = this.calls;
        Map map2 = (Map) threadLocal.get();
        if (map2 == null) {
            map2 = new HashMap();
            threadLocal.set(map2);
            z = true;
        } else {
            z = false;
        }
        FutureTypeAdapter futureTypeAdapter = (FutureTypeAdapter) map2.get(typeToken);
        if (futureTypeAdapter != null) {
            return futureTypeAdapter;
        }
        try {
            FutureTypeAdapter futureTypeAdapter2 = new FutureTypeAdapter();
            map2.put(typeToken, futureTypeAdapter2);
            Iterator it = this.factories.iterator();
            while (it.hasNext()) {
                TypeAdapter create = ((TypeAdapterFactory) it.next()).create(this, typeToken);
                if (create != null) {
                    if (futureTypeAdapter2.delegate != null) {
                        throw new AssertionError();
                    }
                    futureTypeAdapter2.delegate = create;
                    ((ConcurrentHashMap) map).put(typeToken, create);
                    return create;
                }
            }
            throw new IllegalArgumentException("GSON (2.9.0) cannot handle " + typeToken);
        } finally {
            map2.remove(typeToken);
            if (z) {
                threadLocal.remove();
            }
        }
    }

    public final TypeAdapter getDelegateAdapter(TypeAdapterFactory typeAdapterFactory, TypeToken typeToken) {
        List<TypeAdapterFactory> list = this.factories;
        if (!list.contains(typeAdapterFactory)) {
            typeAdapterFactory = this.jsonAdapterFactory;
        }
        boolean z = false;
        for (TypeAdapterFactory typeAdapterFactory2 : list) {
            if (z) {
                TypeAdapter create = typeAdapterFactory2.create(this, typeToken);
                if (create != null) {
                    return create;
                }
            } else if (typeAdapterFactory2 == typeAdapterFactory) {
                z = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + typeToken);
    }

    public final JsonWriter newJsonWriter(Writer writer) {
        if (this.generateNonExecutableJson) {
            writer.write(")]}'\n");
        }
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.indent = "  ";
            jsonWriter.separator = ": ";
        }
        jsonWriter.htmlSafe = this.htmlSafe;
        jsonWriter.lenient = this.lenient;
        jsonWriter.serializeNulls = this.serializeNulls;
        return jsonWriter;
    }

    public final void toJson(JsonNull jsonNull, JsonWriter jsonWriter) {
        boolean z = jsonWriter.lenient;
        jsonWriter.lenient = true;
        boolean z2 = jsonWriter.htmlSafe;
        jsonWriter.htmlSafe = this.htmlSafe;
        boolean z3 = jsonWriter.serializeNulls;
        jsonWriter.serializeNulls = this.serializeNulls;
        try {
            try {
                TypeAdapters.JSON_ELEMENT.write(jsonWriter, jsonNull);
            } catch (IOException e) {
                throw new JsonIOException(e);
            } catch (AssertionError e2) {
                AssertionError assertionError = new AssertionError("AssertionError (GSON 2.9.0): " + e2.getMessage());
                assertionError.initCause(e2);
                throw assertionError;
            }
        } finally {
            jsonWriter.lenient = z;
            jsonWriter.htmlSafe = z2;
            jsonWriter.serializeNulls = z3;
        }
    }

    public final String toString() {
        return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }

    public Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map<Type, Object> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, LongSerializationPolicy longSerializationPolicy, String str, int i, int i2, List<TypeAdapterFactory> list, List<TypeAdapterFactory> list2, List<TypeAdapterFactory> list3, ToNumberStrategy toNumberStrategy, ToNumberStrategy toNumberStrategy2) {
        final TypeAdapter typeAdapter;
        TypeAdapter typeAdapter2;
        TypeAdapter typeAdapter3;
        TypeAdapterFactory newFactory;
        this.calls = new ThreadLocal();
        this.typeTokenCache = new ConcurrentHashMap();
        this.excluder = excluder;
        this.fieldNamingStrategy = fieldNamingStrategy;
        this.instanceCreators = map;
        ConstructorConstructor constructorConstructor = new ConstructorConstructor(map, z8);
        this.constructorConstructor = constructorConstructor;
        this.serializeNulls = z;
        this.complexMapKeySerialization = z2;
        this.generateNonExecutableJson = z3;
        this.htmlSafe = z4;
        this.prettyPrinting = z5;
        this.lenient = z6;
        this.serializeSpecialFloatingPointValues = z7;
        this.useJdkUnsafe = z8;
        this.longSerializationPolicy = longSerializationPolicy;
        this.datePattern = str;
        this.dateStyle = i;
        this.timeStyle = i2;
        this.builderFactories = list;
        this.builderHierarchyFactories = list2;
        this.objectToNumberStrategy = toNumberStrategy;
        this.numberToNumberStrategy = toNumberStrategy2;
        ArrayList arrayList = new ArrayList();
        arrayList.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        arrayList.add(ObjectTypeAdapter.getFactory(toNumberStrategy));
        arrayList.add(excluder);
        arrayList.addAll(list3);
        arrayList.add(TypeAdapters.STRING_FACTORY);
        arrayList.add(TypeAdapters.INTEGER_FACTORY);
        arrayList.add(TypeAdapters.BOOLEAN_FACTORY);
        arrayList.add(TypeAdapters.BYTE_FACTORY);
        arrayList.add(TypeAdapters.SHORT_FACTORY);
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            typeAdapter = TypeAdapters.LONG;
        } else {
            typeAdapter = new TypeAdapter() { // from class: com.google.gson.Gson.3
                @Override // com.google.gson.TypeAdapter
                /* renamed from: read */
                public final Object mo2768read(JsonReader jsonReader) {
                    if (jsonReader.peek() != JsonToken.NULL) {
                        return Long.valueOf(jsonReader.nextLong());
                    }
                    jsonReader.nextNull();
                    return null;
                }

                @Override // com.google.gson.TypeAdapter
                public final void write(JsonWriter jsonWriter, Object obj) {
                    Number number = (Number) obj;
                    if (number == null) {
                        jsonWriter.nullValue();
                    } else {
                        jsonWriter.value(number.toString());
                    }
                }
            };
        }
        arrayList.add(TypeAdapters.newFactory(Long.TYPE, Long.class, typeAdapter));
        Class cls = Double.TYPE;
        if (z7) {
            typeAdapter2 = TypeAdapters.DOUBLE;
        } else {
            typeAdapter2 = new TypeAdapter(this) { // from class: com.google.gson.Gson.1
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
                    Number number = (Number) obj;
                    if (number == null) {
                        jsonWriter.nullValue();
                    } else {
                        Gson.checkValidFloatingPoint(number.doubleValue());
                        jsonWriter.value(number);
                    }
                }
            };
        }
        arrayList.add(TypeAdapters.newFactory(cls, Double.class, typeAdapter2));
        Class cls2 = Float.TYPE;
        if (z7) {
            typeAdapter3 = TypeAdapters.FLOAT;
        } else {
            typeAdapter3 = new TypeAdapter(this) { // from class: com.google.gson.Gson.2
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
                    Number number = (Number) obj;
                    if (number == null) {
                        jsonWriter.nullValue();
                    } else {
                        Gson.checkValidFloatingPoint(number.floatValue());
                        jsonWriter.value(number);
                    }
                }
            };
        }
        arrayList.add(TypeAdapters.newFactory(cls2, Float.class, typeAdapter3));
        TypeAdapterFactory typeAdapterFactory = NumberTypeAdapter.LAZILY_PARSED_NUMBER_FACTORY;
        if (toNumberStrategy2 == ToNumberPolicy.LAZILY_PARSED_NUMBER) {
            newFactory = NumberTypeAdapter.LAZILY_PARSED_NUMBER_FACTORY;
        } else {
            newFactory = NumberTypeAdapter.newFactory(toNumberStrategy2);
        }
        arrayList.add(newFactory);
        arrayList.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        arrayList.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        arrayList.add(TypeAdapters.newFactory(AtomicLong.class, new TypeAdapter() { // from class: com.google.gson.Gson.4
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                return new AtomicLong(((Number) TypeAdapter.this.mo2768read(jsonReader)).longValue());
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                TypeAdapter.this.write(jsonWriter, Long.valueOf(((AtomicLong) obj).get()));
            }
        }.new C44441()));
        arrayList.add(TypeAdapters.newFactory(AtomicLongArray.class, new TypeAdapter() { // from class: com.google.gson.Gson.5
            @Override // com.google.gson.TypeAdapter
            /* renamed from: read */
            public final Object mo2768read(JsonReader jsonReader) {
                ArrayList arrayList2 = new ArrayList();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    arrayList2.add(Long.valueOf(((Number) TypeAdapter.this.mo2768read(jsonReader)).longValue()));
                }
                jsonReader.endArray();
                int size = arrayList2.size();
                AtomicLongArray atomicLongArray = new AtomicLongArray(size);
                for (int i3 = 0; i3 < size; i3++) {
                    atomicLongArray.set(i3, ((Long) arrayList2.get(i3)).longValue());
                }
                return atomicLongArray;
            }

            @Override // com.google.gson.TypeAdapter
            public final void write(JsonWriter jsonWriter, Object obj) {
                AtomicLongArray atomicLongArray = (AtomicLongArray) obj;
                jsonWriter.beginArray();
                int length = atomicLongArray.length();
                for (int i3 = 0; i3 < length; i3++) {
                    TypeAdapter.this.write(jsonWriter, Long.valueOf(atomicLongArray.get(i3)));
                }
                jsonWriter.endArray();
            }
        }.new C44441()));
        arrayList.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        arrayList.add(TypeAdapters.CHARACTER_FACTORY);
        arrayList.add(TypeAdapters.STRING_BUILDER_FACTORY);
        arrayList.add(TypeAdapters.STRING_BUFFER_FACTORY);
        arrayList.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        arrayList.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        arrayList.add(TypeAdapters.newFactory(LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
        arrayList.add(TypeAdapters.URL_FACTORY);
        arrayList.add(TypeAdapters.URI_FACTORY);
        arrayList.add(TypeAdapters.UUID_FACTORY);
        arrayList.add(TypeAdapters.CURRENCY_FACTORY);
        arrayList.add(TypeAdapters.LOCALE_FACTORY);
        arrayList.add(TypeAdapters.INET_ADDRESS_FACTORY);
        arrayList.add(TypeAdapters.BIT_SET_FACTORY);
        arrayList.add(DateTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.CALENDAR_FACTORY);
        if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
            arrayList.add(SqlTypesSupport.TIME_FACTORY);
            arrayList.add(SqlTypesSupport.DATE_FACTORY);
            arrayList.add(SqlTypesSupport.TIMESTAMP_FACTORY);
        }
        arrayList.add(ArrayTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.CLASS_FACTORY);
        arrayList.add(new CollectionTypeAdapterFactory(constructorConstructor));
        arrayList.add(new MapTypeAdapterFactory(constructorConstructor, z2));
        JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(constructorConstructor);
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
        arrayList.add(jsonAdapterAnnotationTypeAdapterFactory);
        arrayList.add(TypeAdapters.ENUM_FACTORY);
        arrayList.add(new ReflectiveTypeAdapterFactory(constructorConstructor, fieldNamingStrategy, excluder, jsonAdapterAnnotationTypeAdapterFactory));
        this.factories = Collections.unmodifiableList(arrayList);
    }

    public final Object fromJson(String str, Type type) {
        Object obj = null;
        if (str == null) {
            return null;
        }
        JsonReader jsonReader = new JsonReader(new StringReader(str));
        boolean z = this.lenient;
        boolean z2 = true;
        jsonReader.lenient = true;
        try {
            try {
                try {
                    jsonReader.peek();
                    z2 = false;
                    obj = getAdapter(new TypeToken(type)).mo2768read(jsonReader);
                } finally {
                    jsonReader.lenient = z;
                }
            } catch (EOFException e) {
                if (!z2) {
                    throw new JsonSyntaxException(e);
                }
            } catch (AssertionError e2) {
                AssertionError assertionError = new AssertionError("AssertionError (GSON 2.9.0): " + e2.getMessage());
                assertionError.initCause(e2);
                throw assertionError;
            }
            if (obj != null) {
                try {
                    if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                        throw new JsonIOException("JSON document was not fully consumed.");
                    }
                } catch (MalformedJsonException e3) {
                    throw new JsonSyntaxException(e3);
                } catch (IOException e4) {
                    throw new JsonIOException(e4);
                }
            }
            return obj;
        } catch (IOException e5) {
            throw new JsonSyntaxException(e5);
        } catch (IllegalStateException e6) {
            throw new JsonSyntaxException(e6);
        }
    }

    public final void toJson(Object obj, Type type, JsonWriter jsonWriter) {
        TypeAdapter adapter = getAdapter(new TypeToken(type));
        boolean z = jsonWriter.lenient;
        jsonWriter.lenient = true;
        boolean z2 = jsonWriter.htmlSafe;
        jsonWriter.htmlSafe = this.htmlSafe;
        boolean z3 = jsonWriter.serializeNulls;
        jsonWriter.serializeNulls = this.serializeNulls;
        try {
            try {
                adapter.write(jsonWriter, obj);
            } catch (IOException e) {
                throw new JsonIOException(e);
            } catch (AssertionError e2) {
                AssertionError assertionError = new AssertionError("AssertionError (GSON 2.9.0): " + e2.getMessage());
                assertionError.initCause(e2);
                throw assertionError;
            }
        } finally {
            jsonWriter.lenient = z;
            jsonWriter.htmlSafe = z2;
            jsonWriter.serializeNulls = z3;
        }
    }

    public final String toJson(Object obj) {
        if (obj == null) {
            JsonNull jsonNull = JsonNull.INSTANCE;
            StringWriter stringWriter = new StringWriter();
            try {
                toJson(jsonNull, newJsonWriter(stringWriter));
                return stringWriter.toString();
            } catch (IOException e) {
                throw new JsonIOException(e);
            }
        }
        Type type = obj.getClass();
        StringWriter stringWriter2 = new StringWriter();
        try {
            toJson(obj, type, newJsonWriter(stringWriter2));
            return stringWriter2.toString();
        } catch (IOException e2) {
            throw new JsonIOException(e2);
        }
    }
}
