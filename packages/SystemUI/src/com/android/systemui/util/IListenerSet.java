package com.android.systemui.util;

import java.util.Set;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface IListenerSet<E> extends Set<E>, KMappedMarker {
    boolean addIfAbsent(E e);

    @Override // com.android.systemui.util.IListenerSet, java.util.Set
    boolean remove(E e);
}
