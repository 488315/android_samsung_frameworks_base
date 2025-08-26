package kotlin.properties;

import kotlin.reflect.KProperty;

/* loaded from: classes4.dex */
public interface ReadOnlyProperty {
    Object getValue(Object obj, KProperty kProperty);
}
