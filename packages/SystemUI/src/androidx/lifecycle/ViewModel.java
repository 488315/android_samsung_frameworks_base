package androidx.lifecycle;

import androidx.lifecycle.viewmodel.internal.ViewModelImpl;
import java.util.Arrays;
import java.util.LinkedHashMap;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ViewModel {
    public final ViewModelImpl impl;

    public ViewModel() {
        this.impl = new ViewModelImpl();
    }

    public final void clear$lifecycle_viewmodel_release() {
        ViewModelImpl viewModelImpl = this.impl;
        if (viewModelImpl != null && !viewModelImpl.isCleared) {
            viewModelImpl.isCleared = true;
            synchronized (viewModelImpl.lock) {
                try {
                    for (AutoCloseable autoCloseable : SetsKt___SetsKt.plus(viewModelImpl.closeables, (Iterable) ((LinkedHashMap) viewModelImpl.keyToCloseables).values())) {
                        if (autoCloseable != null) {
                            try {
                                autoCloseable.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    viewModelImpl.closeables.clear();
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        onCleared();
    }

    public ViewModel(CoroutineScope coroutineScope) {
        this.impl = new ViewModelImpl(coroutineScope);
    }

    public ViewModel(AutoCloseable... autoCloseableArr) {
        this.impl = new ViewModelImpl((AutoCloseable[]) Arrays.copyOf(autoCloseableArr, autoCloseableArr.length));
    }

    public ViewModel(CoroutineScope coroutineScope, AutoCloseable... autoCloseableArr) {
        this.impl = new ViewModelImpl(coroutineScope, (AutoCloseable[]) Arrays.copyOf(autoCloseableArr, autoCloseableArr.length));
    }

    public void onCleared() {
    }
}
