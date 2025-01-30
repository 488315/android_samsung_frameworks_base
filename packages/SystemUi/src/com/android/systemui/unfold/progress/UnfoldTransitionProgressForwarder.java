package com.android.systemui.unfold.progress;

import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldTransitionProgressForwarder extends IUnfoldAnimation$Stub implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public static final String TAG;
    public IUnfoldTransitionListener remoteListener;

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
        TAG = "UnfoldTransitionProgressForwarder";
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinished() {
        try {
            Log.d(TAG, "onTransitionFinished");
            IUnfoldTransitionListener iUnfoldTransitionListener = this.remoteListener;
            if (iUnfoldTransitionListener != null) {
                ((IUnfoldTransitionListener$Stub$Proxy) iUnfoldTransitionListener).onTransitionFinished();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed call onTransitionFinished", e);
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionProgress(float f) {
        try {
            IUnfoldTransitionListener iUnfoldTransitionListener = this.remoteListener;
            if (iUnfoldTransitionListener != null) {
                ((IUnfoldTransitionListener$Stub$Proxy) iUnfoldTransitionListener).onTransitionProgress(f);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed call onTransitionProgress", e);
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        try {
            Log.d(TAG, "onTransitionStarted");
            IUnfoldTransitionListener iUnfoldTransitionListener = this.remoteListener;
            if (iUnfoldTransitionListener != null) {
                ((IUnfoldTransitionListener$Stub$Proxy) iUnfoldTransitionListener).onTransitionStarted();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed call onTransitionStarted", e);
        }
    }
}
