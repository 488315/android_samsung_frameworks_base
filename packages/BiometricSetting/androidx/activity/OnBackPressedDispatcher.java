package androidx.activity;

import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnBackPressedDispatcher.kt */
/* loaded from: classes.dex */
public final class OnBackPressedDispatcher {
    private boolean backInvokedCallbackRegistered;
    private final Runnable fallbackOnBackPressed;
    private OnBackInvokedDispatcher invokedDispatcher;
    private OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0 onBackInvokedCallback;
    private final ArrayDeque<OnBackPressedCallback> onBackPressedCallbacks = new ArrayDeque<>();
    private Function0<Unit> enabledChangedCallback = new Function0<Unit>() { // from class: androidx.activity.OnBackPressedDispatcher.1
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Unit invoke() {
            OnBackPressedDispatcher.this.updateBackInvokedCallbackState$activity_release();
            return Unit.INSTANCE;
        }
    };

    /* compiled from: OnBackPressedDispatcher.kt */
    private final class LifecycleOnBackPressedCancellable implements LifecycleEventObserver, Cancellable {
        private Cancellable currentCancellable;
        private final Lifecycle lifecycle;
        private final OnBackPressedCallback onBackPressedCallback;
        final /* synthetic */ OnBackPressedDispatcher this$0;

        public LifecycleOnBackPressedCancellable(OnBackPressedDispatcher onBackPressedDispatcher, Lifecycle lifecycle, OnBackPressedCallback onBackPressedCallback) {
            Intrinsics.checkNotNullParameter(onBackPressedCallback, "onBackPressedCallback");
            this.this$0 = onBackPressedDispatcher;
            this.lifecycle = lifecycle;
            this.onBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        @Override // androidx.activity.Cancellable
        public final void cancel() {
            this.lifecycle.removeObserver(this);
            this.onBackPressedCallback.removeCancellable(this);
            Cancellable cancellable = this.currentCancellable;
            if (cancellable != null) {
                cancellable.cancel();
            }
            this.currentCancellable = null;
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                this.currentCancellable = this.this$0.addCancellableCallback$activity_release(this.onBackPressedCallback);
                return;
            }
            if (event != Lifecycle.Event.ON_STOP) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    cancel();
                }
            } else {
                Cancellable cancellable = this.currentCancellable;
                if (cancellable != null) {
                    ((OnBackPressedCancellable) cancellable).cancel();
                }
            }
        }
    }

    /* compiled from: OnBackPressedDispatcher.kt */
    private final class OnBackPressedCancellable implements Cancellable {
        private final OnBackPressedCallback onBackPressedCallback;
        final /* synthetic */ OnBackPressedDispatcher this$0;

        public OnBackPressedCancellable(OnBackPressedDispatcher onBackPressedDispatcher, OnBackPressedCallback onBackPressedCallback) {
            Intrinsics.checkNotNullParameter(onBackPressedCallback, "onBackPressedCallback");
            this.this$0 = onBackPressedDispatcher;
            this.onBackPressedCallback = onBackPressedCallback;
        }

        @Override // androidx.activity.Cancellable
        public final void cancel() {
            OnBackPressedDispatcher onBackPressedDispatcher = this.this$0;
            ArrayDeque arrayDeque = onBackPressedDispatcher.onBackPressedCallbacks;
            OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
            arrayDeque.remove(onBackPressedCallback);
            onBackPressedCallback.removeCancellable(this);
            onBackPressedCallback.setEnabledChangedCallback$activity_release(null);
            onBackPressedDispatcher.updateBackInvokedCallbackState$activity_release();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.activity.OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0] */
    public OnBackPressedDispatcher(Runnable runnable) {
        this.fallbackOnBackPressed = runnable;
        final Function0<Unit> function0 = new Function0<Unit>() { // from class: androidx.activity.OnBackPressedDispatcher.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Unit invoke() {
                OnBackPressedDispatcher.this.onBackPressed();
                return Unit.INSTANCE;
            }
        };
        this.onBackInvokedCallback = new OnBackInvokedCallback() { // from class: androidx.activity.OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                Function0 onBackInvoked = Function0.this;
                Intrinsics.checkNotNullParameter(onBackInvoked, "$onBackInvoked");
                onBackInvoked.invoke();
            }
        };
    }

    public final void addCallback(LifecycleOwner lifecycleOwner, OnBackPressedCallback onBackPressedCallback) {
        Intrinsics.checkNotNullParameter(onBackPressedCallback, "onBackPressedCallback");
        LifecycleRegistry lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        onBackPressedCallback.addCancellable(new LifecycleOnBackPressedCancellable(this, lifecycle, onBackPressedCallback));
        updateBackInvokedCallbackState$activity_release();
        onBackPressedCallback.setEnabledChangedCallback$activity_release(this.enabledChangedCallback);
    }

    public final Cancellable addCancellableCallback$activity_release(OnBackPressedCallback onBackPressedCallback) {
        Intrinsics.checkNotNullParameter(onBackPressedCallback, "onBackPressedCallback");
        this.onBackPressedCallbacks.addLast(onBackPressedCallback);
        OnBackPressedCancellable onBackPressedCancellable = new OnBackPressedCancellable(this, onBackPressedCallback);
        onBackPressedCallback.addCancellable(onBackPressedCancellable);
        updateBackInvokedCallbackState$activity_release();
        onBackPressedCallback.setEnabledChangedCallback$activity_release(this.enabledChangedCallback);
        return onBackPressedCancellable;
    }

    public final void onBackPressed() {
        OnBackPressedCallback onBackPressedCallback;
        ArrayDeque<OnBackPressedCallback> arrayDeque = this.onBackPressedCallbacks;
        ListIterator<OnBackPressedCallback> listIterator = arrayDeque.listIterator(arrayDeque.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                onBackPressedCallback = null;
                break;
            } else {
                onBackPressedCallback = listIterator.previous();
                if (onBackPressedCallback.isEnabled()) {
                    break;
                }
            }
        }
        OnBackPressedCallback onBackPressedCallback2 = onBackPressedCallback;
        if (onBackPressedCallback2 != null) {
            onBackPressedCallback2.handleOnBackPressed();
            return;
        }
        Runnable runnable = this.fallbackOnBackPressed;
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void setOnBackInvokedDispatcher(OnBackInvokedDispatcher invoker) {
        Intrinsics.checkNotNullParameter(invoker, "invoker");
        this.invokedDispatcher = invoker;
        updateBackInvokedCallbackState$activity_release();
    }

    public final void updateBackInvokedCallbackState$activity_release() {
        boolean z;
        ArrayDeque<OnBackPressedCallback> arrayDeque = this.onBackPressedCallbacks;
        if (!(arrayDeque instanceof Collection) || !arrayDeque.isEmpty()) {
            Iterator<OnBackPressedCallback> it = arrayDeque.iterator();
            while (it.hasNext()) {
                if (it.next().isEnabled()) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.invokedDispatcher;
        OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0 onBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0 = this.onBackInvokedCallback;
        if (onBackInvokedDispatcher == null || onBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0 == null) {
            return;
        }
        if (z && !this.backInvokedCallbackRegistered) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(0, onBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0);
            this.backInvokedCallbackRegistered = true;
        } else {
            if (z || !this.backInvokedCallbackRegistered) {
                return;
            }
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0);
            this.backInvokedCallbackRegistered = false;
        }
    }
}
