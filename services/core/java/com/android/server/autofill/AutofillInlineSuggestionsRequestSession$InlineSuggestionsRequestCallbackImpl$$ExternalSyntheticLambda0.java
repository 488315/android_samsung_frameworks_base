package com.android.server.autofill;

import java.util.function.Consumer;

public final /* synthetic */
class AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                (AutofillInlineSuggestionsRequestSession) obj;
        synchronized (autofillInlineSuggestionsRequestSession.mLock) {
            try {
                if (autofillInlineSuggestionsRequestSession.mDestroyed) {
                    return;
                }
                autofillInlineSuggestionsRequestSession.mImeSessionInvalidated = true;
            } finally {
            }
        }
    }
}
