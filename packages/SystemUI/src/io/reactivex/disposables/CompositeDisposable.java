package io.reactivex.disposables;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.OpenHashSet;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class CompositeDisposable implements Disposable, DisposableContainer {
    public volatile boolean disposed;
    public OpenHashSet resources;

    public CompositeDisposable() {
    }

    @Override // io.reactivex.internal.disposables.DisposableContainer
    public final boolean add(Disposable disposable) {
        int i = ObjectHelper.$r8$clinit;
        if (!this.disposed) {
            synchronized (this) {
                try {
                    if (!this.disposed) {
                        OpenHashSet openHashSet = this.resources;
                        if (openHashSet == null) {
                            openHashSet = new OpenHashSet();
                            this.resources = openHashSet;
                        }
                        openHashSet.add(disposable);
                        return true;
                    }
                } finally {
                }
            }
        }
        disposable.dispose();
        return false;
    }

    @Override // io.reactivex.internal.disposables.DisposableContainer
    public final boolean delete(Disposable disposable) {
        Object obj;
        int i = ObjectHelper.$r8$clinit;
        if (this.disposed) {
            return false;
        }
        synchronized (this) {
            try {
                if (this.disposed) {
                    return false;
                }
                OpenHashSet openHashSet = this.resources;
                if (openHashSet != null) {
                    Object[] objArr = openHashSet.keys;
                    int i2 = openHashSet.mask;
                    int iHashCode = disposable.hashCode() * (-1640531527);
                    int i3 = (iHashCode ^ (iHashCode >>> 16)) & i2;
                    Object obj2 = objArr[i3];
                    if (obj2 != null) {
                        if (obj2.equals(disposable)) {
                            openHashSet.removeEntry(i3, i2, objArr);
                        } else {
                            do {
                                i3 = (i3 + 1) & i2;
                                obj = objArr[i3];
                                if (obj == null) {
                                }
                            } while (!obj.equals(disposable));
                            openHashSet.removeEntry(i3, i2, objArr);
                        }
                        return true;
                    }
                }
                return false;
            } finally {
            }
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        if (this.disposed) {
            return;
        }
        synchronized (this) {
            try {
                if (this.disposed) {
                    return;
                }
                this.disposed = true;
                OpenHashSet openHashSet = this.resources;
                ArrayList arrayList = null;
                this.resources = null;
                if (openHashSet == null) {
                    return;
                }
                for (Object obj : openHashSet.keys) {
                    if (obj instanceof Disposable) {
                        try {
                            ((Disposable) obj).dispose();
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(th);
                        }
                    }
                }
                if (arrayList != null) {
                    if (arrayList.size() != 1) {
                        throw new CompositeException(arrayList);
                    }
                    throw ExceptionHelper.wrapOrThrow((Throwable) arrayList.get(0));
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // io.reactivex.internal.disposables.DisposableContainer
    public final boolean remove(Disposable disposable) {
        if (!delete(disposable)) {
            return false;
        }
        disposable.dispose();
        return true;
    }

    public CompositeDisposable(Disposable... disposableArr) {
        ObjectHelper.requireNonNull(disposableArr, "disposables is null");
        this.resources = new OpenHashSet(disposableArr.length + 1);
        for (Disposable disposable : disposableArr) {
            ObjectHelper.requireNonNull(disposable, "A Disposable in the disposables array is null");
            this.resources.add(disposable);
        }
    }

    public CompositeDisposable(Iterable<? extends Disposable> iterable) {
        ObjectHelper.requireNonNull(iterable, "disposables is null");
        this.resources = new OpenHashSet();
        for (Disposable disposable : iterable) {
            ObjectHelper.requireNonNull(disposable, "A Disposable item in the disposables sequence is null");
            this.resources.add(disposable);
        }
    }
}
