package io.reactivex;

import io.reactivex.disposables.Disposable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface Observer {
    void onComplete();

    void onError(Throwable th);

    void onNext(Object obj);

    void onSubscribe(Disposable disposable);
}
