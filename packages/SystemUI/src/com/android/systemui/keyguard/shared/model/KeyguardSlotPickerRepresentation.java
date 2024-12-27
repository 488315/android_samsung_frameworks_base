package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        return "KeyguardSlotPickerRepresentation(id=" + this.id + ", maxSelectedAffordances=" + this.maxSelectedAffordances + ")";
    }

    public /* synthetic */ KeyguardSlotPickerRepresentation(String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? 1 : i);
    }
}
