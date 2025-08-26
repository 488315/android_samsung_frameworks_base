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
import java.util.ArrayList;

/* compiled from: WallpaperExtraBundleHelper.java */
/* loaded from: classes6.dex */
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
    private static final String JSON_VALUE_TYPE_PREFIX_STRING_ARRAY = "SA";
    private static final String TAG = "BundleAndJsonConverter";

    BundleAndJsonConverter() {
    }

    public Bundle convertJsonToBundle(String str) throws IOException {
        if (str == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
            JsonReader jsonReader = new JsonReader(new InputStreamReader(byteArrayInputStream, "UTF-8"));
            putJsonObjectFieldsToBundle(jsonReader, bundle);
            jsonReader.close();
            byteArrayInputStream.close();
            return bundle;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "convertJsonToBundle : e=" + e);
            return bundle;
        } catch (IOException e2) {
            Log.e(TAG, "convertJsonToBundle : e=" + e2);
            return bundle;
        }
    }

    public ArrayList<String> convertStringToStringArray(String str) {
        if (str == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        String[] strArrSplit = str.split("\\|");
        if (strArrSplit != null) {
            for (String str2 : strArrSplit) {
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public String convertBundleToJson(Bundle bundle) throws IOException {
        if (bundle == null) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"));
            jsonWriter.setIndent(JSON_INDENT);
            writeBundleToJson(bundle, jsonWriter);
            jsonWriter.close();
            byteArrayOutputStream.close();
            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "convertBundleToJson : e=" + e);
            return "";
        } catch (IOException e2) {
            Log.e(TAG, "convertBundleToJson : e=" + e2);
            return "";
        }
    }

    public String convertStringArrayToString(ArrayList<String> arrayList) {
        if (arrayList == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            sb.append(arrayList.get(i));
            if (i != size - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    private void writeBundleToJson(Bundle bundle, JsonWriter jsonWriter) throws IOException {
        String string;
        jsonWriter.beginObject();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj == null) {
                Log.i(TAG, "writeBundleToJson: the value of " + str + " is null. skipping..");
            } else {
                String strDetermineDataTypePrefix = determineDataTypePrefix(obj);
                if (strDetermineDataTypePrefix == null) {
                    Log.i(TAG, "writeBundleToJson: unsupported value type : key=" + str + ", type=" + obj.getClass().getSimpleName() + ", skipping..");
                } else {
                    jsonWriter.name(str);
                    if (JSON_VALUE_TYPE_PREFIX_BUNDLE.equals(strDetermineDataTypePrefix)) {
                        string = convertBundleToJson((Bundle) obj);
                    } else if (JSON_VALUE_TYPE_PREFIX_STRING_ARRAY.equals(strDetermineDataTypePrefix)) {
                        string = convertStringArrayToString((ArrayList) obj);
                    } else {
                        string = obj.toString();
                    }
                    jsonWriter.value(strDetermineDataTypePrefix + "|" + string);
                }
            }
        }
        jsonWriter.endObject();
    }

    private void putJsonObjectFieldsToBundle(JsonReader jsonReader, Bundle bundle) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (Boolean.valueOf(putValueToBundle(bundle, strNextName, jsonReader.nextString())) == null) {
                Log.d(TAG, "putJsonObjectFieldsToBundle: failed to decode value. key=" + strNextName);
            }
        }
        jsonReader.endObject();
    }

    private boolean putValueToBundle(Bundle bundle, String str, String str2) {
        String strSubstring;
        if (bundle == null || str == null || str2 == null) {
            return false;
        }
        int iIndexOf = str2.indexOf("|");
        if (iIndexOf < 0) {
            Log.e(TAG, "putValueToBundle : type delimiter is absent : " + str2);
            return false;
        }
        String strSubstring2 = str2.substring(0, iIndexOf);
        strSubstring = str2.substring(iIndexOf + 1, str2.length());
        strSubstring2.hashCode();
        switch (strSubstring2) {
            case "B":
                bundle.putBoolean(str, Boolean.valueOf(strSubstring).booleanValue());
                break;
            case "D":
                bundle.putDouble(str, Double.valueOf(strSubstring).doubleValue());
                break;
            case "F":
                bundle.putFloat(str, Float.valueOf(strSubstring).floatValue());
                break;
            case "I":
                bundle.putInt(str, Integer.valueOf(strSubstring).intValue());
                break;
            case "L":
                bundle.putLong(str, Long.valueOf(strSubstring).longValue());
                break;
            case "S":
                bundle.putString(str, strSubstring);
                break;
            case "BD":
                bundle.putBundle(str, convertJsonToBundle(strSubstring));
                break;
            case "SA":
                bundle.putStringArrayList(str, convertStringToStringArray(strSubstring));
                break;
            default:
                Log.e(TAG, "putValueToBundle: unexpected data type : " + str2);
                break;
        }
        return false;
    }

    private String determineDataTypePrefix(Object obj) {
        if (obj instanceof String) {
            return "S";
        }
        if (obj instanceof Boolean) {
            return "B";
        }
        if (obj instanceof Integer) {
            return "I";
        }
        if (obj instanceof Long) {
            return "L";
        }
        if (obj instanceof Float) {
            return JSON_VALUE_TYPE_PREFIX_FLOAT;
        }
        if (obj instanceof Double) {
            return "D";
        }
        if (obj instanceof Bundle) {
            return JSON_VALUE_TYPE_PREFIX_BUNDLE;
        }
        if (!(obj instanceof ArrayList)) {
            return null;
        }
        ArrayList arrayList = (ArrayList) obj;
        if (arrayList.isEmpty() || !(arrayList.get(0) instanceof String)) {
            return null;
        }
        return JSON_VALUE_TYPE_PREFIX_STRING_ARRAY;
    }
}
