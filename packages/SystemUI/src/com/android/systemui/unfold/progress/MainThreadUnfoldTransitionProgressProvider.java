package com.android.systemui.unfold.progress;

import android.os.Handler;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.progress.MainThreadUnfoldTransitionProgressProvider;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MainThreadUnfoldTransitionProgressProvider implements UnfoldTransitionProgressProvider {
    public final Map listenerMap = Collections.synchronizedMap(new LinkedHashMap());
    public final Handler mainHandler;
    public final UnfoldTransitionProgressProvider rootProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        MainThreadUnfoldTransitionProgressProvider create(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TransitionProgressListerProxy implements UnfoldTransitionProgressProvider.TransitionProgressListener {
        public final UnfoldTransitionProgressProvider.TransitionProgressListener listener;

        public TransitionProgressListerProxy(UnfoldTransitionProgressProvider.TransitionProgressListener transitionProgressListener) {
            this.listener = transitionProgressListener;
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionFinished() {
            MainThreadUnfoldTransitionProgressProvider.this.mainHandler.post(new Runnable() { // from class: com.android.systemui.unfold.progress.MainThreadUnfoldTransitionProgressProvider$TransitionProgressListerProxy$onTransitionFinished$1
                @Override // java.lang.Runnable
                public final void run() {
                    MainThreadUnfoldTransitionProgressProvider.TransitionProgressListerProxy.this.listener.onTransitionFinished();
                }
            });
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionFinishing() {
            MainThreadUnfoldTransitionProgressProvider.this.mainHandler.post(new Runnable() { // from class: com.android.systemui.unfold.progress.MainThreadUnfoldTransitionProgressProvider$TransitionProgressListerProxy$onTransitionFinishing$1
                @Override // java.lang.Runnable
                public final void run() {
                    MainThreadUnfoldTransitionProgressProvider.TransitionProgressListerProxy.this.listener.onTransitionFinishing();
                }
            });
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionProgress(final float f) {
            MainThreadUnfoldTransitionProgressProvider.this.mainHandler.post(new Runnable() { // from class: com.android.systemui.unfold.progress.MainThreadUnfoldTransitionProgressProvider$TransitionProgressListerProxy$onTransitionProgress$1
                @Override // java.lang.Runnable
                public final void run() {
                    MainThreadUnfoldTransitionProgressProvider.TransitionProgressListerProxy.this.listener.onTransitionProgress(f);
                }
            });
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionStarted() {
            MainThreadUnfoldTransitionProgressProvider.this.mainHandler.post(new Runnable() { // from class: com.android.systemui.unfold.progress.MainThreadUnfoldTransitionProgressProvider$TransitionProgressListerProxy$onTransitionStarted$1
                @Override // java.lang.Runnable
                public final void run() {
                    MainThreadUnfoldTransitionProgressProvider.TransitionProgressListerProxy.this.listener.onTransitionStarted();
                }
            });
        }
    }

    public MainThreadUnfoldTransitionProgressProvider(Handler handler, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.mainHandler = handler;
        this.rootProvider = unfoldTransitionProgressProvider;
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        UnfoldTransitionProgressProvider.TransitionProgressListener transitionProgressListener = (UnfoldTransitionProgressProvider.TransitionProgressListener) obj;
        TransitionProgressListerProxy transitionProgressListerProxy = new TransitionProgressListerProxy(transitionProgressListener);
        this.rootProvider.addCallback(transitionProgressListerProxy);
        this.listenerMap.put(transitionProgressListener, transitionProgressListerProxy);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        UnfoldTransitionProgressProvider.TransitionProgressListener transitionProgressListener = (UnfoldTransitionProgressProvider.TransitionProgressListener) this.listenerMap.remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
        if (transitionProgressListener == null) {
            return;
        }
        this.rootProvider.removeCallback(transitionProgressListener);
    }
}
