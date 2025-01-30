package io.reactivex.disposables;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.OpenHashSet;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
                if (!this.disposed) {
                    OpenHashSet openHashSet = this.resources;
                    if (openHashSet == null) {
                        openHashSet = new OpenHashSet();
                        this.resources = openHashSet;
                    }
                    openHashSet.add(disposable);
                    return true;
                }
            }
        }
        disposable.dispose();
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004c A[Catch: all -> 0x0050, DONT_GENERATE, TryCatch #0 {, blocks: (B:9:0x000d, B:11:0x0011, B:13:0x0013, B:15:0x0017, B:21:0x004c, B:23:0x002d, B:25:0x0033, B:27:0x0037, B:29:0x003f, B:32:0x0045, B:35:0x004e), top: B:8:0x000d }] */
    @Override // io.reactivex.internal.disposables.DisposableContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean delete(Disposable disposable) {
        Object obj;
        boolean z;
        int i = ObjectHelper.$r8$clinit;
        if (disposable == null) {
            throw new NullPointerException("disposables is null");
        }
        if (this.disposed) {
            return false;
        }
        synchronized (this) {
            if (this.disposed) {
                return false;
            }
            OpenHashSet openHashSet = this.resources;
            if (openHashSet != null) {
                Object[] objArr = openHashSet.keys;
                int i2 = openHashSet.mask;
                int hashCode = disposable.hashCode() * (-1640531527);
                int i3 = (hashCode ^ (hashCode >>> 16)) & i2;
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
                    z = true;
                    if (!z) {
                        return true;
                    }
                }
                z = false;
                if (!z) {
                }
            }
            return false;
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        if (this.disposed) {
            return;
        }
        synchronized (this) {
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
        int i = ObjectHelper.$r8$clinit;
        if (disposableArr == null) {
            throw new NullPointerException("disposables is null");
        }
        this.resources = new OpenHashSet(disposableArr.length + 1);
        for (Disposable disposable : disposableArr) {
            ObjectHelper.requireNonNull(disposable, "A Disposable in the disposables array is null");
            this.resources.add(disposable);
        }
    }

    public CompositeDisposable(Iterable<? extends Disposable> iterable) {
        int i = ObjectHelper.$r8$clinit;
        if (iterable != null) {
            this.resources = new OpenHashSet();
            for (Disposable disposable : iterable) {
                ObjectHelper.requireNonNull(disposable, "A Disposable item in the disposables sequence is null");
                this.resources.add(disposable);
            }
            return;
        }
        throw new NullPointerException("disposables is null");
    }
}
