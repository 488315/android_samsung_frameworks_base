package com.android.systemui.accessibility.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import java.time.LocalTime;
import kotlin.jvm.internal.Intrinsics;

public interface NightDisplayChangeEvent {

    public final class OnActivatedChanged implements NightDisplayChangeEvent {
        public final boolean isActivated;

        public OnActivatedChanged(boolean z) {
            this.isActivated = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnActivatedChanged) && this.isActivated == ((OnActivatedChanged) obj).isActivated;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isActivated);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnActivatedChanged(isActivated="), this.isActivated, ")");
        }
    }

    public final class OnAutoModeChanged implements NightDisplayChangeEvent {
        public final int autoMode;

        public OnAutoModeChanged(int i) {
            this.autoMode = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnAutoModeChanged) && this.autoMode == ((OnAutoModeChanged) obj).autoMode;
        }

        public final int hashCode() {
            return Integer.hashCode(this.autoMode);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.autoMode, ")", new StringBuilder("OnAutoModeChanged(autoMode="));
        }
    }

    public final class OnCustomEndTimeChanged implements NightDisplayChangeEvent {
        public final LocalTime endTime;

        public OnCustomEndTimeChanged(LocalTime localTime) {
            this.endTime = localTime;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnCustomEndTimeChanged) && Intrinsics.areEqual(this.endTime, ((OnCustomEndTimeChanged) obj).endTime);
        }

        public final int hashCode() {
            LocalTime localTime = this.endTime;
            if (localTime == null) {
                return 0;
            }
            return localTime.hashCode();
        }

        public final String toString() {
            return "OnCustomEndTimeChanged(endTime=" + this.endTime + ")";
        }
    }

    public final class OnCustomStartTimeChanged implements NightDisplayChangeEvent {
        public final LocalTime startTime;

        public OnCustomStartTimeChanged(LocalTime localTime) {
            this.startTime = localTime;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnCustomStartTimeChanged) && Intrinsics.areEqual(this.startTime, ((OnCustomStartTimeChanged) obj).startTime);
        }

        public final int hashCode() {
            LocalTime localTime = this.startTime;
            if (localTime == null) {
                return 0;
            }
            return localTime.hashCode();
        }

        public final String toString() {
            return "OnCustomStartTimeChanged(startTime=" + this.startTime + ")";
        }
    }

    public final class OnForceAutoModeChanged implements NightDisplayChangeEvent {
        public final boolean shouldForceAutoMode;

        public OnForceAutoModeChanged(boolean z) {
            this.shouldForceAutoMode = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnForceAutoModeChanged) && this.shouldForceAutoMode == ((OnForceAutoModeChanged) obj).shouldForceAutoMode;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.shouldForceAutoMode);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnForceAutoModeChanged(shouldForceAutoMode="), this.shouldForceAutoMode, ")");
        }
    }

    public final class OnLocationEnabledChanged implements NightDisplayChangeEvent {
        public final boolean locationEnabled;

        public OnLocationEnabledChanged(boolean z) {
            this.locationEnabled = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnLocationEnabledChanged) && this.locationEnabled == ((OnLocationEnabledChanged) obj).locationEnabled;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.locationEnabled);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnLocationEnabledChanged(locationEnabled="), this.locationEnabled, ")");
        }
    }
}
