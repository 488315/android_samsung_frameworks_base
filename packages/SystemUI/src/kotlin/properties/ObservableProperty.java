package kotlin.properties;

import kotlin.reflect.KProperty;

/* loaded from: classes4.dex */
public abstract class ObservableProperty implements ReadWriteProperty {
    public Object value;

    public ObservableProperty(Object obj) {
        this.value = obj;
    }

    @Override // kotlin.properties.ReadOnlyProperty
    public final Object getValue(Object obj, KProperty kProperty) {
        return this.value;
    }

    @Override // kotlin.properties.ReadWriteProperty
    public final void setValue(Object obj, KProperty kProperty, Object obj2) {
        Object obj3 = this.value;
        this.value = obj2;
        afterChange(obj3, obj2);
    }

    public final String toString() {
        return "ObservableProperty(value=" + this.value + ')';
    }

    public void afterChange(Object obj, Object obj2) {
    }
}
