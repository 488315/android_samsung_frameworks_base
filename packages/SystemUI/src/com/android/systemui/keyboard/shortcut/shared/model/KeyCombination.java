package com.android.systemui.keyboard.shortcut.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class KeyCombination {
    public final Integer keyCode;
    public final int modifiers;

    public KeyCombination(int i, Integer num) {
        this.modifiers = i;
        this.keyCode = num;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyCombination)) {
            return false;
        }
        KeyCombination keyCombination = (KeyCombination) obj;
        return this.modifiers == keyCombination.modifiers && Intrinsics.areEqual(this.keyCode, keyCombination.keyCode);
    }

    public final int hashCode() {
        int iHashCode = Integer.hashCode(this.modifiers) * 31;
        Integer num = this.keyCode;
        return iHashCode + (num == null ? 0 : num.hashCode());
    }

    public final String toString() {
        return "KeyCombination(modifiers=" + this.modifiers + ", keyCode=" + this.keyCode + ")";
    }
}
