package com.android.server.autofill;

import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.internal.inputmethod.IInlineSuggestionsResponseCallback;
import com.android.internal.util.function.TriConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1 implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public final void accept(Object obj, Object obj2, Object obj3) {
        AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = (AutofillInlineSuggestionsRequestSession) obj;
        switch (this.$r8$classId) {
            case 0:
                InlineSuggestionsRequest inlineSuggestionsRequest = (InlineSuggestionsRequest) obj2;
                IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback = (IInlineSuggestionsResponseCallback) obj3;
                synchronized (autofillInlineSuggestionsRequestSession.mLock) {
                    try {
                        if (!autofillInlineSuggestionsRequestSession.mDestroyed && !autofillInlineSuggestionsRequestSession.mImeRequestReceived) {
                            autofillInlineSuggestionsRequestSession.mImeRequestReceived = true;
                            autofillInlineSuggestionsRequestSession.mImeSessionInvalidated = false;
                            if (inlineSuggestionsRequest != null && iInlineSuggestionsResponseCallback != null) {
                                autofillInlineSuggestionsRequestSession.mImeRequest = inlineSuggestionsRequest;
                                autofillInlineSuggestionsRequestSession.mResponseCallback = iInlineSuggestionsResponseCallback;
                                autofillInlineSuggestionsRequestSession.handleOnReceiveImeStatusUpdated(autofillInlineSuggestionsRequestSession.mAutofillId, true, false);
                            }
                            Consumer consumer = autofillInlineSuggestionsRequestSession.mImeRequestConsumer;
                            if (consumer != null) {
                                consumer.accept(autofillInlineSuggestionsRequestSession.mImeRequest);
                                autofillInlineSuggestionsRequestSession.mImeRequestConsumer = null;
                            }
                            return;
                        }
                        return;
                    } finally {
                    }
                }
            default:
                autofillInlineSuggestionsRequestSession.handleOnReceiveImeStatusUpdated(((Boolean) obj2).booleanValue(), ((Boolean) obj3).booleanValue());
                return;
        }
    }
}
