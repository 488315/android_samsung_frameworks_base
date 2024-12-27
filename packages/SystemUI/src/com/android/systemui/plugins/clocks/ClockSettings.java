package com.android.systemui.plugins.clocks;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ClockSettings {
    private final String clockId;
    private JSONObject metadata;
    private final Integer seedColor;
    public static final Companion Companion = new Companion(null);
    private static final String KEY_CLOCK_ID = "clockId";
    private static final String KEY_SEED_COLOR = "seedColor";
    private static final String KEY_METADATA = "metadata";

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public final ClockSettings deserialize(String str) {
            if (str == null || str.length() == 0) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            ClockSettings clockSettings = new ClockSettings(!jSONObject.isNull(ClockSettings.KEY_CLOCK_ID) ? jSONObject.getString(ClockSettings.KEY_CLOCK_ID) : null, jSONObject.isNull(ClockSettings.KEY_SEED_COLOR) ? null : Integer.valueOf(jSONObject.getInt(ClockSettings.KEY_SEED_COLOR)));
            if (!jSONObject.isNull(ClockSettings.KEY_METADATA)) {
                clockSettings.setMetadata(jSONObject.getJSONObject(ClockSettings.KEY_METADATA));
            }
            return clockSettings;
        }

        public final String serialize(ClockSettings clockSettings) {
            return clockSettings == null ? "" : new JSONObject().put(ClockSettings.KEY_CLOCK_ID, clockSettings.getClockId()).put(ClockSettings.KEY_SEED_COLOR, clockSettings.getSeedColor()).put(ClockSettings.KEY_METADATA, clockSettings.getMetadata()).toString();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ClockSettings() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ ClockSettings copy$default(ClockSettings clockSettings, String str, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockSettings.clockId;
        }
        if ((i & 2) != 0) {
            num = clockSettings.seedColor;
        }
        return clockSettings.copy(str, num);
    }

    public final String component1() {
        return this.clockId;
    }

    public final Integer component2() {
        return this.seedColor;
    }

    public final ClockSettings copy(String str, Integer num) {
        return new ClockSettings(str, num);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockSettings)) {
            return false;
        }
        ClockSettings clockSettings = (ClockSettings) obj;
        return Intrinsics.areEqual(this.clockId, clockSettings.clockId) && Intrinsics.areEqual(this.seedColor, clockSettings.seedColor);
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
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.seedColor;
        return hashCode + (num != null ? num.hashCode() : 0);
    }

    public final void setMetadata(JSONObject jSONObject) {
        this.metadata = jSONObject;
    }

    public String toString() {
        return "ClockSettings(clockId=" + this.clockId + ", seedColor=" + this.seedColor + ")";
    }

    public ClockSettings(String str, Integer num) {
        this.clockId = str;
        this.seedColor = num;
        this.metadata = new JSONObject();
    }

    public /* synthetic */ ClockSettings(String str, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : num);
    }
}
