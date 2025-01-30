package kotlinx.coroutines.channels;

import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectBuilderImpl;
import kotlinx.coroutines.selects.SelectKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ConflatedChannel extends AbstractChannel {
    public final ReentrantLock lock;
    public Object value;

    public ConflatedChannel(Function1 function1) {
        super(function1);
        this.lock = new ReentrantLock();
        this.value = AbstractChannelKt.EMPTY;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean enqueueReceiveInternal(Receive receive) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.enqueueReceiveInternal(receive);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final String getBufferDebugString() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return "(value=" + this.value + ")";
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferAlwaysEmpty() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferAlwaysFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferEmpty() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return this.value == AbstractChannelKt.EMPTY;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final Object offerInternal(Object obj) {
        Function1 function1;
        ReceiveOrClosed takeFirstReceiveOrPeekClosed;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Closed closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            if (this.value == AbstractChannelKt.EMPTY) {
                do {
                    takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                    if (takeFirstReceiveOrPeekClosed != null) {
                        if (takeFirstReceiveOrPeekClosed instanceof Closed) {
                            return takeFirstReceiveOrPeekClosed;
                        }
                    }
                } while (takeFirstReceiveOrPeekClosed.tryResumeReceive(obj) == null);
                Unit unit = Unit.INSTANCE;
                reentrantLock.unlock();
                takeFirstReceiveOrPeekClosed.completeResumeReceive(obj);
                return takeFirstReceiveOrPeekClosed.getOfferResult();
            }
            Object obj2 = this.value;
            UndeliveredElementException undeliveredElementException = null;
            if (obj2 != AbstractChannelKt.EMPTY && (function1 = this.onUndeliveredElement) != null) {
                undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj2, null);
            }
            this.value = obj;
            if (undeliveredElementException == null) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
            throw undeliveredElementException;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final void onCancelIdempotent(boolean z) {
        Function1 function1;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Symbol symbol = AbstractChannelKt.EMPTY;
            Object obj = this.value;
            UndeliveredElementException undeliveredElementException = null;
            if (obj != symbol && (function1 = this.onUndeliveredElement) != null) {
                undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, null);
            }
            this.value = symbol;
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            super.onCancelIdempotent(z);
            if (undeliveredElementException != null) {
                throw undeliveredElementException;
            }
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final Object pollInternal() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object obj = this.value;
            Symbol symbol = AbstractChannelKt.EMPTY;
            if (obj != symbol) {
                this.value = symbol;
                Unit unit = Unit.INSTANCE;
                return obj;
            }
            Object closedForSend = getClosedForSend();
            if (closedForSend == null) {
                closedForSend = AbstractChannelKt.POLL_FAILED;
            }
            return closedForSend;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final Object pollSelectInternal(SelectBuilderImpl selectBuilderImpl) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object obj = this.value;
            Symbol symbol = AbstractChannelKt.EMPTY;
            if (obj == symbol) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            if (!selectBuilderImpl.trySelect()) {
                return SelectKt.ALREADY_SELECTED;
            }
            Object obj2 = this.value;
            this.value = symbol;
            Unit unit = Unit.INSTANCE;
            return obj2;
        } finally {
            reentrantLock.unlock();
        }
    }
}
