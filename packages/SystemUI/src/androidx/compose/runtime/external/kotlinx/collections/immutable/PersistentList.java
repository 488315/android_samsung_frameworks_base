package androidx.compose.runtime.external.kotlinx.collections.immutable;

import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.PersistentVectorBuilder;
import java.util.Collection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface PersistentList<E> extends ImmutableList<E>, Collection, KMappedMarker {
    @Override // java.util.List
    PersistentList add(int i, Object obj);

    @Override // java.util.List, java.util.Collection
    PersistentList add(Object obj);

    @Override // java.util.List, java.util.Collection
    PersistentList addAll(Collection collection);

    PersistentVectorBuilder builder();

    PersistentList removeAll(Function1 function1);

    PersistentList removeAt(int i);

    @Override // java.util.List
    PersistentList set(int i, Object obj);
}
