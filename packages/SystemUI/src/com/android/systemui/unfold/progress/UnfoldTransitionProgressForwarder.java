package com.android.systemui.unfold.progress;

import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UnfoldTransitionProgressForwarder extends IUnfoldAnimation$Stub implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public static final String TAG;
    public IUnfoldTransitionListener$Stub$Proxy remoteListener;

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
                    obtain.recycle();
                } catch (Throwable th) {
                    obtain.recycle();
                    throw th;
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
                    obtain.recycle();
                } catch (Throwable th) {
                    obtain.recycle();
                    throw th;
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
                    obtain.recycle();
                } catch (Throwable th) {
                    obtain.recycle();
                    throw th;
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed call onTransitionStarted", e);
        }
    }
}
