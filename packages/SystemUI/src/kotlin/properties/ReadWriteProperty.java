package kotlin.properties;

import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ReadWriteProperty extends ReadOnlyProperty {
    void setValue(Object obj, KProperty kProperty, Object obj2);
}
