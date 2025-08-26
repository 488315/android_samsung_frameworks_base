package kotlin.coroutines;

/* loaded from: classes4.dex */
public interface Continuation {
    CoroutineContext getContext();

    void resumeWith(Object obj);
}
