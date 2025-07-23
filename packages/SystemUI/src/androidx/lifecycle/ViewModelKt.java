package androidx.lifecycle;

import androidx.lifecycle.viewmodel.internal.CloseableCoroutineScope;
import androidx.lifecycle.viewmodel.internal.SynchronizedObject;
import androidx.lifecycle.viewmodel.internal.ViewModelImpl;
import java.util.LinkedHashMap;
import kotlin.NotImplementedError;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ViewModelKt {
    public static final SynchronizedObject VIEW_MODEL_SCOPE_LOCK = new SynchronizedObject();

    public static final CloseableCoroutineScope getViewModelScope(ViewModel viewModel) {
        AutoCloseable autoCloseable;
        CloseableCoroutineScope closeableCoroutineScope;
        CoroutineContext coroutineContext;
        synchronized (VIEW_MODEL_SCOPE_LOCK) {
            ViewModelImpl viewModelImpl = viewModel.impl;
            if (viewModelImpl != null) {
                synchronized (viewModelImpl.lock) {
                    autoCloseable = (AutoCloseable) ((LinkedHashMap) viewModelImpl.keyToCloseables).get("androidx.lifecycle.viewmodel.internal.ViewModelCoroutineScope.JOB_KEY");
                }
            } else {
                autoCloseable = null;
            }
            closeableCoroutineScope = (CloseableCoroutineScope) autoCloseable;
            if (closeableCoroutineScope == null) {
                try {
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    coroutineContext = MainDispatcherLoader.dispatcher.immediate;
                } catch (NotImplementedError unused) {
                    coroutineContext = EmptyCoroutineContext.INSTANCE;
                }
                CloseableCoroutineScope closeableCoroutineScope2 = new CloseableCoroutineScope(coroutineContext.plus(SupervisorKt.SupervisorJob$default()));
                ViewModelImpl viewModelImpl2 = viewModel.impl;
                if (viewModelImpl2 != null) {
                    viewModelImpl2.addCloseable("androidx.lifecycle.viewmodel.internal.ViewModelCoroutineScope.JOB_KEY", closeableCoroutineScope2);
                }
                closeableCoroutineScope = closeableCoroutineScope2;
            }
        }
        return closeableCoroutineScope;
    }
}
