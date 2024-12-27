package com.android.systemui.communal.data.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalEnabledState implements Diffable, Set, KMappedMarker {
    public final EnumSet disabledReasons;

    private /* synthetic */ CommunalEnabledState(EnumSet enumSet) {
        this.disabledReasons = enumSet;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ CommunalEnabledState m939boximpl(EnumSet enumSet) {
        return new CommunalEnabledState(enumSet);
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        if (!(obj instanceof DisabledReason)) {
            return false;
        }
        return this.disabledReasons.contains((DisabledReason) obj);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(Collection collection) {
        return this.disabledReasons.containsAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean equals(Object obj) {
        return (obj instanceof CommunalEnabledState) && Intrinsics.areEqual(this.disabledReasons, ((CommunalEnabledState) obj).disabledReasons);
    }

    @Override // java.util.Set, java.util.Collection
    public final int hashCode() {
        return this.disabledReasons.hashCode();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        return this.disabledReasons.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.disabledReasons.iterator();
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        EnumSet enumSet = ((CommunalEnabledState) obj).disabledReasons;
        EnumSet enumSet2 = this.disabledReasons;
        AbstractList abstractList = (AbstractList) DisabledReason.$ENTRIES;
        abstractList.getClass();
        AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
        while (iteratorImpl.hasNext()) {
            DisabledReason disabledReason = (DisabledReason) iteratorImpl.next();
            boolean contains = enumSet2.contains(disabledReason);
            if (contains != enumSet.contains(disabledReason)) {
                tableRowLoggerImpl.logChange(disabledReason.getLoggingString(), contains);
            }
        }
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        EnumSet enumSet = this.disabledReasons;
        AbstractList abstractList = (AbstractList) DisabledReason.$ENTRIES;
        abstractList.getClass();
        AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
        while (iteratorImpl.hasNext()) {
            DisabledReason disabledReason = (DisabledReason) iteratorImpl.next();
            tableRowLoggerImpl.logChange(disabledReason.getLoggingString(), enumSet.contains(disabledReason));
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        return this.disabledReasons.size();
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    public final String toString() {
        return "CommunalEnabledState(disabledReasons=" + this.disabledReasons + ")";
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return CollectionToArray.toArray(this, objArr);
    }
}
