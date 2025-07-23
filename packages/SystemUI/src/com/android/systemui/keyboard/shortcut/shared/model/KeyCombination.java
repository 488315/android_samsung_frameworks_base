package com.android.systemui.keyboard.shortcut.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int hashCode = Integer.hashCode(this.modifiers) * 31;
        Integer num = this.keyCode;
        return hashCode + (num == null ? 0 : num.hashCode());
    }

    public final String toString() {
        return "KeyCombination(modifiers=" + this.modifiers + ", keyCode=" + this.keyCode + ")";
    }
}
