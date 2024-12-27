package com.android.server.autofill;

import android.view.autofill.AutofillId;

import com.android.internal.util.function.QuadConsumer;

public final /* synthetic */
class AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda2
        implements QuadConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        ((AutofillInlineSuggestionsRequestSession) obj)
                .handleOnReceiveImeStatusUpdated(
                        (AutofillId) obj2,
                        ((Boolean) obj3).booleanValue(),
                        ((Boolean) obj4).booleanValue());
    }
}
