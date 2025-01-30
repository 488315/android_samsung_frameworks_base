package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.selects.SelectClause1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ReceiveChannel {
    void cancel(CancellationException cancellationException);

    SelectClause1 getOnReceiveCatching();

    boolean isClosedForReceive();

    /* renamed from: receiveCatching-JP2dKIU */
    Object mo2870receiveCatchingJP2dKIU(Continuation continuation);

    /* renamed from: tryReceive-PtdJZtk */
    Object mo2871tryReceivePtdJZtk();
}
