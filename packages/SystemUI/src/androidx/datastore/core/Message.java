package androidx.datastore.core;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CompletableDeferred;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Message {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Update extends Message {
        public final CompletableDeferred ack;
        public final CoroutineContext callerContext;
        public final State lastState;
        public final Function2 transform;

        public Update(Function2 function2, CompletableDeferred completableDeferred, State state, CoroutineContext coroutineContext) {
            super(null);
            this.transform = function2;
            this.ack = completableDeferred;
            this.lastState = state;
            this.callerContext = coroutineContext;
        }
    }

    public /* synthetic */ Message(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private Message() {
    }
}
