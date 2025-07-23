package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.reflect.ReflectionHelper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Adapter<T> extends TypeAdapter<T> {
        private final Map<String, BoundField> boundFields;
        private final ObjectConstructor<T> constructor;

        public Adapter(ObjectConstructor<T> objectConstructor, Map<String, BoundField> map) {
            this.constructor = objectConstructor;
            this.boundFields = map;
        }

        @Override // com.google.gson.TypeAdapter
        /* renamed from: read */
        public T read2(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            T construct = this.constructor.construct();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    BoundField boundField = this.boundFields.get(jsonReader.nextName());
                    if (boundField != null && boundField.deserialized) {
                        boundField.read(jsonReader, construct);
                    }
                    jsonReader.skipValue();
                }
                jsonReader.endObject();
                return construct;
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (IllegalStateException e2) {
                throw new JsonSyntaxException(e2);
            }
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            if (t == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                for (BoundField boundField : this.boundFields.values()) {
                    if (boundField.writeField(t)) {
                        jsonWriter.name(boundField.name);
                        boundField.write(jsonWriter, t);
                    }
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    abstract class BoundField {
        final boolean deserialized;
        final String name;
        final boolean serialized;

        public BoundField(String str, boolean z, boolean z2) {
            this.name = str;
            this.serialized = z;
            this.deserialized = z2;
        }

        public abstract void read(JsonReader jsonReader, Object obj) throws IOException, IllegalAccessException;

        public abstract void write(JsonWriter jsonWriter, Object obj) throws IOException, IllegalAccessException;

        public abstract boolean writeField(Object obj) throws IOException, IllegalAccessException;
    }

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingStrategy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
    }

    private BoundField createBoundField(final Gson gson, final Field field, String str, final TypeToken<?> typeToken, boolean z, boolean z2) {
        final boolean isPrimitive = Primitives.isPrimitive(typeToken.getRawType());
        JsonAdapter jsonAdapter = (JsonAdapter) field.getAnnotation(JsonAdapter.class);
        TypeAdapter<?> typeAdapter = jsonAdapter != null ? this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter) : null;
        final boolean z3 = typeAdapter != null;
        if (typeAdapter == null) {
            typeAdapter = gson.getAdapter(typeToken);
        }
        final TypeAdapter<?> typeAdapter2 = typeAdapter;
        return new BoundField(str, z, z2) { // from class: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.1
            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public void read(JsonReader jsonReader, Object obj) throws IOException, IllegalAccessException {
                Object read2 = typeAdapter2.read2(jsonReader);
                if (read2 == null && isPrimitive) {
                    return;
                }
                field.set(obj, read2);
            }

            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public void write(JsonWriter jsonWriter, Object obj) throws IOException, IllegalAccessException {
                (z3 ? typeAdapter2 : new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter2, typeToken.getType())).write(jsonWriter, field.get(obj));
            }

            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public boolean writeField(Object obj) throws IOException, IllegalAccessException {
                return this.serialized && field.get(obj) != obj;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v2, types: [int] */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    private Map<String, BoundField> getBoundFields(Gson gson, TypeToken<?> typeToken, Class<?> cls) {
        TypeToken<?> typeToken2;
        ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory = this;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (!cls.isInterface()) {
            Type type = typeToken.getType();
            TypeToken<?> typeToken3 = typeToken;
            Class<?> cls2 = cls;
            while (cls2 != Object.class) {
                Field[] declaredFields = cls2.getDeclaredFields();
                int length = declaredFields.length;
                boolean z = false;
                int i = 0;
                ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory2 = reflectiveTypeAdapterFactory;
                while (i < length) {
                    Field field = declaredFields[i];
                    boolean excludeField = reflectiveTypeAdapterFactory2.excludeField(field, true);
                    boolean excludeField2 = reflectiveTypeAdapterFactory2.excludeField(field, z);
                    if (excludeField || excludeField2) {
                        ReflectionHelper.makeAccessible(field);
                        Type resolve = C$Gson$Types.resolve(typeToken3.getType(), cls2, field.getGenericType());
                        List<String> fieldNames = reflectiveTypeAdapterFactory2.getFieldNames(field);
                        int size = fieldNames.size();
                        BoundField boundField = null;
                        ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory3 = reflectiveTypeAdapterFactory2;
                        for (?? r13 = z; r13 < size; r13++) {
                            String str = fieldNames.get(r13);
                            if (r13 != 0) {
                                excludeField = false;
                            }
                            int i2 = size;
                            List<String> list = fieldNames;
                            TypeToken<?> typeToken4 = typeToken3;
                            BoundField boundField2 = boundField;
                            boolean z2 = excludeField;
                            BoundField boundField3 = (BoundField) linkedHashMap.put(str, reflectiveTypeAdapterFactory3.createBoundField(gson, field, str, TypeToken.get(resolve), z2, excludeField2));
                            if (boundField2 != null) {
                                boundField3 = boundField2;
                            }
                            typeToken3 = typeToken4;
                            excludeField = z2;
                            fieldNames = list;
                            size = i2;
                            boundField = boundField3;
                            reflectiveTypeAdapterFactory3 = this;
                        }
                        typeToken2 = typeToken3;
                        BoundField boundField4 = boundField;
                        if (boundField4 != null) {
                            throw new IllegalArgumentException(type + " declares multiple JSON fields named " + boundField4.name);
                        }
                    } else {
                        typeToken2 = typeToken3;
                    }
                    i++;
                    z = false;
                    reflectiveTypeAdapterFactory2 = this;
                    typeToken3 = typeToken2;
                }
                typeToken3 = TypeToken.get(C$Gson$Types.resolve(typeToken3.getType(), cls2, cls2.getGenericSuperclass()));
                cls2 = typeToken3.getRawType();
                reflectiveTypeAdapterFactory = this;
            }
        }
        return linkedHashMap;
    }

    private List<String> getFieldNames(Field field) {
        SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName(field));
        }
        String value = serializedName.value();
        String[] alternate = serializedName.alternate();
        if (alternate.length == 0) {
            return Collections.singletonList(value);
        }
        ArrayList arrayList = new ArrayList(alternate.length + 1);
        arrayList.add(value);
        for (String str : alternate) {
            arrayList.add(str);
        }
        return arrayList;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (Object.class.isAssignableFrom(rawType)) {
            return new Adapter(this.constructorConstructor.get(typeToken), getBoundFields(gson, typeToken, rawType));
        }
        return null;
    }

    public boolean excludeField(Field field, boolean z) {
        return excludeField(field, z, this.excluder);
    }

    public static boolean excludeField(Field field, boolean z, Excluder excluder) {
        return (excluder.excludeClass(field.getType(), z) || excluder.excludeField(field, z)) ? false : true;
    }
}
