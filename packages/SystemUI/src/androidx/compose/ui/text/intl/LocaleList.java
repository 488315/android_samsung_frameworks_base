package androidx.compose.ui.text.intl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LocaleList implements Collection<Locale>, KMappedMarker {
    public static final Companion Companion = new Companion(null);
    public static final LocaleList Empty = new LocaleList(EmptyList.INSTANCE);
    public final List localeList;
    public final int size;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LocaleList(List<Locale> list) {
        this.localeList = list;
        this.size = list.size();
    }

    @Override // java.util.Collection
    public final /* bridge */ /* synthetic */ boolean add(Locale locale) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection<? extends Locale> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        if (!(obj instanceof Locale)) {
            return false;
        }
        return this.localeList.contains((Locale) obj);
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        return this.localeList.containsAll(collection);
    }

    @Override // java.util.Collection
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LocaleList) && Intrinsics.areEqual(this.localeList, ((LocaleList) obj).localeList);
    }

    @Override // java.util.Collection
    public final int hashCode() {
        return this.localeList.hashCode();
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.localeList.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.localeList.iterator();
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean removeIf(Predicate<? super Locale> predicate) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final int size() {
        return this.size;
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    public final String toString() {
        return "LocaleList(localeList=" + this.localeList + ')';
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return CollectionToArray.toArray(this, objArr);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public LocaleList(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = ","
            java.lang.String[] r0 = new java.lang.String[]{r0}
            r1 = 0
            r2 = 6
            java.util.List r6 = kotlin.text.StringsKt__StringsKt.split$default(r6, r0, r1, r2)
            java.util.ArrayList r0 = new java.util.ArrayList
            int r2 = r6.size()
            r0.<init>(r2)
            r2 = r6
            java.util.Collection r2 = (java.util.Collection) r2
            int r2 = r2.size()
            r3 = r1
        L1d:
            if (r3 >= r2) goto L33
            java.lang.Object r4 = r6.get(r3)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.CharSequence r4 = kotlin.text.StringsKt__StringsKt.trim(r4)
            java.lang.String r4 = r4.toString()
            r0.add(r4)
            int r3 = r3 + 1
            goto L1d
        L33:
            java.util.ArrayList r6 = new java.util.ArrayList
            int r2 = r0.size()
            r6.<init>(r2)
            int r2 = r0.size()
        L40:
            if (r1 >= r2) goto L53
            java.lang.Object r3 = r0.get(r1)
            java.lang.String r3 = (java.lang.String) r3
            androidx.compose.ui.text.intl.Locale r4 = new androidx.compose.ui.text.intl.Locale
            r4.<init>(r3)
            r6.add(r4)
            int r1 = r1 + 1
            goto L40
        L53:
            r5.<init>(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.intl.LocaleList.<init>(java.lang.String):void");
    }

    public LocaleList(Locale... localeArr) {
        this((List<Locale>) ArraysKt___ArraysKt.toList(localeArr));
    }
}
