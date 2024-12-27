package com.android.systemui.bouncer.data.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SimPukInputModel {
    public final String enteredSimPin;
    public final String enteredSimPuk;

    public SimPukInputModel() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimPukInputModel)) {
            return false;
        }
        SimPukInputModel simPukInputModel = (SimPukInputModel) obj;
        return Intrinsics.areEqual(this.enteredSimPuk, simPukInputModel.enteredSimPuk) && Intrinsics.areEqual(this.enteredSimPin, simPukInputModel.enteredSimPin);
    }

    public final int hashCode() {
        String str = this.enteredSimPuk;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.enteredSimPin;
        return hashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SimPukInputModel(enteredSimPuk=");
        sb.append(this.enteredSimPuk);
        sb.append(", enteredSimPin=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.enteredSimPin, ")");
    }

    public SimPukInputModel(String str, String str2) {
        this.enteredSimPuk = str;
        this.enteredSimPin = str2;
    }

    public /* synthetic */ SimPukInputModel(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2);
    }
}
