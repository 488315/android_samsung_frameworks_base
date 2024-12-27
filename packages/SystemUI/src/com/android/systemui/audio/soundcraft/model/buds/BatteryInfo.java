package com.android.systemui.audio.soundcraft.model.buds;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BatteryInfo {

    @SerializedName("batteryCradle")
    private String batteryCradle;

    @SerializedName("batteryLeft")
    private String batteryLeft;

    @SerializedName("batteryRight")
    private String batteryRight;

    public BatteryInfo() {
        this(null, null, null, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BatteryInfo)) {
            return false;
        }
        BatteryInfo batteryInfo = (BatteryInfo) obj;
        return Intrinsics.areEqual(this.batteryLeft, batteryInfo.batteryLeft) && Intrinsics.areEqual(this.batteryRight, batteryInfo.batteryRight) && Intrinsics.areEqual(this.batteryCradle, batteryInfo.batteryCradle);
    }

    public final String getBatteryCradle() {
        return this.batteryCradle;
    }

    public final String getBatteryLeft() {
        return this.batteryLeft;
    }

    public final String getBatteryRight() {
        return this.batteryRight;
    }

    public final int hashCode() {
        String str = this.batteryLeft;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.batteryRight;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.batteryCradle;
        return hashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public final void setBatteryCradle(String str) {
        this.batteryCradle = str;
    }

    public final void setBatteryLeft(String str) {
        this.batteryLeft = str;
    }

    public final void setBatteryRight(String str) {
        this.batteryRight = str;
    }

    public final String toString() {
        String str = this.batteryLeft;
        String str2 = this.batteryRight;
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("BatteryInfo(batteryLeft=", str, ", batteryRight=", str2, ", batteryCradle="), this.batteryCradle, ")");
    }

    public BatteryInfo(String str, String str2, String str3) {
        this.batteryLeft = str;
        this.batteryRight = str2;
        this.batteryCradle = str3;
    }

    public /* synthetic */ BatteryInfo(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3);
    }
}
