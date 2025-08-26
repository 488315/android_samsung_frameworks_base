package com.android.systemui.screenshot.scroll;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.view.IScrollCaptureConnection;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.scroll.ScrollCaptureClient;

/* loaded from: classes2.dex */
public final /* synthetic */ class ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda1 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScrollCaptureClient.SessionWrapper f$0;

    public /* synthetic */ ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda1(ScrollCaptureClient.SessionWrapper sessionWrapper, int i) {
        this.$r8$classId = i;
        this.f$0 = sessionWrapper;
    }

    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        switch (this.$r8$classId) {
            case 0:
                ScrollCaptureClient.SessionWrapper sessionWrapper = this.f$0;
                IScrollCaptureConnection iScrollCaptureConnection = sessionWrapper.mConnection;
                if (iScrollCaptureConnection != null && iScrollCaptureConnection.asBinder().isBinderAlive()) {
                    try {
                        sessionWrapper.mTileRequestCompleter = completer;
                        sessionWrapper.mCancellationSignal = sessionWrapper.mConnection.requestImage(sessionWrapper.mRequestRect);
                        ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0 scrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0 = new ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0(sessionWrapper, 1);
                        ProfileInstallReceiver$$ExternalSyntheticLambda0 profileInstallReceiver$$ExternalSyntheticLambda0 = new ProfileInstallReceiver$$ExternalSyntheticLambda0();
                        ResolvableFuture resolvableFuture = completer.cancellationFuture;
                        if (resolvableFuture != null) {
                            resolvableFuture.addListener(scrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0, profileInstallReceiver$$ExternalSyntheticLambda0);
                            break;
                        }
                    } catch (RemoteException e) {
                        completer.setException(e);
                        break;
                    }
                } else {
                    completer.setException(new DeadObjectException("Connection is closed!"));
                    break;
                }
                break;
            default:
                ScrollCaptureClient.SessionWrapper sessionWrapper2 = this.f$0;
                if (!sessionWrapper2.mStarted) {
                    try {
                        sessionWrapper2.mConnection.asBinder().unlinkToDeath(sessionWrapper2, 0);
                        sessionWrapper2.mConnection.close();
                    } catch (RemoteException unused) {
                    }
                    sessionWrapper2.mConnection = null;
                    completer.set(null);
                    break;
                } else {
                    sessionWrapper2.mEndCompleter = completer;
                    try {
                        sessionWrapper2.mConnection.endCapture();
                        break;
                    } catch (RemoteException e2) {
                        completer.setException(e2);
                        break;
                    }
                }
        }
        return "";
        return "IScrollCaptureCallbacks#onImageRequestCompleted";
        return "IScrollCaptureCallbacks#onCaptureEnded";
    }
}
