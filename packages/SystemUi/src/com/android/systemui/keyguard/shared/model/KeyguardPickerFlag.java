package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.name.hashCode() * 31;
        boolean z = this.value;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "KeyguardPickerFlag(name=" + this.name + ", value=" + this.value + ")";
    }
}
