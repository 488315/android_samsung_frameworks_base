package com.samsung.android.wallpaper.utils;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;

class BundleAndJsonConverter {
    private static final String CHARSET_UTF_8 = "UTF-8";
    private static final String JSON_INDENT = "  ";
    private static final String JSON_VALUE_TYPE_DELIMITER = "|";
    private static final String JSON_VALUE_TYPE_PREFIX_BOOLEAN = "B";
    private static final String JSON_VALUE_TYPE_PREFIX_BUNDLE = "BD";
    private static final String JSON_VALUE_TYPE_PREFIX_DOUBLE = "D";
    private static final String JSON_VALUE_TYPE_PREFIX_FLOAT = "F";
    private static final String JSON_VALUE_TYPE_PREFIX_INTEGER = "I";
    private static final String JSON_VALUE_TYPE_PREFIX_LONG = "L";
    private static final String JSON_VALUE_TYPE_PREFIX_STRING = "S";
    private static final String TAG = BundleAndJsonConverter.class.getSimpleName();

    BundleAndJsonConverter() {}

    public Bundle convertJsonToBundle(String jsonStr) {
        if (jsonStr == null) {
            return null;
        }
        Bundle resultBundle = new Bundle();
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            putJsonObjectFieldsToBundle(reader, resultBundle);
            reader.close();
            inputStream.close();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "convertJsonToBundle : e=" + e);
        } catch (IOException e2) {
            Log.e(TAG, "convertJsonToBundle : e=" + e2);
        }
        return resultBundle;
    }

    public String convertBundleToJson(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(outStream, "UTF-8"));
            writer.setIndent(JSON_INDENT);
            writeBundleToJson(bundle, writer);
            writer.close();
            outStream.close();
            return new String(outStream.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "convertBundleToJson : e=" + e);
            return "";
        } catch (IOException e2) {
            Log.e(TAG, "convertBundleToJson : e=" + e2);
            return "";
        }
    }

    private void writeBundleToJson(Bundle bundle, JsonWriter writer) throws IOException {
        String stringValue;
        writer.beginObject();
        Set<String> keySet = bundle.keySet();
        for (String key : keySet) {
            Object value = bundle.get(key);
            if (value == null) {
                Log.i(TAG, "writeBundleToJson: the value of " + key + " is null. skipping..");
            } else {
                String typePrefix = determineDataTypePrefix(value);
                if (typePrefix == null) {
                    Log.i(
                            TAG,
                            "writeBundleToJson: unsupported value type : key="
                                    + key
                                    + ", type="
                                    + value.getClass().getSimpleName()
                                    + ", skipping..");
                } else {
                    writer.name(key);
                    if (JSON_VALUE_TYPE_PREFIX_BUNDLE.equals(typePrefix)) {
                        stringValue = convertBundleToJson((Bundle) value);
                    } else {
                        stringValue = value.toString();
                    }
                    writer.value(typePrefix + "|" + stringValue);
                }
            }
        }
        writer.endObject();
    }

    private void putJsonObjectFieldsToBundle(JsonReader reader, Bundle resultBundle)
            throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();
            String jsonValue = reader.nextString();
            Object valueObject = Boolean.valueOf(putValueToBundle(resultBundle, key, jsonValue));
            if (valueObject == null) {
                Log.d(TAG, "putJsonObjectFieldsToBundle: failed to decode value. key=" + key);
            }
        }
        reader.endObject();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean putValueToBundle(Bundle bundle, String key, String jsonValue) {
        char c;
        if (bundle == null || key == null || jsonValue == null) {
            return false;
        }
        int delimiterIndex = jsonValue.indexOf("|");
        if (delimiterIndex < 0) {
            Log.e(TAG, "putValueToBundle : type delimiter is absent : " + jsonValue);
            return false;
        }
        String typePrefix = jsonValue.substring(0, delimiterIndex);
        String strTypeValue = jsonValue.substring(delimiterIndex + 1, jsonValue.length());
        switch (typePrefix.hashCode()) {
            case 66:
                if (typePrefix.equals("B")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 68:
                if (typePrefix.equals("D")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 70:
                if (typePrefix.equals(JSON_VALUE_TYPE_PREFIX_FLOAT)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 73:
                if (typePrefix.equals("I")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 76:
                if (typePrefix.equals("L")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 83:
                if (typePrefix.equals("S")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2114:
                if (typePrefix.equals(JSON_VALUE_TYPE_PREFIX_BUNDLE)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                bundle.putString(key, strTypeValue);
                break;
            case 1:
                bundle.putBoolean(key, Boolean.valueOf(strTypeValue).booleanValue());
                break;
            case 2:
                bundle.putInt(key, Integer.valueOf(strTypeValue).intValue());
                break;
            case 3:
                bundle.putLong(key, Long.valueOf(strTypeValue).longValue());
                break;
            case 4:
                bundle.putFloat(key, Float.valueOf(strTypeValue).floatValue());
                break;
            case 5:
                bundle.putDouble(key, Double.valueOf(strTypeValue).doubleValue());
                break;
            case 6:
                bundle.putBundle(key, convertJsonToBundle(strTypeValue));
                break;
            default:
                Log.e(TAG, "putValueToBundle: unexpected data type : " + jsonValue);
                break;
        }
        return false;
    }

    private String determineDataTypePrefix(Object value) {
        if (value instanceof String) {
            return "S";
        }
        if (value instanceof Boolean) {
            return "B";
        }
        if (value instanceof Integer) {
            return "I";
        }
        if (value instanceof Long) {
            return "L";
        }
        if (value instanceof Float) {
            return JSON_VALUE_TYPE_PREFIX_FLOAT;
        }
        if (value instanceof Double) {
            return "D";
        }
        if (value instanceof Bundle) {
            return JSON_VALUE_TYPE_PREFIX_BUNDLE;
        }
        return null;
    }
}
