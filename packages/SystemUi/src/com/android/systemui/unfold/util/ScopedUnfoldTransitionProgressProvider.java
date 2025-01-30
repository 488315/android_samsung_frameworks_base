package com.android.systemui.unfold.util;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScopedUnfoldTransitionProgressProvider implements UnfoldTransitionProgressProvider, UnfoldTransitionProgressProvider.TransitionProgressListener {
    public boolean isReadyToHandleTransition;
    public boolean isTransitionRunning;
    public float lastTransitionProgress;
    public final List listeners;
    public UnfoldTransitionProgressProvider source;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Multi-variable type inference failed */
    public ScopedUnfoldTransitionProgressProvider() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((ArrayList) this.listeners).add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinished() {
        if (this.isReadyToHandleTransition) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinished();
            }
        }
        this.isTransitionRunning = false;
        this.lastTransitionProgress = -1.0f;
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinishing() {
        if (this.isReadyToHandleTransition) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinishing();
            }
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionProgress(float f) {
        if (this.isReadyToHandleTransition) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionProgress(f);
            }
        }
        this.lastTransitionProgress = f;
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        this.isTransitionRunning = true;
        if (this.isReadyToHandleTransition) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
            }
        }
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.listeners).remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void setReadyToHandleTransition(boolean z) {
        if (this.isTransitionRunning) {
            List list = this.listeners;
            if (z) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
                }
                if (!(this.lastTransitionProgress == -1.0f)) {
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        ((UnfoldTransitionProgressProvider.TransitionProgressListener) it2.next()).onTransitionProgress(this.lastTransitionProgress);
                    }
                }
            } else {
                this.isTransitionRunning = false;
                Iterator it3 = list.iterator();
                while (it3.hasNext()) {
                    ((UnfoldTransitionProgressProvider.TransitionProgressListener) it3.next()).onTransitionFinished();
                }
            }
        }
        this.isReadyToHandleTransition = z;
    }

    public ScopedUnfoldTransitionProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.listeners = new ArrayList();
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
