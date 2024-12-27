package com.android.internal.inputmethod;

import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestionsRequest;

public interface InlineSuggestionsRequestCallback {
    void onInlineSuggestionsRequest(
            InlineSuggestionsRequest inlineSuggestionsRequest,
            IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback);

    void onInlineSuggestionsSessionInvalidated();

    void onInlineSuggestionsUnsupported();

    void onInputMethodFinishInput();

    void onInputMethodFinishInputView();

    void onInputMethodShowInputRequested(boolean z);

    void onInputMethodStartInput(AutofillId autofillId);

    void onInputMethodStartInputView();
}
