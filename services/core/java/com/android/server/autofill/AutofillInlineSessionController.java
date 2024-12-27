package com.android.server.autofill;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.util.Slog;
import android.view.autofill.AutofillId;

import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.server.autofill.ui.InlineFillUi;
import com.android.server.inputmethod.InputMethodManagerInternal;

import java.util.function.Consumer;

public final class AutofillInlineSessionController {
    public final ComponentName mComponentName;
    public final Handler mHandler;
    public InlineFillUi mInlineFillUi;
    public final InputMethodManagerInternal mInputMethodManagerInternal;
    public final Object mLock;
    public AutofillInlineSuggestionsRequestSession mSession;
    public final Session.AnonymousClass2 mUiCallback;
    public final int mUserId;

    public AutofillInlineSessionController(
            InputMethodManagerInternal inputMethodManagerInternal,
            int i,
            ComponentName componentName,
            Handler handler,
            Object obj,
            Session.AnonymousClass2 anonymousClass2) {
        this.mInputMethodManagerInternal = inputMethodManagerInternal;
        this.mUserId = i;
        this.mComponentName = componentName;
        this.mHandler = handler;
        this.mLock = obj;
        this.mUiCallback = anonymousClass2;
    }

    public final void hideInlineSuggestionsUiLocked(AutofillId autofillId) {
        AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                this.mSession;
        if (autofillInlineSuggestionsRequestSession != null) {
            autofillInlineSuggestionsRequestSession.onInlineSuggestionsResponseLocked(
                    new InlineFillUi(autofillId));
        }
    }

    public final void onCreateInlineSuggestionsRequestLocked(
            AutofillId autofillId, Consumer consumer, Bundle bundle) {
        AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                this.mSession;
        if (autofillInlineSuggestionsRequestSession != null) {
            autofillInlineSuggestionsRequestSession.destroySessionLocked();
        }
        this.mInlineFillUi = null;
        ComponentName componentName = this.mComponentName;
        Handler handler = this.mHandler;
        InputMethodManagerInternal inputMethodManagerInternal = this.mInputMethodManagerInternal;
        int i = this.mUserId;
        AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession2 =
                new AutofillInlineSuggestionsRequestSession(
                        inputMethodManagerInternal,
                        i,
                        componentName,
                        handler,
                        this.mLock,
                        autofillId,
                        consumer,
                        bundle,
                        this.mUiCallback);
        this.mSession = autofillInlineSuggestionsRequestSession2;
        autofillInlineSuggestionsRequestSession2.mImeSessionInvalidated = false;
        if (Helper.sDebug) {
            Slog.d(
                    "AutofillInlineSuggestionsRequestSession",
                    "onCreateInlineSuggestionsRequestLocked called: "
                            + autofillInlineSuggestionsRequestSession2.mAutofillId);
        }
        inputMethodManagerInternal.onCreateInlineSuggestionsRequest(
                i,
                new InlineSuggestionsRequestInfo(
                        componentName,
                        autofillInlineSuggestionsRequestSession2.mAutofillId,
                        bundle),
                new AutofillInlineSuggestionsRequestSession.InlineSuggestionsRequestCallbackImpl(
                        autofillInlineSuggestionsRequestSession2));
    }

    public final boolean requestImeToShowInlineSuggestionsLocked() {
        InlineFillUi inlineFillUi;
        AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                this.mSession;
        if (autofillInlineSuggestionsRequestSession == null
                || (inlineFillUi = this.mInlineFillUi) == null) {
            return false;
        }
        return autofillInlineSuggestionsRequestSession.onInlineSuggestionsResponseLocked(
                inlineFillUi);
    }
}
