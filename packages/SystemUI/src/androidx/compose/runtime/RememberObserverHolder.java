package androidx.compose.runtime;

/* loaded from: classes.dex */
public final class RememberObserverHolder {
    public final Anchor after;
    public final RememberObserver wrapped;

    public RememberObserverHolder(RememberObserver rememberObserver, Anchor anchor) {
        this.wrapped = rememberObserver;
        this.after = anchor;
    }
}
