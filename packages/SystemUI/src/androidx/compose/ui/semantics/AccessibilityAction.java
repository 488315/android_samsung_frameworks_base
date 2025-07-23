package androidx.compose.ui.semantics;

import kotlin.Function;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AccessibilityAction<T extends Function> {
    public final Function action;
    public final String label;

    public AccessibilityAction(String str, T t) {
        this.label = str;
        this.action = t;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccessibilityAction)) {
            return false;
        }
        AccessibilityAction accessibilityAction = (AccessibilityAction) obj;
        return Intrinsics.areEqual(this.label, accessibilityAction.label) && Intrinsics.areEqual(this.action, accessibilityAction.action);
    }

    public final int hashCode() {
        String str = this.label;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        Function function = this.action;
        return hashCode + (function != null ? function.hashCode() : 0);
    }

    public final String toString() {
        return "AccessibilityAction(label=" + this.label + ", action=" + this.action + ')';
    }
}
