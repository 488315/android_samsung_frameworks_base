package com.android.systemui.statusbar.pipeline.mobile.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ServiceStateModel {
    public static final Companion Companion = new Companion(null);
    public final boolean isEmergencyOnly;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
