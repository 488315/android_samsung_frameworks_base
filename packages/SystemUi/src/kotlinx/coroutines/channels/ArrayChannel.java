package kotlinx.coroutines.channels;

import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.channels.AbstractChannel;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectBuilderImpl;
import kotlinx.coroutines.selects.SelectKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ArrayChannel extends AbstractChannel {
    public Object[] buffer;
    public final int capacity;
    public int head;
    public final ReentrantLock lock;
    public final BufferOverflow onBufferOverflow;
    public final AtomicInt size;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BufferOverflow.values().length];
            try {
                iArr[BufferOverflow.SUSPEND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BufferOverflow.DROP_LATEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BufferOverflow.DROP_OLDEST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ArrayChannel(int i, BufferOverflow bufferOverflow, Function1 function1) {
        super(function1);
        this.capacity = i;
        this.onBufferOverflow = bufferOverflow;
        if (!(i >= 1)) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("ArrayChannel capacity must be at least 1, but ", i, " was specified").toString());
        }
        this.lock = new ReentrantLock();
        int min = Math.min(i, 8);
        Object[] objArr = new Object[min];
        Arrays.fill(objArr, 0, min, AbstractChannelKt.EMPTY);
        this.buffer = objArr;
        this.size = AtomicFU.atomic();
    }

    public final void enqueueElement(int i, Object obj) {
        int i2 = this.capacity;
        if (i >= i2) {
            Object[] objArr = this.buffer;
            int i3 = this.head;
            objArr[i3 % objArr.length] = null;
            objArr[(i + i3) % objArr.length] = obj;
            this.head = (i3 + 1) % objArr.length;
            return;
        }
        Object[] objArr2 = this.buffer;
        if (i >= objArr2.length) {
            int min = Math.min(objArr2.length * 2, i2);
            Object[] objArr3 = new Object[min];
            for (int i4 = 0; i4 < i; i4++) {
                Object[] objArr4 = this.buffer;
                objArr3[i4] = objArr4[(this.head + i4) % objArr4.length];
            }
            Arrays.fill(objArr3, i, min, AbstractChannelKt.EMPTY);
            this.buffer = objArr3;
            this.head = 0;
        }
        Object[] objArr5 = this.buffer;
        objArr5[(this.head + i) % objArr5.length] = obj;
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
    public final Object enqueueSend(SendElement sendElement) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.enqueueSend(sendElement);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final String getBufferDebugString() {
        return SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("(buffer:capacity=", this.capacity, ",size=", this.size.value, ")");
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
        return this.size.value == 0;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return this.size.value == this.capacity && this.onBufferOverflow == BufferOverflow.SUSPEND;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel, kotlinx.coroutines.channels.ReceiveChannel
    public final boolean isClosedForReceive() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.isClosedForReceive();
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0041 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object offerInternal(Object obj) {
        Symbol symbol;
        ReceiveOrClosed takeFirstReceiveOrPeekClosed;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int i = this.size.value;
            Closed closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            if (i >= this.capacity) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[this.onBufferOverflow.ordinal()];
                if (i2 == 1) {
                    symbol = AbstractChannelKt.OFFER_FAILED;
                } else if (i2 == 2) {
                    symbol = AbstractChannelKt.OFFER_SUCCESS;
                } else if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                if (symbol == null) {
                    return symbol;
                }
                if (i == 0) {
                    do {
                        takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                        if (takeFirstReceiveOrPeekClosed != null) {
                            if (takeFirstReceiveOrPeekClosed instanceof Closed) {
                                this.size.setValue(i);
                                return takeFirstReceiveOrPeekClosed;
                            }
                        }
                    } while (takeFirstReceiveOrPeekClosed.tryResumeReceive(obj) == null);
                    this.size.setValue(i);
                    Unit unit = Unit.INSTANCE;
                    reentrantLock.unlock();
                    takeFirstReceiveOrPeekClosed.completeResumeReceive(obj);
                    return takeFirstReceiveOrPeekClosed.getOfferResult();
                }
                enqueueElement(i, obj);
                return AbstractChannelKt.OFFER_SUCCESS;
            }
            this.size.setValue(i + 1);
            symbol = null;
            if (symbol == null) {
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final void onCancelIdempotent(boolean z) {
        Function1 function1 = this.onUndeliveredElement;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int i = this.size.value;
            UndeliveredElementException undeliveredElementException = null;
            for (int i2 = 0; i2 < i; i2++) {
                Object obj = this.buffer[this.head];
                if (function1 != null && obj != AbstractChannelKt.EMPTY) {
                    undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, obj, undeliveredElementException);
                }
                Object[] objArr = this.buffer;
                int i3 = this.head;
                objArr[i3] = AbstractChannelKt.EMPTY;
                this.head = (i3 + 1) % objArr.length;
            }
            this.size.setValue(0);
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
            int i = this.size.value;
            if (i == 0) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            Object[] objArr = this.buffer;
            int i2 = this.head;
            Object obj = objArr[i2];
            Send send = null;
            objArr[i2] = null;
            this.size.setValue(i - 1);
            Object obj2 = AbstractChannelKt.POLL_FAILED;
            boolean z = false;
            if (i == this.capacity) {
                Send send2 = null;
                while (true) {
                    Send takeFirstSendOrPeekClosed = takeFirstSendOrPeekClosed();
                    if (takeFirstSendOrPeekClosed == null) {
                        send = send2;
                        break;
                    }
                    if (takeFirstSendOrPeekClosed.tryResumeSend(null) != null) {
                        obj2 = takeFirstSendOrPeekClosed.getPollResult();
                        z = true;
                        send = takeFirstSendOrPeekClosed;
                        break;
                    }
                    takeFirstSendOrPeekClosed.undeliveredElement();
                    send2 = takeFirstSendOrPeekClosed;
                }
            }
            if (obj2 != AbstractChannelKt.POLL_FAILED && !(obj2 instanceof Closed)) {
                this.size.setValue(i);
                Object[] objArr2 = this.buffer;
                objArr2[(this.head + i) % objArr2.length] = obj2;
            }
            this.head = (this.head + 1) % this.buffer.length;
            Unit unit = Unit.INSTANCE;
            if (z) {
                Intrinsics.checkNotNull(send);
                send.completeResumeSend();
            }
            return obj;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ac A[Catch: all -> 0x00d6, TRY_LEAVE, TryCatch #0 {all -> 0x00d6, blocks: (B:3:0x0005, B:5:0x000b, B:7:0x0011, B:11:0x0017, B:13:0x002e, B:48:0x0040, B:28:0x008f, B:30:0x0093, B:32:0x0097, B:33:0x00bd, B:38:0x00a6, B:40:0x00ac, B:15:0x0050, B:17:0x0054, B:20:0x0058, B:22:0x005c, B:25:0x006b, B:43:0x0073, B:44:0x008d), top: B:2:0x0005 }] */
    @Override // kotlinx.coroutines.channels.AbstractChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object pollSelectInternal(SelectBuilderImpl selectBuilderImpl) {
        boolean z;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int i = this.size.value;
            if (i == 0) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            Object[] objArr = this.buffer;
            int i2 = this.head;
            Object obj = objArr[i2];
            Object obj2 = null;
            objArr[i2] = null;
            this.size.setValue(i - 1);
            Object obj3 = AbstractChannelKt.POLL_FAILED;
            if (i == this.capacity) {
                while (true) {
                    AbstractChannel.TryPollDesc tryPollDesc = new AbstractChannel.TryPollDesc(this.queue);
                    Object perform = new SelectBuilderImpl.AtomicSelectOp(selectBuilderImpl, tryPollDesc).perform(null);
                    if (perform != null) {
                        if (perform == AbstractChannelKt.POLL_FAILED) {
                            break;
                        }
                        if (perform != AtomicKt.RETRY_ATOMIC) {
                            if (perform == SelectKt.ALREADY_SELECTED) {
                                this.size.setValue(i);
                                this.buffer[this.head] = obj;
                                return perform;
                            }
                            if (!(perform instanceof Closed)) {
                                throw new IllegalStateException(("performAtomicTrySelect(describeTryOffer) returned " + perform).toString());
                            }
                            z = true;
                            obj3 = perform;
                            obj2 = obj3;
                        }
                    } else {
                        obj2 = tryPollDesc.getAffectedNode();
                        Intrinsics.checkNotNull(obj2);
                        obj3 = ((Send) obj2).getPollResult();
                        z = true;
                        break;
                    }
                }
                if (obj3 == AbstractChannelKt.POLL_FAILED && !(obj3 instanceof Closed)) {
                    this.size.setValue(i);
                    Object[] objArr2 = this.buffer;
                    objArr2[(this.head + i) % objArr2.length] = obj3;
                } else if (!selectBuilderImpl.trySelect()) {
                    this.size.setValue(i);
                    this.buffer[this.head] = obj;
                    return SelectKt.ALREADY_SELECTED;
                }
                this.head = (this.head + 1) % this.buffer.length;
                Unit unit = Unit.INSTANCE;
                if (z) {
                    Intrinsics.checkNotNull(obj2);
                    ((Send) obj2).completeResumeSend();
                }
                return obj;
            }
            z = false;
            if (obj3 == AbstractChannelKt.POLL_FAILED) {
            }
            if (!selectBuilderImpl.trySelect()) {
            }
            this.head = (this.head + 1) % this.buffer.length;
            Unit unit2 = Unit.INSTANCE;
            if (z) {
            }
            return obj;
        } finally {
            reentrantLock.unlock();
        }
    }
}
