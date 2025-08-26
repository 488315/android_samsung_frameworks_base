package com.android.systemui.plugins.clocks;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ClockAxisStyle {
    private final Map<String, Float> settings;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final String KEY_AXIS_KEY = "key";
    private static final String KEY_AXIS_VALUE = "value";

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final ClockAxisStyle fromJson(JSONArray jSONArray) throws JSONException {
            ClockAxisStyle clockAxisStyle = new ClockAxisStyle(null, 1, 0 == true ? 1 : 0);
            int length = jSONArray.length() - 1;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject != null) {
                        clockAxisStyle.put(jSONObject.getString(ClockAxisStyle.KEY_AXIS_KEY), (float) jSONObject.getDouble(ClockAxisStyle.KEY_AXIS_VALUE));
                    }
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
            return clockAxisStyle;
        }

        public final JSONArray toJson(ClockAxisStyle clockAxisStyle) throws JSONException {
            JSONArray jSONArray = new JSONArray();
            for (Map.Entry entry : clockAxisStyle.settings.entrySet()) {
                String str = (String) entry.getKey();
                float fFloatValue = ((Number) entry.getValue()).floatValue();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(ClockAxisStyle.KEY_AXIS_KEY, str);
                jSONObject.put(ClockAxisStyle.KEY_AXIS_VALUE, Float.valueOf(fFloatValue));
                jSONArray.put(jSONObject);
            }
            return jSONArray;
        }

        private Companion() {
        }
    }

    public ClockAxisStyle(Function1 function1) {
        this.settings = new LinkedHashMap();
        function1.mo781invoke(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$0(ClockAxisStyle clockAxisStyle) {
        return Unit.INSTANCE;
    }

    public final ClockAxisStyle copy(Function1 function1) {
        ClockAxisStyle clockAxisStyle = new ClockAxisStyle(this);
        function1.mo781invoke(clockAxisStyle);
        return clockAxisStyle;
    }

    public final ClockAxisStyle copyWith(ClockAxisStyle clockAxisStyle) {
        ClockAxisStyle clockAxisStyle2 = new ClockAxisStyle(this);
        for (Map.Entry<String, Float> entry : clockAxisStyle.settings.entrySet()) {
            clockAxisStyle2.set(entry.getKey(), entry.getValue().floatValue());
        }
        return clockAxisStyle2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ClockAxisStyle) {
            return Intrinsics.areEqual(this.settings, ((ClockAxisStyle) obj).settings);
        }
        return false;
    }

    public final Float get(String str) {
        return this.settings.get(str);
    }

    public final Iterable<Map.Entry<String, Float>> getItems() {
        return this.settings.entrySet();
    }

    public final boolean isEmpty() {
        return this.settings.isEmpty();
    }

    public final void put(String str, float f) {
        this.settings.put(str, Float.valueOf(f));
    }

    public final void set(String str, float f) {
        put(str, f);
    }

    public final String toFVar() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Float> entry : this.settings.entrySet()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            String key = entry.getKey();
            sb.append("'" + ((Object) key) + "' " + ((int) entry.getValue().floatValue()));
        }
        return sb.toString();
    }

    public /* synthetic */ ClockAxisStyle(Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ClockAxisStyle$$ExternalSyntheticLambda0() : function1);
    }

    public ClockAxisStyle(ClockAxisStyle clockAxisStyle) {
        this.settings = new LinkedHashMap(clockAxisStyle.settings);
    }

    public ClockAxisStyle(Map<String, Float> map) {
        this.settings = new LinkedHashMap(map);
    }

    public ClockAxisStyle(String str, float f) {
        this.settings = MapsKt__MapsKt.mutableMapOf(new Pair(str, Float.valueOf(f)));
    }

    public ClockAxisStyle(List<ClockFontAxis> list) {
        List<ClockFontAxis> list2 = list;
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity < 16 ? 16 : iMapCapacity);
        for (ClockFontAxis clockFontAxis : list2) {
            Pair pair = new Pair(clockFontAxis.getKey(), Float.valueOf(clockFontAxis.getCurrentValue()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.settings = new LinkedHashMap(linkedHashMap);
    }
}
