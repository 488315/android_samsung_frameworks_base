package kotlinx.coroutines.channels;

import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ReceiveOrClosed {
    void completeResumeReceive(Object obj);

    Object getOfferResult();

    Symbol tryResumeReceive(Object obj);
}
