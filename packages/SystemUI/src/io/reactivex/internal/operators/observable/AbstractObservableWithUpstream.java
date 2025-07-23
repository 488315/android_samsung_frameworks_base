package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractObservableWithUpstream extends Observable {
    public final ObservableSource source;

    public AbstractObservableWithUpstream(ObservableSource observableSource) {
        this.source = observableSource;
    }
}
