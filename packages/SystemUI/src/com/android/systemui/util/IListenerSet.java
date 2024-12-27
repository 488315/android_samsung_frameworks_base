package com.android.systemui.util;

import java.util.Set;
import kotlin.jvm.internal.markers.KMappedMarker;

public interface IListenerSet<E> extends Set<E>, KMappedMarker {
    boolean addIfAbsent(E e);

    @Override // com.android.systemui.util.IListenerSet, java.util.Set
    boolean remove(E e);
}
