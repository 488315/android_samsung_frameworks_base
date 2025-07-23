package androidx.compose.runtime;

import androidx.compose.runtime.tooling.CompositionObserver;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CompositionObserverHolder {
    public final boolean root;

    public CompositionObserverHolder() {
        this(null, false, 3, 0 == true ? 1 : 0);
    }

    public CompositionObserverHolder(CompositionObserver compositionObserver, boolean z) {
        this.root = z;
    }

    public /* synthetic */ CompositionObserverHolder(CompositionObserver compositionObserver, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : compositionObserver, (i & 2) != 0 ? false : z);
    }
}
