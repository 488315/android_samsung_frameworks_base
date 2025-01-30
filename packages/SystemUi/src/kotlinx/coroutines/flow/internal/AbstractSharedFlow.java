package kotlinx.coroutines.flow.internal;

import java.util.Arrays;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractSharedFlow {
    public SubscriptionCountStateFlow _subscriptionCount;
    public int nCollectors;
    public int nextIndex;
    public AbstractSharedFlowSlot[] slots;

    public final AbstractSharedFlowSlot allocateSlot() {
        AbstractSharedFlowSlot abstractSharedFlowSlot;
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            AbstractSharedFlowSlot[] abstractSharedFlowSlotArr = this.slots;
            if (abstractSharedFlowSlotArr == null) {
                abstractSharedFlowSlotArr = createSlotArray();
                this.slots = abstractSharedFlowSlotArr;
            } else if (this.nCollectors >= abstractSharedFlowSlotArr.length) {
                Object[] copyOf = Arrays.copyOf(abstractSharedFlowSlotArr, abstractSharedFlowSlotArr.length * 2);
                this.slots = (AbstractSharedFlowSlot[]) copyOf;
                abstractSharedFlowSlotArr = (AbstractSharedFlowSlot[]) copyOf;
            }
            int i = this.nextIndex;
            do {
                abstractSharedFlowSlot = abstractSharedFlowSlotArr[i];
                if (abstractSharedFlowSlot == null) {
                    abstractSharedFlowSlot = createSlot();
                    abstractSharedFlowSlotArr[i] = abstractSharedFlowSlot;
                }
                i++;
                if (i >= abstractSharedFlowSlotArr.length) {
                    i = 0;
                }
            } while (!abstractSharedFlowSlot.allocateLocked(this));
            this.nextIndex = i;
            this.nCollectors++;
            subscriptionCountStateFlow = this._subscriptionCount;
        }
        if (subscriptionCountStateFlow != null) {
            synchronized (subscriptionCountStateFlow) {
                Object[] objArr = subscriptionCountStateFlow.buffer;
                Intrinsics.checkNotNull(objArr);
                long head = (subscriptionCountStateFlow.replayIndex + ((int) ((subscriptionCountStateFlow.getHead() + subscriptionCountStateFlow.bufferSize) - subscriptionCountStateFlow.replayIndex))) - 1;
                Symbol symbol = SharedFlowKt.NO_VALUE;
                subscriptionCountStateFlow.tryEmit(Integer.valueOf(((Number) objArr[((int) head) & (objArr.length - 1)]).intValue() + 1));
            }
        }
        return abstractSharedFlowSlot;
    }

    public abstract AbstractSharedFlowSlot createSlot();

    public abstract AbstractSharedFlowSlot[] createSlotArray();

    public final void freeSlot(AbstractSharedFlowSlot abstractSharedFlowSlot) {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        int i;
        Continuation[] freeLocked;
        synchronized (this) {
            int i2 = this.nCollectors - 1;
            this.nCollectors = i2;
            subscriptionCountStateFlow = this._subscriptionCount;
            if (i2 == 0) {
                this.nextIndex = 0;
            }
            freeLocked = abstractSharedFlowSlot.freeLocked(this);
        }
        for (Continuation continuation : freeLocked) {
            if (continuation != null) {
                int i3 = Result.$r8$clinit;
                continuation.resumeWith(Unit.INSTANCE);
            }
        }
        if (subscriptionCountStateFlow != null) {
            synchronized (subscriptionCountStateFlow) {
                Intrinsics.checkNotNull(subscriptionCountStateFlow.buffer);
                long head = (subscriptionCountStateFlow.replayIndex + ((int) ((subscriptionCountStateFlow.getHead() + subscriptionCountStateFlow.bufferSize) - subscriptionCountStateFlow.replayIndex))) - 1;
                Symbol symbol = SharedFlowKt.NO_VALUE;
                subscriptionCountStateFlow.tryEmit(Integer.valueOf(((Number) r8[((int) head) & (r8.length - 1)]).intValue() - 1));
            }
        }
    }

    public final SubscriptionCountStateFlow getSubscriptionCount() {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            subscriptionCountStateFlow = this._subscriptionCount;
            if (subscriptionCountStateFlow == null) {
                subscriptionCountStateFlow = new SubscriptionCountStateFlow(this.nCollectors);
                this._subscriptionCount = subscriptionCountStateFlow;
            }
        }
        return subscriptionCountStateFlow;
    }
}
