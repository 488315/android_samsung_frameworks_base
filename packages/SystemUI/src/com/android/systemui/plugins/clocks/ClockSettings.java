package com.android.systemui.plugins.clocks;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ClockSettings {
    private final ClockAxisStyle axes;
    private final String clockId;
    private JSONObject metadata;
    private final Integer seedColor;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final String KEY_CLOCK_ID = "clockId";
    private static final String KEY_SEED_COLOR = "seedColor";
    private static final String KEY_METADATA = "metadata";
    private static final String KEY_AXIS_LIST = "axes";

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final ClockSettings fromJson(JSONObject jSONObject) {
            Function1 function1 = null;
            Object[] objArr = 0;
            String string = !jSONObject.isNull(ClockSettings.KEY_CLOCK_ID) ? jSONObject.getString(ClockSettings.KEY_CLOCK_ID) : null;
            Integer numValueOf = !jSONObject.isNull(ClockSettings.KEY_SEED_COLOR) ? Integer.valueOf(jSONObject.getInt(ClockSettings.KEY_SEED_COLOR)) : null;
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(ClockSettings.KEY_AXIS_LIST);
            ClockAxisStyle clockAxisStyleFromJson = jSONArrayOptJSONArray != null ? ClockAxisStyle.Companion.fromJson(jSONArrayOptJSONArray) : null;
            if (clockAxisStyleFromJson == null) {
                clockAxisStyleFromJson = new ClockAxisStyle(function1, 1, objArr == true ? 1 : 0);
            }
            ClockSettings clockSettings = new ClockSettings(string, numValueOf, clockAxisStyleFromJson);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(ClockSettings.KEY_METADATA);
            if (jSONObjectOptJSONObject == null) {
                jSONObjectOptJSONObject = new JSONObject();
            }
            clockSettings.setMetadata(jSONObjectOptJSONObject);
            return clockSettings;
        }

        public final JSONObject toJson(ClockSettings clockSettings) throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(ClockSettings.KEY_CLOCK_ID, clockSettings.getClockId());
            jSONObject.put(ClockSettings.KEY_SEED_COLOR, clockSettings.getSeedColor());
            jSONObject.put(ClockSettings.KEY_METADATA, clockSettings.getMetadata());
            jSONObject.put(ClockSettings.KEY_AXIS_LIST, ClockAxisStyle.Companion.toJson(clockSettings.getAxes()));
            return jSONObject;
        }

        private Companion() {
        }
    }

    public ClockSettings() {
        this(null, null, null, 7, null);
    }

    public static /* synthetic */ ClockSettings copy$default(ClockSettings clockSettings, String str, Integer num, ClockAxisStyle clockAxisStyle, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockSettings.clockId;
        }
        if ((i & 2) != 0) {
            num = clockSettings.seedColor;
        }
        if ((i & 4) != 0) {
            clockAxisStyle = clockSettings.axes;
        }
        return clockSettings.copy(str, num, clockAxisStyle);
    }

    public final String component1() {
        return this.clockId;
    }

    public final Integer component2() {
        return this.seedColor;
    }

    public final ClockAxisStyle component3() {
        return this.axes;
    }

    public final ClockSettings copy(String str, Integer num, ClockAxisStyle clockAxisStyle) {
        return new ClockSettings(str, num, clockAxisStyle);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockSettings)) {
            return false;
        }
        ClockSettings clockSettings = (ClockSettings) obj;
        return Intrinsics.areEqual(this.clockId, clockSettings.clockId) && Intrinsics.areEqual(this.seedColor, clockSettings.seedColor) && Intrinsics.areEqual(this.axes, clockSettings.axes);
    }

    public final ClockAxisStyle getAxes() {
        return this.axes;
    }

    public final String getClockId() {
        return this.clockId;
    }

    public final JSONObject getMetadata() {
        return this.metadata;
    }

    public final Integer getSeedColor() {
        return this.seedColor;
    }

    public int hashCode() {
        String str = this.clockId;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.seedColor;
        return this.axes.hashCode() + ((iHashCode + (num != null ? num.hashCode() : 0)) * 31);
    }

    public final void setMetadata(JSONObject jSONObject) {
        this.metadata = jSONObject;
    }

    public String toString() {
        return "ClockSettings(clockId=" + this.clockId + ", seedColor=" + this.seedColor + ", axes=" + this.axes + ")";
    }

    public ClockSettings(String str, Integer num, ClockAxisStyle clockAxisStyle) {
        this.clockId = str;
        this.seedColor = num;
        this.axes = clockAxisStyle;
        this.metadata = new JSONObject();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ ClockSettings(String str, Integer num, ClockAxisStyle clockAxisStyle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : num, (i & 4) != 0 ? new ClockAxisStyle(null, 1, 0 == true ? 1 : 0) : clockAxisStyle);
    }
}
