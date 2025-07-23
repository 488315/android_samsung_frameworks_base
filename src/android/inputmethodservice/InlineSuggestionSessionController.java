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

    InlineSuggestionSessionController(Function<Bundle, InlineSuggestionsRequest> function, Supplier<IBinder> supplier, Consumer<InlineSuggestionsResponse> consumer) {
        this.mRequestSupplier = function;
        this.mHostInputTokenSupplier = supplier;
        this.mResponseConsumer = consumer;
    }

    void onMakeInlineSuggestionsRequest(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
        InlineSuggestionSession inlineSuggestionSession = this.mSession;
        if (inlineSuggestionSession != null) {
            inlineSuggestionSession.invalidate();
        }
        InlineSuggestionSession inlineSuggestionSession2 = new InlineSuggestionSession(inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback, this.mRequestSupplier, this.mHostInputTokenSupplier, this.mResponseConsumer, this, this.mMainThreadHandler);
        this.mSession = inlineSuggestionSession2;
        if (this.mImeInputStarted && match(inlineSuggestionSession2.getRequestInfo())) {
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

    void notifyOnStartInput(String str, AutofillId autofillId) {
        if (str == null || autofillId == null) {
            return;
        }
        this.mImeInputStarted = true;
        this.mImeClientPackageName = str;
        this.mImeClientFieldId = autofillId;
        InlineSuggestionSession inlineSuggestionSession = this.mSession;
        if (inlineSuggestionSession != null) {
            inlineSuggestionSession.consumeInlineSuggestionsResponse(InlineSuggestionSession.EMPTY_RESPONSE);
            if (!this.mSession.isCallbackInvoked() && match(this.mSession.getRequestInfo())) {
                this.mSession.makeInlineSuggestionRequestUncheck();
                return;
            }
            if (this.mSession.shouldSendImeStatus()) {
                try {
                    this.mSession.getRequestCallback().onInputMethodStartInput(this.mImeClientFieldId);
                } catch (RemoteException e) {
                    Log.w(TAG, "onInputMethodStartInput() remote exception:" + e);
                }
            }
        }
    }

    void notifyOnShowInputRequested(boolean z) {
        InlineSuggestionSession inlineSuggestionSession = this.mSession;
        if (inlineSuggestionSession == null || !inlineSuggestionSession.shouldSendImeStatus()) {
            return;
        }
        try {
            this.mSession.getRequestCallback().onInputMethodShowInputRequested(z);
        } catch (RemoteException e) {
            Log.w(TAG, "onInputMethodShowInputRequested() remote exception:" + e);
        }
    }

    void notifyOnStartInputView() {
        this.mImeInputViewStarted = true;
        InlineSuggestionSession inlineSuggestionSession = this.mSession;
        if (inlineSuggestionSession == null || !inlineSuggestionSession.shouldSendImeStatus()) {
            return;
        }
        try {
            this.mSession.getRequestCallback().onInputMethodStartInputView();
        } catch (RemoteException e) {
            Log.w(TAG, "onInputMethodStartInputView() remote exception:" + e);
        }
    }

    void notifyOnFinishInputView() {
        this.mImeInputViewStarted = false;
        InlineSuggestionSession inlineSuggestionSession = this.mSession;
        if (inlineSuggestionSession == null || !inlineSuggestionSession.shouldSendImeStatus()) {
            return;
        }
        try {
            this.mSession.getRequestCallback().onInputMethodFinishInputView();
        } catch (RemoteException e) {
            Log.w(TAG, "onInputMethodFinishInputView() remote exception:" + e);
        }
    }

    void notifyOnFinishInput() {
        this.mImeClientPackageName = null;
        this.mImeClientFieldId = null;
        this.mImeInputViewStarted = false;
        this.mImeInputStarted = false;
        InlineSuggestionSession inlineSuggestionSession = this.mSession;
        if (inlineSuggestionSession == null || !inlineSuggestionSession.shouldSendImeStatus()) {
            return;
        }
        try {
            this.mSession.getRequestCallback().onInputMethodFinishInput();
        } catch (RemoteException e) {
            Log.w(TAG, "onInputMethodFinishInput() remote exception:" + e);
        }
    }

    boolean match(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo) {
        return match(inlineSuggestionsRequestInfo, this.mImeClientPackageName, this.mImeClientFieldId);
    }

    boolean match(AutofillId autofillId) {
        return match(autofillId, this.mImeClientFieldId);
    }

    private static boolean match(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, String str, AutofillId autofillId) {
        return (inlineSuggestionsRequestInfo == null || str == null || autofillId == null || !inlineSuggestionsRequestInfo.getComponentName().getPackageName().equals(str) || !match(inlineSuggestionsRequestInfo.getAutofillId(), autofillId)) ? false : true;
    }

    private static boolean match(AutofillId autofillId, AutofillId autofillId2) {
        return (autofillId == null || autofillId2 == null || autofillId.getViewId() != autofillId2.getViewId()) ? false : true;
    }
}
