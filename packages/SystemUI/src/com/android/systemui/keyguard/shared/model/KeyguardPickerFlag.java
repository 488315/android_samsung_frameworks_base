package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.Intrinsics;

public final class KeyguardPickerFlag {
    public final String name;
    public final boolean value;

    public KeyguardPickerFlag(String str, boolean z) {
        this.name = str;
        this.value = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardPickerFlag)) {
            return false;
        }
        KeyguardPickerFlag keyguardPickerFlag = (KeyguardPickerFlag) obj;
        return Intrinsics.areEqual(this.name, keyguardPickerFlag.name) && this.value == keyguardPickerFlag.value;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.value) + (this.name.hashCode() * 31);
    }

    public final String toString() {
        return "KeyguardPickerFlag(name=" + this.name + ", value=" + this.value + ")";
    }
}
