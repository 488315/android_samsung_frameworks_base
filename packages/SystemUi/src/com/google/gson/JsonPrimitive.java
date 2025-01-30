package com.google.gson;

import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigInteger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class JsonPrimitive extends JsonElement {
    public final Object value;

    public JsonPrimitive(Boolean bool) {
        bool.getClass();
        this.value = bool;
    }

    public static boolean isIntegral(JsonPrimitive jsonPrimitive) {
        Object obj = jsonPrimitive.value;
        if (!(obj instanceof Number)) {
            return false;
        }
        Number number = (Number) obj;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || JsonPrimitive.class != obj.getClass()) {
            return false;
        }
        JsonPrimitive jsonPrimitive = (JsonPrimitive) obj;
        Object obj2 = this.value;
        Object obj3 = jsonPrimitive.value;
        if (obj2 == null) {
            return obj3 == null;
        }
        if (isIntegral(this) && isIntegral(jsonPrimitive)) {
            return getAsNumber().longValue() == jsonPrimitive.getAsNumber().longValue();
        }
        if (!(obj2 instanceof Number) || !(obj3 instanceof Number)) {
            return obj2.equals(obj3);
        }
        double doubleValue = getAsNumber().doubleValue();
        double doubleValue2 = jsonPrimitive.getAsNumber().doubleValue();
        if (doubleValue != doubleValue2) {
            return Double.isNaN(doubleValue) && Double.isNaN(doubleValue2);
        }
        return true;
    }

    @Override // com.google.gson.JsonElement
    public final boolean getAsBoolean() {
        Object obj = this.value;
        return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : Boolean.parseBoolean(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public final int getAsInt() {
        return this.value instanceof Number ? getAsNumber().intValue() : Integer.parseInt(getAsString());
    }

    public final Number getAsNumber() {
        Object obj = this.value;
        return obj instanceof String ? new LazilyParsedNumber((String) obj) : (Number) obj;
    }

    @Override // com.google.gson.JsonElement
    public final String getAsString() {
        Object obj = this.value;
        return obj instanceof Number ? getAsNumber().toString() : obj instanceof Boolean ? ((Boolean) obj).toString() : (String) obj;
    }

    public final int hashCode() {
        long doubleToLongBits;
        Object obj = this.value;
        if (obj == null) {
            return 31;
        }
        if (isIntegral(this)) {
            doubleToLongBits = getAsNumber().longValue();
        } else {
            if (!(obj instanceof Number)) {
                return obj.hashCode();
            }
            doubleToLongBits = Double.doubleToLongBits(getAsNumber().doubleValue());
        }
        return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
    }

    public JsonPrimitive(Number number) {
        number.getClass();
        this.value = number;
    }

    public JsonPrimitive(String str) {
        str.getClass();
        this.value = str;
    }

    public JsonPrimitive(Character ch) {
        ch.getClass();
        this.value = ch.toString();
    }
}
