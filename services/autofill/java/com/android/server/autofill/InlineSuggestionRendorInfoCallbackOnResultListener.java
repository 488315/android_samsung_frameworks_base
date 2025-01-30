package com.android.server.autofill;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.util.Slog;
import android.view.autofill.AutofillId;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class InlineSuggestionRendorInfoCallbackOnResultListener implements RemoteCallback.OnResultListener {
    public final AutofillId mFocusedId;
    public final Consumer mInlineSuggestionsRequestConsumer;
    public final int mRequestIdCopy;
    public final WeakReference mSessionWeakReference;

    public InlineSuggestionRendorInfoCallbackOnResultListener(WeakReference weakReference, int i, Consumer consumer, AutofillId autofillId) {
        this.mRequestIdCopy = i;
        this.mInlineSuggestionsRequestConsumer = consumer;
        this.mSessionWeakReference = weakReference;
        this.mFocusedId = autofillId;
    }

    public void onResult(Bundle bundle) {
        Session session = (Session) this.mSessionWeakReference.get();
        if (session == null) {
            Slog.wtf("InlineSuggestionRendorInfoCallbackOnResultListener", "Session is null before trying to call onResult");
            return;
        }
        synchronized (session.mLock) {
            if (session.mDestroyed) {
                Slog.wtf("InlineSuggestionRendorInfoCallbackOnResultListener", "Session is destroyed before trying to call onResult");
            } else {
                session.mInlineSessionController.onCreateInlineSuggestionsRequestLocked(this.mFocusedId, session.inlineSuggestionsRequestCacheDecorator(this.mInlineSuggestionsRequestConsumer, this.mRequestIdCopy), bundle);
            }
        }
    }
}
