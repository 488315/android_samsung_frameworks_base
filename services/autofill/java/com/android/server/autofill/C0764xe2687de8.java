package com.android.server.autofill;

import com.android.internal.util.function.TriConsumer;

/* compiled from: R8$$SyntheticClass */
/* renamed from: com.android.server.autofill.AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda2 */
/* loaded from: classes.dex */
public final /* synthetic */ class C0764xe2687de8 implements TriConsumer {
  public final void accept(Object obj, Object obj2, Object obj3) {
    ((AutofillInlineSuggestionsRequestSession) obj)
        .handleOnReceiveImeStatusUpdated(
            ((Boolean) obj2).booleanValue(), ((Boolean) obj3).booleanValue());
  }
}
