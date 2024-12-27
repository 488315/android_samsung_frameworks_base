package com.android.systemui.qs.tiles.impl.night.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import java.time.LocalTime;
import kotlin.jvm.internal.Intrinsics;

public interface NightDisplayTileModel {

    public final class AutoModeCustom implements NightDisplayTileModel {
        public final LocalTime endTime;
        public final boolean is24HourFormat;
        public final boolean isActivated;
        public final boolean isEnrolledInForcedNightDisplayAutoMode;
        public final LocalTime startTime;

        public AutoModeCustom(boolean z, boolean z2, LocalTime localTime, LocalTime localTime2, boolean z3) {
            this.isActivated = z;
            this.isEnrolledInForcedNightDisplayAutoMode = z2;
            this.startTime = localTime;
            this.endTime = localTime2;
            this.is24HourFormat = z3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AutoModeCustom)) {
                return false;
            }
            AutoModeCustom autoModeCustom = (AutoModeCustom) obj;
            return this.isActivated == autoModeCustom.isActivated && this.isEnrolledInForcedNightDisplayAutoMode == autoModeCustom.isEnrolledInForcedNightDisplayAutoMode && Intrinsics.areEqual(this.startTime, autoModeCustom.startTime) && Intrinsics.areEqual(this.endTime, autoModeCustom.endTime) && this.is24HourFormat == autoModeCustom.is24HourFormat;
        }

        public final int hashCode() {
            int m = TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isActivated) * 31, 31, this.isEnrolledInForcedNightDisplayAutoMode);
            LocalTime localTime = this.startTime;
            int hashCode = (m + (localTime == null ? 0 : localTime.hashCode())) * 31;
            LocalTime localTime2 = this.endTime;
            return Boolean.hashCode(this.is24HourFormat) + ((hashCode + (localTime2 != null ? localTime2.hashCode() : 0)) * 31);
        }

        @Override // com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel
        public final boolean isActivated() {
            return this.isActivated;
        }

        @Override // com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel
        public final boolean isEnrolledInForcedNightDisplayAutoMode() {
            return this.isEnrolledInForcedNightDisplayAutoMode;
        }

        public final String toString() {
            LocalTime localTime = this.startTime;
            LocalTime localTime2 = this.endTime;
            StringBuilder sb = new StringBuilder("AutoModeCustom(isActivated=");
            sb.append(this.isActivated);
            sb.append(", isEnrolledInForcedNightDisplayAutoMode=");
            sb.append(this.isEnrolledInForcedNightDisplayAutoMode);
            sb.append(", startTime=");
            sb.append(localTime);
            sb.append(", endTime=");
            sb.append(localTime2);
            sb.append(", is24HourFormat=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.is24HourFormat, ")");
        }
    }

    public final class AutoModeOff implements NightDisplayTileModel {
        public final boolean isActivated;
        public final boolean isEnrolledInForcedNightDisplayAutoMode;

        public AutoModeOff(boolean z, boolean z2) {
            this.isActivated = z;
            this.isEnrolledInForcedNightDisplayAutoMode = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AutoModeOff)) {
                return false;
            }
            AutoModeOff autoModeOff = (AutoModeOff) obj;
            return this.isActivated == autoModeOff.isActivated && this.isEnrolledInForcedNightDisplayAutoMode == autoModeOff.isEnrolledInForcedNightDisplayAutoMode;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isEnrolledInForcedNightDisplayAutoMode) + (Boolean.hashCode(this.isActivated) * 31);
        }

        @Override // com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel
        public final boolean isActivated() {
            return this.isActivated;
        }

        @Override // com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel
        public final boolean isEnrolledInForcedNightDisplayAutoMode() {
            return this.isEnrolledInForcedNightDisplayAutoMode;
        }

        public final String toString() {
            return "AutoModeOff(isActivated=" + this.isActivated + ", isEnrolledInForcedNightDisplayAutoMode=" + this.isEnrolledInForcedNightDisplayAutoMode + ")";
        }
    }

    public final class AutoModeTwilight implements NightDisplayTileModel {
        public final boolean isActivated;
        public final boolean isEnrolledInForcedNightDisplayAutoMode;
        public final boolean isLocationEnabled;

        public AutoModeTwilight(boolean z, boolean z2, boolean z3) {
            this.isActivated = z;
            this.isEnrolledInForcedNightDisplayAutoMode = z2;
            this.isLocationEnabled = z3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AutoModeTwilight)) {
                return false;
            }
            AutoModeTwilight autoModeTwilight = (AutoModeTwilight) obj;
            return this.isActivated == autoModeTwilight.isActivated && this.isEnrolledInForcedNightDisplayAutoMode == autoModeTwilight.isEnrolledInForcedNightDisplayAutoMode && this.isLocationEnabled == autoModeTwilight.isLocationEnabled;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isLocationEnabled) + TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isActivated) * 31, 31, this.isEnrolledInForcedNightDisplayAutoMode);
        }

        @Override // com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel
        public final boolean isActivated() {
            return this.isActivated;
        }

        @Override // com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel
        public final boolean isEnrolledInForcedNightDisplayAutoMode() {
            return this.isEnrolledInForcedNightDisplayAutoMode;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("AutoModeTwilight(isActivated=");
            sb.append(this.isActivated);
            sb.append(", isEnrolledInForcedNightDisplayAutoMode=");
            sb.append(this.isEnrolledInForcedNightDisplayAutoMode);
            sb.append(", isLocationEnabled=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isLocationEnabled, ")");
        }
    }

    boolean isActivated();

    boolean isEnrolledInForcedNightDisplayAutoMode();
}
