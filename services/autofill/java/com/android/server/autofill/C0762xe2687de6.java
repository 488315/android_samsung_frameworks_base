package com.android.server.autofill;

import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.internal.inputmethod.IInlineSuggestionsResponseCallback;
import com.android.internal.util.function.TriConsumer;

/* compiled from: R8$$SyntheticClass */
/* renamed from: com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda0 */
/* loaded from: classes.dex */
public final /* synthetic */ class C0762xe2687de6 implements TriConsumer {
  public final void accept(Object obj, Object obj2, Object obj3) {
    ((AutofillInlineSuggestionsRequestSession) obj)
        .handleOnReceiveImeRequest(
            (InlineSuggestionsRequest) obj2, (IInlineSuggestionsResponseCallback) obj3);
  }
}
