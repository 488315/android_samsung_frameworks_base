package kotlin.properties;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class NotNullVar implements ReadWriteProperty {
    public Object value;

    @Override // kotlin.properties.ReadOnlyProperty
    public final Object getValue(Object obj, KProperty kProperty) {
        Object obj2 = this.value;
        if (obj2 != null) {
            return obj2;
        }
        throw new IllegalStateException("Property " + kProperty.getName() + " should be initialized before get.");
    }

    @Override // kotlin.properties.ReadWriteProperty
    public final void setValue(Object obj, KProperty kProperty, Object obj2) {
        this.value = obj2;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("NotNullProperty(");
        if (this.value != null) {
            str = "value=" + this.value;
        } else {
            str = "value not initialized yet";
        }
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, str, ')');
    }
}
