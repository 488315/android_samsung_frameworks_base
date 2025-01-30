package com.samsung.android.sdk.routines.automationservice.data;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.sdk.routines.automationservice.internal.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ParameterValues {
    public static final Companion Companion = new Companion(null);
    public final Map parameterValueMap;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ParameterValue {
        public static final Companion Companion = new Companion(null);

        @SerializedName("TYPE")
        private ValueType mValueType;

        @SerializedName("VALUE")
        private Object value;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[ValueType.values().length];
                    try {
                        iArr[ValueType.BOOLEAN.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[ValueType.NUMBER.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[ValueType.STRING.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[ValueType.LIST_BOOLEAN.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr[ValueType.LIST_NUMBER.ordinal()] = 5;
                    } catch (NoSuchFieldError unused5) {
                    }
                    try {
                        iArr[ValueType.LIST_STRING.ordinal()] = 6;
                    } catch (NoSuchFieldError unused6) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public static ValueType parseType(JSONObject jSONObject) {
                ValueType.Companion companion = ValueType.Companion;
                String string = jSONObject.getString("TYPE");
                companion.getClass();
                for (ValueType valueType : ValueType.values()) {
                    if (StringsKt__StringsJVMKt.equals(valueType.getMName(), string, true)) {
                        return valueType;
                    }
                }
                return ValueType.UNKNOWN;
            }

            /* JADX WARN: Code restructure failed: missing block: B:101:0x0067, code lost:
            
                if (kotlin.jvm.internal.Intrinsics.areEqual(r4, kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(java.lang.String.class)) != false) goto L26;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public static Object parseValue(JSONObject jSONObject, ValueType valueType) {
                Object[] objArr;
                Boolean bool;
                ClassReference orCreateKotlinClass;
                Float f;
                ClassReference orCreateKotlinClass2;
                String string;
                Object obj;
                try {
                    int i = 0;
                    switch (WhenMappings.$EnumSwitchMapping$0[valueType.ordinal()]) {
                        case 1:
                            String string2 = jSONObject.getString("VALUE");
                            try {
                                ClassReference orCreateKotlinClass3 = Reflection.getOrCreateKotlinClass(Boolean.class);
                                if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                    Object valueOf = Boolean.valueOf(string2);
                                    obj = valueOf;
                                    if (valueOf == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
                                    }
                                } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                    Object valueOf2 = Float.valueOf(string2);
                                    if (valueOf2 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
                                    }
                                    obj = (Boolean) valueOf2;
                                } else {
                                    if (!Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(String.class))) {
                                        return null;
                                    }
                                    obj = (Boolean) string2;
                                }
                                return obj;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        case 2:
                            String string3 = jSONObject.getString("VALUE");
                            try {
                                ClassReference orCreateKotlinClass4 = Reflection.getOrCreateKotlinClass(Float.class);
                                if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                    Object valueOf3 = Boolean.valueOf(string3);
                                    if (valueOf3 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Float");
                                    }
                                    obj = (Float) valueOf3;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                    Object valueOf4 = Float.valueOf(string3);
                                    if (valueOf4 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Float");
                                    }
                                    obj = valueOf4;
                                } else {
                                    if (!Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(String.class))) {
                                        return null;
                                    }
                                    obj = (Float) string3;
                                }
                                return obj;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                return null;
                            }
                        case 3:
                            String string4 = jSONObject.getString("VALUE");
                            try {
                                ClassReference orCreateKotlinClass5 = Reflection.getOrCreateKotlinClass(String.class);
                                if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                    Object valueOf5 = Boolean.valueOf(string4);
                                    if (valueOf5 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                                    }
                                    obj = (String) valueOf5;
                                } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                    Object valueOf6 = Float.valueOf(string4);
                                    if (valueOf6 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                                    }
                                    obj = (String) valueOf6;
                                } else {
                                    obj = string4;
                                    if (!Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(String.class))) {
                                        return null;
                                    }
                                }
                                return obj;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return null;
                            }
                        case 4:
                            try {
                                JSONArray jSONArray = jSONObject.getJSONArray("VALUE");
                                int length = jSONArray.length();
                                objArr = new Boolean[length];
                                for (int i2 = 0; i2 < length; i2++) {
                                    objArr[i2] = null;
                                }
                                int length2 = jSONArray.length();
                                while (i < length2) {
                                    String string5 = jSONArray.getString(i);
                                    try {
                                        orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Boolean.class);
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                    if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                        bool = Boolean.valueOf(string5);
                                    } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                        bool = (Boolean) Float.valueOf(string5);
                                    } else {
                                        if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(String.class))) {
                                            bool = (Boolean) string5;
                                        }
                                        bool = null;
                                    }
                                    objArr[i] = bool;
                                    i++;
                                }
                                return objArr;
                            } catch (JSONException e5) {
                                e5.printStackTrace();
                                return null;
                            }
                        case 5:
                            try {
                                JSONArray jSONArray2 = jSONObject.getJSONArray("VALUE");
                                int length3 = jSONArray2.length();
                                Float[] fArr = new Float[length3];
                                for (int i3 = 0; i3 < length3; i3++) {
                                    fArr[i3] = null;
                                }
                                int length4 = jSONArray2.length();
                                while (i < length4) {
                                    String string6 = jSONArray2.getString(i);
                                    try {
                                        orCreateKotlinClass2 = Reflection.getOrCreateKotlinClass(Float.class);
                                    } catch (Exception e6) {
                                        e6.printStackTrace();
                                    }
                                    if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                        f = (Float) Boolean.valueOf(string6);
                                    } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                        f = Float.valueOf(string6);
                                    } else {
                                        if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(String.class))) {
                                            f = (Float) string6;
                                        }
                                        f = null;
                                    }
                                    fArr[i] = f;
                                    i++;
                                }
                                return fArr;
                            } catch (JSONException e7) {
                                e7.printStackTrace();
                                return null;
                            }
                        case 6:
                            try {
                                JSONArray jSONArray3 = jSONObject.getJSONArray("VALUE");
                                int length5 = jSONArray3.length();
                                objArr = new String[length5];
                                for (int i4 = 0; i4 < length5; i4++) {
                                    objArr[i4] = null;
                                }
                                int length6 = jSONArray3.length();
                                while (i < length6) {
                                    string = jSONArray3.getString(i);
                                    try {
                                        ClassReference orCreateKotlinClass6 = Reflection.getOrCreateKotlinClass(String.class);
                                        if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                            string = (String) Boolean.valueOf(string);
                                        } else if (!Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                            break;
                                        } else {
                                            string = (String) Float.valueOf(string);
                                        }
                                    } catch (Exception e8) {
                                        e8.printStackTrace();
                                        break;
                                    }
                                    objArr[i] = string;
                                    i++;
                                }
                                return objArr;
                            } catch (JSONException e9) {
                                e9.printStackTrace();
                                return null;
                            }
                        default:
                            new Throwable().printStackTrace();
                            return jSONObject.get("VALUE");
                    }
                    string = null;
                    objArr[i] = string;
                    i++;
                } catch (JSONException e10) {
                    Log log = Log.INSTANCE;
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    e10.printStackTrace(printWriter);
                    printWriter.flush();
                    String stringWriter2 = stringWriter.toString();
                    log.getClass();
                    Log.m265e("ParameterValue", stringWriter2);
                    return null;
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public enum ValueType {
            UNKNOWN("UNKNOWN"),
            BOOLEAN("BOOLEAN"),
            NUMBER("NUMBER"),
            STRING("STRING"),
            LIST_BOOLEAN("LIST{BOOLEAN}"),
            LIST_NUMBER("LIST{NUMBER}"),
            LIST_STRING("LIST{STRING}");

            public static final Companion Companion = new Companion(null);
            private final String mName;

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            public final class Companion {
                private Companion() {
                }

                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }
            }

            ValueType(String str) {
                this.mName = str;
            }

            public final String getMName() {
                return this.mName;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ValueType.values().length];
                try {
                    iArr[ValueType.BOOLEAN.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ValueType.NUMBER.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ValueType.LIST_BOOLEAN.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ValueType.LIST_NUMBER.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[ValueType.STRING.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[ValueType.LIST_STRING.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private ParameterValue() {
        }

        public /* synthetic */ ParameterValue(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Object getValue() {
            return this.value;
        }

        public final ValueType getValueType() {
            ValueType valueType = this.mValueType;
            if (valueType == null) {
                return null;
            }
            return valueType;
        }

        public final String toJsonString() {
            JSONObject jSONObject = new JSONObject();
            try {
                ValueType valueType = this.mValueType;
                ValueType valueType2 = null;
                if (valueType == null) {
                    valueType = null;
                }
                jSONObject.put("TYPE", valueType.getMName());
                ValueType valueType3 = this.mValueType;
                if (valueType3 != null) {
                    valueType2 = valueType3;
                }
                int i = 0;
                switch (WhenMappings.$EnumSwitchMapping$0[valueType2.ordinal()]) {
                    case 1:
                    case 2:
                        jSONObject.put("VALUE", String.valueOf(this.value));
                        break;
                    case 3:
                        JSONArray jSONArray = new JSONArray();
                        Object[] objArr = (Object[]) this.value;
                        int length = objArr.length;
                        while (i < length) {
                            jSONArray.put(String.valueOf(objArr[i]));
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray);
                        break;
                    case 4:
                        JSONArray jSONArray2 = new JSONArray();
                        Object[] objArr2 = (Object[]) this.value;
                        int length2 = objArr2.length;
                        while (i < length2) {
                            jSONArray2.put(String.valueOf(objArr2[i]));
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray2);
                        break;
                    case 5:
                        jSONObject.put("VALUE", this.value);
                        break;
                    case 6:
                        JSONArray jSONArray3 = new JSONArray();
                        Object[] objArr3 = (Object[]) this.value;
                        int length3 = objArr3.length;
                        while (i < length3) {
                            jSONArray3.put(objArr3[i]);
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray3);
                        break;
                    default:
                        new Throwable().printStackTrace();
                        jSONObject.put("VALUE", this.value);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jSONObject.toString();
        }

        public ParameterValue(boolean z) {
            this.value = Boolean.valueOf(z);
            this.mValueType = ValueType.BOOLEAN;
        }

        public ParameterValue(Boolean[] boolArr) {
            this.value = boolArr.clone();
            this.mValueType = ValueType.LIST_BOOLEAN;
        }

        public ParameterValue(float f) {
            this.value = Float.valueOf(f);
            this.mValueType = ValueType.NUMBER;
        }

        public ParameterValue(Float[] fArr) {
            this.value = fArr.clone();
            this.mValueType = ValueType.LIST_NUMBER;
        }

        public ParameterValue(String str) {
            this.value = str;
            this.mValueType = ValueType.STRING;
        }

        public ParameterValue(String[] strArr) {
            this.value = strArr.clone();
            this.mValueType = ValueType.LIST_STRING;
        }
    }

    public /* synthetic */ ParameterValues(Map map, DefaultConstructorMarker defaultConstructorMarker) {
        this(map);
    }

    public final void put(String str, String str2) {
        ((HashMap) this.parameterValueMap).put(str, new ParameterValue(str2));
    }

    public final String toJsonString() {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : ((HashMap) this.parameterValueMap).entrySet()) {
            hashMap.put(entry.getKey(), ((ParameterValue) entry.getValue()).toJsonString());
        }
        return new JSONObject(hashMap).toString();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : ((HashMap) this.parameterValueMap).entrySet()) {
            sb.append("{");
            sb.append((String) entry.getKey());
            sb.append(",");
            sb.append(((ParameterValue) entry.getValue()).getValue());
            sb.append("@");
            sb.append(((ParameterValue) entry.getValue()).getValueType().name());
            sb.append("}");
        }
        return sb.toString();
    }

    public ParameterValues() {
        this.parameterValueMap = new HashMap();
    }

    private ParameterValues(Map<String, ParameterValue> map) {
        HashMap hashMap = new HashMap();
        this.parameterValueMap = hashMap;
        hashMap.putAll(map);
    }
}
