package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/* loaded from: classes4.dex */
public abstract class AbstractObservableWithUpstream extends Observable {
    public final ObservableSource source;

    public AbstractObservableWithUpstream(ObservableSource observableSource) {
        this.source = observableSource;
    }
}
