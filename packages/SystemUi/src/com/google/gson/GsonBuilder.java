package com.google.gson;

import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.DefaultDateTypeAdapter;
import com.google.gson.internal.sql.SqlTypesSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GsonBuilder {
    public final boolean complexMapKeySerialization;
    public final String datePattern;
    public final int dateStyle;
    public boolean escapeHtmlChars;
    public final Excluder excluder;
    public final List factories;
    public final FieldNamingStrategy fieldNamingPolicy;
    public final boolean generateNonExecutableJson;
    public final List hierarchyFactories;
    public final Map instanceCreators;
    public final boolean lenient;
    public final LongSerializationPolicy longSerializationPolicy;
    public final ToNumberStrategy numberToNumberStrategy;
    public final ToNumberStrategy objectToNumberStrategy;
    public boolean prettyPrinting;
    public boolean serializeNulls;
    public final boolean serializeSpecialFloatingPointValues;
    public final int timeStyle;
    public final boolean useJdkUnsafe;

    public GsonBuilder() {
        this.excluder = Excluder.DEFAULT;
        this.longSerializationPolicy = LongSerializationPolicy.DEFAULT;
        this.fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
        this.instanceCreators = new HashMap();
        this.factories = new ArrayList();
        this.hierarchyFactories = new ArrayList();
        this.serializeNulls = false;
        FieldNamingStrategy fieldNamingStrategy = Gson.DEFAULT_FIELD_NAMING_STRATEGY;
        this.datePattern = null;
        this.dateStyle = 2;
        this.timeStyle = 2;
        this.complexMapKeySerialization = false;
        this.serializeSpecialFloatingPointValues = false;
        this.escapeHtmlChars = true;
        this.prettyPrinting = false;
        this.generateNonExecutableJson = false;
        this.lenient = false;
        this.useJdkUnsafe = true;
        this.objectToNumberStrategy = Gson.DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
        this.numberToNumberStrategy = Gson.DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Gson create() {
        int i;
        TypeAdapterFactory createAdapterFactory;
        TypeAdapterFactory createAdapterFactory2;
        TypeAdapterFactory typeAdapterFactory;
        List list = this.factories;
        int size = ((ArrayList) list).size();
        List list2 = this.hierarchyFactories;
        ArrayList arrayList = new ArrayList(((ArrayList) list2).size() + size + 3);
        arrayList.addAll(list);
        Collections.reverse(arrayList);
        ArrayList arrayList2 = new ArrayList(list2);
        Collections.reverse(arrayList2);
        arrayList.addAll(arrayList2);
        boolean z = SqlTypesSupport.SUPPORTS_SQL_TYPES;
        String str = this.datePattern;
        if (str == null || str.trim().isEmpty()) {
            int i2 = this.dateStyle;
            if (i2 != 2 && (i = this.timeStyle) != 2) {
                createAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(i2, i);
                if (z) {
                    TypeAdapterFactory createAdapterFactory3 = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(i2, i);
                    createAdapterFactory2 = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(i2, i);
                    typeAdapterFactory = createAdapterFactory3;
                    arrayList.add(createAdapterFactory);
                    if (z) {
                        arrayList.add(typeAdapterFactory);
                        arrayList.add(createAdapterFactory2);
                    }
                }
                typeAdapterFactory = null;
                createAdapterFactory2 = null;
                arrayList.add(createAdapterFactory);
                if (z) {
                }
            }
        } else {
            createAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(str);
            if (z) {
                typeAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(str);
                createAdapterFactory2 = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(str);
                arrayList.add(createAdapterFactory);
                if (z) {
                }
            }
            typeAdapterFactory = null;
            createAdapterFactory2 = null;
            arrayList.add(createAdapterFactory);
            if (z) {
            }
        }
        return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.lenient, this.serializeSpecialFloatingPointValues, this.useJdkUnsafe, this.longSerializationPolicy, this.datePattern, this.dateStyle, this.timeStyle, list, list2, arrayList, this.objectToNumberStrategy, this.numberToNumberStrategy);
    }

    public GsonBuilder(Gson gson) {
        this.excluder = Excluder.DEFAULT;
        this.longSerializationPolicy = LongSerializationPolicy.DEFAULT;
        this.fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
        HashMap hashMap = new HashMap();
        this.instanceCreators = hashMap;
        ArrayList arrayList = new ArrayList();
        this.factories = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.hierarchyFactories = arrayList2;
        this.serializeNulls = false;
        FieldNamingStrategy fieldNamingStrategy = Gson.DEFAULT_FIELD_NAMING_STRATEGY;
        this.datePattern = null;
        this.dateStyle = 2;
        this.timeStyle = 2;
        this.complexMapKeySerialization = false;
        this.serializeSpecialFloatingPointValues = false;
        this.escapeHtmlChars = true;
        this.prettyPrinting = false;
        this.generateNonExecutableJson = false;
        this.lenient = false;
        this.useJdkUnsafe = true;
        this.objectToNumberStrategy = Gson.DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
        this.numberToNumberStrategy = Gson.DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
        this.excluder = gson.excluder;
        this.fieldNamingPolicy = gson.fieldNamingStrategy;
        hashMap.putAll(gson.instanceCreators);
        this.serializeNulls = gson.serializeNulls;
        this.complexMapKeySerialization = gson.complexMapKeySerialization;
        this.generateNonExecutableJson = gson.generateNonExecutableJson;
        this.escapeHtmlChars = gson.htmlSafe;
        this.prettyPrinting = gson.prettyPrinting;
        this.lenient = gson.lenient;
        this.serializeSpecialFloatingPointValues = gson.serializeSpecialFloatingPointValues;
        this.longSerializationPolicy = gson.longSerializationPolicy;
        this.datePattern = gson.datePattern;
        this.dateStyle = gson.dateStyle;
        this.timeStyle = gson.timeStyle;
        arrayList.addAll(gson.builderFactories);
        arrayList2.addAll(gson.builderHierarchyFactories);
        this.useJdkUnsafe = gson.useJdkUnsafe;
        this.objectToNumberStrategy = gson.objectToNumberStrategy;
        this.numberToNumberStrategy = gson.numberToNumberStrategy;
    }
}
