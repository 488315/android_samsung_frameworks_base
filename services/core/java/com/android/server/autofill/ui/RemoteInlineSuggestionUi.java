package com.android.server.autofill.ui;

import android.content.IntentSender;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.autofill.IInlineSuggestionUi;
import android.service.autofill.IInlineSuggestionUiCallback;
import android.service.autofill.ISurfacePackageResultCallback;
import android.util.Slog;
import android.view.SurfaceControlViewHost;

import com.android.internal.view.inline.IInlineContentCallback;
import com.android.server.LocalServices;
import com.android.server.autofill.Helper;
import com.android.server.inputmethod.InputMethodManagerInternal;

public final class RemoteInlineSuggestionUi {
    public int mActualHeight;
    public int mActualWidth;
    public RemoteInlineSuggestionUi$$ExternalSyntheticLambda0 mDelayedReleaseViewRunnable;
    public final Handler mHandler;
    public final int mHeight;
    public IInlineContentCallback mInlineContentCallback;
    public IInlineSuggestionUi mInlineSuggestionUi;
    public final RemoteInlineSuggestionViewConnector mRemoteInlineSuggestionViewConnector;
    public final int mWidth;
    public int mRefCount = 0;
    public boolean mWaitingForUiCreation = false;
    public final InlineSuggestionUiCallbackImpl mInlineSuggestionUiCallback =
            new InlineSuggestionUiCallbackImpl();

    /* renamed from: com.android.server.autofill.ui.RemoteInlineSuggestionUi$1, reason: invalid class name */
    public final class AnonymousClass1 extends ISurfacePackageResultCallback.Stub {
        public AnonymousClass1() {}

        public final void onResult(SurfaceControlViewHost.SurfacePackage surfacePackage) {
            RemoteInlineSuggestionUi.this.mHandler.post(
                    new RemoteInlineSuggestionUi$$ExternalSyntheticLambda1(
                            1, this, surfacePackage));
        }
    }

    public final class InlineSuggestionUiCallbackImpl extends IInlineSuggestionUiCallback.Stub {
        public InlineSuggestionUiCallbackImpl() {}

        public final void onClick() {
            RemoteInlineSuggestionUi remoteInlineSuggestionUi = RemoteInlineSuggestionUi.this;
            remoteInlineSuggestionUi.mHandler.post(
                    new RemoteInlineSuggestionUi$$ExternalSyntheticLambda0(
                            remoteInlineSuggestionUi, 3));
        }

