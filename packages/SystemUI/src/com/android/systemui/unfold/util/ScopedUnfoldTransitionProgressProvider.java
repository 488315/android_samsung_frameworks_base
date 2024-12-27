package com.android.systemui.unfold.util;

import android.os.Handler;
import android.os.Looper;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ScopedUnfoldTransitionProgressProvider implements UnfoldTransitionProgressProvider, UnfoldTransitionProgressProvider.TransitionProgressListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isReadyToHandleTransition;
    public boolean isTransitionRunning;
    public float lastTransitionProgress;
    public final CopyOnWriteArrayList listeners;
    public final Object lock;
    public Handler progressHandler;
    public final UnfoldTransitionProgressProvider source;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ScopedUnfoldTransitionProgressProvider() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        this.listeners.add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void assertInProgressThread() {
        Handler handler = this.progressHandler;
        if (handler == null) {
            Looper myLooper = Looper.myLooper();
            if (myLooper == null) {
                throw new IllegalStateException("This thread is expected to have a looper.".toString());
            }
            this.progressHandler = new Handler(myLooper);
            return;
        }
        if (handler.getLooper().isCurrentThread()) {
            return;
        }
        throw new IllegalStateException(StringsKt__IndentKt.trimMargin$default("Receiving unfold transition callback from different threads.\n                    |Current: " + Thread.currentThread() + "\n                    |expected: " + handler.getLooper().getThread()).toString());
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinished() {
        assertInProgressThread();
        synchronized (this.lock) {
            try {
                if (this.isReadyToHandleTransition) {
                    Iterator it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinished();
                    }
                }
                this.isTransitionRunning = false;
                this.lastTransitionProgress = -1.0f;
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinishing() {
        assertInProgressThread();
        synchronized (this.lock) {
            try {
                if (this.isReadyToHandleTransition) {
                    Iterator it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinishing();
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionProgress(float f) {
        assertInProgressThread();
        synchronized (this.lock) {
            try {
                if (this.isReadyToHandleTransition) {
                    Iterator it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionProgress(f);
                    }
                }
                this.lastTransitionProgress = f;
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        assertInProgressThread();
        synchronized (this.lock) {
            try {
                this.isTransitionRunning = true;
                if (this.isReadyToHandleTransition) {
                    Iterator it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        this.listeners.remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void setReadyToHandleTransition(boolean z) {
        synchronized (this.lock) {
            try {
                this.isReadyToHandleTransition = z;
                Handler handler = this.progressHandler;
                if (handler != null) {
                    final Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider$setReadyToHandleTransition$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = ScopedUnfoldTransitionProgressProvider.this;
                            int i = ScopedUnfoldTransitionProgressProvider.$r8$clinit;
                            scopedUnfoldTransitionProgressProvider.assertInProgressThread();
                            synchronized (scopedUnfoldTransitionProgressProvider.lock) {
                                try {
                                    if (scopedUnfoldTransitionProgressProvider.isTransitionRunning) {
                                        if (scopedUnfoldTransitionProgressProvider.isReadyToHandleTransition) {
                                            Iterator it = scopedUnfoldTransitionProgressProvider.listeners.iterator();
                                            while (it.hasNext()) {
                                                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
                                            }
                                            if (scopedUnfoldTransitionProgressProvider.lastTransitionProgress != -1.0f) {
                                                Iterator it2 = scopedUnfoldTransitionProgressProvider.listeners.iterator();
                                                while (it2.hasNext()) {
                                                    ((UnfoldTransitionProgressProvider.TransitionProgressListener) it2.next()).onTransitionProgress(scopedUnfoldTransitionProgressProvider.lastTransitionProgress);
                                                }
                                            }
                                        } else {
                                            scopedUnfoldTransitionProgressProvider.isTransitionRunning = false;
                                            Iterator it3 = scopedUnfoldTransitionProgressProvider.listeners.iterator();
                                            while (it3.hasNext()) {
                                                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it3.next()).onTransitionFinished();
                                            }
                                        }
                                        Unit unit = Unit.INSTANCE;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    if (handler.getLooper().isCurrentThread()) {
                        function0.invoke();
                    } else {
                        handler.post(new Runnable() { // from class: com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider$sam$java_lang_Runnable$0
                            @Override // java.lang.Runnable
                            public final /* synthetic */ void run() {
                                Function0.this.invoke();
                            }
                        });
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ScopedUnfoldTransitionProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.listeners = new CopyOnWriteArrayList();
        this.lock = new Object();
        this.lastTransitionProgress = -1.0f;
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider2 = this.source;
        if (unfoldTransitionProgressProvider2 != null) {
            unfoldTransitionProgressProvider2.removeCallback(this);
        }
        if (unfoldTransitionProgressProvider == null) {
            this.source = null;
        } else {
            this.source = unfoldTransitionProgressProvider;
            unfoldTransitionProgressProvider.addCallback(this);
        }
    }

    public /* synthetic */ ScopedUnfoldTransitionProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : unfoldTransitionProgressProvider);
    }
}
