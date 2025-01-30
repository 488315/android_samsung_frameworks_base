package com.android.server.autofill;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.view.autofill.AutofillId;
import com.android.server.autofill.p008ui.InlineFillUi;
import com.android.server.inputmethod.InputMethodManagerInternal;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class AutofillInlineSessionController {
  public final ComponentName mComponentName;
  public final Handler mHandler;
  public InlineFillUi mInlineFillUi;
  public final InputMethodManagerInternal mInputMethodManagerInternal;
  public final Object mLock;
  public AutofillInlineSuggestionsRequestSession mSession;
  public final InlineFillUi.InlineUiEventCallback mUiCallback;
  public final int mUserId;

  public AutofillInlineSessionController(
      InputMethodManagerInternal inputMethodManagerInternal,
      int i,
      ComponentName componentName,
      Handler handler,
      Object obj,
      InlineFillUi.InlineUiEventCallback inlineUiEventCallback) {
    this.mInputMethodManagerInternal = inputMethodManagerInternal;
    this.mUserId = i;
    this.mComponentName = componentName;
    this.mHandler = handler;
    this.mLock = obj;
    this.mUiCallback = inlineUiEventCallback;
  }

  public void onCreateInlineSuggestionsRequestLocked(
      AutofillId autofillId, Consumer consumer, Bundle bundle) {
    AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession;
    if (autofillInlineSuggestionsRequestSession != null) {
      autofillInlineSuggestionsRequestSession.destroySessionLocked();
    }
    this.mInlineFillUi = null;
    AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession2 =
        new AutofillInlineSuggestionsRequestSession(
            this.mInputMethodManagerInternal,
            this.mUserId,
            this.mComponentName,
            this.mHandler,
            this.mLock,
            autofillId,
            consumer,
            bundle,
            this.mUiCallback);
    this.mSession = autofillInlineSuggestionsRequestSession2;
    autofillInlineSuggestionsRequestSession2.onCreateInlineSuggestionsRequestLocked();
  }

  public void destroyLocked(AutofillId autofillId) {
    AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession;
    if (autofillInlineSuggestionsRequestSession != null) {
      autofillInlineSuggestionsRequestSession.onInlineSuggestionsResponseLocked(
          InlineFillUi.emptyUi(autofillId));
      this.mSession.destroySessionLocked();
      this.mSession = null;
    }
    this.mInlineFillUi = null;
  }

  public Optional getInlineSuggestionsRequestLocked() {
    AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession;
    if (autofillInlineSuggestionsRequestSession != null) {
      return autofillInlineSuggestionsRequestSession.getInlineSuggestionsRequestLocked();
    }
    return Optional.empty();
  }

  public boolean hideInlineSuggestionsUiLocked(AutofillId autofillId) {
    AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession;
    if (autofillInlineSuggestionsRequestSession != null) {
      return autofillInlineSuggestionsRequestSession.onInlineSuggestionsResponseLocked(
          InlineFillUi.emptyUi(autofillId));
    }
    return false;
  }

  public void disableFilterMatching(AutofillId autofillId) {
    InlineFillUi inlineFillUi = this.mInlineFillUi;
    if (inlineFillUi == null || !inlineFillUi.getAutofillId().equals(autofillId)) {
      return;
    }
    this.mInlineFillUi.disableFilterMatching();
  }

  public void resetInlineFillUiLocked() {
    this.mInlineFillUi = null;
    AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession;
    if (autofillInlineSuggestionsRequestSession != null) {
      autofillInlineSuggestionsRequestSession.resetInlineFillUiLocked();
    }
  }

  public boolean filterInlineFillUiLocked(AutofillId autofillId, String str) {
    InlineFillUi inlineFillUi = this.mInlineFillUi;
    if (inlineFillUi == null || !inlineFillUi.getAutofillId().equals(autofillId)) {
      return false;
    }
    this.mInlineFillUi.setFilterText(str);
    return requestImeToShowInlineSuggestionsLocked();
  }

  public boolean setInlineFillUiLocked(InlineFillUi inlineFillUi) {
    this.mInlineFillUi = inlineFillUi;
    return requestImeToShowInlineSuggestionsLocked();
  }

  public final boolean requestImeToShowInlineSuggestionsLocked() {
    InlineFillUi inlineFillUi;
    AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession;
    if (autofillInlineSuggestionsRequestSession == null
        || (inlineFillUi = this.mInlineFillUi) == null) {
      return false;
    }
    return autofillInlineSuggestionsRequestSession.onInlineSuggestionsResponseLocked(inlineFillUi);
  }

  public boolean isImeShowing() {
    AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession = this.mSession;
    if (autofillInlineSuggestionsRequestSession != null) {
      return autofillInlineSuggestionsRequestSession.isImeShowing();
    }
    return false;
  }
}