        public final void onContent(
                final IInlineSuggestionUi iInlineSuggestionUi,
                final SurfaceControlViewHost.SurfacePackage surfacePackage,
                final int i,
                final int i2) {
            RemoteInlineSuggestionUi.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.autofill.ui.RemoteInlineSuggestionUi$InlineSuggestionUiCallbackImpl$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl
                                    inlineSuggestionUiCallbackImpl =
                                            RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl
                                                    .this;
                            IInlineSuggestionUi iInlineSuggestionUi2 = iInlineSuggestionUi;
                            SurfaceControlViewHost.SurfacePackage surfacePackage2 = surfacePackage;
                            int i3 = i;
                            int i4 = i2;
                            RemoteInlineSuggestionUi remoteInlineSuggestionUi =
                                    RemoteInlineSuggestionUi.this;
                            remoteInlineSuggestionUi.mInlineSuggestionUi = iInlineSuggestionUi2;
                            remoteInlineSuggestionUi.mRefCount = 0;
                            remoteInlineSuggestionUi.mWaitingForUiCreation = false;
                            remoteInlineSuggestionUi.mActualWidth = i3;
                            remoteInlineSuggestionUi.mActualHeight = i4;
                            if (remoteInlineSuggestionUi.mInlineContentCallback != null) {
                                try {
                                    if (Helper.sVerbose) {
                                        Slog.v(
                                                "RemoteInlineSuggestionUi",
                                                "Sending new UI content to IME");
                                    }
                                    remoteInlineSuggestionUi.handleUpdateRefCount(1);
                                    remoteInlineSuggestionUi.mInlineContentCallback.onContent(
                                            surfacePackage2,
                                            remoteInlineSuggestionUi.mActualWidth,
                                            remoteInlineSuggestionUi.mActualHeight);
                                } catch (RemoteException unused) {
                                    Slog.w(
                                            "RemoteInlineSuggestionUi",
                                            "RemoteException calling onContent");
                                }
                            }
                            if (surfacePackage2 != null) {
                                surfacePackage2.release();
                            }
                            remoteInlineSuggestionUi.mRemoteInlineSuggestionViewConnector
                                    .mOnInflateCallback.run();
                        }
                    });
        }

        public final void onError() {
            RemoteInlineSuggestionUi remoteInlineSuggestionUi = RemoteInlineSuggestionUi.this;
            remoteInlineSuggestionUi.mHandler.post(
                    new RemoteInlineSuggestionUi$$ExternalSyntheticLambda0(
                            remoteInlineSuggestionUi, 5));
        }

        public final void onLongClick() {
            RemoteInlineSuggestionUi remoteInlineSuggestionUi = RemoteInlineSuggestionUi.this;
            remoteInlineSuggestionUi.mHandler.post(
                    new RemoteInlineSuggestionUi$$ExternalSyntheticLambda0(
                            remoteInlineSuggestionUi, 4));
        }

        public final void onStartIntentSender(IntentSender intentSender) {
            RemoteInlineSuggestionUi.this.mHandler.post(
                    new RemoteInlineSuggestionUi$$ExternalSyntheticLambda1(2, this, intentSender));
        }

        public final void onTransferTouchFocusToImeWindow(final IBinder iBinder, final int i) {
            RemoteInlineSuggestionUi.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.autofill.ui.RemoteInlineSuggestionUi$InlineSuggestionUiCallbackImpl$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl
                                    inlineSuggestionUiCallbackImpl =
                                            RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl
                                                    .this;
                            IBinder iBinder2 = iBinder;
                            int i2 = i;
                            RemoteInlineSuggestionViewConnector
                                    remoteInlineSuggestionViewConnector =
                                            RemoteInlineSuggestionUi.this
                                                    .mRemoteInlineSuggestionViewConnector;
                            remoteInlineSuggestionViewConnector.getClass();
                            if (((InputMethodManagerInternal)
                                            LocalServices.getService(
                                                    InputMethodManagerInternal.class))
                                    .transferTouchFocusToImeWindow(
                                            i2,
                                            iBinder2,
                                            remoteInlineSuggestionViewConnector.mUserId)) {
                                return;
                            }
                            Slog.e(
                                    "RemoteInlineSuggestionViewConnector",
                                    "Cannot transfer touch focus from suggestion to IME");
                            remoteInlineSuggestionViewConnector.mOnErrorCallback.run();
                        }
                    });
        }
    }

    public RemoteInlineSuggestionUi(
            RemoteInlineSuggestionViewConnector remoteInlineSuggestionViewConnector,
            int i,
            int i2,
            Handler handler) {
        this.mHandler = handler;
        this.mRemoteInlineSuggestionViewConnector = remoteInlineSuggestionViewConnector;
        this.mWidth = i;
        this.mHeight = i2;
    }

    public final void handleUpdateRefCount(int i) {
        RemoteInlineSuggestionUi$$ExternalSyntheticLambda0
                remoteInlineSuggestionUi$$ExternalSyntheticLambda0 =
                        this.mDelayedReleaseViewRunnable;
        Handler handler = this.mHandler;
        if (remoteInlineSuggestionUi$$ExternalSyntheticLambda0 != null) {
            handler.removeCallbacks(remoteInlineSuggestionUi$$ExternalSyntheticLambda0);
            this.mDelayedReleaseViewRunnable = null;
        }
        int i2 = this.mRefCount + i;
        this.mRefCount = i2;
        if (i2 <= 0) {
            RemoteInlineSuggestionUi$$ExternalSyntheticLambda0
                    remoteInlineSuggestionUi$$ExternalSyntheticLambda02 =
                            new RemoteInlineSuggestionUi$$ExternalSyntheticLambda0(this, 2);
            this.mDelayedReleaseViewRunnable = remoteInlineSuggestionUi$$ExternalSyntheticLambda02;
            handler.postDelayed(remoteInlineSuggestionUi$$ExternalSyntheticLambda02, 200L);
        }
    }
}
