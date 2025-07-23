package com.google.common.util.concurrent;

import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.util.concurrent.AggregateFuture;
import com.google.common.util.concurrent.CollectionFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CollectionFuture extends AggregateFuture {
    public List values;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ListFuture extends CollectionFuture {
        public ListFuture(ImmutableCollection<? extends ListenableFuture> immutableCollection, boolean z) {
            super(immutableCollection, z);
            Objects.requireNonNull(this.futures);
            if (this.futures.isEmpty()) {
                handleAllCompleted();
                return;
            }
            if (!this.allMustSucceed) {
                final ImmutableCollection immutableCollection2 = this.collectsValues ? this.futures : null;
                Runnable runnable = new Runnable() { // from class: com.google.common.util.concurrent.AggregateFuture$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CollectionFuture.ListFuture listFuture = CollectionFuture.ListFuture.this;
                        ImmutableCollection immutableCollection3 = immutableCollection2;
                        LazyLogger lazyLogger = AggregateFuture.logger;
                        listFuture.decrementCountAndMaybeComplete(immutableCollection3);
                    }
                };
                UnmodifiableIterator it = this.futures.iterator();
                while (it.hasNext()) {
                    ListenableFuture listenableFuture = (ListenableFuture) it.next();
                    if (listenableFuture.isDone()) {
                        decrementCountAndMaybeComplete(immutableCollection2);
                    } else {
                        listenableFuture.addListener(runnable, DirectExecutor.INSTANCE);
                    }
                }
                return;
            }
            UnmodifiableIterator it2 = this.futures.iterator();
            final int i = 0;
            while (it2.hasNext()) {
                final ListenableFuture listenableFuture2 = (ListenableFuture) it2.next();
                int i2 = i + 1;
                if (listenableFuture2.isDone()) {
                    processAllMustSucceedDoneFuture(i, listenableFuture2);
                } else {
                    listenableFuture2.addListener(new Runnable() { // from class: com.google.common.util.concurrent.AggregateFuture$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CollectionFuture.ListFuture listFuture = CollectionFuture.ListFuture.this;
                            int i3 = i;
                            ListenableFuture listenableFuture3 = listenableFuture2;
                            LazyLogger lazyLogger = AggregateFuture.logger;
                            listFuture.processAllMustSucceedDoneFuture(i3, listenableFuture3);
                        }
                    }, DirectExecutor.INSTANCE);
                }
                i = i2;
            }
        }

        @Override // com.google.common.util.concurrent.CollectionFuture
        public final Object combine(List list) {
            int size = list.size();
            CollectPreconditions.checkNonnegative(size, "initialArraySize");
            ArrayList arrayList = new ArrayList(size);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Present present = (Present) it.next();
                arrayList.add(present != null ? present.value : null);
            }
            return Collections.unmodifiableList(arrayList);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Present {
        public final Object value;

        public Present(Object obj) {
            this.value = obj;
        }
    }

    public CollectionFuture(ImmutableCollection<? extends ListenableFuture> immutableCollection, boolean z) {
        super(immutableCollection, z, true);
        List arrayList;
        if (immutableCollection.isEmpty()) {
            arrayList = Collections.EMPTY_LIST;
        } else {
            int size = immutableCollection.size();
            CollectPreconditions.checkNonnegative(size, "initialArraySize");
            arrayList = new ArrayList(size);
        }
        for (int i = 0; i < immutableCollection.size(); i++) {
            arrayList.add(null);
        }
        this.values = arrayList;
    }

    @Override // com.google.common.util.concurrent.AggregateFuture
    public final void collectOneValue(int i, Object obj) {
        List list = this.values;
        if (list != null) {
            list.set(i, new Present(obj));
        }
    }

    public abstract Object combine(List list);

    @Override // com.google.common.util.concurrent.AggregateFuture
    public final void handleAllCompleted() {
        List list = this.values;
        if (list != null) {
            set(combine(list));
        }
    }

    @Override // com.google.common.util.concurrent.AggregateFuture
    public final void releaseResources(AggregateFuture.ReleaseResourcesReason releaseResourcesReason) {
        super.releaseResources(releaseResourcesReason);
        this.values = null;
    }
}
