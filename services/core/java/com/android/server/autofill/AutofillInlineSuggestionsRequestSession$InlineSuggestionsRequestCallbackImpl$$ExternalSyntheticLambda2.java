package com.android.server.autofill;

import android.view.autofill.AutofillId;

import com.android.internal.util.function.QuadConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
