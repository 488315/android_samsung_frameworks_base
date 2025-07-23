package androidx.compose.ui.platform;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ValueElement {
    public final String name;
    public final Object value;

    public ValueElement(String str, Object obj) {
        this.name = str;
        this.value = obj;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ValueElement)) {
            return false;
        }
        ValueElement valueElement = (ValueElement) obj;
        return Intrinsics.areEqual(this.name, valueElement.name) && Intrinsics.areEqual(this.value, valueElement.value);
    }

    public final int hashCode() {
        int hashCode = this.name.hashCode() * 31;
        Object obj = this.value;
        return hashCode + (obj == null ? 0 : obj.hashCode());
    }

    public final String toString() {
        return "ValueElement(name=" + this.name + ", value=" + this.value + ')';
    }
}
