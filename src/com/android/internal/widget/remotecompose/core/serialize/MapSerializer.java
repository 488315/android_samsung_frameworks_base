package com.android.internal.widget.remotecompose.core.serialize;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public interface MapSerializer {
    MapSerializer add(String str, float f, float f2);

    MapSerializer add(String str, float f, float f2, float f3, float f4);

    MapSerializer add(String str, Serializable serializable);

    MapSerializer add(String str, Boolean bool);

    MapSerializer add(String str, Byte b);

    MapSerializer add(String str, Double d);

    <T extends Enum<T>> MapSerializer add(String str, Enum<T> r2);

    MapSerializer add(String str, Float f);

    MapSerializer add(String str, Integer num);

    MapSerializer add(String str, Long l);

    MapSerializer add(String str, Short sh);

    MapSerializer add(String str, String str2);

    <T> MapSerializer add(String str, List<T> list);

    <T> MapSerializer add(String str, Map<String, T> map);

    MapSerializer addFloatExpressionSrc(String str, float[] fArr);

    MapSerializer addIntExpressionSrc(String str, int[] iArr, int i);

    MapSerializer addPath(String str, float[] fArr);

    MapSerializer addTags(SerializeTags... serializeTagsArr);

    MapSerializer addType(String str);

    static LinkedHashMap<String, Object> orderedOf(Object... objArr) {
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        for (int i = 0; i < objArr.length; i += 2) {
            linkedHashMap.put((String) objArr[i], objArr[i + 1]);
        }
        return linkedHashMap;
    }
}
