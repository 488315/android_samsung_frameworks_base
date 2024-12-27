package com.android.systemui.accessibility.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import java.time.LocalTime;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NightDisplayState {
    public final int autoMode;
    public final LocalTime endTime;
    public final boolean isActivated;
    public final boolean locationEnabled;
    public final boolean shouldForceAutoMode;
    public final LocalTime startTime;

    public NightDisplayState() {
        this(0, false, null, null, false, false, 63, null);
    }

    public static NightDisplayState copy$default(NightDisplayState nightDisplayState, int i, boolean z, LocalTime localTime, LocalTime localTime2, boolean z2, boolean z3, int i2) {
        if ((i2 & 1) != 0) {
            i = nightDisplayState.autoMode;
        }
        int i3 = i;
        if ((i2 & 2) != 0) {
            z = nightDisplayState.isActivated;
        }
        boolean z4 = z;
        if ((i2 & 4) != 0) {
            localTime = nightDisplayState.startTime;
        }
        LocalTime localTime3 = localTime;
        if ((i2 & 8) != 0) {
            localTime2 = nightDisplayState.endTime;
        }
        LocalTime localTime4 = localTime2;
        if ((i2 & 16) != 0) {
            z2 = nightDisplayState.shouldForceAutoMode;
        }
        boolean z5 = z2;
        if ((i2 & 32) != 0) {
            z3 = nightDisplayState.locationEnabled;
        }
        nightDisplayState.getClass();
        return new NightDisplayState(i3, z4, localTime3, localTime4, z5, z3);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NightDisplayState)) {
            return false;
        }
        NightDisplayState nightDisplayState = (NightDisplayState) obj;
        return this.autoMode == nightDisplayState.autoMode && this.isActivated == nightDisplayState.isActivated && Intrinsics.areEqual(this.startTime, nightDisplayState.startTime) && Intrinsics.areEqual(this.endTime, nightDisplayState.endTime) && this.shouldForceAutoMode == nightDisplayState.shouldForceAutoMode && this.locationEnabled == nightDisplayState.locationEnabled;
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.autoMode) * 31, 31, this.isActivated);
        LocalTime localTime = this.startTime;
        int hashCode = (m + (localTime == null ? 0 : localTime.hashCode())) * 31;
        LocalTime localTime2 = this.endTime;
        return Boolean.hashCode(this.locationEnabled) + TransitionData$$ExternalSyntheticOutline0.m((hashCode + (localTime2 != null ? localTime2.hashCode() : 0)) * 31, 31, this.shouldForceAutoMode);
    }

    public final String toString() {
        LocalTime localTime = this.startTime;
        LocalTime localTime2 = this.endTime;
        StringBuilder sb = new StringBuilder("NightDisplayState(autoMode=");
        sb.append(this.autoMode);
        sb.append(", isActivated=");
        sb.append(this.isActivated);
        sb.append(", startTime=");
        sb.append(localTime);
        sb.append(", endTime=");
        sb.append(localTime2);
        sb.append(", shouldForceAutoMode=");
        sb.append(this.shouldForceAutoMode);
        sb.append(", locationEnabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.locationEnabled, ")");
    }

    public NightDisplayState(int i, boolean z, LocalTime localTime, LocalTime localTime2, boolean z2, boolean z3) {
        this.autoMode = i;
        this.isActivated = z;
        this.startTime = localTime;
        this.endTime = localTime2;
        this.shouldForceAutoMode = z2;
        this.locationEnabled = z3;
    }

    public /* synthetic */ NightDisplayState(int i, boolean z, LocalTime localTime, LocalTime localTime2, boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? true : z, (i2 & 4) != 0 ? null : localTime, (i2 & 8) != 0 ? null : localTime2, (i2 & 16) != 0 ? false : z2, (i2 & 32) != 0 ? false : z3);
    }
}
