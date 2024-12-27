package com.android.server.autofill;

import android.util.Slog;
import android.view.inputmethod.InlineSuggestionsRequest;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class InlineSuggestionRequestConsumer implements Consumer {
    public final WeakReference mAssistDataReceiverWeakReference;
    public final WeakReference mViewStateWeakReference;

    public InlineSuggestionRequestConsumer(
            WeakReference weakReference, WeakReference weakReference2) {
        this.mAssistDataReceiverWeakReference = weakReference;
        this.mViewStateWeakReference = weakReference2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        InlineSuggestionsRequest inlineSuggestionsRequest = (InlineSuggestionsRequest) obj;
        Session.AssistDataReceiverImpl assistDataReceiverImpl =
                (Session.AssistDataReceiverImpl) this.mAssistDataReceiverWeakReference.get();
        ViewState viewState = (ViewState) this.mViewStateWeakReference.get();
        if (assistDataReceiverImpl == null) {
            Slog.wtf(
                    "InlineSuggestionRequestConsumer",
                    "assistDataReceiver is null when accepting new inline suggestionrequests");
            return;
        }
        if (viewState == null) {
            Slog.wtf(
                    "InlineSuggestionRequestConsumer",
                    "view state is null when accepting new inline suggestion requests");
            return;
        }
        synchronized (Session.this.mLock) {
            try {
                if (assistDataReceiverImpl.mWaitForInlineRequest
                        && assistDataReceiverImpl.mPendingInlineSuggestionsRequest == null) {
                    assistDataReceiverImpl.mWaitForInlineRequest = inlineSuggestionsRequest != null;
                    assistDataReceiverImpl.mPendingInlineSuggestionsRequest =
                            inlineSuggestionsRequest;
                    assistDataReceiverImpl.maybeRequestFillLocked();
                    viewState.resetState(
                            EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
                }
            } finally {
            }
        }
    }
}
