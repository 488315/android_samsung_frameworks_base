package com.android.systemui.statusbar.pipeline.mobile.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ServiceStateModel {
    public static final Companion Companion = new Companion(null);
    public final boolean isEmergencyOnly;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ServiceStateModel(boolean z) {
        this.isEmergencyOnly = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ServiceStateModel) && this.isEmergencyOnly == ((ServiceStateModel) obj).isEmergencyOnly;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEmergencyOnly);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("ServiceStateModel(isEmergencyOnly="), this.isEmergencyOnly, ")");
    }
}
