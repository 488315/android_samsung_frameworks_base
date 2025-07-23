package com.google.common.util.concurrent;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.util.concurrent.AbstractFuture;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AggregateFuture extends AggregateFutureState {
    public static final LazyLogger logger = new LazyLogger(AggregateFuture.class);
    public final boolean allMustSucceed;
    public final boolean collectsValues;
    public ImmutableCollection futures;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum ReleaseResourcesReason {
        OUTPUT_FUTURE_DONE,
        ALL_INPUT_FUTURES_PROCESSED
    }

    public AggregateFuture(ImmutableCollection<? extends ListenableFuture> immutableCollection, boolean z, boolean z2) {
        super(immutableCollection.size());
        this.futures = immutableCollection;
        this.allMustSucceed = z;
        this.collectsValues = z2;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        ImmutableCollection immutableCollection = this.futures;
        releaseResources(ReleaseResourcesReason.OUTPUT_FUTURE_DONE);
        if ((this.value instanceof AbstractFuture.Cancellation) && (immutableCollection != null)) {
            boolean wasInterrupted = wasInterrupted();
            UnmodifiableIterator it = immutableCollection.iterator();
            while (it.hasNext()) {
                ((Future) it.next()).cancel(wasInterrupted);
            }
        }
    }

    public abstract void collectOneValue(int i, Object obj);

    public final void decrementCountAndMaybeComplete(ImmutableCollection immutableCollection) {
        int decrementAndGetRemainingCount = AggregateFutureState.ATOMIC_HELPER.decrementAndGetRemainingCount(this);
        int i = 0;
        if (!(decrementAndGetRemainingCount >= 0)) {
            throw new IllegalStateException("Less than 0 remaining futures");
        }
        if (decrementAndGetRemainingCount == 0) {
            if (immutableCollection != null) {
                UnmodifiableIterator it = immutableCollection.iterator();
                while (it.hasNext()) {
                    Future future = (Future) it.next();
                    if (!future.isCancelled()) {
                        try {
                            collectOneValue(i, Uninterruptibles.getUninterruptibly(future));
                        } catch (ExecutionException e) {
                            handleException(e.getCause());
                        } catch (Throwable th) {
                            handleException(th);
                        }
                    }
                    i++;
                }
            }
            this.seenExceptions = null;
            handleAllCompleted();
            releaseResources(ReleaseResourcesReason.ALL_INPUT_FUTURES_PROCESSED);
        }
    }

    public abstract void handleAllCompleted();

    public final void handleException(Throwable th) {
        th.getClass();
        if (this.allMustSucceed && !setException(th)) {
            Set set = this.seenExceptions;
            if (set == null) {
                Set newSetFromMap = Collections.newSetFromMap(new ConcurrentHashMap());
                newSetFromMap.getClass();
                if (!(this.value instanceof AbstractFuture.Cancellation)) {
                    Throwable tryInternalFastPathGetFailure = tryInternalFastPathGetFailure();
                    Objects.requireNonNull(tryInternalFastPathGetFailure);
                    while (tryInternalFastPathGetFailure != null && newSetFromMap.add(tryInternalFastPathGetFailure)) {
                        tryInternalFastPathGetFailure = tryInternalFastPathGetFailure.getCause();
                    }
                }
                AggregateFutureState.ATOMIC_HELPER.compareAndSetSeenExceptions(this, newSetFromMap);
                Set set2 = this.seenExceptions;
                Objects.requireNonNull(set2);
                set = set2;
            }
            for (Throwable th2 = th; th2 != null; th2 = th2.getCause()) {
                if (set.add(th2)) {
                }
            }
            logger.get().log(Level.SEVERE, th instanceof Error ? "Input Future failed with Error" : "Got more than one input Future failure. Logging failures after the first", th);
            return;
        }
        boolean z = th instanceof Error;
        if (z) {
            logger.get().log(Level.SEVERE, z ? "Input Future failed with Error" : "Got more than one input Future failure. Logging failures after the first", th);
        }
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String pendingToString() {
        ImmutableCollection immutableCollection = this.futures;
        if (immutableCollection == null) {
            return super.pendingToString();
        }
        return "futures=" + immutableCollection;
    }

    public final void processAllMustSucceedDoneFuture(int i, ListenableFuture listenableFuture) {
        try {
            if (listenableFuture.isCancelled()) {
                this.futures = null;
                cancel(false);
            } else {
                try {
                    collectOneValue(i, Uninterruptibles.getUninterruptibly(listenableFuture));
                } catch (ExecutionException e) {
                    handleException(e.getCause());
                } catch (Throwable th) {
                    handleException(th);
                }
            }
        } finally {
            decrementCountAndMaybeComplete(null);
        }
    }

    public void releaseResources(ReleaseResourcesReason releaseResourcesReason) {
        releaseResourcesReason.getClass();
        this.futures = null;
    }
}
