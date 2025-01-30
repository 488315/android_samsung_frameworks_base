package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SerializedObserver implements Observer, Disposable {
    public final boolean delayError;
    public volatile boolean done;
    public final Observer downstream;
    public boolean emitting;
    public AppendOnlyLinkedArrayList queue;
    public Disposable upstream;

    public SerializedObserver(Observer observer) {
        this(observer, false);
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        this.upstream.dispose();
    }

    @Override // io.reactivex.Observer
    public final void onComplete() {
        if (this.done) {
            return;
        }
        synchronized (this) {
            if (this.done) {
                return;
            }
            if (!this.emitting) {
                this.done = true;
                this.emitting = true;
                this.downstream.onComplete();
            } else {
                AppendOnlyLinkedArrayList appendOnlyLinkedArrayList = this.queue;
                if (appendOnlyLinkedArrayList == null) {
                    appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList(4);
                    this.queue = appendOnlyLinkedArrayList;
                }
                appendOnlyLinkedArrayList.add(NotificationLite.COMPLETE);
            }
        }
    }

    @Override // io.reactivex.Observer
    public final void onError(Throwable th) {
        if (this.done) {
            RxJavaPlugins.onError(th);
            return;
        }
        synchronized (this) {
            boolean z = true;
            if (!this.done) {
                if (this.emitting) {
                    this.done = true;
                    AppendOnlyLinkedArrayList appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList(4);
                        this.queue = appendOnlyLinkedArrayList;
                    }
                    Object error = NotificationLite.error(th);
                    if (this.delayError) {
                        appendOnlyLinkedArrayList.add(error);
                    } else {
                        appendOnlyLinkedArrayList.head[0] = error;
                    }
                    return;
                }
                this.done = true;
                this.emitting = true;
                z = false;
            }
            if (z) {
                RxJavaPlugins.onError(th);
            } else {
                this.downstream.onError(th);
            }
        }
    }

    @Override // io.reactivex.Observer
    public final void onNext(Object obj) {
        AppendOnlyLinkedArrayList appendOnlyLinkedArrayList;
        if (this.done) {
            return;
        }
        if (obj == null) {
            this.upstream.dispose();
            onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            return;
        }
        synchronized (this) {
            if (this.done) {
                return;
            }
            if (this.emitting) {
                AppendOnlyLinkedArrayList appendOnlyLinkedArrayList2 = this.queue;
                if (appendOnlyLinkedArrayList2 == null) {
                    appendOnlyLinkedArrayList2 = new AppendOnlyLinkedArrayList(4);
                    this.queue = appendOnlyLinkedArrayList2;
                }
                NotificationLite notificationLite = NotificationLite.COMPLETE;
                appendOnlyLinkedArrayList2.add(obj);
                return;
            }
            this.emitting = true;
            this.downstream.onNext(obj);
            do {
                synchronized (this) {
                    appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        this.emitting = false;
                        return;
                    }
                    this.queue = null;
                }
            } while (!appendOnlyLinkedArrayList.accept(this.downstream));
        }
    }

    @Override // io.reactivex.Observer
    public final void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.upstream, disposable)) {
            this.upstream = disposable;
            this.downstream.onSubscribe(this);
        }
    }

    public SerializedObserver(Observer observer, boolean z) {
        this.downstream = observer;
        this.delayError = z;
    }
}
