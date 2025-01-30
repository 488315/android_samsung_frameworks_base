package androidx.activity;

import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.core.util.Consumer;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import java.util.ArrayDeque;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class OnBackPressedDispatcher {
    public boolean mBackInvokedCallbackRegistered;
    public final OnBackPressedDispatcher$$ExternalSyntheticLambda0 mEnabledConsumer;
    public final Runnable mFallbackOnBackPressed;
    public OnBackInvokedDispatcher mInvokedDispatcher;
    public final OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0 mOnBackInvokedCallback;
    public final ArrayDeque mOnBackPressedCallbacks;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class LifecycleOnBackPressedCancellable implements LifecycleEventObserver, Cancellable {
        public OnBackPressedCancellable mCurrentCancellable;
        public final Lifecycle mLifecycle;
        public final OnBackPressedCallback mOnBackPressedCallback;

        public LifecycleOnBackPressedCancellable(Lifecycle lifecycle, OnBackPressedCallback onBackPressedCallback) {
            this.mLifecycle = lifecycle;
            this.mOnBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        @Override // androidx.activity.Cancellable
        public final void cancel() {
            this.mLifecycle.removeObserver(this);
            this.mOnBackPressedCallback.cancellables.remove(this);
            OnBackPressedCancellable onBackPressedCancellable = this.mCurrentCancellable;
            if (onBackPressedCancellable != null) {
                onBackPressedCancellable.cancel();
                this.mCurrentCancellable = null;
            }
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                OnBackPressedDispatcher onBackPressedDispatcher = OnBackPressedDispatcher.this;
                ArrayDeque arrayDeque = onBackPressedDispatcher.mOnBackPressedCallbacks;
                OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
                arrayDeque.add(onBackPressedCallback);
                OnBackPressedCancellable onBackPressedCancellable = onBackPressedDispatcher.new OnBackPressedCancellable(onBackPressedCallback);
                onBackPressedCallback.cancellables.add(onBackPressedCancellable);
                onBackPressedDispatcher.updateBackInvokedCallbackState();
                onBackPressedCallback.enabledConsumer = onBackPressedDispatcher.mEnabledConsumer;
                this.mCurrentCancellable = onBackPressedCancellable;
                return;
            }
            if (event != Lifecycle.Event.ON_STOP) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    cancel();
                }
            } else {
                OnBackPressedCancellable onBackPressedCancellable2 = this.mCurrentCancellable;
                if (onBackPressedCancellable2 != null) {
                    onBackPressedCancellable2.cancel();
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnBackPressedCancellable implements Cancellable {
        public final OnBackPressedCallback mOnBackPressedCallback;

        public OnBackPressedCancellable(OnBackPressedCallback onBackPressedCallback) {
            this.mOnBackPressedCallback = onBackPressedCallback;
        }

        @Override // androidx.activity.Cancellable
        public final void cancel() {
            OnBackPressedDispatcher onBackPressedDispatcher = OnBackPressedDispatcher.this;
            ArrayDeque arrayDeque = onBackPressedDispatcher.mOnBackPressedCallbacks;
            OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
            arrayDeque.remove(onBackPressedCallback);
            onBackPressedCallback.cancellables.remove(this);
            onBackPressedCallback.enabledConsumer = null;
            onBackPressedDispatcher.updateBackInvokedCallbackState();
        }
    }

    public OnBackPressedDispatcher() {
        this(null);
    }

    public final void addCallback(LifecycleOwner lifecycleOwner, FragmentManager.C02301 c02301) {
        LifecycleRegistry lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.mState == Lifecycle.State.DESTROYED) {
            return;
        }
        c02301.cancellables.add(new LifecycleOnBackPressedCancellable(lifecycle, c02301));
        updateBackInvokedCallbackState();
        c02301.enabledConsumer = this.mEnabledConsumer;
    }

    public final void onBackPressed() {
        Iterator descendingIterator = this.mOnBackPressedCallbacks.descendingIterator();
        while (descendingIterator.hasNext()) {
            OnBackPressedCallback onBackPressedCallback = (OnBackPressedCallback) descendingIterator.next();
            if (onBackPressedCallback.isEnabled) {
                onBackPressedCallback.handleOnBackPressed();
                return;
            }
        }
        Runnable runnable = this.mFallbackOnBackPressed;
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void updateBackInvokedCallbackState() {
        boolean z;
        Iterator descendingIterator = this.mOnBackPressedCallbacks.descendingIterator();
        while (true) {
            if (!descendingIterator.hasNext()) {
                z = false;
                break;
            } else if (((OnBackPressedCallback) descendingIterator.next()).isEnabled) {
                z = true;
                break;
            }
        }
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mInvokedDispatcher;
        if (onBackInvokedDispatcher != null) {
            OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0 onBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0 = this.mOnBackInvokedCallback;
            if (z && !this.mBackInvokedCallbackRegistered) {
                onBackInvokedDispatcher.registerOnBackInvokedCallback(0, onBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0);
                this.mBackInvokedCallbackRegistered = true;
            } else {
                if (z || !this.mBackInvokedCallbackRegistered) {
                    return;
                }
                onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0);
                this.mBackInvokedCallbackRegistered = false;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.activity.OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticLambda1] */
    public OnBackPressedDispatcher(Runnable runnable) {
        this.mOnBackPressedCallbacks = new ArrayDeque();
        this.mBackInvokedCallbackRegistered = false;
        this.mFallbackOnBackPressed = runnable;
        this.mEnabledConsumer = new Consumer() { // from class: androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                OnBackPressedDispatcher.this.updateBackInvokedCallbackState();
            }
        };
        final ?? r2 = new Runnable() { // from class: androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                OnBackPressedDispatcher.this.onBackPressed();
            }
        };
        this.mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: androidx.activity.OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                r2.run();
            }
        };
    }
}
