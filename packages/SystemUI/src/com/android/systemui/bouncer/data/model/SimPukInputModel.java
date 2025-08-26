package com.android.systemui.bouncer.data.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SimPukInputModel {
    public final String enteredSimPin;
    public final String enteredSimPuk;

    /* JADX WARN: Multi-variable type inference failed */
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
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.enteredSimPin;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SimPukInputModel(enteredSimPuk=");
        sb.append(this.enteredSimPuk);
        sb.append(", enteredSimPin=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.enteredSimPin, ")");
    }

    public SimPukInputModel(String str, String str2) {
        this.enteredSimPuk = str;
        this.enteredSimPin = str2;
    }

    public /* synthetic */ SimPukInputModel(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2);
    }
}
