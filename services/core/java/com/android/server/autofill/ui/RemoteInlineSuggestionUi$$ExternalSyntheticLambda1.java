package com.android.server.autofill.ui;

import android.content.IntentSender;
import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControlViewHost;

import com.android.internal.view.inline.IInlineContentCallback;
import com.android.server.autofill.Helper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RemoteInlineSuggestionUi$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RemoteInlineSuggestionUi$$ExternalSyntheticLambda1(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((RemoteInlineSuggestionUi) this.f$0).mInlineContentCallback =
                        (IInlineContentCallback) this.f$1;
                break;
            case 1:
                RemoteInlineSuggestionUi.AnonymousClass1 anonymousClass1 =
                        (RemoteInlineSuggestionUi.AnonymousClass1) this.f$0;
                SurfaceControlViewHost.SurfacePackage surfacePackage =
                        (SurfaceControlViewHost.SurfacePackage) this.f$1;
                anonymousClass1.getClass();
                if (Helper.sVerbose) {
                    Slog.v("RemoteInlineSuggestionUi", "Sending refreshed SurfacePackage to IME");
                }
                try {
                    RemoteInlineSuggestionUi remoteInlineSuggestionUi =
                            RemoteInlineSuggestionUi.this;
                    remoteInlineSuggestionUi.mInlineContentCallback.onContent(
                            surfacePackage,
                            remoteInlineSuggestionUi.mActualWidth,
                            remoteInlineSuggestionUi.mActualHeight);
                    RemoteInlineSuggestionUi.this.handleUpdateRefCount(1);
                    break;
                } catch (RemoteException unused) {
                    Slog.w("RemoteInlineSuggestionUi", "RemoteException calling onContent");
                    return;
                }
            default:
                RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl
                        inlineSuggestionUiCallbackImpl =
                                (RemoteInlineSuggestionUi.InlineSuggestionUiCallbackImpl) this.f$0;
                RemoteInlineSuggestionUi.this.mRemoteInlineSuggestionViewConnector
                        .mStartIntentSenderFromClientApp.accept((IntentSender) this.f$1);
                break;
        }
    }
}
