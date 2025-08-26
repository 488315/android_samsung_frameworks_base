package com.samsung.android.sdk.routines.automationservice.data;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.sdk.routines.automationservice.internal.Log;
import java.util.HashMap;
import java.util.Map;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ParameterValues {
    public static final Companion Companion = new Companion(null);
    public final Map parameterValueMap;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class ParameterValue {
        public static final Companion Companion = new Companion(null);

        @SerializedName("TYPE")
        private ValueType mValueType;

        @SerializedName("VALUE")
        private Object value;

        public final class Companion {

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

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public static ValueType parseType(JSONObject jSONObject) throws JSONException {
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

            /* JADX WARN: Multi-variable type inference failed */
            public static Object parseValue(JSONObject jSONObject, ValueType valueType) throws JSONException, NumberFormatException {
                Object[] objArr;
                ClassReference orCreateKotlinClass;
                ClassReference orCreateKotlinClass2;
                ClassReference orCreateKotlinClass3;
                Object obj;
                try {
                    int i = 0;
                    switch (WhenMappings.$EnumSwitchMapping$0[valueType.ordinal()]) {
                        case 1:
                            String string = jSONObject.getString("VALUE");
                            try {
                                ClassReference orCreateKotlinClass4 = Reflection.getOrCreateKotlinClass(Boolean.class);
                                if (orCreateKotlinClass4.equals(Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                    Object objValueOf = Boolean.valueOf(string);
                                    if (objValueOf == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
                                    }
                                    obj = objValueOf;
                                } else if (orCreateKotlinClass4.equals(Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                    Object objValueOf2 = Float.valueOf(string);
                                    if (objValueOf2 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
                                    }
                                    obj = (Boolean) objValueOf2;
                                } else {
                                    if (!orCreateKotlinClass4.equals(Reflection.getOrCreateKotlinClass(String.class))) {
                                        return null;
                                    }
                                    obj = (Boolean) string;
                                }
                                return obj;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        case 2:
                            String string2 = jSONObject.getString("VALUE");
                            try {
                                ClassReference orCreateKotlinClass5 = Reflection.getOrCreateKotlinClass(Float.class);
                                if (orCreateKotlinClass5.equals(Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                    Object objValueOf3 = Boolean.valueOf(string2);
                                    if (objValueOf3 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Float");
                                    }
                                    obj = (Float) objValueOf3;
                                } else if (orCreateKotlinClass5.equals(Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                    Object objValueOf4 = Float.valueOf(string2);
                                    if (objValueOf4 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Float");
                                    }
                                    obj = objValueOf4;
                                } else {
                                    if (!orCreateKotlinClass5.equals(Reflection.getOrCreateKotlinClass(String.class))) {
                                        return null;
                                    }
                                    obj = (Float) string2;
                                }
                                return obj;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                return null;
                            }
                        case 3:
                            String string3 = jSONObject.getString("VALUE");
                            try {
                                ClassReference orCreateKotlinClass6 = Reflection.getOrCreateKotlinClass(String.class);
                                if (orCreateKotlinClass6.equals(Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                    Object objValueOf5 = Boolean.valueOf(string3);
                                    if (objValueOf5 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                                    }
                                    obj = (String) objValueOf5;
                                } else if (orCreateKotlinClass6.equals(Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                    Object objValueOf6 = Float.valueOf(string3);
                                    if (objValueOf6 == null) {
                                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                                    }
                                    obj = (String) objValueOf6;
                                } else {
                                    obj = string3;
                                    if (!orCreateKotlinClass6.equals(Reflection.getOrCreateKotlinClass(String.class))) {
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
                                Boolean[] boolArr = new Boolean[length];
                                for (int i2 = 0; i2 < length; i2++) {
                                    boolArr[i2] = null;
                                }
                                int length2 = jSONArray.length();
                                while (i < length2) {
                                    String string4 = jSONArray.getString(i);
                                    try {
                                        orCreateKotlinClass3 = Reflection.getOrCreateKotlinClass(Boolean.class);
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                    Boolean boolValueOf = orCreateKotlinClass3.equals(Reflection.getOrCreateKotlinClass(Boolean.TYPE)) ? Boolean.valueOf(string4) : orCreateKotlinClass3.equals(Reflection.getOrCreateKotlinClass(Float.TYPE)) ? (Boolean) Float.valueOf(string4) : orCreateKotlinClass3.equals(Reflection.getOrCreateKotlinClass(String.class)) ? (Boolean) string4 : null;
                                    boolArr[i] = boolValueOf;
                                    i++;
                                }
                                return boolArr;
                            } catch (JSONException e5) {
                                e5.printStackTrace();
                                return null;
                            }
                        case 5:
                            try {
                                JSONArray jSONArray2 = jSONObject.getJSONArray("VALUE");
                                int length3 = jSONArray2.length();
                                objArr = new Float[length3];
                                for (int i3 = 0; i3 < length3; i3++) {
                                    objArr[i3] = null;
                                }
                                int length4 = jSONArray2.length();
                                while (i < length4) {
                                    String string5 = jSONArray2.getString(i);
                                    try {
                                        orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Float.class);
                                    } catch (Exception e6) {
                                        e6.printStackTrace();
                                    }
                                    Float fValueOf = orCreateKotlinClass.equals(Reflection.getOrCreateKotlinClass(Boolean.TYPE)) ? (Float) Boolean.valueOf(string5) : orCreateKotlinClass.equals(Reflection.getOrCreateKotlinClass(Float.TYPE)) ? Float.valueOf(string5) : orCreateKotlinClass.equals(Reflection.getOrCreateKotlinClass(String.class)) ? (Float) string5 : null;
                                    objArr[i] = fValueOf;
                                    i++;
                                }
                                return objArr;
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
                                    String string6 = jSONArray3.getString(i);
                                    try {
                                        orCreateKotlinClass2 = Reflection.getOrCreateKotlinClass(String.class);
                                    } catch (Exception e8) {
                                        e8.printStackTrace();
                                    }
                                    if (orCreateKotlinClass2.equals(Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                                        string6 = (String) Boolean.valueOf(string6);
                                    } else if (orCreateKotlinClass2.equals(Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                                        string6 = (String) Float.valueOf(string6);
                                    } else if (!orCreateKotlinClass2.equals(Reflection.getOrCreateKotlinClass(String.class))) {
                                        string6 = null;
                                    }
                                    objArr[i] = string6;
                                    i++;
                                }
                                return objArr;
                            } catch (JSONException e9) {
                                e9.printStackTrace();
                                return null;
                            }
                        default:
                            new Throwable().printStackTrace();
                            obj = jSONObject.get("VALUE");
                            return obj;
                    }
                } catch (JSONException e10) {
                    Log log = Log.INSTANCE;
                    String strStackTraceToString = ExceptionsKt__ExceptionsKt.stackTraceToString(e10);
                    log.getClass();
                    Log.e("ParameterValue", strStackTraceToString);
                    return null;
                }
            }

            private Companion() {
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        public final class ValueType {
            public static final /* synthetic */ ValueType[] $VALUES;
            public static final ValueType BOOLEAN;
            public static final Companion Companion;
            public static final ValueType LIST_BOOLEAN;
            public static final ValueType LIST_NUMBER;
            public static final ValueType LIST_STRING;
            public static final ValueType NUMBER;
            public static final ValueType STRING;
            public static final ValueType UNKNOWN;
            private final String mName;

            public final class Companion {
                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }

                private Companion() {
                }
            }

            static {
                ValueType valueType = new ValueType("UNKNOWN", 0, "UNKNOWN");
                UNKNOWN = valueType;
                ValueType valueType2 = new ValueType("BOOLEAN", 1, "BOOLEAN");
                BOOLEAN = valueType2;
                ValueType valueType3 = new ValueType("NUMBER", 2, "NUMBER");
                NUMBER = valueType3;
                ValueType valueType4 = new ValueType("STRING", 3, "STRING");
                STRING = valueType4;
                ValueType valueType5 = new ValueType("LIST_BOOLEAN", 4, "LIST{BOOLEAN}");
                LIST_BOOLEAN = valueType5;
                ValueType valueType6 = new ValueType("LIST_NUMBER", 5, "LIST{NUMBER}");
                LIST_NUMBER = valueType6;
                ValueType valueType7 = new ValueType("LIST_STRING", 6, "LIST{STRING}");
                LIST_STRING = valueType7;
                ValueType[] valueTypeArr = {valueType, valueType2, valueType3, valueType4, valueType5, valueType6, valueType7};
                $VALUES = valueTypeArr;
                EnumEntriesKt.enumEntries(valueTypeArr);
                Companion = new Companion(null);
            }

            private ValueType(String str, int i, String str2) {
                this.mName = str2;
            }

            public static ValueType valueOf(String str) {
                return (ValueType) Enum.valueOf(ValueType.class, str);
            }

            public static ValueType[] values() {
                return (ValueType[]) $VALUES.clone();
            }

            public final String getMName() {
                return this.mName;
            }
        }

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

        public final String toJsonString() throws JSONException {
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

        private ParameterValue() {
        }
    }

    public /* synthetic */ ParameterValues(Map map, DefaultConstructorMarker defaultConstructorMarker) {
        this(map);
    }

    public final String getString(String str, String str2) {
        ParameterValue parameterValue = (ParameterValue) ((HashMap) this.parameterValueMap).get(str);
        return (parameterValue == null || parameterValue.getValue() == null) ? str2 : (String) parameterValue.getValue();
    }

    public final void put(String str, String str2) {
        ((HashMap) this.parameterValueMap).put(str, new ParameterValue(str2));
    }

    public final String toJsonString() {
        HashMap map = new HashMap();
        for (Map.Entry entry : ((HashMap) this.parameterValueMap).entrySet()) {
            map.put(entry.getKey(), ((ParameterValue) entry.getValue()).toJsonString());
        }
        return new JSONObject(map).toString();
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
        HashMap map2 = new HashMap();
        this.parameterValueMap = map2;
        map2.putAll(map);
    }
}
