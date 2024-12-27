package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
