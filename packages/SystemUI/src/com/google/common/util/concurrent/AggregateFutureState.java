package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractFuture;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;

/* loaded from: classes4.dex */
public abstract class AggregateFutureState extends AbstractFuture.TrustedFuture {
    public static final AtomicHelper ATOMIC_HELPER;
    public static final LazyLogger log = new LazyLogger(AggregateFutureState.class);
    public volatile int remaining;
    public volatile Set seenExceptions = null;

    public abstract class AtomicHelper {
        private AtomicHelper() {
        }

        public abstract void compareAndSetSeenExceptions(AggregateFuture aggregateFuture, Set set);

        public abstract int decrementAndGetRemainingCount(AggregateFuture aggregateFuture);
    }

    public final class SafeAtomicHelper extends AtomicHelper {
        public final AtomicIntegerFieldUpdater remainingCountUpdater;
        public final AtomicReferenceFieldUpdater seenExceptionsUpdater;

        public SafeAtomicHelper(AtomicReferenceFieldUpdater<? super AggregateFutureState, ? super Set<Throwable>> atomicReferenceFieldUpdater, AtomicIntegerFieldUpdater<? super AggregateFutureState> atomicIntegerFieldUpdater) {
            super();
            this.seenExceptionsUpdater = atomicReferenceFieldUpdater;
            this.remainingCountUpdater = atomicIntegerFieldUpdater;
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        public final void compareAndSetSeenExceptions(AggregateFuture aggregateFuture, Set set) {
            this.seenExceptionsUpdater.compareAndSet(aggregateFuture, null, set);
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        public final int decrementAndGetRemainingCount(AggregateFuture aggregateFuture) {
            return this.remainingCountUpdater.decrementAndGet(aggregateFuture);
        }
    }

    public final class SynchronizedAtomicHelper extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        public final void compareAndSetSeenExceptions(AggregateFuture aggregateFuture, Set set) {
            synchronized (aggregateFuture) {
                if (aggregateFuture.seenExceptions == null) {
                    aggregateFuture.seenExceptions = set;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        public final int decrementAndGetRemainingCount(AggregateFuture aggregateFuture) {
            int i;
            synchronized (aggregateFuture) {
                i = aggregateFuture.remaining - 1;
                aggregateFuture.remaining = i;
            }
            return i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        AtomicHelper safeAtomicHelper;
        Throwable th = null;
        Object[] objArr = 0;
        try {
            safeAtomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining"));
        } catch (Throwable th2) {
            SynchronizedAtomicHelper synchronizedAtomicHelper = new SynchronizedAtomicHelper();
            th = th2;
            safeAtomicHelper = synchronizedAtomicHelper;
        }
        ATOMIC_HELPER = safeAtomicHelper;
        if (th != null) {
            log.get().log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
    }

    public AggregateFutureState(int i) {
        this.remaining = i;
    }
}
