package android.inputmethodservice;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.view.inputmethod.InlineSuggestionsResponse;

import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
class InlineSuggestionSessionController {
    private static final String TAG = "InlineSuggestionSessionController";
    private final Supplier<IBinder> mHostInputTokenSupplier;
    private AutofillId mImeClientFieldId;
    private String mImeClientPackageName;
    private boolean mImeInputStarted;
    private boolean mImeInputViewStarted;
    private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper(), null, true);
    private final Function<Bundle, InlineSuggestionsRequest> mRequestSupplier;
    private final Consumer<InlineSuggestionsResponse> mResponseConsumer;
    private InlineSuggestionSession mSession;

    InlineSuggestionSessionController(
            Function<Bundle, InlineSuggestionsRequest> requestSupplier,
            Supplier<IBinder> hostInputTokenSupplier,
            Consumer<InlineSuggestionsResponse> responseConsumer) {
        this.mRequestSupplier = requestSupplier;
        this.mHostInputTokenSupplier = hostInputTokenSupplier;
        this.mResponseConsumer = responseConsumer;
    }

    void onMakeInlineSuggestionsRequest(
            InlineSuggestionsRequestInfo requestInfo, IInlineSuggestionsRequestCallback callback) {
        if (this.mSession != null) {
            this.mSession.invalidate();
        }
        this.mSession =
                new InlineSuggestionSession(
                        requestInfo,
                        callback,
                        this.mRequestSupplier,
                        this.mHostInputTokenSupplier,
                        this.mResponseConsumer,
                        this,
                        this.mMainThreadHandler);
        if (this.mImeInputStarted && match(this.mSession.getRequestInfo())) {
            this.mSession.makeInlineSuggestionRequestUncheck();
            if (this.mImeInputViewStarted) {
                try {
                    this.mSession.getRequestCallback().onInputMethodStartInputView();
                } catch (RemoteException e) {
                    Log.w(TAG, "onInputMethodStartInputView() remote exception:" + e);
                }
            }
        }
    }

    void notifyOnStartInput(String imeClientPackageName, AutofillId imeFieldId) {
        if (imeClientPackageName == null || imeFieldId == null) {
            return;
        }
        this.mImeInputStarted = true;
        this.mImeClientPackageName = imeClientPackageName;
        this.mImeClientFieldId = imeFieldId;
        if (this.mSession != null) {
            this.mSession.consumeInlineSuggestionsResponse(InlineSuggestionSession.EMPTY_RESPONSE);
            if (!this.mSession.isCallbackInvoked() && match(this.mSession.getRequestInfo())) {
                this.mSession.makeInlineSuggestionRequestUncheck();
            } else if (this.mSession.shouldSendImeStatus()) {
                try {
                    this.mSession
                            .getRequestCallback()
                            .onInputMethodStartInput(this.mImeClientFieldId);
                } catch (RemoteException e) {
                    Log.w(TAG, "onInputMethodStartInput() remote exception:" + e);
                }
            }
        }
    }

    void notifyOnShowInputRequested(boolean requestResult) {
        if (this.mSession != null && this.mSession.shouldSendImeStatus()) {
            try {
                this.mSession.getRequestCallback().onInputMethodShowInputRequested(requestResult);
            } catch (RemoteException e) {
                Log.w(TAG, "onInputMethodShowInputRequested() remote exception:" + e);
            }
        }
    }

    void notifyOnStartInputView() {
        this.mImeInputViewStarted = true;
        if (this.mSession != null && this.mSession.shouldSendImeStatus()) {
            try {
                this.mSession.getRequestCallback().onInputMethodStartInputView();
            } catch (RemoteException e) {
                Log.w(TAG, "onInputMethodStartInputView() remote exception:" + e);
            }
        }
    }

    void notifyOnFinishInputView() {
        this.mImeInputViewStarted = false;
        if (this.mSession != null && this.mSession.shouldSendImeStatus()) {
            try {
                this.mSession.getRequestCallback().onInputMethodFinishInputView();
            } catch (RemoteException e) {
                Log.w(TAG, "onInputMethodFinishInputView() remote exception:" + e);
            }
        }
    }

    void notifyOnFinishInput() {
        this.mImeClientPackageName = null;
        this.mImeClientFieldId = null;
        this.mImeInputViewStarted = false;
        this.mImeInputStarted = false;
        if (this.mSession != null && this.mSession.shouldSendImeStatus()) {
            try {
                this.mSession.getRequestCallback().onInputMethodFinishInput();
            } catch (RemoteException e) {
                Log.w(TAG, "onInputMethodFinishInput() remote exception:" + e);
            }
        }
    }

    boolean match(InlineSuggestionsRequestInfo requestInfo) {
        return match(requestInfo, this.mImeClientPackageName, this.mImeClientFieldId);
    }

    boolean match(AutofillId autofillId) {
        return match(autofillId, this.mImeClientFieldId);
    }

    private static boolean match(
            InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo,
            String imeClientPackageName,
            AutofillId imeClientFieldId) {
        return (inlineSuggestionsRequestInfo == null
                        || imeClientPackageName == null
                        || imeClientFieldId == null
                        || !inlineSuggestionsRequestInfo
                                .getComponentName()
                                .getPackageName()
                                .equals(imeClientPackageName)
                        || !match(inlineSuggestionsRequestInfo.getAutofillId(), imeClientFieldId))
                ? false
                : true;
    }

    private static boolean match(AutofillId autofillId, AutofillId imeClientFieldId) {
        return (autofillId == null
                        || imeClientFieldId == null
                        || autofillId.getViewId() != imeClientFieldId.getViewId())
                ? false
                : true;
    }
}
