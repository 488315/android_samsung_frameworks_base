package com.android.systemui.keyguard.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSlotPickerRepresentation {
    public final String id;
    public final int maxSelectedAffordances;

    public KeyguardSlotPickerRepresentation(String str, int i) {
        this.id = str;
        this.maxSelectedAffordances = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardSlotPickerRepresentation)) {
            return false;
        }
        KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation = (KeyguardSlotPickerRepresentation) obj;
        return Intrinsics.areEqual(this.id, keyguardSlotPickerRepresentation.id) && this.maxSelectedAffordances == keyguardSlotPickerRepresentation.maxSelectedAffordances;
    }

    public final int hashCode() {
        return Integer.hashCode(this.maxSelectedAffordances) + (this.id.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("KeyguardSlotPickerRepresentation(id=");
        sb.append(this.id);
        sb.append(", maxSelectedAffordances=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.maxSelectedAffordances, ")", sb);
    }

    public /* synthetic */ KeyguardSlotPickerRepresentation(String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? 1 : i);
    }
}
