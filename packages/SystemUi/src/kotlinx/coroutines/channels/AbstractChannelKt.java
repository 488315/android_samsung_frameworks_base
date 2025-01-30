package kotlinx.coroutines.channels;

import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractChannelKt {
    public static final Symbol EMPTY = new Symbol("EMPTY");
    public static final Symbol OFFER_SUCCESS = new Symbol("OFFER_SUCCESS");
    public static final Symbol OFFER_FAILED = new Symbol("OFFER_FAILED");
    public static final Symbol POLL_FAILED = new Symbol("POLL_FAILED");
    public static final Symbol ENQUEUE_FAILED = new Symbol("ENQUEUE_FAILED");
    public static final Symbol HANDLER_INVOKED = new Symbol("ON_CLOSE_HANDLER_INVOKED");
}
