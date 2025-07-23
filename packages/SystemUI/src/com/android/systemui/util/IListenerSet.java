package com.android.systemui.util;

import java.util.Set;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface IListenerSet<E> extends Set<E>, KMappedMarker {
    boolean addIfAbsent(E e);

    @Override // com.android.systemui.util.IListenerSet, java.util.Set
    boolean remove(E e);
}
