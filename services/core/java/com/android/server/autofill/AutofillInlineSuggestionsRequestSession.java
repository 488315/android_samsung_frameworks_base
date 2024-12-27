package com.android.server.autofill;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.util.Slog;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestionsRequest;

import com.android.internal.inputmethod.IInlineSuggestionsResponseCallback;
import com.android.internal.inputmethod.InlineSuggestionsRequestCallback;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.autofill.ui.InlineFillUi;
import com.android.server.inputmethod.InputMethodManagerInternal;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AutofillInlineSuggestionsRequestSession {
    public AutofillId mAutofillId;
    public final Handler mHandler;
    public AutofillId mImeCurrentFieldId;
    public boolean mImeInputStarted;
    public boolean mImeInputViewStarted;
    public InlineSuggestionsRequest mImeRequest;
    public Consumer mImeRequestConsumer;
    public boolean mImeRequestReceived;
    public InlineFillUi mInlineFillUi;
    public final Object mLock;
    public boolean mPreviousHasNonPinSuggestionShow;
    public IInlineSuggestionsResponseCallback mResponseCallback;
    public final Session.AnonymousClass2 mUiCallback;
    public Boolean mPreviousResponseIsNotEmpty = null;
    public boolean mDestroyed = false;
    public boolean mImeSessionInvalidated = false;
    public boolean mImeShowing = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InlineSuggestionsRequestCallbackImpl
            implements InlineSuggestionsRequestCallback {
        public final WeakReference mSession;

        public InlineSuggestionsRequestCallbackImpl(
                AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession) {
            this.mSession = new WeakReference(autofillInlineSuggestionsRequestSession);
        }

        public final void onInlineSuggestionsRequest(
                InlineSuggestionsRequest inlineSuggestionsRequest,
                IInlineSuggestionsResponseCallback iInlineSuggestionsResponseCallback) {
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillInlineSuggestionsRequestSession",
                        "onInlineSuggestionsRequest() received: " + inlineSuggestionsRequest);
            }
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    (AutofillInlineSuggestionsRequestSession) this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1(
                                        0),
                                autofillInlineSuggestionsRequestSession,
                                inlineSuggestionsRequest,
                                iInlineSuggestionsResponseCallback));
            }
        }

        public final void onInlineSuggestionsSessionInvalidated() {
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillInlineSuggestionsRequestSession",
                        "onInlineSuggestionsSessionInvalidated() called.");
            }
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    (AutofillInlineSuggestionsRequestSession) this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda0(),
                                autofillInlineSuggestionsRequestSession));
            }
        }

        public final void onInlineSuggestionsUnsupported() {
            if (Helper.sDebug) {
                Slog.d(
                        "AutofillInlineSuggestionsRequestSession",
                        "onInlineSuggestionsUnsupported() called.");
            }
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    (AutofillInlineSuggestionsRequestSession) this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1(
                                        0),
                                autofillInlineSuggestionsRequestSession,
                                (Object) null,
                                (Object) null));
            }
        }

        public final void onInputMethodFinishInput() {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillInlineSuggestionsRequestSession",
                        "onInputMethodFinishInput() received");
            }
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    (AutofillInlineSuggestionsRequestSession) this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1
                        autofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1 =
                                new AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1(
                                        1);
                Boolean bool = Boolean.FALSE;
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                autofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1,
                                autofillInlineSuggestionsRequestSession,
                                bool,
                                bool));
            }
        }

        public final void onInputMethodFinishInputView() {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillInlineSuggestionsRequestSession",
                        "onInputMethodFinishInputView() received");
            }
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    (AutofillInlineSuggestionsRequestSession) this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1(
                                        1),
                                autofillInlineSuggestionsRequestSession,
                                Boolean.TRUE,
                                Boolean.FALSE));
            }
        }

        public final void onInputMethodShowInputRequested(boolean z) {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillInlineSuggestionsRequestSession",
                        "onInputMethodShowInputRequested() received: " + z);
            }
        }

        public final void onInputMethodStartInput(AutofillId autofillId) {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillInlineSuggestionsRequestSession",
                        "onInputMethodStartInput() received on " + autofillId);
            }
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    (AutofillInlineSuggestionsRequestSession) this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda2(),
                                autofillInlineSuggestionsRequestSession,
                                autofillId,
                                Boolean.TRUE,
                                Boolean.FALSE));
            }
        }

        public final void onInputMethodStartInputView() {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillInlineSuggestionsRequestSession",
                        "onInputMethodStartInputView() received");
            }
            AutofillInlineSuggestionsRequestSession autofillInlineSuggestionsRequestSession =
                    (AutofillInlineSuggestionsRequestSession) this.mSession.get();
            if (autofillInlineSuggestionsRequestSession != null) {
                AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1
                        autofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1 =
                                new AutofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1(
                                        1);
                Boolean bool = Boolean.TRUE;
                autofillInlineSuggestionsRequestSession.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                autofillInlineSuggestionsRequestSession$InlineSuggestionsRequestCallbackImpl$$ExternalSyntheticLambda1,
                                autofillInlineSuggestionsRequestSession,
                                bool,
                                bool));
            }
        }
    }

    public AutofillInlineSuggestionsRequestSession(
            InputMethodManagerInternal inputMethodManagerInternal,
            int i,
            ComponentName componentName,
            Handler handler,
            Object obj,
            AutofillId autofillId,
            Consumer consumer,
            Bundle bundle,
            Session.AnonymousClass2 anonymousClass2) {
        this.mHandler = handler;
        this.mLock = obj;
        this.mUiCallback = anonymousClass2;
        this.mAutofillId = autofillId;
        this.mImeRequestConsumer = consumer;
    }

    public final void destroySessionLocked() {
        this.mDestroyed = true;
        if (this.mImeRequestReceived) {
            return;
        }
        Slog.w(
                "AutofillInlineSuggestionsRequestSession",
                "Never received an InlineSuggestionsRequest from the IME for " + this.mAutofillId);
    }

    public final void handleOnReceiveImeStatusUpdated(
            AutofillId autofillId, boolean z, boolean z2) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                if (autofillId != null) {
                    this.mImeCurrentFieldId = autofillId;
                }
                handleOnReceiveImeStatusUpdated(z, z2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void handleOnReceiveImeStatusUpdated(boolean z, boolean z2) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                this.mImeShowing = z2;
                if (this.mImeCurrentFieldId != null) {
                    boolean z3 = this.mImeInputStarted != z;
                    boolean z4 = this.mImeInputViewStarted != z2;
                    this.mImeInputStarted = z;
                    this.mImeInputViewStarted = z2;
                    if (z3 || z4) {
                        maybeUpdateResponseToImeLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x014a, code lost:

       if (r7.getAuthentication() == null) goto L47;
    */
    /* JADX WARN: Removed duplicated region for block: B:113:0x015f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void maybeUpdateResponseToImeLocked() {
        /*
            Method dump skipped, instructions count: 600
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.AutofillInlineSuggestionsRequestSession.maybeUpdateResponseToImeLocked():void");
    }

    public final boolean onInlineSuggestionsResponseLocked(InlineFillUi inlineFillUi) {
        if (this.mDestroyed) {
            return false;
        }
        if (Helper.sDebug) {
            Slog.d(
                    "AutofillInlineSuggestionsRequestSession",
                    "onInlineSuggestionsResponseLocked called for:" + inlineFillUi.mAutofillId);
        }
        if (this.mImeRequest == null
                || this.mResponseCallback == null
                || this.mImeSessionInvalidated) {
            return false;
        }
        this.mAutofillId = inlineFillUi.mAutofillId;
        this.mInlineFillUi = inlineFillUi;
        maybeUpdateResponseToImeLocked();
        return true;
    }
}
