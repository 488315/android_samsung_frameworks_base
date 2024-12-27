package com.android.server.autofill;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.util.Pair;
import android.util.Slog;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestionsRequest;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class InlineSuggestionRendorInfoCallbackOnResultListener
        implements RemoteCallback.OnResultListener {
    public final AutofillId mFocusedId;
    public final Consumer mInlineSuggestionsRequestConsumer;
    public final int mRequestIdCopy;
    public final WeakReference mSessionWeakReference;

    public InlineSuggestionRendorInfoCallbackOnResultListener(
            WeakReference weakReference,
            int i,
            InlineSuggestionRequestConsumer inlineSuggestionRequestConsumer,
            AutofillId autofillId) {
        this.mRequestIdCopy = i;
        this.mInlineSuggestionsRequestConsumer = inlineSuggestionRequestConsumer;
        this.mSessionWeakReference = weakReference;
        this.mFocusedId = autofillId;
    }

    public final void onResult(Bundle bundle) {
        final Session session = (Session) this.mSessionWeakReference.get();
        if (session == null) {
            Slog.wtf(
                    "InlineSuggestionRendorInfoCallbackOnResultListener",
                    "Session is null before trying to call onResult");
            return;
        }
        synchronized (session.mLock) {
            try {
                if (session.mDestroyed) {
                    Slog.wtf(
                            "InlineSuggestionRendorInfoCallbackOnResultListener",
                            "Session is destroyed before trying to call onResult");
                    return;
                }
                AutofillInlineSessionController autofillInlineSessionController =
                        session.mInlineSessionController;
                AutofillId autofillId = this.mFocusedId;
                Consumer consumer = this.mInlineSuggestionsRequestConsumer;
                final int i = this.mRequestIdCopy;
                final InlineSuggestionRequestConsumer inlineSuggestionRequestConsumer =
                        (InlineSuggestionRequestConsumer) consumer;
                autofillInlineSessionController.onCreateInlineSuggestionsRequestLocked(
                        autofillId,
                        new Consumer() { // from class:
                                         // com.android.server.autofill.Session$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Session session2 = Session.this;
                                Consumer consumer2 = inlineSuggestionRequestConsumer;
                                int i2 = i;
                                InlineSuggestionsRequest inlineSuggestionsRequest =
                                        (InlineSuggestionsRequest) obj;
                                session2.getClass();
                                consumer2.accept(inlineSuggestionsRequest);
                                synchronized (session2.mLock) {
                                    session2.mLastInlineSuggestionsRequest =
                                            Pair.create(
                                                    Integer.valueOf(i2), inlineSuggestionsRequest);
                                }
                            }
                        },
                        bundle);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
