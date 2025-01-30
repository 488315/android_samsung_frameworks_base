package kotlinx.coroutines.flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface MutableSharedFlow extends SharedFlow, FlowCollector {
    void resetReplayCache();

    boolean tryEmit(Object obj);
}
