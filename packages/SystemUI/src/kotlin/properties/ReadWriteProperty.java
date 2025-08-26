package kotlin.properties;

import kotlin.reflect.KProperty;

/* loaded from: classes4.dex */
public interface ReadWriteProperty extends ReadOnlyProperty {
    void setValue(Object obj, KProperty kProperty, Object obj2);
}
