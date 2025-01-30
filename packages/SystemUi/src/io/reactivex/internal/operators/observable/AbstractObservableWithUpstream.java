package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractObservableWithUpstream extends Observable {
    public final ObservableSource source;

    public AbstractObservableWithUpstream(ObservableSource observableSource) {
        this.source = observableSource;
    }
}
