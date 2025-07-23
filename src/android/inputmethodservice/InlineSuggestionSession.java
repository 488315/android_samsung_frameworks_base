package android.inputmethodservice;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.autofill.AutofillFeatureFlags;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestion;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.view.inputmethod.InlineSuggestionsResponse;
import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.IInlineSuggestionsResponseCallback;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
class InlineSuggestionSession {
    static final InlineSuggestionsResponse EMPTY_RESPONSE = new InlineSuggestionsResponse((List<InlineSuggestion>) Collections.EMPTY_LIST);
    private static final String TAG = "ImsInlineSuggestionSession";
    private boolean mAlwaysNotifyAutofill;
    private final IInlineSuggestionsRequestCallback mCallback;
    private boolean mCallbackInvoked = false;
    private final Supplier<IBinder> mHostInputTokenSupplier;
    private final InlineSuggestionSessionController mInlineSuggestionSessionController;
    private final Handler mMainThreadHandler;
    private Boolean mPreviousResponseIsEmpty;
    private final InlineSuggestionsRequestInfo mRequestInfo;
    private final Function<Bundle, InlineSuggestionsRequest> mRequestSupplier;
    private InlineSuggestionsResponseCallbackImpl mResponseCallback;
    private final Consumer<InlineSuggestionsResponse> mResponseConsumer;

    InlineSuggestionSession(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback, Function<Bundle, InlineSuggestionsRequest> function, Supplier<IBinder> supplier, Consumer<InlineSuggestionsResponse> consumer, InlineSuggestionSessionController inlineSuggestionSessionController, Handler handler) {
        this.mAlwaysNotifyAutofill = false;
        this.mRequestInfo = inlineSuggestionsRequestInfo;
        this.mCallback = iInlineSuggestionsRequestCallback;
        this.mRequestSupplier = function;
        this.mHostInputTokenSupplier = supplier;
        this.mResponseConsumer = consumer;
        this.mInlineSuggestionSessionController = inlineSuggestionSessionController;
        this.mMainThreadHandler = handler;
        this.mAlwaysNotifyAutofill = AutofillFeatureFlags.isImproveFillDialogEnabled();
    }

    InlineSuggestionsRequestInfo getRequestInfo() {
        return this.mRequestInfo;
    }

    IInlineSuggestionsRequestCallback getRequestCallback() {
        return this.mCallback;
    }

    boolean shouldSendImeStatus() {
        return this.mResponseCallback != null;
    }

    boolean isCallbackInvoked() {
        return this.mCallbackInvoked;
    }

    void invalidate() {
        try {
            this.mCallback.onInlineSuggestionsSessionInvalidated();
        } catch (RemoteException e) {
            Log.w(TAG, "onInlineSuggestionsSessionInvalidated() remote exception", e);
        }
        if (this.mResponseCallback != null) {
            consumeInlineSuggestionsResponse(EMPTY_RESPONSE);
            this.mResponseCallback.invalidate();
            this.mResponseCallback = null;
        }
    }

    void makeInlineSuggestionRequestUncheck() {
        if (this.mCallbackInvoked) {
            return;
        }
        try {
            InlineSuggestionsRequest apply = this.mRequestSupplier.apply(this.mRequestInfo.getUiExtras());
            if (this.mAlwaysNotifyAutofill) {
                this.mResponseCallback = new InlineSuggestionsResponseCallbackImpl();
            }
            if (apply == null) {
                this.mCallback.onInlineSuggestionsUnsupported();
            } else {
                apply.setHostInputToken(this.mHostInputTokenSupplier.get());
                apply.filterContentTypes();
                if (!this.mAlwaysNotifyAutofill) {
                    this.mResponseCallback = new InlineSuggestionsResponseCallbackImpl();
                }
                this.mCallback.onInlineSuggestionsRequest(apply, this.mResponseCallback);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "makeInlinedSuggestionsRequest() remote exception:" + e);
        }
        this.mCallbackInvoked = true;
    }

    void handleOnInlineSuggestionsResponse(AutofillId autofillId, InlineSuggestionsResponse inlineSuggestionsResponse) {
        if (this.mInlineSuggestionSessionController.match(autofillId)) {
            consumeInlineSuggestionsResponse(inlineSuggestionsResponse);
        }
    }

    void consumeInlineSuggestionsResponse(InlineSuggestionsResponse inlineSuggestionsResponse) {
        boolean isEmpty = inlineSuggestionsResponse.getInlineSuggestions().isEmpty();
        if (isEmpty && Boolean.TRUE.equals(this.mPreviousResponseIsEmpty)) {
            return;
        }
        this.mPreviousResponseIsEmpty = Boolean.valueOf(isEmpty);
        this.mResponseConsumer.accept(inlineSuggestionsResponse);
    }

    private static final class InlineSuggestionsResponseCallbackImpl extends IInlineSuggestionsResponseCallback.Stub {
        private volatile boolean mInvalid;
        private final WeakReference<InlineSuggestionSession> mSession;

        private InlineSuggestionsResponseCallbackImpl(InlineSuggestionSession inlineSuggestionSession) {
            this.mInvalid = false;
            this.mSession = new WeakReference<>(inlineSuggestionSession);
        }

        void invalidate() {
            this.mInvalid = true;
        }

        @Override // com.android.internal.inputmethod.IInlineSuggestionsResponseCallback
        public void onInlineSuggestionsResponse(AutofillId autofillId, InlineSuggestionsResponse inlineSuggestionsResponse) {
            InlineSuggestionSession inlineSuggestionSession;
            if (this.mInvalid || (inlineSuggestionSession = this.mSession.get()) == null) {
                return;
            }
            inlineSuggestionSession.mMainThreadHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.inputmethodservice.InlineSuggestionSession$InlineSuggestionsResponseCallbackImpl$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((InlineSuggestionSession) obj).handleOnInlineSuggestionsResponse((AutofillId) obj2, (InlineSuggestionsResponse) obj3);
                }
            }, inlineSuggestionSession, autofillId, inlineSuggestionsResponse));
        }
    }
}
