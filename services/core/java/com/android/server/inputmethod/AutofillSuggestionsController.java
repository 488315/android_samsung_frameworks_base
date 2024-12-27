package com.android.server.inputmethod;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestionsRequest;

import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.IInlineSuggestionsResponseCallback;
import com.android.internal.inputmethod.InlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.server.autofill.AutofillInlineSuggestionsRequestSession;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AutofillSuggestionsController {
    public final InputMethodBindingController mBindingController;
    public IBinder mCurHostInputToken;
    public InlineSuggestionsRequestCallback mInlineSuggestionsRequestCallback;
    public CreateInlineSuggestionsRequest mPendingInlineSuggestionsRequest;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CreateInlineSuggestionsRequest {
        public final InlineSuggestionsRequestCallback mCallback;
        public final String mPackageName;
        public final InlineSuggestionsRequestInfo mRequestInfo;

        public CreateInlineSuggestionsRequest(
                InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo,
                AutofillInlineSuggestionsRequestSession.InlineSuggestionsRequestCallbackImpl
                        inlineSuggestionsRequestCallbackImpl,
                String str) {
            this.mRequestInfo = inlineSuggestionsRequestInfo;
            this.mCallback = inlineSuggestionsRequestCallbackImpl;
            this.mPackageName = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InlineSuggestionsRequestCallbackDecorator
            extends IInlineSuggestionsRequestCallback.Stub {
        public final InlineSuggestionsRequestCallback mCallback;
        public final int mImeDisplayId;
        public final String mImePackageName;
        public final IBinder mImeToken;

        public InlineSuggestionsRequestCallbackDecorator(
                AutofillInlineSuggestionsRequestSession.InlineSuggestionsRequestCallbackImpl
                        inlineSuggestionsRequestCallbackImpl,
                String str,
                int i,
                IBinder iBinder) {
            this.mCallback = inlineSuggestionsRequestCallbackImpl;
            this.mImePackageName = str;
            this.mImeDisplayId = i;
            this.mImeToken = iBinder;
        }

        public final void onInlineSuggestionsRequest(
                InlineSuggestionsRequest inlineSuggestionsRequest,
                IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) {
            if (!this.mImePackageName.equals(inlineSuggestionsRequest.getHostPackageName())) {
                StringBuilder sb = new StringBuilder("Host package name in the provide request=[");
                sb.append(inlineSuggestionsRequest.getHostPackageName());
                sb.append("] doesn't match the IME package name=[");
                throw new SecurityException(
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                sb, this.mImePackageName, "]."));
            }
            inlineSuggestionsRequest.setHostDisplayId(this.mImeDisplayId);
            synchronized (ImfLock.class) {
                try {
                    AutofillSuggestionsController autofillSuggestionsController =
                            AutofillSuggestionsController.this;
                    if (this.mImeToken
                            == autofillSuggestionsController.mBindingController.mCurToken) {
                        autofillSuggestionsController.mCurHostInputToken =
                                inlineSuggestionsRequest.getHostInputToken();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mCallback.onInlineSuggestionsRequest(
                    inlineSuggestionsRequest, iInlineSuggestionsResponseCallback);
        }

        public final void onInlineSuggestionsSessionInvalidated() {
            this.mCallback.onInlineSuggestionsSessionInvalidated();
        }

        public final void onInlineSuggestionsUnsupported() {
            this.mCallback.onInlineSuggestionsUnsupported();
        }

        public final void onInputMethodFinishInput() {
            this.mCallback.onInputMethodFinishInput();
        }

        public final void onInputMethodFinishInputView() {
            this.mCallback.onInputMethodFinishInputView();
        }

        public final void onInputMethodShowInputRequested(boolean z) {
            this.mCallback.onInputMethodShowInputRequested(z);
        }

        public final void onInputMethodStartInput(AutofillId autofillId) {
            this.mCallback.onInputMethodStartInput(autofillId);
        }

        public final void onInputMethodStartInputView() {
            this.mCallback.onInputMethodStartInputView();
        }
    }

    public AutofillSuggestionsController(
            InputMethodBindingController inputMethodBindingController) {
        this.mBindingController = inputMethodBindingController;
    }

    public final void performOnCreateInlineSuggestionsRequest() {
        if (this.mPendingInlineSuggestionsRequest == null) {
            return;
        }
        InputMethodBindingController inputMethodBindingController = this.mBindingController;
        IInputMethodInvoker iInputMethodInvoker = inputMethodBindingController.mCurMethod;
        if (iInputMethodInvoker != null) {
            CreateInlineSuggestionsRequest createInlineSuggestionsRequest =
                    this.mPendingInlineSuggestionsRequest;
            InlineSuggestionsRequestCallback inlineSuggestionsRequestCallback =
                    createInlineSuggestionsRequest.mCallback;
            InlineSuggestionsRequestCallbackDecorator inlineSuggestionsRequestCallbackDecorator =
                    new InlineSuggestionsRequestCallbackDecorator(
                            (AutofillInlineSuggestionsRequestSession
                                            .InlineSuggestionsRequestCallbackImpl)
                                    inlineSuggestionsRequestCallback,
                            createInlineSuggestionsRequest.mPackageName,
                            inputMethodBindingController.mCurTokenDisplayId,
                            inputMethodBindingController.mCurToken);
            try {
                iInputMethodInvoker.mTarget.onCreateInlineSuggestionsRequest(
                        this.mPendingInlineSuggestionsRequest.mRequestInfo,
                        inlineSuggestionsRequestCallbackDecorator);
            } catch (RemoteException e) {
                IInputMethodInvoker.logRemoteException(e);
            }
        } else {
            Slog.w(
                    "AutofillSuggestionsController",
                    "No IME connected! Abandoning inline suggestions creation request.");
        }
        this.mPendingInlineSuggestionsRequest = null;
    }
}
