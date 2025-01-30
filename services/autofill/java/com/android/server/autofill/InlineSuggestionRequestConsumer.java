package com.android.server.autofill;

import android.util.Slog;
import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.server.autofill.Session;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class InlineSuggestionRequestConsumer implements Consumer {
    public final WeakReference mAssistDataReceiverWeakReference;
    public final WeakReference mViewStateWeakReference;

    public InlineSuggestionRequestConsumer(WeakReference weakReference, WeakReference weakReference2) {
        this.mAssistDataReceiverWeakReference = weakReference;
        this.mViewStateWeakReference = weakReference2;
    }

    @Override // java.util.function.Consumer
    public void accept(InlineSuggestionsRequest inlineSuggestionsRequest) {
        Session.AssistDataReceiverImpl assistDataReceiverImpl = (Session.AssistDataReceiverImpl) this.mAssistDataReceiverWeakReference.get();
        ViewState viewState = (ViewState) this.mViewStateWeakReference.get();
        if (assistDataReceiverImpl == null) {
            Slog.wtf("InlineSuggestionRequestConsumer", "assistDataReceiver is null when accepting new inline suggestionrequests");
        } else if (viewState == null) {
            Slog.wtf("InlineSuggestionRequestConsumer", "view state is null when accepting new inline suggestion requests");
        } else {
            assistDataReceiverImpl.handleInlineSuggestionRequest(inlineSuggestionsRequest, viewState);
        }
    }
}
