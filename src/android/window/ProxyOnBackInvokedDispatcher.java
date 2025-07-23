package android.window;

import android.content.Context;
import android.util.Pair;
import android.window.WindowOnBackInvokedDispatcher;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class ProxyOnBackInvokedDispatcher implements OnBackInvokedDispatcher {
    private final WindowOnBackInvokedDispatcher.Checker mChecker;
    private ImeOnBackInvokedDispatcher mImeDispatcher;
    private final List<Pair<OnBackInvokedCallback, Integer>> mCallbacks = new ArrayList();
    private final Object mLock = new Object();
    private OnBackInvokedDispatcher mActualDispatcher = null;

    public ProxyOnBackInvokedDispatcher(Context context) {
        this.mChecker = new WindowOnBackInvokedDispatcher.Checker(context);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerOnBackInvokedCallback(int i, OnBackInvokedCallback onBackInvokedCallback) {
        if (this.mChecker.checkApplicationCallbackRegistration(i, onBackInvokedCallback)) {
            registerOnBackInvokedCallbackUnchecked(onBackInvokedCallback, i);
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerSystemOnBackInvokedCallback(OnBackInvokedCallback onBackInvokedCallback) {
        registerOnBackInvokedCallbackUnchecked(onBackInvokedCallback, -1);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void unregisterOnBackInvokedCallback(final OnBackInvokedCallback onBackInvokedCallback) {
        synchronized (this.mLock) {
            this.mCallbacks.removeIf(new Predicate() { // from class: android.window.ProxyOnBackInvokedDispatcher$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean equals;
                    equals = ((OnBackInvokedCallback) ((Pair) obj).first).equals(OnBackInvokedCallback.this);
                    return equals;
                }
            });
            OnBackInvokedDispatcher onBackInvokedDispatcher = this.mActualDispatcher;
            if (onBackInvokedDispatcher != null) {
                onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
            }
        }
    }

    private void registerOnBackInvokedCallbackUnchecked(OnBackInvokedCallback onBackInvokedCallback, int i) {
        synchronized (this.mLock) {
            this.mCallbacks.add(Pair.create(onBackInvokedCallback, Integer.valueOf(i)));
            OnBackInvokedDispatcher onBackInvokedDispatcher = this.mActualDispatcher;
            if (onBackInvokedDispatcher != null) {
                if (i == -1) {
                    onBackInvokedDispatcher.registerSystemOnBackInvokedCallback(onBackInvokedCallback);
                } else {
                    onBackInvokedDispatcher.registerOnBackInvokedCallback(i, onBackInvokedCallback);
                }
            }
        }
    }

    private void transferCallbacksToDispatcher() {
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mActualDispatcher;
        if (onBackInvokedDispatcher == null) {
            return;
        }
        ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher = this.mImeDispatcher;
        if (imeOnBackInvokedDispatcher != null) {
            onBackInvokedDispatcher.setImeOnBackInvokedDispatcher(imeOnBackInvokedDispatcher);
        }
        for (Pair<OnBackInvokedCallback, Integer> pair : this.mCallbacks) {
            int intValue = pair.second.intValue();
            if (Flags.predictiveBackPrioritySystemNavigationObserver()) {
                if (intValue >= 0 || intValue == -2) {
                    this.mActualDispatcher.registerOnBackInvokedCallback(intValue, pair.first);
                } else {
                    this.mActualDispatcher.registerSystemOnBackInvokedCallback(pair.first);
                }
            } else if (intValue >= 0) {
                this.mActualDispatcher.registerOnBackInvokedCallback(intValue, pair.first);
            } else {
                this.mActualDispatcher.registerSystemOnBackInvokedCallback(pair.first);
            }
        }
        this.mCallbacks.clear();
        this.mImeDispatcher = null;
    }

    private void clearCallbacksOnDispatcher() {
        if (this.mActualDispatcher == null) {
            return;
        }
        Iterator<Pair<OnBackInvokedCallback, Integer>> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            this.mActualDispatcher.unregisterOnBackInvokedCallback(it.next().first);
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mCallbacks.clear();
            this.mImeDispatcher = null;
        }
    }

    public void setActualDispatcher(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        synchronized (this.mLock) {
            if (onBackInvokedDispatcher == this.mActualDispatcher) {
                return;
            }
            clearCallbacksOnDispatcher();
            this.mActualDispatcher = onBackInvokedDispatcher;
            transferCallbacksToDispatcher();
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void setImeOnBackInvokedDispatcher(ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mActualDispatcher;
        if (onBackInvokedDispatcher != null) {
            onBackInvokedDispatcher.setImeOnBackInvokedDispatcher(imeOnBackInvokedDispatcher);
        } else {
            this.mImeDispatcher = imeOnBackInvokedDispatcher;
        }
    }
}
