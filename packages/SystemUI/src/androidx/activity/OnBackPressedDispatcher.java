package androidx.activity;

import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.core.util.Consumer;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.Iterator;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class OnBackPressedDispatcher {
    public boolean backInvokedCallbackRegistered;
    public final Runnable fallbackOnBackPressed;
    public boolean hasEnabledCallbacks;
    public OnBackPressedCallback inProgressCallback;
    public OnBackInvokedDispatcher invokedDispatcher;
    public final OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1 onBackInvokedCallback;
    public final ArrayDeque onBackPressedCallbacks;
    public final Consumer onHasEnabledCallbacksChanged;

    public final class Api33Impl {
        public static final Api33Impl INSTANCE = new Api33Impl();

        private Api33Impl() {
        }
    }

    public final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();

        private Api34Impl() {
        }
    }

    public final class LifecycleOnBackPressedCancellable implements LifecycleEventObserver, Cancellable {
        public OnBackPressedCancellable currentCancellable;
        public final Lifecycle lifecycle;
        public final OnBackPressedCallback onBackPressedCallback;

        public LifecycleOnBackPressedCancellable(Lifecycle lifecycle, OnBackPressedCallback onBackPressedCallback) {
            this.lifecycle = lifecycle;
            this.onBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        @Override // androidx.activity.Cancellable
        public final void cancel() {
            this.lifecycle.removeObserver(this);
            this.onBackPressedCallback.cancellables.remove(this);
            OnBackPressedCancellable onBackPressedCancellable = this.currentCancellable;
            if (onBackPressedCancellable != null) {
                onBackPressedCancellable.cancel();
            }
            this.currentCancellable = null;
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                OnBackPressedDispatcher onBackPressedDispatcher = OnBackPressedDispatcher.this;
                ArrayDeque arrayDeque = onBackPressedDispatcher.onBackPressedCallbacks;
                OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
                arrayDeque.addLast(onBackPressedCallback);
                OnBackPressedCancellable onBackPressedCancellable = onBackPressedDispatcher.new OnBackPressedCancellable(onBackPressedCallback);
                onBackPressedCallback.cancellables.add(onBackPressedCancellable);
                onBackPressedDispatcher.updateEnabledCallbacks();
                onBackPressedCallback.enabledChangedCallback = new OnBackPressedDispatcher$addCancellableCallback$1(onBackPressedDispatcher);
                this.currentCancellable = onBackPressedCancellable;
                return;
            }
            if (event != Lifecycle.Event.ON_STOP) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    cancel();
                }
            } else {
                OnBackPressedCancellable onBackPressedCancellable2 = this.currentCancellable;
                if (onBackPressedCancellable2 != null) {
                    onBackPressedCancellable2.cancel();
                }
            }
        }
    }

    public final class OnBackPressedCancellable implements Cancellable {
        public final OnBackPressedCallback onBackPressedCallback;

        public OnBackPressedCancellable(OnBackPressedCallback onBackPressedCallback) {
            this.onBackPressedCallback = onBackPressedCallback;
        }

        /* JADX WARN: Type inference failed for: r4v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.FunctionReferenceImpl] */
        @Override // androidx.activity.Cancellable
        public final void cancel() {
            OnBackPressedDispatcher onBackPressedDispatcher = OnBackPressedDispatcher.this;
            ArrayDeque arrayDeque = onBackPressedDispatcher.onBackPressedCallbacks;
            OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
            arrayDeque.remove(onBackPressedCallback);
            if (Intrinsics.areEqual(onBackPressedDispatcher.inProgressCallback, onBackPressedCallback)) {
                onBackPressedCallback.handleOnBackCancelled();
                onBackPressedDispatcher.inProgressCallback = null;
            }
            onBackPressedCallback.cancellables.remove(this);
            ?? r4 = onBackPressedCallback.enabledChangedCallback;
            if (r4 != 0) {
                r4.invoke();
            }
            onBackPressedCallback.enabledChangedCallback = null;
        }
    }

    /* renamed from: androidx.activity.OnBackPressedDispatcher$addCallback$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C06711 extends FunctionReferenceImpl implements Function0 {
        public C06711(Object obj) {
            super(0, obj, OnBackPressedDispatcher.class, "updateEnabledCallbacks", "updateEnabledCallbacks()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ((OnBackPressedDispatcher) this.receiver).updateEnabledCallbacks();
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public OnBackPressedDispatcher() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final void addCallback(LifecycleOwner lifecycleOwner, OnBackPressedCallback onBackPressedCallback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        onBackPressedCallback.cancellables.add(new LifecycleOnBackPressedCancellable(lifecycle, onBackPressedCallback));
        updateEnabledCallbacks();
        onBackPressedCallback.enabledChangedCallback = new C06711(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.Object] */
    public final void onBackCancelled() {
        OnBackPressedCallback onBackPressedCallbackPrevious;
        OnBackPressedCallback onBackPressedCallback = this.inProgressCallback;
        if (onBackPressedCallback == null) {
            ArrayDeque arrayDeque = this.onBackPressedCallbacks;
            ListIterator listIterator = arrayDeque.listIterator(arrayDeque.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    onBackPressedCallbackPrevious = 0;
                    break;
                } else {
                    onBackPressedCallbackPrevious = listIterator.previous();
                    if (((OnBackPressedCallback) onBackPressedCallbackPrevious).isEnabled) {
                        break;
                    }
                }
            }
            onBackPressedCallback = onBackPressedCallbackPrevious;
        }
        this.inProgressCallback = null;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackCancelled();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.Object] */
    public final void onBackPressed() {
        OnBackPressedCallback onBackPressedCallbackPrevious;
        OnBackPressedCallback onBackPressedCallback = this.inProgressCallback;
        if (onBackPressedCallback == null) {
            ArrayDeque arrayDeque = this.onBackPressedCallbacks;
            ListIterator listIterator = arrayDeque.listIterator(arrayDeque.getSize());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    onBackPressedCallbackPrevious = 0;
                    break;
                } else {
                    onBackPressedCallbackPrevious = listIterator.previous();
                    if (((OnBackPressedCallback) onBackPressedCallbackPrevious).isEnabled) {
                        break;
                    }
                }
            }
            onBackPressedCallback = onBackPressedCallbackPrevious;
        }
        this.inProgressCallback = null;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackPressed();
            return;
        }
        Runnable runnable = this.fallbackOnBackPressed;
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void setOnBackInvokedDispatcher(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        this.invokedDispatcher = onBackInvokedDispatcher;
        updateBackInvokedCallbackState(this.hasEnabledCallbacks);
    }

    public final void updateBackInvokedCallbackState(boolean z) {
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.invokedDispatcher;
        OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1 onBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1 = this.onBackInvokedCallback;
        if (onBackInvokedDispatcher == null || onBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1 == null) {
            return;
        }
        if (z && !this.backInvokedCallbackRegistered) {
            Api33Impl.INSTANCE.getClass();
            onBackInvokedDispatcher.registerOnBackInvokedCallback(0, onBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1);
            this.backInvokedCallbackRegistered = true;
        } else {
            if (z || !this.backInvokedCallbackRegistered) {
                return;
            }
            Api33Impl.INSTANCE.getClass();
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1);
            this.backInvokedCallbackRegistered = false;
        }
    }

    public final void updateEnabledCallbacks() {
        boolean z = this.hasEnabledCallbacks;
        boolean z2 = false;
        ArrayDeque arrayDeque = this.onBackPressedCallbacks;
        if (arrayDeque == null || !arrayDeque.isEmpty()) {
            Iterator<E> it = arrayDeque.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (((OnBackPressedCallback) it.next()).isEnabled) {
                    z2 = true;
                    break;
                }
            }
        }
        this.hasEnabledCallbacks = z2;
        if (z2 != z) {
            Consumer consumer = this.onHasEnabledCallbacksChanged;
            if (consumer != null) {
                consumer.accept(Boolean.valueOf(z2));
            }
            updateBackInvokedCallbackState(z2);
        }
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [androidx.activity.OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1] */
    public OnBackPressedDispatcher(Runnable runnable, Consumer consumer) {
        this.fallbackOnBackPressed = runnable;
        this.onHasEnabledCallbacksChanged = consumer;
        this.onBackPressedCallbacks = new ArrayDeque();
        Api34Impl api34Impl = Api34Impl.INSTANCE;
        final Function1 function1 = new Function1() { // from class: androidx.activity.OnBackPressedDispatcher.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object objPrevious;
                OnBackPressedDispatcher onBackPressedDispatcher = OnBackPressedDispatcher.this;
                ArrayDeque arrayDeque = onBackPressedDispatcher.onBackPressedCallbacks;
                ListIterator listIterator = arrayDeque.listIterator(arrayDeque.getSize());
                while (true) {
                    if (!listIterator.hasPrevious()) {
                        objPrevious = null;
                        break;
                    }
                    objPrevious = listIterator.previous();
                    if (((OnBackPressedCallback) objPrevious).isEnabled) {
                        break;
                    }
                }
                OnBackPressedCallback onBackPressedCallback = (OnBackPressedCallback) objPrevious;
                if (onBackPressedDispatcher.inProgressCallback != null) {
                    onBackPressedDispatcher.onBackCancelled();
                }
                onBackPressedDispatcher.inProgressCallback = onBackPressedCallback;
                if (onBackPressedCallback != null) {
                    onBackPressedCallback.handleOnBackStarted();
                }
                return Unit.INSTANCE;
            }
        };
        final Function1 function12 = new Function1() { // from class: androidx.activity.OnBackPressedDispatcher.2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object objPrevious;
                BackEventCompat backEventCompat = (BackEventCompat) obj;
                OnBackPressedDispatcher onBackPressedDispatcher = OnBackPressedDispatcher.this;
                OnBackPressedCallback onBackPressedCallback = onBackPressedDispatcher.inProgressCallback;
                if (onBackPressedCallback == null) {
                    ArrayDeque arrayDeque = onBackPressedDispatcher.onBackPressedCallbacks;
                    ListIterator listIterator = arrayDeque.listIterator(arrayDeque.getSize());
                    while (true) {
                        if (!listIterator.hasPrevious()) {
                            objPrevious = null;
                            break;
                        }
                        objPrevious = listIterator.previous();
                        if (((OnBackPressedCallback) objPrevious).isEnabled) {
                            break;
                        }
                    }
                    onBackPressedCallback = (OnBackPressedCallback) objPrevious;
                }
                if (onBackPressedCallback != null) {
                    onBackPressedCallback.handleOnBackProgressed(backEventCompat);
                }
                return Unit.INSTANCE;
            }
        };
        final Function0 function0 = new Function0() { // from class: androidx.activity.OnBackPressedDispatcher.3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                OnBackPressedDispatcher.this.onBackPressed();
                return Unit.INSTANCE;
            }
        };
        final Function0 function02 = new Function0() { // from class: androidx.activity.OnBackPressedDispatcher.4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                OnBackPressedDispatcher.this.onBackCancelled();
                return Unit.INSTANCE;
            }
        };
        api34Impl.getClass();
        this.onBackInvokedCallback = new OnBackAnimationCallback() { // from class: androidx.activity.OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1
            @Override // android.window.OnBackAnimationCallback
            public final void onBackCancelled() {
                function02.invoke();
            }

            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                function0.invoke();
            }

            @Override // android.window.OnBackAnimationCallback
            public final void onBackProgressed(BackEvent backEvent) {
                function12.mo781invoke(new BackEventCompat(backEvent));
            }

            @Override // android.window.OnBackAnimationCallback
            public final void onBackStarted(BackEvent backEvent) {
                function1.mo781invoke(new BackEventCompat(backEvent));
            }
        };
    }

    public OnBackPressedDispatcher(Runnable runnable) {
        this(runnable, null);
    }

    public /* synthetic */ OnBackPressedDispatcher(Runnable runnable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : runnable);
    }
}
