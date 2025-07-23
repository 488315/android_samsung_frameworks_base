package com.samsung.android.sdk.routines.v3.data;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ParameterValues {
    public final HashMap a;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.samsung.android.sdk.routines.v3.data.ParameterValues$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ParameterValue.ValueType.values().length];
            a = iArr;
            try {
                iArr[ParameterValue.ValueType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ParameterValue.ValueType.NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[ParameterValue.ValueType.LIST_BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[ParameterValue.ValueType.LIST_NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[ParameterValue.ValueType.STRING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[ParameterValue.ValueType.LIST_STRING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class ParameterValue {

        @SerializedName("VALUE")
        private Object a;

        @SerializedName("TYPE")
        private ValueType b;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        enum ValueType {
            UNKNOWN("UNKNOWN"),
            BOOLEAN("BOOLEAN"),
            NUMBER("NUMBER"),
            STRING("STRING"),
            LIST_BOOLEAN("LIST{BOOLEAN}"),
            LIST_NUMBER("LIST{NUMBER}"),
            LIST_STRING("LIST{STRING}");

            public final String a;

            ValueType(String str) {
                this.a = str;
            }
        }

        public ParameterValue(Boolean bool) {
            this.a = bool;
            this.b = ValueType.BOOLEAN;
        }

        public final Object a() {
            return this.a;
        }

        public final String b() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("TYPE", this.b.a);
                int i = 0;
                switch (AnonymousClass1.a[this.b.ordinal()]) {
                    case 1:
                    case 2:
                        jSONObject.put("VALUE", this.a.toString());
                        break;
                    case 3:
                        JSONArray jSONArray = new JSONArray();
                        Boolean[] boolArr = (Boolean[]) this.a;
                        int length = boolArr.length;
                        while (i < length) {
                            jSONArray.put(boolArr[i].toString());
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray);
                        break;
                    case 4:
                        JSONArray jSONArray2 = new JSONArray();
                        Float[] fArr = (Float[]) this.a;
                        int length2 = fArr.length;
                        while (i < length2) {
                            jSONArray2.put(fArr[i].toString());
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray2);
                        break;
                    case 5:
                        jSONObject.put("VALUE", this.a);
                        break;
                    case 6:
                        JSONArray jSONArray3 = new JSONArray();
                        String[] strArr = (String[]) this.a;
                        int length3 = strArr.length;
                        while (i < length3) {
                            jSONArray3.put(strArr[i]);
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray3);
                        break;
                    default:
                        new Throwable().printStackTrace();
                        jSONObject.put("VALUE", this.a);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jSONObject.toString();
        }

        public static ParameterValue a(String str) {
            ValueType valueType;
            ParameterValue parameterValue = new ParameterValue();
            try {
                JSONObject jSONObject = new JSONObject(str);
                String string = jSONObject.getString("TYPE");
                ValueType[] values = ValueType.values();
                int length = values.length;
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        valueType = ValueType.UNKNOWN;
                        break;
                    }
                    valueType = values[i2];
                    if (valueType.a.equalsIgnoreCase(string)) {
                        break;
                    }
                    i2++;
                }
                parameterValue.b = valueType;
                switch (AnonymousClass1.a[valueType.ordinal()]) {
                    case 1:
                        parameterValue.a = Boolean.valueOf(jSONObject.getString("VALUE"));
                        break;
                    case 2:
                        parameterValue.a = Float.valueOf(jSONObject.getString("VALUE"));
                        break;
                    case 3:
                        JSONArray jSONArray = jSONObject.getJSONArray("VALUE");
                        Boolean[] boolArr = new Boolean[jSONArray.length()];
                        while (i < jSONArray.length()) {
                            boolArr[i] = Boolean.valueOf(jSONArray.getString(i));
                            i++;
                        }
                        parameterValue.a = boolArr;
                        break;
                    case 4:
                        JSONArray jSONArray2 = jSONObject.getJSONArray("VALUE");
                        Float[] fArr = new Float[jSONArray2.length()];
                        while (i < jSONArray2.length()) {
                            fArr[i] = Float.valueOf(jSONArray2.getString(i));
                            i++;
                        }
                        parameterValue.a = fArr;
                        break;
                    case 5:
                        parameterValue.a = jSONObject.getString("VALUE");
                        break;
                    case 6:
                        JSONArray jSONArray3 = jSONObject.getJSONArray("VALUE");
                        String[] strArr = new String[jSONArray3.length()];
                        while (i < jSONArray3.length()) {
                            strArr[i] = jSONArray3.getString(i);
                            i++;
                        }
                        parameterValue.a = strArr;
                        break;
                    default:
                        new Throwable().printStackTrace();
                        parameterValue.a = jSONObject.get("VALUE");
                        break;
                }
                return parameterValue;
            } catch (NumberFormatException | JSONException e) {
                e.printStackTrace();
                return parameterValue;
            }
        }

        public ParameterValue(Boolean[] boolArr) {
            this.a = boolArr.clone();
            this.b = ValueType.LIST_BOOLEAN;
        }

        public ParameterValue(Float f) {
            this.a = f;
            this.b = ValueType.NUMBER;
        }

        public ParameterValue(Float[] fArr) {
            this.a = fArr.clone();
            this.b = ValueType.LIST_NUMBER;
        }

        public ParameterValue(String str) {
            this.a = str;
            this.b = ValueType.STRING;
        }

        public ParameterValue(String[] strArr) {
            this.a = strArr.clone();
            this.b = ValueType.LIST_STRING;
        }

        public ParameterValue() {
        }
    }

    public ParameterValues() {
        this.a = new HashMap();
    }

    public static ParameterValues fromJsonString(String str) {
        HashMap hashMap = new HashMap();
        if (str == null || str.isEmpty()) {
            return new ParameterValues(hashMap);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, ParameterValue.a(jSONObject.getString(next)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ParameterValues(hashMap);
    }

    public final Boolean getBoolean() {
        Boolean bool = Boolean.FALSE;
        ParameterValue parameterValue = (ParameterValue) this.a.get("is_buds_action");
        return (parameterValue == null || parameterValue.a() == null) ? bool : (Boolean) parameterValue.a();
    }

    public final String getString(String str, String str2) {
        ParameterValue parameterValue = (ParameterValue) this.a.get(str);
        return (parameterValue == null || parameterValue.a() == null) ? str2 : (String) parameterValue.a();
    }

    public final void put(String str, String str2) {
        this.a.put(str, new ParameterValue(str2));
    }

    public final String toJsonString() {
        final HashMap hashMap = new HashMap();
        this.a.entrySet().forEach(new Consumer() { // from class: com.samsung.android.sdk.routines.v3.data.ParameterValues$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                ((HashMap) hashMap).put((String) entry.getKey(), ((ParameterValues.ParameterValue) entry.getValue()).b());
            }
        });
        return new JSONObject(hashMap).toString();
    }

    public ParameterValues(HashMap hashMap) {
        HashMap hashMap2 = new HashMap();
        this.a = hashMap2;
        hashMap2.putAll(hashMap);
    }
}
