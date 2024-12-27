package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return "KeyguardSlotPickerRepresentation(id=" + this.id + ", maxSelectedAffordances=" + this.maxSelectedAffordances + ")";
    }

    public /* synthetic */ KeyguardSlotPickerRepresentation(String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? 1 : i);
    }
}
