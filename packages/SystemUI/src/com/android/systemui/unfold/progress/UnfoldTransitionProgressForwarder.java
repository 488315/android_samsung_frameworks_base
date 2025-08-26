package com.android.systemui.unfold.progress;

import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class UnfoldTransitionProgressForwarder extends IUnfoldAnimation$Stub implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public static final String TAG;
    public IUnfoldTransitionListener$Stub$Proxy remoteListener;

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
                Parcel parcelObtain = Parcel.obtain(iUnfoldTransitionListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
                    iUnfoldTransitionListener$Stub$Proxy.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
                Parcel parcelObtain = Parcel.obtain(iUnfoldTransitionListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
                    parcelObtain.writeFloat(f);
                    iUnfoldTransitionListener$Stub$Proxy.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
                Parcel parcelObtain = Parcel.obtain(iUnfoldTransitionListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
                    iUnfoldTransitionListener$Stub$Proxy.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed call onTransitionStarted", e);
        }
    }
}
