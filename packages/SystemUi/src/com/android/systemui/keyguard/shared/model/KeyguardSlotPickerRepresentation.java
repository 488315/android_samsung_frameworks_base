package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSlotPickerRepresentation {

    /* renamed from: id */
    public final String f302id;
    public final int maxSelectedAffordances;

    public KeyguardSlotPickerRepresentation(String str, int i) {
        this.f302id = str;
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
        return Intrinsics.areEqual(this.f302id, keyguardSlotPickerRepresentation.f302id) && this.maxSelectedAffordances == keyguardSlotPickerRepresentation.maxSelectedAffordances;
    }

    public final int hashCode() {
        return Integer.hashCode(this.maxSelectedAffordances) + (this.f302id.hashCode() * 31);
    }

    public final String toString() {
        return "KeyguardSlotPickerRepresentation(id=" + this.f302id + ", maxSelectedAffordances=" + this.maxSelectedAffordances + ")";
    }

    public /* synthetic */ KeyguardSlotPickerRepresentation(String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? 1 : i);
    }
}
