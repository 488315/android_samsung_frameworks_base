package com.android.systemui.unfold.progress;

import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UnfoldTransitionProgressForwarder extends IUnfoldAnimation$Stub implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public static final String TAG;
    public IUnfoldTransitionListener$Stub$Proxy remoteListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
            IUnfoldTransitionListener$Stub$Proxy iUnfoldTransitionListener$Stub$Proxy = this.remoteListener;
            if (iUnfoldTransitionListener$Stub$Proxy != null) {
                Parcel obtain = Parcel.obtain(iUnfoldTransitionListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
                    iUnfoldTransitionListener$Stub$Proxy.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed call onTransitionFinished", e);
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionProgress(float f) {
        try {
            IUnfoldTransitionListener$Stub$Proxy iUnfoldTransitionListener$Stub$Proxy = this.remoteListener;
            if (iUnfoldTransitionListener$Stub$Proxy != null) {
                Parcel obtain = Parcel.obtain(iUnfoldTransitionListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
                    obtain.writeFloat(f);
                    iUnfoldTransitionListener$Stub$Proxy.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed call onTransitionProgress", e);
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        try {
            Log.d(TAG, "onTransitionStarted");
            IUnfoldTransitionListener$Stub$Proxy iUnfoldTransitionListener$Stub$Proxy = this.remoteListener;
            if (iUnfoldTransitionListener$Stub$Proxy != null) {
                Parcel obtain = Parcel.obtain(iUnfoldTransitionListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
                    iUnfoldTransitionListener$Stub$Proxy.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed call onTransitionStarted", e);
        }
    }
}
